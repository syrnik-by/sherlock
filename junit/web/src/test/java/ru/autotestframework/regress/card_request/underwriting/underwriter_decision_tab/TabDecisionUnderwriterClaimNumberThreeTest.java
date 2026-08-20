package ru.autotestframework.regress.card_request.underwriting.underwriter_decision_tab;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import java.util.List;
import java.util.Objects;

import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;
import static ru.autotestframework.util.Validator.assertThat;
import static ru.autotestframework.utils.Constants.REQUESTS;

@Tag("no_check_verification")
@Tag("regress")
@Tag("card_request")
@Tag("underwriting")
@ClassName("Вкладка \"Решение Андеррайтера\". На заявке Тип №3 Решение андеррайтера. Созаемщик")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TabDecisionUnderwriterClaimNumberThreeTest extends BaseTest {

    @BeforeAll
    public void login() {
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi();
        }
    }

    @BeforeEach
    public void goTOPersonalAccountPage() {
        loginPage
                .checkModal()
                .openMenuLinks("Личный кабинет")
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Андеррайтинг").waitBusyCondition();
    }

    @AfterEach
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("smoke")
    @Tag("carried_check_1722084")
    @DisplayName("1722084 - Решение Андеррайтера. Проведенные проверки - 3 значения для Созаемщика. Сохранить и заркыть")
    @WorkItemIds({"1722084"})
    public void carried_check_1722084(TestInfo testInfo) {
        List<String> checkCarriedOut = List.of(
                "Проверка негатив",
                "Проверка антифрод-отчет",
                "Проверка открытые источники – сайт");
        String checkCarriedOutCoBorrower = "Проверка предыдущих заявок";

        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3860383.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(cardRequestPage)
                .assertElementByTitleActivity("Вкладка основные данные", "активен")
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .selectValueFromDropDownList("Выпадающий список Проведенные проверки", checkCarriedOutCoBorrower)
                .selectValueFromDropDownList("Выпадающий список Проведенные проверки (Созаемщик)", checkCarriedOut)
                .clickOnElement("Кнопка Сохранить и закрыть").switchToOneTab();

        assertIsTrue(queuesPage.getTextFromTable("Таблица Очереди", 1, "Статус заявки").
                        equals("На рассмотрении"),
                "Значение в столбце Статус заявки должно быть На рассмотрении");

        personalAccountPage
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(cardRequestPage)
                .assertElementByTitleActivity("Вкладка основные данные", "активен")
                .clickOnElement("Вкладка Решение Андеррайтера");
        String actualValueCoBorrower = underwriterDecisionPage.getTextByElementTitle("Выпадающий список Проведенные проверки (Созаемщик)");
        assertIsTrue(actualValueCoBorrower.equals("Проверка негатив, Проверка антифрод-отчет, Проверка открытые источники – сайт"), "Значение в Выпадающий список Проведенные проверки (Созаемщик)" + actualValueCoBorrower + ", а должно быть равно " + checkCarriedOut);
        String actualValue = underwriterDecisionPage.getTextByElementTitle("Выпадающий список Проведенные проверки");
        assertIsTrue(checkCarriedOutCoBorrower.equals(actualValue), "Значение в Выпадающий список Проведенные проверки (Созаемщик) " + actualValue + ", а должно быть равно " + checkCarriedOutCoBorrower);

        actionsClaimSteps.executeQuery(REQUESTS, "SELECT is_negative_check, " +
                "is_antifraud_check, " +
                "IS_WEBSITE_CHECK, " +
                "is_prev_claim_check, " +
                "rf.applicant_other, " +
                "decision_request_check_id " +
                "FROM requests.rqs_underwriter_check ruc " +
                "JOIN requests.rqs_form rf on rf.id = ruc.form_id " +
                "JOIN requests.rqs_request rr on rr.id = rf.request_id " +
                "WHERE rr.claim_id = '" + claim + "' " +
                "order by rf.applicant_other ASC");

        assertThat(Objects.equals(actionsClaimSteps.getVariablesRowNumber("applicant_other", 1), "Заемщик"), "Значение applicant_other не равно Заемщик");
        assertThat(Objects.equals(actionsClaimSteps.getVariablesRowNumber("IS_NEGATIVE_CHECK", 1), null), "Значение IS_NEGATIVE_CHECK не равно null");
        assertThat(Objects.equals(actionsClaimSteps.getVariablesRowNumber("IS_ANTIFRAUD_CHECK", 1), null), "Значение IS_ANTIFRAUD_CHECK не равно null");
        assertThat(Objects.equals(actionsClaimSteps.getVariablesRowNumber("IS_WEBSITE_CHECK", 1), null), "Значение IS_WEBSITE_CHECK не равно null");
        assertThat(Objects.equals(actionsClaimSteps.getVariablesRowNumber("is_prev_claim_check", 1), "true"), "Значение IS_PREV_CLAIM_CHECK не равно true");
        assertThat(Objects.equals(actionsClaimSteps.getVariablesRowNumber("decision_request_check_id", 1), null), "Значение decision_request_check_id не равно null");

        assertThat(Objects.equals(actionsClaimSteps.getVariablesRowNumber("applicant_other", 2), "Созаемщик"), "Значение applicant_other не равно Созаемщик");
        assertThat(Objects.equals(actionsClaimSteps.getVariablesRowNumber("IS_NEGATIVE_CHECK", 2), "true"), "Значение IS_NEGATIVE_CHECK не равно true");
        assertThat(Objects.equals(actionsClaimSteps.getVariablesRowNumber("IS_ANTIFRAUD_CHECK", 2), "true"), "Значение IS_ANTIFRAUD_CHECK не равно true");
        assertThat(Objects.equals(actionsClaimSteps.getVariablesRowNumber("IS_WEBSITE_CHECK", 2), "true"), "Значение IS_WEBSITE_CHECK не равно true");
        assertThat(Objects.equals(actionsClaimSteps.getVariablesRowNumber("IS_PREV_CLAIM_CHECK", 2), null), "Значение IS_PREV_CLAIM_CHECK не равно null");
        assertThat(Objects.equals(actionsClaimSteps.getVariablesRowNumber("decision_request_check_id", 2), null), "Значение decision_request_check_id не равно null");

//Постусловия
        underwriterDecisionPage
                .selectValueFromDropDownList("Выпадающий список Проведенные проверки", checkCarriedOutCoBorrower)
                .selectValueFromDropDownList("Выпадающий список Проведенные проверки (Созаемщик)", checkCarriedOut)
                .clickOnElement("Кнопка Сохранить и закрыть").switchToOneTab();
    }


    @Test
    @Tag("smoke")
    @Tag("auto_completion_application_type_1722097")
    @DisplayName("1722097 - Решение Андеррайтера. Блок Созаемщик. Автозаполнение Тип заявки")
    @WorkItemIds({"1722097"})
    public void auto_completion_application_type_1722097(TestInfo testInfo) {
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3860383.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(cardRequestPage)
                .assertElementByTitleActivity("Вкладка основные данные", "активен")
                .clickOnElement("Вкладка Решение Андеррайтера");

        String actualValueCoBorrower = underwriterDecisionPage.getTextByElementTitle("Выпадающий список Тип заявки");
        assertIsTrue(actualValueCoBorrower.equals("Заявка ЕКЛ"), "Значение в Выпадающий список Тип заявки" + actualValueCoBorrower + ", а должно быть равно 'Заявка ЕКЛ'");
        String actualValue = underwriterDecisionPage.getTextByElementTitle("Выпадающий список Тип заявки (Созаемщик)");
        assertIsTrue(actualValue.equals("Заявка ЕКЛ"), "Значение в Выпадающий список Тип заявки (Созаемщик) " + actualValue + ", а должно быть равно 'Заявка ЕКЛ'");
    }
}