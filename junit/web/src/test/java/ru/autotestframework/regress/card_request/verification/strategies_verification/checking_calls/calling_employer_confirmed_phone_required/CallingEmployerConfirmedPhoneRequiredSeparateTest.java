package ru.autotestframework.regress.card_request.verification.strategies_verification.checking_calls.calling_employer_confirmed_phone_required;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import java.util.List;
import java.util.Map;

import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;

@Tag("regress")
@Tag("card_request")
@Tag("verification")
@Tag("strategies_verification")
@Tag("CallingEmployerConfirmedPhoneRequiredSeparate")
@ClassName("Карточка заявки. Верификация. ЭФ стратегий верификации. Проверка прозвонов. Прозвон работодателя - подтвержденный телефон (обязательный). На каждый кейс отдельная заявка")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CallingEmployerConfirmedPhoneRequiredSeparateTest extends BaseTest {

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
    @Tag("displaying_verification_results_1720149")
    @DisplayName("1720149 - Верификация.Прозвон работодателя - подтвержденный телефон (обязательный).Отображение результатов проверки ")
    @WorkItemIds({"1720149"})
    public void displaying_verification_results_1720149(TestInfo testInfo) {
        List<String> actualDropDownListCheckBox;
        List<String> listValueVerificationResult = List.of(
                "Предоставлен документ, закрывающий риски",
                "Подтвержденный телефон не найден",
                "Нерезультативный прозвон",
                "Результативный прозвон");
        List<String> listValueDocumentCoveringRisks = List.of(
                "Выписка из ПФР",
                "Электронная ТК");
        List<String> listValueIneffective = List.of(
                "Работодатель не отвечает/недоступен",
                "Представитель работодателя просит перезвонить",
                "Работодатель просит перезвонить через длительный промежуток времени.",
                "Отказ в предоставлении информации");
        List<String> listValueEffective = List.of(
                "Негатив не выявлен, все ответы получены",
                "Выявлен негатив");
        List<String> listValueNegative = List.of(
                "Клиент уволен / находится в стадии увольнения",
                "Декрет",
                "Негативная характеристика Клиента от работодателя",
                "Задержки з/п",
                "Информация о сокращениях",
                "Подставной рабочий телефон",
                "Документы имеют признаки фальсификации",
                "Несоответствие минимальным требованиям",
                "Клиент предоставляет ложные анкетные данные",
                "Негатив на работодателя",
                "Трудоустройство не по найму/временная работа");
        Map<String, String> claimParams = Map.of(
                "incomeMain", "NO",
                "kpMain", "null",
                "kpClient", "null",
                "Code", "stub16");

        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1651046.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab();

        callingEmployerConfirmedPhoneRequiredPage
                .checkDropDownListElements("Выпадающий список Результат проверки", listValueVerificationResult)
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Предоставлен документ, закрывающий риски")
                .checkDropDownListElements("Выпадающий список Предоставлен документ, закрывающий риски", listValueDocumentCoveringRisks)
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Нерезультативный прозвон")
                .checkDropDownListElements("Выпадающий список Нерезультативный прозвон", listValueIneffective)
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Результативный прозвон")
                .checkDropDownListElements("Выпадающий список Результативный прозвон", listValueEffective)
                .selectValueFromDropDownList("Выпадающий список Результативный прозвон", "Выявлен негатив")
                .clickOnElement("Выпадающий список Выявлен негатив");
        actualDropDownListCheckBox = callingEmployerConfirmedPhoneRequiredPage.getListCheckBox("Выпадающий список чек-боксов Выявлен негатив");
        assertIsTrue(listValueNegative.containsAll(actualDropDownListCheckBox), "Список " + listValueNegative + " соответствует списку " + actualDropDownListCheckBox);

        callingEmployerConfirmedPhoneRequiredPage
                .clickOnElement("Интерфейс")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Подтвержденный телефон не найден")
                .assertElementByTitleVisibility("Выпадающий список Предоставлен документ, закрывающий риски", "не отображается")
                .assertElementByTitleVisibility("Выпадающий список Нерезультативный прозвон", "не отображается")
                .assertElementByTitleVisibility("Выпадающий список Результативный прозвон", "не отображается")
                .assertElementByTitleVisibility("Выпадающий список Выявлен негатив", "не отображается");
    }
}
