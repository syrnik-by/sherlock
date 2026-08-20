package ru.autotestframework.pages.employees;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.w3c.dom.Text;
import ru.autotestframework.pages.components.TopBar;
import ru.autotestframework.pages.settings.PrioritySettingsPage;
import ru.autotestframework.ui_core.exceptions.ElementInteractionException;
import ru.autotestframework.ui_core.page_manager.Element;
import ru.autotestframework.ui_core.page_manager.PageEntry;
import ru.autotestframework.web_elements.elements.Button;
import ru.autotestframework.web_elements.elements.TextBlock;
import ru.autotestframework.web_elements.elements.TextInput;
import ru.autotestframework.web_elements.elements.typified.TypifiedWebElement;
import ru.psb.testit.annotations.Step;
import ru.psb.testit.annotations.Title;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import static com.codeborne.selenide.Selenide.$x;

@PageEntry(title = "Страница Карточка сотрудника")
public class CardEmployeePage extends TopBar<CardEmployeePage> {

    @Element("Вкладка Общая информация")
    @FindBy(xpath = "//span[contains(text(), 'Общая информация')]")
    public TextBlock tabGeneralInformationTextBlock;

    @Element("Вкладка Роли / Шаблоны")
    @FindBy(xpath = "//span[contains(text(), 'Роли / Шаблоны')]")
    public TextBlock tabRoleTemplatesTextBlock;

    //  Раздел Общая информация
    @Element("Статус пользователя в системе")
    @FindBy(xpath = "//div[contains(@class,'status-value')]")
    public TextBlock statusEmployeeInSystemTextBlock;

    @Element("Время последнего входа в ЛКА")
    @FindBy(xpath = "//div[contains(@class,'status-lastLogin')]/span")
    public TextBlock timeEntryInLkaTextBlock;

    @Element("Статус пользователя")
    @FindBy(xpath = "//div[contains(@class,'profile-header')]//label//mat-label")
    public TextBlock statusEmployeeTextBlock;

    @Element("Поле ввода Руководитель")
    @FindBy(xpath = "//div[contains(@class,'psbInput-text')][./div[contains(text(), 'Руководитель')]]")
    public TextInput leaderTextInput;

    @Element("Поле ввода Утверждающий")
    @FindBy(xpath = "//div[contains(@class,'psbInput-text')][./div[contains(text(), 'Утверждающий')]]")
    public TextInput approvingTextInput;

    @Element("Поле ввода Функциональный руководитель")
    @FindBy(xpath = "//div[contains(@class,'psbInput-text')][./div[contains(text(), 'Функциональный руководитель')]]")
    public TextInput functionLeaderTextInput;

    @Element("Поле ввода Регион")
    @FindBy(xpath = "//div[contains(@class,'psbInput-text')][./div[contains(text(), 'Регион')]]")
    public TextInput regionTextInput;

    @Element("Поле ввода Группы")
    @FindBy(xpath = "//div[contains(@class,'psbInput-text')][./div[contains(text(), 'Группы')]]")
    public TextInput groupTextInput;

    @Element("Поле ввода Начало работы в андеррайтинге")
    @FindBy(xpath = "//div[contains(@class,'psbInput-text')][./div[contains(text(), 'Начало работы в андеррайтинге')]]")
    public TextInput beginningOfWorkTextInput;

    @Element("Поле ввода Стаж в андеррайтинге")
    @FindBy(xpath = "//div[contains(@class,'psbInput-text')][./div[contains(text(), 'Стаж в андеррайтинге')]]")
    public TextInput experienceInUnderwriterTextInput;

    @Element("Поле ввода Дата заведения в ЛКА")
    @FindBy(xpath = "//div[contains(@class,'psbInput-text')][./div[contains(text(), 'Дата заведения в ЛКА')]]")
    public TextInput dateCreateInLkaTextInput;

    @Element("Поле ввода Тип графика")
    @FindBy(xpath = "//div[contains(@class,'psbInput-text')][./div[contains(text(), 'Тип графика')]]")
    public TextInput typeScheduleTextInput;

