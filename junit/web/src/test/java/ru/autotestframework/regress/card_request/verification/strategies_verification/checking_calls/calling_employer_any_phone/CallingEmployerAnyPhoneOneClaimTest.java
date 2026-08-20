package ru.autotestframework.regress.card_request.verification.strategies_verification.checking_calls.calling_employer_any_phone;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import java.util.List;

import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;
import static ru.autotestframework.utils.Constants.VERIFICATION;

@Tag("regress")
@Tag("card_request")
@Tag("verification")
@Tag("strategies_verification")
@Tag("calling_employer_any_phone_one_claim")
@ClassName("Карточка заявки. Верификация. ЭФ стратегий верификации. Проверка прозвонов. Прозвон работодателя - любой телефон. На одной заявке")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CallingEmployerAnyPhoneOneClaimTest extends BaseTest {

    private String claim;

    @BeforeAll
    public void createClaim(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3241821.json", 1, testInfo).get(0);
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
        callingEmployerAnyPhonePage.closeCurrentTab();
    }

    @AfterAll
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("smoke")
    @Tag("checking_presence_elements_1719738")
    @DisplayName("1719738 - Проверка наличия элементов на ЭФ верификации для стратегии «Прозвон работодателя – любой телефон»")
    @WorkItemIds({"1719738"})
    public void checking_presence_elements_1719738() {
        List<String> listValue = List.of(
                "",
                "Подтвержденный",
                "Неподтвержденный");

        callingEmployerAnyPhonePage
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон работодателя - любой телефон/Версия 1")
                .assertElementByTitleVisibility("Кнопка Основные данные", "отображается")
                .assertElementByTitleVisibility("Кнопка Изменить результат", "отображается")
                .assertElementByTitleVisibility("Выпадающий список Результат проверки", "отображается")
                .assertElementByTitleVisibility("Выпадающий список Результат по заявке", "отображается")
                .assertElementByTitleVisibility("Кнопка Скрипт разговора", "отображается")
                .assertElementByTitleVisibility("Выпадающий список Номер, используемый для звонка", "отображается")

                .checkDropDownListElements("Выпадающий список Номер, используемый для звонка", listValue)
                .checkAvailabilityMultipleChoice("Выпадающий список Номер, используемый для звонка", false);
    }

    @Test
    @Tag("checking_dropdown_list_values_1719737")
    @DisplayName("1719737 - Проверка выпадающего списка \"Результат проверки\" для стратегии «Прозвон работодателя – любой телефон»")
    @WorkItemIds({"1719737"})
    public void checking_dropdown_list_values_1719737() {
        actionsClaimSteps.executeQuery(VERIFICATION, "select vdcsr.description from vrf_dir_check_step_result vdcsr " +
                "join vrf_dir_check_step_result_check_type vdcsrct on vdcsrct.dir_check_step_result_id = vdcsr.id " +
                "where vdcsrct.check_type_code = 'EMPLOYER_CALL' and dir_check_step_result_parent_id is null;");
        List<String> valuesFromDb = actionsClaimSteps.getValuesFromResponseDb("description");

        callingEmployerAnyPhonePage
                .checkDropDownListElements("Выпадающий список Результат проверки", valuesFromDb)
                .checkAvailabilityMultipleChoice("Выпадающий список Результат проверки", false);
    }

    @Test
    @Tag("checking_dropdown_list_values_1719730")
    @DisplayName("1719730 -  Проверка появления дополнительного поля при выборе результата проверки «Нерезультативный прозвон» для стратегии «Прозвон работодателя – любой телефон»")
    @WorkItemIds({"1719730"})
    public void checking_dropdown_list_values_1719730() {
        actionsClaimSteps.executeQuery(VERIFICATION, "select vdcsr.description from vrf_dir_check_step_result vdcsr " +
                "join vrf_dir_check_step_result_check_type vdcsrct on vdcsrct.dir_check_step_result_id = vdcsr.id " +
                "where vdcsrct.check_type_code = 'EMPLOYER_CALL' and dir_check_step_result_parent_id = 101;");
        List<String> valuesFromDb = actionsClaimSteps.getValuesFromResponseDb("description");

        callingEmployerAnyPhonePage
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Нерезультативный прозвон")
                .checkDropDownListElements("Выпадающий список Нерезультативный прозвон", valuesFromDb)
                .checkAvailabilityMultipleChoice("Выпадающий список Нерезультативный прозвон", false);
    }

    @Test
    @Tag("checking_contactless_confirmation_1719736")
    @DisplayName("1719736 - Проверка появления дополнительного поля при выборе результата проверки «Бесконтакное подтверждение» для стратегии «Прозвон работодателя – любой телефон»")
    @WorkItemIds({"1719736"})
    public void checking_contactless_confirmation_1719736() {
        actionsClaimSteps.executeQuery(VERIFICATION, "select vdcsr.description from vrf_dir_check_step_result vdcsr " +
                "join vrf_dir_check_step_result_check_type vdcsrct on vdcsrct.dir_check_step_result_id = vdcsr.id " +
                "where vdcsrct.check_type_code = 'EMPLOYER_CALL' and dir_check_step_result_parent_id = 103;");
        List<String> valuesFromDb = actionsClaimSteps.getValuesFromResponseDb("description");
        callingEmployerConfirmedPhonePage
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Бесконтактное подтверждение")
                .checkAvailabilityMultipleChoice("Выпадающий список Бесконтактное подтверждение", false)
                .checkDropDownListElements("Выпадающий список Бесконтактное подтверждение", valuesFromDb);
        for (String value : valuesFromDb) {
            callingEmployerConfirmedPhonePage
                    .selectValueFromDropDownList("Выпадающий список Бесконтактное подтверждение", value)
                    .assertElementByTitleVisibility("Поле ввода Источник подтверждения", "отображается");
        }
    }

    @Test
    @Tag("checking_document_covering_risks_provided_1719734")
    @DisplayName("1719734 - Проверка появления дополнительного поля при выборе результата проверки «Предоставлен документ закрывающий риски» для стратегии «Прозвон работодателя – любой телефон»")
    @WorkItemIds({"1719734"})
    public void checking_document_covering_risks_provided_1719734() {
        actionsClaimSteps.executeQuery(VERIFICATION, "select vdcsr.description from vrf_dir_check_step_result vdcsr " +
                "join vrf_dir_check_step_result_check_type vdcsrct on vdcsrct.dir_check_step_result_id = vdcsr.id " +
                "where vdcsrct.check_type_code = 'EMPLOYER_CALL' and dir_check_step_result_parent_id = 79;");
        List<String> valuesFromDb = actionsClaimSteps.getValuesFromResponseDb("description");

        callingEmployerConfirmedPhonePage
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Предоставлен документ, закрывающий риски")
                .checkAvailabilityMultipleChoice("Выпадающий список Предоставлен документ, закрывающий риски", false)
                .checkDropDownListElements("Выпадающий список Предоставлен документ, закрывающий риски", valuesFromDb);
    }

    @Test
    @Tag("checking_indirect_proof_employment_1719741")
    @DisplayName("1719741 - Проверка появления дополнительного поля при выборе результата проверки «Косвенное подтверждение занятости» для стратегии «Прозвон работодателя – любой телефон»")
    @WorkItemIds({"1719741"})
    public void checking_indirect_proof_employment_1719741() {
        actionsClaimSteps.executeQuery(VERIFICATION, "select vdcsr.description from vrf_dir_check_step_result vdcsr " +
                "join vrf_dir_check_step_result_check_type vdcsrct on vdcsrct.dir_check_step_result_id = vdcsr.id " +
                "where vdcsrct.check_type_code = 'EMPLOYER_CALL' and dir_check_step_result_parent_id = 111;");
        List<String> valuesFromDb = actionsClaimSteps.getValuesFromResponseDb("description");
        callingEmployerConfirmedPhonePage
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Косвенное подтверждение занятости")
                .checkAvailabilityMultipleChoice("Выпадающий список Косвенное подтверждение занятости", false)
                .checkDropDownListElements("Выпадающий список Косвенное подтверждение занятости", valuesFromDb)
                .selectValueFromDropDownList("Выпадающий список Косвенное подтверждение занятости", "Пункт 5 РА")
                .assertElementByTitleVisibility("Поле ввода Источник подтверждения", "отображается");
    }

    @Test
    @Tag("checking_effective_ringing_1719733")
    @DisplayName("1719733 - Проверка появления дополнительного поля при выборе результата проверки «Результативный прозвон» для стратегии «Прозвон работодателя – любой телефон»")
    @WorkItemIds({"1719733"})
    public void checking_effective_ringing_1719733() {
        List<String> actualDropDownListCheckBox;
        List<String> value = List.of(
                "Клиент уволен / находится в стадии увольнения",
                "Декрет");

        actionsClaimSteps.executeQuery(VERIFICATION, "select vdcsr.description from vrf_dir_check_step_result vdcsr " +
                "join vrf_dir_check_step_result_check_type vdcsrct on vdcsrct.dir_check_step_result_id = vdcsr.id " +
                "where vdcsrct.check_type_code = 'EMPLOYER_CALL' and dir_check_step_result_parent_id = 102;");
        List<String> valuesFromDb = actionsClaimSteps.getValuesFromResponseDb("description");

        callingEmployerConfirmedPhonePage
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                .checkDropDownListElements("Выпадающий список Результативный прозвон", valuesFromDb)
                .checkAvailabilityMultipleChoice("Выпадающий список Результативный прозвон", false)

                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Негатив не выявлен, все ответы получены")
                .assertElementByTitleVisibility("Выпадающий список Выявлен негатив", "не отображается")
                .assertElementByTitleActivity("Кнопка Далее", "активен")

                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Выявлен негатив")
                .assertElementByTitleVisibility("Выпадающий список Выявлен негатив", "отображается")
                .checkAvailabilityMultipleChoice("Выпадающий список Выявлен негатив", true);

        actionsClaimSteps.executeQuery(VERIFICATION, "select vdcsr.description from vrf_dir_check_step_result vdcsr " +
                "join vrf_dir_check_step_result_check_type vdcsrct on vdcsrct.dir_check_step_result_id = vdcsr.id " +
                "where vdcsrct.check_type_code = 'EMPLOYER_CALL' and dir_check_step_result_parent_id = 7;");
        List<String> valuesNegativeFromDb = actionsClaimSteps.getValuesFromResponseDb("description");

        callingEmployerConfirmedPhonePage
                .clickOnElement("Выпадающий список Выявлен негатив");
        actualDropDownListCheckBox = callingEmployerConfirmedPhonePage.getListCheckBox("Выпадающий список чек-боксов Выявлен негатив");
        assertIsTrue(valuesNegativeFromDb.containsAll(actualDropDownListCheckBox), "Список " + valuesNegativeFromDb + " соответствует списку " + actualDropDownListCheckBox);
        callingEmployerConfirmedPhonePage
                .clickOnElement("Интерфейс")
                .selectValueFromDropDownList("Выпадающий список Выявлен негатив", value)
                .clickOnElement("Иконка удалить Выбранный чекбокс")
                .clickOnElement("Иконка удалить Выбранный чекбокс")
                .assertElementByTitleVisibility("Список выбранных чекбоксов в разделе Выявлен негатив", "не отображается")

                .selectValueFromDropDownList("Выпадающий список Выявлен негатив", value)
                .assertElementByTitleVisibility("Список выбранных чекбоксов в разделе Выявлен негатив", "отображается")
                .selectValueFromDropDownList("Выпадающий список Выявлен негатив", value)
                .assertElementByTitleVisibility("Список выбранных чекбоксов в разделе Выявлен негатив", "не отображается");
    }
    @Test
    @Tag("checking_routing_unsuccessful_ringing_1719743")
    @DisplayName("1719743 -  Проверка маршрутизации заявки при выборе результата проверки \"Нерезультативный прозвон\" для стратегии «Прозвон работодателя – любой телефон»")
    @WorkItemIds({"1719743"})
    public void checking_routing_unsuccessful_ringing_1719743() {
        List<String> values = List.of(
                "Работодатель не отвечает/недоступен",
                "Представитель работодателя просит перезвонить",
                "Работодатель просит перезвонить через длительный промежуток времени.",
                "Отказ в предоставлении информации");
        callingEmployerAnyPhoneRequiredOneClaimPage.selectValueFromDropDownList("Выпадающий список Результат проверки", "Нерезультативный прозвон");

        for (String value : values) {
            callingEmployerAnyPhoneRequiredOneClaimPage
                    .selectValueFromDropDownList("Выпадающий список Нерезультативный прозвон", value)
                    .assertElementByTitleActivity("Кнопка Далее", "не активен");
        }
    }

    @Test
    @Tag("checking_routing_unsuccessful_ringing_1719739")
    @DisplayName("1719739 - Проверка маршрутизации заявки при выборе результата проверки «Бесконтакное подтверждение» для стратегии «Прозвон работодателя – любой телефон»»")
    @WorkItemIds({"1719739"})
    public void checking_routing_contactless_confirmation_1719739() {
        List<String> values = List.of(
                "Сторонние сайты",
                "Официальный сайт");
        callingEmployerConfirmedPhoneRequiredPage.selectValueFromDropDownList("Выпадающий список Результат проверки", "Бесконтактное подтверждение");

        for (String value : values) {
            callingEmployerConfirmedPhoneRequiredPage
                    .selectValueFromDropDownList("Выпадающий список Бесконтактное подтверждение", value)
                    .assertElementByTitleActivity("Кнопка Далее", "активен")
                    .assertElementByTitleVisibility("Иконка Шаг заблокирован", "не отображается");
        }
    }

    @Test
    @Tag("checking_document_covering_risks_provided_1719735")
    @DisplayName("1719735 - Проверка маршрутизации заявки при выборе результата проверки \"Предоставлен документ закрывающий риски\" для стратегии «Прозвон работодателя – любой телефон»")
    @WorkItemIds({"1719735"})
    public void checking_document_covering_risks_provided_1719735() {
        List<String> values = List.of(
                "Выписка из ПФР",
                "Электронная ТК",
                "Выписка с з/п счета",
                "Удостоверение силовика/военнослужащего/военный билет (для военнослужащего)");
        callingEmployerConfirmedPhonePage.selectValueFromDropDownList("Выпадающий список Результат проверки", "Предоставлен документ, закрывающий риски");

        for (String value : values) {
            callingEmployerConfirmedPhonePage
                    .selectValueFromDropDownList("Выпадающий список Предоставлен документ, закрывающий риски", value)
                    .assertElementByTitleActivity("Кнопка Далее", "активен")
                    .assertElementByTitleVisibility("Иконка Шаг заблокирован", "не отображается");
        }
    }

    @Test
    @Tag("checking_indirect_proof_employment_1719742")
    @DisplayName("1719742 - Проверка маршрутизации заявки при выборе результата проверки \"Косвенное подтверждение занятости\" для стратегии «Прозвон работодателя – любой телефон»")
    @WorkItemIds({"1719742"})
    public void checking_indirect_proof_employment_1719742() {
        List<String> values = List.of(
                "Пункт 1 РА",
                "Пункт 2 РА",
                "Пункт 3 РА",
                "Пункт 5 РА");
        callingEmployerConfirmedPhonePage.selectValueFromDropDownList("Выпадающий список Результат проверки", "Косвенное подтверждение занятости");

        for (String value : values) {
            callingEmployerConfirmedPhonePage
                    .selectValueFromDropDownList("Выпадающий список Косвенное подтверждение занятости", value)
                    .assertElementByTitleActivity("Кнопка Далее", "активен")
                    .assertElementByTitleVisibility("Иконка Шаг заблокирован", "не отображается");
        }
    }

    @Test
    @Tag("checking_effective_ringing_1719732")
    @DisplayName("1719732 - Проверка маршрутизации заявки при выборе результата проверки \"Результативный прозвон\" для стратегии «Прозвон работодателя – любой телефон»")
    @WorkItemIds({"1719732"})
    public void checking_effective_ringing_1719732() {
        List<String> actualDropDownListCheckBox;
        List<String> value = List.of(
                "Клиент уволен / находится в стадии увольнения",
                "Декрет");

        actionsClaimSteps.executeQuery(VERIFICATION, "select vdcsr.description from vrf_dir_check_step_result vdcsr " +
                "join vrf_dir_check_step_result_check_type vdcsrct on vdcsrct.dir_check_step_result_id = vdcsr.id " +
                "where vdcsrct.check_type_code = 'EMPLOYER_MANDATORY_CALL' and dir_check_step_result_parent_id = 102;");
        List<String> valuesFromDb = actionsClaimSteps.getValuesFromResponseDb("description");

        callingEmployerAnyPhoneRequiredOneClaimPage
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                .checkDropDownListElements("Выпадающий список Результативный прозвон", valuesFromDb)
                .checkAvailabilityMultipleChoice("Выпадающий список Результативный прозвон", false)
                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Негатив не выявлен, все ответы получены")
                .assertElementByTitleVisibility("Выпадающий список Выявлен негатив", "не отображается")
                .assertElementByTitleActivity("Кнопка Далее", "активен")
                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Выявлен негатив")
                .checkAvailabilityMultipleChoice("Выпадающий список Выявлен негатив", true);

        actionsClaimSteps.executeQuery(VERIFICATION, "select vdcsr.description from vrf_dir_check_step_result vdcsr " +
                "join vrf_dir_check_step_result_check_type vdcsrct on vdcsrct.dir_check_step_result_id = vdcsr.id " +
                "where vdcsrct.check_type_code = 'EMPLOYER_MANDATORY_CALL' and dir_check_step_result_parent_id = 7;");
        List<String> valuesNegativeFromDb = actionsClaimSteps.getValuesFromResponseDb("description");

        callingEmployerAnyPhoneRequiredOneClaimPage
                .clickOnElement("Выпадающий список Выявлен негатив");
        actualDropDownListCheckBox = callingEmployerAnyPhoneRequiredOneClaimPage.getListCheckBox("Выпадающий список чек-боксов Выявлен негатив");
        assertIsTrue(valuesNegativeFromDb.containsAll(actualDropDownListCheckBox), "Список " + valuesNegativeFromDb + " соответствует списку " + actualDropDownListCheckBox);
        callingEmployerAnyPhoneRequiredOneClaimPage
                .clickOnElement("Интерфейс")
                .selectValueFromDropDownList("Выпадающий список Выявлен негатив", value)
                .clickOnElement("Иконка удалить Выбранный чекбокс")
                .clickOnElement("Иконка удалить Выбранный чекбокс")
                .assertElementByTitleVisibility("Список выбранных чекбоксов в разделе Выявлен негатив", "не отображается")

                .selectValueFromDropDownList("Выпадающий список Выявлен негатив", value)
                .assertElementByTitleVisibility("Список выбранных чекбоксов в разделе Выявлен негатив", "отображается")
                .selectValueFromDropDownList("Выпадающий список Выявлен негатив", value)
                .assertElementByTitleVisibility("Список выбранных чекбоксов в разделе Выявлен негатив", "не отображается");
    }
    @Test
    @Tag("checking_fix_result_1719740")
    @DisplayName("1719740 - Проверка фиксации результатов проверки для стратегии «Прозвон работодателя – любой телефон»")
    @WorkItemIds({"1719740"})
    public void checking_fix_result_1719740() {
        callingEmployerAnyPhoneRequiredOneClaimPage
                .clickOnElement("Кнопка Далее")
                .waitText(2, "Для завершения шага необходимо заполнить результат проверки или результат по заявке")
                .clickOnElement("Кнопка ОК")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                .clickOnElement("Кнопка Далее")
                .waitText(2, "Пожалуйста, выберите значение")
                .clickOnElement("Кнопка ОК")
                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Выявлен негатив")
                .clickOnElement("Кнопка Далее")
                .waitText(2, "Пожалуйста, выберите негатив")
                .clickOnElement("Кнопка ОК");
    }
}
