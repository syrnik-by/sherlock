package ru.autotestframework.pages.monitoring;

import org.openqa.selenium.support.FindBy;
import ru.autotestframework.pages.components.TopBar;
import ru.autotestframework.ui_core.page_manager.Element;
import ru.autotestframework.ui_core.page_manager.PageEntry;
import ru.autotestframework.ui_core.services.table_service.FindCellsBy;
import ru.autotestframework.ui_core.services.table_service.FindHeadersBy;
import ru.autotestframework.web_elements.elements.Button;
import ru.autotestframework.web_elements.elements.TextBlock;
import ru.autotestframework.web_elements.elements.TextInput;
import ru.autotestframework.web_elements.elements.WebTable;
import ru.psb.testit.annotations.Description;
import ru.psb.testit.annotations.Step;
import ru.psb.testit.annotations.Title;

@PageEntry(title = "Страница Действия с заявками")
public class ActionsRequestsPage extends TopBar<ActionsRequestsPage> {

    @Element("Кнопка Сбросить сортировку")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Сбросить сортировку')]]")
    public Button resetSortingButton;

    @Element("Кнопка Выгрузить данные")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Выгрузить данные')]]")
    public Button uploadDataButton;

    @Element("Кнопка Найти")
    @FindBy(xpath = "//button[./span[contains(text(), 'Найти')]]")
    public Button searchButton;

    @Element("Выпадающий список Отображать по")
    @FindBy(xpath = "//nz-select")
    public Button displayByDropDown;

    @Element("Кнопка Предыдущая страница таблицы")
    @FindBy(xpath = "//i[contains(@class,'anticon-left')]")
    public Button previousTablePageButton;

    @Element("Кнопка Следующая страница таблицы")
    @FindBy(xpath = "//i[contains(@class,'anticon-right')]")
    public Button nextTablePageButton;

    @Element("Поле ввода ФИО пользователя")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(), 'ФИО пользователя')]]//input")
    public TextInput fullNameUserTextInput;

    @Element("Поле ввода Номер заявки")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(), 'Номер заявки')]]//input")
    public TextInput applicationNumberTextInput;

    @Element("Поле ввода Время от")
    @FindBy(xpath = "//mat-form-field[.//span[contains(text(), 'Время от')]]//input")
    public TextInput timeFromTextInput;

    @Element("Поле ввода Время до")
    @FindBy(xpath = "//mat-form-field[.//span[contains(text(), 'Время до')]]//input")
    public TextInput timeFromToTextInput;

    @Element("Поле ввода ФИО участника сделки")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(), 'ФИО участника сделки')]]//input")
    public TextInput fullNameTransactionTextInput;

    @Element("Поле ввода Операция")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(), 'Операция')]]//input")
    public TextInput operationTextInput;

    @Element("Поле Тип операции")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(), 'Тип операции')]]//input")
    public TextInput typeOperationTextInput;

    @Element("Кнопка выбрать Тип операции")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(), 'Тип операции')]]//mat-icon")
    public Button selectOperationButton;

    @Element("Таблица Действия с заявками")
    @FindBy(xpath = "//table[contains(@class, 'mat-table')]")
    @FindCellsBy(xpath = ".//td[@role='cell']")
    @FindHeadersBy(xpath = ".//th[@role='columnheader']//span[@class='column-name']")
    public WebTable actionRequestsTable;

    @Element("Кнопка Удалить все")
    @FindBy(xpath = "//span[text() = ' Удалить все ']")
    public Button deleteAllButton;

    //Модальное окно Тип операции
    @Element("Поле ввода Тип операции")
    @FindBy(xpath = "//input[@formcontrolname='inputField']")
    public TextInput typeOfOperationTextInput;

    @Element("Кнопка Найти на модальном окне Тип операции")
    @FindBy(xpath = "//mat-dialog-container//app-button[@theme='accent']/button")
    public Button searchTypeOfOperationButton;

    @Element("Найденная операция")
    @FindBy(xpath = "//mat-dialog-container//div[@class='list']//span[@class='list-cell']")
    public TextBlock operationFoundTextBlock;

    @Element("Кнопка Выбрать на модальном окне Тип операции")
    @FindBy(xpath = "//mat-dialog-container//button//span[contains(text(), 'Выбрать')]")
    public Button selectTypeOfOperationButton;

    @Step
    @Title("Ввести значение в поле Тип операции = {operation}")
    @Description("Ввод значения в поле с использованием модального окна Тип операции")
    public ActionsRequestsPage inputTypeOperation(String operation) {
        clickOnElement("Кнопка выбрать Тип операции")
                .fillInput("Поле ввода Тип операции", operation)
                .clickOnElement("Кнопка Найти на модальном окне Тип операции")
                .clickOnElement("Найденная операция")
                .clickOnElement("Кнопка Выбрать на модальном окне Тип операции");
        return this;
    }

}