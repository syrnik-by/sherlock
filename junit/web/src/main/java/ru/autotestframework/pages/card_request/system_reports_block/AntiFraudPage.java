package ru.autotestframework.pages.card_request.system_reports_block;

import org.openqa.selenium.support.FindBy;
import ru.autotestframework.pages.BasePage;
import ru.autotestframework.ui_core.page_manager.Element;
import ru.autotestframework.ui_core.page_manager.PageEntry;
import ru.autotestframework.ui_core.services.table_service.FindCellsBy;
import ru.autotestframework.ui_core.services.table_service.FindHeadersBy;
import ru.autotestframework.web_elements.elements.WebTable;

@PageEntry(title = "Страница Antifraud")
public class AntiFraudPage extends BasePage<AntiFraudPage> {

    @Element("Таблица Antifraud")
    @FindBy(xpath = "//table[@role='table']")
    @FindCellsBy(xpath = ".//td[@role='cell' and not(@colspan)]/*")
    @FindHeadersBy(xpath = ".//th[@role='columnheader']//button")
    public WebTable antiFraudTable;

}
