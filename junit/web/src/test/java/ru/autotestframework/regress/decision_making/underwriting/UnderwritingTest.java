package ru.autotestframework.regress.decision_making.underwriting;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import java.util.List;

import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;

@Tag("regress")
@Tag("personal_account")
@Tag("underwriting")
@ClassName("Андеррайтинг")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UnderwritingTest extends BaseTest {

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
    @Tag("select_decide_1722067")
    @DisplayName("1722067 - Выбор значений блока Проведенные проверки. Принять решение (Отклонение). Андеррайтер с полномочиями.")
    @WorkItemIds({"1722067"})
    public void select_decide_1722067(TestInfo testInfo) {
        List<String> checkCarriedOut = List.of(
                "Обязательный звонок работодателю по подтвержденному телефону",
                "Звонок арендодателю",
                "Звонок клиенту",
                "Звонок контактному лицу/супруге");

        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1722067.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.clickOnElement("Раздел Андеррайтинг").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(cardRequestPage)
                .fillInput("Поле ввода Внутренний комментарий андеррайтера", "Коментарий АТ")
                .selectValueFromDropDownList("Выпадающий список Занятость подтверждена", "Звонок не назначался")

                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .selectValueFromDropDownList("Выпадающий список Проведенные проверки", checkCarriedOut)
                .selectValueFromDropDownList("Выпадающий список Полномочия", "Собственные")
                .selectValueFromDropDownList("Выпадающий список Одобрить/Отклонить", "Отклонить")
                .selectValueFromDropDownList("Выпадающий список Тип одобрения/Причина отклонения", "Негатив на Клиента")
                .clickOnElement("Кнопка Принять решение").switchToOneTab()

                .goTo(loginPage)
                .openMenuLinks("Поиск")
                .goTo(searchPage)
                .searchClaimOnPage(claim);
        String statusClaim = searchPage.getTextFromTable("Таблица результаты поиска", 1, "Статус заявки");
        assertIsTrue(statusClaim.equals("Отказ"),
                "Значение в столбце Статус заявки должно быть равно Отказ. Фактическое значение: " + statusClaim);
    }
}
