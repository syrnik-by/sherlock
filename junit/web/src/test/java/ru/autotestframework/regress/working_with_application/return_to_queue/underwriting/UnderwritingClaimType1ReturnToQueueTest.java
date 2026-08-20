package ru.autotestframework.regress.working_with_application.return_to_queue.underwriting;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

@Tag("regress")
@Tag("working_with_application")
@Tag("return_to_queue")
@Tag("no_check_verification")
@Tag("underwriting")
@Tag("on_claim_1_return_to_queue")
@ClassName("Работа с заявкой. Вернуть в очередь. Андеррайтинг. На заявке №1 Веернуть в очереть")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UnderwritingClaimType1ReturnToQueueTest extends BaseTest {

    String claim;

    @BeforeEach
    public void login(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3251519.json", 1, testInfo).get(0);
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
    @Tag("button_return_queue_active_2653844")
    @DisplayName("2653844 - Андеррайтинг: кнопка \"Вернуть в очередь\" активна в ЛК, Пользователь - Владелец блокировки")
    @WorkItemIds({"2653844"})
    public void button_return_queue_active_2653844() {
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.openMenuLinks("Личный кабинет")
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Андеррайтинг")
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(cardRequestPage)
                .assertElementByTitleActivity("Кнопка Вернуть в очередь", "активен")
                .clickOnElement("Кнопка Вернуть в очередь")
                .checkElementByTitleContains("Модальное окно предупреждения", "Заявка будет возвращена в очередь!\n" +
                        "Выполненные изменения будут утеряны. Да\\Нет?")
                .clickOnElement("Кнопка Да модального окна")
                .switchToOneTab();
    }

    @Test
    @Tag("button_return_queue_no_active_2653850")
    @DisplayName("2653850 - Андеррайтинг: кнопка \"Вернуть в очередь\" неактивна на вкладке \"Очереди\". Статус заявки - \"На рассмотрении\"")
    @WorkItemIds({"2653850"})
    public void button_return_queue_no_active_2653850() {
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
    @Tag("button_return_queue_no_active_2653847")
    @DisplayName("2653847 - Андеррайтинг: кнопка \"Вернуть в очередь\" неактивна на вкладке \"Поиск\". Статус заявки - \"На рассмотрении\"")
    @WorkItemIds({"2653847"})
    public void button_return_queue_no_active_2653847() {
        loginPage
                .openMenuLinks("Поиск")
                .goTo(searchPage)
                .searchClaimOnPage(claim).doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(cardRequestPage)
                .assertElementByTitleActivity("Кнопка Вернуть в очередь", "не активен")
                .closeCurrentTab();
    }
}