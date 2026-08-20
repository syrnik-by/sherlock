package ru.autotestframework.regress.monitoring.actions_with_claims.underwriting;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.autotestframework.BaseTest;
import ru.autotestframework.pages.BasePage;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.ExternalId;
import ru.psb.testit.annotations.WorkItemIds;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static ru.autotestframework.steps.actions.BaseActions.sleep;
import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;
import static ru.autotestframework.utils.Constants.DF;

@Tag("regress")
@Tag("monitoring")
@Tag("actions_with_claims")
@Tag("actions_with_applications")
@ClassName("Мониторинг. Действия с заявками. Андеррайтинг")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UnderwritingTest extends BaseTest {

    private String claim;

    @BeforeEach
    @Order(1)
    public void login(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_" +
                (testInfo.getDisplayName().contains("4093152") ? "4093143" : "4083343") + ".json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
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
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("actions_with_applications_1723758")
    @DisplayName("1723758 - Мониторинг. Действия с заявками. Андеррайтинг. Изменение статуса заявки. Сохранения причины отправки заявки в отложенные")
    @WorkItemIds({"1723758"})
    public void actions_with_applications_1723758() {
        personalAccountPage
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Отложить")
                .selectValueFromDropDownList("Выпадающий список Причина (Перевод заявки в отложенные)", "Заемщик просит перезвонить")
                .fillInput("Поле ввода Время для звонка участнику", LocalDateTime.now().plusDays(1).withHour(18).format(DF))
                .clickOnElement("Кнопка Отложить заявку")
                .switchToOneTab()
                .goTo(personalAccountPage)
                .openMenuLinks("Мониторинг > Действия с заявками")
                .goTo(actionsRequestsPage)
                .inputTypeOperation("Изменение статуса заявки")
                .searchClaimOnPage(claim);
        Map<String, String> expectedValues = Map.of(
                "ФИО пользователя", "Система",
                "Номер заявки", claim,
                "ФИО участника сделки", "",
                "Стратегия", "",
                "Тип операции", "Изменение статуса заявки",
                "Операция", "Изменение статуса заявки " + claim + " c \"На рассмотрении\" на \"Отложена (рассмотрение)\". \"Заемщик просит перезвонить\"",
                "Начальное значение поля", "101",
                "Конечное значение поля", "108",
                "Объект проверки", "");
        validateExpectedValues(expectedValues, 3);
    }

    @Test
    @Tag("actions_with_applications_4083461")
    @DisplayName("4083461 - Мониторинг. Действия с заявками. Андеррайтинг. Изменение статуса заявки. Проверка сохранения причины отправки заявки на доработку")
    @WorkItemIds({"4083461"})
    public void actions_with_applications_4083461() {
        personalAccountPage
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .selectValueFromDropDownList("Выпадающий список Занятость подтверждена", "Звонок не назначался")
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .selectValueFromDropDownList("Выпадающий список Проведенные проверки", "Проверка критичных данных")
                .selectValueFromDropDownList("Выпадающий список Причина доработки", "Ошибка МРК при вводе контактных данных клиента (адресов, телефонов)")
                .fillInput("Поле ввода Комментарий МРК и отлагательных условий", "Комментарий МРК")
                .fillInput("Поле ввода Внутренний комментарий андеррайтера", "коммент")
                .clickOnElement("Кнопка Доработка")
                .waitBusyCondition()
                .switchToOneTab();
        String dateTimeNow = now().format(DF);
        personalAccountPage.openMenuLinks("Мониторинг > Действия с заявками")
                .goTo(actionsRequestsPage)
                .inputTypeOperation("Изменение статуса заявки")
                .searchClaimOnPage(claim);
        Map<String, String> expectedValues = Map.of(
                "ФИО пользователя", "Система",
                "Номер заявки", claim,
                "Время", dateTimeNow,
                "ФИО участника сделки", "",
                "Стратегия", "",
                "Тип операции", "Изменение статуса заявки",
                "Операция", "Изменение статуса заявки " + claim + " c \"На рассмотрении\" на \"На доработке\". \"Ошибка МРК при вводе контактных данных клиента (адресов, телефонов)\"",
                "Начальное значение поля", "101",
                "Конечное значение поля", "30",
                "Объект проверки", "");
        validateExpectedValues(expectedValues, 3);
    }

    @Test
    @Tag("actions_with_applications_4083587")
    @DisplayName("4083587 - Мониторинг. Действия с заявками. Андеррайтинг. Заявка автоматически назначена в работу пользователю")
    @WorkItemIds({"4083587"})
    public void actions_with_applications_4083587() {
        personalAccountPage
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Отложить")
                .selectValueFromDropDownList("Выпадающий список Причина (Перевод заявки в отложенные)", "Заемщик просит перезвонить");
        LocalDateTime dateTime = LocalDateTime.now().plusMinutes(3);
        cardRequestPage.fillInput("Поле ввода Время для звонка участнику", dateTime.format(DF))
                .clickOnElement("Кнопка Отложить заявку")
                .switchToOneTab();
        sleep(180);
        personalAccountPage.openMenuLinks("Мониторинг > Действия с заявками")
                .goTo(actionsRequestsPage)
                .inputTypeOperation("Заявка автоматически назначена в работу пользователю")
                .searchClaimOnPage(claim);
        Map<String, String> expectedValues = Map.of(
                "ФИО пользователя", "Система",
                "Номер заявки", claim,
                "Время", dateTime.minusMinutes(1).format(DF),
                "ФИО участника сделки", "",
                "Стратегия", "",
                "Тип операции", "Заявка автоматически назначена в работу пользователю",
                "Операция", "Заявка " + claim + " автоматически назначена в работу пользователю Автоматическое Тестирование1",
                "Начальное значение поля", "",
                "Конечное значение поля", "",
                "Объект проверки", "");
        validateExpectedValues(expectedValues, 1);
    }

    @Test
    @Tag("actions_with_applications_4091951")
    @DisplayName("4091951 - Мониторинг. Действия с заявками. Андеррайтинг. Пользователь вручную вернул заявку из очереди отложенных")
    @WorkItemIds({"4091951"})
    public void actions_with_applications_4091951() {
        personalAccountPage
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Отложить")
                .selectValueFromDropDownList("Выпадающий список Причина (Перевод заявки в отложенные)", "Заемщик просит перезвонить");
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1).withHour(18).withMinute(0);
        cardRequestPage.fillInput("Поле ввода Время для звонка участнику", dateTime.format(DF))
                .clickOnElement("Кнопка Отложить заявку")
                .switchToOneTab()
                .goTo(personalAccountPage)
                .clickOnElement("Кнопка раскрыть таблицу Отложено")
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Взять в работу");
        dateTime = LocalDateTime.now();
        cardRequestPage.checkElementByTitleContains("Информация по заявке (Дата и Статус)", "На рассмотрении")
                .closeCurrentTab()
                .goTo(personalAccountPage)
                .openMenuLinks("Мониторинг > Действия с заявками")
                .goTo(actionsRequestsPage)
                .inputTypeOperation("Пользователь вручную вернул заявку из очереди отложенных")
                .searchClaimOnPage(claim);
        Map<String, String> expectedValues = Map.of(
                "ФИО пользователя", "Автоматическое Тестирование1",
                "Номер заявки", claim,
                "Время", dateTime.format(DF),
                "ФИО участника сделки", "",
                "Стратегия", "",
                "Тип операции", "Пользователь вручную вернул заявку из очереди отложенных",
                "Операция", "Пользователь Автоматическое Тестирование1 вернул заявку " + claim + " из очереди отложенных в статус \"На рассмотрении\"",
                "Начальное значение поля", "",
                "Конечное значение поля", "",
                "Объект проверки", "");
        validateExpectedValues(expectedValues, 1);
    }

    @Test
    @Tag("actions_with_applications_4091970")
    @DisplayName("4091970 - Мониторинг. Действия с заявками. Андеррайтинг. Нажатие кнопки \"Вернуть в очередь\"")
    @WorkItemIds({"4091970"})
    public void actions_with_applications_4091970() {
        personalAccountPage
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .assertElementByTitleActivity("Кнопка Вернуть в очередь", "активен")
                .clickOnElement("Кнопка Вернуть в очередь")
                .checkElementByTitleContains("Модальное окно предупреждения", "Заявка будет возвращена в очередь!\n" +
                        "Выполненные изменения будут утеряны. Да\\Нет?")
                .clickOnElement("Кнопка Да модального окна").switchToOneTab().waitBusyCondition();
        String dateTime = LocalDateTime.now().format(DF);
        personalAccountPage.openMenuLinks("Мониторинг > Действия с заявками")
                .goTo(actionsRequestsPage)
                .inputTypeOperation("Нажатие кнопки \"Вернуть в очередь\"")
                .searchClaimOnPage(claim);
        Map<String, String> expectedValues = Map.of(
                "ФИО пользователя", "Автоматическое Тестирование1",
                "Номер заявки", claim,
                "Время", dateTime,
                "ФИО участника сделки", "",
                "Стратегия", "",
                "Тип операции", "Нажатие кнопки \"Вернуть в очередь\"",
                "Операция", "Нажатие кнопки \"Вернуть в очередь\"",
                "Начальное значение поля", "",
                "Конечное значение поля", "",
                "Объект проверки", "");
        validateExpectedValues(expectedValues, 1);
    }


    @Test
    @Tag("actions_with_applications_4092020")
    @DisplayName("4092020 - Мониторинг. Действия с заявками. Андеррайтинг. Нажатие кнопки \"Сохранить и закрыть\"")
    @WorkItemIds({"4092020"})
    public void actions_with_applications_4092020() {
        personalAccountPage
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .assertElementByTitleActivity("Кнопка Вернуть в очередь", "активен")
                .clickOnElement("Кнопка Сохранить и закрыть")
                .switchToOneTab().waitBusyCondition();
        String dateTime = LocalDateTime.now().format(DF);
        personalAccountPage.openMenuLinks("Мониторинг > Действия с заявками")
                .goTo(actionsRequestsPage)
                .inputTypeOperation("Нажатие кнопки \"Сохранить и закрыть\"")
                .searchClaimOnPage(claim);
        Map<String, String> expectedValues = Map.of(
                "ФИО пользователя", "Автоматическое Тестирование1",
                "Номер заявки", claim,
                "Время", dateTime,
                "ФИО участника сделки", "",
                "Стратегия", "",
                "Тип операции", "Нажатие кнопки \"Сохранить и закрыть\"",
                "Операция", "Нажатие кнопки \"Сохранить и закрыть\"",
                "Начальное значение поля", "",
                "Конечное значение поля", "",
                "Объект проверки", "");
        validateExpectedValues(expectedValues, 1);
    }

    @Test
    @Tag("actions_with_applications_4092027")
    @DisplayName("4092027 - Мониторинг. Действия с заявками. Андеррайтинг. Нажатие кнопки \"Выйти без сохранения\"")
    @WorkItemIds({"4092027"})
    public void actions_with_applications_4092027() {
        personalAccountPage
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Выйти без сохранения")
                .checkElementByTitleContains("Модальное окно Потеря изменений", "Выполненные изменения будут утеряны. Да\\Нет?")
                .clickOnElement("Кнопка Да на модальном окне")
                .switchToOneTab().waitBusyCondition();
        String dateTime = LocalDateTime.now().format(DF);
        personalAccountPage.openMenuLinks("Мониторинг > Действия с заявками")
                .goTo(actionsRequestsPage)
                .inputTypeOperation("Нажатие кнопки \"Выйти без сохранения\"")
                .searchClaimOnPage(claim);
        Map<String, String> expectedValues = Map.of(
                "ФИО пользователя", "Автоматическое Тестирование1",
                "Номер заявки", claim,
                "Время", dateTime,
                "ФИО участника сделки", "",
                "Стратегия", "",
                "Тип операции", "Нажатие кнопки \"Выйти без сохранения\"",
                "Операция", "Нажатие кнопки \"Выйти без сохранения\"",
                "Начальное значение поля", "",
                "Конечное значение поля", "",
                "Объект проверки", "");
        validateExpectedValues(expectedValues, 1);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "4092048, Основные данные. Выбор причины доработки, Причина доработки, Неполный пакет документов (ошибка МРК), Уточнение данных по кредитам клиента, " +
                    "'', '', Неполный пакет документов (ошибка МРК), Неполный пакет документов (ошибка МРК), Уточнение данных по кредитам клиента",
            "4093194, Основные данные. Телефон подтвержден, Телефон подтвержден, Да, Нет, Романов Юрий Иванович, '', Да, Да, Нет"
    })
    @Tag("actions_with_applications_4092048_4093194")
    @DisplayName("{id} - Мониторинг. Действия с заявками. Андеррайтинг. Основные данные. {operation}")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void actions_with_applications_4092048_4093194(String id, String operation, String fieldName, String fieldValue1, String fieldValue2,
                                                          String fio, String startValue1, String endValue1,
                                                          String startValue2, String endValue2) {
        personalAccountPage
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .selectValueFromDropDownList("Выпадающий список " + fieldName, fieldValue1);
        String dateTime = LocalDateTime.now().format(DF);
        cardRequestPage.selectValueFromDropDownList("Выпадающий список " + fieldName, fieldValue2);
        String dateTime2 = LocalDateTime.now().format(DF);
        cardRequestPage.closeCurrentTab();
        personalAccountPage.openMenuLinks("Мониторинг > Действия с заявками")
                .goTo(actionsRequestsPage)
                .inputTypeOperation(operation)
                .searchClaimOnPage(claim);
        Map<String, String> expectedValues = Map.of(
                "ФИО пользователя", "Автоматическое Тестирование1",
                "Номер заявки", claim,
                "Время", dateTime,
                "ФИО участника сделки", fio,
                "Стратегия", "",
                "Тип операции", operation,
                "Операция", operation,
                "Начальное значение поля", startValue1,
                "Конечное значение поля", endValue1,
                "Объект проверки", "");
        Map<String, String> expectedValues2 = Map.of(
                "ФИО пользователя", "Автоматическое Тестирование1",
                "Номер заявки", claim,
                "Время", dateTime2,
                "ФИО участника сделки", fio,
                "Стратегия", "",
                "Тип операции", operation,
                "Операция", operation,
                "Начальное значение поля", startValue2,
                "Конечное значение поля", endValue2,
                "Объект проверки", "");
        validateExpectedValues(expectedValues, 1);
        validateExpectedValues(expectedValues2, 2);
    }

    @Test
    @Tag("actions_with_applications_4093042")
    @DisplayName("4093042 - Мониторинг. Действия с заявками. Андеррайтинг. Основные данные. Комментарий к причине доработки")
    @WorkItemIds({"4093042"})
    public void actions_with_applications_4093042() {
        personalAccountPage
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .selectValueFromDropDownList("Выпадающий список Проведенные проверки", "Проверка критичных данных")
                .clickOnElement("Кнопка Основные данные")
                .goTo(cardRequestPage)
                .selectValueFromDropDownList("Выпадающий список Занятость подтверждена", "Звонок не назначался")
                .selectValueFromDropDownList("Выпадающий список Причина доработки", "Неполный пакет документов (ошибка МРК)")
                .fillInput("Поле ввода Комментарий МРК и отлагательных условий", "Тест")
                .fillInput("Поле ввода Внутренний комментарий андеррайтера", "коммент")
                .clickOnElement("Кнопка Доработка")
                .waitBusyCondition()
                .switchToOneTab();
        String dateTime = LocalDateTime.now().format(DF);
        personalAccountPage.openMenuLinks("Мониторинг > Действия с заявками")
                .goTo(actionsRequestsPage)
                .inputTypeOperation("Основные данные. Комментарий к причине доработки")
                .searchClaimOnPage(claim);
        Map<String, String> expectedValues = Map.of(
                "ФИО пользователя", "Автоматическое Тестирование1",
                "Номер заявки", claim,
                "Время", dateTime,
                "ФИО участника сделки", "",
                "Стратегия", "",
                "Тип операции", "Основные данные. Комментарий к причине доработки",
                "Операция", "Основные данные. Комментарий к причине доработки",
                "Начальное значение поля", "",
                "Конечное значение поля", "Тест",
                "Объект проверки", "");
        validateExpectedValues(expectedValues, 1);
    }

    @Test
    @Tag("actions_with_applications_4093054")
    @DisplayName("4093054 - Мониторинг. Действия с заявками. Андеррайтинг. Основные данные. Нажатие кнопки \"Доработка\"")
    @WorkItemIds({"4093054"})
    public void actions_with_applications_4093054() {
        personalAccountPage
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Доработка")
                .checkElementByTitleContains("Модальное окно Информация об ошибке",
                        "Необходимо заполнить атрибут “Внутренний комментарий андеррайтера”\n" +
                                "Необходимо заполнить атрибут “Проведенные проверки”\n" +
                                "Необходимо заполнить атрибут “Причина доработки”\n" +
                                "Необходимо заполнить атрибут “Комментарий МРК”\n" +
                                "Необходимо заполнить атрибут “Занятость подтверждена”")
                .clickOnElement("Кнопка ОК на модальном окне Информация об ошибке")
                .closeCurrentTab();
        String dateTimeNow = now().format(DF);
        personalAccountPage.openMenuLinks("Мониторинг > Действия с заявками")
                .goTo(actionsRequestsPage)
                .inputTypeOperation("Основные данные. Нажатие кнопки \"Доработка\"")
                .searchClaimOnPage(claim);
        Map<String, String> expectedValues = Map.of(
                "ФИО пользователя", "Автоматическое Тестирование1",
                "Номер заявки", claim,
                "Время", dateTimeNow,
                "ФИО участника сделки", "",
                "Стратегия", "",
                "Тип операции", "Основные данные. Нажатие кнопки \"Доработка\"",
                "Операция", "Основные данные. Нажатие кнопки \"Доработка\"",
                "Начальное значение поля", "",
                "Конечное значение поля", "",
                "Объект проверки", "");
        validateExpectedValues(expectedValues, 1);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "4093082, Основные данные. Нажатие кнопки \"История\" (Комментарий к причине доработки), Кнопка История(Комментарий МРК)",
            "4093092, Основные данные. Нажатие кнопки \"История\" (Внутренний комментарий андеррайтера), Кнопка История(Комментарий андеррайтера)"
    })
    @Tag("actions_with_applications_4093082_4093092")
    @DisplayName("{id} - Мониторинг. Действия с заявками. Андеррайтинг. {operationType}")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void actions_with_applications_4093082_4093092(String id, String operationType, String button) {
        personalAccountPage
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement(button)
                .assertElementByTitleVisibility("Окно История комментариев", "отображается")
                .clickOnElement("Кнопка закрыть Окно История комментариев")
                .closeCurrentTab();
        String dateTimeNow = now().format(DF);
        personalAccountPage.openMenuLinks("Мониторинг > Действия с заявками")
                .goTo(actionsRequestsPage)
                .inputTypeOperation(operationType)
                .searchClaimOnPage(claim);
        Map<String, String> expectedValues = Map.of(
                "ФИО пользователя", "Автоматическое Тестирование1",
                "Номер заявки", claim,
                "Время", dateTimeNow,
                "ФИО участника сделки", "",
                "Стратегия", "",
                "Тип операции", operationType,
                "Операция", operationType,
                "Начальное значение поля", "",
                "Конечное значение поля", "",
                "Объект проверки", "");
        validateExpectedValues(expectedValues, 1);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "4093107, Нажатие кнопки \"Сохранить\" (Внутренний комментарий андеррайтера), Внутренний комментарий андеррайтера, Коммент, " +
                    "Сохранить(Комментарий андеррайтера), '', '', ''",
            "4093202, Занятость подтверждена по номеру телефона, Занятость подтверждена по:, 9999999999, Сохранить и закрыть, " +
                    "Романов Юрий Иванович, '', +79999999999"
    })
    @Tag("actions_with_applications_4093107_4093202")
    @DisplayName("{id} - Мониторинг. Действия с заявками. Андеррайтинг. Основные данные. {operation}")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void actions_with_applications_4093107_4093202(String id, String operation, String fieldName, String fieldValue,
                                                          String button, String fio, String startValue, String endValue, TestInfo testInfo) {
        personalAccountPage
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage);
        if (testInfo.getDisplayName().contains("4093202")) {
            cardRequestPage.clickOnElement("Кнопка редактирования поля Занятость подтверждена по:");
        }
        cardRequestPage.fillInput("Поле ввода " + fieldName, fieldValue)
                .clickOnElement("Кнопка " + button)
                .closeCurrentTab();
        String dateTimeNow = now().format(DF);
        personalAccountPage.openMenuLinks("Мониторинг > Действия с заявками")
                .goTo(actionsRequestsPage)
                .inputTypeOperation("Основные данные. " + operation)
                .searchClaimOnPage(claim);
        Map<String, String> expectedValues = Map.of(
                "ФИО пользователя", "Автоматическое Тестирование1",
                "Номер заявки", claim,
                "Время", dateTimeNow,
                "ФИО участника сделки", fio,
                "Стратегия", "",
                "Тип операции", "Основные данные. " + operation,
                "Операция", "Основные данные. " + operation,
                "Начальное значение поля", startValue,
                "Конечное значение поля", endValue,
                "Объект проверки", "");
        validateExpectedValues(expectedValues, 1);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "4093138, Скоррект. доход Иные доходы, Скоррект. доход Иные доходы, 14000, Пересчитать лимит, Романов Юрий Иванович, -, 14000",
            "4093152, Скоррект. доход Совмест. 1, Скоррект. доход Совмест. 1, 20002, Пересчитать лимит, Романов Юрий Иванович, 20001, 20002",
            "4093184, Нажатие кнопки \"Пересчитать лимит\", Скоррект. доход Иные доходы, 14000, Пересчитать лимит, '', '', ''",
            "4093189, Нажатие кнопки \"Отменить\" (Скоррект. доход), Скоррект. доход Иные доходы, 14000, Отменить, Романов Юрий Иванович, '', ''"
    })
    @Tag("actions_with_applications_4093138_4093152_4093184_4093189")
    @DisplayName("{id} - Мониторинг. Действия с заявками. Андеррайтинг. Основные данные. {operation}")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void actions_with_applications_4093138_4093152_4093184_4093189(String id, String operation, String fieldName, String fieldValue,
                                                                          String button, String fio, String startValue, String endValue, TestInfo testInfo) {
        personalAccountPage
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка редактировать " + fieldName)
                .clearInput("Поле редактировать " + fieldName)
                .clickOnElement("Кнопка редактировать " + fieldName)
                .fillInput("Поле редактировать " + fieldName, fieldValue)
                .clickOnElement("Кнопка " + button);
        if (!testInfo.getDisplayName().contains("4093189")) {
            cardRequestPage.checkElementByTitleContains("Модальное окно Информация об ошибке",
                            "Необходимо заполнить атрибут “Внутренний комментарий андеррайтера”\n" +
                                    "Необходимо заполнить атрибут “Проведенные проверки”\n" +
                                    "Необходимо заполнить атрибут “Занятость подтверждена”")
                    .clickOnElement("Кнопка ОК на модальном окне Информация об ошибке");
        }
        cardRequestPage.closeCurrentTab();
        String dateTimeNow = now().format(DF);
        personalAccountPage.openMenuLinks("Мониторинг > Действия с заявками")
                .goTo(actionsRequestsPage)
                .inputTypeOperation("Основные данные. " + operation)
                .searchClaimOnPage(claim);
        Map<String, String> expectedValues = Map.of(
                "ФИО пользователя", "Автоматическое Тестирование1",
                "Номер заявки", claim,
                "Время", dateTimeNow,
                "ФИО участника сделки", fio,
                "Стратегия", "",
                "Тип операции", "Основные данные. " + operation,
                "Операция", "Основные данные. " + operation,
                "Начальное значение поля", startValue,
                "Конечное значение поля", endValue,
                "Объект проверки", "");
        validateExpectedValues(expectedValues, 1);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "4093212, Документы к заявке, Документы Романов Юрий Иванович",
            "4093215, Автопроверки, Автопроверки",
            "4093222, Изменение полей, Изменения реквизитов заявки",
            "4093231, Отчет Anti FRAUD, Противоречия",
            "4093262, Предыдущие заявки, Предыдущие заявки",
            "4093264, Идеальная КИ, Участники сделки"
    })
    @Tag("actions_with_applications_open_report")
    @DisplayName("{id} - Мониторинг. Действия с заявками. Андеррайтинг. Основные данные. Открытие отчета \"{reportName}\"")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void actions_with_applications_open_report(String id, String reportName, String textOnPage) {
        personalAccountPage
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Ссылка " + reportName)
                .switchToNewTab()
                .goTo(id.equals("4093212") ? applicationDocumentsPage :
                        id.equals("4093215") ? autocheckPage :
                                id.equals("4093222") ? changingFieldsPage :
                                        id.equals("4093231") ? antiFraudPage :
                                                id.equals("4093262") ? previousClaimsPage :
                                                        id.equals("4093264") ? previousClaimsPage : null)
                .waitText(5, textOnPage)
                .closeCurrentTab();
        cardRequestPage.closeCurrentTab();
        String dateTimeNow = now().format(DF);
        personalAccountPage.openMenuLinks("Мониторинг > Действия с заявками")
                .goTo(actionsRequestsPage)
                .inputTypeOperation("Основные данные. Открытие отчета \"" + reportName + "\"")
                .searchClaimOnPage(claim);
        Map<String, String> expectedValues = Map.of(
                "ФИО пользователя", "Автоматическое Тестирование1",
                "Номер заявки", claim,
                "Время", dateTimeNow,
                "ФИО участника сделки", "Романов Юрий Иванович",
                "Стратегия", "",
                "Тип операции", "Основные данные. Открытие отчета \"" + reportName + "\"",
                "Операция", "Основные данные. Открытие отчета \"" + reportName + "\"",
                "Начальное значение поля", "",
                "Конечное значение поля", "",
                "Объект проверки", "");
        validateExpectedValues(expectedValues, 1);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "4094212, основные данные, Информация о кредите",
            "4094214, Результаты проверок, Результаты проверок",
            "4094215, Решение Андеррайтера, Проведенные проверки",
            "4094220, История, История заявки",
            "4094225, Дополнительная информация, Дополнительная информация"
    })
    @Tag("actions_with_applications_open_report")
    @DisplayName("{id} - Мониторинг. Действия с заявками. Переключение на вкладку \"{tabName}\"")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void actions_with_applications_switch_on_tab(String id, String tabName, String textOnPage) {
        personalAccountPage
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage);
        if (id.equals("4094212")) {
            cardRequestPage.clickOnElement("Вкладка Результаты проверок").goTo(resultCheck);
        }
        cardRequestPage.clickOnElement("Вкладка " + tabName)
                .goTo(id.equals("4094212") ? cardRequestPage :
                        id.equals("4094214") ? resultCheck :
                                id.equals("4094215") ? underwriterDecisionPage :
                                        id.equals("4094220") ? historyPage :
                                                id.equals("4094225") ? additionalInformation : null)
                .waitText(5, textOnPage)
                .closeCurrentTab();
        String dateTimeNow = now().format(DF);
        tabName = tabName.substring(0, 1).toUpperCase() + tabName.substring(1);
        personalAccountPage.openMenuLinks("Мониторинг > Действия с заявками")
                .goTo(actionsRequestsPage)
                .inputTypeOperation("Переключение на вкладку \"" + tabName + "\"")
                .searchClaimOnPage(claim);
        Map<String, String> expectedValues = Map.of(
                "ФИО пользователя", "Автоматическое Тестирование1",
                "Номер заявки", claim,
                "Время", dateTimeNow,
                "ФИО участника сделки", "",
                "Стратегия", "",
                "Тип операции", "Переключение на вкладку \"" + tabName + "\"",
                "Операция", "Переключение на вкладку \"" + tabName + "\"",
                "Начальное значение поля", "",
                "Конечное значение поля", "",
                "Объект проверки", "");
        validateExpectedValues(expectedValues, 1);
    }

    @Test
    @Tag("actions_with_applications_4094236")
    @DisplayName("4094236 - Мониторинг. Действия с заявками. Андеррайтинг. Основные данные. Нажатие кнопки \"Доработка\"")
    @WorkItemIds({"4094236"})
    public void actions_with_applications_4094236() {
        personalAccountPage
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .selectValueFromDropDownList("Выпадающий список Полномочия", "Собственные");
        String dateTime = LocalDateTime.now().format(DF);
        underwriterDecisionPage.selectValueFromDropDownList("Выпадающий список Полномочия", "ДЧК");
        String dateTime2 = LocalDateTime.now().format(DF);
        cardRequestPage.closeCurrentTab();
        personalAccountPage.openMenuLinks("Мониторинг > Действия с заявками")
                .goTo(actionsRequestsPage)
                .inputTypeOperation("Решение Андеррайтера. Полномочия")
                .searchClaimOnPage(claim);
        Map<String, String> expectedValues = Map.of(
                "ФИО пользователя", "Автоматическое Тестирование1",
                "Номер заявки", claim,
                "Время", dateTime,
                "ФИО участника сделки", "Романов Юрий Иванович",
                "Стратегия", "",
                "Тип операции", "Решение Андеррайтера. Полномочия",
                "Операция", "Решение Андеррайтера. Полномочия",
                "Начальное значение поля", "",
                "Конечное значение поля", "Собственные",
                "Объект проверки", "");
        Map<String, String> expectedValues2 = Map.of(
                "ФИО пользователя", "Автоматическое Тестирование1",
                "Номер заявки", claim,
                "Время", dateTime2,
                "ФИО участника сделки", "Романов Юрий Иванович",
                "Стратегия", "",
                "Тип операции", "Решение Андеррайтера. Полномочия",
                "Операция", "Решение Андеррайтера. Полномочия",
                "Начальное значение поля", "Собственные",
                "Конечное значение поля", "ДЧК",
                "Объект проверки", "");
        validateExpectedValues(expectedValues, 1);
        validateExpectedValues(expectedValues2, 2);
    }

    @Test
    @Tag("actions_with_applications_4094266")
    @DisplayName("4094266 - Мониторинг. Действия с заявками. Андеррайтинг. Решение Андеррайтера. Тип одобрения/Причина отклонения")
    @WorkItemIds({"4094266"})
    public void actions_with_applications_4094266() {
        personalAccountPage
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .selectValueFromDropDownList("Выпадающий список Одобрить/Отклонить", "Одобрить")
                .selectValueFromDropDownList("Выпадающий список Тип одобрения/Причина отклонения", "Одобрено");
        String dateTime = LocalDateTime.now().format(DF);
        underwriterDecisionPage.selectValueFromDropDownList("Выпадающий список Одобрить/Отклонить", "Отклонить")
                .selectValueFromDropDownList("Выпадающий список Тип одобрения/Причина отклонения", "Кредит на бизнес");
        String dateTime2 = LocalDateTime.now().format(DF);
        cardRequestPage.closeCurrentTab();
        personalAccountPage.openMenuLinks("Мониторинг > Действия с заявками")
                .goTo(actionsRequestsPage)
                .inputTypeOperation("Решение Андеррайтера. Тип одобрения/Причина отклонения")
                .searchClaimOnPage(claim);
        Map<String, String> expectedValues = Map.of(
                "ФИО пользователя", "Автоматическое Тестирование1",
                "Номер заявки", claim,
                "Время", dateTime,
                "ФИО участника сделки", "Романов Юрий Иванович",
                "Стратегия", "",
                "Тип операции", "Решение Андеррайтера. Тип одобрения/Причина отклонения",
                "Операция", "Решение Андеррайтера. Тип одобрения/Причина отклонения",
                "Начальное значение поля", "",
                "Конечное значение поля", "Одобрено",
                "Объект проверки", "");
        Map<String, String> expectedValues2 = Map.of(
                "ФИО пользователя", "Автоматическое Тестирование1",
                "Номер заявки", claim,
                "Время", dateTime2,
                "ФИО участника сделки", "Романов Юрий Иванович",
                "Стратегия", "",
                "Тип операции", "Решение Андеррайтера. Тип одобрения/Причина отклонения",
                "Операция", "Решение Андеррайтера. Тип одобрения/Причина отклонения",
                "Начальное значение поля", "",
                "Конечное значение поля", "Кредит на бизнес",
                "Объект проверки", "");
        validateExpectedValues(expectedValues, 1);
        validateExpectedValues(expectedValues2, 2);
    }

    @Test
    @Tag("actions_with_applications_4094277")
    @DisplayName("4094277 - Мониторинг. Действия с заявками. Андеррайтинг. Решение Андеррайтера. Нажатие кнопки \"Принять решение\"")
    @WorkItemIds({"4094277"})
    public void actions_with_applications_4094277() {
        personalAccountPage
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .selectValueFromDropDownList("Выпадающий список Проведенные проверки", "Проверка критичных данных")
                .clickOnElement("Кнопка Принять решение");
        String dateTime = LocalDateTime.now().format(DF);
        underwriterDecisionPage.checkElementByTitleContains("Модальное окно Информация об ошибке",
                        "Необходимо заполнить атрибут “Внутренний комментарий андеррайтера”\n" +
                                "Необходимо заполнить атрибут “Тип одобрения/Причина отклонения”\n" +
                                "Необходимо заполнить атрибут “Полномочия”\n" +
                                "Необходимо заполнить атрибут “Одобрить/Отклонить”\n" +
                                "Необходимо заполнить атрибут “Занятость подтверждена”")
                .clickOnElement("Кнопка ОК на модальном окне")
                .closeCurrentTab();
        personalAccountPage.openMenuLinks("Мониторинг > Действия с заявками")
                .goTo(actionsRequestsPage)
                .inputTypeOperation("Решение Андеррайтера. Нажатие кнопки \"Принять решение\"")
                .searchClaimOnPage(claim);
        Map<String, String> expectedValues = Map.of(
                "ФИО пользователя", "Автоматическое Тестирование1",
                "Номер заявки", claim,
                "Время", dateTime,
                "ФИО участника сделки", "",
                "Стратегия", "",
                "Тип операции", "Решение Андеррайтера. Нажатие кнопки \"Принять решение\"",
                "Операция", "Решение Андеррайтера. Нажатие кнопки \"Принять решение\"",
                "Начальное значение поля", "",
                "Конечное значение поля", "Заемщик(Романов Юрий Иванович): Проверка критичных данных;",
                "Объект проверки", "");
        validateExpectedValues(expectedValues, 1);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "4094282, На утверждение, Проверка документов",
            "4094393, Доработка, Проверка критичных данных"
    })
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    @Tag("actions_with_applications_4094282_4094393")
    @DisplayName("{id} - Мониторинг. Действия с заявками. Андеррайтинг. Решение Андеррайтера. Нажатие кнопки \"{buttonName}\"")
    public void actions_with_applications_4094282_4094393(String id, String buttonName, String endValue) {
        personalAccountPage
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .selectValueFromDropDownList("Выпадающий список Проведенные проверки", endValue)
                .clickOnElement("Кнопка " + buttonName);
        String dateTime = LocalDateTime.now().format(DF);
        underwriterDecisionPage.checkElementByTitleContains("Модальное окно Информация об ошибке",
                        id.equals("4094282") ? "Необходимо заполнить атрибут “Внутренний комментарий андеррайтера”\n" +
                                "Необходимо заполнить атрибут “Тип одобрения/Причина отклонения”\n" +
                                "Необходимо заполнить атрибут “Полномочия”\n" +
                                "Необходимо заполнить атрибут “Одобрить/Отклонить”\n" +
                                "Необходимо заполнить атрибут “Занятость подтверждена”"
                                : "Необходимо заполнить атрибут “Внутренний комментарий андеррайтера”\n" +
                                "Необходимо заполнить атрибут “Причина доработки”\n" +
                                "Необходимо заполнить атрибут “Комментарий МРК”\n" +
                                "Необходимо заполнить атрибут “Занятость подтверждена”")
                .clickOnElement("Кнопка ОК на модальном окне")
                .closeCurrentTab();
        personalAccountPage.openMenuLinks("Мониторинг > Действия с заявками")
                .goTo(actionsRequestsPage)
                .inputTypeOperation("Решение Андеррайтера. Нажатие кнопки \"" + buttonName + "\"")
                .searchClaimOnPage(claim);
        Map<String, String> expectedValues = Map.of(
                "ФИО пользователя", "Автоматическое Тестирование1",
                "Номер заявки", claim,
                "Время", dateTime,
                "ФИО участника сделки", "",
                "Стратегия", "",
                "Тип операции", "Решение Андеррайтера. Нажатие кнопки \"" + buttonName + "\"",
                "Операция", "Решение Андеррайтера. Нажатие кнопки \"" + buttonName + "\"",
                "Начальное значение поля", "",
                "Конечное значение поля", "Заемщик(Романов Юрий Иванович): " + endValue + ";",
                "Объект проверки", "");
        validateExpectedValues(expectedValues, 1);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "4095724, История заявки",
            "4095726, Версии заявки"
    })
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    @Tag("actions_with_applications_4095724_4095726")
    @DisplayName("{id} - Мониторинг. Действия с заявками. Андеррайтинг. История. Открытие раздела \"{operation}\"")
    public void actions_with_applications_4095724_4095726(String id, String operation) {
        personalAccountPage
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка История")
                .goTo(historyPage)
                .clickOnElement("Вкладка " + operation)
                .assertElementByTitleVisibility("Таблица " + operation, "отображается");
        String dateTime = LocalDateTime.now().format(DF);
        historyPage.closeCurrentTab();
        personalAccountPage.openMenuLinks("Мониторинг > Действия с заявками")
                .goTo(actionsRequestsPage)
                .inputTypeOperation("История. Открытие раздела \"" + operation + "\"")
                .searchClaimOnPage(claim);
        Map<String, String> expectedValues = Map.of(
                "ФИО пользователя", "Автоматическое Тестирование1",
                "Номер заявки", claim,
                "Время", dateTime,
                "ФИО участника сделки", "",
                "Стратегия", "",
                "Тип операции", "История. Открытие раздела \"" + operation + "\"",
                "Операция", "История. Открытие раздела \"" + operation + "\"",
                "Начальное значение поля", "",
                "Конечное значение поля", "",
                "Объект проверки", "");
        validateExpectedValues(expectedValues, 1);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "4095894, Заемщик. Сегмент клиента, Сегмент клиента, Пенсионеры, Прочие клиенты",
            "4095898, Заемщик. Статус Клиента, Статус Клиента, Специалист, ''"
    })
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    @Tag("actions_with_applications_4095894_4095898")
    @DisplayName("{id} - Мониторинг. Действия с заявками. Андеррайтинг. Решение Андеррайтера. \"{operation}\"")
    public void actions_with_applications_4095894_4095898(String id, String operation, String fieldName, String fieldValue,
                                                          String startValue) {
        personalAccountPage
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .selectValueFromDropDownList("Выпадающий список " + fieldName, fieldValue);
        String dateTime = LocalDateTime.now().format(DF);
        historyPage.closeCurrentTab();
        personalAccountPage.openMenuLinks("Мониторинг > Действия с заявками")
                .goTo(actionsRequestsPage)
                .inputTypeOperation("Решение Андеррайтера. " + operation)
                .searchClaimOnPage(claim);
        Map<String, String> expectedValues = Map.of(
                "ФИО пользователя", "Автоматическое Тестирование1",
                "Номер заявки", claim,
                "Время", dateTime,
                "ФИО участника сделки", "Романов Юрий Иванович",
                "Стратегия", "",
                "Тип операции", "Решение Андеррайтера. " + operation,
                "Операция", "Решение Андеррайтера. " + operation,
                "Начальное значение поля", startValue,
                "Конечное значение поля", fieldValue,
                "Объект проверки", "");
        validateExpectedValues(expectedValues, 1);
    }

    private void validateExpectedValues(Map<String, String> expectedValues, int rowNum) {
        //   actionsRequestsPage.checkRowCount("Таблица Действия с заявками", rowNum);
        for (Map.Entry<String, String> expected : expectedValues.entrySet()) {
            String actualValue = actionsRequestsPage.getTextFromTable("Таблица Действия с заявками", rowNum, expected.getKey());
            if (expected.getKey().equals("Время")) {
                actualValue = actualValue.substring(0, 16);
            }
            assertIsTrue(actualValue.equals(expected.getValue()),
                    "Значение столбца " + expected.getKey() + " строки " + rowNum + " должно быть равно " + expected.getValue() + " . Фактическое значение = " + actualValue);
        }
    }

}