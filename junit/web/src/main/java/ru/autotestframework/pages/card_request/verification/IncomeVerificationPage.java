package ru.autotestframework.pages.card_request.verification;

import org.openqa.selenium.support.FindBy;
import ru.autotestframework.pages.BasePage;

import ru.autotestframework.ui_core.page_manager.Element;
import ru.autotestframework.ui_core.page_manager.PageEntry;
import ru.autotestframework.ui_core.services.table_service.FindCellsBy;
import ru.autotestframework.ui_core.services.table_service.FindHeadersBy;
import ru.autotestframework.web_elements.elements.*;
import ru.psb.testit.annotations.Step;
import ru.psb.testit.annotations.Title;

import static org.junit.jupiter.api.Assertions.assertEquals;

@PageEntry(title = "Страница Проверка дохода")
public class IncomeVerificationPage extends BasePage<IncomeVerificationPage> {

    @Element("Поле Наименование стратегии")
    @FindBy(xpath = "//div[@class='top-panel-title']")
    public TextBlock nameOfStrategyTextBlock;

    @Element("Поле ввода Средний доход по рынку для занимаемой должности")
    @FindBy(xpath = "//div[text()='Средний доход по рынку для занимаемой должности']/..//input")
    public TextInput averageIncomeTextInput;

    @Element("Поле Средний доход по рынку для занимаемой должности")
    @FindBy(xpath = "//div[text()='Средний доход по рынку для занимаемой должности']")
    public TextInput averageIncomeText;

    @Element("Кнопка Далее")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Далее')]]")
    public Button buttonNext;

    @Element("Кнопка ОК")
    @FindBy(xpath = "//button[.//span[contains(text(), 'ОК') or contains(text(), 'Ок')]]")
    public Button buttonOk;

    @Element("Кнопка Сохранить")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Сохранить')]]")
    public Button buttonSave;

    @Element("Кнопка Закрыть")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Закрыть')]]")
    public Button buttonClose;

    @Element("Кнопка Редактировать")
    @FindBy(xpath = "//app-income-check-table//mat-icon")
    public Button buttonEdit;

    @Element("Кнопка Внутренний комментарий")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Внутренний комментарий')]]")
    public Button buttonComment;

    @Element("Кнопка Рассчитать")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Рассчитать')]]")
    public Button buttonCalculate;

    @Element("Кнопка Завершить проверку")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Завершить проверку')]]")
    public Button buttonFinishVerification;

    @Element("Кнопка Взять шаг в работу")
    @FindBy(xpath = "//button[span[contains(text(), 'Взять шаг в работу')]]")
    public Button takeStepIntoWorkButton;

    @Element("Выпадающий список Результат проверки")
    @FindBy(xpath = "//div[contains(text(), 'Результат проверки')]/..//nz-select")
    public Button displayByDropDownPostPoned;

    @Element("Выпадающий список Нерезультативный прозвон")
    @FindBy(xpath = "//app-verification-select[@formcontrolname = 'ineffective']//nz-select")
    public Button ineffectiveDropDown;

    @Element("Выпадающий список Результативный прозвон")
    @FindBy(xpath = "//app-verification-select[@formcontrolname = 'productive']//nz-select")
    public Button productiveDropDown;

    @Element("Таблица Оценка дохода проведена")
    @FindBy(xpath = "//app-income-check-table | //app-income-check-founder-table")
    @FindCellsBy(xpath = ".//div[contains(@class,'income-check-table_td')]")
    @FindHeadersBy(xpath = ".//div[contains(@class,'income-check-table_th')]")
    public WebTable prioritySettingsTable;

    @Element("Иконка Завершен первый этап")
    @FindBy(xpath = "//nz-step[contains(@class,'ant-steps-item-finish')]//span[contains(@class,'ant-steps-icon')]")
    public Button buttonFinish;

    @Element("Иконка Второй этап")
    @FindBy(xpath = "//nz-step[contains(@class,'ant-steps-item-active')]//span[contains(text(),'2')]")
    public Button buttonActiveSecondStage;

    @Element("Иконка Второй этап (неактивная)")
    @FindBy(xpath = "//div[contains(@class,'ant-steps-item-icon')]//span[contains(text(),'2')]")
    public Button buttonNoActiveSecondStage;

    @Element("Иконка Завершен второй этап")
    @FindBy(xpath = "//nz-step[contains(@class,'ant-steps-item-active')]//span[contains(@class,'ant-steps-icon')]")
    public Button buttonFinishSecondStage;

