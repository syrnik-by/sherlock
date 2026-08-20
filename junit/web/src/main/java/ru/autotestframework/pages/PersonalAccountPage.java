package ru.autotestframework.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
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
import ru.autotestframework.web_elements.elements.WebTable;
import ru.psb.testit.annotations.Step;
import ru.psb.testit.annotations.Title;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

import static com.codeborne.selenide.Selenide.$x;
import static ru.autotestframework.util.Validator.assertThat;

@Component
@PageEntry(title = "Страница Личный кабинет")
public class PersonalAccountPage extends TopBar<PersonalAccountPage> {

    @Element("Таблица в работе")
    @FindBy(xpath = "//app-in-progress//table")
    @FindCellsBy(xpath = ".//td[@role='cell']")
    @FindHeadersBy(xpath = ".//th[@role='columnheader']//span[@class='column-name']")
    public WebTable prioritySettingsTable;

    @Element("Таблица Отложено")
    @FindBy(xpath = "//app-postponed//table")
    @FindCellsBy(xpath = ".//td[@role='cell']")
    @FindHeadersBy(xpath = ".//th[@role='columnheader']//span[@class='column-name']")
    public WebTable postPonedTable;

    @Element("Раздел Утверждение")
    @FindBy(xpath = "//div[contains(@class, personal-area-bottom-controls-item)]/span[contains(text(), 'Утверждение')]")
    public TextBlock statement;

    @Element("Таблица в работе Утверждение")
    @FindBy(xpath = "//app-in-progress//div[contains(@class, 'table-wrapper')]")
    @FindCellsBy(xpath = ".//td[@role='cell']")
    @FindHeadersBy(xpath = ".//th[@role='columnheader']//span[@class='column-name']")
    public WebTable prioritySettingsStatementTable;

    @Element("Раздел Андеррайтинг")
    @FindBy(xpath = "//div[contains(@class, personal-area-bottom-controls-item)]/span[contains(text(), 'Андеррайтинг') and not(contains(text(),'Андеррайтинг ГО'))]")
    public TextBlock underwriter;

    @Element("Раздел Верификация")
    @FindBy(xpath = "//div[contains(@class, personal-area-bottom-controls-item)]/span[contains(text(), 'Верификация')]")
    public TextBlock verification;

    @Element("Раздел Вопрос в ГО")
    @FindBy(xpath = "//div[contains(@class, personal-area-bottom-controls-item)]/span[contains(text(), 'Вопрос в ГО')]")
    public TextBlock questionGO;

    @Element("Раздел Проверка сотрудниками ОПМ")
    @FindBy(xpath = "//div[contains(@class, personal-area-bottom-controls-item)]/span[contains(text(), 'Проверка сотрудниками ОПМ')]")
    public TextBlock checkPersonalOPM;

    @Element("Переключатель Новая заявка")
    @FindBy(xpath = "//span[contains(text(), 'Новая заявка')]/../nz-switch")
    public Button newClaimToggle;

    @Element("Поле Количество записей")
    @FindBy(xpath = "//div[@class='mat-paginator-range-label']")
    public TextBlock numberOfRecordsTextBlock;

    @Element("Кнопка Следующая страница таблицы")
    @FindBy(xpath = "//i[@nztype='right']")
    public Button nextTablePageButton;

    @Element("Кнопка Настройка списка(таблица В работе)")
    @FindBy(xpath = "//app-in-progress//button[.//span[contains(text(), 'Настройка списка')]]")
    public Button buttonSettingListProgress;

    @Element("Кнопка Настройка списка(таблица Отложено)")
    @FindBy(xpath = "//app-postponed//button[.//span[contains(text(), 'Настройка списка')]]")
    public Button buttonSettingListPostponed;

    @Element("Кнопка раскрыть таблицу Отложено")
    @FindBy(xpath = "(//app-postponed//i)[1]")
    public Button buttonOpenSettingListPostponed;

    @Element("Выпадающий список Отображать по (таблица В работе)")
    @FindBy(xpath = "//app-in-progress//nz-select")
    public Button displayByDropDownProgress;

    @Element("Выпадающий список Отображать по (таблица Отложено)")
    @FindBy(xpath = "//app-postponed//nz-select")
    public Button displayByDropDownPostPoned;

    @Element("Чек-бокс ЦСКО")
    @FindBy(xpath = "//span[contains(text(), 'ЦСКО')]")
    public TextBlock checkBoxTssko;

