package ru.autotestframework.regress.card_request.underwriting.system_report.auto_checks;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

@Tag("regress")
@Tag("card_request")
@Tag("underwriting")
@Tag("auto_checks_separate_claim")
@ClassName("Критерии отправки на серую зону. Зарплатные клиенты с полными зачислениями")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AutoChecksSeparateClaimTest extends BaseTest {

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
    @Tag("criteria_sending_gray_zone_salary_clients_1723609")
    @DisplayName("1723609 - Критерии отправки на серую зону. Зарплатные клиенты с полными зачислениями")
    @WorkItemIds({"1723609"})
    public void criteria_sending_gray_zone_salary_clients_1723609(TestInfo testInfo) {

        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3859713.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .clickOnElement("Кнопка Основные данные").switchToNewTab()
                .goTo(cardRequestPage).waitBusyCondition()
                .assertElementByTitleActivity("Вкладка основные данные", "активен")
                .clickOnElement("Ссылка Автопроверки")
                .goTo(autocheckPage).switchToNewTab()
                .checkElementByTitleEquals("Поле Критерии отправки на серую зону", "Верификация" + "\n" + "stub1");
    }
}
