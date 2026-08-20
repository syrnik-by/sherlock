package ru.autotestframework.regress.card_request.verification.strategies_verification.fssp;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.autotestframework.BaseTest;
import ru.autotestframework.pages.SearchPage;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.ExternalId;
import ru.psb.testit.annotations.WorkItemIds;

import java.util.List;
import java.util.Map;

import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;

@Tag("regress")
@Tag("card_request")
@Tag("verification")
@Tag("strategies_verification")
@Tag("fssp")
@Tag("fssp_separate_claim")
@ClassName("Карточка заявки. Верификация. ЭФ стратегий верификации. ФССП. На каждый кейс отдельная заявка")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FsspSeparateClaimTest extends BaseTest {

    private String claim;
    private final List<String> checkFields = List.of("Чек-бокс Активные ИП по кредитным платежам, алиментам (свыше 1000 р.), уголовным штрафам",
            "Чек-бокс Закрытые по ст.46 ИП по кредитным платежам, алиментам (свыше 1000 р.), уголовным штрафам",
            "Чек-бокс Прочие активные ИП",
            "Чек-бокс Закрытые ИП по кредитным платежам",
            "Чек-бокс Закрытые ИП по статье 47 (банкротство)");

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
    @Tag("checking_fixed_result_1644882")
    @DisplayName("1644882 - ЭФ ФССП. Проверка фиксации результатов проверки по стратегии ФССП")
    @WorkItemIds({"1644882"})
    public void checking_fixed_result_1644882(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1644911.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.doubleClickByText(claim).switchToNewTab();
        fsspPage.clickOnElement("Кнопка Далее")
                .waitText(2, "Для завершения шага необходимо заполнить результат проверки или результат по заявке")
                .clickOnElement("Кнопка ОК")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Найдено исполнительное производство")
                .clickOnElement("Кнопка Далее")
                .waitText(2, "Пожалуйста, выберите хотя бы одно из списка")
                .clickOnElement("Кнопка ОК")
                .clickOnElement("Чек-бокс Активные ИП по кредитным платежам, алиментам (свыше 1000 р.), уголовным штрафам")
                .assertElementByTitleSelected("Чек-бокс Активные ИП по кредитным платежам, алиментам (свыше 1000 р.), уголовным штрафам", "выбран")
                .clickOnElement("Кнопка Далее")
                .elementByTitleVisibility("Кнопка Завершить проверку", "отображается")
                .elementByTitleVisibility("Иконка статуса Проверка заверешена", "отображается");
        checkFields.subList(1, checkFields.size())
                .forEach(title -> fsspPage.clickOnElement(title).assertElementByTitleSelected(title, "не выбран"));
        fsspPage.closeCurrentTab();

    }

    @Test
    @Tag("checking_fixed_result_1644875")
    @DisplayName("1644875 - ЭФ ФССП. Проверка фиксации результатов и завершения проверки по стратегии ФССП")
    @WorkItemIds({"1644875"})
    public void checking_fixed_result_1644875(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1644911.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.doubleClickByText(claim).switchToNewTab();
        fsspPage.selectValueFromDropDownList("Выпадающий список Результат проверки", "Невозможно запросить ФССП")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab();
        loginPage.openMenuLinks("Поиск")
                .goTo(searchPage)
                .searchClaimOnPage(claim);
        String claimNum = searchPage.getTextFromTable("Таблица результаты поиска", 1, "Номер заявки");
        String statusClaim = searchPage.getTextFromTable("Таблица результаты поиска", 1, "Статус заявки");
        assertIsTrue(claimNum.equals(claim),
                "Значение в столбце Номер заявки должно быть равно " + claim + ". Фактическое значение: " + claimNum);
        assertIsTrue(statusClaim.equals("Кредит разрешен"),
                "Значение в столбце Статус заявки должно быть равно Кредит разрешен. Фактическое значение: " + statusClaim);
    }

    @ParameterizedTest
    @CsvSource({
            "1720099, Зарплатный клиент с полными зачислениями, FormSpravType5, Client_Salary",
            "1720100, Госслужащие, NO, Comp_Type_Public_Servant_Spark"})
    @Tag("displaying_test_results_1720099_1720100")
    @DisplayName("{id} - Верификация. ФССП.Отображение результатов проверки сегмент- {displayName}")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void displaying_test_results_1720099_1720100(String id, String displayName, String incomeMain, String kpMain, TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "incomeMain", incomeMain,
                "kpMain", kpMain,
                "kpClient", "null",
                "Code", "stub1");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1651046.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.doubleClickByText(claim).switchToNewTab();
        fsspPage.checkDropDownListElements("Выпадающий список Результат проверки",
                List.of("Невозможно запросить ФССП",
                        "Исполнительное производство не найдено",
                        "Найдено исполнительное производство"))
                .closeCurrentTab();
    }
}
