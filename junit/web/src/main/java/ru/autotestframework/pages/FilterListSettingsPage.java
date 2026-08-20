package ru.autotestframework.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.FindBy;
import ru.autotestframework.ui_core.exceptions.ElementInteractionException;
import ru.autotestframework.ui_core.page_manager.Element;
import ru.autotestframework.ui_core.page_manager.PageEntry;
import ru.autotestframework.ui_core.services.table_service.FindCellsBy;
import ru.autotestframework.ui_core.services.table_service.FindHeadersBy;
import ru.autotestframework.web_elements.elements.Button;
import ru.autotestframework.web_elements.elements.TextBlock;
import ru.autotestframework.web_elements.elements.WebTable;
import ru.psb.testit.annotations.Step;
import ru.psb.testit.annotations.Title;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$x;

@PageEntry(title = "Модальное окно Настройка фильтров/списка")
public class FilterListSettingsPage extends BasePage<FilterListSettingsPage> {

    // Настройка фильтров(модальное окно)

    @Element("Кнопка Сбросить")
    @FindBy(xpath = "//mat-dialog-container//button[./span[contains(text(), 'Сбросить')]]")
    public Button skipFiltersButton;

    @Element("Кнопка Настройка списка")
    @FindBy(xpath = "//div[contains(@class, 'table-header-actions')]//button[.//span[contains(text(), 'Настройка списка')]]")
    public Button listCustomizationButton;

    @Element("Кнопка Закрыть окно фильтров")
    @FindBy(xpath = "//mat-dialog-container//mat-icon")
    public Button closeFiltersButton;

    @Element("Список активных фильтров")
    @FindBy(xpath = "//div[@id='activeColumnsList']")
    public TextBlock listActiveFiltersTextBlock;

    @Element("Список не активных фильтров")
    @FindBy(xpath = "//div[@id='inactiveColumnsList']")
    public TextBlock listNoActiveFiltersTextBlock;

    @Element("Кнопка Сбросить")
    @FindBy(xpath = "//div[contains(@class, 'cdk-global-overlay-wrapper')]//button[.//span[contains(text(), 'Сбросить')]]")
    public Button resetButton;

    @Element("Таблица Настройка списка")
    @FindBy(xpath = "//table[contains(@class, 'mat-table')]")
    @FindCellsBy(xpath = ".//table//tr")
    @FindHeadersBy(xpath = ".//th/div")
    public WebTable settingsFilterTable;

    private static final String DIALOG_CONTAINER_LOCATOR = "//mat-dialog-container";

    @Step
    @Title("Выполнить перемещение столбцов {columns} {action} в модальном окне \"Настройки столбцов\"")
    public FilterListSettingsPage dragColumns(String action, List<String> columns) {
        activeOrNoActiveElement(action, columns);
        return this;
    }

    @Step
    @Title("Получить {typeListFilters}")
    public List<String> getFiltersname(String typeListFilters) {
        return new ArrayList<>(List.of(getElementByTitle(typeListFilters)
                .getText().split("\n")));
    }

    public void activeOrNoActiveElement(String action, List<String> elementTitlesFromList) {
        resetButton.click();
        String activ = "из правой колонки в левую";
        String typeElementFrom = activ.equals(action) ?
                "inactiveColumnsList" :
                "activeColumnsList";
        String typeListTo = activ.equals(action) ?
                "activeColumnsList" :
                "inactiveColumnsList";
        SelenideElement elementTo = $x(DIALOG_CONTAINER_LOCATOR).$x("descendant::div[@id='" + typeListTo + "']").shouldBe(Condition.visible);
        int x = elementTo.getLocation().x;
        int y = elementTo.getLocation().y;

        for (String elementTitle : elementTitlesFromList) {
            SelenideElement elementFrom = $x(DIALOG_CONTAINER_LOCATOR)
                    .$x("descendant::div[@id='" + typeElementFrom + "']//div[normalize-space()='" + elementTitle + "']");
            if (elementFrom.exists()) {
                executeJavaScript("arguments[0].scrollIntoView(true);", elementFrom);
                try {
                    do {
                        actions().moveToElement(elementFrom)
                                .clickAndHold(elementFrom)
                                .pause(100)
                                .moveByOffset(x, y)
                                .pause(100)
                                .moveToElement(elementTo)
                                .pause(100)
                                .release().build().perform();
                    } while (elementFrom.exists());
                } catch (MoveTargetOutOfBoundsException e) {
                    System.err.println("Ошибка: элемент вне границ видимости. " + e.getMessage());
                }
            } else {
                throw new ElementInteractionException("Элемент не найден: " + elementTitle);
            }
        }
    }

    @Step
    @Title("сбросить все параметры колонок")
    public FilterListSettingsPage resetFilters() {
        resetButton.click();
        closeFiltersButton.click();
        listActiveFiltersTextBlock.shouldBe(Condition.disappear, true);
        return this;
    }
}