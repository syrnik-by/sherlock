package ru.autotestframework.regress.monitoring.actions_with_claims.underwriting;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import java.util.Map;

import static java.time.LocalDateTime.now;
import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;
import static ru.autotestframework.utils.Constants.DF;

@Tag("regress")
@Tag("monitoring")
@Tag("actions_with_claims")
@Tag("underwriting_event_check")
@ClassName("Мониторинг. Действия с заявками. Андеррайтинг. Проверка события")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UnderwritingEventCheckTest extends BaseTest {

    @BeforeEach
    @Order(1)
    public void login() {
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi();
        }
        loginPage.checkModal()
                .openMenuLinks("Личный кабинет")
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Андеррайтинг").waitBusyCondition();
    }

    @AfterEach
    public void cleanQueueClaims(TestInfo testInfo) {
        if (!testInfo.getDisplayName().contains("1723794")) {
            actionsRequestsPage.closeCurrentTab();
        }
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("smoke")
    @Tag("event_check_1723839")
    @DisplayName("1723839 - Проверка события Решение Андеррайтера. Одобрить/Отклонить")
    @WorkItemIds({"1723839"})
    public void event_check_1723839(TestInfo testInfo) {
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1723839.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .selectValueFromDropDownList("Выпадающий список Одобрить/Отклонить", "Одобрить")
                .openMenuLinks("Мониторинг > Действия с заявками")
                .goTo(actionsRequestsPage)
                .inputTypeOperation("Решение Андеррайтера. Одобрить/Отклонить")
                .searchClaimOnPage(claim);
        Map<String, String> expectedValues = Map.of(
                "ФИО пользователя", "Автоматическое Тестирование1",
                "Номер заявки", claim,
                "ФИО участника сделки", "Иванов Дмитрий Юрьевич",
                "Стратегия", "",
                "Тип операции", "Решение Андеррайтера. Одобрить/Отклонить",
                "Операция", "Решение Андеррайтера. Одобрить/Отклонить",
                "Начальное значение поля", "",
                "Конечное значение поля", "Одобрить",
                "Объект проверки", "");
        validateExpectedValues(expectedValues);
    }

    @Test
    @Tag("smoke")
    @Tag("event_check_1723845")
    @DisplayName("1723845 - Проверка события Решение Андеррайтера. Выбор причины доработки")
    @WorkItemIds({"1723845"})
    public void event_check_1723845(TestInfo testInfo) {
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1723839.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .selectValueFromDropDownList("Выпадающий список Причина доработки", "Отказ клиента")
                .openMenuLinks("Мониторинг > Действия с заявками")
                .goTo(actionsRequestsPage)
                .inputTypeOperation("Решение Андеррайтера. Выбор причины доработки")
                .searchClaimOnPage(claim);
        Map<String, String> expectedValues = Map.of(
                "ФИО пользователя", "Автоматическое Тестирование1",
                "Номер заявки", claim,
                "ФИО участника сделки", "",
                "Стратегия", "",
                "Тип операции", "Решение Андеррайтера. Выбор причины доработки",
                "Операция", "Решение Андеррайтера. Выбор причины доработки",
                "Начальное значение поля", "",
                "Конечное значение поля", "Отказ клиента",
                "Объект проверки", "");
        validateExpectedValues(expectedValues);
    }

    @Test
    @Tag("smoke")
    @Tag("event_check_1723813")
    @DisplayName("1723813 - Проверка события Основные данные. Занятость подтверждена")
    @WorkItemIds({"1723813"})
    public void event_check_1723813(TestInfo testInfo) {
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1723839.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .selectValueFromDropDownList("Выпадающий список Занятость подтверждена", "Недозвон")
                .openMenuLinks("Мониторинг > Действия с заявками")
                .goTo(actionsRequestsPage)
                .inputTypeOperation("Основные данные. Занятость подтверждена")
                .searchClaimOnPage(claim);
        Map<String, String> expectedValues = Map.of(
                "ФИО пользователя", "Автоматическое Тестирование1",
                "Номер заявки", claim,
                "ФИО участника сделки", "Иванов Дмитрий Юрьевич",
                "Стратегия", "",
                "Тип операции", "Основные данные. Занятость подтверждена",
                "Операция", "Основные данные. Занятость подтверждена",
                "Начальное значение поля", "",
                "Конечное значение поля", "Недозвон",
                "Объект проверки", "");
        validateExpectedValues(expectedValues);
    }

    @Test
    @Tag("smoke")
    @Tag("event_check_1723805")
    @DisplayName("1723805 - Проверка события Основные данные. Внутренний комментарий андеррайтера")
    @WorkItemIds({"1723805"})
    public void event_check_1723805(TestInfo testInfo) {
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1723839.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .fillInput("Поле ввода Внутренний комментарий андеррайтера", "123")
                .clickOnElement("Кнопка Сохранить")
                .openMenuLinks("Мониторинг > Действия с заявками")
                .goTo(actionsRequestsPage)
                .inputTypeOperation("Основные данные. Внутренний комментарий андеррайтера")
                .searchClaimOnPage(claim);
        Map<String, String> expectedValues = Map.of(
                "ФИО пользователя", "Автоматическое Тестирование1",
                "Номер заявки", claim,
                "ФИО участника сделки", "",
                "Стратегия", "",
                "Тип операции", "Основные данные. Внутренний комментарий андеррайтера",
                "Операция", "Основные данные. Внутренний комментарий андеррайтера",
                "Начальное значение поля", "",
                "Конечное значение поля", "123",
                "Объект проверки", "");
        validateExpectedValues(expectedValues);
    }

    @Test
    @Tag("smoke")
    @Tag("event_check_1723796")
    @DisplayName("1723796 - Проверка события Нажатие кнопки \"Взять в работу\"")
    @WorkItemIds({"1723796"})
    public void event_check_1723796(TestInfo testInfo) {
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1723839.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .selectValueFromDropDownList("Выпадающий список Занятость подтверждена", "Звонок не назначался")
                .fillInput("Поле ввода Внутренний комментарий андеррайтера", "123")
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .selectValueFromDropDownList("Выпадающий список Проведенные проверки", "Проверка документов")
                .selectValueFromDropDownList("Выпадающий список Полномочия", "Собственные")
                .selectValueFromDropDownList("Выпадающий список Одобрить/Отклонить", "Одобрить")
                .selectValueFromDropDownList("Выпадающий список Тип одобрения/Причина отклонения", "Одобрено")
                .clickOnElement("Кнопка На утверждение")
                .switchToOneTab()
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Утверждение")
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Взять в работу")
                .openMenuLinks("Мониторинг > Действия с заявками")
                .goTo(actionsRequestsPage)
                .inputTypeOperation("Нажатие кнопки \"Взять в работу\"")
                .searchClaimOnPage(claim);
        Map<String, String> expectedValues = Map.of(
                "ФИО пользователя", "Автоматическое Тестирование1",
                "Номер заявки", claim,
                "ФИО участника сделки", "",
                "Стратегия", "",
                "Тип операции", "Нажатие кнопки \"Взять в работу\"",
                "Операция", "Нажатие кнопки \"Взять в работу\"",
                "Начальное значение поля", "",
                "Конечное значение поля", "",
                "Объект проверки", "");
        validateExpectedValues(expectedValues);
    }

    @Test
    @Tag("smoke")
    @Tag("event_check_1723794")
    @DisplayName("1723794 - Проверка события Пользователь отложил заявку в очередь отложенных")
    @WorkItemIds({"1723794"})
    public void event_check_1723794(TestInfo testInfo) {
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1723839.json", 1, testInfo).get(0);
        String dateTimeNow = now().plusMinutes(10).format(DF);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Отложить")
                .selectValueFromDropDownList("Выпадающий список Причина (Перевод заявки в отложенные)", "Недозвон Заемщику")
                .fillInput("Поле ввода комментарий (Перевод заявки в отложенные)", "Перевод заявки в отложенные АТ")
                .fillInput("Поле ввода Время для звонка участнику", dateTimeNow)
                .clickOnElement("Кнопка Отложить заявку")
                .switchToOneTab()
                .goTo(personalAccountPage)
                .openMenuLinks("Мониторинг > Действия с заявками")
                .goTo(actionsRequestsPage)
                .inputTypeOperation("Пользователь отложил заявку в очередь отложенных")
                .searchClaimOnPage(claim);
        Map<String, String> expectedValues = Map.of(
                "ФИО пользователя", "Автоматическое Тестирование1",
                "Номер заявки", claim,
                "ФИО участника сделки", "",
                "Стратегия", "",
                "Тип операции", "Пользователь отложил заявку в очередь отложенных",
                "Операция", "Пользователь Автоматическое Тестирование1 отложил заявку " + claim + " в очередь отложенных по причине \"Недозвон Заемщику\" в статус \"Отложена (рассмотрение)\" до " + "\"" + dateTimeNow + "\"",
                "Начальное значение поля", "",
                "Конечное значение поля", "",
                "Объект проверки", "");
        validateExpectedValues(expectedValues);
    }

    @Test
    @Tag("smoke")
    @Tag("event_check_1723808")
    @DisplayName("1723808 - Проверка события Пользователь отложил заявку в очередь отложенных")
    @WorkItemIds({"1723808"})
    public void event_check_1723808(TestInfo testInfo) {
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1723839.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка редактировать Скоррект. доход По осн. месту")
                .clearInput("Поле редактировать Скоррект. доход По осн. месту")
                .clickOnElement("Кнопка редактировать Скоррект. доход По осн. месту")
                .fillInput("Поле редактировать Скоррект. доход По осн. месту", "85742")
                .clickOnElement("Кнопка Пересчитать лимит")
                .clickOnElement("Кнопка ОК на модальном окне Информация об ошибке")
                .openMenuLinks("Мониторинг > Действия с заявками")
                .goTo(actionsRequestsPage)
                .inputTypeOperation("Основные данные. Скоррект. доход По осн. месту")
                .searchClaimOnPage(claim);
        Map<String, String> expectedValues = Map.of(
                "ФИО пользователя", "Автоматическое Тестирование1",
                "Номер заявки", claim,
                "ФИО участника сделки", "Иванов Дмитрий Юрьевич",
                "Стратегия", "",
                "Тип операции", "Основные данные. Скоррект. доход По осн. месту",
                "Операция", "Основные данные. Скоррект. доход По осн. месту",
                "Начальное значение поля", "85741",
                "Конечное значение поля", "85742",
                "Объект проверки", "");
        validateExpectedValues(expectedValues);
    }

    @Test
    @Tag("smoke")
    @Tag("event_check_1723859")
    @DisplayName("1723859 - Проверка события Решение Андеррайтера. Подбор решений. Редактирование решения. Ставка")
    @WorkItemIds({"1723859"})
    public void event_check_1723859(TestInfo testInfo) {
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1723859.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .clickOnElement("Кнопка редактировать 1 строку таблцы Подбор решений")
                .clearInput("Поле ввода Ставка, % 1 строки таблицы Подбор решений")
                .fillInput("Поле ввода Ставка, % 1 строки таблицы Подбор решений", "15")
                .clickOnElement("Кнопка Сохранить таблцы Подбор решений")
                .openMenuLinks("Мониторинг > Действия с заявками")
                .goTo(actionsRequestsPage)
                .inputTypeOperation("Решение Андеррайтера. Подбор решений.Редактирование решения.Ставка")
                .searchClaimOnPage(claim);
        Map<String, String> expectedValues = Map.of(
                "ФИО пользователя", "Автоматическое Тестирование1",
                "Номер заявки", claim,
                "ФИО участника сделки", "Романов Юрий Иванович",
                "Стратегия", "",
                "Тип операции", "Решение Андеррайтера. Подбор решений.Редактирование решения.Ставка",
                "Операция", "Решение Андеррайтера. Подбор решений.Редактирование решения.Ставка (ГИ_Вт_Квартира)",
                "Начальное значение поля", "14.7",
                "Конечное значение поля", "15",
                "Объект проверки", "");
        validateExpectedValues(expectedValues);
    }

    private void validateExpectedValues(Map<String, String> expectedValues) {
        for (Map.Entry<String, String> expected : expectedValues.entrySet()) {
            String actualValue = actionsRequestsPage.getTextFromTable("Таблица Действия с заявками", 1, expected.getKey());
            assertIsTrue(actualValue.equals(expected.getValue()),
                    "Значение столбца " + expected.getKey() + " строки 1 должно быть равно " + expected.getValue() + " . Фактическое значение = " + actualValue);
        }
    }
}