package ru.autotestframework.pages.card_request.verification;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.support.FindBy;
import ru.autotestframework.pages.BasePage;
import ru.autotestframework.ui_core.exceptions.ElementInteractionException;
import ru.autotestframework.ui_core.page_manager.Element;
import ru.autotestframework.ui_core.page_manager.PageEntry;
import ru.autotestframework.web_elements.elements.Button;
import ru.autotestframework.web_elements.elements.IFrame;
import ru.autotestframework.web_elements.elements.TextBlock;
import ru.autotestframework.web_elements.elements.TextInput;
import ru.psb.testit.annotations.Description;
import ru.psb.testit.annotations.Step;
import ru.psb.testit.annotations.Title;

import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.$x;

@PageEntry(title = "Страница Прозвон клиента")
public class CustomerCallPage extends BasePage<CustomerCallPage> {

    @Element("Поле Наименование стратегии")
    @FindBy(xpath = "//div[@class='top-panel-title']")
    public TextBlock nameOfStrategyTextBlock;

    @Element("Поле Наименование шага")
    @FindBy(xpath = "//div[@class='ant-steps-item-title']")
    public List<TextBlock> listOfNameSteps;

    @Element("Поле ввода Внутренний комментарий")
    @FindBy(xpath = "//div[contains(text(), 'Внутренний комментарий')]//..//textarea")
    public TextInput textareaCommentTextInput;

    @Element("Поле ввода Комментарий для МРК")
    @FindBy(xpath = "//div[contains(text(), 'Комментарий для МРК')]//..//textarea")
    public TextInput commentMRKTextInput;

    @Element("Кнопка Основные данные")
    @FindBy(xpath = "//app-main-data-button/a[contains(text(), 'Основные данные')]")
    public Button basicDataButton;

    @Element("Кнопка Изменить результат")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Изменить результат')]]")
    public Button changeResultButton;

    @Element("Кнопка Скрипт разговора")
    @FindBy(xpath = "//app-call-script/a[contains(text(), 'Скрипт разговора')]")
    public Button conversationScriptButton;

    @Element("Кнопка Взять шаг в работу")
    @FindBy(xpath = "//button[span[contains(text(), 'Взять шаг в работу')]]")
    public Button takeStepIntoWorkButton;

    @Element("Выпадающий список Результат проверки")
    @FindBy(xpath = "//app-verification-select[@formcontrolname='result']//nz-select")
    public Button displayByDropDownPostPoned;

    @Element("Выпадающий список Причина доработки")
    @FindBy(xpath = "//app-verification-select[@formcontrolname='revisionReason']//nz-select")
    public Button listReasonForRevisionVerificationButton;

    @Element("Выпадающий список Тип одобрения")
    @FindBy(xpath = "//app-verification-select[@formcontrolname='approveType']//nz-select")
    public Button typeForApprovalButton;

    @Element("Выпадающий список Причина отклонения")
    @FindBy(xpath = "//app-verification-select[@formcontrolname='declineReason']//nz-select")
    public Button reasonForRefusalButton;

    @Element("Выпадающий список Тип вопроса")
    @FindBy(xpath = "//app-verification-select[@formcontrolname='questionTypeCode']//nz-select")
    public Button typeForQuestionButton;

    @Element("Выпадающий список Результат по заявке")
    @FindBy(xpath = "//app-verification-select[@formcontrolname='appResult']//nz-select")
    public Button resultApplicationButton;

    @Element("Выпадающий список Нерезультативный прозвон")
    @FindBy(xpath = "//app-verification-select[@formcontrolname = 'ineffective']//nz-select")
    public Button ineffectiveDropDown;

    @Element("Выпадающий список Результативный прозвон")
    @FindBy(xpath = "//app-verification-select[@formcontrolname = 'productive']//nz-select")
    public Button productiveDropDown;

    @Element("Выпадающий список Негатив не выявлен")
    @FindBy(xpath = "//app-verification-select[@formcontrolname = 'noNegative']//nz-select")
    public Button noNegativeDropDown;

    @Element("Выпадающий список Выявлен негатив")
    @FindBy(xpath = "//mat-form-field//mat-select[@role = 'combobox']")
    public Button negativeDropDown;

