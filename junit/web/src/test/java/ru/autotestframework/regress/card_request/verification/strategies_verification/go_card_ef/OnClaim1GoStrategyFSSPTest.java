package ru.autotestframework.regress.card_request.verification.strategies_verification.go_card_ef;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static ru.autotestframework.steps.asserts.Asserts.*;
import static ru.autotestframework.steps.asserts.Asserts.assertIsEquals;
import static ru.autotestframework.utils.Constants.DF;
import static ru.autotestframework.utils.Constants.VERIFICATION;


@Tag("regress")
@Tag("card_request")
@Tag("verification")
@Tag("go_card_ef")
@Tag("on_claim_2_go_employer_verification")
@ClassName("Карточка заявки. Верификация. ЭФ стратегий верификации. ГО карточка ЭФ. На заявке Тип №1 ГО со стратегии ФССП")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OnClaim1GoStrategyFSSPTest extends BaseTest {

    private String claim;

    @BeforeEach
    public void login(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3207063.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi()
                    .openMenuLinks("Личный кабинет")
                    .goTo(personalAccountPage);
        }
    }

    @AfterAll
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "3208438; Экранная форма стратегии \"Вопрос в ГО\". Отображение в поле \"Стратегия\". ФССП; Стратегия; ФССП",
            "3208425; Экранная форма стратегии \"Вопрос в ГО\". Отображение в поле \"Результат\". ФССП; Результат; Вопрос в ГО"
    })
    @Tag("smoke")
    @Tag("on_screen_strategy_form")
    @DisplayName("{id} - {displayName}")
    @WorkItemIds({"{id}"})
    public void on_screen_strategy_form(String id, String displayName, String columnName, String resultValue, TestInfo testInfo) {
        personalAccountPage.doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .waitBusyCondition()
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Методологический")
                .fillInput("Поле ввода Комментарий", "ТестАТ")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .switchToOneTab().waitBusyCondition();

        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage
                .clickOnElement("Раздел Вопрос в ГО");
        assertIsTrue(queuesPage.getTextFromTable("Таблица Очереди", 1, "Статус заявки").
                        equals("На рассмотрении"),
                "Значение в столбце Статус заявки должно быть На рассмотрении");

        loginPage.doubleClickByText(claim)
                .goTo(questionInGoPage).switchToNewTab()
                .elementByTitleContains("Поле Наименование стратегии", "Вопрос в ГО");
        List<String> values = questionInGoPage.getListValuesByColumnName("Таблица Степпер", columnName);
        assertContains(values.toString(), resultValue);

        questionInGoPage.closeCurrentTab();
        personalAccountPage
                .clickOnElement("Раздел Верификация");
    }

    @Test
    @Tag("ef_question_to_go_check_subject_borrower_2652424")
    @DisplayName("2652424 - Экранная форма стратегии \"Вопрос в ГО\". Отображение в поле \"Объект проверки\". Заемщик")
    @WorkItemIds({"2652424"})
    public void ef_question_to_go_check_subject_borrower_2652424() {
        personalAccountPage.clickOnElement("Раздел Верификация")
                .waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(verificationStrategyPage)
                .waitBusyCondition()
                .checkElementByTitleContains("Поле Наименование стратегии", "ФССП")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Методологический")
                .fillInput("Поле Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .waitBusyCondition()
                .switchToOneTab();
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.clickOnElement("Раздел Вопрос в ГО")
                .waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(questionInGoPage);
        assertIsEquals("Заемщик - Романов Юрий Иванович 05.12.1990",
                questionInGoPage.getTextFromTable("Таблица Степпер", 1, "Объект проверки"), "Объект проверки");
        questionInGoPage
                .closeCurrentTab()
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Верификация");
    }

    @Test
    @Tag("check_result_question_in_go_mandatory_area_2652415")
    @DisplayName("2652415 - Результат проверки Вопрос в ГО. Обязательность поля \"Комментарий\"")
    @WorkItemIds({"2652415"})
    public void check_result_question_in_go_mandatory_area_2652415() {
        personalAccountPage.clickOnElement("Раздел Верификация")
                .waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(verificationStrategyPage)
                .waitBusyCondition()
                .checkElementByTitleContains("Поле Наименование стратегии", "ФССП")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Методологический")
                .fillInput("Поле Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .waitBusyCondition()
                .switchToOneTab();
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.clickOnElement("Раздел Вопрос в ГО")
                .waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(questionInGoPage)
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Вопрос решен")
                .clickOnElement("Кнопка Далее")
                .assertElementByTitleVisibility("Модальное окно Заполнение поля", "отображается")
                .checkElementByTitleContains("Модальное окно Заполнение поля", "Пожалуйста, заполните поле \"Комментарий\"")
                .closeCurrentTab()
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Верификация");
    }

    @Test
    @Tag("assigning_strategies_question_in_go_mandatory_area_question_type_3208397")
    @DisplayName("3208397 - Назначение стратегии \"Вопрос в ГО\". Обязательность поля \"Тип вопроса\"")
    @WorkItemIds({"3208397"})
    public void assigning_strategies_question_in_go_mandatory_area_question_type_3208397() {
        personalAccountPage.clickOnElement("Раздел Верификация")
                .waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(verificationStrategyPage)
                .waitBusyCondition()
                .checkElementByTitleContains("Поле Наименование стратегии", "ФССП")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .fillInput("Поле Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .assertElementByTitleVisibility("Модальное окно Заполнение поля", "отображается")
                .checkElementByTitleContains("Модальное окно Заполнение поля", "Пожалуйста, выберите тип вопроса")
                .closeCurrentTab()
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Верификация");
    }

    @Test
    @Tag("assigning_strategies_question_in_go_mandatory_area_commentary_3208398")
    @DisplayName("3208398 - Назначение стратегии \"Вопрос в ГО\". Обязательность поля \"Комментарий\"")
    @WorkItemIds({"3208398"})
    public void assigning_strategies_question_in_go_mandatory_area_commentary_3208398() {
        personalAccountPage.clickOnElement("Раздел Верификация")
                .waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(verificationStrategyPage)
                .waitBusyCondition()
                .checkElementByTitleContains("Поле Наименование стратегии", "ФССП")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Методологический")
                .clickOnElement("Кнопка Далее")
                .assertElementByTitleVisibility("Модальное окно Заполнение поля", "отображается")
                .checkElementByTitleContains("Модальное окно Заполнение поля", "Пожалуйста, заполните комментарий")
                .closeCurrentTab()
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Верификация");
    }

    @Test
    @Tag("assigning_strategies_question_in_go_question_type_methodological_3208399")
    @DisplayName("3208399 - Назначение стратегии \"Вопрос в ГО\". При выборе \"Тип вопроса\" = \"Методологический\"")
    @WorkItemIds({"3208399"})
    public void assigning_strategies_question_in_go_question_type_methodological_3208399() {
        personalAccountPage.clickOnElement("Раздел Верификация")
                .waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(verificationStrategyPage)
                .waitBusyCondition()
                .checkElementByTitleContains("Поле Наименование стратегии", "ФССП")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Методологический")
                .fillInput("Поле Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .waitBusyCondition()
                .switchToOneTab()
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .waitBusyCondition()
                .searchClaimOnPage(claim);
        String strategyClaim = queuesPage.getTextFromTable("Таблица результаты поиска", 1, "Стратегия");
        assertIsEquals("Вопрос в ГО", strategyClaim,
                "Значение в столбце Статус заявки должно быть равно Вопрос в ГО+ Фактическое значение: " + strategyClaim);
        queuesPage.resetFilters()
                .openMenuLinks("Личный кабинет")
                .goTo(personalAccountPage);
    }

    @Test
    @Tag("ef_question_to_go_display_data_in_stepper_3208433")
    @DisplayName("3208433 - Экранная форма стратегии \"Вопрос в ГО\". Отображение данных в степпере при отправке со стратегии объекта Заёмщик")
    @WorkItemIds({"3208433"})
    public void ef_question_to_go_display_data_in_stepper_3208433() {
        personalAccountPage.clickOnElement("Раздел Верификация")
                .waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(verificationStrategyPage)
                .waitBusyCondition()
                .checkElementByTitleContains("Поле Наименование стратегии", "ФССП")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Методологический")
                .fillInput("Поле Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .waitBusyCondition()
                .switchToOneTab();
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.clickOnElement("Раздел Вопрос в ГО")
                .waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(questionInGoPage)
                .checkElementByTitleContains("Поле Степпер", "Романов Юрий Иванович 05.12.1990")
                .closeCurrentTab()
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Верификация");
    }

    @Test
    @Tag("ef_question_to_go_display_in_block_date_3208426")
    @DisplayName("3208426 - Экранная форма стратегии \"Вопрос в ГО\". Отображение в поле \"Дата\"")
    @WorkItemIds({"3208426"})
    public void ef_question_to_go_display_in_block_date_3208426() {
        personalAccountPage.clickOnElement("Раздел Верификация")
                .waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(verificationStrategyPage)
                .waitBusyCondition()
                .checkElementByTitleContains("Поле Наименование стратегии", "ФССП")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Методологический")
                .fillInput("Поле Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку");
        LocalDateTime time = LocalDateTime.now();
        verificationStrategyPage.goTo(personalAccountPage)
                .waitBusyCondition()
                .switchToOneTab();
        actionsClaimSteps.executeQuery(VERIFICATION,
                "SELECT date_finished FROM vrf_check_group_result " +
                        "WHERE claim_id = '" + claim + "';");
        OffsetDateTime dateTimeDb = OffsetDateTime.parse(actionsClaimSteps.getVariables("date_finished"));
        assertIsEquals(time.format(DF), dateTimeDb.format(DF), "date_finished");
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.clickOnElement("Раздел Вопрос в ГО")
                .waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(questionInGoPage);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        assertIsEquals(time.format(formatter),
                questionInGoPage.getTextFromTable("Таблица Степпер", 1, "Дата"), "Дата");
        questionInGoPage.closeCurrentTab()
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Верификация");
    }

    @Test
    @Tag("ef_question_to_go_display_in_block_fio_3208430")
    @DisplayName("3208430 - Экранная форма стратегии \"Вопрос в ГО\". Отображение в поле \"ФИО Пользователя\"")
    @WorkItemIds({"3208430"})
    public void ef_question_to_go_display_in_block_fio_3208430() {
        personalAccountPage.clickOnElement("Раздел Верификация")
                .waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(verificationStrategyPage)
                .waitBusyCondition()
                .checkElementByTitleContains("Поле Наименование стратегии", "ФССП")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Методологический")
                .fillInput("Поле Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .waitBusyCondition()
                .switchToOneTab();
        actionsClaimSteps.executeQuery(VERIFICATION,
                "SELECT user_fio FROM vrf_check_group_result " +
                        "WHERE claim_id = '" + claim + "';");
        assertIsEquals("Автоматическое Тестирование1", actionsClaimSteps.getVariables("user_fio"), "user_fio");
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.clickOnElement("Раздел Вопрос в ГО")
                .waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(questionInGoPage);
        assertIsEquals("Автоматическое Тестирование1",
                questionInGoPage.getTextFromTable("Таблица Степпер", 1, "ФИО пользователя"), "ФИО Пользователя");
        questionInGoPage.closeCurrentTab()
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Верификация");
    }

    @Test
    @Tag("ef_question_to_go_display_in_block_question_type_fssp_3208434")
    @DisplayName("3208434 - Экранная форма стратегии \"Вопрос в ГО\". Отображение в поле \"Тип вопроса\". ФССП")
    @WorkItemIds({"3208434"})
    public void ef_question_to_go_display_in_block_question_type_fssp_3208434() {
        personalAccountPage.clickOnElement("Раздел Верификация")
                .waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(verificationStrategyPage)
                .waitBusyCondition()
                .checkElementByTitleContains("Поле Наименование стратегии", "ФССП")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Методологический")
                .fillInput("Поле Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .waitBusyCondition()
                .switchToOneTab();
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.clickOnElement("Раздел Вопрос в ГО")
                .waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(questionInGoPage);
        assertIsEquals("Методологический",
                questionInGoPage.getTextFromTable("Таблица Степпер", 1, "Тип вопроса"), "Тип вопроса");
        questionInGoPage.closeCurrentTab()
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Верификация");
    }

    @Test
    @Tag("ef_question_to_go_block_check_result_3208479")
    @DisplayName("3208479 - Экранная форма стратегии \"Вопрос в ГО\". Поле \"Результат проверки\"")
    @WorkItemIds({"3208479"})
    public void ef_question_to_go_block_check_result_3208479() {
        personalAccountPage.clickOnElement("Раздел Верификация")
                .waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(verificationStrategyPage)
                .waitBusyCondition()
                .checkElementByTitleContains("Поле Наименование стратегии", "ФССП")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Методологический")
                .fillInput("Поле Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .waitBusyCondition()
                .switchToOneTab();
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.clickOnElement("Раздел Вопрос в ГО")
                .waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(questionInGoPage)
                .checkElementByTitleContains("Выпадающий список Результат проверки", " ")
                .checkDropDownListElements("Выпадающий список Результат проверки", List.of(
                        "",
                        "Вопрос решен",
                        "Назначить дополнительную проверку без возврата на этап ГО",
                        "Назначить дополнительную проверку с возвратом на этап ГО"
                ))
                .closeCurrentTab()
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Верификация");
    }

    @Test
    @Tag("ef_question_to_go_block_question_type_3208487")
    @DisplayName("3208487 - Экранная форма стратегии \"Вопрос в ГО\". Поле \"Тип вопроса\"")
    @WorkItemIds({"3208487"})
    public void ef_question_to_go_block_question_type_3208487() {
        personalAccountPage.clickOnElement("Раздел Верификация")
                .waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(verificationStrategyPage)
                .waitBusyCondition()
                .checkElementByTitleContains("Поле Наименование стратегии", "ФССП")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Методологический")
                .fillInput("Поле Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .waitBusyCondition()
                .switchToOneTab();
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.clickOnElement("Раздел Вопрос в ГО")
                .waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(questionInGoPage)
                .checkElementByTitleContains("Выпадающий список Тип вопроса", "Методологический")
                .checkDropDownListElements("Выпадающий список Тип вопроса", List.of(
                        "",
                        "Методологический",
                        "Подозрение на Fraud",
                        "Технический",
                        "Методологический / Технический",
                        "Методологический / Подозрение на Fraud",
                        "Подозрение на Fraud / Технический",
                        "Методологический / Подозрение на Fraud / Технический"
                ))
                .closeCurrentTab()
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Верификация");
    }

    @Test
    @Tag("check_result_question_in_go_mandatory_area_question_type_3208477")
    @DisplayName("3208477 - Результат проверки Вопрос в ГО. Обязательность поля \"Тип вопроса\"")
    @WorkItemIds({"3208477"})
    public void check_result_question_in_go_mandatory_area_question_type_3208477() {
        personalAccountPage.clickOnElement("Раздел Верификация")
                .waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(verificationStrategyPage)
                .waitBusyCondition()
                .checkElementByTitleContains("Поле Наименование стратегии", "ФССП")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Методологический")
                .fillInput("Поле Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .waitBusyCondition()
                .switchToOneTab();
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.clickOnElement("Раздел Вопрос в ГО")
                .waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(questionInGoPage)
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Вопрос решен")
                .fillInput("Поле Комментарий", "test")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "")
                .clickOnElement("Кнопка Далее")
                .assertElementByTitleVisibility("Модальное окно Заполнение поля", "отображается")
                .checkElementByTitleContains("Модальное окно Заполнение поля", "Пожалуйста, укажите тип вопроса")
                .closeCurrentTab()
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Верификация");
    }

    @Test
    @Tag("check_result_question_in_go_mandatory_area_commentary_3208471")
    @DisplayName("3208471 - Результат проверки Вопрос в ГО. Обязательность поля \"Комментарий\"")
    @WorkItemIds({"3208471"})
    public void check_result_question_in_go_mandatory_area_commentary_3208471() {
        personalAccountPage.clickOnElement("Раздел Верификация")
                .waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(verificationStrategyPage)
                .waitBusyCondition()
                .checkElementByTitleContains("Поле Наименование стратегии", "ФССП")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Методологический")
                .fillInput("Поле Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .waitBusyCondition()
                .switchToOneTab();
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.clickOnElement("Раздел Вопрос в ГО")
                .waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(questionInGoPage)
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Вопрос решен")
                .clickOnElement("Кнопка Далее")
                .assertElementByTitleVisibility("Модальное окно Заполнение поля", "отображается")
                .checkElementByTitleContains("Модальное окно Заполнение поля", "Пожалуйста, заполните поле \"Комментарий\"")
                .closeCurrentTab()
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Верификация");
    }

    @Test
    @Tag("check_result_question_in_go_mandatory_check_result_3208480")
    @DisplayName("3208480 - Результат проверки Вопрос в ГО. Обязательность поля \"Результат проверки\"")
    @WorkItemIds({"3208480"})
    public void check_result_question_in_go_mandatory_check_result_3208480() {
        personalAccountPage.clickOnElement("Раздел Верификация")
                .waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(verificationStrategyPage)
                .waitBusyCondition()
                .checkElementByTitleContains("Поле Наименование стратегии", "ФССП")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Методологический")
                .fillInput("Поле Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .waitBusyCondition()
                .switchToOneTab();
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.clickOnElement("Раздел Вопрос в ГО")
                .waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(questionInGoPage)
                .fillInput("Поле Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .assertElementByTitleVisibility("Модальное окно Заполнение поля", "отображается")
                .checkElementByTitleContains("Модальное окно Заполнение поля", "Пожалуйста, заполните Результат проверки")
                .closeCurrentTab()
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Верификация");
    }

    @Test
    @Tag("check_result_question_in_go_mandatory_strategy_result_3208474")
    @DisplayName("3208474 - Результат проверки Вопрос в ГО. Обязательность стратегии при результате \"Назначить дополнительную проверку с возвратом на этап ГО\"")
    @WorkItemIds({"3208474"})
    public void check_result_question_in_go_mandatory_strategy_result_3208474() {
        personalAccountPage.clickOnElement("Раздел Верификация")
                .waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(verificationStrategyPage)
                .waitBusyCondition()
                .checkElementByTitleContains("Поле Наименование стратегии", "ФССП")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Методологический")
                .fillInput("Поле Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .waitBusyCondition()
                .switchToOneTab();
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.clickOnElement("Раздел Вопрос в ГО")
                .waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(questionInGoPage)
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Назначить дополнительную проверку с возвратом на этап ГО")
                .fillInput("Поле Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .assertElementByTitleVisibility("Модальное окно Заполнение поля", "отображается")
                .checkElementByTitleContains("Модальное окно Заполнение поля", "Пожалуйста, укажите дополнительные проверки и объекты проверки")
                .closeCurrentTab()
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Верификация");
    }
}