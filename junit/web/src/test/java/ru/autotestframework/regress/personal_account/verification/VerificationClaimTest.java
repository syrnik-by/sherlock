package ru.autotestframework.regress.personal_account.verification;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;
import static ru.autotestframework.steps.elements.IDataSteps.removeSpaces;


@Tag("regress")
@Tag("personal_account")
@Tag("verification")
@ClassName("Личный кабинет. Верификация")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VerificationClaimTest extends BaseTest {

    private static List<String> claim = new ArrayList<>();

    @BeforeEach
    public void login() {
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi()
                    .openMenuLinks("Личный кабинет")
                    .goTo(personalAccountPage)
                    .clickOnElement("Раздел Верификация").waitBusyCondition();
        }
    }

    @AfterEach
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("filling_main_fields_default")
    @Tag("smoke")
    @DisplayName("1648082 - Личный кабинет. Заполнение основных полей по умолчанию")
    @WorkItemIds({"1648082"})
    public void filling_main_fields_default_1648082(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "Code", "stub1",
                "firstName", "Юрий",
                "lastName", "Романов",
                "loanSum", "2200000.0",
                "middleName", "Иванович",
                "previousFirstName", "Юрисиус",
                "previousLastName", "Романовский",
                "previousMiddleName", "Иоанович");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1649935.json", 1, testInfo, claimParams);
        actionsClaimSteps.assigningClaims(claim, 1);

        Map<String, String> expectedValues = Map.of(
                "ФИО заемщика", "Романов (Романовский) Юрий (Юрисиус) Иванович (Иоанович)",
                "Сумма кредита", "2 200 000",
                "Стратегия", "ФССП");
        for (Map.Entry<String, String> expected : expectedValues.entrySet()) {
            String actualValue = personalAccountPage.getTextFromTable("Таблица в работе", 1, expected.getKey());
            assertIsTrue(actualValue.equals(expected.getValue()),
                    "Значение столбца " + expected.getKey() + " строки 1 должно быть равно " + expected.getValue() + " . Фактическое значение = " + actualValue);
        }
    }

    @Test
    @Tag("fill_additional_fields_1649942")
    @Tag("smoke")
    @DisplayName("1649942 - Личный кабинет. Заполнение полей из анкеты допполей (Заемщика, Работодателя и создания Заявки)")
    @WorkItemIds({"1649942"})
    public void fillAdditionalFields_1649942(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "Code", "stub1",
                "birthDate", "1990-12-05T00:00:00.000+00:00",
                "employerTaxPayerNumber", "7714794048",
                "employerName", "ФКУ \\\"ЕРЦ МО РФ\\\" (В/Ч №09436)",
                "issueDate", "2023-04-20T00:00:00.000+00:00",
                "codeFilial", "0",
                "unifiedLimit", "2114000.0",
                "confirmedFormType", "ConfirmedIncomeForm4");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1650017.json", 1, testInfo, claimParams);
        actionsClaimSteps.assigningClaims(claim, 1);
        personalAccountPage.clickOnElement("Кнопка Настройка списка(таблица В работе)")
                .goTo(filterListSettingsPage)
                .dragColumns("из правой колонки в левую",
                        List.of("Дата рождения заемщика",
                                "Номер клиента PSB Retail",
                                "Удостоверение личности военнослужащего",
                                "Наименование работодателя",
                                "ИНН работодателя",
                                "Дата создания",
                                "Наименование филиала",
                                "Региональное время",
                                "Макс. сумма кредита",
                                "Форма подтверждения дохода"))
                .checkElementsOnFields("Список активных фильтров",
                        List.of("Номер заявки",
                                "Стратегия",
                                "Время попадания на РП",
                                "ФИО заемщика",
                                "Сумма кредита",
                                "Вид кредита",
                                "Тип заявки",
                                "Программа кредитования",
                                "Статус заявки",
                                "Предыдущий статус",
                                "Дата рождения заемщика",
                                "Номер клиента PSB Retail",
                                "Удостоверение личности военнослужащего",
                                "Наименование работодателя",
                                "ИНН работодателя",
                                "Дата создания",
                                "Наименование филиала",
                                "Региональное время",
                                "Вид кредита",
                                "Макс. сумма кредита",
                                "Форма подтверждения дохода"));
        List<String> expectedFilters = filterListSettingsPage.getFiltersname("Список активных фильтров");
        filterListSettingsPage.clickOnElement("Кнопка Закрыть окно фильтров");
        personalAccountPage.checkTableHeaders("Таблица в работе", expectedFilters);

        Map<String, String> expectedValues = Map.ofEntries(
                Map.entry("Дата рождения заемщика", "05.12.1990"),
                Map.entry("Номер клиента PSB Retail", actionsClaimSteps.getValueByJsonPathFromRequestBody("claimWithVersions[0].forms[0].formPrimary.clientId")),
                Map.entry("Удостоверение личности военнослужащего", "Нет"),
                Map.entry("Наименование работодателя", claimParams.get("employerName").replace("\\", "")),
                Map.entry("ИНН работодателя", claimParams.get("employerTaxPayerNumber")),
                Map.entry("Дата создания", "20.04.2023 03:00"),
                Map.entry("Наименование филиала", "ПАО \"Банк ПСБ\" г.Ярославль"),
                Map.entry("Региональное время", "MSK"),
                Map.entry("Вид кредита", "Единый кредитный лимит"),
                Map.entry("Макс. сумма кредита", "2 114 000"),
                Map.entry("Форма подтверждения дохода", "Зарплатные/пенсионные зачисления в ПСБ"));

        for (Map.Entry<String, String> expected : expectedValues.entrySet()) {
            String actualValue = personalAccountPage.getTextFromTable("Таблица в работе", 1, expected.getKey());
            assertIsTrue(actualValue.equals(expected.getValue()),
                    "Значение столбца " + expected.getKey() + " строки 1 должно быть равно " + expected.getValue() + " . Фактическое значение = " + actualValue);
        }
        personalAccountPage.clickOnElement("Кнопка Настройка списка(таблица В работе)")
                .goTo(filterListSettingsPage).resetFilters();
    }

    @Test
    @Tag("authorization_with_process_function_1644949")
    @DisplayName("1644949 - Личный кабинет. Авторизация с процессной функцией \"ФССП\"")
    @WorkItemIds({"1644949"})
    public void authorizationWithProcessFunction_1644949(TestInfo testInfo) {
        int numberOfClaims = 4;
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1954447.json", numberOfClaims, testInfo);
        List<String> claims = actionsClaimSteps.assigningClaims(claim, numberOfClaims);
        List<String> pendingClaims = deferredClaimProcess(claims.stream().limit(2).collect(Collectors.toList()), 2);
        claims.removeAll(pendingClaims);
        personalAccountPage.clickOnElement("Кнопка раскрыть таблицу Отложено");
        for (int i = 0; i < 2; i++) {
            assertIsTrue(personalAccountPage.getRowNumberByClaim(claims.get(i), "Таблица в работе") != -1,
                    "Заявка " + claims.get(i) + " содержится в таблице в работе");
            assertIsTrue(personalAccountPage.getRowNumberByClaim(pendingClaims.get(i), "Таблица Отложено") != -1,
                    "Заявка " + pendingClaims.get(i) + " содержится в таблице Отложено");
        }
    }

    @Disabled("Исключен из регресса")
    @Test
    @Tag("pagination_1644936")
    @DisplayName("1644936 - Личный кабинет. Пагинация. Выбор значений. Этап Верификация")
    @WorkItemIds({"1644936"})
    public void pagination_1644936(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1954447.json", 70, testInfo);
        List<String> claims = actionsClaimSteps.assigningClaims(claim,70);
        personalAccountPage.selectValueFromDropDownList("Выпадающий список Отображать по (таблица В работе)", "100");
        deferredClaimProcess(claims.stream().limit(20).collect(Collectors.toList()), 20);
        personalAccountPage.checkDropDownListElements("Выпадающий список Отображать по (таблица В работе)", List.of("10", "20", "50", "100"))
                .selectValueFromDropDownList("Выпадающий список Отображать по (таблица В работе)", "50")
                .checkRowCount("Таблица в работе", 50)
                .clickOnElement("Кнопка раскрыть таблицу Отложено")
                .checkDropDownListElements("Выпадающий список Отображать по (таблица Отложено)", List.of("10", "20", "50", "100"))
                .selectValueFromDropDownList("Выпадающий список Отображать по (таблица Отложено)", "20")
                .checkRowCount("Таблица Отложено", 20)
                .selectValueFromDropDownList("Выпадающий список Отображать по (таблица В работе)", "10")
                .selectValueFromDropDownList("Выпадающий список Отображать по (таблица Отложено)", "10");
    }

    @Test
    @Tag("vertical_scrolling_1648061")
    @DisplayName("1648061 - Личный кабинет. Пагинация. Вертикальный скроллинг")
    @WorkItemIds({"1648061"})
    public void verticalScrolling_1648061(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1954447.json", 15, testInfo);
        actionsClaimSteps.assigningClaims(claim,15);
        personalAccountPage.selectValueFromDropDownList("Выпадающий список Отображать по (таблица В работе)", "100")
                .checkDropDownListElements("Выпадающий список Отображать по (таблица В работе)", List.of("10", "20", "50", "100"))
                .selectValueFromDropDownList("Выпадающий список Отображать по (таблица В работе)", "20")
                .checkScroll("вертикальный", "Таблица в работе", true);
    }

    @Test
    @Tag("reset_sort_sum_credit_1644938")
    @DisplayName("1644938 - Личный кабинет. Сортировка по 1 столбцу \"Сумма кредита\". По возрастанию, убыванию, сброс.")
    @WorkItemIds({"1644938"})
    public void resetSortSumCredit_1644938(TestInfo testInfo) {
        List<String> loanSums = List.of("2200000", "2100000", "200000");
        for (String loanSum : loanSums) {
            claim.add(actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1954506.json", 1, testInfo, Map.of("loanSum", loanSum)).get(0));
        }
        actionsClaimSteps.assigningClaims(claim,3);
        personalAccountPage.selectValueFromDropDownList("Выпадающий список Отображать по (таблица В работе)", "100")
                .clickOnElement("Кнопка Сбросить сортировку");

        List<String> timeRPactual = personalAccountPage.getListValuesByColumnName("Таблица в работе", "Время попадания на РП");
        List<String> timeRPexpected = personalAccountPage.getSortedList(timeRPactual, "timestamp", "убыванию");
        assertIsTrue(timeRPexpected.equals(timeRPactual), "Значения в столбце Время попадания на РП таблицы В работе являются отсортированными по убыванию");

        personalAccountPage.clickOnElement("сортировка Столбец Сумма кредита")
                .checkElementByTitleEquals("сортировка Столбец Сумма кредита", "1");
        List<String> sumCreditActual = removeSpaces(personalAccountPage.getListValuesByColumnName("Таблица в работе", "Сумма кредита"));
        List<String> sumCreditExpected = personalAccountPage.getSortedList(sumCreditActual, "bigint", "возрастанию");
        assertIsTrue(sumCreditExpected.equals(sumCreditActual), "Значения в столбце Сумма кредита таблицы В работе являются отсортированными по возрастанию");

        personalAccountPage.clickOnElement("сортировка Столбец Сумма кредита");
        sumCreditActual = removeSpaces(personalAccountPage.getListValuesByColumnName("Таблица в работе", "Сумма кредита"));
        sumCreditExpected = personalAccountPage.getSortedList(sumCreditActual, "bigint", "убыванию");
        assertIsTrue(sumCreditExpected.equals(sumCreditActual), "Значения в столбце Сумма кредита таблицы В работе являются отсортированными по убыванию");

        personalAccountPage.clickOnElement("сортировка Столбец Сумма кредита");
        timeRPactual = personalAccountPage.getListValuesByColumnName("Таблица в работе", "Время попадания на РП");
        timeRPexpected = personalAccountPage.getSortedList(timeRPactual, "timestamp", "убыванию");
        assertIsTrue(timeRPexpected.equals(timeRPactual), "Значения в столбце Время попадания на РП таблицы В работе являются отсортированными по убыванию");

    }

    @Test
    @Tag("reset_sort_1648042")
    @Tag("smoke")
    @DisplayName("1648042 - Личный кабинет. Очередность сортировки и Кнопка \"Сбросить сортировку\"")
    @WorkItemIds({"1648042"})
    public void resetSort_1648042(TestInfo testInfo) {
        List<String> loanSums = List.of("2200000", "2100000", "200000", "2100000", "2100000");
        for (String loanSum : loanSums) {
            claim.add(actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1954506.json", 1, testInfo, Map.of("loanSum", loanSum)).get(0));
        }
        actionsClaimSteps.assigningClaims(claim,5);
        personalAccountPage.selectValueFromDropDownList("Выпадающий список Отображать по (таблица В работе)", "100")
                .clickOnElement("Кнопка Сбросить сортировку");

        List<String> timeRPactual = personalAccountPage.getListValuesByColumnName("Таблица в работе", "Время попадания на РП");
        List<String> timeRPexpected = personalAccountPage.getSortedList(timeRPactual, "timestamp", "убыванию");
        assertIsTrue(timeRPexpected.equals(timeRPactual), "Значения в столбце Время попадания на РП таблицы В работе являются отсортированными по убыванию");

        List<String> sumCreditExpected = personalAccountPage.getSortedList(personalAccountPage.getListValuesByColumnName("Таблица в работе", "Сумма кредита"), "bigint", "возрастанию");
        personalAccountPage.clickOnElement("сортировка Столбец Сумма кредита")
                .checkElementByTitleEquals("сортировка Столбец Сумма кредита", "1");
        List<String> sumCreditActual = removeSpaces(personalAccountPage.getListValuesByColumnName("Таблица в работе", "Сумма кредита"));

        assertIsTrue(sumCreditExpected.equals(sumCreditActual), "Значения в столбце Сумма кредита таблицы В работе являются отсортированными по возрастанию");

        sumCreditExpected = personalAccountPage.getSortedList(personalAccountPage.getListValuesByColumnName("Таблица в работе", "Сумма кредита"),
                "bigint", "возрастанию");
        List<String> typeOfLoanExpected = personalAccountPage.getSortedListWithNeighboringColumns(
                "Таблица в работе", "Вид кредита", "Сумма кредита",
                "string", "возрастанию");
        personalAccountPage.clickOnElement("сортировка Столбец Вид кредита")
                .checkElementByTitleEquals("сортировка Столбец Вид кредита", "2");
        sumCreditActual = removeSpaces(personalAccountPage.getListValuesByColumnName("Таблица в работе", "Сумма кредита"));
        assertIsTrue(sumCreditExpected.equals(sumCreditActual), "Значения в столбце Сумма кредита таблицы В работе являются отсортированными по возрастанию");
        List<String> typeOfLoanActual = personalAccountPage.getListValuesByColumnName("Таблица в работе", "Вид кредита");
        assertIsTrue(personalAccountPage.assertIsSubsequence(typeOfLoanExpected, typeOfLoanActual), "Значения в столбце Вид кредита таблицы В работе являются отсортированными по возрастанию");

        timeRPexpected = personalAccountPage.getSortedListWithNeighboringColumns("Таблица в работе",
                "Время попадания на РП", "Сумма кредита", "timestamp", "возрастанию");
        sumCreditExpected = personalAccountPage.getSortedList(personalAccountPage.getListValuesByColumnName("Таблица в работе", "Сумма кредита"),
                "bigint", "возрастанию");
        personalAccountPage.clickOnElement("сортировка Столбец Время попадания на РП")
                .checkElementByTitleEquals("сортировка Столбец Время попадания на РП", "3");
        timeRPactual = personalAccountPage.getListValuesByColumnName("Таблица в работе", "Время попадания на РП");
        assertIsTrue(personalAccountPage.assertIsSubsequence(timeRPexpected, timeRPactual), "Значения в столбце Время попадания на РП таблицы В работе являются отсортированными по возрастанию");
        sumCreditActual = removeSpaces(personalAccountPage.getListValuesByColumnName("Таблица в работе", "Сумма кредита"));
        assertIsTrue(sumCreditExpected.equals(sumCreditActual), "Значения в столбце Сумма кредита таблицы В работе являются отсортированными по возрастанию");

        personalAccountPage.clickOnElement("Кнопка Сбросить сортировку");
        timeRPactual = personalAccountPage.getListValuesByColumnName("Таблица в работе", "Время попадания на РП");
        timeRPexpected = personalAccountPage.getSortedList(timeRPactual, "timestamp", "убыванию");
        assertIsTrue(timeRPexpected.equals(timeRPactual), "Значения в столбце Время попадания на РП таблицы В работе являются отсортированными по убыванию");
    }

    @Step
    @Title("Выполнение процесса перевода заявок {claims} в отложенные в количестве {numClaims} шт.")
    private List<String> deferredClaimProcess(List<String> claims, int numClaims) {
        List<String> pendingClaims = new ArrayList<>();
        for (int i = 0; i < claims.size() && i < numClaims; i++) {
            personalAccountPage
                    .doubleClickByText(claims.get(i))
                    .switchToNewTab()
                    .goTo(cardRequestPage)
                    .clickOnElement("Кнопка Отложить")
                    .waitText(10, "Перевод заявки в отложенные")
                    .selectValueFromDropDownList("Выпадающий список Причина (Перевод заявки в отложенные)", "Вопрос в ГО")
                    .clickOnElement("Кнопка Отложить заявку")
                    .switchToOneTab().waitBusyCondition();
            pendingClaims.add(claims.get(i));
        }
        return pendingClaims;
    }

}
