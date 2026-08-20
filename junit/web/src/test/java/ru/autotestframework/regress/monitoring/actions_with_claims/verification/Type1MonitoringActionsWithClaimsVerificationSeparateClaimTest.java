package ru.autotestframework.regress.monitoring.actions_with_claims.verification;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.autotestframework.BaseTest;
import ru.autotestframework.pages.card_request.verification.CustomerCallPage;
import ru.autotestframework.pages.monitoring.ActionsRequestsPage;
import ru.autotestframework.ui_core.exceptions.ElementInteractionException;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.ExternalId;
import ru.psb.testit.annotations.WorkItemIds;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.nCopies;
import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;
import static ru.autotestframework.utils.Constants.DF;

@Tag("regress")
@Tag("monitoring")
@Tag("actions_with_claims")
@Tag("verification")
@Tag("type1_monitoring_actions_with_claims_verification_separate_claim")
@ClassName("Тип 1. Мониторинг. Действия с заявками. Верификация. На каждый кейс отдельная заявка")
public class Type1MonitoringActionsWithClaimsVerificationSeparateClaimTest extends BaseTest {

    private String claim;

    @BeforeAll
    public void login() {
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi();
        }
    }

    @BeforeEach
    public void goToPersonalAccountPage(TestInfo testInfo) {
        loginPage.openMenuLinks("Личный кабинет")
                .goTo(personalAccountPage);
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2521438.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
    }

    @AfterAll
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "4026684; контактного лица; Прозвон контактного лица/супруга (-и); Заемщик Гроза Ольга Ивановна. Супруг(-а) Холодов Данил Ашотович",
            "4026727; работодателя; Прозвон работодателя - любой телефон; Заемщик Гроза Ольга Ивановна. Основное место работы - АО \"Турбонасос\" - ТОП ОПК",
            "4026721; клиента; Прозвон клиента; Заемщик Гроза Ольга Ивановна"
    })
    @Tag("logging_check_commentary_for_mrk_4026684_4026727_4026721")
    @DisplayName("{id} - Проверка логирования типа операции \"Комментарий для МРК\" (для {displayName})")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void logging_check_commentary_for_mrk_4026684_4026727_4026721(String id, String displayName, String step, String checkObject) {
        goToClaim();
        if (id.equals("4026684") || id.equals("4026727")) {
            customerCallPage.clickOnStep(step)
                    .clickOnElement("Кнопка Взять шаг в работу")
                    .waitBusyCondition();
        }
        customerCallPage.checkStepActivity(step)
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Одобрить (контролируемая сделка)")
                .fillInput("Поле ввода Внутренний комментарий", "любое значение")
                .fillInput("Поле ввода Комментарий для МРК", "001")
                .clickOnElement("Кнопка Сохранить");
        LocalDateTime dateTimeNow = LocalDateTime.now();
        customerCallPage.fillInput("Поле ввода Комментарий для МРК", "Тест");
        postponeClaim();
        LocalDateTime dateTimeNow2 = LocalDateTime.now();
        customerCallPage.switchToOneTab()
                .goTo(personalAccountPage)
                .clickOnElement("Кнопка раскрыть таблицу Отложено")
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(customerCallPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", step + "/Версия 1")
                .clickOnElement("Кнопка Взять в работу")
                .waitBusyCondition()
                .fillInput("Поле ввода Комментарий для МРК", "002")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку");
        LocalDateTime dateTimeNow3 = LocalDateTime.now();
        goToActionsRequestsPage("Комментарий для МРК");
        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", new ArrayList<>(nCopies(3, "Автоматическое Тестирование1"))),
                Map.entry("Номер заявки", new ArrayList<>(nCopies(3, claim))),
                Map.entry("Время", List.of(dateTimeNow.format(DF), dateTimeNow2.format(DF), dateTimeNow3.format(DF))),
                Map.entry("ФИО участника сделки", new ArrayList<>(nCopies(3, "Гроза Ольга Ивановна"))),
                Map.entry("Стратегия", new ArrayList<>(nCopies(3, "Прозвон"))),
                Map.entry("Тип операции", new ArrayList<>(nCopies(3, "Комментарий для МРК"))),
                Map.entry("Операция", new ArrayList<>(nCopies(3, step + ". Комментарий для МРК"))),
                Map.entry("Начальное значение поля", List.of("", "001", "Тест")),
                Map.entry("Конечное значение поля", List.of("001", "Тест", "002")),
                Map.entry("Объект проверки", new ArrayList<>(nCopies(3, checkObject))));
        checkTableMonitoring(expectedValues);
        actionsRequestsPage.clickOnElement("Кнопка Удалить все");
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "4026742; контактного лица; Прозвон контактного лица/супруга (-и); Заемщик Гроза Ольга Ивановна. Супруг(-а) Холодов Данил Ашотович",
            "4026669; работодателя; Прозвон работодателя - любой телефон;  Заемщик Гроза Ольга Ивановна. Основное место работы - АО \"Турбонасос\" - ТОП ОПК",
            "4026628; клиента; Прозвон клиента; Заемщик Гроза Ольга Ивановна"
    })
    @Tag("logging_check_internal_commentary_4026742_4026669_4026628")
    @DisplayName("{id} - Проверка логирования типа операции \"Внутренний комментарий\" (для {displayName})")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void logging_check_internal_commentary_4026742_4026669_4026628(String id, String displayName, String step, String checkObject) {
        goToClaim();
        if (id.equals("4026742") || id.equals("4026669")) {
            customerCallPage.clickOnStep(step)
                    .clickOnElement("Кнопка Взять шаг в работу")
                    .waitBusyCondition();
        }
        customerCallPage.checkStepActivity(step)
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Одобрить")
                .selectValueFromDropDownList("Выпадающий список Тип одобрения", "Одобрено")
                .fillInput("Поле ввода Внутренний комментарий", "001")
                .clickOnElement("Кнопка Сохранить");
        LocalDateTime dateTimeNow = LocalDateTime.now();
        customerCallPage.fillInput("Поле ввода Внутренний комментарий", "Тест");
        postponeClaim();
        LocalDateTime dateTimeNow2 = LocalDateTime.now();
        customerCallPage.switchToOneTab()
                .goTo(personalAccountPage)
                .clickOnElement("Кнопка раскрыть таблицу Отложено")
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(customerCallPage)
                .waitBusyCondition()
                .checkElementByTitleEquals("Поле Наименование стратегии", step + "/Версия 1")
                .clickOnElement("Кнопка Взять в работу")
                .waitBusyCondition()
                .fillInput("Поле ввода Внутренний комментарий", "002")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку");
        LocalDateTime dateTimeNow3 = LocalDateTime.now();
        goToActionsRequestsPage("Внутренний комментарий");
        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", new ArrayList<>(nCopies(3, "Автоматическое Тестирование1"))),
                Map.entry("Номер заявки", new ArrayList<>(nCopies(3, claim))),
                Map.entry("Время", List.of(dateTimeNow.format(DF), dateTimeNow2.format(DF), dateTimeNow3.format(DF))),
                Map.entry("ФИО участника сделки", new ArrayList<>(nCopies(3, "Гроза Ольга Ивановна"))),
                Map.entry("Стратегия", new ArrayList<>(nCopies(3, "Прозвон"))),
                Map.entry("Тип операции", new ArrayList<>(nCopies(3, "Внутренний комментарий"))),
                Map.entry("Операция", new ArrayList<>(nCopies(3, step + ". Внутренний комментарий"))),
                Map.entry("Начальное значение поля", List.of("", "001", "Тест")),
                Map.entry("Конечное значение поля", List.of("001", "Тест", "002")),
                Map.entry("Объект проверки", new ArrayList<>(nCopies(3, checkObject))));
        checkTableMonitoring(expectedValues);
        actionsRequestsPage.clickOnElement("Кнопка Удалить все");
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "4026522; результативного; контактного лица; Прозвон контактного лица/супруга (-и); Результативный прозвон; Выявлен негатив; Негатив отсутствует; Отказ контактного лица/супруга (-и) предоставить информацию; Заемщик Гроза Ольга Ивановна. Супруг(-а) Холодов Данил Ашотович",
            "4026584; нерезультативного; контактного лица; Прозвон контактного лица/супруга (-и); Нерезультативный прозвон; Контактное лицо/супруг (-а) не отвечает/недоступен; Контактное лицо/супруг (-а) просит перезвонить; Контактное лицо/супруг (-а) просит перезвонить через длительный промежуток времени; Заемщик Гроза Ольга Ивановна. Супруг(-а) Холодов Данил Ашотович",
            "4026468; результативного; клиента; Прозвон клиента; Результативный прозвон; Негатив не выявлен; Клиент Заявку не подавал или подавал Заявку через посредников; Заявка не актуальна; Заемщик Гроза Ольга Ивановна"
    })
    @Tag("logging_check_choosing_the_reason_4026522_4026584_4026468")
    @DisplayName("{id} - Проверка логирования типа операции \"Выбор причины {displayName} прозвона\" (для {displayName2}})")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void logging_check_choosing_the_reason_4026522_4026584_4026468(String id, String displayName, String displayName2, String step, String checkResultValue, String addValue1, String addValue2, String addValue3, String checkObject) {
        goToClaim();
        if (id.equals("4026522") || id.equals("4026584")) {
            customerCallPage.clickOnStep(step)
                    .clickOnElement("Кнопка Взять шаг в работу")
                    .waitBusyCondition();
        }
        customerCallPage.checkStepActivity(step)
                .selectValueFromDropDownList("Выпадающий список Результат проверки", checkResultValue)
                .selectValueFromDropDownList("Выпадающий список Дополнительное поле результата проверки", addValue1)
                .clickOnElement("Кнопка Сохранить");
        LocalDateTime dateTimeNow = LocalDateTime.now();
        customerCallPage.selectValueFromDropDownList("Выпадающий список Дополнительное поле результата проверки", addValue2)
                .clickOnElement("Кнопка Далее");
        LocalDateTime dateTimeNow2 = LocalDateTime.now();
        if (id.equals("4026522") || id.equals("4026584")) {
            customerCallPage.clickOnStep(step)
                    .clickOnElement("Кнопка Взять шаг в работу")
                    .waitBusyCondition();
        }
        customerCallPage.clickOnElement("Кнопка Изменить результат")
                .waitBusyCondition()
                .selectValueFromDropDownList("Выпадающий список Дополнительное поле результата проверки", addValue3);
        postponeClaim();
        LocalDateTime dateTimeNow3 = LocalDateTime.now();
        goToActionsRequestsPage("Выбор причины " + displayName + " прозвона");
        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", new ArrayList<>(nCopies(3, "Автоматическое Тестирование1"))),
                Map.entry("Номер заявки", new ArrayList<>(nCopies(3, claim))),
                Map.entry("Время", List.of(dateTimeNow.format(DF), dateTimeNow2.format(DF), dateTimeNow3.format(DF))),
                Map.entry("ФИО участника сделки", new ArrayList<>(nCopies(3, "Гроза Ольга Ивановна"))),
                Map.entry("Стратегия", new ArrayList<>(nCopies(3, "Прозвон"))),
                Map.entry("Тип операции", new ArrayList<>(nCopies(3, "Выбор причины " + displayName + " прозвона"))),
                Map.entry("Операция", new ArrayList<>(nCopies(3, step + ". Выбор причины " + displayName + " прозвона"))),
                Map.entry("Начальное значение поля", List.of("", addValue1, addValue2)),
                Map.entry("Конечное значение поля", List.of(addValue1, addValue2, addValue3)),
                Map.entry("Объект проверки", new ArrayList<>(nCopies(3, checkObject))));
        checkTableMonitoring(expectedValues);
        actionsRequestsPage.clickOnElement("Кнопка Удалить все");
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "4026478; результативного; работодателя; Прозвон работодателя - любой телефон; Результативный прозвон; Выявлен негатив; Негатив не выявлен, все ответы получены; Заемщик Гроза Ольга Ивановна. Основное место работы - АО \"Турбонасос\" - ТОП ОПК",
            "4026578; нерезультативного; работодателя; Прозвон работодателя - любой телефон; Нерезультативный прозвон; Работодатель не отвечает/недоступен; Представитель работодателя просит перезвонить; Заемщик Гроза Ольга Ивановна. Основное место работы - АО \"Турбонасос\" - ТОП ОПК",
            "4026570; нерезультативного; клиента; Прозвон клиента; Нерезультативный прозвон; Клиент не отвечает/недоступен; Клиент просит перезвонить; Заемщик Гроза Ольга Ивановна"
    })
    @Tag("logging_check_choosing_the_reason_4026478_4026578_4026570")
    @DisplayName("{id} - Проверка логирования типа операции \"Выбор причины {displayName} прозвона\" (для {displayName2}})")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void logging_check_choosing_the_reason_4026478_4026578_4026570(String id, String displayName, String displayName2, String step, String checkResultValue, String addValue1, String addValue2, String checkObject) {
        goToClaim();
        if (id.equals("4026478") || id.equals("4026578")) {
            customerCallPage.clickOnStep(step)
                    .clickOnElement("Кнопка Взять шаг в работу")
                    .waitBusyCondition();
        }
        customerCallPage.checkStepActivity(step)
                .selectValueFromDropDownList("Выпадающий список Результат проверки", checkResultValue)
                .selectValueFromDropDownList("Выпадающий список Дополнительное поле результата проверки", addValue1)
                .clickOnElement("Кнопка Сохранить");
        LocalDateTime dateTimeNow = LocalDateTime.now();
        customerCallPage.selectValueFromDropDownList("Выпадающий список Дополнительное поле результата проверки", addValue2);
        postponeClaim();
        LocalDateTime dateTimeNow2 = LocalDateTime.now();
        goToActionsRequestsPage("Выбор причины " + displayName + " прозвона");
        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", new ArrayList<>(nCopies(2, "Автоматическое Тестирование1"))),
                Map.entry("Номер заявки", new ArrayList<>(nCopies(2, claim))),
                Map.entry("Время", List.of(dateTimeNow.format(DF), dateTimeNow2.format(DF))),
                Map.entry("ФИО участника сделки", new ArrayList<>(nCopies(2, "Гроза Ольга Ивановна"))),
                Map.entry("Стратегия", new ArrayList<>(nCopies(2, "Прозвон"))),
                Map.entry("Тип операции", new ArrayList<>(nCopies(2, "Выбор причины " + displayName + " прозвона"))),
                Map.entry("Операция", new ArrayList<>(nCopies(2, step + ". Выбор причины " + displayName + " прозвона"))),
                Map.entry("Начальное значение поля", List.of("", addValue1)),
                Map.entry("Конечное значение поля", List.of(addValue1, addValue2)),
                Map.entry("Объект проверки", new ArrayList<>(nCopies(2, checkObject))));
        checkTableMonitoring(expectedValues);
        actionsRequestsPage.clickOnElement("Кнопка Удалить все");
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "1720695; клиента; Прозвон работодателя - любой телефон; Прозвон клиента; Заемщик Гроза Ольга Ивановна",
            "1720696; работодателя; Прозвон работодателя - любой телефон; Прозвон работодателя - любой телефон; Заемщик Гроза Ольга Ивановна. Основное место работы - АО \"Турбонасос\" - ТОП ОПК",
            "1720697; контактного лица; Прозвон контактного лица/супруга (-и); Прозвон контактного лица/супруга (-и); Заемщик Гроза Ольга Ивановна. Супруг(-а) Холодов Данил Ашотович"
    })
    @Tag("logging_check_take_step_in_work_1720695_1720696_1720697")
    @DisplayName("{id} - Проверка логирования типа операции \"Нажатие кнопки \"Взять шаг в работу\"\" (для {displayName})")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void logging_check_take_step_in_work_1720695_1720696_1720697(String id, String displayName, String step, String operation, String checkObject) {
        goToClaim().clickOnStep(step)
                .clickOnElement("Кнопка Взять шаг в работу")
                .waitBusyCondition()
                .checkStepActivity(step);
        if (id.equals("1720695")) {
            customerCallPage.waitBusyCondition()
                    .clickOnStep("Прозвон клиента")
                    .clickOnElement("Кнопка Взять шаг в работу")
                    .waitBusyCondition()
                    .checkStepActivity("Прозвон клиента");
        }
        LocalDateTime dateTimeNow = LocalDateTime.now();
        customerCallPage.closeCurrentTab();
        goToActionsRequestsPage("Нажатие кнопки \"Взять шаг в работу\"");
        Map<String, String> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", "Автоматическое Тестирование1"),
                Map.entry("Номер заявки", claim),
                Map.entry("Время", dateTimeNow.format(DF)),
                Map.entry("ФИО участника сделки", "Гроза Ольга Ивановна"),
                Map.entry("Стратегия", "Прозвон"),
                Map.entry("Тип операции", "Нажатие кнопки \"Взять шаг в работу\""),
                Map.entry("Операция", operation + ". Нажатие кнопки \"Взять шаг в работу\""),
                Map.entry("Начальное значение поля", ""),
                Map.entry("Конечное значение поля", ""),
                Map.entry("Объект проверки", checkObject));
        for (Map.Entry<String, String> expected : expectedValues.entrySet()) {
            List<String> actualValuesCells = actionsRequestsPage.getListValuesByColumnName("Таблица Действия с заявками", expected.getKey());
            String expectedValuesCells = expected.getValue();
            String actualValue = actualValuesCells.get(actualValuesCells.size() - 1).trim();
            String expectedValue = expectedValuesCells.trim();
            if ("Время".equals(expected.getKey())) {
                actualValue = actualValue.substring(0, 16);
                expectedValue = expectedValue.substring(0, 16);
            }
            assertIsTrue(expectedValue.equals(actualValue),
                    "Значение столбца " + expected.getKey() + " строки 2" +
                            " должно быть равно " + expectedValue + " . Фактическое значение = " + actualValue);
        }
        actionsRequestsPage.clickOnElement("Кнопка Удалить все");
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "4018465; работодателя; Прозвон работодателя - любой телефон; Заемщик Гроза Ольга Ивановна. Основное место работы - АО \"Турбонасос\" - ТОП ОПК",
            "4018521; контактного лица; Прозвон контактного лица/супруга (-и); Заемщик Гроза Ольга Ивановна. Супруг(-а) Холодов Данил Ашотович",
            "4018442; клиента; Прозвон клиента; Заемщик Гроза Ольга Ивановна"
    })
    @Tag("logging_check_result_on_claim_choose_approval_type_4018465_4018521_4018442")
    @DisplayName("{id} - Проверка логирования типа операции \"Результат по заявке. Выбор типа одобрения\" (для {displayName})")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void logging_check_result_on_claim_choose_approval_type_4018465_4018521_4018442(String id, String displayName, String step, String checkObject) {
        goToClaim();
        if (id.equals("4018465") || id.equals("4018521")) {
            customerCallPage.clickOnStep(step)
                    .clickOnElement("Кнопка Взять шаг в работу")
                    .waitBusyCondition();
        }
        customerCallPage.checkStepActivity(step)
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Одобрить")
                .selectValueFromDropDownList("Выпадающий список Тип одобрения", "Одобрено")
                .fillInput("Поле ввода Внутренний комментарий", "любое значение")
                .clickOnElement("Кнопка Сохранить");
        LocalDateTime dateTimeNow = LocalDateTime.now();
        customerCallPage.selectValueFromDropDownList("Выпадающий список Тип одобрения", "Одобрено с отлагательным условием по кредиту")
                .fillInput("Поле ввода Комментарий для МРК", "любое значение")
                .clickOnElement("Кнопка Далее");
        LocalDateTime dateTimeNow2 = LocalDateTime.now();
        customerCallPage.clickOnElement("Кнопка Изменить результат")
                .selectValueFromDropDownList("Выпадающий список Тип одобрения", "Одобрено с отлагательным условием");
        postponeClaim();
        LocalDateTime dateTimeNow3 = LocalDateTime.now();
        goToActionsRequestsPage("Результат по заявке. Выбор типа одобрения");
        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", new ArrayList<>(nCopies(3, "Автоматическое Тестирование1"))),
                Map.entry("Номер заявки", new ArrayList<>(nCopies(3, claim))),
                Map.entry("Время", List.of(dateTimeNow.format(DF), dateTimeNow2.format(DF), dateTimeNow3.format(DF))),
                Map.entry("ФИО участника сделки", new ArrayList<>(nCopies(3, "Гроза Ольга Ивановна"))),
                Map.entry("Стратегия", new ArrayList<>(nCopies(3, "Прозвон"))),
                Map.entry("Тип операции", new ArrayList<>(nCopies(3, "Результат по заявке. Выбор типа одобрения"))),
                Map.entry("Операция", new ArrayList<>(nCopies(3, step + ". Результат по заявке. Выбор типа одобрения"))),
                Map.entry("Начальное значение поля", List.of("", "Одобрено", "Одобрено с отлагательным условием по кредиту")),
                Map.entry("Конечное значение поля", List.of("Одобрено", "Одобрено с отлагательным условием по кредиту", "Одобрено с отлагательным условием")),
                Map.entry("Объект проверки", new ArrayList<>(nCopies(3, checkObject))));
        checkTableMonitoring(expectedValues);
        actionsRequestsPage.clickOnElement("Кнопка Удалить все");
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "4018598; работодателя; Прозвон работодателя - любой телефон; Заемщик Гроза Ольга Ивановна. Основное место работы - АО \"Турбонасос\" - ТОП ОПК",
            "4018628; контактного лица; Прозвон контактного лица/супруга (-и); Заемщик Гроза Ольга Ивановна. Супруг(-а) Холодов Данил Ашотович",
            "4018543; клиента; Прозвон клиента; Заемщик Гроза Ольга Ивановна"
    })
    @Tag("logging_check_result_on_claim_choose_deny_reason_4018598_4018628_4018543")
    @DisplayName("{id} - Проверка логирования типа операции \"Результат по заявке. Выбор причины отклонения\" (для {displayName})")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void logging_check_result_on_claim_choose_deny_reason_4018598_4018628_4018543(String id, String displayName, String step, String checkObject) {
        goToClaim();
        if (id.equals("4018598") || id.equals("4018628")) {
            customerCallPage.clickOnStep(step)
                    .clickOnElement("Кнопка Взять шаг в работу")
                    .waitBusyCondition();
        }
        customerCallPage.checkStepActivity(step)
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Отказать")
                .selectValueFromDropDownList("Выпадающий список Причина отклонения", "Признаки предоставления недостоверных сведений о трудоустройстве", true)
                .fillInput("Поле ввода Внутренний комментарий", "любое значение")
                .clickOnElement("Кнопка Сохранить");
        LocalDateTime dateTimeNow = LocalDateTime.now();
        customerCallPage.selectValueFromDropDownList("Выпадающий список Причина отклонения", "Трудоустройство не по найму / временная работа", true)
                .clickOnElement("Кнопка Далее");
        LocalDateTime dateTimeNow2 = LocalDateTime.now();
        customerCallPage.clickOnElement("Кнопка Изменить результат")
                .selectValueFromDropDownList("Выпадающий список Причина отклонения", "Ликвидация/банкротство/негатив на работодателя", true);
        postponeClaim();
        LocalDateTime dateTimeNow3 = LocalDateTime.now();
        goToActionsRequestsPage("Результат по заявке. Выбор причины отклонения");
        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", new ArrayList<>(nCopies(3, "Автоматическое Тестирование1"))),
                Map.entry("Номер заявки", new ArrayList<>(nCopies(3, claim))),
                Map.entry("Время", List.of(dateTimeNow.format(DF), dateTimeNow2.format(DF), dateTimeNow3.format(DF))),
                Map.entry("ФИО участника сделки", new ArrayList<>(nCopies(3, "Гроза Ольга Ивановна"))),
                Map.entry("Стратегия", new ArrayList<>(nCopies(3, "Прозвон"))),
                Map.entry("Тип операции", new ArrayList<>(nCopies(3, "Результат по заявке. Выбор причины отклонения"))),
                Map.entry("Операция", new ArrayList<>(nCopies(3, step + ". Результат по заявке. Выбор причины отклонения"))),
                Map.entry("Начальное значение поля", List.of("", "Признаки предоставления недостоверных сведений о трудоустройстве", "Трудоустройство не по найму / временная работа")),
                Map.entry("Конечное значение поля", List.of("Признаки предоставления недостоверных сведений о трудоустройстве", "Трудоустройство не по найму / временная работа", "Ликвидация/банкротство/негатив на работодателя")),
                Map.entry("Объект проверки", new ArrayList<>(nCopies(3, checkObject))));
        checkTableMonitoring(expectedValues);
        actionsRequestsPage.clickOnElement("Кнопка Удалить все");
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "4041468; работодателя; Прозвон работодателя - любой телефон; Неполный пакет документов (ошибка МРК); Заемщик Гроза Ольга Ивановна. Основное место работы - АО \"Турбонасос\" - ТОП ОПК",
            "4041535; контактного лица; Прозвон контактного лица/супруга (-и); Недозвон с запросом документов; Заемщик Гроза Ольга Ивановна. Супруг(-а) Холодов Данил Ашотович",
            "1723747; клиента; Прозвон клиента; Недозвон; Заемщик Гроза Ольга Ивановна"
    })
    @Tag("logging_check_send_on_work_after_call_4041468_4041535_1723747")
    @DisplayName("{id} - Проверка логирования типа операции \"Отправка на доработку после прозвона. Выбор причины доработки\" (для {displayName})")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void logging_check_send_on_work_after_call_4041468_4041535_1723747(String id, String displayName, String step, String reason, String checkObject) {
        goToClaim();
        if (id.equals("4041468") || id.equals("4041535")) {
            customerCallPage.clickOnStep(step)
                    .clickOnElement("Кнопка Взять шаг в работу")
                    .waitBusyCondition();
        }
        customerCallPage.checkStepActivity(step)
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Отправить на доработку после прозвона")
                .selectValueFromDropDownList("Выпадающий список Причина доработки", reason)
                .fillInput("Поле ввода Внутренний комментарий", "любое значение")
                .fillInput("Поле ввода Комментарий для МРК", "любое значение")
                .clickOnElement(id.equals("4041468") ? "Кнопка Далее" : "Кнопка Сохранить");
        LocalDateTime dateTimeNow = LocalDateTime.now();
        customerCallPage.waitBusyCondition()
                .closeCurrentTab();
        goToActionsRequestsPage("Отправка на доработку. Выбор причины доработки");
        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", List.of("Автоматическое Тестирование1")),
                Map.entry("Номер заявки", List.of(claim)),
                Map.entry("Время", List.of(dateTimeNow.format(DF))),
                Map.entry("ФИО участника сделки", List.of("Гроза Ольга Ивановна")),
                Map.entry("Стратегия", List.of("Прозвон")),
                Map.entry("Тип операции", List.of("Отправка на доработку. Выбор причины доработки")),
                Map.entry("Операция", List.of(step + ". Отправка на доработку. Выбор причины доработки")),
                Map.entry("Начальное значение поля", List.of("")),
                Map.entry("Конечное значение поля", List.of(reason)),
                Map.entry("Объект проверки", List.of(checkObject)));
        checkTableMonitoring(expectedValues);
        actionsRequestsPage.clickOnElement("Кнопка Удалить все");
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "1720673; работодателя; Прозвон работодателя - любой телефон; Отказать; Заемщик Гроза Ольга Ивановна. Основное место работы - АО \"Турбонасос\" - ТОП ОПК",
            "1720674; контактного лица; Прозвон контактного лица/супруга (-и); Отказать; Заемщик Гроза Ольга Ивановна. Супруг(-а) Холодов Данил Ашотович",
            "1720672; клиента; Прозвон клиента; Одобрить; Заемщик Гроза Ольга Ивановна"
    })
    @Tag("logging_check_press_button_change_result_1720673_1720674_1720672")
    @DisplayName("{id} - Проверка логирования типа операции \"Нажатие кнопки \"Изменить результат\"\" (для {displayName})")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void logging_check_press_button_change_result_1720673_1720674_1720672(String id, String displayName, String step, String claimResult, String checkObject) {
        goToClaim();
        if (id.equals("1720673") || id.equals("1720674")) {
            customerCallPage.clickOnStep(step)
                    .clickOnElement("Кнопка Взять шаг в работу")
                    .waitBusyCondition();
        }
        customerCallPage.checkStepActivity(step)
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", claimResult);
        if (id.equals("1720673") || id.equals("1720674")) {
            customerCallPage.selectValueFromDropDownList("Выпадающий список Причина отклонения", "Негатив на Клиента", true);
        } else {
            customerCallPage.selectValueFromDropDownList("Выпадающий список Тип одобрения", "Одобрено");
        }
        customerCallPage.fillInput("Поле ввода Внутренний комментарий", "любое значение")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Изменить результат");
        LocalDateTime dateTimeNow = LocalDateTime.now();
        customerCallPage.waitBusyCondition()
                .closeCurrentTab();
        goToActionsRequestsPage("Нажатие кнопки \"Изменить результат\"");
        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", List.of("Автоматическое Тестирование1")),
                Map.entry("Номер заявки", List.of(claim)),
                Map.entry("Время", List.of(dateTimeNow.format(DF))),
                Map.entry("ФИО участника сделки", List.of("Гроза Ольга Ивановна")),
                Map.entry("Стратегия", List.of("Прозвон")),
                Map.entry("Тип операции", List.of("Нажатие кнопки \"Изменить результат\"")),
                Map.entry("Операция", List.of(step + ". Нажатие кнопки \"Изменить результат\"")),
                Map.entry("Начальное значение поля", List.of("")),
                Map.entry("Конечное значение поля", List.of("")),
                Map.entry("Объект проверки", List.of(checkObject)));
        checkTableMonitoring(expectedValues);
        actionsRequestsPage.clickOnElement("Кнопка Удалить все");
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "1720676; работодателя; Прозвон работодателя - любой телефон; Заемщик Гроза Ольга Ивановна. Основное место работы - АО \"Турбонасос\" - ТОП ОПК",
            "1720677; контактного лица; Прозвон контактного лица/супруга (-и); Заемщик Гроза Ольга Ивановна. Супруг(-а) Холодов Данил Ашотович",
            "1720675; клиента; Прозвон клиента; Заемщик Гроза Ольга Ивановна"
    })
    @Tag("logging_check_press_button_change_result_1720673_1720674_1720672")
    @DisplayName("{id} - Проверка логирования типа операции \"Нажатие кнопки \"Далее\"\" (для {displayName})")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void logging_check_press_button_continue_1720676_1720676_1720675(String id, String displayName, String step, String checkObject) {
        goToClaim();
        if (id.equals("1720676") || id.equals("1720677")) {
            customerCallPage.clickOnStep(step)
                    .clickOnElement("Кнопка Взять шаг в работу")
                    .waitBusyCondition();
        }
        customerCallPage.checkStepActivity(step)
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Отказать")
                .selectValueFromDropDownList("Выпадающий список Причина отклонения", "Негатив на Клиента", true)
                .fillInput("Поле ввода Внутренний комментарий", "любое значение")
                .clickOnElement("Кнопка Далее");
        LocalDateTime dateTimeNow = LocalDateTime.now();
        customerCallPage.waitBusyCondition()
                .closeCurrentTab();
        goToActionsRequestsPage("Нажатие кнопки \"Далее\"");
        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", List.of("Автоматическое Тестирование1")),
                Map.entry("Номер заявки", List.of(claim)),
                Map.entry("Время", List.of(dateTimeNow.format(DF))),
                Map.entry("ФИО участника сделки", List.of("Гроза Ольга Ивановна")),
                Map.entry("Стратегия", List.of("Прозвон")),
                Map.entry("Тип операции", List.of("Нажатие кнопки \"Далее\"")),
                Map.entry("Операция", List.of(step + ". Нажатие кнопки \"Далее\"")),
                Map.entry("Начальное значение поля", List.of("")),
                Map.entry("Конечное значение поля", List.of("")),
                Map.entry("Объект проверки", List.of(checkObject)));
        checkTableMonitoring(expectedValues);
        actionsRequestsPage.clickOnElement("Кнопка Удалить все");
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "4041633; работодателя; Прозвон работодателя - любой телефон; Заемщик Гроза Ольга Ивановна. Основное место работы - АО \"Турбонасос\" - ТОП ОПК",
            "4041637; контактного лица; Прозвон контактного лица/супруга (-и); Заемщик Гроза Ольга Ивановна. Супруг(-а) Холодов Данил Ашотович",
            "4041627; клиента; Прозвон клиента; Заемщик Гроза Ольга Ивановна"
    })
    @Tag("logging_check_press_button_change_result_1720673_1720674_1720672")
    @DisplayName("{id} - Проверка логирования типа операции \"Выбор результата по заявке = Отправить на доработку после прозвона\" (для {displayName})")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void logging_check_choose_claim_result_4041633_4041637_4041627(String id, String displayName, String step, String checkObject) {
        goToClaim();
        if (id.equals("4041633") || id.equals("4041637")) {
            customerCallPage.clickOnStep(step)
                    .clickOnElement("Кнопка Взять шаг в работу")
                    .waitBusyCondition();
        }
        customerCallPage.checkStepActivity(step)
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Отправить на доработку после прозвона");
        if (id.equals("4041637")) {
            customerCallPage.selectValueFromDropDownList("Выпадающий список Причина доработки", "Недозвон с запросом документов")
                    .fillInput("Поле ввода Внутренний комментарий", "любое значение")
                    .fillInput("Поле ввода Комментарий для МРК", "любое значение");
        }
        if (id.equals("4041633")) {
            postponeClaim();
        } else {
            customerCallPage.clickOnElement(id.equals("4041637") ? "Кнопка Далее" : "Кнопка Сохранить")
                    .waitBusyCondition()
                    .closeCurrentTab();
        }
        LocalDateTime dateTimeNow = LocalDateTime.now();
        goToActionsRequestsPage("Выбор результата по заявке");
        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", List.of("Автоматическое Тестирование1")),
                Map.entry("Номер заявки", List.of(claim)),
                Map.entry("Время", List.of(dateTimeNow.format(DF))),
                Map.entry("ФИО участника сделки", List.of("Гроза Ольга Ивановна")),
                Map.entry("Стратегия", List.of("Прозвон")),
                Map.entry("Тип операции", List.of("Выбор результата по заявке")),
                Map.entry("Операция", List.of(step + ". Выбор результата по заявке")),
                Map.entry("Начальное значение поля", List.of("")),
                Map.entry("Конечное значение поля", List.of("Отправить на доработку после прозвона")),
                Map.entry("Объект проверки", List.of(checkObject)));
        checkTableMonitoring(expectedValues);
        actionsRequestsPage.clickOnElement("Кнопка Удалить все");
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "1720684; работодателя; Прозвон работодателя - любой телефон; Отказ работодателя подтвердить место работы; Клиент не соответствует требованиям Банка; Заемщик Гроза Ольга Ивановна. Основное место работы - АО \"Турбонасос\" - ТОП ОПК",
            "4024121; контактного лица; Прозвон контактного лица/супруга (-и); Уточнение данных по кредитам клиента; Запрос иных документов; Заемщик Гроза Ольга Ивановна. Супруг(-а) Холодов Данил Ашотович",
            "1720683; клиента; Прозвон клиента; Отказ клиента; Клиент не соответствует требованиям Банка; Заемщик Гроза Ольга Ивановна"
    })
    @Tag("logging_check_send_for_modification_choosing_modification_reason_1720684_4024121_1720683")
    @DisplayName("{id} - Проверка логирования типа операции \"Отправка на доработку. Выбор причины доработки\" (для {displayName})")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void logging_check_send_for_modification_choosing_modification_reason_1720684_4024121_1720683(String id, String displayName, String step, String modificationReason, String modificationReason2, String checkObject) {
        goToClaim();
        if (id.equals("1720684") || id.equals("4024121")) {
            customerCallPage.clickOnStep(step)
                    .clickOnElement("Кнопка Взять шаг в работу")
                    .waitBusyCondition();
        }
        customerCallPage.checkStepActivity(step)
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Отправить на доработку")
                .selectValueFromDropDownList("Выпадающий список Причина доработки", "Недозвон")
                .fillInput("Поле ввода Внутренний комментарий", "любое значение")
                .fillInput("Поле ввода Комментарий для МРК", "любое значение")
                .clickOnElement("Кнопка Сохранить");
        LocalDateTime dateTimeNow = LocalDateTime.now();
        customerCallPage.selectValueFromDropDownList("Выпадающий список Причина доработки", modificationReason)
                .clickOnElement("Кнопка Далее");
        LocalDateTime dateTimeNow2 = LocalDateTime.now();
        customerCallPage.clickOnElement("Кнопка Изменить результат")
                .selectValueFromDropDownList("Выпадающий список Причина доработки", modificationReason2);
        postponeClaim();
        LocalDateTime dateTimeNow3 = LocalDateTime.now();
        goToActionsRequestsPage("Отправка на доработку. Выбор причины доработки");
        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", new ArrayList<>(nCopies(3, "Автоматическое Тестирование1"))),
                Map.entry("Номер заявки", new ArrayList<>(nCopies(3, claim))),
                Map.entry("Время", List.of(dateTimeNow.format(DF), dateTimeNow2.format(DF), dateTimeNow3.format(DF))),
                Map.entry("ФИО участника сделки", new ArrayList<>(nCopies(3, "Гроза Ольга Ивановна"))),
                Map.entry("Стратегия", new ArrayList<>(nCopies(3, "Прозвон"))),
                Map.entry("Тип операции", new ArrayList<>(nCopies(3, "Отправка на доработку. Выбор причины доработки"))),
                Map.entry("Операция", new ArrayList<>(nCopies(3, step + ". Отправка на доработку. Выбор причины доработки"))),
                Map.entry("Начальное значение поля", List.of("", "Недозвон", modificationReason)),
                Map.entry("Конечное значение поля", List.of("Недозвон", modificationReason, modificationReason2)),
                Map.entry("Объект проверки", new ArrayList<>(nCopies(3, checkObject))));
        checkTableMonitoring(expectedValues);
        actionsRequestsPage.clickOnElement("Кнопка Удалить все");
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "4043020; конт. лица; Прозвон контактного лица/супруга (-и); Комментарий для МРК; Заемщик Гроза Ольга Ивановна. Супруг(-а) Холодов Данил Ашотович",
            "1723745; клиента; Прозвон клиента; Комментарий для МРК; Заемщик Гроза Ольга Ивановна",
            "1723740; конт. лица; Прозвон контактного лица/супруга (-и); Внутренний комментарий; Заемщик Гроза Ольга Ивановна. Супруг(-а) Холодов Данил Ашотович",
            "1723749; работодателя; Прозвон работодателя - любой телефон; Внутренний комментарий; Заемщик Гроза Ольга Ивановна. Основное место работы - АО \"Турбонасос\" - ТОП ОПК"
    })
    @Tag("send_for_modification_4043020_1723745_1723740_1723749")
    @DisplayName("{id} - \"Отправить на доработку после прозвона\" для типа операции \"{typeOfCommentary}\" (для {displayName})")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void send_for_modification_4043020_1723745_1723740_1723749(String id, String displayName, String step, String typeOfCommentary, String checkObject) {
        goToClaim();
        if (id.equals("4043020") || id.equals("1723740") || id.equals("1723749")) {
            customerCallPage.clickOnStep(step)
                    .clickOnElement("Кнопка Взять шаг в работу")
                    .waitBusyCondition();
        }
        customerCallPage.checkStepActivity(step)
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Отправить на доработку после прозвона")
                .selectValueFromDropDownList("Выпадающий список Причина доработки", "Некорректные документы (нарушение требований к оформлению, ошибка МРК)")
                .fillInput("Поле ввода Внутренний комментарий", "Тест")
                .fillInput("Поле ввода Комментарий для МРК", "Тест")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку");
        LocalDateTime dateTimeNow = LocalDateTime.now();
        goToActionsRequestsPage(typeOfCommentary);
        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", List.of("Автоматическое Тестирование1")),
                Map.entry("Номер заявки", List.of(claim)),
                Map.entry("Время", List.of(dateTimeNow.format(DF))),
                Map.entry("ФИО участника сделки", List.of("Гроза Ольга Ивановна")),
                Map.entry("Стратегия", List.of("Прозвон")),
                Map.entry("Тип операции", List.of(typeOfCommentary)),
                Map.entry("Операция", List.of(step + ". " + typeOfCommentary)),
                Map.entry("Начальное значение поля", List.of("")),
                Map.entry("Конечное значение поля", List.of("Тест")),
                Map.entry("Объект проверки", List.of(checkObject)));
        checkTableMonitoring(expectedValues);
        actionsRequestsPage.clickOnElement("Кнопка Удалить все");
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "1720664; работодателя; Прозвон работодателя - любой телефон; Отказать; Заемщик Гроза Ольга Ивановна. Основное место работы - АО \"Турбонасос\" - ТОП ОПК",
            "1720665; контактного лица; Прозвон контактного лица/супруга (-и); Отправить на доработку; Заемщик Гроза Ольга Ивановна. Супруг(-а) Холодов Данил Ашотович"
    })
    @Tag("logging_check_choosing_claim_result_1720664_1720665")
    @DisplayName("{id} - Проверка логирования типа операции \"Выбор результата по заявке\" (для {displayName})")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void logging_check_choosing_claim_result_1720664_1720665(String id, String displayName, String step, String resultOnClaim, String checkObject) {
        goToClaim().clickOnStep(step)
                .clickOnElement("Кнопка Взять шаг в работу")
                .waitBusyCondition()
                .checkStepActivity(step)
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Одобрить")
                .selectValueFromDropDownList("Выпадающий список Тип одобрения", "Одобрено")
                .fillInput("Поле ввода Внутренний комментарий", "любое значение")
                .clickOnElement("Кнопка Сохранить");
        LocalDateTime dateTimeNow = LocalDateTime.now();
        customerCallPage.selectValueFromDropDownList("Выпадающий список Результат по заявке", resultOnClaim);
        if (id.equals("1720664")) {
            customerCallPage.selectValueFromDropDownList("Выпадающий список Причина отклонения", "Негатив на Клиента");
        } else {
            customerCallPage.selectValueFromDropDownList("Выпадающий список Причина доработки", "Ошибка МРК при вводе контактных данных клиента (адресов, телефонов)")
                    .fillInput("Поле ввода Комментарий для МРК", "любое значение");
        }
        customerCallPage.clickOnElement("Кнопка Далее");
        LocalDateTime dateTimeNow2 = LocalDateTime.now();
        customerCallPage.clickOnElement("Кнопка Изменить результат")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Одобрить стратегию");
        postponeClaim();
        LocalDateTime dateTimeNow3 = LocalDateTime.now();
        goToActionsRequestsPage("Выбор результата по заявке");
        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", new ArrayList<>(nCopies(3, "Автоматическое Тестирование1"))),
                Map.entry("Номер заявки", new ArrayList<>(nCopies(3, claim))),
                Map.entry("Время", List.of(dateTimeNow.format(DF), dateTimeNow2.format(DF), dateTimeNow3.format(DF))),
                Map.entry("ФИО участника сделки", new ArrayList<>(nCopies(3, "Гроза Ольга Ивановна"))),
                Map.entry("Стратегия", new ArrayList<>(nCopies(3, "Прозвон"))),
                Map.entry("Тип операции", new ArrayList<>(nCopies(3, "Выбор результата по заявке"))),
                Map.entry("Операция", new ArrayList<>(nCopies(3, step + ". Выбор результата по заявке"))),
                Map.entry("Начальное значение поля", List.of("", "Одобрить", resultOnClaim)),
                Map.entry("Конечное значение поля", List.of("Одобрить", resultOnClaim, "Одобрить стратегию")),
                Map.entry("Объект проверки", new ArrayList<>(nCopies(3, checkObject))));
        checkTableMonitoring(expectedValues);
        actionsRequestsPage.clickOnElement("Кнопка Удалить все");
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "4041209; документа, закрывающего риски; Предоставлен документ, закрывающий риски; Выписка из ПФР; Электронная ТК; Удостоверение силовика/военнослужащего/военный билет (для военнослужащего)",
            "4041213; косвенного подтверждения занятости; Косвенное подтверждение занятости; Пункт 1 РА; Пункт 2 РА; Пункт 3 РА"
    })
    @Tag("logging_check_choosing_type_of_4041209_4041213")
    @DisplayName("{id} - Проверка логирования типа операции \"Выбор типа {typeOf}\"")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void logging_check_choosing_type_of_4041209_4041213(String id, String typeOf, String checkResult, String dopValue, String dopValue2, String dopValue3) {
        goToClaim().clickOnStep("Прозвон работодателя - любой телефон")
                .clickOnElement("Кнопка Взять шаг в работу")
                .waitBusyCondition()
                .checkStepActivity("Прозвон работодателя - любой телефон")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", checkResult)
                .selectValueFromDropDownList("Выпадающий список Дополнительное поле результата проверки", dopValue)
                .clickOnElement("Кнопка Сохранить");
        LocalDateTime dateTimeNow = LocalDateTime.now();
        customerCallPage.selectValueFromDropDownList("Выпадающий список Дополнительное поле результата проверки", dopValue2)
                .clickOnElement("Кнопка Далее");
        LocalDateTime dateTimeNow2 = LocalDateTime.now();
        customerCallPage.waitBusyCondition()
                .clickOnStep("Прозвон работодателя - любой телефон")
                .clickOnElement("Кнопка Взять шаг в работу")
                .waitBusyCondition()
                .clickOnElement("Кнопка Изменить результат")
                .selectValueFromDropDownList("Выпадающий список Дополнительное поле результата проверки", dopValue3);
        postponeClaim();
        LocalDateTime dateTimeNow3 = LocalDateTime.now();
        goToActionsRequestsPage("Выбор типа " + typeOf);
        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", new ArrayList<>(nCopies(3, "Автоматическое Тестирование1"))),
                Map.entry("Номер заявки", new ArrayList<>(nCopies(3, claim))),
                Map.entry("Время", List.of(dateTimeNow.format(DF), dateTimeNow2.format(DF), dateTimeNow3.format(DF))),
                Map.entry("ФИО участника сделки", new ArrayList<>(nCopies(3, "Гроза Ольга Ивановна"))),
                Map.entry("Стратегия", new ArrayList<>(nCopies(3, "Прозвон"))),
                Map.entry("Тип операции", new ArrayList<>(nCopies(3, "Выбор типа " + typeOf))),
                Map.entry("Операция", new ArrayList<>(nCopies(3, "Прозвон работодателя - любой телефон. Выбор типа " + typeOf))),
                Map.entry("Начальное значение поля", List.of("", dopValue, dopValue2)),
                Map.entry("Конечное значение поля", List.of(dopValue, dopValue2, dopValue3)),
                Map.entry("Объект проверки", new ArrayList<>(nCopies(3, "Заемщик Гроза Ольга Ивановна. Основное место работы - АО \"Турбонасос\" - ТОП ОПК"))));
        checkTableMonitoring(expectedValues);
        actionsRequestsPage.clickOnElement("Кнопка Удалить все");
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "4038857; для работодателя; Прозвон работодателя - любой телефон; Официальный сайт; Сайт 1; Сайт New 2; Заемщик Гроза Ольга Ивановна. Основное место работы - АО \"Турбонасос\" - ТОП ОПК",
            "1720691; для клиента; Прозвон клиента; Социальные сети; Соцсеть 1; Соцсеть New 2; Заемщик Гроза Ольга Ивановна"
    })
    @Tag("logging_check_choosing_type_of_operation_confirmation_root_selected_4041182_4038857_1720691")
    @DisplayName("{id} - Проверка логирования типа операции \"Указан источник подтверждения\" {для displayName}")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void logging_check_choosing_type_of_operation_confirmation_root_selected_4041182_4038857_1720691(String id, String displayName, String step, String contactlessApprove, String dopValue, String dopValue2, String checkObject) {
        goToClaim();
        if (id.equals("4038857")) {
            customerCallPage.clickOnStep(step)
                    .clickOnElement("Кнопка Взять шаг в работу")
                    .waitBusyCondition();
        }
        customerCallPage.checkStepActivity(step)
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Бесконтактное подтверждение")
                .selectValueFromDropDownList("Выпадающий список Бесконтактное подтверждение", contactlessApprove)
                .fillInput("Поле ввода Источник подтверждения", dopValue)
                .clickOnElement("Кнопка Сохранить");
        LocalDateTime dateTimeNow = LocalDateTime.now();
        customerCallPage.fillInput("Поле ввода Источник подтверждения", dopValue2);
        customerCallPage.clickOnElement("Кнопка Далее");
        LocalDateTime dateTimeNow2 = LocalDateTime.now();
        customerCallPage.closeCurrentTab();
        goToActionsRequestsPage("Указан источник подтверждения");
        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", new ArrayList<>(nCopies(2, "Автоматическое Тестирование1"))),
                Map.entry("Номер заявки", new ArrayList<>(nCopies(2, claim))),
                Map.entry("Время", List.of(dateTimeNow.format(DF), dateTimeNow2.format(DF))),
                Map.entry("ФИО участника сделки", new ArrayList<>(nCopies(2, "Гроза Ольга Ивановна"))),
                Map.entry("Стратегия", new ArrayList<>(nCopies(2, "Прозвон"))),
                Map.entry("Тип операции", new ArrayList<>(nCopies(2, "Указан источник подтверждения"))),
                Map.entry("Операция", new ArrayList<>(nCopies(2, step + ". Указан источник подтверждения"))),
                Map.entry("Начальное значение поля", List.of("", dopValue)),
                Map.entry("Конечное значение поля", List.of(dopValue, dopValue2)),
                Map.entry("Объект проверки", new ArrayList<>(nCopies(2, checkObject))));
        checkTableMonitoring(expectedValues);
        actionsRequestsPage.clickOnElement("Кнопка Удалить все");
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "4024197; работодателя; Прозвон работодателя - любой телефон; Клиент уволен / находится в стадии увольнения; Клиент уволен / находится в стадии увольнения, Негативная характеристика Клиента от работодателя; Информация о сокращениях; Заемщик Гроза Ольга Ивановна. Основное место работы - АО \"Турбонасос\" - ТОП ОПК",
            "4024211; контактного лица; Прозвон контактного лица/супруга (-и); Супруг(-а) не может сообщить персональные данные; Негативная характеристика Клиента от супруга (-и)/контактного лица; Кредит на бизнес; Заемщик Гроза Ольга Ивановна. Супруг(-а) Холодов Данил Ашотович",
            "4024183; клиента; Прозвон клиента; Кредит для третьего лица, Кредит на бизнес; Кредит для третьего лица, Финансовые трудности; Несоответствие минимальным требованиям; Заемщик Гроза Ольга Ивановна"
    })
    @Tag("logging_check_result_on_claim_choose_deny_reason_4018598_4018628_4018543")
    @DisplayName("{id} - Проверка логирования типа операции \"Выбор типа выявленного негатива\" (для {displayName})")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void logging_check_choosing_type_negative_4024197_4024211_4024183(String id, String displayName, String step, String dop1, String dop2, String dop3, String checkObject) {
        goToClaim();
        if (id.equals("4024197") || id.equals("4024211")) {
            customerCallPage.clickOnStep(step)
                    .clickOnElement("Кнопка Взять шаг в работу")
                    .waitBusyCondition();
        }
        customerCallPage.checkStepActivity(step)
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                .selectValueFromDropDownList("Выпадающий список Дополнительное поле результата проверки", "Выявлен негатив")
                .selectValueFromDropDownList("Выпадающий список Выявлен негатив", Arrays.stream(dop1.split(",")).map(String::trim).collect(Collectors.toList()))
                .clickOnElement("Кнопка Сохранить");
        LocalDateTime dateTimeNow = LocalDateTime.now();
        customerCallPage.cleanDropDownListCheckboxes("Выпадающий список Выявлен негатив")
                .selectValueFromDropDownList("Выпадающий список Выявлен негатив", Arrays.stream(dop2.split(",")).map(String::trim).collect(Collectors.toList()))
                .clickOnElement("Кнопка Далее");
        LocalDateTime dateTimeNow2 = LocalDateTime.now();
        customerCallPage.waitBusyCondition()
                .clickOnStep(step)
                .clickOnElement("Кнопка Взять шаг в работу")
                .waitBusyCondition()
                .clickOnElement("Кнопка Изменить результат")
                .cleanDropDownListCheckboxes("Выпадающий список Выявлен негатив")
                .selectValueFromDropDownList("Выпадающий список Выявлен негатив", Arrays.stream(dop3.split(",")).map(String::trim).collect(Collectors.toList()));
        postponeClaim();
        LocalDateTime dateTimeNow3 = LocalDateTime.now();
        goToActionsRequestsPage("Выбор типа выявленного негатива");
        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", new ArrayList<>(nCopies(3, "Автоматическое Тестирование1"))),
                Map.entry("Номер заявки", new ArrayList<>(nCopies(3, claim))),
                Map.entry("Время", List.of(dateTimeNow.format(DF), dateTimeNow2.format(DF), dateTimeNow3.format(DF))),
                Map.entry("ФИО участника сделки", new ArrayList<>(nCopies(3, "Гроза Ольга Ивановна"))),
                Map.entry("Стратегия", new ArrayList<>(nCopies(3, "Прозвон"))),
                Map.entry("Тип операции", new ArrayList<>(nCopies(3, "Выбор типа выявленного негатива"))),
                Map.entry("Операция", new ArrayList<>(nCopies(3, step + ". Выбор типа выявленного негатива"))),
                Map.entry("Начальное значение поля", List.of("", dop1, dop2)),
                Map.entry("Конечное значение поля", List.of(dop1, dop2, dop3)),
                Map.entry("Объект проверки", new ArrayList<>(nCopies(3, checkObject))));
        checkTableMonitoring(expectedValues);
        actionsRequestsPage.clickOnElement("Кнопка Удалить все");
    }

    @Test
    @Tag("logging_check_choosing_type_of_operation_4041182")
    @DisplayName("4041182 - Проверка логирования типа операции \"Выбор типа бесконтактного подтверждения\"")
    @WorkItemIds({"4041182"})
    public void logging_check_choosing_type_of_operation_choosing_type_of_contactless_approval_4041182() {
        goToClaim().clickOnStep("Прозвон работодателя - любой телефон")
                .clickOnElement("Кнопка Взять шаг в работу")
                .waitBusyCondition()
                .checkStepActivity("Прозвон работодателя - любой телефон")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Бесконтактное подтверждение")
                .selectValueFromDropDownList("Выпадающий список Бесконтактное подтверждение", "Официальный сайт")
                .fillInput("Поле ввода Источник подтверждения", "любое значение")
                .clickOnElement("Кнопка Сохранить");
        LocalDateTime dateTimeNow = LocalDateTime.now();
        customerCallPage.selectValueFromDropDownList("Выпадающий список Бесконтактное подтверждение", "Сторонние сайты")
                .clickOnElement("Кнопка Далее");
        LocalDateTime dateTimeNow2 = LocalDateTime.now();
        customerCallPage.closeCurrentTab();
        goToActionsRequestsPage("Выбор типа бесконтактного подтверждения");
        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", new ArrayList<>(nCopies(2, "Автоматическое Тестирование1"))),
                Map.entry("Номер заявки", new ArrayList<>(nCopies(2, claim))),
                Map.entry("Время", List.of(dateTimeNow.format(DF), dateTimeNow2.format(DF))),
                Map.entry("ФИО участника сделки", new ArrayList<>(nCopies(2, "Гроза Ольга Ивановна"))),
                Map.entry("Стратегия", new ArrayList<>(nCopies(2, "Прозвон"))),
                Map.entry("Тип операции", new ArrayList<>(nCopies(2, "Выбор типа бесконтактного подтверждения"))),
                Map.entry("Операция", new ArrayList<>(nCopies(2, "Прозвон работодателя - любой телефон. Выбор типа бесконтактного подтверждения"))),
                Map.entry("Начальное значение поля", List.of("", "Официальный сайт")),
                Map.entry("Конечное значение поля", List.of("Официальный сайт", "Сторонние сайты")),
                Map.entry("Объект проверки", new ArrayList<>(nCopies(2, "Заемщик Гроза Ольга Ивановна. Основное место работы - АО \"Турбонасос\" - ТОП ОПК"))));
        checkTableMonitoring(expectedValues);
        actionsRequestsPage.clickOnElement("Кнопка Удалить все");
    }

    @Test
    @Tag("logging_check_choosing_type_of_operation_choosing_reason_of_no_negative_1720705")
    @DisplayName("1720705 - Проверка логирования типа операции \"Выбор причины отсутствия негатива\"")
    @WorkItemIds({"1720705"})
    public void logging_check_choosing_type_of_operation_choosing_reason_of_no_negative_1720705() {
        goToClaim().checkStepActivity("Прозвон клиента")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Негатив не выявлен")
                .selectValueFromDropDownList("Выпадающий список Негатив не выявлен", "Негатив отсутствует")
                .clickOnElement("Кнопка Сохранить");
        LocalDateTime dateTimeNow = LocalDateTime.now();
        customerCallPage.selectValueFromDropDownList("Выпадающий список Негатив не выявлен", "Негатив отсутствует. Занятость клиента подтверждена по корпоративной почте клиента")
                .clickOnElement("Кнопка Далее");
        LocalDateTime dateTimeNow2 = LocalDateTime.now();
        customerCallPage.closeCurrentTab();
        goToActionsRequestsPage("Выбор причины отсутствия негатива");
        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", new ArrayList<>(nCopies(2, "Автоматическое Тестирование1"))),
                Map.entry("Номер заявки", new ArrayList<>(nCopies(2, claim))),
                Map.entry("Время", List.of(dateTimeNow.format(DF), dateTimeNow2.format(DF))),
                Map.entry("ФИО участника сделки", new ArrayList<>(nCopies(2, "Гроза Ольга Ивановна"))),
                Map.entry("Стратегия", new ArrayList<>(nCopies(2, "Прозвон"))),
                Map.entry("Тип операции", new ArrayList<>(nCopies(2, "Выбор причины отсутствия негатива"))),
                Map.entry("Операция", new ArrayList<>(nCopies(2, " Прозвон клиента. Выбор причины отсутствия негатива"))),
                Map.entry("Начальное значение поля", List.of("", "Негатив отсутствует")),
                Map.entry("Конечное значение поля", List.of("Негатив отсутствует", "Негатив отсутствует. Занятость клиента подтверждена по корпоративной почте клиента")),
                Map.entry("Объект проверки", new ArrayList<>(nCopies(2, "Заемщик Гроза Ольга Ивановна"))));
        checkTableMonitoring(expectedValues);
        actionsRequestsPage.clickOnElement("Кнопка Удалить все");
    }

    @Test
    @Tag("logging_check_choosing_type_of_operation_choosing_type_of_phone_4041263")
    @DisplayName("4041263 - Проверка логирования типа операции \"Выбор типа телефона\"")
    @WorkItemIds({"4041263"})
    public void logging_check_choosing_type_of_operation_choosing_type_of_phone_4041263() {
        goToClaim().clickOnStep("Прозвон работодателя - любой телефон")
                .clickOnElement("Кнопка Взять шаг в работу")
                .waitBusyCondition()
                .checkStepActivity("Прозвон работодателя - любой телефон")
                .selectValueFromDropDownList("Выпадающий список Номер, используемый для звонка", "Подтвержденный")
                .clickOnElement("Кнопка Сохранить");
        LocalDateTime dateTimeNow = LocalDateTime.now();
        customerCallPage.selectValueFromDropDownList("Выпадающий список Номер, используемый для звонка", "Неподтвержденный");
        postponeClaim();
        LocalDateTime dateTimeNow2 = LocalDateTime.now();
        customerCallPage.switchToOneTab()
                .goTo(personalAccountPage)
                .clickOnElement("Кнопка раскрыть таблицу Отложено")
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(customerCallPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон работодателя - любой телефон/Версия 1")
                .clickOnElement("Кнопка Взять в работу")
                .selectValueFromDropDownList("Выпадающий список Номер, используемый для звонка", "Подтвержденный")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Негатив не выявлен, все ответы получены")
                .clickOnElement("Кнопка Далее");
        LocalDateTime dateTimeNow3 = LocalDateTime.now();
        customerCallPage.closeCurrentTab();
        goToActionsRequestsPage("Выбор типа телефона");
        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", new ArrayList<>(nCopies(3, "Автоматическое Тестирование1"))),
                Map.entry("Номер заявки", new ArrayList<>(nCopies(3, claim))),
                Map.entry("Время", List.of(dateTimeNow.format(DF), dateTimeNow2.format(DF), dateTimeNow3.format(DF))),
                Map.entry("ФИО участника сделки", new ArrayList<>(nCopies(3, "Гроза Ольга Ивановна"))),
                Map.entry("Стратегия", new ArrayList<>(nCopies(3, "Прозвон"))),
                Map.entry("Тип операции", new ArrayList<>(nCopies(3, "Выбор типа телефона"))),
                Map.entry("Операция", new ArrayList<>(nCopies(3, "Прозвон работодателя - любой телефон. Выбор типа телефона"))),
                Map.entry("Начальное значение поля", List.of("", "Подтвержденный", "Неподтвержденный")),
                Map.entry("Конечное значение поля", List.of("Подтвержденный", "Неподтвержденный", "Подтвержденный")),
                Map.entry("Объект проверки", new ArrayList<>(nCopies(3, "Заемщик Гроза Ольга Ивановна. Основное место работы - АО \"Турбонасос\" - ТОП ОПК"))));
        checkTableMonitoring(expectedValues);
        actionsRequestsPage.clickOnElement("Кнопка Удалить все");
    }

    private CustomerCallPage goToClaim() {
        return personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(customerCallPage)
                .waitBusyCondition()
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон клиента/Версия 1");
    }

    private void checkTableMonitoring(Map<String, List<String>> expectedValues) {
        for (Map.Entry<String, List<String>> expected : expectedValues.entrySet()) {
            List<String> actualValuesCells = actionsRequestsPage.getListValuesByColumnName("Таблица Действия с заявками", expected.getKey());
            List<String> expectedValuesCells = expected.getValue();
            if (actualValuesCells.size() == expectedValuesCells.size()) {
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
            } else {
                throw new ElementInteractionException("Количество актуальных значений =" + actualValuesCells.size() +
                        " не совпадает с ожидаемым =" + expectedValuesCells.size() + " в таблице Действия с заявками");
            }
        }
    }

    private CustomerCallPage postponeClaim() {
        return customerCallPage
                .clickOnElement("Кнопка Отложить")
                .assertElementByTitleVisibility("Модальное окно Перевод заявки в отложенные", "отображается")
                .selectValueFromDropDownList("Выпадающий список Причина (Перевод заявки в отложенные)", "Недозвон Заемщику")
                .fillInput("Поле ввода Комментарий (Перевод заявки в отложенные)", "Тест")
                .fillInput("Поле ввода Время для звонка участнику (Перевод заявки в отложенные)", LocalDateTime.now().plusDays(1).withHour(18).withMinute(0).format(DF))
                .clickOnElement("Кнопка Отложить заявку (Перевод заявки в отложенные)");
    }

    private ActionsRequestsPage goToActionsRequestsPage(String typeOperation) {
        return customerCallPage.switchToOneTab()
                .goTo(personalAccountPage)
                .openMenuLinks("Мониторинг > Действия с заявками")
                .goTo(actionsRequestsPage)
                .clickOnElement("Кнопка Сбросить сортировку")
                .fillInput("Поле ввода Номер заявки", claim)
                .inputTypeOperation(typeOperation)
                .clickOnElement("Кнопка Найти")
                .waitBusyCondition();
    }
}
