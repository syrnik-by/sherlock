package ru.autotestframework.regress.card_request.verification.strategies_verification;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.*;
import ru.psb.testit.annotations.DisplayName;

import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;

@Tag("regress")
@Tag("card_request")
@Tag("verification")
@Tag("ef_verification_opm")
@ClassName("Карточка заявки. Верификация. ЭФ стратегий верификации. ОПМ")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EfVerificationOpmTest extends BaseTest {

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
        loginPage.openMenuLinks("Личный кабинет");
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("smoke")
    @Tag("check_strategy_2653942")
    @DisplayName("2653942 - ЭФ проверки ОПМ. Проверка отображения значения поля \"Стратегия\". Отправка на ОПМ через результат по заявке. ФССП")
    @WorkItemIds({"2653942"})
    public void check_strategy_2653942(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2653942.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Отправить на Antifraud")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Внутреннее мошенничество")
                .fillInput("Поле ввода Комментарий", "123")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab().waitBusyCondition()
                .goTo(loginPage)
                .openMenuLinks("Поиск")
                .goTo(searchPage)
                .searchClaimOnPage(claim);
        String strategy = searchPage.getTextFromTable("Таблица результаты поиска", 1, "Стратегия");
        assertIsTrue(strategy.equals("Проверка сотрудниками ОПМ"),
                "Значение в столбце Стратегия заявки должно быть равно Проверка сотрудниками ОПМ. Фактическое значение: " + strategy);
        loginPage
                .doubleClickByText(claim).switchToNewTab();
        String strategyOpm = verificationOpmEmployeesPage.getTextFromTable("Таблица Сообщений ОПМ", 1, "Стратегия");
        assertIsTrue(strategyOpm.equals("ФССП"),
                "Значение в столбце Стратегия заявки должно быть равно ФССП. Фактическое значение: " + strategyOpm);
        verificationOpmEmployeesPage.closeCurrentTab();
    }

    @Test
    @Tag("smoke")
    @Tag("check_verification_object_2653919")
    @DisplayName("2653919 - ЭФ проверки ОПМ. Проверка отображения значения поля \"Объект проверки\". Отправка на ОПМ через результат по заявке. Заемщик")
    @WorkItemIds({"2653919"})
    public void check_verification_object_2653919(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2653919.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Отправить на Antifraud")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Внешнее мошенничество")
                .fillInput("Поле ввода Комментарий", "123")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab().waitBusyCondition()
                .goTo(loginPage)
                .openMenuLinks("Поиск")
                .goTo(searchPage)
                .searchClaimOnPage(claim);
        String strategy = searchPage.getTextFromTable("Таблица результаты поиска", 1, "Стратегия");
        assertIsTrue(strategy.equals("Проверка сотрудниками ОПМ"),
                "Значение в столбце Стратегия заявки должно быть равно Проверка сотрудниками ОПМ. Фактическое значение: " + strategy);
        loginPage
                .doubleClickByText(claim).switchToNewTab();
        String borrower = verificationOpmEmployeesPage.getTextFromTable("Таблица Сообщений ОПМ", 1, "Объект проверки");
        assertIsTrue(borrower.equals("Заемщик - Романоввв Юрий Иванович 05.12.1990"),
                "Значение в столбце Стратегия заявки должно быть равно Заемщик - Романоввв Юрий Иванович 05.12.1990. Фактическое значение: " + borrower);
    }

    @Test
    @Tag("smoke")
    @Tag("check_form_2653946")
    @DisplayName("2653946 - ЭФ проверки ОПМ. Проверка формы \"Дополнительные проверки\"")
    @WorkItemIds({"2653946"})
    public void check_form_2653946(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2653946.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(customerCallPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон клиента/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Отправить на Antifraud")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Внутреннее мошенничество")
                .fillInput("Поле ввода Комментарий", "123")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab().waitBusyCondition()
                .goTo(loginPage)
                .openMenuLinks("Личный кабинет")
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Проверка сотрудниками ОПМ")
                .doubleClickByText(claim).switchToNewTab()
                .goTo(verificationOpmEmployeesPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка сотрудниками ОПМ/Версия 1")
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Взять в работу")
                .goTo(verificationOpmEmployeesPage)
                .assertElementByTitleVisibility("Список Дополнительных проверок", "не отображается")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Назначить дополнительную проверку с возвратом на этап ОПМ")
                .assertElementByTitleVisibility("Список Дополнительных проверок", "отображается")
                .closeCurrentTab();
    }
}