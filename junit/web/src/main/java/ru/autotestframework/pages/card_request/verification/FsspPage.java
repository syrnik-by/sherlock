package ru.autotestframework.pages.card_request.verification;

import org.openqa.selenium.support.FindBy;
import ru.autotestframework.pages.BasePage;
import ru.autotestframework.ui_core.page_manager.Element;
import ru.autotestframework.ui_core.page_manager.PageEntry;
import ru.autotestframework.web_elements.elements.*;

@PageEntry(title = "ЭФ ФССП")
public class FsspPage extends BasePage<FsspPage> {

    @Element("Поле Наименование стратегии")
    @FindBy(xpath = "//div[@class='top-panel-title']")
    public TextBlock nameOfStrategyTextBlock;

    @Element("Выпадающий список Результат проверки")
    @FindBy(xpath = "//div[text()='Результат проверки']/..//nz-select")
    public Button displayByDropDownPostPoned;

    @Element("Кнопка Взять шаг в работу")
    @FindBy(xpath = "//button[span[contains(text(), 'Взять шаг в работу')]]")
    public Button takeStepIntoWorkButton;

    @Element("Выпадающий список Результат по заявке")
    @FindBy(xpath = "//div[./div[contains(text(), 'Результат по заявке')]]//nz-select")
    public Button resultApplicationButton;

    @Element("Выпадающий список Тип одобрения")
    @FindBy(xpath = "//div[./div[contains(text(), 'Тип одобрения')]]//nz-select")
    public Button typeForApprovalButton;

    @Element("Выпадающий список Тип вопроса")
    @FindBy(xpath = "//div[./div[contains(text(), 'Тип вопроса')]]//nz-select")
    public Button typeForQuestionButton;

    @Element("Выпадающий список Причина отклонения")
    @FindBy(xpath = "//div[./div[contains(text(), 'Причина отклонения')]]//nz-select")
    public Button reasonForRefusalButton;

    @Element("Чек-бокс Активные ИП по кредитным платежам, алиментам (свыше 1000 р.), уголовным штрафам")
    @FindBy(xpath = "//span[contains(text(),'Активные ИП по кредитным платежам, алиментам (свыше 1000 р.), уголовным штрафам')]/../span[@class='mat-checkbox-inner-container']")
    public ClassicCheckBox activeIpCheckBox;

    @Element("Чек-бокс Закрытые по ст.46 ИП по кредитным платежам, алиментам (свыше 1000 р.), уголовным штрафам")
    @FindBy(xpath = "//span[contains(text(),'Закрытые по ст.46 ИП по кредитным платежам, алиментам (свыше 1000 р.), уголовным штрафам')]/../span[@class='mat-checkbox-inner-container']")
    public ClassicCheckBox closedIpCheckBox;

    @Element("Чек-бокс Прочие активные ИП")
    @FindBy(xpath = "//span[contains(text(),'Прочие активные ИП')]/../span[@class='mat-checkbox-inner-container']")
    public ClassicCheckBox activeIpOtherCheckBox;

    @Element("Чек-бокс Закрытые ИП по кредитным платежам")
    @FindBy(xpath = "//span[contains(text(),'Закрытые ИП по кредитным платежам')]/../span[@class='mat-checkbox-inner-container']")
    public ClassicCheckBox closedIpCreditPaymentsCheckBox;

    @Element("Чек-бокс Закрытые ИП по статье 47 (банкротство)")
    @FindBy(xpath = "//span[contains(text(),'Закрытые ИП по статье 47 (банкротство)')]/../span[@class='mat-checkbox-inner-container']")
    public ClassicCheckBox closedIpBankruptcyCheckBox;

    @Element("Поле ввода Сумма действующего ИП")
    @FindBy(xpath = "//label[text()='Сумма действующего ИП']/../input")
    public TextInput sumOfCurrentTextInput;

    @Element("Кнопка Далее")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Далее')]]")
    public Button buttonNext;

    @Element("Кнопка Завершить проверку")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Завершить проверку')]]")
    public Button buttonFinishCheck;

    @Element("Иконка статуса Проверка заверешена")
    @FindBy(xpath = "//*[local-name()='svg' and @data-icon='check']")
    public Image iconStatusFinishCheck;

    @Element("Кнопка ОК")
    @FindBy(xpath = "//button[.//span[contains(text(), 'ОК') or contains(text(), 'Ок')]]")
    public Button buttonOk;

    @Element("Шаг №2. Созаемщик")
    @FindBy(xpath = "//span[contains(@class,'ant-steps-icon') and contains(text(),'2')]/..")
    public Button buttonStep2;

    @Element("Иконка Шаг заблокирован")
    @FindBy(xpath = "//div[contains(@class, 'blocked')]//span[text() = 'Заблокирован']")
    public Button blockedButton;

    @Element("Кнопка Основные данные")
    @FindBy(xpath = "//app-main-data-button/a[contains(text(), 'Основные данные')]")
    public Button basicDataButton;

    @Element("Кнопка Вернуть в очередь")
    @FindBy(xpath = "//div[contains(@class, 'header')]//button[.//span[contains(text(), 'Вернуть в очередь')]]")
    public Button returnToQueueButton;

