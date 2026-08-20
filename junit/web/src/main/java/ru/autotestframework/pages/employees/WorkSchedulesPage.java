package ru.autotestframework.pages.employees;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.FindBy;
import ru.autotestframework.pages.components.TopBar;
import ru.autotestframework.ui_core.exceptions.ElementInteractionException;
import ru.autotestframework.ui_core.page_manager.Element;
import ru.autotestframework.ui_core.page_manager.PageEntry;
import ru.autotestframework.ui_core.services.table_service.FindCellsBy;
import ru.autotestframework.ui_core.services.table_service.FindHeadersBy;
import ru.autotestframework.web_elements.elements.Button;
import ru.autotestframework.web_elements.elements.TextBlock;
import ru.autotestframework.web_elements.elements.TextInput;
import ru.autotestframework.web_elements.elements.WebTable;
import ru.psb.testit.annotations.Step;
import ru.psb.testit.annotations.Title;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

@PageEntry(title = "Страница Графики работы")
public class WorkSchedulesPage extends TopBar<WorkSchedulesPage> {

    @Element("Вкладка Графики работы")
    @FindBy(xpath = "//span[contains(text(), 'Графики работы')]")
    public TextBlock worksSchedulesTextBlock;

    @Element("Вкладка Производственный календарь")
    @FindBy(xpath = "//span[contains(text(), 'Производственный календарь')]")
    public TextBlock productionCalendarTextBlock;

    @Element("Вкладка Шаблоны графиков")
    @FindBy(xpath = "//span[contains(text(), 'Шаблоны графиков')]")
    public TextBlock templatesSchedulesTextBlock;

    //  Раздел Графики работы
    @Element("Таблица Графики работы")
    @FindBy(xpath = "//table")
    @FindCellsBy(xpath = ".//td[@role='cell']")
    @FindHeadersBy(xpath = "./thead/tr[2]/th")
    public WebTable workSchedulesTable;

    @Element("Таблица Графики работы - детализация")
    @FindBy(xpath = "//table")
    @FindCellsBy(xpath = ".//td[@role='cell']//div[contains(@class,'day-time')]")
    @FindHeadersBy(xpath = "./thead/tr[2]/th//div[contains(@class,'day-number')]")
    public WebTable workSchedulesDetailTable;

    @Element("Поиск по ФИО")
    @FindBy(xpath = "//input[@formcontrolname = 'name']")
    public TextInput filterFullNameTextInput;

    @Element("Фильтр Служба / группа")
    @FindBy(xpath = "//mat-select[@formcontrolname = 'groupDepartment']")
    public Button filterServiceOrGroupButton;

    @Element("Фильтр Тип графика")
    @FindBy(xpath = "//mat-select[@formcontrolname = 'typeId']")
    public Button filterTypeScheduleButton;

    @Element("Фильтр Рабочее время с")
    @FindBy(xpath = "//input[@formcontrolname = 'fromTime']")
    public TextInput filterFromTimeTextInput;

    @Element("Фильтр Рабочее время по")
    @FindBy(xpath = "//input[@formcontrolname = 'toTime']")
    public TextInput filterToTimeTextInput;

    @Element("Фильтр Период с")
    @FindBy(xpath = "//input[@formcontrolname = 'fromDate']")
    public TextInput filterFromDateTextInput;

    @Element("Фильтр Период по")
    @FindBy(xpath = "//input[@formcontrolname = 'toDate']")
    public TextInput filterToDateTextInput;

    @Element("Блок Списка месяцев")
    @FindBy(xpath = "//div[contains(@class, 'staff-table-header-control')]")
    public TextBlock blockListMonthTextBlock;

    @Element("Текущий квартал")
    @FindBy(xpath = "//table//mat-select//span[contains(@class, 'mat-select-min-line')]")
    public TextBlock currentQuarterTextBlock;

    @Element("Чек-бокс массового выбора")
    @FindBy(xpath = "//th/mat-checkbox")
    public Button checkBoxButton;

    @Element("Заголовок столбца ФИО сотрудника")
    @FindBy(xpath = "//th[./span[contains(text(), 'ФИО сотрудника')]]")
    public TextBlock columnFullNameTextBlock;

    @Element("Заголовок столбца Тип графика")
    @FindBy(xpath = "//th[./span[contains(text(), 'Тип графика')]]")
    public TextBlock columnTypeScheduleTextBlock;

    @Element("Заголовок столбца Служба")
    @FindBy(xpath = "//th[./span[contains(text(), 'Служба')]]")
    public TextBlock columnServiceTextBlock;

    @Element("Заголовок столбца Группа")
    @FindBy(xpath = "//th[./span[contains(text(), 'Группа')]]")
    public TextBlock columnGroupTextBlock;

    @Element("Фильтр Название шаблона")
    @FindBy(xpath = "//input[@formcontrolname = 'name']")
    public TextInput filterNameTemplatesTextBlock;

