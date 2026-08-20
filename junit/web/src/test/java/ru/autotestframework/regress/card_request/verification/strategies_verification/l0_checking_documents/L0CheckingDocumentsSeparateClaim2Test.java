package ru.autotestframework.regress.card_request.verification.strategies_verification.l0_checking_documents;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import org.openqa.selenium.StaleElementReferenceException;
import ru.autotestframework.BaseTest;
import ru.autotestframework.core.exception.ExecutionException;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.autotestframework.pages.card_request.verification.L0CheckingDocumentsPage.*;
import static ru.autotestframework.steps.asserts.Asserts.assertIsEquals;

@Tag("regress")
@Tag("card_request")
@Tag("verification")
@Tag("strategies_verification")
@Tag("l0")
@Tag("l0_separate_claim_2")
@ClassName("Карточка заявки. Верификация. ЭФ стратегий верификации. L0. Проверка документов. На каждый кейс отдельная заявка №2")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class L0CheckingDocumentsSeparateClaim2Test extends BaseTest {

    private static String claim;

    @BeforeEach
    public void login(TestInfo testInfo) {
        try {
            try {
                Map<String, String> claimParams = Map.of("incomeMain", "ConfirmedIncomeForm12");
                claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1649548.json", 1, testInfo, claimParams).get(0);
                actionsClaimSteps.appointResponsiblePerson(claim);
            } catch (ExecutionException e) {
                throw new RuntimeException("Ошибка при попытке отправить заявку: " + e.getMessage(), e);
            }
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException | StaleElementReferenceException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi();
        }
        loginPage.openMenuLinks("Личный кабинет")
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition();
    }

    @AfterEach
    public void cleanQueueClaims() {
        l0CheckingDocumentsPage.closeCurrentTab();
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("strategy_l0_1645422")
    @DisplayName("1645422 - Верификация. Стратегия «L0/Проверка документов» при наведении мышки на иконку \"Документа\" должна появляться всплывающая подсказка.")
    @WorkItemIds({"1645422"})
    public void strategy_l0_1645422() {
        l0CheckingDocumentsPage.checkToolTipText("ФОТО", "Документ НЕ предоставлен")
                .checkDocsOnStep(STEP_1)
                .clickOnNotProvidedIconForDoc("ФОТО", "Документ не предоставлен")
                .checkToolTipText("ФОТО", "Документ предоставлен")
                .clickOnElement(STEP_2)
                .checkDocsOnStep(STEP_2)
                .clickOnElement("Кнопка Взять шаг в работу").waitBusyCondition()
                .checkToolTipText("Справка по форме банка/работодателя", "Документ НЕ предоставлен")
                .clickOnNotProvidedIconForDoc("Справка по форме банка/работодателя", "Документ не предоставлен")
                .checkToolTipText("Справка по форме банка/работодателя", "Документ предоставлен");
    }

    @Test
    @Tag("smoke")
    @Tag("strategy_l0_1645423")
    @DisplayName("1645423 - Верификация. Стратегия «L0/Проверка документов» при клике по иконке документа меняется статус документа.")
    @WorkItemIds({"1645423"})
    public void strategy_l0_1645423() {
        l0CheckingDocumentsPage
                .checkDocsOnStep(STEP_1)
                .clickOnNotProvidedIconForDoc("ФОТО", "Документ не предоставлен")
                .clickOnNotProvidedIconForDoc("Справки, подтверждающие закрытие обязательств", "Документ не предоставлен")
                .clickOnElement(STEP_2)
                .checkDocsOnStep(STEP_3)
                .clickOnElement("Кнопка Взять шаг в работу").waitBusyCondition()
                .clickOnNotProvidedIconForDoc("2-НДФЛ", "Документ не предоставлен")
                .clickOnNotProvidedIconForDoc("Трудовой договор", "Документ не предоставлен")
                .clickOnNotProvidedIconForDoc("Удостоверение военнослужащего", "Документ не предоставлен");
    }

    @Test
    @Tag("strategy_l0_1645447")
    @DisplayName("1645447 - Верификация. Стратегия «L0/Проверка документов»  отображение подсказки для \"Доход по коду 2014\"")
    @WorkItemIds({"1645447"})
    public void strategy_l0_1645447() {
        List<String> expectedTriggers = List.of(
                "Доход по коду 2611",
                "Доход по коду 2013",
                "Доход по коду 2014",
                "Наличие сведений о ликвидации",
                "Признаки подделки",
                "Признаки фальсификации");
        String document = "2-НДФЛ";
        l0CheckingDocumentsPage.clickOnElement(STEP_2)
                .clickOnElement("Кнопка Взять шаг в работу").waitBusyCondition()
                .clickOnNotProvidedIconForDoc(document, "Документ не предоставлен");
        List<String> actualTriggers = l0CheckingDocumentsPage.getActualListTriggersByDoc(document);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.checkRadioButtonsForTrigger(document, trigger));
        assertEquals(expectedTriggers, actualTriggers, "Список триггеров для документа " + document + " не совпадает с актуальным: " + actualTriggers);
        l0CheckingDocumentsPage.checkToolTipTextTrigger("Доход по коду 2014", "Анализируется весь период в справке");
    }

    @Test
    @Tag("strategy_l0_1645446")
    @DisplayName("1645446 - Верификация.Стратегия «L0/Проверка документов» отображение подсказки " +
            "для \"Отметка о рождении ребенка до 1,5 лет на момент обращения")
    @WorkItemIds({"1645446"})
    public void strategy_l0_1645446() {
        List<String> expectedTriggers = List.of(
                "Подпись в анкете и паспорте отличается",
                "Признаки фальсификации",
                "Отметка о рождении ребенка до 1,5 лет на момент обращения",
                "Регистрация в текущем регионе менее 1 года");
        String document = "Паспорт";
        l0CheckingDocumentsPage
                .checkDocsOnStep(STEP_1)
                .clickOnNotProvidedIconForDoc(document, "Документ не предоставлен");
        List<String> actualTriggers = l0CheckingDocumentsPage.getActualListTriggersByDoc(document);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.checkRadioButtonsForTrigger(document, trigger));
        assertEquals(expectedTriggers, actualTriggers, "Список триггеров для документа " + document + " не совпадает с актуальным: " + actualTriggers);
        l0CheckingDocumentsPage.checkToolTipTextTrigger("Отметка о рождении ребенка до 1,5 лет на момент обращения", "Не учитывается для мужчин");
    }

    @Test
    @Tag("strategy_l0_2511901")
    @DisplayName("2511901 - Верификация. Стратегия «L0/Проверка документов» кнопка «Снять отметки о предоставлении документов» - по умолчанию не активна")
    @WorkItemIds({"2511901"})
    public void strategy_l0_2511901() {
        l0CheckingDocumentsPage.clickOnElement(STEP_2)
                .clickOnElement("Кнопка Взять шаг в работу")
                .assertElementByTitleVisibility("Кнопка Снять отметки о предоставлении документов", "отображается")
                .assertElementByTitleActivity("Кнопка Снять отметки о предоставлении документов", "неактивен")
                .colorElementEquals("Кнопка Снять отметки о предоставлении документов", "rgb(199, 202, 211)");
    }

    @Test
    @Tag("strategy_l0_1645420")
    @DisplayName("1645420 - Верификация. Стратегия «L0/Проверка документов» для проставления результатов пользователю необходимо отметить, что «Документ предоставлен»")
    @WorkItemIds({"1645420"})
    public void strategy_l0_1645420() {
        List<String> expectedTriggers = List.of(
                "Подпись в анкете и паспорте отличается",
                "Признаки фальсификации",
                "Отметка о рождении ребенка до 1,5 лет на момент обращения",
                "Регистрация в текущем регионе менее 1 года");
        String documentSTEP_1 = "Паспорт";
        l0CheckingDocumentsPage
                .checkDocsOnStep(STEP_1)
                .clickOnNotProvidedIconForDoc(documentSTEP_1, "Документ не предоставлен")
                .checkToolTipText(documentSTEP_1, "Документ предоставлен");
        List<String> actualTriggers = l0CheckingDocumentsPage.getActualListTriggersByDoc(documentSTEP_1);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.checkRadioButtonsForTrigger(documentSTEP_1, trigger));
        assertEquals(expectedTriggers, actualTriggers, "Список триггеров для документа " + documentSTEP_1 + " не совпадает с актуальным: " + actualTriggers);

        String documentStep2 = "Военный билет";
        List<String> expectedTriggersStep2 = List.of("Признаки фальсификации");
        l0CheckingDocumentsPage
                .clickOnElement(STEP_2)
                .checkDocsOnStep(STEP_2)
                .clickOnElement("Кнопка Взять шаг в работу").waitBusyCondition()
                .colorElementEquals(STEP_2, "rgba(0, 167, 112, 1)")
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен")
                .checkToolTipText(documentStep2, "Документ предоставлен");
        actualTriggers = l0CheckingDocumentsPage.getActualListTriggersByDoc(documentStep2);
        expectedTriggersStep2.forEach(trigger -> l0CheckingDocumentsPage.checkRadioButtonsForTrigger(documentStep2, trigger));
        assertEquals(expectedTriggersStep2, actualTriggers, "Список триггеров для документа " + documentStep2 + " не совпадает с актуальным: " + actualTriggers);

    }

    @Test
    @Tag("strategy_l0_2511898")
    @DisplayName("2511898 - Верификация. Стратегия «L0/Проверка документов»  кнопка «Снять отметки о предоставлении документов»-кнопка становится активной если есть документ в статусе \"Документ предоставлен\"")
    @WorkItemIds({"2511898"})
    public void strategy_l0_2511898() {
        l0CheckingDocumentsPage
                .checkToolTipText("ФОТО", "Документ НЕ предоставлен")
                .checkDocsOnStep(STEP_1)
                .clickOnNotProvidedIconForDoc("ФОТО", "Документ не предоставлен")
                .checkToolTipText("ФОТО", "Документ предоставлен")
                .clickOnNotProvidedIconForDoc("Справки, подтверждающие закрытие обязательств", "Документ не предоставлен")
                .checkToolTipText("Справки, подтверждающие закрытие обязательств", "Документ предоставлен")
                .assertElementByTitleActivity("Кнопка Снять отметки о предоставлении документов", "активен")
                .colorElementEquals("Кнопка Снять отметки о предоставлении документов", "rgb(202, 64, 57)")
                .clickOnElement(STEP_2)
                .checkDocsOnStep(STEP_2)
                .clickOnElement("Кнопка Взять шаг в работу").waitBusyCondition()
                .colorElementEquals(STEP_2, "rgba(0, 167, 112, 1)")
                .clickOnNotProvidedIconForDoc("2-НДФЛ", "Документ не предоставлен")
                .checkToolTipText("2-НДФЛ", "Документ предоставлен")
                .clickOnNotProvidedIconForDoc("Трудовой договор", "Документ не предоставлен")
                .checkToolTipText("Трудовой договор", "Документ предоставлен")
                .clickOnNotProvidedIconForDoc("Удостоверение военнослужащего", "Документ не предоставлен")
                .checkToolTipText("Удостоверение военнослужащего", "Документ предоставлен")
                .assertElementByTitleActivity("Кнопка Снять отметки о предоставлении документов", "активен")
                .colorElementEquals("Кнопка Снять отметки о предоставлении документов", "rgb(202, 64, 57)");
    }

    @Test
    @Tag("smoke")
    @Tag("strategy_l0_2511899")
    @DisplayName("2511899 - Верификация.Стратегия «L0/Проверка документов»  кнопка «Снять отметки о предоставлении документов» при нажатие статус документа меняется на «Документ не предоставлен»")
    @WorkItemIds({"2511899"})
    public void strategy_l0_2511899() {
        l0CheckingDocumentsPage
                .checkDocsOnStep(STEP_1)
                .clickOnNotProvidedIconForDoc("ФОТО", "Документ не предоставлен")
                .checkToolTipText("ФОТО", "Документ предоставлен")
                .clickOnElement("Кнопка Снять отметки о предоставлении документов")
                .assertElementByTitleActivity("Кнопка Снять отметки о предоставлении документов", "неактивна")
                .checkToolTipText("ФОТО", "Документ НЕ предоставлен")
                .clickOnNotProvidedIconForDoc("Справки, подтверждающие закрытие обязательств", "Документ не предоставлен")
                .checkToolTipText("Справки, подтверждающие закрытие обязательств", "Документ предоставлен")
                .clickOnNotProvidedIconForDoc("ФОТО", "Документ не предоставлен")
                .checkToolTipText("ФОТО", "Документ предоставлен")
                .clickOnElement("Кнопка Снять отметки о предоставлении документов")
                .assertElementByTitleActivity("Кнопка Снять отметки о предоставлении документов", "неактивна")
                .checkToolTipText("Справки, подтверждающие закрытие обязательств", "Документ НЕ предоставлен")
                .checkToolTipText("ФОТО", "Документ НЕ предоставлен")
                .clickOnElement(STEP_2)
                .checkDocsOnStep(STEP_2)
                .clickOnElement("Кнопка Взять шаг в работу").waitBusyCondition()
                .colorElementEquals(STEP_2, "rgba(0, 167, 112, 1)")
                .clickOnNotProvidedIconForDoc("2-НДФЛ", "Документ не предоставлен")
                .checkToolTipText("2-НДФЛ", "Документ предоставлен")
                .clickOnElement("Кнопка Снять отметки о предоставлении документов")
                .assertElementByTitleActivity("Кнопка Снять отметки о предоставлении документов", "неактивна")
                .clickOnNotProvidedIconForDoc("Трудовой договор", "Документ не предоставлен")
                .checkToolTipText("Трудовой договор", "Документ предоставлен")
                .clickOnNotProvidedIconForDoc("Удостоверение военнослужащего", "Документ не предоставлен")
                .checkToolTipText("Удостоверение военнослужащего", "Документ предоставлен")
                .clickOnElement("Кнопка Снять отметки о предоставлении документов")
                .checkToolTipText("Трудовой договор", "Документ НЕ предоставлен")
                .checkToolTipText("Удостоверение военнослужащего", "Документ НЕ предоставлен")
                .assertElementByTitleActivity("Кнопка Снять отметки о предоставлении документов", "неактивна");
    }

    @Test
    @Tag("strategy_l0_2511899")
    @DisplayName("2511899 - Верификация.Стратегия «L0/Проверка документов»  кнопка «Снять отметки о предоставлении документов» при нажатие статус документа меняется на «Документ не предоставлен»")
    @WorkItemIds({"2511899"})
    public void strategy_l0_2511900() {
        l0CheckingDocumentsPage
                .checkDocsOnStep(STEP_1)
                .clickOnNotProvidedIconForDoc("ФОТО", "Документ не предоставлен")
                .checkToolTipText("ФОТО", "Документ предоставлен")
                .clickOnNotProvidedIconForDoc("Справки, подтверждающие закрытие обязательств", "Документ не предоставлен")
                .checkToolTipText("Справки, подтверждающие закрытие обязательств", "Документ предоставлен")
                .assertElementByTitleActivity("Кнопка Снять отметки о предоставлении документов", "активен")
                .colorElementEquals("Кнопка Снять отметки о предоставлении документов", "rgb(202, 64, 57)")
                .checkToolTipTextElement("Кнопка Снять отметки о предоставлении документов", "Снять отметки о предоставлении документов")
                .clickOnElement(STEP_2)
                .checkDocsOnStep(STEP_2)
                .clickOnElement("Кнопка Взять шаг в работу").waitBusyCondition()
                .colorElementEquals(STEP_2, "rgba(0, 167, 112, 1)")
                .clickOnNotProvidedIconForDoc("2-НДФЛ", "Документ не предоставлен")
                .checkToolTipText("2-НДФЛ", "Документ предоставлен")
                .clickOnNotProvidedIconForDoc("Удостоверение военнослужащего", "Документ не предоставлен")
                .checkToolTipText("Удостоверение военнослужащего", "Документ предоставлен")
                .colorElementEquals("Кнопка Снять отметки о предоставлении документов", "rgb(202, 64, 57)")
                .assertElementByTitleActivity("Кнопка Снять отметки о предоставлении документов", "активен")
                .checkToolTipTextElement("Кнопка Снять отметки о предоставлении документов", "Снять отметки о предоставлении документов");
    }

    @Test
    @Tag("smoke")
    @Tag("strategy_l0_1645451")
    @DisplayName("1645451 - Верификация. Стратегия «L0/Проверка документов» запись о нетрудоспособности в ПФР» необходимо отобразить дополнительные поля с чек-боксами")
    @WorkItemIds({"1645451"})
    public void strategy_l0_1645451() {
        List<String> expectedTriggers = List.of(
                "Признаки подделки",
                "Признаки фальсификации",
                "Запись о нетрудоспособности в ПФР");
        String document = "Выписка из ПФР";
        l0CheckingDocumentsPage
                .checkDocsOnStep(STEP_1)
                .clickOnElement(STEP_2)
                .checkDocsOnStep(STEP_2)
                .clickOnElement("Кнопка Взять шаг в работу").waitBusyCondition()
                .clickOnNotProvidedIconForDoc(document, "Документ не предоставлен")
                .checkToolTipText(document, "Документ предоставлен");

        List<String> actualTriggers = l0CheckingDocumentsPage.getActualListTriggersByDoc(document);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.checkRadioButtonsForTrigger(document, trigger));
        assertEquals(expectedTriggers, actualTriggers, "Список триггеров для документа " + document + " не совпадает с актуальным: " + actualTriggers);

        l0CheckingDocumentsPage
                .clickButtonsForTrigger("Да", "Запись о нетрудоспособности в ПФР", document)
                .clickButtonsForTrigger("Нет", "Признаки подделки", document)
                .clickButtonsForTrigger("Нет", "Признаки фальсификации", document)
                .clickOnElement("Кнопка Далее")
                .checkElementByTitleContains("Модальное окно с сообщением", "Пожалуйста, заполните поле Дополнительные документы");
    }

    @Test
    @Tag("strategy_l0_1645440")
    @DisplayName("1645440 - Верификация. Стратегия «L0/Проверка документов» появляется модальное окно, содержащее текст: «Заполните признак фальсификации».Заемщик")
    @WorkItemIds({"1645440"})
    public void strategy_l0_1645440() {
        String document = "Справки, подтверждающие закрытие обязательств";
        List<String> expectedTriggers = List.of("Признаки фальсификации");

        l0CheckingDocumentsPage
                .checkDocsOnStep(STEP_1)
                .clickOnNotProvidedIconForDoc(document, "Документ не предоставлен");
        List<String> actualTriggers = l0CheckingDocumentsPage.getActualListTriggersByDoc(document);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.checkRadioButtonsForTrigger(document, trigger));
        assertEquals(expectedTriggers, actualTriggers, "Список триггеров для документа " + document + " не совпадает с актуальным: " + actualTriggers);

        l0CheckingDocumentsPage.clickButtonsForTrigger("Да", expectedTriggers.get(0), document);
        List<String> expectedListOfSigns = List.of("Наличие очевидных на копии документа признаков травления текста / подчисток / дописок / рисовок / вклейки бумаги / замены листов");
        List<String> actualListOfSigns = l0CheckingDocumentsPage.getActualListOfSigns("не выбранные");

        assertIsEquals(expectedListOfSigns, actualListOfSigns, "Детализация признаков фальсификации");

        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Готово на модальном окне Детализация признаков")
                .checkElementByTitleContains("Модальное окно с сообщением", "Заполните признак фальсификации");
    }

    @Test
    @Tag("strategy_l0_1645441")
    @DisplayName("1645441 - Верификация. Стратегия «L0/Проверка документов» появляется модальное окно, содержащее текст: «Заполните признак подделки». Основное место работы")
    @WorkItemIds({"1645441"})
    public void strategy_l0_1645441() {
        List<String> expectedTriggers = List.of(
                "Признаки подделки",
                "Признаки фальсификации");
        String document = "Справка по форме банка/работодателя";
        l0CheckingDocumentsPage.checkDocsOnStep(STEP_1)
                .clickOnElement(STEP_2)
                .checkDocsOnStep(STEP_2)
                .clickOnElement("Кнопка Взять шаг в работу").waitBusyCondition()
                .clickOnNotProvidedIconForDoc(document, "Документ не предоставлен")
                .checkToolTipText(document, "Документ предоставлен");

        List<String> actualTriggers = l0CheckingDocumentsPage.getActualListTriggersByDoc(document);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.checkRadioButtonsForTrigger(document, trigger));
        assertEquals(expectedTriggers, actualTriggers, "Список триггеров для документа " + document + " не совпадает с актуальным: " + actualTriggers);

        l0CheckingDocumentsPage.clickButtonsForTrigger("Да", expectedTriggers.get(0), document);
        List<String> expectedListOfSigns = List.of(
                "Гербовая печать (неприменимо к бюджетным / государственным организациям)",
                "Клиент работает в организации не с начала года, а справка предоставлена за весь год",
                "Исправления в дате выдачи справки или суммах дохода, а также иные исправления, препятствующие анализу данных справки",
                "Некорректная комбинация в номере балансовых счетов и/или некорректный код валюты",
                "Неверно указана сумма и нет логического обоснования");
        List<String> actualListOfSigns = l0CheckingDocumentsPage.getActualListOfSigns("не выбранные");

        assertIsEquals(expectedListOfSigns, actualListOfSigns, "Детализация признаков фальсификации");

        l0CheckingDocumentsPage
                .clickOnElement("Кнопка Готово на модальном окне Детализация признаков")
                .checkElementByTitleContains("Модальное окно с сообщением", "Заполните признак подделки");
    }

    @Test
    @Tag("smoke")
    @Tag("strategy_l0_1645432")
    @DisplayName("1645432 - Верификация. Стратегия «L0/Проверка документов»  поиск по списку признаков")
    @WorkItemIds({"1645432"})
    public void strategy_l0_1645432() {
        List<String> expectedTriggers = List.of(
                "Подпись в анкете и паспорте отличается",
                "Признаки фальсификации",
                "Отметка о рождении ребенка до 1,5 лет на момент обращения",
                "Регистрация в текущем регионе менее 1 года");
        String document = "Паспорт";
        l0CheckingDocumentsPage
                .checkDocsOnStep(STEP_1)
                .clickOnNotProvidedIconForDoc(document, "Документ не предоставлен");
        List<String> actualTriggers = l0CheckingDocumentsPage.getActualListTriggersByDoc(document);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.checkRadioButtonsForTrigger(document, trigger));
        assertEquals(expectedTriggers, actualTriggers, "Список триггеров для документа " + document + " не совпадает с актуальным: " + actualTriggers);

        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.clickButtonsForTrigger("Нет", trigger, document));
        l0CheckingDocumentsPage.clickOnElement("Кнопка Далее").waitBusyCondition()
                .checkDocsOnStep(STEP_2);
        String documentStep2 = "2-НДФЛ";
        l0CheckingDocumentsPage
                .clickOnNotProvidedIconForDoc(documentStep2, "Документ не предоставлен")
                .clickButtonsForTrigger("Да", "Признаки подделки", documentStep2)
                .fillInput("Поле Поиск признака", "Ф");

        List<String> expectedListOfSigns = List.of(
                "Различные шрифты в одинаковых полях, несоответствие символа @, разные отступы в начислениях",
                "Различие в ФИО и/или дате рождения Клиента между паспортом и справкой",
                "Сходство в подписях / почерках / фамилиях подписантов / записях их заверении",
                "Неправильное название компании - любая ошибка в организационно правовой форме или явная ошибка в названии");
        List<String> actualListOfSigns = l0CheckingDocumentsPage.getActualListOfSigns("не выбранные");
        assertIsEquals(expectedListOfSigns, actualListOfSigns, "Детализация признаков фальсификации");

        l0CheckingDocumentsPage
                .clear("Поле Поиск признака")
                .fillInput("Поле Поиск признака", "ду");
        expectedListOfSigns = List.of(
                "Дублирование реквизитов",
                "Различие в ФИО и/или дате рождения Клиента между паспортом и справкой");
        actualListOfSigns = l0CheckingDocumentsPage.getActualListOfSigns("не выбранные");
        assertIsEquals(expectedListOfSigns, actualListOfSigns, "Детализация признаков фальсификации");

        l0CheckingDocumentsPage
                .clear("Поле Поиск признака")
                .fillInput("Поле Поиск признака", "компании");
        expectedListOfSigns = List.of(
                "Неправильное название компании - любая ошибка в организационно правовой форме или явная ошибка в названии");
        actualListOfSigns = l0CheckingDocumentsPage.getActualListOfSigns("не выбранные");
        assertIsEquals(expectedListOfSigns, actualListOfSigns, "Детализация признаков фальсификации");
    }

    @Test
    @Tag("strategy_l0_1645442")
    @DisplayName("1645442 - Верификация. Стратегия «L0/Проверка документов» закрыть окно «Детализация признаков фальсификации», не выбирая признаков фальсификации, нажатием на крестик")
    @WorkItemIds({"1645442"})
    public void strategy_l0_1645442() {
        List<String> expectedTriggers = List.of(
                "Подпись в анкете и паспорте отличается",
                "Признаки фальсификации",
                "Отметка о рождении ребенка до 1,5 лет на момент обращения",
                "Регистрация в текущем регионе менее 1 года");
        String document = "Паспорт";
        l0CheckingDocumentsPage
                .checkDocsOnStep(STEP_1)
                .clickOnNotProvidedIconForDoc(document, "Документ не предоставлен");
        List<String> actualTriggers = l0CheckingDocumentsPage.getActualListTriggersByDoc(document);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.checkRadioButtonsForTrigger(document, trigger));
        assertEquals(expectedTriggers, actualTriggers, "Список триггеров для документа " + document + " не совпадает с актуальным: " + actualTriggers);

        l0CheckingDocumentsPage.clickButtonsForTrigger("Да", "Признаки фальсификации", document);
        List<String> expectedListOfSigns = List.of(
                "Несоответствие кода подразделения, выдавшего паспорт, на странице 2 коду подразделения в оттиске печати на этой же странице",
                "Реквизиты паспорта не соответствуют органу выдачи",
                "Отсутствие круглой печати",
                "Наличие очевидных на копии документа признаков травления текста / подчисток / дописок / рисовок / вклейки бумаги / замены листов / подделки подписи / признаков переклейки фотографии",
                "Ошибки в нумерации страниц, несовпадение номера и серии паспорта на предоставленных страницах, наличие грамматических ошибок",
                "Противоречия в тексте");
        List<String> actualListOfSigns = l0CheckingDocumentsPage.getActualListOfSigns("не выбранные");
        assertIsEquals(expectedListOfSigns, actualListOfSigns, "Детализация признаков фальсификации");
        l0CheckingDocumentsPage.clickOnElement("Кнопка Закрыть модальное окно Детализация признаков")
                .assertElementByTitleVisibility("Модальное окно Детализация признаков", "не отображается");
    }

    @Test
    @Tag("strategy_l0_1645443")
    @DisplayName("1645443 - Верификация. Стратегия «L0/Проверка документов» закрыть окно «Детализация признаков подделки», не выбирая признаков подделки, нажатием на крестик. Основное место работы")
    @WorkItemIds({"1645443"})
    public void strategy_l0_1645443() {
        List<String> expectedTriggers = List.of(
                "Доход по коду 2611",
                "Доход по коду 2013",
                "Доход по коду 2014",
                "Наличие сведений о ликвидации",
                "Признаки подделки",
                "Признаки фальсификации");
        String document = "2-НДФЛ";
        l0CheckingDocumentsPage.clickOnElement(STEP_2)
                .clickOnElement("Кнопка Взять шаг в работу").waitBusyCondition()
                .clickOnNotProvidedIconForDoc(document, "Документ не предоставлен");
        List<String> actualTriggers = l0CheckingDocumentsPage.getActualListTriggersByDoc(document);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.checkRadioButtonsForTrigger(document, trigger));
        assertEquals(expectedTriggers, actualTriggers, "Список триггеров для документа " + document + " не совпадает с актуальным: " + actualTriggers);

        l0CheckingDocumentsPage.clickButtonsForTrigger("Да", "Признаки подделки", document);
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
        List<String> actualListOfSigns = l0CheckingDocumentsPage.getActualListOfSigns("не выбранные");
        assertIsEquals(expectedListOfSigns, actualListOfSigns, "Детализация признаков фальсификации");
        l0CheckingDocumentsPage.clickOnElement("Кнопка Закрыть модальное окно Детализация признаков")
                .assertElementByTitleVisibility("Модальное окно Детализация признаков", "не отображается");
    }

    @Test
    @Tag("strategy_l0_1645425")
    @DisplayName("1645425 - Верификация. Стратегия «L0/Проверка документов» очистить введенные по документу результаты при изменение статуса \"Документа\" этап Заемщик")
    @WorkItemIds({"1645425"})
    public void strategy_l0_1645425() {
        String document = "ФОТО";
        List<String> expectedTriggers = List.of(
                "Признаки лица БОМЖ",
                "Фото клиента не соответствует фото в паспорте");

        l0CheckingDocumentsPage
                .checkDocsOnStep(STEP_1)
                .clickOnNotProvidedIconForDoc(document, "Документ не предоставлен");
        List<String> actualTriggers = l0CheckingDocumentsPage.getActualListTriggersByDoc(document);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.checkRadioButtonsForTrigger(document, trigger));
        assertEquals(expectedTriggers, actualTriggers, "Список триггеров для документа " + document + " не совпадает с актуальным: " + actualTriggers);
        l0CheckingDocumentsPage
                .clickButtonsForTrigger("Да", "Признаки лица БОМЖ", document)
                .clickButtonsForTrigger("Нет", "Фото клиента не соответствует фото в паспорте", document)
                .clickOnNotProvidedIconForDoc(document, "Документ предоставлен")
                .clickOnNotProvidedIconForDoc(document, "Документ не предоставлен")
                .checkToolTipText(document, "Документ предоставлен");
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.checkRadioButtonsForTrigger(document, trigger));
        assertEquals(expectedTriggers, actualTriggers, "Список триггеров для документа " + document + " не совпадает с актуальным: " + actualTriggers);
    }

    @Test
    @Tag("strategy_l0_1645426")
    @DisplayName("1645426 - Верификация. Стратегия «L0/Проверка документов» очистить введенные по документу результаты при изменение статуса \"Документа\" этап Заемщик. Основное место работы")
    @WorkItemIds({"1645426"})
    public void strategy_l0_1645426() {
        List<String> expectedTriggers = List.of(
                "Доход по коду 2611",
                "Доход по коду 2013",
                "Доход по коду 2014",
                "Наличие сведений о ликвидации",
                "Признаки подделки",
                "Признаки фальсификации");
        String document = "2-НДФЛ";
        l0CheckingDocumentsPage
                .checkDocsOnStep(STEP_1)
                .clickOnElement(STEP_2)
                .checkDocsOnStep(STEP_2)
                .clickOnElement("Кнопка Взять шаг в работу").waitBusyCondition()
                .clickOnNotProvidedIconForDoc(document, "Документ не предоставлен");
        List<String> actualTriggers = l0CheckingDocumentsPage.getActualListTriggersByDoc(document);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.checkRadioButtonsForTrigger(document, trigger));
        assertEquals(expectedTriggers, actualTriggers, "Список триггеров для документа " + document + " не совпадает с актуальным: " + actualTriggers);
        l0CheckingDocumentsPage
                .clickButtonsForTrigger("Да", "Наличие сведений о ликвидации", document)
                .clickButtonsForTrigger("Нет", "Признаки фальсификации", document)
                .clickOnNotProvidedIconForDoc(document, "Документ предоставлен")
                .clickOnNotProvidedIconForDoc(document, "Документ не предоставлен")
                .checkToolTipText(document, "Документ предоставлен");
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.checkRadioButtonsForTrigger(document, trigger));
        assertEquals(expectedTriggers, actualTriggers, "Список триггеров для документа " + document + " не совпадает с актуальным: " + actualTriggers);
    }

    @Test
    @Tag("strategy_l0_1645444")
    @DisplayName("1645444 - Верификация. Стратегия «L0/Проверка документов» редактирование результата выбора признаков фальсификации для проверки Заемщика")
    @WorkItemIds({"1645444"})
    public void strategy_l0_1645444() {
        List<String> expectedTriggers = List.of(
                "Подпись в анкете и паспорте отличается",
                "Признаки фальсификации",
                "Отметка о рождении ребенка до 1,5 лет на момент обращения",
                "Регистрация в текущем регионе менее 1 года");
        String document = "Паспорт";
        l0CheckingDocumentsPage
                .checkDocsOnStep(STEP_1)
                .clickOnNotProvidedIconForDoc(document, "Документ не предоставлен");
        List<String> actualTriggers = l0CheckingDocumentsPage.getActualListTriggersByDoc(document);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.checkRadioButtonsForTrigger(document, trigger));
        assertEquals(expectedTriggers, actualTriggers, "Список триггеров для документа " + document + " не совпадает с актуальным: " + actualTriggers);

        l0CheckingDocumentsPage.clickButtonsForTrigger("Да", "Признаки фальсификации", document);
        List<String> expectedListOfSigns = List.of(
                "Несоответствие кода подразделения, выдавшего паспорт, на странице 2 коду подразделения в оттиске печати на этой же странице",
                "Реквизиты паспорта не соответствуют органу выдачи",
                "Отсутствие круглой печати",
                "Наличие очевидных на копии документа признаков травления текста / подчисток / дописок / рисовок / вклейки бумаги / замены листов / подделки подписи / признаков переклейки фотографии",
                "Ошибки в нумерации страниц, несовпадение номера и серии паспорта на предоставленных страницах, наличие грамматических ошибок",
                "Противоречия в тексте");
        List<String> actualListOfSigns = l0CheckingDocumentsPage.getActualListOfSigns("не выбранные");
        assertIsEquals(expectedListOfSigns, actualListOfSigns, "Детализация признаков фальсификации");
        l0CheckingDocumentsPage
                .clickOnSign("Реквизиты паспорта не соответствуют органу выдачи")
                .clickOnSign("Отсутствие круглой печати")
                .clickOnElement("Кнопка Готово на модальном окне Детализация признаков");

        List<String> expectedListOfSignsSelect = List.of(
                "Реквизиты паспорта не соответствуют органу выдачи",
                "Отсутствие круглой печати");
        List<String> actualListOfSignsSelect = l0CheckingDocumentsPage.getActualListSignsByTrigger("Признаки фальсификации");
        assertIsEquals(expectedListOfSignsSelect, actualListOfSignsSelect, "Признаки под триггером Признаки фальсификации");

        l0CheckingDocumentsPage.clickButtonsForTrigger("Редактировать", "Признаки фальсификации", "Паспорт");
        actualListOfSignsSelect = l0CheckingDocumentsPage.getActualListOfSigns("выбранные");
        assertIsEquals(expectedListOfSigns, actualListOfSigns, "Детализация признаков фальсификации");
        assertIsEquals(expectedListOfSignsSelect, actualListOfSignsSelect, "Детализация признаков фальсификации (выбранные признаки)");

        l0CheckingDocumentsPage.clickOnSign("Несоответствие кода подразделения, выдавшего паспорт, на странице 2 коду подразделения в оттиске печати на этой же странице")
                .clickOnElement("Кнопка Готово на модальном окне Детализация признаков");
        expectedListOfSignsSelect = List.of(
                "Несоответствие кода подразделения, выдавшего паспорт, на странице 2 коду подразделения в оттиске печати на этой же странице",
                "Реквизиты паспорта не соответствуют органу выдачи",
                "Отсутствие круглой печати");
        actualListOfSignsSelect = l0CheckingDocumentsPage.getActualListSignsByTrigger("Признаки фальсификации");
        assertIsEquals(expectedListOfSignsSelect, actualListOfSignsSelect, "Признаки под триггером Признаки фальсификации");
        l0CheckingDocumentsPage.clickButtonsForTrigger("Редактировать", "Признаки фальсификации", "Паспорт");
        assertIsEquals(expectedListOfSigns, actualListOfSigns, "Детализация признаков фальсификации");
        assertIsEquals(expectedListOfSignsSelect, actualListOfSignsSelect, "Детализация признаков фальсификации (выбранные признаки)");

        l0CheckingDocumentsPage
                .clickOnSign("Несоответствие кода подразделения, выдавшего паспорт, на странице 2 коду подразделения в оттиске печати на этой же странице")
                .clickOnSign("Реквизиты паспорта не соответствуют органу выдачи")
                .clickOnElement("Кнопка Готово на модальном окне Детализация признаков");
        expectedListOfSignsSelect = List.of("Отсутствие круглой печати");
        actualListOfSignsSelect = l0CheckingDocumentsPage.getActualListSignsByTrigger("Признаки фальсификации");
        assertIsEquals(expectedListOfSignsSelect, actualListOfSignsSelect, "Признаки под триггером Признаки фальсификации");

        l0CheckingDocumentsPage
                .clickButtonsForTrigger("Редактировать", "Признаки фальсификации", "Паспорт");
        actualListOfSignsSelect = l0CheckingDocumentsPage.getActualListOfSigns("выбранные");
        assertIsEquals(expectedListOfSigns, actualListOfSigns, "Детализация признаков фальсификации");
        assertIsEquals(expectedListOfSignsSelect, actualListOfSignsSelect, "Детализация признаков фальсификации (выбранные признаки)");

        l0CheckingDocumentsPage
                .clickOnSign("Отсутствие круглой печати")
                .clickOnElement("Кнопка Готово на модальном окне Детализация признаков")
                .checkElementByTitleContains("Модальное окно с сообщением", "Заполните признак фальсификации")
                .clickOnElement("Кнопка ОК")
                .clickOnElement("Кнопка Закрыть модальное окно Детализация признаков");
        actualListOfSignsSelect = l0CheckingDocumentsPage.getActualListSignsByTrigger("Признаки фальсификации");
        assertIsEquals(expectedListOfSignsSelect, actualListOfSignsSelect, "Признаки под триггером Признаки фальсификации");
    }

    @Test
    @Tag("strategy_l0_1645445")
    @DisplayName("1645445 - Верификация. Стратегия «L0/Проверка документов» редактирование результата выбора признаков подделки для проверки Основное место работы")
    @WorkItemIds({"1645445"})
    public void strategy_l0_1645445() {
        List<String> expectedTriggers = List.of(
                "Доход по коду 2611",
                "Доход по коду 2013",
                "Доход по коду 2014",
                "Наличие сведений о ликвидации",
                "Признаки подделки",
                "Признаки фальсификации");
        String document = "2-НДФЛ";
        l0CheckingDocumentsPage
                .checkDocsOnStep(STEP_1)
                .clickOnElement(STEP_2)
                .checkDocsOnStep(STEP_2)
                .clickOnElement("Кнопка Взять шаг в работу").waitBusyCondition()
                .colorElementEquals(STEP_2, "rgba(0, 167, 112, 1)")
                .clickOnNotProvidedIconForDoc(document, "Документ не предоставлен");
        List<String> actualTriggers = l0CheckingDocumentsPage.getActualListTriggersByDoc(document);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.checkRadioButtonsForTrigger(document, trigger));
        assertEquals(expectedTriggers, actualTriggers, "Список триггеров для документа " + document + " не совпадает с актуальным: " + actualTriggers);

        l0CheckingDocumentsPage.clickButtonsForTrigger("Да", "Признаки подделки", document);
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
        List<String> actualListOfSigns = l0CheckingDocumentsPage.getActualListOfSigns("не выбранные");
        assertIsEquals(expectedListOfSigns, actualListOfSigns, "Детализация признаков подделки");
        l0CheckingDocumentsPage
                .clickOnSign("Не указаны коды доходов")
                .clickOnElement("Кнопка Готово на модальном окне Детализация признаков");

        List<String> expectedListOfSignsSelect = List.of("Не указаны коды доходов");
        List<String> actualListOfSignsSelect = l0CheckingDocumentsPage.getActualListSignsByTrigger("Признаки подделки");
        assertIsEquals(expectedListOfSignsSelect, actualListOfSignsSelect, "Признаки под триггером Признаки подделки");

        l0CheckingDocumentsPage.clickButtonsForTrigger("Редактировать", "Признаки подделки", document);
        actualListOfSignsSelect = l0CheckingDocumentsPage.getActualListOfSigns("выбранные");
        assertIsEquals(expectedListOfSigns, actualListOfSigns, "Детализация признаков подделки");
        assertIsEquals(expectedListOfSignsSelect, actualListOfSignsSelect, "Детализация признаков подделки (выбранные признаки)");

        l0CheckingDocumentsPage
                .clickOnSign("Дублирование реквизитов")
                .clickOnSign("Неправильное название компании - любая ошибка в организационно правовой форме или явная ошибка в названии")
                .clickOnElement("Кнопка Готово на модальном окне Детализация признаков");
        expectedListOfSignsSelect = List.of(
                "Дублирование реквизитов",
                "Не указаны коды доходов",
                "Неправильное название компании - любая ошибка в организационно правовой форме или явная ошибка в названии");
        actualListOfSignsSelect = l0CheckingDocumentsPage.getActualListSignsByTrigger("Признаки подделки");
        assertIsEquals(expectedListOfSignsSelect, actualListOfSignsSelect, "Признаки под триггером Признаки подделки");
        l0CheckingDocumentsPage.clickButtonsForTrigger("Редактировать", "Признаки подделки", document);
        assertIsEquals(expectedListOfSigns, actualListOfSigns, "Детализация признаков подделки");
        assertIsEquals(expectedListOfSignsSelect, actualListOfSignsSelect, "Детализация признаков подделки (выбранные признаки)");

        l0CheckingDocumentsPage
                .clickOnSign("Дублирование реквизитов")
                .clickOnSign("Не указаны коды доходов")
                .clickOnSign("Неправильное название компании - любая ошибка в организационно правовой форме или явная ошибка в названии")
                .clickOnElement("Кнопка Готово на модальном окне Детализация признаков")
                .checkElementByTitleContains("Модальное окно с сообщением", "Заполните признак подделки")
                .clickOnElement("Кнопка ОК")
                .clickOnElement("Кнопка Закрыть модальное окно Детализация признаков");
        actualListOfSignsSelect = l0CheckingDocumentsPage.getActualListSignsByTrigger("Признаки подделки");
        assertIsEquals(expectedListOfSignsSelect, actualListOfSignsSelect, "Признаки под триггером Признаки подделки");

    }

    @Test
    @Tag("strategy_l0_1645450")
    @DisplayName("1645450 - Верификация. Стратегия «L0/Проверка документов» при проставлении чек-бокса «Да» для риска «Отметка о рождении ребенка до 1,5 лет на момент обращения»» дополнительные поля должны быть заполнены.")
    @WorkItemIds({"1645450"})
    public void strategy_l0_1645450() {
        List<String> expectedTriggers = List.of(
                "Подпись в анкете и паспорте отличается",
                "Признаки фальсификации",
                "Отметка о рождении ребенка до 1,5 лет на момент обращения",
                "Регистрация в текущем регионе менее 1 года");
        String document = "Паспорт";
        l0CheckingDocumentsPage
                .checkDocsOnStep(STEP_1)
                .clickOnNotProvidedIconForDoc(document, "Документ не предоставлен");
        List<String> actualTriggers = l0CheckingDocumentsPage.getActualListTriggersByDoc(document);
        expectedTriggers.forEach(trigger -> l0CheckingDocumentsPage.checkRadioButtonsForTrigger(document, trigger));
        assertEquals(expectedTriggers, actualTriggers, "Список триггеров для документа " + document + " не совпадает с актуальным: " + actualTriggers);

        l0CheckingDocumentsPage
                .clickButtonsForTrigger("Да", "Отметка о рождении ребенка до 1,5 лет на момент обращения", document)
                .clickButtonsForTrigger("Нет", "Подпись в анкете и паспорте отличается", document)
                .clickButtonsForTrigger("Нет", "Признаки фальсификации", document)
                .clickButtonsForTrigger("Нет", "Регистрация в текущем регионе менее 1 года", document)
                .clickButtonsForAddField("Нет", "Должность предполагает работу на дому", "Отметка о рождении ребенка до 1,5 лет на момент обращения")
                .clickOnElement("Кнопка Далее")
                .checkElementByTitleContains("Модальное окно с сообщением", "Пожалуйста, заполните результаты проверки")
                .clickOnElement("Кнопка ОК")
                .clickButtonsForTrigger("Нет", "Отметка о рождении ребенка до 1,5 лет на момент обращения", document)
                .clickButtonsForTrigger("Да", "Отметка о рождении ребенка до 1,5 лет на момент обращения", document)
                .clickOnElement("Кнопка Далее")
                .checkElementByTitleContains("Модальное окно с сообщением", "Пожалуйста, заполните результаты проверки")
                .clickOnElement("Кнопка ОК")
                .clickButtonsForAddField("Нет данных", "Доход менялся в период рождения ребенка", "Отметка о рождении ребенка до 1,5 лет на момент обращения")
                .clickOnElement("Кнопка Далее")
                .checkElementByTitleContains("Модальное окно с сообщением", "Пожалуйста, заполните результаты проверки")
                .clickOnElement("Кнопка ОК");
    }
}