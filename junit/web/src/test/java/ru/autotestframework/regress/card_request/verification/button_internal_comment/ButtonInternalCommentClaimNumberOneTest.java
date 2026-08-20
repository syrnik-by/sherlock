package ru.autotestframework.regress.card_request.verification.button_internal_comment;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.ResourceLock;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import java.util.Map;

import static ru.autotestframework.steps.asserts.Asserts.assertIsEquals;

@Tag("regress")
@Tag("card_request")
@Tag("verification")
@Tag("card_request")
@Tag("button_internal_comment")
@ClassName("Карточка заявки. Верификация. На каждый кейс отдельная заявка. Кнопка внутренний комментарий")
public class ButtonInternalCommentClaimNumberOneTest extends BaseTest {

    private String claim;

    @BeforeAll
    public void createClaim(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3245090.json", 1, testInfo).get(0);
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
    @Tag("smoke")
    @Tag("checking_not_save_internal_comment_1725607")
    @DisplayName("1725607 - Проверка предзаполненности внутреннего комментария для текущей версии, если комментарий не сохранялся в текущей версии ни разу")
    @WorkItemIds({"1725607"})
    public void checking_not_save_internal_comment_1725607() {
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(cardRequestPage)
                .assertElementByTitleActivity("Кнопка Внутренний комментарий", "активен")
                .clickOnElement("Кнопка Внутренний комментарий")
                .assertElementByTitleVisibility("Окно внутренних комментариев", "отображается")
                .fillInput("Поле ввода История комментариев", "коммент");
        assertIsEquals("коммент", cardRequestPage.getValueByElementTitle("Поле ввода История комментариев"), "Поле ввода История комментариев");
        cardRequestPage
                .clickOnElement("Кнопка закрыть Окно")
                .closeCurrentTab()
                .goTo(loginPage)
                .openMenuLinks("Личный кабинет")
                .doubleClickByText(claim).switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Внутренний комментарий")
                .checkElementByTitleEquals("Поле ввода История комментариев", "")
                .closeCurrentTab();
    }

    @Test
    @Tag("button_internal_comment_1725609")
    @DisplayName("1725609 - Проверка работы кнопки \"Внутренний комментарий\"")
    @WorkItemIds({"1725609"})
    public void button_internal_comment_1725609() {
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(cardRequestPage)
                .assertElementByTitleActivity("Кнопка Внутренний комментарий", "активен")
                .clickOnElement("Кнопка Внутренний комментарий")
                .assertElementByTitleVisibility("Окно внутренних комментариев", "отображается")
                .closeCurrentTab();
    }

    @Test
    @Tag("internal_comment_1725615")
    @DisplayName("1725615 - Проверка обязательности заполнения внутреннего комментария при заполнении блока результата по заявке")
    @WorkItemIds({"1725615"})
    public void internal_comment_1725615() {
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(cardRequestPage)
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Одобрить стратегию")
                .assertElementByTitleVisibility("Поле ввода Внутренний комментарий", "отображается")
                .clickOnElement("Кнопка Далее")
                .checkElementByTitleContains("Модальное окно предупреждения", "Пожалуйста, заполните внутренний комментарий")
                .clickOnElement("Кнопка ОК на Модальном окне предупреждения")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "")
                .clickOnElement("Кнопка Сохранить (header)")
                .closeCurrentTab();
    }

    @Test
    @Tag("internal_comment_1725606")
    @DisplayName("1725606 - Проверка необязательности заполнения внутреннего комментария при заполнении блока результата проверки")
    @WorkItemIds({"1725606"})
    public void internal_comment_1725606() {
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Невозможно запросить ФССП")
                .assertElementByTitleVisibility("Поле ввода Внутренний комментарий", "не отображается")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "")
                .clickOnElement("Кнопка Сохранить (header)")
                .closeCurrentTab();
    }
}
