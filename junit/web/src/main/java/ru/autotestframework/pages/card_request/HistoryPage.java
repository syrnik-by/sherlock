package ru.autotestframework.pages.card_request;

import org.openqa.selenium.support.FindBy;
import ru.autotestframework.pages.BasePage;
import ru.autotestframework.ui_core.page_manager.Element;
import ru.autotestframework.ui_core.page_manager.PageEntry;
import ru.autotestframework.ui_core.services.table_service.FindCellsBy;
import ru.autotestframework.ui_core.services.table_service.FindHeadersBy;
import ru.autotestframework.web_elements.elements.Button;
import ru.autotestframework.web_elements.elements.WebTable;

@PageEntry(title = "Страница История")
public class HistoryPage extends BasePage<HistoryPage> {

    @Element("Таблица История заявки")
    @FindBy(xpath = "//table")
    @FindCellsBy(xpath = ".//td[@role='cell']/div")
    @FindHeadersBy(xpath = ".//th[@role='columnheader']//button")
    public WebTable requestHistoryTable;

    @Element("Таблица Версии заявки")
    @FindBy(xpath = "//table")
    @FindCellsBy(xpath = ".//td[@role='cell']/div")
    @FindHeadersBy(xpath = ".//th[@role='columnheader']//button")
    public WebTable requestVersionTable;

    @Element("Вкладка История заявки")
    @FindBy(xpath = "//div[.//span[contains(text(), 'История заявки')]][@role='tab']")
    public Button buttonHistoryApplication;

    @Element("Вкладка Версии заявки")
    @FindBy(xpath = "//div[.//span[contains(text(), 'Версии заявки')]][@role='tab']")
    public Button buttonVersionApplication;

    @Element("Выпадающий список Отображать по")
    @FindBy(xpath = "//nz-select")
    public Button displayByDropDown;

    @Element("Кнопка Предыдущая страница таблицы")
    @FindBy(xpath = "//i[@nztype='left']")
    public Button previousTablePageButton;

    @Element("Кнопка Следующая страница таблицы")
    @FindBy(xpath = "//i[@nztype='right']")
    public Button nextTablePageButton;
}
