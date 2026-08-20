package ru.autotestframework.regress.card_request.verification.strategies_verification.checking_calls.calling_employer_any_phone_required;

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
@Tag("calling_employer_any_phone_required_one_claim")
@ClassName("Карточка заявки. Верификация. ЭФ стратегий верификации. Проверка прозвонов. Прозвон работодателя - любой телефон (Обязательный). На одной заявке")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CallingEmployerAnyPhoneRequiredOneClaimTest extends BaseTest {

    private String claim;

    @BeforeAll
    public void createClaim(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3210154.json", 1, testInfo).get(0);
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
        callingEmployerAnyPhoneRequiredOneClaimPage.closeCurrentTab();
    }

    @AfterAll
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("checking_presence_elements_1719694")
    @DisplayName("1719694 - Проверка наличия элементов на ЭФ верификации для стратегии «Прозвон работодателя – любой телефон(обязательный)»")
    @WorkItemIds({"1719694"})
    public void checking_presence_elements_1719694() {
        callingEmployerAnyPhoneRequiredOneClaimPage
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон работодателя - любой телефон (Обязательный)/Версия 1")
                .assertElementByTitleVisibility("Кнопка Основные данные", "отображается")
                .assertElementByTitleVisibility("Кнопка Изменить результат", "отображается")
                .assertElementByTitleVisibility("Выпадающий список Результат проверки", "отображается")
                .assertElementByTitleVisibility("Выпадающий список Результат по заявке", "отображается")
                .assertElementByTitleVisibility("Кнопка Скрипт разговора", "отображается")
                .assertElementByTitleVisibility("Выпадающий список Номер, используемый для звонка", "отображается");
    }

    @Test
    @Tag("checking_dropdown_list_values_1719693")
    @DisplayName("1719693 - Проверка выпадающего списка поля \"Результат проверки\" для стратегии «Прозвон работодателя – любой телефон(обязательный)»")
    @WorkItemIds({"1719693"})
    public void checking_dropdown_list_values_1719693() {
        actionsClaimSteps.executeQuery(VERIFICATION, "select vdcsr.description from vrf_dir_check_step_result vdcsr " +
                "join vrf_dir_check_step_result_check_type vdcsrct on vdcsrct.dir_check_step_result_id = vdcsr.id " +
                "where vdcsrct.check_type_code = 'EMPLOYER_MANDATORY_CALL' and dir_check_step_result_parent_id is null;");
        List<String> valuesFromDb = actionsClaimSteps.getValuesFromResponseDb("description");

        callingEmployerConfirmedPhoneRequiredPage
                .checkDropDownListElements("Выпадающий список Результат проверки", valuesFromDb)
                .checkAvailabilityMultipleChoice("Выпадающий список Результат проверки", false);
    }

    @Test
    @Tag("checking_dropdown_number_used_call_1719698")
    @DisplayName("1719698 - Проверка выпадающего списка поля \"Номер, используемый для звонка\" для стратегии «Прозвон работодателя – любой телефон(обязательный)»")
    @WorkItemIds({"1719698"})
    public void checking_dropdown_number_used_call_1719698() {
        List<String> listValueNumberUsedCall = List.of(
                "",
                "Подтвержденный",
                "Неподтвержденный");

        callingEmployerAnyPhoneRequiredOneClaimPage
                .checkDropDownListElements("Выпадающий список Номер, используемый для звонка", listValueNumberUsedCall)
                .checkAvailabilityMultipleChoice("Выпадающий список Номер, используемый для звонка", false);
    }

    @Test
    @Tag("checking_dropdown_list_values_1719700")
    @DisplayName("1719700 - Проверка отображения дополнительного поля при результате проверки \"Нерезультативный прозвон\" для стратегии «Прозвон работодателя – любой телефон(обязательный)»")
    @WorkItemIds({"1719700"})
    public void checking_dropdown_list_values_1719700() {
        actionsClaimSteps.executeQuery(VERIFICATION, "select vdcsr.description from vrf_dir_check_step_result vdcsr " +
                "join vrf_dir_check_step_result_check_type vdcsrct on vdcsrct.dir_check_step_result_id = vdcsr.id " +
                "where vdcsrct.check_type_code = 'EMPLOYER_MANDATORY_CALL' and dir_check_step_result_parent_id = 101;");
        List<String> valuesFromDb = actionsClaimSteps.getValuesFromResponseDb("description");

        callingEmployerAnyPhoneRequiredOneClaimPage
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Нерезультативный прозвон")
                .checkDropDownListElements("Выпадающий список Нерезультативный прозвон", valuesFromDb)
                .checkAvailabilityMultipleChoice("Выпадающий список Нерезультативный прозвон", false);
    }

    @Test
    @Tag("checking_dropdown_list_values_1719701")
    @DisplayName("1719701 - Проверка отображения дополнительного поля при результате проверки \"Предоставлен документ, закрывающий риски\" для стратегии «Прозвон работодателя – любой телефон(обязательный)»")
    @WorkItemIds({"1719701"})
    public void checking_dropdown_list_values_1719701() {
        actionsClaimSteps.executeQuery(VERIFICATION, "select vdcsr.description from vrf_dir_check_step_result vdcsr " +
                "join vrf_dir_check_step_result_check_type vdcsrct on vdcsrct.dir_check_step_result_id = vdcsr.id " +
                "where vdcsrct.check_type_code = 'EMPLOYER_MANDATORY_CALL' and dir_check_step_result_parent_id = 79;");
        List<String> valuesFromDb = actionsClaimSteps.getValuesFromResponseDb("description");

        callingEmployerAnyPhoneRequiredOneClaimPage
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Предоставлен документ, закрывающий риски")
                .checkDropDownListElements("Выпадающий список Предоставлен документ, закрывающий риски", valuesFromDb)
                .checkAvailabilityMultipleChoice("Выпадающий список Предоставлен документ, закрывающий риски", false);
    }

    @Test
    @Tag("checking_effective_ringing_1719703")
    @DisplayName("1719703 - Проверка отображения дополнительного поля при результате проверки \"Результативный прозвон\" для стратегии «Прозвон работодателя – любой телефон(обязательный)»")
    @WorkItemIds({"1719703"})
    public void checking_effective_ringing_1719703() {
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
    @Tag("checking_routing_unsuccessful_ringing_1719699")
    @DisplayName("1719699 - Проверка маршрутизации заявки при выборе результата проверки \"Нерезультативный прозвон\" для стратегии «Прозвон работодателя – любой телефон(обязательный)»")
    @WorkItemIds({"1719699"})
    public void checking_routing_unsuccessful_ringing_1719699() {
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
    @Tag("checking_dropdown_list_values_1719695")
    @DisplayName("1719695 - Проверка маршрутизации заявки при выборе результата проверки \"Предоставлен документ закрывающий риски\" для стратегии «Прозвон работодателя – любой телефон(обязательный)»")
    @WorkItemIds({"1719695"})
    public void checking_dropdown_list_values_1719695() {
        callingEmployerAnyPhoneRequiredOneClaimPage
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Предоставлен документ, закрывающий риски")
                .selectValueFromDropDownList("Выпадающий список Предоставлен документ, закрывающий риски", "Выписка из ПФР")
                .assertElementByTitleActivity("Кнопка Далее", "активен")
                .selectValueFromDropDownList("Выпадающий список Предоставлен документ, закрывающий риски", "Электронная ТК")
                .assertElementByTitleActivity("Кнопка Далее", "активен");
    }

    @Test
    @Tag("checking_dropdown_list_values_1719702")
    @DisplayName("1719702 - Проверка маршрутизации заявки при выборе результата проверки \"Результативный прозвон\" для стратегии «Прозвон работодателя – любой телефон(обязательный)»")
    @WorkItemIds({"1719702"})
    public void checking_dropdown_list_values_1719702() {
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

        callingEmployerAnyPhoneRequiredOneClaimPage
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Негатив не выявлен, все ответы получены")
                .assertElementByTitleActivity("Кнопка Далее", "активен")
                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Выявлен негатив");

        for (String value : values) {
            callingEmployerAnyPhoneRequiredOneClaimPage
                    .selectValueFromDropDownList("Выпадающий список Выявлен негатив", value)
                    .assertElementByTitleVisibility("Иконка Шаг заблокирован", "отображается")
                    .assertElementByTitleActivity("Кнопка Далее", "активен")
                    .clickOnElement("Иконка удалить Выбранный чекбокс");
        }

        callingEmployerAnyPhoneRequiredOneClaimPage
                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Выявлен негатив");
        for (String value : valueSecond) {
            callingEmployerAnyPhoneRequiredOneClaimPage
                    .selectValueFromDropDownList("Выпадающий список Выявлен негатив", value)
                    .assertElementByTitleVisibility("Иконка Шаг заблокирован", "отображается")
                    .assertElementByTitleActivity("Кнопка Далее", "активен")
                    .clickOnElement("Иконка удалить Выбранный чекбокс");
        }
    }

    @Test
    @Tag("checking_fix_result_1719696")
    @DisplayName("1719696 - Проверка фиксации результатов для стратегии «Прозвон работодателя – любой телефон(обязательный)»")
    @WorkItemIds({"1719696"})
    public void checking_fix_result_1719696() {
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