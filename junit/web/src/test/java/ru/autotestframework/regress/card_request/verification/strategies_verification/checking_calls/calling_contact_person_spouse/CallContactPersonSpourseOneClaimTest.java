package ru.autotestframework.regress.card_request.verification.strategies_verification.checking_calls.calling_contact_person_spouse;

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
@Tag("call_contact_person_spourse_one_claim")
@ClassName("Карточка заявки. Верификация. ЭФ стратегий верификации. Проверка прозвонов. Прозвон контактного лица/супруга (-и). На одной заявке")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CallContactPersonSpourseOneClaimTest extends BaseTest {

    private String claim;

    @BeforeAll
    public void createClaim(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2513779.json", 1, testInfo).get(0);
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
        callContactPersonSpoursePage.closeCurrentTab();
    }

    @AfterAll
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("checking_presence_elements_1719655")
    @DisplayName("1719655 - Проверка наличия элементов на ЭФ верификации для стратегии \"Прозвон контактного лица/супруга(и)\"")
    @WorkItemIds({"1719655"})
    public void checking_presence_elements_1719655() {
        callContactPersonSpoursePage.checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон контактного лица/супруга (-и)/Версия 1")
                .assertElementByTitleVisibility("Кнопка Основные данные", "отображается")
                .assertElementByTitleVisibility("Кнопка Изменить результат", "отображается")
                .assertElementByTitleVisibility("Выпадающий список Результат проверки", "отображается")
                .assertElementByTitleVisibility("Выпадающий список Результат по заявке", "отображается")
                .assertElementByTitleVisibility("Кнопка Скрипт разговора", "отображается");
    }

    @Test
    @Tag("checking_dropdown_list_values_1719654")
    @DisplayName("1719654 - Проверка выпадающего списка \"Результат проверки\" для стратегии \"Прозвон контактного лица/супруга(и)\"")
    @WorkItemIds({"1719654"})
    public void checking_dropdown_list_values_1719654() {
        actionsClaimSteps.executeQuery(VERIFICATION, "select vdcsr.description from vrf_dir_check_step_result vdcsr " +
                "join vrf_dir_check_step_result_check_type vdcsrct on vdcsrct.dir_check_step_result_id = vdcsr.id " +
                "where vdcsrct.check_type_code = 'CONTACT_PERSON_CALL' and dir_check_step_result_parent_id is null;");
        List<String> valuesFromDb = actionsClaimSteps.getValuesFromResponseDb("description");

        callContactPersonSpoursePage
                .checkDropDownListElements("Выпадающий список Результат проверки", valuesFromDb)
                .selectValueFromDropDownList("Выпадающий список Результат проверки", valuesFromDb);
    }

    @Test
    @Tag("checking_dropdown_list_values_1719653")
    @DisplayName("1719653 - Проверка появления нового поля для ввода информации при выборе результата проверки «Нерезультативный прозвон» для стратегии \"Прозвон контактного лица/супруга(и)\"")
    @WorkItemIds({"1719653"})
    public void checking_dropdown_list_values_1719653() {
        actionsClaimSteps.executeQuery(VERIFICATION, "select vdcsr.description from vrf_dir_check_step_result vdcsr " +
                "join vrf_dir_check_step_result_check_type vdcsrct on vdcsrct.dir_check_step_result_id = vdcsr.id " +
                "where vdcsrct.check_type_code = 'CONTACT_PERSON_CALL' and dir_check_step_result_parent_id = 101;");
        List<String> valuesFromDb = actionsClaimSteps.getValuesFromResponseDb("description");

        callContactPersonSpoursePage
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Нерезультативный прозвон")
                .checkDropDownListElements("Выпадающий список Нерезультативный прозвон", valuesFromDb)
                .selectValueFromDropDownList("Выпадающий список Нерезультативный прозвон", "Контактное лицо/супруг (-а) не отвечает/недоступен")
                .checkVisibilityDropDownElement("Контактное лицо/супруг (-а) не отвечает/недоступен")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "")
                .assertElementByTitleVisibility("Выпадающий список Нерезультативный прозвон", "не отображается");
    }

    @Test
    @Tag("checking_dropdown_list_values_1719661")
    @DisplayName("1719661 - Проверка появления нового поля для ввода информации при выборе результата проверки «Результативный прозвон» для стратегии \"Прозвон контактного лица/супруга(и)\"")
    @WorkItemIds({"1719661"})
    public void checking_dropdown_list_values_1719661() {
        actionsClaimSteps.executeQuery(VERIFICATION, "select vdcsr.description from vrf_dir_check_step_result vdcsr " +
                "join vrf_dir_check_step_result_check_type vdcsrct on vdcsrct.dir_check_step_result_id = vdcsr.id " +
                "where vdcsrct.check_type_code = 'CONTACT_PERSON_CALL' and dir_check_step_result_parent_id = 102;");
        List<String> valuesFromDb = actionsClaimSteps.getValuesFromResponseDb("description");

        callContactPersonSpoursePage
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                .checkDropDownListElements("Выпадающий список Результативный прозвон", valuesFromDb)
                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Выявлен негатив")
                .checkVisibilityDropDownElement("Выявлен негатив")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "")
                .assertElementByTitleVisibility("Выпадающий список Результативный прозвон", "не отображается");
    }

    @Test
    @Tag("checking_delete_value_1719657")
    @DisplayName("1719657 - Проверка появления второго поля для ввода информации при выборе результата проверки «Результативный прозвон» и выявлении негатива для стратегии \"Прозвон контактного лица/супруга(и)\"")
    @WorkItemIds({"1719657"})
    public void checking_delete_value_1719657() {
        List<String> value = List.of("Негативная характеристика Клиента от супруга (-и)/контактного лица",
                "Кредит на бизнес");
        callContactPersonSpoursePage
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Выявлен негатив")
                .selectValueFromDropDownList("Выпадающий список Выявлен негатив", value)
                .assertElementByTitleVisibility("Список выбранных чекбоксов в разделе Выявлен негатив", "отображается")

                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Негатив отсутствует")
                .assertElementByTitleVisibility("Выпадающий список Выявлен негатив", "не отображается")

                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Выявлен негатив")
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
    @Tag("checking_routing_unsuccessful_ringing_1719660")
    @DisplayName("1719660 - Проверка маршрутизации заявки для результата проверки \"Нерезультативный прозвон\" для стратегии \"Прозвон контактного лица/супруга(и)\"")
    @WorkItemIds({"1719660"})
    public void checking_routing_unsuccessful_ringing_1719660() {
        List<String> values = List.of(
                "Контактное лицо/супруг (-а) не отвечает/недоступен",
                "Контактное лицо/супруг (-а) просит перезвонить",
                "Контактное лицо/супруг (-а) просит перезвонить через длительный промежуток времени");
        callContactPersonSpoursePage.selectValueFromDropDownList("Выпадающий список Результат проверки", "Нерезультативный прозвон");

        for (String value : values) {
            callContactPersonSpoursePage
                    .selectValueFromDropDownList("Выпадающий список Нерезультативный прозвон", value)
                    .assertElementByTitleActivity("Кнопка Далее", "активен");
        }
    }

    @Test
    @Tag("checking_routing_successful_ringing_1719658")
    @DisplayName("1719658 - Проверка маршрутизации заявки для результата проверки \"Результативный прозвон\" для стратегии \"Прозвон контактного лица/супруга(и)\"")
    @WorkItemIds({"1719658"})
    public void checking_routing_successful_ringing_1719658() {
        callContactPersonSpoursePage
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Негатив отсутствует")
                .assertElementByTitleActivity("Кнопка Далее", "активен")

                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Отказ контактного лица/супруга (-и) предоставить информацию")
                .assertElementByTitleActivity("Кнопка Далее", "не активен")

                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Отвечает третье лицо")
                .assertElementByTitleActivity("Кнопка Далее", "не активен")

                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Выявлен негатив")
                .assertElementByTitleVisibility("Выпадающий список Выявлен негатив", "отображается")

                .selectValueFromDropDownList("Выпадающий список Выявлен негатив", "Негативная характеристика Клиента от супруга (-и)/контактного лица")
                .assertElementByTitleVisibility("Иконка Шаг заблокирован", "отображается")
                .assertElementByTitleActivity("Кнопка Далее", "активен")
                .clickOnElement("Иконка удалить Выбранный чекбокс")

                .selectValueFromDropDownList("Выпадающий список Выявлен негатив", "Кредит на бизнес")
                .assertElementByTitleVisibility("Иконка Шаг заблокирован", "отображается")
                .assertElementByTitleActivity("Кнопка Далее", "активен");
    }

    @Test
    @Tag("checking_fixing_results_1719662")
    @DisplayName("1719662 - Проверка фиксации результатов проверки для стратегии \"Прозвон контактного лица/супруга(и)\"")
    @WorkItemIds({"1719662"})
    public void checking_fixing_results_1719662() {
        callContactPersonSpoursePage
                .clickOnElement("Кнопка Далее")
                .waitText(2, "Для завершения шага необходимо заполнить результат проверки или результат по заявке")
                .clickOnElement("Кнопка ОК")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                .clickOnElement("Кнопка Далее")
                .waitText(2, "Пожалуйста, выберите значение")
                .clickOnElement("Кнопка ОК");
    }
}
