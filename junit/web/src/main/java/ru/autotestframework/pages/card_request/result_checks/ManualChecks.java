package ru.autotestframework.pages.card_request.result_checks;

import org.openqa.selenium.support.FindBy;
import ru.autotestframework.pages.BasePage;
import ru.autotestframework.ui_core.page_manager.Element;
import ru.autotestframework.ui_core.page_manager.PageEntry;
import ru.autotestframework.ui_core.services.table_service.FindCellsBy;
import ru.autotestframework.ui_core.services.table_service.FindHeadersBy;
import ru.autotestframework.web_elements.elements.Button;
import ru.autotestframework.web_elements.elements.Link;
import ru.autotestframework.web_elements.elements.WebTable;

@PageEntry(title = "Результаты проверок. Вкладка Ручные проверки")
public class ManualChecks extends BasePage<ManualChecks> {

    @Element("Таблица Верификация назначенные проверки")
    @FindBy(xpath = "//app-verification-assigned-checks")
    @FindCellsBy(xpath = ".//td")
    @FindHeadersBy(xpath = ".//th")
    public WebTable verificationAssignedChecksTable;

    @Element("Кнопка Верификация назначенные проверки")
    @FindBy(xpath = "//mat-panel-title[normalize-space()='Верификация назначенные проверки']")
    public Button verificationAssignedChecksButton;

    @Element("Ссылка Открыть стратегию")
    @FindBy(xpath = "//app-verification-assigned-checks//a[normalize-space()='Открыть стратегию']")
    public Link verificationAssignedChecksLink;

    @Element("Кнопка Верификация рассчитанные проверки")
    @FindBy(xpath = "//mat-panel-title[normalize-space()='Верификация рассчитанные проверки']")
    public Button verificationCalculatedChecksButton;

    @Element("Таблица Верификация рассчитанные проверки")
    @FindBy(xpath = "//app-verification-calculated-checks")
    @FindCellsBy(xpath = ".//td")
    @FindHeadersBy(xpath = ".//th")
    public WebTable verificationCalculatedChecksTable;

}
