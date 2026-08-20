package ru.autotestframework.regress.working_with_application.postponement.underwriting;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.*;
import ru.psb.testit.annotations.DisplayName;

import java.time.LocalDateTime;
import java.util.List;

import static ru.autotestframework.steps.actions.BaseActions.*;
import static ru.autotestframework.steps.asserts.Asserts.assertIsEquals;
import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;
import static ru.autotestframework.utils.Constants.*;

@Tag("regress")
@Tag("no_check_verification")
@Tag("working_with_application")
@Tag("postponement")
@Tag("underwriting")
@Tag("on_claim_1_postponement_underwriting")
@ClassName("Андеррайтинг. На заявке №1 Откладывание Андеррайтинг")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OnClaim1PostponementUnderwritingTest extends BaseTest {

    String claim;

    @BeforeEach
    public void login(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3252301.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi()
                    .openMenuLinks("Личный кабинет")
                    .goTo(personalAccountPage)
                    .clickOnElement("Раздел Андеррайтинг");
        }
    }

    @AfterEach
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }


    @Test
    @Tag("smoke")
    @Tag("transfer_to_postponed_review_stage_1723090")
    @DisplayName("1723090 - Перевод в отложенные с причиной \"Конт. лицо/супруг(а) просит перезвонить\". Этап Рассмотрение. ")
    @WorkItemIds({"1723090"})
    public void transfer_to_postponed_review_stage_1723090() {
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Отложить")
                .assertElementByTitleVisibility("Модальное окно Перевод заявки в отложенные", "отображается")
                .selectValueFromDropDownList("Выпадающий список Причина (Перевод заявки в отложенные)", "Конт. лицо/супруг(а) просит перезвонить")
                .fillInput("Поле ввода комментарий (Перевод заявки в отложенные)", "коммент звонок")
                .assertElementByTitleActivity("Кнопка Отложить заявку", "не активен");
        String time = LocalDateTime.now().plusMinutes(7).format(DF);
        cardRequestPage.fillInput("Поле ввода Время для звонка участнику", time)
                .clickOnElement("Кнопка Отложить заявку")
                .goTo(personalAccountPage)
                .switchToOneTab()
                .waitBusyCondition()
                .clickOnElement("Кнопка раскрыть таблицу Отложено");
        String statusClaim = personalAccountPage.getTextFromTable("Таблица Отложено", 1, "Статус заявки");
        assertIsTrue(statusClaim.equals("Отложена (рассмотрение)"),
                "Значение в столбце Статус заявки должно быть равно Отложена (рассмотрение). Фактическое значение: " + statusClaim);
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка История")
                .goTo(historyPage)
                .waitBusyCondition();
        List<String> list = historyPage.getListValuesByColumnName("Таблица История заявки", "Тип события");
        int row = list.indexOf("Пользователь отложил заявку в очередь отложенных");
        assertIsEquals("Пользователь Автоматическое Тестирование1 отложил заявку " + claim + " в очередь отложенных " +
                        "по причине \"Конт. лицо/супруг(а) просит перезвонить\" в статус \"Отложена (рассмотрение)\" до \"" + time + "\"",
                historyPage.getTextFromTable("Таблица История заявки", row + 1, "Описание события"),
                "Значение столбца \"Описание события\" у события с типом " + list.get(row));
        cardRequestPage.closeCurrentTab()
                .goTo(personalAccountPage)
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Взять в работу")
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "На рассмотрении")
                .clickOnElement("Кнопка Вернуть в очередь")
                .assertElementByTitleVisibility("Модальное окно Возвращение заявки в очереди", "отображается")
                .clickOnElement("Кнопка Да на модальном окне Возвращение заявки в очереди")
                .goTo(personalAccountPage)
                .switchToOneTab()
                .checkNotifications();
    }

    @Test
    @Tag("smoke")
    @Tag("to_claim_from_notification_3253543")
    @DisplayName("3253543 - Переход на заявку из напоминания")
    @WorkItemIds({"3253543"})
    public void to_claim_from_notification_3253543() {
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Отложить")
                .assertElementByTitleVisibility("Модальное окно Перевод заявки в отложенные", "отображается")
                .selectValueFromDropDownList("Выпадающий список Причина (Перевод заявки в отложенные)", "Недозвон Заемщику")
                .checkElementByTitleContains("Выпадающий список Участник сделки", "Романов Юрий Иванович")
                .assertElementByTitleNotAvailableEditing("Выпадающий список Участник сделки", "не доступен для редактирования")
                .fillInput("Поле ввода комментарий (Перевод заявки в отложенные)", "коммент звонок");
        String time = LocalDateTime.now().plusMinutes(11).format(DF);
        cardRequestPage.fillInput("Поле ввода Время для звонка участнику", time)
                .clickOnElement("Кнопка Отложить заявку")
                .goTo(personalAccountPage)
                .switchToOneTab()
                .waitBusyCondition()
                .clickOnElement("Кнопка раскрыть таблицу Отложено");
        String statusClaim = personalAccountPage.getTextFromTable("Таблица Отложено", 1, "Статус заявки");
        assertIsTrue(statusClaim.equals("Отложена (рассмотрение)"),
                "Значение в столбце Статус заявки должно быть равно Отложена (рассмотрение). Фактическое значение: " + statusClaim);
        personalAccountPage.clickOnElement("Кнопка раскрыть таблицу Отложено")
                .waitText(120, "Напоминание о предстоящем звонке")
                .colorElementEquals("Иконка Колокольчик на модальном окне Напоминание о предстоящем звонке", "rgba(39, 174, 96, 1)")
                .clickOnElement("Поле Номер заявки")
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Взять в работу")
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "На рассмотрении")
                .closeCurrentTab()
                .goTo(personalAccountPage)
                .switchToOneTab()
                .checkNotifications();
    }

    @Test
    @Tag("open_modal_widow_1723080")
    @DisplayName("1723080 - Открытие модального окна")
    @WorkItemIds({"1723080"})
    public void open_modal_widow_1723080() {
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Отложить")
                .assertElementByTitleVisibility("Модальное окно Перевод заявки в отложенные", "отображается")
                .assertElementByTitleVisibility("Выпадающий список Причина (Перевод заявки в отложенные)", "отображается")
                .assertElementByTitleVisibility("Поле ввода комментарий (Перевод заявки в отложенные)", "отображается")
                .assertElementByTitleVisibility("Поле ввода Время для звонка участнику", "отображается")
                .assertElementByTitleVisibility("Выпадающий список Участник сделки", "отображается")
                .assertElementByTitleVisibility("Кнопка Отложить заявку", "отображается")
                .assertElementByTitleVisibility("Кнопка Отменить Перевод заявки в отложенные", "отображается")
                .clickOnElement("Кнопка закрыть модальное окно (крестик)")
                .closeCurrentTab()
                .goTo(personalAccountPage)
                .switchToOneTab();
    }

    @Test
    @Tag("manual_return_1723070")
    @DisplayName("1723070 - Ручной возврат заявки из статуса \"Отложена\" (рассмотрение)\"")
    @WorkItemIds({"1723070"})
    public void manual_return_1723070() {
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Отложить")
                .selectValueFromDropDownList("Выпадающий список Причина (Перевод заявки в отложенные)", "Антифрод")
                .assertElementByTitleVisibility("Выпадающий список Участник сделки", "не отображается");
        String time = LocalDateTime.now().plusMinutes(11).format(DF);
        cardRequestPage.fillInput("Поле ввода Время возврата заявки", time)
                .clickOnElement("Кнопка Отложить заявку").waitBusyCondition();
        actionsClaimSteps.checkStatusClaimFromDb(claim, 27);
        personalAccountPage
                .switchToOneTab()
                .waitBusyCondition()
                .clickOnElement("Кнопка раскрыть таблицу Отложено");
        String statusClaim = personalAccountPage.getTextFromTable("Таблица Отложено", 1, "Статус заявки");
        assertIsTrue(statusClaim.equals("Отложена (рассмотрение)"),
                "Значение в столбце Статус заявки должно быть равно Отложена (рассмотрение). Фактическое значение: " + statusClaim);
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(cardRequestPage)
                .assertElementByTitleActivity("Кнопка Вернуть в очередь", "не активен")
                .clickOnElement("Кнопка Взять в работу").waitBusyCondition()
                .assertElementByTitleActivity("Кнопка Вернуть в очередь", "активен")
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - На рассмотрении");
        actionsClaimSteps.checkStatusClaimFromDb(claim, 4);
        cardRequestPage
                .closeCurrentTab()
                .goTo(personalAccountPage)
                .switchToOneTab();
    }

    @Test
    @Tag("auto_return_1723087")
    @DisplayName("1723087 - Автоматический возврат заявки \"Отложена (на рассмотрении)\" из отложенных")
    @WorkItemIds({"1723087"})
    public void auto_return_1723087() {
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Отложить")
                .selectValueFromDropDownList("Выпадающий список Причина (Перевод заявки в отложенные)", "Вопрос в ГО");
        String time = LocalDateTime.now().plusMinutes(6).format(DF);
        cardRequestPage.fillInput("Поле ввода Время возврата заявки", time)
                .clickOnElement("Кнопка Отложить заявку").waitBusyCondition()
                .goTo(personalAccountPage)
                .switchToOneTab()
                .clickOnElement("Кнопка раскрыть таблицу Отложено");
        assertIsTrue(personalAccountPage.getTextFromTable("Таблица Отложено", 1, "Статус заявки").
                        equals("Отложена (рассмотрение)"),
                "Значение в столбце Статус заявки должно быть равно Отложено (рассмотрение)");
        personalAccountPage
                .waitText(120, "Уведомление").waitBusyCondition();
        assertIsTrue(personalAccountPage.getTextFromTable("Таблица в работе", 1, "Статус заявки").
                        equals("На рассмотрении"),
                "Значение в столбце Статус заявки должно быть равно На рассмотрении");
        personalAccountPage
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Вернуть в очередь")
                .checkElementByTitleContains("Модальное окно предупреждения", "Заявка будет возвращена в очередь!\n" +
                        "Выполненные изменения будут утеряны. Да\\Нет?")
                .clickOnElement("Кнопка Да модального окна").switchToOneTab().waitBusyCondition();
        actionsClaimSteps.checkStatusClaimFromDb(claim, 3);
    }

    @Test
    @Tag("auto_return_1723082")
    @DisplayName("1723082 - Перевод в отложенные с причиной \"Заемщик просит перезвонить\". Этап Рассмотрение. + Автоматический возврат из отложенных.")
    @WorkItemIds({"1723082"})
    public void auto_return_1723082() {
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Отложить")
                .selectValueFromDropDownList("Выпадающий список Причина (Перевод заявки в отложенные)", "Заемщик просит перезвонить")
                .checkElementByTitleContains("Выпадающий список Участник сделки", "Романов Юрий Иванович")
                .assertElementByTitleNotAvailableEditing("Выпадающий список Участник сделки", "не доступен для редактирования");

        String time = LocalDateTime.now().plusMinutes(6).format(DF);
        cardRequestPage.fillInput("Поле ввода Время для звонка участнику", time)
                .clickOnElement("Кнопка Отложить заявку").waitBusyCondition()
                .goTo(personalAccountPage)
                .switchToOneTab()
                .clickOnElement("Кнопка раскрыть таблицу Отложено");
        assertIsTrue(personalAccountPage.getTextFromTable("Таблица Отложено", 1, "Статус заявки").
                        equals("Отложена (рассмотрение)"),
                "Значение в столбце Статус заявки должно быть равно Отложена (рассмотрение)");
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка История")
                .goTo(historyPage)
                .waitBusyCondition();
        List<String> list = historyPage.getListValuesByColumnName("Таблица История заявки", "Тип события");
        int row = list.indexOf("Пользователь отложил заявку в очередь отложенных");
        assertIsEquals("Пользователь Автоматическое Тестирование1 отложил заявку " + claim + " в очередь отложенных " +
                        "по причине \"Заемщик просит перезвонить\" в статус \"Отложена (рассмотрение)\" до \"" + time + "\"",
                historyPage.getTextFromTable("Таблица История заявки", row + 1, "Описание события"),
                "Значение столбца \"Описание события\" у события с типом " + list.get(row));
        cardRequestPage
                .waitText(120, "Уведомление").waitBusyCondition()
                .closeCurrentTab()
                .goTo(personalAccountPage)
                .switchToOneTab()
                .goTo(loginPage)
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim);
        assertIsTrue(queuesPage.getTextFromTable("Таблица Очереди", 1, "Статус заявки").
                        equals("На рассмотрении"),
                "Значение в столбце Статус заявки должно быть На рассмотрении");
        assertIsTrue(queuesPage.getTextFromTable("Таблица Очереди", 1, "Владелец блокировки").
                        equals("Автоматическое Тестирование1"),
                "Значение в столбце Владелец блокировки должно быть Автоматическое Тестирование1");
        queuesPage.resetFilters();
    }

    @Test
    @Tag("reminder_list_3253404")
    @DisplayName("3253404 - Список напоминаний")
    @WorkItemIds({"3253404"})
    public void reminder_list_3253404() {
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Отложить")
                .selectValueFromDropDownList("Выпадающий список Причина (Перевод заявки в отложенные)", "Недозвон Заемщику")
                .checkElementByTitleContains("Выпадающий список Участник сделки", "Романов Юрий Иванович")
                .fillInput("Поле ввода комментарий (Перевод заявки в отложенные)", "коммент звонок");
        String time = LocalDateTime.now().plusMinutes(11).format(DF);
        cardRequestPage.fillInput("Поле ввода Время для звонка участнику", time)
                .clickOnElement("Кнопка Отложить заявку").waitBusyCondition().switchToOnetab()
                .waitText(120, "Напоминание о предстоящем звонке");
        String actualText = cardRequestPage.getTextByElementTitle("Описание окна Напоминание о предстоящем звонке");
        assertIsEquals("Заявка " + claim + ", заёмщик Романов Юрий Иванович.\n" +
                        "Статус заявки Отложена (рассмотрение).\n" +
                        "Причина: Недозвон Заемщику.\n" +
                        "Комментарий: коммент звонок.",
                actualText,
                "Модальное окно Напоминание о предстоящем звонке :" + actualText);
        cardRequestPage
                .clickOnElement("Кнопка ОК - Модальное окно Напоминание о предстоящем звонке")
                .assertElementByTitleVisibility("Модальное окно Напоминание о предстоящем звонке", "отображается")
                .clickOnElement("Кнопка колокол")
                .clickOnElement("Раздел Звонки");
        String actualTextBell = cardRequestPage.getTextByElementTitle("Описание окна Напоминание о предстоящем звонке (В разделе Уведомлений)");
        assertIsEquals("Заявка " + claim + ", заёмщик Романов Юрий Иванович.\n" +
                        "Статус заявки Отложена (рассмотрение).\n" +
                        "Причина: Недозвон Заемщику.\n" +
                        "Комментарий: коммент звонок.",
                actualTextBell,
                "Модальное окно Напоминание о предстоящем звонке :" + actualTextBell);
        BODY.click();
        personalAccountPage
                .clickOnElement("Раздел Андеррайтинг")
                .clickOnElement("Кнопка раскрыть таблицу Отложено")
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка колокол")
                .clickOnElement("Раздел Звонки");
        String actualTextBell1 = cardRequestPage.getTextByElementTitle("Описание окна Напоминание о предстоящем звонке (В разделе Уведомлений)");
        assertIsEquals("Заявка " + claim + ", заёмщик Романов Юрий Иванович.\n" +
                        "Статус заявки Отложена (рассмотрение).\n" +
                        "Причина: Недозвон Заемщику.\n" +
                        "Комментарий: коммент звонок.",
                actualTextBell1,
                "Модальное окно Напоминание о предстоящем звонке :" + actualTextBell1);
        BODY.click();
        cardRequestPage.checkNotifications()
                .goTo(loginPage)
                .openMenuLinks("Личный кабинет")
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Андеррайтинг");
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "3254663; Список Уведомлений; Кнопка Закрыть модальное окно Уведомление",
            "3254666; Действие кнопки \"Не показывать все уведомления\"; Кнопка Не показывать все уведомления"
    })
    @Tag("test_3254663_3254666")
    @DisplayName("{id} - {displayName}")
    @WorkItemIds({"{id}"})
    public void test_3254663_3254666(String id, String displayName, String closeButton) {
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Отложить")
                .selectValueFromDropDownList("Выпадающий список Причина (Перевод заявки в отложенные)", "Вопрос в ГО");
        String time = LocalDateTime.now().plusMinutes(6).format(DF);
        cardRequestPage.fillInput("Поле ввода Время возврата заявки", time)
                .clickOnElement("Кнопка Отложить заявку").waitBusyCondition().switchToOnetab()
                .waitText(120, "Уведомление");
        String actualTextBell = cardRequestPage.getTextByElementTitle("Описание окна Уведомление (В разделе Уведомлений)");
        assertIsEquals("Из «отложенных» возвращена заявка " + claim + ", заёмщик Романов Юрий Иванович.\n" +
                        "Статус заявки На рассмотрении.\n" +
                        "Причина: Вопрос в ГО.\n" +
                        "Комментарий: .",
                actualTextBell,
                "Описание окна Уведомление (В разделе Уведомлений) :" + actualTextBell);
        cardRequestPage
                .clickOnElement("Кнопка ОК - Модальное окно Уведомление")
                .clickOnElement("Кнопка колокол")
                .clickOnElement("Раздел Уведомления")
                .clickOnElement(closeButton).waitBusyCondition()
                .assertElementByTitleVisibility("Модальное окно Уведомление", "не отображается");
        BODY.click();
    }

    @Test
    @Tag("dont_show_all_calls_3253515")
    @DisplayName("3253515 - Действие кнопки \"Не показывать все звонки\"")
    @WorkItemIds({"3253515"})
    public void dont_show_all_calls_3253515() {
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Отложить")
                .selectValueFromDropDownList("Выпадающий список Причина (Перевод заявки в отложенные)", "Недозвон Заемщику")
                .checkElementByTitleContains("Выпадающий список Участник сделки", "Романов Юрий Иванович")
                .assertElementByTitleNotAvailableEditing("Выпадающий список Участник сделки", "не доступен для редактирования")
                .fillInput("Поле ввода комментарий (Перевод заявки в отложенные)", "коммент звонок");
        String time = LocalDateTime.now().plusMinutes(11).format(DF);
        cardRequestPage.fillInput("Поле ввода Время для звонка участнику", time)
                .clickOnElement("Кнопка Отложить заявку").waitBusyCondition().switchToOnetab()
                .waitText(120, "Напоминание о предстоящем звонке")
                .clickOnElement("Кнопка ОК - Модальное окно Напоминание о предстоящем звонке")
                .assertElementByTitleVisibility("Модальное окно Напоминание о предстоящем звонке", "отображается")
                .clickOnElement("Кнопка колокол")
                .clickOnElement("Раздел Звонки")
                .clickOnElement("Кнопка Не показывать все звонки")
                .assertElementByTitleVisibility("Модальное окно Напоминание о предстоящем звонке", "не отображается");
        BODY.click();
        personalAccountPage
                .clickOnElement("Кнопка раскрыть таблицу Отложено")
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Взять в работу")
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "На рассмотрении")
                .closeCurrentTab()
                .goTo(personalAccountPage)
                .switchToOneTab();
    }

    @Test
    @Tag("color_bell_3253469")
    @DisplayName("3253469 - Цветовое обозначение напоминаний.")
    @WorkItemIds({"3253469"})
    public void color_bell_3253469() {
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Отложить")
                .assertElementByTitleVisibility("Модальное окно Перевод заявки в отложенные", "отображается")
                .selectValueFromDropDownList("Выпадающий список Причина (Перевод заявки в отложенные)", "Недозвон Заемщику")
                .checkElementByTitleContains("Выпадающий список Участник сделки", "Романов Юрий Иванович")
                .assertElementByTitleNotAvailableEditing("Выпадающий список Участник сделки", "не доступен для редактирования")
                .fillInput("Поле ввода комментарий (Перевод заявки в отложенные)", "коммент звонок");
        String time = LocalDateTime.now().plusMinutes(1).format(DF);
        cardRequestPage.fillInput("Поле ввода Время для звонка участнику", time)
                .clickOnElement("Кнопка Отложить заявку").switchToOneTab().waitBusyCondition()
                .goTo(personalAccountPage)
                .waitText(120, "Уведомление")
                .clickOnElement("Кнопка ОК - Модальное окно Уведомление")
                .assertElementByTitleVisibility("Модальное окно Уведомление", "не отображается")
                .assertElementByTitleVisibility("Модальное окно Напоминание о предстоящем звонке", "отображается")
                .colorElementEquals("Иконка Колокольчик на модальном окне Напоминание о предстоящем звонке", "rgba(39, 174, 96, 1)");
        sleep(120);
        refreshPage();
        cardRequestPage
                .colorElementEquals("Иконка Колокольчик на модальном окне Напоминание о предстоящем звонке", "rgba(245, 34, 45, 1)")
                .clickOnElement("Кнопка ОК - Модальное окно Напоминание о предстоящем звонке")
                .clickOnElement("Кнопка колокол")
                .clickOnElement("Раздел Звонки")
                .assertElementByTitleVisibility("Модальное окно Напоминание о предстоящем звонке", "отображается")
                .colorElementEquals("Иконка Колокольчик на модальном окне Напоминание о предстоящем звонке", "rgba(245, 34, 45, 1)")
                .clickOnElement("Кнопка Закрыть модальное окно (В Разделе уведомления)")
                .assertElementByTitleVisibility("Модальное окно Напоминание о предстоящем звонке", "не отображается")
                .clickOnElement("Раздел Уведомления")
                .clickOnElement("Кнопка Закрыть модальное окно (В Разделе уведомления)")
                .assertElementByTitleVisibility("Модальное окно Уведомление", "не отображается");
        BODY.click();

        loginPage
                .openMenuLinks("Личный кабинет")
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Андеррайтинг");
    }
}