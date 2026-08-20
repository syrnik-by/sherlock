package ru.autotestframework.pages.personal_account;

import org.openqa.selenium.support.FindBy;
import ru.autotestframework.pages.components.TopBar;
import ru.autotestframework.ui_core.page_manager.Element;
import ru.autotestframework.ui_core.page_manager.PageEntry;
import ru.autotestframework.ui_core.services.table_service.FindCellsBy;
import ru.autotestframework.ui_core.services.table_service.FindHeadersBy;
import ru.autotestframework.web_elements.elements.Button;
import ru.autotestframework.web_elements.elements.ClassicCheckBox;
import ru.autotestframework.web_elements.elements.TextBlock;
import ru.autotestframework.web_elements.elements.WebTable;

@PageEntry(title = "Страница очередь заявок")
public class QueueRequestsPage extends TopBar<QueueRequestsPage> {

    @Element("Кнопка Сбросить сортировку(таблица В работе)")
    @FindBy(xpath = "//app-in-progress//button[.//span[contains(text(), 'Сбросить сортировку')]]")
    public Button buttonSkipSortProgress;

    @Element("Switcher Новая заявка")
    @FindBy(xpath = "//span[contains(text(), 'Новая заявка')]/..//button")
    public Button switcherNewApplication;

    @Element("Раздел Утверждение")
    @FindBy(xpath = "//div[contains(@class, personal-area-bottom-controls-item)]/span[contains(text(), ' Утверждение')]")
    public TextBlock statement;

    @Element("Раздел Андеррайтинг")
    @FindBy(xpath = "//div[contains(@class, personal-area-bottom-controls-item)]/span[contains(text(), 'Андеррайтинг') and not(contains(text(),'Андеррайтинг ГО'))]")
    public TextBlock underwriter;

    @Element("Чек-бокс ЦСКО")
    @FindBy(xpath = "//span[contains(text(), 'ЦСКО')]/..//..//label")
    public ClassicCheckBox checkBoxTssko;

    @Element("Чек-бокс ГО")
    @FindBy(xpath = "//span[contains(text(), 'ГО')]/..//..//label")
    public ClassicCheckBox checkBoxGo;

    @Element("Чек-бокс Сортировка по приоритетам")
    @FindBy(xpath = "//span[contains(text(), 'Сортировка по приоритетам')]/..//..//label")
    public ClassicCheckBox checkBoxSortByPriority;

    @Element("Табличный блок В работе")
    @FindBy(xpath = "//app-personal-area-lists-container//app-in-progress")
    public TextBlock blockInWork;

    @Element("Кнопка Настройка списка(таблица В работе)")
    @FindBy(xpath = "//app-in-progress//button[.//span[contains(text(), 'Настройка списка')]]")
    public Button buttonSettingListProgress;

    @Element("Кнопка выбора количества записей(таблица В работе)")
    @FindBy(xpath = "//app-in-progress//nz-select")
    public Button buttonAmountRecordProgress;

    @Element("Кнопка предыдущая страница(таблица В работе)")
    @FindBy(xpath = "//app-in-progress//i[@nztype='left']")
    public Button buttonPreviousPageProgress;

    @Element("Кнопка следующая страница(таблица В работе)")
    @FindBy(xpath = "//app-in-progress//i[@nztype='right']")
    public Button buttonNextPageProgress;

    @Element("Столбец Номер заявки(таблица В работе)")
    @FindBy(xpath = "//app-in-progress//div[contains(@class,'table-heading-container')][./span[contains(text(), 'Номер заявки')]]")
    public TextBlock columnNumberApplicationProgress;

    @Element("Столбец Время попадания на РП(таблица В работе)")
    @FindBy(xpath = "//app-in-progress//div[contains(@class,'table-heading-container')][./span[contains(text(), 'Время попадания на РП')]]")
    public TextBlock columnRpProgress;

    @Element("Столбец ФИО заемщика(таблица В работе)")
    @FindBy(xpath = "//app-in-progress//div[contains(@class,'table-heading-container')][./span[contains(text(), 'ФИО заемщика')]]")
    public TextBlock columnApplicantFullNameProgress;

    @Element("Столбец Сумма кредита(таблица В работе)")
    @FindBy(xpath = "//app-in-progress//div[contains(@class,'table-heading-container')][./span[contains(text(), 'Сумма кредита')]]")
    public TextBlock columnLoanSumProgress;

    @Element("Столбец Вид кредита(таблица В работе)")
    @FindBy(xpath = "//app-in-progress//div[contains(@class,'table-heading-container')][./span[contains(text(), 'Вид кредита')]]")
    public TextBlock columnLoanProgramProgress;

    @Element("Столбец Тип заявки(таблица В работе)")
    @FindBy(xpath = "//app-in-progress//div[contains(@class,'table-heading-container')][./span[contains(text(), 'Тип заявки')]]")
    public TextBlock columnTypeApplicationProgress;

