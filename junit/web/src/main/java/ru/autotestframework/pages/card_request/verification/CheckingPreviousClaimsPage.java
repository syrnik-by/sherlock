package ru.autotestframework.pages.card_request.verification;

import org.openqa.selenium.support.FindBy;
import ru.autotestframework.pages.BasePage;
import ru.autotestframework.ui_core.page_manager.Element;
import ru.autotestframework.ui_core.page_manager.PageEntry;
import ru.autotestframework.web_elements.elements.Button;
import ru.autotestframework.web_elements.elements.TextBlock;

@PageEntry(title = "Страница Проверка предыдущих заявок")
public class CheckingPreviousClaimsPage extends BasePage<CheckingPreviousClaimsPage> {

    @Element("Поле Наименование стратегии")
    @FindBy(xpath = "//div[@class='top-panel-title']")
    public TextBlock nameOfStrategyTextBlock;

    @Element("Выпадающий список Результат проверки")
    @FindBy(xpath = "//div[text()=' Результат проверки ']/..//nz-select")
    public Button displayByDropDownPostPoned;

    @Element("Выпадающий список Виды выявленных расхождений")
    @FindBy(xpath = "//div[text()='Виды выявленных расхождений']/..//mat-select")
    public Button discrepanciesIdentifiedDropDown;

    @Element("Кнопка Далее")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Далее')]]")
    public Button buttonNext;

    @Element("Кнопка ОК")
    @FindBy(xpath = "//button[.//span[contains(text(), 'ОК') or contains(text(), 'Ок')]]")
    public Button buttonOk;

    @Element("Кнопка Рассчитать")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Рассчитать')]]")
    public Button buttonCalculate;

    @Element("Кнопка Завершить проверку")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Завершить проверку')]]")
    public Button buttonFinishVerification;
}
