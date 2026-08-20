package ru.autotestframework.pages.settings;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.stereotype.Component;
import ru.autotestframework.pages.BasePage;
import ru.autotestframework.ui_core.page_manager.Element;
import ru.autotestframework.ui_core.page_manager.PageEntry;
import ru.autotestframework.ui_core.services.table_service.FindCellsBy;
import ru.autotestframework.ui_core.services.table_service.FindHeadersBy;
import ru.autotestframework.web_elements.elements.*;
import ru.psb.testit.annotations.Step;
import ru.psb.testit.annotations.Title;

import static com.codeborne.selenide.Selenide.$x;

@Component
@PageEntry(title = "Страница Настройки приоритетов")
public class PrioritySettingsPage extends BasePage<PrioritySettingsPage> {

    // Работа с приоритетами

    @Element("Кнопка Добавить")
    @FindBy(xpath = "//app-button//span[normalize-space()='Добавить']")
    public Button addButton;

    @Element("Кнопка Удалить")
    @FindBy(xpath = "//app-button//span[normalize-space()='Удалить']")
    public Button deleteButton;

    @Element("Выпадающий список Этап обработки")
    @FindBy(xpath = "//mat-form-field[contains(@class, 'stage-type-select')]//mat-select")
    public Button computeStageDropDown;

    @Element("Таблица Настройки приоритетов")
    @FindBy(xpath = "//table")
    @FindHeadersBy(xpath = ".//th[@role='columnheader']")
    @FindCellsBy(xpath = ".//td[@role='cell']")
    public WebTable prioritySettingsTable;

    // ЭФ Настройки приоритетов (Открывается при нажатии кнопки Добавить или двойном клике по приоритету)

    @Element("Модальное окно Настройки приоритетов")
    @FindBy(xpath = "//app-edit-priority-settings-page")
    public TextBlock editPriorityModal;

    @Element("Кнопка Сохранить (Настройки приоритетов)")
    @FindBy(xpath = "//app-button//span[normalize-space()='Сохранить']")
    public Button saveButton;

    @Element("Поле ввода Название настройки приоритетов (Настройки приоритетов)")
    @FindBy(xpath = "//span[text()='Настройки приоритетов']/following-sibling::div[contains(@class, 'edit-priority-title')]/input")
    public TextInput namePriorityTextInput;

    @Element("Иконка Переключение редактирования поля (Настройки приоритетов)")
    @FindBy(xpath = "//span[text()='Настройки приоритетов']/following-sibling::div[contains(@class, 'edit-priority-title')]/mat-icon")
    public Image penIcon;

    @Element("Выпадающий список Действует (Настройки приоритетов)")
    @FindBy(xpath = "//mat-form-field[contains(@class, 'is-active')]//mat-select")
    public Button isActiveDropDown;

    @Element("Выпадающий список Этап обработки (Настройки приоритетов)")
    @FindBy(xpath = "//mat-form-field[contains(@class, 'processing-stage')]//mat-select")
    public Button processingStageDropDown;

    @Element("Выпадающий список Сортировка настройки приоритета (Настройки приоритетов)")
    @FindBy(xpath = "//mat-form-field[contains(@class, 'sorting-settings')]//mat-select")
    public Button sortPrioritySettingDropDown;

    @Element("Выпадающий список Тип сортировки (Настройки приоритетов)")
    @FindBy(xpath = "//mat-form-field[contains(@class, 'sorting')][2]//mat-select")
    public Button sortOrderDropDown;

    @Element("Выпадающий список Первый у условия (Настройки приоритетов)")
    @FindBy(xpath = "//mat-form-field[contains(@class, 'first')]//mat-select")
    public Button firstDropDown;

    @Element("Выпадающий список Второй у условия (Настройки приоритетов)")
    @FindBy(xpath = "//mat-form-field[contains(@class, 'second')]//mat-select")
    public Button secondDropDown;

    @Element("Выпадающий список Третий у условия (Настройки приоритетов)")
    @FindBy(xpath = "//mat-form-field[contains(@class, 'third')]//mat-select")
    public Button thirdDropDown;

    @Element("Выпадающий список Первый у сортировки (Настройки приоритетов)")
    @FindBy(xpath = "//mat-form-field[contains(@class, 'criteria')]//mat-select")
    public Button criteriaDropDown;

    @Element("Выпадающий список Второй у сортировки (Настройки приоритетов)")
    @FindBy(xpath = "//mat-form-field[contains(@class, 'sort-type')]//mat-select")
    public Button sortTypeDropDown;

    @Step
    @Title("Клик по ячейке чек-бокса в таблице Настройки приоритетов рядом с названием {text}")
    public PrioritySettingsPage clickOnCheckboxNearName(String text) {
        WebElement element = $x("//td[@role='cell' and (./span[text()='" + text + "'])]/preceding-sibling::td/mat-checkbox").shouldBe(Condition.visible);
        element.click();
        return this;
    }
}

