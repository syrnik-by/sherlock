package ru.autotestframework.regress.decision_making.verification.calling_employer_any_phone_required;

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
@Tag("CallingEmployerConfirmedPhoneSeparate")
@ClassName("Прозвон работодателя - подтвержденный телефон. На каждый кейс отдельная заявка.")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CallingEmployerAnyPhoneRequiredSeparateTest extends BaseTest {

    private String claim;

    @BeforeEach
    public void login(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3248557.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi()
                    .openMenuLinks("Личный кабинет")
                    .goTo(personalAccountPage);
        }
        loginPage.doubleClickByText(claim).switchToNewTab();
    }

    @AfterEach
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("smoke")
    @Tag("processing_verification_by_code_C108_1724961")
    @DisplayName("1724961 - Прозвон работодателя - любой телефон\" (Обязательный). Обработка проверки по 'Коду результата проверки' = C108. NEXT")
    @WorkItemIds({"1724961"})
    public void processing_verification_by_code_C108_1724961() {

        callingEmployerAnyPhoneRequiredOneClaimPage
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон работодателя - любой телефон (обязательный)/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Предоставлен документ, закрывающий риски")
                .selectValueFromDropDownList("Выпадающий список Предоставлен документ, закрывающий риски", "Выписка из ПФР")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку").switchToOneTab().waitBusyCondition();

        actionsClaimSteps.executeQuery(VERIFICATION, "select request_final_result_code from vrf_check_step vcs " +
                "join vrf_check_set vcs2 on vcs2.id = vcs.check_set_id " +
                "where vcs2.claim_id ='" + claim + "' and check_type_code = 'EMPLOYER_MANDATORY_CALL'");
        assertIsTrue(actionsClaimSteps.getVariables("request_final_result_code").equals("NEXT"), "Значение request_final_result_code == NEXT");
    }

    @Test
    @Tag("processing_verification_by_code_C84_1724957")
    @DisplayName("1724957 - Прозвон работодателя - любой телефон\" (Обязательный). Обработка проверки по 'Коду результата проверки' = C84. NEXT")
    @WorkItemIds({"1724957"})
    public void processing_verification_by_code_C84_1724957() {

        callingEmployerAnyPhoneRequiredOneClaimPage
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон работодателя - любой телефон (обязательный)/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Негатив не выявлен, все ответы получены")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку").switchToOneTab().waitBusyCondition();

        actionsClaimSteps.executeQuery(VERIFICATION, "select request_final_result_code from vrf_check_step vcs " +
                "join vrf_check_set vcs2 on vcs2.id = vcs.check_set_id " +
                "where vcs2.claim_id ='" + claim + "' and check_type_code = 'EMPLOYER_MANDATORY_CALL'");
        assertIsTrue(actionsClaimSteps.getVariables("request_final_result_code").equals("NEXT"), "Значение request_final_result_code == NEXT");

    }

    @Test
    @Tag("processing_verification_by_code_C89_1724964")
    @DisplayName("1724964 - Прозвон работодателя - любой телефон\" (Обязательный). Обработка проверки по 'Коду результата проверки' = C89. AUTOREFUSE")
    @WorkItemIds({"1724964"})
    public void processing_verification_by_code_C89_1724964() {

        callingEmployerAnyPhoneRequiredOneClaimPage
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон работодателя - любой телефон (обязательный)/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Выявлен негатив")
                .selectValueFromDropDownList("Выпадающий список Выявлен негатив", "Информация о сокращениях")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку").switchToOneTab().waitBusyCondition();

        actionsClaimSteps.executeQuery(VERIFICATION, "select request_final_result_code from vrf_check_step vcs " +
                "join vrf_check_set vcs2 on vcs2.id = vcs.check_set_id " +
                "where vcs2.claim_id ='" + claim + "' and check_type_code = 'EMPLOYER_MANDATORY_CALL'");
        assertIsTrue(actionsClaimSteps.getVariables("request_final_result_code").equals("AUTOREFUSE"), "Значение request_final_result_code == AUTOREFUSE");
    }
}