    @Element("Описание Первый этап")
    @FindBy(xpath = "//nz-step[contains(@class,'ant-steps-item-active')]//div[@class='ant-steps-item-description']")
    public TextBlock firstStageTextBlock;

    @Element("Описание Второй этап")
    @FindBy(xpath = "//nz-step[contains(@class,'ant-steps-item-wait')]//div[@class='ant-steps-item-description']//div")
    public TextBlock secondStageTextBlock;

    @Element("Поле Доход завышен/не завышен")
    @FindBy(xpath = "//div[contains(@class,'risk__message')]//span")
    public TextBlock fieldIncome;

    @Element("Модальное окно Предупреждение")
    @FindBy(xpath = "//mat-dialog-container")
    public TextBlock warningModalWindow;

    @Element("Выпадающий список Результат по заявке")
    @FindBy(xpath = "//div[./div[contains(text(), 'Результат по заявке')]]//nz-select")
    public Button resultApplicationButton;

    @Element("Выпадающий список Причина доработки")
    @FindBy(xpath = "//div[./div[contains(text(), 'Причина доработки')]]//nz-select")
    public Button listReasonForRevisionButton;

    @Element("Выпадающий список Тип вопроса")
    @FindBy(xpath = "//div[./div[contains(text(), 'Тип вопроса')]]//nz-select")
    public Button typeForQuestionButton;

    @Element("Поле ввода Комментарий")
    @FindBy(xpath = "//div[contains(text(), 'Комментарий')]//..//textarea")
    public TextInput commentTextInput;

    @Element("Поле ввода Внутренний комментарий")
    @FindBy(xpath = "//div[contains(text(), 'Внутренний комментарий')]//..//textarea")
    public TextInput textareaCommentTextInput;

    @Element("Поле ввода Комментарий для МРК")
    @FindBy(xpath = "//div[contains(text(), 'Комментарий для МРК')]//..//textarea")
    public TextInput commentMRKTextInput;

    @Element("Поле ввода Выручка по официальным данным за предыдущий год (руб.)")
    @FindBy(xpath = "//div[contains(text(), 'Выручка по официальным данным за предыдущий год (руб.)')]//..//input")
    public TextInput revenueOfficialPreviousTextInput;

    @Element("Поле ввода Доля в бизнесе (%)")
    @FindBy(xpath = "//div[contains(text(), 'Доля в бизнесе (%)')]//..//input")
    public TextInput businessShareTextInput;

    @Element("Поле ввода Рассчитанный доход от ведения бизнеса (руб.)")
    @FindBy(xpath = "//div[contains(text(), 'Рассчитанный доход от ведения бизнеса (руб.)')]//..//input")
    public TextInput calculatedIncomeTextInput;

    @Element("Поле Деятельность компании подразумевает большую закупочную часть или траты вне персонала")
    @FindBy(xpath = "//span[contains(text(), 'Деятельность компании подразумевает большую закупочную часть или траты вне персонала')]")
    public TextBlock companyActivitiesTextBlock;

    @Element("Иконка подсказка")
    @FindBy(xpath = "//span[contains(text(), 'Деятельность компании подразумевает большую закупочную часть или траты вне персонала')]//..//app-hint-icon")
    public TextBlock hintTextBlock;

    @Element("Кнопка Редактирования")
    @FindBy(xpath = "//div[contains(text(), 'Значение')]//mat-icon")
    public Button matIconButton;

    @Element("Переключатель Да (Деятельность компании подразумевает большую закупочную часть или траты вне персонала)")
    @FindBy(xpath = "//mat-radio-button[.//span[text() = ' ДА ']]//span[@class = 'mat-radio-container']")
    public Button yesButton;

    @Element("Переключатель Нет (Деятельность компании подразумевает большую закупочную часть или траты вне персонала)")
    @FindBy(xpath = "//mat-radio-button[.//span[text() = ' НЕТ ']]//span[@class = 'mat-radio-container']")
    public Button noButton;

    @Element("Модальное окно с сообщением")
    @FindBy(xpath = "//mat-dialog-container//*[@class='main-text' or @class='modal_message']")
    public TextBlock textBlockModalInfo;

    @Element("Поле ввода знчения Рассчитанный доход от ведения бизнеса (руб.)")
    @FindBy(xpath = "//div[contains(text(), 'Рассчитанный доход от ведения бизнеса (руб.)')]//..//div[input]")
    public TextInput calculatedIncomeResultTextInput;

