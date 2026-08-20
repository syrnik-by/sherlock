package ru.autotestframework.regress.card_request.verification.strategies_verification.checking_calls.calling_employer_confirmed_phone;

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
@Tag("CallingEmployerConfirmedPhoneOneClaimTest")
@ClassName("Карточка заявки. Верификация. ЭФ стратегий верификации. Проверка прозвонов. Прозвон работодателя - подтвержденный телефон")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CallingEmployerConfirmedPhoneOneClaimTest extends BaseTest {

    private String claim;

    @BeforeAll
    public void createClaim(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3241520.json", 1, testInfo).get(0);
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
        callingEmployerConfirmedPhonePage.closeCurrentTab();
    }

    @AfterAll
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("smoke")
    @Tag("checking_presence_elements_1719720")
    @DisplayName("1719720 - Проверка наличия элементов на ЭФ верификации для стратегии «Проверка прозвон работодателя – подтвержденный телефон»")
    @WorkItemIds({"1719720"})
    public void checking_presence_elements_1719720() {
        callingEmployerConfirmedPhonePage.checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон работодателя - подтвержденный телефон/Версия 1")
                .assertElementByTitleVisibility("Кнопка Основные данные", "отображается")
                .assertElementByTitleVisibility("Кнопка Изменить результат", "отображается")
                .assertElementByTitleVisibility("Выпадающий список Результат проверки", "отображается")
                .assertElementByTitleVisibility("Выпадающий список Результат по заявке", "отображается")
                .assertElementByTitleVisibility("Кнопка Скрипт разговора", "отображается")
                .assertElementByTitleVisibility("Выпадающий список Номер, используемый для звонка", "отображается")
                .assertElementByTitleBlock("Выпадающий список Номер, используемый для звонка", "заблокирован");
        String actualValue = callingEmployerConfirmedPhonePage.getTextByElementTitle("Выпадающий список Номер, используемый для звонка");
        assertIsTrue(actualValue.contains("Подтвержденный"), "Поле предзаполнено значением «Подтвержденный»");
    }

    @Test
    @Tag("checking_dropdown_list_values_1719722")
    @DisplayName("1719722 - Проверка выпадающего списка \"Результат проверки\" для стратегии «Проверка прозвон работодателя – подтвержденный телефон»")
    @WorkItemIds({"1719722"})
    public void checking_dropdown_list_values_1719722() {
        actionsClaimSteps.executeQuery(VERIFICATION, "select vdcsr.description from vrf_dir_check_step_result vdcsr " +
                "join vrf_dir_check_step_result_check_type vdcsrct on vdcsrct.dir_check_step_result_id = vdcsr.id " +
                "where vdcsrct.check_type_code = 'EMPLOYER_CALL_CONFIRMED_NUMBER' and dir_check_step_result_parent_id is null;");
        List<String> valuesFromDb = actionsClaimSteps.getValuesFromResponseDb("description");

        callingEmployerConfirmedPhonePage
                .checkDropDownListElements("Выпадающий список Результат проверки", valuesFromDb)
                .checkAvailabilityMultipleChoice("Выпадающий список Результат проверки", false);
    }

    @Test
    @Tag("checking_dropdown_list_values_1719726")
    @DisplayName("1719726 - Проверка выбора результата проверки «Нерезультативный прозвон» и появление нового дополнительного поля")
    @WorkItemIds({"1719726"})
    public void checking_dropdown_list_values_1719726() {
        actionsClaimSteps.executeQuery(VERIFICATION, "select vdcsr.description from vrf_dir_check_step_result vdcsr " +
                "join vrf_dir_check_step_result_check_type vdcsrct on vdcsrct.dir_check_step_result_id = vdcsr.id " +
                "where vdcsrct.check_type_code = 'EMPLOYER_CALL_CONFIRMED_NUMBER' and dir_check_step_result_parent_id = 101;");
        List<String> valuesFromDb = actionsClaimSteps.getValuesFromResponseDb("description");

        callingEmployerConfirmedPhonePage
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Нерезультативный прозвон")
                .checkDropDownListElements("Выпадающий список Нерезультативный прозвон", valuesFromDb)
                .checkAvailabilityMultipleChoice("Выпадающий список Нерезультативный прозвон", false);
    }

    @Test
    @Tag("checking_dropdown_list_values_1719716")
    @DisplayName("1719716 - Проверка выбора результата проверки «Предоставлен документ, закрывающий риски» и появление нового дополнительного поля")
    @WorkItemIds({"1719716"})
    public void checking_dropdown_list_values_1719716() {
        actionsClaimSteps.executeQuery(VERIFICATION, "select vdcsr.description from vrf_dir_check_step_result vdcsr " +
                "join vrf_dir_check_step_result_check_type vdcsrct on vdcsrct.dir_check_step_result_id = vdcsr.id " +
                "where vdcsrct.check_type_code = 'EMPLOYER_CALL_CONFIRMED_NUMBER' and dir_check_step_result_parent_id = 79;");
        List<String> valuesFromDb = actionsClaimSteps.getValuesFromResponseDb("description");

        callingEmployerConfirmedPhonePage
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Предоставлен документ, закрывающий риски")
                .checkDropDownListElements("Выпадающий список Предоставлен документ, закрывающий риски", valuesFromDb)
                .checkAvailabilityMultipleChoice("Выпадающий список Предоставлен документ, закрывающий риски", false);
    }

    @Test
    @Tag("checking_confirmed_phone_number_not_found_1719724")
    @DisplayName("1719724 - Проверка выбора результата проверки «Подтвержденный телефон не найден» и появление нового дополнительного поля")
    @WorkItemIds({"1719724"})
    public void checking_confirmed_phone_number_not_found_1719724() {
        callingEmployerConfirmedPhonePage
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Подтвержденный телефон не найден")
                .assertElementByTitleActivity("Кнопка Далее", "не активен");
    }

    @Test
    @Tag("checking_contactless_confirmation_1719715")
    @DisplayName("1719715 - Проверка выбора результата проверки «Бесконтактное подтверждение» и появление нового дополнительного поля")
    @WorkItemIds({"1719715"})
    public void checking_contactless_confirmation_1719715() {
        actionsClaimSteps.executeQuery(VERIFICATION, "select vdcsr.description from vrf_dir_check_step_result vdcsr " +
                "join vrf_dir_check_step_result_check_type vdcsrct on vdcsrct.dir_check_step_result_id = vdcsr.id " +
                "where vdcsrct.check_type_code = 'EMPLOYER_CALL_CONFIRMED_NUMBER' and dir_check_step_result_parent_id = 103;");
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
    @Tag("checking_indirect_proof_employment_1719714")
    @DisplayName("1719714 - Проверка выбора результата проверки «Косвенное подтверждение занятости» и появление нового дополнительного поля")
    @WorkItemIds({"1719714"})
    public void checking_indirect_proof_employment_1719714() {
        actionsClaimSteps.executeQuery(VERIFICATION, "select vdcsr.description from vrf_dir_check_step_result vdcsr " +
                "join vrf_dir_check_step_result_check_type vdcsrct on vdcsrct.dir_check_step_result_id = vdcsr.id " +
                "where vdcsrct.check_type_code = 'EMPLOYER_CALL_CONFIRMED_NUMBER' and dir_check_step_result_parent_id = 111;");
        List<String> valuesFromDb = actionsClaimSteps.getValuesFromResponseDb("description");
        callingEmployerConfirmedPhonePage
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Косвенное подтверждение занятости")
                .checkAvailabilityMultipleChoice("Выпадающий список Косвенное подтверждение занятости", false)
                .checkDropDownListElements("Выпадающий список Косвенное подтверждение занятости", valuesFromDb)
                .selectValueFromDropDownList("Выпадающий список Косвенное подтверждение занятости", "Пункт 5 РА")
                .assertElementByTitleVisibility("Поле ввода Источник подтверждения", "отображается");
    }

    @Test
    @Tag("checking_effective_ringing_1719723")
    @DisplayName("1719723 - Проверка выбора результата проверки «Результативный прозвон» и появление нового дополнительного поля")
    @WorkItemIds({"1719723"})
    public void checking_effective_ringing_1719723() {
        List<String> actualDropDownListCheckBox;
        List<String> value = List.of(
                "Клиент уволен / находится в стадии увольнения",
                "Декрет");

        actionsClaimSteps.executeQuery(VERIFICATION, "select vdcsr.description from vrf_dir_check_step_result vdcsr " +
                "join vrf_dir_check_step_result_check_type vdcsrct on vdcsrct.dir_check_step_result_id = vdcsr.id " +
                "where vdcsrct.check_type_code = 'EMPLOYER_CALL_CONFIRMED_NUMBER' and dir_check_step_result_parent_id = 102;");
        List<String> valuesFromDb = actionsClaimSteps.getValuesFromResponseDb("description");

        callingEmployerConfirmedPhonePage
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                .checkDropDownListElements("Выпадающий список Результативный прозвон", valuesFromDb)
                .checkAvailabilityMultipleChoice("Выпадающий список Результативный прозвон", false)

                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Выявлен негатив")
                .assertElementByTitleVisibility("Выпадающий список Выявлен негатив", "отображается")
                .checkAvailabilityMultipleChoice("Выпадающий список Выявлен негатив", true);

        actionsClaimSteps.executeQuery(VERIFICATION, "select vdcsr.description from vrf_dir_check_step_result vdcsr " +
                "join vrf_dir_check_step_result_check_type vdcsrct on vdcsrct.dir_check_step_result_id = vdcsr.id " +
                "where vdcsrct.check_type_code = 'EMPLOYER_CALL_CONFIRMED_NUMBER' and dir_check_step_result_parent_id = 7;");
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
                .assertElementByTitleVisibility("Список выбранных чекбоксов в разделе Выявлен негатив", "не отображается")

                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Негатив не выявлен, все ответы получены")
                .assertElementByTitleVisibility("Выпадающий список Выявлен негатив", "не отображается");
    }

    @Test
    @Tag("checking_routing_unsuccessful_ringing_1719718")
    @DisplayName("1719718 - Проверка маршрутизации заявки при выборе результата проверки «Нерезультативный прозвон»")
    @WorkItemIds({"1719718"})
    public void checking_routing_unsuccessful_ringing_1719718() {
        List<String> values = List.of(
                "Работодатель не отвечает/недоступен",
                "Представитель работодателя просит перезвонить",
                "Работодатель просит перезвонить через длительный промежуток времени.",
                "Отказ в предоставлении информации");
        callingEmployerConfirmedPhoneRequiredPage.selectValueFromDropDownList("Выпадающий список Результат проверки", "Нерезультативный прозвон");

        for (String value : values) {
            callingEmployerConfirmedPhoneRequiredPage
                    .selectValueFromDropDownList("Выпадающий список Нерезультативный прозвон", value)
                    .assertElementByTitleActivity("Кнопка Далее", "не активен");
        }
    }

    @Test
    @Tag("checking_routing_unsuccessful_ringing_1719729")
    @DisplayName("1719729 - Проверка маршрутизации заявки при выборе результата проверки «Бесконтакное подтверждение»")
    @WorkItemIds({"1719729"})
    public void checking_routing_contactless_confirmation_1719729() {
        List<String> values = List.of(
                "Сторонние сайты",
                "Официальный сайт");
        callingEmployerConfirmedPhoneRequiredPage.selectValueFromDropDownList("Выпадающий список Результат проверки", "Бесконтактное подтверждение");

        for (String value : values) {
            callingEmployerConfirmedPhoneRequiredPage
                    .selectValueFromDropDownList("Выпадающий список Бесконтактное подтверждение", value)
                    .assertElementByTitleActivity("Кнопка Далее", "активен");
        }
    }

    @Test
    @Tag("checking_document_covering_risks_provided_1719721")
    @DisplayName("1719721 - Проверка маршрутизации заявки при выборе результата проверки «Предоставлен документ закрывающий риски»")
    @WorkItemIds({"1719721"})
    public void checking_document_covering_risks_provided_1719721() {
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
    @Tag("checking_indirect_proof_employment_1719719")
    @DisplayName("1719719 - Проверка маршрутизации заявки при выборе результата проверки «Косвенное подтверждение занятости»")
    @WorkItemIds({"1719719"})
    public void checking_indirect_proof_employment_1719719() {
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
    @Tag("checking_document_covering_risks_provided_1719725")
    @DisplayName("1719725 - Проверка маршрутизации заявки при выборе результата проверки «Подтвержденный телефон не найден»")
    @WorkItemIds({"1719725"})
    public void checking_document_covering_risks_provided_1719725() {
        callingEmployerConfirmedPhonePage.selectValueFromDropDownList("Выпадающий список Результат проверки", "Подтвержденный телефон не найден")
                .assertElementByTitleActivity("Кнопка Далее", "не активен")
                .assertElementByTitleVisibility("Иконка Шаг заблокирован", "отображается");
    }

    @Test
    @Tag("checking_effective_ringing_1719717")
    @DisplayName("1719717 - Проверка маршрутизации заявки при выборе результата проверки «Результативный прозвон»")
    @WorkItemIds({"1719717"})
    public void checking_effective_ringing_1719717() {
        List<String> values = List.of(
                "Клиент уволен / находится в стадии увольнения",
                "Декрет",
                "Негативная характеристика Клиента от работодателя",
                "Задержки з/п",
                "Информация о сокращениях",
                "Несоответствие минимальным требованиям",
                "Негатив на работодателя");

        List<String> valueSecond = List.of(
                "Подставной рабочий телефон",
                "Документы имеют признаки фальсификации",
                "Клиент предоставляет ложные анкетные данные");

        callingEmployerConfirmedPhonePage
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Негатив не выявлен, все ответы получены")
                .assertElementByTitleActivity("Кнопка Далее", "активен")
                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Выявлен негатив");
        for (String value : values) {
            callingEmployerConfirmedPhonePage
                    .selectValueFromDropDownList("Выпадающий список Выявлен негатив", value)
                    .assertElementByTitleVisibility("Иконка Шаг заблокирован", "отображается")
                    .assertElementByTitleActivity("Кнопка Далее", "активен")
                    .clickOnElement("Иконка удалить Выбранный чекбокс");
        }
        callingEmployerConfirmedPhonePage
                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Выявлен негатив");
        for (String value : valueSecond) {
            callingEmployerConfirmedPhonePage
                    .selectValueFromDropDownList("Выпадающий список Выявлен негатив", value)
                    .assertElementByTitleVisibility("Иконка Шаг заблокирован", "отображается")
                    .assertElementByTitleActivity("Кнопка Далее", "активен")
                    .clickOnElement("Иконка удалить Выбранный чекбокс");
        }
    }

    @Test
    @Tag("checking_fix_result_1719728")
    @DisplayName("1719728 - Проверка фиксации результатов проверки для стратегии «Проверка прозвон работодателя – подтвержденный телефон»")
    @WorkItemIds({"1719728"})
    public void checking_fix_result_1719728() {
        callingEmployerConfirmedPhonePage
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