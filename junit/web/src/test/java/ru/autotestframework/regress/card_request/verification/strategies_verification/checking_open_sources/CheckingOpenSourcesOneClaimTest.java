package ru.autotestframework.regress.card_request.verification.strategies_verification.checking_open_sources;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.autotestframework.pages.PersonalAccountPage;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import java.util.List;
import java.util.stream.Collectors;

import static ru.autotestframework.steps.asserts.Asserts.assertIsEquals;
import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;
import static ru.autotestframework.utils.Constants.VERIFICATION;

@Tag("regress")
@Tag("card_request")
@Tag("verification")
@Tag("strategies_verification")
@Tag("checking_open_sources_one_claim")
@ClassName("Карточка заявки. Верификация. ЭФ стратегий верификации. Проверка открытых источников. На одной заявке")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CheckingOpenSourcesOneClaimTest extends BaseTest {

    private static String claim;

    @BeforeAll
    public void createClaim(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1650804.json", 1, testInfo).get(0);
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
    @Tag("smoke")
    @Tag("check_name_1650788")
    @DisplayName("1650788 - ЭФ Открытые источники. Проверка наименования стратегии и назначения всех проверок из группы OPEN_SOURCE для стратегии \"Проверка открытые источники\"")
    @WorkItemIds({"1650788"})
    public void check_name_1650788() {
        checkingOpenSourcesPage.checkElementByTitleEquals("Поле Наименование стратегии", "Проверка открытых источников/Версия 1")
                .assertElementByTitleVisibility("Раздел Проверка сайта", "отображается")
                .assertElementByTitleVisibility("Раздел Проверка работодателя", "отображается")
                .assertElementByTitleVisibility("Раздел Привязка телефона из анкеты", "отображается")
                .assertElementByTitleVisibility("Раздел Бесконтактное подтверждение трудоустройства", "отображается")
                .assertElementByTitleVisibility("Раздел Брокерские услуги", "отображается");
    }

    @Test
    @Tag("checking_dropdown_list_values_1650786")
    @DisplayName("1650786 - ЭФ Открытые источники. Проверка значений выпадающего списка поля \"Проверка сайта\" и появления новых полей при выборе значений")
    @WorkItemIds({"1650786"})
    public void checking_dropdown_list_values_1650786() {
        actionsClaimSteps.executeQuery(VERIFICATION, "SELECT vdcsr.description from vrf_dir_check_step_result vdcsr " +
                "join vrf_dir_check_step_result_check_type vdcsrct ON vdcsrct.dir_check_step_result_id = vdcsr.id " +
                "WHERE vdcsrct.check_type_code = 'OPEN_SOURCE_SITE' and dir_check_step_result_parent_id is null;");
        List<String> valuesFromDb = actionsClaimSteps.getValuesFromResponseDb("description");

        checkingOpenSourcesPage
                .checkDropDownListElements("Выпадающий список Проверка сайта", valuesFromDb)
                .selectValueFromDropDownList("Выпадающий список Проверка сайта", "Сайт найден и негатив не выявлен")
                .assertElementByTitleVisibility("Поле ввода Источник подтверждения", "отображается")
                .selectValueFromDropDownList("Выпадающий список Проверка сайта", "Выявлен негатив")
                .assertElementByTitleVisibility("Выпадающий список C выбором вида негатива", "отображается")
                .assertElementByTitleVisibility("Поле ввода Источник подтверждения", "отображается")

                .clickOnElement("Кнопка Далее")
                .waitText(2, "Пожалуйста, выберите негатив").waitBusyCondition()
                .clickOnElement("Кнопка ОК");
    }

    @Test
    @Tag("check_flag_1650790")
    @DisplayName("1650790 - ЭФ Открытые источники. Проверка флага обязательности для проверок, которые пришли из МС верификации с признаком is_optional = false")
    @WorkItemIds({"1650790"})
    public void check_flag_1650790() {
        checkingOpenSourcesPage
                .assertElementByTitleVisibility("Флаг Проверить!", "отображается");
    }

    @Test
    @Tag("checking_appearance_new_field_negative_1650784")
    @DisplayName("1650784 - ЭФ Открытые источники. Проверка появления нового поля ввода для ввода негативных результатов при проставлении результата \"Сайт найден и выявлен негатив\" в стратегии \"Проверка сайта\"")
    @WorkItemIds({"1650784"})
    public void checking_appearance_new_field_negative_1650784() {
        List<String> actualCheckBox;
        actionsClaimSteps.executeQuery(VERIFICATION, "select vdcsr.description from vrf_dir_check_step_result vdcsr " +
                "join vrf_dir_check_step_result_check_type vdcsrct on vdcsrct.dir_check_step_result_id = vdcsr.id " +
                "where vdcsrct.check_type_code = 'OPEN_SOURCE_SITE' and dir_check_step_result_parent_id is not null;");
        List<String> expectedCheckBoxFromBD = actionsClaimSteps.getValuesFromResponseDb("description");

        checkingOpenSourcesPage
                .selectValueFromDropDownList("Выпадающий список Проверка сайта", "Выявлен негатив").waitBusyCondition()
                .clickOnElement("Выпадающий список C выбором вида негатива");
        actualCheckBox = checkingOpenSourcesPage.getListCheckBox("Список Чек-боксов Выявлен негатив");
        assertIsTrue(expectedCheckBoxFromBD.containsAll(actualCheckBox), "Список " + expectedCheckBoxFromBD + " соответствует списку " + actualCheckBox);

        checkingOpenSourcesPage
                .clickOnElement("Чек-бокс Сайт дублер")
                .clickOnElement("Чек-бокс Сайт создан менее 6 месяцев")
                .clickOnElement("Интерфейс")
                .assertElementByTitleVisibility("Плашка Сайт дублер", "отображается")
                .assertElementByTitleVisibility("Плашка Сайт создан менее 6 месяцев", "отображается")

                .selectValueFromDropDownList("Выпадающий список Проверка сайта", "Сайт не найден")
                .assertElementByTitleVisibility("Выпадающий список C выбором вида негатива", "не отображается")
                .selectValueFromDropDownList("Выпадающий список Проверка сайта", "");
    }

    @Test
    @Tag("checking_appearance_new_field_1650785")
    @DisplayName("1650785 - ЭФ Открытые источники. Проверка появления нового поля для ввода данных и перечня значений из выпадающего списка в стратегии \"Проверка работодателя\"")
    @WorkItemIds({"1650785"})
    public void checking_appearance_new_field_1650785() {
        List<String> actualCheckBox;
        actionsClaimSteps.executeQuery(VERIFICATION, "select vdcsr.description from vrf_dir_check_step_result vdcsr " +
                "join vrf_dir_check_step_result_check_type vdcsrct on vdcsrct.dir_check_step_result_id = vdcsr.id " +
                "where vdcsrct.check_type_code = 'OPEN_SOURCE_EMPLOYER' and dir_check_step_result_parent_id is not null;");
        List<String> expectedCheckBoxFromBD = actionsClaimSteps.getValuesFromResponseDb("description").stream().map(String::trim).collect(Collectors.toList());

        actualCheckBox = checkingOpenSourcesPage.getListCheckBox("Список Чек-боксов Проверка работодателя").stream().map(String::trim).collect(Collectors.toList());
        assertIsTrue(expectedCheckBoxFromBD.containsAll(actualCheckBox), "Список " + expectedCheckBoxFromBD + " не соответствует списку " + actualCheckBox);

        checkingOpenSourcesPage
                .clickOnElement("Чек-бокс Негатив по работодателю не выявлен")
                .assertElementByTitleVisibility("Чек-бокс Негатив на работодателя в сети", "не отображается")
                .assertElementByTitleVisibility("Чек-бокс Решение о санации", "не отображается")
                .clickOnElement("Чек-бокс Негатив по работодателю не выявлен")

                .clickOnElement("Чек-бокс Негатив на работодателя в сети")
                .clickOnElement("Чек-бокс Решение о санации")
                .assertElementByTitleSelected("Чек-бокс Негатив на работодателя в сети", "выбран")
                .assertElementByTitleSelected("Чек-бокс Решение о санации", "выбран")

                .clickOnElement("Чек-бокс Негатив на работодателя в сети")
                .clickOnElement("Чек-бокс Решение о санации");
    }

    @Test
    @Tag("checking_employer_indicating_amount_1650792")
    @DisplayName("1650792 - ЭФ Открытые источники. Проверка добавления дополнительных полей для ввода при выборе значений «ИП на работодателя с указанием сумм» и «Действующие арбитражные дела» в стратегии \"Проверка работодателя\"")
    @WorkItemIds({"1650792"})
    public void checking_employer_indicating_amount_1650792() {
        String firstFieldTitle = "Поле ввода Введите сумму ИП";
        String secondFieldTitle = "Поле ввода Введите сумму";

        checkingOpenSourcesPage
                .clickOnElement("Чек-бокс ИП на работодателя с указанием сумм")
                .assertElementByTitleVisibility(firstFieldTitle, "отображается")
                .fillInput(firstFieldTitle, "-111.");
        assertIsEquals("111", checkingOpenSourcesPage.getValueByElementTitle(firstFieldTitle), firstFieldTitle);

        checkingOpenSourcesPage
                .clickOnElement("Чек-бокс Действующие арбитражные дела")
                .assertElementByTitleVisibility(secondFieldTitle, "отображается")
                .fillInput(secondFieldTitle, "-111.");
        assertIsEquals("111", checkingOpenSourcesPage.getValueByElementTitle(secondFieldTitle), secondFieldTitle);

        checkingOpenSourcesPage
                .clickOnElement("Чек-бокс ИП на работодателя с указанием сумм")
                .clickOnElement("Чек-бокс Действующие арбитражные дела")
                .assertElementByTitleVisibility(firstFieldTitle, "не отображается")
                .assertElementByTitleVisibility(secondFieldTitle, "не отображается");
    }

    @Test
    @Tag("checking_contactless_confirmation_employment_1650789")
    @DisplayName("1650789 - ЭФ Открытые источники. Проверка значений выпадающего списка \"Бесконтактное подтверждение трудоустройства\" и появление нового поля для ввода информации в стратегии \"Бесконтактное подтверждение занятости\" ")
    @WorkItemIds({"1650789"})
    public void checking_contactless_confirmation_employment_1650789() {
        actionsClaimSteps.executeQuery(VERIFICATION, "select vdcsr.description from vrf_dir_check_step_result vdcsr " +
                "join vrf_dir_check_step_result_check_type vdcsrct on vdcsrct.dir_check_step_result_id = vdcsr.id " +
                "where vdcsrct.check_type_code = 'OPEN_SOURCE_EMPLOYER_CONTACTLESS' and dir_check_step_result_parent_id is null;");
        List<String> expectedCheckBoxFromBD = actionsClaimSteps.getValuesFromResponseDb("description");

        checkingOpenSourcesPage
                .checkDropDownListElements("Выпадающий список Бесконтактное подтверждение трудоустройства", expectedCheckBoxFromBD)
                .selectValueFromDropDownList("Выпадающий список Бесконтактное подтверждение трудоустройства", "Занятость подтверждена")
                .assertElementByTitleVisibility("Поле ввода Сайт работодателя", "отображается")
                .clickOnElement("Чек-бокс Негатив по работодателю не выявлен")

                .clickOnElement("Кнопка Далее")
                .waitText(2, "Пожалуйста, заполните сайт работодателя")
                .clickOnElement("Кнопка ОК")

                .selectValueFromDropDownList("Выпадающий список Бесконтактное подтверждение трудоустройства", "Занятость не подтверждена")
                .assertElementByTitleVisibility("Поле ввода Сайт работодателя", "не отображается")

                .clickOnElement("Чек-бокс Негатив по работодателю не выявлен")
                .selectValueFromDropDownList("Выпадающий список Бесконтактное подтверждение трудоустройства", "");
    }

    @Test
    @Tag("checkbox_display_brokerage_services_detected_1650793")
    @DisplayName("1650793 - ЭФ Открытые источники. Проверка отображения чек-бокса «Обнаружены объявления о брокерских услугах» и отображения нового поля для ввода текстового значения в стратегии «Брокерские услуги»")
    @WorkItemIds({"1650793"})
    public void checkbox_display_brokerage_services_detected_1650793() {
        checkingOpenSourcesPage
                .checkDropDownListElements("Выпадающий список Брокерские услуги", List.of("Обнаружены", "Не обнаружены"))
                .selectValueFromDropDownList("Выпадающий список Брокерские услуги", "Не обнаружены")
                .assertElementByTitleActivity("Кнопка Далее", "активен")
                .selectValueFromDropDownList("Выпадающий список Брокерские услуги", "Обнаружены")
                .assertElementByTitleVisibility("Поле ввода Источник подтверждения", "отображается")
                .assertElementByTitleActivity("Кнопка Далее", "активен")
                .selectValueFromDropDownList("Выпадающий список Брокерские услуги", "")
                .assertElementByTitleVisibility("Поле ввода Источник подтверждения", "не отображается");
    }

    @Test
    @Tag("checking_fixation_verification_results_1650787")
    @DisplayName("1650787 - ЭФ Открытые источники. Проверка фиксации результатов проверки по стратегии \"Проверка открытых источников\"")
    @WorkItemIds({"1650787"})
    public void checking_fixation_verification_results_1650787() {
        checkingOpenSourcesPage
                .clickOnElement("Кнопка Далее")
                .waitText(2, "Для завершения шага необходимо заполнить результат проверки или результат по заявке")
                .clickOnElement("Кнопка ОК")

                .selectValueFromDropDownList("Выпадающий список Проверка сайта", "Сайт найден и негатив не выявлен")
                .clickOnElement("Чек-бокс Негатив по работодателю не выявлен")
                .clickOnElement("Кнопка Далее")
                .waitText(2, "Пожалуйста, заполните источник подтверждения (проверка сайта)")
                .clickOnElement("Кнопка ОК")

                .clickOnElement("Чек-бокс Негатив по работодателю не выявлен")
                .selectValueFromDropDownList("Выпадающий список Проверка сайта", "");
    }

}
