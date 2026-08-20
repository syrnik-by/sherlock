package ru.autotestframework.pages.card_request.verification;

import org.openqa.selenium.support.FindBy;
import ru.autotestframework.ui_core.page_manager.Element;
import ru.autotestframework.ui_core.page_manager.PageEntry;
import ru.autotestframework.web_elements.elements.Button;
import ru.autotestframework.web_elements.elements.TextInput;

@PageEntry(title = "Страница Прозвон работодателя - подтвержденный телефон (обязательный)")

public class CallingEmployerConfirmedPhoneRequiredPage extends CustomerCallPage {

    @Element("Выпадающий список Номер, используемый для звонка")
    @FindBy(xpath = "//div[./span[contains(text(), 'Номер, используемый для звонка')]]//nz-select")
    public TextInput numberUsedCallDropDown;

    @Element("Выпадающий список Предоставлен документ, закрывающий риски")
    @FindBy(xpath = "//app-verification-select[@formcontrolname = 'closedRisksDocument']//nz-select")
    public Button closedRisksDocumentDropDown;

    @Element("Выпадающий список Дополнительное поле результата проверки")
    @FindBy(xpath = "//app-verification-select[2]//nz-select")
    public Button addFieldResultCheckDropDown;

    @Element("Выпадающий список Дополнительное поле результата проверки 2")
    @FindBy(xpath = "//div[contains(@class,'ng-star-inserted')]//mat-select")
    public Button addFieldResultCheck2DropDown;

    @Element("Кнопка Сохранить")
    @FindBy(xpath = "//button[span[text() = ' Сохранить ']]")
    public Button saveButton;

}
