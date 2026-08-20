package ru.autotestframework.regress.decision_making.verification.checking_open_sources;

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
@Tag("checking_open_sources_separate_claim")
@ClassName("Проверка открытых источников. На каждый кейс отдельная заявка")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CheckingOpenSourcesSeparateClaimTest extends BaseTest {

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
        checkingOpenSourcesPage.closeCurrentTab();
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("smoke")
    @Tag("check_code_c46_1720331")
    @DisplayName("1720331 - Проверка открытых источников.Обработка проверки по 'Коду результата проверки' = C46. ADD_STRATEGY.")
    @WorkItemIds({"1720331"})
    public void check_code_c46_1720331(TestInfo testInfo) {

        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3859674.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(checkingOpenSourcesPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка открытых источников/Версия 1")
                .clickOnElement("Чек-бокс Приостановление по счетам организации")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab();

        actionsClaimSteps.executeQuery(VERIFICATION, "select request_final_result_code from vrf_check_step vcs " +
                "join vrf_check_set vcs2 on vcs2.id = vcs.check_set_id  " +
                "where vcs2.claim_id ='" + claim + "' and check_type_code = 'OPEN_SOURCE_EMPLOYER'");
        assertIsTrue(actionsClaimSteps.getVariables("request_final_result_code").equals("NEXT"), "Значение request_final_result_code == NEXT");

        loginPage
                .openMenuLinks("Поиск")
                .goTo(searchPage)
                .searchClaimOnPage(claim);
        String statusClaim = searchPage.getTextFromTable("Таблица результаты поиска", 1, "Статус заявки");
        String strategy = searchPage.getTextFromTable("Таблица результаты поиска", 1, "Стратегия");
        assertIsTrue(statusClaim.equals("Ожидает"),
                "Значение в столбце Статус заявки должно быть равно Отказ. Фактическое значение: " + statusClaim);
        assertIsTrue(strategy.equals("Прозвон"),
                "Значение в столбце Стратегия должно быть равно Прозвон. Фактическое значение: " + strategy);

        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage
                .openMenuLinks("Личный кабинет")
                .doubleClickByText(claim).switchToNewTab()
                .goTo(checkingOpenSourcesPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Прозвон работодателя - любой телефон/Версия 1");
    }
}