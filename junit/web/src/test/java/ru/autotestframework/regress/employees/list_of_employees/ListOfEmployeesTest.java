package ru.autotestframework.regress.employees.list_of_employees;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.autotestframework.BaseTest;
import ru.autotestframework.steps.dbApiSteps.DbSteps;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;
import static ru.autotestframework.utils.Constants.DF;
import static ru.autotestframework.utils.Constants.EMPLOYEE;

@Tag("regress")
@Tag("employees")
@Tag("list_of_employees")
@ClassName("Карточка сотрудника")
public class ListOfEmployeesTest extends BaseTest {

    @BeforeAll
    public void login() {
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi();
        }
        personalAccountPage
                .openMenuLinks("Сотрудники > Список сотрудников")
                .goTo(listEmployeesPage)
                .clickOnElement("Кнопка фильтра ФИО")
                .fillInput("Поле ФИО сотрудника (модальное окно Поиск сотрудника)", "Автоматическое Тестирование1")
                .clickOnElement("Чек-бокс Сотрудник (модальное окно Поиск сотрудника)")
                .clickOnElement("Кнпока Добавить (модальное окно Поиск сотрудника)")
                .clickOnElement("Кнопка Найти")
                .clickOnElement("Процессная функция (первая строка) - значок вложение");
    }

    @Test
    @Tag("smoke")
    @Tag("list_of_employees_3469000")
    @DisplayName("3469000 - Добавление ПФ Пользователю: ПФ отсутствует")
    @WorkItemIds({"3469000"})
    public void list_of_employees_3469000() {
        cardEmployeePage.clickOnElement("Кнопка Удалить ПФ ФССП (модальное окно Назначение ПФ)");
        assertIsTrue(!cardEmployeePage.getListCheckBox("Блок ПФ (модальное окно Назначение ПФ)").contains("ФССП"),
                "Блок Процессная функция (модальное окно Назначение ПФ) не содержит ПФ ФССП");
        cardEmployeePage.clickOnElement("Кнопка Сохранить (модальное окно Назначение ПФ)").waitBusyCondition();

        dbSteps.executeQuery(EMPLOYEE, "SELECT id, full_name " +
                "FROM employee.epl_employee " +
                "WHERE full_name ='Автоматическое Тестирование1'");
        String userId = dbSteps.getVariables("id");

        dbSteps.executeQuery(EMPLOYEE, "SELECT employee_id, process_function_id, deleted " +
                "FROM employee.epl_employee_process_functions " +
                "WHERE employee_id ='" + userId + "' AND process_function_id = 3");
        assertIsTrue(dbSteps.getVariables("process_function_id") == null,
                "Для employee_id = " + userId + " отсутствует process_function_id = 3");

        dbSteps.executeQuery(EMPLOYEE, "SELECT * " +
                "FROM employee.epl_employee_process_functions_history " +
                "WHERE employee_id = '" + userId + "'");
        int count = dbSteps.getCountRecordsFromQuery();

        listEmployeesPage.clickOnElement("Процессная функция (первая строка) - значок вложение");
        cardEmployeePage.selectValueFromDropDownList("Выпадающий список Название функции (модальное окно Назначение ПФ)", "ФССП")
                .clickOnElement("Кнопка Добавить (модальное окно Назначение ПФ)");
        assertIsTrue(cardEmployeePage.getListCheckBox("Блок ПФ (модальное окно Назначение ПФ)").contains("ФССП"),
                "Блок Процессная функция (модальное окно Назначение ПФ) содержит ПФ ФССП");
        String dateTimeNow = LocalDateTime.now().format(DF);
        cardEmployeePage.clickOnElement("Кнопка Сохранить (модальное окно Назначение ПФ)").waitBusyCondition();

        dbSteps.executeQuery(EMPLOYEE, "SELECT employee_id, process_function_id, deleted " +
                "FROM employee.epl_employee_process_functions " +
                "WHERE employee_id = '" + userId + "' AND process_function_id = '3'");
        assertIsTrue(dbSteps.getVariables("process_function_id").equals("3"),
                "Для employee_id = " + userId + " отображается process_function_id = 3");

        dbSteps.executeQuery(EMPLOYEE, "SELECT * " +
                "FROM employee.epl_employee_process_functions_history " +
                "WHERE employee_id = '" + userId + "' ORDER BY id DESC");

        assertIsTrue(dbSteps.getCountRecordsFromQuery() == count + 1,
                "Кол-во записей стало на +1 больше по сравнению с шагом 3");

        String dateBegin = OffsetDateTime.parse(dbSteps.getVariables("date_begin"),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX"))
                .format(DF);
        assertIsTrue(dateBegin.equals(dateTimeNow),
                "date_begin = зафиксированной дате и времени на шаге 12. date_begin = " + dateBegin + ". " +
                        "Дата и время из шага 12 " + dateTimeNow);
        assertIsTrue(dbSteps.getVariables("date_end") == null,
                "date_end = null");
    }
}
