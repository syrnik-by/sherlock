package ru.autotestframework.regress.employees.list_of_employees;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.ExternalId;
import ru.psb.testit.annotations.WorkItemIds;

import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;

@Tag("regress")
@Tag("employees")
@Tag("list_of_employees")
@Tag("employee_card")
@ClassName("Карточка сотрудника")
public class EmployeeCardTest extends BaseTest {

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
                .clickOnElement("ФИО сотрудника(первая строка)")
                .switchToNewTab()
                .goTo(cardEmployeePage);
    }

    @Tag("smoke")
    @Tag("employee_card_1724122_1724115")
    @ParameterizedTest
    @CsvSource({
            "1724122, Регион, Ижевск, Ярославль",
            "1724115, Категория рассмотрения, Автотесты_очередь, Максимум_рассмотрение"})
    @DisplayName("{id} - Карточка сотрудника. Редактирование, сохранение поля \"{fieldName}\"")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void employee_card_1724122_1724115(String id, String fieldName, String newValue, String oldValue) {
        cardEmployeePage.clickOnElement("Кнопка " + fieldName + " - Редактировать")
                .assertElementByTitleVisibility("Кнопка " + fieldName + " - Сохранить", "отображается")
                .assertElementByTitleVisibility("Кнопка " + fieldName + " - Отменить", "отображается")
                .selectValueFromDropDownList("Выпадающий список " + fieldName, newValue)
                .clickOnElement("Кнопка " + fieldName + " - Сохранить")
                .assertElementByTitleVisibility("Кнопка " + fieldName + " - Редактировать", "отображается")
                .checkElementByTitleEquals("Поле " + fieldName, newValue)
                .clickOnElement("Кнопка " + fieldName + " - Редактировать")
                .selectValueFromDropDownList("Выпадающий список " + fieldName, oldValue)
                .clickOnElement("Кнопка " + fieldName + " - Сохранить")
                .checkElementByTitleEquals("Поле " + fieldName, oldValue);
    }

    @Test
    @Tag("smoke")
    @Tag("employee_card_1724109")
    @DisplayName("1724109 - Карточка сотрудника. Редактирование \"Процессная функция\"")
    @WorkItemIds({"1724109"})
    public void employee_card_1724109() {
        cardEmployeePage.clickOnElement("Процессная функция - Редактировать")
                .clickOnElement("Кнопка Удалить ПФ ФССП (модальное окно Назначение ПФ)");
        assertIsTrue(!cardEmployeePage.getListCheckBox("Блок ПФ (модальное окно Назначение ПФ)").contains("ФССП"),
                "Блок Процессная функция (модальное окно Назначение ПФ) не содержит ПФ ФССП");
        cardEmployeePage.clickOnElement("Кнопка Сохранить (модальное окно Назначение ПФ)");
        assertIsTrue(!cardEmployeePage.getListCheckBox("Блок Процессная функция").contains("ФССП"),
                "Блок Процессная функция не содержит ПФ ФССП");
        cardEmployeePage.clickOnElement("Процессная функция - Редактировать")
                .selectValueFromDropDownList("Выпадающий список Название функции (модальное окно Назначение ПФ)", "ФССП")
                .clickOnElement("Кнопка Добавить (модальное окно Назначение ПФ)");
        assertIsTrue(cardEmployeePage.getListCheckBox("Блок ПФ (модальное окно Назначение ПФ)").contains("ФССП"),
                "Блок Процессная функция (модальное окно Назначение ПФ) содержит ПФ ФССП");
        cardEmployeePage.clickOnElement("Кнопка Сохранить (модальное окно Назначение ПФ)");
        assertIsTrue(cardEmployeePage.getListCheckBox("Блок Процессная функция").contains("ФССП"),
                "Блок Процессная функция содержит ПФ Андеррайтинг");

    }
}
