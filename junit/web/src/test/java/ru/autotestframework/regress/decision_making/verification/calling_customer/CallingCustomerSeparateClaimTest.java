package ru.autotestframework.regress.decision_making.verification.calling_customer;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;
import static ru.autotestframework.utils.Constants.VERIFICATION;

@Tag("regress")
@Tag("verification")
@Tag("strategies_verification")
@Tag("calling_customer_separate_claim")
@ClassName("Прозвон клиента. На каждый кейс отдельная заявка")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CallingCustomerSeparateClaimTest extends BaseTest {

    private String claim;

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
        callingEmployerAnyPhonePage.closeCurrentTab();
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("smoke")
    @Tag("processing_verification_by_code_C60_1724876")
    @DisplayName("1724876 - Обработка проверки по 'Коду результата проверки' = C60. Прозвон клиента. AUTOREFUSE")
    @WorkItemIds({"1724876"})
    public void processing_verification_by_code_C60_1724876(TestInfo testInfo) {

        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3250533.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(callingEmployerAnyPhonePage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон клиента/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Выявлен негатив")
                .selectValueFromDropDownList("Выпадающий список Выявлен негатив", "Кредит для третьего лица")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку").switchToOneTab().waitBusyCondition();

        actionsClaimSteps.executeQuery(VERIFICATION, "select request_final_result_code from vrf_check_step vcs " +
                "join vrf_check_set vcs2 on vcs2.id = vcs.check_set_id " +
                "where vcs2.claim_id ='" + claim + "' and check_type_code = 'CLIENT_CALL'");
        assertIsTrue(actionsClaimSteps.getVariables("request_final_result_code").equals("AUTOREFUSE"), "Значение request_final_result_code == AUTOREFUSE");
    }
}
