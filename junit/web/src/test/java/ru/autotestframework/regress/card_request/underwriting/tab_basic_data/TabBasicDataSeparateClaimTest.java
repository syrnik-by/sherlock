package ru.autotestframework.regress.card_request.underwriting.tab_basic_data;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import static ru.autotestframework.steps.asserts.Asserts.assertIsEquals;

@Tag("regress")
@Tag("personal_account")
@Tag("underwriting")
@Tag("tab_basic_data_separate_claim")
@ClassName("На каждый кейс отдельная заявка. Карточка заявки. Андеррайтинг")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TabBasicDataSeparateClaimTest extends BaseTest {

    private String claim;

    @BeforeEach
    public void login(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3857556.json", 1, testInfo).get(0);
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi()
                    .openMenuLinks("Личный кабинет");
        }
    }

    @AfterEach
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("smoke")
    @Tag("editing_fields_general_queue_1721989")
    @DisplayName("1721989 - Редактирование полей при открытии новой заявки из общей Очереди")
    @WorkItemIds({"1721989"})
    public void editing_fields_general_queue_1721989() {

        loginPage
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim)
                .doubleClickByText(claim).switchToNewTab().waitBusyCondition()
                .goTo(cardRequestPage)
                .assertElementByTitleActivity("Вкладка основные данные", "активен")
                .assertElementByTitleNotAvailableEditing("Выпадающий список Причина доработки", "не доступен для редактирования")
                .assertElementByTitleNotAvailableEditing("Выпадающий список Занятость подтверждена", "не доступен для редактирования")
                .assertElementByTitleNotAvailableEditing("Выпадающий список Телефон подтвержден", "не доступен для редактирования")
                .assertElementByTitleNotAvailableEditing("Поле ввода Занятость подтверждена по:", "не доступен для редактирования")

                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .assertElementByTitleNotAvailableEditing("Выпадающий список Тип заявки", "не доступен для редактирования")
                .assertElementByTitleNotAvailableEditing("Выпадающий список Проведенные проверки", "не доступен для редактирования");
    }

    @Test
    @Tag("smoke")
    @Tag("editing_fields_personal_account_3857584")
    @DisplayName("3857584 - Редактирование полей при открытии новой заявки из Личного кабинета")
    @WorkItemIds({"3857584"})
    public void editing_fields_personal_account_3857584() {

        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage
                .clickOnElement("Раздел Андеррайтинг")
                .doubleClickByText(claim).switchToNewTab()
                .goTo(cardRequestPage).waitBusyCondition()

                .fillInput("Поле ввода Комментарий МРК и отлагательных условий", "для МРК")
                .fillInput("Поле ввода Внутренний комментарий андеррайтера", "от андера")
                .selectValueFromDropDownList("Выпадающий список Занятость подтверждена", "Звонок не назначался")
                .selectValueFromDropDownList("Выпадающий список Телефон подтвержден", "Да")
                .clickOnElement("Кнопка редактирования поля Занятость подтверждена по:")
                .fillInput("Поле ввода Занятость подтверждена по:", "9998887766")

                .checkElementByTitleEquals("Выпадающий список Занятость подтверждена", "Звонок не назначался")
                .checkElementByTitleEquals("Выпадающий список Телефон подтвержден", "Да");
        assertIsEquals("для МРК", cardRequestPage.getValueByElementTitle("Поле ввода Комментарий МРК и отлагательных условий"), "Поле ввода Комментарий МРК и отлагательных условий");
        assertIsEquals("от андера", cardRequestPage.getValueByElementTitle("Поле ввода Внутренний комментарий андеррайтера"), "Поле ввода Внутренний комментарий андеррайтера");

        String partFirst = cardRequestPage.getValueByElementTitle("Поле введенного телефона Занятость подтверждена по: часть1");
        String partSecond = cardRequestPage.getValueByElementTitle("Поле введенного телефона Занятость подтверждена по: часть2");
        String partThird = cardRequestPage.getValueByElementTitle("Поле введенного телефона Занятость подтверждена по: часть3");
        String partFourth = cardRequestPage.getValueByElementTitle("Поле введенного телефона Занятость подтверждена по: часть4");
        assertIsEquals("9998887766", partFirst + partSecond + partThird + partFourth, "Поле введенного телефона Занятость подтверждена по:" + partFirst + partSecond + partThird + partFourth);
    }

    @Test
    @Tag("smoke")
    @Tag("editing_fields_personal_account_3857584")
    @DisplayName("1721994 - Обновление комментария - разные андеррайтеры.")
    @WorkItemIds({"1721994"})
    public void update_comment_any_undwr_1721994() {

        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage
                .clickOnElement("Раздел Андеррайтинг")
                .doubleClickByText(claim).switchToNewTab()
                .goTo(cardRequestPage).waitBusyCondition()
                .fillInput("Поле ввода Внутренний комментарий андеррайтера", "коммент андера1")
                .goTo(underwriterDecisionPage)
                .clickOnElement("Кнопка Сохранить и закрыть").switchToOneTab()
                .goTo(personalAccountPage)
                .clickOnElement("Кнопка выхода")

                .goTo(loginPage)
                .openAuthorizationPage()
                .loginViaUiOnUser("user2")
                .checkElementByTitleEquals("Фамилия и Имя пользователя", "Автоматическое Тестирование2");
        actionsClaimSteps.appointResponsiblePerson(claim, "testat2");
        loginPage
                .openMenuLinks("Личный кабинет")
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Андеррайтинг")
                .doubleClickByText(claim).switchToNewTab()
                .goTo(cardRequestPage).waitBusyCondition()
                .clearInput("Поле ввода Внутренний комментарий андеррайтера")
                .fillInput("Поле ввода Внутренний комментарий андеррайтера", "коммент андера2")
                .goTo(underwriterDecisionPage)
                .clickOnElement("Кнопка Сохранить и закрыть").switchToOneTab()
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Андеррайтинг")
                .doubleClickByText(claim).switchToNewTab();

        assertIsEquals("коммент андера2", cardRequestPage.getValueByElementTitle("Поле ввода Внутренний комментарий андеррайтера"), "Поле ввода Внутренний комментарий андеррайтера");

        cardRequestPage
                .clickOnElement("Кнопка История(Внутренний комментарий андеррайтера)")
                .clickOnElement("Кнопка Раскрыть все комментарии")
                .checkElementByTitleEquals("Поле с Историей комментариев 1", "коммент андера1")
                .checkElementByTitleEquals("Поле с Историей комментариев 2", "коммент андера2")
                .clickOnElement("Кнопка закрыть Окно История комментариев")
                .closeCurrentTab()
                .goTo(personalAccountPage)
                .clickOnElement("Кнопка выхода");
    }
}