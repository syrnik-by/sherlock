package ru.autotestframework.regress.employees.work_schedule;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.autotestframework.BaseTest;
import ru.autotestframework.utils.annotations.DisabledIfWeekday;
import ru.autotestframework.utils.annotations.DisabledIfWeekdayExtension;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.ExternalId;
import ru.psb.testit.annotations.WorkItemIds;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;

@Tag("regress")
@Tag("employees")
@Tag("work_schedule")
@Tag("work_schedules")
@ClassName("Сотрудники. Графики работы")
@ExtendWith(DisabledIfWeekdayExtension.class)
public class WorkSchedulesTest extends BaseTest {

    private final LocalDate nowDate = LocalDate.now();
    private final DateTimeFormatter df = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @BeforeEach
    public void login() {
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi();
        }
    }

    @Test
    @Tag("smoke")
    @Tag("work_schedules_1724179")
    @DisplayName("1724179 - Поиск по службе + тип графика")
    @WorkItemIds({"1724179"})
    public void work_schedules_1724179() {
        personalAccountPage.openMenuLinks("Сотрудники > Графики работы")
                .goTo(workSchedulesPage)
                .fillInput("Поиск по ФИО", "Автоматическое тестирование2")
                .clickOnElement("Кнопка Найти")
                .waitBusyCondition()
                .clickOnCellFromTable("Таблица Графики работы", 1, 1)
                .clickOnElement("Кнопка Назначить")
                .selectValueFromDropDownList("Выпадающий список Шаблон", "Смена_Исланов")
                .fillDateField("Поле Начало периода", nowDate.format(df))
                .fillDateField("Поле Конец периода", nowDate.format(df))
                .clickOnElement("Кнопка Сохранить")
                .openMenuLinks("Сотрудники > Графики работы")
                .goTo(workSchedulesPage)
                .deleteFilter("Автоматическое тестирование2")
                .selectValueFromDropDownList("Фильтр Служба / группа", "Автотестеры")
                .selectValueFromDropDownList("Фильтр Тип графика", "Смена")
                .clickOnElement("Кнопка Найти")
                .checkActiveFilters(List.of(
                        "Смена",
                        "Автотестеры",
                        nowDate.minusMonths(2).withDayOfMonth(1).format(df),
                        nowDate.plusMonths(3).withDayOfMonth(nowDate.plusMonths(3).lengthOfMonth()).format(df)))
                .assertElementByTitleVisibility("Кнопка удалить все", "отображается");
        assertIsTrue(workSchedulesPage.getTextFromTable("Таблица Графики работы", 1, "ФИО сотрудника")
                        .equals("Автоматическое Тестирование2"),
                "Значение в столбце ФИО сотрудника должно быть равно Автоматическое Тестирование2. Фактическое значение "
                        + workSchedulesPage.getTextFromTable("Таблица Графики работы", 1, "ФИО сотрудника"));
        assertIsTrue(workSchedulesPage.getTextFromTable("Таблица Графики работы", 1, "Тип графика")
                        .equals("Смена"),
                "Значение в столбце Тип графика должно быть равно Смена");

    }

    @DisabledIfWeekday({DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY})
    @ParameterizedTest
    @CsvSource({"1724149, Сотруднику\\Смена, 2, Смена_Исланов, Смена, 08:00-19:00",
            "1724153, Сотруднику\\\"Пятидневка\", 3, Пятидневка_Исланов, Пятидневка, 08:00-17:00"})
    @Tag("smoke")
    @Tag("work_schedules_1724149_1724153")
    @DisplayName("{id} - Графики работы: Назначение графика: {displayName}")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void work_schedules_1724149_1724153(String id, String displayName, String user, String template, String type, String time) {
        String month = nowDate.getMonth().getDisplayName(TextStyle.FULL_STANDALONE, new Locale("ru", "RU"));
        int day = nowDate.getDayOfMonth();
        int year = nowDate.getYear();
        String formattedDate = month.substring(0, 1).toUpperCase() + month.substring(1) + " " + year;

        personalAccountPage.openMenuLinks("Сотрудники > Графики работы")
                .goTo(workSchedulesPage)
                .clearInput("Поиск по ФИО")
                .fillInput("Поиск по ФИО", "Автоматическое тестирование" + user)
                .clickOnElement("Кнопка Найти")
                .waitBusyCondition()
                .clickOnCellFromTable("Таблица Графики работы", 1, 1)
                .clickOnElement("Кнопка Назначить")
                .selectValueFromDropDownList("Выпадающий список Шаблон", template)
                .fillDateField("Поле Начало периода", nowDate.format(df))
                .fillDateField("Поле Конец периода", nowDate.format(df))
                .clickOnElement("Кнопка Сохранить")
                .expandCurrentMonth(formattedDate);
        String currentValue = workSchedulesPage.getTextFromTable("Таблица Графики работы", 1, "Тип графика");
        assertIsTrue(currentValue.equals(type),
                "Значение в столбце Тип графика должно быть равно " + type + ". Фактическое значение: " + currentValue);
        currentValue = workSchedulesPage.getTextFromTable("Таблица Графики работы - детализация", 1, String.valueOf(day));
        assertIsTrue(currentValue.equals(time),
                "Значение в столбце " + day + " должно быть равно " + time + ". Фактическое значение: " + currentValue);

    }
}
