package ru.autotestframework.regress.monitoring.actions_with_claims.verification;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Collections.nCopies;
import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;
import static ru.autotestframework.utils.Constants.DF;

@Tag("regress")
@Tag("monitoring")
@Tag("actions_with_claims")
@Tag("verification")
@ClassName("Мониторинг. Действия с заявками. Верификация. На каждый кейс отдельная заявка")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VerificationSeparateClaimTest extends BaseTest {

    @BeforeAll
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
    @Tag("smoke")
    @Tag("logging_check_1720659")
    @DisplayName("1720659 - Проверка логирования типа операции \"Выбор результата проверки\" (для клиента)")
    @WorkItemIds({"1720659"})
    public void logging_check_1720659(TestInfo testInfo) {
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2521438.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(customerCallPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон клиента/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                .selectValueFromDropDownList("Выпадающий список Дополнительное поле результата проверки", "Негатив не выявлен")
                .selectValueFromDropDownList("Выпадающий список Дополнительное поле результата проверки 2", "Негатив отсутствует")
                .clickOnElement("Кнопка Сохранить");
        LocalDateTime dateTimeNow = LocalDateTime.now();
        customerCallPage.selectValueFromDropDownList("Выпадающий список Результат проверки", "Бесконтактное подтверждение")
                .selectValueFromDropDownList("Выпадающий список Дополнительное поле результата проверки", "Социальные сети")
                .fillInput("Поле ввода Источник подтверждения", "Сайт")
                .clickOnElement("Кнопка Далее");
        LocalDateTime dateTimeNow2 = LocalDateTime.now();
        customerCallPage.checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон контактного лица/супруга (-и)/Версия 1")
                .goTo(callContactPersonSpoursePage)
                .clickOnStep("Прозвон клиента")
                .clickOnElement("Кнопка Изменить результат")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                .selectValueFromDropDownList("Выпадающий список Дополнительное поле результата проверки", "Выявлен негатив")
                .selectValueFromDropDownList("Выпадающий список Дополнительное поле результата проверки 2", "Кредит для третьего лица")
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Отложить")
                .selectValueFromDropDownList("Выпадающий список Причина (Перевод заявки в отложенные)", "Недозвон Заемщику")
                .fillInput("Поле ввода комментарий (Перевод заявки в отложенные)", "Тест")
                .fillInput("Поле ввода Время для звонка участнику",
                        dateTimeNow.plusDays(1).withHour(18).withMinute(0).format(DF))
                .clickOnElement("Кнопка Отложить заявку").switchToOneTab();
        LocalDateTime dateTimeNow3 = LocalDateTime.now();
        cardRequestPage.openMenuLinks("Мониторинг > Действия с заявками")
                .goTo(actionsRequestsPage)
                .clickOnElement("Кнопка Сбросить сортировку")
                .fillInput("Поле ввода Номер заявки", claim)
                .inputTypeOperation("Выбор результата проверки")
                .clickOnElement("Кнопка Найти").waitBusyCondition();

        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", new ArrayList<>(nCopies(3, "Автоматическое Тестирование1"))),
                Map.entry("Номер заявки", new ArrayList<>(nCopies(3, claim))),
                Map.entry("Время", List.of(dateTimeNow.format(DF), dateTimeNow2.format(DF), dateTimeNow3.format(DF))),
                Map.entry("ФИО участника сделки", new ArrayList<>(nCopies(3, "Гроза Ольга Ивановна"))),
                Map.entry("Стратегия", new ArrayList<>(nCopies(3, "Прозвон"))),
                Map.entry("Тип операции", new ArrayList<>(nCopies(3, "Выбор результата проверки"))),
                Map.entry("Операция", new ArrayList<>(nCopies(3, "Прозвон клиента. Выбор результата проверки"))),
                Map.entry("Начальное значение поля", List.of("", "Результативный прозвон", "Бесконтактное подтверждение")),
                Map.entry("Конечное значение поля", List.of("Результативный прозвон", "Бесконтактное подтверждение", "Результативный прозвон")),
                Map.entry("Объект проверки", new ArrayList<>(nCopies(3, "Заемщик Гроза Ольга Ивановна"))));
        checkTableMonitoring(expectedValues);
        actionsRequestsPage.clickOnElement("Кнопка Удалить все")
                .goTo(loginPage).openMenuLinks("Личный кабинет");

    }

    @Test
    @Tag("smoke")
    @Tag("logging_check_1720660")
    @DisplayName("1720660 - Проверка логирования типа операции \"Выбор результата проверки\" (для работодателя)")
    @WorkItemIds({"1720660"})
    public void logging_check_1720660(TestInfo testInfo) {
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2521438.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(customerCallPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон клиента/Версия 1")
                .clickOnStep("Прозвон работодателя - любой телефон")
                .clickOnElement("Кнопка Взять шаг в работу")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                .selectValueFromDropDownList("Выпадающий список Дополнительное поле результата проверки", "Негатив не выявлен, все ответы получены")
                .clickOnElement("Кнопка Сохранить");
        LocalDateTime dateTimeNow = LocalDateTime.now();
        customerCallPage.selectValueFromDropDownList("Выпадающий список Результат проверки", "Бесконтактное подтверждение")
                .selectValueFromDropDownList("Выпадающий список Дополнительное поле результата проверки", "Официальный сайт")
                .fillInput("Поле ввода Источник подтверждения", "Сайт")
                .clickOnElement("Кнопка Далее");
        LocalDateTime dateTimeNow2 = LocalDateTime.now();
        customerCallPage.checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон клиента/Версия 1")
                .goTo(callingEmployerAnyPhonePage)
                .clickOnStep("Прозвон работодателя - любой телефон")
                .clickOnElement("Кнопка Изменить результат")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Косвенное подтверждение занятости")
                .selectValueFromDropDownList("Выпадающий список Дополнительное поле результата проверки", "Пункт 1 РА")
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Отложить")
                .selectValueFromDropDownList("Выпадающий список Причина (Перевод заявки в отложенные)", "Недозвон Заемщику")
                .fillInput("Поле ввода комментарий (Перевод заявки в отложенные)", "Тест")
                .fillInput("Поле ввода Время для звонка участнику",
                        dateTimeNow.plusDays(1).withHour(18).withMinute(0).format(DF))
                .clickOnElement("Кнопка Отложить заявку").switchToOneTab();
        LocalDateTime dateTimeNow3 = LocalDateTime.now();
        cardRequestPage.openMenuLinks("Мониторинг > Действия с заявками")
                .goTo(actionsRequestsPage)
                .clickOnElement("Кнопка Сбросить сортировку")
                .fillInput("Поле ввода Номер заявки", claim)
                .inputTypeOperation("Выбор результата проверки")
                .clickOnElement("Кнопка Найти").waitBusyCondition();

        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", new ArrayList<>(nCopies(3, "Автоматическое Тестирование1"))),
                Map.entry("Номер заявки", new ArrayList<>(nCopies(3, claim))),
                Map.entry("Время", List.of(dateTimeNow.format(DF), dateTimeNow2.format(DF), dateTimeNow3.format(DF))),
                Map.entry("ФИО участника сделки", new ArrayList<>(nCopies(3, "Гроза Ольга Ивановна"))),
                Map.entry("Стратегия", new ArrayList<>(nCopies(3, "Прозвон"))),
                Map.entry("Тип операции", new ArrayList<>(nCopies(3, "Выбор результата проверки"))),
                Map.entry("Операция", new ArrayList<>(nCopies(3, "Прозвон работодателя - любой телефон. Выбор результата проверки"))),
                Map.entry("Начальное значение поля", List.of("", "Результативный прозвон", "Бесконтактное подтверждение")),
                Map.entry("Конечное значение поля", List.of("Результативный прозвон", "Бесконтактное подтверждение", "Косвенное подтверждение занятости")),
                Map.entry("Объект проверки", new ArrayList<>(nCopies(3, "Заемщик Гроза Ольга Ивановна. Основное место работы - АО \"Турбонасос\" - ТОП ОПК"))));
        checkTableMonitoring(expectedValues);
        actionsRequestsPage.clickOnElement("Кнопка Удалить все")
                .goTo(loginPage).openMenuLinks("Личный кабинет");

    }

    @Test
    @Tag("smoke")
    @Tag("logging_check_1720661")
    @DisplayName("1720661 - Проверка логирования типа операции \"Выбор результата проверки\" (для контактного лица)")
    @WorkItemIds({"1720661"})
    public void logging_check_1720661(TestInfo testInfo) {
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2521438.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(customerCallPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон клиента/Версия 1")
                .clickOnStep("Прозвон контактного лица/супруга (-и)")
                .goTo(callContactPersonSpoursePage)
                .clickOnElement("Кнопка Взять шаг в работу")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                .selectValueFromDropDownList("Выпадающий список Дополнительное поле результата проверки", "Негатив отсутствует")
                .clickOnElement("Кнопка Сохранить");
        LocalDateTime dateTimeNow = LocalDateTime.now();
        callContactPersonSpoursePage.selectValueFromDropDownList("Выпадающий список Результат проверки", "Нерезультативный прозвон")
                .selectValueFromDropDownList("Выпадающий список Дополнительное поле результата проверки", "Контактное лицо/супруг (-а) не отвечает/недоступен")
                .clickOnElement("Кнопка Далее")
                .goTo(callingEmployerAnyPhonePage);
        LocalDateTime dateTimeNow2 = LocalDateTime.now();
        callingEmployerAnyPhonePage.checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон работодателя - любой телефон/Версия 1")
                .clickOnStep("Прозвон контактного лица/супруга (-и)")
                .goTo(callContactPersonSpoursePage)
                .clickOnElement("Кнопка Изменить результат")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                .selectValueFromDropDownList("Выпадающий список Дополнительное поле результата проверки", "Негатив отсутствует")
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Отложить")
                .selectValueFromDropDownList("Выпадающий список Причина (Перевод заявки в отложенные)", "Недозвон Заемщику")
                .fillInput("Поле ввода комментарий (Перевод заявки в отложенные)", "Тест")
                .fillInput("Поле ввода Время для звонка участнику",
                        dateTimeNow.plusDays(1).withHour(18).withMinute(0).format(DF))
                .clickOnElement("Кнопка Отложить заявку").switchToOneTab();
        LocalDateTime dateTimeNow3 = LocalDateTime.now();
        cardRequestPage.openMenuLinks("Мониторинг > Действия с заявками")
                .goTo(actionsRequestsPage)
                .clickOnElement("Кнопка Сбросить сортировку")
                .fillInput("Поле ввода Номер заявки", claim)
                .inputTypeOperation("Выбор результата проверки")
                .clickOnElement("Кнопка Найти").waitBusyCondition();

        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", new ArrayList<>(nCopies(3, "Автоматическое Тестирование1"))),
                Map.entry("Номер заявки", new ArrayList<>(nCopies(3, claim))),
                Map.entry("Время", List.of(dateTimeNow.format(DF), dateTimeNow2.format(DF), dateTimeNow3.format(DF))),
                Map.entry("ФИО участника сделки", new ArrayList<>(nCopies(3, "Гроза Ольга Ивановна"))),
                Map.entry("Стратегия", new ArrayList<>(nCopies(3, "Прозвон"))),
                Map.entry("Тип операции", new ArrayList<>(nCopies(3, "Выбор результата проверки"))),
                Map.entry("Операция", new ArrayList<>(nCopies(3, "Прозвон контактного лица/супруга (-и). Выбор результата проверки"))),
                Map.entry("Начальное значение поля", List.of("", "Результативный прозвон", "Нерезультативный прозвон")),
                Map.entry("Конечное значение поля", List.of("Результативный прозвон", "Нерезультативный прозвон", "Результативный прозвон")),
                Map.entry("Объект проверки", new ArrayList<>(nCopies(3, "Заемщик Гроза Ольга Ивановна. Супруг(-а) Холодов Данил Ашотович"))));
        checkTableMonitoring(expectedValues);
        actionsRequestsPage.clickOnElement("Кнопка Удалить все")
                .goTo(loginPage).openMenuLinks("Личный кабинет");

    }

    @Test
    @Tag("smoke")
    @Tag("logging_check_1720663")
    @DisplayName("1720663 - Проверка логирования типа операции \"Выбор результата по заявке\" (для клиента)")
    @WorkItemIds({"1720663"})
    public void logging_check_1720663(TestInfo testInfo) {
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2521438.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(customerCallPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон клиента/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Одобрить")
                .selectValueFromDropDownList("Выпадающий список Тип одобрения", "Одобрено")
                .fillInput("Поле ввода Внутренний комментарий", "Комментарий")
                .clickOnElement("Кнопка Сохранить");
        LocalDateTime dateTimeNow = LocalDateTime.now();
        customerCallPage.selectValueFromDropDownList("Выпадающий список Результат по заявке", "Отказать")
                .selectValueFromDropDownList("Выпадающий список Причина отклонения", "Негатив на Клиента")
                .fillInput("Поле ввода Внутренний комментарий", "Комментарий2")
                .clickOnElement("Кнопка Далее");
        LocalDateTime dateTimeNow2 = LocalDateTime.now();
        customerCallPage.clickOnElement("Кнопка Изменить результат")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Одобрить стратегию")
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Отложить")
                .selectValueFromDropDownList("Выпадающий список Причина (Перевод заявки в отложенные)", "Недозвон Заемщику")
                .fillInput("Поле ввода комментарий (Перевод заявки в отложенные)", "Тест")
                .fillInput("Поле ввода Время для звонка участнику",
                        dateTimeNow.plusDays(1).withHour(18).withMinute(0).format(DF))
                .clickOnElement("Кнопка Отложить заявку").switchToOneTab();
        LocalDateTime dateTimeNow3 = LocalDateTime.now();
        cardRequestPage.openMenuLinks("Мониторинг > Действия с заявками")
                .goTo(actionsRequestsPage)
                .clickOnElement("Кнопка Сбросить сортировку")
                .fillInput("Поле ввода Номер заявки", claim)
                .inputTypeOperation("Выбор результата по заявке")
                .clickOnElement("Кнопка Найти").waitBusyCondition();

        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", new ArrayList<>(nCopies(3, "Автоматическое Тестирование1"))),
                Map.entry("Номер заявки", new ArrayList<>(nCopies(3, claim))),
                Map.entry("Время", List.of(dateTimeNow.format(DF), dateTimeNow2.format(DF), dateTimeNow3.format(DF))),
                Map.entry("ФИО участника сделки", new ArrayList<>(nCopies(3, "Гроза Ольга Ивановна"))),
                Map.entry("Стратегия", new ArrayList<>(nCopies(3, "Прозвон"))),
                Map.entry("Тип операции", new ArrayList<>(nCopies(3, "Выбор результата по заявке"))),
                Map.entry("Операция", new ArrayList<>(nCopies(3, "Прозвон клиента. Выбор результата по заявке"))),
                Map.entry("Начальное значение поля", List.of("", "Одобрить", "Отказать")),
                Map.entry("Конечное значение поля", List.of("Одобрить", "Отказать", "Одобрить стратегию")),
                Map.entry("Объект проверки", new ArrayList<>(nCopies(3, "Заемщик Гроза Ольга Ивановна"))));
        checkTableMonitoring(expectedValues);
        actionsRequestsPage.clickOnElement("Кнопка Удалить все")
                .goTo(loginPage).openMenuLinks("Личный кабинет");

    }

    private void checkTableMonitoring(Map<String, List<String>> expectedValues) {
        for (Map.Entry<String, List<String>> expected : expectedValues.entrySet()) {
            List<String> actualValuesCells = actionsRequestsPage.getListValuesByColumnName("Таблица Действия с заявками", expected.getKey());
            List<String> expectedValuesCells = expected.getValue();
            for (int i = 0; i < expectedValuesCells.size(); i++) {
                String actualValue = actualValuesCells.get(i).trim();
                String expectedValue = expectedValuesCells.get(i).trim();
                if ("Время".equals(expected.getKey())) {
                    actualValue = actualValue.substring(0, 16);
                    expectedValue = expectedValue.substring(0, 16);
                }

                assertIsTrue(expectedValue.equals(actualValue),
                        "Значение столбца " + expected.getKey() + " строки " + (i + 1) +
                                " должно быть равно " + expectedValue + " . Фактическое значение = " + actualValue);
            }
        }
    }
}
