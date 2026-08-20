package ru.autotestframework.pages.card_request;

import org.openqa.selenium.support.FindBy;
import ru.autotestframework.pages.components.LeftBar;
import ru.autotestframework.ui_core.page_manager.Element;
import ru.autotestframework.ui_core.page_manager.PageEntry;
import ru.autotestframework.ui_core.services.table_service.FindCellsBy;
import ru.autotestframework.ui_core.services.table_service.FindHeadersBy;
import ru.autotestframework.web_elements.elements.*;
import ru.psb.testit.annotations.Step;
import ru.psb.testit.annotations.Title;

@SuppressWarnings("unused")
@PageEntry(title = "Страница карточка заявки")
public class CardRequestPage extends LeftBar<CardRequestPage> {

    @Element("Вкладка основные данные")
    @FindBy(xpath = "//div[.//span[contains(text(), 'Основные данные')]][@role='tab']")
    public Button basicDataTab;

    @Element("Кнопка Основные данные")
    @FindBy(xpath = "//app-main-data-button/a[contains(text(), 'Основные данные')]")
    public Button basicDataButton;

    @Element("Выпадающий список Результат по заявке")
    @FindBy(xpath = "//div[./div[contains(text(), 'Результат по заявке')]]//nz-select")
    public Button resultApplicationButton;

    @Element("Выпадающий список Результат проверки")
    @FindBy(xpath = "//div[text()=' Результат проверки ']/..//nz-select")
    public Button displayByDropDownPostPoned;

    @Element("Выпадающий список Причина доработки верификация")
    @FindBy(xpath = "//div[./div[contains(text(), 'Причина доработки')]]//nz-select")
    public Button listReasonForRevisionVerificationButton;

    @Element("Вкладка основные данные (с подчеркиванием)")
    @FindBy(xpath = "//div[.//span[contains(text(), 'Основные данные')]][@role='tab']/../../mat-ink-bar")
    public Button basicDataButtonInkBar;

    @Element("Вкладка Результаты проверок")
    @FindBy(xpath = "//div[.//span[contains(text(), 'Результаты проверок')]][@role='tab']")
    public Button testResultButton;

    @Element("Вкладка Решение Андеррайтера")
    @FindBy(xpath = "//div[.//span[contains(text(), 'Решение Андеррайтера')]][@role='tab']")
    public Button decisionUnderwriterButton;

    @Element("Вкладка История")
    @FindBy(xpath = "//div[.//span[contains(text(), 'История')]][@role='tab']")
    public Button historyButton;

    @Element("Вкладка Дополнительная информация")
    @FindBy(xpath = "//div[.//span[contains(text(), 'Дополнительная информация')]][@role='tab']")
    public Button additionalInformationButton;

    @Element("Кнопка История версий(блок участники сделки)")
    @FindBy(xpath = "//app-inspection-results//button[./span[contains(text(), 'История версий')]]")
    public Button historyVersionsDealsButton;

    @Element("Таблица Участник сделки")
    @FindBy(xpath = "//div[@class='app-inspection-results-list-root']/mat-table")
    @FindCellsBy(xpath = ".//mat-row//mat-cell")
    @FindHeadersBy(xpath = ".//mat-header-row//mat-header-cell")
    public WebTable transactionParticipantTable;

    @Element("Таблица Кредитные отчеты")
    @FindBy(xpath = "//div[@class='app-credit-reports-list-root']")
    @FindCellsBy(xpath = ".//th|//td")
    @FindHeadersBy(xpath = ".//th[contains(@class, 'heading-bold table-name')]|//td[contains(@class, 'heading-bold ng-star-inserted')]")
    public WebTable creditReportsTable;

    @Element("Таблица Блок комментария")
    @FindBy(xpath = "//div[@class='app-services-comments-list-root']")
    @FindCellsBy(xpath = ".//th|//td")
    @FindHeadersBy(xpath = ".//th[contains(@class, 'heading-bold')]|//td[contains(@class, 'cell-with-icon')]|//td")
    public WebTable commentBlockTable;

    @Element("Таблица Список подобранных решений")
    @FindBy(xpath = "//div[@class='app-selected-solutions-list-root']")
    @FindCellsBy(xpath = ".//div[contains(@class, 'column')]")
    @FindHeadersBy(xpath = ".//div[@class='heading']")
    public WebTable listResultTable;

    @Element("Таблица Список подобранных решений развернутый")
    @FindBy(xpath = "//div[@class='secondary-columns outside-viewport ng-star-inserted']")
    @FindCellsBy(xpath = ".//div[contains(@class, 'column')]")
    @FindHeadersBy(xpath = ".//div[@class='heading']")
    public WebTable listResultAllTable;

    @Element("Кнопка История версий(блок кредитные отчеты)")
    @FindBy(xpath = "//app-credit-reports-list//button[./span[contains(text(), 'История версий')]]")
    public Button historyVersionsCreditButton;

    @Element("Кнопка История версий(блок комментарий)")
    @FindBy(xpath = "//app-services-comments-list//button[./span[contains(text(), 'История версий')]]")
    public Button historyVersionsCommentButton;

    @Element("Первая заявка в таблице")
    @FindBy(xpath = "//tbody/tr")
    public TextBlock firstApplicationInTable;

    @Element("Переключатель Да(Подбор Решения)")
    @FindBy(xpath = "//div[contains(@class, 'loan-information-content-radioBtn')]//mat-radio-button[.//span[contains(text(), 'Да')]]")
    public Button radioButtonYesInSolutionSelection;

