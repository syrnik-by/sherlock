package ru.autotestframework.pages.card_request.system_reports_block;

import org.openqa.selenium.support.FindBy;
import ru.autotestframework.pages.BasePage;
import ru.autotestframework.ui_core.page_manager.Element;
import ru.autotestframework.ui_core.page_manager.PageEntry;
import ru.autotestframework.ui_core.services.table_service.FindCellsBy;
import ru.autotestframework.ui_core.services.table_service.FindHeadersBy;
import ru.autotestframework.web_elements.elements.Button;
import ru.autotestframework.web_elements.elements.TextBlock;
import ru.autotestframework.web_elements.elements.TextInput;
import ru.autotestframework.web_elements.elements.WebTable;

@PageEntry(title = "Страница Идеальная КИ")
public class IdealCiPage extends BasePage<IdealCiPage> {

    @Element("Поле Версия КИ")
    @FindBy(xpath = "//span[contains(@class, 'version-title')]")
    public TextBlock versionKiTextBlock;

    @Element("Поле Номер")
    @FindBy(xpath = "//span[contains(@class, 'internal_credit')]")
    public TextBlock recordNumTextBlock;

    @Element("Поле Источник выдачи")
    @FindBy(xpath = "//span[contains(@class, 'ng-star-inserted')][contains(text(), 'Промсвязьбанк')]")
    public TextBlock bankNameTextBlock;

    @Element("Поле Вид кредита")
    @FindBy(xpath = "//span[contains(@class, 'ng-star-inserted')][contains(text(), 'Кредитный договор(розн. кред.)')]")
    public TextBlock loanProgramTypeTextBlock;

    @Element("Поле Отношение к кредиту")
    @FindBy(xpath = "//div[contains(@class, 'data-cell')]//span[contains(@class, 'ng-tns')][contains(text(), 'Заемщик')]")
    public TextBlock borrowerTextBlock;

    @Element("Поле Статус кредита")
    @FindBy(xpath = ".//span[contains(@class, 'ng-tns')][contains(text(), 'Счет закрыт')]")
    public TextBlock statusCreditTextBlock;

    @Element("Поле Сумма кредита")
    @FindBy(xpath = ".//span[contains(@class, 'ng-star-inserted')][contains(text(), '809')]")
    public TextBlock sumCreditTextBlock;

    @Element("Поле Остаток задолженности")
    @FindBy(xpath = ".//span[contains(@class, 'ng-tns')][contains(text(), ' 652,19 ')]")
    public TextBlock remainingDebtTextBlock;

    @Element("Поле Р/$")
    @FindBy(xpath = ".//span[contains(@class, 'ng-tns')][contains(text(), 'RUR')]")
    public TextBlock pTextBlock;

    @Element("Поле Ежемесячный платеж")
    @FindBy(xpath = ".//span[contains(@class, 'ng-tns')][contains(text(), '1456')]")
    public TextBlock monthlyPayTextBlock;

    @Element("Поле Дата получения")
    @FindBy(xpath = ".//span[contains(@class, 'ng-tns')][contains(text(), '07.2019')]")
    public TextBlock startDateTextBlock;

    @Element("Поле Дата факт")
    @FindBy(xpath = "//table//span[contains(text(), 'Промсвязьбанк')]//..//..//..//..//..//input")
    public TextInput factDateTextInput;

    @Element("Поле Дата план")
    @FindBy(xpath = "//span[contains(text(), 'Промсвязьбанк')]//..//..//..//..//..//span[contains(text(), '07.2024')]")
    public TextBlock planDateTextBlock;

    @Element("Поле Дата посл. обнов.")
    @FindBy(xpath = "//span[contains(text(), 'Промсвязьбанк')]//..//..//..//..//..//span[contains(text(), '22.12.2022')]")
    public TextBlock lastUpdateDateTextBlock;

    @Element("Поле Источник")
    @FindBy(xpath = "//span[contains(text(), 'Промсвязьбанк')]//..//..//..//..//..//span[contains(text(), 'SAP')]")
    public TextBlock sourceTextBlock;

    @Element("Поле Сумма рефинансирования")
    @FindBy(xpath = "//span[contains(text(), 'Промсвязьбанк')]//..//..//..//..//..//span[contains(text(), '109')]")
    public TextBlock refinancingAmountTextBlock;

    @Element("Поле Ставка")
    @FindBy(xpath = "//span[contains(text(), 'Промсвязьбанк')]//..//..//..//..//..//span[contains(text(), '20,5')]")
    public TextBlock bidTextBlock;

    @Element("Поле Рефинансирование")
    @FindBy(xpath = "//span[contains(text(), 'Промсвязьбанк')]//..//..//..//..//..//span[contains(text(), 'Не доступен')]")
    public TextBlock refinancingTextBlock;

    @Element("Поле Ставка УСО")
    @FindBy(xpath = "//span[contains(text(), 'Промсвязьбанк')]//..//..//..//..//..//span[contains(text(), '22,5')]")
    public TextBlock bidUsoTextBlock;

    @Element("Поле ПСК (УСО)")
    @FindBy(xpath = "//span[contains(text(), 'Промсвязьбанк')]//..//..//..//..//..//span[contains(text(), ' 15,92 ')]")
    public TextBlock pskUsoTextBlock;

    @Element("Поле Заемщик по кредиту")
    @FindBy(xpath = "//span[contains(text(), 'Промсвязьбанк')]//..//..//..//..//..//span[contains(text(), 'Заявитель')]")
    public TextBlock loanBorrowerTextBlock;

    @Element("Поле Платежи по кредиту")
    @FindBy(xpath = "//span[contains(text(), 'Промсвязьбанк')]//..//..//..//..//..//span[text() = 22]")
    public TextBlock loansTextBlock;

    @Element("Кнопка показать/скрыть столбцы")
    @FindBy(xpath = "//mat-icon[@data-mat-icon-name = 'eye'] | //mat-icon[@data-mat-icon-name = 'eye-closed']")
    public Button buttonColumButton;

    @Element("Кнопка Показать столбцы")
    @FindBy(xpath = "//mat-icon[@data-mat-icon-name = 'eye']")
    public Button buttonShowColumButton;

    @Element("Кнопка Скрыть столбцы")
    @FindBy(xpath = "//mat-icon[@data-mat-icon-name = 'eye-closed']")
    public Button buttonHideColumButton;

    @Element("Кнопка УСО")
    @FindBy(xpath = "//mat-chip-list//mat-chip")
    public Button usoButton;

    @Element("Поле Ввода редактирования Ежемесячного платежа")
    @FindBy(xpath = "//input[@psbselect]")
    public TextInput textInput;

    @Element("Таблица Участники сделки")
    @FindBy(xpath = "//table[@role='table']")
    @FindCellsBy(xpath = ".//td[@role='cell' and not(@colspan)]/*")
    @FindHeadersBy(xpath = ".//th[@role='columnheader']//button")
    public WebTable transactionsParticipantsTable;

    @Element("Таблица Платежи по кредиту")
    @FindBy(xpath = "//div[contains(@class,'detailExpand')]//table")
    @FindCellsBy(xpath = ".//td")
    @FindHeadersBy(xpath = ".//th")
    public WebTable loanPaymentsTable;
}
