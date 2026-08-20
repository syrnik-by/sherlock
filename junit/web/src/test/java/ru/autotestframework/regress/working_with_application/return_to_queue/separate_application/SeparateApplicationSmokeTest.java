package ru.autotestframework.regress.working_with_application.return_to_queue.separate_application;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import java.util.Map;

import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;

@Tag("regress")
@Tag("working_with_application")
@Tag("return_to_queue")
@ClassName("Работа с заявкой. Вернуть в очередь. На каждый кейс отдельная заявка. Вернуть в очередь")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SeparateApplicationSmokeTest extends BaseTest {

    private String claim;

    @BeforeAll
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
        loginPage.closeCurrentTab().resetFilters().openMenuLinks("Личный кабинет");
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("smoke")
    @Tag("checking_button_return_to_queue_and_save_3251406")
    @DisplayName("3251406 - Верификация: Прозвон клиента + Прозвон работодателя - любой телефон. Кнопка \"Вернуть в очередь\". Изменения сохранены")
    @WorkItemIds({"3251406"})
    public void checking_button_return_to_queue_and_save_3251406(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "Code1", "stub11",
                "Code2", "stub13");

        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3253115.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(customerCallPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон клиента/Версия 1")
                .goTo(cardRequestPage)
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - На рассмотрении")
                .assertElementByTitleActivity("Кнопка Вернуть в очередь", "активен")
                .goTo(customerCallPage)
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Результативный прозвон")
                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Негатив не выявлен")
                .checkElementByTitleEquals("Выпадающий список Результативный прозвон", "Негатив не выявлен")
                .selectValueFromDropDownList("Выпадающий список Негатив не выявлен", "Негатив отсутствует")
                .checkElementByTitleEquals("Выпадающий список Негатив не выявлен", "Негатив отсутствует")

                .clickOnElement("Иконка Степ 2")
                .clickOnElement("Кнопка Взять шаг в работу")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Бесконтактное подтверждение")
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Бесконтактное подтверждение")
                .selectValueFromDropDownList("Выпадающий список Бесконтактное подтверждение", "Официальный сайт")
                .checkElementByTitleEquals("Выпадающий список Бесконтактное подтверждение", "Официальный сайт")
                .fillInput("Поле ввода Источник подтверждения", "123")

                .clickOnElement("Иконка Степ 3")
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Вернуть в очередь")
                .checkElementByTitleContains("Модальное окно предупреждения", "Заявка будет возвращена в очередь!\n" +
                        "Выполненные изменения будут утеряны. Да\\Нет?")
                .clickOnElement("Кнопка Нет модального окна").switchToOneTab()

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

        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(customerCallPage)
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Результативный прозвон")
                .checkElementByTitleEquals("Выпадающий список Результативный прозвон", "Негатив не выявлен")
                .checkElementByTitleEquals("Выпадающий список Негатив не выявлен", "Негатив отсутствует")

                .clickOnElement("Иконка Степ 2")
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Бесконтактное подтверждение")
                .checkElementByTitleEquals("Выпадающий список Бесконтактное подтверждение", "Официальный сайт");

    }

    @Test
    @Tag("smoke")
    @Tag("checking_button_return_to_queue_3251401")
    @DisplayName("3251401 - Заявка переходит в очередь по нажатию на кнопку \"Вернуть в очередь\" на стратегии \"Вопрос в ГО\". Пользователь - Владелец блокировки")
    @WorkItemIds({"3251401"})
    public void checking_button_return_to_queue_3251401(TestInfo testInfo) {

        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2056882.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .goTo(cardRequestPage)
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - На рассмотрении")
                .goTo(fsspPage)
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Методологический")
                .fillInput("Поле ввода Комментарий", "Любой текст")
                .checkElementByTitleEquals("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .checkElementByTitleEquals("Выпадающий список Тип вопроса", "Методологический")
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .clickOnElement("Кнопка Завершить проверку").waitBusyCondition().switchToOneTab()
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Вопрос в ГО").waitBusyCondition()

                .doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Вопрос в ГО/Версия 1")
                .goTo(cardRequestPage)
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - Ожидает")

                .clickOnElement("Кнопка Взять в работу")
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - На рассмотрении")
                .assertElementByTitleActivity("Кнопка Вернуть в очередь", "активен")
                .clickOnElement("Кнопка Вернуть в очередь")

                .checkElementByTitleContains("Модальное окно предупреждения", "Заявка будет возвращена в очередь!\n" +
                        "Выполненные изменения будут утеряны. Да\\Нет?")
                .clickOnElement("Кнопка Да модального окна").switchToOneTab()
                .goTo(loginPage)
                .openMenuLinks("Очереди")
                .searchClaimOnPage(claim);

        assertIsTrue(queuesPage.getTextFromTable("Таблица Очереди", 1, "Владелец блокировки").
                        equals(""),
                "Значение в столбце Владелец блокировки должно быть пустым");
        assertIsTrue(queuesPage.getTextFromTable("Таблица Очереди", 1, "Статус заявки").
                        equals("Ожидает"),
                "Значение в столбце Статус заявки должно быть Ожидает");
    }
}