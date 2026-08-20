package ru.autotestframework.regress.card_request.verification.button_internal_comment;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.ResourceLock;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.codeborne.selenide.Selenide.$$x;
import static ru.autotestframework.steps.asserts.Asserts.assertIsEquals;
import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;

@Tag("regress")
@Tag("verification")
@Tag("card_request")
@Tag("button_internal_comment")
@ClassName("Карточка заявки. Верификация. Кнопка \"Внутренний комментарий\". На каждый кейс отдельная заявка")
public class ButtonInternalCommentSeparateClaimTest extends BaseTest {

    private String claim;

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
    @Tag("checking_save_internal_comment_1725608")
    @DisplayName("1725608 - Проверка НЕзатирания внутреннего комментария после откладывания заявки")
    @WorkItemIds({"1725608"})
    public void checking_save_internal_comment_1725608(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "Code", "stub1");

        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3245115.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Одобрить")
                .fillInput("Поле ввода Внутренний комментарий", "коммент")
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Сохранить (header)")
                .clickOnElement("Кнопка Отложить")
                .selectValueFromDropDownList("Выпадающий список Причина (Перевод заявки в отложенные)", "Вопрос в ГО")
                .clickOnElement("Кнопка Отложить заявку").switchToOneTab()
                .goTo(personalAccountPage).waitBusyCondition()
                .clickOnElement("Кнопка раскрыть таблицу Отложено")
                .doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Взять в работу")
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - На рассмотрении")
                .goTo(fsspPage)
                .checkElementByTitleEquals("Выпадающий список Результат по заявке", "Одобрить");
        assertIsEquals("коммент", fsspPage.getValueByElementTitle("Поле ввода Внутренний комментарий"), "Поле ввода Внутренний комментарий");
        cardRequestPage
                .clickOnElement("Кнопка Внутренний комментарий")
                .checkElementByTitleContains("Поле История комментариев", "коммент");
    }

    @Test
    @Tag("checking_active_internal_comment_button_1725613")
    @DisplayName("1725613 - Проверка активности кнопки \"Внутренний комментарий\" из любого режима")
    @WorkItemIds({"1725613"})
    public void checking_active_internal_comment_button_1725613(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "Code", "stub5");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3245115.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(incomeVerificationPage)
                .assertElementByTitleActivity("Кнопка Внутренний комментарий", "активен");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3245115.json", 1, testInfo, claimParams).get(0);
        incomeVerificationPage.closeCurrentTab()
                .goTo(loginPage)
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim);
        String statusClaim = queuesPage.getTextFromTable("Таблица результаты поиска", 1, "Статус заявки");
        assertIsTrue(statusClaim.equals("Ожидает"),
                "Значение в столбце Статус заявки должно быть равно Кредит разрешен. Фактическое значение: " + statusClaim);
        queuesPage.doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(incomeVerificationPage)
                .assertElementByTitleActivity("Кнопка Внутренний комментарий", "активен")
                .closeCurrentTab();
    }

    @Test
    @Tag("checking_input_internal_comment_1725619")
    @DisplayName("1725619 - Проверка возможности ввода комментария в поле ввода комментария из режима просмотра и из режима обработки заявки")
    @WorkItemIds({"1725619"})
    public void checking_input_internal_comment_1725619(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "Code", "stub1");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3245115.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .assertElementByTitleActivity("Кнопка Внутренний комментарий", "активен")
                .clickOnElement("Кнопка Внутренний комментарий")
                .assertElementByTitleNotAvailableEditing("Поле ввода История комментариев", "доступен для редактирования")
                .clickOnElement("Кнопка закрыть Окно");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3245115.json", 1, testInfo, claimParams).get(0);
        fsspPage.closeCurrentTab()
                .goTo(loginPage)
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim);
        String statusClaim = queuesPage.getTextFromTable("Таблица результаты поиска", 1, "Статус заявки");
        assertIsTrue(statusClaim.equals("Ожидает"),
                "Значение в столбце Статус заявки должно быть равно Кредит разрешен. Фактическое значение: " + statusClaim);
        queuesPage.doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(fsspPage)
                .assertElementByTitleActivity("Кнопка Внутренний комментарий", "активен")
                .clickOnElement("Кнопка Внутренний комментарий")
                .assertElementByTitleNotAvailableEditing("Поле ввода История комментариев", "не доступен для редактирования")
                .closeCurrentTab();
    }

    @Test
    @Tag("checking_input_internal_comment_1725617")
    @DisplayName("1725617 - Проверка предзаполненности внутреннего комментария для текущей версии, если комментарий уже сохранялся в текущей версии")
    @WorkItemIds({"1725617"})
    public void checking_input_internal_comment_1725617(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "Code", "stub11");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3245115.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(verificationStrategyPage)
                .assertElementByTitleActivity("Кнопка Внутренний комментарий", "активен")
                .clickOnElement("Кнопка Внутренний комментарий")
                .assertElementByTitleNotAvailableEditing("Поле ввода История комментариев", "доступен для редактирования")
                .fillInput("Поле ввода История комментариев", "коммент")
                .clickOnElement("Кнопка Сохранить на модальном окне история комментариев")
                .clickOnElement("Кнопка закрыть Окно")
                .closeCurrentTab()
                .goTo(personalAccountPage)
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(verificationStrategyPage)
                .assertElementByTitleActivity("Кнопка Внутренний комментарий", "активен")
                .clickOnElement("Кнопка Внутренний комментарий");
        assertIsEquals("коммент", verificationStrategyPage.getValueByElementTitle("Поле История комментариев"), "Поле ввода Внутренний комментарий");
        verificationStrategyPage.closeCurrentTab();
    }

    @Test
    @Tag("checking_active_internal_comment_button_1725618")
    @DisplayName("1725618 - Проверка активности кнопки \"Внутренний комментарий\" из любого режима")
    @WorkItemIds({"1725618"})
    public void checking_active_save_comment_button_1725618(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "Code", "stub7");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3245115.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(verificationStrategyPage)
                .assertElementByTitleActivity("Кнопка Внутренний комментарий", "активен")
                .clickOnElement("Кнопка Внутренний комментарий")
                .assertElementByTitleActivity("Кнопка Сохранить на модальном окне история комментариев", "активен");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3245115.json", 1, testInfo, claimParams).get(0);
        verificationStrategyPage.closeCurrentTab()
                .goTo(loginPage)
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim);
        String statusClaim = queuesPage.getTextFromTable("Таблица результаты поиска", 1, "Статус заявки");
        assertIsTrue(statusClaim.equals("Ожидает"),
                "Значение в столбце Статус заявки должно быть равно Кредит разрешен. Фактическое значение: " + statusClaim);
        queuesPage.doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(verificationStrategyPage)
                .assertElementByTitleActivity("Кнопка Внутренний комментарий", "активен")
                .clickOnElement("Кнопка Внутренний комментарий")
                .assertElementByTitleActivity("Кнопка Сохранить на модальном окне история комментариев", "не активен")
                .closeCurrentTab();
    }

    @Test
    @Tag("checking_save_internal_comment_1725610")
    @DisplayName("1725610 - Проверка сохранения информации после нажатия кнопки \"Сохранить\" на форме ввода внутреннего комментария")
    @WorkItemIds({"1725610"})
    public void checking_save_internal_comment_1725610(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "Code", "stub1");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3245115.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .assertElementByTitleActivity("Кнопка Внутренний комментарий", "активен")
                .clickOnElement("Кнопка Внутренний комментарий")
                .assertElementByTitleActivity("Кнопка Сохранить на модальном окне история комментариев", "активен")
                .assertElementByTitleNotAvailableEditing("Поле ввода История комментариев", "доступен для редактирования")
                .fillInput("Поле ввода История комментариев", "коммент")
                .clickOnElement("Кнопка Сохранить на модальном окне история комментариев");
        String dateTimeNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy"));
        fsspPage.checkElementByTitleContains("Запись в истории комментариев", dateTimeNow + " ФССП Автоматическое Тестирование1")
                .closeCurrentTab();
    }

    @Test
    @Tag("checking_edit_internal_comment_1725620")
    @DisplayName("1725620 - Проверка возможности редактирования параметров в истории комментариев")
    @WorkItemIds({"1725620"})
    public void checking_save_internal_comment_1725620(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "Code", "stub1");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3245115.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .assertElementByTitleActivity("Кнопка Внутренний комментарий", "активен")
                .clickOnElement("Кнопка Внутренний комментарий")
                .assertElementByTitleActivity("Кнопка Сохранить на модальном окне история комментариев", "активен")
                .assertElementByTitleNotAvailableEditing("Поле ввода История комментариев", "доступен для редактирования")
                .fillInput("Поле ввода История комментариев", "коммент")
                .clickOnElement("Кнопка Сохранить на модальном окне история комментариев");
        String dateTimeNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy"));
        fsspPage.checkElementByTitleContains("Запись в истории комментариев", dateTimeNow + " ФССП Автоматическое Тестирование1")
                .clickOnElement("Кнопка Сохранить на модальном окне история комментариев")
                .clickOnElement("Кнопка закрыть Окно")
                .clickOnElement("Кнопка Внутренний комментарий")
                .assertElementByTitleNotAvailableEditing("Поле История комментариев", "не доступен для редактирования")
                .closeCurrentTab();
    }

    @Test
    @Tag("checking_input_internal_comment_1725611")
    @DisplayName("1725611 - Проверка возможности ввода только одного внутреннего комментария в рамках одной версии проверки")
    @WorkItemIds({"1725611"})
    public void checking_input_internal_comment_1725611(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "Code", "stub1");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3245115.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .assertElementByTitleActivity("Кнопка Внутренний комментарий", "активен")
                .clickOnElement("Кнопка Внутренний комментарий")
                .assertElementByTitleActivity("Кнопка Сохранить на модальном окне история комментариев", "активен")
                .assertElementByTitleNotAvailableEditing("Поле ввода История комментариев", "доступен для редактирования")
                .fillInput("Поле ввода История комментариев", "коммент")
                .clickOnElement("Кнопка Сохранить на модальном окне история комментариев")
                .closeCurrentTab()
                .goTo(personalAccountPage)
                .doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .clickOnElement("Кнопка Внутренний комментарий");
        assertIsEquals("коммент", fsspPage.getValueByElementTitle("Поле ввода История комментариев"),
                "Поле ввода История комментариев");
        fsspPage.clearInput("Поле ввода История комментариев")
                .fillInput("Поле ввода История комментариев", "Комментик")
                .clickOnElement("Кнопка Сохранить на модальном окне история комментариев");
        String dateTimeNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy"));
        assertIsEquals("Комментик", fsspPage.getValueByElementTitle("Поле ввода История комментариев"),
                "Поле ввода История комментариев");
        fsspPage.checkElementByTitleContains("Запись в истории комментариев", dateTimeNow + " ФССП Автоматическое Тестирование1")
                .closeCurrentTab();

    }

    @Test
    @Tag("checking_input_internal_comment_1725614")
    @DisplayName("1725614 - Проверка возможности ввода только одного внутреннего комментария в рамках одной версии проверки")
    @WorkItemIds({"1725614"})
    public void checking_input_internal_comment_1725614(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "Code1", "stub1",
                "Code2", "stub11");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3248522.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Одобрить стратегию")
                .fillInput("Поле ввода Внутренний комментарий", "коммент")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .switchToOneTab()
                .waitBusyCondition();
        actionsClaimSteps.appointResponsiblePerson(claim);
        actionsClaimSteps.checkStatusClaimFromDb(claim, 4);
        personalAccountPage.openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim)
                .doubleClickByText(claim).switchToNewTab().waitBusyCondition()
                .goTo(customerCallPage)
                .clickOnElement("Кнопка Основные данные").switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Результаты проверок")
                .goTo(resultCheck)
                .clickOnElement("Вкладка Ручные проверки")
                .goTo(manualChecks)
                .clickOnElement("Кнопка Верификация назначенные проверки")
                .clickOnElement("Ссылка Открыть стратегию").switchToNewTab()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1");
        assertIsEquals("коммент", fsspPage.getValueByElementTitle("Поле ввода Внутренний комментарий"),
                "Поле ввода Поле ввода Внутренний комментарий");
        fsspPage.clickOnElement("Кнопка Внутренний комментарий");
        assertIsEquals("коммент", fsspPage.getValueByElementTitle("Поле История комментариев"),
                "Поле История комментариев");
        fsspPage.switchToFirstTab()
                .goTo(personalAccountPage)
                .openMenuLinks("Личный кабинет")
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(customerCallPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон клиента/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Одобрить стратегию");
        assertIsEquals("", customerCallPage.getValueByElementTitle("Поле ввода Внутренний комментарий"),
                "Поле ввода Поле ввода Внутренний комментарий");
        customerCallPage.clickOnElement("Кнопка Внутренний комментарий")
                .checkElementByTitleContains("Запись в истории комментариев", "ФССП");
        assertIsEquals("коммент", fsspPage.getValueByElementTitle("Поле История комментариев"),
                "Поле История комментариев");
        customerCallPage.closeCurrentTab();
    }

    @Test
    @Tag("checking_input_internal_comment_1725621")
    @DisplayName("1725621 - Проверка возможности ввода только одного внутреннего комментария в рамках одной версии проверки")
    @WorkItemIds({"1725621"})
    public void checking_input_internal_comment_1725621(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "Code", "stub1");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3245115.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Одобрить")
                .fillInput("Поле ввода Внутренний комментарий", "коммент")
                .clickOnElement("Кнопка Сохранить (header)")
                .clickOnElement("Кнопка Закрыть")
                .switchToOneTab()
                .goTo(personalAccountPage)
                .clickOnElement("Кнопка выхода")
                .goTo(loginPage)
                .openAuthorizationPage()
                .loginViaUiOnUser("user2")
                .checkElementByTitleEquals("Фамилия и Имя пользователя", "Автоматическое Тестирование2");
        actionsClaimSteps.appointResponsiblePerson(claim, "testat2");
        loginPage.openMenuLinks("Личный кабинет")
                .doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .clearInput("Поле ввода Внутренний комментарий")
                .fillInput("Поле ввода Внутренний комментарий", "коммент2")
                .clickOnElement("Кнопка Сохранить (header)");
        assertIsEquals("коммент2", customerCallPage.getValueByElementTitle("Поле ввода Внутренний комментарий"),
                "Поле ввода Поле ввода Внутренний комментарий");
        customerCallPage.clickOnElement("Кнопка Внутренний комментарий");
        isComment2AboveComment("коммент", "коммент2");
        customerCallPage.closeCurrentTab()
                .goTo(personalAccountPage)
                .clickOnElement("Кнопка выхода");
    }

    @Test
    @Tag("checking_save_internal_comment_3248626")
    @DisplayName("3248626 - Проверка сохранения внутреннего комментария для каждой стратегии")
    @WorkItemIds({"3248626"})
    public void checking_save_internal_comment_3248626(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "Code1", "stub1",
                "Code2", "stub11");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3248522.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Одобрить стратегию")
                .fillInput("Поле ввода Внутренний комментарий", "коммент ФССП")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .switchToOneTab()
                .waitBusyCondition();
        actionsClaimSteps.appointResponsiblePerson(claim);
        actionsClaimSteps.checkStatusClaimFromDb(claim, 4);
        personalAccountPage
                .doubleClickByText(claim).switchToNewTab().waitBusyCondition()
                .goTo(customerCallPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон клиента/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Одобрить стратегию")
                .fillInput("Поле ввода Внутренний комментарий", "коммент Прозвон")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .switchToOneTab()
                .waitBusyCondition()
                .openMenuLinks("Поиск")
                .goTo(searchPage)
                .searchClaimOnPage(claim)
                .doubleClickByText(claim).switchToNewTab().waitBusyCondition()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Результаты проверок")
                .goTo(resultCheck)
                .clickOnElement("Вкладка Ручные проверки")
                .goTo(manualChecks)
                .clickOnElement("Кнопка Верификация назначенные проверки")
                .clickOnCellFromTable("Таблица Верификация назначенные проверки", 1, 4, "Открыть стратегию")
                .switchToNewTab()
                .goTo(fsspPage)
                .clickOnElement("Кнопка Внутренний комментарий");
        isComment2AboveComment("коммент ФССП", "коммент Прозвон");
        fsspPage.switchToFirstTab()
                .goTo(searchPage)
                .doubleClickByText(claim).switchToNewTab().waitBusyCondition()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Результаты проверок")
                .goTo(resultCheck)
                .clickOnElement("Вкладка Ручные проверки")
                .goTo(manualChecks)
                .clickOnElement("Кнопка Верификация назначенные проверки")
                .clickOnCellFromTable("Таблица Верификация назначенные проверки", 2, 4, "Открыть стратегию")
                .switchToNewTab()
                .goTo(customerCallPage)
                .clickOnElement("Кнопка Внутренний комментарий");
        isComment2AboveComment("коммент ФССП", "коммент Прозвон");
        customerCallPage.closeCurrentTab();
    }

    private void isComment2AboveComment(String commentText1, String commentText2) {
        // Находим все элементы по локатору
        ElementsCollection textAreas = $$x("//div[@class= 'ng-star-inserted']//textarea");

        SelenideElement comment2 = textAreas.get(0);   // Первый элемент
        SelenideElement comment = textAreas.get(1);  // Второй элемент

        assertIsTrue(Objects.equals(comment.getValue(), commentText1), "Проверить, что в истории комментариев содержится запись с текстом \"" + commentText1 + "\"");
        assertIsTrue(Objects.equals(comment2.getValue(), commentText2), "Проверить, что в истории комментариев содержится запись с текстом \"" + commentText2 + "\"");

        // Получаем координаты элементов
        int commentY = comment.getLocation().getY();
        int comment2Y = comment2.getLocation().getY();

        assertIsTrue(comment2Y < commentY, "Проверить, что комментарий второго пользователя находится выше комментария первого пользователя");
    }
}
