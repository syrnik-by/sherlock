package ru.autotestframework.regress.card_request.verification.button_save;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import java.util.Map;

import static ru.autotestframework.steps.asserts.Asserts.assertIsEquals;

@Tag("regress")
@Tag("card_request")
@Tag("verification")
@Tag("button_save")
@Tag("button_save_separate_claim")
@ClassName("На каждый кейс отдельная заявка. Кнопка Сохранить")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ButtonSaveSeparateClaimTest extends BaseTest {

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
    @Tag("checking_active_button_1725600")
    @DisplayName("1725600 - Проверка активности кнопки \"Сохранить\" в режиме просмотра и режиме обработки заявки")
    @WorkItemIds({"1725600"})
    public void checking_active_button_1725600(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "Code", "stub1");

        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3242320.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Исполнительное производство не найдено")
                .goTo(cardRequestPage)
                .assertElementByTitleActivity("Кнопка Сохранить (header)", "активен")
                .closeCurrentTab();

        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3242320.json", 1, testInfo, claimParams).get(0);
        loginPage
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim)
                .doubleClickByText(claim).switchToNewTab()
                .goTo(cardRequestPage)
                .assertElementByTitleActivity("Кнопка Сохранить (header)", "не активен");

        loginPage.closeCurrentTab().resetFilters().openMenuLinks("Личный кабинет");
    }

    @Test
    @Tag("save_data_1725591")
    @DisplayName("1725591 - Сохранение данных при заполнении \"Результат проверки\" стратегия Проверка дохода")
    @WorkItemIds({"1725591"})
    public void save_data_1725591(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "Code", "stub5");

        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3242320.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(incomeVerificationPage)
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Оценка дохода проведена");
        buttonSaveAndReopenClaim();
        incomeVerificationPage
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Оценка дохода проведена")
                .fillInput("Поле ввода Средний доход по рынку для занимаемой должности", "50000");
        buttonSaveAndReopenClaim();
        assertIsEquals("50000", incomeVerificationPage.getValueByElementTitle("Поле ввода Средний доход по рынку для занимаемой должности"), "Поле ввода Средний доход по рынку для занимаемой должности");
        incomeVerificationPage.closeCurrentTab();
    }

    @Test
    @Tag("save_data_1725593")
    @DisplayName("1725593 - Сохранение данных при заполнении \"Результат проверки\" стратегия Открытые источники")
    @WorkItemIds({"1725593"})
    public void save_data_1725593(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "Code", "stub7");

        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3242320.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(checkingOpenSourcesPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка открытых источников/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Проверка сайта", "")
                .selectValueFromDropDownList("Выпадающий список Привязка телефона из анкеты", "")
                .selectValueFromDropDownList("Выпадающий список Бесконтактное подтверждение трудоустройства", "")
                .selectValueFromDropDownList("Выпадающий список Брокерские услуги", "");
        buttonSaveAndReopenClaim();
        checkingOpenSourcesPage
                .checkElementByTitleEquals("Выпадающий список Проверка сайта", "")
                .checkElementByTitleEquals("Выпадающий список Привязка телефона из анкеты", "")
                .checkElementByTitleEquals("Выпадающий список Бесконтактное подтверждение трудоустройства", "")
                .checkElementByTitleEquals("Выпадающий список Брокерские услуги", "")

                .selectValueFromDropDownList("Выпадающий список Проверка сайта", "Выявлен негатив")
                .checkElementByTitleEquals("Выпадающий список Проверка сайта", "Выявлен негатив");
        buttonSaveAndReopenClaim();
        checkingOpenSourcesPage
                .checkElementByTitleEquals("Выпадающий список Проверка сайта", "Выявлен негатив")

                .selectValueFromDropDownList("Выпадающий список C выбором вида негатива", "Сайт дублер");
        buttonSaveAndReopenClaim();
        checkingOpenSourcesPage
                .checkElementByTitleEquals("Выпадающий список Проверка сайта", "Выявлен негатив")
                .checkElementByTitleEquals("Выпадающий список C выбором вида негатива", "Сайт дублер")

                .selectValueFromDropDownList("Выпадающий список Проверка сайта", "Сайт найден и негатив не выявлен");
        buttonSaveAndReopenClaim();
        checkingOpenSourcesPage
                .checkElementByTitleEquals("Выпадающий список Проверка сайта", "Сайт найден и негатив не выявлен")

                .fillInput("Поле ввода Источник подтверждения", "сайт123");
        buttonSaveAndReopenClaim();
        checkingOpenSourcesPage
                .checkElementByTitleEquals("Выпадающий список Проверка сайта", "Сайт найден и негатив не выявлен");
        assertIsEquals("сайт123", checkingOpenSourcesPage.getValueByElementTitle("Поле ввода Источник подтверждения"), "Поле ввода Источник подтверждения");
        checkingOpenSourcesPage.selectValueFromDropDownList("Выпадающий список Проверка сайта", "")
                .closeCurrentTab();
    }

    @Test
    @Tag("save_data_1725602")
    @DisplayName("1725602 - Сохранение данных при заполнении \"Результат проверки\" стратегия Прозвон клиента")
    @WorkItemIds({"1725602"})
    public void save_data_1725602(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "Code", "stub11");

        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3242320.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(customerCallPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон клиента/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Нерезультативный прозвон")
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Нерезультативный прозвон");
        buttonSaveAndReopenClaim();
        customerCallPage
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Нерезультативный прозвон")

                .selectValueFromDropDownList("Выпадающий список Результат проверки", "");
        buttonSaveAndReopenClaim();
        customerCallPage
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "")

                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Нерезультативный прозвон")
                .selectValueFromDropDownList("Выпадающий список Нерезультативный прозвон", "Клиент не отвечает/недоступен");
        buttonSaveAndReopenClaim();
        customerCallPage
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Нерезультативный прозвон")
                .checkElementByTitleEquals("Выпадающий список Нерезультативный прозвон", "Клиент не отвечает/недоступен")

                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон");
        buttonSaveAndReopenClaim();
        customerCallPage
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Результативный прозвон")

                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Негатив не выявлен");
        buttonSaveAndReopenClaim();
        customerCallPage
                .checkElementByTitleEquals("Выпадающий список Результативный прозвон", "Негатив не выявлен")
                .checkElementByTitleEquals("Выпадающий список Негатив не выявлен", "")

                .selectValueFromDropDownList("Выпадающий список Негатив не выявлен", "Негатив отсутствует");
        buttonSaveAndReopenClaim();
        customerCallPage
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Результативный прозвон")
                .checkElementByTitleEquals("Выпадающий список Результативный прозвон", "Негатив не выявлен")
                .checkElementByTitleEquals("Выпадающий список Негатив не выявлен", "Негатив отсутствует")

                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Выявлен негатив");
        buttonSaveAndReopenClaim();
        customerCallPage
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Результативный прозвон")
                .checkElementByTitleEquals("Выпадающий список Результативный прозвон", "Выявлен негатив")

                .selectValueFromDropDownList("Выпадающий список Выявлен негатив", "Кредит для третьего лица");
        buttonSaveAndReopenClaim();
        customerCallPage
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Результативный прозвон")
                .checkElementByTitleEquals("Выпадающий список Результативный прозвон", "Выявлен негатив")
                .checkElementByTitleEquals("Выпадающий список Выявлен негатив", "Кредит для третьего лица")
                .closeCurrentTab();
    }

    @Test
    @Tag("save_data_1725594")
    @DisplayName("1725594 - Сохранение данных при заполнении \"Результат проверки\" стратегия Прозвон контактного лица/супруга(и)")
    @WorkItemIds({"1725594"})
    public void save_data_1725594(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "Code", "stub12");

        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3242320.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(customerCallPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон контактного лица/супруга (-и)/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Нерезультативный прозвон")
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Нерезультативный прозвон");
        buttonSaveAndReopenClaim();
        customerCallPage
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Нерезультативный прозвон")

                .selectValueFromDropDownList("Выпадающий список Результат проверки", "");
        buttonSaveAndReopenClaim();
        customerCallPage
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "")

                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Нерезультативный прозвон")
                .selectValueFromDropDownList("Выпадающий список Нерезультативный прозвон", "Контактное лицо/супруг (-а) не отвечает/недоступен");
        buttonSaveAndReopenClaim();
        customerCallPage
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Нерезультативный прозвон")
                .checkElementByTitleEquals("Выпадающий список Нерезультативный прозвон", "Контактное лицо/супруг (-а) не отвечает/недоступен")

                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон");
        buttonSaveAndReopenClaim();
        customerCallPage
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Результативный прозвон")

                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Негатив отсутствует");
        buttonSaveAndReopenClaim();
        customerCallPage
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Результативный прозвон")
                .checkElementByTitleEquals("Выпадающий список Результативный прозвон", "Негатив отсутствует")

                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Выявлен негатив");
        buttonSaveAndReopenClaim();
        customerCallPage
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Результативный прозвон")
                .checkElementByTitleEquals("Выпадающий список Результативный прозвон", "Выявлен негатив")

                .selectValueFromDropDownList("Выпадающий список Выявлен негатив", "Кредит на бизнес");
        buttonSaveAndReopenClaim();
        customerCallPage
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Результативный прозвон")
                .checkElementByTitleEquals("Выпадающий список Результативный прозвон", "Выявлен негатив")
                .checkElementByTitleEquals("Выпадающий список Выявлен негатив", "Кредит на бизнес")
                .closeCurrentTab();
    }

    @Test
    @Tag("save_data_1725592")
    @DisplayName("1725592 - Сохранение данных при заполнении \"Результат проверки\" стратегия Прозвон работодателя - любой телефон (Обязательный)")
    @WorkItemIds({"1725592"})
    public void save_data_1725592(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "Code", "stub15");

        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3242320.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(customerCallPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон работодателя - любой телефон (Обязательный)/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Нерезультативный прозвон")
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Нерезультативный прозвон");
        buttonSaveAndReopenClaim();
        customerCallPage
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Нерезультативный прозвон")

                .selectValueFromDropDownList("Выпадающий список Результат проверки", "");
        buttonSaveAndReopenClaim();
        customerCallPage
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "")

                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Нерезультативный прозвон")
                .selectValueFromDropDownList("Выпадающий список Нерезультативный прозвон", "Представитель работодателя просит перезвонить");
        buttonSaveAndReopenClaim();
        customerCallPage
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Нерезультативный прозвон")
                .checkElementByTitleEquals("Выпадающий список Нерезультативный прозвон", "Представитель работодателя просит перезвонить")

                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон");
        buttonSaveAndReopenClaim();
        customerCallPage
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Результативный прозвон")

                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Негатив не выявлен, все ответы получены");
        buttonSaveAndReopenClaim();
        customerCallPage
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Результативный прозвон")
                .checkElementByTitleEquals("Выпадающий список Результативный прозвон", "Негатив не выявлен, все ответы получены")

                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Выявлен негатив");
        buttonSaveAndReopenClaim();
        customerCallPage
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Результативный прозвон")
                .checkElementByTitleEquals("Выпадающий список Результативный прозвон", "Выявлен негатив")

                .selectValueFromDropDownList("Выпадающий список Выявлен негатив", "Задержки з/п");
        buttonSaveAndReopenClaim();
        customerCallPage
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Результативный прозвон")
                .checkElementByTitleEquals("Выпадающий список Результативный прозвон", "Выявлен негатив")
                .checkElementByTitleEquals("Выпадающий список Выявлен негатив", "Задержки з/п")

                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Предоставлен документ, закрывающий риски");
        buttonSaveAndReopenClaim();
        callingEmployerConfirmedPhonePage
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Предоставлен документ, закрывающий риски")

                .selectValueFromDropDownList("Выпадающий список Предоставлен документ, закрывающий риски", "Электронная ТК");
        buttonSaveAndReopenClaim();
        callingEmployerConfirmedPhonePage
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Предоставлен документ, закрывающий риски")
                .checkElementByTitleEquals("Выпадающий список Предоставлен документ, закрывающий риски", "Электронная ТК")
                .closeCurrentTab();
    }

    @Test
    @Tag("save_data_1725595")
    @DisplayName("1725595 - Сохранение данных при заполнении \"Результат проверки\" стратегия Прозвон работодателя - подтвержденный телефон")
    @WorkItemIds({"1725595"})
    public void save_data_1725595(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "Code", "stub14");

        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3242320.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(callingEmployerConfirmedPhonePage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон работодателя - подтвержденный телефон/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Бесконтактное подтверждение")
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Бесконтактное подтверждение");
        buttonSaveAndReopenClaim();
        callingEmployerConfirmedPhonePage
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Бесконтактное подтверждение")

                .selectValueFromDropDownList("Выпадающий список Бесконтактное подтверждение", "Сторонние сайты");
        buttonSaveAndReopenClaim();
        callingEmployerConfirmedPhonePage
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Бесконтактное подтверждение")
                .checkElementByTitleEquals("Выпадающий список Бесконтактное подтверждение", "Сторонние сайты")

                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Косвенное подтверждение занятости");
        buttonSaveAndReopenClaim();
        callingEmployerConfirmedPhonePage
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Косвенное подтверждение занятости")

                .selectValueFromDropDownList("Выпадающий список Косвенное подтверждение занятости", "Пункт 2 РА");
        buttonSaveAndReopenClaim();
        callingEmployerConfirmedPhonePage
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Косвенное подтверждение занятости")
                .checkElementByTitleEquals("Выпадающий список Косвенное подтверждение занятости", "Пункт 2 РА")
                .closeCurrentTab();
    }

    void buttonSaveAndReopenClaim() {
        cardRequestPage
                .clickOnElement("Кнопка Сохранить (header)")
                .closeCurrentTab()
                .goTo(loginPage)
                .openMenuLinks("Личный кабинет")
                .doubleClickByText(claim).switchToNewTab().waitBusyCondition();
    }
}