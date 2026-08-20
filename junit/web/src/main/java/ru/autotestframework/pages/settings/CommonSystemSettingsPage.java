package ru.autotestframework.pages.settings;

import org.openqa.selenium.support.FindBy;
import org.springframework.stereotype.Component;
import ru.autotestframework.pages.BasePage;
import ru.autotestframework.ui_core.page_manager.Element;
import ru.autotestframework.ui_core.page_manager.PageEntry;
import ru.autotestframework.web_elements.elements.Button;
import ru.autotestframework.web_elements.elements.TextInput;

@Component
@PageEntry(title = "Страница Общие настройки системы")
public class CommonSystemSettingsPage extends BasePage<CommonSystemSettingsPage> {

    // Кнопки

    @Element("Кнопка Сохранить")
    @FindBy(xpath = "//span[normalize-space()='Сохранить']")
    public Button saveButton;

    // Блок Автоматическое назначение и переназначение

    @Element("Поле ввода Максимальное количество заявок в личной очереди, при котором кнопка «Новая заявка» должна быть заблокирована")
    @FindBy(xpath = "//div[contains(@class, 'settings-container') and (.//span[text()='Максимальное количество заявок в личной очереди, при котором кнопка «Новая заявка» должна быть заблокирована'])]//input")
    public TextInput maximumAmountOfClaimsInPersonalQueueTextInput;

    // Блок Очередь отложенных

    @Element("Поле ввода Количество минут для автоматического возврата отложенной заявки")
    @FindBy(xpath = "//div[contains(@class, 'settings-container') and (.//span[text()='Количество минут для автоматического возврата отложенной заявки'])]//input")
    public TextInput amountOfMinutesForAutomaticReturnPostponedClaimTextInput;

    @Element("Поле ввода Напомнить о звонке за (минут)")
    @FindBy(xpath = "//div[contains(@class, 'settings-container') and (.//span[text()='Напомнить о звонке за (минут)'])]//input")
    public TextInput remindOfCallTextInput;
}