    @Element("Фильтр Рабочее время с (Шаблоны графиков)")
    @FindBy(xpath = "//input[@formcontrolname = 'from']")
    public TextInput filterFromTimeTemplatesTextBlock;

    @Element("Фильтр Рабочее время по (Шаблоны графиков)")
    @FindBy(xpath = "//input[@formcontrolname = 'to']")
    public TextInput filterToTimeTemplatesTextBlock;

    @Element("Кнопка Редактировать")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Редактировать')]]")
    public Button editButton;

    @Element("Кнопка Назначить")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Назначить')]]")
    public Button appointButton;

    //  Раздел Производственный календарь

    @Element("Календарные дни")
    @FindBy(xpath = "//div[contains(@class, 'production-calendar-header-info')]/div[1]/span[1]")
    public TextBlock calendarDaysTextBlock;

    @Element("Рабочие дни")
    @FindBy(xpath = "//div[contains(@class, 'production-calendar-header-info')]/div[1]/span[2]")
    public TextBlock workDaysTextBlock;

    @Element("Выходные/праздничные дни")
    @FindBy(xpath = "//div[contains(@class, 'production-calendar-header-info')]/div[1]/span[3]")
    public TextBlock weekendOrHolidayDaysTextBlock;

    @Element("Количество дней")
    @FindBy(xpath = "//div[contains(@class, 'schedule-header-info')]/span[1]")
    public TextBlock countDaysTextBlock;

    @Element("Рабочее время")
    @FindBy(xpath = "//div[contains(@class, 'schedule-header-info')]/span[2]")
    public TextBlock workTimeTextBlock;

    @Element("Количество дней при 40-часовой неделе")
    @FindBy(xpath = "//div[contains(@class, 'production-calendar-header-info')]/div[2]/span")
    public TextBlock countDaysAtWeekTextBlock;

    @Element("Кнопка Создать календарь")
    @FindBy(xpath = "//button[./span[contains(text(), 'Создать календарь')]]")
    public Button createCalendarButton;

    @Element("Кнопка Удалить календарь")
    @FindBy(xpath = "//button[./span[contains(text(), 'Удалить календарь')]]")
    public Button deleteCalendarButton;

    //  Раздел Шаблоны графиков

    @Element("Кнопка Найти")
    @FindBy(xpath = "//button[./span[contains(text(), 'Найти')]]")
    public Button buttonSearchButton;

    @Element("Кнопка Создать шаблон")
    @FindBy(xpath = "//button[./span[contains(text(), 'Создать шаблон')]]")
    public Button createTemplateButton;

    @Element("Выпадающий список Отображать по")
    @FindBy(xpath = "//nz-select")
    public Button displayByDropDownButton;

    @Element("Кнопка Предыдущая страница таблицы")
    @FindBy(xpath = "//i[contains(@class,'anticon-left')]")
    public Button previousTablePageButton;

    @Element("Кнопка Следующая страница таблицы")
    @FindBy(xpath = "//i[contains(@class,'anticon-right')]")
    public Button nextTablePageButton;

    @Element("Таблица Шаблоны графиков")
    @FindBy(xpath = "//table")
    @FindCellsBy(xpath = ".//td[@role='cell']")
    @FindHeadersBy(xpath = ".//th[@role='columnheader']")
    public WebTable graphTemplatesTable;

    @Element("Поле ввода Название шаблона")
    @FindBy(xpath = "//input[@formcontrolname='name']")
    public TextInput scheduleNameTextInput;