    @Element("Столбец Программа кредитования(таблица В работе)")
    @FindBy(xpath = "//app-in-progress//div[contains(@class,'table-heading-container')][./span[contains(text(), 'Программа кредитования')]]")
    public TextBlock columnCreditProgramProgress;

    @Element("Столбец Статус заявки(таблица В работе)")
    @FindBy(xpath = "//app-in-progress//div[contains(@class,'table-heading-container')][./span[contains(text(), 'Статус заявки')]]")
    public TextBlock columnStatusApplicationProgress;

    @Element("Столбец Предыдущий статус(таблица В работе)")
    @FindBy(xpath = "//app-in-progress//div[contains(@class,'table-heading-container')][./span[contains(text(), 'Предыдущий статус')]]")
    public TextBlock columnPreviousStatusProgress;

    @Element("Табличный блок Отложено")
    @FindBy(xpath = "//app-personal-area-lists-container//app-postponed")
    public TextBlock blockInPostponed;

    @Element("Список блока Отложено")
    @FindBy(xpath = "//app-postponed//div[contains(@class, 'ant-collapse-header')]/i")
    public Button listBlockInPostponed;

    @Element("Таблица отложенных заявок")
    @FindBy(xpath = "//app-personal-area-lists-container//app-postponed//table")
    public TextBlock tableApplicationInPostponed;

    @Element("Кнопка Сбросить сортировку(таблица Отложено)")
    @FindBy(xpath = "//app-postponed//button[.//span[contains(text(), 'Сбросить сортировку')]]")
    public Button buttonSkipSortPostponed;

    @Element("Кнопка Настройка списка(таблица Отложено)")
    @FindBy(xpath = "//app-postponed//button[.//span[contains(text(), 'Настройка списка')]]")
    public Button buttonSettingListPostponed;

    @Element("Кнопка выбора количества записей(таблица Отложено)")
    @FindBy(xpath = "//app-postponed//nz-select")
    public Button buttonAmountRecordPostponed;

    @Element("Кнопка предыдущая страница(таблица Отложено)")
    @FindBy(xpath = "//app-postponed//i[@nztype='left']")
    public Button buttonPreviousPagePostponed;

    @Element("Кнопка следующая страница(таблица Отложено)")
    @FindBy(xpath = "//app-postponed//i[@nztype='right']")
    public Button buttonNextPagePostponed;

    @Element("Столбец Номер заявки(таблица Отложено)")
    @FindBy(xpath = "//app-postponed//div[contains(@class,'table-heading-container')][./span[contains(text(), 'Номер заявки')]]")
    public TextBlock columnNumberApplicationPostponed;

    @Element("Столбец Время попадания на РП(таблица Отложено)")
    @FindBy(xpath = "//app-postponed//div[contains(@class,'table-heading-container')][./span[contains(text(), 'Время попадания на РП')]]")
    public TextBlock columnRpPostponed;

    @Element("Столбец ФИО заемщика(таблица Отложено)")
    @FindBy(xpath = "//app-postponed//div[contains(@class,'table-heading-container')][./span[contains(text(), 'ФИО заемщика')]]")
    public TextBlock columnApplicantFullNamePostponed;

    @Element("Столбец Сумма кредита(таблица Отложено)")
    @FindBy(xpath = "//app-postponed//div[contains(@class,'table-heading-container')][./span[contains(text(), 'Cумма кредита')]]")
    public TextBlock columnLoanSumPostponed;

    @Element("Столбец Вид кредита(таблица Отложено)")
    @FindBy(xpath = "//app-postponed//div[contains(@class,'table-heading-container')][./span[contains(text(), 'Вид кредита')]]")
    public TextBlock columnLoanProgramPostponed;

    @Element("Столбец Тип заявки(таблица Отложено)")
    @FindBy(xpath = "//app-postponed//div[contains(@class,'table-heading-container')][./span[contains(text(), 'Тип заявки')]]")
    public TextBlock columnTypeApplicationPostponed;

    @Element("Столбец Программа кредитования(таблица Отложено)")
    @FindBy(xpath = "//app-postponed//div[contains(@class,'table-heading-container')][./span[contains(text(), 'Программа кредитования')]]")
    public TextBlock columnCreditProgramPostponed;

    @Element("Столбец Статус заявки(таблица Отложено)")
    @FindBy(xpath = "//app-postponed//div[contains(@class,'table-heading-container')][./span[contains(text(), 'Статус заявки')]]")
    public TextBlock columnStatusApplicationPostponed;

    @Element("Столбец Предыдущий статус(таблица Отложено)")
    @FindBy(xpath = "//app-postponed//div[contains(@class,'table-heading-container')][./span[contains(text(), 'Предыдущий статус')]]")
    public TextBlock columnPreviousStatusPostponed;

    @Element("Таблица в работе")
    @FindBy(xpath = "//table")
    @FindCellsBy(xpath = ".//td[@role='cell']")
    @FindHeadersBy(xpath = ".//th[@role='columnheader']")
    public WebTable prioritySettingsTable;

}
