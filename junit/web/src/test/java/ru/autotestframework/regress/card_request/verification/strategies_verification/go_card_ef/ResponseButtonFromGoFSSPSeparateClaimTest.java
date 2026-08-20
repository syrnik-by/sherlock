package ru.autotestframework.regress.card_request.verification.strategies_verification.go_card_ef;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.ExternalId;
import ru.psb.testit.annotations.WorkItemIds;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;
import static ru.autotestframework.utils.Constants.*;
import static ru.autotestframework.utils.Constants.DF;


@Tag("regress")
@Tag("card_request")
@Tag("verification")
@Tag("go_card_ef")
@Tag("on_claim_3_response_button_from_go")
@ClassName("Карточка заявки. Верификация. ЭФ стратегий верификации. ГО карточка ЭФ. На кейс отдельная заявка Кнопка Ответ от ГО ФССП")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ResponseButtonFromGoFSSPSeparateClaimTest extends BaseTest {

    private String claim;

    @BeforeEach
    public void login(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3210068.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi()
                    .openMenuLinks("Личный кабинет")
                    .goTo(personalAccountPage)
                    .clickOnElement("Раздел Верификация")
                    .waitBusyCondition();
        }
    }

    @AfterEach
    public void cleanQueueClaims() {
        verificationStrategyPage.closeCurrentTab();
        clearingQueueClaims.requestExpireAfterTestScenario();
    }


    @ParameterizedTest
    @CsvSource(value = {
            "2652419, Заполнение поля \"Комментарий\" для типа \"Вопрос\", 2, Комментарий, Коммент для ГО",
            "2652425, Заполнение поля \"Комментарий\" для типа \"Ответ\", 1, Комментарий, Коммент от ГО",
            "2652431, Заполнение поля \"Стратегия\" для типа \"Вопрос\". ФССП, 2, Стратегия, ФССП",
            "2652423, Заполнение поля \"Стратегия\" для типа \"Ответ\". ФССП, 1, Стратегия, Вопрос в ГО",
            "1740970, Заполнение поля \"Результат\" для типа \"Вопрос\". ФССП, 2, Результат, Вопрос в ГО",
            "1740973, Заполнение поля \"Результат\" для типа \"Ответ\". ФССП, 1, Результат, Вопрос решен"
    })
    @Tag("response_button_from_go_2652419")
    @DisplayName("{id} - Кнопка Ответ от ГО. {displayName}")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void response_button_from_go_2652419(String id, String displayName, int row, String column, String expectedValue) {
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(verificationStrategyPage)
                .waitBusyCondition()
                .checkElementByTitleContains("Поле Наименование стратегии", "ФССП")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Методологический")
                .fillInput("Поле Комментарий", "Коммент для ГО")
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
                .fillInput("Поле Комментарий", "Коммент от ГО")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .switchToOneTab()
                .waitBusyCondition()
                .clickOnElement("Раздел Верификация")
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(verificationStrategyPage)
                .waitBusyCondition()
                .checkElementByTitleContains("Поле Наименование стратегии", "ФССП/Версия 2")
                .clickOnElement("Кнопка Ответ от ГО");

        String actualValue = verificationStrategyPage.getTextFromTable("Таблица Ответ от ГО", row, column);
        assertIsTrue(actualValue.equals(expectedValue),
                "Значение в столбце " + column + " должно быть равно " + expectedValue + ". Фактическое значение: " + actualValue);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1740633, Заполнение поля \"Дата\" для типа \"Вопрос\", 2, FSSP",
            "1740747, Заполнение поля \"Дата\" для типа \"Ответ\", 1, QUESTION_GO"
    })
    @Tag("response_button_from_go_1740633_1740747")
    @DisplayName("{id} - Кнопка Ответ от ГО. {displayName}")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void response_button_from_go_1740633(String id, String displayName, int row, String groupCode) {
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(verificationStrategyPage)
                .waitBusyCondition()
                .checkElementByTitleContains("Поле Наименование стратегии", "ФССП")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Методологический")
                .fillInput("Поле Комментарий", "Коммент для ГО")
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
                .fillInput("Поле Комментарий", "Коммент")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку");
        LocalDateTime dateTimeNow = LocalDateTime.now();
        verificationStrategyPage.goTo(personalAccountPage)
                .switchToOneTab()
                .waitBusyCondition();

        actionsClaimSteps.executeQuery(VERIFICATION, "SELECT date_finished FROM vrf_check_group_result " +
                "WHERE claim_id ='" + claim + "' AND group_code = '" + groupCode + "';");
        OffsetDateTime dateTimeDb = OffsetDateTime.parse(actionsClaimSteps.getVariables("date_finished"));
        assertIsTrue(dateTimeNow.format(DF).equals(dateTimeDb.format(DF)), "Значение из БД соответствует времени выполнения шага 12");
        personalAccountPage.clickOnElement("Раздел Верификация")
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(verificationStrategyPage)
                .waitBusyCondition()
                .checkElementByTitleContains("Поле Наименование стратегии", "ФССП/Версия 2")
                .clickOnElement("Кнопка Ответ от ГО");

        String actualValue = verificationStrategyPage.getTextFromTable("Таблица Ответ от ГО", row, "Дата");
        String expectedValue = dateTimeDb.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
        assertIsTrue(actualValue.equals(expectedValue),
                "Значение в столбце Дата должно быть равно " + expectedValue + ". Фактическое значение: " + actualValue);
    }

    @Test
    @Tag("response_button_from_go_1740783")
    @DisplayName("1740783 - Кнопка Ответ от ГО. Заполнение поля \"ФИО пользователя\" для типа \"Вопрос\"")
    @WorkItemIds({"1740783"})
    public void response_button_from_go_1740783() {
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(verificationStrategyPage)
                .waitBusyCondition()
                .checkElementByTitleContains("Поле Наименование стратегии", "ФССП")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Методологический")
                .fillInput("Поле Комментарий", "Коммент для ГО")
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
                .fillInput("Поле Комментарий", "Коммент")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .switchToOneTab()
                .waitBusyCondition();

        actionsClaimSteps.executeQuery(VERIFICATION, "SELECT user_fio FROM vrf_check_group_result " +
                "WHERE claim_id ='" + claim + "' AND group_code = 'FSSP';");
        String fioFromDb = actionsClaimSteps.getVariables("user_fio");
        assertIsTrue(fioFromDb.equals("Автоматическое Тестирование1"), "Значение соответствует ФИО Пользователя, выполнявшего шаг 6");
        personalAccountPage.clickOnElement("Раздел Верификация")
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(verificationStrategyPage)
                .waitBusyCondition()
                .checkElementByTitleContains("Поле Наименование стратегии", "ФССП/Версия 2")
                .clickOnElement("Кнопка Ответ от ГО");

        String actualValue = verificationStrategyPage.getTextFromTable("Таблица Ответ от ГО", 1, "ФИО пользователя");
        assertIsTrue(actualValue.equals("Автоматическое Тестирование1"),
                "Значение в столбце Дата должно быть равно Автоматическое Тестирование1. Фактическое значение: " + actualValue);
    }
}