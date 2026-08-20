package ru.autotestframework.pages.card_request.verification;

import org.openqa.selenium.support.FindBy;
import ru.autotestframework.pages.BasePage;
import ru.autotestframework.ui_core.page_manager.Element;
import ru.autotestframework.ui_core.page_manager.PageEntry;
import ru.autotestframework.ui_core.services.table_service.FindCellsBy;
import ru.autotestframework.ui_core.services.table_service.FindHeadersBy;
import ru.autotestframework.web_elements.elements.*;

@PageEntry(title = "Страница Проверка открытых источников")
public class CheckingOpenSourcesPage extends BasePage<CheckingOpenSourcesPage> {

    @Element("Поле Наименование стратегии")
    @FindBy(xpath = "//div[@class='top-panel-title']")
    public TextBlock nameOfStrategyTextBlock;

    @Element("Раздел Проверка сайта")
    @FindBy(xpath = "//section[@formgroupname = 'siteCheck']")
    public TextBlock siteVerificationTextBlock;

    @Element("Раздел Проверка работодателя")
    @FindBy(xpath = "//section[@formgroupname = 'employerCheck']")
    public TextBlock employerCheckTextBlock;

    @Element("Чек-бокс Приостановление по счетам организации")
    @FindBy(xpath = "//span[contains(text(), ' Приостановление по счетам организации ')]/..//..//label")
    public ClassicCheckBox suspensionOrganizationAccountsCheckBox;

    @Element("Раздел Привязка телефона из анкеты")
    @FindBy(xpath = "//section[@formgroupname = 'phoneCheck']")
    public TextBlock phoneCheckTextBlock;

    @Element("Раздел Бесконтактное подтверждение трудоустройства")
    @FindBy(xpath = "//section[@formgroupname = 'employmentCheck']")
    public TextBlock employmentCheckTextBlock;

    @Element("Выпадающий список Результат по заявке")
    @FindBy(xpath = "//div[./div[contains(text(), 'Результат по заявке')]]//nz-select")
    public Button resultApplicationButton;

    @Element("Выпадающий список Тип вопроса")
    @FindBy(xpath = "//div[./div[contains(text(), 'Тип вопроса')]]//nz-select")
    public Button typeForQuestionButton;

    @Element("Поле ввода Комментарий")
    @FindBy(xpath = "//div[contains(text(), 'Комментарий')]//..//textarea")
    public TextInput commentTextInput;

    @Element("Кнопка Ответ от ГО")
    @FindBy(xpath = "//button/span[contains(text(), 'Ответ от ГО')]")
    public Button responseFromGoButton;

    @Element("Таблица Ответ от ГО")
    @FindBy(xpath = "//app-opm-go-messages-table/table")
    @FindCellsBy(xpath = ".//td[@role='cell']")
    @FindHeadersBy(xpath = ".//th[@role='columnheader']")
    public WebTable responseFromGoTable;

    @Element("Раздел Брокерские услуги")
    @FindBy(xpath = "//section[@class = 'section ng-star-inserted']")
    public TextBlock brokerageServicesTextBlock;

    @Element("Выпадающий список Проверка сайта")
    @FindBy(xpath = "//div[contains(text(), 'Проверка сайта')]/..//nz-select")
    public Button siteVerificationDropDown;

    @Element("Поле ввода Источник подтверждения")
    @FindBy(xpath = "//textarea[@placeholder = 'Источник подтверждения']")
    public TextInput textareaSourceConfirmationTextInput;

    @Element("Поле ввода Источник подтверждения Проверка телефона из анкеты")
    @FindBy(xpath = "//section[@formgroupname='phoneCheck']//textarea[@placeholder = 'Источник подтверждения']")
    public TextInput textareaSourceConfirmationPhoneCheckTextInput;

    @Element("Поле ввода Источник подтверждения Проверка сайта")
    @FindBy(xpath = "//section[@formgroupname='siteCheck']//textarea[@placeholder = 'Источник подтверждения']")
    public TextInput textareaSourceConfirmationSiteCheckTextInput;

