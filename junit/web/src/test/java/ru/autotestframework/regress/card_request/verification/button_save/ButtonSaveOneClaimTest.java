package ru.autotestframework.regress.card_request.verification.button_save;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import java.util.List;

@Tag("regress")
@Tag("card_request")
@Tag("verification")
@Tag("button_save")
@Tag("button_save_one_claim")
@ClassName("Карточка заявки. Верификация. На заявке Тип №1 ФССП. Кнопка Сохранить")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ButtonSaveOneClaimTest extends BaseTest {

    private String claim;

    @BeforeAll
    public void createClaim(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3242304.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
    }

    @BeforeEach
    public void login() {
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi()
                    .openMenuLinks("Личный кабинет");
        }
        personalAccountPage.clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab();
    }

    @AfterEach
    public void closeTab() {
        callContactPersonSpoursePage.closeCurrentTab();
    }

    @AfterAll
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("smoke")
    @Tag("save_data_application_result_1725604")
    @DisplayName("1725604 - Сохранение данных при заполнении \"Результат по заявке\"")
    @WorkItemIds({"1725604"})
    public void save_data_application_result_1725604() {
        List<String> values = List.of(
                "Отказать",
                "Одобрить",
                "Отправить на доработку",
                "Одобрить стратегию");
        fsspPage
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1");
        for (String value : values) {
            fsspPage
                    .selectValueFromDropDownList("Выпадающий список Результат по заявке", value)
                    .checkElementByTitleEquals("Выпадающий список Результат по заявке", value);
            buttonSaveAndReopenClaim();
            fsspPage
                    .checkElementByTitleEquals("Выпадающий список Результат по заявке", value);
        }
        fsspPage
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "")
                .checkElementByTitleEquals("Выпадающий список Результат по заявке", "")
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Сохранить (header)");
    }

    @Test
    @Tag("smoke")
    @Tag("save_data_verification_result_1725603")
    @DisplayName("1725603 - Сохранение данных при заполнении \"Результат проверки\" стратегия ФССП")
    @WorkItemIds({"1725603"})
    public void save_data_verification_result_1725603() {
        fsspPage
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Найдено исполнительное производство")
                .clickOnElement("Чек-бокс Закрытые ИП по статье 47 (банкротство)");
        buttonSaveAndReopenClaim();
        fsspPage
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Найдено исполнительное производство")
                .assertElementByTitleSelected("Чек-бокс Закрытые ИП по статье 47 (банкротство)", "выбран")

                .selectValueFromDropDownList("Выпадающий список Результат проверки", "");
        buttonSaveAndReopenClaim();
        fsspPage
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "");
    }

    @Test
    @Tag("data_resave_1725601")
    @DisplayName("1725601 - Пересохранение данных с \"Результат проверки\" на \"Результат по заявке\"")
    @WorkItemIds({"1725601"})
    public void data_resave_1725601() {
        fsspPage
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Невозможно запросить ФССП")
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Невозможно запросить ФССП");
        buttonSaveAndReopenClaim();
        fsspPage
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Невозможно запросить ФССП")

                .selectValueFromDropDownList("Выпадающий список Результат проверки", "")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Отказать");
        buttonSaveAndReopenClaim();
        fsspPage
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "")
                .checkElementByTitleEquals("Выпадающий список Результат по заявке", "Отказать")

                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "")
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Сохранить (header)");
    }

    @Test
    @Tag("data_resave_1725597")
    @DisplayName("1725597 - Пересохранение данных с \"Результат по заявке\" на \"Результат проверки\"")
    @WorkItemIds({"1725597"})
    public void data_resave_1725597() {
        fsspPage
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Отказать")
                .checkElementByTitleEquals("Выпадающий список Результат по заявке", "Отказать");
        buttonSaveAndReopenClaim();
        fsspPage
                .checkElementByTitleEquals("Выпадающий список Результат по заявке", "Отказать")

                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Невозможно запросить ФССП");
        buttonSaveAndReopenClaim();
        fsspPage
                .checkElementByTitleEquals("Выпадающий список Результат по заявке", "")
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Невозможно запросить ФССП")

                .selectValueFromDropDownList("Выпадающий список Результат проверки", "")
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Сохранить (header)");
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