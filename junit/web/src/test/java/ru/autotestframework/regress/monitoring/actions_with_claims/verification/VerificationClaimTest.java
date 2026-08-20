package ru.autotestframework.regress.monitoring.actions_with_claims.verification;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.ExternalId;
import ru.psb.testit.annotations.WorkItemIds;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;

@Tag("regress")
@Tag("monitoring")
@Tag("actions_with_claims")
@Tag("verification")
@ClassName("Личный кабинет. Верификация")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VerificationClaimTest extends BaseTest {

    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    @BeforeEach
    public void login() {
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi()
                    .openMenuLinks("Личный кабинет")
                    .goTo(personalAccountPage)
                    .clickOnElement("Раздел Верификация").waitBusyCondition();
        }
    }

    @AfterEach
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "1723743; \"Отправить на доработку после прозвона\" для типа операции \"Внутренний комментарий\" (для клиента); Прозвон клиента/Версия 1;" +
                    " Внутренний комментарий; Прозвон клиента. Внутренний комментарий; Заемщик Иванов Дмитрий Юрьевич; 1",
            "1723748; \"Отправить на доработку после прозвона\" для типа операции \"Комментарий для МРК\" (для работодателя); Прозвон работодателя - любой телефон/Версия 1;" +
                    " Комментарий для МРК; Прозвон работодателя - любой телефон. Комментарий для МРК;" +
                    " Заемщик Иванов Дмитрий Юрьевич. Основное место работы - УПРАВЛЕНИЕ МИНИСТЕРСТВА ВНУТРЕННИХ ДЕЛ РОССИЙСКОЙ ФЕДЕРАЦИИ ПО ЕВРЕЙСКОЙ АВТОНОМНОЙ ОБЛАСТИ; 2"
    })
    @Tag("send_revision_after_rings_comment")
    @Tag("smoke")
    @DisplayName("{id} -{displayName}")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void send_revision_after_rings_comment(String id, String displayName, String strategy, String typeOperation, String operation, String objectCheck, String finalValue, TestInfo testInfo) {
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_" + id + ".json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(customerCallPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", strategy)
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Отправить на доработку после прозвона")
                .selectValueFromDropDownList("Выпадающий список Причина доработки", "Неполный пакет документов (ошибка МРК)")
                .fillInput("Поле ввода Внутренний комментарий", "1")
                .fillInput("Поле ввода Комментарий для МРК", "2")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку");
        String timeSend = LocalDateTime.now().format(DF);

        loginPage.switchToOneTab().waitBusyCondition()
                .openMenuLinks("Мониторинг > Действия с заявками")
                .goTo(actionsRequestsPage)
                .fillInput("Поле ввода Номер заявки", claim)
                .inputTypeOperation(typeOperation)
                .clickOnElement("Кнопка Найти").waitBusyCondition();

        Map<String, String> expectedValue = Map.ofEntries(
                Map.entry("ФИО пользователя", "Автоматическое Тестирование1"),
                Map.entry("Номер заявки", claim),
                Map.entry("Время", timeSend),
                Map.entry("ФИО участника сделки", "Иванов Дмитрий Юрьевич"),
                Map.entry("Стратегия", "Прозвон"),
                Map.entry("Тип операции", typeOperation),
                Map.entry("Операция", operation),
                Map.entry("Начальное значение поля", ""),
                Map.entry("Конечное значение поля", finalValue),
                Map.entry("Объект проверки", objectCheck));
        for (Map.Entry<String, String> expected : expectedValue.entrySet()) {
            String actualValue = actionsRequestsPage.getTextFromTable("Таблица Действия с заявками", 1, expected.getKey());
            assertIsTrue(actualValue.equals(expected.getValue()),
                    "Значение столбца " + expected.getKey() + " строки 1 должно быть равно " + expected.getValue() + " . Фактическое значение = " + actualValue);
        }
        actionsRequestsPage.clickOnElement("Кнопка Удалить все")
                .goTo(loginPage).openMenuLinks("Личный кабинет");
    }
}
