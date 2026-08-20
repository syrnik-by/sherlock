package ru.autotestframework.regress.card_request.verification.strategies_verification.income_verification;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import java.util.List;

import static ru.autotestframework.steps.actions.BaseActions.sleep;
import static ru.autotestframework.steps.asserts.Asserts.*;
import static ru.autotestframework.utils.Constants.BODY;

@Tag("regress")
@Tag("card_request")
@Tag("verification")
@Tag("strategies_verification")
@Tag("income_verification")
@Tag("income_verification_one_claim")
@ClassName("Карточка заявки. Верификация. ЭФ стратегий верификации. Проверка дохода. На одной заявке (Учредитель)")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IncomeVerificationOneClaimFounderTest extends BaseTest {

    private String claim;

    @BeforeEach
    public void login(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3208269.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi()
                    .openMenuLinks("Личный кабинет");
        }
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
    @Tag("smoke")
    @Tag("checking_data_save_3208489")
    @DisplayName("3208489 - Проверка дохода. Проверка сохранения данных таблицы. Учредитель")
    @WorkItemIds({"3208489"})
    public void checking_data_save_3208489() {
        personalAccountPage.clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка дохода/Версия 1")
                .fillInput("Поле ввода Выручка по официальным данным за предыдущий год (руб.)", "3000000")
                .fillInput("Поле ввода Доля в бизнесе (%)", "50")
                .clickOnElement("Переключатель Нет (Деятельность компании подразумевает большую закупочную часть или траты вне персонала)")
                .clickOnElement("Кнопка Рассчитать")
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Доход не завышен");
        String expectedValue = incomeVerificationPage.getValueByElementTitle("Поле ввода Рассчитанный доход от ведения бизнеса (руб.)");

        incomeVerificationPage
                .clickOnElement("Кнопка Далее")
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Вернуть в очередь")
                .checkElementByTitleContains("Модальное окно предупреждения", "Заявка будет возвращена в очередь!\n" +
                        "Выполненные изменения будут утеряны. Да\\Нет?")
                .clickOnElement("Кнопка Да модального окна").switchToOneTab()
                .goTo(loginPage)
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim)
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка дохода/Версия 1");
        assertIsEquals("3000000", incomeVerificationPage.getValueByElementTitle("Поле ввода Выручка по официальным данным за предыдущий год (руб.)"), "Поле ввода Выручка по официальным данным за предыдущий год (руб.)");
        assertIsEquals("50", incomeVerificationPage.getValueByElementTitle("Поле ввода Доля в бизнесе (%)"), "Поле ввода Доля в бизнесе (%)");

        String actualValue = incomeVerificationPage.getValueByElementTitle("Поле ввода Рассчитанный доход от ведения бизнеса (руб.)");
        assertIsTrue(expectedValue.equals(actualValue),
                "Значенние Поле ввода Рассчитанный доход от ведения бизнеса (руб.) " + actualValue + " должно быть равно " + expectedValue + " . Фактическое значение = " + actualValue);

//Постусловие
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage
                .openMenuLinks("Личный кабинет")
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка дохода/Версия 1")
                .clickOnElement("Кнопка Изменить результат")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Оценка дохода проведена")
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Оценка дохода проведена")
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Сохранить (header)")
                .switchToOneTab();
    }

    @Test
    @Tag("checking_vision_table_3208277")
    @DisplayName("3208277 - Проверка дохода. Проверка отображения таблицы для результата проверки \"Оценка дохода проведена\". Учредитель")
    @WorkItemIds({"3208277"})
    public void checking_vision_table_3208277() {
        List<String> checkList = List.of(
                "Поле Деятельность компании подразумевает большую закупочную часть или траты вне персонала",
                "Поле ввода Выручка по официальным данным за предыдущий год (руб.)",
                "Поле ввода Доля в бизнесе (%)",
                "Поле ввода Рассчитанный доход от ведения бизнеса (руб.)");

        personalAccountPage.clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка дохода/Версия 1")
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Оценка дохода проведена")
                .checkDropDownListElements("Выпадающий список Результат проверки",
                        List.of("",
                                "Оценка дохода проведена"))
                .checkTableHeaders("Таблица Оценка дохода проведена", List.of("Параметр", "Значение"));
        for (String checkLists : checkList) {
            incomeVerificationPage
                    .assertElementByTitleVisibility(checkLists, "отображается");
        }
        assertIsEquals("0", incomeVerificationPage.getValueByElementTitle("Поле ввода Выручка по официальным данным за предыдущий год (руб.)"),
                "Поле ввода Выручка по официальным данным за предыдущий год (руб.)");
        assertIsEquals("0", incomeVerificationPage.getValueByElementTitle("Поле ввода Доля в бизнесе (%)"),
                "Поле ввода Доля в бизнесе (%)");
        assertIsEquals("", incomeVerificationPage.getValueByElementTitle("Поле ввода Рассчитанный доход от ведения бизнеса (руб.)"),
                "Поле ввода Рассчитанный доход от ведения бизнеса (руб.)");
        incomeVerificationPage.assertElementByTitleVisibility("Кнопка Рассчитать", "отображается");
    }

    @Test
    @Tag("checking_vision_hints_3208389")
    @DisplayName("3208389 - Проверка дохода. Проверка отображения подсказки и кнопки редактирования в таблице для результата проверки. Учредитель")
    @WorkItemIds({"3208389"})
    public void checking_vision_hints_3208389() {
        List<String> checkList = List.of(
                "Поле Деятельность компании подразумевает большую закупочную часть или траты вне персонала",
                "Поле ввода Выручка по официальным данным за предыдущий год (руб.)",
                "Поле ввода Доля в бизнесе (%)",
                "Поле ввода Рассчитанный доход от ведения бизнеса (руб.)");

        personalAccountPage.clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка дохода/Версия 1")
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Оценка дохода проведена")
                .checkDropDownListElements("Выпадающий список Результат проверки",
                        List.of("",
                                "Оценка дохода проведена"))
                .checkTableHeaders("Таблица Оценка дохода проведена", List.of("Параметр", "Значение"));
        for (String checkLists : checkList) {
            incomeVerificationPage
                    .assertElementByTitleVisibility(checkLists, "отображается");
        }
        incomeVerificationPage
                .assertElementByTitleVisibility("Иконка подсказка", "отображается")
                .goTo(l0CheckingDocumentsPage)
                .checkToolTipTextElement("Иконка подсказка", "Примеры сфер деятельности, подразумевающих большую закупочную часть или траты вне персонала - строительство, торговля; не подразумевающих – бухгалтерские, юридические услуги.")
                .clickOnElement("Иконка подсказка")
                .goTo(incomeVerificationPage)
                .clickOnElement("Переключатель Да (Деятельность компании подразумевает большую закупочную часть или траты вне персонала)")
                .fillInput("Поле ввода Выручка по официальным данным за предыдущий год (руб.)", "10000000")
                .fillInput("Поле ввода Доля в бизнесе (%)", "100")
                .clickOnElement("Кнопка Рассчитать")
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Доход не завышен")
                .assertElementByTitleActivity("Кнопка Рассчитать", "не активен")
                .assertElementByTitleVisibility("Кнопка Редактирования", "отображается");
        String actualValue = incomeVerificationPage.getValueByElementTitle("Поле ввода Рассчитанный доход от ведения бизнеса (руб.)");
        assertIsTrue("200000".equals(actualValue),
                "Значенние Поле ввода Рассчитанный доход от ведения бизнеса (руб.) " + actualValue + " должно быть равно 200000 . Фактическое значение = " + actualValue);
    }

    @Test
    @Tag("checking_disappearing_tables_3208768")
    @DisplayName("3208768 - Проверка дохода. Проверка исчезновения таблицы. Учредитель")
    @WorkItemIds({"3208768"})
    public void checking_disappearing_tables_3208768() {
        personalAccountPage.clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка дохода/Версия 1")
                .clickOnElement("Переключатель Нет (Деятельность компании подразумевает большую закупочную часть или траты вне персонала)")
                .fillInput("Поле ввода Выручка по официальным данным за предыдущий год (руб.)", "3000000")
                .fillInput("Поле ввода Доля в бизнесе (%)", "50")
                .clickOnElement("Кнопка Рассчитать")
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Доход не завышен")
                .assertElementByTitleActivity("Кнопка Рассчитать", "не активен");
        String actualValue = incomeVerificationPage.getValueByElementTitle("Поле ввода Рассчитанный доход от ведения бизнеса (руб.)");
        assertIsTrue("90000".equals(actualValue),
                "Значенние Поле ввода Рассчитанный доход от ведения бизнеса (руб.) " + actualValue + " должно быть равно 90000 . Фактическое значение = " + actualValue);
        incomeVerificationPage
                .clickOnElement("Кнопка Далее")
                .assertElementByTitleVisibility("Кнопка Завершить проверку", "отображается")
                .clickOnElement("Кнопка Изменить результат")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "")
                .assertElementByTitleVisibility("Кнопка Рассчитать", "не отображается")
                .assertElementByTitleVisibility("Таблица Оценка дохода проведена", "не отображается")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Оценка дохода проведена")
                .assertElementByTitleVisibility("Кнопка Рассчитать", "отображается")
                .assertElementByTitleVisibility("Таблица Оценка дохода проведена", "отображается");
        assertIsEquals("0", incomeVerificationPage.getValueByElementTitle("Поле ввода Выручка по официальным данным за предыдущий год (руб.)"),
                "Поле ввода Выручка по официальным данным за предыдущий год (руб.)");
        assertIsEquals("0", incomeVerificationPage.getValueByElementTitle("Поле ввода Доля в бизнесе (%)"),
                "Поле ввода Доля в бизнесе (%)");
        assertIsEquals("", incomeVerificationPage.getValueByElementTitle("Поле ввода Рассчитанный доход от ведения бизнеса (руб.)"),
                "Поле ввода Рассчитанный доход от ведения бизнеса (руб.)");
        incomeVerificationPage
                .assertElementByTitleVisibility("Кнопка Рассчитать", "отображается")
                .clickOnElement("Кнопка Сохранить");
    }

    @Test
    @Tag("checking_validation_fill_3208798")
    @DisplayName("3208798 - Проверка дохода. Валидация числового поля для \"Рассчитанный доход от ведения бизнеса (руб.)\" в таблице. Учредитель")
    @WorkItemIds({"3208798"})
    public void checking_validation_fill_3208798() {
        String actualValue;
        personalAccountPage.clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка дохода/Версия 1")
                .fillInput("Поле ввода Выручка по официальным данным за предыдущий год (руб.)", "Текст");
        BODY.click();
        actualValue = incomeVerificationPage.getValueByElementTitle("Поле ввода Выручка по официальным данным за предыдущий год (руб.)");
        assertIsTrue("".equals(actualValue),
                "Поле ввода Выручка по официальным данным за предыдущий год (руб.) должно равно 0. Фактическое значение = " + actualValue);
        incomeVerificationPage.fillInput("Поле ввода Выручка по официальным данным за предыдущий год (руб.)", "?");
        BODY.click();
        actualValue = incomeVerificationPage.getValueByElementTitle("Поле ввода Выручка по официальным данным за предыдущий год (руб.)");
        assertIsTrue("0".equals(actualValue),
                "Поле ввода Выручка по официальным данным за предыдущий год (руб.) должно равно 0. Фактическое значение = " + actualValue);
        incomeVerificationPage.fillInput("Поле ввода Выручка по официальным данным за предыдущий год (руб.)", "3000000");
        BODY.click();
        actualValue = incomeVerificationPage.getValueByElementTitle("Поле ввода Выручка по официальным данным за предыдущий год (руб.)");
        assertIsTrue("3000000".equals(actualValue.replaceAll("\u00A0", "")),
                "Поле ввода Выручка по официальным данным за предыдущий год (руб.) должно быть равно 3 000 000. Фактическое значение = " + actualValue);
    }

    @Test
    @Tag("checking_validation_fill_3208808")
    @DisplayName("3208808 - Проверка дохода. Валидация числового поля для \"Доля в бизнесе (%)\" в таблице. Учредитель")
    @WorkItemIds({"3208808"})
    public void checking_validation_fill_3208808() {
        String actualValue;
        personalAccountPage.clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка дохода/Версия 1")
                .fillInput("Поле ввода Доля в бизнесе (%)", "Текст")
                .clickOnElement("Поле ввода Выручка по официальным данным за предыдущий год (руб.)");
        actualValue = incomeVerificationPage.getValueByElementTitle("Поле ввода Доля в бизнесе (%)");
        assertIsTrue("".equals(actualValue),
                "Поле ввода Доля в бизнесе (%) должно равно 0. Фактическое значение = " + actualValue);

        incomeVerificationPage.fillInput("Поле ввода Доля в бизнесе (%)", "?")
                .clickOnElement("Поле ввода Выручка по официальным данным за предыдущий год (руб.)");
        sleep(1);
        actualValue = incomeVerificationPage.getValueByElementTitle("Поле ввода Доля в бизнесе (%)");
        assertIsTrue("0".equals(actualValue),
                "Поле ввода Доля в бизнесе (%) должно равно 0. Фактическое значение = " + actualValue);

        incomeVerificationPage.fillInput("Поле ввода Доля в бизнесе (%)", "1")
                .clickOnElement("Поле ввода Выручка по официальным данным за предыдущий год (руб.)");
        actualValue = incomeVerificationPage.getValueByElementTitle("Поле ввода Доля в бизнесе (%)");
        assertIsTrue("1".equals(actualValue),
                "Поле ввода Доля в бизнесе (%) должно быть равно 1. Фактическое значение = " + actualValue);

        incomeVerificationPage.fillInput("Поле ввода Доля в бизнесе (%)", "1000")
                .clickOnElement("Поле ввода Выручка по официальным данным за предыдущий год (руб.)");
        actualValue = incomeVerificationPage.getValueByElementTitle("Поле ввода Доля в бизнесе (%)");
        assertIsTrue("".equals(actualValue),
                "Поле ввода Доля в бизнесе (%) должно быть равно 1000. Фактическое значение = " + actualValue);

        incomeVerificationPage
                .fillInput("Поле ввода Доля в бизнесе (%)", "100")
                .clickOnElement("Поле ввода Выручка по официальным данным за предыдущий год (руб.)");
        actualValue = incomeVerificationPage.getValueByElementTitle("Поле ввода Доля в бизнесе (%)");
        assertIsTrue("100".equals(actualValue),
                "Поле ввода Доля в бизнесе (%) должно быть равно 100. Фактическое значение = " + actualValue);
    }

    @Test
    @Tag("checking_unavailability_entering_number_3208819")
    @DisplayName("3208819 - Проверка дохода. Проверка недоступности ввода числа в поле \"Рассчитанный доход от ведения бизнеса (руб.)\" в таблице. Учредитель")
    @WorkItemIds({"3208819"})
    public void checking_unavailability_entering_number_3208819() {
        personalAccountPage.clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка дохода/Версия 1")
                .assertElementByTitleVisibility("Таблица Оценка дохода проведена", "отображается")
                .assertElementByTitleVisibility("Поле ввода Рассчитанный доход от ведения бизнеса (руб.)", "отображается")
                .assertElementByTitleBlock("Поле ввода знчения Рассчитанный доход от ведения бизнеса (руб.)", "заблокирован");
    }

    @Test
    @Tag("checking_modal_window_3208887")
    @DisplayName("3208887 - Проверка дохода. Проверка модального окна по нажатию на кнопку \"Рассчитать\". Учредитель")
    @WorkItemIds({"3208887"})
    public void checking_modal_window_3208887() {
        personalAccountPage.clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка дохода/Версия 1")
                .clickOnElement("Кнопка Рассчитать")
                .checkElementByTitleContains("Модальное окно с сообщением", "Пожалуйста, заполните поле: " +
                        "\"Деятельность компании подразумевает большую закупочную часть или траты вне персонала\".")
                .clickOnElement("Кнопка ОК");
    }

    @Test
    @Tag("checking_modal_window_3208977")
    @DisplayName("3208977 - Проверка дохода. Проверка модального окна по нажатию на кнопку \"Далее\". Все поля не заполнены. Учредитель")
    @WorkItemIds({"3208977"})
    public void checking_modal_window_3208977() {
        personalAccountPage.clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка дохода/Версия 1")
                .clickOnElement("Кнопка Далее")
                .checkElementByTitleContains("Модальное окно с сообщением", "Пожалуйста, заполните поле: " +
                        "\"Деятельность компании подразумевает большую закупочную часть или траты вне персонала\".")
                .clickOnElement("Кнопка ОК");
    }

    @Test
    @Tag("checking_modal_window_3209079")
    @DisplayName("3209079 - Проверка дохода. Проверка модального окна по нажатию на кнопку \"Далее\". Нет рассчитанного дохода. Учредитель")
    @WorkItemIds({"3209079"})
    public void checking_modal_window_3209079() {
        personalAccountPage.clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка дохода/Версия 1")
                .clickOnElement("Переключатель Нет (Деятельность компании подразумевает большую закупочную часть или траты вне персонала)")
                .fillInput("Поле ввода Выручка по официальным данным за предыдущий год (руб.)", "3000000")
                .fillInput("Поле ввода Доля в бизнесе (%)", "50")
                .clickOnElement("Кнопка Далее")
                .checkElementByTitleContains("Модальное окно с сообщением", "Пожалуйста, нажмите на кнопку «Рассчитать»")
                .clickOnElement("Кнопка ОК");
    }

    @Test
    @Tag("checking_modal_window_3209021")
    @DisplayName("3209021 - Проверка дохода. Проверка модального окна по нажатию на кнопку \"Далее\". \"Результат проверки\" = \"пустое значение\". Учредитель")
    @WorkItemIds({"3209021"})
    public void checking_modal_window_3209021() {
        personalAccountPage.clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка дохода/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "")
                .assertElementByTitleVisibility("Кнопка Рассчитать", "не отображается")
                .assertElementByTitleVisibility("Таблица Оценка дохода проведена", "не отображается")
                .clickOnElement("Кнопка Далее")
                .checkElementByTitleContains("Модальное окно с сообщением", "Для завершения шага необходимо заполнить результат проверки или результат по заявке")
                .clickOnElement("Кнопка ОК");
    }

    @Test
    @Tag("checking_modal_window_3209061")
    @DisplayName("3209061 - Проверка дохода. Проверка разблокировки таблицы для редактирования. Учредитель")
    @WorkItemIds({"3209061"})
    public void checking_modal_window_3209061() {
        personalAccountPage.clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка дохода/Версия 1")
                .clickOnElement("Переключатель Нет (Деятельность компании подразумевает большую закупочную часть или траты вне персонала)")
                .fillInput("Поле ввода Выручка по официальным данным за предыдущий год (руб.)", "3000000")
                .fillInput("Поле ввода Доля в бизнесе (%)", "50")
                .clickOnElement("Кнопка Рассчитать");
        assertIsEquals("90000", incomeVerificationPage.getValueByElementTitle("Поле ввода Рассчитанный доход от ведения бизнеса (руб.)"),
                "Поле ввода Рассчитанный доход от ведения бизнеса (руб.)");

        incomeVerificationPage.checkElementByTitleEquals("Выпадающий список Результат проверки", "Доход не завышен")
                .assertElementByTitleActivity("Кнопка Рассчитать", "не активен")
                .assertElementByTitleVisibility("Кнопка Редактирования", "отображается")
                .clickOnElement("Кнопка Редактирования")
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Оценка дохода проведена")
                .assertElementByTitleActivity("Кнопка Рассчитать", "активен");
        assertIsEquals("", incomeVerificationPage.getValueByElementTitle("Поле ввода Рассчитанный доход от ведения бизнеса (руб.)"),
                "Поле ввода Рассчитанный доход от ведения бизнеса (руб.)");
    }
}
