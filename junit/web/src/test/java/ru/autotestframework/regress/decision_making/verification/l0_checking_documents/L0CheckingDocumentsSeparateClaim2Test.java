package ru.autotestframework.regress.decision_making.verification.l0_checking_documents;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import java.util.List;
import java.util.Map;

import static ru.autotestframework.pages.card_request.verification.L0CheckingDocumentsPage.STEP_2;

@Tag("regress")
@Tag("decision_making")
@Tag("verification")
@Tag("l0_checking_documents")
@Tag("l0_separate_claim_2")
@ClassName("L0 Проверка документов. На каждый кейс отдельная заявка")
public class L0CheckingDocumentsSeparateClaim2Test extends L0CheckingDocumentsSteps {

    @Test
    @Tag("l0_checking_documents_1645463")
    @DisplayName("1645463 - Результат проверки. Документ \"2-НДФЛ\" для \"Признаки подделки\" - да ")
    @WorkItemIds({"1645463"})
    public void strategy_l0_1645463(TestInfo testInfo) {
        String[][] expectedValues = {
                {"Наименование стратегии", "Статус", "Результат", "Ссылка"},
                {"L0.Проверка документов клиента", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"L0.Проверка документов работодателя", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"ФССП", "В работе", "", "Открыть стратегию"},
                {"Проверка предыдущих заявок", "Назначен", "", ""},
                {"Проверка дохода", "Назначен", "", ""},
                {"Открытые источники - проверка сайта", "Назначен", "", ""},
                {"Открытые источники - проверка работодателя", "Назначен", "", ""},
                {"Открытые источники - привязка телефона из анкеты", "Назначен", "", ""},
                {"Открытые источники - брокерские услуги", "Назначен", "", ""},
                {"Прозвон клиента", "Назначен", "", ""},
                {"Прозвон работодателя - подтвержденный телефон", "Назначен", "", ""}};
        Map<String, String> claimParams = Map.of(
                "incomeMain", "ConfirmedIncomeForm12",
                "kpMain", "Comp_Rel_Corp",
                "kpClient", "null",
                "Code", "stub1");
        String document = "Паспорт";
        List<String> expectedTriggers = List.of(
                "Подпись в анкете и паспорте отличается",
                "Признаки фальсификации",
                "Отметка о рождении ребенка до 1,5 лет на момент обращения",
                "Регистрация в текущем регионе менее 1 года");
        String documentStep2 = "2-НДФЛ";

        String claim = getClaim(testInfo, "1651046", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2)
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен")
                .clickButtonsForTrigger("Да", "Признаки подделки", documentStep2)
                .clickOnSign("Дублирование реквизитов")
                .clickOnElement("Кнопка Готово на модальном окне Детализация признаков")
                .clickButtonsForTrigger("Нет", "Доход по коду 2611", documentStep2)
                .clickButtonsForTrigger("Нет", "Доход по коду 2013", documentStep2)
                .clickButtonsForTrigger("Нет", "Доход по коду 2014", documentStep2)
                .clickButtonsForTrigger("Нет", "Наличие сведений о ликвидации", documentStep2)
                .clickButtonsForTrigger("Нет", "Признаки фальсификации", documentStep2)
                .clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен");
        TRIGGERS_ADDITIONAL_DOCS.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, "Выбор дополнительных документов"));
        checkTableVerification(claim, expectedValues);
    }

    @Test
    @Tag("l0_checking_documents_1645464")
    @DisplayName("1645464 - Результат проверки. Документ \"2-НДФЛ\" для \"Признаки фальсификации\" - да")
    @WorkItemIds({"1645464"})
    public void strategy_l0_1645464(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "incomeMain", "ConfirmedIncomeForm12",
                "kpMain", "Comp_Rel_Corp",
                "kpClient", "null",
                "Code", "stub1");
        String document = "Паспорт";
        List<String> expectedTriggers = List.of(
                "Подпись в анкете и паспорте отличается",
                "Признаки фальсификации",
                "Отметка о рождении ребенка до 1,5 лет на момент обращения",
                "Регистрация в текущем регионе менее 1 года");
        String documentStep2 = "2-НДФЛ";

        String claim = getClaim(testInfo, "1651046", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2)
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен")
                .clickButtonsForTrigger("Да", "Признаки фальсификации", documentStep2)
                .clickOnSign("Отсутствует месяц получения дохода")
                .clickOnElement("Кнопка Готово на модальном окне Детализация признаков")
                .clickButtonsForTrigger("Нет", "Доход по коду 2611", documentStep2)
                .clickButtonsForTrigger("Нет", "Доход по коду 2013", documentStep2)
                .clickButtonsForTrigger("Нет", "Доход по коду 2014", documentStep2)
                .clickButtonsForTrigger("Нет", "Наличие сведений о ликвидации", documentStep2)
                .clickButtonsForTrigger("Нет", "Признаки подделки", documentStep2)
                .clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен");
        TRIGGERS_ADDITIONAL_DOCS.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, "Выбор дополнительных документов"));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку");
        validateClaimStatus(claim);
    }

    @Test
    @Tag("l0_checking_documents_1645465")
    @DisplayName("1645465 - Результат проверки. Документ \"2-НДФЛ\" для \"Доход по коду 2013\", \"Доход по коду 2014\", \"Доход по коду 2611\" - да")
    @WorkItemIds({"1645465"})
    public void strategy_l0_1645465(TestInfo testInfo) {
        String[][] expectedValues = {
                {"Наименование стратегии", "Статус", "Результат", "Ссылка"},
                {"L0.Проверка документов клиента", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"L0.Проверка документов работодателя", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"ФССП", "В работе", "", "Открыть стратегию"},
                {"Прозвон клиента", "Назначен", "", ""},
                {"Прозвон работодателя - подтвержденный телефон (обязательный)", "Назначен", "", ""},
                {"Прозвон работодателя - любой телефон (Обязательный)", "Назначен", "", ""}};
        Map<String, String> claimParams = Map.of(
                "incomeMain", "ConfirmedIncomeForm12",
                "kpMain", "Comp_Type_OPK_Macro_War",
                "kpClient", "null",
                "Code", "stub1");
        String document = "Паспорт";
        List<String> expectedTriggers = List.of(
                "Подпись в анкете и паспорте отличается",
                "Признаки фальсификации",
                "Отметка о рождении ребенка до 1,5 лет на момент обращения");
        Map<String, List<String>> expectedDocsStep2 = Map.of(
                "Подтверждение дохода", List.of(
                        "2-НДФЛ",
                        "Выписка с з/п счета",
                        "Справка по форме банка/работодателя",
                        "3-НДФЛ",
                        "Выписка с з/п счета/2-НДФЛ",
                        "Выписка с з/п счета/Выписка из ПФР",
                        "2-НДФЛ/Выписка из ПФР",
                        "Справка по форме банка/Выписка с з/п счета/2-НДФЛ"),
                "Подтверждение трудоустройства", List.of(
                        "Трудовая книжка",
                        "Трудовой договор",
                        "Выписка из ПФР",
                        "Контракт о прохождении военной службы"),
                "Прочие документы", List.of(
                        "Удостоверение военнослужащего",
                        "Военный билет"),
                "Реестры", List.of(
                        "Проверка реестров"),
                "Дополнительные документы", List.of(
                        "Выбор дополнительных документов"),
                "Звание/должность", List.of(
                        "Выбор звания/должности"),
                "Автоматическое определение НИС", List.of(
                        "Автоматическое определение НИС"));
        String documentStep2 = "2-НДФЛ";

        String claim = getClaim(testInfo, "1651046", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2, expectedDocsStep2)
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен")
                .clickButtonsForTrigger("Нет", "Признаки фальсификации", documentStep2)
                .clickButtonsForTrigger("Да", "Доход по коду 2611", documentStep2)
                .clickButtonsForTrigger("Да", "Доход по коду 2013", documentStep2)
                .clickButtonsForTrigger("Да", "Доход по коду 2014", documentStep2)
                .clickButtonsForTrigger("Нет", "Признаки подделки", documentStep2)
                .clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен");
        TRIGGERS_ADDITIONAL_DOCS.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, "Выбор дополнительных документов"));
        l0CheckingDocumentsPage
                .clickButtonsForTrigger("Нет", "Удостоверение силовика/военнослужащего/военный билет", "Выбор дополнительных документов")
                .clickButtonsForTrigger("Нет", "Регистрация клиента совпадает с адресом места работы/войсковой части (только для военнослужащих)", "Выбор дополнительных документов")
                .clickOnNotProvidedIconForDoc("Выбор звания/должности", "Документ не предоставлен")
                .selectValueFromDropDownList("Выпадающий список Выберите звание/должность", "Офицеры (с генерал-майора)")
                .checkElementByTitleEquals("Поле Доход завышен/не завышен", "Доход не завышен");
        checkTableVerification(claim, expectedValues);
    }

    @Test
    @Tag("l0_checking_documents_1645466")
    @DisplayName("1645466 - Результат проверки. Документ \"2-НДФЛ\" для  всех триггеров - нет")
    @WorkItemIds({"1645466"})
    public void strategy_l0_1645466(TestInfo testInfo) {
        String[][] expectedValues = {
                {"Наименование стратегии", "Статус", "Результат", "Ссылка"},
                {"L0.Проверка документов клиента", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"L0.Проверка документов работодателя", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"ФССП", "В работе", "", "Открыть стратегию"}};
        Map<String, String> claimParams = Map.of(
                "incomeMain", "ConfirmedIncomeForm12",
                "kpMain", "Client_Salary_New",
                "kpClient", "null",
                "Code", "stub1");
        String document = "Паспорт";
        List<String> expectedTriggers = List.of(
                "Подпись в анкете и паспорте отличается",
                "Признаки фальсификации");
        Map<String, List<String>> expectedDocsStep2 = Map.of(
                "Подтверждение дохода", List.of(
                        "2-НДФЛ",
                        "Выписка с з/п счета",
                        "Справка по форме банка/работодателя",
                        "3-НДФЛ",
                        "Выписка с з/п счета/Выписка из ПФР",
                        "2-НДФЛ/Выписка из ПФР",
                        "Справка по форме банка/Выписка с з/п счета/2-НДФЛ"),
                "Подтверждение трудоустройства", List.of(
                        "Трудовая книжка",
                        "Трудовой договор",
                        "Выписка из ПФР",
                        "Контракт о прохождении военной службы"),
                "Прочие документы", List.of(
                        "Удостоверение военнослужащего",
                        "Военный билет"),
                "Дополнительные документы", List.of(
                        "Выбор дополнительных документов"));
        String documentStep2 = "2-НДФЛ";

        String claim = getClaim(testInfo, "1651046", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2, expectedDocsStep2)
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен")
                .clickButtonsForTrigger("Нет", "Признаки фальсификации", documentStep2)
                .clickButtonsForTrigger("Нет", "Доход по коду 2611", documentStep2)
                .clickButtonsForTrigger("Нет", "Доход по коду 2013", documentStep2)
                .clickButtonsForTrigger("Нет", "Доход по коду 2014", documentStep2)
                .clickButtonsForTrigger("Нет", "Наличие сведений о ликвидации", documentStep2)
                .clickButtonsForTrigger("Нет", "Признаки подделки", documentStep2)
                .clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен");
        TRIGGERS_ADDITIONAL_DOCS.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, "Выбор дополнительных документов"));
        checkTableVerification(claim, expectedValues);
    }

    @Test
    @Tag("l0_checking_documents_1645467")
    @DisplayName("1645467 - Результат проверки. Документ \"Выписка с з/п счета\" для \"Признаки подделки\" - да (Военнослужащие)")
    @WorkItemIds({"1645467"})
    public void strategy_l0_1645467(TestInfo testInfo) {
        String[][] expectedValues = {
                {"Наименование стратегии", "Статус", "Результат", "Ссылка"},
                {"L0.Проверка документов клиента", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"L0.Проверка документов работодателя", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"ФССП", "В работе", "", "Открыть стратегию"},
                {"Проверка предыдущих заявок", "Назначен", "", ""},
                {"Прозвон клиента", "Назначен", "", ""},
                {"Прозвон работодателя - подтвержденный телефон", "Назначен", "", ""}};
        Map<String, String> claimParams = Map.of(
                "incomeMain", "ConfirmedIncomeForm12",
                "kpMain", "Comp_Type_OPK_Macro_War",
                "kpClient", "null",
                "Code", "stub1");
        String document = "Паспорт";
        List<String> expectedTriggers = List.of(
                "Подпись в анкете и паспорте отличается",
                "Признаки фальсификации",
                "Отметка о рождении ребенка до 1,5 лет на момент обращения");
        Map<String, List<String>> expectedDocsStep2 = Map.of(
                "Подтверждение дохода", List.of(
                        "2-НДФЛ",
                        "Выписка с з/п счета",
                        "Справка по форме банка/работодателя",
                        "3-НДФЛ",
                        "Выписка с з/п счета/2-НДФЛ",
                        "Выписка с з/п счета/Выписка из ПФР",
                        "2-НДФЛ/Выписка из ПФР",
                        "Справка по форме банка/Выписка с з/п счета/2-НДФЛ"),
                "Подтверждение трудоустройства", List.of(
                        "Трудовая книжка",
                        "Трудовой договор",
                        "Выписка из ПФР",
                        "Контракт о прохождении военной службы"),
                "Прочие документы", List.of(
                        "Удостоверение военнослужащего",
                        "Военный билет"),
                "Реестры", List.of(
                        "Проверка реестров"),
                "Дополнительные документы", List.of(
                        "Выбор дополнительных документов"),
                "Звание/должность", List.of(
                        "Выбор звания/должности"),
                "Автоматическое определение НИС", List.of(
                        "Автоматическое определение НИС"));
        String documentStep2 = "Выписка с з/п счета";

        String claim = getClaim(testInfo, "1651046", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2, expectedDocsStep2)
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен")
                .clickButtonsForTrigger("Да", "Признаки подделки", documentStep2)
                .clickOnSign("Выписка Сбербанка - форма выписки соответствует электронной, но предоставлена на бумажном носителе")
                .clickOnElement("Кнопка Готово на модальном окне Детализация признаков")
                .clickButtonsForTrigger("Нет", "Признаки фальсификации", documentStep2)
                .clickButtonsForTrigger("Нет", "Зачисление \"Расчет при увольнении\"", documentStep2)
                .clickButtonsForTrigger("Нет", "Отсутствует начисление за последние 30 дней", documentStep2)
                .clickButtonsForTrigger("Нет", "Зачисление кредита", documentStep2)
                .clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен");
        TRIGGERS_ADDITIONAL_DOCS.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, "Выбор дополнительных документов"));
        l0CheckingDocumentsPage
                .clickButtonsForTrigger("Нет", "Удостоверение силовика/военнослужащего/военный билет", "Выбор дополнительных документов")
                .clickButtonsForTrigger("Нет", "Регистрация клиента совпадает с адресом места работы/войсковой части (только для военнослужащих)", "Выбор дополнительных документов")
                .clickOnNotProvidedIconForDoc("Выбор звания/должности", "Документ не предоставлен")
                .selectValueFromDropDownList("Выпадающий список Выберите звание/должность", "Офицеры (с генерал-майора)")
                .checkElementByTitleEquals("Поле Доход завышен/не завышен", "Доход не завышен");
        checkTableVerification(claim, expectedValues);
    }

    @Test
    @Tag("l0_checking_documents_1645468")
    @DisplayName("1645468 - Результат проверки. Документ \"Выписка с з/п счета\" для \"Признаки фальсификации\" - да")
    @WorkItemIds({"1645468"})
    public void strategy_l0_1645468(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "incomeMain", "ConfirmedIncomeForm12",
                "kpMain", "Comp_Type_Public_Servant_Spark",
                "kpClient", "null",
                "Code", "stub1");
        String document = "Паспорт";
        List<String> expectedTriggers = List.of(
                "Подпись в анкете и паспорте отличается",
                "Признаки фальсификации",
                "Отметка о рождении ребенка до 1,5 лет на момент обращения",
                "Регистрация в текущем регионе менее 1 года");
        String documentStep2 = "Выписка с з/п счета";

        String claim = getClaim(testInfo, "1651046", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2)
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен")
                .clickButtonsForTrigger("Да", "Признаки фальсификации", documentStep2)
                .clickOnSign("Расхождение по суммам в выписке Сбербанка (бумажная или электронная)")
                .clickOnElement("Кнопка Готово на модальном окне Детализация признаков")
                .clickButtonsForTrigger("Нет", "Признаки подделки", documentStep2)
                .clickButtonsForTrigger("Нет", "Зачисление \"Расчет при увольнении\"", documentStep2)
                .clickButtonsForTrigger("Нет", "Отсутствует начисление за последние 30 дней", documentStep2)
                .clickButtonsForTrigger("Нет", "Зачисление кредита", documentStep2)
                .clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен");
        TRIGGERS_ADDITIONAL_DOCS.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, "Выбор дополнительных документов"));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку");
        validateClaimStatus(claim);
    }

    @Test
    @Tag("l0_checking_documents_1645469")
    @DisplayName("1645469 - Результат проверки. Документ \"Выписка с з/п счета\" для \"Зачисление \"Расчет при увольнении\"\" - да")
    @WorkItemIds({"1645469"})
    public void strategy_l0_1645469(TestInfo testInfo) {
        String[][] expectedValues = {
                {"Наименование стратегии", "Статус", "Результат", "Ссылка"},
                {"L0.Проверка документов клиента", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"L0.Проверка документов работодателя", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"ФССП", "В работе", "", "Открыть стратегию"},
                {"Открытые источники - проверка работодателя", "Назначен", "", ""},
                {"Прозвон работодателя - любой телефон (Обязательный)", "Назначен", "", ""}};
        Map<String, String> claimParams = Map.of(
                "incomeMain", "ConfirmedIncomeForm12",
                "kpMain", "null",
                "kpClient", "null",
                "Code", "stub1");
        String document = "Паспорт";
        List<String> expectedTriggers = List.of(
                "Подпись в анкете и паспорте отличается",
                "Признаки фальсификации",
                "Отметка о рождении ребенка до 1,5 лет на момент обращения",
                "Регистрация в текущем регионе менее 1 года");
        String documentStep2 = "Выписка с з/п счета";

        String claim = getClaim(testInfo, "1651046", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2)
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен")
                .clickButtonsForTrigger("Нет", "Признаки подделки", documentStep2)
                .clickButtonsForTrigger("Нет", "Признаки фальсификации", documentStep2)
                .clickButtonsForTrigger("Да", "Зачисление \"Расчет при увольнении\"", documentStep2)
                .clickButtonsForTrigger("Нет", "Отсутствует начисление за последние 30 дней", documentStep2)
                .clickButtonsForTrigger("Нет", "Зачисление кредита", documentStep2)
                .clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен");
        TRIGGERS_ADDITIONAL_DOCS.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, "Выбор дополнительных документов"));
        checkTableVerification(claim, expectedValues);
    }

    @Test
    @Tag("l0_checking_documents_1645470")
    @DisplayName("1645470 - Результат проверки. Документ \"Выписка с з/п счета\" для \"Отсутствует начисление за последние 30 дней\" - да(Госслужащие)")
    @WorkItemIds({"1645470"})
    public void strategy_l0_1645470(TestInfo testInfo) {
        String[][] expectedValues = {
                {"Наименование стратегии", "Статус", "Результат", "Ссылка"},
                {"L0.Проверка документов клиента", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"L0.Проверка документов работодателя", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"ФССП", "В работе", "", "Открыть стратегию"},
                {"Открытые источники - проверка работодателя", "Назначен", "", ""},
                {"Прозвон работодателя - любой телефон (Обязательный)", "Назначен", "", ""}};
        Map<String, String> claimParams = Map.of(
                "incomeMain", "ConfirmedIncomeForm12",
                "kpMain", "Comp_Type_Public_Servant_Spark",
                "kpClient", "null",
                "Code", "stub1");
        String document = "Паспорт";
        List<String> expectedTriggers = List.of(
                "Подпись в анкете и паспорте отличается",
                "Признаки фальсификации",
                "Отметка о рождении ребенка до 1,5 лет на момент обращения",
                "Регистрация в текущем регионе менее 1 года");
        String documentStep2 = "Выписка с з/п счета";

        String claim = getClaim(testInfo, "1651046", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2)
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен")
                .clickButtonsForTrigger("Нет", "Признаки подделки", documentStep2)
                .clickButtonsForTrigger("Нет", "Признаки фальсификации", documentStep2)
                .clickButtonsForTrigger("Да", "Зачисление \"Расчет при увольнении\"", documentStep2)
                .clickButtonsForTrigger("Нет", "Отсутствует начисление за последние 30 дней", documentStep2)
                .clickButtonsForTrigger("Нет", "Зачисление кредита", documentStep2)
                .clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен");
        TRIGGERS_ADDITIONAL_DOCS.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, "Выбор дополнительных документов"));
        checkTableVerification(claim, expectedValues);
    }

    @Test
    @Tag("l0_checking_documents_1645471")
    @DisplayName("1645471 - Результат проверки. Документ \"Выписка с з/п счета\" для \"Зачисление кредита\" - да")
    @WorkItemIds({"1645471"})
    public void strategy_l0_1645471(TestInfo testInfo) {
        String[][] expectedValues = {
                {"Наименование стратегии", "Статус", "Результат", "Ссылка"},
                {"L0.Проверка документов клиента", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"L0.Проверка документов работодателя", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"ФССП", "В работе", "", "Открыть стратегию"},
                {"Прозвон клиента", "Назначен", "", ""}};
        Map<String, String> claimParams = Map.of(
                "incomeMain", "ConfirmedIncomeForm12",
                "kpMain", "Comp_Type_Public_Servant_Spark",
                "kpClient", "null",
                "Code", "stub1");
        String document = "Паспорт";
        List<String> expectedTriggers = List.of(
                "Подпись в анкете и паспорте отличается",
                "Признаки фальсификации",
                "Отметка о рождении ребенка до 1,5 лет на момент обращения",
                "Регистрация в текущем регионе менее 1 года");
        String documentStep2 = "Выписка с з/п счета";

        String claim = getClaim(testInfo, "1651046", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2)
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен")
                .clickButtonsForTrigger("Нет", "Признаки подделки", documentStep2)
                .clickButtonsForTrigger("Нет", "Признаки фальсификации", documentStep2)
                .clickButtonsForTrigger("Нет", "Зачисление \"Расчет при увольнении\"", documentStep2)
                .clickButtonsForTrigger("Нет", "Отсутствует начисление за последние 30 дней", documentStep2)
                .clickButtonsForTrigger("Да", "Зачисление кредита", documentStep2)
                .clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен");
        TRIGGERS_ADDITIONAL_DOCS.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, "Выбор дополнительных документов"));
        checkTableVerification(claim, expectedValues);
    }

    @Test
    @Tag("l0_checking_documents_1645472")
    @DisplayName("1645472 - Результат проверки. Документ \"Выписка с з/п счета\" для всех триггеров - нет")
    @WorkItemIds({"1645472"})
    public void strategy_l0_1645472(TestInfo testInfo) {
        String[][] expectedValues = {
                {"Наименование стратегии", "Статус", "Результат", "Ссылка"},
                {"L0.Проверка документов клиента", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"L0.Проверка документов работодателя", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"ФССП", "В работе", "", "Открыть стратегию"}};
        Map<String, String> claimParams = Map.of(
                "incomeMain", "ConfirmedIncomeForm12",
                "kpMain", "Comp_Type_OPK_Macro_War",
                "kpClient", "null",
                "Code", "stub1");
        String document = "Паспорт";
        List<String> expectedTriggers = List.of(
                "Подпись в анкете и паспорте отличается",
                "Признаки фальсификации",
                "Отметка о рождении ребенка до 1,5 лет на момент обращения");
        String documentStep2 = "Выписка с з/п счета";
        Map<String, List<String>> expectedDocsStep2 = Map.of(
                "Подтверждение дохода", List.of(
                        "2-НДФЛ",
                        "Выписка с з/п счета",
                        "Справка по форме банка/работодателя",
                        "3-НДФЛ",
                        "Выписка с з/п счета/2-НДФЛ",
                        "Выписка с з/п счета/Выписка из ПФР",
                        "2-НДФЛ/Выписка из ПФР",
                        "Справка по форме банка/Выписка с з/п счета/2-НДФЛ"),
                "Подтверждение трудоустройства", List.of(
                        "Трудовая книжка",
                        "Трудовой договор",
                        "Выписка из ПФР",
                        "Контракт о прохождении военной службы"),
                "Прочие документы", List.of(
                        "Удостоверение военнослужащего",
                        "Военный билет"),
                "Реестры", List.of(
                        "Проверка реестров"),
                "Дополнительные документы", List.of(
                        "Выбор дополнительных документов"),
                "Звание/должность", List.of(
                        "Выбор звания/должности"),
                "Автоматическое определение НИС", List.of(
                        "Автоматическое определение НИС"));

        String claim = getClaim(testInfo, "1651046", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2, expectedDocsStep2)
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен")
                .clickButtonsForTrigger("Нет", "Признаки подделки", documentStep2)
                .clickButtonsForTrigger("Нет", "Признаки фальсификации", documentStep2)
                .clickButtonsForTrigger("Нет", "Зачисление \"Расчет при увольнении\"", documentStep2)
                .clickButtonsForTrigger("Нет", "Отсутствует начисление за последние 30 дней", documentStep2)
                .clickButtonsForTrigger("Нет", "Зачисление кредита", documentStep2)
                .clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен");
        TRIGGERS_ADDITIONAL_DOCS.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, "Выбор дополнительных документов"));
        l0CheckingDocumentsPage
                .clickButtonsForTrigger("Нет", "Удостоверение силовика/военнослужащего/военный билет", "Выбор дополнительных документов")
                .clickButtonsForTrigger("Нет", "Регистрация клиента совпадает с адресом места работы/войсковой части (только для военнослужащих)", "Выбор дополнительных документов")
                .clickOnNotProvidedIconForDoc("Выбор звания/должности", "Документ не предоставлен")
                .selectValueFromDropDownList("Выпадающий список Выберите звание/должность", "Офицеры (с генерал-майора)")
                .checkElementByTitleEquals("Поле Доход завышен/не завышен", "Доход не завышен");
        checkTableVerification(claim, expectedValues);
    }

    @Test
    @Tag("l0_checking_documents_1645473")
    @DisplayName("1645473 - Результат проверки. Документ \"Справка по форме банка/работодателя\" для всех триггеров - нет")
    @WorkItemIds({"1645473"})
    public void strategy_l0_1645473(TestInfo testInfo) {
        String[][] expectedValues = {
                {"Наименование стратегии", "Статус", "Результат", "Ссылка"},
                {"L0.Проверка документов клиента", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"L0.Проверка документов работодателя", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"ФССП", "В работе", "", "Открыть стратегию"}};
        Map<String, String> claimParams = Map.of(
                "incomeMain", "ConfirmedIncomeForm12",
                "kpMain", "Comp_Type_OPK_Other",
                "kpClient", "null",
                "Code", "stub1");
        String document = "ФОТО";
        List<String> expectedTriggers = List.of(
                "Признаки лица БОМЖ",
                "Фото клиента не соответствует фото в паспорте");
        String documentStep2 = "Справка по форме банка/работодателя";

        String claim = getClaim(testInfo, "1651046", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2)
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен")
                .clickButtonsForTrigger("Нет", "Признаки подделки", documentStep2)
                .clickButtonsForTrigger("Нет", "Признаки фальсификации", documentStep2)
                .clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен");
        TRIGGERS_ADDITIONAL_DOCS.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, "Выбор дополнительных документов"));
        checkTableVerification(claim, expectedValues);
    }

    @Test
    @Tag("l0_checking_documents_1645474")
    @DisplayName("1645474 - Результат проверки. Документ \"Справка по форме банка/работодателя\" для \"Признаки фальсификации\" и \"Признаки подделки\" - да")
    @WorkItemIds({"1645474"})
    public void strategy_l0_1645474(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "incomeMain", "ConfirmedIncomeForm12",
                "kpMain", "Comp_Type_OPK_Other",
                "kpClient", "null",
                "Code", "stub1");
        String document = "ФОТО";
        List<String> expectedTriggers = List.of(
                "Признаки лица БОМЖ",
                "Фото клиента не соответствует фото в паспорте");
        String documentStep2 = "Справка по форме банка/работодателя";

        String claim = getClaim(testInfo, "1651046", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2)
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен")
                .clickButtonsForTrigger("Да", "Признаки подделки", documentStep2)
                .clickOnSign("Гербовая печать (неприменимо к бюджетным / государственным организациям)")
                .clickOnElement("Кнопка Готово на модальном окне Детализация признаков")
                .clickButtonsForTrigger("Да", "Признаки фальсификации", documentStep2)
                .clickOnSign("Наличие очевидных на копии документа признаков травления текста / подчисток / дописок / рисовок / вклейки бумаги / замены листов")
                .clickOnElement("Кнопка Готово на модальном окне Детализация признаков")
                .clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен");
        TRIGGERS_ADDITIONAL_DOCS.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, "Выбор дополнительных документов"));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку");
        validateClaimStatus(claim);
    }

    @Test
    @Tag("l0_checking_documents_1645475")
    @DisplayName("1645475 - Результат проверки. Документ \"Трудовая книжка\" для \"Признаки подделки\" - да (Военнослужащие)")
    @WorkItemIds({"1645475"})
    public void strategy_l0_1645475(TestInfo testInfo) {
        String[][] expectedValues = {
                {"Наименование стратегии", "Статус", "Результат", "Ссылка"},
                {"L0.Проверка документов клиента", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"L0.Проверка документов работодателя", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"ФССП", "В работе", "", "Открыть стратегию"},
                {"Проверка предыдущих заявок", "Назначен", "", ""},
                {"Прозвон клиента", "Назначен", "", ""},
                {"Прозвон работодателя - подтвержденный телефон", "Назначен", "", ""}};
        Map<String, String> claimParams = Map.of(
                "incomeMain", "ConfirmedIncomeForm12",
                "kpMain", "Comp_Type_OPK_Macro_War",
                "kpClient", "null",
                "Code", "stub1");
        String document = "Паспорт";
        List<String> expectedTriggers = List.of(
                "Подпись в анкете и паспорте отличается",
                "Признаки фальсификации",
                "Отметка о рождении ребенка до 1,5 лет на момент обращения");
        Map<String, List<String>> expectedDocsStep2 = Map.of(
                "Подтверждение дохода", List.of(
                        "2-НДФЛ",
                        "Выписка с з/п счета",
                        "Справка по форме банка/работодателя",
                        "3-НДФЛ",
                        "Выписка с з/п счета/2-НДФЛ",
                        "Выписка с з/п счета/Выписка из ПФР",
                        "2-НДФЛ/Выписка из ПФР",
                        "Справка по форме банка/Выписка с з/п счета/2-НДФЛ"),
                "Подтверждение трудоустройства", List.of(
                        "Трудовая книжка",
                        "Трудовой договор",
                        "Выписка из ПФР",
                        "Контракт о прохождении военной службы"),
                "Прочие документы", List.of(
                        "Удостоверение военнослужащего",
                        "Военный билет"),
                "Реестры", List.of(
                        "Проверка реестров"),
                "Дополнительные документы", List.of(
                        "Выбор дополнительных документов"),
                "Звание/должность", List.of(
                        "Выбор звания/должности"),
                "Автоматическое определение НИС", List.of(
                        "Автоматическое определение НИС"));
        String documentStep2 = "Трудовая книжка";

        String claim = getClaim(testInfo, "1651046", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2, expectedDocsStep2)
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен")
                .clickButtonsForTrigger("Да", "Признаки подделки", documentStep2)
                .clickOnSign("Серия ТК не соответствует году выдачи")
                .clickOnElement("Кнопка Готово на модальном окне Детализация признаков")
                .clickButtonsForTrigger("Нет", "Негативные причины увольнения", documentStep2)
                .clickButtonsForTrigger("Нет", "Признаки фальсификации", documentStep2)
                .clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен");
        TRIGGERS_ADDITIONAL_DOCS.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, "Выбор дополнительных документов"));
        l0CheckingDocumentsPage
                .clickButtonsForTrigger("Нет", "Удостоверение силовика/военнослужащего/военный билет", "Выбор дополнительных документов")
                .clickButtonsForTrigger("Нет", "Регистрация клиента совпадает с адресом места работы/войсковой части (только для военнослужащих)", "Выбор дополнительных документов")
                .clickOnNotProvidedIconForDoc("Выбор звания/должности", "Документ не предоставлен")
                .selectValueFromDropDownList("Выпадающий список Выберите звание/должность", "Офицеры (с генерал-майора)")
                .checkElementByTitleEquals("Поле Доход завышен/не завышен", "Доход не завышен");
        checkTableVerification(claim, expectedValues);
    }

    @Test
    @Tag("l0_checking_documents_1645476")
    @DisplayName("1645476 - Результат проверки. Документ \"Трудовая книжка\" для \"Резкий карьерный рост или резкая смена сферы деятельности\" - да (Крупные работодатели)")
    @WorkItemIds({"1645476"})
    public void strategy_l0_1645476(TestInfo testInfo) {
        String[][] expectedValues = {
                {"Наименование стратегии", "Статус", "Результат", "Ссылка"},
                {"L0.Проверка документов клиента", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"L0.Проверка документов работодателя", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"ФССП", "В работе", "", "Открыть стратегию"},
                {"Проверка предыдущих заявок", "Назначен", "", ""},
                {"Открытые источники - проверка работодателя", "Назначен", "", ""},
                {"Открытые источники - привязка телефона из анкеты", "Назначен", "", ""},
                {"Открытые источники - бесконтактное подтверждение трудоустройства", "Назначен", "", ""},
                {"Открытые источники - брокерские услуги", "Назначен", "", ""},
                {"Прозвон клиента", "Назначен", "", ""},
                {"Прозвон работодателя - подтвержденный телефон", "Назначен", "", ""}};
        Map<String, String> claimParams = Map.of(
                "incomeMain", "ConfirmedIncomeForm12",
                "kpMain", "Comp_Type_Big_earn",
                "kpClient", "null",
                "Code", "stub1");
        String document = "Паспорт";
        List<String> expectedTriggers = List.of(
                "Подпись в анкете и паспорте отличается",
                "Признаки фальсификации",
                "Отметка о рождении ребенка до 1,5 лет на момент обращения",
                "Регистрация в текущем регионе менее 1 года");
        String documentStep2 = "Трудовая книжка";

        String claim = getClaim(testInfo, "1651046", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2)
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен")
                .clickButtonsForTrigger("Да", "Резкий карьерный рост или резкая смена сферы деятельности", documentStep2)
                .clickButtonsForTrigger("Нет", "Признаки подделки", documentStep2)
                .clickButtonsForTrigger("Нет", "Негативные причины увольнения", documentStep2)
                .clickButtonsForTrigger("Нет", "Признаки фальсификации", documentStep2)
                .clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен");
        TRIGGERS_ADDITIONAL_DOCS.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, "Выбор дополнительных документов"));
        checkTableVerification(claim, expectedValues);
    }

    @Test
    @Tag("l0_checking_documents_1645477")
    @DisplayName("1645477 - Результат проверки. Документ \"Трудовая книжка\" для \"Негативные причины увольнения\" - да")
    @WorkItemIds({"1645477"})
    public void strategy_l0_1645477(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "incomeMain", "ConfirmedIncomeForm12",
                "kpMain", "null",
                "kpClient", "null",
                "Code", "stub1");
        String document = "Паспорт";
        List<String> expectedTriggers = List.of(
                "Подпись в анкете и паспорте отличается",
                "Признаки фальсификации",
                "Отметка о рождении ребенка до 1,5 лет на момент обращения",
                "Регистрация в текущем регионе менее 1 года");
        String documentStep2 = "Трудовая книжка";

        String claim = getClaim(testInfo, "1651046", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2)
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен")
                .clickButtonsForTrigger("Нет", "Резкий карьерный рост или резкая смена сферы деятельности", documentStep2)
                .clickButtonsForTrigger("Нет", "Признаки подделки", documentStep2)
                .clickButtonsForTrigger("Да", "Негативные причины увольнения", documentStep2)
                .clickButtonsForTrigger("Нет", "Признаки фальсификации", documentStep2)
                .clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен");
        TRIGGERS_ADDITIONAL_DOCS.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, "Выбор дополнительных документов"));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку");
        validateClaimStatus(claim);
    }

    @Test
    @Tag("l0_checking_documents_1645478")
    @DisplayName("1645478 - Результат проверки. Документ \"Трудовая книжка\" для \"Признаки фальсификации\" - да")
    @WorkItemIds({"1645478"})
    public void strategy_l0_1645478(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "incomeMain", "ConfirmedIncomeForm12",
                "kpMain", "Comp_Type_OPK_Macro_War",
                "kpClient", "null",
                "Code", "stub1");
        String document = "Паспорт";
        List<String> expectedTriggers = List.of(
                "Подпись в анкете и паспорте отличается",
                "Признаки фальсификации",
                "Отметка о рождении ребенка до 1,5 лет на момент обращения");
        Map<String, List<String>> expectedDocsStep2 = Map.of(
                "Подтверждение дохода", List.of(
                        "2-НДФЛ",
                        "Выписка с з/п счета",
                        "Справка по форме банка/работодателя",
                        "3-НДФЛ",
                        "Выписка с з/п счета/2-НДФЛ",
                        "Выписка с з/п счета/Выписка из ПФР",
                        "2-НДФЛ/Выписка из ПФР",
                        "Справка по форме банка/Выписка с з/п счета/2-НДФЛ"),
                "Подтверждение трудоустройства", List.of(
                        "Трудовая книжка",
                        "Трудовой договор",
                        "Выписка из ПФР",
                        "Контракт о прохождении военной службы"),
                "Прочие документы", List.of(
                        "Удостоверение военнослужащего",
                        "Военный билет"),
                "Реестры", List.of(
                        "Проверка реестров"),
                "Дополнительные документы", List.of(
                        "Выбор дополнительных документов"),
                "Звание/должность", List.of(
                        "Выбор звания/должности"),
                "Автоматическое определение НИС", List.of(
                        "Автоматическое определение НИС"));
        String documentStep2 = "Трудовая книжка";

        String claim = getClaim(testInfo, "1651046", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2, expectedDocsStep2)
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен")
                .clickButtonsForTrigger("Нет", "Признаки подделки", documentStep2)
                .clickButtonsForTrigger("Нет", "Негативные причины увольнения", documentStep2)
                .clickButtonsForTrigger("Да", "Признаки фальсификации", documentStep2)
                .clickOnSign("Одинаковые номер и серия ТК у разных клиентов")
                .clickOnElement("Кнопка Готово на модальном окне Детализация признаков")
                .clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен");
        TRIGGERS_ADDITIONAL_DOCS.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, "Выбор дополнительных документов"));
        l0CheckingDocumentsPage
                .clickButtonsForTrigger("Нет", "Удостоверение силовика/военнослужащего/военный билет", "Выбор дополнительных документов")
                .clickButtonsForTrigger("Нет", "Регистрация клиента совпадает с адресом места работы/войсковой части (только для военнослужащих)", "Выбор дополнительных документов")
                .clickOnNotProvidedIconForDoc("Выбор звания/должности", "Документ не предоставлен")
                .selectValueFromDropDownList("Выпадающий список Выберите звание/должность", "Офицеры (с генерал-майора)")
                .checkElementByTitleEquals("Поле Доход завышен/не завышен", "Доход не завышен")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку");
        validateClaimStatus(claim);
    }

    @Test
    @Tag("l0_checking_documents_1645479")
    @DisplayName("1645479 - Результат проверки. Документ \"Трудовая книжка\" для всех триггеров - нет")
    @WorkItemIds({"1645479"})
    public void strategy_l0_1645479(TestInfo testInfo) {
        String[][] expectedValues = {
                {"Наименование стратегии", "Статус", "Результат", "Ссылка"},
                {"L0.Проверка документов клиента", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"L0.Проверка документов работодателя", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"ФССП", "В работе", "", "Открыть стратегию"}};
        Map<String, String> claimParams = Map.of(
                "incomeMain", "ConfirmedIncomeForm12",
                "kpMain", "Comp_Type_Big_earn",
                "kpClient", "null",
                "Code", "stub1");
        String document = "Паспорт";
        List<String> expectedTriggers = List.of(
                "Подпись в анкете и паспорте отличается",
                "Признаки фальсификации",
                "Отметка о рождении ребенка до 1,5 лет на момент обращения",
                "Регистрация в текущем регионе менее 1 года");
        String documentStep2 = "Трудовая книжка";

        String claim = getClaim(testInfo, "1651046", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2)
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен")
                .clickButtonsForTrigger("Нет", "Резкий карьерный рост или резкая смена сферы деятельности", documentStep2)
                .clickButtonsForTrigger("Нет", "Признаки подделки", documentStep2)
                .clickButtonsForTrigger("Нет", "Негативные причины увольнения", documentStep2)
                .clickButtonsForTrigger("Нет", "Признаки фальсификации", documentStep2)
                .clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен");
        TRIGGERS_ADDITIONAL_DOCS.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, "Выбор дополнительных документов"));
        checkTableVerification(claim, expectedValues);
    }

    @Test
    @Tag("l0_checking_documents_1645480")
    @DisplayName("1645480 - Результат проверки. Документ \"Трудовая книжка\" для \"Негативные причины увольнения\" и \"Резкий карьерный рост или резкая смена сферы деятельности\" - да")
    @WorkItemIds({"1645480"})
    public void strategy_l0_1645480(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "incomeMain", "ConfirmedIncomeForm12",
                "kpMain", "Comp_Rel_Corp",
                "kpClient", "null",
                "Code", "stub1");
        String document = "Паспорт";
        List<String> expectedTriggers = List.of(
                "Подпись в анкете и паспорте отличается",
                "Признаки фальсификации",
                "Отметка о рождении ребенка до 1,5 лет на момент обращения",
                "Регистрация в текущем регионе менее 1 года");
        String documentStep2 = "Трудовая книжка";

        String claim = getClaim(testInfo, "1651046", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2)
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен")
                .clickButtonsForTrigger("Да", "Резкий карьерный рост или резкая смена сферы деятельности", documentStep2)
                .clickButtonsForTrigger("Нет", "Признаки подделки", documentStep2)
                .clickButtonsForTrigger("Да", "Негативные причины увольнения", documentStep2)
                .clickButtonsForTrigger("Нет", "Признаки фальсификации", documentStep2)
                .clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен");
        TRIGGERS_ADDITIONAL_DOCS.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, "Выбор дополнительных документов"));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку");
        validateClaimStatus(claim);
    }

    @Test
    @Tag("l0_checking_documents_1645481")
    @DisplayName("1645481 - Результат проверки. Документ \"Удостоверение военнослужащего\" для всех триггеров - нет")
    @WorkItemIds({"1645481"})
    public void strategy_l0_1645481(TestInfo testInfo) {
        String[][] expectedValues = {
                {"Наименование стратегии", "Статус", "Результат", "Ссылка"},
                {"L0.Проверка документов клиента", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"L0.Проверка документов работодателя", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"ФССП", "В работе", "", "Открыть стратегию"}};
        Map<String, String> claimParams = Map.of(
                "incomeMain", "ConfirmedIncomeForm12",
                "kpMain", "Client_Salary_Early_Macro",
                "kpClient", "null",
                "Code", "stub1");
        String document = "Паспорт";
        List<String> expectedTriggers = List.of(
                "Подпись в анкете и паспорте отличается",
                "Признаки фальсификации");
        String documentStep2 = "Удостоверение военнослужащего";
        Map<String, List<String>> expectedDocsStep2 = Map.of(
                "Подтверждение дохода", List.of(
                        "2-НДФЛ",
                        "Выписка с з/п счета",
                        "Справка по форме банка/работодателя",
                        "3-НДФЛ",
                        "Выписка с з/п счета/Выписка из ПФР",
                        "2-НДФЛ/Выписка из ПФР",
                        "Справка по форме банка/Выписка с з/п счета/2-НДФЛ"),
                "Подтверждение трудоустройства", List.of(
                        "Трудовая книжка",
                        "Трудовой договор",
                        "Выписка из ПФР",
                        "Контракт о прохождении военной службы"),
                "Прочие документы", List.of(
                        "Удостоверение военнослужащего",
                        "Военный билет"),
                "Дополнительные документы", List.of(
                        "Выбор дополнительных документов"));
        String claim = getClaim(testInfo, "1651046", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2, expectedDocsStep2)
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен")
                .clickButtonsForTrigger("Нет", "Признаки подделки", documentStep2)
                .clickButtonsForTrigger("Нет", "Признаки фальсификации", documentStep2)
                .clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен");
        TRIGGERS_ADDITIONAL_DOCS.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, "Выбор дополнительных документов"));
        checkTableVerification(claim, expectedValues);
    }

    @Test
    @Tag("l0_checking_documents_1645482")
    @DisplayName("1645482 - Результат проверки. Документ \"Удостоверение военнослужащего\" для \"Признаки фальсификации\" и \"Признаки подделки\" - да")
    @WorkItemIds({"1645482"})
    public void strategy_l0_1645482(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "incomeMain", "ConfirmedIncomeForm12",
                "kpMain", "Comp_Type_Public_Servant_Macro",
                "kpClient", "null",
                "Code", "stub1");
        String document = "Паспорт";
        List<String> expectedTriggers = List.of(
                "Подпись в анкете и паспорте отличается",
                "Признаки фальсификации",
                "Отметка о рождении ребенка до 1,5 лет на момент обращения",
                "Регистрация в текущем регионе менее 1 года");
        String documentStep2 = "Удостоверение военнослужащего";
        String claim = getClaim(testInfo, "1651046", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2)
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен")
                .clickButtonsForTrigger("Да", "Признаки подделки", documentStep2)
                .clickOnSign("Заполнена только первая страница")
                .clickOnElement("Кнопка Готово на модальном окне Детализация признаков")
                .clickButtonsForTrigger("Да", "Признаки фальсификации", documentStep2)
                .clickOnSign("Наличие печати неустановленного образца (оригинальная печать гербовая)")
                .clickOnElement("Кнопка Готово на модальном окне Детализация признаков")
                .clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен");
        TRIGGERS_ADDITIONAL_DOCS.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, "Выбор дополнительных документов"));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку");
        validateClaimStatus(claim);
    }

    @Test
    @Tag("l0_checking_documents_1645483")
    @DisplayName("1645483 - Результат проверки. Документ \"Военный билет\" для \"Признаки фальсификации\" - нет")
    @WorkItemIds({"1645483"})
    public void strategy_l0_1645483(TestInfo testInfo) {
        String[][] expectedValues = {
                {"Наименование стратегии", "Статус", "Результат", "Ссылка"},
                {"L0.Проверка документов клиента", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"L0.Проверка документов работодателя", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"ФССП", "В работе", "", "Открыть стратегию"}};
        Map<String, String> claimParams = Map.of(
                "incomeMain", "ConfirmedIncomeForm12",
                "kpMain", "Comp_Type_Public_Servant_Macro",
                "kpClient", "null",
                "Code", "stub1");
        String document = "Паспорт";
        List<String> expectedTriggers = List.of(
                "Подпись в анкете и паспорте отличается",
                "Признаки фальсификации",
                "Отметка о рождении ребенка до 1,5 лет на момент обращения",
                "Регистрация в текущем регионе менее 1 года");
        String documentStep2 = "Военный билет";
        String claim = getClaim(testInfo, "1651046", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2)
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен")
                .clickButtonsForTrigger("Нет", "Признаки фальсификации", documentStep2)
                .clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен");
        TRIGGERS_ADDITIONAL_DOCS.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, "Выбор дополнительных документов"));
        checkTableVerification(claim, expectedValues);
    }

    @Test
    @Tag("l0_checking_documents_1645484")
    @DisplayName("1645484 - Результат проверки. Документ \"3-НДФЛ\" для \"Признаки фальсификации\" - да")
    @WorkItemIds({"1645484"})
    public void strategy_l0_1645484(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "incomeMain", "ConfirmedIncomeForm12",
                "kpMain", "Comp_Type_OPK_Other",
                "kpClient", "null",
                "Code", "stub1");
        String document = "Паспорт";
        List<String> expectedTriggers = List.of(
                "Подпись в анкете и паспорте отличается",
                "Признаки фальсификации",
                "Отметка о рождении ребенка до 1,5 лет на момент обращения",
                "Регистрация в текущем регионе менее 1 года");
        String documentStep2 = "3-НДФЛ";
        String claim = getClaim(testInfo, "1651046", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2)
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен")
                .clickButtonsForTrigger("Да", "Признаки фальсификации", documentStep2)
                .clickOnSign("Наличие очевидных на копии документа признаков травления текста / подчисток / дописок / рисовок / вклейки бумаги / замены листов")
                .clickOnElement("Кнопка Готово на модальном окне Детализация признаков")
                .clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен");
        TRIGGERS_ADDITIONAL_DOCS.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, "Выбор дополнительных документов"));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку");
        validateClaimStatus(claim);
    }

    @Test
    @Tag("l0_checking_documents_1645485")
    @DisplayName("1645485 - Результат проверки. Документ \"Выписка из ПФР\" для \"Признаки подделки\" - да")
    @WorkItemIds({"1645485"})
    public void strategy_l0_1645485(TestInfo testInfo) {
        String[][] expectedValues = {
                {"Наименование стратегии", "Статус", "Результат", "Ссылка"},
                {"L0.Проверка документов клиента", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"L0.Проверка документов работодателя", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"ФССП", "В работе", "", "Открыть стратегию"},
                {"Проверка предыдущих заявок", "Назначен", "", ""},
                {"Проверка дохода", "Назначен", "", ""},
                {"Открытые источники - проверка сайта", "Назначен", "", ""},
                {"Открытые источники - проверка работодателя", "Назначен", "", ""},
                {"Открытые источники - привязка телефона из анкеты", "Назначен", "", ""},
                {"Открытые источники - брокерские услуги", "Назначен", "", ""},
                {"Прозвон клиента", "Назначен", "", ""},
                {"Прозвон работодателя - подтвержденный телефон", "Назначен", "", ""}};
        Map<String, String> claimParams = Map.of(
                "incomeMain", "ConfirmedIncomeForm12",
                "kpMain", "Comp_Type_Big_Macro",
                "kpClient", "null",
                "Code", "stub1");
        String document = "Паспорт";
        List<String> expectedTriggers = List.of(
                "Подпись в анкете и паспорте отличается",
                "Признаки фальсификации",
                "Отметка о рождении ребенка до 1,5 лет на момент обращения",
                "Регистрация в текущем регионе менее 1 года");
        String documentStep2 = "Выписка из ПФР";

        String claim = getClaim(testInfo, "1651046", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2)
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен")
                .clickButtonsForTrigger("Да", "Признаки подделки", documentStep2)
                .clickOnSign("Форма выписки соответствует электронной, но предоставлена на бумажном носителе")
                .clickOnElement("Кнопка Готово на модальном окне Детализация признаков")
                .clickButtonsForTrigger("Нет", "Признаки фальсификации", documentStep2)
                .clickButtonsForTrigger("Нет", "Запись о нетрудоспособности в ПФР", documentStep2)
                .clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен");
        TRIGGERS_ADDITIONAL_DOCS.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, "Выбор дополнительных документов"));
        checkTableVerification(claim, expectedValues);
    }

    @Test
    @Tag("l0_checking_documents_1645486")
    @DisplayName("1645486 - Результат проверки. Документ \"Выписка из ПФР \" для \"Признаки фальсификации\" - да")
    @WorkItemIds({"1645486"})
    public void strategy_l0_1645486(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "incomeMain", "ConfirmedIncomeForm12",
                "kpMain", "Comp_Type_OPK_Macro_War",
                "kpClient", "null",
                "Code", "stub1");
        String document = "Паспорт";
        List<String> expectedTriggers = List.of(
                "Подпись в анкете и паспорте отличается",
                "Признаки фальсификации",
                "Отметка о рождении ребенка до 1,5 лет на момент обращения");
        Map<String, List<String>> expectedDocsStep2 = Map.of(
                "Подтверждение дохода", List.of(
                        "2-НДФЛ",
                        "Выписка с з/п счета",
                        "Справка по форме банка/работодателя",
                        "3-НДФЛ",
                        "Выписка с з/п счета/2-НДФЛ",
                        "Выписка с з/п счета/Выписка из ПФР",
                        "2-НДФЛ/Выписка из ПФР",
                        "Справка по форме банка/Выписка с з/п счета/2-НДФЛ"),
                "Подтверждение трудоустройства", List.of(
                        "Трудовая книжка",
                        "Трудовой договор",
                        "Выписка из ПФР",
                        "Контракт о прохождении военной службы"),
                "Прочие документы", List.of(
                        "Удостоверение военнослужащего",
                        "Военный билет"),
                "Реестры", List.of(
                        "Проверка реестров"),
                "Дополнительные документы", List.of(
                        "Выбор дополнительных документов"),
                "Звание/должность", List.of(
                        "Выбор звания/должности"),
                "Автоматическое определение НИС", List.of(
                        "Автоматическое определение НИС"));
        String documentStep2 = "Выписка из ПФР";

        String claim = getClaim(testInfo, "1651046", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2, expectedDocsStep2)
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен")
                .clickButtonsForTrigger("Да", "Признаки фальсификации", documentStep2)
                .clickOnSign("Логическое несоответствие по ФИО (прежним ФИО)")
                .clickOnElement("Кнопка Готово на модальном окне Детализация признаков")
                .clickButtonsForTrigger("Нет", "Признаки подделки", documentStep2)
                .clickButtonsForTrigger("Нет", "Запись о нетрудоспособности в ПФР", documentStep2)
                .clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен");
        TRIGGERS_ADDITIONAL_DOCS.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, "Выбор дополнительных документов"));
        l0CheckingDocumentsPage
                .clickButtonsForTrigger("Нет", "Удостоверение силовика/военнослужащего/военный билет", "Выбор дополнительных документов")
                .clickButtonsForTrigger("Нет", "Регистрация клиента совпадает с адресом места работы/войсковой части (только для военнослужащих)", "Выбор дополнительных документов")
                .clickOnNotProvidedIconForDoc("Выбор звания/должности", "Документ не предоставлен")
                .selectValueFromDropDownList("Выпадающий список Выберите звание/должность", "Офицеры (с генерал-майора)")
                .checkElementByTitleEquals("Поле Доход завышен/не завышен", "Доход не завышен")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку");
        validateClaimStatus(claim);
    }

    @Test
    @Tag("l0_checking_documents_1645487")
    @DisplayName("1645487 - Результат проверки. Документ \"Выписка из ПФР \" для всех триггеров - нет")
    @WorkItemIds({"1645487"})
    public void strategy_l0_1645487(TestInfo testInfo) {
        String[][] expectedValues = {
                {"Наименование стратегии", "Статус", "Результат", "Ссылка"},
                {"L0.Проверка документов клиента", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"L0.Проверка документов работодателя", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"ФССП", "В работе", "", "Открыть стратегию"}};
        Map<String, String> claimParams = Map.of(
                "incomeMain", "ConfirmedIncomeForm12",
                "kpMain", "Comp_Rel_Corp",
                "kpClient", "null",
                "Code", "stub1");
        String document = "Паспорт";
        List<String> expectedTriggers = List.of(
                "Подпись в анкете и паспорте отличается",
                "Признаки фальсификации",
                "Отметка о рождении ребенка до 1,5 лет на момент обращения",
                "Регистрация в текущем регионе менее 1 года");
        String documentStep2 = "Выписка из ПФР";

        String claim = getClaim(testInfo, "1651046", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2)
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен")
                .clickButtonsForTrigger("Нет", "Признаки подделки", documentStep2)
                .clickButtonsForTrigger("Нет", "Признаки фальсификации", documentStep2)
                .clickButtonsForTrigger("Нет", "Запись о нетрудоспособности в ПФР", documentStep2)
                .clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен");
        TRIGGERS_ADDITIONAL_DOCS.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, "Выбор дополнительных документов"));
        checkTableVerification(claim, expectedValues);
    }
}