    @Element("Поле ввода Категория рассмотрения")
    @FindBy(xpath = "//div[contains(@class,'psbInput-text')][./div[contains(text(), 'Категория рассмотрения')]]")
    public TextInput categoryReviewTextInput;

    @Element("Поле ввода Категория принятия решения")
    @FindBy(xpath = "//div[contains(@class,'psbInput-text')][./div[contains(text(), 'Категория принятия решения')]]")
    public TextInput categoryDecisionTextInput;

    @Element("Блок Роли / Шаблоны")
    @FindBy(xpath = "//div[contains(@class,'profile-contentRow-elem')][.//span[contains(text(), 'Роли / Шаблоны')]]")
    public TextBlock blockRoleTemplatesTextBlock;

    @Element("Блок Процессная функция")
    @FindBy(xpath = "//div[contains(@class,'profile-contentRow-elem')][.//span[contains(text(), 'Процессная функция')]]/div")
    public TextBlock statusProcessFunctionTextBlock;

    @Element("Процессная функция - Редактировать")
    @FindBy(xpath = "//div[contains(@class,'profile-contentRow-elem')][.//span[contains(text(), 'Процессная функция')]]/span[contains(text(),'Редактировать')]")
    public Button editProcessFunctionButton;

    // ЭФ Назначение процессной функции

    @Element("Модальное окно Назначение процессной функции")
    @FindBy(xpath = "//mat-dialog-container[.//span[text()='Назначение процессной функции']]")
    public TextBlock assignmentProcessFunctionModal;

    @Element("Блок Функции (Назначение процессной функции)")
    @FindBy(xpath = "//mat-dialog-container//mat-chip-list")
    public TextBlock functionTextBlock;

    @Element("Выпадающий список Название функции (Назначение процессной функции)")
    @FindBy(xpath = "//mat-dialog-container//button[contains(@class, 'select-button')]")
    public Button functionNameDropDown;

    @Element("Кнопка Добавить (Назначение процессной функции)")
    @FindBy(xpath = "//mat-dialog-container//button//span[normalize-space()='Добавить']")
    public Button addInModalProcessFunctionButton;

    @Element("Кнопка Сохранить (Назначение процессной функции)")
    @FindBy(xpath = "//mat-dialog-container//button//span[normalize-space()='Сохранить']")
    public Button saveInModalProcessFunctionButton;

    @Element("Кнопка Регион - Редактировать")
    @FindBy(xpath = "//div[contains(@class,'profile-contentRow')][.//div[contains(text(), 'Регион')]]//span[contains(text(),'Редактировать')]")
    public Button editRegionButton;

    @Element("Кнопка Регион - Сохранить")
    @FindBy(xpath = "//div[contains(@class,'profile-contentRow')][.//span[contains(text(), 'Регион')]]//span[contains(text(), 'Сохранить')]")
    public Button saveRegionButton;

    @Element("Кнопка Регион - Отменить")
    @FindBy(xpath = "//div[contains(@class,'profile-contentRow')][.//span[contains(text(), 'Регион')]]//span[contains(text(), 'Отменить')]")
    public Button cancelRegionButton;

    @Element("Выпадающий список Регион")
    @FindBy(xpath = "//div[contains(@class,'psbInput-modal')][.//span[contains(text(), 'Регион')]]/button")
    public Button editRegionDropDown;

    @Element("Поле Регион")
    @FindBy(xpath = "//div[contains(@class,'psbInput')][.//div[contains(text(), 'Регион')]]/span[text()]")
    public TextBlock regionTextBlock;

    @Element("Кнопка Категория рассмотрения - Редактировать")
    @FindBy(xpath = "//div[@class='editingField'][.//div[text()='Категория рассмотрения']]//span[contains(text(),'Редактировать')]")
    public Button editCategoryOfConsiderationButton;

    @Element("Кнопка Категория рассмотрения - Сохранить")
    @FindBy(xpath = "//div[contains(@class,'profile-contentRow')][.//span[contains(text(), 'Категория рассмотрения')]]//span[contains(text(), 'Сохранить')]")
    public Button saveCategoryOfConsiderationButton;