    @Element("Выпадающий список чек-боксов Выявлен негатив")
    @FindBy(xpath = "//div[@role = 'listbox']")
    public Button negativeListCheckBoxDropDown;

    @Element("Интерфейс")
    @FindBy(xpath = "//div[@class = 'cdk-overlay-container']")
    public Button interfaceButton;

    @Element("Список выбранных чекбоксов в разделе Выявлен негатив")
    @FindBy(xpath = "//div[@class = 'tags']")
    public TextBlock negativeActiveCheckBoxListTextBlock;

    @Element("Выпадающий список Бесконтактное подтверждение")
    @FindBy(xpath = "//app-verification-select[@formcontrolname = 'contactlessApprove']//nz-select")
    public Button contactlessApproveDropDown;

    @Element("Поле ввода Источник подтверждения")
    @FindBy(xpath = "//textarea[@placeholder = 'Источник подтверждения']")
    public TextInput sourceConfirmationContactlessApproveDropDown;

    @Element("Иконка удалить Выбранный чекбокс")
    @FindBy(xpath = "//i[@nztype='close']")
    public Button deleteButton;

    @Element("Кнопка Далее")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Далее')]]")
    public Button buttonNext;

    @Element("Кнопка Завершить проверку")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Завершить проверку')]]")
    public Button buttonFinishCheck;

    @Element("Иконка Шаг заблокирован")
    @FindBy(xpath = "//div[contains(@class, 'blocked')]//span[text() = 'Заблокирован']")
    public Button blockedButton;

    @Element("Иконка Отправить на доработку")
    @FindBy(xpath = "//nz-tag[text()]//app-exclamation[i[@style = '--iconColor: #000;']]")
    public Button sendRevisionButton;

    @Element("Иконка Необходима проверка сотрудниками ОПМ")
    @FindBy(xpath = "//nz-tag[text()]//app-exclamation[i[@style = '--iconColor: #FF0000;']]")
    public Button antifraudButton;

    @Element("Кнопка ОК")
    @FindBy(xpath = "//button[.//span[contains(text(), 'ОК') or contains(text(), 'Ок')]]")
    public Button buttonOk;

    @Element("Иконка Завершен первый этап")
    @FindBy(xpath = "//nz-step[contains(@class,'ant-steps-item-finish')]//span[contains(@class,'ant-steps-icon')]")
    public Button buttonFinish;

    @Element("Иконка Степ 1")
    @FindBy(xpath = "//div[@class='ant-steps-item-icon']//span[contains(text(),'1')]")
    public Button stepsOne;

    @Element("Иконка Степ 2")
    @FindBy(xpath = "//div[@class='ant-steps-item-icon']//span[contains(text(),'2')]")
    public Button stepsTwo;

    @Element("Иконка Степ 3")
    @FindBy(xpath = "//div[@class='ant-steps-item-icon']//span[contains(text(),'3')]")
    public Button stepsThree;

    @Element("Название Степа 1")
    @FindBy(xpath = "//div[@class='ant-steps-item-icon']//span[contains(text(),'1')]//..//..//div[@class='ant-steps-item-title']")
    public Button nameStepsOne;

    @Element("Название Степа 2")
    @FindBy(xpath = "//div[@class='ant-steps-item-icon']//span[contains(text(),'2')]//..//..//div[@class='ant-steps-item-title']")
    public Button nameStepsTwo;

    @Element("Название Степа 3")
    @FindBy(xpath = "//div[@class='ant-steps-item-icon']//span[contains(text(),'3')]//..//..//div[@class='ant-steps-item-title']")
    public Button nameStepsThree;

    @Element("Описание Степа 1")
    @FindBy(xpath = "//div[@class='ant-steps-item-icon']//span[contains(text(),'1')]//..//..//div[@class='ant-steps-item-description']")
    public Button descriptionStepsOne;

    @Element("Описание Степа 2")
    @FindBy(xpath = "//div[@class='ant-steps-item-icon']//span[contains(text(),'2')]//..//..//div[@class='ant-steps-item-description']")
    public Button descriptionStepsTwo;

