package ru.autotestframework.regress.card_request.verification.strategies_verification.go_card_ef;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

import static ru.autotestframework.steps.asserts.Asserts.assertContains;
import static ru.autotestframework.steps.asserts.Asserts.assertIsEquals;
import static ru.autotestframework.utils.Constants.DF;
import static ru.autotestframework.utils.Constants.VERIFICATION;

@Tag("regress")
@Tag("card_request")
@Tag("verification")
@Tag("go_card_ef")
@Tag("on_claim_2_go_employer_verification")
@ClassName("Карточка заявки. Верификация. ЭФ стратегий верификации. ГО карточка ЭФ. На каждый кейс отдельная заявка ЭФ ГО")
public class EfGoSeparateClaimTest extends BaseTest {

    private String claim;

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

    @AfterEach
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("ef_go_2652422")
    @DisplayName("2652422 - Экранная форма стратегии \"Вопрос в ГО\". Отображение в поле \"Объект проверки\". Основное место работы")
    @WorkItemIds({"2652422"})
    public void ef_go_2652422(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of("Code", "stub1");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3209103.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(l0CheckingDocumentsPage)
                .waitBusyCondition()
                .elementByTitleContains("Поле Наименование стратегии", "L0.Проверка документов")
                .clickOnElement("Проверка Заемщик. Основное место работы")
                .clickOnElement("Кнопка Взять шаг в работу")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Методологический")
                .fillInput("Поле Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .switchToOneTab();
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.elementByTitleVisibility("Раздел Вопрос в ГО", "отображается")
                .clickOnElement("Раздел Вопрос в ГО")
                .doubleClickByText(claim)
                .goTo(questionInGoPage)
                .switchToNewTab()
                .elementByTitleContains("Поле Наименование стратегии", "Вопрос в ГО");
        List<String> values = questionInGoPage.getListValuesByColumnName("Таблица Степпер", "Объект проверки");
        assertContains(values.toString(), "Заемщик - Романов Юрий Иванович 05.12.1990");
        assertContains(values.toString(), "Основное место работы - ФКУ \"ЕРЦ МО РФ\" (В/Ч №09436)");
        questionInGoPage.closeCurrentTab();
    }

    @Test
    @Tag("ef_go_3208482")
    @DisplayName("3208482 - Экранная форма стратегии \"Вопрос в ГО\". Выбор проверок в поле \"Дополнительные проверки\"")
    @WorkItemIds({"3208482"})
    public void ef_go_3208482(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "codeBor", "stub1",
                "codeCobor", "stub1");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3234469.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(fsspPage)
                .waitBusyCondition()
                .elementByTitleContains("Поле Наименование стратегии", "ФССП")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Методологический")
                .fillInput("Поле Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .switchToOneTab();
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.elementByTitleVisibility("Раздел Вопрос в ГО", "отображается")
                .clickOnElement("Раздел Вопрос в ГО")
                .doubleClickByText(claim)
                .goTo(questionInGoPage)
                .switchToNewTab()
                .elementByTitleContains("Поле Наименование стратегии", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Назначить дополнительную проверку с возвратом на этап ГО")
                .expandBlocks("ФССП")
                .assertCheckboxState("ФССП > Заемщик - Иванов Дмитрий Юрьевич", "выключен")
                .assertCheckboxState("ФССП > Созаемщик - Павлов Павел Павлович", "выключен")
                .clickCheckbox("ФССП > Заемщик - Иванов Дмитрий Юрьевич")
                .assertCheckboxState("ФССП > Заемщик - Иванов Дмитрий Юрьевич", "включен")
                .assertCheckboxState("ФССП > Созаемщик - Павлов Павел Павлович", "выключен")
                .assertCheckboxState("ФССП", "промежуточный")
                .clickCheckbox("ФССП > Созаемщик - Павлов Павел Павлович")
                .assertCheckboxState("ФССП > Созаемщик - Павлов Павел Павлович", "включен")
                .assertCheckboxState("ФССП", "включен")

                .expandBlocks("Проверка дохода")
                .assertCheckboxState("Проверка дохода > Заемщик - Иванов Дмитрий Юрьевич", "выключен")
                .assertCheckboxState("Проверка дохода > Созаемщик - Павлов Павел Павлович", "выключен")
                .expandBlocks("Проверка дохода > Заемщик - Иванов Дмитрий Юрьевич")
                .assertCheckboxState("Проверка дохода > Заемщик - Иванов Дмитрий Юрьевич > Основное место работы - УПРАВЛЕНИЕ МИНИСТЕРСТВА ВНУТРЕННИХ ДЕЛ РОССИЙСКОЙ ФЕДЕРАЦИИ ПО ЕВРЕЙСКОЙ АВТОНОМНОЙ ОБЛАСТИ", "выключен")
                .assertCheckboxState("Проверка дохода > Заемщик - Иванов Дмитрий Юрьевич > Совместительство - Суперклассное", "выключен")
                .clickCheckbox("Проверка дохода > Заемщик - Иванов Дмитрий Юрьевич")
                .assertCheckboxState("Проверка дохода > Заемщик - Иванов Дмитрий Юрьевич", "включен")
                .assertCheckboxState("Проверка дохода > Заемщик - Иванов Дмитрий Юрьевич > Основное место работы - УПРАВЛЕНИЕ МИНИСТЕРСТВА ВНУТРЕННИХ ДЕЛ РОССИЙСКОЙ ФЕДЕРАЦИИ ПО ЕВРЕЙСКОЙ АВТОНОМНОЙ ОБЛАСТИ", "включен")
                .assertCheckboxState("Проверка дохода > Заемщик - Иванов Дмитрий Юрьевич > Совместительство - Суперклассное", "включен")
                .clickCheckbox("Проверка дохода > Заемщик - Иванов Дмитрий Юрьевич > Основное место работы - УПРАВЛЕНИЕ МИНИСТЕРСТВА ВНУТРЕННИХ ДЕЛ РОССИЙСКОЙ ФЕДЕРАЦИИ ПО ЕВРЕЙСКОЙ АВТОНОМНОЙ ОБЛАСТИ")
                .assertCheckboxState("Проверка дохода > Заемщик - Иванов Дмитрий Юрьевич > Основное место работы - УПРАВЛЕНИЕ МИНИСТЕРСТВА ВНУТРЕННИХ ДЕЛ РОССИЙСКОЙ ФЕДЕРАЦИИ ПО ЕВРЕЙСКОЙ АВТОНОМНОЙ ОБЛАСТИ", "выключен")
                .assertCheckboxState("Проверка дохода > Заемщик - Иванов Дмитрий Юрьевич", "промежуточный")
                .assertCheckboxState("Проверка дохода", "промежуточный")
                .expandBlocks("Проверка дохода > Созаемщик - Павлов Павел Павлович")
                .assertCheckboxState("Проверка дохода > Созаемщик - Павлов Павел Павлович > Основное место работы - ФКУ \"ЕРЦ МО РФ\" (В/Ч №09436)", "выключен")
                .assertCheckboxState("Проверка дохода > Созаемщик - Павлов Павел Павлович > Совместительство - Какая-то работа", "выключен")
                .clickCheckbox("Проверка дохода > Созаемщик - Павлов Павел Павлович")
                .assertCheckboxState("Проверка дохода > Созаемщик - Павлов Павел Павлович > Основное место работы - ФКУ \"ЕРЦ МО РФ\" (В/Ч №09436)", "включен")
                .assertCheckboxState("Проверка дохода > Созаемщик - Павлов Павел Павлович > Совместительство - Какая-то работа", "включен")
                .clickCheckbox("Проверка дохода > Созаемщик - Павлов Павел Павлович")
                .assertCheckboxState("Проверка дохода > Созаемщик - Павлов Павел Павлович > Основное место работы - ФКУ \"ЕРЦ МО РФ\" (В/Ч №09436)", "выключен")
                .assertCheckboxState("Проверка дохода > Созаемщик - Павлов Павел Павлович > Совместительство - Какая-то работа", "выключен")
                .clickCheckbox("Проверка дохода > Созаемщик - Павлов Павел Павлович")
                .clickCheckbox("Проверка дохода > Созаемщик - Павлов Павел Павлович > Основное место работы - ФКУ \"ЕРЦ МО РФ\" (В/Ч №09436)")
                .clickCheckbox("Проверка дохода > Созаемщик - Павлов Павел Павлович > Совместительство - Какая-то работа")
                .assertCheckboxState("Проверка дохода > Созаемщик - Павлов Павел Павлович > Основное место работы - ФКУ \"ЕРЦ МО РФ\" (В/Ч №09436)", "выключен")
                .assertCheckboxState("Проверка дохода > Созаемщик - Павлов Павел Павлович > Совместительство - Какая-то работа", "выключен")
                .assertCheckboxState("Проверка дохода > Созаемщик - Павлов Павел Павлович ", "выключен")
                .closeCurrentTab();
    }

    @Test
    @Tag("ef_go_2652418")
    @DisplayName("2652418 - Экранная форма стратегии \"Вопрос в ГО\". Отображение в поле \"Объект проверки\". Контактное лицо")
    @WorkItemIds({"2652418"})
    public void ef_go_2652418(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "Code1", "stub11",
                "Code2", "stub12");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3208874.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .waitBusyCondition()
                .goTo(customerCallPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон клиента/Версия 1")
                .clickOnStep("Прозвон контактного лица/супруга (-и)")
                .goTo(callContactPersonSpoursePage)
                .clickOnElement("Кнопка Взять шаг в работу")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Методологический")
                .fillInput("Поле ввода Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .switchToOneTab();
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.elementByTitleVisibility("Раздел Вопрос в ГО", "отображается")
                .clickOnElement("Раздел Вопрос в ГО")
                .doubleClickByText(claim)
                .goTo(questionInGoPage)
                .switchToNewTab()
                .elementByTitleContains("Поле Наименование стратегии", "Вопрос в ГО");
        List<String> values = questionInGoPage.getListValuesByColumnName("Таблица Степпер", "Объект проверки");
        assertContains(values.toString(), "Заемщик - Романов Юрий Иванович 05.12.1990");
        assertContains(values.toString(), "Контактное лицо - Барабанов Игорь Петрович");
        questionInGoPage.closeCurrentTab();
    }

    @Test
    @Tag("ef_go_3208435")
    @DisplayName("3208435 - Экранная форма стратегии \"Вопрос в ГО\". Отображение в поле \"Объект проверки\". Совместительство")
    @WorkItemIds({"3208435"})
    public void ef_go_3208435(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of("Code", "stub5");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3209048.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .waitBusyCondition()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка дохода/Версия 1")
                .clickOnElement("Иконка Второй этап (неактивная)")
                .clickOnElement("Кнопка Взять шаг в работу")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Методологический")
                .fillInput("Поле ввода Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .switchToOneTab();
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.elementByTitleVisibility("Раздел Вопрос в ГО", "отображается")
                .clickOnElement("Раздел Вопрос в ГО")
                .doubleClickByText(claim)
                .goTo(questionInGoPage)
                .switchToNewTab()
                .elementByTitleContains("Поле Наименование стратегии", "Вопрос в ГО");
        List<String> values = questionInGoPage.getListValuesByColumnName("Таблица Степпер", "Объект проверки");
        assertContains(values.toString(), "Заемщик - Романов Юрий Иванович 05.12.1990");
        assertContains(values.toString(), "Совместительство - Какая-то работа");
        questionInGoPage.closeCurrentTab();
    }

    @Test
    @Tag("ef_go_3208475")
    @DisplayName("3208475 - Экранная форма стратегии \"Вопрос в ГО\". Отображение в поле \"Объект проверки\". Супруга")
    @WorkItemIds({"3208475"})
    public void ef_go_3208475(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "Code1", "stub11",
                "Code2", "stub12");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3224728.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .waitBusyCondition()
                .goTo(customerCallPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон клиента/Версия 1")
                .clickOnStep("Прозвон контактного лица/супруга (-и)")
                .goTo(callContactPersonSpoursePage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон контактного лица/супруга (-и)/Версия 1")
                .clickOnElement("Кнопка Взять шаг в работу")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Методологический")
                .fillInput("Поле ввода Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .switchToOneTab();
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.elementByTitleVisibility("Раздел Вопрос в ГО", "отображается")
                .clickOnElement("Раздел Вопрос в ГО")
                .doubleClickByText(claim)
                .goTo(questionInGoPage)
                .switchToNewTab()
                .elementByTitleContains("Поле Наименование стратегии", "Вопрос в ГО");
        List<String> values = questionInGoPage.getListValuesByColumnName("Таблица Степпер", "Объект проверки");
        assertContains(values.toString(), "Заемщик - Романов Юрий Иванович 05.12.1990");
        assertContains(values.toString(), "Супруг(-а) - Романова Юлия Михайловна 05.03.1993");
        questionInGoPage.closeCurrentTab();
    }

    @Test
    @Tag("ef_go_3208431")
    @DisplayName("3208431 - Экранная форма стратегии \"Вопрос в ГО\". Отображение в поле \"Объект проверки\". Созаемщик")
    @WorkItemIds({"3208431"})
    public void ef_go_3208431(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "codeBor", "stub1",
                "codeCobor", "stub1");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3234469.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .waitBusyCondition()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .clickOnElement("Шаг №2. Созаемщик")
                .clickOnElement("Кнопка Взять шаг в работу")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Методологический")
                .fillInput("Поле ввода Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .switchToOneTab();
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.elementByTitleVisibility("Раздел Вопрос в ГО", "отображается")
                .clickOnElement("Раздел Вопрос в ГО")
                .doubleClickByText(claim)
                .goTo(questionInGoPage)
                .switchToNewTab()
                .elementByTitleContains("Поле Наименование стратегии", "Вопрос в ГО");
        List<String> values = questionInGoPage.getListValuesByColumnName("Таблица Степпер", "Объект проверки");
        assertContains(values.toString(), "Созаемщик - Павлов Павел Павлович 05.12.1995");
        questionInGoPage.closeCurrentTab();
    }

    @Test
    @Tag("ef_go_2652430")
    @DisplayName("2652430 - Кнопка Ответ от ГО. Заполнение поля \"Объект проверки\" для типа \"Ответ\". Открытые источники")
    @WorkItemIds({"2652430"})
    public void ef_go_2652430(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of("Code", "stub7");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3209048.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .waitBusyCondition()
                .goTo(checkingOpenSourcesPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка открытых источников/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Методологический")
                .fillInput("Поле ввода Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .switchToOneTab();
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.elementByTitleVisibility("Раздел Вопрос в ГО", "отображается")
                .clickOnElement("Раздел Вопрос в ГО")
                .doubleClickByText(claim)
                .goTo(questionInGoPage)
                .switchToNewTab()
                .elementByTitleContains("Поле Наименование стратегии", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Вопрос решен")
                .fillInput("Поле Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .switchToOneTab()
                .clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .waitBusyCondition()
                .goTo(checkingOpenSourcesPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка открытых источников/Версия 2")
                .clickOnElement("Кнопка Ответ от ГО");
        String value = checkingOpenSourcesPage.getTextFromTable("Таблица Ответ от ГО", 1, "Объект проверки");
        assertContains(value, "Заемщик - Романов Юрий Иванович 05.12.1990");
        checkingOpenSourcesPage.closeCurrentTab();
    }

    @Test
    @Tag("ef_go_2652428")
    @DisplayName("2652428 - Кнопка Ответ от ГО. Заполнение поля \"Объект проверки\" для типа \"Вопрос\". Совместительство")
    @WorkItemIds({"2652428"})
    public void ef_go_2652428(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of("Code", "stub7");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3209048.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .waitBusyCondition()
                .goTo(checkingOpenSourcesPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка открытых источников/Версия 1")
                .clickOnElement("Шаг №2. Заёмщик. Совместительство")
                .clickOnElement("Кнопка Взять шаг в работу")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Методологический")
                .fillInput("Поле ввода Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .switchToOneTab();
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.elementByTitleVisibility("Раздел Вопрос в ГО", "отображается")
                .clickOnElement("Раздел Вопрос в ГО")
                .doubleClickByText(claim)
                .goTo(questionInGoPage)
                .switchToNewTab()
                .elementByTitleContains("Поле Наименование стратегии", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Вопрос решен")
                .fillInput("Поле Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .switchToOneTab()
                .clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .waitBusyCondition()
                .goTo(checkingOpenSourcesPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка открытых источников/Версия 2")
                .clickOnElement("Кнопка Ответ от ГО");
        String value = checkingOpenSourcesPage.getTextFromTable("Таблица Ответ от ГО", 2, "Объект проверки");
        assertContains(value, "Заемщик - Романов Юрий Иванович 05.12.1990\n" +
                "Совместительство - Какая-то работа");
        checkingOpenSourcesPage.closeCurrentTab();
    }

    @Test
    @Tag("ef_go_2652417")
    @DisplayName("2652417 - Кнопка Ответ от ГО. Заполнение поля \"Объект проверки\" для типа \"Вопрос\". Совместительство")
    @WorkItemIds({"2652417"})
    public void ef_go_2652417(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of("Code", "stub1");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3209048.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .waitBusyCondition()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Подозрение на Fraud")
                .fillInput("Поле ввода Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .switchToOneTab();
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.elementByTitleVisibility("Раздел Вопрос в ГО", "отображается")
                .clickOnElement("Раздел Вопрос в ГО")
                .doubleClickByText(claim)
                .goTo(questionInGoPage)
                .switchToNewTab()
                .elementByTitleContains("Поле Наименование стратегии", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Технический")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Вопрос решен")
                .fillInput("Поле Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .switchToOneTab()
                .clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .waitBusyCondition()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 2")
                .clickOnElement("Кнопка Ответ от ГО");
        String value = checkingOpenSourcesPage.getTextFromTable("Таблица Ответ от ГО", 1, "Тип вопроса");
        assertContains(value, "Технический");
        checkingOpenSourcesPage.closeCurrentTab();
    }

    @Test
    @Tag("ef_go_3208473")
    @DisplayName("3208473 - Экранная форма стратегии \"Вопрос в ГО\". Дополнительные блоки для Супруга в поле \"Дополнительные проверки\"")
    @WorkItemIds({"3208473"})
    public void ef_go_3208473(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of("Code", "stub1");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3209048.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .waitBusyCondition()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Подозрение на Fraud")
                .fillInput("Поле ввода Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .switchToOneTab();
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.elementByTitleVisibility("Раздел Вопрос в ГО", "отображается")
                .clickOnElement("Раздел Вопрос в ГО")
                .doubleClickByText(claim)
                .goTo(questionInGoPage)
                .switchToNewTab()
                .elementByTitleContains("Поле Наименование стратегии", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Назначить дополнительную проверку с возвратом на этап ГО")
                .expandBlocks("Прозвон контактного лица/супруга (-и)")
                .assertCheckboxState("Прозвон контактного лица/супруга (-и) > Контактное лицо/супруг заемщика - Барабанов Игорь Петрович", "выключен")
                .closeCurrentTab();
    }

    @Test
    @Tag("ef_go_3208472")
    @DisplayName("3208472 - Экранная форма стратегии \"Вопрос в ГО\". Дополнительные блоки для Работодателя+Созаёмщик в поле \"Дополнительные проверки\"")
    @WorkItemIds({"3208472"})
    public void ef_go_3208472(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "codeBor", "stub1",
                "codeCobor", "stub1");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3234469.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .waitBusyCondition()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Подозрение на Fraud")
                .fillInput("Поле ввода Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .switchToOneTab();
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.elementByTitleVisibility("Раздел Вопрос в ГО", "отображается")
                .clickOnElement("Раздел Вопрос в ГО")
                .doubleClickByText(claim)
                .goTo(questionInGoPage)
                .switchToNewTab()
                .elementByTitleContains("Поле Наименование стратегии", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Назначить дополнительную проверку с возвратом на этап ГО");
        List<String> parentBlocks = List.of("Проверка дохода", "Проверка сайта", "Проверка работодателя",
                "Привязка телефона из анкеты", "Бесконтактное подтверждение трудоустройства", "Прозвон работодателя - любой телефон",
                "Прозвон работодателя - подтвержденный телефон", "Прозвон работодателя - любой телефон (Обязательный)", "Прозвон работодателя - подтвержденный телефон (обязательный)");

        for (String parentBlock : parentBlocks) {
            questionInGoPage
                    .expandBlocks(parentBlock)
                    .assertSortHeaders(
                            List.of(parentBlock + " > Заемщик - Иванов Дмитрий Юрьевич",
                                    parentBlock + " > Созаемщик - Павлов Павел Павлович"))
                    .expandBlocks(parentBlock + " > Заемщик - Иванов Дмитрий Юрьевич")
                    .assertSortHeaders(
                            List.of(parentBlock + " > Заемщик - Иванов Дмитрий Юрьевич > Основное место работы - УПРАВЛЕНИЕ МИНИСТЕРСТВА ВНУТРЕННИХ ДЕЛ РОССИЙСКОЙ ФЕДЕРАЦИИ ПО ЕВРЕЙСКОЙ АВТОНОМНОЙ ОБЛАСТИ",
                                    parentBlock + " > Заемщик - Иванов Дмитрий Юрьевич > Совместительство - Суперклассное"))
                    .expandBlocks(parentBlock + " > Созаемщик - Павлов Павел Павлович")
                    .assertSortHeaders(
                            List.of(parentBlock + " > Созаемщик - Павлов Павел Павлович > Основное место работы - ФКУ \"ЕРЦ МО РФ\" (В/Ч №09436)",
                                    parentBlock + " > Созаемщик - Павлов Павел Павлович > Совместительство - Какая-то работа"));
        }
        questionInGoPage.closeCurrentTab();
    }

    @Test
    @Tag("ef_go_3208484")
    @DisplayName("3208484 - Экранная форма стратегии \"Вопрос в ГО\". Дополнительные блоки для Созаёмщика и Конт. лица в поле \"Дополнительные проверки\"")
    @WorkItemIds({"3208484"})
    public void ef_go_3208484(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "codeBor", "stub1",
                "codeCobor", "stub1");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3234469.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .waitBusyCondition()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Подозрение на Fraud")
                .fillInput("Поле ввода Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .switchToOneTab();
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.elementByTitleVisibility("Раздел Вопрос в ГО", "отображается")
                .clickOnElement("Раздел Вопрос в ГО")
                .doubleClickByText(claim)
                .goTo(questionInGoPage)
                .switchToNewTab()
                .elementByTitleContains("Поле Наименование стратегии", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Назначить дополнительную проверку с возвратом на этап ГО");
        List<String> parentBlocks = List.of("ФССП", "Проверка предыдущих заявок", "Прозвон клиента");

        for (String parentBlock : parentBlocks) {
            questionInGoPage
                    .expandBlocks(parentBlock)
                    .assertSortHeaders(
                            List.of(parentBlock + " > Заемщик - Иванов Дмитрий Юрьевич",
                                    parentBlock + " > Созаемщик - Павлов Павел Павлович"))
                    .assertHeaderNotContains(parentBlock + " > Место работы");
        }
        questionInGoPage
                .expandBlocks("Прозвон контактного лица/супруга (-и)")
                .assertSortHeaders(
                        List.of("Прозвон контактного лица/супруга (-и) > Контактное лицо/супруг заемщика - Иванов Юрий Петрович",
                                "Прозвон контактного лица/супруга (-и) > Контактное лицо/супруг созаемщика - Барабанов Игорь Петрович"))
                .assertHeaderNotContains("Прозвон контактного лица/супруга (-и) > Место работы")
                .closeCurrentTab();
    }

    @Test
    @Tag("ef_go_3208486")
    @DisplayName("3208486 - Экранная форма стратегии \"Вопрос в ГО\". Отображение в поле \"Тип вопроса\" по последней версии \"L0. Проверка документов\"")
    @WorkItemIds({"3208486"})
    public void ef_go_3208486(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of("Code", "stub1");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3209103.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .waitBusyCondition()
                .goTo(l0CheckingDocumentsPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "L0.Проверка документов/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Методологический")
                .fillInput("Поле Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .switchToOneTab();
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.elementByTitleVisibility("Раздел Вопрос в ГО", "отображается")
                .clickOnElement("Раздел Вопрос в ГО")
                .doubleClickByText(claim)
                .goTo(questionInGoPage)
                .switchToNewTab()
                .checkElementByTitleEquals("Поле Наименование стратегии", "Вопрос в ГО/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Вопрос решен")
                .fillInput("Поле Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab()
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .waitBusyCondition()
                .goTo(l0CheckingDocumentsPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "L0.Проверка документов/Версия 2")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Технический")
                .fillInput("Поле Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab()
                .goTo(personalAccountPage)
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim)
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(questionInGoPage)
                .switchToNewTab()
                .checkElementByTitleEquals("Поле Наименование стратегии", "Вопрос в ГО/Версия 2");
        assertIsEquals("Технический", questionInGoPage.getTextFromTable("Таблица Степпер", 1, "Тип вопроса"),
                "Значение в столбце Тип вопроса");
        checkingOpenSourcesPage.closeCurrentTab();
    }

    @Test
    @Tag("ef_go_3208483")
    @DisplayName("3208483 - Экранная форма стратегии \"Вопрос в ГО\". Отображение в поле \"Стратегия\" по последней версии. Прозвон клиента")
    @WorkItemIds({"3208483"})
    public void ef_go_3208483(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "Code1", "stub1",
                "Code2", "stub11");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3208874.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .waitBusyCondition()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Методологический")
                .fillInput("Поле Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab()
                .goTo(personalAccountPage);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.elementByTitleVisibility("Раздел Вопрос в ГО", "отображается")
                .clickOnElement("Раздел Вопрос в ГО")
                .doubleClickByText(claim)
                .goTo(questionInGoPage)
                .switchToNewTab()
                .checkElementByTitleEquals("Поле Наименование стратегии", "Вопрос в ГО/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Вопрос решен")
                .fillInput("Поле Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab()
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .waitBusyCondition()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 2")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Одобрить стратегию")
                .fillInput("Поле Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab()
                .goTo(personalAccountPage);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.clickOnElement("Раздел Верификация")
                .waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .waitBusyCondition()
                .goTo(customerCallPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон клиента/Версия 1")
                .clickOnElement("Кнопка Взять шаг в работу")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Методологический")
                .fillInput("Поле ввода Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab()
                .goTo(personalAccountPage)
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim)
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(questionInGoPage)
                .switchToNewTab()
                .checkElementByTitleEquals("Поле Наименование стратегии", "Вопрос в ГО/Версия 2");
        assertIsEquals("Прозвон", questionInGoPage.getTextFromTable("Таблица Степпер", 1, "Стратегия"),
                "Значение в столбце Стратегия");
        checkingOpenSourcesPage.closeCurrentTab();
    }

    @Test
    @Tag("ef_go_2652416")
    @DisplayName("2652416 - Экранная форма стратегии \"Вопрос в ГО\". Отображение в поле \"Комментарий\" по последней версии \"Привязка телефона из анкеты\"")
    @WorkItemIds({"2652416"})
    public void ef_go_2652416(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of("Code", "stub8");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3209048.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .waitBusyCondition()
                .goTo(checkingOpenSourcesPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка открытых источников/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Методологический")
                .fillInput("Поле ввода Комментарий", "это для ГО")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .switchToOneTab();
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.elementByTitleVisibility("Раздел Вопрос в ГО", "отображается")
                .clickOnElement("Раздел Вопрос в ГО")
                .doubleClickByText(claim)
                .goTo(questionInGoPage)
                .switchToNewTab()
                .checkElementByTitleEquals("Поле Наименование стратегии", "Вопрос в ГО/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Вопрос решен")
                .fillInput("Поле Комментарий", "от ГО")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab()
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .waitBusyCondition()
                .goTo(checkingOpenSourcesPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка открытых источников/Версия 2")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Технический")
                .fillInput("Поле ввода Комментарий", "для ГО это 2")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab()
                .goTo(personalAccountPage)
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim)
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(questionInGoPage)
                .switchToNewTab()
                .checkElementByTitleEquals("Поле Наименование стратегии", "Вопрос в ГО/Версия 2");
        assertIsEquals("для ГО это 2", questionInGoPage.getTextFromTable("Таблица Степпер", 1, "Комментарий"),
                "Значение в столбце Комментарий");
        checkingOpenSourcesPage.closeCurrentTab();
    }

    @Test
    @Tag("ef_go_3208427")
    @DisplayName("3208427 - Экранная форма стратегии \"Вопрос в ГО\". Отображение в поле \"Стратегия\" по последней версии. Стратегии разных групп")
    @WorkItemIds({"3208427"})
    public void ef_go_3208427(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of("Code", "stub1");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3209103.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .waitBusyCondition()
                .goTo(l0CheckingDocumentsPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "L0.Проверка документов/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Методологический")
                .fillInput("Поле Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .switchToOneTab();
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.elementByTitleVisibility("Раздел Вопрос в ГО", "отображается")
                .clickOnElement("Раздел Вопрос в ГО")
                .doubleClickByText(claim)
                .goTo(questionInGoPage)
                .switchToNewTab()
                .checkElementByTitleEquals("Поле Наименование стратегии", "Вопрос в ГО/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Вопрос решен")
                .fillInput("Поле Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab()
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .waitBusyCondition()
                .goTo(l0CheckingDocumentsPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "L0.Проверка документов/Версия 2")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Одобрить стратегию")
                .fillInput("Поле ввода Внутренний комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab();
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .waitBusyCondition()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Методологический")
                .fillInput("Поле ввода Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab()
                .goTo(personalAccountPage)
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim)
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(questionInGoPage)
                .switchToNewTab()
                .checkElementByTitleEquals("Поле Наименование стратегии", "Вопрос в ГО/Версия 2");
        assertIsEquals("ФССП", questionInGoPage.getTextFromTable("Таблица Степпер", 1, "Стратегия"),
                "Значение в столбце Стратегия");
        checkingOpenSourcesPage.closeCurrentTab();
    }

    @Test
    @Tag("ef_go_3208481")
    @DisplayName("3208481 - Экранная форма стратегии \"Вопрос в ГО\". Отображение в поле \"ФИО Пользователя\" по последней версии")
    @WorkItemIds({"3208481"})
    public void ef_go_3208481(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of("Code", "stub1");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3209048.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .waitBusyCondition()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Методологический")
                .fillInput("Поле ввода Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .switchToOneTab();
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.elementByTitleVisibility("Раздел Вопрос в ГО", "отображается")
                .clickOnElement("Раздел Вопрос в ГО")
                .doubleClickByText(claim)
                .goTo(questionInGoPage)
                .switchToNewTab()
                .checkElementByTitleEquals("Поле Наименование стратегии", "Вопрос в ГО/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Вопрос решен")
                .fillInput("Поле Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab()
                .goTo(personalAccountPage)
                .clickOnElement("Кнопка выхода")

                .goTo(loginPage)
                .openAuthorizationPage()
                .loginViaUiOnUser("user2")
                .checkElementByTitleEquals("Фамилия и Имя пользователя", "Автоматическое Тестирование2");
        actionsClaimSteps.appointResponsiblePerson(claim, "testat2");
        personalAccountPage.clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .waitBusyCondition()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 2")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Методологический")
                .fillInput("Поле ввода Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab()
                .goTo(personalAccountPage)
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim)
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(questionInGoPage)
                .switchToNewTab()
                .checkElementByTitleEquals("Поле Наименование стратегии", "Вопрос в ГО/Версия 2");
        assertIsEquals("Автоматическое Тестирование2", questionInGoPage.getTextFromTable("Таблица Степпер", 1, "ФИО пользователя"),
                "Значение в столбце ФИО пользователя");
        checkingOpenSourcesPage
                .closeCurrentTab()
                .goTo(personalAccountPage)
                .clickOnElement("Кнопка выхода");
    }

    @Test
    @Tag("ef_go_1740751")
    @DisplayName("1740751 - Кнопка Ответ от ГО. Заполнение поля \"ФИО пользователя\" для типа \"Ответ\"")
    @WorkItemIds({"1740751"})
    public void ef_go_1740751(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of("Code", "stub1");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3209048.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .waitBusyCondition()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Методологический")
                .fillInput("Поле ввода Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .switchToOneTab()
                .clickOnElement("Кнопка выхода")

                .goTo(loginPage)
                .openAuthorizationPage()
                .loginViaUiOnUser("user2")
                .checkElementByTitleEquals("Фамилия и Имя пользователя", "Автоматическое Тестирование2");
        actionsClaimSteps.appointResponsiblePerson(claim, "testat2");
        personalAccountPage.elementByTitleVisibility("Раздел Вопрос в ГО", "отображается")
                .clickOnElement("Раздел Вопрос в ГО")
                .doubleClickByText(claim)
                .goTo(questionInGoPage)
                .switchToNewTab()
                .checkElementByTitleEquals("Поле Наименование стратегии", "Вопрос в ГО/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Вопрос решен")
                .fillInput("Поле Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab()
                .clickOnElement("Кнопка выхода")
                .goTo(loginPage)
                .openAuthorizationPage()
                .loginViaUi()
                .goTo(personalAccountPage);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .waitBusyCondition()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 2")
                .clickOnElement("Кнопка Ответ от ГО");
        String value = checkingOpenSourcesPage.getTextFromTable("Таблица Ответ от ГО", 1, "ФИО пользователя");
        assertContains(value, "Автоматическое Тестирование2");
        checkingOpenSourcesPage.closeCurrentTab();

    }

    @Test
    @Tag("ef_go_3208437")
    @DisplayName("3208437 - Экранная форма стратегии \"Вопрос в ГО\". Отображение данных в степпере при отправке со стратегии объекта Совместительство")
    @WorkItemIds({"3208437"})
    public void ef_go_3208437(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of("Code", "stub7");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3209048.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .waitBusyCondition()
                .goTo(checkingOpenSourcesPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка открытых источников/Версия 1")
                .clickOnElement("Шаг №2. Заёмщик. Совместительство")
                .clickOnElement("Кнопка Взять шаг в работу")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Методологический")
                .fillInput("Поле ввода Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .switchToOneTab();
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.elementByTitleVisibility("Раздел Вопрос в ГО", "отображается")
                .clickOnElement("Раздел Вопрос в ГО")
                .doubleClickByText(claim)
                .goTo(questionInGoPage)
                .switchToNewTab()
                .elementByTitleContains("Поле Наименование стратегии", "Вопрос в ГО")
                .checkElementByTitleContains("Поле Степпер", "Романов Юрий Иванович 05.12.1990")
                .closeCurrentTab();
    }

    @Test
    @Tag("ef_go_3208485")
    @DisplayName("3208485 - Экранная форма стратегии \"Вопрос в ГО\". Отображение в поле \"Дата\" по последней версии")
    @WorkItemIds({"3208485"})
    public void ef_go_3208485(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of("Code", "stub1");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3209048.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .waitBusyCondition()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Методологический")
                .fillInput("Поле ввода Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .switchToOneTab();
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.elementByTitleVisibility("Раздел Вопрос в ГО", "отображается")
                .clickOnElement("Раздел Вопрос в ГО")
                .doubleClickByText(claim)
                .goTo(questionInGoPage)
                .switchToNewTab()
                .checkElementByTitleEquals("Поле Наименование стратегии", "Вопрос в ГО/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Вопрос решен")
                .fillInput("Поле Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab()
                .waitBusyCondition()
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .waitBusyCondition()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 2")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Методологический")
                .fillInput("Поле ввода Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab();
        actionsClaimSteps.executeQuery(VERIFICATION,
                "SELECT date_finished FROM vrf_check_group_result " +
                        "WHERE claim_id = '" + claim + "';");
        OffsetDateTime dateTimeDb = OffsetDateTime.parse(actionsClaimSteps.getVariables("date_finished"));
        personalAccountPage.clickOnElement("Раздел Вопрос в ГО")
                .waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(questionInGoPage);
        assertContains(questionInGoPage.getTextFromTable("Таблица Степпер", 1, "Дата"),
                dateTimeDb.format(DF));
        checkingOpenSourcesPage.closeCurrentTab();
    }
}
