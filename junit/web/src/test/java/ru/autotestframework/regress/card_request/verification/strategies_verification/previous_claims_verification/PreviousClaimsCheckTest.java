package ru.autotestframework.regress.card_request.verification.strategies_verification.previous_claims_verification;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import java.util.List;
import java.util.Map;

@Tag("previous_claims")
@Tag("regress")
@Tag("card_request")
@Tag("verification")
@Tag("ef_verification_strategies")
@Tag("previous_claims_check")
@ClassName("Проверка предыдущих заявок. На каждый кейс отдельная заявка. Карточка заявки. ЭФ стратегий верификации")
public class PreviousClaimsCheckTest extends BaseTest {

    Map<String, String> claimParams;

    @BeforeEach
    public void login() {
        int clientId = (int) (Math.random() * 90000000) + 10000000;
        claimParams = Map.of("clientId", clientId + "");
        actionsClaimSteps.emulateCreationOfPreviousClaim("" + clientId, "1720104");
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi()
                    .openMenuLinks("Личный кабинет")
                    .goTo(personalAccountPage)
                    .waitBusyCondition()
                    .clickOnElement("Раздел Верификация");
        }
    }

    @AfterAll
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("smoke")
    @Tag("display_result_check_segment_military_1720104")
    @DisplayName("1720104 - Верификация. Проверка предыдущих заявок.Отображение результатов проверки сегмент - Военнослужащие/силовики")
    @WorkItemIds({"1720104"})
    public void display_result_check_segment_military_1720104(TestInfo testInfo) {
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1720104.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);

        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(verificationStrategyPage)
                .waitBusyCondition()
                .checkElementByTitleContains("Поле Наименование стратегии", "Проверка предыдущих заявок")
                .checkDropDownListElements("Выпадающий список Результат проверки", List.of("Расхождения не выявлены", "Расхождения выявлены"))
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Расхождения выявлены")
                .waitBusyCondition()
                .selectValueFromDropDownList("Выпадающий список Виды выявленных расхождений",
                        List.of("Расхождения по трудовой деятельности с предоставленной ТК",
                                "Расхождения в справках 2-НДФЛ за один и тот же период",
                                "Расхождения в справках по форме Банка за один и тот же период",
                                "Подпись одного и того же Заверителя в справках/копии ТК отлична",
                                "Расхождения по трудовой деятельности без ТК",
                                "Значительное увеличение дохода подтверждено"))
                .closeCurrentTab();
    }
}
