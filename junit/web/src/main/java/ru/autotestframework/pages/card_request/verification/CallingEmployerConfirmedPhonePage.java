package ru.autotestframework.pages.card_request.verification;

import org.openqa.selenium.support.FindBy;
import ru.autotestframework.ui_core.page_manager.Element;
import ru.autotestframework.ui_core.page_manager.PageEntry;
import ru.autotestframework.web_elements.elements.Button;
import ru.autotestframework.web_elements.elements.TextInput;

@PageEntry(title = "Страница Прозвон работодателя - подтвержденный телефон")
public class CallingEmployerConfirmedPhonePage extends CustomerCallPage {

    @Element("Выпадающий список Номер, используемый для звонка")
    @FindBy(xpath = "//div[./span[contains(text(), 'Номер, используемый для звонка')]]//nz-select")
    public TextInput numberUsedCallDropDown;

    @Element("Выпадающий список Предоставлен документ, закрывающий риски")
    @FindBy(xpath = "//app-verification-select[@formcontrolname = 'closedRisksDocument']//nz-select")
    public Button closedRisksDocumentDropDown;

    @Element("Выпадающий список Бесконтактное подтверждение")
    @FindBy(xpath = "//app-verification-select[@formcontrolname = 'contactlessApprove']//nz-select")
    public TextInput contactlessConfirmationDropDown;

    @Element("Выпадающий список Косвенное подтверждение занятости")
    @FindBy(xpath = "//app-verification-select[@formcontrolname = 'indirectConfirm']//nz-select")
    public TextInput indirectConfirmDropDown;

    @Element("Поле ввода Источник подтверждения")
    @FindBy(xpath = "//textarea[@placeholder = 'Источник подтверждения']")
    public TextInput textareaSourceConfirmationTextInput;

}
