package ru.autotestframework.pages.setting_control;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.springframework.stereotype.Component;
import ru.autotestframework.pages.components.TopBar;
import ru.autotestframework.ui_core.exceptions.ElementInteractionException;
import ru.autotestframework.ui_core.page_manager.Element;
import ru.autotestframework.ui_core.page_manager.PageEntry;
import ru.autotestframework.ui_core.services.table_service.FindCellsBy;
import ru.autotestframework.ui_core.services.table_service.FindHeadersBy;
import ru.autotestframework.web_elements.elements.Button;
import ru.autotestframework.web_elements.elements.TextBlock;
import ru.autotestframework.web_elements.elements.TextInput;
import ru.autotestframework.web_elements.elements.WebTable;
import ru.autotestframework.web_elements.elements.typified.TypifiedWebElement;
import ru.psb.testit.annotations.Description;
import ru.psb.testit.annotations.Step;
import ru.psb.testit.annotations.Title;

import java.util.List;


@Component
@PageEntry(title = "Страница Настройка скриптов")
public class ScriptConfigurationPage extends TopBar<ScriptConfigurationPage> {

    // Вкладки

    @Element("Вкладка Основной скрипт")
    @FindBy(xpath = "//app-link-menu//a[normalize-space()='Основной скрипт']")
    public Button mainScriptButton;

    @Element("Вкладка Вопросы")
    @FindBy(xpath = "//app-link-menu//a[normalize-space()='Вопросы']")
    public Button questionsButton;

    @Element("Вкладка Общий скрипт")
    @FindBy(xpath = "//app-link-menu//a[normalize-space()='Общий скрипт']")
    public Button commonScriptButton;

    // Фильтры таблицы

    @Element("Поле ввода Текст вопроса")
    @FindBy(xpath = "//span[.//psb-text[text()='Текст вопроса']]/preceding-sibling::input")
    public TextInput textQuestionTextInput;

    @Element("Выпадающий список Причина назначения")
    @FindBy(xpath = "//app-multi-select[.//psb-text[text()='Причина назначения']]//mat-select")
    public Button assignmentReasonDropDown;

    @Element("Выпадающий список Продукт")
    @FindBy(xpath = "//app-multi-select[.//psb-text[text()='Продукт']]//mat-select")
    public Button productDropDown;

    @Element("Выпадающий список Канал поступления")
    @FindBy(xpath = "//app-multi-select[.//psb-text[text()='Канал поступления']]//mat-select")
    public Button comeSourceDropDown;

    @Element("Выпадающий список Сегмент")
    @FindBy(xpath = "//app-multi-select[.//psb-text[text()='Сегмент']]//mat-select")
    public Button segmentDropDown;

    @Element("Выпадающий список Форма подтверждения дохода")
    @FindBy(xpath = "//app-multi-select[.//psb-text[text()='Форма подтверждения дохода']]//mat-select")
    public Button formIncomeApproveDropDown;

    @Element("Выпадающий список Статус клиента")
    @FindBy(xpath = "//app-multi-select[.//psb-text[text()='Статус клиента']]//mat-select")
    public Button clientStatusDropDown;

    @Element("Выпадающий список Тип прозвона")
    @FindBy(xpath = "//app-multi-select[.//psb-text[text()='Тип прозвона']]//mat-select")
    public Button callTypeDropDown;

    @Element("Поле ввода Наименование скрипта")
    @FindBy(xpath = "//span[.//psb-text[text()='Наименование скрипта']]/preceding-sibling::input")
    public TextInput scriptNameTextInput;

    @Element("Выпадающий список Категория скрипта")
    @FindBy(xpath = "//app-multi-select[.//psb-text[text()='Категория скрипта']]//mat-select")
    public Button scriptCategoryDropDown;

    @Element("Кнопка Найти")
    @FindBy(xpath = "//app-button//span[normalize-space()='Найти']")
    public Button findButton;

    // Таблица Результаты поиска

    @Element("Таблица Результаты поиска")
    @FindBy(xpath = "//psb-table-container//*[@class='psb-base-table']")
    @FindHeadersBy(xpath = ".//th[@role='columnheader']")
    @FindCellsBy(xpath = ".//td[@role='cell']")
    public WebTable searchResultTable;

