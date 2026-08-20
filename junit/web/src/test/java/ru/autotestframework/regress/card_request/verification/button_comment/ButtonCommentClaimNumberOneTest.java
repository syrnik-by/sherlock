package ru.autotestframework.regress.card_request.verification.button_comment;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

@Tag("regress")
@Tag("verification")
@Tag("card_request")
@Tag("button_comments")
@ClassName("Карточка заявки. Верификация. Кнопка \"Комментарии\". На заявке Тип №1 Кнопка комментарии")
public class ButtonCommentClaimNumberOneTest extends BaseTest {

    private String claim;

    @BeforeAll
    public void createClaim(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3250596.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
    }

    @BeforeEach
    public void login() {
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi()
                    .openMenuLinks("Личный кабинет");
        }
    }

    @AfterAll
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("checking_comments_1725624")
    @DisplayName("1725624 - Проверка отображения кнопки \"Комментарии\" на каждой экранной форме")
    @WorkItemIds({"1725624"})
    public void checking_comments_1725624() {
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .assertElementByTitleActivity("Кнопка Комментарии", "активен")
                .closeCurrentTab()
                .goTo(personalAccountPage)
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim)
                .doubleClickByText(claim).switchToNewTab().waitBusyCondition()
                .goTo(fsspPage)
                .assertElementByTitleActivity("Кнопка Комментарии", "активен")
                .closeCurrentTab();
    }

    @Test
    @Tag("checking_comments_1725626")
    @DisplayName("1725626 - Проверка работоспособности кнопки \"Комментарии\"")
    @WorkItemIds({"1725626"})
    public void checking_comments_1725626() {
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .assertElementByTitleActivity("Кнопка Комментарии", "активен")
                .clickOnElement("Кнопка Комментарии")
                .assertElementByTitleVisibility("Окно комментарии", "отображается")
                .closeCurrentTab();
    }
}
