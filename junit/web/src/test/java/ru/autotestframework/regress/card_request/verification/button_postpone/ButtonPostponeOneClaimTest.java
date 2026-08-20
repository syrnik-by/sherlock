package ru.autotestframework.regress.card_request.verification.button_postpone;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import java.util.List;

import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;
import static ru.autotestframework.utils.Constants.REQUESTS;

@Tag("regress")
@Tag("card_request")
@Tag("verification")
@Tag("button_postpone")
@Tag("button_postpone_one_claim")
@ClassName("Карточка заявки. Верификация. На заявке Тип №1 Кнопка отложить")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ButtonPostponeOneClaimTest extends BaseTest {

    private String claim;

    @BeforeAll
    public void createClaim(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3242156.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
    }

    @BeforeEach
    public void login() {
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi()
                    .openMenuLinks("Личный кабинет");
        }
        personalAccountPage.clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab();
    }

    @AfterEach
    public void closeTab() {
        callContactPersonSpoursePage.closeCurrentTab();
    }

    @AfterAll
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }


    @Test
    @Tag("smoke")
    @Tag("checking_click_postpone_1725590")
    @DisplayName("1725590 - Проверка открытия модального окна при нажатии на кнопку \"Отложить\"")
    @WorkItemIds({"1725590"})
    public void checking_click_postpone_1725590() {
        cardRequestPage
                .assertElementByTitleActivity("Кнопка Отложить", "активен")
                .clickOnElement("Кнопка Отложить")
                .elementByTitleVisibility("Модальное окно Перевод заявки в отложенные", "отображается");
    }

    @Test
    @Tag("checking_reason_1725587")
    @DisplayName("1725587 - Проверка значений поля \"Причина\" при откладывании заявки")
    @WorkItemIds({"1725587"})
    public void checking_reason_1725587() {
        List<String> actualDropDownListCheckBox;
        actionsClaimSteps.executeQuery(REQUESTS, "SELECT * FROM requests.rqs_dir_delay_reason;");
        List<String> valuesFromDb = actionsClaimSteps.getValuesFromResponseDb("description");

        cardRequestPage
                .assertElementByTitleActivity("Кнопка Отложить", "активен")
                .clickOnElement("Кнопка Отложить")
                .clickOnElement("Выпадающий список Причина (Перевод заявки в отложенные)");
        actualDropDownListCheckBox = cardRequestPage.getListCheckBox("Список Причин (Перевод заявки в отложенные)");
        assertIsTrue(valuesFromDb.containsAll(actualDropDownListCheckBox), "Список " + valuesFromDb + " соответствует списку " + actualDropDownListCheckBox);
    }
}
