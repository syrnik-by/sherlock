package ru.autotestframework.regress.decision_making.verification.calling_employer;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.ExternalId;
import ru.psb.testit.annotations.WorkItemIds;

import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;
import static ru.autotestframework.utils.Constants.VERIFICATION;

@Tag("regress")
@Tag("decision_making")
@Tag("verification")
@Tag("calling_employer")
@ClassName("Верификация. Прозвон работодателя - подтевержденный телефон (обязательный). На каждый кейс отдельная заявка")
public class CallingEmployerTest extends BaseTest {

    @BeforeAll
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

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "1724991; C84. NEXT; Результативный прозвон; Негатив не выявлен, все ответы получены; NEXT",
            "1724993; C109. NEXT; Предоставлен документ, закрывающий риски; Электронная ТК; NEXT",
            "1724996; C87. AUTOREFUSE; Результативный прозвон; Выявлен негатив; AUTOREFUSE"
    })
    @Tag("smoke")
    @Tag("calling_employer_1724991_1724993_1724996")
    @DisplayName("{id} - Прозвон работодателя - подтвержденный телефон (Обязательный). Обработка проверки по 'Коду результата проверки' = {resultOfClaim}")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void calling_employer_1724991_1724993_1724996(String id, String resultOfClaim, String resultCheck, String addField, String resultCode, TestInfo testInfo) {
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3248625.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(callingEmployerConfirmedPhoneRequiredPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон работодателя - подтвержденный телефон (обязательный)/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", resultCheck)
                .selectValueFromDropDownList("Выпадающий список Дополнительное поле результата проверки", addField);
        if (testInfo.getDisplayName().contains("1724996")) {
            callingEmployerConfirmedPhoneRequiredPage.selectValueFromDropDownList("Выпадающий список Дополнительное поле результата проверки 2", "Негативная характеристика Клиента от работодателя");
        }
        callingEmployerConfirmedPhoneRequiredPage.clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab()
                .waitBusyCondition();

        actionsClaimSteps.executeQuery(VERIFICATION, "SELECT check_type_code, request_final_result_code FROM vrf_check_step vcs " +
                "JOIN vrf_check_set vcs2 ON vcs2.id = vcs.check_set_id " +
                "WHERE vcs2.claim_id ='" + claim + "' AND check_type_code = 'EMPLOYER_MANDATORY_CALL_CONFIRMED_NUMBER'");
        assertIsTrue(actionsClaimSteps.getVariables("request_final_result_code").equals(resultCode), "Значение request_final_result_code == " + resultCode);
    }
}
