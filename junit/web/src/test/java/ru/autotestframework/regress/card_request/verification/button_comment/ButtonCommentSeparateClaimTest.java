package ru.autotestframework.regress.card_request.verification.button_comment;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import java.util.Map;

import static ru.autotestframework.steps.asserts.Asserts.assertIsEquals;
import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;
import static ru.autotestframework.utils.Constants.REQUESTS;

@Tag("regress")
@Tag("card_request")
@Tag("verification")
@Tag("button_comment")
@Tag("button_comment_test")
@ClassName("Карточка заявки. Верификация. На каждый кейс отдельная заявка. Кнопка Комментарий")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ButtonCommentSeparateClaimTest extends BaseTest {

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

    @AfterEach
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("smoke")
    @Tag("checking_comment_1725627")
    @DisplayName("1725627 - Отображение комментария для МРК")
    @WorkItemIds({"1725627"})
    public void checking_comment_1725627(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "Code", "stub5");

        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3250685.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.doubleClickByText(claim).switchToNewTab()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка дохода/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Отправить на доработку")
                .selectValueFromDropDownList("Выпадающий список Причина доработки", "Ошибка МРК при вводе контактных данных клиента (адресов, телефонов)")
                .fillInput("Поле ввода Внутренний комментарий", "коммент")
                .fillInput("Поле ввода Комментарий для МРК", "ком для МРК")
                .clickOnElement("Кнопка Далее")
                .elementByTitleVisibility("Кнопка Завершить проверку", "отображается");
        assertIsEquals("ком для МРК", incomeVerificationPage.getValueByElementTitle("Поле ввода Комментарий для МРК"), "Поле ввода Комментарий для МРК");

        incomeVerificationPage
                .clickOnElement("Кнопка Завершить проверку")
                .waitBusyCondition().switchToOneTab();

        actionsClaimSteps.checkStatusClaimFromDb(claim, 9);

        actionsClaimSteps.repeatSendSclRequestToStand("9");
        personalAccountPage.waitBusyCondition();
        assertIsTrue(personalAccountPage.getTextFromTable("Таблица в работе", 1, "Статус заявки").
                        equals("На рассмотрении"),
                "Значение в столбце Статус заявки должно быть На рассмотрении");
        actionsClaimSteps.checkStatusClaimFromDb(claim, 4);

        personalAccountPage
                .doubleClickByText(claim).switchToNewTab()
                .goTo(cardRequestPage)
                .assertElementByTitleActivity("Кнопка Комментарии", "активен")
                .clickOnElement("Кнопка Комментарии")
                .checkElementByTitleEquals("Метка", "Комментарий для МРК/L0");
        assertIsEquals("ком для МРК", cardRequestPage.getValueByElementTitle("Поле Комментарий"), "Поле Комментарий");
        cardRequestPage.closeCurrentTab();
    }

    @Test
    @Tag("checking_comment_1725625")
    @DisplayName("1725625 - Проверка отображения кнопки \"Комментарии\" на каждой экранной форме")
    @WorkItemIds({"1725625"})
    public void checking_comment_1725625(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "Code", "stub7");
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3250685.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(checkingOpenSourcesPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка открытых источников/Версия 1")
                .assertElementByTitleVisibility("Кнопка Комментарии", "отображается")
                .clickOnElement("Чек-бокс Негатив по работодателю не выявлен")
                .clickOnElement("Кнопка Далее")
                .assertElementByTitleVisibility("Кнопка Комментарии", "отображается")
                .closeCurrentTab();
    }
}