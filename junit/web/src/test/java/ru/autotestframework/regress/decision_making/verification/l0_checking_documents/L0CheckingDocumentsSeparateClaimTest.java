package ru.autotestframework.regress.decision_making.verification.l0_checking_documents;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.ExternalId;
import ru.psb.testit.annotations.WorkItemIds;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.autotestframework.pages.card_request.verification.L0CheckingDocumentsPage.STEP_1;
import static ru.autotestframework.pages.card_request.verification.L0CheckingDocumentsPage.STEP_2;
import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;

@Tag("regress")
@Tag("decision_making")
@Tag("verification")
@Tag("l0_checking_documents")
@Tag("l0_separate_claim_1")
@ClassName("L0 Проверка документов. На каждый кейс отдельная заявка")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class L0CheckingDocumentsSeparateClaimTest extends L0CheckingDocumentsSteps {

    @Test
    @Tag("l0_checking_documents_1645457")
    @DisplayName("1645457 - Верификация. Стратегия «L0/Проверка документов» при проставлении чек-бокса «Да» для риска «Отметка о рождении ребенка до 1,5 лет на момент обращения»» дополнительные поля должны быть заполнены")
    @WorkItemIds({"1645457"})
    public void strategy_l0_1645457(TestInfo testInfo) {
        Map<String, List<String>> expectedValues = new LinkedHashMap<>();
        addElements(expectedValues, "Наименование стратегии",
                List.of("L0.Проверка документов клиента",
                        "L0.Проверка документов работодателя",
                        "ФССП"));
        addElements(expectedValues, "Статус", List.of("Завершен", "Завершен", "В работе"));
        addElements(expectedValues, "Результат", List.of("Далее по процессу", "Далее по процессу", ""));
        addElements(expectedValues, "Ссылка", List.of("Открыть стратегию", "Открыть стратегию", "Открыть стратегию"));
        Map<String, String> claimParams = Map.of(
                "incomeMain", "ConfirmedIncomeForm12",
                "kpMain", "Comp_Type_OPK_Other",
                "kpClient", "null",
                "Code", "stub1");
        String document = "ФОТО";
        List<String> expectedTriggers = List.of(
                "Признаки лица БОМЖ",
                "Фото клиента не соответствует фото в паспорте");
        String documentStep2 = "2-НДФЛ";
        List<String> expectedTriggersStep2 = List.of(
                "Доход по коду 2611",
                "Доход по коду 2013",
                "Доход по коду 2014",
                "Наличие сведений о ликвидации",
                "Признаки подделки",
                "Признаки фальсификации");

        String claim = getClaim(testInfo, "1651046", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        l0CheckingDocumentsPage
                .clickButtonsForTrigger("Нет", "Признаки лица БОМЖ", document)
                .clickButtonsForTrigger("Нет", "Фото клиента не соответствует фото в паспорте", document)
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2)
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен");
        expectedTriggersStep2.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, documentStep2));
        l0CheckingDocumentsPage.clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен");
        TRIGGERS_ADDITIONAL_DOCS.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, "Выбор дополнительных документов"));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab()
                .goTo(loginPage)
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim)
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .clickOnElement("Кнопка Основные данные").switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Результаты проверок")
                .goTo(resultCheck)
                .clickOnElement("Вкладка Ручные проверки")
                .goTo(manualChecks)
                .clickOnElement("Кнопка Верификация назначенные проверки");

        Map<String, List<String>> actualValues = manualChecks.getTableHeadersAndContent("Таблица Верификация назначенные проверки");
        assertIsTrue(expectedValues.equals(actualValues), "Актуальные " + actualValues.toString() + " и ожидаемые значения " + expectedValues + " в таблице \"Таблица Верификация назначенные проверки\" должны совпадать");
        manualChecks.closeCurrentTab().closeCurrentTab();
    }

    @Test
    @Tag("l0_checking_documents_1645458")
    @DisplayName("1645458 - Верификация. Стратегия «L0/Проверка документов» при проставлении чек-бокса «Да» для риска «Отметка о рождении ребенка до 1,5 лет на момент обращения»» дополнительные поля должны быть заполнены")
    @WorkItemIds({"1645458"})
    public void strategy_l0_1645458(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "incomeMain", "ConfirmedIncomeForm12",
                "kpMain", "Comp_Type_OPK_Other",
                "kpClient", "null",
                "Code", "stub1");
        String document = "ФОТО";
        List<String> expectedTriggers = List.of(
                "Признаки лица БОМЖ",
                "Фото клиента не соответствует фото в паспорте");
        String documentStep2 = "2-НДФЛ";
        List<String> expectedTriggersStep2 = List.of(
                "Доход по коду 2611",
                "Доход по коду 2013",
                "Доход по коду 2014",
                "Наличие сведений о ликвидации",
                "Признаки подделки",
                "Признаки фальсификации");

        String claim = getClaim(testInfo, "1651046", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        l0CheckingDocumentsPage
                .clickButtonsForTrigger("Да", "Признаки лица БОМЖ", document)
                .clickButtonsForTrigger("Да", "Фото клиента не соответствует фото в паспорте", document)
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2)
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен");
        expectedTriggersStep2.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, documentStep2));
        l0CheckingDocumentsPage.clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен");
        TRIGGERS_ADDITIONAL_DOCS.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, "Выбор дополнительных документов"));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку");
        validateClaimStatus(claim);
    }

    @Test
    @Tag("l0_checking_documents_1645459")
    @DisplayName("1645459 - Верификация.Стратегия «L0/Проверка документов» результат проверки. Документ  \"Паспорт\" для \"Подпись в анкете и паспорте отличается\" - да")
    @WorkItemIds({"1645459"})
    public void strategy_l0_1645459(TestInfo testInfo) {
        Map<String, List<String>> expectedValues = new LinkedHashMap<>();
        addElements(expectedValues, "Наименование стратегии",
                List.of("L0.Проверка документов клиента",
                        "L0.Проверка документов работодателя",
                        "ФССП",
                        "Прозвон клиента",
                        "Прозвон контактного лица/супруга (-и)"));
        addElements(expectedValues, "Статус", List.of("Завершен", "Завершен", "В работе", "Назначен", "Назначен"));
        addElements(expectedValues, "Результат", List.of("Далее по процессу", "Далее по процессу", "", "", ""));
        addElements(expectedValues, "Ссылка", List.of("Открыть стратегию", "Открыть стратегию", "Открыть стратегию", "", ""));
        Map<String, String> claimParams = Map.of(
                "incomeMain", "ConfirmedIncomeForm12",
                "kpMain", "Client_Salary",
                "kpClient", "null",
                "Code", "stub1");
        String document = "Паспорт";
        List<String> expectedTriggers = List.of(
                "Подпись в анкете и паспорте отличается",
                "Признаки фальсификации");
        String documentStep2 = "Выписка с з/п счета";
        List<String> expectedTriggersStep2 = List.of(
                "Признаки подделки",
                "Признаки фальсификации",
                "Зачисление \"Расчет при увольнении\"",
                "Отсутствует начисление за последние 30 дней",
                "Зачисление кредита");

        String claim = getClaim(testInfo, "1651046", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        l0CheckingDocumentsPage
                .clickButtonsForTrigger("Да", "Подпись в анкете и паспорте отличается", document)
                .clickButtonsForTrigger("Нет", "Признаки фальсификации", document)
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2)
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен");
        expectedTriggersStep2.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, documentStep2));
        l0CheckingDocumentsPage.clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен");
        TRIGGERS_ADDITIONAL_DOCS.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, "Выбор дополнительных документов"));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab()
                .goTo(loginPage)
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim)
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .clickOnElement("Кнопка Основные данные").switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Результаты проверок")
                .goTo(resultCheck)
                .clickOnElement("Вкладка Ручные проверки")
                .goTo(manualChecks)
                .clickOnElement("Кнопка Верификация назначенные проверки");

        Map<String, List<String>> actualValues = manualChecks.getTableHeadersAndContent("Таблица Верификация назначенные проверки");
        assertIsTrue(expectedValues.equals(actualValues), "Актуальные " + actualValues.toString() + " и ожидаемые значения " + expectedValues.toString() + " в таблице \"Таблица Верификация назначенные проверки\" должны совпадать");
        manualChecks.closeCurrentTab().closeCurrentTab();
    }

    @Test
    @Tag("l0_checking_documents_3207907")
    @DisplayName("3207907 - Верификация L0. Дополнительные документы- отображение Новые зарплатные клиенты")
    @WorkItemIds({"3207907"})
    public void strategy_l0_3207907(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "incomeMain", "NO1",
                "kpMainOne", "Client_Salary_Early_Spec",
                "kpMainTwo", "null",
                "kpClient", "null",
                "Code", "stub1",
                "Code1", "stub1",
                "Code2", "stub1");
        List<String> expectedTriggers = List.of(
                "Выписка из СФР с доходом",
                "Выписка из СФР без дохода",
                "ЭТК",
                "Выписка с з/п счета с ящика doc");
        Map<String, List<String>> expectedDocsStep2 = Map.of(
                "Подтверждение дохода", List.of("2-НДФЛ",
                        "Выписка с з/п счета",
                        "Справка по форме банка/работодателя",
                        "3-НДФЛ",
                        "Выписка с з/п счета/Выписка из ПФР",
                        "2-НДФЛ/Выписка из ПФР",
                        "Справка по форме банка/Выписка с з/п счета/2-НДФЛ"),
                "Подтверждение трудоустройства", List.of("Трудовая книжка",
                        "Трудовой договор",
                        "Выписка из ПФР",
                        "Контракт о прохождении военной службы"),
                "Прочие документы", List.of("Удостоверение военнослужащего",
                        "Военный билет"),
                "Дополнительные документы", List.of("Выбор дополнительных документов"));
        String document = "Выбор дополнительных документов";
        String claim = getClaim(testInfo, "2535622", claimParams);
        loginPage.openMenuLinks("Личный кабинет")
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(l0CheckingDocumentsPage)
                .checkDocsOnStep(STEP_1)
                .clickOnElement(STEP_2)
                .checkDocsOnStep(STEP_2, expectedDocsStep2)
                .clickOnElement("Кнопка Взять шаг в работу").waitBusyCondition()
                .colorElementEquals(STEP_2, "rgba(0, 167, 112, 1)")
                .clickOnNotProvidedIconForDoc(document, "Документ не предоставлен");
        List<String> actualTriggers = l0CheckingDocumentsPage.getActualListTriggersByDoc(document);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.checkRadioButtonsForTrigger(document, trigger));
        assertEquals(expectedTriggers, actualTriggers, "Список триггеров для документа " + document + " не совпадает с актуальным: " + actualTriggers);
        l0CheckingDocumentsPage.closeCurrentTab();
    }

    @Test
    @Tag("l0_checking_documents_3207910")
    @DisplayName("3207910 - Верификация L0. Дополнительные документы- отображение Частичный зарплатный клиент военнослужащий")
    @WorkItemIds({"3207910"})
    public void strategy_l0_3207910(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "incomeMain", "NO1",
                "kpMainOne", "Pos_War_NIS",
                "kpMainTwo", "null",
                "kpClient", "\"Client_Salary\"",
                "Code", "stub1",
                "Code1", "stub1",
                "Code2", "stub1");
        List<String> expectedTriggers = List.of(
                "Выписка из СФР с доходом",
                "Выписка из СФР без дохода",
                "ЭТК",
                "Удостоверение силовика/военнослужащего/военный билет",
                "Выписка с з/п счета с ящика doc",
                "Регистрация клиента совпадает с адресом места работы/войсковой части (только для военнослужащих)");
        Map<String, List<String>> expectedDocsStep2 = Map.of(
                "Подтверждение дохода", List.of("2-НДФЛ",
                        "Выписка с з/п счета",
                        "Справка по форме банка/работодателя",
                        "3-НДФЛ",
                        "Выписка с з/п счета/2-НДФЛ",
                        "Выписка с з/п счета/Выписка из ПФР",
                        "2-НДФЛ/Выписка из ПФР",
                        "Справка по форме банка/Выписка с з/п счета/2-НДФЛ"),
                "Подтверждение трудоустройства", List.of("Трудовая книжка",
                        "Трудовой договор",
                        "Выписка из ПФР",
                        "Контракт о прохождении военной службы"),
                "Прочие документы", List.of("Удостоверение военнослужащего",
                        "Военный билет"),
                "Реестры", List.of("Проверка реестров"),
                "Дополнительные документы", List.of("Выбор дополнительных документов"),
                "Звание/должность", List.of("Выбор звания/должности"),
                "Автоматическое определение НИС", List.of("Автоматическое определение НИС"));
        String document = "Выбор дополнительных документов";
        String claim = getClaim(testInfo, "2535622", claimParams);
        loginPage.openMenuLinks("Личный кабинет")
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(l0CheckingDocumentsPage)
                .checkDocsOnStep(STEP_1)
                .clickOnElement(STEP_2)
                .checkDocsOnStep(STEP_2, expectedDocsStep2)
                .clickOnElement("Кнопка Взять шаг в работу").waitBusyCondition()
                .colorElementEquals(STEP_2, "rgba(0, 167, 112, 1)")
                .clickOnNotProvidedIconForDoc(document, "Документ не предоставлен");
        List<String> actualTriggers = l0CheckingDocumentsPage.getActualListTriggersByDoc(document);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.checkRadioButtonsForTrigger(document, trigger));
        assertEquals(expectedTriggers, actualTriggers, "Список триггеров для документа " + document + " не совпадает с актуальным: " + actualTriggers);
        l0CheckingDocumentsPage.closeCurrentTab();
    }

    @Test
    @Tag("l0_checking_documents_1645460")
    @DisplayName("1645460 - Верификация. Стратегия «L0/Проверка документов»  результат проверки. Документ \"Паспорт\" для \"Подпись в анкете и паспорте отличается\" и \"Признаки фальсификации\" - да")
    @WorkItemIds({"1645460"})
    public void strategy_l0_1645460(TestInfo testInfo) {
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
        String documentStep2 = "Выписка с з/п счета";
        List<String> expectedTriggersStep2 = List.of(
                "Признаки подделки",
                "Признаки фальсификации",
                "Зачисление \"Расчет при увольнении\"",
                "Отсутствует начисление за последние 30 дней",
                "Зачисление кредита");

        String claim = getClaim(testInfo, "1651046", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        l0CheckingDocumentsPage
                .clickButtonsForTrigger("Да", "Подпись в анкете и паспорте отличается", document)
                .clickButtonsForTrigger("Да", "Признаки фальсификации", document)
                .clickOnSign("Отсутствие круглой печати")
                .clickOnElement("Кнопка Готово на модальном окне Детализация признаков")
                .clickButtonsForTrigger("Нет", "Отметка о рождении ребенка до 1,5 лет на момент обращения", document)
                .clickButtonsForTrigger("Нет", "Регистрация в текущем регионе менее 1 года", document)
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2)
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен");
        expectedTriggersStep2.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, documentStep2));
        l0CheckingDocumentsPage.clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен");
        TRIGGERS_ADDITIONAL_DOCS.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, "Выбор дополнительных документов"));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку");
        validateClaimStatus(claim);
    }

    @Test
    @Tag("l0_checking_documents_1645461")
    @DisplayName("1645461 - Верификация. Стратегия «L0/Проверка документов» результат проверки. Документ \"Справки, подтверждающие закрытие обязательств\" для \"Признаки фальсификации\" - да")
    @WorkItemIds({"1645461"})
    public void strategy_l0_1645461(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "incomeMain", "ConfirmedIncomeForm12",
                "kpMain", "Comp_Rel_Corp",
                "kpClient", "null",
                "Code", "stub1");
        String document = "Справки, подтверждающие закрытие обязательств";
        List<String> expectedTriggers = List.of("Признаки фальсификации");
        String documentStep2 = "Справка по форме банка/работодателя";
        List<String> expectedTriggersStep2 = List.of(
                "Признаки подделки",
                "Признаки фальсификации");

        String claim = getClaim(testInfo, "1651046", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        l0CheckingDocumentsPage
                .clickButtonsForTrigger("Да", "Признаки фальсификации", document)
                .clickOnSign("Наличие очевидных на копии документа признаков травления текста / подчисток / дописок / рисовок / вклейки бумаги / замены листов")
                .clickOnElement("Кнопка Готово на модальном окне Детализация признаков")
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2)
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен");
        expectedTriggersStep2.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, documentStep2));
        l0CheckingDocumentsPage.clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен");
        TRIGGERS_ADDITIONAL_DOCS.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, "Выбор дополнительных документов"));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку");
        validateClaimStatus(claim);
    }

    @Test
    @Tag("l0_checking_documents_1645454")
    @DisplayName("1645454 - Верификация. Стратегия «L0/Проверка документов» результат проверки для Определение итогового результата для результата «Отметка о рождении ребенка до 1,5 лет на момент обращения» отказ")
    @WorkItemIds({"1645454"})
    public void strategy_l0_1645454(TestInfo testInfo) {
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
        String documentStep2 = "2-НДФЛ";

        String claim = getClaim(testInfo, "1651046", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        l0CheckingDocumentsPage
                .clickButtonsForTrigger("Да", "Отметка о рождении ребенка до 1,5 лет на момент обращения", document)
                .clickButtonsForAddField("Нет", "Доход менялся в период рождения ребенка", "Отметка о рождении ребенка до 1,5 лет на момент обращения")
                .clickButtonsForAddField("Нет", "Должность предполагает работу на дому", "Отметка о рождении ребенка до 1,5 лет на момент обращения")
                .clickButtonsForTrigger("Нет", "Признаки фальсификации", document)
                .clickButtonsForTrigger("Нет", "Подпись в анкете и паспорте отличается", document)
                .clickButtonsForTrigger("Нет", "Регистрация в текущем регионе менее 1 года", document)
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
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку");
        validateClaimStatus(claim);
    }

    @Test
    @Tag("l0_checking_documents_1645455")
    @DisplayName("1645455 -  Верификация. Стратегия «L0/Проверка документов» запись о нетрудоспособности в ПФР» -  отказ")
    @WorkItemIds({"1645455"})
    public void strategy_l0_1645455(TestInfo testInfo) {
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
        String documentStep2 = "Выписка из ПФР";

        String claim = getClaim(testInfo, "1651046", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2)
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен")
                .clickButtonsForTrigger("Да", "Запись о нетрудоспособности в ПФР", documentStep2)
                .clickButtonsForAddField("Нет", "Должность предполагает работу на дому", "Запись о нетрудоспособности в ПФР")
                .clickButtonsForTrigger("Нет", "Признаки подделки", documentStep2)
                .clickButtonsForTrigger("Нет", "Признаки фальсификации", documentStep2)
                .clickOnNotProvidedIconForDoc("Выписка с з/п счета", "Документ не предоставлен")
                .clickButtonsForTrigger("Да", "Признаки подделки", "Выписка с з/п счета")
                .clickOnSign("Выписка Сбербанка - форма выписки соответствует электронной, но предоставлена на бумажном носителе")
                .clickOnElement("Кнопка Готово на модальном окне Детализация признаков")
                .clickButtonsForTrigger("Нет", "Признаки фальсификации", "Выписка с з/п счета")
                .clickButtonsForTrigger("Нет", "Зачисление \"Расчет при увольнении\"", "Выписка с з/п счета")
                .clickButtonsForTrigger("Нет", "Отсутствует начисление за последние 30 дней", "Выписка с з/п счета")
                .clickButtonsForTrigger("Нет", "Зачисление кредита", "Выписка с з/п счета")
                .clickOnNotProvidedIconForDoc("2-НДФЛ/Выписка из ПФР", "Документ не предоставлен")
                .clickButtonsForTrigger("Да", "Сумма з/п в выписке из ПФР отличается более чем на 30% от 2-НДФЛ", "2-НДФЛ/Выписка из ПФР")
                .clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен");
        TRIGGERS_ADDITIONAL_DOCS.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, "Выбор дополнительных документов"));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку");
        validateClaimStatus(claim);
    }

    @Test
    @Tag("l0_checking_documents_1645493")
    @DisplayName("1645493 - Результат проверки. Документ \"Выписка с з/п счета/Выписка из ПФР\" для \"Сумма з/п в выписке из ПФР отличается более чем на 30% от выписки с з/п счета\" - да")
    @WorkItemIds({"1645493"})
    public void strategy_l0_1645493(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "incomeMain", "ConfirmedIncomeForm12",
                "kpMain", "Client_Salary",
                "kpClient", "null",
                "Code", "stub1");
        String document = "Паспорт";
        List<String> expectedTriggers = List.of(
                "Подпись в анкете и паспорте отличается",
                "Признаки фальсификации");
        String documentStep2 = "Выписка с з/п счета/Выписка из ПФР";

        String claim = getClaim(testInfo, "1651046", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2)
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен")
                .clickButtonsForTrigger("Да", "Сумма з/п в выписке из ПФР отличается более чем на 30% от выписки с з/п счета", documentStep2)
                .clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен");
        TRIGGERS_ADDITIONAL_DOCS.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, "Выбор дополнительных документов"));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку");
        validateClaimStatus(claim);
    }

    @Test
    @Tag("l0_checking_documents_1645498")
    @DisplayName("1645498 - Результат проверки. Документ \"Контракт о прохождении военной службы\" для всех триггеров - нет")
    @WorkItemIds({"1645498"})
    public void strategy_l0_1645498(TestInfo testInfo) {
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
        String documentStep2 = "Контракт о прохождении военной службы";
        List<String> expectedTriggersStep2 = List.of("Признаки фальсификации");

        String claim = getClaim(testInfo, "1651046", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2)
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен")
                .clickButtonsForTrigger("Нет", expectedTriggersStep2.get(0), documentStep2)
                .clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен");
        TRIGGERS_ADDITIONAL_DOCS.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, "Выбор дополнительных документов"));
        checkTableVerification(claim, expectedValues);
    }

    @Test
    @Tag("l0_checking_documents_1645462")
    @DisplayName("1645462 - Верификация. Стратегия «L0/Проверка документов» результат проверки. Документ \"2-НДФЛ\" для \"Наличие сведений о ликвидации \" - да")
    @WorkItemIds({"1645462"})
    public void strategy_l0_1645462(TestInfo testInfo) {
        String[][] expectedValues = {
                {"Наименование стратегии", "Статус", "Результат", "Ссылка"},
                {"L0.Проверка документов клиента", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"L0.Проверка документов работодателя", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"ФССП", "В работе", "", "Открыть стратегию"},
                {"Открытые источники - проверка работодателя", "Назначен", "", ""},
                {"Открытые источники - привязка телефона из анкеты", "Назначен", "", ""},
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
        String documentStep2 = "2-НДФЛ";

        String claim = getClaim(testInfo, "1651046", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2)
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен")
                .clickButtonsForTrigger("Да", "Наличие сведений о ликвидации", documentStep2)
                .clickButtonsForTrigger("Нет", "Доход по коду 2611", documentStep2)
                .clickButtonsForTrigger("Нет", "Доход по коду 2013", documentStep2)
                .clickButtonsForTrigger("Нет", "Доход по коду 2014", documentStep2)
                .clickButtonsForTrigger("Нет", "Признаки подделки", documentStep2)
                .clickButtonsForTrigger("Нет", "Признаки фальсификации", documentStep2)
                .clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен");
        TRIGGERS_ADDITIONAL_DOCS.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, "Выбор дополнительных документов"));
        checkTableVerification(claim, expectedValues);
    }

    @ParameterizedTest
    @CsvSource({
            "3207928, ЭТК- Да + \"Сумма з/п в выписке из ПФР отличается более чем на 30% от 2-НДФЛ\" (ОПК прочие), ConfirmedIncomeForm12, 2-НДФЛ/Выписка из ПФР, Сумма з/п в выписке из ПФР отличается более чем на 30% от 2-НДФЛ",
            "3207936, ЭТК - Да + \"Сумма з/п в выписке из ПФР отличается более чем на 30% от выписки с з/п счета\" (ОПК прочие), NO1, Выписка с з/п счета/Выписка из ПФР, Сумма з/п в выписке из ПФР отличается более чем на 30% от выписки с з/п счета"})
    @Tag("l0_checking_documents_3207928")
    @DisplayName("{id} - Верификация L0. Выбор дополнительных документов: {displayName}")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void strategy_l0_3207928_3207936(String id, String displayName, String incomeMain, String documentStep2, String triggerStep2, TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "incomeMain", incomeMain,
                "kpMainOne", "Comp_Type_TOP_OPK",
                "kpMainTwo", "null",
                "kpClient", "null",
                "Code", "stub13",
                "Code1", "stub8",
                "Code2", "stub14");
        String document = "Паспорт";
        List<String> expectedTriggers = List.of(
                "Подпись в анкете и паспорте отличается",
                "Признаки фальсификации",
                "Отметка о рождении ребенка до 1,5 лет на момент обращения",
                "Регистрация в текущем регионе менее 1 года");

        String claim = getClaim(testInfo, "2535622", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2)
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен")
                .clickButtonsForTrigger("Да", triggerStep2, documentStep2)
                .clickOnNotProvidedIconForDoc("Выбор дополнительных документов", "Документ не предоставлен")
                .clickButtonsForTrigger("Да", "ЭТК", "Выбор дополнительных документов")
                .clickButtonsForTrigger("Нет", "Выписка из СФР с доходом", "Выбор дополнительных документов")
                .clickButtonsForTrigger("Нет", "Выписка из СФР без дохода", "Выбор дополнительных документов")
                .clickButtonsForTrigger("Нет", "Выписка с з/п счета с ящика doc", "Выбор дополнительных документов")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку");
        validateClaimStatus(claim);
    }

    @Test
    @Tag("l0_checking_documents_1645497")
    @DisplayName("1645497 - Результат проверки. Документ \"Контракт о прохождении военной службы\" для \"Признаки фальсификации\" - да")
    @WorkItemIds({"1645497"})
    public void strategy_l0_1645497(TestInfo testInfo) {
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
        String documentStep2 = "Контракт о прохождении военной службы";

        String claim = getClaim(testInfo, "1651046", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2, expectedDocsStep2)
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен")
                .clickButtonsForTrigger("Да", "Признаки фальсификации", documentStep2)
                .clickOnSign("Первый контракт о прохождении заключен на срок более 5 лет")
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
}
