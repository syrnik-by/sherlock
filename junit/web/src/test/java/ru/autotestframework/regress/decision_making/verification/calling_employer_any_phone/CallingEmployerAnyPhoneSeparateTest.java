package ru.autotestframework.regress.decision_making.verification.calling_employer_any_phone;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;
import static ru.autotestframework.utils.Constants.VERIFICATION;

@Tag("regress")
@Tag("decision_making")
@Tag("verification")
@Tag("calling_employer_any_phone_separate")
@ClassName("Прозвон работодателя - любой телефон. На каждый кейс отдельная заявка")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CallingEmployerAnyPhoneSeparateTest extends BaseTest {

    private String claim;

    @BeforeEach
    public void login(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3244139.json", 1, testInfo).get(0);
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
    @Tag("processing_verification_by_code_C110_1724949")
    @DisplayName("1724949 - Прозвон работодателя - любой телефон. Обработка проверки по 'Коду результата проверки' = C110. AUTOREFUSE")
    @WorkItemIds({"1724949"})
    public void processing_verification_by_code_C110_1724949() {

        callingEmployerAnyPhonePage
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон работодателя - любой телефон/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Выявлен негатив")
                .selectValueFromDropDownList("Выпадающий список Выявлен негатив", "Негатив на работодателя")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку").switchToOneTab().waitBusyCondition();

        actionsClaimSteps.executeQuery(VERIFICATION, "select request_final_result_code from vrf_check_step vcs " +
                "join vrf_check_set vcs2 on vcs2.id = vcs.check_set_id " +
                "where vcs2.claim_id ='" + claim + "' and check_type_code = 'EMPLOYER_CALL'");
        assertIsTrue(actionsClaimSteps.getVariables("request_final_result_code").equals("AUTOREFUSE"), "Значение request_final_result_code == AUTOREFUSE");
    }

    @Test
    @Tag("processing_verification_by_code_C84")
    @DisplayName("1724920 - Прозвон работодателя - любой телефон. Обработка проверки по 'Коду результата проверки' = C84. NEXT")
    @WorkItemIds({"1724920"})
    public void processing_verification_by_code_C84() {

        callingEmployerAnyPhonePage
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон работодателя - любой телефон/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Негатив не выявлен, все ответы получены")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку").switchToOneTab().waitBusyCondition();

        actionsClaimSteps.executeQuery(VERIFICATION, "select request_final_result_code from vrf_check_step vcs " +
                "join vrf_check_set vcs2 on vcs2.id = vcs.check_set_id " +
                "where vcs2.claim_id ='" + claim + "' and check_type_code = 'EMPLOYER_CALL'");
        assertIsTrue(actionsClaimSteps.getVariables("request_final_result_code").equals("NEXT"), "Значение request_final_result_code == NEXT");
    }

    @Test
    @Tag("processing_verification_by_code_C112")
    @DisplayName("1724922 - Прозвон работодателя - любой телефон. Обработка проверки по 'Коду результата проверки' = C112. NEXT")
    @WorkItemIds({"1724922"})
    public void processing_verification_by_code_C112() {

        callingEmployerAnyPhonePage
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон работодателя - любой телефон/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Предоставлен документ, закрывающий риски")
                .selectValueFromDropDownList("Выпадающий список Предоставлен документ, закрывающий риски", "Выписка с з/п счета")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку").switchToOneTab().waitBusyCondition();

        actionsClaimSteps.executeQuery(VERIFICATION, "select request_final_result_code from vrf_check_step vcs " +
                "join vrf_check_set vcs2 on vcs2.id = vcs.check_set_id " +
                "where vcs2.claim_id ='" + claim + "' and check_type_code = 'EMPLOYER_CALL'");
        assertIsTrue(actionsClaimSteps.getVariables("request_final_result_code").equals("NEXT"), "Значение request_final_result_code == NEXT");
    }

    @Test
    @Tag("processing_verification_by_code_C114")
    @DisplayName("1724935 - Прозвон работодателя - любой телефон. Обработка проверки по 'Коду результата проверки' = C114. NEXT")
    @WorkItemIds({"1724935"})
    public void processing_verification_by_code_C114() {

        callingEmployerAnyPhonePage
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон работодателя - любой телефон/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Бесконтактное подтверждение")
                .selectValueFromDropDownList("Выпадающий список Бесконтактное подтверждение", "Сторонние сайты")
                .fillInput("Поле ввода Источник подтверждения", "TestAt")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку").switchToOneTab().waitBusyCondition();

        actionsClaimSteps.executeQuery(VERIFICATION, "select request_final_result_code from vrf_check_step vcs " +
                "join vrf_check_set vcs2 on vcs2.id = vcs.check_set_id " +
                "where vcs2.claim_id ='" + claim + "' and check_type_code = 'EMPLOYER_CALL'");
        assertIsTrue(actionsClaimSteps.getVariables("request_final_result_code").equals("NEXT"), "Значение request_final_result_code == NEXT");
    }

    @Test
    @Tag("processing_verification_by_code_C116")
    @DisplayName("1724937 - Прозвон работодателя - любой телефон. Обработка проверки по 'Коду результата проверки' = C116. NEXT")
    @WorkItemIds({"1724937"})
    public void processing_verification_by_code_C116() {

        callingEmployerConfirmedPhonePage
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон работодателя - любой телефон/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Косвенное подтверждение занятости")
                .selectValueFromDropDownList("Выпадающий список Косвенное подтверждение занятости", "Пункт 1 РА")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку").switchToOneTab().waitBusyCondition();

        actionsClaimSteps.executeQuery(VERIFICATION, "select request_final_result_code from vrf_check_step vcs " +
                "join vrf_check_set vcs2 on vcs2.id = vcs.check_set_id " +
                "where vcs2.claim_id ='" + claim + "' and check_type_code = 'EMPLOYER_CALL'");
        assertIsTrue(actionsClaimSteps.getVariables("request_final_result_code").equals("NEXT"), "Значение request_final_result_code == NEXT");
    }
}