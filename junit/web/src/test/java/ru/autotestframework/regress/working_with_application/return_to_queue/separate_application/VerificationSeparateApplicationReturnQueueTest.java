package ru.autotestframework.regress.working_with_application.return_to_queue.separate_application;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.ExternalId;
import ru.psb.testit.annotations.WorkItemIds;

import java.util.List;
import java.util.Map;

import static ru.autotestframework.steps.asserts.Asserts.assertIsEquals;
import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;

@Tag("regress")
@Tag("working_with_application")
@Tag("return_to_queue")
@ClassName("Работа с заявкой. Вернуть в очередь. На каждый кейс отдельная заявка. Вернуть в очередь")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VerificationSeparateApplicationReturnQueueTest extends BaseTest {

    String claim;

    @BeforeEach
    public void login() {
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi();
        }
        loginPage.openMenuLinks("Личный кабинет");
    }

    @AfterEach
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("button_return_to_queue_change_status_3251408")
    @DisplayName("3251408 - Верификация: ФССП. Кнопка \"Вернуть в очередь\". Смена статуса с \"На рассмотрении\" на \"Ожидает\"")
    @WorkItemIds({"3251408"})
    public void button_return_to_queue_change_status_3251408(TestInfo testInfo) {

        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2056882.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage
                .clickOnElement("Раздел Верификация")
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
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
        queuesPage.resetFilters();

    }

    @Test
    @Tag("button_return_to_queue_save_change_3251391")
    @DisplayName("3251391 - Верификация: Прозвон клиента. Кнопка \"Вернуть в очередь\". Изменения не сохранены.")
    @WorkItemIds({"3251391"})
    public void button_return_to_queue_save_change_3251391(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "Code", "stub11");

        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2182156.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage
                .clickOnElement("Раздел Верификация")
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(callingEmployerAnyPhonePage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон клиента/Версия 1")
                .goTo(cardRequestPage)
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - На рассмотрении")
                .assertElementByTitleActivity("Кнопка Вернуть в очередь", "активен")
                .goTo(callingEmployerAnyPhonePage)
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Нерезультативный прозвон")
                .selectValueFromDropDownList("Выпадающий список Нерезультативный прозвон", "Клиент просит перезвонить")
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
                .goTo(callingEmployerAnyPhonePage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон клиента/Версия 1")
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "")
                .assertElementByTitleVisibility("Выпадающий список Нерезультативный прозвон", "не отображается")
                .goTo(queuesPage)
                .closeCurrentTab().resetFilters();
    }

    @Test
    @Tag("button_return_to_queue_not_save_change_3251393")
    @DisplayName("3251393 - Верификация: ФССП. Кнопка \"Вернуть в очередь\". Изменения не сохранены")
    @WorkItemIds({"3251393"})
    public void button_return_to_queue_not_save_change_3251393(TestInfo testInfo) {

        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2056882.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage
                .clickOnElement("Раздел Верификация")
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Невозможно запросить ФССП")
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
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "")
                .goTo(queuesPage)
                .closeCurrentTab().resetFilters();
    }

    @Test
    @Tag("button_return_to_queue_save_change_3251399")
    @DisplayName("3251399 - Верификация: ФССП. Сначала кнопка \"Далее\" а потом - кнопка \"Вернуть в очередь\". Изменения сохранены")
    @WorkItemIds({"3251399"})
    public void button_return_to_queue_save_change_3251399(TestInfo testInfo) {

        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2056882.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage
                .clickOnElement("Раздел Верификация")
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Невозможно запросить ФССП")
                .goTo(cardRequestPage)
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - На рассмотрении")
                .assertElementByTitleActivity("Кнопка Вернуть в очередь", "активен")
                .clickOnElement("Кнопка Далее")
                .assertElementByTitleVisibility("Кнопка Завершить проверку", "отображается")
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
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Невозможно запросить ФССП")
                .goTo(queuesPage)
                .closeCurrentTab().resetFilters();
    }

    @Test
    @Tag("button_return_to_queue_not_save_change_3251398")
    @DisplayName("3251398 - Верификация: Открытые источники - проверка работодателя.  Заемщик + Основ + Совм. Кнопка \"Вернуть в очередь\". Изменения не сохранены")
    @WorkItemIds({"3251398"})
    public void button_return_to_queue_not_save_change_3251398(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "Code", "stub7");

        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2182156.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage
                .clickOnElement("Раздел Верификация")
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(checkingOpenSourcesPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка открытых источников/Версия 1")
                .clickOnElement("Чек-бокс Негатив по работодателю не выявлен")
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
                .goTo(checkingOpenSourcesPage)
                .assertElementByTitleSelected("Чек-бокс Негатив по работодателю не выявлен", "не выбран")
                .goTo(queuesPage)
                .closeCurrentTab().resetFilters();
    }

    @Test
    @Tag("button_return_to_queue_save_change_3251394")
    @DisplayName("3251394 - Верификация: Открытые источники - проверка работодателя.  Заемщик + Созаем + Основ + Совм. Кнопка \"Вернуть в очередь\". Изменения сохранены")
    @WorkItemIds({"3251394"})
    public void button_return_to_queue_save_change_3251394(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "Code1", "stub7",
                "Code2", "stub7");

        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2191697.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage
                .clickOnElement("Раздел Верификация")
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(cardRequestPage)
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - На рассмотрении")
                .assertElementByTitleActivity("Кнопка Вернуть в очередь", "активен")
                .goTo(checkingOpenSourcesPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка открытых источников/Версия 1")
                .clickOnElement("Чек-бокс Негатив по работодателю не выявлен")
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .clickOnElement("Чек-бокс Негатив на работодателя в сети")
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .clickOnElement("Чек-бокс Негатив на работодателя в сети")
                .clickOnElement("Чек-бокс Решение о санации")
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .clickOnElement("Чек-бокс Негатив по работодателю не выявлен")
                .goTo(cardRequestPage)
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
                .goTo(checkingOpenSourcesPage)
                .assertElementByTitleSelected("Чек-бокс Негатив по работодателю не выявлен", "выбран")
                .clickOnElement("Шаг №2. Заёмщик. Совместительство").waitBusyCondition()
                .assertElementByTitleSelected("Чек-бокс Негатив на работодателя в сети", "выбран")
                .clickOnElement("Шаг №3. Созаемщик. Основное место работы").waitBusyCondition()
                .assertElementByTitleSelected("Чек-бокс Негатив на работодателя в сети", "выбран")
                .assertElementByTitleSelected("Чек-бокс Решение о санации", "выбран")
                .clickOnElement("Шаг №4. Созаемщик. Совместительство").waitBusyCondition()
                .assertElementByTitleSelected("Чек-бокс Негатив по работодателю не выявлен", "выбран")
                .goTo(queuesPage)
                .closeCurrentTab().resetFilters();
    }

    @Test
    @Tag("button_return_to_queue_save_change_3251407")
    @DisplayName("3251407 - Верификация: Проверка дохода. Заем + Созаем + Основ + Совм. Кнопка \"Вернуть в очередь\". Изменения сохранены")
    @WorkItemIds({"3251407"})
    public void button_return_to_queue_save_change_3251407(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "Code1", "stub5",
                "Code2", "stub5");

        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2191697.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage
                .clickOnElement("Раздел Верификация")
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(cardRequestPage)
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - На рассмотрении")
                .assertElementByTitleActivity("Кнопка Вернуть в очередь", "активен")
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка дохода/Версия 1")
                .clickOnElement("Переключатель Да (Деятельность компании подразумевает большую закупочную часть или траты вне персонала)")
                .fillInput("Поле ввода Выручка по официальным данным за предыдущий год (руб.)", "5000000")
                .fillInput("Поле ввода Доля в бизнесе (%)", "100")
                .clickOnElement("Кнопка Рассчитать").waitBusyCondition()
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Доход не завышен");
        String actualValue = incomeVerificationPage.getValueByElementTitle("Поле ввода Рассчитанный доход от ведения бизнеса (руб.)");
        assertIsTrue("100000".equals(actualValue),
                "Значенние Поле ввода Рассчитанный доход от ведения бизнеса (руб.) " + actualValue + " должно быть равно 100000. Фактическое значение = " + actualValue);
        incomeVerificationPage
                .clickOnElement("Второй шаг на степере")
                .clickOnElement("Кнопка Взять шаг в работу")
                .clickOnElement("Переключатель Нет (Деятельность компании подразумевает большую закупочную часть или траты вне персонала)")
                .fillInput("Поле ввода Выручка по официальным данным за предыдущий год (руб.)", "6000000")
                .fillInput("Поле ввода Доля в бизнесе (%)", "99")
                .clickOnElement("Кнопка Рассчитать").waitBusyCondition()
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Доход не завышен");
        String actualValue2 = incomeVerificationPage.getValueByElementTitle("Поле ввода Рассчитанный доход от ведения бизнеса (руб.)");
        assertIsTrue("356400".equals(actualValue2),
                "Значенние Поле ввода Рассчитанный доход от ведения бизнеса (руб.) " + actualValue2 + " должно быть равно 356400. Фактическое значение = " + actualValue2);
        incomeVerificationPage
                .clickOnElement("Третий шаг на степере")
                .clickOnElement("Кнопка Взять шаг в работу")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Должность клиента не позволяет оценить его доход")
                .clickOnElement("Кнопка Рассчитать").waitBusyCondition()
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Доход не завышен")
                .clickOnElement("Четвертый шаг на степере")
                .clickOnElement("Кнопка Взять шаг в работу")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Оценка дохода проведена")
                .fillInput("Поле ввода Средний доход по рынку для занимаемой должности", "100000")
                .clickOnElement("Кнопка Рассчитать").waitBusyCondition()
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Доход не завышен")
                .assertElementByTitleActivity("Кнопка Рассчитать", "не активен")
                .assertElementByTitleActivity("Поле ввода Средний доход по рынку для занимаемой должности", "не активен")
                .goTo(cardRequestPage)
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
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Доход не завышен");
        assertIsEquals("5000000", incomeVerificationPage.getValueByElementTitle("Поле ввода Выручка по официальным данным за предыдущий год (руб.)"), "Поле ввода Выручка по официальным данным за предыдущий год (руб.)");
        assertIsEquals("100", incomeVerificationPage.getValueByElementTitle("Поле ввода Доля в бизнесе (%)"), "Поле ввода Доля в бизнесе (%)");
        String actualValue3 = incomeVerificationPage.getValueByElementTitle("Поле ввода Рассчитанный доход от ведения бизнеса (руб.)");
        assertIsTrue("100000".equals(actualValue3),
                "Значенние Поле ввода Рассчитанный доход от ведения бизнеса (руб.) " + actualValue3 + " должно быть равно 100000. Фактическое значение = " + actualValue3);
        incomeVerificationPage
                .clickOnElement("Второй шаг на степере")
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Доход не завышен");
        assertIsEquals("6000000", incomeVerificationPage.getValueByElementTitle("Поле ввода Выручка по официальным данным за предыдущий год (руб.)"), "Поле ввода Выручка по официальным данным за предыдущий год (руб.)");
        assertIsEquals("99", incomeVerificationPage.getValueByElementTitle("Поле ввода Доля в бизнесе (%)"), "Поле ввода Доля в бизнесе (%)");
        String actualValue4 = incomeVerificationPage.getValueByElementTitle("Поле ввода Рассчитанный доход от ведения бизнеса (руб.)");
        assertIsTrue("356400".equals(actualValue4),
                "Значенние Поле ввода Рассчитанный доход от ведения бизнеса (руб.) " + actualValue4 + " должно быть равно 356400. Фактическое значение = " + actualValue4);
        incomeVerificationPage
                .clickOnElement("Третий шаг на степере")
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Доход не завышен")
                .clickOnElement("Четвертый шаг на степере")
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Доход не завышен");
        assertIsEquals("100000", incomeVerificationPage.getValueByElementTitle("Поле ввода Средний доход по рынку для занимаемой должности"), "Поле ввода Средний доход по рынку для занимаемой должности");
        queuesPage
                .closeCurrentTab().resetFilters();
    }

    @Test
    @Tag("button_return_to_queue_not_save_change_3251400")
    @DisplayName("3251400 - Верификация: Проверка дохода.  Заемщик + Основ + Совм. Кнопка \"Вернуть в очередь\". Изменения не сохранены")
    @WorkItemIds({"3251400"})
    public void button_return_to_queue_not_save_change_3251400(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "Code", "stub5");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2182156.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage
                .clickOnElement("Раздел Верификация")
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка дохода/Версия 1")
                .goTo(cardRequestPage)
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - На рассмотрении")
                .assertElementByTitleActivity("Кнопка Вернуть в очередь", "активен")
                .goTo(incomeVerificationPage)
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Должность клиента не позволяет оценить его доход")
                .clickOnElement("Кнопка Рассчитать")
                .checkElementByTitleContains("Выпадающий список Результат проверки", "Доход не завышен")
                .clickOnElement("Иконка Степ 2")
                .clickOnElement("Кнопка Взять шаг в работу").waitBusyCondition()
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Должность клиента не позволяет оценить его доход")
                .clickOnElement("Кнопка Рассчитать")
                .checkElementByTitleContains("Выпадающий список Результат проверки", "Доход не завышен")
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
                .goTo(incomeVerificationPage)
                .checkElementByTitleContains("Выпадающий список Результат проверки", "Доход не завышен")
                .clickOnElement("Иконка Степ 2").waitBusyCondition();
        assertIsTrue(incomeVerificationPage.getTextByElementTitle("Выпадающий список Результат проверки").
                        equals(""),
                "Значение в Выпадающий список Результат проверки должно быть пустым");
    }

    @ParameterizedTest
    @CsvSource({
            "3251395, Верификация: L0. Проверка документов. Заемщик. Основ. + Совм. Кнопка \"Вернуть в очередь\". Изменения не сохранены, Да, не выбран",
            "3251404, Верификация: L0. Проверка документов. Заемщик. Основ. + Совм. Кнопка \"Вернуть в очередь\". Изменения сохранены, Нет, выбран"})
    @DisplayName("{id} - Верификация L0. Выбор дополнительных документов: {displayName}")
    @Tag("verification_strategy_l0_not_save_change_3251395_3251404")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void verification_strategy_l0_not_save_change_3251395_3251404(String id, String displayName, String button, String select, TestInfo testInfo) {
        List<String> expectedCheckedTriggersStep1 = List.of("Признаки лица БОМЖ",
                "Фото клиента не соответствует фото в паспорте");
        List<String> expectedTriggersStep2 = List.of(
                "Доход по коду 2611",
                "Доход по коду 2013",
                "Доход по коду 2014",
                "Наличие сведений о ликвидации",
                "Признаки подделки",
                "Признаки фальсификации");
        List<String> expectedTriggersAdditionalDocs = List.of(
                "Выписка из СФР с доходом",
                "Выписка из СФР без дохода",
                "ЭТК",
                "Выписка с з/п счета с ящика doc");

        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2192774.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(l0CheckingDocumentsPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "L0.Проверка документов/Версия 1")
                .goTo(cardRequestPage)
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - На рассмотрении")
                .assertElementByTitleActivity("Кнопка Вернуть в очередь", "активен")
                .goTo(l0CheckingDocumentsPage)
                .clickOnNotProvidedIconForDoc("ФОТО", "Документ не предоставлен")
                .clickButtonsForTrigger("Нет", "Признаки лица БОМЖ", "ФОТО")
                .clickButtonsForTrigger("Нет", "Фото клиента не соответствует фото в паспорте", "ФОТО")
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .clickOnNotProvidedIconForDoc("2-НДФЛ", "Документ не предоставлен");
        expectedTriggersStep2.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, "2-НДФЛ"));
        l0CheckingDocumentsPage
                .clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен");
        expectedTriggersAdditionalDocs.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, "Выбор дополнительных документов"));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .clickOnNotProvidedIconForDoc("Военный билет", "Документ не предоставлен")
                .clickButtonsForTrigger("Нет", "Признаки фальсификации", "Военный билет")
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Вернуть в очередь")
                .checkElementByTitleContains("Модальное окно предупреждения", "Заявка будет возвращена в очередь!\n" +
                        "Выполненные изменения будут утеряны. Да\\Нет?")
                .clickOnElement("Кнопка " + button + " модального окна").switchToOneTab().waitBusyCondition()
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
                .goTo(l0CheckingDocumentsPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "L0.Проверка документов/Версия 1");
        expectedCheckedTriggersStep1.forEach(trigger -> l0CheckingDocumentsPage.checkRadioButtonsCheckedForTrigger("ФОТО", trigger, "НЕТ", "выбран"));
        l0CheckingDocumentsPage.clickOnElement("Шаг №2. Заёмщик. Основное место работы");
        expectedTriggersStep2.forEach(trigger -> l0CheckingDocumentsPage.checkRadioButtonsCheckedForTrigger("2-НДФЛ", trigger, "НЕТ", "выбран"));
        expectedTriggersAdditionalDocs.forEach(trigger -> l0CheckingDocumentsPage.checkRadioButtonsCheckedForTrigger("Выбор дополнительных документов", trigger, "НЕТ", "выбран"));
        l0CheckingDocumentsPage.clickOnElement("Шаг №3. Заёмщик. Совместительство")
                .checkRadioButtonsCheckedForTrigger("Военный билет", "Признаки фальсификации", "НЕТ", select);
        cardRequestPage.closeCurrentTab().switchToOnetab();
    }
}