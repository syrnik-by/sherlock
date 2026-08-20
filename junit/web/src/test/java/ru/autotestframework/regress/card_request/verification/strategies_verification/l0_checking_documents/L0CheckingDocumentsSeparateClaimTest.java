package ru.autotestframework.regress.card_request.verification.strategies_verification.l0_checking_documents;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.ExternalId;
import ru.psb.testit.annotations.WorkItemIds;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;
import static ru.autotestframework.pages.card_request.verification.L0CheckingDocumentsPage.*;
import static ru.autotestframework.pages.card_request.verification.L0CheckingDocumentsPage.STEP_3;
import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;

@Tag("regress")
@Tag("card_request")
@Tag("verification")
@Tag("strategies_verification")
@Tag("l0")
@Tag("l0_separate_claim")
@ClassName("Карточка заявки. Верификация. ЭФ стратегий верификации. L0. Проверка документов. На каждый кейс отдельная заявка")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class L0CheckingDocumentsSeparateClaimTest extends BaseTest {

    @BeforeEach
    public void login() {
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi();
        }
        loginPage.openMenuLinks("Личный кабинет");
    }

    @AfterEach
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }


    @Test
    @Tag("smoke")
    @Tag("verification_strategy_l0_1645392")
    @DisplayName("1645392 - Верификация.Стратегия «L0/Проверка документов» назначается для заемщика, " +
            "основного места работы и совместительства для не реестровых и без подтверждения дохода")
    @WorkItemIds({"1645392"})
    public void verification_strategy_l0_assigned_1645392(TestInfo testInfo) {
        Map<String, List<String>> expectedValues = new LinkedHashMap<>();
        addElements(expectedValues, "Наименование стратегии",
                List.of("L0.Проверка документов клиента", "L0.Проверка документов работодателя", "ФССП"));
        addElements(expectedValues, "Статус", List.of("В работе", "В работе", "Назначен"));
        addElements(expectedValues, "Результат", List.of("", "", ""));
        addElements(expectedValues, "Ссылка", List.of("Открыть стратегию", "Открыть стратегию", ""));
        Map<String, String> claimParams = Map.of(
                "incomeMain", "ConfirmedIncomeForm11",
                "incomeSecond", "ConfirmedIncomeForm13",
                "kpMain", "Comp_Type_Big_Macro",
                "kpSecond", "Comp_Type_Big_Macro",
                "kpClient", "null");

        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1649906.json",
                1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(l0CheckingDocumentsPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "L0.Проверка документов/Версия 1")
                .assertElementByTitleVisibility("Проверка Заемщик", "отображается")
                .assertElementByTitleVisibility("Проверка Заемщик. Основное место работы", "отображается")
                .assertElementByTitleVisibility("Проверка Заемщик. Совместительство", "отображается")
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

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "1645394; Стратегия «L0/Проверка документов» назначается для заемщика, основного места работы, а на совместительство не назначается; FormSpravType66666; FormSpravType12",
            "1645391; Стратегия «L0/Проверка документов» назначается для заемщика, совместительство и не назначается для основного места работы; ConfirmedIncomeForm17; null"})
    @Tag("verification_strategy_l0_assigned_for_loan")
    @Tag("smoke")
    @DisplayName("{id} - Верификация. {displayName}")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void verification_strategy_l0_assigned_for_loan(String id, String displayName, String incomeMain, String incomeSecond, TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "incomeMain", incomeMain,
                "incomeSecond", incomeSecond,
                "kpMain", "Comp_Type_Big_Macro",
                "kpSecond", "Comp_Type_Big_Macro",
                "kpClient", "null");

        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1649906.json",
                1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(l0CheckingDocumentsPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "L0.Проверка документов/Версия 1")
                .assertElementByTitleVisibility("Проверка Заемщик", "отображается")
                .assertElementByTitleVisibility("Проверка Заемщик. Основное место работы", id.equals("1645391")
                        ? "не отображается" : "отображается")
                .assertElementByTitleVisibility("Проверка Заемщик. Совместительство", id.equals("1645394")
                        ? "не отображается" : "отображается");
        manualChecks.closeCurrentTab();
    }

    @ParameterizedTest
    @CsvSource({
            "1645384, Электронная выписка по зп карте/счету стор. Банка, ConfirmedIncomeForm19",
            "1645387, Текущий счёт/карта в ПСБ, ConfirmedIncomeForm12"})
    @Tag("verification_strategy_l0")
    @DisplayName("{id} - Верификация. Стратегия «L0/Проверка документов» назначается если для основного места работы " +
            "\"Форма подтверждения\" - \"{displayName}\"")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void verification_strategy_l0_assigned(String id, String displayName, String incomeMain, TestInfo testInfo) {
        Map<String, List<String>> expectedValues = new LinkedHashMap<>();
        addElements(expectedValues, "Наименование стратегии",
                List.of("L0.Проверка документов клиента",
                        "L0.Проверка документов работодателя",
                        "ФССП"));
        addElements(expectedValues, "Статус", List.of("В работе", "В работе", "Назначен"));
        addElements(expectedValues, "Результат", List.of("", "", ""));
        addElements(expectedValues, "Ссылка", List.of("Открыть стратегию", "Открыть стратегию", ""));
        Map<String, String> claimParams = Map.of("incomeMain", incomeMain);

        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1649548.json",
                1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "L0.Проверка документов/Версия 1")
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

    @ParameterizedTest
    @MethodSource("provideArguments")
    @Tag("smoke")
    @Tag("strategy_l0")
    @DisplayName("{id} - Верификация. Стратегия «L0/Проверка документов» не назначается если для основного места работы " +
            "\"Форма подтверждения\" - \"{displayName}\"")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void verification_strategy_l0_not_assigned(String id, String claimTemp, String displayName, Map<String, String> claimParams, TestInfo testInfo) {
        Map<String, String> expectedValues = Map.of(
                "Наименование стратегии", "ФССП",
                "Статус", "В работе",
                "Результат", "",
                "Ссылка", "Открыть стратегию");
        List<String> strategiesValue = List.of(
                "L0.Проверка документов работодателя",
                "L0.Проверка документов клиента");
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_" + claimTemp + ".json",
                1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .clickOnElement("Кнопка Основные данные").switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Результаты проверок")
                .goTo(resultCheck)
                .clickOnElement("Вкладка Ручные проверки")
                .goTo(manualChecks)
                .clickOnElement("Кнопка Верификация назначенные проверки");

        for (Map.Entry<String, String> expectedValue : expectedValues.entrySet()) {
            String actualValue = manualChecks.getTextFromTable("Таблица Верификация назначенные проверки", 1, expectedValue.getKey());
            assertIsTrue(actualValue.equals(expectedValue.getValue()),
                    "Значение в столбце " + expectedValue.getKey() + " должно быть равно " + expectedValue.getValue());
        }
        assertIsTrue(manualChecks.checkAttributeElementTitle("href", "Ссылка Открыть стратегию"),
                "Ссылка открыть стратегию должна являться ссылкой, а не текстом");

        boolean containsStrategy = manualChecks.getListValuesByColumnName("Таблица Верификация назначенные проверки", "Наименование стратегии")
                .stream().anyMatch(strategiesValue::contains);
        assertIsTrue(!containsStrategy, "Указанные стратегии " + strategiesValue + " не содержаться в списке назначенных стратегий");
        manualChecks.closeCurrentTab().closeCurrentTab();
    }

    @Test
    @Tag("strategy_l0_1645421")
    @DisplayName("1645421 - Верификация. Стратегия «L0/Проверка документов» по умолчанию все документы находятся в статусе «Документ не предоставлен» для стратегии «L0/Проверка документов»")
    @WorkItemIds({"1645421"})
    public void strategy_l0_1645421(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "incomeMain", "ConfirmedIncomeForm11",
                "incomeSecond", "ConfirmedIncomeForm13",
                "kpMain", "null",
                "kpSecond", "Comp_Type_Big_Macro",
                "kpClient", "\"Comp_Type_Big_Macro\"");
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1649906.json", 1,
                testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi();
        }
        loginPage.openMenuLinks("Личный кабинет")
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(l0CheckingDocumentsPage)
                .checkDocsOnStep(STEP_1)
                .clickOnElement(STEP_2)
                .checkDocsOnStep(STEP_2)
                .clickOnElement(STEP_3)
                .checkDocsOnStep(STEP_3);
    }

    private static Stream<Arguments> provideArguments() {
        return Stream.of(
                arguments("1645381", "1649548", "Реестр доход до прекращ ЗП-зачисл", Map.of("incomeMain", "ConfirmedIncomeForm17")),
                arguments("1645382", "1649548", "Реестр работодателя", Map.of("incomeMain", "ConfirmedIncomeForm14")),
                arguments("1645383", "1649548", "Реестр работодателя", Map.of("incomeMain", "FormSpravType13")),
                arguments("1645388", "1649548", "Реестровые зачисления", Map.of("incomeMain", "FormSpravType5")),
                arguments("1645386", "1649548", "Зарплатные/пенсионные зачисления в ПСБ", Map.of("incomeMain", "ConfirmedIncomeForm4")),
                arguments("1645385", "1649548", "Реестровые зачисления (пилот)", Map.of("incomeMain", "FormSpravType12")),
                arguments("1645389", "1649548", "Без подтверждения", Map.of("incomeMain", "NO")),
                arguments("1645390", "1649906", "Зарплатные/пенсионные зачисления в ПСБ",
                        Map.of(
                                "incomeMain", "ConfirmedIncomeForm17",
                                "incomeSecond", "ConfirmedIncomeForm4",
                                "kpMain", "Comp_Type_Big_Macro",
                                "kpSecond", "Comp_Type_Big_Macro",
                                "kpClient", "null")),
                arguments("1645393", "1260785", "Реестровые зачисления\" и для совместительства \"Форма подтверждения\" - \"Без подтверждения\"",
                        Map.of(
                                "incomeMain", "FormSpravType5",
                                "incomeSecond", "NO",
                                "kpMain", "Comp_Type_Big_Macro",
                                "kpSecond", "Comp_Type_Big_Macro",
                                "kpClient", "null")));
    }

    private void addElements(Map<String, List<String>> map, String key, List<String> values) {
        // Получаем список по ключу, если он существует или создаем новый
        map.computeIfAbsent(key, k -> new ArrayList<>()).addAll(values);
    }
}