    @Element("Кнопка Изменить результат")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Изменить результат')]]")
    public Button changeResultButton;

    @Element("Иконка Степ 1")
    @FindBy(xpath = "//div[@class='ant-steps-item-icon']//span[contains(text(),'1')]")
    public Button stepsOne;

    @Element("Иконка Степ 2")
    @FindBy(xpath = "//div[@class='ant-steps-item-icon']//span[contains(text(),'2')]")
    public Button stepsTwo;

    @Element("Иконка Степ 3")
    @FindBy(xpath = "//div[@class='ant-steps-item-icon']//span[contains(text(),'3')]")
    public Button stepsThree;

    @Element("Иконка Степ 4")
    @FindBy(xpath = "//div[@class='ant-steps-item-icon']//span[contains(text(),'4')]")
    public Button stepsFour;

    @Element("Первый шаг на степере")
    @FindBy(xpath = "//nz-step[.//span[text() = ' 1 ']]")
    public TextBlock firstSteps;

    @Element("Второй шаг на степере")
    @FindBy(xpath = "//nz-step[.//span[text() = ' 2 ']]")
    public TextBlock secondSteps;

    @Element("Третий шаг на степере")
    @FindBy(xpath = "//nz-step[.//span[text() = ' 3 ']]")
    public TextBlock threeSteps;

    @Element("Четвертый шаг на степере")
    @FindBy(xpath = "//nz-step[.//span[text() = ' 4 ']]")
    public TextBlock fourSteps;

    @Element("Пятый шаг на степере")
    @FindBy(xpath = "//nz-step[.//span[text() = ' 5 ']]")
    public TextBlock fiveSteps;

    @Element("Шестой шаг на степере")
    @FindBy(xpath = "//nz-step[.//span[text() = ' 6 ']]")
    public TextBlock sixSteps;

    @Element("Иконка 'галочка' на первом шаге степера")
    @FindBy(xpath = "//div/br/following-sibling::text() [contains(., 'Основное место работы')]//..//..//..//..//i[contains(@class, 'anticon-check')]")
    public TextBlock anticonFirstSteps;

    @Element("Иконка 'галочка' на втором шаге степера")
    @FindBy(xpath = "//div/br/following-sibling::text() [contains(., 'Совместительство')]//..//..//..//..//i[contains(@class, 'anticon-check')]")
    public TextBlock anticonSecondSteps;

    @Element("Иконка 'галочка' на третьем шаге степера")
    @FindBy(xpath = "//div/br/following-sibling::text() [contains(., 'ФКУ')]//..//..//..//..//i[contains(@class, 'anticon-check')]")
    public TextBlock anticonThreeSteps;

    @Element("Второй шаг на степере. Прозвон контактного лица/супруга - заблокирован")
    @FindBy(xpath = "//div[contains(text() , 'Прозвон контактного лица/супруга')]//..//..//div[contains(@class, 'blocked')]//span[text() = 'Заблокирован']")
    public Button blockedStep2Button;

    @Element("Третий шаг на степере. Прозвон работодателя - любой телефон - заблокирован")
    @FindBy(xpath = "//nz-step[.//span[normalize-space() = '3']]//div[contains(text() , 'Прозвон работодателя - любой телефон')]//..//..//div[contains(@class, 'blocked')]//span[text() = 'Заблокирован']")
    public Button blockedStep3Button;

    @Element("Четвертый шаг на степере. Прозвон работодателя - любой телефон (Совместительство) - заблокирован")
    @FindBy(xpath = "//nz-step[.//span[normalize-space() = '4']]//div[contains(text() , 'Прозвон работодателя - любой телефон')]//..//..//div[contains(@class, 'blocked')]//span[text() = 'Заблокирован']")
    public Button blockedStep4Button;

    @Element("Иконка для активации поля ввода Скоррект. доход/По Осн. месту")
    @FindBy(xpath = "//div[span[contains(text(), 'По осн. месту')]]/following-sibling::mat-form-field//div//mat-icon")
    public TextInput iconCorrectTextInput;

    @Element("Поле ввода Скоррект. доход/По Осн. месту")
    @FindBy(xpath = "//div[span[contains(text(), 'По осн. месту')]]/following-sibling::mat-form-field//div//input[not(@value)]")
    public TextInput correctTextInput;

    @Element("Кнопка Основные данные")
    @FindBy(xpath = "//app-main-data-button/a[contains(text(), 'Основные данные')]")
    public Button basicDataButton;
}
