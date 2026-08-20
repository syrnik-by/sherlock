package ru.autotestframework.regress.working_with_application.revision.underwriting;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import static ru.autotestframework.steps.asserts.Asserts.assertIsEquals;
import static ru.autotestframework.utils.Constants.REQUESTS;

import java.util.List;

@Tag("regress")
@Tag("no_check_verification")
@Tag("working_with_application")
@Tag("revision")
@Tag("underwriting")
@Tag("separate_claim_revision_underwriting")
@ClassName("На каждый кейс одна заявка. Работа с заявкой. Доработка андеррайтинг")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SeparateClaimRevisionUnderwritingTest extends BaseTest {

    String claim;

    @BeforeEach
    public void login(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3857556.json", 1, testInfo).get(0);
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
    @Tag("select_values_block_check_carried_out_1723091")
    @DisplayName("1723091 - Ручной перевод заявки из статуса \"Отложена\" (утверждение)\"")
    @WorkItemIds({"1723091"})
    public void select_values_block_check_carried_out_1723091() {
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .selectValueFromDropDownList("Выпадающий список Проведенные проверки", List.of("Проверка предыдущих заявок", "Проверка антифрод-отчет"))
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка основные данные")
                .selectValueFromDropDownList("Выпадающий список Занятость подтверждена", "Звонок не назначался")
                .fillInput("Поле ввода Внутренний комментарий андеррайтера", "любое значение")
                .selectValueFromDropDownList("Выпадающий список Причина доработки", "Ошибка МРК при вводе контактных данных клиента (адресов, телефонов)")
                .fillInput("Поле ввода Комментарий МРК и отлагательных условий", "test")
                .clickOnElement("Кнопка Доработка")
                .goTo(personalAccountPage)
                .switchToOneTab()
                .waitBusyCondition()
                .openMenuLinks("Поиск")
                .goTo(searchPage)
                .searchClaimOnPage(claim)
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .checkElementByTitleContains("Блок Выбранных проверок", "Проверка предыдущих заявок")
                .checkElementByTitleContains("Блок Выбранных проверок", "Проверка антифрод-отчет")
                .closeCurrentTab()
                .goTo(personalAccountPage);
        actionsClaimSteps.executeQuery(REQUESTS,
                "SELECT is_prev_claim_check, is_antifraud_check FROM requests.rqs_underwriter_check ruc " +
                        "JOIN requests.rqs_form rf ON rf.id = ruc.form_id " +
                        "JOIN requests.rqs_request rr ON rr.id = rf.request_id " +
                        "WHERE claim_id = '" + claim + "'");
        assertIsEquals("true", actionsClaimSteps.getVariables("is_prev_claim_check"), "is_prev_claim_check");
        assertIsEquals("true", actionsClaimSteps.getVariables("is_antifraud_check"), "is_antifraud_check");
    }
}
