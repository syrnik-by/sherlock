package ru.autotestframework.regress.card_request.verification.strategies_verification.income_verification;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import java.util.List;

import static ru.autotestframework.steps.asserts.Asserts.assertIsEquals;

@Tag("regress")
@Tag("card_request")
@Tag("verification")
@Tag("strategies_verification")
@Tag("income_verification")
@Tag("income_verification_one_claim")
@ClassName("Карточка заявки. Верификация. ЭФ стратегий верификации. Проверка дохода. На одной заявке")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IncomeVerificationOneClaimTest extends BaseTest {

    private String claim;

    @BeforeAll
    public void createClaim(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1649638.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
    }

    @BeforeEach
    public void login() {
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi()
                    .openMenuLinks("Личный кабинет");
        }
        personalAccountPage.clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab();
    }

    @AfterEach
    public void closeTab() {
        incomeVerificationPage.closeCurrentTab();
    }

    @AfterAll
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }


    @Test
    @Tag("smoke")
    @Tag("checking_commit_result_1649608")
    @DisplayName("1649608 - ЭФ Проверка дохода. Проверка отображения наименования стратегии и значений из выпадающего списка \"Результат проверки\" у стратегии \"Проверка дохода\"")
    @WorkItemIds({"1649608"})
    public void checking_commit_result_1649608() {
        String verificationResult = "Выпадающий список Результат проверки";
        List<String> checkList = List.of(
                "Должность клиента не позволяет оценить его доход",
                "Оценка дохода проведена",
                "Работодатель в списке исключений");

        incomeVerificationPage.checkElementByTitleEquals("Поле Наименование стратегии", "Проверка дохода/Версия 1")
                .checkElementByTitleContains("Описание Первый этап", "Основное место работы")
                .checkElementByTitleContains("Описание Второй этап", " Совместительство ")
                .checkDropDownListElements(verificationResult, checkList);
    }

    @Test
    @Tag("checking_commit_result_1649605")
    @DisplayName("1649605 - ЭФ Проверка дохода. Проверка выбора значений в поле \"Результат проверки\" и обязательность заполнения нового поля ввода данных в стратегии \"Проверка дохода\"")
    @WorkItemIds({"1649605"})
    public void checking_commit_result_1649605() {
        String verificationResult = "Выпадающий список Результат проверки";
        String textInputAverageIncome = "Поле ввода Средний доход по рынку для занимаемой должности";

        incomeVerificationPage.selectValueFromDropDownList(verificationResult, "Должность клиента не позволяет оценить его доход")
                .assertElementByTitleVisibility("Таблица Оценка дохода проведена", "не отображается")
                .selectValueFromDropDownList(verificationResult, "Оценка дохода проведена")
                .assertElementByTitleVisibility("Таблица Оценка дохода проведена", "отображается")
                .clickOnElement("Кнопка Далее")
                .waitText(2, "Пожалуйста, заполните поле: \"Средний доход по рынку для занимаемой должности")
                .clickOnElement("Кнопка ОК")
                .fillInput("Поле ввода Средний доход по рынку для занимаемой должности", "-60000");
        assertIsEquals("60000", incomeVerificationPage.getValueByElementTitle(textInputAverageIncome), textInputAverageIncome);

        incomeVerificationPage.selectValueFromDropDownList(verificationResult, "Должность клиента не позволяет оценить его доход")
                .assertElementByTitleVisibility("Таблица Оценка дохода проведена", "не отображается");

    }
}
