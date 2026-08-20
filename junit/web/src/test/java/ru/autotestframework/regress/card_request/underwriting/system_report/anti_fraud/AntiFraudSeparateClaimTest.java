package ru.autotestframework.regress.card_request.underwriting.system_report.anti_fraud;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;

@Tag("regress")
@Tag("personal_account")
@Tag("underwriting")
@Tag("anti_fraud_separate_claim")
@ClassName("На каждый кейс отдельная заявка. Отчеты системы. Antifraud")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AntiFraudSeparateClaimTest extends BaseTest {

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
    @Tag("anti_fraud_open_report_1723656")
    @DisplayName("1723656 - Открытие отчета Antifraud")
    @WorkItemIds({"1723656"})
    public void anti_fraud_open_report_1723656(TestInfo testInfo) {

        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3860190.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);

        personalAccountPage
                .clickOnElement("Раздел Андеррайтинг")
                .doubleClickByText(claim).switchToNewTab()
                .goTo(cardRequestPage).waitBusyCondition()
                .assertElementByTitleActivity("Вкладка основные данные", "активен")
                .clickOnElement("Ссылка Отчет Anti FRAUD").switchToNewTab()
                .goTo(antiFraudPage)
                .checkRowCount("Таблица Antifraud",2);

        assertIsTrue(antiFraudPage.getTextFromTable("Таблица Antifraud", 1, "Тип совпадения").
                        equals("Противоречие"),
                "Значение в столбце Тип совпадения должно быть равно Противоречие");

        assertIsTrue(antiFraudPage.getTextFromTable("Таблица Antifraud", 2, "Тип совпадения").
                        equals("Противоречие"),
                "Значение в столбце Тип совпадения должно быть равно Противоречие");
    }
}