    @Element("Кнопка Категория рассмотрения - Отменить")
    @FindBy(xpath = "//div[contains(@class,'profile-contentRow')][.//span[contains(text(), 'Категория рассмотрения')]]//span[contains(text(), 'Отменить')]")
    public Button cancelCategoryOfConsiderationButton;

    @Element("Выпадающий список Категория рассмотрения")
    @FindBy(xpath = "//div[contains(@class,'psbInput-modal')][.//span[contains(text(), 'Категория рассмотрения')]]/button")
    public Button editCategoryOfConsiderationDropDown;

    @Element("Поле Категория рассмотрения")
    @FindBy(xpath = "//div[contains(@class,'psbInput')][.//div[contains(text(), 'Категория рассмотрения')]]/span[text()]")
    public TextBlock categoryOfConsiderationTextBlock;

    //  Раздел Роли и шаблоны
    @Element("Кнопка Привязать")
    @FindBy(xpath = "//button[./span[contains(text(), 'Привязать')]]")
    public Button bindButton;

    @Element("Кнопка Отвязать")
    @FindBy(xpath = "//button[./span[contains(text(), 'Отвязать')]]")
    public Button untieButton;

    @Element("Блок Список шаблонов роли")
    @FindBy(xpath = "//div[contains(@class, 'app-permission-templates-list-root')]")
    public TextBlock listTemplatesOfRoleTextBlock;

    @Element("Кнопка Привязать (Привязка шаблонов)")
    @FindBy(xpath = "//mat-dialog-container//app-button//span[normalize-space()='Привязать']")
    public Button bindTemplateButton;

    @Element("Кнопка Отвязать (Отвязка шаблонов)")
    @FindBy(xpath = "//mat-dialog-container//app-button//span[normalize-space()='Отвязать']")
    public Button untieTemplateButton;

    @Element("Блок Список шаблонов роли (Привязка шаблонов)")
    @FindBy(xpath = "//mat-dialog-container//div[contains(@class, 'app-permission-templates-list-root')]")
    public TextBlock listTemplatesOfRoleInModalTextBlock;

    @Element("Кнопка Разрешения настроены")
    @FindBy(xpath = "//label[.//mat-label[contains(text(), 'Разрешения настроены')]]")
    public Button untiePermissionsConfiguredButton;

    @Element("Строка поиска по наименованию Шаблона")
    @FindBy(xpath = "//input[contains(@class,'search-permissions')]")
    public TextInput inputNameTemplatesTextInput;

    @Element("Кнопка Найти(блок Шаблон разрешений)")
    @FindBy(xpath = "//app-permission-templates-list//button[./span[contains(text(), 'Найти')]]")
    public Button searchPermissionsButton;

    @Element("Блок с шаблонами")
    @FindBy(xpath = "//div[contains(@class,'app-permission-templates-list-root')]")
    public TextBlock blockWithTemplatesTextBlock;

    @Element("Строка поиска по наименованию Разрешения")
    @FindBy(xpath = "//input[contains(@class,'search-templates')]")
    public TextInput inputSearchNamePermissionsTextInput;

    @Element("Поле ввода Название шаблона")
    @FindBy(xpath = "//div[contains(@class,'psbInput-text')][./div[contains(text(), 'Название шаблона')]]")
    public TextInput inputNameTemplateTextInput;

    @Element("Кнопка Найти(блок Название шаблона)")
    @FindBy(xpath = "//div[contains(@class,'right-column')]//button[./span[contains(text(), 'Найти')]]")
    public Button searchTemplatesButton;

    @Element("Кнопка Кому назначен шаблон")
    @FindBy(xpath = "//button[./span[contains(text(), 'Кому назначен шаблон')]]")
    public Button templateIsAssignedButton;

    @Element("Таблица с наименованием Разрешение")
    @FindBy(xpath = "//app-permissions-list//table[.//span[contains(text(), 'Разрешение')]]")
    public TextBlock tableWithNamePermissionTextBlock;

    @Element("Кнопка Удалить ПФ Андеррайтинг (модальное окно Назначение ПФ)")
    @FindBy(xpath = "//mat-chip[contains(text(),'Андеррайтинг')]/mat-icon")
    public Button deletePfUnderButton;