    //Модальное окно Назначить график
    @Element("Выпадающий список Шаблон")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(),'Шаблон')]]//mat-select")
    public Button templateDropDown;

    @Element("Выпадающий список Тип дня")
    @FindBy(xpath = "//mat-form-field[contains(@class, 'assign-schedule-form-name')]//mat-select")
    public Button dayTypeDropDown;

    @Element("Поле Начало периода")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(),'Начало периода')]]//input")
    public TextInput beginningOfPeriodTextBlock;

    @Element("Поле Конец периода")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(),'Конец Периода')]]//input")
    public TextInput endOfPeriodTextBlock;

    @Element("Поле ввода Начало смены (Назначить график)")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(),'Начало смены')]]//input")
    public TextInput startOfShiftTextBlock;

    @Element("Поле ввода Конец смены (Назначить график)")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(),'Конец смены')]]//input")
    public TextInput endOfShiftTextBlock;

    @Element("Поле ввода Всего часов (Назначить график)")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(),'Всего часов')]]//input")
    public TextInput totalHoursTextBlock;

    @Element("Поле ввода Начало обеда (Назначить график)")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(),'Начало обеда')]]//input")
    public TextInput startOfDinnerTextBlock;

    @Element("Поле ввода Конец обеда (Назначить график)")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(),'Конец обеда')]]//input")
    public TextInput endOfDinnerTextBlock;

    @Element("Кнопка Сохранить")
    @FindBy(xpath = "//app-button//span[contains(text(),'Сохранить')]")
    public Button saveButton;

    //Модальное окно Создать шаблон
    @Element("Поле ввода Название шаблона (модальное окно Создать шаблон)")
    @FindBy(xpath = "//input[@formcontrolname='scheduleName']")
    public TextInput scheduleNameMWTextInput;

    @Element("Поле ввода Начало смены")
    @FindBy(xpath = "//input[@formcontrolname='workShiftStart']")
    public TextInput workShiftStartTextInput;

    @Element("Поле ввода Конец смены")
    @FindBy(xpath = "//input[@formcontrolname='workShiftEnd']")
    public TextInput workShiftEndTextInput;

    @Element("Поле ввода Начало обеда")
    @FindBy(xpath = "//input[@formcontrolname='lunchStartTime']")
    public TextInput lunchStartTimeTextInput;

    @Element("Поле ввода Конец обеда")
    @FindBy(xpath = "//input[@formcontrolname='lunchEndTime']")
    public TextInput lunchEndTimeTextInput;

    @Element("Выпадающий список Тип графика")
    @FindBy(xpath = "//mat-dialog-container//mat-form-field[.//psb-text[contains(text(),'Тип графика')]]//mat-select")
    public Button typeScheduleDropDown;

    @Element("Модальное окно Информация об удаляемом шаблоне")
    @FindBy(xpath = "//mat-dialog-container//div[contains(text(),'Вы действительно хотите удалить шаблон')]")
    public TextBlock modalInfo;

    @Element("Кнопка Да на модальном окне")
    @FindBy(xpath = "//mat-dialog-container//app-button[.//span[contains(text(),'Да')]]")
    public Button buttonYesModalInfo;

    @Step
    @Title("Заполнить поле даты {title} значением {date}")
    public WorkSchedulesPage fillDateField(String title, String date) {
        if (!date.matches("\\d{2}\\.\\d{2}\\.\\d{4}")) {
            throw new IllegalArgumentException("Дата должна быть в формате dd.MM.yyyy");
        }

        String[] dateParts = date.split("\\.");
        String day = dateParts[0];
        String month = dateParts[1];
        String year = dateParts[2];

        getElementByTitle(title).clear();
        getElementByTitle(title).sendKeys(day);
        getElementByTitle(title).sendKeys(month);
        getElementByTitle(title).sendKeys(year);
        return this;
    }

    @Step
    @Title("Раскрыть текущий месяц \"{curMonth}\" в таблице Графики работы")
    public void expandCurrentMonth(String curMonth) {
        SelenideElement currentMonth = $x("//div[contains(@class, 'month')][./span[text()='" + curMonth + "']]/app-button").shouldBe(Condition.visible);
        if (currentMonth.$x(".//span[contains(text(), '+')]").isDisplayed()) {
            currentMonth.click();
        }
    }

    @Step
    @Title("Удалить шаблон в строке {rowNum} таблицы Шаблоны графиков")
    public WorkSchedulesPage deleteTemplate(int rowNum) {
        getElementfromTable(graphTemplatesTable, rowNum, "Действия")
                .$x(".//mat-icon[@data-mat-icon-name='trash']")
                .click();
        checkModal();
        return this;
    }

    @Override
    @Step
    @Title("Проверка появления модального окна с информацией об удаляемом шаблоне")
    public WorkSchedulesPage checkModal() {
        modalInfo.getSelenideElement().shouldBe(Condition.visible);
        if (modalInfo.isDisplayed()) {
            buttonYesModalInfo.click();
        }
        return this;
    }

    @Override
    @Step
    @Title("Из таблицы {tableTitle} в строке {row} столбца {columnName} вернуть значение")
    public String getTextFromTable(String tableTitle, int rowNum, String columnName) {
        WebTable tableName = getElementByTitle(tableTitle);
        int columnIndex = getColumnIndexByName(tableName, columnName);

        SelenideElement table = tableName.getSelenideElement().shouldBe(Condition.visible, Duration.ofSeconds(10));
        String cellsPath = tableName.getCellsPath();

        // Проверяем количество строк в таблице
        int rowCount = table.$$x(".//tbody/tr").size();

        if (rowCount == 0) {
            throw new ElementInteractionException("Таблица \"" + tableName.getTitle() + "\" пуста. Невозможно получить данные.");
        }

        if (rowNum < 1 || rowNum > rowCount) {
            throw new ElementInteractionException("Неверный номер строки: " + rowNum + ". Количество строк: " + rowCount);
        }

        try {
            SelenideElement row = table.$$x(".//tbody/tr").get(rowNum - 1);
            // Проверяем наличие ячейки перед получением текста
            if (row.$$x(cellsPath).size() <= columnIndex) {
                throw new ElementInteractionException("Ячейка столбца \"" + columnName + "\" строки " + rowNum + " не найдена или отсутствует");
            }
            return row.$$x(cellsPath).get(columnIndex).getText();
        } catch (NoSuchElementException e) {
            throw new ElementInteractionException("Не удалось найти элемент в строке " + rowNum + ", столбец: " + columnName, e);
        }
    }
}
