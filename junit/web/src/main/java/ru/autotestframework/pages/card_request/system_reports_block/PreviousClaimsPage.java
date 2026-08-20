package ru.autotestframework.pages.card_request.system_reports_block;

import org.openqa.selenium.support.FindBy;
import ru.autotestframework.pages.BasePage;
import ru.autotestframework.ui_core.page_manager.Element;
import ru.autotestframework.ui_core.page_manager.PageEntry;
import ru.autotestframework.ui_core.services.table_service.FindCellsBy;
import ru.autotestframework.ui_core.services.table_service.FindHeadersBy;
import ru.autotestframework.web_elements.elements.WebTable;

@PageEntry(title = "Страница Предыдущие заявки")
public class PreviousClaimsPage extends BasePage<PreviousClaimsPage> {

    @Element("Таблица Предыдущие заявки")
    @FindBy(xpath = "//table[contains(@class, 'mat-table')]")
    @FindCellsBy(xpath = ".//td[@role='cell']")
    @FindHeadersBy(xpath = ".//th[@role='columnheader']/mat-multi-sort-header/div")
    public WebTable previousClaimsTable;
}
