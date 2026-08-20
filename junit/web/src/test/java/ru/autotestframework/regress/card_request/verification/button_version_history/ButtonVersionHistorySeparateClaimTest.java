package ru.autotestframework.regress.card_request.verification.button_version_history;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

import static ru.autotestframework.steps.asserts.Asserts.assertIsEquals;
import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;
import static ru.autotestframework.utils.Constants.*;

@Tag("regress")
@Tag("verification")
@Tag("card_request")
@Tag("button_history_version")
@ClassName("Карточка заявки. Верификация. Кнопка \"История версий\". На каждый кейс отдельная заявка")
public class ButtonVersionHistorySeparateClaimTest extends BaseTest {

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
    @Tag("checking_save_internal_comment_1725612")
    @DisplayName("1725612 - Проверка сохранения внутреннего комментария после доработки заявки")
    @WorkItemIds({"1725612"})
    public void checking_save_internal_comment_1725612(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "Code", "stub1");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3245115.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Отправить на доработку")
                .selectValueFromDropDownList("Выпадающий список Причина доработки", "Ошибка МРК при вводе контактных данных клиента (адресов, телефонов)")
                .fillInput("Поле ввода Внутренний комментарий", "коммент внут1")
                .fillInput("Поле ввода Комментарий для МРК", "Коммент для мрк")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .switchToOneTab()
                .waitBusyCondition();
        actionsClaimSteps.repeatSendSclRequestToStand("9", "data/json/claim_template_4100124.json");
        actionsClaimSteps.executeQuery(REQUESTS, "select status_id from requests.rqs_request rr where rr.claim_id = '" + claim + "' " +
                "and rr.is_current_version = true");
        String valuesFromDb = actionsClaimSteps.getVariables("status_id");
        assertIsTrue(valuesFromDb.equals("4"), "Значение из БД " + valuesFromDb + ", а должно быть равно 4");
        personalAccountPage
                .doubleClickByText(claim).switchToNewTab().waitBusyCondition()
                .goTo(fsspPage)
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Одобрить стратегию");
        assertIsEquals("коммент внут1", fsspPage.getValueByElementTitle("Поле ввода Внутренний комментарий"),
                "Поле ввода Поле ввода Внутренний комментарий");
        fsspPage.clickOnElement("Кнопка Внутренний комментарий")
                .fillInput("Поле ввода История комментариев", "коммент внут2")
                .clickOnElement("Кнопка Сохранить на модальном окне история комментариев")
                .clickOnElement("Кнопка закрыть Окно")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToNewTab()
                .goTo(personalAccountPage)
                .openMenuLinks("Поиск")
                .goTo(searchPage)
                .waitBusyCondition()
                .searchClaimOnPage(claim)
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Результаты проверок")
                .goTo(resultCheck)
                .clickOnElement("Вкладка Ручные проверки")
                .goTo(manualChecks)
                .clickOnElement("Кнопка Верификация назначенные проверки")
                .clickOnElement("Ссылка Открыть стратегию").switchToNewTab()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 2");
        assertIsEquals("коммент внут2", fsspPage.getValueByElementTitle("Поле ввода Внутренний комментарий"),
                "Поле ввода Поле ввода Внутренний комментарий");
        fsspPage.clickOnElement("Кнопка Внутренний комментарий")
                .checkElementByTitleContains("Поле История комментариев (все)", "коммент внут2")
                .checkElementByTitleContains("Поле История комментариев (все)", "коммент внут1")
                .clickOnElement("Кнопка закрыть Окно")
                .selectValueFromDropDownList("Выпадающий список История версий", "Версия 1")
                .switchToNewTab()
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1");
        assertIsEquals("коммент внут2", fsspPage.getValueByElementTitle("Поле ввода Внутренний комментарий"),
                "Поле ввода Поле ввода Внутренний комментарий");
        fsspPage.clickOnElement("Кнопка Внутренний комментарий")
                .checkElementByTitleContains("Поле История комментариев (все)", "коммент внут2")
                .checkElementByTitleContains("Поле История комментариев (все)", "коммент внут1")
                .switchToFirstTab();
    }

    @Test
    @Tag("checking_button_version_history_1725630")
    @DisplayName("1725630 - Проверка работоспособности кнопки \"История версий\"")
    @WorkItemIds({"1725630"})
    public void checking_button_version_history_1725630(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "Code", "stub1");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3250749.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab().waitBusyCondition()
                .goTo(fsspPage)
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Отправить на доработку")
                .selectValueFromDropDownList("Выпадающий список Причина доработки", "Ошибка МРК при вводе контактных данных клиента (адресов, телефонов)")
                .fillInput("Поле ввода Внутренний комментарий", "коммент внут")
                .fillInput("Поле ввода Комментарий для МРК", "Коммент для мрк")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .switchToOneTab()
                .waitBusyCondition();
        actionsClaimSteps.repeatSendSclRequestToStand("9", "data/json/claim_template_4100124.json");
        personalAccountPage
                .openMenuLinks("Личный кабинет")
                .doubleClickByText(claim).switchToNewTab().goTo(fsspPage).waitBusyCondition()
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Невозможно запросить ФССП")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .switchToOneTab()
                .waitBusyCondition()
                .openMenuLinks("Поиск")
                .searchClaimOnPage(claim)
                .doubleClickByText(claim).switchToNewTab().waitBusyCondition()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Результаты проверок")
                .goTo(resultCheck)
                .clickOnElement("Вкладка Ручные проверки")
                .goTo(manualChecks)
                .clickOnElement("Кнопка Верификация назначенные проверки")
                .clickOnCellFromTable("Таблица Верификация назначенные проверки", 1, 4, "Открыть стратегию")
                .switchToNewTab();
        actionsClaimSteps.executeQuery(VERIFICATION, "select date_finished from vrf_check_group_result vcs " +
                "where vcs.claim_id ='" + claim + "' and check_group_version = 1");
        OffsetDateTime dateTimeVersion1 = OffsetDateTime.parse(actionsClaimSteps.getVariables("date_finished"));

        actionsClaimSteps.executeQuery(VERIFICATION, "select date_finished from vrf_check_group_result vcs " +
                "where vcs.claim_id ='" + claim + "' and check_group_version = 2");
        OffsetDateTime dateTimeVersion2 = OffsetDateTime.parse(actionsClaimSteps.getVariables("date_finished"));

        fsspPage.checkDropDownListElements("Выпадающий список История версий",
                        List.of("Версия 1 " + dateTimeVersion1.format(DF) + " Автоматическое Тестирование1",
                                "Версия 2 " + dateTimeVersion2.format(DF) + " Автоматическое Тестирование1"))
                .switchToFirstTab();
    }

    @Test
    @Tag("checking_button_version_history_3250759")
    @DisplayName("3250759 - Проверка возможности перехода в любую версию из списка версий")
    @WorkItemIds({"3250759"})
    public void checking_button_version_history_3250759(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "Code", "stub1");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3250749.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab().waitBusyCondition()
                .goTo(fsspPage)
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Отправить на доработку")
                .selectValueFromDropDownList("Выпадающий список Причина доработки", "Ошибка МРК при вводе контактных данных клиента (адресов, телефонов)")
                .fillInput("Поле ввода Внутренний комментарий", "коммент внут")
                .fillInput("Поле ввода Комментарий для МРК", "Коммент для мрк")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .switchToOneTab()
                .waitBusyCondition();
        actionsClaimSteps.repeatSendSclRequestToStand("9", "data/json/claim_template_4100124.json");
        personalAccountPage
                .openMenuLinks("Личный кабинет")
                .doubleClickByText(claim).switchToNewTab().goTo(fsspPage).waitBusyCondition()
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Невозможно запросить ФССП")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .switchToOneTab()
                .waitBusyCondition()
                .openMenuLinks("Поиск")
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
                .selectValueFromDropDownList("Выпадающий список История версий", "Версия 1")
                .switchToNewTab()
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .selectValueFromDropDownList("Выпадающий список История версий", "Версия 2")
                .switchToNewTab()
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 2")
                .switchToFirstTab();
    }
}
