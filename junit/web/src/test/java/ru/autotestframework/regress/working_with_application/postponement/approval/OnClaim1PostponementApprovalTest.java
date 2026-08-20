package ru.autotestframework.regress.working_with_application.postponement.approval;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;

@Tag("regress")
@Tag("no_check_verification")
@Tag("working_with_application")
@Tag("postponement")
@Tag("approval")
@Tag("on_claim_1_postponement_approval")
@ClassName("Утверждение. На заявке №1. Откладывание Утверждение")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OnClaim1PostponementApprovalTest extends BaseTest {

    String claim;

    @BeforeEach
    public void login(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3861993.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi()
                    .openMenuLinks("Личный кабинет")
                    .goTo(personalAccountPage)
                    .clickOnElement("Раздел Андеррайтинг");
        }
    }

    @AfterAll
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("smoke")
    @Tag("manual_transfer_from_status_postponed_approval_1723091")
    @DisplayName("1723091 - Ручной перевод заявки из статуса \"Отложена\" (утверждение)\"")
    @WorkItemIds({"1723091"})
    public void manual_transfer_from_status_postponed_approval_1723091() {
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .selectValueFromDropDownList("Выпадающий список Занятость подтверждена", "Звонок не назначался")
                .fillInput("Поле ввода Внутренний комментарий андеррайтера", "Комментарий")
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .selectValueFromDropDownList("Выпадающий список Проведенные проверки", "Проверка критичных данных")
                .selectValueFromDropDownList("Выпадающий список Полномочия", "Собственные")
                .selectValueFromDropDownList("Выпадающий список Одобрить/Отклонить", "Одобрить")
                .selectValueFromDropDownList("Выпадающий список Тип одобрения/Причина отклонения", "Одобрено")
                .clickOnElement("Кнопка На утверждение")
                .switchToOneTab()
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Утверждение");
        String statusClaim = personalAccountPage.getTextFromTable("Таблица в работе", 1, "Статус заявки");
        assertIsTrue(statusClaim.equals("Ожидает утверждения"),
                "Значение в столбце Статус заявки должно быть равно Ожидает утверждения. Фактическое значение: " + statusClaim);
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Взять в работу")
                .waitBusyCondition()
                .assertElementByTitleActivity("Кнопка Отложить", "активен")
                .clickOnElement("Кнопка Отложить")
                .assertElementByTitleVisibility("Модальное окно Перевод заявки в отложенные", "отображается")
                .selectValueFromDropDownList("Выпадающий список Причина (Перевод заявки в отложенные)", "Вопрос в ГО")
                .clickOnElement("Кнопка Отложить заявку")
                .goTo(personalAccountPage)
                .switchToOneTab()
                .waitBusyCondition()
                .clickOnElement("Кнопка раскрыть таблицу Отложено")
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Взять в работу")
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "На утверждении");
    }
}
