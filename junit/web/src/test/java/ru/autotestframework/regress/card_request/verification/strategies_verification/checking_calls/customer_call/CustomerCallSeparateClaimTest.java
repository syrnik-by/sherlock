package ru.autotestframework.regress.card_request.verification.strategies_verification.checking_calls.customer_call;

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
@Tag("customer_call_separate_claim")
@ClassName("Карточка заявки. Верификация. ЭФ стратегий верификации. Проверка прозвонов. Прозвон клиента. На каждый кейс отдельная заявка")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CustomerCallSeparateClaimTest extends BaseTest {

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
    @Tag("displaying_verification_results_1720137")
    @DisplayName("1720137 - Верификация.Прозвон клиента.Отображение результатов проверки")
    @WorkItemIds({"1720137"})
    public void displaying_verification_results_1720137(TestInfo testInfo) {
        List<String> listValueVerificationResult = List.of(
                "Нерезультативный прозвон",
                "Результативный прозвон",
                "Бесконтактное подтверждение");
        List<String> listValueIneffective = List.of(
                "Клиент не отвечает/недоступен",
                "Клиент просит перезвонить",
                "Клиент просит перезвонить через длительный промежуток времени",
                "Отказ клиента предоставить информацию");
        List<String> listValueProductive = List.of(
                "Негатив не выявлен",
                "Клиент Заявку не подавал или подавал Заявку через посредников",
                "Заявка не актуальна",
                "Выявлен негатив");
        List<String> listValueContactlessApprove = List.of(
                "Социальные сети");
        Map<String, String> claimParams = Map.of(
                "incomeMain", "FormSpravType5",
                "kpMain", "Comp_Type_OPK_Macro_War",
                "kpClient", "null",
                "Code", "stub11");

        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1651046.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab();

        customerCallPage
                .checkDropDownListElements("Выпадающий список Результат проверки", listValueVerificationResult)
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Нерезультативный прозвон")
                .checkDropDownListElements("Выпадающий список Нерезультативный прозвон", listValueIneffective)
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                .checkDropDownListElements("Выпадающий список Результативный прозвон", listValueProductive)
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Бесконтактное подтверждение")
                .checkDropDownListElements("Выпадающий список Бесконтактное подтверждение", listValueContactlessApprove);
    }

    @Test
    @Tag("checking_routing_1719228")
    @DisplayName("1719228 - Проверка маршрутизации заявки для результата проверки \"Бесконтактное подтверждение\"")
    @WorkItemIds({"1719228"})
    public void checking_routing_1719228(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2512523.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab();

        customerCallPage
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Бесконтактное подтверждение")
                .selectValueFromDropDownList("Выпадающий список Бесконтактное подтверждение", "Социальные сети")
                .clickOnElement("Кнопка Далее")
                .waitText(2, "Пожалуйста, заполните источник подтверждения")
                .clickOnElement("Кнопка ОК")
                .fillInput("Поле ввода Источник подтверждения","Источник")
                .clickOnElement("Кнопка Далее")
                .assertElementByTitleVisibility("Иконка Завершен первый этап", "отображается");
    }
}
