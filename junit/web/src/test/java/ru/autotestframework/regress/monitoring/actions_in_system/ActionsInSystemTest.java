package ru.autotestframework.regress.monitoring.actions_in_system;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.*;
import ru.psb.testit.annotations.DisplayName;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;
import static ru.autotestframework.utils.Constants.DF;

@Tag("regress")
@Tag("monitoring")
@Tag("actions_in_system")
@ClassName("Мониторинг. Действия в системе")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ActionsInSystemTest extends BaseTest {

    private String dateTimeAuth;

    @BeforeAll
    public void login() {
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi();
            dateTimeAuth = getDateTimeNow();
        }
    }

    @AfterEach
    public void resetSort() {
        actionsInSystemPage.clickOnElement("Кнопка Сбросить сортировку").waitBusyCondition();
    }

    @Test
    @Tag("smoke")
    @Tag("actions_in_system_1723878")
    @DisplayName("1723878 - Проверка события в системе: Вход в систему")
    @WorkItemIds({"1723878"})
    public void actions_in_system_1723878() {
        personalAccountPage
                .openMenuLinks("Мониторинг > Действия в системе")
                .goTo(actionsInSystemPage)
                .resetFilters()
                .fillInput("Поле ввода ФИО пользователя", "Автоматическое Тестирование1")
                .selectEventType("Вход в систему")
                .clickOnElement("Кнопка Сбросить сортировку")
                .clickOnElement("сортировка Столбец Время")
                .clickOnElement("сортировка Столбец Время")
                .clickOnElement("Кнопка Найти").waitBusyCondition();
        assertIsTrue(actionsInSystemPage.getTextFromTable("Таблица Действия в системе", 1, "ФИО пользователя")
                        .equals("Автоматическое Тестирование1"),
                "Значение в столбце ФИО пользователя должно быть равно Автоматическое Тестирование1");
        assertIsTrue(actionsInSystemPage.getTextFromTable("Таблица Действия в системе", 1, "ФИО пользователя (объект)")
                        .isEmpty(),
                "Значение в столбце ФИО пользователя (объект) должно быть пусто");
        assertIsTrue(actionsInSystemPage.getTextFromTable("Таблица Действия в системе", 1, "Время")
                        .contains(dateTimeAuth),
                "Значение в столбце Время должно быть равно " + dateTimeAuth);
        assertIsTrue(actionsInSystemPage.getTextFromTable("Таблица Действия в системе", 1, "Тип события")
                        .equals("Вход в систему"),
                "Значение в столбце Тип события должно быть равно Вход в систему");
        assertIsTrue(actionsInSystemPage.getTextFromTable("Таблица Действия в системе", 1, "Описание события")
                        .equals("Пользователь TESTAT1 вошел в систему"),
                "Значение в столбце Описание события должно быть равно Пользователь TESTAT1 вошел в систему");
        actionsInSystemPage.clickOnElement("Кнопка Сбросить сортировку");
    }

    @Test
    @Tag("smoke")
    @Tag("actions_in_system_1723898")
    @DisplayName("1723898 - Проверка события в системе: Активирован чек-бокс \"Новая заявка\"")
    @WorkItemIds({"1723898"})
    public void actions_in_system_1723898() {
        loginPage.openMenuLinks("Личный кабинет")
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Андеррайтинг")
                .clickOnElement("Переключатель Новая заявка");
        String dateTimeActivation = getDateTimeNow();
        personalAccountPage
                .waitBusyCondition()
                .openMenuLinks("Мониторинг > Действия в системе")
                .goTo(actionsInSystemPage)
                .resetFilters()
                .fillInput("Поле ввода ФИО пользователя", "Автоматическое Тестирование1")
                .selectEventType("Активирован чек-бокс \"Новая заявка\"")
                .clickOnElement("сортировка Столбец Время")
                .clickOnElement("сортировка Столбец Время")
                .clickOnElement("Кнопка Найти").waitBusyCondition();
        assertIsTrue(actionsInSystemPage.getTextFromTable("Таблица Действия в системе", 1, "ФИО пользователя")
                        .equals("Автоматическое Тестирование1"),
                "Значение в столбце ФИО пользователя должно быть равно Автоматическое Тестирование1");
        assertIsTrue(actionsInSystemPage.getTextFromTable("Таблица Действия в системе", 1, "ФИО пользователя (объект)")
                        .isEmpty(),
                "Значение в столбце ФИО пользователя (объект) должно быть пусто");
        assertIsTrue(actionsInSystemPage.getTextFromTable("Таблица Действия в системе", 1, "Время")
                        .contains(dateTimeActivation),
                "Значение в столбце Время должно быть равно " + dateTimeActivation);
        assertIsTrue(actionsInSystemPage.getTextFromTable("Таблица Действия в системе", 1, "Тип события")
                        .equals("Активирован чек-бокс \"Новая заявка\""),
                "Значение в столбце Тип события должно быть равно Активирован чек-бокс \"Новая заявка\"");
        assertIsTrue(actionsInSystemPage.getTextFromTable("Таблица Действия в системе", 1, "Описание события")
                        .equals("Пользовтель Автоматическое Тестирование1 активировал чек-бокс \"Новая заявка\""),
                "Значение в столбце Описание события должно быть равно Пользовтель Автоматическое Тестирование1 активировал чек-бокс \"Новая заявка\"");
    }

    @Test
    @Tag("smoke")
    @Tag("actions_in_system_1723876")
    @DisplayName("1723876 - Проверка события в системе: Деактивирован чек-бокс \"Новая заявка\"")
    @WorkItemIds({"1723876"})
    public void actions_in_system_1723876() {
        loginPage.openMenuLinks("Личный кабинет")
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Андеррайтинг")
                .clickOnElement("Переключатель Новая заявка").waitBusyCondition();
        String dateTimeDeactivation = getDateTimeNow();
        personalAccountPage
                .openMenuLinks("Мониторинг > Действия в системе")
                .goTo(actionsInSystemPage)
                .resetFilters()
                .fillInput("Поле ввода ФИО пользователя (объект)", "Автоматическое Тестирование1")
                .selectEventType("Деактивирован чек-бокс \"Новая заявка\"")
                .clickOnElement("Кнопка Сбросить сортировку")
                .clickOnElement("сортировка Столбец Время")
                .clickOnElement("сортировка Столбец Время")
                .clickOnElement("Кнопка Найти").waitBusyCondition();
        assertIsTrue(actionsInSystemPage.getTextFromTable("Таблица Действия в системе", 1, "ФИО пользователя")
                        .equals("Система"),
                "Значение в столбце ФИО пользователя должно быть равно Система");
        assertIsTrue(actionsInSystemPage.getTextFromTable("Таблица Действия в системе", 1, "ФИО пользователя (объект)")
                        .equals("Автоматическое Тестирование1"),
                "Значение в столбце ФИО пользователя (объект) должно быть Автоматическое Тестирование1");
        assertIsTrue(actionsInSystemPage.getTextFromTable("Таблица Действия в системе", 1, "Время")
                        .contains(dateTimeDeactivation),
                "Значение в столбце Время должно быть равно " + dateTimeDeactivation);
        assertIsTrue(actionsInSystemPage.getTextFromTable("Таблица Действия в системе", 1, "Тип события")
                        .equals("Деактивирован чек-бокс \"Новая заявка\""),
                "Значение в столбце Тип события должно быть равно Деактивирован чек-бокс \"Новая заявка\"");
        assertIsTrue(actionsInSystemPage.getTextFromTable("Таблица Действия в системе", 1, "Описание события")
                        .equals("Система деактивировала чек-бокс \"Новая заявка\""),
                "Значение в столбце Описание события должно быть равно Система деактивировала чек-бокс \"Новая заявка\"");
        actionsInSystemPage.clickOnElement("Кнопка Сбросить сортировку");
    }

    @Step
    @Title("Получить текущие дату и время")
    private String getDateTimeNow() {
        return LocalDateTime.now().format(DF);
    }
}
