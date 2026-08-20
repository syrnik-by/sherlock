package ru.autotestframework.pages.components;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import org.openqa.selenium.support.FindBy;
import ru.autotestframework.pages.BasePage;
import ru.autotestframework.ui_core.page_manager.Element;
import ru.autotestframework.web_elements.elements.Button;
import ru.autotestframework.web_elements.elements.Image;
import ru.autotestframework.web_elements.elements.TextBlock;
import ru.autotestframework.web_elements.elements.TextInput;
import ru.psb.testit.annotations.Step;
import ru.psb.testit.annotations.Title;

import static com.codeborne.selenide.Selenide.$$x;
import static ru.autotestframework.utils.Constants.BODY;

public class Notifications<T extends BasePage<T>> extends BasePage<T> {

    @Element("Количество уведомлений")
    @FindBy(xpath = "//nz-badge-sup")
    public TextBlock notificationCountTextBlock;

    @Element("Кнопка Не показывать все звонки")
    @FindBy(xpath = "//span[normalize-space()='Не показывать все звонки']")
    public Button hideAllCallsButton;

    @Element("Кнопка Уведомления")
    @FindBy(xpath = "//span[normalize-space()='Уведомления']")
    public Button notificationButton;

    @Element("Кнопка Не показывать все уведомления")
    @FindBy(xpath = "//span[normalize-space()='Не показывать все уведомления']")
    public Button hideAllNotificationsButton;

    @Element("Модальное окно Уведомление")
    @FindBy(xpath = "//app-notification-card//div//span[contains(text(), 'Уведомление')]")
    public TextBlock modalInfoNotification;

    @Element("Модальное окно Информация об ошибке")
    @FindBy(xpath = "//div[contains(@class, 'modal-error')]//div[contains(text(), 'Информация об ошибке')]")
    public TextBlock modalInfoError;

    @Element("Кнопка Ок на модальном окне")
    @FindBy(xpath = "//div[contains(@class, 'modal-error')]//button/span[contains(text(), 'Ок')]")
    public Button buttonOkModalInfoError;

    @Element("Модальное окно Напоминание о предстоящем звонке")
    @FindBy(xpath = "//app-notification-card[//span[contains(text(), 'Напоминание о предстоящем звонке')]]")
    public TextBlock modalReminderUpcomingCall;

    @Element("Описание окна Напоминание о предстоящем звонке")
    @FindBy(xpath = "//app-notification-card[//span[contains(text(), 'Напоминание о предстоящем звонке')]]//div[@class = 'notification-info']")
    public TextBlock descriptionModalReminderUpcomingCall;

    @Element("Описание окна Напоминание о предстоящем звонке (В разделе Уведомлений)")
    @FindBy(xpath = "//div[contains(@class, 'notification-container')]//app-notification-card[//span[contains(text(), 'Напоминание о предстоящем звонке')]]//div[@class = 'notification-info']")
    public TextBlock descriptionOnBellModalReminderUpcomingCall;

    @Element("Описание окна Уведомление (В разделе Уведомлений)")
    @FindBy(xpath = "//app-notification-card[//span[contains(text(), 'Уведомление')]]//div[@class = 'notification-info']")
    public TextBlock descriptionOnBellModalNotification;


    @Element("Кнопка ОК - Модальное окно Напоминание о предстоящем звонке")
    @FindBy(xpath = "//span[contains(text(), 'ОК')]")
    public Button buttonOK;

    @Element("Кнопка ОК - Модальное окно Уведомление")
    @FindBy(xpath = "//app-notification-card[//div//span[contains(text(), 'Уведомление')]]//button[span[contains(text(), 'ОК')]]")
    public Button buttonOKNotification;

    @Element("Кнопка Закрыть модальное окно Напоминание о предстоящем звонке")
    @FindBy(xpath = "//div[@class='notification']//i[@nztype='close']")
    public Button buttonCloseNotification;

    @Element("Кнопка Закрыть модальное окно (В Разделе уведомления)")
    @FindBy(xpath = "//div[contains(@class, '-notification-lists')]//div[@class='notification']//i[@nztype='close']")
    public Button buttonCloseWindowNotification;

    @Element("Кнопка Закрыть модальное окно Уведомление")
    @FindBy(xpath = "//div[@class='notification']//i[@nztype='close']")
    public Button buttonClose;

    @Element("Поле ввода Номер заявки")
    @FindBy(xpath = "//mat-form-field[.//psb-text[contains(text(), 'Номер заявки')]]//input")
    public TextInput requestNumberTextInput;

    @Element("Кнопка колокол")
    @FindBy(xpath = "//nz-badge")
    public Button bellButton;

    @Element("Иконка Колокольчик на модальном окне Напоминание о предстоящем звонке")
    @FindBy(xpath = "//app-notification-card//*[@data-icon='bell']")
    public Image bellIcon;

    @Element("Поле Номер заявки")
    @FindBy(xpath = "//div[contains(@class, 'notification')]//a")
    public TextBlock claimTextInput;

    @Element("Поле Номер заявки (В разделе Уведомлений)")
    @FindBy(xpath = "//div[contains(@class, 'notification-container')]//div[contains(@class, 'notification')]//a")
    public TextBlock claimBellTextInput;

    @Element("Раздел Звонки")
    @FindBy(xpath = "//span[text() = ' Звонки ']")
    public Button callButton;

    @Element("Раздел Уведомления")
    @FindBy(xpath = "//span[text() = ' Уведомления ']")
    public Button notificationsButton;

    @Step
    @Title("Проверка появления модального окна с информацией об ошибке поиска")
    public T checkModal() {
        sleep(1);
        if (modalInfoError.isDisplayed()) {
            buttonOkModalInfoError.click();
            requestNumberTextInput.clear();
        }
        return getSelf();
    }

    private void checkCloseButton() {
        while (buttonCloseNotification.isDisplayed()) {
            buttonCloseNotification.click();
        }
    }

    private int getAmountOfNotifications() {
        ElementsCollection allNotifications = $$x("//div[@class='notification-list']/app-notification-card");
        return allNotifications.size();
    }

    public T checkNotifications() {
        if (notificationCountTextBlock.isDisplayed()) {
            checkCloseButton();
            checkModal();
            bellButton.click();
            if (hideAllCallsButton.isDisplayed() && getAmountOfNotifications() != 0) {
                hideAllCallsButton.click();
            } else {
                notificationButton.click();
                hideAllNotificationsButton.click();
            }
            BODY.click();
        }
        checkCloseButton();
        return getSelf();
    }
}