    @Element("Выпадающий список C выбором вида негатива")
    @FindBy(xpath = "//mat-form-field[//mat-select[@role = 'combobox']]")
    public Button choosingTypeNegativeDropDown;

    @Element("Флаг Проверить!")
    @FindBy(xpath = "//div[contains(text(), ' Проверка работодателя ')]//span[contains(text(), ' (Проверить!) ')]")
    public TextBlock checkFlagTextBlock;

    @Element("Выпадающий список Проверка работодателя")
    @FindBy(xpath = "//div[div[@class='section_checkbox_wrapper'] and div[@class='ng-star-inserted']]")
    public Button employmentVerificationDropDown;

    @Element("Плашка Сайт дублер")
    @FindBy(xpath = "//nz-tag[contains(text(), ' Сайт дублер ')]")
    public Button doublerWebsiteButton;

    @Element("Плашка Сайт создан менее 6 месяцев")
    @FindBy(xpath = "//nz-tag[contains(text(), ' Сайт создан менее 6 месяцев ')]")
    public Button sixMonthsButton;

    @Element("Чек-бокс Негатив по работодателю не выявлен")
    @FindBy(xpath = "//span[contains(text(), ' Негатив по работодателю не выявлен ')]/..//..//label")
    public ClassicCheckBox negativeInformationCheckBox;

    @Element("Чек-бокс Негатив на работодателя в сети")
    @FindBy(xpath = "//span[contains(text(), 'Негатив на работодателя в сети')]/..//..//label")
    public ClassicCheckBox negativeInformationOnlineCheckBox;

    @Element("Чек-бокс Решение о санации")
    @FindBy(xpath = "//span[contains(text(), 'Решение о санации')]/..//..//label")
    public ClassicCheckBox decisionRehabilitationCheckBox;

    @Element("Чек-бокс ИП на работодателя с указанием сумм")
    @FindBy(xpath = "//span[contains(text(), ' ИП на работодателя с указанием сумм ')]/..//..//label")
    public ClassicCheckBox employerIndicatingAmountsCheckBox;

    @Element("Поле ввода Введите сумму ИП")
    @FindBy(xpath = "//input[@placeholder='Введите сумму ИП']")
    public TextInput enterAmountIpTextInput;

    @Element("Чек-бокс Действующие арбитражные дела")
    @FindBy(xpath = "//span[contains(text(), ' Действующие арбитражные дела ')]/..//..//label")
    public ClassicCheckBox currentArbitrationCasesCheckBox;

    @Element("Поле ввода Введите сумму")
    @FindBy(xpath = "//input[@placeholder='Введите сумму']")
    public TextInput enterAmountTextInput;

    @Element("Поле ввода Сайт работодателя")
    @FindBy(xpath = "//textarea[@placeholder='Сайт работодателя']")
    public TextInput employerWebsiteTextInput;

    @Element("Выпадающий список Привязка телефона из анкеты")
    @FindBy(xpath = "//div[contains(text(), 'Привязка телефона из анкеты')]/..//nz-select")
    public Button linkingPhoneNumberProfileDropDown;

    @Element("Выпадающий список Бесконтактное подтверждение трудоустройства")
    @FindBy(xpath = "//div[contains(text(), 'Бесконтактное подтверждение трудоустройства')]/..//nz-select")
    public Button contactlessConfirmationEmploymentDropDown;

    @Element("Выпадающий список Брокерские услуги")
    @FindBy(xpath = "//div[contains(text(), 'Брокерские услуги')]/..//nz-select")
    public Button brokerageServicesDropDown;

    @Element("Кнопка Далее")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Далее')]]")
    public Button buttonNext;

    @Element("Кнопка Завершить проверку")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Завершить проверку')]]")
    public Button buttonFinishCheck;

    @Element("Кнопка ОК")
    @FindBy(xpath = "//button[.//span[contains(text(), 'ОК') or contains(text(), 'Ок')]]")
    public Button buttonOk;

