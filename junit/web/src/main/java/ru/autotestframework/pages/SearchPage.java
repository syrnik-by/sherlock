package ru.autotestframework.pages;

import org.openqa.selenium.support.FindBy;
import ru.autotestframework.pages.components.TopBar;
import ru.autotestframework.ui_core.page_manager.Element;
import ru.autotestframework.ui_core.page_manager.PageEntry;
import ru.autotestframework.web_elements.elements.Button;
import ru.autotestframework.web_elements.elements.TextBlock;
import ru.autotestframework.web_elements.elements.TextInput;

@PageEntry(title = "Страница Поиск")
public class SearchPage extends TopBar<SearchPage> {

    @Element("Кнопка удалить все")
    @FindBy(xpath = "//span[@class='reset-filters']")
    public Button resetFiltersButton;

    @Element("Кнопка Найти")
    @FindBy(xpath = "//button[./span[contains(text(), 'Найти')]]")
    public Button findButton;

    @Element("Кнопка Сбросить сортировку")
    @FindBy(xpath = "//div[contains(@class, 'table-header-actions')]//button[./span[contains(text(), 'Сбросить сортировку')]]")
    public Button resetSortingButton;

    @Element("Кнопка Настройка списка")
    @FindBy(xpath = "//div[contains(@class, 'table-header-actions')]//button[./span[contains(text(), 'Настройка списка')]]")
    public Button listSettingsButton;

    @Element("Чек-бокс массового выбора")
    @FindBy(xpath = "//span[contains(@class,'column-name')]//label//input")
    public Button checkBoxAllChoiceButton;

    @Element("Кнопка Назначить заявку")
    @FindBy(xpath = "//div[contains(@class, 'table-sorting')]//button[./span[contains(text(), 'Назначить заявку')]]")
    public Button assignApplicationButton;

    @Element("Поле ввода ФИО сотрудника")
    @FindBy(xpath = "//mat-form-field[.//mat-label[contains(text(), 'ФИО сотрудника')]]//input")
    public TextInput fullNameEmployeeTextInput;

    @Element("Кнопка Найти (окно поиск сотрудников)")
    @FindBy(xpath = "//mat-tab-body//button[span[contains(text(), 'Найти')]]")
    public Button searchEmployeeButton;

    @Element("Чек-бокс массового выбора (окно поиск сотрудников)")
    @FindBy(xpath = "//td//mat-checkbox[contains(@class,'mat-accent')]")
    public Button checkBoxEmployeeButton;

    @Element("Выпадающий список Причина назначения (окно поиск сотрудников)")
    @FindBy(xpath = "//div[contains(@class,'flex')][.//mat-label[contains(text(),'Причина назначения')]]//mat-select")
    public Button reasonAppointmentEmployeeButton;

    @Element("Поле Отдаленные регионы")
    @FindBy(xpath = "//mat-option[span[contains(text(),'Отдаленные регионы')]]")
    public Button outlyingRegionsButton;

    @Element("Кнопка Назначить")
    @FindBy(xpath = "//div[contains(@class, 'controls')]//button[span[contains(text(),'Назначить')]]")
    public Button appointButton;

    @Element("Кнопка Иконка закрыть")
    @FindBy(xpath = "//mat-dialog-container//mat-icon[contains(@class, 'inserted')]")
    public Button closeButton;