    @Element("Кнопка Создать")
    @FindBy(xpath = "//app-button//span[normalize-space()='Создать']")
    public Button createButton;

    @Element("Кнопка Редактировать")
    @FindBy(xpath = "//app-button//span[normalize-space()='Редактировать']")
    public Button modifyButton;

    @Element("Кнопка Удалить")
    @FindBy(xpath = "//app-button//span[normalize-space()='Удалить']")
    public Button deleteButton;

    // ЭФ Создание/Редактирование вопроса

    @Element("Модальное окно Создание вопроса")
    @FindBy(xpath = "//mat-dialog-container/app-questions-modal")
    public TextBlock createQuestionModal;

    @Element("Поле ввода Текст вопроса (Создание вопроса)")
    @FindBy(xpath = "//app-questions-modal//textarea")
    public TextInput textOfQuestionTextInput;

    @Element("Выпадающий список Тип вопроса (Создание вопроса)")
    @FindBy(xpath = "//app-questions-modal//label[normalize-space()='Тип вопроса']/following-sibling::nz-select[1]")
    public TextBlock questionTypeDropDown;

    @Element("Выпадающий список Блок вопроса (Создание вопроса)")
    @FindBy(xpath = "//app-questions-modal//label[normalize-space()='Блок вопроса']/following-sibling::nz-select[1]")
    public TextBlock questionBlockDropDown;

    @Element("Выпадающий список Принадлежность абоненту (Создание вопроса)")
    @FindBy(xpath = "//app-questions-modal//label[normalize-space()='Принадлежность абоненту']/following-sibling::nz-select[1]")
    public TextBlock subscriberAffiliationDropDown;

    @Element("Выпадающий список Название профессии (Создание вопроса)")
    @FindBy(xpath = "//app-questions-modal//label[normalize-space()='Название профессии']/following-sibling::nz-select[1]")
    public TextBlock professionNameDropDown;

    @Element("Кнопка Сохранить (Создание вопроса)")
    @FindBy(xpath = "//app-questions-modal//app-button//span[normalize-space()='Сохранить']")
    public Button saveButton;

    // ЭФ Создание/Редактирование общего скрипта

    @Element("Модальное окно Создание общего скрипта")
    @FindBy(xpath = "//mat-dialog-container//app-general-script-create-form")
    public TextBlock appCreateModal;

    @Element("Выпадающий список Причина назначения. Критерий отправки на СЗ (Создание общего скрипта)")
    @FindBy(xpath = "//app-general-script-create-form//mat-form-field[.//psb-text[text()='Причина назначения. Критерий отправки на СЗ']]//mat-select")
    public TextBlock reasonForAssignmentCriteriaDropDown;

    @Element("Выпадающий список Причина назначения. Результаты проверок (Создание общего скрипта)")
    @FindBy(xpath = "//app-general-script-create-form//mat-form-field[.//psb-text[text()='Причина назначения. Результаты проверок']]//mat-select")
    public TextBlock reasonForAssignmentResultDropDown;

    @Element("Выпадающий список Продукт (Создание общего скрипта)")
    @FindBy(xpath = "//app-general-script-create-form//mat-form-field[.//psb-text[text()='Продукт']]//mat-select")
    public TextBlock productInEFDropDown;

    @Element("Выпадающий список Канал поступления (Создание общего скрипта)")
    @FindBy(xpath = "//app-general-script-create-form//mat-form-field[.//psb-text[text()='Канал поступления']]//mat-select")
    public TextBlock comeSourceInEFDropDown;

    @Element("Выпадающий список Сегмент (Создание общего скрипта)")
    @FindBy(xpath = "//app-general-script-create-form//mat-form-field[.//psb-text[text()='Сегмент']]//mat-select")
    public TextBlock segmentDropInEFDown;

    @Element("Выпадающий список Форма подтверждения дохода (Создание общего скрипта)")
    @FindBy(xpath = "//app-general-script-create-form//mat-form-field[.//psb-text[text()='Форма подтверждения дохода']]//mat-select")
    public TextBlock formIncomeTrustInEFDropDown;

