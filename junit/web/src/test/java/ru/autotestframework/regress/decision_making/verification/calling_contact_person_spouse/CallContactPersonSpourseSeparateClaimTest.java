package ru.autotestframework.regress.decision_making.verification.calling_contact_person_spouse;

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
@Tag("call_contact_person_spourse_separate_claim")
@ClassName("Прозвон контактного лица / супруги. На каждый кейс отдельная заявка")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CallContactPersonSpourseSeparateClaimTest extends BaseTest {

    private String claim;

    @BeforeEach
    public void login(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3250591.json", 1, testInfo).get(0);
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
    @Tag("processing_verification_by_code_C110_1724828")
    @DisplayName("1724828 - Обработка проверки по 'Коду результата проверки' = C70. Прозвон контактного лица/Супруги. NEXT")
    @WorkItemIds({"1724828"})
    public void processing_verification_by_code_C70_1724828() {

        callContactPersonSpoursePage
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон контактного лица/супруга (-и)/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Нерезультативный прозвон")
                .selectValueFromDropDownList("Выпадающий список Нерезультативный прозвон", "Контактное лицо/супруг (-а) не отвечает/недоступен")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку").switchToOneTab().waitBusyCondition();

        actionsClaimSteps.executeQuery(VERIFICATION, "select request_final_result_code from vrf_check_step vcs " +
                "join vrf_check_set vcs2 on vcs2.id = vcs.check_set_id " +
                "where vcs2.claim_id ='" + claim + "' and check_type_code = 'CONTACT_PERSON_CALL'");
        assertIsTrue(actionsClaimSteps.getVariables("request_final_result_code").equals("NEXT"), "Значение request_final_result_code == NEXT");
    }

    @Test
    @Tag("processing_verification_by_code_C75_1720274")
    @DisplayName("1720274 - Прозвон контактного лица/Супруги. Обработка проверки по 'Коду результата проверки' = C75. Прозвон контактного лица/Супруги. AUTOREFUSE")
    @WorkItemIds({"1720274"})
    public void processing_verification_by_code_C75_1720274() {

        callContactPersonSpoursePage
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон контактного лица/супруга (-и)/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Выявлен негатив")
                .selectValueFromDropDownList("Выпадающий список Выявлен негатив", "Негативная характеристика Клиента от супруга (-и)/контактного лица")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку").switchToOneTab().waitBusyCondition();

        actionsClaimSteps.executeQuery(VERIFICATION, "select request_final_result_code from vrf_check_step vcs " +
                "join vrf_check_set vcs2 on vcs2.id = vcs.check_set_id " +
                "where vcs2.claim_id ='" + claim + "' and check_type_code = 'CONTACT_PERSON_CALL'");
        assertIsTrue(actionsClaimSteps.getVariables("request_final_result_code").equals("AUTOREFUSE"), "Значение request_final_result_code == AUTOREFUSE");
    }

    @Test
    @Tag("processing_verification_by_code_C73_1724824")
    @DisplayName("1724824 - Обработка проверки по 'Коду результата проверки' = C73. Прозвон контактного лица/Супруги. NEXT")
    @WorkItemIds({"1724824"})
    public void processing_verification_by_code_C73_1724824(TestInfo testInfo) {

        callContactPersonSpoursePage
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон контактного лица/супруга (-и)/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Негатив отсутствует")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку").switchToOneTab().waitBusyCondition();

        actionsClaimSteps.executeQuery(VERIFICATION, "select request_final_result_code from vrf_check_step vcs " +
                "join vrf_check_set vcs2 on vcs2.id = vcs.check_set_id " +
                "where vcs2.claim_id ='" + claim + "' and check_type_code = 'CONTACT_PERSON_CALL'");
        assertIsTrue(actionsClaimSteps.getVariables("request_final_result_code").equals("NEXT"), "Значение request_final_result_code == NEXT");
    }
}