    @Element("Кнопка Удалить ПФ ФССП (модальное окно Назначение ПФ)")
    @FindBy(xpath = "//mat-chip[contains(text(),'ФССП')]/mat-icon")
    public Button deletePfFsspButton;

    @Element("Блок ПФ (модальное окно Назначение ПФ)")
    @FindBy(xpath = "//mat-chip-list")
    public TextBlock blockPfTextBlock;

    @Element("Кнопка Сохранить (модальное окно Назначение ПФ)")
    @FindBy(xpath = "//button/span[contains(text(),'Сохранить')]")
    public Button saveOnModalAssignPfButton;

    @Element("Выпадающий список Название функции (модальное окно Назначение ПФ)")
    @FindBy(xpath = "//div[@class='select']/button")
    public Button nameOfPfButton;

    @Element("Кнопка Добавить (модальное окно Назначение ПФ)")
    @FindBy(xpath = "//app-button//span[contains(text(),'Добавить')]")
    public Button addPfButton;

    // ЭФ Параметры разрешения

    @Element("Модальное окно Параметры разрешения")
    @FindBy(xpath = "//mat-dialog-container[.//span[text()='Параметры разрешения']]")
    public TextBlock accessSettingsModal;

    @Element("Кнопка Сохранить (Параметры разрешения)")
    @FindBy(xpath = "//mat-dialog-container//app-button[normalize-space()='Сохранить']")
    public TextBlock saveInModalButton;

    @Step
    @Title("Раскрытие списка {listName}")
    public CardEmployeePage openListViaClick(String listName, String role) {
        SelenideElement element = ((TypifiedWebElement) getElementByTitle(listName)).getSelenideElement().shouldBe(Condition.visible);
        WebElement icon = element.findElement(By.xpath(".//span[normalize-space()='" + role + "']/preceding-sibling::mat-icon"));
        icon.click();
        return this;
    }

    public CardEmployeePage selectTemplateNoStep(String listName, String templateName) {
        try {
            SelenideElement selenideElement = ((TypifiedWebElement) getElementByTitle(listName)).getSelenideElement().shouldBe(Condition.visible);
            List<WebElement> icon = selenideElement.findElements(By.xpath(".//span[normalize-space()='" + templateName + "']/preceding-sibling::mat-checkbox"));
            if (!icon.isEmpty() && icon.get(0).isDisplayed()) {
                WebElement checkBox = icon.get(0);
                checkBox.click();
            } else {
                selenideElement.findElement(By.xpath(".//span[normalize-space()='" + templateName + "']")).click();
            }
        } catch (Throwable e) {
            // если шаблон с таким названием не находится, то возвращаем nulll
            return null;
        }
        return this;
    }

    @Step
    @Title("Выбрать шаблон {templateName}")
    public CardEmployeePage selectTemplate(String listName, String templateName) {
        if (selectTemplateNoStep(listName, templateName) == null) {
            throw new ElementInteractionException("Шаблон с названием " + templateName + " не найден в блоке listName");
        }
        return this;
    }

    @Step
    @Title("Выбрать подразделение {unitName} в ЭФ Параметры разрешения")
    public CardEmployeePage selectUnit(String unitName) {
        SelenideElement ef = accessSettingsModal.getSelenideElement().shouldBe(Condition.visible);
        WebElement unit = ef.findElement(By.xpath(".//mat-radio-button//span[contains(text(), '" + unitName + "')]"));
        unit.click();
        return this;
    }

    @Step
    @Title("Нажать на крестик у функции {functionName} в ЭФ Назначение процессной функции")
    public CardEmployeePage deleteFunctionFromList(String functionName) {
        assignmentProcessFunctionModal.getSelenideElement().shouldBe(Condition.visible);
        SelenideElement unit = assignmentProcessFunctionModal.getSelenideElement().$x(".//mat-chip-list//mat-chip[normalize-space()='" + functionName + "']/mat-icon");
        unit.click();
        unit.shouldBe(Condition.disappear);
        return this;
    }
}
