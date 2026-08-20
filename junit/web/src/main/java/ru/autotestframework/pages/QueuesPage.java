package ru.autotestframework.pages;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.FindBy;
import ru.autotestframework.pages.components.TopBar;
import ru.autotestframework.ui_core.exceptions.ElementInteractionException;
import ru.autotestframework.ui_core.page_manager.Element;
import ru.autotestframework.ui_core.page_manager.PageEntry;
import ru.autotestframework.ui_core.services.table_service.FindCellsBy;
import ru.autotestframework.ui_core.services.table_service.FindHeadersBy;
import ru.autotestframework.web_elements.elements.*;
import ru.psb.testit.annotations.Step;
import ru.psb.testit.annotations.Title;

import java.time.Duration;
import java.util.List;

import static ru.autotestframework.utils.Constants.TABLE_ROWS;

@PageEntry(title = "Страница Очереди")
public class QueuesPage extends TopBar<QueuesPage> {

    @Element("Чек-бокс массового выбора")
    @FindBy(xpath = "//span[contains(@class,'column-name')]//label//input|//span[contains(@class,'column-name')]//label")
    public Button checkBoxAllChoiceButton;

    @Element("радио-баттон Распределяется до равного количества заявок в личном кабинете")
    @FindBy(xpath = "//input[@type='radio']/../following-sibling::span[contains(text(),'Распределяется до равного количества заявок в личном кабинете')]")
    public Button distributionClaimsAnEqualRadioButton;

    @Element("радио-баттон Распределяется равное количество заявок для всех пользователей")
    @FindBy(xpath = "//input[@type='radio']/../following-sibling::span[contains(text(),'Распределяется равное количество заявок для всех пользователей')]")
    public Button distributionClaimsIntoEqualRadioButton;

    @Element("Результаты назначения")
    @FindBy(xpath = "//div[contains(@class,'report ng')]")
    public TextBlock distributionResultCount;

    @Element("Результаты поиска")
    @FindBy(xpath = "//span[@class='table-title']")
    public TextBlock searchResultCount;

    @Element("Кнопка удалить все")
    @FindBy(xpath = "//span[@class='reset-filters']")
    public Button resetFiltersButton;

    @Element("Кнопка Назначить заявку")
    @FindBy(xpath = "//div[contains(@class, 'table-sorting')]//button[./span[contains(text(), 'Назначить заявку')]]")
    public Button assignApplicationButton;

    @Element("Кнопка Сбросить сортировку")
    @FindBy(xpath = "//div[contains(@class, 'table-header-actions')]//button[./span[contains(text(), 'Сбросить сортировку')]]")
    public Button resetSortingButton;

    @Element("Кнопка Настройка фильтров")
    @FindBy(xpath = "//div[contains(@class, 'table-header-actions')]//button[./span[contains(text(), 'Настройка фильтров')]]")
    public Button filterSettingsButton;

    @Element("Кнопка Настройка списка")
    @FindBy(xpath = "//div[contains(@class, 'table-header-actions')]//button[./span[contains(text(), 'Настройка списка')]]")
    public Button listSettingsButton;

    @Element("Кнопка Выгрузка отчета")
    @FindBy(xpath = "//button[contains(@class, 'download-report-button')]")
    public Button downloadReportButton;

    @Element("Выпадающий список Служба/группа")
    @FindBy(xpath = "//div[contains(@class,'ng-star-inserted')][.//psb-text[contains(text(),'Служба/группа')]]//div[contains(@class,'mat-menu-trigger')]")
    public Button serviceGroupListButton;

    @Element("Выпадающий список Статус заявки")
    @FindBy(xpath = "//div[contains(@class,'ng-star-inserted')][.//psb-text[contains(text(),'Статус заявки')]]//mat-select")
    public Button applicationStatusButton;

    @Element("Выпадающий список Этап обработки")
    @FindBy(xpath = "//div[contains(@class,'ng-star-inserted')][.//psb-text[contains(text(),'Этап обработки')]]//div[contains(@class,'mat-menu-trigger')]")
    public Button processingStepButton;

    @Element("Выпадающий список НИС")
    @FindBy(xpath = "//div[contains(@class,'ng-star-inserted')][.//psb-text[contains(text(),'НИС')]]//mat-select")
    public Button nisButton;