    @Element("Список Чек-боксов Проверка работодателя")
    @FindBy(xpath = "//div[@class = 'ng-star-inserted'][div[contains(@class, 'section_checkbox_wrapper')]]")
    public TextBlock listCheckBoxFiltersTextBlock;

    @Element("Список Чек-боксов Выявлен негатив")
    @FindBy(xpath = "//div[@role='listbox']")
    public TextBlock listCheckBoxNegativeTextBlock;

    @Element("Чек-бокс Сайт дублер")
    @FindBy(xpath = "//mat-option[.//span[text() = ' Сайт дублер ']]")
    public Button checkBoxDoublerWebsiteButton;

    @Element("Чек-бокс Сайт создан менее 6 месяцев")
    @FindBy(xpath = "//mat-option[.//span[text() = ' Сайт создан менее 6 месяцев ']]")
    public Button checkBoxdSixMonthsButton;

    @Element("Интерфейс")
    @FindBy(xpath = "//div[@class = 'cdk-overlay-container']")
    public Button interfaceButton;

    @Element("Иконка Завершен первый этап")
    @FindBy(xpath = "//nz-step[contains(@class,'ant-steps-item-finish')]//span[contains(@class,'ant-steps-icon')]")
    public Button buttonFinish;

    @Element("Иконка Активен первый этап")
    @FindBy(xpath = "//nz-step[contains(@class,'ant-steps-item-active')]//span[contains(text(),'1')]")
    public Button buttonActiveFirstStage;

    @Element("Иконка Активен Второй этап")
    @FindBy(xpath = "//nz-step[contains(@class,'ant-steps-item-active')]//span[contains(text(),'2')]")
    public Button buttonActiveSecondStage;

    @Element("Кнопка Изменить результат")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Изменить результат')]]")
    public Button changeResultButton;

    @Element("Кнопка Комментарии")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Комментарии')]]")
    public Button buttonComment;

    @Element("Шаг №2. Заёмщик. Совместительство")
    @FindBy(xpath = "//span[contains(@class,'ant-steps-icon') and contains(text(),'2')]/.. | //div/br/following-sibling::text() [contains(., 'Совместительство')]//..//..//..//..//i[contains(@class, 'anticon-check')]")
    public Button buttonStep2;

    @Element("Шаг №3. Созаемщик. Основное место работы")
    @FindBy(xpath = "//span[contains(@class,'ant-steps-icon') and contains(text(),'3')]/..")
    public Button buttonStep3;

    @Element("Шаг №4. Созаемщик. Совместительство")
    @FindBy(xpath = "//span[contains(@class,'ant-steps-icon') and contains(text(),'4')]/..")
    public Button buttonStep4;

    @Element("Кнопка Взять шаг в работу")
    @FindBy(xpath = "//button[span[contains(text(), 'Взять шаг в работу')]]")
    public Button takeStepIntoWorkButton;

    @Element("Поле ввода Внутренний комментарий")
    @FindBy(xpath = "//div[contains(text(), 'Внутренний комментарий')]//..//textarea")
    public TextInput textareaCommentTextInput;

    @Element("Кнопка Идеальная КИ")
    @FindBy(xpath = "//div[span[contains(text(), 'Идеальная КИ')]]")
    public Button idealKiButton;

    @Element("Текст 'Отредактирована!'")
    @FindBy(xpath = "//div//span[contains(text(), 'Отредактирована!')]")
    public TextBlock edditTextBlock;

    @Element("Поле ввода Скоррект. доход/По Осн. месту")
    @FindBy(xpath = "//div[span[contains(text(), 'По осн. месту')]]/following-sibling::mat-form-field//div//input[not(@value)]")
    public TextInput correctTextInput;

    @Element("Иконка для активации поля ввода Скоррект. доход/По Осн. месту")
    @FindBy(xpath = "//div[span[contains(text(), 'По осн. месту')]]/following-sibling::mat-form-field//div//mat-icon")
    public TextInput iconCorrectTextInput;
}
