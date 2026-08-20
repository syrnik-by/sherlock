package ru.autotestframework.steps.dbApiSteps;

import io.restassured.http.Header;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.autotestframework.core.FileLoaderImpl;
import ru.autotestframework.core.context.Context;
import ru.autotestframework.core.exception.ExecutionException;
import ru.autotestframework.dataprovider.DbProperties;
import ru.autotestframework.http_steps.HttpSteps;
import ru.autotestframework.http_steps.components.RequestContainer;
import ru.autotestframework.http_steps.components.restassured.RestAssuredConfiguration;
import ru.autotestframework.utils.ClaimFileManager;
import ru.psb.testit.annotations.Description;
import ru.psb.testit.annotations.Step;
import ru.psb.testit.annotations.Title;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.restassured.path.json.JsonPath.with;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static ru.autotestframework.pages.BasePage.sleep;
import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;
import static ru.autotestframework.utils.Constants.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActionsClaimSteps extends DbSteps {

    @Autowired
    protected ApiServiceSteps apiServiceSteps;

    @Autowired
    protected Context context;

    @Autowired
    protected RequestContainer requestContainer;

    @Autowired
    protected DbProperties dbProperties;

    @Autowired
    protected RestAssuredConfiguration raConfig;

    @Autowired
    protected FileLoaderImpl fileLoader;

    @Autowired
    private ClaimFileManager claimFileManager;

    private String claimNumber;

    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String AUTH_URL = "auth_ldap/keycloak/token";
    private static final int MAX_ATTEMPTS = 10;
    private static final int INTERVAL = 2;

    protected HttpSteps getSteps() {
        return new HttpSteps(
                context,
                fileLoader,
                raConfig,
                requestContainer);
    }

    @Step
    @Title("создать уникальный claim_id по коннектору")
    @Description("Создание уникального claim_id")
    public void generateClaimId() {
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            String claimId = generateUniqueClaimId();
            executeQuery(REQUESTS, buildClaimIdQuery(claimId));
            if (getCountRecordsFromQuery() == 0) {
                context.set("claim_id", claimId);
                return;
            }
        }
        throw new RuntimeException("Не удалось получить claim_id. Превышено количество попыток.");
    }

    private String buildClaimIdQuery(String claimId) {
        return String.format("SELECT status_id FROM requests.rqs_request rr " +
                "WHERE rr.claim_id ='CLL.%s_tstAT' " +
                "OR rr.claim_id = 'RKKL.%s.0_tstAT' " +
                "OR rr.claim_id = 'RKKL.%s_vrf_tstAT'", claimId, claimId, claimId);
    }

    /**
     * Эмулирует создание предыдущей заявки
     *
     * <p> Проводит получение авторизоционного токена и эмулирует создание предыдущей заявки
     * для клиента с id = clientId
     *
     * @param clientId    id клиента
     * @param claimNumber номер шаблона заявки. Обычно равен id тест-кейса
     */
    @Step
    @Title("Эмулировать создание предыдущей заявки")
    public void emulateCreationOfPreviousClaim(String clientId, String claimNumber) {
        setAuthorizationToken("token");
        apiServiceSteps.postPreviousClaim(context.get("token"), clientId, claimNumber, 201);
    }

    @Step
    @Title("Повторно отправить ранее отправленную заявку на стенд ЕКЛ")
    public LocalDateTime repeatSendSclRequestToStand(String claimStatus, String... customClaimPath) {
        assertIsTrue(changeStatusRequest(Collections.singletonList(claimNumber), claimStatus).isEmpty(),
                "Проверить, что заявка № " + claimNumber + " перешла в статус " + claimStatus);
        if (customClaimPath.length > 0) {
            context.set("clientId", getValueByJsonPathFromRequestBody("claimWithVersions[0].forms[0].formPrimary.clientId"));
            sendRequest(true, customClaimPath[0], false);
        } else {
            sendRequest(false, context.get("jsonRequest0"), false);
        }
        assertIsTrue(changeStatusRequest(Collections.singletonList(claimNumber), "4").isEmpty(),
                "Проверить, что заявка № " + claimNumber + " перешла в статус 4. Фактическое значение = " + getStatusClaimFromDb(claimNumber));
        return LocalDateTime.now();
    }


    /**
     * Отправляет заявку и проверяет код ответа
     *
     * <p> Подготавливает запрос для отправки, при указании isAuthorizationNeeded производит авторизацию пользователя
     * и сохраняет токен в контекст. Отправляет заявку на стенд не более чем MAX_ATTEMPTS раз и проверяет код ответа на равенство 200.
     *
     * @param isPath                содержится ли тело запроса в файле
     * @param bodyOrPath            тело запроса или путь к файлу, содержащий тело запроса
     * @param isAuthorizationNeeded требуется ли получение токена авторизации
     * @param endPoint              адрес отправки (опционально)
     */
    private void sendRequest(boolean isPath, String bodyOrPath, boolean isAuthorizationNeeded, String... endPoint) {
        Response response = null;
        setUpRequest(isPath, bodyOrPath, endPoint);
        if (isAuthorizationNeeded) {
            setAuthorizationToken("token");
        }
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            apiServiceSteps.sendRequest(Method.POST);
            response = requestContainer.getResponse();
            if (response.statusCode() == 200) {
                return;
            }
            sleep(INTERVAL);
        }
        throw new ExecutionException("Не удалось отправить заявку. Код ответа: " + response.statusCode() + ". " +
                "Количество совершенных попыток отправки заявки: " + MAX_ATTEMPTS + ". " +
                "Интервал времени между попытками: " + INTERVAL + " сек.\n" +
                "Дата и время последней попытки отправки заявки: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")) + "\n" +
                "Response headers:\n" + response.getHeaders().toString() + "\n" +
                "Response body:\n" + response.getBody().prettyPrint() + "\n" +
                "Request body:\n" + getRequestBody());
    }

    /**
     * Подготавливает запрос для отправки
     *
     * <p> Устанавливает url, устанавливает стандартный endpoint если не предоставлен опциональный параметр endPoint,
     * если параметр предоставлен - устанавливает его как endpoint, устанавливает заголовок запроса
     *
     * @param isPath     содержится ли тело запроса в файле
     * @param bodyOrPath тело запроса или путь к файлу, содержащий тело запроса
     * @param endPoint   адрес отправки (опционально)
     */
    private void setUpRequest(boolean isPath, String bodyOrPath, String... endPoint) {
        apiServiceSteps.setUrl(dbProperties.getLkaUrl());
        apiServiceSteps.setEndpoint(endPoint.length > 0 ? endPoint[0] : "rcc-integration/rccFormatRequest/acceptRequest");
        if (isPath) {
            getSteps().setBodyByPath(bodyOrPath);
        } else {
            apiServiceSteps.setBody(bodyOrPath);
        }
        apiServiceSteps.setHeaders(List.of(new Header("Content-Type", CONTENT_TYPE_JSON)));
    }

    @Step
    @Title("отправить заявку ЕКЛ на стенд с body {path} в количестве = {numberOfClaims} шт.")
    public List<String> sendSclRequestToStandWithSpecifiedJson(String path, int numberOfClaims, TestInfo testInfo, Map<String, String>... params) {
        List<String> claims = new ArrayList<>();
        for (int i = 0; i < numberOfClaims; i++) {
            setRequestParams(i, params);
            createAndSendHttpRequest(path);
            context.set("jsonRequest" + i, getRequestBody());
            String claim = checkClaimDb(testInfo);
            claims.add(claim);
            try {
                claimFileManager.createClaimFile(Path.of("tmp/"));
            } catch (Exception e) {
                log.error("Ошибка создания файла: "+ e.getMessage());
            }
            claimFileManager.writeClaimToFileIfNotExists(claim);
            if (numberOfClaims > 1) {
                sleep(INTERVAL); // Задержка перед созданием следующей заявки, если их требуется > 1
            }
        }
        return claims;
    }

    @SafeVarargs
    private void setRequestParams(int index, Map<String, String>... params) {
        if (params.length > 0) {
            Map<String, String> paramsAtIndex = params[index];
            for (Map.Entry<String, String> entry : paramsAtIndex.entrySet()) {
                context.set(entry.getKey(), entry.getValue());
            }
        }
    }

    private String getRequestBody() {
        try {
            Object request = requestContainer.getSpecification().request().log().all();
            Field field = request.getClass().getDeclaredField("requestBody");
            field.setAccessible(true);
            return field.get(request).toString();
        } catch (IllegalAccessException | NullPointerException e) {
            log.error("Ошибка доступа к полю: {}", e.getMessage());
            return "Ошибка доступа к полю";
        } catch (NoSuchFieldException e) {
            log.error("Отсутствует поле 'requestBody': {}", e.getMessage());
            return "Отсутствует поле 'requestBody'";
        }
    }

    @Step
    @Title("Получение значения поля из заявки по jsonPath = {jsonPath}")
    public String getValueByJsonPathFromRequestBody(String jsonPath) {
        return with(context.get("jsonRequest0")).get(jsonPath).toString();
    }

    @Step
    @Title("Проверка наличия отправленной заявки в БД Шерлок requests")
    private String checkClaimDb(TestInfo testInfo) {
        int attempt;
        claimNumber = buildClaimId(context.get("claim_id"), testInfo);
        for (attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            executeQuery(REQUESTS, "select status_id from requests.rqs_request rr " +
                    "where rr.claim_id ='" + claimNumber + "'");
            if (getCountRecordsFromQuery() > 0) {
                context.set("status", getVariables("status_id"));
                if (context.get("status").equals("3")) {
                    if (checkClaimDbVerification(testInfo, claimNumber)) {
                        return claimNumber;
                    } else {
                        throw new ExecutionException("Заявка " + claimNumber + " не прошла проверку в БД на Верификацию");
                    }
                }
            }
            sleep(INTERVAL);
        }
        throw new ExecutionException(
                "Количество совершенных попыток поиска заявки " + claimNumber + " в статусе = 3: " + (attempt + 1) + ". " +
                        "\nИнтервал времени между попытками: " + INTERVAL + " сек.\n" +
                        "\nДата и время последней попытки поиска заявки в БД: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")) +
                        "\nИстория статусов:" +
                        "\n" + getStatusHistory(claimNumber));
    }

    @Step
    @Title("Проверка наличия/отсутсвия отправленной заявки на Верификации")
    private boolean checkClaimDbVerification(TestInfo testInfo, String claim) {
        executeQuery(VERIFICATION, "SELECT claim_id FROM vrf_check_set " +
                "WHERE claim_id ='" + claim + "'");
        return (testInfo.getTags().stream().noneMatch(Arrays.asList("card_request", "verification", "search", "working_with_application")::contains)) || (testInfo.getTags().stream().anyMatch(List.of("no_check_verification")::contains)) ? getCountRecordsFromQuery() == 0 : getCountRecordsFromQuery() > 0;
    }

    private String buildClaimId(String claimId, TestInfo testInfo) {
        if (testInfo.getTags().contains("card_application")) {
            return "CLL." + claimId + "_tstAT";
        } else if (testInfo.getTags().contains("previous_claims")) {
            return "RKKL." + claimId + "_vrf_vrfAT";
        } else if (testInfo.getTags().stream().anyMatch(Arrays.asList("queues", "card_request", "search", "monitoring", "personal_account", "verification", "working_with_application")::contains)) {
            return "RKKL." + claimId + "_vrf_tstAT";
        } else return "RKKL." + claimId + ".0_tstAT";
    }

    private String generateUniqueClaimId() {
        return String.valueOf(new Faker().number().numberBetween(1, 99999999));
    }

    private void createAndSendHttpRequest(String path) {
        generateClaimId();
        sendRequest(true, path, false);
    }

    public void setAuthorizationToken(String tokenVariableName) {
        apiServiceSteps.setUrl(dbProperties.getLkaUrl());
        String bodyParameter = "username=" + dbProperties.getLkaUser() +
                "&password=" + dbProperties.getLkaPassword() +
                "&grant_type=" + dbProperties.getLkaPassword() +
                "&client_id=mcClient" +
                "&client_secret=" + dbProperties.getAuthorizationClientSecret();
        RequestSpecification specification = getSteps().getSpecification().basePath(AUTH_URL)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .and().body(bodyParameter);
        Response response = specification.when().request(Method.POST);

        if (response.getStatusCode() == 200) {
            String authorizationToken = "Bearer " + response.jsonPath().get("access_token");
            log.info(authorizationToken);
            context.set(tokenVariableName, authorizationToken);
        } else {
            throw new ExecutionException("Статус запроса получения авторизационного токена = " + response.getStatusCode());
        }
    }

    @Step
    @Title("Проверка возможности дальнейшего назначения заявки {claimNumber}")
    private void checkPossibilityAssigningClaim(String claimNumber) {
        int attempt;
        for (attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            executeQuery(REQUESTS, "SELECT * FROM requests.rqs_user_task_info " +
                    "WHERE claim_id = '" + claimNumber + "' AND is_active limit 1");
            if (getCountRecordsFromQuery() > 0) {
                return;
            }
            sleep(INTERVAL);
        }
        throw new ExecutionException(
                "Количество совершенных попыток проверки назначения заявки " + claimNumber + " : " + (attempt + 1) + ". " +
                        "\nИнтервал времени между попытками: " + INTERVAL + " сек.\n" +
                        "\nДата и время последней попытки поиска заявки в БД: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
    }

    @Step
    @Title("Назначить заявку номер {claimNumber} на пользователя {userLogin}")
    public void appointResponsiblePerson(String claimNumber, String userLogin) {
        setAuthorizationToken("token");
        executeQuery(REQUESTS, "SELECT id FROM requests.rqs_request WHERE claim_id ='" + claimNumber + "'");
        context.set("id", getVariables("id"));
        String id = getVariables("id");

        apiServiceSteps.putDistributeRequests(context.get("token"),
                apiServiceSteps.createBodyDistributeRequests(id, userLogin), 200);

        boolean isAssigned = changeStatusRequest(Collections.singletonList(claimNumber), "4").isEmpty();
        if (!isAssigned) {
            sleep(3);
            throw new ExecutionException("\nОшибка перехода заявки № " + claimNumber + " в статус 4 (на рассмотрении)." +
                    "\nИстория статусов:" +
                    "\n" + getStatusHistory(claimNumber));
        }
    }

    @Step
    @Title("Назначить заявку номер {claimNumber} на пользователя testat1")
    public void appointResponsiblePerson(String claimNumber) {
        checkPossibilityAssigningClaim(claimNumber);
        appointResponsiblePerson(claimNumber, "testat1");
    }

    public Set<String> changeStatusRequest(List<String> claimNumber, String waitingStatus) {
        Set<String> claimsNotInStatus = new HashSet<>();
        String claims = claimNumber.stream().map(s -> "'" + s + "'").collect(Collectors.joining(", "));

        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            executeQuery(REQUESTS, "SELECT claim_id, status_id " +
                    "FROM requests.rqs_request " +
                    "WHERE claim_id in (" + claims + ") and status_id not in (21, 25)");

            List<String> claimIds = getValuesFromResponseDb("claim_id");
            List<String> statusIds = getValuesFromResponseDb("status_id");

            Map<String, String> claimStatusMap = IntStream.range(0, claimIds.size())
                    .boxed()
                    .collect(Collectors.toMap(
                            claimIds::get,    // Ключ - claim_id
                            statusIds::get    // Значение - статус
                    ));

            for (Map.Entry<String, String> entry : claimStatusMap.entrySet()) {
                String claimId = entry.getKey();
                String statusId = entry.getValue();
                if (!statusId.equals(waitingStatus)) {
                    claimsNotInStatus.add(claimId);
                } else {
                    claimsNotInStatus.remove(claimId);
                }
            }

            if (!claimsNotInStatus.isEmpty()) {
                sleep(INTERVAL);
            } else {
                break;
            }
        }
        return claimsNotInStatus;
    }

    @Step
    @Title("Вернуть количество минут для автоматического возврата отложенной заявки")
    public int getTimeAutomaticRefundApplication() {
        executeQuery(EMPLOYEE, "SELECT value " +
                "FROM employee.epl_system_setting ess " +
                "JOIN employee.epl_setting_field_type esft on esft.id = ess.setting_field_type_id " +
                "WHERE esft.description = 'Количество минут для автоматического возврата отложенной заявки'");
        return Integer.parseInt(getVariables("value"));
    }

    @Step
    @Title("Проверить, что статус заявки {claimNumber} = {expectedStatus}")
    public void checkStatusClaimFromDb(String claimNumber, int expectedStatus) {
        assertIsTrue(expectedStatus == getStatusClaimFromDb(claimNumber), "Значение status_id == " + expectedStatus + " для заявки " + claimNumber +
                ". Фактическое значение = " + getStatusClaimFromDb(claimNumber));
    }

    @Step
    @Title("Выполнение процесса назначения заявок в количестве {numberClaims} шт.")
    public List<String> assigningClaims(List<String> claim, int numberClaims) {
        List<String> assigning = new ArrayList<>();
        if (claim.size() >= numberClaims) {
            for (int i = 0; i < numberClaims; i++) {
                appointResponsiblePerson(claim.get(i));
                assigning.add(claim.get(i));
            }
            return assigning;
        } else {
            throw new ExecutionException("Запрашиваемое количество заявок для назначения больше заранее подготовленных.");
        }
    }

    private int getStatusClaimFromDb(String claimNumber) {
        executeQuery(REQUESTS, "SELECT status_id FROM requests.rqs_request WHERE claim_id ='" + claimNumber + "'");
        return Integer.parseInt(getVariables("status_id"));
    }

    private String getStatusHistory(String claimNumber) {
        StringBuilder historyBuilder = new StringBuilder();

        historyBuilder.append("\n");
        historyBuilder.append("+-------------------------+---+----------------------------------+\n"); // Границы таблицы

        executeQuery(REQUESTS,
                "SELECT r.claim_id, rsl.status_type_id, rsl.created_ts, rsl.updated_ts " +
                        "FROM requests.requests.rqs_request AS r " +
                        "JOIN requests.requests.rqs_request_status_log AS rsl " +
                        "ON r.id = rsl.request_id " +
                        "WHERE r.claim_id = '" + claimNumber + "' " +
                        "ORDER BY rsl.created_ts;");

        // Получаем значения для каждого поля
        List<String> claimIds = getValuesFromResponseDb("claim_id");
        List<String> statusTypeIds = getValuesFromResponseDb("status_type_id");
        List<String> createdTimestamps = getValuesFromResponseDb("created_ts");

        // Формируем строки с данными
        for (int i = 0; i < claimIds.size(); i++) {
            historyBuilder.append(String.format("| %-18s | %-12s | %-19s |\n",
                    claimIds.get(i),
                    statusTypeIds.get(i),
                    createdTimestamps.get(i)));
            //   updatedTimestamps.get(i)));
        }
        historyBuilder.append("+-------------------------+---+----------------------------------+\n\n");
        return historyBuilder.toString();
    }

}