    @Element("Переключатель Нет(Подбор Решения)")
    @FindBy(xpath = "//div[contains(@class, 'loan-information-content-radioBtn')]//mat-radio-button[.//span[contains(text(), 'Нет')]]")
    public Button radioButtonNoInSolutionSelection;

    @Element("Поле ввода Комментарий MPK/L0")
    @FindBy(xpath = "//div[./span[contains(text(), 'Комментарий MPK/L0')]]/div[contains(@class, 'system-reports-content-comment-message')]")
    public TextInput underwriter;

    @Element("Выпадающий список Занятость подтверждена")
    @FindBy(xpath = "//div[./span[contains(text(), 'Занятость подтверждена')]]//mat-select")
    public Button listEmploymentConfirmed;

    @Element("Выпадающий список Телефон подтвержден")
    @FindBy(xpath = "//div[./span[contains(text(), 'Телефон подтвержден')]]//mat-select")
    public Button listPhoneConfirmed;

    @Element("Переключатель Да(Совпадает с местом регистрации)")
    @FindBy(xpath = "//div[contains(@class, 'applicant-addresses-content-radioBtn')]//mat-radio-button[.//span[contains(text(), 'Да')]]")
    public Button radioButtonYesRegistration;

    @Element("Переключатель Нет(Совпадает с местом регистрации)")
    @FindBy(xpath = "//div[contains(@class, 'applicant-addresses-content-radioBtn')]//mat-radio-button[.//span[contains(text(), 'Нет')]]")
    public Button radioButtonNoRegistration;

    @Element("Поле ФИО и дата рождения")
    @FindBy(xpath = "//div[@class = 'documents-data-info-activeParticipant']")
    public TextBlock fullNameYearOfBirthTextBlock;

    @Element("Поле Название файла")
    @FindBy(xpath = "//td[contains(@class, 'cdk-column-name')]/span")
    public TextBlock fileNameTextBlock;

    @Element("Поле Дата прикрепления документов")
    @FindBy(xpath = "//td[contains(@class, 'cdk-column-date')]")
    public TextBlock storageFileTimeTextBlock;

    @Element("Таблица участники сделки")
    @FindBy(xpath = "//div[@class='participants-in-transaction']/div")
    @FindCellsBy(xpath = ".//div[contains(@class,'item') and not(contains(@class,'header'))]/*")
    @FindHeadersBy(xpath = ".//div[contains(@class,'content-header-item')]/div|/span")
    public WebTable searchResultTable;

    @Element("Поле Источник поступления")
    @FindBy(xpath = "//mat-form-field[.//mat-label[contains(text(),'Источник поступления')]]//span[contains(@class,'text')]")
    public TextBlock incomeSourceTextBlock;

    @Element("Поле Опер. офис (регион)")
    @FindBy(xpath = "//mat-form-field[.//mat-label[contains(text(),'Опер. офис (регион)')]]//span[contains(@class,'text')]")
    public TextBlock operationalOfficeRegionTextBlock;

    @Element("Поле Запрашиваемая сумма")
    @FindBy(xpath = "//mat-form-field[.//mat-label[contains(text(),'Запрашиваемая сумма')]]//span[contains(@class,'text')]")
    public TextBlock requestedAmountTextBlock;

    @Element("Поле Срок кредита")
    @FindBy(xpath = "//mat-form-field[.//mat-label[contains(text(),'Срок кредита')]]//span[contains(@class,'text')]")
    public TextBlock creditTermTextBlock;

    @Element("Поле Макс. сумма подбора")
    @FindBy(xpath = "//mat-form-field[.//mat-label[contains(text(),'Макс. сумма подбора')]]//span[contains(@class,'text')]")
    public TextBlock maximumLoanAmountTextBlock;

    @Element("Таблица Доход по анкете")
    @FindBy(xpath = "//div[contains(@class,'claim-mainData-content-bottom-right-large')]//div[@class='income-according-questionnaire']/span/following-sibling::div[@class='income-according-questionnaire-content']")
    @FindCellsBy(xpath = ".//div[contains(@class,'column')]/*[contains(@class,'income-according-questionnaire-content-column') and not(contains(@class,'header'))]")
    @FindHeadersBy(xpath = ".//div[contains(@class,'column-header')]")
    public WebTable formIncomeTable;

    @Element("Кнопка Показать все столбцы")
    @FindBy(xpath = "//div[contains(@class, 'header-row')]//button[.//span[contains(text(), 'Показать все столбцы')]]")
    public Button showAllColumnsButton;

    @Element("Кнопка Просмотреть историю")
    @FindBy(xpath = "//div[contains(@class, 'system-reports')]//button[.//span[contains(text(), 'Просмотреть историю')]]")
    public Button viewHistoryButton;

    @Element("Кнопка Доработка")
    @FindBy(xpath = "//div[contains(@class, 'conclusion-chooseReason')]//button[.//span[contains(text(), 'Доработка')]]")
    public Button refinementButton;

    @Element("Кнопка Сохранить")
    @FindBy(xpath = "//div[contains(@class, 'conclusion-commentUnderwriter')]//button[.//span[contains(text(), 'Сохранить')]]")
    public Button saveButton;

    @Element("Кнопка Отложить")
    @FindBy(xpath = "//div[contains(@class, 'header')]//button[.//span[contains(text(), 'Отложить')]]")
    public Button postponeButton;

    @Element("Кнопка Сохранить (header)")
    @FindBy(xpath = "//div[contains(@class, 'header')]//button[.//span[contains(text(), 'Сохранить')]]")
    public Button saveButtonHeader;

