package ru.autotestframework.regress.working_with_application.postponement.verification;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import java.time.LocalDateTime;
import java.util.Map;

import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;
import static ru.autotestframework.utils.Constants.DF;

@Tag("regress")
@Tag("working_with_application")
@Tag("verification")
@Tag("postponement")
@ClassName("Работа с заявкой. Откладывание. Верификация. На каждый кейс отдельная заявка. Откладывание Верификация")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PostponementVerificationSeparateApplicationTest extends BaseTest {

    private String claim;
    private LocalDateTime nowTime;

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
        loginPage.openMenuLinks("Личный кабинет");
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("smoke")
    @Tag("save_info_1723058")
    @DisplayName("1723058 - Сохранение информации, введенной при проверках, при возврате из Отложенной вручную")
    @WorkItemIds({"1723058"})
    public void save_info_1723058(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "incomeMain", "NO",
                "kpMainOne", "null",
                "kpMainTwo", "null",
                "kpClient", "null");
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2516792.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Невозможно запросить ФССП")
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Отложить")
                .selectValueFromDropDownList("Выпадающий список Причина (Перевод заявки в отложенные)", "Недозвон Заемщику")
                .fillInput("Поле ввода комментарий (Перевод заявки в отложенные)", "Тест")
                .fillInput("Поле ввода Время для звонка участнику", getTimeNow().plusHours(1).format(DF))
                .clickOnElement("Кнопка Отложить заявку")
                .switchToOneTab()
                .goTo(personalAccountPage).waitBusyCondition()
                .clickOnElement("Кнопка раскрыть таблицу Отложено");
        String statusClaim = personalAccountPage.getTextFromTable("Таблица Отложено", 1, "Статус заявки");
        assertIsTrue(statusClaim.equals("Отложена (рассмотрение)"),
                "Значение в столбце Статус заявки должно быть равно Отложена (рассмотрение). Фактическое значение: " + statusClaim);

        personalAccountPage
                .doubleClickByText(claim).switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Взять в работу")
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - На рассмотрении")
                .goTo(fsspPage)
                .checkElementByTitleContains("Выпадающий список Результат проверки", "Невозможно запросить ФССП")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Исполнительное производство не найдено")
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Отложить")
                .selectValueFromDropDownList("Выпадающий список Причина (Перевод заявки в отложенные)", "Вопрос в ГО")
                .fillInput("Поле ввода комментарий (Перевод заявки в отложенные)", "Тест")
                .fillInput("Поле ввода Время возврата заявки", getTimeNow().plusHours(1).format(DF))
                .clickOnElement("Кнопка Отложить заявку")
                .switchToOneTab()
                .goTo(personalAccountPage).waitBusyCondition()
                .clickOnElement("Кнопка раскрыть таблицу Отложено")
                .doubleClickByText(claim).switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Взять в работу")
                .goTo(fsspPage)
                .checkElementByTitleContains("Выпадающий список Результат проверки", "Исполнительное производство не найдено")
                .goTo(cardRequestPage)
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - На рассмотрении")
                .closeCurrentTab();
    }

    @Test
    @Tag("check_save_info_1723059")
    @DisplayName("1723059 - Проверка сохранения информации, введенной при проверках, при возврате из Отложенной с Созаёмщиком")
    @WorkItemIds({"1723059"})
    public void check_save_info_1723059(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "codeBor1", "stub1",
                "codeBor2", "stub11",
                "codeCobor1", "stub1",
                "codeCobor2", "stub11");
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1316817.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Одобрить стратегию")
                .fillInput("Поле ввода Внутренний комментарий", "комАт")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку").switchToOneTab().waitBusyCondition()
                .goTo(loginPage)
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage
                .openMenuLinks("Личный кабинет")
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Верификация").doubleClickByText(claim).switchToNewTab()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон клиента/Версия 1")
                .clickOnElement("Иконка Степ 2")
                .clickOnElement("Кнопка Взять шаг в работу")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Нерезультативный прозвон")
                .selectValueFromDropDownList("Выпадающий список Нерезультативный прозвон", "Клиент не отвечает/недоступен")
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Отложить")
                .selectValueFromDropDownList("Выпадающий список Причина (Перевод заявки в отложенные)", "Недозвон Созаемщику")
                .fillInput("Поле ввода комментарий (Перевод заявки в отложенные)", "комАТ")
                .fillInput("Поле ввода Время для звонка участнику", getTimeNow().plusMinutes(6).format(DF))
                .clickOnElement("Кнопка Отложить заявку")
                .switchToOneTab()
                .goTo(personalAccountPage).waitBusyCondition()
                .clickOnElement("Кнопка раскрыть таблицу Отложено")
                .doubleClickByText(claim).switchToNewTab()
                .goTo(cardRequestPage)
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - Отложена (рассмотрение)")
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Нерезультативный прозвон")
                .checkElementByTitleEquals("Выпадающий список Нерезультативный прозвон", "Клиент не отвечает/недоступен")
                .clickOnElement("Иконка Степ 1")
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "")
                .waitText(120, "Уведомление").waitBusyCondition()
                .closeCurrentTab().switchToOnetab()
                .goTo(loginPage)
                .openMenuLinks("Личный кабинет")
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Верификация").doubleClickByText(claim).switchToNewTab()
                .goTo(cardRequestPage)
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - На рассмотрении")
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Нерезультативный прозвон")
                .checkElementByTitleEquals("Выпадающий список Нерезультативный прозвон", "Клиент не отвечает/недоступен")
                .clickOnElement("Иконка Степ 1")
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "");
    }

    private LocalDateTime getTimeNow() {
        nowTime = LocalDateTime.now();
        return nowTime;
    }
}
