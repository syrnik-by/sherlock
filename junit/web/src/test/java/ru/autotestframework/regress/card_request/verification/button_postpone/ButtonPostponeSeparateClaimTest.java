package ru.autotestframework.regress.card_request.verification.button_postpone;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static ru.autotestframework.utils.Constants.DF;

@Tag("regress")
@Tag("card_request")
@Tag("verification")
@Tag("button_postpone")
@Tag("button_postpone_separate_claim")
@ClassName("Карточка заявки. Верификация. На каждый кейс отдельная заявка. Кнопка Отложить")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ButtonPostponeSeparateClaimTest extends BaseTest {

    private LocalDateTime nowTime;
    private String claim;

    @BeforeAll
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
        checkingOpenSourcesPage.closeCurrentTab();
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("checking_presence_button_1725588")
    @DisplayName("1725588 - Проверка присутствия кнопки \"Отложить\"  на каждой экранной форме")
    @WorkItemIds({"1725588"})
    public void checking_presence_button_1725588(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "codeBor", "stub1",
                "codeCobor", "stub1");

        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3242050.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab();

        cardRequestPage
                .elementByTitleVisibility("Кнопка Отложить", "отображается")
                .clickOnElement("Шаг №2")
                .elementByTitleVisibility("Кнопка Отложить", "отображается");
    }

    @Test
    @Tag("checking_active_button_1725585")
    @DisplayName("1725585 - Проверка активности/не активности кнопки Отложить в зависимости от режима просмотра/обработки заявки")
    @WorkItemIds({"1725585"})
    public void checking_active_button_1725585(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "Code", "stub1");

        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3209048.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab();
        cardRequestPage
                .assertElementByTitleActivity("Кнопка Отложить", "активен");

        checkingOpenSourcesPage.closeCurrentTab();
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3209048.json", 1, testInfo, claimParams).get(0);
        cardRequestPage
                .goTo(loginPage)
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim)
                .doubleClickByText(claim).switchToNewTab();
        cardRequestPage
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - Ожидает")
                .assertElementByTitleActivity("Кнопка Отложить", "не активен");
    }

    @Test
    @Tag("smoke")
    @Tag("checking_save_comment_1725586")
    @DisplayName("1725586 - Проверка сохранения комментария при откладывании заявки и просмотр этого комментария по кнопке \"Внутренний комментарий\"")
    @WorkItemIds({"1725586"})
    public void checking_save_comment_1725586(TestInfo testInfo) {
        String comment = "Проверка сохранения комментария";
        Map<String, String> claimParams = Map.of(
                "Code", "stub1");

        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3242031.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab();
        cardRequestPage
                .assertElementByTitleActivity("Кнопка Отложить", "активен")
                .clickOnElement("Кнопка Отложить")
                .elementByTitleVisibility("Модальное окно Перевод заявки в отложенные", "отображается")
                .selectValueFromDropDownList("Выпадающий список Причина (Перевод заявки в отложенные)", "Недозвон Заемщику")
                .fillInput("Поле ввода комментарий (Перевод заявки в отложенные)", comment)
                .fillInput("Поле ввода Время для звонка участнику", getTimeNow().plusMinutes(7).format(DF))
                .clickOnElement("Кнопка Отложить заявку")
                .switchToOneTab()
                .goTo(personalAccountPage).waitBusyCondition()
                .clickOnElement("Кнопка раскрыть таблицу Отложено")
                .doubleClickByText(claim).switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Внутренний комментарий")
                .checkElementByTitleContains("Поле История комментариев", comment);
    }

    @Test
    @Tag("checking_save_info_1725589")
    @DisplayName("1725589 - Проверка сохранения всей информации, введенной при проверках ")
    @WorkItemIds({"1725589"})
    public void checking_save_info_1725589(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "codeBor", "stub1",
                "codeCobor", "stub1");

        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3242050.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab();
        cardRequestPage
                .assertElementByTitleActivity("Кнопка Отложить", "активен")
                .goTo(fsspPage)
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Исполнительное производство не найдено")
                .checkElementByTitleContains("Выпадающий список Результат проверки", "Исполнительное производство не найдено")
                .goTo(cardRequestPage)
                .clickOnElement("Шаг №2")
                .assertElementByTitleActivity("Кнопка Отложить", "активен")
                .clickOnElement("Кнопка Отложить")
                .selectValueFromDropDownList("Выпадающий список Причина (Перевод заявки в отложенные)", "Недозвон Заемщику")
                .fillInput("Поле ввода комментарий (Перевод заявки в отложенные)", "Проверка сохранения всей информации")
                .fillInput("Поле ввода Время для звонка участнику", getTimeNow().plusMinutes(7).format(DF))
                .clickOnElement("Кнопка Отложить заявку")
                .switchToOneTab()
                .goTo(personalAccountPage).waitBusyCondition()
                .clickOnElement("Кнопка раскрыть таблицу Отложено")
                .doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .checkElementByTitleContains("Выпадающий список Результат проверки", "Исполнительное производство не найдено")
                .assertElementByTitleBlock("Выпадающий список Результат проверки", "заблокирован");
    }

    private LocalDateTime getTimeNow() {
        nowTime = LocalDateTime.now();
        return nowTime;
    }
}