    @Element("Кнопка Закрыть (header)")
    @FindBy(xpath = "//div[contains(@class, 'header')]//button[.//span[contains(text(), 'Закрыть')]]")
    public Button closeButtonHeader;

    @Element("Кнопка Взять в работу")
    @FindBy(xpath = "//div[contains(@class, 'header')]//button[.//span[contains(text(), 'Взять в работу')]]")
    public Button takeToWorkButton;

    @Element("Кнопка Вернуть в очередь")
    @FindBy(xpath = "//div[contains(@class, 'header')]//button[.//span[contains(text(), 'Вернуть в очередь')]]")
    public Button returnToQueueButton;

    @Element("Модальное окно предупреждения")
    @FindBy(xpath = "//div//mat-dialog-container")
    public TextBlock modalInfoError;

    @Element("Кнопка ОК на Модальном окне предупреждения")
    @FindBy(xpath = "//div//mat-dialog-container//app-button")
    public TextBlock okOnmodalInfoErrorButton;

    @Element("Модальное окно Информация об ошибке")
    @FindBy(xpath = "//div[@class='modal-error_description']")
    public TextBlock infoErrorModal;

    @Element("Кнопка Да модального окна")
    @FindBy(xpath = "//div[contains(@class, 'application-attention-btn')]//button[.//span[contains(text(), 'Да')]] " +
            "| //div[contains(@class, 'cdk-overlay-pane')]//button[.//span[contains(text(), 'Да')]]")
    public Button yesButton;

    @Element("Кнопка Нет модального окна")
    @FindBy(xpath = "//div[contains(@class, 'application-attention-btn')]//button[.//span[contains(text(), 'Нет')]] " +
            "| //div[contains(@class, 'cdk-overlay-pane')]//button[.//span[contains(text(), 'Нет')]]")
    public Button noButton;

    @Element("Кнопка редактировать Скоррект. доход По осн. месту")
    @FindBy(xpath = "(//div[contains(text(),'По осн. месту')]/..//mat-icon[@data-mat-icon-name='edit'])[1]")
    public Button editMainPlaceWorkButton;

    @Element("Поле редактировать Скоррект. доход По осн. месту")
    @FindBy(xpath = "(//div[contains(text(),'По осн. месту')]/..//input)[2]")
    public TextInput amountMainPlaceWorkButton;

    @Element("Кнопка редактировать Скоррект. доход Иные доходы")
    @FindBy(xpath = "(//div[contains(text(),'Иные доходы')]/..//mat-icon[@data-mat-icon-name='edit'])[1]")
    public Button editOthersIncomeWorkButton;

    @Element("Поле редактировать Скоррект. доход Иные доходы")
    @FindBy(xpath = "(//div[contains(text(),'Иные доходы')]/..//input)[2]")
    public TextInput amountOthersIncomeButton;

    @Element("Кнопка редактировать Скоррект. доход Совмест. 1")
    @FindBy(xpath = "(//div[contains(text(),'Совмест. 1')]/..//mat-icon[@data-mat-icon-name='edit'])[1]")
    public Button editJoint1Button;

    @Element("Поле редактировать Скоррект. доход Совмест. 1")
    @FindBy(xpath = "(//div[contains(text(),'Совмест. 1')]/..//input)[2]")
    public TextInput amountJoint1Button;

    @Element("Кнопка Пересчитать лимит")
    @FindBy(xpath = "//div[contains(@class, 'claim-mainData-content-bottom-right-first')]//button[.//span[contains(text(), 'Пересчитать лимит')]]")
    public Button recalculateLimitButton;

    @Element("Кнопка Отменить")
    @FindBy(xpath = "//div[contains(@class, 'claim-mainData-content-bottom-right-first')]//button[.//span[contains(text(), 'Отменить')]]")
    public Button cancelButton;

    @Element("Кнопка Отменить (Отправить на корректировку)")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Отменить')]]")
    public Button cancelCorrectButton;

    @Element("Информация по заявке (ФИО и № заявки)")
    @FindBy(xpath = "//div[@class = 'header-info']//span[1]")
    public TextBlock infoFIOTextBlock;

    @Element("Информация по заявке (Дата и Статус)")
    @FindBy(xpath = "//span[contains(text(),  'Статус')]")
    public TextBlock infoDateStatusTextBlock;

    @Element("Ссылка Документы к заявке")
    @FindBy(xpath = "//div[contains(@class,'content-links-item')]/span[contains(text(),'Документы к заявке')]")
    public Link requestDocumentLink;

    @Element("Ссылка Автопроверки")
    @FindBy(xpath = "//div[contains(@class,'content-links-item')]/span[contains(text(),'Автопроверки')]")
    public Link autochecksLink;

    @Element("Ссылка Изменение полей")
    @FindBy(xpath = "//div[contains(@class,'content-links-item')]/span[contains(text(),'Изменение полей')]")
    public Link changeFieldsLink;

    @Element("Ссылка Отчет Data Miner")
    @FindBy(xpath = "//div[contains(@class,'content-links-item')]/span[contains(text(),'Отчет Data Miner')]")
    public Link reportDataMinerLink;

    @Element("Ссылка Отчет Anti FRAUD")
    @FindBy(xpath = "//div[contains(@class,'content-links-item')]/span[contains(text(),'Отчет Anti FRAUD')]")
    public Link reportAntiFraudLink;

    @Element("Ссылка Предыдущие заявки")
    @FindBy(xpath = "//div[contains(@class,'content-links-item')]/span[contains(text(),'Предыдущие заявки')]")
    public Link previousRequestsLink;

