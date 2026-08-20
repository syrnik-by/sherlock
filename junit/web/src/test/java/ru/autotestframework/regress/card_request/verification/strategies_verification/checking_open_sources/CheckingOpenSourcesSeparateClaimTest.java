package ru.autotestframework.regress.card_request.verification.strategies_verification.checking_open_sources;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.ExternalId;
import ru.psb.testit.annotations.WorkItemIds;

import java.util.List;
import java.util.Map;

import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;
import static ru.autotestframework.utils.Constants.VERIFICATION;

@Tag("regress")
@Tag("card_request")
@Tag("verification")
@Tag("strategies_verification")
@Tag("checking_open_sources_separate_claim")
@ClassName("Карточка заявки. Верификация. ЭФ стратегий верификации. Проверка открытых источников. На каждый кейс отдельная заявка")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CheckingOpenSourcesSeparateClaimTest extends BaseTest {

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
    @Tag("check_site_verification_1720124")
    @DisplayName("1720124 - Верификация.Открытые Источники - проверка сайта.Отображение результатов проверки сегмент-Частичные зарплатные клиенты")
    @WorkItemIds({"1720124"})
    public void check_site_verification_1720124(TestInfo testInfo) {
        List<String> actualCheckBox;
        List<String> listSiteVerification = List.of("Сайт найден и негатив не выявлен", "Сайт не найден", "Выявлен негатив");
        List<String> expectedCheckBox = List.of("Сайт дублер");

        Map<String, String> claimParams = Map.of(
                "incomeMain", "NO",
                "kpMain", "Client_Salary",
                "kpClient", "null",
                "Code", "stub7");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1651046.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab();

        checkingOpenSourcesPage
                .checkDropDownListElements("Выпадающий список Проверка сайта", listSiteVerification)
                .selectValueFromDropDownList("Выпадающий список Проверка сайта", "Выявлен негатив")
                .elementByTitleVisibility("Выпадающий список C выбором вида негатива", "отображается")
                .clickOnElement("Выпадающий список C выбором вида негатива");
        actualCheckBox = checkingOpenSourcesPage.getListCheckBox("Список Чек-боксов Выявлен негатив");
        assertIsTrue(expectedCheckBox.equals(actualCheckBox), "Список " + expectedCheckBox + " соответствует списку " + actualCheckBox);
    }

    @Test
    @Tag("check_employment_verification_dropdown_1720121")
    @DisplayName("1720121 - Верификация.Открытые источники - проверка работодателя.Отображение результатов проверки сегмент- Прочие ОПК")
    @WorkItemIds({"1720121"})
    public void check_employment_verification_dropdown_1720121(TestInfo testInfo) {
        List<String> actualCheckBox;
        List<String> expectedCheckBox = List.of("Негатив по работодателю не выявлен", "Негатив на работодателя в сети", "Решение о санации",
                "Сокращения в организации", "Собственник другой компании", "Запрос в МСБ за последние 6 месяцев", "Банкротство НЕ находится в стадии внешнего управления/конкурсного производства",
                "Банкротство находится в стадии внешнего управления/конкурсного производства", "Недостоверные сведения об адресе по данным ЕГРЮЛ/СПАРК", "Прочие недостоверные сведения по данным ЕГРЮЛ/СПАРК");

        Map<String, String> claimParams = Map.of(
                "incomeMain", "NO",
                "kpMain", "Comp_Type_OPK_Other",
                "kpClient", "null",
                "Code", "stub7");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1651046.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab();

        actualCheckBox = checkingOpenSourcesPage.getListCheckBox("Выпадающий список Проверка работодателя");
        assertIsTrue(expectedCheckBox.equals(actualCheckBox), "Список " + expectedCheckBox + " соответствует списку " + actualCheckBox);
    }

    @ParameterizedTest
    @CsvSource({
            "1720120, Открытые Источники - привязка телефона из анкеты, NO, Comp_Type_Public_Servant_Spark, stub8, Телефон привязан, Телефон не привязан, Выпадающий список Привязка телефона из анкеты",
            "1720122, Бесконтактное подтверждение трудоустройства, FormSpravType5, Client_Salary, stub9, Занятость подтверждена, Занятость не подтверждена, Выпадающий список Бесконтактное подтверждение трудоустройства",
            "1720123, Брокерские услуги, FormSpravType5, Comp_Type_OPK_Macro_War, stub10, Обнаружены, Не обнаружены, Выпадающий список Брокерские услуги"})
    @Tag("check_1720120_1720122_1720123")
    @DisplayName("{id} - Верификация.{displayName}.Отображение результатов проверки")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void check_1720120_1720122_1720123(String id, String displayName, String incomeMain, String kpMain, String code, String expectedCheckBox1, String expectedCheckBox2, String title, TestInfo testInfo) {
        List<String> expectedList = List.of("", expectedCheckBox1, expectedCheckBox2);

        Map<String, String> claimParams = Map.of(
                "incomeMain", incomeMain,
                "kpMain", kpMain,
                "kpClient", "null",
                "Code", code);
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1651046.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab();
        checkingOpenSourcesPage.checkDropDownListElements(title, expectedList);
    }

    @Test
    @Tag("check_phone_check_1650791")
    @DisplayName("1650791 - ЭФ Открытые источники. Проверка списка значений поля \"Привязка телефона из анкеты\" и появления нового поля для ввода информации в стратегии \"Привязка телефона\"")
    @WorkItemIds({"1650791"})
    public void check_phone_check_1650791(TestInfo testInfo) {
        actionsClaimSteps.executeQuery(VERIFICATION, "select vdcsr.description from vrf_dir_check_step_result vdcsr " +
                "join vrf_dir_check_step_result_check_type vdcsrct on vdcsrct.dir_check_step_result_id = vdcsr.id " +
                "where vdcsrct.check_type_code = 'OPEN_SOURCE_PHONE' and dir_check_step_result_parent_id is null;");
        List<String> expectedCheckBoxFromBD = actionsClaimSteps.getValuesFromResponseDb("description");

        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1650804.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage.doubleClickByText(claim).switchToNewTab();

        checkingOpenSourcesPage
                .checkDropDownListElements("Выпадающий список Привязка телефона из анкеты", expectedCheckBoxFromBD)
                .selectValueFromDropDownList("Выпадающий список Привязка телефона из анкеты", "Телефон привязан")
                .assertElementByTitleVisibility("Поле ввода Источник подтверждения", "отображается")
                .clickOnElement("Чек-бокс Негатив по работодателю не выявлен")

                .clickOnElement("Кнопка Далее")
                .waitText(2, "Пожалуйста, заполните источник подтверждения (проверка номера телефона)")
                .clickOnElement("Кнопка ОК")

                .fillInput("Поле ввода Источник подтверждения", "89012345678")
                .clickOnElement("Кнопка Далее")
                .assertElementByTitleVisibility("Иконка Завершен первый этап", "отображается")
                .assertElementByTitleActivity("Иконка Активен Второй этап", "активен")

                .clickOnElement("Иконка Завершен первый этап").waitBusyCondition()
                .clickOnElement("Кнопка Изменить результат")
                .assertElementByTitleActivity("Иконка Активен первый этап", "активен")
                .selectValueFromDropDownList("Выпадающий список Привязка телефона из анкеты", "Телефон не привязан")
                .assertElementByTitleVisibility("Поле ввода Источник подтверждения", "не отображается");
    }
}