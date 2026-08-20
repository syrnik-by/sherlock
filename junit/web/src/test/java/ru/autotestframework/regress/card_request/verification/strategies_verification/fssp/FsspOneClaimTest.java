package ru.autotestframework.regress.card_request.verification.strategies_verification.fssp;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.autotestframework.pages.PersonalAccountPage;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import java.util.List;

import static ru.autotestframework.steps.asserts.Asserts.assertIsEquals;
import static ru.autotestframework.utils.Constants.VERIFICATION;

@Tag("regress")
@Tag("card_request")
@Tag("verification")
@Tag("strategies_verification")
@Tag("fssp")
@Tag("fssp_one_claim")
@ClassName("Карточка заявки. Верификация. ЭФ стратегий верификации. ФССП. На одной заявке")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FsspOneClaimTest extends BaseTest {

    private static String claim;
    private final List<String> checkFields = List.of("Чек-бокс Активные ИП по кредитным платежам, алиментам (свыше 1000 р.), уголовным штрафам",
            "Чек-бокс Закрытые по ст.46 ИП по кредитным платежам, алиментам (свыше 1000 р.), уголовным штрафам",
            "Чек-бокс Прочие активные ИП",
            "Чек-бокс Закрытые ИП по кредитным платежам",
            "Чек-бокс Закрытые ИП по статье 47 (банкротство)");

    @BeforeAll
    public void createClaim(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1644911.json", 1, testInfo).get(0);
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
        fsspPage.closeCurrentTab();
    }

    @AfterAll
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("check_display_strategy_name_1644877")
    @DisplayName("1644877 - ЭФ ФССП. Проверка отображения наименования стратегии ФССП")
    @WorkItemIds({"1644877"})
    public void check_display_strategy_name_1644877() {
        fsspPage.checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1");
    }

    @Test
    @Tag("smoke")
    @Tag("check_list_result_1644878")
    @DisplayName("1644878 - ЭФ ФССП. Проверка списка \"Результаты проверки\" для стратегии ФССП")
    @WorkItemIds({"1644878"})
    public void check_list_result_1644878() {
        actionsClaimSteps.executeQuery(VERIFICATION, "SELECT vdcsr.description FROM vrf_dir_check_step_result vdcsr " +
                "JOIN vrf_dir_check_step_result_check_type vdcsrct ON vdcsrct.dir_check_step_result_id = vdcsr.id " +
                "WHERE vdcsrct.check_type_code = 'FSSP'");
        List<String> valuesFromDb = actionsClaimSteps.getValuesFromResponseDb("description");
        fsspPage.checkDropDownListElements("Выпадающий список Результат проверки", valuesFromDb);
    }

    @Test
    @Tag("smoke")
    @Tag("check_not_display_fields_1644879")
    @DisplayName("1644879 - ЭФ ФССП. Проверка НЕотображения дополнительных полей для ввода при значении результата проверки отличным от «Исполнительное производство найдено»")
    @WorkItemIds({"1644879"})
    public void check_not_display_strategy_name_1644879() {
        List<String> checkOfResults = List.of("Невозможно запросить ФССП", "Исполнительное производство не найдено");
        for (String result : checkOfResults) {
            fsspPage.selectValueFromDropDownList("Выпадающий список Результат проверки", result);
            checkFields.forEach(fieldName -> fsspPage.assertElementByTitleVisibility(fieldName, "не отображается"));
        }
    }

    @Test
    @Tag("check_display_fields_1644880")
    @DisplayName("1644880 - ЭФ ФССП. Проверка отображения новых полей для ввода информации при значении результата проверки «Исполнительное производство найдено»")
    @WorkItemIds({"1644880"})
    public void check_display_strategy_name_1644880() {
        fsspPage.selectValueFromDropDownList("Выпадающий список Результат проверки", "Найдено исполнительное производство");
        checkFields.forEach(fieldName -> fsspPage
                .assertElementByTitleVisibility(fieldName, "отображается")
                .assertElementByTitleActivity(fieldName, "активен"));
    }

    @Test
    @Tag("check_display_fields_1644881")
    @DisplayName("1644881 - ЭФ ФССП. Проверка изменения результата проверки со значения «Исполнительное производство найдено» на любое другое значение в случае, если дополнительная информация был введена")
    @WorkItemIds({"1644881"})
    public void checking_change_result_1644881() {
        fsspPage.selectValueFromDropDownList("Выпадающий список Результат проверки", "Исполнительное производство не найдено").selectValueFromDropDownList("Выпадающий список Результат проверки", "Найдено исполнительное производство")
                .clickOnElement("Чек-бокс Активные ИП по кредитным платежам, алиментам (свыше 1000 р.), уголовным штрафам")
                .assertElementByTitleSelected("Чек-бокс Активные ИП по кредитным платежам, алиментам (свыше 1000 р.), уголовным штрафам", "выбран")
                .clickOnElement("Чек-бокс Закрытые по ст.46 ИП по кредитным платежам, алиментам (свыше 1000 р.), уголовным штрафам")
                .assertElementByTitleSelected("Чек-бокс Закрытые по ст.46 ИП по кредитным платежам, алиментам (свыше 1000 р.), уголовным штрафам", "выбран")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Невозможно запросить ФССП");
        checkFields.forEach(fieldName -> fsspPage.assertElementByTitleVisibility(fieldName, "не отображается"));
    }

    @Test
    @Tag("check_display_fields_1644874")
    @DisplayName("1644874 - ЭФ ФССП. Проверка списка дополнительных полей для ввода информации и возможность их множественного выбора")
    @WorkItemIds({"1644874"})
    public void checking_additional_fields_1644874() {
        fsspPage.selectValueFromDropDownList("Выпадающий список Результат проверки", "Найдено исполнительное производство");
        checkFields.forEach(fieldName -> fsspPage.clickOnElement(fieldName));
        checkFields.forEach(selectedFieldName -> fsspPage.assertElementByTitleSelected(selectedFieldName, "выбран"));
    }

    @Test
    @Tag("check_display_fields_1644876")
    @DisplayName("1644876 - ЭФ ФССП. Проверка появления и ввода значения в поле «Сумма действующих ИП» при выборе дополнительного поля «Прочие активные ИП»")
    @WorkItemIds({"1644876"})
    public void checking_input_value_1644876() {
        String fieldTitle = "Поле ввода Сумма действующего ИП";
        fsspPage.selectValueFromDropDownList("Выпадающий список Результат проверки", "Найдено исполнительное производство")
                .clickOnElement("Чек-бокс Прочие активные ИП")
                .assertElementByTitleVisibility(fieldTitle, "отображается")
                .clickOnElement("Кнопка Далее")
                .waitText(2, "Пожалуйста, введите сумму ИП")
                .clickOnElement("Кнопка ОК")
                .fillInput(fieldTitle, "-111");
        assertIsEquals("111", fsspPage.getValueByElementTitle(fieldTitle), fieldTitle);
    }
}
