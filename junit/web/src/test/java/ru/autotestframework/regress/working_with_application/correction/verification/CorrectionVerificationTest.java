package ru.autotestframework.regress.working_with_application.correction.verification;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import java.util.Map;

import static ru.autotestframework.steps.asserts.Asserts.assertIsEquals;

@Tag("regress")
@Tag("correction")
@Tag("verification")
@Tag("correction_verification")
@ClassName("Корректировка. Редактирование дохода.")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CorrectionVerificationTest extends BaseTest {

    private String claim;

    @BeforeEach
    public void login() {
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi()
                    .openMenuLinks("Личный кабинет");
        }
    }

    @AfterEach
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
        loginPage.closeCurrentTab().resetFilters().openMenuLinks("Личный кабинет");
    }

    @Test
    @Tag("smoke")
    @Tag("save_value_1653402")
    @DisplayName("1653402 - Сохранение значения в поле Внутренний комментарий на Корректировке")
    @WorkItemIds({"1653402"})
    public void save_value_1653402(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3858726.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Отправить на корректировку")
                .clickOnElement("Иконка для активации поля ввода Скоррект. доход/По Осн. месту")
                .fillInput("Поле ввода Скоррект. доход/По Осн. месту", "70000")
                .fillInput("Поле ввода Внутренний комментарий", "КоментАТ")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab()
                .goTo(loginPage)
                .openMenuLinks("Поиск")
                .goTo(searchPage)
                .searchClaimOnPage(claim)
                .goTo(loginPage)
                .doubleClickByText(claim).switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Результаты проверок")
                .goTo(resultCheck)
                .clickOnElement("Вкладка Ручные проверки")
                .goTo(manualChecks)
                .clickOnElement("Кнопка Верификация назначенные проверки")
                .clickOnElement("Ссылка Открыть стратегию").switchToNewTab()
                .goTo(fsspPage)

                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .checkElementByTitleEquals("Выпадающий список Результат по заявке", "Отправить на корректировку");
        String actualValue = fsspPage.getValueByElementTitle("Поле ввода Скоррект. доход/По Осн. месту").replaceAll("\u00A0", "");
        assertIsEquals("70000", actualValue, "Поле ввода Скоррект. доход/По Осн. месту");

        cardRequestPage
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - Предыдущая версия проверки");
        assertIsEquals("КоментАТ", fsspPage.getValueByElementTitle("Поле ввода Внутренний комментарий"), "Поле ввода Внутренний комментарий");
        cardRequestPage
                .clickOnElement("Кнопка Внутренний комментарий")
                .checkElementByTitleContains("Поле История комментариев", "КоментАТ")
                .checkElementByTitleContains("Поле Информация по истории изменений", "ФССП");
    }

    @Test
    @Tag("block_step_1653415")
    @DisplayName("1653415 - Корректировка. Редактирование дохода. Блокировка других шагов после редактирования дохода до отправки на корректировку. L0. Проверка документов")
    @WorkItemIds({"1653415"})
    public void block_step_1653415(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "Code", "stub1");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1283360.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(l0CheckingDocumentsPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "L0.Проверка документов/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Отправить на корректировку")
                .clickOnElement("Иконка для активации поля ввода Скоррект. доход/По Осн. месту")
                .fillInput("Поле ввода Скоррект. доход/По Осн. месту", "50001");
        String actualValue = l0CheckingDocumentsPage.getValueByElementTitle("Поле ввода Скоррект. доход/По Осн. месту").replaceAll("\u00A0", "");
        assertIsEquals("50001", actualValue, "Поле ввода Скоррект. доход/По Осн. месту");
        l0CheckingDocumentsPage
                .clickOnElement("Шаг №2. Заёмщик. Основное место работы")
                .assertElementByTitleVisibility("Иконка Шаг №2 Заёмщик. Основное место работы - заблокирован", "отображается")
                .assertElementByTitleActivity("Кнопка Взять шаг в работу", "не активен")
                .clickOnElement("Шаг №3. Заёмщик. Совместительство")
                .assertElementByTitleVisibility("Иконка Шаг №3 Заёмщик. Совместительство - заблокирован", "отображается")
                .assertElementByTitleActivity("Кнопка Взять шаг в работу", "не активен");
    }

    @Test
    @Tag("block_step_1653393")
    @DisplayName("1653393 - Корректировка. Редактирование дохода. Блокировка других шагов после редактирования дохода до отправки на корректировку. Прозвон")
    @WorkItemIds({"1653393"})
    public void block_step_call_1653393(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "Code1", "stub11",
                "Code2", "stub12",
                "Code3", "stub13");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1337114.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон клиента/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Отправить на корректировку")
                .clickOnElement("Иконка для активации поля ввода Скоррект. доход/По Осн. месту")
                .fillInput("Поле ввода Скоррект. доход/По Осн. месту", "50001");
        String actualValue = l0CheckingDocumentsPage.getValueByElementTitle("Поле ввода Скоррект. доход/По Осн. месту").replaceAll("\u00A0", "");
        assertIsEquals("50001", actualValue, "Поле ввода Скоррект. доход/По Осн. месту");
        incomeVerificationPage
                .clickOnElement("Второй шаг на степере")
                .assertElementByTitleVisibility("Второй шаг на степере. Прозвон контактного лица/супруга - заблокирован", "отображается")
                .assertElementByTitleActivity("Кнопка Взять шаг в работу", "не активен")
                .clickOnElement("Третий шаг на степере")
                .assertElementByTitleVisibility("Третий шаг на степере. Прозвон работодателя - любой телефон - заблокирован", "отображается")
                .assertElementByTitleActivity("Кнопка Взять шаг в работу", "не активен")
                .clickOnElement("Четвертый шаг на степере")
                .assertElementByTitleVisibility("Четвертый шаг на степере. Прозвон работодателя - любой телефон (Совместительство) - заблокирован", "отображается")
                .assertElementByTitleActivity("Кнопка Взять шаг в работу", "не активен");
    }

    @Test
    @Tag("save_value_after_correct_1653394")
    @DisplayName("1653394 - Корректировка. Редактирование дохода. Сохранение значения в поле Внутренний комментарий после Корректировки")
    @WorkItemIds({"1653394"})
    public void save_value_after_correct_1653394(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_4109413.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Отправить на корректировку")
                .clickOnElement("Иконка для активации поля ввода Скоррект. доход/По Осн. месту")
                .fillInput("Поле ввода Скоррект. доход/По Осн. месту", "500001");
        String actualValue = fsspPage.getValueByElementTitle("Поле ввода Скоррект. доход/По Осн. месту").replaceAll("\u00A0", "");
        assertIsEquals("500001", actualValue, "Поле ввода Скоррект. доход/По Осн. месту");
        fsspPage
                .fillInput("Поле ввода Внутренний комментарий", "КоментАТ")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab();
        actionsClaimSteps.repeatSendSclRequestToStand("13","data/json/claim_template_4109434.json");
        personalAccountPage.openMenuLinks("Личный кабинет")
                .doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 2")
                .clickOnElement("Кнопка Внутренний комментарий")
                .checkElementByTitleContains("Поле История комментариев", "КоментАТ")
                .clickOnElement("Кнопка закрыть Окно");
    }

    @Test
    @Tag("change_color_1653400")
    @DisplayName("1653400 - Корректировка. Редактирование ИКИ. Изменение цвета ссылки после Редактирования Идеальной КИ")
    @WorkItemIds({"1653400"})
    public void change_color_1653400(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_4109413.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Отправить на корректировку")
                .assertElementByTitleVisibility("Кнопка Идеальная КИ", "отображается")
                .colorElementEquals("Кнопка Идеальная КИ", "rgba(242, 153, 74, 1)")
                .clickOnElement("Кнопка Идеальная КИ").switchToNewTab()
                .goTo(idealCiPage)
                .clickOnButtonEditCellFromTable("Таблица Участники сделки", 3, 10)
                .fillInput("Поле Ввода редактирования Ежемесячного платежа", "2500")
                .clickOnButtonEditCellFromTable("Таблица Участники сделки", 3, 10)
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Сохранить (header)").closeCurrentTab();
        fsspPage
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .assertElementByTitleVisibility("Кнопка Идеальная КИ", "отображается")
                .colorElementEquals("Кнопка Идеальная КИ", "rgba(0, 129, 86, 1)")
                .assertElementByTitleVisibility("Текст 'Отредактирована!'", "отображается")
                .colorElementEquals("Текст 'Отредактирована!'", "rgba(235, 87, 87, 1)");
    }

    @Test
    @Tag("block_button_cancel_1653410")
    @DisplayName("1653410 - Корректировка. Редактирование дохода. Блокировка кнопки Отменить после нажатия кнопки Далее")
    @WorkItemIds({"1653410"})
    public void block_button_cancel_1653410(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_4109413.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Отправить на корректировку")
                .clickOnElement("Иконка для активации поля ввода Скоррект. доход/По Осн. месту")
                .fillInput("Поле ввода Скоррект. доход/По Осн. месту", "500001");
        String actualValue = fsspPage.getValueByElementTitle("Поле ввода Скоррект. доход/По Осн. месту").replaceAll("\u00A0", "");
        assertIsEquals("500001", actualValue, "Поле ввода Скоррект. доход/По Осн. месту");
        fsspPage
                .fillInput("Поле ввода Внутренний комментарий", "КоментАТ")
                .clickOnElement("Кнопка Далее")
                .goTo(cardRequestPage)
                .assertElementByTitleActivity("Кнопка Отменить (Отправить на корректировку)", "не активен");
    }

    @Test
    @Tag("block_button_1653414")
    @DisplayName("1653414 - Корректировка. Редактирование ИКИ. Блокировка кнопок после Редактирования Идеальной КИ")
    @WorkItemIds({"1653414"})
    public void block_button_1653414(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_4109413.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Отправить на корректировку")
                .assertElementByTitleVisibility("Кнопка Идеальная КИ", "отображается")
                .colorElementEquals("Кнопка Идеальная КИ", "rgba(242, 153, 74, 1)")
                .clickOnElement("Кнопка Идеальная КИ").switchToNewTab()
                .goTo(idealCiPage)
                .clickOnButtonEditCellFromTable("Таблица Участники сделки", 3, 10)
                .fillInput("Поле Ввода редактирования Ежемесячного платежа", "2500")
                .clickOnButtonEditCellFromTable("Таблица Участники сделки", 3, 10)
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Сохранить (header)").closeCurrentTab().waitBusyCondition()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .goTo(cardRequestPage)
                .assertElementByTitleActivity("Кнопка Отложить", "не активен")
                .assertElementByTitleActivity("Кнопка Сохранить (header)", "не активен")
                .assertElementByTitleActivity("Кнопка Закрыть (header)", "не активен")
                .goTo(fsspPage)
                .assertElementByTitleActivity("Кнопка Изменить результат", "не активен")
                .assertElementByTitleBlock("Выпадающий список Результат по заявке", "заблокирован");
    }

    @Test
    @Tag("edit_after_delete_result_1653419")
    @DisplayName("1653419 - Корректировка. Редактирование ИКИ. Возможность редактировать КИ после удаления результата проверки на другом шаге. Прозвон")
    @WorkItemIds({"1653419"})
    public void edit_after_delete_result_1653419(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "Code1", "stub11",
                "Code2", "stub13");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3208874.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim).switchToNewTab().waitBusyCondition()
                .goTo(customerCallPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон клиента/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Негатив не выявлен")
                .selectValueFromDropDownList("Выпадающий список Негатив не выявлен", "Негатив отсутствует")
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Отправить на корректировку")
                .waitText(1, "Для пересчета лимита удалите указанные ранее результаты проверок!")
                .clickOnElement("Кнопка ОК")
                .clickOnStep("Прозвон клиента")
                .clickOnElement("Кнопка Изменить результат")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "")
                .clickOnStep("Прозвон работодателя - любой телефон")
                .clickOnElement("Кнопка Взять шаг в работу")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Отправить на корректировку")
                .assertElementByTitleVisibility("Кнопка Идеальная КИ", "отображается")
                .colorElementEquals("Кнопка Идеальная КИ", "rgba(242, 153, 74, 1)")
                .clickOnElement("Кнопка Идеальная КИ").switchToNewTab()
                .goTo(idealCiPage)
                .assertElementByTitleVisibility("Таблица Участники сделки", "отображается")
                .closeCurrentTab();
    }

    @Test
    @Tag("save_result_1653395")
    @DisplayName("1653395 - Корректировка. Редактирование ИКИ. Сохранение результата по заявке и блокировки кнопок после закрытия вкладки браузера. Редактирование КИ")
    @WorkItemIds({"1653395"})
    public void save_result_1653395(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_4109413.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Отправить на корректировку")
                .assertElementByTitleVisibility("Кнопка Идеальная КИ", "отображается")
                .clickOnElement("Кнопка Идеальная КИ").switchToNewTab()
                .goTo(idealCiPage)
                .clickOnButtonEditCellFromTable("Таблица Участники сделки", 3, 10)
                .fillInput("Поле Ввода редактирования Ежемесячного платежа", "2500")
                .clickOnButtonEditCellFromTable("Таблица Участники сделки", 3, 10)
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Сохранить (header)").closeCurrentTab()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .closeCurrentTab()
                .goTo(personalAccountPage)
                .openMenuLinks("Личный кабинет")
                .doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Выпадающий список Результат по заявке", "Отправить на корректировку")
                .assertElementByTitleBlock("Выпадающий список Результат по заявке", "заблокирован")
                .assertElementByTitleActivity("Кнопка Изменить результат", "не активен")
                .goTo(cardRequestPage)
                .assertElementByTitleActivity("Кнопка Отложить", "не активен")
                .assertElementByTitleActivity("Кнопка Сохранить (header)", "не активен");
    }

    @Test
    @Tag("save_block_step_1653408")
    @DisplayName("1653408 - Корректировка. Редактирование ИКИ. Сохранение блокировки других шагов после редактирования КИ и закрытия вкладки браузера")
    @WorkItemIds({"1653408"})
    public void save_block_step_1653408(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "codeBor", "stub1",
                "codeCobor", "stub1");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1316550.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Отправить на корректировку")
                .assertElementByTitleVisibility("Кнопка Идеальная КИ", "отображается")
                .clickOnElement("Кнопка Идеальная КИ").switchToNewTab()
                .goTo(idealCiPage)
                .clickOnButtonEditCellFromTable("Таблица Участники сделки", 3, 10)
                .fillInput("Поле Ввода редактирования Ежемесячного платежа", "2500")
                .clickOnButtonEditCellFromTable("Таблица Участники сделки", 3, 10)
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Сохранить (header)").closeCurrentTab()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .closeCurrentTab()
                .goTo(personalAccountPage)
                .openMenuLinks("Личный кабинет")
                .doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .clickOnElement("Шаг №2. Созаемщик")
                .assertElementByTitleVisibility("Иконка Шаг заблокирован", "отображается")
                .assertElementByTitleBlock("Выпадающий список Результат проверки", "заблокирован");
    }

    @Test
    @Tag("edit_IKI_save_value_1653407")
    @DisplayName("1653407 - Корректировка. Редактирование ИКИ. Сохранение значения в поле Внутренний комментарий после Корректировки. ФССП")
    @WorkItemIds({"1653407"})
    public void edit_IKI_save_value_1653407(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_4109413.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Отправить на корректировку")
                .assertElementByTitleVisibility("Кнопка Идеальная КИ", "отображается")
                .clickOnElement("Кнопка Идеальная КИ").switchToNewTab()
                .goTo(idealCiPage)
                .clickOnButtonEditCellFromTable("Таблица Участники сделки", 3, 10)
                .fillInput("Поле Ввода редактирования Ежемесячного платежа", "2500")
                .clickOnButtonEditCellFromTable("Таблица Участники сделки", 3, 10)
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Сохранить (header)").closeCurrentTab();
        fsspPage
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .fillInput("Поле ввода Внутренний комментарий", "КоментАТ")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab();
        actionsClaimSteps.repeatSendSclRequestToStand("13","data/json/claim_template_4109434.json");
        personalAccountPage.openMenuLinks("Личный кабинет")
                .doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 2")
                .clickOnElement("Кнопка Внутренний комментарий")
                .checkElementByTitleContains("Поле История комментариев", "КоментАТ")
                .clickOnElement("Кнопка закрыть Окно");
    }

    @Test
    @Tag("update_check_FSSP_1720488")
    @DisplayName("1720488 - Обновление проверок ФССП после Корректировки")
    @WorkItemIds({"1720488"})
    public void update_check_FSSP_1720488(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "firstNameBor", "Дмитрий",
                "previousFirstNameBor", "Ираклий",
                "birthDateBor", "1990-02-02",
                "contactPersonFullName", "Иванов Юрий Петрович",
                "Code1", "stub1",
                "Code2", "stub7");

        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1335371.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Исполнительное производство не найдено")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab();
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.doubleClickByText(claim).switchToNewTab()
                .goTo(checkingOpenSourcesPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка открытых источников/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Отправить на корректировку")
                .fillInput("Поле ввода Внутренний комментарий", "КоментАТ")
                .clickOnElement("Иконка для активации поля ввода Скоррект. доход/По Осн. месту")
                .fillInput("Поле ввода Скоррект. доход/По Осн. месту", "50001");
        String actualValue = checkingOpenSourcesPage.getValueByElementTitle("Поле ввода Скоррект. доход/По Осн. месту").replaceAll("\u00A0", "");
        assertIsEquals("50001", actualValue, "Поле ввода Скоррект. доход/По Осн. месту");
        checkingOpenSourcesPage
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab();
        actionsClaimSteps.repeatSendSclRequestToStand("13","data/json/claim_template_4140548.json");

        personalAccountPage.openMenuLinks("Личный кабинет")
                .doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 2")
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "");
    }

    @Test
    @Tag("save_result_check_1720489")
    @DisplayName("1720489 - Сохранение результатов проверок в группе Открытые источники после Корректировки")
    @WorkItemIds({"1720489"})
    public void save_result_check_1720489(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "firstNameBor", "Дмитрий",
                "previousFirstNameBor", "Ираклий",
                "birthDateBor", "1990-02-02",
                "contactPersonFullName", "Иванов Юрий Петрович",
                "Code1", "stub7",
                "Code2", "stub11");

        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1335371.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.doubleClickByText(claim).switchToNewTab()
                .goTo(checkingOpenSourcesPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка открытых источников/Версия 1")
                .clickOnElement("Чек-бокс Негатив по работодателю не выявлен")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Чек-бокс Негатив по работодателю не выявлен")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку").switchToOneTab().waitBusyCondition();
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.doubleClickByText(claim).switchToNewTab()
                .goTo(customerCallPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон клиента/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Отправить на корректировку")
                .fillInput("Поле ввода Внутренний комментарий", "КоментАТ")
                .clickOnElement("Иконка для активации поля ввода Скоррект. доход/По Осн. месту")
                .fillInput("Поле ввода Скоррект. доход/По Осн. месту", "50001");
        String actualValue = checkingOpenSourcesPage.getValueByElementTitle("Поле ввода Скоррект. доход/По Осн. месту").replaceAll("\u00A0", "");
        assertIsEquals("50001", actualValue, "Поле ввода Скоррект. доход/По Осн. месту");
        checkingOpenSourcesPage
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab();
        actionsClaimSteps.repeatSendSclRequestToStand("13","data/json/claim_template_4140548_1.json");
        personalAccountPage.openMenuLinks("Личный кабинет")
                .doubleClickByText(claim).switchToNewTab()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон клиента/Версия 2")
                .clickOnElement("Кнопка Основные данные").switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Результаты проверок").switchToNewTab()
                .goTo(resultCheck)
                .clickOnElement("Вкладка Ручные проверки")
                .goTo(manualChecks)
                .clickOnElement("Кнопка Верификация назначенные проверки")
                .clickOnCellFromTable("Таблица Верификация назначенные проверки", 1, 4, "Открыть стратегию").switchToNewTab().waitBusyCondition()
                .goTo(checkingOpenSourcesPage)
                .assertElementByTitleSelected("Чек-бокс Негатив по работодателю не выявлен", "выбран")
                .clickOnElement("Шаг №2. Заёмщик. Совместительство")
                .assertElementByTitleSelected("Чек-бокс Негатив по работодателю не выявлен", "выбран")
                .closeCurrentTab()
                .closeCurrentTab();
    }
}