    @Element("Выпадающий список Тип заявки")
    @FindBy(xpath = "//div[contains(@class,'ng-star-inserted')][.//psb-text[contains(text(),'Тип заявки')]]//mat-select")
    public Button applicationTypeButton;

    @Element("Выпадающий список Форма подтверждения дохода")
    @FindBy(xpath = "//div[contains(@class,'ng-star-inserted')][.//psb-text[contains(text(),'Форма подтверждения дохода')]]//mat-select")
    public Button incomeVerificationFormButton;

    @Element("Выпадающий список Признак «Госслужащий»")
    @FindBy(xpath = "//div[contains(@class,'ng-star-inserted')][.//psb-text[contains(text(),'Признак «Госслужащий»')]]//mat-select")
    public Button signCivilServantButton;

    @Element("Выпадающий список Управленческий статус заемщика")
    @FindBy(xpath = "//div[contains(@class,'ng-star-inserted')][.//psb-text[contains(text(),'Управленческий статус заемщика')]]//mat-select")
    public Button managerialStatusBorrowerButton;

    @Element("Выпадающий список Была доработка")
    @FindBy(xpath = "//div[contains(@class,'ng-star-inserted')][.//psb-text[contains(text(),'Была доработка')]]//mat-select")
    public Button wasRevisionButton;

    @Element("Выпадающий список Удостоверение личности военнослужащего")
    @FindBy(xpath = "//div[contains(@class,'ng-star-inserted')][.//psb-text[contains(text(),'Удостоверение личности военнослужащего')]]//mat-select")
    public Button soldierIDButton;

    @Element("Выпадающий список Время попадания на РП")
    @FindBy(xpath = "//div[contains(@class,'ng-star-inserted')][.//psb-text[contains(text(),'Время попадания на РП')]]//mat-select")
    public Button timeOnRPButton;

    @Element("Выпадающий список Отправка на доработку")
    @FindBy(xpath = "//div[contains(@class,'ng-star-inserted')][.//psb-text[contains(text(),'Отправка на доработку')]]//mat-select")
    public Button sendingRevisionButton;

    @Element("Выпадающий список Дата возврата заявки")
    @FindBy(xpath = "//div[contains(@class,'ng-star-inserted')][.//psb-text[contains(text(),'Дата возврата заявки')]]//mat-select")
    public Button applicationReturnDateButton;

    @Element("Выпадающий список Дата создания")
    @FindBy(xpath = "//div[contains(@class,'ng-star-inserted')][.//psb-text[contains(text(),'Дата создания')]]//mat-select")
    public Button dateCreateButton;

    @Element("Выпадающий список Дата версии")
    @FindBy(xpath = "//div[contains(@class,'ng-star-inserted')][.//psb-text[contains(text(),'Дата версии')]]//mat-select")
    public Button dateVersionButton;

    @Element("Выпадающий список Дата изменения")
    @FindBy(xpath = "//div[contains(@class,'ng-star-inserted')][.//psb-text[contains(text(),'Дата изменения')]]//mat-select")
    public Button dateModifiedButton;

    @Element("Выпадающий список Дата принятия решения")
    @FindBy(xpath = "//div[contains(@class,'ng-star-inserted')][.//psb-text[contains(text(),'Дата принятия решения')]]//mat-select")
    public Button dateDecisionButton;

    @Element("Выпадающий список ГО")
    @FindBy(xpath = "//div[contains(@class,'ng-star-inserted')][.//psb-text[contains(text(),'ГО')]]//mat-select")
    public Button goButton;

    @Element("Выпадающий список Полномочия")
    @FindBy(xpath = "//div[contains(@class,'ng-star-inserted')][.//psb-text[contains(text(),'Полномочия')]]//mat-select")
    public Button powersButton;

    @Element("Выпадающий список Время попадания в очередь")
    @FindBy(xpath = "//div[contains(@class,'ng-star-inserted')][.//psb-text[contains(text(),'Время попадания в очередь')]]//mat-select")
    public Button queuingTimeButton;

    @Element("Выпадающий список Назначена вручную")
    @FindBy(xpath = "//div[contains(@class,'ng-star-inserted')][.//psb-text[contains(text(),'Назначена вручную')]]//mat-select")
    public Button assignedManuallyButton;

