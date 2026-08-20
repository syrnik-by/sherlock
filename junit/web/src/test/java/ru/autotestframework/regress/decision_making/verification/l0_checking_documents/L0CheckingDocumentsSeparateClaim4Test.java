package ru.autotestframework.regress.decision_making.verification.l0_checking_documents;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.ExternalId;
import ru.psb.testit.annotations.WorkItemIds;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.autotestframework.pages.card_request.verification.L0CheckingDocumentsPage.STEP_2;
import static ru.autotestframework.steps.asserts.Asserts.assertIsEquals;
import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;

@Tag("regress")
@Tag("decision_making")
@Tag("verification")
@Tag("l0_checking_documents")
@Tag("l0_separate_claim_4")
@ClassName("L0 Проверка документов. На каждый кейс отдельная заявка")
public class L0CheckingDocumentsSeparateClaim4Test extends L0CheckingDocumentsSteps {

    @Test
    @Tag("l0_verification_3207938")
    @DisplayName("3207938 - Верификация L0. Выбор дополнительных документов:ЭТК - Да + \"Компания в реестре «Сокращения»\" (Госслужащие) ")
    @WorkItemIds({"3207938"})
    public void l0_verification_3207938(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "incomeMain", "NO1",
                "kpMainOne", "Comp_Type_Public_Servant_WSOpen",
                "kpMainTwo", "null",
                "kpClient", "null",
                "Code", "stub13",
                "Code1", "stub8",
                "Code2", "stub16");

        String document = "Паспорт";
        List<String> expectedTriggers = List.of(
                "Подпись в анкете и паспорте отличается",
                "Признаки фальсификации",
                "Отметка о рождении ребенка до 1,5 лет на момент обращения",
                "Регистрация в текущем регионе менее 1 года");
        String documentStep2 = "Проверка реестров";

