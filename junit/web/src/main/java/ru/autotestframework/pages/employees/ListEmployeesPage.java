package ru.autotestframework.pages.employees;

import org.openqa.selenium.support.FindBy;
import ru.autotestframework.pages.components.TopBar;
import ru.autotestframework.ui_core.page_manager.Element;
import ru.autotestframework.ui_core.page_manager.PageEntry;
import ru.autotestframework.ui_core.services.table_service.FindCellsBy;
import ru.autotestframework.ui_core.services.table_service.FindHeadersBy;
import ru.autotestframework.web_elements.elements.*;

@PageEntry(title = "Страница Список сотрудников")
public class ListEmployeesPage extends TopBar<ListEmployeesPage> {

    @Element("Кнопка фильтра ФИО")
    @FindBy(xpath = "//span[contains(text(), 'ФИО')]/..//button")
    public Button filterFullNameButton;

    @Element("Кнопка фильтра Процессная функция")
    @FindBy(xpath = "//span[contains(text(), 'Процессная функция')]/..//button")
    public Button processFunctionButton;

    @Element("Кнопка фильтра Шаблоны")
    @FindBy(xpath = "//span[contains(text(), 'Шаблоны')]/..//button")
    public Button templatesButton;

    @Element("Кнопка фильтра Вкл/выкл")
    @FindBy(xpath = "//span[contains(text(), 'Вкл/выкл')]/..//button")
    public Button onOffButton;

    @Element("Кнопка фильтра Группы")
    @FindBy(xpath = "//span[contains(text(), 'Группы')]/..//button")
    public Button groupsButton;

    @Element("Кнопка фильтра Функциональный руководитель")
    @FindBy(xpath = "//span[contains(text(), 'Функциональный руководитель')]/..//button")
    public Button functionLeaderButton;

    @Element("Кнопка фильтра Тип графика")
    @FindBy(xpath = "//span[contains(text(), 'Тип графика')]/..//button")
    public Button typeScheduleButton;

    @Element("Кнопка фильтра Роли")
    @FindBy(xpath = "//span[contains(text(), 'Роли')]/..//button")
    public Button roleButton;

    @Element("Кнопка фильтра Статус в системе")
    @FindBy(xpath = "//span[contains(text(), 'Статус в системе')]/..//button")
    public Button statusInSystemButton;

    @Element("Кнопка фильтра Время последнего входа в ЛКА")
    @FindBy(xpath = "//span[contains(text(), 'Время последнего входа в ЛКА')]/..//button")
    public Button entryInLkaButton;

    @Element("Кнопка фильтра Руководитель")
    @FindBy(xpath = "//span[contains(text(), 'Руководитель')]/..//button")
    public Button leaderButton;

    @Element("Кнопка фильтра Утверждающий")
    @FindBy(xpath = "//span[contains(text(), 'Утверждающий')]/..//button")
    public Button approvingButton;

    @Element("Кнопка фильтра Категория рассмотрения")
    @FindBy(xpath = "//span[contains(text(), 'Категория рассмотрения')]/..//button")
    public Button categoryReviewButton;

    @Element("Кнопка фильтра Категория принятия решения")
    @FindBy(xpath = "//span[contains(text(), 'Категория принятия решения')]/..//button")
    public Button categoryDecisionButton;

    @Element("Кнопка фильтра Регион")
    @FindBy(xpath = "//span[contains(text(), 'Регион')]/..//button")
    public Button regionButton;

    @Element("Кнопка фильтра Стаж в андеррайтинге")
    @FindBy(xpath = "//span[contains(text(), 'Стаж в андеррайтинге')]/..//button")
    public Button experienceInUnderwriterButton;

    @Element("Кнопка фильтра Логин")
    @FindBy(xpath = "//span[contains(text(), 'Логин')]/..//input")
    public TextInput loginTextInput;

    @Element("Кнопка фильтра Дата заведения в ЛКА")
    @FindBy(xpath = "//span[contains(text(), 'Дата заведения в ЛКА')]/..//button")
    public Button dateCreateInLkaButton;