    @Element("Выпадающий список Статус клиента (Создание общего скрипта)")
    @FindBy(xpath = "//app-general-script-create-form//mat-form-field[.//psb-text[text()='Статус клиента']]//mat-select")
    public TextBlock clientStatusInEFDropDown;

    @Element("Выпадающий список Тип прозвона (Создание общего скрипта)")
    @FindBy(xpath = "//app-general-script-create-form//mat-form-field[.//psb-text[text()='Тип прозвона']]//mat-select")
    public TextBlock callTypeInEFDropDown;

    @Element("Кнопка Сохранить (Создание общего скрипта)")
    @FindBy(xpath = "//app-button//span[normalize-space()='Сохранить']")
    public Button saveMainScriptButton;

    @Element("Кнопка Закрыть (Создание общего скрипта)")
    @FindBy(xpath = "//mat-dialog-container//span[@nztype= 'icons:close']")
    public Button closeMainScriptButton;

    // Форма Тип прозвона

    @Element("Форма Тип прозвона")
    @FindBy(xpath = "//app-employer-call-form|//app-client-call-form|//app-client-call-form|//app-spouse-call-form|//app-contact-person-call-form")
    public TextBlock typeCallModal;

    @Element("Выпадающий список Основной скрипт (Тип прозвона)")
    @FindBy(xpath = "//app-select[.//label[normalize-space()='Основной скрипт']]//nz-select")
    public Button mainScriptDropDown;

    @Element("Выпадающий список Текст подсказки (Тип прозвона)")
    @FindBy(xpath = "//app-add-question[.//div[text()='Текст подсказки']]//input")
    public Button helpTextDropDown;

    @Element("Выпадающий список Доп. вопрос (Тип прозвона)")
    @FindBy(xpath = "//app-add-question[.//div[normalize-space()='Доп. вопрос']]//input")
    public Button additionalQuestionDropDown;

    @Element("Выпадающий список Доп. вопрос 2 (Тип прозвона)")
    @FindBy(xpath = "(//app-add-question[.//div[normalize-space()='Доп. вопрос']]//input)[2]")
    public Button additionalQuestion2DropDown;

    @Element("Кнопка Добавить вопрос у Доп. вопрос (Тип прозвона)")
    @FindBy(xpath = "//app-add-question[.//div[normalize-space()='Доп. вопрос']]//app-button//span[normalize-space()='Добавить вопрос']")
    public Button addQuestionAddQuestionButton;

    @Element("Выпадающий список Ситуационный вопрос (Тип прозвона)")
    @FindBy(xpath = "//app-select[.//label[normalize-space()='Ситуационный вопрос']]//nz-select")
    public Button situationalQuestionDropDown;

    @Element("Выпадающий список Основной скрипт. Подтвержденный (Звонок работодателю)")
    @FindBy(xpath = "//app-employer-call-form//app-select[.//label[normalize-space()='Основной скрипт. Подтвержденный']]//nz-select")
    public Button mainScriptConfirmedDropDown;

    @Element("Выпадающий список Доп. вопрос. Подтвержденный (Звонок работодателю)")
    @FindBy(xpath = "//app-employer-call-form//app-add-question[.//div[text()='Доп. вопрос. Подтвержденный']]//input")
    public Button additionalQuestionConfirmedDropDown;

    @Element("Выпадающий список Доп. вопрос. Неподтвержденный (Звонок работодателю)")
    @FindBy(xpath = "//app-employer-call-form//app-add-question[.//div[text()='Доп. вопрос. Неподтвержденный']]//input")
    public Button additionalQuestionUnconfirmedDropDown;

    @Element("Выпадающий список Проф. вопрос (Звонок клиенту)")
    @FindBy(xpath = "//app-client-call-form//app-select[.//label[normalize-space()='Проф. вопрос']]//nz-select")
    public Button profQuestionDropDown;

    @Element("Выпадающий список Доп. вопрос. Основное (Звонок клиенту)")
    @FindBy(xpath = "//app-client-call-form//app-add-question[.//div[text()='Доп. вопрос. Основное']]//input")
    public Button additionalQuestionMainDropDown;