    @Element("Ссылка Идеальная КИ")
    @FindBy(xpath = "//div[contains(@class,'content-links-item')]/span[contains(text(),'Идеальная КИ')]")
    public Link idealKiLink;

    @Element("Поле ФИО заявителя")
    @FindBy(xpath = "//mat-form-field[.//mat-label[contains(text(), 'ФИО заявителя')]]//span[contains(@class,'text')]")
    public TextBlock nameApplicantTextBlock;

    @Element("Поле ввода Дата рождения")
    @FindBy(xpath = "//mat-form-field[.//mat-label[contains(text(), 'Дата рождения')]]//div[contains(@class, 'static-form-field')]//span")
    public TextBlock dateBirthTextBlock;

    @Element("Поле ввода Место рождения")
    @FindBy(xpath = "//mat-form-field[.//mat-label[contains(text(), 'Место рождения')]]//div[contains(@class, 'static-form-field')]//span")
    public TextBlock placeBirthTextBlock;

    @Element("Поле ввода Паспорт")
    @FindBy(xpath = "//mat-form-field[.//mat-label[contains(text(), 'Паспорт')]]//div[contains(@class, 'static-form-field')]//span")
    public TextBlock pasportTextBlock;

    @Element("Поле ввода Код подразделения")
    @FindBy(xpath = "//mat-form-field[.//mat-label[contains(text(), 'Код подразделения')]]//div[contains(@class, 'static-form-field')]//span")
    public TextBlock departmentCodeTextBlock;

    @Element("Поле ввода Дата выдачи")
    @FindBy(xpath = "//mat-form-field[.//mat-label[contains(text(), 'Дата выдачи')]]//div[contains(@class, 'static-form-field')]//span")
    public TextBlock dateIssueTextBlock;

    @Element("Поле ввода Кем выдан")
    @FindBy(xpath = "//mat-form-field[.//mat-label[contains(text(), 'Кем выдан')]]//div[contains(@class, 'static-form-field')]//span")
    public TextBlock issuedByTextBlock;

    @Element("Поле ввода Номер клиента")
    @FindBy(xpath = "//mat-form-field[.//mat-label[contains(text(), 'Номер клиента')]]//div[contains(@class, 'static-form-field')]")
    public TextBlock numberClientTextBlock;

    @Element("Поле ввода Образование")
    @FindBy(xpath = "//mat-form-field[.//mat-label[contains(text(), 'Образование')]]//div[contains(@class, 'static-form-field')]")
    public TextBlock educationTextBlock;

    @Element("Поле ввода Семейное полож.")
    @FindBy(xpath = "//mat-form-field[.//mat-label[contains(text(), 'Семейное полож.')]]//div[contains(@class, 'static-form-field')]")
    public TextBlock familyStatusTextBlock;

    @Element("Поле ввода Трудовая книжка")
    @FindBy(xpath = "//mat-form-field[.//mat-label[contains(text(), 'Трудовая книжка')]]//div[contains(@class, 'static-form-field')]")
    public TextBlock employmentHistoryTextBlock;

    @Element("Поле ввода Общий стаж")
    @FindBy(xpath = "//mat-form-field[.//mat-label[contains(text(), 'Общий стаж')]]//div[contains(@class, 'static-form-field')]")
    public TextBlock generalExperienceTextBlock;

    @Element("Поле ввода Мобильный")
    @FindBy(xpath = "//div[contains(@class, 'applicant-details')]//mat-form-field[.//mat-label[contains(text(), 'Мобильный')]]//div[contains(@class, 'static-form-field')]")
    public TextBlock mobileTextInput;

    @Element("Поле ввода Работодатель")
    @FindBy(xpath = "//mat-form-field[.//mat-label[contains(text(), 'Работодатель')]]//div[contains(@class, 'static-form-field')]")
    public TextBlock employerTextBlock;

    @Element("Поле ввода Форма собственности")
    @FindBy(xpath = "//mat-form-field[.//mat-label[contains(text(), 'Форма собственности')]]//div[contains(@class, 'static-form-field')]")
    public TextBlock typeOwnershipTextBlock;

    @Element("Поле ввода Дата начала работы")
    @FindBy(xpath = "//mat-form-field[.//mat-label[contains(text(), 'Дата начала работы')]]//div[contains(@class, 'static-form-field')]")
    public TextBlock startDateTextBlock;

    @Element("Поле ввода Число сотрудников")
    @FindBy(xpath = "//mat-form-field[.//mat-label[contains(text(), 'Число сотрудников')]]//div[contains(@class, 'static-form-field')]")
    public TextBlock numberEmployeesTextBlock;

    @Element("Поле ввода ИНН")
    @FindBy(xpath = "//mat-form-field[.//mat-label[contains(text(), 'ИНН')]]//div[contains(@class, 'static-form-field')]")
    public TextBlock innTextBlock;

    @Element("Поле ввода Сфера деятельности")
    @FindBy(xpath = "//mat-form-field[.//mat-label[contains(text(), 'Сфера деятельности')]]//div[contains(@class, 'static-form-field')]")
    public TextBlock fieldActivityTextBlock;

    @Element("Поле ввода Управленческий статус")
    @FindBy(xpath = "//mat-form-field[.//mat-label[contains(text(), 'Управленческий статус')]]//div[contains(@class, 'static-form-field')]")
    public TextBlock managerialStatusTextBlock;

    @Element("Поле ввода Должность")
    @FindBy(xpath = "//mat-form-field[.//mat-label[contains(text(), 'Должность')]]//div[contains(@class, 'static-form-field')]")
    public TextBlock jobTitleTextBlock;

