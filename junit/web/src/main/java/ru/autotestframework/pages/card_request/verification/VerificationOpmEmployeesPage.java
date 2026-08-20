package ru.autotestframework.pages.card_request.verification;

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

@PageEntry(title = "Проверка сотрудниками ОПМ")
public class VerificationOpmEmployeesPage extends BasePage<VerificationOpmEmployeesPage> {

    @Element("Поле Наименование стратегии")
    @FindBy(xpath = "//div[@class='top-panel-title']")
    public TextBlock nameOfStrategyTextBlock;

    @Element("Таблица Сообщений ОПМ")
    @FindBy(xpath = "//app-opm-go-messages-table")
    @FindCellsBy(xpath = ".//td")
    @FindHeadersBy(xpath = ".//th")
    public WebTable opmMessagesTable;

    @Element("Список Дополнительных проверок")
    @FindBy(xpath = "//app-additional-check-list-tree")
    public TextBlock checkListTextBlock;

    @Element("Выпадающий список Результат проверки")
    @FindBy(xpath = "//app-verification-select[@formcontrolname = 'result']//nz-select")
    public Button displayByDropDownPostPoned;

    @Element("Поле Комментарий ОПМ")
    @FindBy(xpath = "//app-textarea[@formcontrolname = 'commentIntOPM']//textarea")
    public TextInput commentIntOPM;
}