    @Element("Выпадающий список Доп. вопрос. Совместительство (Звонок клиенту)")
    @FindBy(xpath = "//app-client-call-form//app-add-question[.//div[text()='Доп. вопрос. Совместительство']]//input")
    public Button additionalQuestionPartDropDown;

    // ЭФ Создание/Редактирование основного скрипта

    @Element("Модальное окно Создание основного скрипта")
    @FindBy(xpath = "//mat-dialog-container/app-main-script-modal")
    public TextBlock appMainScriptModal;

    @Element("Поле ввода Наименование скрипта (Создание основного скрипта)")
    @FindBy(xpath = "//app-main-script-modal//section[./label[text()='Наименование скрипта']]/input")
    public TextInput scriptNameInModalTextInput;

    @Element("Выпадающий список Категория скрипта (Создание основного скрипта)")
    @FindBy(xpath = "//app-main-script-modal//section[./label[text()='Категория скрипта']]//nz-select")
    public Button scriptCategoryInModalDropDown;

    @Element("Выпадающий список Текст скрипта (Создание основного скрипта)")
    @FindBy(xpath = "//app-main-script-modal//app-add-question[.//div[text()='Текст скрипта']]//input")
    public TextInput scriptTextDropDown;

    @Element("Выпадающий список Текст скрипта 2 (Создание основного скрипта)")
    @FindBy(xpath = "(//app-main-script-modal//app-add-question[.//div[text()='Текст скрипта']]//input)[2]")
    public TextInput scriptText2DropDown;

    @Element("Кнопка Добавить (Создание основного скрипта)")
    @FindBy(xpath = "//app-main-script-modal//app-button//span[normalize-space()='Добавить']")
    public Button addInModalButton;

    @Element("Кнопка Сохранить (Создание основного скрипта)")
    @FindBy(xpath = "//app-main-script-modal//app-button//span[normalize-space()='Сохранить']")
    public Button saveInModalButton;

    // ЭФ Подтверждение удаления

    @Element("Модальное окно Подтверждение удаления")
    @FindBy(xpath = "//mat-dialog-container/app-confirm-dialog")
    public TextBlock deletionModal;

    @Element("Кнопка Удалить (Подтверждение удаления)")
    @FindBy(xpath = "//mat-dialog-container/app-confirm-dialog//app-button//span[normalize-space()='Удалить']")
    public Button confirmDeletionButton;

    // ЭФ Информация об ошибке

    @Element("Модальное окно Информация об ошибке")
    @FindBy(xpath = "//mat-dialog-container/app-error-dialog")
    public TextBlock errorModal;

    @Element("Кнопка Ок (Информация об ошибке)")
    @FindBy(xpath = "//mat-dialog-container/app-error-dialog//app-button//span[normalize-space()='Ок']")
    public Button okButton;

    @Step
    @Title("Получить количество строк в таблице")
    public int getRowCountFromTable(String tableTitle) {
        return getRowCount(getElementByTitle(tableTitle));
    }

    @Step
    @Title("Нажать на крестик у выпадающего списка {textBlock} в ЭФ")
    public ScriptConfigurationPage clickOnCrossNearTextBlock(String modalName, String textBlock, String... blockNumber) {
        String num = "1";
        if (blockNumber != null && blockNumber.length != 0) {
            num = blockNumber[0];
        }
        SelenideElement element = ((TypifiedWebElement) getElementByTitle(modalName)).getSelenideElement().shouldBe(Condition.visible);
        element.findElement(By.xpath("(.//app-add-question[.//div[text()='" + textBlock + "']]//i[@nztype='delete' or @nztype='icons:close'])[" + num + "]")).click();
        if (!element.findElement(By.xpath(".//app-add-question[.//div[text()='" + textBlock + "']]//input")).getText().isBlank()) {
            throw new ElementInteractionException("Не удалось очистить поле " + textBlock);
        }
        return getSelf();
    }

    @Step
    @Title("выбрать из выпадающего списка {titleDropDownList} значение - {value}")
    @Description("Выбор одного значения в выпадающем списке")
    public ScriptConfigurationPage selectValueFromDropDownList(String titleDropDownList, String value, boolean hasScroll) {
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
        return getSelf();
    }
}