    @Element("Выпадающий список Причина назначения")
    @FindBy(xpath = "//div[contains(@class,'ng-star-inserted')][.//psb-text[contains(text(),'Причина назначения')]]//mat-select")
    public Button reasonAppointmentButton;

    @Element("Выпадающий список Региональное время")
    @FindBy(xpath = "//div[contains(@class,'ng-star-inserted')][.//psb-text[contains(text(),'Региональное время')]]//mat-select")
    public Button regionTimeButton;

    @Element("Выпадающий список Перевод в отложенные")
    @FindBy(xpath = "//div[contains(@class,'ng-star-inserted')][.//psb-text[contains(text(),'Перевод в отложенные')]]//mat-select")
    public Button transferDeferredButton;

    @Element("Выпадающий список Причина перевода в отложенные")
    @FindBy(xpath = "//div[contains(@class,'ng-star-inserted')][.//psb-text[contains(text(),'Причина перевода в отложенные')]]//mat-select")
    public Button reasonTransferDeferredButton;

    @Element("Выпадающий список Возврат из отложенных")
    @FindBy(xpath = "//div[contains(@class,'ng-star-inserted')][.//psb-text[contains(text(),'Возврат из отложенных')]]//mat-select")
    public Button returnFromDeferredButton;

    @Element("Выпадающий список Предыдущий статус")
    @FindBy(xpath = "//div[contains(@class,'ng-star-inserted')][.//psb-text[contains(text(),'Предыдущий статус')]]//mat-select")
    public Button previousStatusButton;

