package ru.autotestframework.regress.working_with_application.return_to_queue.verification;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

@Tag("regress")
@Tag("working_with_application")
@Tag("return_to_queue")
@Tag("verification")
@Tag("on_claim_3_return_to_queue")
@ClassName("Работа с заявкой. Вернуть в очередь. Верификация. На заявке №3. Вернуть в очередь")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VerificationClaimType3ReturnToQueueTest extends BaseTest {

    String claim;

    @BeforeEach
    public void login(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3250926.json", 1, testInfo).get(0);
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
    @Tag("checking_button_return_to_queue_2653288")
    @DisplayName("2653288 - Отображение кнопки \"Вернуть в очередь\"")
    @WorkItemIds({"2653288"})
    public void checking_button_return_to_queue_2653288() {
        loginPage.doubleClickByText(claim).switchToNewTab().waitBusyCondition()
                .goTo(cardRequestPage)
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - На рассмотрении")
                .assertElementByTitleVisibility("Кнопка Вернуть в очередь", "отображается")
                .assertElementByTitleActivity("Кнопка Вернуть в очередь", "активен")
                .closeCurrentTab();
    }

    @Test
    @Tag("checking_modal_windows_return_to_queue_2653287")
    @DisplayName("2653287 - Отображение информационного окна \"Заявка будет возвращена в очередь!\" по нажатию на кнопку \"Вернуть в очередь\"")
    @WorkItemIds({"2653287"})
    public void checking_modal_windows_return_to_queue_2653287() {
        loginPage.doubleClickByText(claim).switchToNewTab().waitBusyCondition()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .goTo(cardRequestPage)
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - На рассмотрении")
                .assertElementByTitleVisibility("Кнопка Вернуть в очередь", "отображается")
                .assertElementByTitleActivity("Кнопка Вернуть в очередь", "активен")
                .clickOnElement("Кнопка Вернуть в очередь")
                .checkElementByTitleContains("Модальное окно предупреждения", "Заявка будет возвращена в очередь!\n" +
                        "Выполненные изменения будут утеряны. Да\\Нет?")
                .assertElementByTitleVisibility("Кнопка Да модального окна", "отображается")
                .assertElementByTitleVisibility("Кнопка Нет модального окна", "отображается")
                .closeCurrentTab();
    }
}
