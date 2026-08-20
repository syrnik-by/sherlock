package ru.autotestframework.regress.working_with_application.return_to_queue.separate_application;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.*;
import ru.psb.testit.annotations.DisplayName;

import static ru.autotestframework.steps.asserts.Asserts.assertIsEquals;
import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;
import static ru.autotestframework.utils.Constants.REQUESTS;

@Tag("regress")
@Tag("working_with_application")
@Tag("return_to_queue")
@Tag("no_check_verification")
@ClassName("Работа с заявкой. Вернуть в очередь. На каждый кейс отдельная заявка. Вернуть в очередь")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StatementSeparateApplicationReturnQueueTest extends BaseTest {

    String claim;

    @BeforeEach
    public void login() {
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi();
        }
    }

    @AfterEach
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("button_return_queue_active_3244235")
    @DisplayName("3244235 - Утверждение: Кнопка \"Вернуть в очередь\" - Заявка переходит в общую очередь. Пользователь - Владелец блокировки")
    @WorkItemIds({"3244235"})
    public void button_return_queue_active_3244235(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2057991.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        transferApplicationStatusAwaitingApproval();
        personalAccountPage
                .clickOnElement("Раздел Утверждение")
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(cardRequestPage)
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - Ожидает утверждения")
                .clickOnElement("Кнопка Взять в работу")
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - На утверждении")
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
                        equals("Ожидает утверждения"),
                "Значение в столбце Статус заявки должно быть Ожидает утверждения");
        queuesPage.resetFilters();
    }

    @Test
    @Tag("button_return_queue_active_3244237")
    @DisplayName("3244237 - Утверждение: Кнопка \"Вернуть в очередь\" - Заявка переходит в общую очередь. Пользователь - НЕ Владелец блокировки")
    @WorkItemIds({"3244237"})
    public void button_return_queue_active_3244237(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2057991.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        transferApplicationStatusAwaitingApproval();
        personalAccountPage
                .clickOnElement("Раздел Утверждение")
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(cardRequestPage)
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - Ожидает утверждения")
                .clickOnElement("Кнопка Взять в работу")
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - На утверждении")
                .closeCurrentTab()
                .goTo(personalAccountPage)
                .clickOnElement("Кнопка выхода")
                .goTo(loginPage)
                .openAuthorizationPage()
                .loginViaUiOnUser("user2")
                .openMenuLinks("Личный кабинет")
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Утверждение")
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(cardRequestPage)
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
                        equals("Ожидает утверждения"),
                "Значение в столбце Статус заявки должно быть Ожидает утверждения");
        queuesPage.resetFilters()
                .goTo(personalAccountPage)
                .clickOnElement("Кнопка выхода");
    }

    @Test
    @Tag("button_return_queue_save_3244238")
    @DisplayName("3244238 - Утверждение: Кнопка \"Вернуть в очередь\" - Заявка переходит в общую очередь и сохраняет изменения")
    @WorkItemIds({"3244238"})
    public void button_return_queue_save_3244238(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2057991.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        transferApplicationStatusAwaitingApproval();
        personalAccountPage
                .clickOnElement("Раздел Утверждение")
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(cardRequestPage)
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - Ожидает утверждения")
                .clickOnElement("Кнопка Взять в работу")
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - На утверждении")
                .assertElementByTitleActivity("Кнопка Вернуть в очередь", "активен")
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .fillInput("Поле ввода Комментарий утверждающего", "321")
                .goTo(cardRequestPage)
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
                        equals("Ожидает утверждения"),
                "Значение в столбце Статус заявки должно быть Ожидает утверждения");
        queuesPage
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Решение Андеррайтера");
        assertIsEquals("321", underwriterDecisionPage.getValueByElementTitle("Поле ввода Комментарий утверждающего"), "Поле ввода Комментарий утверждающего");
        queuesPage.closeCurrentTab().resetFilters();
    }

    @Test
    @Tag("button_return_queue_save_3244236")
    @DisplayName("3244236 - Утверждение: Кнопка \"Вернуть в очередь\" - Заявка переходит в общую очередь и НЕ сохраняет изменения")
    @WorkItemIds({"3244236"})
    public void button_return_queue_not_save_3244236(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2057991.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        transferApplicationStatusAwaitingApproval();
        personalAccountPage
                .clickOnElement("Раздел Утверждение")
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(cardRequestPage)
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - Ожидает утверждения")
                .clickOnElement("Кнопка Взять в работу")
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - На утверждении")
                .assertElementByTitleActivity("Кнопка Вернуть в очередь", "активен")
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .fillInput("Поле ввода Комментарий утверждающего", "321")
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
                        equals("Ожидает утверждения"),
                "Значение в столбце Статус заявки должно быть Ожидает утверждения");
        queuesPage
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Решение Андеррайтера");
        assertIsEquals("", underwriterDecisionPage.getValueByElementTitle("Поле ввода Комментарий утверждающего"), "Поле ввода Комментарий утверждающего");
        queuesPage.closeCurrentTab().resetFilters();

    }

    @Test
    @Tag("button_return_queue_not_active_2653851")
    @DisplayName("2653851 - Кнопка \"Вернуть в очередь\" неактивна. У Пользователя есть разрешения - Утверждение. Статус \"Ожидает утверждения\"")
    @WorkItemIds({"2653851"})
    public void button_return_queue_not_active_2653851(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3251629.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        transferApplicationStatusAwaitingApproval();
        actionsClaimSteps.checkStatusClaimFromDb(claim, 5);
        personalAccountPage
                .clickOnElement("Раздел Утверждение")
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(cardRequestPage)
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - Ожидает утверждения")
                .assertElementByTitleActivity("Кнопка Вернуть в очередь", "не активен")
                .closeCurrentTab();
    }

    @Step
    @Title("Перевод заявки в статус Ожидает утверждения")
    private void transferApplicationStatusAwaitingApproval() {
        loginPage.openMenuLinks("Личный кабинет")
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Андеррайтинг")
                .goTo(personalAccountPage).doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(cardRequestPage)
                .selectValueFromDropDownList("Выпадающий список Занятость подтверждена", "Звонок не назначался")
                .fillInput("Поле ввода Внутренний комментарий андеррайтера", "123")
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .selectValueFromDropDownList("Выпадающий список Проведенные проверки", "Проверка критичных данных")
                .selectValueFromDropDownList("Выпадающий список Полномочия", "Собственные")
                .selectValueFromDropDownList("Выпадающий список Одобрить/Отклонить", "Одобрить")
                .selectValueFromDropDownList("Выпадающий список Тип одобрения/Причина отклонения", "Одобрено")
                .clickOnElement("Кнопка На утверждение")
                .switchToOneTab();
    }
}