    @Element("Поле ввода ФИО руководителя")
    @FindBy(xpath = "//mat-form-field[.//mat-label[contains(text(), 'ФИО руководителя')]]//div[contains(@class, 'static-form-field')]")
    public TextBlock fullNameHeadTextBlock;

    @Element("Поле ввода Служебный телефон")
    @FindBy(xpath = "//mat-form-field[.//mat-label[contains(text(), 'Служебный телефон')]]//div[contains(@class, 'static-form-field')]")
    public TextBlock officialPhoneTextBlock;

    @Element("Кнопка редактирования поля Занятость подтверждена по:")
    @FindBy(xpath = "//mat-label[contains(text(), 'Занятость подтверждена по:')]//..//..//..//..//div[contains(@class, 'suffix')]//mat-icon")
    public Button matIconButton;

    @Element("Поле введенного телефона Занятость подтверждена по: часть1")
    @FindBy(xpath = "//div//input[@id = 'first']")
    public TextInput tellInputFirstTextInput;

    @Element("Поле введенного телефона Занятость подтверждена по: часть2")
    @FindBy(xpath = "//div//input[@id = 'second']")
    public TextInput tellInputSecondTextInput;

    @Element("Поле введенного телефона Занятость подтверждена по: часть3")
    @FindBy(xpath = "//div//input[@id = 'third']")
    public TextInput tellInputThirdTextInput;

    @Element("Поле введенного телефона Занятость подтверждена по: часть4")
    @FindBy(xpath = "//div//input[@id = 'fourth']")
    public TextInput tellInputFourthTextInput;

    @Element("Поле ввода Телефон отдела кадров")
    @FindBy(xpath = "//mat-form-field[.//mat-label[contains(text(), 'Телефон отдела кадров')]]//div[contains(@class, 'static-form-field')]")
    public TextInput hrPhoneTextInput;

    @Element("Поле ввода Телефон руководителя")
    @FindBy(xpath = "//mat-form-field[.//mat-label[contains(text(), 'Телефон руководителя')]]//div[contains(@class, 'static-form-field')]")
    public TextInput managerPhoneTextInput;

    @Element("Поле ввода Занятость подтверждена по:")
    @FindBy(xpath = "//mat-form-field[.//mat-label[contains(text(), 'Занятость подтверждена по:')]]//input")
    public TextInput employmentConfirmedTextInput;

    @Element("Поле ввода Адрес регистрации")
    @FindBy(xpath = "//mat-form-field[.//mat-label[contains(text(), 'Адрес регистрации')]]//div[contains(@class, 'static-form-field')]")
    public TextBlock fieldRegistrationAddressTextBlock;

    @Element("Поле ввода Адрес фактического проживания")
    @FindBy(xpath = "//mat-form-field[.//mat-label[contains(text(), 'Адрес фактического проживания')]]//div[contains(@class, 'static-form-field')]")
    public TextBlock actualResidenceTextBlock;

    @Element("Поле Адрес регистрации")
    @FindBy(xpath = "//div[span[contains(text(), 'Адреса заявителя')]]//span[contains(text(), 'Еврейская')]//..//..//..//mat-label")
    public TextBlock registrationAddressTextBlock;

    @Element("Поле ввода Дата регистрации")
    @FindBy(xpath = "//mat-form-field[.//mat-label[contains(text(), 'Дата регистрации')]]//div[contains(@class, 'static-form-field')]")
    public TextBlock registrationDateTextBlock;

    @Element("Поле Совпадает с местом регистрации")
    @FindBy(xpath = "//div[contains(@class, 'applicant-addresses-content-radioBtn')]//mat-radio-button[contains(@value, 'true')]")
    public TextBlock samePlaceTextBlock;

    @Element("Поле ввода Фактический адрес работодателя")
    @FindBy(xpath = "//mat-form-field[.//mat-label[text() = ' Фактический адрес работодателя ']]//div[contains(@class, 'static-form-field')]")
    public TextBlock factRegistrationAddressTextBlock;

    @Element("Поле Фактический адрес работодателя")
    @FindBy(xpath = "//div[span[contains(text(), 'Адреса работодателя')]]//span[contains(text(), 'Еврейская')]//..//..//..//mat-label")
    public TextBlock factRegistrationEmployerTextBlock;

    @Element("Поле Номер заявки")
    @FindBy(xpath = "//div[@class='claim-header-info-top']/span[contains(@class,'main') and not(contains(@class,'text'))]")
    public TextBlock requestNumberTextBlock;

    @Element("Поле Тип заявки")
    @FindBy(xpath = "//div[@class='claim-header-info-top']/div/span")
    public TextBlock requestTypeTextBlock;

    @Element("Поле Дата заведения заявки")
    @FindBy(xpath = "//div[@class='claim-header-info-top']/span[contains(@class,'top-date')]")
    public TextBlock topDateTextBlock;

    @Element("Поле Цель кредита")
    @FindBy(xpath = "//div[contains(@class,'mat-form-field')][.//mat-label[contains(text(),'Цель кредита')]]//span[contains(@class,'text')]")
    public TextBlock loanPurposeTextBlock;

    @Element("Поле ФИО контактного лица для срочной связи (Отец)")
    @FindBy(xpath = "//div[contains(@class, 'spouse')]//mat-form-field[.//mat-label[contains(text(), ' Контактное лицо ')]]//div[contains(@class, 'static')]//span")
    public TextBlock contactPersonTextBlock;

