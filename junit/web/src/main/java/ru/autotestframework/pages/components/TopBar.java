package ru.autotestframework.pages.components;

import org.openqa.selenium.support.FindBy;
import ru.autotestframework.steps.elements.ITableSteps;
import ru.autotestframework.ui_core.exceptions.ElementInteractionException;
import ru.autotestframework.ui_core.page_manager.Element;
import ru.autotestframework.ui_core.page_manager.PageEntry;
import ru.autotestframework.ui_core.services.table_service.FindCellsBy;
import ru.autotestframework.ui_core.services.table_service.FindHeadersBy;
import ru.autotestframework.web_elements.elements.Button;
import ru.autotestframework.web_elements.elements.TextBlock;
import ru.autotestframework.web_elements.elements.WebTable;
import ru.psb.testit.annotations.Description;
import ru.psb.testit.annotations.Step;
import ru.psb.testit.annotations.Title;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.core.IsEqual.equalTo;
import static ru.autotestframework.util.Validator.assertThat;


@PageEntry(title = "Верхняя панель")
public class TopBar<T extends LeftBar<T>> extends LeftBar<T> {

    @Element("Кнопка Меню")
    @FindBy(xpath = "//div[@class='header-logo']//i")
    public Button menuButton;

    @Element("Фамилия и Имя пользователя")
    @FindBy(xpath = "//div[contains(@class, 'user-name-block')]/span[1]")
    public TextBlock userSurnameAndNameTextBlock;

    @Element("Отчество пользователя")
    @FindBy(xpath = "//div[contains(@class, 'user-name-block')]/span[2]")
    public TextBlock userPatronymicTextBlock;

    @Element("Кнопка выхода")
    @FindBy(xpath = "//i[@nztype='logout']")
    public TextBlock logoutButton;

    @Element("Кнопка Найти")
    @FindBy(xpath = "//button[./span[contains(text(), 'Найти')]]")
    public Button searchButton;

    @Element("Кнопка удалить все")
    @FindBy(xpath = "//span[contains(@class, 'reset-filters')]")
    public Button resetFiltersButton;

    @Element("Плашки фильтров")
    @FindBy(xpath = "//div[contains(@class,'applied-filters')]/psb-table-filter-value")
    public List<TextBlock> tableFilterTextBlock;

    @Element("Таблица результаты поиска")
    @FindBy(xpath = "//table")
    @FindCellsBy(xpath = ".//td[@role='cell']")
    @FindHeadersBy(xpath = ".//th[@role='columnheader']")
    public WebTable searchResultTable;

    @Step
    @Title("Поиск заявки {claimId} с использованием поля \"Номер заявки\"")
    public T searchClaimOnPage(String claimId) {
        int attempt = 0;
        int maxAttempts = 2;
        int interval = 5;
        searchClaim(claimId);
        sleep(1);
        while (attempt < maxAttempts) {
            if (ITableSteps.initTable(searchResultTable).isEmpty()) {
                sleep(interval);
                searchClaim(claimId);
                attempt++;
            } else {
                return getSelf();
            }
        }
        throw new ElementInteractionException("Заявка " + claimId + " не найдена. Количество совершенных попыток поиска: " + attempt + ". " +
                "Интервал времени между попытками: " + interval + " сек.");
    }

    private void searchClaim(String claimId) {
        checkModal();
        resetFilters();
        fillField(requestNumberTextInput.getTitle(), claimId);
        searchButton.click();
        checkModal();
    }

    @Step
    @Title("сбросить все фильтры, если они были установлены")
    @Description("Выполнение сброса всех фильтров на страницах Очереди или Поиск, если они ранее были установлены")
    public T resetFilters() {
        checkModal();
        if (resetFiltersButton.isDisplayed()) {
            resetFiltersButton.click();
            searchButton.click();
            sleep(2);
        }
        return getSelf();
    }

    @Step
    @Title("Проверить наличие плашек активных фильтров {expectedFilters} с крестиком")
    @Description("Выполнение проверки наличия плашек активных фильтров с крестиком в соответствии с заданным списком.")
    public T checkActiveFilters(List<String> expectedFilters) {
        List<String> actualValues = tableFilterTextBlock.stream()
                .map(TextBlock::getText)
                .map(String::trim)
                .collect(Collectors.toList());

        assertThat(actualValues.size(), equalTo(expectedFilters.size()),
                "Количество актуальных значений фильтров (" + actualValues.size() +
                        ") не совпадает с количеством ожидаемых значений (" + expectedFilters.size() + ")");
        for (String expectedFilter : expectedFilters) {
            boolean isPresent = actualValues.contains(expectedFilter.trim());
            boolean hasCloseIcon = tableFilterTextBlock.stream()
                    .filter(filter -> filter.getText().trim().equals(expectedFilter.trim()))
                    .anyMatch(filter -> filter.getSelenideElement().$x(".//i[@nztype='close']").isDisplayed());

            assertThat(isPresent, "Фильтр отсутствует: " + expectedFilter);
            assertThat(hasCloseIcon, "Иконка с крестиком отсутствует для фильтра: " + expectedFilter);
        }

        return getSelf();
    }

    @Step
    @Title("Удалить фильтр {delFilter}")
    public T deleteFilter(String delFilter) {
        tableFilterTextBlock.stream()
                .filter(filter -> filter.getText().trim().equals(delFilter.trim()))
                .findFirst() // Находим первый элемент, соответствующий фильтру
                .ifPresent(filter -> filter.getSelenideElement().$x(".//i[@nztype='close']").click());
        return getSelf();
    }
}

