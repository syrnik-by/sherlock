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

import static ru.autotestframework.steps.asserts.Asserts.assertIsEquals;
import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;

@Tag("regress")
@Tag("working_with_application")
@Tag("return_to_queue")
@Tag("no_check_verification")
@ClassName("Работа с заявкой. Вернуть в очередь. На каждый кейс отдельная заявка. Вернуть в очередь")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UnderwritingButtonReturnQueue extends BaseTest {

    String claim;

    @BeforeEach
    public void login() {
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi();
        }
        loginPage.openMenuLinks("Личный кабинет")
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Андеррайтинг");
    }

    @AfterEach
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("return_queue_3244105")
    @DisplayName("3244105 - Андеррайтинг: кнопка \"Вернуть в очередь\" - Заявка переходит в общую очередь ")
    @WorkItemIds({"3244105"})
    public void return_queue_3244105(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2057991.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage
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
                        equals("Ожидает"),
                "Значение в столбце Статус заявки должно быть Ожидает");
        queuesPage.resetFilters();
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "3244103; Заявка переходит в общую очередь и сохраняет изменения; Нет; Звонок не назначался; 123",
            "3244104; Заявка переходит в общую очередь и НЕ сохраняет изменения; Да; ''; ''"})
    @Tag("return_queue_and_save_3244103")
    @DisplayName("{id} - Андеррайтинг: кнопка \"Вернуть в очередь\" - {displayName}")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void return_queue_3244103_3244104(String id, String displayName, String buttonName, String selectValue, String comment, TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2057991.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(cardRequestPage)
                .assertElementByTitleActivity("Кнопка Вернуть в очередь", "активен")
                .selectValueFromDropDownList("Выпадающий список Занятость подтверждена", "Звонок не назначался")
                .fillInput("Поле ввода Внутренний комментарий андеррайтера", "123")
                .clickOnElement("Кнопка Вернуть в очередь")
                .checkElementByTitleContains("Модальное окно предупреждения", "Заявка будет возвращена в очередь!\n" +
                        "Выполненные изменения будут утеряны. Да\\Нет?")
                .clickOnElement("Кнопка " + buttonName + " модального окна").switchToOneTab().waitBusyCondition()
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
                .doubleClickByText(claim).switchToNewTab().waitBusyCondition()
                .goTo(cardRequestPage)
                .assertElementByTitleActivity("Вкладка основные данные", "активен");

        assertIsEquals(selectValue, cardRequestPage.getTextByElementTitle("Выпадающий список Занятость подтверждена"), "Выпадающий список Занятость подтверждена");
        assertIsEquals(comment, underwriterDecisionPage.getValueByElementTitle("Поле ввода Внутренний комментарий андеррайтера"), "Поле ввода Внутренний комментарий андеррайтера");
        queuesPage.closeCurrentTab().resetFilters();
    }
}
