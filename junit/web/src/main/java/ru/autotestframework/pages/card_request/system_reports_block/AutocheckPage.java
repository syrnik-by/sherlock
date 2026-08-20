package ru.autotestframework.pages.card_request.system_reports_block;

import org.openqa.selenium.support.FindBy;
import ru.autotestframework.pages.BasePage;
import ru.autotestframework.ui_core.page_manager.Element;
import ru.autotestframework.ui_core.page_manager.PageEntry;
import ru.autotestframework.web_elements.elements.TextBlock;

import java.util.List;

@PageEntry(title = "Страница Автопроверки")
public class AutocheckPage extends BasePage<AutocheckPage> {

    @Element("Поле Сегмент клиента")
    @FindBy(xpath = "//div[./span[contains(text(), 'Сегмент клиента')]]/following-sibling::div")
    public TextBlock clientSegmentTextBlock;

    @Element("Поле Статус клиента")
    @FindBy(xpath = "//div[@class='autochecks-rcc-body-content-table-item'][.//span[contains(text(),'Статус клиента')]]/div[2]")
    public TextBlock clientStatusTextBlock;

    @Element("Поле Форма подтверждения дохода")
    @FindBy(xpath = "//div[@class='autochecks-rcc-body-content-table-item'][.//span[contains(text(),'Форма подтверждения дохода')]]/div[2]")
    public TextBlock incomeConfirmationFormTextBlock;

    @Element("Поле Источник поступления заявки")
    @FindBy(xpath = "//div[@class='autochecks-rcc-body-content-table-item'][.//span[contains(text(),'Источник поступления заявки')]]/div[2]")
    public TextBlock requestSourceTextBlock;

    @Element("Поле Критерии отправки на серую зону")
    @FindBy(xpath = "//div[@class='autochecks-rcc-body-content-table-item'][.//span[contains(text(),'Критерии отправки на серую зону')]]/div[2]")
    public TextBlock grayZoneTextBlock;

    @Element("Список Критерии отправки на серую зону")
    @FindBy(xpath = "//span[normalize-space()='Критерии отправки на серую зону']/../following-sibling::div/div")
    public List<TextBlock> grayZoneList;

    @Element("Поле ИНН работодателя (основное место работы)")
    @FindBy(xpath = "//div[@class='autochecks-rcc-body-content-table-item'][.//span[contains(text(),'ИНН работодателя (основное место работы)')]]/div[2]")
    public TextBlock employerTinTextBlock;
}
