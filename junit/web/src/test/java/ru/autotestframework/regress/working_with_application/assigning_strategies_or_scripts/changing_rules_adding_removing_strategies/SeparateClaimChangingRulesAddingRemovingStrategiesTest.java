package ru.autotestframework.regress.working_with_application.assigning_strategies_or_scripts.changing_rules_adding_removing_strategies;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import java.util.Arrays;
import java.util.Map;

import static ru.autotestframework.steps.asserts.Asserts.assertIsEquals;
import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;

@Tag("regress")
@Tag("working_with_application")
@Tag("assigning_strategies_or_scripts")
@Tag("changing_rules_adding_removing_strategies")
@Tag("separate_claim_changing_rules_adding_removing_strategies")
@ClassName("Изменение правил добавления/удаления стратегий. На каждый кейс отдельная заявка -Изменение правил добавления/удаления стратегий")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SeparateClaimChangingRulesAddingRemovingStrategiesTest extends BaseTest {

    String[][] expectedValues = {
            {"Наименование стратегии", "Статус", "Результат", "Ссылка"},
            {"L0.Проверка документов клиента", "В работе", "", "Открыть стратегию"},
            {"L0.Проверка документов работодателя", "В работе", "", "Открыть стратегию"},
            {"ФССП", "Назначен", "", ""},
            {"Открытые источники - привязка телефона из анкеты", "Назначен", "", ""}};

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
    }

    @AfterAll
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("smoke")
    @Tag("excluded_KP_not_assigned_L0_3469461")
    @DisplayName("3469461 - Стратегия \"Открытые источники  - проверка работодателя\" была в первоначальном наборе, но исключилась по КП. Не назначится после L0.")
    @WorkItemIds({"3469461"})
    public void excluded_KP_not_assigned_L0_3469461(TestInfo testInfo) {
        String[][] expectedValues = {
                {"Наименование стратегии", "Статус", "Результат", "Ссылка"},
                {"L0.Проверка документов клиента", "В работе", "", "Открыть стратегию"},
                {"L0.Проверка документов работодателя", "В работе", "", "Открыть стратегию"},
                {"ФССП", "Назначен", "", ""}};
        String[][] expectedValuesPostVerification = {
                {"Наименование стратегии", "Статус", "Результат", "Ссылка"},
                {"L0.Проверка документов клиента", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"L0.Проверка документов работодателя", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"ФССП", "В работе", "", "Открыть стратегию"},
                {"Проверка предыдущих заявок", "Назначен", "", ""},
                {"Прозвон клиента", "Назначен", "", ""},};
        Map<String, String> claimParams = Map.of(
                "incomeMain", "ConfirmedIncomeForm12");
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2525528.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(l0CheckingDocumentsPage)
                .clickOnElement("Кнопка Основные данные")
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Результаты проверок")
                .goTo(resultCheck)
                .clickOnElement("Вкладка Ручные проверки")
                .goTo(manualChecks)
                .clickOnElement("Кнопка Верификация назначенные проверки");
        String[][] actualValues = manualChecks.getTableHeadersAndContentAsArray("Таблица Верификация назначенные проверки");
        assertIsTrue(Arrays.deepEquals(expectedValues, actualValues), "Актуальные \n" + Arrays.deepToString(actualValues) + "\n и ожидаемые значения \n" + Arrays.deepToString(expectedValues) + "\n в таблице \"Таблица Верификация назначенные проверки\" должны совпадать");
        manualChecks.closeCurrentTab().closeCurrentTab()
                .goTo(personalAccountPage)
                .doubleClickByText(claim)
                .switchToNewTab()
                .waitBusyCondition()
                .goTo(l0CheckingDocumentsPage)
                .checkElementByTitleContains("Поле Наименование стратегии", "L0.Проверка документов")
                .clickOnNotProvidedIconForDoc("Паспорт", "Документ не предоставлен")
                .clickButtonsForTrigger("Да", "Регистрация в текущем регионе менее 1 года", "Паспорт")
                .clickButtonsForTrigger("Нет", "Подпись в анкете и паспорте отличается", "Паспорт")
                .clickButtonsForTrigger("Нет", "Признаки фальсификации", "Паспорт")
                .clickButtonsForTrigger("Нет", "Отметка о рождении ребенка до 1,5 лет на момент обращения", "Паспорт")
                .clickOnElement("Кнопка Далее")
                .waitBusyCondition()
                .clickOnNotProvidedIconForDoc("Военный билет", "Документ не предоставлен")
                .clickButtonsForTrigger("Нет", "Признаки фальсификации", "Военный билет")
                .clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен")
                .clickButtonsForTrigger("Да", "ЭТК", "Выбор дополнительных документов")
                .clickButtonsForTrigger("Нет", "Выписка из СФР с доходом", "Выбор дополнительных документов")
                .clickButtonsForTrigger("Нет", "Выписка из СФР без дохода", "Выбор дополнительных документов")
                .clickButtonsForTrigger("Нет", "Выписка с з/п счета с ящика doc", "Выбор дополнительных документов")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab().waitBusyCondition()
                .goTo(personalAccountPage);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(l0CheckingDocumentsPage)
                .clickOnElement("Кнопка Основные данные")
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Результаты проверок")
                .goTo(resultCheck)
                .clickOnElement("Вкладка Ручные проверки")
                .goTo(manualChecks)
                .clickOnElement("Кнопка Верификация назначенные проверки");
        String[][] actualValuesPostVerification = manualChecks.getTableHeadersAndContentAsArray("Таблица Верификация назначенные проверки");
        assertIsTrue(Arrays.deepEquals(expectedValuesPostVerification, actualValuesPostVerification), "Актуальные \n" + Arrays.deepToString(actualValuesPostVerification) + "\n и ожидаемые значения \n" + Arrays.deepToString(expectedValuesPostVerification) + "\n в таблице \"Таблица Верификация назначенные проверки\" должны совпадать");
        manualChecks.closeCurrentTab().closeCurrentTab()
                .goTo(loginPage)
                .resetFilters()
                .openMenuLinks("Личный кабинет");
    }

    @Test
    @Tag("smoke")
    @Tag("excluded_result_L0_by_C195_3469468")
    @DisplayName("3469468 - Стратегия \"Открытые источники - Привязка телефона из анкеты\" была в первоначальном наборе, но исключилась в рез-те проверки L0 по С195.")
    @WorkItemIds({"3469468"})
    public void excluded_result_L0_by_C195_3469468(TestInfo testInfo) {
        String[][] expectedValuesPostVerification = {
                {"Наименование стратегии", "Статус", "Результат", "Ссылка"},
                {"L0.Проверка документов клиента", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"L0.Проверка документов работодателя", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"ФССП", "В работе", "", "Открыть стратегию"},
                {"Открытые источники - привязка телефона из анкеты", "Назначен", "", ""}};
        Map<String, String> claimParams = Map.of(
                "Code", "stub1",
                "kpClient", "Client_Salary_Other",
                "kpMain", "Client_Salary_Other");
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2525938.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(l0CheckingDocumentsPage)
                .clickOnElement("Кнопка Основные данные")
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Результаты проверок")
                .goTo(resultCheck)
                .clickOnElement("Вкладка Ручные проверки")
                .goTo(manualChecks)
                .clickOnElement("Кнопка Верификация назначенные проверки");
        String[][] actualValues = manualChecks.getTableHeadersAndContentAsArray("Таблица Верификация назначенные проверки");
        assertIsTrue(Arrays.deepEquals(expectedValues, actualValues), "Актуальные \n" + Arrays.deepToString(actualValues) + "\n и ожидаемые значения \n" + Arrays.deepToString(expectedValues) + "\n в таблице \"Таблица Верификация назначенные проверки\" должны совпадать");
        manualChecks.closeCurrentTab().closeCurrentTab()
                .goTo(personalAccountPage)
                .doubleClickByText(claim)
                .switchToNewTab()
                .waitBusyCondition()
                .goTo(l0CheckingDocumentsPage)
                .checkElementByTitleContains("Поле Наименование стратегии", "L0.Проверка документов")
                .clickOnNotProvidedIconForDoc("Паспорт", "Документ не предоставлен")
                .clickButtonsForTrigger("Нет", "Подпись в анкете и паспорте отличается", "Паспорт")
                .clickButtonsForTrigger("Нет", "Признаки фальсификации", "Паспорт")
                .clickButtonsForTrigger("Нет", "Отметка о рождении ребенка до 1,5 лет на момент обращения", "Паспорт")
                .clickButtonsForTrigger("Нет", "Регистрация в текущем регионе менее 1 года", "Паспорт")
                .clickOnElement("Кнопка Далее")
                .waitBusyCondition()
                .clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен")
                .clickButtonsForTrigger("Да", "Выписка из СФР с доходом", "Выбор дополнительных документов")
                .clickButtonsForTrigger("Нет", "Выписка из СФР без дохода", "Выбор дополнительных документов")
                .clickButtonsForTrigger("Нет", "ЭТК", "Выбор дополнительных документов")
                .clickButtonsForTrigger("Нет", "Выписка с з/п счета с ящика doc", "Выбор дополнительных документов")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab().waitBusyCondition()
                .goTo(personalAccountPage)
                .openMenuLinks("Очереди")
                .waitBusyCondition()
                .searchClaimOnPage(claim)
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(l0CheckingDocumentsPage)
                .clickOnElement("Кнопка Основные данные")
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Результаты проверок")
                .goTo(resultCheck)
                .clickOnElement("Вкладка Ручные проверки")
                .goTo(manualChecks)
                .clickOnElement("Кнопка Верификация назначенные проверки");
        String[][] actualValuesPostVerification = manualChecks.getTableHeadersAndContentAsArray("Таблица Верификация назначенные проверки");
        assertIsTrue(Arrays.deepEquals(expectedValuesPostVerification, actualValuesPostVerification), "Актуальные \n" + Arrays.deepToString(actualValuesPostVerification) + "\n и ожидаемые значения \n" + Arrays.deepToString(expectedValuesPostVerification) + "\n в таблице \"Таблица Верификация назначенные проверки\" должны совпадать");
        manualChecks.clickOnElement("Кнопка Верификация рассчитанные проверки");
        assertIsEquals("Привязка телефона из анкеты", manualChecks.getTextFromTable("Таблица Верификация рассчитанные проверки", 8, "Наименование стратегии"), "Значение столбца 'Наименование стратегии'");
        assertIsEquals("Основное место работы", manualChecks.getTextFromTable("Таблица Верификация рассчитанные проверки", 8, "Работодатель"), "Значение столбца 'Работодатель'");
        assertIsEquals("L0.Проверка документов работодателя - Выписка из СФР с доходом", manualChecks.getTextFromTable("Таблица Верификация рассчитанные проверки", 8, "Причина исключения"), "Значение столбца 'Причина исключения'");
        assertIsEquals("Привязка телефона из анкеты", manualChecks.getTextFromTable("Таблица Верификация рассчитанные проверки", 9, "Наименование стратегии"), "Значение столбца 'Наименование стратегии'");
        assertIsEquals("Совместительство", manualChecks.getTextFromTable("Таблица Верификация рассчитанные проверки", 9, "Работодатель"), "Значение столбца 'Работодатель'");
        assertIsEquals("", manualChecks.getTextFromTable("Таблица Верификация рассчитанные проверки", 9, "Причина исключения"), "Значение столбца 'Причина исключения'");
        manualChecks.closeCurrentTab().closeCurrentTab()
                .goTo(loginPage)
                .resetFilters()
                .openMenuLinks("Личный кабинет");
    }

    @Test
    @Tag("smoke")
    @Tag("not_excluded_result_L0_by_C195_3469462")
    @DisplayName("3469462 - Стратегия \"Открытые источники - Привязка телефона из анкеты\" была в первоначальном наборе, но не исключилась в рез-те проверки L0 по С195.")
    @WorkItemIds({"3469462"})
    public void not_excluded_result_L0_by_C195_3469462(TestInfo testInfo) {
        String[][] expectedValuesPostVerification = {
                {"Наименование стратегии", "Статус", "Результат", "Ссылка"},
                {"L0.Проверка документов клиента", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"L0.Проверка документов работодателя", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"ФССП", "В работе", "", "Открыть стратегию"},
                {"Открытые источники - проверка работодателя", "Назначен", "", ""},
                {"Открытые источники - привязка телефона из анкеты", "Назначен", "", ""},
                {"Прозвон работодателя - любой телефон (Обязательный)", "Назначен", "", ""}};
        Map<String, String> claimParams = Map.of(
                "Code", "stub1",
                "kpClient", "Client_Salary_Other",
                "kpMain", "Client_Salary_Other");
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2525938.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(l0CheckingDocumentsPage)
                .clickOnElement("Кнопка Основные данные")
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Результаты проверок")
                .goTo(resultCheck)
                .clickOnElement("Вкладка Ручные проверки")
                .goTo(manualChecks)
                .clickOnElement("Кнопка Верификация назначенные проверки");
        String[][] actualValues = manualChecks.getTableHeadersAndContentAsArray("Таблица Верификация назначенные проверки");
        assertIsTrue(Arrays.deepEquals(expectedValues, actualValues), "Актуальные \n" + Arrays.deepToString(actualValues) + "\n и ожидаемые значения \n" + Arrays.deepToString(expectedValues) + "\n в таблице \"Таблица Верификация назначенные проверки\" должны совпадать");
        manualChecks.closeCurrentTab().closeCurrentTab()
                .goTo(personalAccountPage)
                .doubleClickByText(claim)
                .switchToNewTab()
                .waitBusyCondition()
                .goTo(l0CheckingDocumentsPage)
                .checkElementByTitleContains("Поле Наименование стратегии", "L0.Проверка документов")
                .clickOnNotProvidedIconForDoc("Паспорт", "Документ не предоставлен")
                .clickButtonsForTrigger("Нет", "Подпись в анкете и паспорте отличается", "Паспорт")
                .clickButtonsForTrigger("Нет", "Признаки фальсификации", "Паспорт")
                .clickButtonsForTrigger("Нет", "Отметка о рождении ребенка до 1,5 лет на момент обращения", "Паспорт")
                .clickButtonsForTrigger("Нет", "Регистрация в текущем регионе менее 1 года", "Паспорт")
                .clickOnElement("Кнопка Далее")
                .waitBusyCondition()
                .clickOnNotProvidedIconForDoc("2-НДФЛ", "Документ не предоставлен")
                .clickButtonsForTrigger("Да", "Доход по коду 2014", "2-НДФЛ")
                .clickButtonsForTrigger("Нет", "Доход по коду 2611", "2-НДФЛ")
                .clickButtonsForTrigger("Нет", "Доход по коду 2013", "2-НДФЛ")
                .clickButtonsForTrigger("Нет", "Наличие сведений о ликвидации", "2-НДФЛ")
                .clickButtonsForTrigger("Нет", "Признаки подделки", "2-НДФЛ")
                .clickButtonsForTrigger("Нет", "Признаки фальсификации", "2-НДФЛ")
                .clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен")
                .clickButtonsForTrigger("Да", "Выписка из СФР с доходом", "Выбор дополнительных документов")
                .clickButtonsForTrigger("Нет", "Выписка из СФР без дохода", "Выбор дополнительных документов")
                .clickButtonsForTrigger("Нет", "ЭТК", "Выбор дополнительных документов")
                .clickButtonsForTrigger("Нет", "Выписка с з/п счета с ящика doc", "Выбор дополнительных документов")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab().waitBusyCondition()
                .goTo(personalAccountPage)
                .openMenuLinks("Очереди")
                .waitBusyCondition()
                .searchClaimOnPage(claim)
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(l0CheckingDocumentsPage)
                .clickOnElement("Кнопка Основные данные")
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Результаты проверок")
                .goTo(resultCheck)
                .clickOnElement("Вкладка Ручные проверки")
                .goTo(manualChecks)
                .clickOnElement("Кнопка Верификация назначенные проверки");
        String[][] actualValuesPostVerification = manualChecks.getTableHeadersAndContentAsArray("Таблица Верификация назначенные проверки");
        assertIsTrue(Arrays.deepEquals(expectedValuesPostVerification, actualValuesPostVerification), "Актуальные \n" + Arrays.deepToString(actualValuesPostVerification) + "\n и ожидаемые значения \n" + Arrays.deepToString(expectedValuesPostVerification) + "\n в таблице \"Таблица Верификация назначенные проверки\" должны совпадать");
        manualChecks.closeCurrentTab().closeCurrentTab()
                .goTo(loginPage)
                .resetFilters()
                .openMenuLinks("Личный кабинет");
    }
}
