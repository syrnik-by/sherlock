package ru.autotestframework.regress.card_request.verification.strategies_verification.checking_calls.calling_contact_person_spouse;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import java.util.List;
import java.util.Map;

@Tag("regress")
@Tag("card_request")
@Tag("verification")
@Tag("strategies_verification")
@Tag("call_contact_person_spourse_separate_claim")
@ClassName("Карточка заявки. Верификация. ЭФ стратегий верификации. Проверка прозвонов. Прозвон контактного лица/супруга (-и). На каждый кейс отдельная заявка")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CallContactPersonSpourseSeparateClaimTest extends BaseTest {

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
    @Tag("displaying_verification_results_1720139")
    @DisplayName("1720139 - Верификация.Прозвон контактного лица/супруга (-и).Отображение результатов проверки ")
    @WorkItemIds({"1720139"})
    public void displaying_verification_results_1720139(TestInfo testInfo) {
        List<String> listValueVerificationResult = List.of(
                "Нерезультативный прозвон",
                "Результативный прозвон");
        List<String> listValueIneffective = List.of(
                "Контактное лицо/супруг (-а) не отвечает/недоступен",
                "Контактное лицо/супруг (-а) просит перезвонить",
                "Контактное лицо/супруг (-а) просит перезвонить через длительный промежуток времени");
        List<String> listValueProductive = List.of(
                "",
                "Негатив отсутствует",
                "Отказ контактного лица/супруга (-и)  предоставить информацию",
                "Отвечает третье лицо",
                "Выявлен негатив");

        Map<String, String> claimParams = Map.of(
                "incomeMain", "FormSpravType5",
                "kpMain", "Comp_Type_Public_Servant_Spark",
                "kpClient", "null",
                "Code", "stub12");

        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1651046.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab();

        callContactPersonSpoursePage
                .checkDropDownListElements("Выпадающий список Результат проверки", listValueVerificationResult)
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Нерезультативный прозвон")
                .checkDropDownListElements("Выпадающий список Нерезультативный прозвон", listValueIneffective)
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                .checkDropDownListElements("Выпадающий список Результативный прозвон", listValueProductive);
    }
}