        String claim = getClaim(testInfo, "2535622", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2);
        handleTriggers(TRIGGERS_ADDITIONAL_DOCS, "Выбор дополнительных документов", "ЭТК");
        l0CheckingDocumentsPage
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен")
                .clickButtonsForTrigger("Да", "Компания в реестре «Сокращения»", documentStep2)
                .clickButtonsForTrigger("Нет", "Выявлен негатив в реестре «Подозрения на фрод»", documentStep2)
                .fillInput("Поле ввода Внутренний комментарий", "коммент")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab()
                .goTo(loginPage)
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim);
        String strategyClaim = queuesPage.getTextFromTable("Таблица результаты поиска", 1, "Стратегия");
        assertIsTrue(strategyClaim.equals("Проверка открытых источников"),
                "Значение в столбце Статус заявки должно быть равно Проверка откртых источников. Фактическое значение: " + strategyClaim);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.openMenuLinks("Личный кабинет")
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(checkingOpenSourcesPage)
                .selectValueFromDropDownList("Выпадающий список Привязка телефона из анкеты", "Телефон привязан")
                .assertElementByTitleVisibility("Поле ввода Источник подтверждения", "отображается")
                .fillInput("Поле ввода Источник подтверждения", "Сайт");
        checkSteps(claim, "Прозвон работодателя - подтвержденный телефон (обязательный)/Версия 1", List.of("Прозвон работодателя - подтвержденный телефон (обязательный)"), checkingOpenSourcesPage);
    }

    @Test
    @Tag("l0_verification_3207914")
    @DisplayName("3207914 - Верификация L0. Выбор дополнительных документов:ЭТК- Да + \"Доход не завышен\" (ОПК прочие)")
    @WorkItemIds({"3207914"})
    public void l0_verification_3207914(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "incomeMain", "NO1",
                "kpMainOne", "Comp_Type_TOP_OPK",
                "kpMainTwo", "null",
                "kpClient", "null",
                "Code", "stub5",
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
                .checkDocsOnStep(STEP_2);
        handleTriggers(TRIGGERS_ADDITIONAL_DOCS, "Выбор дополнительных документов", "ЭТК");
        checkStrategy(claim, "Проверка дохода", "Проверка дохода/Версия 1", incomeVerificationPage);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.closeCurrentTab()
                .openMenuLinks("Личный кабинет")
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка дохода/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Оценка дохода проведена")
                .fillInput("Поле ввода Средний доход по рынку для занимаемой должности", "3000")
                .clickOnElement("Кнопка Рассчитать")
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Доход не завышен")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab()
                .goTo(loginPage)
                .openMenuLinks("Поиск")
                .goTo(searchPage)
                .searchClaimOnPage(claim);
        String statusClaim = searchPage.getTextFromTable("Таблица результаты поиска", 1, "Статус заявки");
        assertIsTrue(statusClaim.equals("Кредит разрешен"),
                "Значение в столбце Статус заявки должно быть равно Кредит разрешен. Фактическое значение: " + statusClaim);

    }

    @ParameterizedTest
    @CsvSource({
            "3207919, без дохода - Да + \"Доход по коду 2013\" (Прочие клиенты), null, stub13, Выписка из СФР без дохода, Доход по коду 2013",
            "3207927, без дохода + \"Доход по коду 2014\" (Крупные работодатели), Comp_Type_Big_Macro, stub15, Выписка из СФР без дохода, Доход по коду 2014",
            "3207934, с доходом - Да + \"Доход по коду 2013\" (Прочие клиенты), null, stub13, Выписка из СФР c доходом, Доход по коду 2013",
            "3207930, с доходом - Да + \"Доход по коду 2014\" (Крупные работодатели), Comp_Type_Big_Macro, stub15, Выписка из СФР c доходом, Доход по коду 2014"})
    @Tag("l0_verification_extract_from_sfr")
    @DisplayName("{id} - Верификация L0. Выбор дополнительных документов:Выписка из СФР {displayName}")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void l0_verification_extract_from_sfr(String id, String displayName, String kpMainOne, String code, String addDocs, String yesOnTrigger, TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "incomeMain", "NO1",
                "kpMainOne", kpMainOne,
                "kpMainTwo", "null",
                "kpClient", "null",
                "Code", code,
                "Code1", "stub8",
                "Code2", "stub16");

        String document = "Паспорт";
        List<String> expectedTriggers = List.of(
                "Подпись в анкете и паспорте отличается",
                "Признаки фальсификации",
                "Отметка о рождении ребенка до 1,5 лет на момент обращения",
                "Регистрация в текущем регионе менее 1 года");
        String documentStep2 = "2-НДФЛ";
        List<String> triggersDocumentSteps2 = List.of(
                "Доход по коду 2013",
                "Доход по коду 2611",
                "Доход по коду 2014",
                "Наличие сведений о ликвидации",
                "Признаки подделки",
                "Признаки фальсификации");

        String claim = getClaim(testInfo, "2535622", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2);
        handleTriggers(triggersDocumentSteps2, documentStep2, yesOnTrigger);
        handleTriggers(TRIGGERS_ADDITIONAL_DOCS, "Выбор дополнительных документов", addDocs);
        checkStrategy(claim, "ФССП", "ФССП/Версия 1", fsspPage);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.closeCurrentTab()
                .openMenuLinks("Личный кабинет")
                .goTo(personalAccountPage);
        checkStrategyOnTable("ФССП", personalAccountPage);
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Исполнительное производство не найдено")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab()
                .goTo(loginPage)
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim);
        checkStrategyOnTable("Проверка открытых источников", queuesPage);
        queuesPage.doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(checkingOpenSourcesPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка открытых источников/Версия 1")
                .assertElementByTitleVisibility("Флаг Проверить!", "отображается")
                .closeCurrentTab();
    }

    @ParameterizedTest
    @CsvSource({
            "3207905, " +
                    "без дохода - Да + \"Сумма з/п в выписке из ПФР отличается более чем на 30% от 2-НДФЛ\" (ОПК прочие), " +
                    "Выписка из СФР без дохода, 2-НДФЛ/Выписка из ПФР, " +
                    "Сумма з/п в выписке из ПФР отличается более чем на 30% от 2-НДФЛ",
            "3207908, " +
                    "без дохода - Да + \"Сумма з/п в выписке из ПФР отличается более чем на 30% от выписки с з/п счета\" (ОПК прочие), " +
                    "Выписка из СФР без дохода, Выписка с з/п счета/Выписка из ПФР, " +
                    "Сумма з/п в выписке из ПФР отличается более чем на 30% от выписки с з/п счета",
            "3207937, " +
                    "с доходом - Да + \"Сумма з/п в выписке из ПФР отличается более чем на 30% от выписки с з/п счета\" (ОПК прочие), " +
                    "Выписка из СФР с доходом, Выписка с з/п счета/Выписка из ПФР, " +
                    "Сумма з/п в выписке из ПФР отличается более чем на 30% от выписки с з/п счета",
            "3207918, " +
                    "с доходом - Да + \"Сумма з/п в выписке из ПФР отличается более чем на 30% от 2-НДФЛ\" (ОПК прочие), " +
                    "Выписка из СФР с доходом, 2-НДФЛ/Выписка из ПФР, " +
                    "Сумма з/п в выписке из ПФР отличается более чем на 30% от 2-НДФЛ"})
    @Tag("l0_verification_extract_from_sfr_opk")
    @DisplayName("{id} - Верификация L0. Выбор дополнительных документов:Выписка из СФР {displayName}")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void l0_verification_extract_from_sfr_opk(String id, String displayName,
                                                     String addDocs, String documentStep2, String yesOnTrigger,
                                                     TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "incomeMain", "NO1",
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
                .checkDocsOnStep(STEP_2);
        handleTriggers(List.of(yesOnTrigger), documentStep2, yesOnTrigger);
        handleTriggers(TRIGGERS_ADDITIONAL_DOCS, "Выбор дополнительных документов", addDocs);
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку");
        validateClaimStatus(claim);
    }

    @ParameterizedTest
    @CsvSource({
            "3207916, ЭТК - Да + \"Сумма з/п в выписке стороннего банка отличается более чем на 30% от 2-НДФЛ\" (Госслужащие), ЭТК",
            "3207920, Выписка из СФР с доходом - Да + \"Сумма з/п в выписке стороннего банка отличается более чем на 30% от 2-НДФЛ\" (Госслужащие), Выписка из СФР с доходом",
            "3207926, Выписка из СФР без дохода - Да + \"Сумма з/п в выписке стороннего банка отличается более чем на 30% от 2-НДФЛ\" (Госслужащие) , Выписка из СФР без дохода"})
    @Tag("l0_verification_3207916_3207920_3207926")
    @DisplayName("{id} - Верификация L0. Выбор дополнительных документов: {displayName}")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void l0_verification_3207916_3207920_3207926(String id, String displayName, String addDocs, TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "incomeMain", "NO1",
                "kpMainOne", "Comp_Type_Public_Servant_WSOpen",
                "kpMainTwo", "null",
                "kpClient", "null",
                "Code", "stub13",
                "Code1", "stub8",
                "Code2", "stub16");

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
                .checkDocsOnStep(STEP_2);
        handleTriggers(List.of("Сумма з/п в выписке стороннего банка отличается более чем на 30% от 2-НДФЛ"), "Выписка с з/п счета/2-НДФЛ", "Сумма з/п в выписке стороннего банка отличается более чем на 30% от 2-НДФЛ");
        handleTriggers(TRIGGERS_ADDITIONAL_DOCS, "Выбор дополнительных документов", addDocs);
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOnetab()
                .goTo(loginPage)
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim);
        checkStrategyOnTable("ФССП", personalAccountPage);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.openMenuLinks("Личный кабинет")
                .goTo(personalAccountPage)
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Исполнительное производство не найдено")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab()
                .goTo(loginPage)
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim);
        checkStrategyOnTable("Проверка предыдущих заявок", queuesPage);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage
                .openMenuLinks("Личный кабинет")
                .goTo(personalAccountPage)
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(checkingPreviousClaimsPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка предыдущих заявок/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Расхождения не выявлены")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab()
                .goTo(loginPage)
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim);
        checkStrategyOnTable("Проверка открытых источников", queuesPage);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage
                .openMenuLinks("Личный кабинет")
                .goTo(personalAccountPage)
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(checkingOpenSourcesPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка открытых источников/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Привязка телефона из анкеты", "Телефон привязан")
                .fillInput("Поле ввода Источник подтверждения", "Сайт")
                .clickOnElement("Чек-бокс Негатив по работодателю не выявлен")
                .selectValueFromDropDownList("Выпадающий список Бесконтактное подтверждение трудоустройства", "Занятость не подтверждена")
                .selectValueFromDropDownList("Выпадающий список Брокерские услуги", "Не обнаружены");
        checkSteps(claim, "Прозвон клиента/Версия 1", List.of("Прозвон клиента", "Прозвон работодателя - подтвержденный телефон (обязательный)"), callingEmployerConfirmedPhonePage);
    }

    @Test
    @Tag("l0_verification_3207925")
    @DisplayName("3207925 - Верификация L0. Выбор дополнительных документов:Выписка из СФР без дохода - Да + \"Зачисление \"Расчет при увольнении\" (Корпоративные клиенты)")
    @WorkItemIds({"3207925"})
    public void l0_verification_3207925(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "incomeMain", "NO1",
                "kpMainOne", "Comp_Rel_Corp",
                "kpMainTwo", "null",
                "kpClient", "null",
                "Code", "stub13",
                "Code1", "stub14",
                "Code2", "stub16");

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

        String claim = getClaim(testInfo, "2535622", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2);
        handleTriggers(expectedTriggersStep2, documentStep2, "Зачисление \"Расчет при увольнении\"");
        handleTriggers(TRIGGERS_ADDITIONAL_DOCS, "Выбор дополнительных документов", "Выписка из СФР без дохода");
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab()
                .goTo(loginPage)
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim);
        checkStrategyOnTable("ФССП", personalAccountPage);
        actionsClaimSteps.appointResponsiblePerson(claim);

        loginPage.openMenuLinks("Личный кабинет")
                .goTo(personalAccountPage)
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Исполнительное производство не найдено")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab()

                .goTo(loginPage)
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim);
        checkStrategyOnTable("Проверка открытых источников", queuesPage);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage
                .openMenuLinks("Личный кабинет")
                .goTo(personalAccountPage)
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(checkingOpenSourcesPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка открытых источников/Версия 1")
                .clickOnElement("Чек-бокс Негатив по работодателю не выявлен").clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab()
                .goTo(loginPage)
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim);
        checkStrategyOnTable("Прозвон", queuesPage);
        actionsClaimSteps.appointResponsiblePerson(claim);
        queuesPage
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(callingEmployerConfirmedPhoneRequiredPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон работодателя - подтвержденный телефон (обязательный)/Версия 1");
        List<String> actualSteps = callingEmployerConfirmedPhoneRequiredPage.getActualStepNames();
        assertEquals(List.of("Прозвон работодателя - подтвержденный телефон (обязательный)"), actualSteps, "Ожидаемый список шагов " + List.of("Прозвон работодателя - подтвержденный телефон (обязательный)") + " не совпадает с актуальным: " + actualSteps);
        callingEmployerConfirmedPhoneRequiredPage.closeCurrentTab();
    }

    @ParameterizedTest
    @CsvSource({
            "3207929, ЭТК - Да + \"Снижение дохода в последних месяцах\" (Крупные работодатели), ЭТК",
            "3207915, Выписка из СФР с доходом - Да + \"Снижение дохода в последних месяцах\" (Крупные работодатели), Выписка из СФР с доходом",
            "3207924, Выписка из СФР без дохода- Да + \"Снижение дохода в последних месяцах\" (Крупные работодатели), Выписка из СФР без дохода"})
    @Tag("l0_verification_3207929_3207915_3207924")
    @DisplayName("{id} - Верификация L0. Выбор дополнительных документов: {displayName}")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void l0_verification_3207929_3207915_3207924(String id, String displayName, String doc, TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "incomeMain", "NO1",
                "kpMainOne", "Comp_Type_Big_earn",
                "kpMainTwo", "null",
                "kpClient", "null",
                "Code", "stub13",
                "Code1", "stub8",
                "Code2", "stub16");
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
                .checkDocsOnStep(STEP_2);
        handleTriggers(List.of("Снижение дохода в последних месяцах"), "Справка по форме банка/Выписка с з/п счета/2-НДФЛ", "Снижение дохода в последних месяцах");
        handleTriggers(TRIGGERS_ADDITIONAL_DOCS, "Выбор дополнительных документов", doc);
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOnetab()
                .goTo(loginPage)
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim);
        checkStrategyOnTable("ФССП", personalAccountPage);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.openMenuLinks("Личный кабинет")
                .goTo(personalAccountPage)
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Исполнительное производство не найдено")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab()
                .goTo(loginPage)
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim);
        checkStrategyOnTable("Проверка предыдущих заявок", queuesPage);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage
                .openMenuLinks("Личный кабинет")
                .goTo(personalAccountPage)
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(checkingPreviousClaimsPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка предыдущих заявок/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Расхождения не выявлены")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab()
                .goTo(loginPage)
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim);
        checkStrategyOnTable("Проверка дохода", queuesPage);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage
                .openMenuLinks("Личный кабинет")
                .goTo(personalAccountPage)
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка дохода/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Оценка дохода проведена")
                .fillInput("Поле ввода Средний доход по рынку для занимаемой должности", "300000")
                .clickOnElement("Кнопка Рассчитать")
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Доход не завышен")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab()
                .goTo(loginPage)
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim);
        checkStrategyOnTable("Проверка открытых источников", queuesPage);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage
                .openMenuLinks("Личный кабинет")
                .goTo(personalAccountPage)
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(checkingOpenSourcesPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка открытых источников/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Проверка сайта", "Сайт найден и негатив не выявлен")
                .fillInput("Поле ввода Источник подтверждения Проверка сайта", "Сайт")
                .selectValueFromDropDownList("Выпадающий список Привязка телефона из анкеты", "Телефон привязан")
                .fillInput("Поле ввода Источник подтверждения Проверка телефона из анкеты", "Сайт")
                .clickOnElement("Чек-бокс Негатив по работодателю не выявлен")
                .selectValueFromDropDownList("Выпадающий список Бесконтактное подтверждение трудоустройства", "Занятость не подтверждена")
                .selectValueFromDropDownList("Выпадающий список Брокерские услуги", "Не обнаружены");
        checkSteps(claim, "Прозвон клиента/Версия 1", List.of("Прозвон клиента", "Прозвон работодателя - подтвержденный телефон (обязательный)"), customerCallPage);
    }

    @ParameterizedTest
    @CsvSource({
            "3207939, Выписка из СФР с доходом - Да + \"Компания в реестре «Сокращения»\" (Госслужащие), Выписка из СФР с доходом",
            "3207940, Выписка из СФР без дохода - Да + \"Компания в реестре «Сокращения»\" (Госслужащие), Выписка из СФР без дохода"})
    @Tag("smoke")
    @Tag("l0_verification_3207939_3207940")
    @DisplayName("{id} - Верификация L0. Выбор дополнительных документов: {displayName}")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void l0_verification_3207939_3207940(String id, String displayName, String doc, TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "incomeMain", "NO1",
                "kpMainOne", "Comp_Type_Public_Servant_WSOpen",
                "kpMainTwo", "null",
                "kpClient", "null",
                "Code", "stub13",
                "Code1", "stub8",
                "Code2", "stub16");
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
                .checkDocsOnStep(STEP_2);
        handleTriggers(List.of("Компания в реестре «Сокращения»", "Выявлен негатив в реестре «Подозрения на фрод»"), "Проверка реестров", "Компания в реестре «Сокращения»");
        l0CheckingDocumentsPage.fillInput("Поле ввода Внутренний комментарий", "коммент");
        handleTriggers(TRIGGERS_ADDITIONAL_DOCS, "Выбор дополнительных документов", doc);
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab()
                .goTo(loginPage)
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim);
        checkStrategyOnTable("Проверка открытых источников", queuesPage);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage
                .openMenuLinks("Личный кабинет")
                .goTo(personalAccountPage)
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(checkingOpenSourcesPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка открытых источников/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Привязка телефона из анкеты", "Телефон привязан")
                .fillInput("Поле ввода Источник подтверждения Проверка телефона из анкеты", "Сайт");
        checkSteps(claim, "Прозвон работодателя - подтвержденный телефон (обязательный)/Версия 1",
                List.of("Прозвон работодателя - подтвержденный телефон (обязательный)"), callingEmployerConfirmedPhoneRequiredPage);
    }

    @Test
    @Tag("l0_verification_3207922")
    @DisplayName("3207922 - Верификация L0. Выбор дополнительных документов:Выписка из СФР без дохода- Да + \"Доход завышен\" (ОПК прочие)")
    @WorkItemIds({"3207922"})
    @ExternalId("3207922")
    public void l0_verification_3207922(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "incomeMain", "NO1",
                "kpMainOne", "Comp_Type_TOP_OPK",
                "kpMainTwo", "null",
                "kpClient", "null",
                "Code", "stub5",
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
                .checkDocsOnStep(STEP_2);
        handleTriggers(TRIGGERS_ADDITIONAL_DOCS, "Выбор дополнительных документов", "Выписка из СФР без дохода");
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab()
                .goTo(loginPage)
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim);
        checkStrategyOnTable("Проверка дохода", queuesPage);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage
                .openMenuLinks("Личный кабинет")
                .goTo(personalAccountPage)
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка дохода/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Оценка дохода проведена")
                .fillInput("Поле ввода Средний доход по рынку для занимаемой должности", "1000")
                .clickOnElement("Кнопка Рассчитать")
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Доход завышен. Руководитель/Специалист")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab()
                .goTo(loginPage)
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim);
        checkStrategyOnTable("Проверка открытых источников", queuesPage);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage
                .openMenuLinks("Личный кабинет")
                .goTo(personalAccountPage)
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(checkingOpenSourcesPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка открытых источников/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Привязка телефона из анкеты", "Телефон привязан")
                .fillInput("Поле ввода Источник подтверждения Проверка телефона из анкеты", "Сайт");
        checkSteps(claim, "Прозвон клиента/Версия 1", List.of("Прозвон клиента", "Прозвон работодателя - подтвержденный телефон"), customerCallPage);
    }

    @Test
    @Tag("l0_verification_3207912")
    @DisplayName("3207912 - Верификация L0. Выбор дополнительных документов: Выписка из СФР с доходом - Да + \"Зачисление \"Расчет при увольнении\" (Корпоративные клиенты)")
    @WorkItemIds({"3207912"})
    @ExternalId("3207912")
    public void l0_verification_3207912(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "incomeMain", "NO1",
                "kpMainOne", "Comp_Rel_Corp",
                "kpMainTwo", "null",
                "kpClient", "null",
                "Code", "stub13",
                "Code1", "stub14",
                "Code2", "stub16");
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

        String claim = getClaim(testInfo, "2535622", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2);
        handleTriggers(expectedTriggersStep2, documentStep2, "Зачисление \"Расчет при увольнении\"");
        handleTriggers(TRIGGERS_ADDITIONAL_DOCS, "Выбор дополнительных документов", "Выписка из СФР с доходом");
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOnetab()
                .goTo(loginPage)
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim);
        checkStrategyOnTable("ФССП", personalAccountPage);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.openMenuLinks("Личный кабинет")
                .goTo(personalAccountPage)
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Исполнительное производство не найдено")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab()
                .goTo(loginPage)
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim);
        checkStrategyOnTable("Проверка открытых источников", queuesPage);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage
                .openMenuLinks("Личный кабинет")
                .goTo(personalAccountPage)
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(checkingOpenSourcesPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка открытых источников/Версия 1")
                .clickOnElement("Чек-бокс Негатив по работодателю не выявлен");
        checkSteps(claim, "Прозвон работодателя - подтвержденный телефон (обязательный)/Версия 1", List.of("Прозвон работодателя - подтвержденный телефон (обязательный)"), callingEmployerConfirmedPhoneRequiredPage);
    }

    @Test
    @Tag("strategy_l0_1645453")
    @DisplayName("1645453 - Верификация.Стратегия «L0/Проверка документов»  результат проверки для Определение итогового результата для результата «Отметка о рождении ребенка до 1,5 лет на момент обращения» назначение стратегии (Военнослужащие)")
    @WorkItemIds({"1645453"})
    public void strategy_l0_1645453(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "incomeMain", "ConfirmedIncomeForm12",
                "kpMain", "Comp_Type_OPK_Macro_War",
                "kpClient", "null",
                "Code", "stub1");
        String[][] expectedValues = {
                {"Наименование стратегии", "Статус", "Результат", "Ссылка"},
                {"L0.Проверка документов клиента", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"L0.Проверка документов работодателя", "Завершен", "Далее по процессу", "Открыть стратегию"},
                {"ФССП", "В работе", "", "Открыть стратегию"},
                {"Проверка предыдущих заявок", "Назначен", "", ""},
                {"Открытые источники - проверка работодателя", "Назначен", "", ""},
                {"Прозвон клиента", "Назначен", "", ""},
                {"Прозвон контактного лица/супруга (-и)", "Назначен", "", ""},
                {"Прозвон работодателя - подтвержденный телефон", "Назначен", "", ""}};
        String document = "Паспорт";
        List<String> expectedTriggers = List.of(
                "Подпись в анкете и паспорте отличается",
                "Признаки фальсификации",
                "Отметка о рождении ребенка до 1,5 лет на момент обращения");
        List<String> expectedTriggers2 = List.of(
                "Доход по коду 2611",
                "Доход по коду 2013",
                "Доход по коду 2014",
                "Наличие сведений о ликвидации",
                "Признаки подделки",
                "Признаки фальсификации");
        String document2 = "2-НДФЛ";
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
        List<String> expectedListOfSigns = List.of(
                "Дублирование реквизитов",
                "Отсутствует или неверный № ИНН организации",
                "В справке записи «подготовлена с помощью Консультант +» или «Гарант», либо справка подготовлена с помощью онлайн сервисов",
                "Одинаковые суммы дохода за весь период (не менее 6 месяцев) (либо одинаковые суммы дохода за весь период календарного года (не менее 6 месяцев))",
                "Доход до вычета налога изменяется в пределах 5% в 3-х и более месяцах за весь период в справке (КРОМЕ КЛИЕНТОВ ВОЕННОСЛУЖАЩИХ ИЛИ СИЛОВИКОВ)",
                "В суммах налогов указаны ненулевые копейки (наличие нулевых копеек допускается)",
                "Клиент работает в организации не с начала года, а справка предоставлена за весь год",
                "Не указаны коды доходов",
                "Месяц получения дохода указан прописью (пример – «январь»)",
                "Несоответствие названия/ИНН организации в оттиске печати названию/ИНН в соответствующем реквизите",
                "Недопустимые значения полей (ОКТМО, вычеты на детей)",
                "Различные шрифты в одинаковых полях, несоответствие символа @, разные отступы в начислениях",
                "Различие в ФИО и/или дате рождения Клиента между паспортом и справкой",
                "Сходство в подписях / почерках / фамилиях подписантов / записях их заверении",
                "Указанная сумма налоговой базы по ставке 13% превышает 5 млн. рублей и в справке отсутствует таблица с доходами, облагаемыми по ставке 15%",
                "Неправильное название компании - любая ошибка в организационно правовой форме или явная ошибка в названии");
        String claim = getClaim(testInfo, "1651046", claimParams);
        actionsOnStep1(claim, document, expectedTriggers);
        handleTriggers(expectedTriggers, document, "Отметка о рождении ребенка до 1,5 лет на момент обращения");
        l0CheckingDocumentsPage
                .clickButtonsForAddField("Да", "Доход менялся в период рождения ребенка", "Отметка о рождении ребенка до 1,5 лет на момент обращения")
                .clickButtonsForAddField("Да", "Должность предполагает работу на дому", "Отметка о рождении ребенка до 1,5 лет на момент обращения")
                .clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2, expectedDocsStep2);
        handleTriggers(expectedTriggers2, document2, "Признаки подделки");
        List<String> actualListOfSigns = l0CheckingDocumentsPage.getActualListOfSigns("не выбранные");
        assertIsEquals(expectedListOfSigns, actualListOfSigns, "Детализация признаков подделки");
        l0CheckingDocumentsPage
                .clickOnSign("Дублирование реквизитов")
                .clickOnElement("Кнопка Готово на модальном окне Детализация признаков")
                .assertElementByTitleVisibility("Модальное окно Детализация признаков", "не отображается");

        handleTriggers(expectedTriggersAdditionalDocs, "Выбор дополнительных документов");
        l0CheckingDocumentsPage
                .clickOnNotProvidedIconForDoc("Выбор звания/должности", "Документ не предоставлен")
                .selectValueFromDropDownList("Выпадающий список Выберите звание/должность", "Офицеры (с генерал-майора)")
                .checkElementByTitleEquals("Поле Доход завышен/не завышен", "Доход не завышен");
        checkTableVerification(claim, expectedValues);
    }
}
