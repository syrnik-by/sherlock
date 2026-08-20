package ru.autotestframework.regress.card_request.verification.strategies_verification.income_verification;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import java.util.List;
import java.util.Map;

@Tag("regress")
@Tag("card_request")
@Tag("verification")
@Tag("strategies_verification")
@Tag("income_verification")
@Tag("income_verification_separate_claim")
@ClassName("Карточка заявки. Верификация. ЭФ стратегий верификации. Проверка дохода. На каждый кейс отдельная заявка")
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
    @Tag("checking_commit_result_1649606")
    @DisplayName("1649606 - ЭФ Проверка дохода. Проверка фиксации результата проверки по стратегии \"Проверка дохода\"")
    @WorkItemIds({"1649606"})
    public void checking_commit_result_1649606(TestInfo testInfo) {
        String averageIncome = "Поле ввода Средний доход по рынку для занимаемой должности";
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1649638.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab();

        incomeVerificationPage.checkElementByTitleEquals("Поле Наименование стратегии", "Проверка дохода/Версия 1")
                .clickOnElement("Кнопка Далее")
                .waitText(2, "Для завершения шага необходимо заполнить результат проверки или результат по заявке")
                .clickOnElement("Кнопка ОК")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Оценка дохода проведена")
                .clickOnElement("Кнопка Далее")
                .waitText(2, "Пожалуйста, заполните поле: \"Средний доход по рынку для занимаемой должности")
                .clickOnElement("Кнопка ОК")
                .fillInput(averageIncome, "60000")
                .clickOnElement("Кнопка Рассчитать")
                .clickOnElement("Кнопка Далее")

                .assertElementByTitleVisibility("Иконка Завершен первый этап", "отображается")
                .assertElementByTitleActivity("Иконка Второй этап", "активен")
                .closeCurrentTab();
    }

    @Test
    @Tag("checking_result_fixation_completion_1649607")
    @DisplayName("1649607 - ЭФ Проверка дохода. Проверка фиксации результата и завершения проверки по стратегии \"Проверка дохода\"")
    @WorkItemIds({"1649607"})
    public void checking_result_fixation_completion_1649607(TestInfo testInfo) {
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1649638.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab();

        incomeVerificationPage
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Должность клиента не позволяет оценить его доход")
                .clickOnElement("Кнопка Рассчитать")
                .clickOnElement("Кнопка Далее")
                .assertElementByTitleVisibility("Иконка Завершен первый этап", "отображается")
                .assertElementByTitleActivity("Иконка Второй этап", "активен")

                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Должность клиента не позволяет оценить его доход")
                .clickOnElement("Кнопка Рассчитать")
                .clickOnElement("Кнопка Далее")

                .assertElementByTitleActivity("Иконка Завершен второй этап", "активен")
                .clickOnElement("Кнопка Завершить проверку").closeCurrentTab();
    }

    @Test
    @Tag("displaying_verification_results_1720107")
    @DisplayName("1720107 - Верификация. Проверка дохода.Отображение результатов проверки сегмент-Частичные зарплатные клиенты")
    @WorkItemIds({"1720107"})
    public void displaying_verification_results_1720107(TestInfo testInfo) {
        String verificationResult = "Выпадающий список Результат проверки";
        List<String> checkList = List.of("Должность клиента не позволяет оценить его доход");
        Map<String, String> claimParams = Map.of(
                "incomeMain", "NO",
                "kpMain", "Client_Salary",
                "kpClient", "null",
                "Code", "stub5");
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1651046.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab();

        incomeVerificationPage
                .checkDropDownListElements(verificationResult, checkList).closeCurrentTab();
    }

    @Test
    @Tag("displaying_verification_results_1720108")
    @DisplayName("1720108 - Верификация. Проверка дохода.Отображение результатов проверки сегмент- Крупные работодатели")
    @WorkItemIds({"1720108"})
    public void displaying_verification_results_1720108(TestInfo testInfo) {
        String verificationResult = "Выпадающий список Результат проверки";
        List<String> checkList = List.of(
                "Должность клиента не позволяет оценить его доход",
                "Оценка дохода проведена");
        Map<String, String> claimParams = Map.of(
                "incomeMain", "NO",
                "kpMain", "Comp_Type_Big_Macro",
                "kpClient", "null",
                "Code", "stub5");
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1651046.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab();

        incomeVerificationPage
                .checkDropDownListElements(verificationResult, checkList).closeCurrentTab();
    }
}