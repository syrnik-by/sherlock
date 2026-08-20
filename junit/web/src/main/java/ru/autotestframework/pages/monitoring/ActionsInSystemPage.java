package ru.autotestframework.pages.monitoring;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import ru.autotestframework.pages.components.TopBar;
import ru.autotestframework.steps.asserts.Asserts;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@PageEntry(title = "Страница Действия в системе")
public class ActionsInSystemPage extends TopBar<ActionsInSystemPage> {

    @Element("Кнопка Сбросить сортировку")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Сбросить сортировку')]]")
    public Button buttonResetSortingButton;

    @Element("Кнопка Удалить все")
    @FindBy(xpath = "//span[text() = ' Удалить все ']")
    public Button deleteAllButton;

    @Element("Кнопка Выгрузить данные")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Выгрузить данные')]]")
    public Button buttonUploadDataButton;

    @Element("Кнопка Найти")
    @FindBy(xpath = "//button[./span[contains(text(), 'Найти')]]")
    public Button buttonSearchButton;

    @Element("Выпадающий список Отображать по")
    @FindBy(xpath = "//nz-select")
    public Button displayByDropDownButton;

    @Element("Кнопка Предыдущая страница таблицы")
    @FindBy(xpath = "//i[contains(@class,'anticon-left')]")
    public Button previousTablePageButton;

    @Element("Кнопка Следующая страница таблицы")
    @FindBy(xpath = "//i[contains(@class,'anticon-right')]")
    public Button nextTablePageButton;

    @Element("Поле ввода ФИО пользователя")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(), 'ФИО пользователя')]]//input")
    public TextInput fullNameUserTextInput;

    @Element("Поле ввода ФИО пользователя (объект)")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(), 'ФИО пользователя (объект)')]]//input")
    public TextInput fullNameUserObjectTextInput;

    @Element("Поле ввода Время от")
    @FindBy(xpath = "//mat-form-field[.//span[contains(text(), 'Время от')]]//input")
    public TextInput timeFromTextInput;

    @Element("Поле ввода Время до")
    @FindBy(xpath = "//mat-form-field[.//span[contains(text(), 'Время до')]]//input")
    public TextInput timeFromToTextInput;

    @Element("Поле ввода Тип события")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(), 'Тип события')]]//input")
    public TextInput eventTypeTextInput;

    @Element("Кнопка выбора Тип события")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(), 'Тип события')]]//mat-icon")
    public Button eventTypeButton;

    @Element("Таблица Действия в системе")
    @FindBy(xpath = "//table[contains(@class, 'mat-table')]")
    @FindCellsBy(xpath = ".//td[@role='cell']")
    @FindHeadersBy(xpath = ".//th[@role='columnheader']//span[@class='column-name']")
    public WebTable actionInSystemTable;

    @Element("сортировка Столбец Время")
    @FindBy(xpath = "//span[contains(text(), 'Время')]/../following-sibling::mat-multi-sort-header")
    public TextBlock columnSortOrderTime;

    @Element("Поле ввода Тип события (модальное окно Тип события)")
    @FindBy(xpath = "//input[@formcontrolname='inputField']")
    public TextInput eventTypeOnModalTextInput;

    @Element("Кнопка Найти (модальное окно Тип события)")
    @FindBy(xpath = "//mat-dialog-container//app-button//span[contains(text(), 'Найти')]")
    public Button searchButtonOnModal;

    @Element("Первая строка в результате поиска (модальное окно Тип события)")
    @FindBy(xpath = "//mat-dialog-container//div[contains(@class, 'list-row')]")
    public TextBlock firstRowInSearchResultOnModal;

    @Element("Кнопка Выбрать (модальное окно Тип события)")
    @FindBy(xpath = "//mat-dialog-container//app-button//span[contains(text(), 'Выбрать')]")
    public Button selectButtonOnModal;

    // ЭФ Уведомление о создании отчета

    @Element("Модальное окно Уведомление о создании отчета")
    @FindBy(xpath = "//mat-dialog-container/app-error-dialog")
    public TextBlock errorModal;

    @Element("Кнопка Ок (Уведомление о создании отчета)")
    @FindBy(xpath = "//mat-dialog-container/app-error-dialog//app-button//span[normalize-space()='Ок']")
    public Button okButton;

    @Step
    @Title("Выбрать тип события {eventName}")
    public ActionsInSystemPage selectEventType(String eventName) {
        clickOnElement("Кнопка выбора Тип события")
                .fillInput("Поле ввода Тип события (модальное окно Тип события)", eventName)
                .clickOnElement("Кнопка Найти (модальное окно Тип события)")
                .clickOnElement("Первая строка в результате поиска (модальное окно Тип события)")
                .clickOnElement("Кнопка Выбрать (модальное окно Тип события)");
        return this;
    }

    @Step
    @Title("Отсортировать столбец {columnName} таблицы Действия в системе по {order}")
    public ActionsInSystemPage sortValuesInColumn(String columnName, String order) {
        if (getRowCount(actionInSystemTable) >= 2) { // если меньше двух записей сортировка не требуется
            int index = getColumnIndexByName(actionInSystemTable, columnName) + 1;
            List<String> initialValues = getListValuesByColumnName("Таблица Действия в системе", columnName);
            buttonResetSortingButton.click();
            SelenideElement columnSort = actionInSystemTable.getSelenideElement()
                    .$x("(" + actionInSystemTable.getHeadersPath() + ")" + "[" + index + "]/parent::*/following-sibling::mat-multi-sort-header");
            columnSort.click();
            if (order.equals("убыванию")) {
                columnSort.click();
            }
            waitBusyCondition();
            List<String> expectedValues = getSortedList(initialValues, findDataType(initialValues), order);
            List<String> actualValues = getListValuesByColumnName("Таблица Действия в системе", columnName);
            Asserts.assertIsTrue(actualValues.equals(expectedValues), "Значения в столбце " + columnName + " таблицы Действия в системе являются отсортированными по " + order);
        }
        return getSelf();
    }

    private String findDataType(List<String> initialValues) {
        DateTimeFormatter df_with_seconds = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        try {
            LocalDateTime.parse(initialValues.get(0), df_with_seconds);
            return "timestamp";
        } catch (Exception ignored) {
            try {
                Integer.parseInt(initialValues.get(0));
                return "bigint";
            } catch (Exception e) {
                return "string";
            }
        }
    }
}