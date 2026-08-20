package ru.autotestframework.pages.setting_control;

import org.openqa.selenium.support.FindBy;
import org.springframework.stereotype.Component;
import ru.autotestframework.pages.BasePage;
import ru.autotestframework.ui_core.page_manager.Element;
import ru.autotestframework.ui_core.page_manager.PageEntry;
import ru.autotestframework.ui_core.services.table_service.FindCellsBy;
import ru.autotestframework.ui_core.services.table_service.FindHeadersBy;
import ru.autotestframework.web_elements.elements.Button;
import ru.autotestframework.web_elements.elements.TextBlock;
import ru.autotestframework.web_elements.elements.TextInput;
import ru.autotestframework.web_elements.elements.WebTable;
import ru.psb.testit.annotations.Step;
import ru.psb.testit.annotations.Title;

@Component
@PageEntry(title = "Страница Настройки назначения стратегий")
public class AssignmentStrategyConfiguration extends BasePage<AssignmentStrategyConfiguration> {

    // Вкладки

    @Element("Вкладка Назначение стратегий")
    @FindBy(xpath = "//app-link-menu//a[normalize-space()='Назначение стратегий']")
    public Button assignmentStrategyButton;

    @Element("Вкладка Специальные критерии назначения")
    @FindBy(xpath = "//app-link-menu//a[normalize-space()='Специальные критерии назначения']")
    public Button specialAssignmentCriteriaButton;

    @Element("Вкладка Правила обработки результата")
    @FindBy(xpath = "//app-link-menu//a[normalize-space()='Правила обработки результата']")
    public Button resultComputingRulesButton;

    // Вкладки выбора версии

    @Element("Вкладка Текущая версия")
    @FindBy(xpath = "//div[@role='tab']//span[normalize-space()='Текущая версия']")
    public Button currentVersionButton;

    @Element("Вкладка Редактируемая версия")
    @FindBy(xpath = "//div[@role='tab']//span[normalize-space()='Редактируемая версия']")
    public Button redactionVersionButton;

    // Фильтры

    @Element("Выпадающий список Продукт")
    @FindBy(xpath = "//app-multi-select[.//psb-text[text()='Продукт']]//mat-select")
    public Button productDropDown;

    @Element("Выпадающий список Причина назначения")
    @FindBy(xpath = "//app-multi-select[.//psb-text[text()='Причина назначения']]//mat-select")
    public Button reasonForAssignmentDropDown;

    @Element("Выпадающий список Сегмент")
    @FindBy(xpath = "//app-multi-select[.//psb-text[text()='Сегмент']]//mat-select")
    public Button segmentDropDown;

    @Element("Кнопка Найти")
    @FindBy(xpath = "//app-button//span[normalize-space()='Найти']")
    public Button findButton;

    @Element("Кнопка Удалить все")
    @FindBy(xpath = "//span[normalize-space()='Удалить все']")
    public Button deleteAllButton;

    // Кнопки работы с версиями

    @Element("Кнопка Опубликовать версию")
    @FindBy(xpath = "//app-button//span[normalize-space()='Опубликовать версию']")
    public Button publishVersionButton;

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

    // ЭФ Опубликовать версию

    @Element("Модальное окно Опубликовать версию")
    @FindBy(xpath = "//mat-dialog-container/app-create-version-popup")
    public TextBlock publishVersionModal;

    @Element("Переключатель Опубликовать сразу (Опубликовать версию)")
    @FindBy(xpath = "//mat-dialog-container/app-create-version-popup//nz-switch")
    public Button publishNowToggle;

    @Element("Поле ввода Опубликовать позже (Опубликовать версию)")
    @FindBy(xpath = "//mat-dialog-container//app-datepicker-with-time//input")
    public TextInput publishLaterTextInput;

    @Element("Кнопка Опубликовать (Опубликовать версию)")
    @FindBy(xpath = "//mat-dialog-container/app-create-version-popup//app-button//span[normalize-space()='Опубликовать']")
    public Button publishButton;

    // ЭФ Подтверждение публикцаии

    @Element("Модальное окно Подтверждение публикации")
    @FindBy(xpath = "//mat-dialog-container/app-confirm-dialog")
    public TextBlock confirmPublishModal;

    @Element("Кнопка Опубликовать (Подтверждение публикации)")
    @FindBy(xpath = "//mat-dialog-container/app-confirm-dialog//app-button//span[normalize-space()='Опубликовать']")
    public Button confirmPublishingButton;

    // ЭФ Информация об ошибке

    @Element("Модальное окно Информация об ошибке")
    @FindBy(xpath = "//mat-dialog-container/app-error-dialog")
    public TextBlock errorModal;

    @Element("Кнопка Ок (Информация об ошибке)")
    @FindBy(xpath = "//mat-dialog-container/app-error-dialog//app-button//span[normalize-space()='Ок']")
    public Button okButton;

    // ЭФ Создание/Редактирование правила назначения стратегии

    @Element("Модальное окно Создание правила назначения стратегии")
    @FindBy(xpath = "//mat-dialog-container/app-strategies-assignments-modal")
    public TextBlock createRuleOfAssignmentStrategyModal;

    @Element("Выпадающий список Продукт (Создание правила назначения стратегии)")
    @FindBy(xpath = "//app-strategies-assignments-modal//app-multi-select[.//psb-text[text()='Продукт']]//mat-select")
    public Button productInModalDropDown;

    @Element("Выпадающий список Причина назначения (Создание правила назначения стратегии)")
    @FindBy(xpath = "//app-strategies-assignments-modal//app-multi-select[.//psb-text[text()='Причина назначения']]//mat-select")
    public Button reasonForAssignmentInModalDropDown;

    @Element("Выпадающий список Сегмент (Создание правила назначения стратегии)")
    @FindBy(xpath = "//app-strategies-assignments-modal//app-multi-select[.//psb-text[text()='Сегмент']]//mat-select")
    public Button segmentInModalDropDown;

    @Element("Выпадающий список Стратегия для назначения (Создание правила назначения стратегии)")
    @FindBy(xpath = "//app-strategies-assignments-modal//app-multi-select[.//psb-text[text()='Стратегия для назначения']]//mat-select")
    public Button strategyForAssignmentDropDown;

    @Element("Переключатель ФССП (Создание правила назначения стратегии)")
    @FindBy(xpath = "//app-strategies-assignments-modal//tr[./td[text()='ФССП']]//nz-switch")
    public Button fsspSwitch;

    @Element("Переключатель Проверка сайта (Создание правила назначения стратегии)")
    @FindBy(xpath = "//app-strategies-assignments-modal//tr[./td[text()='Проверка сайта']]//nz-switch")
    public Button checkWebsiteSwitch;

    @Element("Кнопка Сохранить (Создание правила назначения стратегии)")
    @FindBy(xpath = "//app-strategies-assignments-modal//app-button//span[normalize-space()='Сохранить']")
    public Button saveInModalButton;

    // ЭФ Подтверждение удаления

    @Element("Модальное окно Подтверждение удаления")
    @FindBy(xpath = "//mat-dialog-container/app-confirm-dialog")
    public TextBlock deletionModal;

    @Element("Кнопка Удалить (Подтверждение удаления)")
    @FindBy(xpath = "//mat-dialog-container/app-confirm-dialog//app-button//span[normalize-space()='Удалить']")
    public Button confirmDeletionButton;

    @Step
    @Title("Получить количество строк в таблице")
    public int getRowCountFromTable(String tableTitle) {
        return getRowCount(getElementByTitle(tableTitle));
    }
}
