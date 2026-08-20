package ru.autotestframework.regress.personal_account.underwriting;

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
@Tag("underwriting")
@Tag("underwriting_separate_claim")
@ClassName("На каждый кейс отдельная заявка. Личный кабинет. Андеррайтинг")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UnderwritingSeparateClaimTest extends BaseTest {

    private List<String> claim = new ArrayList<>();

    @BeforeEach
    public void login() {
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi()
                    .openMenuLinks("Личный кабинет");
        }
    }

    @AfterEach
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("smoke")
    @Tag("fill_fields_by_default_1650686")
    @DisplayName("1650686 - Личный кабинет. Андеррайтинг. Заполнение основных полей по умолчанию")
    @WorkItemIds({"1650686"})
    public void fillFieldsByDefault_1650686(TestInfo testInfo) {
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
        personalAccountPage.clickOnElement("Раздел Андеррайтинг").waitBusyCondition();

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
    @Tag("smoke")
    @Tag("fill_additional_fields_1650676")
    @DisplayName("1650676 - Личный кабинет. Андеррайтинг. Заполнение полей из анкеты допполей (Заемщика, Работодателя и создания Заявки)")
    @WorkItemIds({"1650676"})
    public void fillAdditionalFields_1650676(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "birthDate", "1990-12-05T00:00:00.000+00:00",
                "employerTaxPayerNumber", "7714794048",
                "employerName", "ФКУ \\\"ЕРЦ МО РФ\\\" (В/Ч №09436)",
                "issueDate", "2023-04-20T00:00:00.000+00:00",
                "codeFilial", "0",
                "unifiedLimit", "2114000.0",
                "confirmedFormType", "ConfirmedIncomeForm4");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1702209.json", 1, testInfo, claimParams);
        actionsClaimSteps.assigningClaims(claim, 1);
        personalAccountPage.clickOnElement("Раздел Андеррайтинг")
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
    @Tag("reset_sort_1650675")
    @DisplayName("1650675 - Личный кабинет. Андеррайтинг. Очередность сортировки и Кнопка \"Сбросить сортировку\"")
    @WorkItemIds({"1650675"})
    public void resetSort_1650675(TestInfo testInfo) {
        List<String> loanSums = List.of("2200000", "2100000", "200000", "2100000", "2100000");
        for (String loanSum : loanSums) {
            claim.add(actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1956230.json", 1, testInfo, Map.of("loanSum", loanSum)).get(0));
        }
        actionsClaimSteps.assigningClaims(claim, 5);
        personalAccountPage.clickOnElement("Раздел Андеррайтинг").waitBusyCondition()
                .selectValueFromDropDownList("Выпадающий список Отображать по (таблица В работе)", "100")
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

    @Test
    @Tag("reset_sort_sum_credit_1650681")
    @DisplayName("1650681 - Личный кабинет. Андеррайтинг. Сортировка по 1 столбцу \"Сумма кредита\". По возрастанию, убыванию, сброс.")
    @WorkItemIds({"1650681"})
    public void resetSortSumCredit_1650681(TestInfo testInfo) {
        List<String> loanSums = List.of("2200000", "2100000", "200000");
        for (String loanSum : loanSums) {
            claim.add(actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1650805.json", 1, testInfo, Map.of("loanSum", loanSum)).get(0));
        }
        actionsClaimSteps.assigningClaims(claim, 3);
        personalAccountPage.clickOnElement("Раздел Андеррайтинг").waitBusyCondition()
                .selectValueFromDropDownList("Выпадающий список Отображать по (таблица В работе)", "100")
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
    @Tag("authorization_with_process_function_1650682")
    @DisplayName("1650682 - Личный кабинет. Андеррайтинг. Авторизация с процессной функцией \"Андеррайтинг\"")
    @WorkItemIds({"1650682"})
    public void authorizationWithProcessFunction_1650682(TestInfo testInfo) {
        int numberOfClaims = 4;
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1956228.json", numberOfClaims, testInfo);
        List<String> claims = actionsClaimSteps.assigningClaims(claim, numberOfClaims);
        personalAccountPage.clickOnElement("Раздел Андеррайтинг").waitBusyCondition();
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
    @Tag("pagination_1650678")
    @DisplayName("1650678 - Личный кабинет. Андеррайтинг. Пагинация, выбор значений")
    @WorkItemIds({"1650678"})
    public void pagination_1650678(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1956228.json", 70, testInfo);
        List<String> claims = actionsClaimSteps.assigningClaims(claim, 70);
        personalAccountPage.clickOnElement("Раздел Андеррайтинг").waitBusyCondition()
                .selectValueFromDropDownList("Выпадающий список Отображать по (таблица В работе)", "100");
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

    @Disabled("Исключен из регресса")
    @Test
    @Tag("smoke")
    @Tag("vertical_scrolling_1650677")
    @DisplayName("1650677 - Личный кабинет. Утверждение. Пагинация. Вертикальный скроллинг")
    @WorkItemIds({"1650677"})
    public void verticalScrolling_1650677(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1956228.json", 15, testInfo);
        actionsClaimSteps.assigningClaims(claim, 15);
        personalAccountPage.clickOnElement("Раздел Андеррайтинг").waitBusyCondition()
                .selectValueFromDropDownList("Выпадающий список Отображать по (таблица В работе)", "100")
                .checkDropDownListElements("Выпадающий список Отображать по (таблица В работе)", List.of("10", "20", "50", "100"))
                .selectValueFromDropDownList("Выпадающий список Отображать по (таблица В работе)", "20")
                .checkScroll("вертикальный", "Таблица в работе", true);
    }

    @Test
    @Tag("fill_field_1650671")
    @DisplayName("1650671 - Личный кабинет. Андеррайтинг. Заполнение полей для заявки на Доработке")
    @WorkItemIds({"1650671"})
    public void fill_field_1650671(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1956228.json", 1, testInfo);
        actionsClaimSteps.assigningClaims(claim, 1);
        personalAccountPage.clickOnElement("Раздел Андеррайтинг").waitBusyCondition()
                .doubleClickByText(claim.get(0))
                .switchToNewTab()
                .goTo(cardRequestPage)
                .fillInput("Поле ввода Внутренний комментарий андеррайтера", "Коментарий АТ")
                .selectValueFromDropDownList("Выпадающий список Занятость подтверждена", "Звонок не назначался")
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .selectValueFromDropDownList("Выпадающий список Проведенные проверки", "Проверка критичных данных")
                .fillInput("Поле ввода Комментарий МРК и отлагательных условий", "Комментарий для МРК")
                .selectValueFromDropDownList("Выпадающий список Причина доработки", "Ошибка МРК при вводе контактных данных клиента (адресов, телефонов)")
                .clickOnElement("Кнопка Доработка").waitBusyCondition();
        actionsClaimSteps.repeatSendSclRequestToStand("9");
        loginPage.switchToOneTab()
                .goTo(personalAccountPage).waitBusyCondition()
                .clickOnElement("Кнопка Настройка списка(таблица В работе)")
                .goTo(filterListSettingsPage)
                .dragColumns("из правой колонки в левую",
                        List.of("Отправивший на доработку/корректировку",
                                "Была доработка",
                                "Дата возврата заявки",
                                "Отправка на доработку",
                                "Изменивший"))
                .clickOnElement("Кнопка Закрыть окно фильтров");

        assertIsTrue(personalAccountPage.getTextFromTable("Таблица в работе", 1, "Отправивший на доработку/корректировку").
                        equals("Автоматическое Тестирование1"),
                "Значение в столбце Отправивший на доработку/корректировку должно быть равно Автоматическое Тестирование1");
        assertIsTrue(personalAccountPage.getTextFromTable("Таблица в работе", 1, "Была доработка").
                        equals("Да"),
                "Значение в столбце Была доработка должно быть равно Да");
        assertIsTrue(!personalAccountPage.getTextFromTable("Таблица в работе", 1, "Отправка на доработку").trim().isEmpty(),
                "Значение в столбце Отправка на доработку должно быть заполнено");
        assertIsTrue(!personalAccountPage.getTextFromTable("Таблица в работе", 1, "Дата возврата заявки").trim().isEmpty(),
                "Значение в столбце Дата возврата заявки должно быть заполнено");
        assertIsTrue(personalAccountPage.getTextFromTable("Таблица в работе", 1, "Изменивший").
                        equals("Автоматическое Тестирование1"),
                "Значение в столбце Изменивший на доработку/корректировку должно быть равно Автоматическое Тестирование1");
        personalAccountPage.clickOnElement("Кнопка Настройка списка(таблица В работе)")
                .goTo(filterListSettingsPage)
                .resetFilters();
    }

    @Step
    @Title("Выполнение процесса перевода заявок {claims} в отложенные в количестве {numClaims} шт.")
    private List<String> deferredClaimProcess(List<String> claims, int numClaims) {
        List<String> pendingClaims = new ArrayList<>();
        for (int i = 0; i < claims.size() && i < numClaims; i++) {
            personalAccountPage.doubleClickByText(claims.get(i))
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