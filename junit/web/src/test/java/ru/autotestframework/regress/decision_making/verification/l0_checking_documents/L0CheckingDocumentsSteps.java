package ru.autotestframework.regress.decision_making.verification.l0_checking_documents;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import ru.autotestframework.BaseTest;
import ru.autotestframework.pages.BasePage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.autotestframework.pages.card_request.verification.L0CheckingDocumentsPage.STEP_1;
import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;

public abstract class L0CheckingDocumentsSteps extends BaseTest {

    final static List<String> TRIGGERS_ADDITIONAL_DOCS = List.of(
            "Выписка из СФР с доходом",
            "Выписка из СФР без дохода",
            "ЭТК",
            "Выписка с з/п счета с ящика doc");

    @BeforeEach
    public void login() {
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi();
        }
    }

    @AfterEach
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    String getClaim(TestInfo testInfo, String template, Map<String, String> claimParams) {
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_" + template + ".json",
                1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        return claim;
    }

    void actionsOnStep1(String claim, String document, List<String> expectedTriggers) {
        loginPage.openMenuLinks("Личный кабинет")
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(l0CheckingDocumentsPage)
                .checkDocsOnStep(STEP_1)
                .clickOnNotProvidedIconForDoc(document, "Документ не предоставлен")
                .checkToolTipText(document, "Документ предоставлен");

        List<String> actualTriggers = l0CheckingDocumentsPage.getActualListTriggersByDoc(document);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.checkRadioButtonsForTrigger(document, trigger));
        assertEquals(expectedTriggers, actualTriggers, "Список триггеров для документа " + document + " не совпадает с актуальным: " + actualTriggers);
    }

    void validateClaimStatus(String claim) {
        l0CheckingDocumentsPage.switchToOneTab()
                .goTo(loginPage)
                .openMenuLinks("Поиск")
                .goTo(searchPage)
                .searchClaimOnPage(claim);
        String statusClaim = searchPage.getTextFromTable("Таблица результаты поиска", 1, "Статус заявки");
        assertIsTrue(statusClaim.equals("Отказ"),
                "Значение в столбце Статус заявки должно быть равно Отказ. Фактическое значение: " + statusClaim);
    }

    void checkTableVerification(String claim, String[][] expectedValues) {
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

        String[][] actualValues = manualChecks.getTableHeadersAndContentAsArray("Таблица Верификация назначенные проверки");
        assertIsTrue(Arrays.deepEquals(expectedValues, actualValues), "Актуальные \n" + Arrays.deepToString(actualValues) + "\n и ожидаемые значения \n" + Arrays.deepToString(expectedValues) + "\n в таблице \"Таблица Верификация назначенные проверки\" должны совпадать");
        manualChecks.closeCurrentTab().closeCurrentTab();
    }

    void checkSteps(String claim, String strategyName, List<String> expectedSteps, BasePage<?> targetPage) {
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab()
                .goTo(loginPage)
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim);
        checkStrategyOnTable("Прозвон", queuesPage);
        queuesPage
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(targetPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", strategyName);
        List<String> actualSteps = customerCallPage.getActualStepNames();
        assertEquals(expectedSteps, actualSteps, "Ожидаемый список шагов " + expectedSteps + " не совпадает с актуальным: " + actualSteps);
        targetPage.closeCurrentTab();
    }

    void checkStrategy(String claim, String strategy, String strategyName, BasePage<?> targetPage) {
        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab()
                .goTo(loginPage)
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim);
        checkStrategyOnTable(strategy, queuesPage);
        queuesPage
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(targetPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", strategyName);
    }

    void checkStrategyOnTable(String strategy, BasePage<?> targetPage) {
        String strategyClaim = targetPage.getTextFromTable("Таблица результаты поиска", 1, "Стратегия");
        assertIsTrue(strategyClaim.equals(strategy),
                "Значение в столбце Статус заявки должно быть равно " + strategy + ". Фактическое значение: " + strategyClaim);
    }

    void handleTriggers(List<String> triggers, String document, String... positiveTrigger) {
        if (l0CheckingDocumentsPage.iconIsVisible(document, "Документ не предоставлен")) {
            l0CheckingDocumentsPage.clickOnNotProvidedIconForDoc(document, "Документ не предоставлен");
        }

        if (positiveTrigger.length != 0) {
            triggers.stream()
                    .filter(trigger -> !trigger.equals(positiveTrigger[0])) // Исключаем положительный триггер
                    .forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));

            triggers.stream()
                    .filter(trigger -> trigger.equals(positiveTrigger[0]))
                    .findFirst()
                    .ifPresent(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Да", trigger, document));
        } else {
            triggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        }
    }

    void addElements(Map<String, List<String>> map, String key, List<String> values) {
        map.computeIfAbsent(key, k -> new ArrayList<>()).addAll(values);
    }
}
