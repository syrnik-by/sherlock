package ru.autotestframework.pages.card_request;

import org.openqa.selenium.support.FindBy;
import ru.autotestframework.pages.BasePage;
import ru.autotestframework.ui_core.page_manager.Element;
import ru.autotestframework.ui_core.page_manager.PageEntry;
import ru.autotestframework.web_elements.elements.Button;
import ru.autotestframework.web_elements.elements.TextBlock;

@PageEntry(title = "Страница Дополнительная информация")
public class AdditionalInformation extends BasePage<AdditionalInformation> {

    @Element("Выпадающий список Информация о заявке")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Информация о заявке')]]")
    public Button applicationInformationButton;

    @Element("Поле Код статуса")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Информация о заявке')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Код статуса')]]//span[contains(@class, 'value')]")
    public TextBlock statusCodeTextBlock;

    @Element("Поле Кредитный инспектор/Ввел в систему")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Информация о заявке')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Кредитный инспектор/Ввел в систему')]]//span[contains(@class, 'value')]")
    public TextBlock loanOfficerTextBlock;

    @Element("Поле Дата создания")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Информация о заявке')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Дата создания')]]//span[contains(@class, 'value')]")
    public TextBlock dateCreationTextBlock;

    @Element("Выпадающий список Карточка клиента")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Карточка клиента')]]")
    public Button statusCodeButton;

    @Element("Поле СНИЛС")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Карточка клиента')]]//div[contains(@class,'table-item')][.//span[text() = ' СНИЛС ']]//span[contains(@class, 'value')]")
    public TextBlock snilsTextBlock;

    @Element("Поле Гражданство")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Карточка клиента')]]//div[contains(@class,'table-item')][.//span[text() = ' Гражданство ']]//span[contains(@class, 'value')]")
    public TextBlock citizenshipTextBlock;

    @Element("Поле ИНН")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Карточка клиента')]]//div[contains(@class,'table-item')][.//span[text() = ' ИНН ']]//span[contains(@class, 'value')]")
    public TextBlock innTextBlock;

    @Element("Поле Отношение к категории без СНИЛС")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Карточка клиента')]]//div[contains(@class,'table-item')][.//span[text() = ' Отношение к категории без СНИЛС ']]//span[contains(@class, 'value')]")
    public TextBlock noSnilsTextBlock;

    @Element("Поле Е-mail")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Карточка клиента')]]//div[contains(@class,'table-item')][.//span[text() = ' Е-mail ']]//span[contains(@class, 'value')]")
    public TextBlock emailTextBlock;

    @Element("Поле Пол")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Карточка клиента')]]//div[contains(@class,'table-item')][.//span[text() = ' Пол ']]//span[contains(@class, 'value')]")
    public TextBlock sexTypeTextBlock;

    @Element("Выпадающий список Информация о запрашиваемом кредите")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Информация о запрашиваемом кредите')]]")
    public Button informationAboutRequestedLoanButton;

    @Element("Поле Тип программы кредитования")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Информация о запрашиваемом кредите')]]//div[contains(@class,'table-item')][.//span[text() = ' Тип программы кредитования ']]//span[contains(@class, 'value')]")
    public TextBlock typeLoanProgramTextBlock;

    @Element("Поле Валюта кредита")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Информация о запрашиваемом кредите')]]//div[contains(@class,'table-item')][.//span[text() = ' Валюта кредита ']]//span[contains(@class, 'value')]")
    public TextBlock loanCurrencyTextBlock;

    @Element("Поле Код филиала")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Информация о запрашиваемом кредите')]]//div[contains(@class,'table-item')][.//span[text() = ' Код филиала ']]//span[contains(@class, 'value')]")
    public TextBlock branchCodeTextBlock;

    @Element("Поле Наименование филиала")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Информация о запрашиваемом кредите')]]//div[contains(@class,'table-item')][.//span[text() = ' Наименование филиала ']]//span[contains(@class, 'value')]")
    public TextBlock nameBranchTextBlock;

    @Element("Поле Код офиса")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Информация о запрашиваемом кредите')]]//div[contains(@class,'table-item')][.//span[text() = ' Код офиса ']]//span[contains(@class, 'value')]")
    public TextBlock officeCodeTextBlock;

    @Element("Поле Наименование доп. Офиса")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Информация о запрашиваемом кредите')]]//div[contains(@class,'table-item')][.//span[text() = ' Наименование доп. Офиса ']]//span[contains(@class, 'value')]")
    public TextBlock nameAddOfficeTextBlock;

    @Element("Выпадающий список Информация по анкетам")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Информация по анкетам')]]")
    public Button informationOnProfilesLoanButton;

    @Element("Поле Номер анкеты")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Информация по анкетам')]]//div[contains(@class,'table-item')][.//span[text() = ' Номер анкеты ']]//span[contains(@class, 'value')]")
    public TextBlock questionnaireNumberTextBlock;

    @Element("Поле ФИО")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Информация по анкетам')]]//div[contains(@class,'table-item')][.//span[text() = ' ФИО ']]//span[contains(@class, 'value')]")
    public TextBlock fullNameNumberTextBlock;

    @Element("Выпадающий список Основное место работы")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Основное место работы')]]")
    public Button mainPlaceOfWorkLoanButton;

    @Element("Поле Сведения о занятости в настоящее время")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Основное место работы')]]//div[contains(@class,'table-item')][.//span[text() = ' Сведения о занятости в настоящее время ']]//span[contains(@class, 'value')]")
    public TextBlock currentEmploymentDetailsTextBlock;

    @Element("Поле Госслужащий")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Основное место работы')]]//div[contains(@class,'table-item')][.//span[text() = ' Госслужащий ']]//span[contains(@class, 'value')]")
    public TextBlock civilServantTextBlock;

    @Element("Поле КПП работодателя")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Основное место работы')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'КПП работодателя')]]//span[contains(@class, 'value')]")
    public TextBlock employerKppTextBlock;

    @Element("Поле Почтовый адрес работодателя")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Основное место работы')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Почтовый адрес работодателя')]]//span[contains(@class, 'value')]")
    public TextBlock employerMailingAddressTextBlock;

    @Element("Выпадающий список Контактное лицо")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Контактное лицо')]]")
    public Button contactPersonLoanButton;

    @Element("Поле Контактное лицо для срочной связи")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Контактное лицо')]]//span[normalize-space()='Контактное лицо для срочной связи']/../following-sibling::div/span")
    public TextBlock contactPersonForCommunicationTextBlock;

    @Element("Поле ФИО контактного лица")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Контактное лицо')]]//span[normalize-space()='ФИО контактного лица']/../following-sibling::div/span")
    public TextBlock nameContactPersonTextBlock;

    @Element("Поле Телефон контактного лица по адресу фактического проживания")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Контактное лицо')]]//span[normalize-space()='Телефон контактного лица по адресу фактического проживания']/../following-sibling::div/span")
    public TextBlock homeNumberTextBlock;

    @Element("Поле Мобильный телефон контактного лица")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Контактное лицо')]]//span[normalize-space()='Мобильный телефон контактного лица']/../following-sibling::div/span")
    public TextBlock mobNumberTextBlock;

    @Element("Выпадающий список Информация о Промсвязьбанке")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Информация о Промсвязьбанке')]]")
    public Button informationAboutPromsvyazbankLoanButton;

    @Element("Поле Является сотрудником Банка")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Информация о Промсвязьбанке')]]//div[contains(@class,'table-item')][.//span[text() = 'Является сотрудником Банка']]//span[contains(@class, 'value')]")
    public TextBlock employeeBankTextBlock;

    @Element("Поле Заявитель")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Информация о Промсвязьбанке')]]//div[contains(@class,'table-item')][.//span[text() = ' Заявитель ']]//span[contains(@class, 'value')]")
    public TextBlock applicantTextBlock;

    @Element("Поле Источник сведений о кредите")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Информация о Промсвязьбанке')]]//div[contains(@class,'table-item')][.//span[text() = ' Источник сведений о кредите ']]//span[contains(@class, 'value')]")
    public TextBlock sourceCreditInformationTextBlock;

    @Element("Поле Сотрудник зарплатной организации")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Информация о Промсвязьбанке')]]//div[contains(@class,'table-item')][.//span[text() = ' Сотрудник зарплатной организации ']]//span[contains(@class, 'value')]")
    public TextBlock payrollEmployeeTextBlock;


    @Element("Выпадающий список Внешний вид и документы клиента")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Внешний вид и документы клиента')]]")
    public Button appearanceAndDocumentsClientLoanButton;

    @Element("Поле Оценка внешнего вида")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Внешний вид и документы клиента')]]//div[contains(@class,'table-item')][.//span[text() = ' Оценка внешнего вида ']]//span[contains(@class, 'value')]")
    public TextBlock appearanceRatingTextBlock;

    @Element("Поле Оценка клиента сотрудником банка")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Внешний вид и документы клиента')]]//div[contains(@class,'table-item')][.//span[text() = ' Оценка клиента сотрудником банка ']]//span[contains(@class, 'value')]")
    public TextBlock evaluationClientBankEmployeeTextBlock;

    @Element("Выпадающий список Дополнительная информация о месте работы")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Дополнительная информация о месте работы')]]")
    public Button additionalInformationAboutWorkLoanButton;

    @Element("Поле Оценка клиента сотрудником банка Доп. инф.")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Дополнительная информация о месте работы')]]//div[contains(@class,'table-item')][.//span[text() = ' Тип работодателя ']]//span[contains(@class, 'value')]")
    public TextBlock typeEmployerTextBlock;

    @Element("Поле Общий стаж, мес.")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Дополнительная информация о месте работы')]]//div[contains(@class,'table-item')][.//span[text() = ' Общий стаж, мес. ']]//span[contains(@class, 'value')]")
    public TextBlock totalExperienceMonthsTextBlock;


    @Element("Выпадающий список ЛПР")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЛПР')]]")
    public Button lprTextBlock;

    @Element("Поле Признак ЛПР")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЛПР')]]//div[contains(@class,'table-item')][.//span[text() = ' Признак ЛПР ']]//span[contains(@class, 'value')]")
    public TextBlock signLprTextBlock;

    @Element("Поле Количество ЗП карт в качестве обязательств")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЛПР')]]//div[contains(@class,'table-item')][.//span[normalize-space() = 'Количество ЗП карт в качестве обязательств']]//span[contains(@class, 'value')]")
    public TextBlock numberOfSalaryCardsAsObligations;

    @Element("Поле Средняя ЗП в организации")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЛПР')]]//div[contains(@class,'table-item')][.//span[text() = ' Средняя ЗП в организации ']]//span[contains(@class, 'value')]")
    public TextBlock AverageSalaryInTheOrganization;
}
