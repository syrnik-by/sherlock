package ru.autotestframework.regress.working_with_application.return_to_queue.separate_application;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;

@Tag("regress")
@Tag("working_with_application")
@Tag("return_to_queue")
@ClassName("Работа с заявкой. Вернуть в очередь. На каждый кейс отдельная заявка. Вернуть в очередь")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DisplayingButtonReturnQueue extends BaseTest {

    String claim;

    @BeforeEach
    public void login() {
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi();
        }
        loginPage.openMenuLinks("Личный кабинет")
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Верификация");
    }

    @AfterEach
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("displaying_button_2653286")
    @DisplayName("2653286 - Отображение кнопки \"Вернуть в очередь\" на стратегии \"Вопрос в ГО\"")
    @WorkItemIds({"2653286"})
    public void displaying_button_2653286(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3251572.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        assertIsTrue(queuesPage.getTextFromTable("Таблица Очереди", 1, "Статус заявки").
                        equals("На рассмотрении"),
                "Значение в столбце Статус заявки должно быть На рассмотрении");
        personalAccountPage
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Методологический")
                .fillInput("Поле ввода Комментарий", "Комментарий АТ")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .switchToOneTab()
                .elementByTitleVisibility("Раздел Вопрос в ГО", "отображается")
                .clickOnElement("Раздел Вопрос в ГО")
                .doubleClickByText(claim)
                .goTo(questionInGoPage)
                .switchToNewTab()
                .elementByTitleContains("Поле Наименование стратегии", "Вопрос в ГО")
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Взять в работу")
                .waitBusyCondition()
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - На рассмотрении")
                .assertElementByTitleVisibility("Кнопка Вернуть в очередь", "отображается")
                .assertElementByTitleActivity("Кнопка Вернуть в очередь", "активен")
                .closeCurrentTab();
    }

    @Test
    @Tag("displaying_button_2653289")
    @DisplayName("2653289 - Отображение кнопки \"Вернуть в очередь\" на стратегии \"Проверка сотрудниками ОПМ\"")
    @WorkItemIds({"2653289"})
    public void displaying_button_2653289(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3251572.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        assertIsTrue(queuesPage.getTextFromTable("Таблица Очереди", 1, "Статус заявки").
                        equals("На рассмотрении"),
                "Значение в столбце Статус заявки должно быть На рассмотрении");
        personalAccountPage
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Отправить на Antifraud")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Внутреннее мошенничество")
                .fillInput("Поле ввода Комментарий", "Комментарий АТ")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .switchToOneTab()
                .clickOnElement("Раздел Проверка сотрудниками ОПМ");
        actionsClaimSteps.appointResponsiblePerson(claim);
        assertIsTrue(queuesPage.getTextFromTable("Таблица Очереди", 1, "Статус заявки").
                        equals("На рассмотрении"),
                "Значение в столбце Статус заявки должно быть На рассмотрении");
        personalAccountPage
                .doubleClickByText(claim).switchToNewTab()
                .goTo(verificationOpmEmployeesPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка сотрудниками ОПМ/Версия 1")
                .goTo(cardRequestPage)
                .assertElementByTitleVisibility("Кнопка Вернуть в очередь", "отображается")
                .assertElementByTitleActivity("Кнопка Вернуть в очередь", "активен")
                .closeCurrentTab();
    }
}