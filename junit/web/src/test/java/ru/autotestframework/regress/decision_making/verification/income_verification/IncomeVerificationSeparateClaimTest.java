package ru.autotestframework.regress.decision_making.verification.income_verification;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.ExternalId;
import ru.psb.testit.annotations.WorkItemIds;

import java.util.*;

import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;

@Tag("regress")
@Tag("verification")
@Tag("strategies_verification")
@Tag("income_verification_separate_claim")
@ClassName("Работа с заявкой. Принятие решения. Верификация. Проверка дохода. На каждый кейс отдельная заявка")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IncomeVerificationSeparateClaimTest extends BaseTest {

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

    @Test
    @Tag("smoke")
    @Tag("checking_code_C31_C193_3209801")
    @DisplayName("3209801 - Проверка дохода. Код результата проверки = C31 -> С193. Сегмент \"OTHER_CLIENTS\". KAIP > BKIP. Специалист. 0")
    @WorkItemIds({"3209801"})
    public void checking_code_C31_C193_3209801(TestInfo testInfo) {

        String[][] expectedValues = {
                {"Наименование стратегии", "Статус", "Результат", "Ссылка"},
                {"Проверка дохода", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"Прозвон клиента", "В работе", "", "Открыть стратегию"},
                {"Прозвон работодателя - любой телефон", "В работе", "", "Открыть стратегию"}};

        Map<String, String> claimParams = Map.of(
                "kpClient", "Client_Salary_Early_New",
                "kpMain", "Client_Salary_Early_New",
                "kp_name", "Pos_No_Head",
                "amount", "0.0",
                "avgIncomeReg", "20000.0");

        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2512637.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка дохода/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Оценка дохода проведена")
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Оценка дохода проведена")
                .assertElementByTitleVisibility("Таблица Оценка дохода проведена", "отображается")
                .assertElementByTitleVisibility("Кнопка Рассчитать", "отображается")

                .fillInput("Поле ввода Средний доход по рынку для занимаемой должности", "55000")
                .clickOnElement("Кнопка Рассчитать").waitBusyCondition()
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Доход завышен. Руководитель/Специалист")
                .assertElementByTitleActivity("Кнопка Рассчитать", "не активен")
                .assertElementByTitleActivity("Поле ввода Средний доход по рынку для занимаемой должности", "не активен")

                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку").switchToOneTab().waitBusyCondition()

                .goTo(loginPage)
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim)
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()

                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон клиента/Версия 1")

                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Основные данные").switchToNewTab()
                .clickOnElement("Вкладка Результаты проверок")
                .goTo(resultCheck)
                .clickOnElement("Вкладка Ручные проверки")
                .goTo(manualChecks)
                .clickOnElement("Кнопка Верификация назначенные проверки");

        String[][] actualValues = manualChecks.getTableHeadersAndContentAsArray("Таблица Верификация назначенные проверки");
        assertIsTrue(Arrays.deepEquals(expectedValues, actualValues), "Актуальные \n" + Arrays.deepToString(actualValues) + "\n и ожидаемые значения \n" + Arrays.deepToString(expectedValues) + "\n в таблице \"Таблица Верификация назначенные проверки\" должны совпадать");
        manualChecks.closeCurrentTab().closeCurrentTab()
                .goTo(loginPage).resetFilters().openMenuLinks("Личный кабинет");
    }


    @Test
    @Tag("smoke")
    @Tag("checking_code_C31_C193_3209817")
    @DisplayName("3209817 - Проверка дохода. Код результата проверки = C30 -> С192. Сегмент \"OTHER_CLIENTS\". KAI < BKI. Руководитель")
    @WorkItemIds({"3209817"})
    public void checking_code_C30_С192_3209817(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "kpClient", "Client_Salary_Early_New",
                "kpMain", "Client_Salary_Early_New",
                "kp_name", "Pos_Other_Head",
                "amount", "65000.0",
                "avgIncomeReg", "53000.0");

        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2512629.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка дохода/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Должность клиента не позволяет оценить его доход")
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Должность клиента не позволяет оценить его доход")
                .assertElementByTitleVisibility("Кнопка Рассчитать", "отображается")

                .clickOnElement("Кнопка Рассчитать").waitBusyCondition()
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Доход не завышен")
                .assertElementByTitleActivity("Кнопка Рассчитать", "не активен")

                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку").switchToOneTab().waitBusyCondition()

                .goTo(loginPage)
                .openMenuLinks("Поиск")
                .goTo(searchPage)
                .searchClaimOnPage(claim);
        String statusClaim = searchPage.getTextFromTable("Таблица результаты поиска", 1, "Статус заявки");
        assertIsTrue(statusClaim.equals("Кредит разрешен"),
                "Значение в столбце Статус заявки должно быть равно Кредит разрешен. Фактическое значение: " + statusClaim);
        loginPage.resetFilters().openMenuLinks("Личный кабинет");
    }

    @ParameterizedTest
    @CsvSource({
            "3209807, Проверка дохода. Код результата проверки = C30 -> С192. Сегмент \"PARTIAL_CREDITS_SALARY_CLIENTS_MILITARY\". KAI = BKI. Специалист, Client_Salary, Comp_Type_OPK_Macro_WAR, Pos_No_Head, 32500.0, 25000.0",
            "3209814, Проверка дохода. Код результата проверки = C30 -> С192. Сегмент \"OPK_OTHER\". KAI < BKI. Специалист, Comp_Type_OPK_Other,Comp_Type_OPK_Other, Pos_No_Head, 40000.0, 31000.0",
            "3209808, Проверка дохода. Код результата проверки = C30 -> С192. Сегмент \"LARGE_ORGANIZATIONS_EMPLOYEES\". KAI < BKI. Специалист, Comp_Type_Big_Macro, Comp_Type_Big_Macro, Pos_No_Head, 85000.0, 100000.0",
            "3209800, Проверка дохода. Код результата проверки = C30 -> С192. Сегмент \"PARTIAL_CREDITS_SALARY_CLIENTS\". KAI < BKI. Руководитель, Client_Salary, Client_Salary, Pos_Other_Head, 410000.0, 360000.0"
    })
    @Tag("checking_code_C30_С192")
    @DisplayName("{id} - {displayName}")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void checking_code_C30_С192(String id, String displayName, String kpClient, String kpMain, String kp_name, String amount, String avgIncomeReg, TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "kpClient", kpClient,
                "kpMain", kpMain,
                "kp_name", kp_name,
                "amount", amount,
                "avgIncomeReg", avgIncomeReg);

        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2512629.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка дохода/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Должность клиента не позволяет оценить его доход")
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Должность клиента не позволяет оценить его доход")
                .assertElementByTitleVisibility("Кнопка Рассчитать", "отображается")

                .clickOnElement("Кнопка Рассчитать").waitBusyCondition()
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Доход не завышен")
                .assertElementByTitleActivity("Кнопка Рассчитать", "не активен")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку").switchToOneTab().waitBusyCondition()

                .goTo(loginPage)
                .openMenuLinks("Поиск")
                .goTo(searchPage)
                .searchClaimOnPage(claim);
        String statusClaim = searchPage.getTextFromTable("Таблица результаты поиска", 1, "Статус заявки");
        assertIsTrue(statusClaim.equals("Кредит разрешен"),
                "Значение в столбце Статус заявки должно быть равно Кредит разрешен. Фактическое значение: " + statusClaim);
        loginPage.resetFilters().openMenuLinks("Личный кабинет");
    }

    @ParameterizedTest
    @CsvSource({
            "3209816, Проверка дохода. Код результата проверки = C31 -> С192. Сегмент \"PARTIAL_CREDITS_SALARY_CLIENTS_MILITARY\". KAIP = BKIP. Специалист, Client_Salary, Comp_Type_OPK_Macro_WAR, Pos_No_Head, 32500.0, 35000.0, 25000",
            "3209818, Проверка дохода. Код результата проверки = C31 -> С192. Сегмент \"PUBLIC_OFFICER\". KAIP < BKIP. Специалист, Comp_Type_Public_Servant_Macro, Comp_Type_Public_Servant_Macro, Pos_No_Head, 45000.0, 25000.0, 35000",
            "3209799, Проверка дохода. Код результата проверки = C31 -> С192. Сегмент \"CORPORATE_CLIENTS_EMPLOYEES\". KAIP < BKIP. Руководитель, Comp_Rel_Corp, Comp_Rel_Corp, Pos_Other_Head, 25000.0, 25000.0, 25000"
    })
    @Tag("checking_code_C31_С192")
    @DisplayName("{id} - {displayName}")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void checking_code_C31_С192(String id, String displayName, String kpClient, String kpMain, String kp_name, String amount, String avgIncomeReg, String averageIncome, TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "kpClient", kpClient,
                "kpMain", kpMain,
                "kp_name", kp_name,
                "amount", amount,
                "avgIncomeReg", avgIncomeReg);

        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2512629.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка дохода/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Оценка дохода проведена")
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Оценка дохода проведена")
                .assertElementByTitleVisibility("Таблица Оценка дохода проведена", "отображается")
                .fillInput("Поле ввода Средний доход по рынку для занимаемой должности", averageIncome)
                .assertElementByTitleVisibility("Кнопка Рассчитать", "отображается")

                .clickOnElement("Кнопка Рассчитать").waitBusyCondition()
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Доход не завышен")
                .assertElementByTitleActivity("Кнопка Рассчитать", "не активен")
                .assertElementByTitleActivity("Поле ввода Средний доход по рынку для занимаемой должности", "не активен")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку").switchToOneTab().waitBusyCondition()

                .goTo(loginPage)
                .openMenuLinks("Поиск")
                .goTo(searchPage)
                .searchClaimOnPage(claim);
        String statusClaim = searchPage.getTextFromTable("Таблица результаты поиска", 1, "Статус заявки");
        assertIsTrue(statusClaim.equals("Кредит разрешен"),
                "Значение в столбце Статус заявки должно быть равно Кредит разрешен. Фактическое значение: " + statusClaim);
        loginPage.resetFilters().openMenuLinks("Личный кабинет");
    }

    @ParameterizedTest
    @CsvSource({
            "3209806, Проверка дохода. Код результата проверки = C31 -> С192. Сегмент \"PUBLIC_OFFICER\". KAIP < BKIP. Учредитель. Да, Comp_Type_Public_Servant_Macro, 30000.0, 5000.0, 3000000, Да, 50",
            "3209798, Проверка дохода. Код результата проверки = C31 -> С192. Сегмент \"CORPORATE_CLIENTS_EMPLOYEES\". KAIP < BKIP. Учредитель. Нет, Comp_Rel_Corp, 10000.0, 15000.0, 1000000, Нет, 50",
            "3209819, Проверка дохода. Код результата проверки = C31 -> С192. Сегмент \"OTHER_CLIENTS\". KAIP < BKIP. Учредитель. Нет. 0, Client_Salary_Early_New, 30000.0, 15000.0, 2000000, Нет, 20"
    })
    @Tag("checking_code_C31_С192")
    @DisplayName("{id} - {displayName}")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void checking_code_C31_С192_founder(String id, String displayName, String kpClient, String avgIncomeReg, String maxPaymentForLast6Month, String revenueOfficialPreviousYear, String selector, String businessShare, TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "kpClient", kpClient,
                "avgIncomeReg", avgIncomeReg,
                "maxPaymentForLast6Month", maxPaymentForLast6Month);

        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2512634.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка дохода/Версия 1")
                .fillInput("Поле ввода Выручка по официальным данным за предыдущий год (руб.)", revenueOfficialPreviousYear)
                .fillInput("Поле ввода Доля в бизнесе (%)", businessShare)
                .clickOnElement("Переключатель " + selector + " (Деятельность компании подразумевает большую закупочную часть или траты вне персонала)")
                .clickOnElement("Кнопка Рассчитать").waitBusyCondition()

                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Доход не завышен")
                .assertElementByTitleActivity("Кнопка Рассчитать", "не активен")
                .assertElementByTitleActivity("Поле ввода Выручка по официальным данным за предыдущий год (руб.)", "не активен")
                .assertElementByTitleActivity("Поле ввода Доля в бизнесе (%)", "не активен")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку").switchToOneTab().waitBusyCondition()

                .goTo(loginPage)
                .openMenuLinks("Поиск")
                .goTo(searchPage)
                .searchClaimOnPage(claim);
        String statusClaim = searchPage.getTextFromTable("Таблица результаты поиска", 1, "Статус заявки");
        assertIsTrue(statusClaim.equals("Кредит разрешен"),
                "Значение в столбце Статус заявки должно быть равно Кредит разрешен. Фактическое значение: " + statusClaim);
        loginPage.resetFilters().openMenuLinks("Личный кабинет");
    }

    @ParameterizedTest
    @CsvSource({
            "3209815, Проверка дохода. Код результата проверки = C30 -> С193. Сегмент \"PUBLIC_OFFICER\". KAI > BKI. Специалист, Comp_Type_Public_Servant_Macro, Comp_Type_Public_Servant_Macro, Pos_No_Head, 35000.0, 10000.0",
            "3209811, Проверка дохода. Проверка дохода. Код результата проверки = C30 -> С193. Сегмент \"CORPORATE_CLIENTS_EMPLOYEES\". KAI > BKI. Руководитель. null, Comp_Rel_Corp, Comp_Rel_Corp, Pos_Other_Head, 35000.0, null",
            "3209802, Проверка дохода. Код результата проверки = C31 -> С193. Сегмент \"PARTIAL_CREDITS_SALARY_CLIENTS\". KAIP > BKIP. Специалист, Client_Salary, Client_Salary, Pos_No_Head, 50000.0, 20000.0",
            "3209810, Проверка дохода. Код результата проверки = C31 -> С193. Сегмент \"PARTIAL_CREDITS_SALARY_CLIENTS_MILITARY\". KAIP > BKIP. Руководитель, Client_Salary, Comp_Type_OPK_Macro_WAR, Pos_Other_Head, 60000.0, 20000.0"
    })
    @Tag("checking_code_C30_С193_specialist")
    @DisplayName("{id} - {displayName}")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void checking_code_C30_С193_specialist(String id, String displayName, String kpClient, String kpMain, String kp_name, String amount, String avgIncomeReg, TestInfo testInfo) {
        String[][] expectedValues = {
                {"Наименование стратегии", "Статус", "Результат", "Ссылка"},
                {"Проверка дохода", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"Прозвон клиента", "В работе", "", "Открыть стратегию"},
                {"Прозвон работодателя - любой телефон", "В работе", "", "Открыть стратегию"}};

        Map<String, String> claimParams = Map.of(
                "kpClient", kpClient,
                "kpMain", kpMain,
                "kp_name", kp_name,
                "amount", amount,
                "avgIncomeReg", avgIncomeReg);

        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2512637.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка дохода/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Должность клиента не позволяет оценить его доход")
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Должность клиента не позволяет оценить его доход")
                .assertElementByTitleVisibility("Кнопка Рассчитать", "отображается")

                .clickOnElement("Кнопка Рассчитать").waitBusyCondition()
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Доход завышен. Руководитель/Специалист")
                .assertElementByTitleActivity("Кнопка Рассчитать", "не активен")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку").switchToOneTab().waitBusyCondition()

                .goTo(loginPage)
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim)
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон клиента/Версия 1")
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Основные данные").switchToNewTab()
                .clickOnElement("Вкладка Результаты проверок")
                .goTo(resultCheck)
                .clickOnElement("Вкладка Ручные проверки")
                .goTo(manualChecks)
                .clickOnElement("Кнопка Верификация назначенные проверки");
        String[][] actualValues = manualChecks.getTableHeadersAndContentAsArray("Таблица Верификация назначенные проверки");
        assertIsTrue(Arrays.deepEquals(expectedValues, actualValues), "Актуальные \n" + Arrays.deepToString(actualValues) + "\n и ожидаемые значения \n" + Arrays.deepToString(expectedValues) + "\n в таблице \"Таблица Верификация назначенные проверки\" должны совпадать");
        manualChecks.closeCurrentTab().closeCurrentTab()
                .goTo(loginPage).resetFilters().openMenuLinks("Личный кабинет");
    }

    @ParameterizedTest
    @CsvSource({
            "3209812, Проверка дохода. Код результата проверки = C31 -> С194. Сегмент \"PARTIAL_CREDITS_SALARY_CLIENTS\". KAIP > BKIP. Учредитель. Да, Client_Salary, Client_Salary, 25000.0, 10000.0, Да, 1000000, 60, 12000",
            "3209803, Проверка дохода. Код результата проверки = C31 -> С194. Сегмент \"OTHER_CLIENTS\". KAIP > BKIP. Учредитель. Нет, Client_Salary_Early_New, Client_Salary_Early_New, 20000.0, 12500.0, Нет, 1000000, 35, 21000",
            "3209804, Проверка дохода. Код результата проверки = C31 -> С194. Сегмент \"CORPORATE_CLIENTS_EMPLOYEES\". KAIP > BKIP. Учредитель. Нет. 0, Comp_Rel_Corp, Comp_Rel_Corp, 0.0, null, Нет, 1, 1, 0",
            "3209813, Проверка дохода. Код результата проверки = C31 -> С194. Сегмент \"PARTIAL_CREDITS_SALARY_CLIENTS_MILITARY\". KAIP > BKIP. Учредитель. Да. =, Client_Salary, Comp_Type_OPK_Macro_WAR, null, 0.0, Да, 1347000, 100, 26940"
    })
    @Tag("checking_code_C31_С194")
    @DisplayName("{id} - {displayName}")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void checking_code_C31_С194(String id, String displayName, String kpClient, String kpMain, String avgIncomeReg, String maxPaymentForLast6Month, String selector, String revenueOfficialPreviousYear, String businessShare, String expectedIncome, TestInfo testInfo) {
        String[][] expectedValues = {
                {"Наименование стратегии", "Статус", "Результат", "Ссылка"},
                {"Проверка дохода", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"Прозвон клиента", "В работе", "", "Открыть стратегию"}};

        Map<String, String> claimParams = Map.of(
                "kpClient", kpClient,
                "kpMain", kpMain,
                "avgIncomeReg", avgIncomeReg,
                "maxPaymentForLast6Month", maxPaymentForLast6Month);

        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2512639.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка дохода/Версия 1")
                .clickOnElement("Переключатель " + selector + " (Деятельность компании подразумевает большую закупочную часть или траты вне персонала)")
                .fillInput("Поле ввода Выручка по официальным данным за предыдущий год (руб.)", revenueOfficialPreviousYear)
                .fillInput("Поле ввода Доля в бизнесе (%)", businessShare)
                .clickOnElement("Кнопка Рассчитать").waitBusyCondition()
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Доход завышен. Учредитель")
                .assertElementByTitleActivity("Кнопка Рассчитать", "не активен")
                .assertElementByTitleActivity("Поле ввода Выручка по официальным данным за предыдущий год (руб.)", "не активен")
                .assertElementByTitleActivity("Поле ввода Доля в бизнесе (%)", "не активен");
        String actualValue = incomeVerificationPage.getValueByElementTitle("Поле ввода Рассчитанный доход от ведения бизнеса (руб.)");
        assertIsTrue(expectedIncome.equals(actualValue),
                "Значенние Поле ввода Рассчитанный доход от ведения бизнеса (руб.) " + actualValue + " должно быть равно " + expectedIncome + ". Фактическое значение = " + actualValue);

        incomeVerificationPage
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку").switchToOneTab().waitBusyCondition()
                .goTo(loginPage)
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim)
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон клиента/Версия 1")
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Основные данные").switchToNewTab()
                .clickOnElement("Вкладка Результаты проверок")
                .goTo(resultCheck)
                .clickOnElement("Вкладка Ручные проверки")
                .goTo(manualChecks)
                .clickOnElement("Кнопка Верификация назначенные проверки");
        String[][] actualValues = manualChecks.getTableHeadersAndContentAsArray("Таблица Верификация назначенные проверки");
        assertIsTrue(Arrays.deepEquals(expectedValues, actualValues), "Актуальные \n" + Arrays.deepToString(actualValues) + "\n и ожидаемые значения \n" + Arrays.deepToString(expectedValues) + "\n в таблице \"Таблица Верификация назначенные проверки\" должны совпадать");
        manualChecks.closeCurrentTab().closeCurrentTab()
                .goTo(loginPage).resetFilters().openMenuLinks("Личный кабинет");
    }

    @Test
    @Tag("checking_code_C31_С194")
    @DisplayName("3209805 - Проверка дохода. Код результата проверки = C31 -> С194. Сегмент \"OTHER_CLIENTS\". Заем + Созаем + Основ + Сов-во. Учредитель")
    @WorkItemIds({"3209805"})
    public void checking_code_C31_С194(TestInfo testInfo) {
        String[][] expectedValues = {
                {"Наименование стратегии", "Статус", "Результат", "Ссылка"},
                {"Проверка дохода", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"Прозвон клиента", "В работе", "", "Открыть стратегию"}};

        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2518618.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка дохода/Версия 1")
                .assertElementByTitleVisibility("Первый шаг на степере", "отображается")
                .assertElementByTitleVisibility("Второй шаг на степере", "отображается")
                .assertElementByTitleVisibility("Третий шаг на степере", "отображается")
                .assertElementByTitleVisibility("Четвертый шаг на степере", "отображается")

                .clickOnElement("Переключатель Да (Деятельность компании подразумевает большую закупочную часть или траты вне персонала)")
                .fillInput("Поле ввода Выручка по официальным данным за предыдущий год (руб.)", "5000000")
                .fillInput("Поле ввода Доля в бизнесе (%)", "80")
                .clickOnElement("Кнопка Рассчитать").waitBusyCondition()
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Доход не завышен")
                .assertElementByTitleActivity("Кнопка Рассчитать", "не активен")
                .assertElementByTitleActivity("Поле ввода Выручка по официальным данным за предыдущий год (руб.)", "не активен")
                .assertElementByTitleActivity("Поле ввода Доля в бизнесе (%)", "не активен");
        String actualValue = incomeVerificationPage.getValueByElementTitle("Поле ввода Рассчитанный доход от ведения бизнеса (руб.)");
        assertIsTrue("80000".equals(actualValue),
                "Значенние Поле ввода Рассчитанный доход от ведения бизнеса (руб.) " + actualValue + " должно быть равно 80000. Фактическое значение = " + actualValue);
        incomeVerificationPage
                .clickOnElement("Кнопка Далее")
                .assertElementByTitleVisibility("Иконка 'галочка' на первом шаге степера", "отображается")
                .assertElementByTitleActivity("Второй шаг на степере", "активен")

                .clickOnElement("Переключатель Да (Деятельность компании подразумевает большую закупочную часть или траты вне персонала)")
                .fillInput("Поле ввода Выручка по официальным данным за предыдущий год (руб.)", "2000000")
                .fillInput("Поле ввода Доля в бизнесе (%)", "60")
                .clickOnElement("Кнопка Рассчитать").waitBusyCondition()
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Доход завышен. Учредитель")
                .assertElementByTitleActivity("Кнопка Рассчитать", "не активен")
                .assertElementByTitleActivity("Поле ввода Выручка по официальным данным за предыдущий год (руб.)", "не активен")
                .assertElementByTitleActivity("Поле ввода Доля в бизнесе (%)", "не активен");
        String actualValue1 = incomeVerificationPage.getValueByElementTitle("Поле ввода Рассчитанный доход от ведения бизнеса (руб.)");
        assertIsTrue("24000".equals(actualValue1),
                "Значенние Поле ввода Рассчитанный доход от ведения бизнеса (руб.) " + actualValue1 + " должно быть равно 24000. Фактическое значение = " + actualValue);
        incomeVerificationPage
                .clickOnElement("Кнопка Далее")
                .assertElementByTitleVisibility("Иконка 'галочка' на втором шаге степера", "отображается")
                .assertElementByTitleActivity("Третий шаг на степере", "активен")

                .clickOnElement("Переключатель Нет (Деятельность компании подразумевает большую закупочную часть или траты вне персонала)")
                .fillInput("Поле ввода Выручка по официальным данным за предыдущий год (руб.)", "100000")
                .fillInput("Поле ввода Доля в бизнесе (%)", "2")
                .clickOnElement("Кнопка Рассчитать").waitBusyCondition()
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Доход завышен. Учредитель")
                .assertElementByTitleActivity("Кнопка Рассчитать", "не активен")
                .assertElementByTitleActivity("Поле ввода Выручка по официальным данным за предыдущий год (руб.)", "не активен")
                .assertElementByTitleActivity("Поле ввода Доля в бизнесе (%)", "не активен");
        String actualValue2 = incomeVerificationPage.getValueByElementTitle("Поле ввода Рассчитанный доход от ведения бизнеса (руб.)");
        assertIsTrue("120".equals(actualValue2),
                "Значенние Поле ввода Рассчитанный доход от ведения бизнеса (руб.) " + actualValue2 + " должно быть равно 120. Фактическое значение = " + actualValue);
        incomeVerificationPage
                .clickOnElement("Кнопка Далее")
                .assertElementByTitleVisibility("Иконка 'галочка' на третьем шаге степера", "отображается")
                .assertElementByTitleActivity("Четвертый шаг на степере", "активен")

                .clickOnElement("Переключатель Да (Деятельность компании подразумевает большую закупочную часть или траты вне персонала)")
                .fillInput("Поле ввода Выручка по официальным данным за предыдущий год (руб.)", "100000000")
                .fillInput("Поле ввода Доля в бизнесе (%)", "99")
                .clickOnElement("Кнопка Рассчитать").waitBusyCondition()
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Доход не завышен")
                .assertElementByTitleActivity("Кнопка Рассчитать", "не активен")
                .assertElementByTitleActivity("Поле ввода Выручка по официальным данным за предыдущий год (руб.)", "не активен")
                .assertElementByTitleActivity("Поле ввода Доля в бизнесе (%)", "не активен");
        String actualValue3 = incomeVerificationPage.getValueByElementTitle("Поле ввода Рассчитанный доход от ведения бизнеса (руб.)");
        assertIsTrue("1980000".equals(actualValue3),
                "Значенние Поле ввода Рассчитанный доход от ведения бизнеса (руб.) " + actualValue3 + " должно быть равно 1980000. Фактическое значение = " + actualValue);
        incomeVerificationPage
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку").switchToOneTab().waitBusyCondition()

                .goTo(loginPage)
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim)
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон клиента/Версия 1")
                .assertElementByTitleVisibility("Первый шаг на степере", "отображается")
                .assertElementByTitleVisibility("Второй шаг на степере", "отображается")
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Основные данные").switchToNewTab()
                .clickOnElement("Вкладка Результаты проверок")
                .goTo(resultCheck)
                .clickOnElement("Вкладка Ручные проверки")
                .goTo(manualChecks)
                .clickOnElement("Кнопка Верификация назначенные проверки");
        String[][] actualValues = manualChecks.getTableHeadersAndContentAsArray("Таблица Верификация назначенные проверки");
        assertIsTrue(Arrays.deepEquals(expectedValues, actualValues), "Актуальные \n" + Arrays.deepToString(actualValues) + "\n и ожидаемые значения \n" + Arrays.deepToString(expectedValues) + "\n в таблице \"Таблица Верификация назначенные проверки\" должны совпадать");
        manualChecks.closeCurrentTab().closeCurrentTab()
                .goTo(loginPage).resetFilters().openMenuLinks("Личный кабинет");
    }


    @Test
    @Tag("checking_code_C31_С193")
    @DisplayName("3209809 - Проверка дохода. Код результата проверки = C31 -> С193. Сегмент \"OTHER_CLIENTS\". Заем + Созаем + Основ + Сов-во. Специалист")
    @WorkItemIds({"3209809"})
    public void checking_code_C31_С193(TestInfo testInfo) {
        String[][] expectedValues = {
                {"Наименование стратегии", "Статус", "Результат", "Ссылка"},
                {"Проверка дохода", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"Прозвон клиента", "В работе", "", "Открыть стратегию"},
                {"Прозвон работодателя - любой телефон", "В работе", "", "Открыть стратегию"}};

        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2518630.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка дохода/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Оценка дохода проведена")
                .assertElementByTitleVisibility("Таблица Оценка дохода проведена", "отображается")
                .assertElementByTitleVisibility("Кнопка Рассчитать", "отображается")
                .fillInput("Поле ввода Средний доход по рынку для занимаемой должности", "10000")
                .clickOnElement("Кнопка Рассчитать").waitBusyCondition()
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Доход завышен. Руководитель/Специалист")
                .assertElementByTitleActivity("Кнопка Рассчитать", "не активен")
                .assertElementByTitleActivity("Поле ввода Средний доход по рынку для занимаемой должности", "не активен")
                .clickOnElement("Кнопка Далее")
                .assertElementByTitleVisibility("Иконка 'галочка' на первом шаге степера", "отображается")
                .assertElementByTitleActivity("Второй шаг на степере", "активен")

                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Оценка дохода проведена")
                .assertElementByTitleVisibility("Таблица Оценка дохода проведена", "отображается")
                .assertElementByTitleVisibility("Кнопка Рассчитать", "отображается")
                .fillInput("Поле ввода Средний доход по рынку для занимаемой должности", "22000")
                .clickOnElement("Кнопка Рассчитать").waitBusyCondition()
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Доход завышен. Руководитель/Специалист")
                .assertElementByTitleActivity("Кнопка Рассчитать", "не активен")
                .assertElementByTitleActivity("Поле ввода Средний доход по рынку для занимаемой должности", "не активен")
                .clickOnElement("Кнопка Далее")
                .assertElementByTitleVisibility("Иконка 'галочка' на втором шаге степера", "отображается")
                .assertElementByTitleActivity("Третий шаг на степере", "активен")

                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Оценка дохода проведена")
                .assertElementByTitleVisibility("Таблица Оценка дохода проведена", "отображается")
                .assertElementByTitleVisibility("Кнопка Рассчитать", "отображается")
                .fillInput("Поле ввода Средний доход по рынку для занимаемой должности", "28000")
                .clickOnElement("Кнопка Рассчитать").waitBusyCondition()
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Доход завышен. Руководитель/Специалист")
                .assertElementByTitleActivity("Кнопка Рассчитать", "не активен")
                .assertElementByTitleActivity("Поле ввода Средний доход по рынку для занимаемой должности", "не активен")
                .clickOnElement("Кнопка Далее")
                .assertElementByTitleVisibility("Иконка 'галочка' на третьем шаге степера", "отображается")
                .assertElementByTitleActivity("Четвертый шаг на степере", "активен")

                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Оценка дохода проведена")
                .assertElementByTitleVisibility("Таблица Оценка дохода проведена", "отображается")
                .assertElementByTitleVisibility("Кнопка Рассчитать", "отображается")
                .fillInput("Поле ввода Средний доход по рынку для занимаемой должности", "23000")
                .clickOnElement("Кнопка Рассчитать").waitBusyCondition()
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Доход завышен. Руководитель/Специалист")
                .assertElementByTitleActivity("Кнопка Рассчитать", "не активен")
                .assertElementByTitleActivity("Поле ввода Средний доход по рынку для занимаемой должности", "не активен")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку").switchToOneTab().waitBusyCondition()

                .goTo(loginPage)
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim)
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон клиента/Версия 1")
                .assertElementByTitleVisibility("Первый шаг на степере", "отображается")
                .assertElementByTitleVisibility("Второй шаг на степере", "отображается")
                .assertElementByTitleVisibility("Третий шаг на степере", "отображается")
                .assertElementByTitleVisibility("Четвертый шаг на степере", "отображается")
                .assertElementByTitleVisibility("Пятый шаг на степере", "отображается")
                .assertElementByTitleVisibility("Шестой шаг на степере", "отображается")

                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Основные данные").switchToNewTab()
                .clickOnElement("Вкладка Результаты проверок")
                .goTo(resultCheck)
                .clickOnElement("Вкладка Ручные проверки")
                .goTo(manualChecks)
                .clickOnElement("Кнопка Верификация назначенные проверки");
        String[][] actualValues = manualChecks.getTableHeadersAndContentAsArray("Таблица Верификация назначенные проверки");
        assertIsTrue(Arrays.deepEquals(expectedValues, actualValues), "Актуальные \n" + Arrays.deepToString(actualValues) + "\n и ожидаемые значения \n" + Arrays.deepToString(expectedValues) + "\n в таблице \"Таблица Верификация назначенные проверки\" должны совпадать");
        manualChecks.closeCurrentTab().closeCurrentTab()
                .goTo(loginPage).resetFilters().openMenuLinks("Личный кабинет");
    }
}