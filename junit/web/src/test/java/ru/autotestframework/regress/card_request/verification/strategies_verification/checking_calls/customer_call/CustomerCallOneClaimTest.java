package ru.autotestframework.regress.card_request.verification.strategies_verification.checking_calls.customer_call;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import org.openqa.selenium.NoSuchElementException;
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
@Tag("customer_call_one_claim")
@ClassName("Карточка заявки. Верификация. ЭФ стратегий верификации. Проверка прозвонов. Прозвон клиента. На одной заявке")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CustomerCallOneClaimTest extends BaseTest {

    private String claim;
    private static List<String> listCheckBox = List.of("Кредит на бизнес", "Финансовые трудности");

    @BeforeAll
    public void createClaim(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2512523.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
    }

    @BeforeEach
    public void login() {
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException | NoSuchElementException e) {
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
        customerCallPage.closeCurrentTab();
    }

    @AfterAll
    public void cleanQueueClaims() {clearingQueueClaims.requestExpireAfterTestScenario(); }


    @Test
    @Tag("smoke")
    @Tag("checking_presence_elements_1719227")
    @DisplayName("1719227 - Проверка наличия элементов на ЭФ верификации для стратегии \"Прозвон клиента\"")
    @WorkItemIds({"1719227"})
    public void checking_presence_elements_1719227() {
        customerCallPage.checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон клиента/Версия 1")
                .assertElementByTitleVisibility("Кнопка Основные данные", "отображается")
                .assertElementByTitleVisibility("Кнопка Изменить результат", "отображается")
                .assertElementByTitleVisibility("Выпадающий список Результат проверки", "отображается")
                .assertElementByTitleVisibility("Выпадающий список Результат по заявке", "отображается")
                .assertElementByTitleVisibility("Кнопка Скрипт разговора", "отображается");
    }

    @Test
    @Tag("checking_dropdown_list_values_1719229")
    @DisplayName("1719229 - Проверка выпадающего списка \"Результат проверки\" для стратегии \"Прозвон клиента\"")
    @WorkItemIds({"1719229"})
    public void checking_dropdown_list_values_1719229() {
        actionsClaimSteps.executeQuery(VERIFICATION, "select vdcsr.description from vrf_dir_check_step_result vdcsr " +
                "join vrf_dir_check_step_result_check_type vdcsrct on vdcsrct.dir_check_step_result_id = vdcsr.id " +
                "where vdcsrct.check_type_code = 'CLIENT_CALL' and dir_check_step_result_parent_id is null;");
        List<String> valuesFromDb = actionsClaimSteps.getValuesFromResponseDb("description");

        customerCallPage
                .checkDropDownListElements("Выпадающий список Результат проверки", valuesFromDb);

    }

    @Test
    @Tag("checking_dropdown_list_values_1719231")
    @DisplayName("1719231 - Проверка выпадающего списка результата проверки «Нерезультативный прозвон» в стратегии \"Прозвон клиента\"")
    @WorkItemIds({"1719231"})
    public void checking_dropdown_list_values_1719231() {
        actionsClaimSteps.executeQuery(VERIFICATION, "select vdcsr.description from vrf_dir_check_step_result vdcsr " +
                "join vrf_dir_check_step_result_check_type vdcsrct on vdcsrct.dir_check_step_result_id = vdcsr.id " +
                "where vdcsrct.check_type_code = 'CLIENT_CALL' and dir_check_step_result_parent_id = 101;");
        List<String> valuesFromDb = actionsClaimSteps.getValuesFromResponseDb("description");

        customerCallPage
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Нерезультативный прозвон")
                .checkDropDownListElements("Выпадающий список Нерезультативный прозвон", valuesFromDb)
                .selectValueFromDropDownList("Выпадающий список Нерезультативный прозвон", "Клиент просит перезвонить")
                .checkVisibilityDropDownElement("Клиент просит перезвонить");
    }


    @Test
    @Tag("checking_dropdown_list_values_1719223")
    @DisplayName("1719223 - Проверка выпадающего списка результата проверки «Результативный прозвон» в стратегии \"Прозвон клиента\"")
    @WorkItemIds({"1719223"})
    public void checking_dropdown_list_values_1719223() {
        List<String> actualDropDownListCheckBox;
        List<String> activeListCheckBox;

        actionsClaimSteps.executeQuery(VERIFICATION, "select vdcsr.description from vrf_dir_check_step_result vdcsr " +
                "join vrf_dir_check_step_result_check_type vdcsrct on vdcsrct.dir_check_step_result_id = vdcsr.id " +
                "where vdcsrct.check_type_code = 'CLIENT_CALL' and dir_check_step_result_parent_id = 102;");
        List<String> productiveValuesFromDb = actionsClaimSteps.getValuesFromResponseDb("description");

        customerCallPage
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                .checkDropDownListElements("Выпадающий список Результативный прозвон", productiveValuesFromDb)
                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Заявка не актуальна")
                .checkVisibilityDropDownElement("Заявка не актуальна")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "")
                .assertElementByTitleVisibility("Выпадающий список Результативный прозвон", "не отображается");

        actionsClaimSteps.executeQuery(VERIFICATION, "select vdcsr.description from vrf_dir_check_step_result vdcsr " +
                "join vrf_dir_check_step_result_check_type vdcsrct on vdcsrct.dir_check_step_result_id = vdcsr.id " +
                "where vdcsrct.check_type_code = 'CLIENT_CALL' and dir_check_step_result_parent_id = 4;");
        List<String> noNegativeValuesFromDb = actionsClaimSteps.getValuesFromResponseDb("description");

        customerCallPage
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Негатив не выявлен")
                .checkDropDownListElements("Выпадающий список Негатив не выявлен", noNegativeValuesFromDb);

        actionsClaimSteps.executeQuery(VERIFICATION, "select vdcsr.description from vrf_dir_check_step_result vdcsr " +
                "join vrf_dir_check_step_result_check_type vdcsrct on vdcsrct.dir_check_step_result_id = vdcsr.id " +
                "where vdcsrct.check_type_code = 'CLIENT_CALL' and dir_check_step_result_parent_id = 7;");
        List<String> negativeValuesFromDb = actionsClaimSteps.getValuesFromResponseDb("description");

        customerCallPage
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Выявлен негатив")
                .clickOnElement("Выпадающий список Выявлен негатив");
        actualDropDownListCheckBox = customerCallPage.getListCheckBox("Выпадающий список чек-боксов Выявлен негатив");
        assertIsTrue(negativeValuesFromDb.containsAll(actualDropDownListCheckBox), "Список " + negativeValuesFromDb + " соответствует списку " + actualDropDownListCheckBox);

        customerCallPage
                .clickOnElement("Интерфейс")
                .selectValueFromDropDownList("Выпадающий список Выявлен негатив", listCheckBox);

        activeListCheckBox = customerCallPage.getListCheckBox("Список выбранных чекбоксов в разделе Выявлен негатив");
        assertIsTrue(listCheckBox.containsAll(activeListCheckBox), "Множественный выбор доступен, выбранные чек-боксы отображаются");
    }

    @Test
    @Tag("checking_dropdown_list_contactless_confirmation_1719233")
    @DisplayName("1719233 - Проверка выпадающего списка результата проверки «Бесконтакное подтверждение» в стратегии \"Прозвон клиента\"")
    @WorkItemIds({"1719233"})
    public void checking_dropdown_list_contactless_confirmation_1719233() {
        actionsClaimSteps.executeQuery(VERIFICATION, "select vdcsr.description from vrf_dir_check_step_result vdcsr " +
                "join vrf_dir_check_step_result_check_type vdcsrct on vdcsrct.dir_check_step_result_id = vdcsr.id " +
                "where vdcsrct.check_type_code = 'CLIENT_CALL' and dir_check_step_result_parent_id = 103;");
        List<String> valuesFromDb = actionsClaimSteps.getValuesFromResponseDb("description");

        customerCallPage
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Бесконтактное подтверждение")
                .checkDropDownListElements("Выпадающий список Бесконтактное подтверждение", valuesFromDb)
                .selectValueFromDropDownList("Выпадающий список Бесконтактное подтверждение", "Социальные сети")
                .assertElementByTitleVisibility("Поле ввода Источник подтверждения", "отображается");
    }

    @Test
    @Tag("checking_delete_value_1719225")
    @DisplayName("1719225 - Проверка возможности удаления выбранных значений при множественном выборе в стратегии \"Прозвон клиента\"")
    @WorkItemIds({"1719225"})
    public void checking_delete_value_1719225() {
        customerCallPage
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Выявлен негатив")
                .selectValueFromDropDownList("Выпадающий список Выявлен негатив", listCheckBox)
                .assertElementByTitleVisibility("Список выбранных чекбоксов в разделе Выявлен негатив", "отображается")

                .selectValueFromDropDownList("Выпадающий список Выявлен негатив", listCheckBox)
                .assertElementByTitleVisibility("Список выбранных чекбоксов в разделе Выявлен негатив", "не отображается")

                .selectValueFromDropDownList("Выпадающий список Выявлен негатив", listCheckBox)
                .clickOnElement("Иконка удалить Выбранный чекбокс")
                .clickOnElement("Иконка удалить Выбранный чекбокс")
                .assertElementByTitleVisibility("Список выбранных чекбоксов в разделе Выявлен негатив", "не отображается");
    }

    @Test
    @Tag("checking_routing_unsuccessful_ringing_1719226")
    @DisplayName("1719226 - Проверка маршрутизации заявки для результата проверки \"Нерезультативный прозвон\"")
    @WorkItemIds({"1719226"})
    public void checking_routing_unsuccessful_ringing_1719226() {
        List<String> values = List.of(
                "Клиент не отвечает/недоступен",
                "Клиент просит перезвонить",
                "Клиент просит перезвонить через длительный промежуток времени",
                "Отказ клиента предоставить информацию");
        customerCallPage.selectValueFromDropDownList("Выпадающий список Результат проверки", "Нерезультативный прозвон");

        for (String value : values) {
            customerCallPage
                    .selectValueFromDropDownList("Выпадающий список Нерезультативный прозвон", value)
                    .assertElementByTitleActivity("Кнопка Далее", "не активен");
        }
    }

    @Test
    @Tag("checking_routing_successful_ringing_1719232")
    @DisplayName("1719232 - Проверка маршрутизации заявки для результата проверки \"Результативный прозвон\"")
    @WorkItemIds({"1719232"})
    public void checking_routing_successful_ringing_1719232() {
        List<String> valuesCheckBox = List.of(
                "Кредит для третьего лица",
                "Кредит на бизнес",
                "От Заявителя получена информация о несвоевременной выплате заработной платы",
                "Несоответствие минимальным требованиям",
                "Клиент анкету не подписывал",
                "Финансовые трудности",
                "Анализ кредитной истории",
                "Клиент уволен / находится в стадии увольнения",
                "Негативная характеристика третьих лиц на Клиента",
                "Негатив на работодателя от клиента");
        customerCallPage
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Негатив не выявлен")
                .selectValueFromDropDownList("Выпадающий список Негатив не выявлен", "Негатив отсутствует")
                .assertElementByTitleActivity("Кнопка Далее", "активен")
                .selectValueFromDropDownList("Выпадающий список Негатив не выявлен", "Негатив отсутствует. Занятость клиента подтверждена по корпоративной почте клиента")
                .assertElementByTitleActivity("Кнопка Далее", "активен")
                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Выявлен негатив");

        for (String valueCheckBox : valuesCheckBox) {
            customerCallPage
                    .selectValueFromDropDownList("Выпадающий список Выявлен негатив", valueCheckBox)
                    .assertElementByTitleVisibility("Иконка Шаг заблокирован", "отображается")
                    .clickOnElement("Иконка удалить Выбранный чекбокс");
        }
        customerCallPage
                .selectValueFromDropDownList("Выпадающий список Выявлен негатив", "Отвечает третье лицо")
                .assertElementByTitleActivity("Кнопка Далее", "не активен")
                .assertElementByTitleVisibility("Иконка Отправить на доработку", "отображается")
                .clickOnElement("Иконка удалить Выбранный чекбокс")

                .selectValueFromDropDownList("Выпадающий список Выявлен негатив", "Клиент анкету не подписывал")
                .assertElementByTitleActivity("Кнопка Далее", "не активен")
                .assertElementByTitleVisibility("Иконка Шаг заблокирован", "отображается")
                .assertElementByTitleVisibility("Иконка Отправить на доработку", "отображается")
                .clickOnElement("Иконка удалить Выбранный чекбокс")

                .selectValueFromDropDownList("Выпадающий список Выявлен негатив", "Клиент предоставляет ложные анкетные данные")
                .assertElementByTitleActivity("Кнопка Далее", "активен")
                .assertElementByTitleVisibility("Иконка Шаг заблокирован", "отображается")
                .assertElementByTitleVisibility("Иконка Необходима проверка сотрудниками ОПМ", "отображается")
                .clickOnElement("Иконка удалить Выбранный чекбокс");
    }

    @Test
    @Tag("checking_unblocks_empty_lines_1719221")
    @DisplayName("1719221 - Проверка разблокировки шагов при выставлении пустой строки в результатах проверки")
    @WorkItemIds({"1719221"})
    public void checking_unblocks_empty_lines_1719221() {
        customerCallPage
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Выявлен негатив")
                .selectValueFromDropDownList("Выпадающий список Выявлен негатив", "Кредит для третьего лица")
                .assertElementByTitleVisibility("Иконка Шаг заблокирован", "отображается")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "")
                .assertElementByTitleVisibility("Выпадающий список Выявлен негатив", "не отображается")
                .assertElementByTitleVisibility("Выпадающий список Результативный прозвон", "не отображается");
    }

    @Test
    @Tag("checking_fixing_results_1719224")
    @DisplayName("1719224 - Проверка фиксации результатов проверок по стратегии \"Прозвон клиента\"")
    @WorkItemIds({"1719224"})
    public void checking_fixing_results_1719224() {
        customerCallPage
                .clickOnElement("Кнопка Далее")
                .waitText(2, "Для завершения шага необходимо заполнить результат проверки или результат по заявке")
                .clickOnElement("Кнопка ОК")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                .clickOnElement("Кнопка Далее")
                .waitText(2, "Пожалуйста, выберите значение")
                .clickOnElement("Кнопка ОК")
                .assertElementByTitleActivity("Кнопка Далее", "активен");
    }
}
