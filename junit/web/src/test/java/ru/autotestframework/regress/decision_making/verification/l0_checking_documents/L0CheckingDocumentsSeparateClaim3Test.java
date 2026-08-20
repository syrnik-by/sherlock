package ru.autotestframework.regress.decision_making.verification.l0_checking_documents;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.ExternalId;
import ru.psb.testit.annotations.WorkItemIds;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;
import static ru.autotestframework.pages.card_request.verification.L0CheckingDocumentsPage.STEP_2;

@Tag("regress")
@Tag("decision_making")
@Tag("verification")
@Tag("l0_checking_documents")
@Tag("l0_separate_claim_3")
@ClassName("L0 Проверка документов. На каждый кейс отдельная заявка")
public class L0CheckingDocumentsSeparateClaim3Test extends L0CheckingDocumentsSteps {

    @Test
    @Tag("smoke")
    @Tag("l0_checking_documents_1645488")
    @DisplayName("1645488 - Результат проверки. Документ \"Трудовой договор\" для \"Признаки подделки\" - да")
    @WorkItemIds({"1645488"})
    public void strategy_l0_1645488(TestInfo testInfo) {
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
        String documentStep2 = "Трудовой договор";

        String claim = getClaim(testInfo, "1651046", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2)
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен")
                .clickButtonsForTrigger("Нет", "Признаки фальсификации", documentStep2)
                .clickButtonsForTrigger("Да", "Признаки подделки", documentStep2)
                .clickOnSign("Рабочий график ограничен 40 часами в неделю")
                .clickOnElement("Кнопка Готово на модальном окне Детализация признаков")
                .clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен");
        TRIGGERS_ADDITIONAL_DOCS.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, "Выбор дополнительных документов"));
        checkTableVerification(claim, expectedValues);
    }

    @Test
    @Tag("l0_checking_documents_1645489")
    @DisplayName("1645489 - Результат проверки. Документ \"Трудовой договор\" для \"Признаки фальсификации\" - да")
    @WorkItemIds({"1645489"})
    public void strategy_l0_1645489(TestInfo testInfo) {
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
        String documentStep2 = "Трудовой договор";

        String claim = getClaim(testInfo, "1651046", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2, expectedDocsStep2)
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен")
                .clickButtonsForTrigger("Да", "Признаки фальсификации", documentStep2)
                .clickOnSign("В договоре указаны паспортные данные документа клиента, полученного после даты заключения ТД")
                .clickOnElement("Кнопка Готово на модальном окне Детализация признаков")
                .clickButtonsForTrigger("Нет", "Признаки подделки", documentStep2)
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
    @Tag("l0_checking_documents_1645490")
    @DisplayName("1645490 - Результат проверки. Документ \"Трудовой договор\" для всех триггеров - нет")
    @WorkItemIds({"1645490"})
    public void strategy_l0_1645490(TestInfo testInfo) {
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
        String documentStep2 = "Трудовой договор";

        String claim = getClaim(testInfo, "1651046", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2)
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен")
                .clickButtonsForTrigger("Нет", "Признаки фальсификации", documentStep2)
                .clickButtonsForTrigger("Нет", "Признаки подделки", documentStep2)
                .clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен");
        TRIGGERS_ADDITIONAL_DOCS.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, "Выбор дополнительных документов"));
        checkTableVerification(claim, expectedValues);
    }

    @Test
    @Tag("l0_checking_documents_1645491")
    @DisplayName("1645491 - Результат проверки. Документ \"Выписка с з/п счета/2-НДФЛ\" для \"Сумма з/п в выписке стороннего банка отличается более чем на 30% от 2-НДФЛ\" - да (Военнослужащие)")
    @WorkItemIds({"1645491"})
    public void strategy_l0_1645491(TestInfo testInfo) {
        String[][] expectedValues = {
                {"Наименование стратегии", "Статус", "Результат", "Ссылка"},
                {"L0.Проверка документов клиента", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"L0.Проверка документов работодателя", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"ФССП", "В работе", "", "Открыть стратегию"},
                {"Проверка предыдущих заявок", "Назначен", "", ""},
                {"Прозвон клиента", "Назначен", "", ""}};
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
        String documentStep2 = "Выписка с з/п счета/2-НДФЛ";
        List<String> expectedTriggersAdditionalDocs = List.of(
                "Выписка из СФР с доходом",
                "Выписка из СФР без дохода",
                "ЭТК",
                "Удостоверение силовика/военнослужащего/военный билет",
                "Выписка с з/п счета с ящика doc",
                "Регистрация клиента совпадает с адресом места работы/войсковой части (только для военнослужащих)");

        String claim = getClaim(testInfo, "1651046", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2, expectedDocsStep2)
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен")
                .clickButtonsForTrigger("Да", "Сумма з/п в выписке стороннего банка отличается более чем на 30% от 2-НДФЛ", documentStep2)
                .clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен");
        expectedTriggersAdditionalDocs.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, "Выбор дополнительных документов"));
        l0CheckingDocumentsPage
                .clickOnNotProvidedIconForDoc("Выбор звания/должности", "Документ не предоставлен")
                .selectValueFromDropDownList("Выпадающий список Выберите звание/должность", "Офицеры (с генерал-майора)")
                .checkElementByTitleEquals("Поле Доход завышен/не завышен", "Доход не завышен");
        checkTableVerification(claim, expectedValues);
    }

    @Test
    @Tag("l0_checking_documents_1645492")
    @DisplayName("1645492 - Результат проверки. Документ \"Выписка с з/п счета/2-НДФЛ\" для \"Сумма з/п в выписке стороннего банка отличается более чем на 30% от 2-НДФЛ\" - нет")
    @WorkItemIds({"1645492"})
    public void strategy_l0_1645492(TestInfo testInfo) {
        String[][] expectedValues = {
                {"Наименование стратегии", "Статус", "Результат", "Ссылка"},
                {"L0.Проверка документов клиента", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"L0.Проверка документов работодателя", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"ФССП", "В работе", "", "Открыть стратегию"},
                {"Проверка предыдущих заявок", "Назначен", "", ""},
                {"Проверка дохода", "Назначен", "", ""},
                {"Открытые источники - проверка работодателя", "Назначен", "", ""},
                {"Открытые источники - привязка телефона из анкеты", "Назначен", "", ""},
                {"Открытые источники - бесконтактное подтверждение трудоустройства", "Назначен", "", ""},
                {"Открытые источники - брокерские услуги", "Назначен", "", ""},
                {"Прозвон клиента", "Назначен", "", ""}};
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
        String documentStep2 = "Выписка с з/п счета/2-НДФЛ";

        String claim = getClaim(testInfo, "1651046", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2)
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен")
                .clickButtonsForTrigger("Да", "Сумма з/п в выписке стороннего банка отличается более чем на 30% от 2-НДФЛ", documentStep2)
                .clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен");
        TRIGGERS_ADDITIONAL_DOCS.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, "Выбор дополнительных документов"));
        checkTableVerification(claim, expectedValues);
    }

    @Test
    @Tag("l0_checking_documents_1645494")
    @DisplayName("1645494 - Результат проверки. Документ 2-НДФЛ/Выписка из ПФР\" для \"Сумма з/п в выписке из ПФР отличается более чем на 30% от 2-НДФЛ\" - нет")
    @WorkItemIds({"1645494"})
    public void strategy_l0_1645494(TestInfo testInfo) {
        String[][] expectedValues = {
                {"Наименование стратегии", "Статус", "Результат", "Ссылка"},
                {"L0.Проверка документов клиента", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"L0.Проверка документов работодателя", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"ФССП", "В работе", "", "Открыть стратегию"}};
        Map<String, String> claimParams = Map.of(
                "incomeMain", "ConfirmedIncomeForm12",
                "kpMain", "Client_Salary",
                "kpClient", "null",
                "Code", "stub1");
        String document = "Паспорт";
        List<String> expectedTriggers = List.of(
                "Подпись в анкете и паспорте отличается",
                "Признаки фальсификации");
        String documentStep2 = "2-НДФЛ/Выписка из ПФР";

        String claim = getClaim(testInfo, "1651046", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2)
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен")
                .clickButtonsForTrigger("Нет", "Сумма з/п в выписке из ПФР отличается более чем на 30% от 2-НДФЛ", documentStep2)
                .clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен");
        TRIGGERS_ADDITIONAL_DOCS.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, "Выбор дополнительных документов"));
        checkTableVerification(claim, expectedValues);
    }

    @Test
    @Tag("l0_checking_documents_1645495")
    @DisplayName("1645495 - Результат проверки. Документ \"Справка по форме банка/Выписка с з/п счета/2-НДФЛ\" для \"Снижение дохода в последних месяцах\" - да (Частичные зарплатные клиенты)")
    @WorkItemIds({"1645495"})
    public void strategy_l0_1645495(TestInfo testInfo) {
        String[][] expectedValues = {
                {"Наименование стратегии", "Статус", "Результат", "Ссылка"},
                {"L0.Проверка документов клиента", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"L0.Проверка документов работодателя", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"ФССП", "В работе", "", "Открыть стратегию"},
                {"Проверка предыдущих заявок", "Назначен", "", ""},
                {"Проверка дохода", "Назначен", "", ""},
                {"Открытые источники - проверка работодателя", "Назначен", "", ""},
                {"Прозвон клиента", "Назначен", "", ""},
                {"Прозвон работодателя - любой телефон", "Назначен", "", ""}};
        Map<String, String> claimParams = Map.of(
                "incomeMain", "ConfirmedIncomeForm12",
                "kpMain", "Client_Salary",
                "kpClient", "null",
                "Code", "stub1");
        String document = "Паспорт";
        List<String> expectedTriggers = List.of(
                "Подпись в анкете и паспорте отличается",
                "Признаки фальсификации");
        String documentStep2 = "Справка по форме банка/Выписка с з/п счета/2-НДФЛ";

        String claim = getClaim(testInfo, "1651046", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2)
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен")
                .clickButtonsForTrigger("Да", "Снижение дохода в последних месяцах", documentStep2)
                .clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен");
        TRIGGERS_ADDITIONAL_DOCS.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, "Выбор дополнительных документов"));
        checkTableVerification(claim, expectedValues);
    }

    @Test
    @Tag("l0_checking_documents_1645496")
    @DisplayName("1645496 - Результат проверки. Документ \"Справка по форме банка/Выписка с з/п счета/2-НДФЛ\" для \"Снижение дохода в последних месяцах\" - нет")
    @WorkItemIds({"1645496"})
    public void strategy_l0_1645496(TestInfo testInfo) {
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
        String documentStep2 = "Справка по форме банка/Выписка с з/п счета/2-НДФЛ";
        List<String> expectedTriggersAdditionalDocs = List.of(
                "Выписка из СФР с доходом",
                "Выписка из СФР без дохода",
                "ЭТК",
                "Удостоверение силовика/военнослужащего/военный билет",
                "Выписка с з/п счета с ящика doc",
                "Регистрация клиента совпадает с адресом места работы/войсковой части (только для военнослужащих)");

        String claim = getClaim(testInfo, "1651046", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2, expectedDocsStep2)
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен")
                .clickButtonsForTrigger("Нет", "Снижение дохода в последних месяцах", documentStep2)
                .clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен");
        expectedTriggersAdditionalDocs.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, "Выбор дополнительных документов"));
        l0CheckingDocumentsPage
                .clickOnNotProvidedIconForDoc("Выбор звания/должности", "Документ не предоставлен")
                .selectValueFromDropDownList("Выпадающий список Выберите звание/должность", "Офицеры (с генерал-майора)")
                .checkElementByTitleEquals("Поле Доход завышен/не завышен", "Доход не завышен");
        checkTableVerification(claim, expectedValues);
    }

    @ParameterizedTest
    @MethodSource("provideArguments")
    @Tag("l0_verification_documents_select_additional_docs")
    @DisplayName("{id} - Верификация L0. Выбор дополнительных документов: {displayName}")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void l0_verification_documents_select_additional_docs(String id, String displayName, Map<String, String> claimParams, List<String> expectedTriggers,
                                                                 List<String> expectedSteps, String additionalDoc, String rank, String income, TestInfo testInfo) {
        String document = "Паспорт";
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
        List<String> expectedTriggersAdditionalDocs = List.of(
                "Выписка из СФР с доходом",
                "Выписка из СФР без дохода",
                "ЭТК",
                "Удостоверение силовика/военнослужащего/военный билет",
                "Выписка с з/п счета с ящика doc",
                "Регистрация клиента совпадает с адресом места работы/войсковой части (только для военнослужащих)");
        String claim = getClaim(testInfo, "2535622", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2, expectedDocsStep2);
        handleTriggers(expectedTriggersAdditionalDocs, "Выбор дополнительных документов", additionalDoc);
        l0CheckingDocumentsPage.clickOnNotProvidedIconForDoc("Выбор звания/должности", "Документ не предоставлен")
                .selectValueFromDropDownList("Выпадающий список Выберите звание/должность", rank)
                .checkElementByTitleEquals("Поле Доход завышен/не завышен", income);
        checkSteps(claim, "Прозвон клиента/Версия 1", expectedSteps, customerCallPage);
    }

    private static Stream<Arguments> provideArguments() {
        return Stream.of(
                arguments("3207923", "Удостоверение силовика/военнослужащего/военный билет - Да (Военнослужащие/силовики)",
                        Map.of(
                                "incomeMain", "NO1",
                                "kpMainOne", "Comp_Type_OPK_Macro_War",
                                "kpMainTwo", "null",
                                "kpClient", "null",
                                "Code", "stub11",
                                "Code1", "stub13",
                                "Code2", "stub14"),
                        List.of("Подпись в анкете и паспорте отличается",
                                "Признаки фальсификации",
                                "Отметка о рождении ребенка до 1,5 лет на момент обращения"),
                        List.of("Прозвон клиента"), "Удостоверение силовика/военнослужащего/военный билет", "Офицеры (с генерал-майора)", "Доход не завышен"),
                arguments("3207909", "Удостоверение силовика/военнослужащего/военный билет- Да + Доход завышен(Военнослужащие/силовики)",
                        Map.of(
                                "incomeMain", "NO1",
                                "kpMainOne", "Comp_Type_OPK_Macro_War",
                                "kpMainTwo", "null",
                                "kpClient", "null",
                                "Code", "stub11",
                                "Code1", "stub13",
                                "Code2", "stub14"),
                        List.of("Подпись в анкете и паспорте отличается",
                                "Признаки фальсификации",
                                "Отметка о рождении ребенка до 1,5 лет на момент обращения"),
                        List.of("Прозвон клиента", "Прозвон работодателя - подтвержденный телефон"), "Удостоверение силовика/военнослужащего/военный билет", "Курсанты 1-го курса", "Доход завышен"),
                arguments("3207906", "Удостоверение силовика/военнослужащего/военный билет- Да (Частичный зарплатный клиент военнослужащий)",
                        Map.of(
                                "incomeMain", "NO1",
                                "kpMainOne", "Pos_War_NIS",
                                "kpMainTwo", "null",
                                "kpClient", "\"Client_Salary\"",
                                "Code", "stub11",
                                "Code1", "stub13",
                                "Code2", "stub14"),
                        List.of("Подпись в анкете и паспорте отличается",
                                "Признаки фальсификации"),
                        List.of("Прозвон клиента"), "Удостоверение силовика/военнослужащего/военный билет", "Офицеры (с генерал-майора)", "Доход не завышен"),
                arguments("3207921", "Выписка из СФР с доходом - Да (Военнослужащие/силовики)",
                        Map.of(
                                "incomeMain", "NO1",
                                "kpMainOne", "Comp_Type_OPK_Macro_War",
                                "kpMainTwo", "null",
                                "kpClient", "null",
                                "Code", "stub8",
                                "Code1", "stub11",
                                "Code2", "stub14"),
                        List.of("Подпись в анкете и паспорте отличается",
                                "Признаки фальсификации",
                                "Отметка о рождении ребенка до 1,5 лет на момент обращения"),
                        List.of("Прозвон клиента"), "Выписка из СФР с доходом", "Курсанты 2-го курса", "Доход не завышен"),
                arguments("3207911", "ЭТК- Да (Военнослужащие/силовики)",
                        Map.of(
                                "incomeMain", "NO1",
                                "kpMainOne", "Comp_Type_OPK_Macro_War",
                                "kpMainTwo", "null",
                                "kpClient", "null",
                                "Code", "stub11",
                                "Code1", "stub8",
                                "Code2", "stub14"),
                        List.of("Подпись в анкете и паспорте отличается",
                                "Признаки фальсификации",
                                "Отметка о рождении ребенка до 1,5 лет на момент обращения"),
                        List.of("Прозвон клиента"), "ЭТК", "Офицеры (с генерал-майора)", "Доход не завышен"),
                arguments("3207931", "ЭТК - Да + Доход  завышен(Частичный зарплатный клиент военнослужащий) ",
                        Map.of(
                                "incomeMain", "NO1",
                                "kpMainOne", "Comp_Type_OPK_Siloviki_OKOGU",
                                "kpMainTwo", "null",
                                "kpClient", "\"Client_Salary\"",
                                "Code", "stub11",
                                "Code1", "stub13",
                                "Code2", "stub14"),
                        List.of("Подпись в анкете и паспорте отличается",
                                "Признаки фальсификации"),
                        List.of("Прозвон клиента", "Прозвон работодателя - подтвержденный телефон"), "ЭТК", "Курсанты 1-го курса", "Доход завышен"),
                arguments("3207913", "Выписка с з/п счета с ящика doc- Да (Военнослужащие/силовики)",
                        Map.of(
                                "incomeMain", "NO1",
                                "kpMainOne", "Comp_Type_OPK_Macro_War",
                                "kpMainTwo", "null",
                                "kpClient", "null",
                                "Code", "stub11",
                                "Code1", "stub13",
                                "Code2", "stub14"),
                        List.of("Подпись в анкете и паспорте отличается",
                                "Признаки фальсификации",
                                "Отметка о рождении ребенка до 1,5 лет на момент обращения"),
                        List.of("Прозвон клиента"), "Выписка с з/п счета с ящика doc", "Офицеры (с генерал-майора)", "Доход не завышен"),
                arguments("3207917", "Регистрация клиента совпадает с адресом места работы/войсковой части (только для военнослужащих)- Да (Военнослужащие/силовики)",
                        Map.of(
                                "incomeMain", "NO1",
                                "kpMainOne", "Comp_Type_OPK_Macro_War",
                                "kpMainTwo", "null",
                                "kpClient", "null",
                                "Code", "stub11",
                                "Code1", "stub13",
                                "Code2", "stub14"),
                        List.of("Подпись в анкете и паспорте отличается",
                                "Признаки фальсификации",
                                "Отметка о рождении ребенка до 1,5 лет на момент обращения"),
                        List.of("Прозвон клиента"), "Регистрация клиента совпадает с адресом места работы/войсковой части (только для военнослужащих)", "Офицеры (с генерал-майора)", "Доход не завышен"),
                arguments("3207935", "Регистрация клиента совпадает с адресом места работы/войсковой части (только для военнослужащих) - Да + Доход завышен(Частичный зарплатный клиент военнослужащий) ",
                        Map.of(
                                "incomeMain", "NO1",
                                "kpMainOne", "Comp_Type_OPK_Siloviki_OKOGU",
                                "kpMainTwo", "null",
                                "kpClient", "\"Client_Salary\"",
                                "Code", "stub11",
                                "Code1", "stub13",
                                "Code2", "stub14"),
                        List.of("Подпись в анкете и паспорте отличается",
                                "Признаки фальсификации"),
                        List.of("Прозвон клиента", "Прозвон работодателя - подтвержденный телефон"), "Регистрация клиента совпадает с адресом места работы/войсковой части (только для военнослужащих)", "Курсанты 1-го курса", "Доход завышен"),
                arguments("3207932", "Выписка из СФР без дохода - Да + Доход завышен(Частичный зарплатный клиент военнослужащий)",
                        Map.of(
                                "incomeMain", "NO1",
                                "kpMainOne", "Comp_Type_OPK_Siloviki_OKOGU",
                                "kpMainTwo", "null",
                                "kpClient", "\"Client_Salary\"",
                                "Code", "stub11",
                                "Code1", "stub13",
                                "Code2", "stub14"),
                        List.of("Подпись в анкете и паспорте отличается",
                                "Признаки фальсификации"),
                        List.of("Прозвон клиента", "Прозвон работодателя - подтвержденный телефон"), "Выписка из СФР без дохода", "Курсанты 1-го курса", "Доход завышен"),
                arguments("3207933", "Выписка из СФР без дохода - Да (Военнослужащие/силовики)",
                        Map.of(
                                "incomeMain", "NO1",
                                "kpMainOne", "Comp_Type_OPK_Macro_War",
                                "kpMainTwo", "null",
                                "kpClient", "null",
                                "Code", "stub11",
                                "Code1", "stub8",
                                "Code2", "stub14"),
                        List.of("Подпись в анкете и паспорте отличается",
                                "Признаки фальсификации",
                                "Отметка о рождении ребенка до 1,5 лет на момент обращения"),
                        List.of("Прозвон клиента"), "Выписка из СФР без дохода", "Офицеры (с генерал-майора)", "Доход не завышен"));
    }
}
