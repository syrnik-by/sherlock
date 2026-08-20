package ru.autotestframework.regress.card_request.verification.strategies_verification.checking_calls.calling_employer_confirmed_phone_required;

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
@Tag("calling_employer_confirmed_phone_required_one_claim")
@ClassName("Карточка заявки. Верификация. ЭФ стратегий верификации. Проверка прозвонов. Прозвон работодателя - подтвержденный телефон (обязательный). На одной заявке")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CallingEmployerConfirmedPhoneRequiredOneClaimTest extends BaseTest {

    private String claim;

    @BeforeAll
    public void createClaim(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2515224.json", 1, testInfo).get(0);
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
        callingEmployerConfirmedPhoneRequiredPage.closeCurrentTab();
    }

    @AfterAll
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("smoke")
    @Tag("checking_presence_elements_1719682")
    @DisplayName("1719682 - Проверка наличия элементов на ЭФ верификации для стратегии «Проверка прозвон работодателя – подтвержденный телефон (обязательный)»")
    @WorkItemIds({"1719682"})
    public void checking_presence_elements_1719682() {
        callingEmployerConfirmedPhoneRequiredPage.checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон работодателя - подтвержденный телефон (обязательный)/Версия 1")
                .assertElementByTitleVisibility("Кнопка Основные данные", "отображается")
                .assertElementByTitleVisibility("Кнопка Изменить результат", "отображается")
                .assertElementByTitleVisibility("Выпадающий список Результат проверки", "отображается")
                .assertElementByTitleVisibility("Выпадающий список Результат по заявке", "отображается")
                .assertElementByTitleVisibility("Кнопка Скрипт разговора", "отображается")
                .assertElementByTitleVisibility("Выпадающий список Номер, используемый для звонка", "отображается")
                .assertElementByTitleBlock("Выпадающий список Номер, используемый для звонка", "заблокирован");
        String actualValue = callingEmployerConfirmedPhoneRequiredPage.getTextByElementTitle("Выпадающий список Номер, используемый для звонка");
        assertIsTrue(actualValue.contains("Подтвержденный"), "Поле предзаполнено значением «Подтвержденный»");
    }

    @Test
    @Tag("checking_dropdown_list_values_1719679")
    @DisplayName("1719679 - Проверка выпадающего списка \"Результат проверки\" для стратегии «Проверка прозвон работодателя – подтвержденный телефон (обязательный)»")
    @WorkItemIds({"1719679"})
    public void checking_dropdown_list_values_1719679() {
        actionsClaimSteps.executeQuery(VERIFICATION, "select vdcsr.description from vrf_dir_check_step_result vdcsr " +
                "join vrf_dir_check_step_result_check_type vdcsrct on vdcsrct.dir_check_step_result_id = vdcsr.id " +
                "where vdcsrct.check_type_code = 'EMPLOYER_MANDATORY_CALL_CONFIRMED_NUMBER' and dir_check_step_result_parent_id is null;");
        List<String> valuesFromDb = actionsClaimSteps.getValuesFromResponseDb("description");

        callingEmployerConfirmedPhoneRequiredPage
                .checkDropDownListElements("Выпадающий список Результат проверки", valuesFromDb);
    }

    @Test
    @Tag("checking_dropdown_list_values_1719689")
    @DisplayName("1719689 - Проверка выбора результата проверки «Нерезультативный прозвон» и появление нового дополнительного поля")
    @WorkItemIds({"1719689"})
    public void checking_dropdown_list_values_1719689() {
        actionsClaimSteps.executeQuery(VERIFICATION, "select vdcsr.description from vrf_dir_check_step_result vdcsr " +
                "join vrf_dir_check_step_result_check_type vdcsrct on vdcsrct.dir_check_step_result_id = vdcsr.id " +
                "where vdcsrct.check_type_code = 'EMPLOYER_MANDATORY_CALL_CONFIRMED_NUMBER' and dir_check_step_result_parent_id = 101;");
        List<String> valuesFromDb = actionsClaimSteps.getValuesFromResponseDb("description");

        callingEmployerConfirmedPhoneRequiredPage
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Нерезультативный прозвон")
                .checkDropDownListElements("Выпадающий список Нерезультативный прозвон", valuesFromDb);
    }

    @Test
    @Tag("checking_dropdown_list_values_1719688")
    @DisplayName("1719688 - Проверка появления нового поля для ввода информации при выборе результата проверки «Результативный прозвон» для стратегии \"Прозвон контактного лица/супруга(и)\"")
    @WorkItemIds({"1719688"})
    public void checking_dropdown_list_values_1719688() {
        actionsClaimSteps.executeQuery(VERIFICATION, "select vdcsr.description from vrf_dir_check_step_result vdcsr " +
                "join vrf_dir_check_step_result_check_type vdcsrct on vdcsrct.dir_check_step_result_id = vdcsr.id " +
                "where vdcsrct.check_type_code = 'EMPLOYER_MANDATORY_CALL_CONFIRMED_NUMBER' and dir_check_step_result_parent_id = 79;");
        List<String> valuesFromDb = actionsClaimSteps.getValuesFromResponseDb("description");

        callingEmployerConfirmedPhoneRequiredPage
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Предоставлен документ, закрывающий риски")
                .checkDropDownListElements("Выпадающий список Предоставлен документ, закрывающий риски", valuesFromDb);
    }

    @Test
    @Tag("checking_confirmed_phone_number_not_found_1719686")
    @DisplayName("1719686 - Проверка выбора результата проверки «Подтвержденный телефон не найден» и появление нового дополнительного поля")
    @WorkItemIds({"1719686"})
    public void checking_confirmed_phone_number_not_found_1719686() {
        callingEmployerConfirmedPhoneRequiredPage
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Подтвержденный телефон не найден")
                .assertElementByTitleActivity("Кнопка Далее", "не активен");
    }

    @Test
    @Tag("checking_effective_ringing_1719680")
    @DisplayName("1719680 - Проверка выбора результата проверки «Результативный прозвон» и появление нового дополнительного поля")
    @WorkItemIds({"1719680"})
    public void checking_effective_ringing_1719680() {
        List<String> actualDropDownListCheckBox;
        List<String> value = List.of(
                "Клиент уволен / находится в стадии увольнения",
                "Декрет");

        actionsClaimSteps.executeQuery(VERIFICATION, "select vdcsr.description from vrf_dir_check_step_result vdcsr " +
                "join vrf_dir_check_step_result_check_type vdcsrct on vdcsrct.dir_check_step_result_id = vdcsr.id " +
                "where vdcsrct.check_type_code = 'EMPLOYER_MANDATORY_CALL_CONFIRMED_NUMBER' and dir_check_step_result_parent_id = 102;");
        List<String> valuesFromDb = actionsClaimSteps.getValuesFromResponseDb("description");

        callingEmployerConfirmedPhoneRequiredPage
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                .checkDropDownListElements("Выпадающий список Результативный прозвон", valuesFromDb)
                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Выявлен негатив")
                .assertElementByTitleVisibility("Выпадающий список Выявлен негатив", "отображается");

        actionsClaimSteps.executeQuery(VERIFICATION, "select vdcsr.description from vrf_dir_check_step_result vdcsr " +
                "join vrf_dir_check_step_result_check_type vdcsrct on vdcsrct.dir_check_step_result_id = vdcsr.id " +
                "where vdcsrct.check_type_code = 'EMPLOYER_MANDATORY_CALL_CONFIRMED_NUMBER' and dir_check_step_result_parent_id = 7;");
        List<String> valuesNegativeFromDb = actionsClaimSteps.getValuesFromResponseDb("description");

        callingEmployerConfirmedPhoneRequiredPage
                .clickOnElement("Выпадающий список Выявлен негатив");
        actualDropDownListCheckBox = callingEmployerConfirmedPhoneRequiredPage.getListCheckBox("Выпадающий список чек-боксов Выявлен негатив");
        assertIsTrue(valuesNegativeFromDb.containsAll(actualDropDownListCheckBox), "Список " + valuesNegativeFromDb + " соответствует списку " + actualDropDownListCheckBox);
        callingEmployerConfirmedPhoneRequiredPage
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
    @Tag("checking_routing_unsuccessful_ringing_1719681")
    @DisplayName("1719681 - Проверка маршрутизации заявки при выбранном результате заявки \"Нерезультативный прозвон\" в стратегии «Проверка прозвон работодателя – подтвержденный телефон (обязательный)»")
    @WorkItemIds({"1719681"})
    public void checking_routing_unsuccessful_ringing_1719681() {
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
    @Tag("checking_dropdown_list_values_1719687")
    @DisplayName("1719687 - Проверка маршрутизации заявки при выбранном результате заявки \"Предоставлен документ закрывающий риски\" в стратегии «Проверка прозвон работодателя – подтвержденный телефон (обязательный)»")
    @WorkItemIds({"1719687"})
    public void checking_dropdown_list_values_1719687() {
        callingEmployerConfirmedPhoneRequiredPage
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Предоставлен документ, закрывающий риски")
                .selectValueFromDropDownList("Выпадающий список Предоставлен документ, закрывающий риски", "Выписка из ПФР")
                .assertElementByTitleActivity("Кнопка Далее", "активен")
                .selectValueFromDropDownList("Выпадающий список Предоставлен документ, закрывающий риски", "Электронная ТК")
                .assertElementByTitleActivity("Кнопка Далее", "активен");
    }

    @Test
    @Tag("checking_dropdown_list_values_1719685")
    @DisplayName("1719685 - Проверка маршрутизации заявки при выбранном результате заявки \"Подтвержденный телефон не найден\" в стратегии «Проверка прозвон работодателя – подтвержденный телефон (обязательный)»")
    @WorkItemIds({"1719685"})
    public void checking_dropdown_list_values_1719685() {
        callingEmployerConfirmedPhoneRequiredPage
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Подтвержденный телефон не найден")
                .assertElementByTitleActivity("Кнопка Далее", "не активен")
                .assertElementByTitleVisibility("Иконка Шаг заблокирован", "отображается")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "")
                .clickOnElement("Иконка Степ 2")
                .clickOnElement("Кнопка Взять шаг в работу")

                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Подтвержденный телефон не найден")
                .assertElementByTitleActivity("Кнопка Далее", "не активен")
                .assertElementByTitleVisibility("Иконка Шаг заблокирован", "не отображается");
    }

    @Test
    @Tag("checking_dropdown_list_values_1719678")
    @DisplayName("1719678 - Проверка маршрутизации заявки при выбранном результате заявки \"Результативный прозвон\" в стратегии «Проверка прозвон работодателя – подтвержденный телефон (обязательный)»")
    @WorkItemIds({"1719678"})
    public void checking_dropdown_list_values_1719678() {
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

        callingEmployerConfirmedPhoneRequiredPage
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Негатив не выявлен, все ответы получены")
                .assertElementByTitleActivity("Кнопка Далее", "активен");

        for (String value : values) {
            callingEmployerConfirmedPhoneRequiredPage
                    .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                    .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Выявлен негатив")
                    .selectValueFromDropDownList("Выпадающий список Выявлен негатив", value)
                    .assertElementByTitleVisibility("Иконка Шаг заблокирован", "отображается")
                    .assertElementByTitleActivity("Кнопка Далее", "активен")
                    .selectValueFromDropDownList("Выпадающий список Результат проверки", "")
                    .clickOnElement("Иконка Степ 2")
                    .clickOnElement("Кнопка Взять шаг в работу")
                    .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                    .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Выявлен негатив")
                    .selectValueFromDropDownList("Выпадающий список Выявлен негатив", value)
                    .assertElementByTitleVisibility("Иконка Шаг заблокирован", "не отображается")
                    .assertElementByTitleActivity("Кнопка Далее", "активен")
                    .selectValueFromDropDownList("Выпадающий список Результат проверки", "")
                    .clickOnElement("Иконка Степ 1")
                    .clickOnElement("Кнопка Взять шаг в работу");
        }

        callingEmployerConfirmedPhoneRequiredPage
                .clickOnElement("Кнопка Сохранить")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Выявлен негатив");
        for (String value : valueSecond) {
            callingEmployerConfirmedPhoneRequiredPage
                    .selectValueFromDropDownList("Выпадающий список Выявлен негатив", value)
                    .assertElementByTitleVisibility("Иконка Шаг заблокирован", "отображается")
                    .assertElementByTitleActivity("Кнопка Далее", "активен")
                    .clickOnElement("Иконка удалить Выбранный чекбокс");
        }
    }

    @Test
    @Tag("checking_fix_result_1719683")
    @DisplayName("1719683 - Проверка фиксации результатов проверки по стратегии «Проверка прозвон работодателя – подтвержденный телефон (обязательный)»")
    @WorkItemIds({"1719683"})
    public void checking_fix_result_1719683() {
        callingEmployerConfirmedPhoneRequiredPage
                .clickOnElement("Кнопка Далее")
                .waitText(2, "Для завершения шага необходимо заполнить результат проверки или результат по заявке")
                .clickOnElement("Кнопка ОК")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                .clickOnElement("Кнопка Далее")
                .waitText(2, "Пожалуйста, выберите значение")
                .clickOnElement("Кнопка ОК");
    }
}