    @Element("Описание Степа 3")
    @FindBy(xpath = "//div[@class='ant-steps-item-icon']//span[contains(text(),'3')]//..//..//div[@class='ant-steps-item-description']")
    public Button descriptionStepsThree;

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
    @FindBy(xpath = "//app-verification-select[@formcontrolname='noNegative']//nz-select" +
            "|//div[contains(@class,'ng-star-inserted')]//mat-select")
    public Button addFieldResultCheck2DropDown;

    @Element("Кнопка Сохранить")
    @FindBy(xpath = "//button[span[text() = ' Сохранить ']]")
    public Button saveButton;

    @Element("Кнопка Отложить")
    @FindBy(xpath = "//button[span[text() = ' Отложить ']]")
    public Button postponeButton;

    @Element("Кнопка Взять в работу")
    @FindBy(xpath = "//button[span[text() = ' Взять в работу ']]")
    public Button takeToWorkButton;

    @Element("Поле ввода Комментарий")
    @FindBy(xpath = "//div[contains(text(), 'Комментарий')]//..//textarea")
    public TextInput commentTextInput;

    @Element("Поле Доход завышен")
    @FindBy(xpath = "//app-info-message")
    public TextBlock incomeOverTextBlock;

    //Модальное окно Перевод заявки в отложенные

    @Element("Модальное окно Перевод заявки в отложенные")
    @FindBy(xpath = "//div[contains(@class, 'psb-dialog-pane')]")
    public IFrame modalWindow;

    @Element("Выпадающий список Причина (Перевод заявки в отложенные)")
    @FindBy(xpath = "//div[.//div[./span[contains(text(), 'Причина')]]]//mat-select[@formcontrolname = 'delayReason']")
    public Button reason;

    @Element("Список Причин (Перевод заявки в отложенные)")
    @FindBy(xpath = "//div[@role = 'listbox']")
    public Button listReason;

    @Element("Поле ввода Комментарий (Перевод заявки в отложенные)")
    @FindBy(xpath = "//textarea[@placeholder='Текст комментария']")
    public TextInput comment;

    @Element("Кнопка Время для звонка участнику (Перевод заявки в отложенные)")
    @FindBy(xpath = "//div[./div[./span[contains(text(), 'Время для звонка участнику')]]]//mat-datepicker-toggle")
    public Button buttonTimeCallParticipant;

    @Element("Кнопка Подтвердить автоматическое время (Перевод заявки в отложенные)")
    @FindBy(xpath = "//button[./span[./*[contains(text(), 'done')]]]")
    public Button buttonDone;

    @Element("Поле ввода Время для звонка участнику (Перевод заявки в отложенные)")
    @FindBy(xpath = "//div[./div[./span[contains(text(), 'Время для звонка участнику')]]]//mat-form-field//input")
    public TextInput inputDate;

    @Element("Выпадающий список Участник сделки (Перевод заявки в отложенные)")
    @FindBy(xpath = "//mat-select[contains(@formcontrolname, 'participant')]")
    public Button participantTextInput;

    @Element("Кнопка Отложить заявку (Перевод заявки в отложенные)")
    @FindBy(xpath = "//button[./span[contains(text(), 'Отложить заявку')]]")
    public Button buttonPostponeRequest;

    @Element("Кнопка Отмена (Перевод заявки в отложенные)")
    @FindBy(xpath = "//button[./span[contains(text(), 'Отмена')]]")
    public Button buttonCanselPostponeRequest;

    //Кнопка внутренний комментарий

    @Element("Окно внутренних комментариев")
    @FindBy(xpath = "//div[contains(@class, 'ant-modal-content')]")
    public Button windowInternalComment;

    @Element("Кнопка закрыть Окно")
    @FindBy(xpath = "//button[@aria-label = 'Close']")
    public Button buttonClose;

    @Element("Кнопка Внутренний комментарий")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Внутренний комментарий')]]")
    public Button buttonInternalComment;

    @Element("Поле ввода История комментариев")
    @FindBy(xpath = "//textarea[@formcontrolname='comment']")
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

    @Element("Кнопка Комментарии")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Комментарии')]]")
    public Button buttonComments;

