package ru.autotestframework.regress.card_request.underwriting.system_report.ideal_KI;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import java.util.List;

@Tag("regress")
@Tag("personal_account")
@Tag("underwriting")
@Tag("ideal_ki_separate_claim")
@ClassName("На каждый кейс отдельная заявка. Отчеты системы. Идеальная КИ")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IdealKiSeparateClaimTest extends BaseTest {

    private String claim;

    @BeforeEach
    public void login() {
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi()
                    .openMenuLinks("Личный кабинет");
        }
    }

    @AfterEach
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("smoke")
    @Tag("ideal_ki_open_report_1723650")
    @DisplayName("1723650 - Идеальная КИ. Открытие отчета.")
    @WorkItemIds({"1723650"})
    public void ideal_ki_open_report_1723650(TestInfo testInfo) {
        List<String> defaultColumns = List.of(
                "№",
                "Вид кредита",
                "Отношение к кредиту",
                "Статус кредита",
                "Сумма кредита",
                "Остаток задолжен.",
                "₽/$",
                "Ежемесячный платеж",
                "Дата получен.",
                "Дата план. погаш.",
                "Дата факт. погаш.",
                "Платежи по кредиту",
                "Дата посл. обновл.",
                "Просроч. текущая (дней)",
                "Наихуд. статус",
                "Рефин.кредит",
                "Учитывать в нагрузку");
        List<String> addedColumns = List.of(
                "Источник выдачи",
                "Заемщик по кредиту",
                "Просроч. текущая (сумма)",
                "Источник",
                "Сумма рефинансирования",
                "Ставка",
                "Рефинансирование",
                "Платёж по кредиту",
                "Модифиц. платёж по кредиту",
                "ПСК (УСО)",
                "ППД1",
                "ППД2",
                "ППД3");

        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3859888.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);

        personalAccountPage
                .clickOnElement("Раздел Андеррайтинг")
                .doubleClickByText(claim).switchToNewTab()
                .goTo(cardRequestPage).waitBusyCondition()
                .assertElementByTitleActivity("Вкладка основные данные", "активен")
                .clickOnElement("Ссылка Идеальная КИ").switchToNewTab()
                .goTo(idealCiPage)
                .checkElementByTitleEquals("Кнопка УСО", "УСО")
                .colorElementEquals("Кнопка УСО", "rgba(28, 76, 164, 1)");
        if (!idealCiPage.getElementByTitle("Кнопка Показать столбцы").isDisplayed()) {
            idealCiPage.clickOnElement("Кнопка Скрыть столбцы");
        }
        idealCiPage.checkContainsTableHeaders("Таблица Участники сделки", defaultColumns)
                .clickOnElement("Кнопка показать/скрыть столбцы")
                .checkContainsTableHeaders("Таблица Участники сделки", addedColumns)
                .clickOnElement("Кнопка показать/скрыть столбцы")
                .checkNotContainsTableHeaders("Таблица Участники сделки", addedColumns)
                .clickOnCellFromTable("Таблица Участники сделки", 1, 2)
                .assertElementByTitleVisibility("Таблица Платежи по кредиту", "отображается");
    }
}