    @Element("Поле ФИО контактного лица для срочной связи")
    @FindBy(xpath = "//div[contains(@class, 'spouse')]//mat-form-field[.//mat-label[contains(text(), ' ФИО контактного лица для срочной связи ')]]//span[contains(@class,'text')]")
    public TextBlock fullNameContactPersonTextBlock;

    @Element("Поле Мобильный телефон контактного лица")
    @FindBy(xpath = "//div[contains(@class, 'spouse')]//mat-form-field[.//mat-label[contains(text(), 'Мобильный')]]//span[contains(@class,'text')]")
    public TextBlock phoneContactPersonTextBlock;

    @Element("Поле По адресу проживания")
    @FindBy(xpath = "//div[contains(@class, 'spouse')]//mat-form-field[.//mat-label[contains(text(), 'По адресу проживания')]]//span[contains(@class,'text')]")
    public TextBlock phoneAddressPersonTextBlock;

    @Element("Поле Доход по анкете")
    @FindBy(xpath = "//div[contains(@class, 'income-according-questionnaire')]//span[contains(text(),'Доход по анкете')]")
    public TextBlock incomeQuestionnaireTextBlock;

    @Element("Выпадающий список Причина доработки")
    @FindBy(xpath = "//div[./span[contains(text(), 'Причина доработки')]]//mat-select")
    public Button listReasonForRevisionButton;

    @Element("Поле ввода Комментарий МРК и отлагательных условий")
    @FindBy(xpath = "//textarea[@placeholder='Комментарий МРК и отлагательных условий']")
    public TextInput textareaCommentMkrTextInput;

    @Element("Поле ввода Внутренний комментарий андеррайтера")
    @FindBy(xpath = "//textarea[@placeholder='Внутренний комментарий андеррайтера']")
    public TextInput textareaCommentUnderwriterTextInput;

    @Element("Поле ввода Внутренний комментарий")
    @FindBy(xpath = "//div[contains(text(), 'Внутренний комментарий')]//..//textarea")
    public TextInput textareaCommentTextInput;

    @Element("Поле ввода Комментарий для МРК")
    @FindBy(xpath = "//div[contains(text(), 'Комментарий для МРК')]//..//textarea")
    public TextInput commentMRKTextInput;

    @Element("Кнопка Далее")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Далее')]]")
    public Button buttonNext;

    @Element("Кнопка Завершить проверку")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Завершить проверку')]]")
    public Button completeVerificationButton;

    //История коментриев
    @Element("Кнопка развернуть комментарий L0")
    @FindBy(xpath = "//span[normalize-space()='L0']/../mat-icon")
    public Button buttonL0;

    @Element("Поле Коментарий Дата")
    @FindBy(xpath = "//span[contains(text(), 'L0')]/..//span[contains(@class, 'comment-date')]")
    public TextBlock commentDateTextBlock;

    @Element("Поле Коментарий ФИО")
    @FindBy(xpath = "//span[contains(text(), 'L0')]/..//span[contains(@class, 'comment-author')]")
    public TextBlock commentFIOTextBlock;

    @Element("Поле Коментарий Текст")
    @FindBy(xpath = "//span[normalize-space()='L0']/../following-sibling::div/span")
    public TextBlock commentTextBlock;

    @Element("Кнопка Выпадающий Коментарий МРК")
    @FindBy(xpath = "//span[contains(text(), 'Иванов Иван Иванович')]/../..//mat-icon")
    public Button commentMrkButton;

    @Element("Поле Коментарий ФИО МРК")
    @FindBy(xpath = "//span[contains(text(), 'МРК')]/..//span[contains(text(), 'Иванов')]")
    public TextBlock commentFioMrkTextBlock;

    @Element("Поле Коментарий Дата МРК")
    @FindBy(xpath = "//span[contains(text(), 'МРК')]/..//span[contains(text(), 'Иванов')]/preceding-sibling::span")
    public TextBlock commentDateMrkTextBlock;

    @Element("Поле Коментарий Текст МРК")
    @FindBy(xpath = "//span[contains(text(), 'Иванов Иван Иванович')]/../..//div[contains(@class, 'comment-body -expanded')]/span")
    public TextBlock commentMrkTextBlock;

    @Element("Кнопка Выпадающий Коментарий Док")
    @FindBy(xpath = "//span[contains(text(), 'Петров Петр Петрович')]/../..//mat-icon")
    public Button commentDocButton;

    @Element("Поле Коментарий ФИО Док")
    @FindBy(xpath = "//span[contains(text(), 'МРК')]/..//span[contains(text(), 'Петров')]")
    public TextBlock commentFioDocTextBlock;

    @Element("Поле Коментарий Дата Док")
    @FindBy(xpath = "//span[contains(text(), 'МРК')]/..//span[contains(text(), 'Петров')]/preceding-sibling::span")
    public TextBlock commentDateDocTextBlock;

    @Element("Поле Коментарий Текст Док")
    @FindBy(xpath = "//span[contains(text(), 'Петров Петр Петрович')]//..//..//div[contains(@class, 'comment-body')]")
    public TextBlock commentDocTextBlock;

    @Element("Кнопка История(Комментарий МРК)")
    @FindBy(xpath = "//div[contains(@class,'conclusion-chooseReason')]//button[./span[contains(text(), 'История')]]")
    public Button historyCommentMkrButton;

    @Element("Кнопка История(Комментарий андеррайтера)")
    @FindBy(xpath = "//div[contains(@class,'conclusion-commentUnderwriter')]//button[./span[contains(text(), 'История')]]")
    public Button historyCommentUnderwriterButton;

