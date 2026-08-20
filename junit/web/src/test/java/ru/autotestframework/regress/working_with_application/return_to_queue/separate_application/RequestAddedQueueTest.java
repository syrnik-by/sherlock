package ru.autotestframework.regress.working_with_application.return_to_queue.separate_application;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import java.util.List;

import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;

@Tag("regress")
@Tag("working_with_application")
@Tag("return_to_queue")
@ClassName("Работа с заявкой. Вернуть в очередь. На каждый кейс отдельная заявка. Вернуть в очередь")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RequestAddedQueueTest extends BaseTest {

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
        actionsClaimSteps.appointResponsiblePerson(claim);
    }

    @AfterEach
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("request_added_queue_3251397")
    @DisplayName("3251397 -Заявка переходит в очередь по нажатию на кнопку \"Вернуть в очередь\" на стратегии \"Проверка сотрудниками ОПМ\". Пользователь - Владелец блокировки")
    @WorkItemIds({"3251397"})
    public void request_added_queue_3251397() {

        personalAccountPage
                .clickOnElement("Раздел Верификация")
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(cardRequestPage)
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - На рассмотрении")
                .assertElementByTitleActivity("Кнопка Вернуть в очередь", "активен")
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Отправить на Antifraud")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Внутреннее мошенничество")
                .fillInput("Поле ввода Комментарий", "Комментарий АТ")
                .clickOnElement("Кнопка Далее")
                .assertElementByTitleVisibility("Кнопка Завершить проверку", "отображается")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab().waitBusyCondition()
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Проверка сотрудниками ОПМ")
                .doubleClickByText(claim).switchToNewTab()
                .goTo(verificationOpmEmployeesPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка сотрудниками ОПМ/Версия 1")
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Взять в работу")
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
        queuesPage
                .closeCurrentTab().resetFilters();
    }

    @Test
    @Tag("request_added_queue_3251396")
    @DisplayName("3251396 -Заявка переходит в очередь по нажатию на кнопку \"Вернуть в очередь\" на стратегии \"Вопрос в ГО\". Пользователь - НЕ владелец блокировки")
    @WorkItemIds({"3251396"})
    public void request_added_queue_3251396() {

        personalAccountPage
                .clickOnElement("Раздел Верификация")
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(cardRequestPage)
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - На рассмотрении")
                .assertElementByTitleActivity("Кнопка Вернуть в очередь", "активен")
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Методологический")
                .fillInput("Поле ввода Комментарий", "Комментарий АТ")
                .clickOnElement("Кнопка Далее")
                .assertElementByTitleVisibility("Кнопка Завершить проверку", "отображается")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab().waitBusyCondition()
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Вопрос в ГО")
                .doubleClickByText(claim).switchToNewTab()
                .goTo(verificationOpmEmployeesPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Вопрос в ГО/Версия 1")
                .goTo(cardRequestPage)
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - Ожидает")
                .clickOnElement("Кнопка Взять в работу")
                .closeCurrentTab()
                .goTo(personalAccountPage)
                .clickOnElement("Кнопка выхода")
                .goTo(loginPage)
                .openAuthorizationPage()
                .loginViaUiOnUser("user2")
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Вопрос в ГО")
                .clickOnElement("Кнопка Настройка списка(таблица В работе)")
                .goTo(filterListSettingsPage)
                .dragColumns("из правой колонки в левую", List.of("Владелец блокировки"))
                .clickOnElement("Кнопка Закрыть окно фильтров");
        assertIsTrue(queuesPage.getTextFromTable("Таблица Очереди", 1, "Статус заявки").
                        equals("На рассмотрении"),
                "Значение в столбце Статус заявки должно быть На рассмотрении");
        assertIsTrue(queuesPage.getTextFromTable("Таблица Очереди", 1, "Владелец блокировки").
                        equals("Автоматическое Тестирование1"),
                "Значение в столбце Владелец блокировки должно быть Автоматическое Тестирование1");

        personalAccountPage
                .doubleClickByText(claim)
                .goTo(questionInGoPage)
                .switchToNewTab()
                .checkElementByTitleEquals("Поле Наименование стратегии", "Вопрос в ГО/Версия 1")
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

        queuesPage
                .resetFilters()
                .goTo(personalAccountPage)
                .clickOnElement("Кнопка выхода");
    }

    @Test
    @Tag("request_added_queue_3251403")
    @DisplayName("3251403 -Заявка переходит в очередь по нажатию на кнопку \"Вернуть в очередь\" на стратегии \"Проверка сотрудниками ОПМ\". Пользователь - НЕ владелец блокировки")
    @WorkItemIds({"3251403"})
    public void request_added_queue_3251403() {
        personalAccountPage
                .clickOnElement("Раздел Верификация")
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(cardRequestPage)
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - На рассмотрении")
                .assertElementByTitleActivity("Кнопка Вернуть в очередь", "активен")
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Отправить на Antifraud")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Внутреннее мошенничество")
                .fillInput("Поле ввода Комментарий", "Комментарий АТ")
                .clickOnElement("Кнопка Далее")
                .assertElementByTitleVisibility("Кнопка Завершить проверку", "отображается")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab().waitBusyCondition()
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Проверка сотрудниками ОПМ")
                .doubleClickByText(claim).switchToNewTab()
                .goTo(verificationOpmEmployeesPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка сотрудниками ОПМ/Версия 1")
                .goTo(cardRequestPage)
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - Ожидает")
                .clickOnElement("Кнопка Взять в работу")
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - На рассмотрении")
                .closeCurrentTab()
                .goTo(personalAccountPage)
                .clickOnElement("Кнопка выхода")
                .goTo(loginPage)
                .openAuthorizationPage()
                .loginViaUiOnUser("user2")
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Проверка сотрудниками ОПМ")
                .clickOnElement("Кнопка Настройка списка(таблица В работе)")
                .goTo(filterListSettingsPage)
                .dragColumns("из правой колонки в левую", List.of("Владелец блокировки"))
                .clickOnElement("Кнопка Закрыть окно фильтров");
        assertIsTrue(queuesPage.getTextFromTable("Таблица Очереди", 1, "Статус заявки").
                        equals("На рассмотрении"),
                "Значение в столбце Статус заявки должно быть На рассмотрении");
        assertIsTrue(queuesPage.getTextFromTable("Таблица Очереди", 1, "Владелец блокировки").
                        equals("Автоматическое Тестирование1"),
                "Значение в столбце Владелец блокировки должно быть Автоматическое Тестирование1");

        personalAccountPage
                .doubleClickByText(claim)
                .goTo(questionInGoPage)
                .switchToNewTab()
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка сотрудниками ОПМ/Версия 1")
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
                .searchClaimOnPage(claim)
                .waitBusyCondition();
        assertIsTrue(queuesPage.getTextFromTable("Таблица Очереди", 1, "Владелец блокировки").
                        equals(""),
                "Значение в столбце Владелец блокировки должно быть пустым");
        assertIsTrue(queuesPage.getTextFromTable("Таблица Очереди", 1, "Статус заявки").
                        equals("Ожидает"),
                "Значение в столбце Статус заявки должно быть Ожидает");

        queuesPage
                .resetFilters()
                .goTo(personalAccountPage)
                .clickOnElement("Кнопка выхода");
    }
}