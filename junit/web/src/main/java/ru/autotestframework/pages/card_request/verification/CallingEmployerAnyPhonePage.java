package ru.autotestframework.pages.card_request.verification;

import org.openqa.selenium.support.FindBy;
import ru.autotestframework.ui_core.page_manager.Element;
import ru.autotestframework.ui_core.page_manager.PageEntry;
import ru.autotestframework.web_elements.elements.Button;
import ru.autotestframework.web_elements.elements.TextInput;

@PageEntry(title = "Страница Прозвон работодателя - любой телефон")
public class CallingEmployerAnyPhonePage extends CustomerCallPage {

    @Element("Выпадающий список Номер, используемый для звонка")
    @FindBy(xpath = "//div[./span[contains(text(), 'Номер, используемый для звонка')]]//nz-select")
    public TextInput numberUsedCallDropDown;

    @Element("Выпадающий список Предоставлен документ, закрывающий риски")
    @FindBy(xpath = "//app-verification-select[@formcontrolname = 'closedRisksDocument']//nz-select")
    public Button closedRisksDocumentDropDown;

    @Element("Кнопка Сохранить")
    @FindBy(xpath = "//button[span[text() = ' Сохранить ']]")
    public Button saveButton;

}
