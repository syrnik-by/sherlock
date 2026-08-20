package ru.autotestframework.regress.decision_making.verification.result_of_claim;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
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
@Tag("result_of_claim")
@ClassName("L0 Проверка документов. На каждый кейс отдельная заявка")
public class ResultOfClaimTest extends BaseTest {

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

    @Test
    @Tag("result_of_claim_1720255")
    @DisplayName("1720255 - Обработка заявки с результатом \"APPROVE_STRATEGY\" и \"APPROVE_CONTROL\"")
    @WorkItemIds({"1720255"})
    public void result_of_claim_1720255(TestInfo testInfo) {
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3243425.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Одобрить стратегию")
                .fillInput("Поле ввода Внутренний комментарий", "Комментарий")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab()
                .waitBusyCondition();
        actionsClaimSteps.executeQuery(VERIFICATION, "SELECT check_type_code, request_final_result_code FROM vrf_check_step vcs " +
                "JOIN vrf_check_set vcs2 ON vcs2.id = vcs.check_set_id " +
                "WHERE vcs2.claim_id ='" + claim + "' AND check_type_code = 'FSSP'");
        assertIsTrue(actionsClaimSteps.getVariables("request_final_result_code").equals("NEXT"), "Значение request_final_result_code == NEXT");

        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(customerCallPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон клиента/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Одобрить (контролируемая сделка)")
                .fillInput("Поле ввода Внутренний комментарий", "Комментарий")
                .fillInput("Поле ввода Комментарий для МРК", "Комментарий мрк")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .waitBusyCondition();
        actionsClaimSteps.executeQuery(VERIFICATION, "SELECT check_type_code, request_final_result_code FROM vrf_check_step vcs " +
                "JOIN vrf_check_set vcs2 ON vcs2.id = vcs.check_set_id " +
                "WHERE vcs2.claim_id ='" + claim + "' AND check_type_code = 'CLIENT_CALL'");
        assertIsTrue(actionsClaimSteps.getVariables("request_final_result_code").equals("APPROVE_CONTROL"), "Значение request_final_result_code == APPROVE_CONTROL");
        actionsClaimSteps.checkStatusClaimFromDb(claim, 7);
    }

    @ParameterizedTest
    @CsvSource({
            "1720254, Одобрить, APPROVE, 7",
            "1720256, Отказать, REFUSE, 8"
    })
    @Tag("smoke")
    @Tag("result_of_claim_1720254_1720256")
    @DisplayName("{id} - Обработка заявки с результатом \"{resultCode}\"")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void result_of_claim_1720254_1720256(String id, String resultOfClaim, String resultCode, int statusId, TestInfo testInfo) {
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3243425.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", resultOfClaim);

        if (testInfo.getDisplayName().contains("APPROVE")) {
            fsspPage.selectValueFromDropDownList("Выпадающий список Тип одобрения", "Одобрено");
        } else {
            fsspPage.selectValueFromDropDownList("Выпадающий список Причина отклонения", "Кредит на бизнес");
        }
        fsspPage
                .fillInput("Поле ввода Внутренний комментарий", "Комментарий")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab()
                .waitBusyCondition();
        actionsClaimSteps.executeQuery(VERIFICATION, "SELECT check_type_code, request_final_result_code FROM vrf_check_step vcs " +
                "JOIN vrf_check_set vcs2 ON vcs2.id = vcs.check_set_id " +
                "WHERE vcs2.claim_id ='" + claim + "' AND check_type_code = 'FSSP'");
        assertIsTrue(actionsClaimSteps.getVariables("request_final_result_code").equals(resultCode), "Значение request_final_result_code == " + resultCode);
        actionsClaimSteps.checkStatusClaimFromDb(claim, statusId);
    }
}
