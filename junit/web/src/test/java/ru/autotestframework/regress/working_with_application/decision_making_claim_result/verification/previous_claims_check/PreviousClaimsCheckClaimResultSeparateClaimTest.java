package ru.autotestframework.regress.working_with_application.decision_making_claim_result.verification.previous_claims_check;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.ExternalId;
import ru.psb.testit.annotations.WorkItemIds;

import java.util.List;
import java.util.Map;

import static ru.autotestframework.steps.asserts.Asserts.assertIsEquals;
import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;
import static ru.autotestframework.utils.Constants.VERIFICATION;

@Tag("previous_claims")
@Tag("regress")
@Tag("working_with_application")
@Tag("decision_making_claim_result")
@Tag("verification")
@Tag("previous_claims_check")
@Tag("previous_claims_check_claim_result_separate_claim")
@ClassName("На каждый кейс отдельная заявка. Результат проверки Проверка предыдущих заявок")
public class PreviousClaimsCheckClaimResultSeparateClaimTest extends BaseTest {

    Map<String, String> claimParams;
    String clientId = Integer.toString((int) (Math.random() * 90000000) + 10000000);

    @BeforeEach
    public void login() {
        claimParams = Map.of("clientId", clientId);
        actionsClaimSteps.emulateCreationOfPreviousClaim(clientId, "3468570");
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi()
                    .openMenuLinks("Личный кабинет")
                    .goTo(personalAccountPage)
                    .waitBusyCondition()
                    .clickOnElement("Раздел Верификация");
        }
    }

    @AfterAll
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("smoke")
    @Tag("absence_of_inf_message_3468570")
    @DisplayName("3468570 - Проверка отсутствия инф.сообщения \"Доход завышен\" на прозвонах после пред. заявок (признак isIncomeGrowth = true)")
    @WorkItemIds({"3468570"})
    public void absence_of_inf_message_3468570(TestInfo testInfo) {
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3861303.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);

        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(verificationStrategyPage)
                .waitBusyCondition()
                .checkElementByTitleContains("Поле Наименование стратегии", "Проверка предыдущих заявок")
                .checkElementByTitleContains("Выпадающий список Результат проверки", "Расхождения выявлены")
                .checkElementByTitleContains("Выпадающий список Виды выявленных расхождений", "Значительное увеличение дохода подтверждено")
                .colorElementEquals("Поле Предупреждение", "rgba(255, 0, 0, 1)")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Расхождения не выявлены")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .waitBusyCondition()
                .switchToOneTab();
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(customerCallPage)
                .waitBusyCondition()
                .checkElementByTitleContains("Поле Наименование стратегии", "Прозвон клиента");
        List<String> actualSteps = callingEmployerConfirmedPhoneRequiredPage.getActualStepNames();
        assertIsEquals(List.of(
                        "Прозвон клиента",
                        "Прозвон контактного лица/супруга (-и)",
                        "Прозвон работодателя - любой телефон",
                        "Прозвон работодателя - любой телефон"),
                actualSteps, "Поле Наименование шага");
        customerCallPage.assertElementByTitleVisibility("Поле Доход завышен", "не отображается")
                .clickOnStep("Прозвон контактного лица/супруга (-и)")
                .assertElementByTitleVisibility("Поле Доход завышен", "не отображается")
                .clickOnStep("Прозвон работодателя - любой телефон", "Основное место работы")
                .assertElementByTitleVisibility("Поле Доход завышен", "не отображается")
                .clickOnStep("Прозвон работодателя - любой телефон", "Совместительство")
                .assertElementByTitleVisibility("Поле Доход завышен", "не отображается")
                .closeCurrentTab()
                .goTo(personalAccountPage)
                .switchToOneTab();
    }

    @Test
    @Tag("data_on_previous_claim_1720317")
    @DisplayName("1720317 - Обработка проверки по 'Коду результата проверки' = C8. PREVIOUS_CLAIM. NEXT.")
    @WorkItemIds({"1720317"})
    public void verification_processing_1720317(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "clientId", clientId + "",
                "Code", "stub3");
        actionsClaimSteps.emulateCreationOfPreviousClaim("" + clientId, "1720317");
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3873666.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);

        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(checkingPreviousClaimsPage)
                .waitBusyCondition()
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка предыдущих заявок/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Расхождения не выявлены")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку").switchToOneTab().waitBusyCondition();
        actionsClaimSteps.executeQuery(VERIFICATION, "select request_final_result_code from vrf_check_step vcs " +
                "join vrf_check_set vcs2 on vcs2.id = vcs.check_set_id " +
                "where vcs2.claim_id ='" + claim + "' and check_type_code = 'PREVIOUS_CLAIM'");
        assertIsTrue(actionsClaimSteps.getVariables("request_final_result_code").equals("NEXT"), "Значение request_final_result_code == NEXT");
    }

    @Test
    @Tag("data_on_previous_claim_1720318")
    @DisplayName("1720318 - Обработка проверки по 'Коду результата проверки' = C10. Предыдущие заявки. AUTOREFUSE.")
    @WorkItemIds({"1720318"})
    public void verification_processing_1720318(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "clientId", clientId + "",
                "Code", "stub3");
        actionsClaimSteps.emulateCreationOfPreviousClaim("" + clientId, "1720318");
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1312880.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);

        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(checkingPreviousClaimsPage)
                .waitBusyCondition()
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка предыдущих заявок/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Расхождения выявлены")
                .selectValueFromDropDownList("Выпадающий список Виды выявленных расхождений", "Расхождения в справках 2-НДФЛ за один и тот же период")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку").switchToOneTab().waitBusyCondition();
        actionsClaimSteps.executeQuery(VERIFICATION, "select request_final_result_code from vrf_check_step vcs " +
                "join vrf_check_set vcs2 on vcs2.id = vcs.check_set_id " +
                "where vcs2.claim_id ='" + claim + "' and check_type_code = 'PREVIOUS_CLAIM'");
        assertIsTrue(actionsClaimSteps.getVariables("request_final_result_code").equals("AUTOREFUSE"), "Значение request_final_result_code == AUTOREFUSE");
    }


    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "1720315; Обработка проверки по 'Коду результата проверки' = C123. Проверка предыдущих заявок. Изменен на NEXT.; Значительное увеличение дохода подтверждено; Проверка предыдущих заявок - Значительное увеличение дохода подтверждено",
            "1720316; Обработка проверки по 'Коду результата проверки' = C12. Предыдущие заявки. Изменен на NEXT.; Подпись одного и того же Заверителя в справках/копии ТК отлична; Проверка предыдущих заявок - Подпись одного и того же Заверителя в справках/копии ТК отлична"
    })
    @Tag("verification_processing_1720315_1720316")
    @DisplayName("{id} - Обработка проверки по 'Коду результата проверки'")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void verification_processing_1720315_1720316(String id,String displayName, String discrepancies, String reasonAppointment, TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "clientId", clientId + "",
                "Code", "stub3");
        actionsClaimSteps.emulateCreationOfPreviousClaim("" + clientId, id);
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3873666.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);

        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(checkingPreviousClaimsPage)
                .waitBusyCondition()
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка предыдущих заявок/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Расхождения выявлены")
                .selectValueFromDropDownList("Выпадающий список Виды выявленных расхождений", discrepancies)
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку").switchToOneTab().waitBusyCondition();
        actionsClaimSteps.executeQuery(VERIFICATION, "select request_final_result_code from vrf_check_step vcs " +
                "join vrf_check_set vcs2 on vcs2.id = vcs.check_set_id " +
                "where vcs2.claim_id ='" + claim + "' and check_type_code = 'PREVIOUS_CLAIM'");
        assertIsTrue(actionsClaimSteps.getVariables("request_final_result_code").equals("NEXT"), "Значение request_final_result_code == NEXT");
        loginPage
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim)
                .goTo(personalAccountPage)
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(cardRequestPage)
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - Ожидает")
                .goTo(customerCallPage)
                .checkElementByTitleContains("Название Степа 1", "Прозвон клиента")
                .checkElementByTitleContains("Название Степа 2", "Прозвон работодателя - любой телефон")
                .checkElementByTitleContains("Название Степа 3", "Прозвон работодателя - любой телефон")
                .checkElementByTitleContains("Описание Степа 2", "Основное место работы")
                .checkElementByTitleContains("Описание Степа 3", "Совместительство")
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Основные данные").switchToNewTab()
                .clickOnElement("Вкладка Результаты проверок")
                .goTo(resultCheck)
                .clickOnElement("Вкладка Ручные проверки")
                .goTo(manualChecks)
                .clickOnElement("Кнопка Верификация рассчитанные проверки");
        assertIsEquals(reasonAppointment, manualChecks.getTextFromTable("Таблица Верификация рассчитанные проверки", 2, "Причина назначения"), "Значение столбца 'Причина назначения");
        assertIsEquals(reasonAppointment, manualChecks.getTextFromTable("Таблица Верификация рассчитанные проверки", 3, "Причина назначения"), "Значение столбца 'Причина назначения");
        assertIsEquals(reasonAppointment, manualChecks.getTextFromTable("Таблица Верификация рассчитанные проверки", 4, "Причина назначения"), "Значение столбца 'Причина назначения");
        manualChecks.closeCurrentTab().closeCurrentTab()
                .goTo(loginPage)
                .resetFilters()
                .openMenuLinks("Личный кабинет");
    }
}
