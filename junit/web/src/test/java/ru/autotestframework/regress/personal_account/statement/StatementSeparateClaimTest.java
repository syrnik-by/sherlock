package ru.autotestframework.regress.personal_account.statement;

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
import static ru.autotestframework.util.Validator.assertThat;


@Tag("regress")
@Tag("personal_account")
@Tag("statement")
@Tag("statement_separate_claim")
@ClassName("На каждый кейс отдельная заявка. Личный кабинет. Утверждение")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StatementSeparateClaimTest extends BaseTest {

    private List<String> claim = new ArrayList<>();

    @BeforeEach
    public void login() {
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi();
        }
        loginPage.openMenuLinks("Личный кабинет");
    }

    @AfterEach
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Disabled("Исключен из регресса")
    @Test
    @Tag("vertical_scrolling_1651126")
    @DisplayName("1651126 - Личный кабинет. Утверждение. Пагинация. Вертикальный скроллинг")
    @WorkItemIds({"1651126"})
    public void verticalScrolling_1651126(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_type1_statement.json", 15, testInfo);
        List<String> claims = actionsClaimSteps.assigningClaims(claim, 15);
        personalAccountPage.clickOnElement("Раздел Андеррайтинг").waitBusyCondition()
                .selectValueFromDropDownList("Выпадающий список Отображать по (таблица В работе)", "100");
        statementClaimProcess(claims, 15);
        personalAccountPage.clickOnElement("Раздел Утверждение").waitBusyCondition()
                .checkDropDownListElements("Выпадающий список Отображать по (таблица В работе)", List.of("10", "20", "50", "100"))
                .selectValueFromDropDownList("Выпадающий список Отображать по (таблица В работе)", "20")
                .checkScroll("вертикальный", "Таблица в работе", true);
    }

    @Disabled("Исключен из регресса")
    @Test
    @Tag("pagination_1651127")
    @DisplayName("1651127 - Личный кабинет. Утверждение. Пагинация, выбор значений")
    @WorkItemIds({"1651127"})
    public void pagination_1651127(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_type1_statement.json", 70, testInfo);
        List<String> claims = actionsClaimSteps.assigningClaims(claim, 70);
        personalAccountPage.clickOnElement("Раздел Андеррайтинг").waitBusyCondition()
                .selectValueFromDropDownList("Выпадающий список Отображать по (таблица В работе)", "100");
        statementClaimProcess(claims, 70);
        deferredClaimProcess(claims.stream().limit(20).collect(Collectors.toList()), 20);
        personalAccountPage.clickOnElement("Раздел Утверждение").waitBusyCondition()
                .checkDropDownListElements("Выпадающий список Отображать по (таблица В работе)", List.of("10", "20", "50", "100"))
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
    @Tag("smoke")
    @Tag("fill_fields_by_default_1651122")
    @DisplayName("1651122 - Личный кабинет. Утверждение. Заполнение основных полей по умолчанию")
    @WorkItemIds({"1651122"})
    public void fillFieldsByDefault_1651122(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "firstName", "Юрий",
                "lastName", "Романов",
                "loanSum", "2200000.0",
                "middleName", "Иванович",
                "previousFirstName", "Юрисиус",
                "previousLastName", "Романовский",
                "previousMiddleName", "Иоанович");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1651045.json", 1, testInfo, claimParams);
        List<String> claims = actionsClaimSteps.assigningClaims(claim, 1);
        personalAccountPage.clickOnElement("Раздел Андеррайтинг");
        statementClaimProcess(claims, 1);
        personalAccountPage.clickOnElement("Раздел Утверждение").waitBusyCondition();

        Map<String, String> expectedValues = Map.of(
                "ФИО заемщика", "Романов (Романовский) Юрий (Юрисиус) Иванович (Иоанович)",
                "Сумма кредита", "2 200 000",
                "Номер заявки", claims.get(0));
        for (Map.Entry<String, String> expected : expectedValues.entrySet()) {
            String actualValue = personalAccountPage.getTextFromTable("Таблица в работе", 1, expected.getKey());
            assertIsTrue(actualValue.equals(expected.getValue()),
                    "Значение столбца " + expected.getKey() + " строки 1 должно быть равно " + expected.getValue() + " . Фактическое значение = " + actualValue);
        }
    }

    @Test
    @Tag("fill_additional_fields_1651119")
    @DisplayName("1651119 - Личный кабинет. Утверждение. Заполнение полей из анкеты допполей (Заемщика, Работодателя и создания Заявки)")
    @WorkItemIds({"1651119"})
    public void fillAdditionalFields_1651119(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "birthDate", "1990-12-05T00:00:00.000+00:00",
                "employerTaxPayerNumber", "7714794048",
                "employerName", "ФКУ \\\"ЕРЦ МО РФ\\\" (В/Ч №09436)",
                "issueDate", "2023-04-20T00:00:00.000+00:00",
                "codeFilial", "0",
                "unifiedLimit", "2114000.0",
                "confirmedFormType", "ConfirmedIncomeForm4");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1702209.json", 1, testInfo, claimParams);
        List<String> claims = actionsClaimSteps.assigningClaims(claim, 1);
        personalAccountPage.clickOnElement("Раздел Андеррайтинг");
        statementClaimProcess(claims, 1);
        personalAccountPage.clickOnElement("Раздел Утверждение").waitBusyCondition()
                .clickOnElement("Кнопка Настройка списка(таблица В работе)")
                .goTo(filterListSettingsPage)
                .dragColumns("из правой колонки в левую",
                        List.of("Макс. сумма кредита",
                                "Дата рождения заемщика",
                                "Номер клиента PSB Retail",
                                "Удостоверение личности военнослужащего",
                                "Наименование работодателя",
                                "ИНН работодателя",
                                "Дата создания",
                                "Наименование филиала",
                                "Региональное время",
                                "Форма подтверждения дохода"))
                .checkElementsOnFields("Список активных фильтров",
                        List.of("Макс. сумма кредита",
                                "Номер заявки",
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
    @Tag("authorization_with_process_function_1651129")
    @DisplayName("1651129 - Личный кабинет. Утверждение. Авторизация с процессной функцией \"Утверждение\"")
    @WorkItemIds({"1651129"})
    public void authorizationWithProcessFunction_1651129(TestInfo testInfo) {
        int numberOfClaims = 4;
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1991859.json", numberOfClaims, testInfo);
        List<String> claims = actionsClaimSteps.assigningClaims(claim, numberOfClaims);
        personalAccountPage.clickOnElement("Раздел Андеррайтинг").waitBusyCondition();
        List<String> approvedClaims = statementClaimProcess(claims, numberOfClaims);
        List<String> pendingClaims = deferredClaimProcess(claims.stream().limit(2).collect(Collectors.toList()), 2);
        approvedClaims.removeAll(pendingClaims);
        personalAccountPage.clickOnElement("Раздел Утверждение");
        List<String> workClaims = takingOnWorkClaimProcess(approvedClaims, 2);
        personalAccountPage.clickOnElement("Чек-бокс ЦСКО")
                .clickOnElement("Чек-бокс ГО")
                .clickOnElement("Кнопка раскрыть таблицу Отложено");
        for (int i = 0; i < 2; i++) {
            assertIsTrue(personalAccountPage.getRowNumberByClaim(workClaims.get(i), "Таблица в работе") != -1,
                    "Заявка " + workClaims.get(i) + " содержится в таблице в работе");
            assertIsTrue(personalAccountPage.getRowNumberByClaim(pendingClaims.get(i), "Таблица Отложено") != -1,
                    "Заявка " + pendingClaims.get(i) + " содержится в таблице Отложено");
        }
        personalAccountPage.clickOnElement("Чек-бокс ЦСКО")
                .clickOnElement("Чек-бокс ГО")
                .clickOnElement("Кнопка раскрыть таблицу Отложено");
    }

    @Test
    @Tag("reset_sort_1651120")
    @DisplayName("1651120 - Личный кабинет. Утверждение. Очередность сортировки и Кнопка \"Сбросить сортировку\"")
    @WorkItemIds({"1651120"})
    public void resetSort_1651120(TestInfo testInfo) {
        List<String> loanSums = List.of("2200000", "2100000", "200000", "2100000", "2100000");
        for (String loanSum : loanSums) {
            claim.add(actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1650805.json", 1, testInfo, Map.of("loanSum", loanSum)).get(0));
        }
        actionsClaimSteps.assigningClaims(claim, 5);
        personalAccountPage.clickOnElement("Раздел Андеррайтинг").waitBusyCondition()
                .selectValueFromDropDownList("Выпадающий список Отображать по (таблица В работе)", "100");
        statementClaimProcess(claim, 5);
        personalAccountPage.clickOnElement("Раздел Утверждение");
        takingOnWorkClaimProcess(claim, 5);
        personalAccountPage
                .clickOnCheckBox("Отключить", "ЦСКО")
                .clickOnCheckBox("Отключить", "ГО")
                .clickOnElement("Кнопка Сбросить сортировку");
        Map<String, List<String>> tableBefore = personalAccountPage.getTableHeadersAndContent("Таблица в работе");
        List<String> timeRPactual = personalAccountPage.getListValuesByColumnName("Таблица в работе", "Время попадания на РП");
        List<String> timeRPexpected = personalAccountPage.getSortedList(timeRPactual, "timestamp", "убыванию");
        assertIsTrue(timeRPexpected.equals(timeRPactual), "Значения в столбце Время попадания на РП таблицы В работе являются отсортированными по убыванию");

        //шаг 18
        List<String> sumCreditExpected = personalAccountPage.getSortedList(personalAccountPage.getListValuesByColumnName("Таблица в работе", "Сумма кредита"), "bigint", "возрастанию");
        personalAccountPage.clickOnElement("сортировка Столбец Сумма кредита")
                .checkElementByTitleEquals("сортировка Столбец Сумма кредита", "1");
        List<String> sumCreditActual = removeSpaces(personalAccountPage.getListValuesByColumnName("Таблица в работе", "Сумма кредита"));

        assertIsTrue(sumCreditExpected.equals(sumCreditActual), "Значения в столбце Сумма кредита таблицы В работе являются отсортированными по возрастанию");

        //шаг 19
        sumCreditExpected = personalAccountPage.getSortedList(personalAccountPage.getListValuesByColumnName("Таблица в работе", "Сумма кредита"),
                "bigint", "возрастанию");
        List<String> typeOfLoanExpected = personalAccountPage.getSortedList(personalAccountPage.getListValuesByColumnName("Таблица в работе", "Вид кредита"),
                "string", "возрастанию");
        personalAccountPage.clickOnElement("сортировка Столбец Вид кредита")
                .checkElementByTitleEquals("сортировка Столбец Вид кредита", "2");
        sumCreditActual = removeSpaces(personalAccountPage.getListValuesByColumnName("Таблица в работе", "Сумма кредита"));
        assertIsTrue(sumCreditExpected.equals(sumCreditActual), "Значения в столбце Сумма кредита таблицы В работе являются отсортированными по возрастанию");
        List<String> typeOfLoanActual = personalAccountPage.getListValuesByColumnName("Таблица в работе", "Вид кредита");
        assertIsTrue(typeOfLoanExpected.equals(typeOfLoanActual), "Значения в столбце Вид кредита таблицы В работе являются отсортированными по возрастанию");

        //шаг 20
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

        //шаг 21
        personalAccountPage.clickOnElement("Кнопка Сбросить сортировку");
        Map<String, List<String>> tableAfter = personalAccountPage.getTableHeadersAndContent("Таблица в работе");
        assertIsTrue(tableBefore.equals(tableAfter), "Значения и порядок их следования в таблице совпадают с первоначальным");
        personalAccountPage.clickOnCheckBox("Включить", "ЦСКО")
                .clickOnCheckBox("Включить", "ГО");
    }

    @Test
    @Tag("reset_sort_sum_credit_1651117")
    @DisplayName("1651117 - Личный кабинет. Утверждение. Сортировка по 1 столбцу \"Сумма кредита\". По возрастанию, убыванию, сброс.")
    @WorkItemIds({"1651117"})
    public void resetSortSumCredit_1651117(TestInfo testInfo) {
        List<String> loanSums = List.of("2200000", "2100000", "200000");
        for (String loanSum : loanSums) {
            claim.add(actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1650805.json", 1, testInfo, Map.of("loanSum", loanSum)).get(0));
        }
        actionsClaimSteps.assigningClaims(claim, 3);
        personalAccountPage.clickOnElement("Раздел Андеррайтинг").waitBusyCondition()
                .selectValueFromDropDownList("Выпадающий список Отображать по (таблица В работе)", "100");
        statementClaimProcess(claim, 3);
        personalAccountPage.clickOnElement("Раздел Утверждение");
        takingOnWorkClaimProcess(claim, 3);
        personalAccountPage.clickOnElement("Чек-бокс ЦСКО")
                .clickOnElement("Чек-бокс ГО")
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
        personalAccountPage.clickOnElement("Чек-бокс ЦСКО")
                .clickOnElement("Чек-бокс ГО");
    }

    @Step
    @Title("Выполнение процесса утверждения заявок {claims} в количестве {numClaims} шт.")
    private List<String> statementClaimProcess(List<String> claims, int numClaims) {
        List<String> approvedClaims = new ArrayList<>();
        for (int i = 0; i < claims.size() && i < numClaims; i++) {
            personalAccountPage.waitBusyCondition().clickOnTextInTable(claims.get(i))
                    .switchToNewTab()
                    .goTo(cardRequestPage)
                    .selectValueFromDropDownList("Выпадающий список Занятость подтверждена", "Звонок не назначался")
                    .fillInput("Поле ввода Внутренний комментарий андеррайтера", "Коментарий АТ")
                    .clickOnElement("Вкладка Решение Андеррайтера")
                    .goTo(underwriterDecisionPage)
                    .selectValueFromDropDownList("Выпадающий список Проведенные проверки", "Проверка критичных данных")
                    .selectValueFromDropDownList("Выпадающий список Полномочия", "Собственные")
                    .selectValueFromDropDownList("Выпадающий список Одобрить/Отклонить", "Одобрить")
                    .selectValueFromDropDownList("Выпадающий список Тип одобрения/Причина отклонения", "Одобрено")
                    .clickOnElement("Кнопка На утверждение")
                    .switchToOneTab();
            approvedClaims.add(claims.get(i));
        }
        checkChangeStatus(approvedClaims);
        return approvedClaims;
    }

    @Step
    @Title("Проверка в БД, что заявки {approvedClaims} утверждены и перешли в статус = 5")
    private void checkChangeStatus(List<String> approvedClaims) {
        assertThat(actionsClaimSteps.changeStatusRequest(approvedClaims, "5").isEmpty(),
                "Заявки " + approvedClaims + " не перешли в статус = 5");
    }

    @Step
    @Title("Выполнение процесса перевода заявок {claims} в отложенные в количестве {numClaims} шт.")
    private List<String> deferredClaimProcess(List<String> claims, int numClaims) {
        List<String> pendingClaims = new ArrayList<>();
        for (int i = 0; i < claims.size() && i < numClaims; i++) {
            personalAccountPage.clickOnElement("Раздел Утверждение")
                    .clickOnTextInTable(claims.get(i))
                    .switchToNewTab()
                    .goTo(cardRequestPage)
                    .clickOnElement("Кнопка Взять в работу")
                    .clickOnElement("Кнопка Отложить")
                    .waitText(10, "Перевод заявки в отложенные")
                    .selectValueFromDropDownList("Выпадающий список Причина (Перевод заявки в отложенные)", "Вопрос в ГО")
                    .clickOnElement("Кнопка Отложить заявку")
                    .switchToOneTab();
            pendingClaims.add(claims.get(i));
        }
        return pendingClaims;
    }

    @Step
    @Title("Выполнение процесса перевода заявок {claims} в отложенные в количестве {numClaims} шт.")
    private List<String> takingOnWorkClaimProcess(List<String> claims, int numClaims) {
        List<String> workClaims = new ArrayList<>();
        for (int i = 0; i < claims.size() && i < numClaims; i++) {
            personalAccountPage.clickOnTextInTable(claims.get(i))
                    .switchToNewTab()
                    .goTo(cardRequestPage)
                    .waitBusyCondition()
                    .clickOnElement("Кнопка Взять в работу")
                    .waitBusyCondition()
                    .clickOnElement("Кнопка Выйти без сохранения")
                    .switchToOneTab();
            workClaims.add(claims.get(i));
        }
        return workClaims;
    }
}