    @Element("Выпадающий список Служба/группа")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(),'Служба/группа')]]//button")
    public Button serviceGroupButton;

    @Element("Выпадающий список Статус заявки")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(),'Статус заявки')]]//mat-select")
    public Button listApplicationStatusButton;

    @Element("Выпадающий список Этап обработки")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(),'Этап обработки')]]//mat-select")
    public Button listProcessingStepButton;

    @Element("Выпадающий список Вид кредита")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(),'Вид кредита')]]//mat-select")
    public Button loanTypeButton;

    @Element("Выпадающий список Отображать по")
    @FindBy(xpath = "//nz-select")
    public Button displayByButton;

    @Element("Поле ввода Дата рождения заемщика")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(), 'Дата рождения заемщика')]]//input")
    public TextInput dateBirthBorrowerTextInput;

    @Element("Поле ввода Номер клиента PSB Retail")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(), 'Номер клиента PSB Retail')]]//input")
    public TextInput psbRetailCustomerNumberTextInput;

    @Element("Поле ввода Андеррайтер")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(), 'Андеррайтер')]]//input")
    public TextInput underwriterTextInput;

    @Element("Поле ввода ИНН работодателя")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(), 'ИНН работодателя')]]//input")
    public TextInput innEmployerTextInput;

    @Element("Поле ввода КПП работодателя")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(), 'КПП работодателя')]]//input")
    public TextInput kppEmployerTextInput;

    @Element("Поле ввода Сумма кредита")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(), 'Сумма кредита')]]//input")
    public TextInput sumCreditTextInput;

    @Element("Поле ввода Программа кредитования")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(), 'Программа кредитования')]]//input")
    public TextInput programCreditTextInput;

    @Element("Поле ввода Отправивший на доработку/корректировку")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(), 'Отправивший на доработку/корректировку')]]//input")
    public TextInput sentRevisionCorrectionTextInput;

    @Element("Поле ввода Наименование филиала")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(), 'Наименование филиала')]]//input")
    public TextInput branchNameTextInput;

    @Element("Поле ввода Наименование опер. офиса")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(), 'Наименование опер. офиса')]]//input")
    public TextInput nameOperOfficeTextInput;

    @Element("Поле ввода Наименование доп. офиса")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(), 'Наименование доп. офиса')]]//input")
    public TextInput nameAddOfficeTextInput;

    @Element("Поле ввода Наименование работодателя")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(), 'Наименование работодателя')]]//input")
    public TextInput nameEmployerTextInput;

    @Element("Поле ввода Изменивший")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(), 'Изменивший')]]//input")
    public TextInput changedTextInput;

    @Element("Поле ввода Макс. сумма кредита")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(), 'Макс. сумма кредита')]]//input")
    public TextInput maxAmountCreditTextInput;

    @Element("Поле ввода Владелец блокировки")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(), 'Владелец блокировки')]]//input")
    public TextInput lockOwnerTextInput;

    @Element("Поле ввода Утверждающий")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(), 'Утверждающий')]]//input")
    public TextInput approverTextInput;

    @Element("Поле ввода ФИО участника сделки")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(), 'ФИО участника сделки')]]//input")
    public TextInput fullNameTransactionTextInput;

    @Element("Пиктограмма поля ввода ФИО участника сделки")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(), 'ФИО участника сделки')]]//input/following-sibling::mat-icon")
    public TextInput fullNameTransactionButton;

    @Element("Поле ввода Фамилия")
    @FindBy(xpath = "//input[@placeholder='Фамилия']")
    public TextInput surnameTextInput;

    @Element("Поле ввода Имя")
    @FindBy(xpath = "//input[@placeholder='Имя']")
    public TextInput nameTextInput;

    @Element("Поле ввода Отчество")
    @FindBy(xpath = "//input[@placeholder='Отчество']")
    public TextInput middleNameTextInput;

    @Element("Кнопка Выбрать")
    @FindBy(xpath = "//button/span[normalize-space()='Выбрать']")
    public Button selectButton;

    @Element("Кнопка Сбросить")
    @FindBy(xpath = "//button/span[normalize-space()='Сбросить']")
    public Button resetButton;

    @Element("Кнопка Следующая страница таблицы")
    @FindBy(xpath = "//i[@nztype='right']")
    public Button nextTablePageButton;

    @Element("Кнопка Предыдущая страница таблицы")
    @FindBy(xpath = "//i[contains(@class,'anticon-left')]")
    public Button previousTablePageButton;

    @Element("Поле Количество записей")
    @FindBy(xpath = "//div[@class='mat-paginator-range-label']")
    public TextBlock numberOfRecordsTextBlock;

    @Element("Таблица Очереди")
    @FindBy(xpath = "//table[contains(@class, 'mat-table')]")
    @FindCellsBy(xpath = ".//td[@role='cell']")
    @FindHeadersBy(xpath = ".//th[@role='columnheader']/div")
    public WebTable queuesTable;

    @Element("сортировка Столбец Время попадания на РП")
    @FindBy(xpath = "//span[contains(text(), 'Время попадания на РП')]/../following-sibling::mat-multi-sort-header")
    public TextBlock columnSortRpPostponed;

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

    @Element("сортировка Столбец Программа кредитования")
    @FindBy(xpath = "//span[contains(text(), 'Программа кредитования')]/../following-sibling::mat-multi-sort-header")
    public TextBlock columnSortLendingProgram;

    @Element("сортировка Столбец Дата создания")
    @FindBy(xpath = "//span[contains(text(), 'Дата создания')]/../following-sibling::mat-multi-sort-header")
    public TextBlock columnSortDateCreation;

    @Element("сортировка Столбец Форма подтверждения дохода")
    @FindBy(xpath = "//span[contains(text(), 'Форма подтверждения дохода')]/../following-sibling::mat-multi-sort-header")
    public TextBlock columnSortIncomeVerificationForm;

    @Element("плашки фильтров")
    @FindBy(xpath = "//div[contains(@class,'applied-filters')]")
    public TextBlock filterPlates;

    @Element("Модальное окно Поиск сотрудников")
    @FindBy(xpath = "//mat-dialog-container[.//div[text()='Поиск сотрудников']]")
    public TextBlock employeeSearchModal;

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

    @Element("Кнопка Распределяется до равного количества заявок в личном кабинете")
    @FindBy(xpath = "//mat-radio-button//span[contains(text(), 'Распределяется до равного количества заявок в личном кабинете')]")
    public Button distributedToEqualAmountsRadioButton;

    @Element("Поле Отдаленные регионы")
    @FindBy(xpath = "//mat-option[span[contains(text(),'Отдаленные регионы')]]")
    public Button outlyingRegionsButton;

    @Element("Кнопка Назначить")
    @FindBy(xpath = "//div[contains(@class, 'controls')]//button[span[contains(text(),'Назначить')]]")
    public Button appointButton;

    @Element("Кнопка Иконка закрыть")
    @FindBy(xpath = "//mat-dialog-container//mat-icon[contains(@class, 'inserted')]/*")
    public Button closeButton;

    @Element("Чек-бокс Сортировка по приоритетам")
    @FindBy(xpath = "//span[contains(text(), 'Сортировка по приоритетам')]/../../label//input")
    public ClassicCheckBox checkBoxSortByPriority;

    @Element("Чек-бокс Сортировка по умолчанию")
    @FindBy(xpath = "//span[contains(text(), 'Сортировка по умолчанию')]/../../label//input")
    public ClassicCheckBox checkBoxSortByDefault;

    @Element("Кнопка выгрузки")
    @FindBy(xpath = "//button[contains(@class, 'download-report-button')]")
    public Button downloadButton;

    @Element("контекстная кнопка Назначить заявку")
    @FindBy(xpath = "//app-context-menu//button")
    public Button contextMenuButton;

    @Element("Модальное окно Информация об ошибке")
    @FindBy(xpath = "//div[contains(@class, 'modal-error')]//div[contains(text(), 'Информация об ошибке')]")
    public TextBlock modalInfoError;

    @Element("Кнопка Ок на модальном окне")
    @FindBy(xpath = "//div[contains(@class, 'modal-error')]//button/span[contains(text(), 'Ок')]")
    public Button buttonOkModalInfoError;

    @Step
    @Title("Назначить заявку номер {claimId} на сотрудников {responsiblePerson}")
    public QueuesPage assignRequest(List<String> responsiblePerson, String... options) {
        clickOnElement("Кнопка Назначить заявку");
        clickOnElement("Кнопка Найти (окно поиск сотрудников)");
        selectEmployees(responsiblePerson);
        selectValueFromDropDownList("Выпадающий список Причина назначения (окно поиск сотрудников)", "Отдаленные регионы");
        if (options.length > 0) {
            clickOnElement(options[0]);
        }
        clickOnElement("Кнопка Назначить");
        waitText(60, "Результаты ручного распределения");
        return this;
    }

    @Step
    @Title("Поиск и выбор сотрудников по списку {responsiblePersons} на модальном окне Поиск сотрудников")
    private void selectEmployees(List<String> responsiblePersons) {
        for (String person : responsiblePersons) {
            clearInput("Поле ввода ФИО сотрудника");
            fillInput("Поле ввода ФИО сотрудника", person);
            clickOnElement("Кнопка Найти (окно поиск сотрудников)");
            clickOnElement("Чек-бокс массового выбора (окно поиск сотрудников)");
        }
    }

    @Step
    @Title("Выбрать заявку номер {claimId} в таблице на странице Очереди")
    public QueuesPage selectClaimOnTable(String claimId) {
        checkModal();
        resetFilters();
        SelenideElement table = queuesTable.getSelenideElement().shouldBe(Condition.visible, Duration.ofSeconds(10));
        String cellsPath = queuesTable.getCellsPath();

        // Проверяем количество строк в таблице
        ElementsCollection rows = table.$$x(TABLE_ROWS);
        rows.forEach(row -> row.shouldBe(Condition.visible));
        int rowCount = rows.size();

        if (rowCount == 0) {
            throw new ElementInteractionException("Таблица \"" + queuesTable.getTitle() + "\" пуста. Невозможно получить данные.");
        }

        try {
            for (int i = 0; i < rowCount; i++) {
                SelenideElement row = rows.get(i);
                SelenideElement requestNumberCell = row.$$x(cellsPath).get(getColumnIndexByName(queuesTable, "Номер заявки"));

                // Проверяем наличие текста в ячейке "Номер заявки"
                if (requestNumberCell.getText().contains(claimId)) {
                    SelenideElement firstColumnCell = row.$$x(cellsPath).get(0); // Индекс 0 для первого столбца (где чек-боксы)
                    firstColumnCell.click();
                    return this;
                }
            }
            throw new ElementInteractionException("Заявка \"" + claimId + "\" не найдена в столбце \"Номер заявки\".");
        } catch (NoSuchElementException e) {
            throw new ElementInteractionException("Не удалось найти элемент в строке.", e);
        }
    }
}