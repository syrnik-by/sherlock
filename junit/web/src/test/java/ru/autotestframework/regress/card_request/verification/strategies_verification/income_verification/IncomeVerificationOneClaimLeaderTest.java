package ru.autotestframework.regress.card_request.verification.strategies_verification.income_verification;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.autotestframework.pages.card_request.verification.IncomeVerificationPage;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.WorkItemIds;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import ru.psb.testit.annotations.DisplayName;
import java.util.List;

import static ru.autotestframework.steps.asserts.Asserts.assertIsEquals;
import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;

@Tag("regress")
@Tag("card_request")
@Tag("verification")
@Tag("strategies_verification")
@Tag("income_verification")
@Tag("income_verification_one_claim_leader")
@ClassName("Карточка заявки. Верификация. ЭФ стратегий верификации. Проверка дохода. На одной заявке (Специалист / Руководитель)")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IncomeVerificationOneClaimLeaderTest extends BaseTest {
    private String claim;

    @BeforeAll
    public void sendClaim(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3206921.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
    }

    @BeforeEach
    public void login() {
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi()
                    .openMenuLinks("Личный кабинет")
                    .goTo(personalAccountPage);
        }
        navigateToIncomeVerificationPage();
    }

    private IncomeVerificationPage navigateToIncomeVerificationPage() {
        return personalAccountPage.clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(incomeVerificationPage)
                .waitBusyCondition();
    }

    @AfterEach
    public void closeTab() {
        incomeVerificationPage.closeCurrentTab();
    }

    @AfterAll
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("checking_table_display_for_result_3206992")
    @DisplayName("3206992 - Проверка дохода. Проверка отображения таблицы для результата проверки \"Должность клиента не позволяет оценить его доход\". Специалист")
    @WorkItemIds({"3206992"})
    public void checking_table_display_for_result_3206992() {
        incomeVerificationPage
                .checkDropDownListElements("Выпадающий список Результат проверки",
                        List.of(" ",
                                "Должность клиента не позволяет оценить его доход",
                                "Оценка дохода проведена"))
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Должность клиента не позволяет оценить его доход")
                .assertElementByTitleVisibility("Кнопка Рассчитать", "отображается");
    }

    @Test
    @Tag("checking_table_display_for_result_3207009")
    @DisplayName("3207009 - Проверка дохода. Проверка отображения таблицы для результата проверки \"Оценка дохода проведена\". Специалист")
    @WorkItemIds({"3207009"})
    public void checking_table_display_for_result_3207009() {
        incomeVerificationPage.checkDropDownListElements("Выпадающий список Результат проверки",
                        List.of(" ",
                                "Должность клиента не позволяет оценить его доход",
                                "Оценка дохода проведена"))
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Оценка дохода проведена")
                .assertElementByTitleVisibility("Таблица Оценка дохода проведена", "отображается")
                .checkTableHeaders("Таблица Оценка дохода проведена", List.of("Параметр", "Значение"))
                .assertElementByTitleVisibility("Поле Средний доход по рынку для занимаемой должности", "отображается")
                .assertElementByTitleVisibility("Кнопка Рассчитать", "отображается");
    }

    @Test
    @Tag("checking_table_disappearing_and_data_not_saving_3207098")
    @DisplayName("3207098 - Проверка дохода. Проверка отображения таблицы для результата проверки \"Оценка дохода проведена\". Специалист")
    @WorkItemIds({"3207098"})
    public void checking_table_disappearing_and_data_not_saving_3207098() {
        incomeVerificationPage.checkDropDownListElements("Выпадающий список Результат проверки",
                        List.of("",
                                "Должность клиента не позволяет оценить его доход",
                                "Оценка дохода проведена",
                                "Работодатель в списке исключений"))
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Оценка дохода проведена")
                .assertElementByTitleVisibility("Таблица Оценка дохода проведена", "отображается")
                .assertElementByTitleVisibility("Кнопка Рассчитать", "отображается")
                .fillInput("Поле ввода Средний доход по рынку для занимаемой должности", "40000")
                .clickOnElement("Кнопка Рассчитать")
                .checkElementByTitleContains("Выпадающий список Результат проверки", "Доход не завышен")
                .assertElementByTitleActivity("Кнопка Рассчитать", "не активен")
                .assertElementByTitleActivity("Поле ввода Средний доход по рынку для занимаемой должности", "не активен")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "")
                .assertElementByTitleVisibility("Таблица Оценка дохода проведена", "не отображается")
                .assertElementByTitleVisibility("Кнопка Рассчитать", "не отображается")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Оценка дохода проведена")
                .assertElementByTitleVisibility("Таблица Оценка дохода проведена", "отображается")
                .assertElementByTitleVisibility("Кнопка Рассчитать", "отображается");
        assertIsTrue(incomeVerificationPage.getValueByElementTitle("Поле ввода Средний доход по рынку для занимаемой должности").isEmpty(),
                "Поле ввода Средний доход по рынку для занимаемой должности должно быть пустым");
        incomeVerificationPage.assertElementByTitleActivity("Поле ввода Средний доход по рынку для занимаемой должности", "активен")
                .assertElementByTitleVisibility("Кнопка Рассчитать", "активен");
    }

    @Test
    @Tag("checking_table_data_saving_3207834")
    @DisplayName("3207834 - Проверка дохода. Проверка сохранения данных таблицы для результата проверки \"Оценка дохода проведена\". Специалист")
    @WorkItemIds({"3207834"})
    public void checking_table_data_saving_3207834() {
        incomeVerificationPage.checkDropDownListElements("Выпадающий список Результат проверки",
                        List.of(" ",
                                "Должность клиента не позволяет оценить его доход",
                                "Оценка дохода проведена",
                                "Работодатель в списке исключений"))
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Оценка дохода проведена")
                .assertElementByTitleVisibility("Таблица Оценка дохода проведена", "отображается")
                .assertElementByTitleVisibility("Кнопка Рассчитать", "отображается")
                .fillInput("Поле ввода Средний доход по рынку для занимаемой должности", "40000")
                .clickOnElement("Кнопка Рассчитать")
                .checkElementByTitleContains("Выпадающий список Результат проверки", "Доход не завышен")
                .clickOnElement("Кнопка Сохранить")
                .clickOnElement("Кнопка Закрыть")
                .switchToOneTab()
                .waitBusyCondition()
                .goTo(personalAccountPage)
                .openMenuLinks("Личный кабинет");
        navigateToIncomeVerificationPage()
                .checkElementByTitleContains("Выпадающий список Результат проверки", "Доход не завышен");
        assertIsEquals("40000",
                incomeVerificationPage.getValueByElementTitle("Поле ввода Средний доход по рынку для занимаемой должности"),
                "Поле ввода Средний доход по рынку для занимаемой должности");
        incomeVerificationPage.assertElementByTitleActivity("Поле ввода Средний доход по рынку для занимаемой должности", "не активен")
                .assertElementByTitleActivity("Кнопка Рассчитать", "не активен")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", " ")
                .assertElementByTitleVisibility("Таблица Оценка дохода проведена", "не отображается")
                .assertElementByTitleVisibility("Кнопка Рассчитать", "не отображается")
                .clickOnElement("Кнопка Сохранить");
    }

    @Test
    @Tag("checking_edit_button_display_3207878")
    @DisplayName("3207878 - Проверка дохода. Проверка отображения кнопки редактирования в таблице для результата проверки \"Оценка дохода проведена\". Специалист")
    @WorkItemIds({"3207878"})
    public void checking_edit_button_display_3207878() {
        incomeVerificationPage.checkDropDownListElements("Выпадающий список Результат проверки",
                        List.of(" ",
                                "Должность клиента не позволяет оценить его доход",
                                "Оценка дохода проведена"))
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Оценка дохода проведена")
                .assertElementByTitleVisibility("Таблица Оценка дохода проведена", "отображается")
                .assertElementByTitleVisibility("Кнопка Рассчитать", "отображается")
                .fillInput("Поле ввода Средний доход по рынку для занимаемой должности", "40000")
                .clickOnElement("Кнопка Рассчитать")
                .checkElementByTitleContains("Выпадающий список Результат проверки", "Доход не завышен")
                .assertElementByTitleVisibility("Кнопка Редактировать", "отображается");
    }

    @Test
    @Tag("validation_number_field_3207883")
    @DisplayName("3207883 - Проверка дохода. Валидация числового поля для \"Средний доход по рынку для занимаемой должности\". Специалист")
    @WorkItemIds({"3207883"})
    public void validation_number_field_3207883() {
        incomeVerificationPage.checkDropDownListElements("Выпадающий список Результат проверки",
                        List.of(" ",
                                "Должность клиента не позволяет оценить его доход",
                                "Оценка дохода проведена",
                                "Работодатель в списке исключений"))
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Оценка дохода проведена")
                .assertElementByTitleVisibility("Таблица Оценка дохода проведена", "отображается")
                .assertElementByTitleVisibility("Кнопка Рассчитать", "отображается")
                .fillInput("Поле ввода Средний доход по рынку для занимаемой должности", "любой текст")
                .clickOnElement("Поле Средний доход по рынку для занимаемой должности");
        assertIsTrue(incomeVerificationPage.getValueByElementTitle("Поле ввода Средний доход по рынку для занимаемой должности").isEmpty(),
                "Поле ввода Средний доход по рынку для занимаемой должности должно быть пустым");

        incomeVerificationPage.fillInput("Поле ввода Средний доход по рынку для занимаемой должности", "!")
                .clickOnElement("Поле Средний доход по рынку для занимаемой должности");
        assertIsTrue(incomeVerificationPage.getValueByElementTitle("Поле ввода Средний доход по рынку для занимаемой должности").isEmpty(),
                "Поле ввода Средний доход по рынку для занимаемой должности должно быть пустым");

        incomeVerificationPage.fillInput("Поле ввода Средний доход по рынку для занимаемой должности", "40000");
        assertIsEquals("40000",
                incomeVerificationPage.getValueByElementTitle("Поле ввода Средний доход по рынку для занимаемой должности"),
                "Поле ввода Средний доход по рынку для занимаемой должности");
    }

    @Test
    @Tag("checking_modal_window_click_button_next_3208037")
    @DisplayName("3208037 - Проверка дохода. Проверка модального окна по нажатию на кнопку \"Далее\" для результата проверки \"Должность клиента не позволяет оценить доход\". Специалист")
    @WorkItemIds({"3208037"})
    public void checking_modal_window_click_button_next_3208037() {
        incomeVerificationPage.checkDropDownListElements("Выпадающий список Результат проверки",
                        List.of(" ",
                                "Должность клиента не позволяет оценить его доход",
                                "Оценка дохода проведена"))
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Должность клиента не позволяет оценить его доход")
                .assertElementByTitleVisibility("Кнопка Рассчитать", "отображается")
                .clickOnElement("Кнопка Далее")
                .checkElementByTitleContains("Модальное окно Предупреждение", "Пожалуйста, нажмите на кнопку «Рассчитать»")
                .clickOnElement("Кнопка ОК");
    }

    @Test
    @Tag("checking_modal_window_click_button_next_3209201")
    @DisplayName("3209201 - Проверка дохода. Проверка модального окна по нажатию на кнопку \"Рассчитать\" для результата проверки \"Оценка дохода приведена\". Заполненного поля нет. Специалист")
    @WorkItemIds({"3209201"})
    public void checking_modal_window_click_button_next_3209201() {
        incomeVerificationPage.checkDropDownListElements("Выпадающий список Результат проверки",
                        List.of(" ",
                                "Должность клиента не позволяет оценить его доход",
                                "Оценка дохода проведена"))
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Оценка дохода проведена")
                .assertElementByTitleVisibility("Таблица Оценка дохода проведена", "отображается")
                .assertElementByTitleVisibility("Кнопка Рассчитать", "отображается")
                .clickOnElement("Кнопка Рассчитать")
                .checkElementByTitleContains("Модальное окно Предупреждение", "Пожалуйста, заполните поле: \"Средний доход по рынку для занимаемой должности\"")
                .clickOnElement("Кнопка ОК");
    }

    @Test
    @Tag("checking_modal_window_click_button_next_3208052")
    @DisplayName("3208052 - Проверка дохода. Проверка модального окна по нажатию на кнопку \"Далее\" для результата проверки \"Оценка дохода приведена\". Заполненное поле есть. Специалист")
    @WorkItemIds({"3208052"})
    public void checking_modal_window_click_button_next_3208052() {
        incomeVerificationPage.checkDropDownListElements("Выпадающий список Результат проверки",
                        List.of(" ",
                                "Должность клиента не позволяет оценить его доход",
                                "Оценка дохода проведена"))
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Оценка дохода проведена")
                .assertElementByTitleVisibility("Таблица Оценка дохода проведена", "отображается")
                .assertElementByTitleVisibility("Кнопка Рассчитать", "отображается")
                .fillInput("Поле ввода Средний доход по рынку для занимаемой должности", "40000")
                .clickOnElement("Кнопка Далее")
                .checkElementByTitleContains("Модальное окно Предупреждение", "Пожалуйста, нажмите на кнопку «Рассчитать»")
                .clickOnElement("Кнопка ОК");
    }

    @Test
    @Tag("checking_table_unblock_for_edit_3208096")
    @DisplayName("3208096 - Проверка дохода. Проверка разблокировки таблицы для редактирования для результата проверки \"Оценка дохода проведена\". Специалист")
    @WorkItemIds({"3208096"})
    public void checking_table_unblock_for_edit_3208096() {
        incomeVerificationPage.checkDropDownListElements("Выпадающий список Результат проверки",
                        List.of(" ",
                                "Должность клиента не позволяет оценить его доход",
                                "Оценка дохода проведена"))
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Оценка дохода проведена")
                .assertElementByTitleVisibility("Таблица Оценка дохода проведена", "отображается")
                .assertElementByTitleVisibility("Кнопка Рассчитать", "отображается")
                .fillInput("Поле ввода Средний доход по рынку для занимаемой должности", "40000")
                .clickOnElement("Кнопка Рассчитать")
                .checkElementByTitleContains("Выпадающий список Результат проверки", "Доход не завышен")
                .clickOnElement("Кнопка Редактировать")
                .checkElementByTitleContains("Выпадающий список Результат проверки", "Оценка дохода проведена")
                .assertElementByTitleActivity("Кнопка Рассчитать", "активен")
                .fillInput("Поле ввода Средний доход по рынку для занимаемой должности", "5");
        assertIsEquals("400005",
                incomeVerificationPage.getValueByElementTitle("Поле ввода Средний доход по рынку для занимаемой должности"),
                "Поле ввода Средний доход по рынку для занимаемой должности");
    }
}