    @Element("Окно комментарии")
    @FindBy(xpath = "//div[contains(@class, 'ant-modal')]")
    public Button windowComments;

    @Element("Кнопка Идеальная КИ")
    @FindBy(xpath = "//div[span[contains(text(), 'Идеальная КИ')]]")
    public Button idealKiButton;

    @Element("Иконка для активации поля ввода Скоррект. доход/По Осн. месту")
    @FindBy(xpath = "//div[span[contains(text(), 'По осн. месту')]]/following-sibling::mat-form-field//div//mat-icon")
    public TextInput iconCorrectTextInput;

    @Element("Поле ввода Скоррект. доход/По Осн. месту")
    @FindBy(xpath = "//div[span[contains(text(), 'По осн. месту')]]/following-sibling::mat-form-field//div//input[not(@value)]")
    public TextInput correctTextInput;

    @Step
    @Title("Проверить, что отображается выбранный элемент {title} из выпадающего списака")
    public CustomerCallPage checkVisibilityDropDownElement(String title) {
        SelenideElement element = $x(String.format("//nz-select//nz-select-item[@title = '%s']", title));
        if (!element.isDisplayed()) {
            throw new ElementNotInteractableException("Выбранный элемент " + title + " не отобразился ");
        }
        return this;
    }

    @Step
    @Title("Проверить, что выбранный шаг {title} является активным")
    public CustomerCallPage checkStepActivity(String title) {
        SelenideElement element = $x("//nz-step[@class='ant-steps-item ant-steps-item-process ant-steps-item-active ng-star-inserted']//div[text()='" + title + "']");
        if (!element.isDisplayed()) {
            throw new ElementNotInteractableException("Шаг " + title + " не является активным");
        }
        return this;
    }

    @Step
    @Title("Получить актуальные наименования шагов")
    public List<String> getActualStepNames() {
        return listOfNameSteps.stream().map(TextBlock::getText).collect(Collectors.toList());
    }

    @Step
    @Title("Кликнуть на степ {step}")
    public CustomerCallPage clickOnStep(String step) {
        $x("//div[@class='ant-steps-item-icon'][./following-sibling::div//div[text()='" + step + "']]")
                .shouldBe(Condition.visible)
                .click();
        return this;
    }

    @Step
    @Title("Кликнуть на степ {step} c описанием {description}")
    public CustomerCallPage clickOnStep(String step, String description) {
        $x("//div[@class='ant-steps-item-icon'][./following-sibling::div//div[text()='" + step + "'] and ./following-sibling::div//div[contains(.,'" + description + "')]]")
                .shouldBe(Condition.visible)
                .click();
        return this;
    }

    @Step
    @Title("выбрать из выпадающего списка {titleDropDownList} значение - {value}")
    @Description("Выбор одного значения в выпадающем списке")
    public CustomerCallPage selectValueFromDropDownList(String titleDropDownList, String value, boolean hasScroll) {
        int scrollAmount;
        // Если скролл есть, то начинаем скроллить и пытаться нажать на целевой элемент
        try {
            clickOnElement(titleDropDownList);
            SelenideElement listChoosingOption = SELECT_OPTIONS.get(0);
            listChoosingOption.shouldBe(Condition.visible);
            // Если скролл не на самом верху (т.е. был прокручен), то возвращаем на место
            if (!isATop(getElementByTitle(titleDropDownList))) {
                scrollAmount = -1000;
                scrollDropDownList(getElementByTitle(titleDropDownList), "вертикальный", scrollAmount);
            }
            selectFromDropDownList(getElementByTitle(titleDropDownList), List.of(value));
        } catch (ElementInteractionException e) {
            scrollAmount = 200;
            // Скроллим и пытаемся нажать на целевой элемент пока не дошли до конца списка
            while (!isABottom(getElementByTitle(titleDropDownList))) {
                try {
                    scrollDropDownList(getElementByTitle(titleDropDownList), "вертикальный", scrollAmount);
                    selectFromDropDownList(getElementByTitle(titleDropDownList), List.of(value));
                    break;
                } catch (ElementInteractionException exception) {
                    scrollAmount += 40;
                }
            }
        }
        return this;
    }
}
