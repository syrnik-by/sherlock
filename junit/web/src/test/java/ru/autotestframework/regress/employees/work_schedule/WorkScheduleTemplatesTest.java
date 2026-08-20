package ru.autotestframework.regress.employees.work_schedule;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;

@Tag("regress")
@Tag("employees")
@Tag("work_schedule")
@Tag("work_schedule_templates")
@ClassName("Сотрудники. График работы. Шаблоны графиков")
public class WorkScheduleTemplatesTest extends BaseTest {

    @BeforeAll
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
    @Tag("work_schedule_templates_1724214")
    @DisplayName("1724214 - Создание шаблона с типом \"Смена\"")
    @WorkItemIds({"1724214"})
    public void work_schedules_1724214() {
        String templateName = "Тест_удалить";
        String type = "Смена";
        String workShiftStart = "07:00";
        String workShiftEnd = "15:59";
        String lunchStartTime = "11:00";
        String lunchEndTime = "12:00";
        String actualValue;

        personalAccountPage.openMenuLinks("Сотрудники > Графики работы")
                .goTo(workSchedulesPage)
                .clickOnElement("Вкладка Шаблоны графиков")
                .clickOnElement("Кнопка Создать шаблон")
                .fillInput("Поле ввода Название шаблона (модальное окно Создать шаблон)", templateName)
                .selectValueFromDropDownList("Выпадающий список Тип графика", type)
                .fillInput("Поле ввода Начало смены", workShiftStart)
                .fillInput("Поле ввода Конец смены", workShiftEnd)
                .fillInput("Поле ввода Начало обеда", lunchStartTime)
                .clickOnElement("Поле ввода Конец обеда");
        actualValue = workSchedulesPage.getValueByElementTitle("Поле ввода Конец обеда");
        assertIsTrue(actualValue.equals(lunchEndTime),
                "Поле ввода Конец обеда должно быть равно " + lunchEndTime + ". Фактическое значение: " + actualValue);
        workSchedulesPage.clickOnElement("Кнопка Сохранить")
                .fillInput("Поле ввода Название шаблона", templateName)
                .clickOnElement("Кнопка Найти");
        actualValue = workSchedulesPage.getTextFromTable("Таблица Шаблоны графиков", 1, "Название шаблона");
        assertIsTrue(actualValue.equals(templateName),
                "Значение в столбце Название шаблона должно быть равно " + templateName);
        actualValue = workSchedulesPage.getTextFromTable("Таблица Шаблоны графиков", 1, "Тип графика");
        assertIsTrue(actualValue.equals(type),
                "Значение в столбце Тип графика должно быть равно " + type);
        actualValue = workSchedulesPage.getTextFromTable("Таблица Шаблоны графиков", 1, "Начало смены");
        assertIsTrue(actualValue.equals(workShiftStart),
                "Значение в столбце Начало смены должно быть равно " + workShiftStart);
        actualValue = workSchedulesPage.getTextFromTable("Таблица Шаблоны графиков", 1, "Конец смены");
        assertIsTrue(actualValue.equals(workShiftEnd),
                "Значение в столбце Конец смены должно быть равно " + workShiftEnd);
        actualValue = workSchedulesPage.getTextFromTable("Таблица Шаблоны графиков", 1, "Начало обеда");
        assertIsTrue(actualValue.equals(lunchStartTime),
                "Значение в столбце Начало обеда должно быть равно " + lunchStartTime);
        actualValue = workSchedulesPage.getTextFromTable("Таблица Шаблоны графиков", 1, "Конец обеда");
        assertIsTrue(actualValue.equals(lunchEndTime),
                "Значение в столбце Начало обеда должно быть равно " + lunchEndTime);
        workSchedulesPage.deleteTemplate(1);
    }
}
