package ru.autotestframework.regress.working_with_application.return_to_queue.separate_application;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import static ru.autotestframework.steps.asserts.Asserts.assertIsEquals;
import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;

@Tag("regress")
@Tag("working_with_application")
@Tag("return_to_queue")
@ClassName("Работа с заявкой. Вернуть в очередь. На каждый кейс отдельная заявка. Вернуть в очередь")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InformationWindowReturnToQueueTest extends BaseTest {

    String claim;

    @BeforeEach
    public void login(TestInfo testInfo) {
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi();
        }
        loginPage.openMenuLinks("Личный кабинет");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2056882.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);;
    }

    @AfterEach
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("information_windows_approve_save_3251392")
    @DisplayName("3251392 - Информационное окно \"Заявка будет возвращена в очередь!\".  Результат по заявке - Одобрить. Изменения сохранены")
    @WorkItemIds({"3251392"})
    public void information_windows_approve_save_3251392() {

        personalAccountPage
                .clickOnElement("Раздел Верификация")
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Одобрить")
                .selectValueFromDropDownList("Выпадающий список Тип одобрения", "Одобрено")
                .fillInput("Поле ввода Внутренний комментарий", "Комментарий АТ")
                .goTo(cardRequestPage)
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - На рассмотрении")
                .assertElementByTitleActivity("Кнопка Вернуть в очередь", "активен")
                .clickOnElement("Кнопка Вернуть в очередь")
                .checkElementByTitleContains("Модальное окно предупреждения", "Заявка будет возвращена в очередь!\n" +
                        "Выполненные изменения будут утеряны. Да\\Нет?")
                .clickOnElement("Кнопка Нет модального окна").switchToOneTab().waitBusyCondition()
                .goTo(loginPage)
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim);
        assertIsTrue(queuesPage.getTextFromTable("Таблица Очереди", 1, "Владелец блокировки").
                        equals(""),
                "Значение в столбце Владелец блокировки должно быть пустым");
        assertIsTrue(queuesPage.getTextFromTable("Таблица Очереди", 1, "Статус заявки").
                        equals("Ожидает"),
                "Значение в столбце Статус заявки должно быть Ожидает");
        personalAccountPage
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .checkElementByTitleEquals("Выпадающий список Результат по заявке", "Одобрить")
                .checkElementByTitleEquals("Выпадающий список Тип одобрения", "Одобрено");
        assertIsEquals("Комментарий АТ", fsspPage.getValueByElementTitle("Поле ввода Внутренний комментарий"), "Поле ввода Внутренний комментарий");
        queuesPage
                .closeCurrentTab().resetFilters();
    }

    @Test
    @Tag("information_windows_approve_not_save_3251402")
    @DisplayName("3251402 - Информационное окно \"Заявка будет возвращена в очередь!\".  Результат по заявке - Одобрить стратегию. Изменения не сохранены")
    @WorkItemIds({"3251402"})
    public void information_windows_approve_not_save_3251402() {

        personalAccountPage
                .clickOnElement("Раздел Верификация")
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Одобрить стратегию")
                .fillInput("Поле ввода Внутренний комментарий", "Комментарий АТ")
                .goTo(cardRequestPage)
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - На рассмотрении")
                .assertElementByTitleActivity("Кнопка Вернуть в очередь", "активен")
                .clickOnElement("Кнопка Вернуть в очередь")
                .checkElementByTitleContains("Модальное окно предупреждения", "Заявка будет возвращена в очередь!\n" +
                        "Выполненные изменения будут утеряны. Да\\Нет?")
                .clickOnElement("Кнопка Да модального окна").switchToOneTab().waitBusyCondition()
                .goTo(loginPage)
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim);
        assertIsTrue(queuesPage.getTextFromTable("Таблица Очереди", 1, "Владелец блокировки").
                        equals(""),
                "Значение в столбце Владелец блокировки должно быть пустым");
        assertIsTrue(queuesPage.getTextFromTable("Таблица Очереди", 1, "Статус заявки").
                        equals("Ожидает"),
                "Значение в столбце Статус заявки должно быть Ожидает");
        personalAccountPage
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .checkElementByTitleEquals("Выпадающий список Результат по заявке", "")
                .assertElementByTitleVisibility("Поле ввода Внутренний комментарий", "не отображается")
                .goTo(queuesPage)
                .closeCurrentTab().resetFilters();
    }

    @Test
    @Tag("information_windows_approve_not_save_3251402")
    @DisplayName("3251405 - Информационное окно \"Заявка будет возвращена в очередь!\".  Результат по заявке - Отказать. Изменения всё равно сохраняются (сохранение по кнопке \"Далее\").")
    @WorkItemIds({"3251405"})
    public void information_windows_refuse_save_3251402() {

        personalAccountPage
                .clickOnElement("Раздел Верификация")
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(cardRequestPage)
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - На рассмотрении")
                .assertElementByTitleActivity("Кнопка Вернуть в очередь", "активен")
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Отказать")
                .selectValueFromDropDownList("Выпадающий список Причина отклонения", "Негатив на Клиента")
                .fillInput("Поле ввода Внутренний комментарий", "Комментарий АТ")
                .clickOnElement("Кнопка Далее")
                .assertElementByTitleVisibility("Кнопка Завершить проверку","отображается")
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Вернуть в очередь")
                .checkElementByTitleContains("Модальное окно предупреждения", "Заявка будет возвращена в очередь!\n" +
                        "Выполненные изменения будут утеряны. Да\\Нет?")
                .clickOnElement("Кнопка Да модального окна").switchToOneTab().waitBusyCondition()
                .goTo(loginPage)
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim);
        assertIsTrue(queuesPage.getTextFromTable("Таблица Очереди", 1, "Владелец блокировки").
                        equals(""),
                "Значение в столбце Владелец блокировки должно быть пустым");
        assertIsTrue(queuesPage.getTextFromTable("Таблица Очереди", 1, "Статус заявки").
                        equals("Ожидает"),
                "Значение в столбце Статус заявки должно быть Ожидает");
        personalAccountPage
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .checkElementByTitleEquals("Выпадающий список Результат по заявке", "Отказать")
                .checkElementByTitleEquals("Выпадающий список Причина отклонения", "Негатив на Клиента");
        assertIsEquals("Комментарий АТ", fsspPage.getValueByElementTitle("Поле ввода Внутренний комментарий"), "Поле ввода Внутренний комментарий");
        queuesPage
                .closeCurrentTab().resetFilters();
    }
}