    @Element("Чек-бокс ГО")
    @FindBy(xpath = "//span[normalize-space()= 'ГО']")
    public TextBlock checkBoxGo;

    @Element("сортировка Столбец Сумма кредита")
    @FindBy(xpath = "//span[contains(text(), 'Сумма кредита')]/../following-sibling::mat-multi-sort-header")
    public TextBlock columnSortLoanAmount;

    @Element("сортировка Столбец Вид кредита")
    @FindBy(xpath = "//span[contains(text(), 'Вид кредита')]/../following-sibling::mat-multi-sort-header")
    public TextBlock columnSortTypeOfCredit;

    @Element("сортировка Столбец Время попадания на РП")
    @FindBy(xpath = "//span[contains(text(), 'Время попадания на РП')]/../following-sibling::mat-multi-sort-header")
    public TextBlock columnSortTimeRP;

    @Element("Кнопка Сбросить сортировку")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Сбросить сортировку')]]")
    public Button buttonSkipSort;


    @Step
    @Title("Проверить наличие нижнего подчеркивания у заголовка раздела {title}")
    public PersonalAccountPage checkBottomLine(String title) {
        boolean hasAfterElement = (Boolean) ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript(
                "return window.getComputedStyle(arguments[0], '::after').getPropertyValue('content') !== 'none';",
                getElementByTitle(title));
        assertThat(hasAfterElement, "Нижнее подчеркивание для раздела " + title + " не отображается");
        return this;
    }

    @Step
    @Title("Выполнить двойной клик по номеру заявки {claim} в таблице")
    public PersonalAccountPage clickOnTextInTable(String claim) {
        boolean textFound = false;
        do {
            if ($x("//*[contains(text(), '" + claim + "')]").should(Condition.visible).isDisplayed()) {
                doubleClickByText(claim);
                textFound = true;
                break; // Выходим из цикла, если текст найден
            } else {
                if (!clickOnNextPage(numberOfRecordsTextBlock, nextTablePageButton)) {
                    break;
                }
            }
        } while (!textFound);

        if (!textFound) {        // Проверяем, был ли найден текст
            throw new ElementInteractionException("Заявка '" + claim + "' не найдена ни на одной из страниц таблицы.");
        }
        return this;
    }

    @Step
    @Title("Получить номер строки по заявке {claim} в таблице")
    public int getRowNumberByClaim(String claim, String tableName) {
        int rowNumber = -1; // Инициализируем переменную для номера строки
        boolean textFound = false;
        do {
            List<WebElement> rows = getListRows(getElementByTitle(tableName)); // Получаем строки таблицы
            for (int i = 0; i < rows.size(); i++) {
                // Проверяем, содержит ли строка номер заявки
                if (rows.get(i).getText().contains(claim)) {
                    rowNumber = i + 1; // Устанавливаем номер строки
                    textFound = true;
                    break; // Выходим из цикла, если текст найден
                }
            }
            if (!textFound) {
                // Переходим на следующую страницу, если текст не найден
                if (!clickOnNextPage(numberOfRecordsTextBlock, nextTablePageButton)) {
                    break; // Выходим, если больше нет страниц
                }
            }
        } while (!textFound);

        if (rowNumber == -1) {
            throw new ElementInteractionException("Заявка '" + claim + "' не найдена ни на одной из страниц таблицы '" + tableName + "'.");
        }
        return rowNumber; // Возвращаем номер строки или -1, если не найдено
    }

    @Step
    @Title("Активировать и деактивировать тоггл Новая заявка")
    public void activateToggleNewClaim() {
        newClaimToggle.click();
        newClaimToggle.getSelenideElement().$x("./button[contains(@class,'checked')]")
                .shouldBe(Condition.disappear, Duration.ofSeconds(10));
        waitBusyCondition();
    }

    @Step
    @Title("{state} чек-бокс {checkBox}")
    public PersonalAccountPage clickOnCheckBox(String state, String checkBoxName) {
        SelenideElement checkBoxElement = $x("//psb-checkbox[.//span[normalize-space()='" + checkBoxName + "']]" +
                "//span[contains(@class,'checkbox')]");
        if (state.equals("Включить") && !Objects.requireNonNull(checkBoxElement.getAttribute("class")).contains("checked")) {
            checkBoxElement.click();
        } else if (state.equals("Отключить") && Objects.requireNonNull(checkBoxElement.getAttribute("class")).contains("checked")) {
            checkBoxElement.click();
        } else return this;
        return this;
    }
}
