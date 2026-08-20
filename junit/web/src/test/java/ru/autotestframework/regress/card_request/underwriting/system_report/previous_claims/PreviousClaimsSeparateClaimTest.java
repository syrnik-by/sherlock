package ru.autotestframework.regress.card_request.underwriting.system_report.previous_claims;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import java.util.Map;

import static ru.autotestframework.steps.asserts.Asserts.assertIsEquals;

@Tag("regress")
@Tag("no_check_verification")
@Tag("card_request")
@Tag("underwriting")
@Tag("system_report")
@Tag("previous_claims")
@Tag("previous_claims_separate_claim")
@ClassName("Предыдущие заявки. На каждый кейс отдельная заявка. Отчеты системы. Предыдущие заявки")
public class PreviousClaimsSeparateClaimTest extends BaseTest {

    Map<String, String> claimParams;

    @BeforeEach
    public void login() {
        int clientId = (int) (Math.random() * 90000000) + 10000000;
        claimParams = Map.of("clientId", clientId + "");
        actionsClaimSteps.emulateCreationOfPreviousClaim("" + clientId, "1723674");
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi()
                    .openMenuLinks("Личный кабинет")
                    .goTo(personalAccountPage)
                    .waitBusyCondition()
                    .clickOnElement("Раздел Андеррайтинг");
        }
    }

    @AfterAll
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("smoke")
    @Tag("data_on_previous_claim_1723674")
    @DisplayName("1723674 - Данные по предыдущей заявке")
    @WorkItemIds({"1723674"})
    public void data_on_previous_claim_1723674(TestInfo testInfo) {
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3861602.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);

        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .waitBusyCondition()
                .clickOnElement("Ссылка Предыдущие заявки")
                .switchToNewTab()
                .goTo(previousClaimsPage)
                .waitBusyCondition()
                .assertElementByTitleVisibility("Таблица Предыдущие заявки", "отображается")
                .checkElementByTitleContains("Таблица Предыдущие заявки", "Номер заявки");
        assertIsEquals("CLL.1234567.0_vrfAT", previousClaimsPage.getTextFromTable("Таблица Предыдущие заявки", 1, "Номер заявки"), "CLL.1234567.0_vrfAT");
        previousClaimsPage.closeCurrentTab().goTo(cardRequestPage).closeCurrentTab();
    }
}