    @Element("Кнопка фильтра Настроить разрешения")
    @FindBy(xpath = "//span[contains(text(), 'Настроить разрешения')]/..//button")
    public Button settingsPermissionButton;

    @Element("Кнопка фильтра Архивный")
    @FindBy(xpath = "//span[contains(text(), 'Архивный')]/..//button")
    public Button archiveButton;

    @Element("Кнопка Найти")
    @FindBy(xpath = "//div[contains(@class, 'employees-filter')]//button[.//span[contains(text(), 'Найти')]]")
    public Button searchButton;

    @Element("Кнопка Настройка списка")
    @FindBy(xpath = "//div[contains(@class, 'table-header-actions')]//button[.//span[contains(text(), 'Настройка списка')]]")
    public Button listCustomizationButton;

    @Element("Столбец ФИО")
    @FindBy(xpath = "//div[contains(@class,'table-heading-container')][./span[contains(text(), 'ФИО')]]")
    public TextBlock columnFullNameTextBlock;

    @Element("Столбец Процессная функция")
    @FindBy(xpath = "//div[contains(@class,'table-heading-container')][./span[contains(text(), 'Процессная функция')]]")
    public TextBlock columnProcessFunctionTextBlock;

    @Element("Столбец Шаблоны")
    @FindBy(xpath = "//div[contains(@class,'table-heading-container')][./span[contains(text(), 'Шаблоны')]]")
    public TextBlock columnTemplatesTextBlock;

    @Element("Столбец Вкл/выкл")
    @FindBy(xpath = "//div[contains(@class,'table-heading-container')][./span[contains(text(), 'Вкл/выкл')]]")
    public TextBlock columnOnOffTextBlock;

    @Element("Столбец Группы")
    @FindBy(xpath = "//div[contains(@class,'table-heading-container')][./span[contains(text(), 'Группы')]]")
    public TextBlock columnGroupsTextBlock;

    @Element("Столбец Функциональный руководитель")
    @FindBy(xpath = "//div[contains(@class,'table-heading-container')][./span[contains(text(), 'Функциональный руководитель')]]")
    public TextBlock columnFunctionLeaderTextBlock;

    @Element("Столбец Тип графика")
    @FindBy(xpath = "//div[contains(@class,'table-heading-container')][./span[contains(text(), 'Тип графика')]]")
    public TextBlock columnTypeScheduleTextBlock;

    @Element("ФИО сотрудника(первая строка)")
    @FindBy(xpath = "//tr[contains(@class,'mat-row')][1]//div[contains(@class,'dataCell-name')]")
    public Link nameInFirstLineLink;

    @Element("Процессная функция (первая строка) - значок вложение")
    @FindBy(xpath = "(//tr[contains(@class,'mat-row')][1]//button)[1]")
    public Link pfInFirstLineLink;

    @Element("Поле ФИО сотрудника (модальное окно Поиск сотрудника)")
    @FindBy(xpath = "//span[text()='ФИО сотрудника']//following-sibling::div/input")
    public TextInput fioEmployeeTextBlock;

    @Element("Чек-бокс Сотрудник (модальное окно Поиск сотрудника)")
    @FindBy(xpath = "//span[text()='Сотрудник']/..//mat-checkbox")
    public ClassicCheckBox employeeCheckBox;

    @Element("Кнпока Добавить (модальное окно Поиск сотрудника)")
    @FindBy(xpath = "//button/span[contains(text(), 'Добавить')]")
    public Button addEmployeeTextBlock;

    @Element("Таблица Список сотрудников")
    @FindBy(xpath = "//table")
    @FindHeadersBy(xpath = ".//th[@role='columnheader']")
    @FindCellsBy(xpath = ".//td[@role='cell']")
    public WebTable employeeListTable;

    @Element("Кнопка Сбросить все")
    @FindBy(xpath = "//span[@class='reset-filters']")
    public Button resetFiltersButton;
}