    @Element("Поле ввода Внутренний комментарий")
    @FindBy(xpath = "//div[contains(text(), 'Внутренний комментарий')]//..//textarea")
    public TextInput textareaCommentTextInput;

    @Element("Поле ввода Комментарий")
    @FindBy(xpath = "//div[contains(text(), 'Комментарий')]//..//textarea")
    public TextInput commentTextInput;

    @Element("Выпадающий список Тип одобрения/Причина отклонения")
    @FindBy(xpath = "//div[./p[contains(text(), 'Тип одобрения/Причина отклонения')]]//mat-select")
    public Button typeApprovalReasonRejectionDropDown;

    @Element("Выпадающий список Причина доработки")
    @FindBy(xpath = "//app-verification-select[@formcontrolname='revisionReason']//nz-select")
    public Button listReasonForRevisionVerificationButton;

    @Element("Поле ввода Комментарий для МРК")
    @FindBy(xpath = "//div[contains(text(), 'Комментарий для МРК')]//..//textarea")
    public TextInput commentMRKTextInput;

    @Element("Выпадающий список История версий")
    @FindBy(xpath = "//button[.//span[contains(text(), 'История версий')]]")
    public Button dropDownHistoryOfVersions;

    @Element("Поле Комментарий")
    @FindBy(xpath = "//div[contains(@class, 'mat-form-field-wrapper')]//textarea")
    public TextInput commentField;

    @Element("Поле ввода Скоррект. доход/По Осн. месту")
    @FindBy(xpath = "//div[span[contains(text(), 'По осн. месту')]]/following-sibling::mat-form-field//div//input[not(@value)]")
    public TextInput correctTextInput;

    @Element("Иконка для активации поля ввода Скоррект. доход/По Осн. месту")
    @FindBy(xpath = "//div[span[contains(text(), 'По осн. месту')]]/following-sibling::mat-form-field//div//mat-icon")
    public TextInput iconCorrectTextInput;

    @Element("Кнопка Сохранить (header)")
    @FindBy(xpath = "//div[contains(@class, 'header')]//button[.//span[contains(text(), 'Сохранить')]]")
    public Button saveButtonHeader;

    @Element("Кнопка Закрыть")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Закрыть')]]")
    public Button buttonClose;

    //Кнопка внутренний комментарий

    @Element("Окно внутренних комментариев")
    @FindBy(xpath = "//div[contains(@class, 'ant-modal-content')]")
    public Button windowInternalComment;

    @Element("Кнопка закрыть Окно")
    @FindBy(xpath = "//button[@aria-label = 'Close']")
    public Button buttonCloseWindow;

    @Element("Кнопка Внутренний комментарий")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Внутренний комментарий')]]")
    public Button buttonInternalComment;

    @Element("Поле ввода История комментариев")
    @FindBy(xpath = "//textarea[@formcontrolname='comment']")
    public TextInput historyComment;

    @Element("Поле История комментариев")
    @FindBy(xpath = "//div[@class= 'ng-star-inserted']//textarea")
    public TextBlock commentHistoryTextBlock;

    @Element("Поле История комментариев (все)")
    @FindBy(xpath = "//div[@class= 'ng-star-inserted'][./div[text()='История комментариев']]")
    public TextBlock commentHistoryAllTextBlock;


    @Element("Кнопка Сохранить на модальном окне история комментариев")
    @FindBy(xpath = "//div[contains(@class,'modal')]//button[./span[normalize-space()='Сохранить']]")
    public Button buttonSaveComment;

    @Element("Запись в истории комментариев")
    @FindBy(xpath = "//span[@class='comment-content']")
    public TextBlock commentContentTextBlock;

    @Element("Кнопка Комментарии")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Комментарии')]]")
    public Button buttonComments;

    @Element("Окно комментарии")
    @FindBy(xpath = "//div[contains(@class, 'ant-modal')]")
    public Button windowComments;

    @Element("Модальное окно предупреждения")
    @FindBy(xpath = "//div//mat-dialog-container")
    public TextBlock modalInfoError;

    @Element("Кнопка Да модального окна")
    @FindBy(xpath = "//div[contains(@class, 'application-attention-btn')]//button[.//span[contains(text(), 'Да')]] " +
            "| //div[contains(@class, 'cdk-overlay-pane')]//button[.//span[contains(text(), 'Да')]]")
    public Button yesButton;

    @Element("Информация по заявке (Дата и Статус)")
    @FindBy(xpath = "//span[contains(text(),  'Статус')]")
    public TextBlock infoDateStatusTextBlock;

    @Element("Кнопка Ответ от ГО")
    @FindBy(xpath = "//button/span[contains(text(), 'Ответ от ГО')]")
    public Button responseFromGoButton;

    @Element("Кнопка Изменить результат")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Изменить результат')]]")
    public Button changeResultButton;

    @Element("Кнопка Идеальная КИ")
    @FindBy(xpath = "//div[span[contains(text(), 'Идеальная КИ')]]")
    public Button idealKiButton;

    @Element("Текст 'Отредактирована!'")
    @FindBy(xpath = "//div//span[contains(text(), 'Отредактирована!')]")
    public TextBlock edditTextBlock;

}