    @Element("Поле ввода Номер заявки")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(), 'Номер заявки')]]//input")
    public TextInput requestNumberTextInput;

    @Element("Поле Дата принятия решения От")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(), 'От')]]//input")
    public TextInput dateOfDecisionFromTextInput;

    @Element("Поле Дата принятия решения До")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(), 'До')]]//input")
    public TextInput dateOfDecisionToTextInput;

    @Element("Результаты поиска")
    @FindBy(xpath = "//span[@class='table-title']")
    public TextBlock searchResultCount;

    @Element("Выпадающий список Отображать по")
    @FindBy(xpath = "//nz-select")
    public Button displayByDropDown;

    @Element("Выпадающий список Дата принятия решения")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(),'Дата принятия решения')]]//mat-select")
    public Button buttonDecisionDate;

    @Element("Кнопка Предыдущая страница таблицы")
    @FindBy(xpath = "//i[contains(@class,'anticon-left')]")
    public Button previousTablePageButton;

    @Element("Выпадающий список Статус заявки")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(),'Статус заявки')]]//mat-select")
    public Button buttonApplicationStatus;

    @Element("Выпадающий список Этап обработки")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(),'Этап обработки')]]//div[contains(@class,'mat-menu-trigger')]")
    public Button buttonProcessingStep;

    @Element("Кнопка Следующая страница таблицы")
    @FindBy(xpath = "//i[@nztype='right']")
    public Button nextTablePageButton;

    @Element("Поле Количество записей")
    @FindBy(xpath = "//div[@class='mat-paginator-range-label']")
    public TextBlock numberOfRecordsTextBlock;

    @Element("Поле ввода Номер заявки")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(), 'Номер заявки')]]//input")
    public TextInput applicationNumberTextInput;

    @Element("Поле ввода ФИО участника сделки")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(), 'ФИО участника сделки')]]//input")
    public TextInput fullNameTransactionTextInput;

    @Element("Поле ввода Дата рождения заемщика")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(), 'Дата рождения заемщика')]]//input")
    public TextInput dateBirthBorrowerTextInput;

    @Element("Поле ввода Номер клиента PSB Retail")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(), 'Номер клиента PSB Retail')]]//input")
    public TextInput psbRetailCustomerNumberTextInput;

    @Element("Поле ввода ИНН работодателя")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(), 'ИНН работодателя')]]//input")
    public TextInput innEmployerTextInput;

    @Element("Поле ввода КПП работодателя")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(), 'КПП работодателя')]]//input")
    public TextInput kppEmployerTextInput;

    @Element("сортировка Столбец Сумма кредита")
    @FindBy(xpath = "//span[contains(text(), 'Сумма кредита')]/../following-sibling::mat-multi-sort-header")
    public TextBlock columnSortLoanAmount;

    @Element("сортировка Столбец Владелец блокировки")
    @FindBy(xpath = "//span[contains(text(), 'Владелец блокировки')]/../following-sibling::mat-multi-sort-header")
    public TextBlock columnSortLockOwner;

    @Element("сортировка Столбец Статус заявки")
    @FindBy(xpath = "//span[contains(text(), 'Статус заявки')]/../following-sibling::mat-multi-sort-header")
    public TextBlock columnSortOrderStatus;

    @Element("сортировка Столбец Номер заявки")
    @FindBy(xpath = "//span[contains(text(), 'Номер заявки')]/../following-sibling::mat-multi-sort-header")
    public TextBlock columnSortOrderNumber;

    @Element("сортировка Столбец ФИО заемщика")
    @FindBy(xpath = "//span[contains(text(), 'ФИО заемщика')]/../following-sibling::mat-multi-sort-header")
    public TextBlock columnSortFIO;

    @Element("Модальное окно Информация об ошибке")
    @FindBy(xpath = "//div[contains(@class, 'application-error')]")
    public TextBlock modalInfoError;

    @Element("Кнопка Ок на модальном окне")
    @FindBy(xpath = "//div[contains(@class, 'application-error')]//button/span[contains(text(), 'Ок')]")
    public Button buttonOkModalInfoError;

    @Element("плашки фильтров")
    @FindBy(xpath = "//div[contains(@class,'applied-filters')]")
    public TextBlock filterPlates;

    @Element("Поле ввода Фамилия")
    @FindBy(xpath = "//input[@placeholder='Фамилия']")
    public TextInput surnameTextInput;

    @Element("Поле ввода Имя")
    @FindBy(xpath = "//input[@placeholder='Имя']")
    public TextInput nameTextInput;

    @Element("Поле ввода Отчество")
    @FindBy(xpath = "//input[@placeholder='Отчество']")
    public TextInput middleNameTextInput;

    @Element("Пиктограмма поля ввода ФИО участника сделки")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(), 'ФИО участника сделки')]]//input/following-sibling::mat-icon")
    public TextInput fullNameTransactionButton;

    @Element("Кнопка Выбрать")
    @FindBy(xpath = "//button/span[normalize-space()='Выбрать']")
    public Button selectButton;
}