    @Element("Кнопка Сохранить(Комментарий андеррайтера)")
    @FindBy(xpath = "//div[contains(@class,'conclusion-commentUnderwriter')]//button[./span[contains(text(), 'Сохранить')]]")
    public Button saveCommentUnderwriterButton;

    //Контакты заявителя

    @Element("Поле Мобильный телефон 1")
    @FindBy(xpath = "//mat-form-field[.//mat-label[contains(text(), 'Мобильный телефон 1')]]//span[contains(@class,'text')]")
    public TextBlock phoneOneTextBlock;

    @Element("Поле Мобильный телефон 2")
    @FindBy(xpath = "//mat-form-field[.//mat-label[contains(text(), 'Мобильный телефон 2')]]//span[contains(@class,'text')]")
    public TextBlock phoneTwoTextBlock;

    @Element("Поле По адресу проживания (Контакты заявителя)")
    @FindBy(xpath = "//div[span[contains(text(),'Контакты заявителя')]]//mat-form-field[.//mat-label[contains(text(), 'По адресу проживания')]]//span[contains(@class,'text')]")
    public TextBlock byAddressTextBlock;

    @Element("Поле По адресу регистрации (Контакты заявителя)")
    @FindBy(xpath = "//div[span[contains(text(),'Контакты заявителя')]]//mat-form-field[.//mat-label[contains(text(), 'По адресу регистрации')]]//span[contains(@class,'text')]")
    public TextBlock byRegistrationAddressTextBlock;

    @Element("Поле ФИО cупруга заявителя")
    @FindBy(xpath = "//mat-form-field[.//mat-label[contains(text(), 'ФИО cупруга заявителя')]]//span[contains(@class,'text')]")
    public TextBlock fullNameApplicantSpouseTextBlock;

    @Element("Поле Дата рождения супруга заявителя")
    @FindBy(xpath = "//div[contains(@class,'spouse')]//mat-form-field[.//mat-label[contains(text(), 'Дата рождения')]]//div[contains(@class, 'static-form-field')]//span")
    public TextBlock dateBirthSpouseTextBlock;

    @Element("Поле Мобильный 1 супруга заявителя")
    @FindBy(xpath = "//div[contains(@class,'spouse')]//mat-form-field[.//mat-label[contains(text(), 'Мобильный 1')]]//div[contains(@class, 'static-form-field')]//span")
    public TextBlock mobileSpouseTextBlock;

    @Element("Поле Служебный супруга заявителя")
    @FindBy(xpath = "//div[contains(@class,'spouse')]//mat-form-field[.//mat-label[contains(text(), 'Служебный')]]//div[contains(@class, 'static-form-field')]//span")
    public TextBlock workPhoneSpouseTextBlock;

    @Element("Поле Форма подтверждения по основному месту")
    @FindBy(xpath = "(//div[./div[text()=' По осн. месту ']]//input)[1]")
    public TextBlock mainPlaceConfirmationFormTextBlock;

    @Element("Поле Доход после налогообложения Иные Доходы")
    @FindBy(xpath = "(//div[./div[text()=' Иные доходы ']]//input)[1]")
    public TextBlock otherIncomeConfirmationFormTextBlock;

    @Element("Поле Доход после налогообложения Совмест. 1")
    @FindBy(xpath = "(//div[contains(text(),'Совмест. 1')]/..//input)[1]")
    public TextBlock incomeAfterTaxJointTextBlock;

    @Element("Вкладка Совместительство 1")
    @FindBy(xpath = "//div[contains(@class,'work-header')]//span[contains(text(), ' Совместительство 1 ')]")
    public TextBlock partTimeJobTextBlock;

    @Element("Поле Информация о заявке")
    @FindBy(xpath = "//div[@class='claim-header-info']")
    public TextBlock headerInfoClaimTextBlock;

    //Кнопка внутренний комментарий

    @Element("Окно внутренних комментариев")
    @FindBy(xpath = "//div[contains(@class, 'ant-modal-content')]")
    public Button windowComment;

    @Element("Кнопка закрыть Окно")
    @FindBy(xpath = "//button[@aria-label = 'Close']")
    public Button buttonClose;

    @Element("Кнопка Внутренний комментарий")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Внутренний комментарий')]]")
    public Button buttonComment;

    @Element("Поле ввода История комментариев")
    @FindBy(xpath = "//textarea[@placeholder = 'Комментарий']")
    public TextInput historyComment;

    @Element("Поле История комментариев")
    @FindBy(xpath = "//div[@class= 'ng-star-inserted']//textarea")
    public TextBlock commentHistoryTextBlock;

    @Element("Поле Информация по истории изменений")
    @FindBy(xpath = "//div//span[@class='comment-content']")
    public TextBlock infoHistoryTextBlock;


    //Модальное окно Истрия комментариев

    @Element("Окно История комментариев")
    @FindBy(xpath = "//mat-dialog-container")
    public Button windowHistoryComment;

    @Element("Кнопка История(Внутренний комментарий андеррайтера)")
    @FindBy(xpath = "//div[contains(@class,'conclusion-commentUnderwriter')]//button[./span[contains(text(), 'История')]]")
    public Button commentUnderwriterButton;

    @Element("Кнопка Раскрыть все комментарии")
    @FindBy(xpath = "//div[contains(@class, 'application-comment-item-root ng-star-inserted')]//mat-icon")
    public Button commentUnderwriterOpenButton;

    @Element("Кнопка закрыть Окно История комментариев")
    @FindBy(xpath = "//mat-icon[@data-mat-icon-name = 'dialog-close-cross']")
    public Button buttonWindowsHistoryCommentClose;

