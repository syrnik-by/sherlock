package ru.autotestframework.regress.decision_making.verification.calling_employer_confirmed_phone;

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
public class CallingEmployerConfirmedPhoneSeparateTest extends BaseTest {

    private String claim;

    @BeforeEach
    public void login(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3247438.json", 1, testInfo).get(0);
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
    @Tag("processing_verification_by_code_C119_1724980")
    @DisplayName("1724980 - Прозвон работодателя - подтвержденный телефон. Обработка проверки по 'Коду результата проверки' = C119. NEXT")
    @WorkItemIds({"1724980"})
    public void processing_verification_by_code_C119_1724980() {

        callingEmployerConfirmedPhonePage
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон работодателя - подтвержденный телефон/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Косвенное подтверждение занятости")
                .selectValueFromDropDownList("Выпадающий список Косвенное подтверждение занятости", "Пункт 5 РА")
                .fillInput("Поле ввода Источник подтверждения", "Любое значение")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку").switchToOneTab().waitBusyCondition();

        actionsClaimSteps.executeQuery(VERIFICATION, "select request_final_result_code from vrf_check_step vcs " +
                "join vrf_check_set vcs2 on vcs2.id = vcs.check_set_id " +
                "where vcs2.claim_id ='" + claim + "' and check_type_code = 'EMPLOYER_CALL_CONFIRMED_NUMBER'");
        assertIsTrue(actionsClaimSteps.getVariables("request_final_result_code").equals("NEXT"), "Значение request_final_result_code == NEXT");
    }

    @Test
    @Tag("processing_verification_by_code_C84")
    @DisplayName("1724973 - Прозвон работодателя - подтвержденный телефон. Обработка проверки по 'Коду результата проверки' = C84. NEXT")
    @WorkItemIds({"1724973"})
    public void processing_verification_by_code_C84() {

        callingEmployerConfirmedPhonePage
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон работодателя - подтвержденный телефон/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Негатив не выявлен, все ответы получены")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку").switchToOneTab().waitBusyCondition();

        actionsClaimSteps.executeQuery(VERIFICATION, "select request_final_result_code from vrf_check_step vcs " +
                "join vrf_check_set vcs2 on vcs2.id = vcs.check_set_id " +
                "where vcs2.claim_id ='" + claim + "' and check_type_code = 'EMPLOYER_CALL_CONFIRMED_NUMBER'");
        assertIsTrue(actionsClaimSteps.getVariables("request_final_result_code").equals("NEXT"), "Значение request_final_result_code == NEXT");
    }

    @Test
    @Tag("processing_verification_by_code_C113")
    @DisplayName("1724977 - Прозвон работодателя - подтвержденный телефон. Обработка проверки по 'Коду результата проверки' = C113. NEXT")
    @WorkItemIds({"1724977"})
    public void processing_verification_by_code_C113() {

        callingEmployerConfirmedPhonePage
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон работодателя - подтвержденный телефон/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Предоставлен документ, закрывающий риски")
                .selectValueFromDropDownList("Выпадающий список Предоставлен документ, закрывающий риски", "Удостоверение силовика/военнослужащего/военный билет (для военнослужащего)")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку").switchToOneTab().waitBusyCondition();

        actionsClaimSteps.executeQuery(VERIFICATION, "select request_final_result_code from vrf_check_step vcs " +
                "join vrf_check_set vcs2 on vcs2.id = vcs.check_set_id " +
                "where vcs2.claim_id ='" + claim + "' and check_type_code = 'EMPLOYER_CALL_CONFIRMED_NUMBER'");
        assertIsTrue(actionsClaimSteps.getVariables("request_final_result_code").equals("NEXT"), "Значение request_final_result_code == NEXT");
    }

    @Test
    @Tag("processing_verification_by_code_C115")
    @DisplayName("1724978 - Прозвон работодателя - подтвержденный телефон. Обработка проверки по 'Коду результата проверки' = C115. NEXT")
    @WorkItemIds({"1724978"})
    public void processing_verification_by_code_C115() {

        callingEmployerConfirmedPhonePage
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон работодателя - подтвержденный телефон/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Бесконтактное подтверждение")
                .selectValueFromDropDownList("Выпадающий список Бесконтактное подтверждение", "Официальный сайт")
                .fillInput("Поле ввода Источник подтверждения", "TestAt")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку").switchToOneTab().waitBusyCondition();

        actionsClaimSteps.executeQuery(VERIFICATION, "select request_final_result_code from vrf_check_step vcs " +
                "join vrf_check_set vcs2 on vcs2.id = vcs.check_set_id " +
                "where vcs2.claim_id ='" + claim + "' and check_type_code = 'EMPLOYER_CALL_CONFIRMED_NUMBER'");
        assertIsTrue(actionsClaimSteps.getVariables("request_final_result_code").equals("NEXT"), "Значение request_final_result_code == NEXT");
    }

    @Test
    @Tag("processing_verification_by_code_C94")
    @DisplayName("1724982 - Прозвон работодателя - подтвержденный телефон. Обработка проверки по 'Коду результата проверки' = C94. AUTOREFUSE")
    @WorkItemIds({"1724982"})
    public void processing_verification_by_code_C94() {

        callingEmployerConfirmedPhonePage
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон работодателя - подтвержденный телефон/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Выявлен негатив")
                .selectValueFromDropDownList("Выпадающий список Выявлен негатив", "Несоответствие минимальным требованиям")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку").switchToOneTab().waitBusyCondition();

        actionsClaimSteps.executeQuery(VERIFICATION, "select request_final_result_code from vrf_check_step vcs " +
                "join vrf_check_set vcs2 on vcs2.id = vcs.check_set_id " +
                "where vcs2.claim_id ='" + claim + "' and check_type_code = 'EMPLOYER_CALL_CONFIRMED_NUMBER'");
        assertIsTrue(actionsClaimSteps.getVariables("request_final_result_code").equals("AUTOREFUSE"), "Значение request_final_result_code == AUTOREFUSE");
    }
}