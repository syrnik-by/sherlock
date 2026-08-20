package ru.autotestframework.regress.working_with_application.return_to_queue.statement;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.*;
import ru.psb.testit.annotations.DisplayName;

import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;
import static ru.autotestframework.utils.Constants.REQUESTS;

@Tag("regress")
@Tag("personal_account")
@Tag("statement")
@Tag("statementClaimType2ReturnToQueueTest")
@ClassName("Работа с заявкой. Вернуть в очередь. Утверждение. На заявке №2. Вернуть в очередь")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StatementClaimType2ReturnToQueueTest extends BaseTest {

    String claim;

    @BeforeEach
    public void login(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3250955.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
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
    @Tag("button_return_queue_active_2653846")
    @DisplayName("2653846 - Кнопка \"Вернуть в очередь\" активна. У Пользователя есть разрешения - Утверждение. Статус \"На утверждении\"")
    @WorkItemIds({"2653846"})
    public void button_return_queue_active_2653846() {
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
                .clickOnElement("Кнопка Да модального окна").switchToOneTab().waitBusyCondition();
        String statusClaim = personalAccountPage.getTextFromTable("Таблица в работе Утверждение", 1, "Статус заявки");
        assertIsTrue(statusClaim.equals("Ожидает утверждения"),
                "Значение в столбце Статус заявки должно быть равно Ожидает утверждения. Фактическое значение: " + statusClaim);
        cardRequestPage.closeCurrentTab();
    }

    @Test
    @Tag("button_return_queue_no_active_2653848")
    @DisplayName("2653848 - Кнопка \"Вернуть в очередь\" неактивна на заявке со статусом \"На утверждении\" на вкладке \"Очереди\"")
    @WorkItemIds({"2653848"})
    public void button_return_queue_no_active_2653848() {
        transferApplicationStatusAwaitingApproval();
        loginPage
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim).doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(cardRequestPage)
                .assertElementByTitleActivity("Кнопка Вернуть в очередь", "не активен")
                .goTo(queuesPage).closeCurrentTab().resetFilters();
    }

    @Test
    @Tag("button_return_queue_no_active_2653849")
    @DisplayName("2653849 - Кнопка \"Вернуть в очередь\" неактивна на заявке со статусом \"На утверждении\" на вкладке \"Поиск\"")
    @WorkItemIds({"2653849"})
    public void button_return_queue_no_active_2653849() {
        transferApplicationStatusAwaitingApproval();
        loginPage
                .openMenuLinks("Поиск")
                .goTo(searchPage)
                .searchClaimOnPage(claim).doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(cardRequestPage)
                .assertElementByTitleActivity("Кнопка Вернуть в очередь", "не активен")
                .closeCurrentTab();
    }

    @Test
    @Tag("button_return_queue_active_2653843")
    @DisplayName("2653843 - Кнопка \"Вернуть в очередь\" неактивна. Заявка со статусом \"Отложена (Утверждение)\"")
    @WorkItemIds({"2653843"})
    public void button_return_queue_active_2653843() {
        transferApplicationStatusAwaitingApproval();
        personalAccountPage
                .clickOnElement("Раздел Утверждение")
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(cardRequestPage)
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - Ожидает утверждения")
                .clickOnElement("Кнопка Взять в работу")
                .clickOnElement("Кнопка Отложить")
                .waitText(10, "Перевод заявки в отложенные")
                .selectValueFromDropDownList("Выпадающий список Причина (Перевод заявки в отложенные)", "Вопрос в ГО")
                .clickOnElement("Кнопка Отложить заявку")
                .switchToOneTab()
                .goTo(personalAccountPage).waitBusyCondition()
                .clickOnElement("Раздел Утверждение")
                .clickOnElement("Кнопка раскрыть таблицу Отложено");
        String statusClaim = personalAccountPage.getTextFromTable("Таблица Отложено", 1, "Статус заявки");
        assertIsTrue(statusClaim.equals("Отложена (утверждение)"),
                "Значение в столбце Статус заявки должно быть равно Отложена (Утверждение). Фактическое значение: " + statusClaim);
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(cardRequestPage)
                .assertElementByTitleActivity("Кнопка Вернуть в очередь", "не активен")
                .clickOnElement("Кнопка Взять в работу").waitBusyCondition()
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - На утверждении");
        actionsClaimSteps.checkStatusClaimFromDb(claim, 6);
        cardRequestPage.closeCurrentTab();
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
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - На рассмотрении")
                .selectValueFromDropDownList("Выпадающий список Занятость подтверждена", "Звонок не назначался")
                .fillInput("Поле ввода Внутренний комментарий андеррайтера", "Коментарий АТ")
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