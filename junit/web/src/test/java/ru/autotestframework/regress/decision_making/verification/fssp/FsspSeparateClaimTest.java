package ru.autotestframework.regress.decision_making.verification.fssp;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;
import static ru.autotestframework.utils.Constants.VERIFICATION;

@Tag("regress")
@Tag("verification")
@Tag("strategies_verification")
@Tag("fssp")
@Tag("fssp_separate_claim")
@ClassName("ФССП. На каждый кейс отдельная заявка")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FsspSeparateClaimTest extends BaseTest {

    private String claim;

    @BeforeAll
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
        loginPage.openMenuLinks("Личный кабинет");
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("smoke")
    @Tag("check_code_C2_1720312")
    @DisplayName("1720312 - Обработка проверки по 'Коду результата проверки' = C2. ФССП. NEXT")
    @WorkItemIds({"1720312"})
    public void check_code_C2_1720312(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3858769.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Исполнительное производство не найдено")
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

        actionsClaimSteps.executeQuery(VERIFICATION, "select request_final_result_code from vrf_check_step vcs " +
                "join vrf_check_set vcs2 on vcs2.id = vcs.check_set_id  " +
                "where vcs2.claim_id ='" + claim + "' and check_type_code = 'FSSP'");
        assertIsTrue(actionsClaimSteps.getVariables("request_final_result_code").equals("NEXT"), "Значение request_final_result_code == NEXT");
    }
}