    @Element("Поле с Историей комментариев 1")
    @FindBy(xpath = "//span[text() = 'Автоматическое Тестирование1']//..//..//div[contains(@class, 'comment-body')]//span")
    public TextBlock commentHistory1TextBlock;

    @Element("Поле с Историей комментариев 2")
    @FindBy(xpath = "//span[text() = 'Автоматическое Тестирование2']//..//..//div[contains(@class, 'comment-body')]//span")
    public TextBlock commentHistory2TextBlock;

    //Кнопка Комментариии и Модальное окно Комментарии

    @Element("Кнопка Комментарии")
    @FindBy(xpath = "//app-button[button[.//span[contains(text(), 'Комментарии')]]]")
    public Button buttonComments;

    @Element("Метка")
    @FindBy(xpath = "//div[contains(@class, 'comment')]//div[text()]")
    public TextBlock markerText;

    @Element("Поле Комментарий")
    @FindBy(xpath = "//div[contains(@class, 'comment')]//textarea")
    public TextBlock commentText;

    //Модальное окно Перевод заявки в отложенные

    @Element("Модальное окно Перевод заявки в отложенные")
    @FindBy(xpath = "//div[contains(@class, 'psb-dialog-pane')]")
    public IFrame modalWindow;

    @Element("Выпадающий список Причина (Перевод заявки в отложенные)")
    @FindBy(xpath = "//div[.//div[./span[contains(text(), 'Причина')]]]//mat-select[@formcontrolname = 'delayReason']")
    public Button reason;

    @Element("Кнопка закрыть модальное окно (крестик)")
    @FindBy(xpath = "//mat-dialog-container//mat-icon[contains(@data-mat-icon-name, 'close')]")
    public Button buttonCloseModalWindows;

    @Element("Список Причин (Перевод заявки в отложенные)")
    @FindBy(xpath = "//div[@role = 'listbox']")
    public Button listReason;

    @Element("Поле ввода комментарий (Перевод заявки в отложенные)")
    @FindBy(xpath = "//textarea[@placeholder='Текст комментария']")
    public TextInput comment;

    @Element("Кнопка Время для звонка участнику")
    @FindBy(xpath = "//div[./div[./span[contains(text(), 'Время для звонка участнику')]]]//mat-datepicker-toggle")
    public Button buttonTimeCallParticipant;

    @Element("Кнопка подтвердить автоматическое время")
    @FindBy(xpath = "//button[./span[./*[contains(text(), 'done')]]]")
    public Button buttonDone;

    @Element("Поле ввода Время для звонка участнику")
    @FindBy(xpath = "//div[./div[./span[contains(text(), 'Время для звонка участнику')]]]//mat-form-field//input")
    public TextInput inputDate;

    @Element("Поле ввода Время возврата заявки")
    @FindBy(xpath = "//div[./div[./span[contains(text(), 'Время возврата заявки')]]]//mat-form-field//input")
    public TextInput inputBackDate;

    @Element("Выпадающий список Участник сделки")
    @FindBy(xpath = "//mat-select[contains(@formcontrolname, 'participant')]")
    public Button participantTextInput;

    @Element("Кнопка Отложить заявку")
    @FindBy(xpath = "//button[./span[contains(text(), 'Отложить заявку')]]")
    public Button buttonPostponeRequest;

    @Element("Кнопка Отменить Перевод заявки в отложенные")
    @FindBy(xpath = "//button[./span[contains(text(), 'Отмена')]]")
    public Button buttonCanselPostponeRequest;

    @Element("Кнопка Сохранить и закрыть")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Сохранить и закрыть')]]")
    public Button saveAndExitButton;

    @Element("Кнопка Выйти без сохранения")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Выйти без сохранения')]]")
    public Button exitWithoutSavingButton;

    @Element("Шаг №1")
    @FindBy(xpath = "//span[contains(@class,'ant-steps-icon') and contains(text(),'1')]/..")
    public Button buttonStep1;

    @Element("Шаг №2")
    @FindBy(xpath = "//span[contains(@class,'ant-steps-icon') and contains(text(),'2')]/..")
    public Button buttonStep2;

    @Element("Кнопка ОК на модальном окне Информация об ошибке")
    @FindBy(xpath = "//div[@class='modal-error']//button")
    public Button buttonOkOnModal;

    @Element("Модальное окно Возвращение заявки в очереди")
    @FindBy(xpath = "//div[contains(@class, 'cdk-overlay-pane')]")
    public IFrame modalWindowReturnToQueue;

    @Element("Кнопка Да на модальном окне Возвращение заявки в очереди")
    @FindBy(xpath = "//div[contains(@class, 'cdk-overlay-pane')]//button//span[contains(text(), 'Да')]")
    public Button buttonYesmodalWindowReturnToQueue;

    @Element("Модальное окно Потеря изменений")
    @FindBy(xpath = "//mat-dialog-container")
    public TextBlock changesLostModal;

    @Element("Кнопка Да на модальном окне")
    @FindBy(xpath = "//mat-dialog-container//span[contains(text(), 'Да')]")
    public TextBlock yesOnChangesLostModalButton;

    @Element("Кнопка Нет на модальном окне")
    @FindBy(xpath = "//mat-dialog-container//span[contains(text(), 'Нет')]")
    public TextBlock noOnChangesLostModalButton;


    @Override
    @Step
    @Title("Заполнить поле {title} значением {value}")
    public CardRequestPage fillInput(String title, String value) {
        getElementByTitle(title).sendKeys(value);
        return this;
    }
}
