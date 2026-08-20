package ru.autotestframework.regress.card_request.verification.button_return_to_queue;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.ExternalId;
import ru.psb.testit.annotations.WorkItemIds;

import java.time.LocalDateTime;

import static ru.autotestframework.steps.actions.BaseActions.sleep;
import static ru.autotestframework.utils.Constants.DF;

@Tag("regress")
@Tag("verification")
@Tag("card_request")
@Tag("button_return_to_queue")
@ClassName("Карточка заявки. Верификация. Кнопка \"Вернуть в очередь\". На заявке №1")
public class ButtonReturnToQueueClaimNumberOneTest extends BaseTest {

    private String claim;

    @BeforeAll
    public void createClaim(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2056882.json", 1, testInfo).get(0);
    }

    @BeforeEach
    public void login() {
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi();
        }
    }

    @AfterAll
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @ParameterizedTest
    @CsvSource({
            "2653035, Очереди",
            "2653040, Поиск"})
    @Tag("button_return_to_queue_2653035_2653040")
    @DisplayName("{id} - Кнопка \"Вернуть в очередь\" неактивна на вкладке {tabName}. Заявка со статусом \"Ожидает\"")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void button_return_to_queue_2653035_2653040(String id, String tabName) {
        personalAccountPage
                .openMenuLinks(tabName)
                .goTo(tabName.equals("Очереди") ? queuesPage : searchPage)
                .waitBusyCondition()
                .searchClaimOnPage(claim)
                .doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .assertElementByTitleActivity("Кнопка Вернуть в очередь", "не активен")
                .closeCurrentTab();
    }

    @ParameterizedTest
    @CsvSource({
            "2653044, Очереди",
            "2653043, Поиск"})
    @Tag("button_return_to_queue_2653044_2653043")
    @DisplayName("{id} - Кнопка \"Вернуть в очередь\" неактивна на вкладке {tabName}. Заявка со статусом \"На рассмотрении\"")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void button_return_to_queue_2653044_2653043(String id, String tabName) {
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage
                .openMenuLinks(tabName)
                .goTo(tabName.equals("Очереди") ? queuesPage : searchPage)
                .waitBusyCondition()
                .searchClaimOnPage(claim)
                .doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .assertElementByTitleActivity("Кнопка Вернуть в очередь", "не активен")
                .closeCurrentTab()
                .goTo(personalAccountPage)
                .openMenuLinks("Личный кабинет")
                .waitBusyCondition()
                .doubleClickByText(claim).switchToNewTab()
                .waitBusyCondition()
                .goTo(fsspPage)
                .assertElementByTitleActivity("Кнопка Вернуть в очередь", "активен")
                .clickOnElement("Кнопка Вернуть в очередь")
                .checkElementByTitleContains("Модальное окно предупреждения", "Заявка будет возвращена в очередь!\n" +
                        "Выполненные изменения будут утеряны. Да\\Нет?")
                .clickOnElement("Кнопка Да модального окна").switchToOneTab().waitBusyCondition();
        actionsClaimSteps.checkStatusClaimFromDb(claim, 3);

    }

    @Test
    @Tag("button_return_to_queue_2653041")
    @DisplayName("2653041 - Кнопка \"Вернуть в очередь\" активна в Личном кабинете. Заявка со статусом \"На рассмотрении\"")
    @WorkItemIds({"2653041"})
    public void button_return_to_queue_2653041() {
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage
                .openMenuLinks("Личный кабинет")
                .waitBusyCondition()
                .doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .assertElementByTitleActivity("Кнопка Вернуть в очередь", "активен")
                .clickOnElement("Кнопка Вернуть в очередь")
                .checkElementByTitleContains("Модальное окно предупреждения", "Заявка будет возвращена в очередь!\n" +
                        "Выполненные изменения будут утеряны. Да\\Нет?")
                .clickOnElement("Кнопка Да модального окна").switchToOneTab().waitBusyCondition();
        actionsClaimSteps.checkStatusClaimFromDb(claim, 3);
    }

    @Test
    @Tag("button_return_to_queue_2653039")
    @DisplayName("2653039 - Кнопка \"Вернуть в очередь\" активна, когда отложенная заявка вернулась на рассмотрение Пользователю")
    @WorkItemIds({"2653039"})
    public void button_return_to_queue_2653039() {
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage
                .openMenuLinks("Личный кабинет")
                .waitBusyCondition()
                .doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .assertElementByTitleActivity("Кнопка Вернуть в очередь", "активен")
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Отложить")
                .selectValueFromDropDownList("Выпадающий список Причина (Перевод заявки в отложенные)", "Дальний регион")
                .fillInput("Поле ввода Время возврата заявки", LocalDateTime.now().plusMinutes(6).format(DF))
                .clickOnElement("Кнопка Отложить заявку")
                .switchToOneTab()
                .goTo(personalAccountPage)
                .waitBusyCondition()
                .checkNotifications()
                .waitText(120, claim)
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .assertElementByTitleActivity("Кнопка Вернуть в очередь", "активен")
                .clickOnElement("Кнопка Вернуть в очередь")
                .checkElementByTitleContains("Модальное окно предупреждения", "Заявка будет возвращена в очередь!\n" +
                        "Выполненные изменения будут утеряны. Да\\Нет?")
                .clickOnElement("Кнопка Да модального окна").switchToOneTab().waitBusyCondition();
        actionsClaimSteps.checkStatusClaimFromDb(claim, 3);
    }

    @Test
    @Tag("button_return_to_queue_2653038")
    @DisplayName("2653038 - Кнопка \"Вернуть в очередь\" неактивна. Заявка со статусом \"Отложена (рассмотрение)\"")
    @WorkItemIds({"2653038"})
    public void button_return_to_queue_2653038() {
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage
                .openMenuLinks("Личный кабинет")
                .waitBusyCondition()
                .doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .assertElementByTitleActivity("Кнопка Вернуть в очередь", "активен")
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Отложить")
                .selectValueFromDropDownList("Выпадающий список Причина (Перевод заявки в отложенные)", "Заемщик просит перезвонить")
                .fillInput("Поле ввода Время для звонка участнику", LocalDateTime.now().format(DF))
                .clickOnElement("Кнопка Отложить заявку")
                .switchToOneTab()
                .goTo(personalAccountPage)
                .doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .assertElementByTitleActivity("Кнопка Вернуть в очередь", "активен")
                .clickOnElement("Кнопка Вернуть в очередь")
                .checkElementByTitleContains("Модальное окно предупреждения", "Заявка будет возвращена в очередь!\n" +
                        "Выполненные изменения будут утеряны. Да\\Нет?")
                .clickOnElement("Кнопка Да модального окна").switchToOneTab().waitBusyCondition();
        actionsClaimSteps.checkStatusClaimFromDb(claim, 3);
    }
}