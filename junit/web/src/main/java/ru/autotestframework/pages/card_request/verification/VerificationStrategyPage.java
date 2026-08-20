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

@PageEntry(title = "Страница Стратегия верификации")
public class VerificationStrategyPage extends BasePage<VerificationStrategyPage> {

    @Element("Поле Наименование стратегии")
    @FindBy(xpath = "//div[@class='top-panel-title']")
    public TextBlock nameOfStrategyTextBlock;

    @Element("Поле Результат проверки - Проверка работадателя")
    @FindBy(xpath = "//div[@class='open-sources-form-title'][contains(text(), 'Проверка работодателя')]")
    public TextBlock employerVerificationTextBlock;

    @Element("Выпадающий список Результат по заявке")
    @FindBy(xpath = "//app-verification-select[@formcontrolname='appResult']//nz-select")
    public Button claimResultDropDown;

    @Element("Выпадающий список Результат проверки")
    @FindBy(xpath = "//app-verification-select[@formcontrolname='result']//nz-select")
    public Button checkResultDropDown;

    @Element("Выпадающий список Виды выявленных расхождений")
    @FindBy(xpath = "//div[contains(text(), 'Виды выявленных расхождений')]/following-sibling::mat-form-field//mat-select")
    public Button typesOfDiscrepanciesIdentifiedDropDown;

    @Element("Поле Предупреждение")
    @FindBy(xpath = "//span[contains(@class, 'previous-applications-form-title-warn ng-star-inserted')]")
    public TextBlock warnTextBlock;

    @Element("Выпадающий список Тип вопроса")
    @FindBy(xpath = "//app-verification-select[@formcontrolname='questionTypeCode']//nz-select")
    public Button questionTypeDropDown;

    @Element("Поле Комментарий")
    @FindBy(xpath = "//div[contains(@class, 'mat-form-field-wrapper')]//textarea")
    public TextInput commentField;

    @Element("Кнопка Далее")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Далее')]]")
    public Button nextButton;

    @Element("Кнопка Завершить проверку")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Завершить проверку')]]")
    public Button endVerificationButton;

    @Element("Поле Проверка работадателя")
    @FindBy(xpath = "//section[contains(@formgroupname, 'employerCheck')]")
    public Button employerCheckField;

    @Element("Модальное окно Заполнение поля")
    @FindBy(xpath = "//div[contains(@class, 'app-documents-error') or @class='modal']")
    public TextBlock modalInfoError;

    @Element("Кнопка Ответ от ГО")
    @FindBy(xpath = "//button/span[contains(text(), 'Ответ от ГО')]")
    public Button responseFromGoButton;

    @Element("Таблица Ответ от ГО")
    @FindBy(xpath = "//app-opm-go-messages-table/table")
    @FindCellsBy(xpath = ".//td[@role='cell']")
    @FindHeadersBy(xpath = ".//th[@role='columnheader']")
    public WebTable responseFromGoTable;

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

    @Element("Кнопка Сохранить на модальном окне история комментариев")
    @FindBy(xpath = "//div[contains(@class,'modal')]//button[./span[normalize-space()='Сохранить']]")
    public Button buttonSaveComment;

    @Element("Запись в истории комментариев")
    @FindBy(xpath = "//span[@class='comment-content']")
    public TextBlock commentContentTextBlock;
}
