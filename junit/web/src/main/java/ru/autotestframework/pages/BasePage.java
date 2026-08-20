package ru.autotestframework.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;
import ru.autotestframework.steps.actions.BaseActions;
import ru.autotestframework.steps.elements.*;
import ru.autotestframework.ui_core.page_manager.PageEntry;
import ru.psb.testit.annotations.Description;
import ru.psb.testit.annotations.Step;
import ru.psb.testit.annotations.Title;

import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Selenide.$;

@Component
public class BasePage<T extends BaseActions<T>> extends BaseActions<T> implements
        IDataSteps,
        IDropDownListSteps<T>,
        IScrollSteps<T>,
        ITableSteps<T>,
        ITabSteps<T>,
        IFieldSteps<T> {

    public static boolean firstPage = true;
    public static String previousText = "";

    public <E> E goTo(E page) {
        if (page == null) {
            throw new IllegalArgumentException("Страница не найдена");
        }

        Class<?> pageClass = page.getClass();
        String pageValue;
        if (pageClass.isAnnotationPresent(PageEntry.class)) {
            PageEntry pageEntry = pageClass.getAnnotation(PageEntry.class);
            pageValue = pageEntry.title();
        } else {
            pageValue = "---";
        }
        return goToReport(pageValue, page);
    }

    @Step
    @Title("Переход на {pageName}")
    private <E> E goToReport(String pageName, E page) {
        return page;
    }

    @Step
    @Title("Отсортировать значения {listValues} в формате {format} по {criteria}")
    @Description("Сортировка значений в списке по убыванию или возрастанию")
    public List<String> getSortedList(List<String> listValues, String format, String criteria) {
        return convertFormatAndSort(listValues, format, criteria);
    }

    @Step
    @Title("Отсортировать значения в столбце {targetColumn} в формате {format} по {criteria} с учетом одинаковых значений в колонке {neighboringColumn}")
    @Description("Сортировка значений в указанной колонке по убыванию или возрастанию с учетом одинаковых значений в соседней колонке")
    public List<String> getSortedListWithNeighboringColumns(String tableName, String targetColumn,
                                                            String neighboringColumn, String format, String criteria) {
        return sortByNeighboringColumns(
                getListValuesbyColumnName(getElementByTitle(tableName), targetColumn),
                getListValuesbyColumnName(getElementByTitle(tableName), neighboringColumn),
                format, criteria);
    }

    @Step
    @Title("проверить вхождение в выпадающий список {elementTitle} списка значений: {columns}")
    @Description("Проверяется совпадение ожидаемого списка значений с акутальным и наоборот c подсчетом каждого значения в актуальных или ожидаемых списках")
    public T checkDropDownListElements(String elementTitle, List<String> columns) {
        return checkDropdownListElements(getElementByTitle(elementTitle), columns);
    }

    @Step
    @Title("выбрать из выпадающего списка {titleDropDownList} значения: {values}")
    @Description("Выбор одного или нескольких значений в выпадающем списке")
    public T selectValueFromDropDownList(String titleDropDownList, List<String> values) {
        return selectFromDropDownList(getElementByTitle(titleDropDownList), values);
    }

    @Step
    @Title("выбрать из выпадающего списка {titleDropDownList} значение - {value}")
    @Description("Выбор одного значения в выпадающем списке")
    public T selectValueFromDropDownList(String titleDropDownList, String value) {
        return selectFromDropDownList(getElementByTitle(titleDropDownList), List.of(value));
    }

    @Step
    @Title("Проверка выпадающего списка {titleDropDownList} на множественный выбор - {editable}")
    @Description("Проверка выпадающего списка на доступность множественного выбора")
    public T checkAvailabilityMultipleChoice(String titleDropDownList, boolean editable) {
        return checkMultipleChoice(getElementByTitle(titleDropDownList), editable);
    }

    @Step
    @Title("Проверить, что {scrollType} скроллинг для элемента {elementTitle} = {editable}")
    @Description("Проверяется функционирование вертикального или горизонтального скроллинга у элемента")
    public T checkScroll(String scrollType, String elementTitle, boolean editable) {
        return checkScrolling(scrollType, getElementByTitle(elementTitle), editable);
    }

    @Step
    @Title("Проверить, что порядок и имена столбцов таблицы {tableTitle} совпадает с ожидаемым {expectedColumns}")
    public T checkTableHeaders(String tableTitle, List<String> expectedColumns) {
        return checkHeaders(getElementByTitle(tableTitle), expectedColumns);
    }

    @Step
    @Title("Проверить, что ожидаемый набор заголовков {expectedColumns} таблицы {tableTitle} содержится в актуальном наборе")
    public T checkContainsTableHeaders(String tableTitle, List<String> expectedColumns) {
        return checkContainsHeaders(getElementByTitle(tableTitle), expectedColumns);
    }

    @Step
    @Title("Проверить, что ожидаемый набор заголовков {expectedColumns} таблицы {tableTitle} не содержится в актуальном наборе")
    public T checkNotContainsTableHeaders(String tableTitle, List<String> expectedColumns) {
        return checkNotContainsHeaders(getElementByTitle(tableTitle), expectedColumns);
    }

    @Step
    @Title("Проверить, что количество строк в таблице {tableTitle} равно {expectedRowCount}")
    public T checkRowCount(String tableTitle, int expectedRowCount) {
        return checkRowcount(getElementByTitle(tableTitle), expectedRowCount);
    }

    @Step
    @Title("Из таблицы {tableTitle} в строке {row} столбца {columnName} вернуть значение")
    public String getTextFromTable(String tableTitle, int row, String columnName) {
        return getElementfromTable(getElementByTitle(tableTitle), row, columnName).getText().trim();
    }

    @Step
    @Title("Клик по ячейке в строке №{row} столбца №{columnNum} таблицы {tableTitle}")
    public T clickOnCellFromTable(String tableTitle, int row, int columnNum) {
        getElementfromTable(getElementByTitle(tableTitle), row, columnNum).click();
        return getSelf();
    }

    @Step
    @Title("Клик по кнопке редактирования в ячейке строки №{row} столбца №{columnNum} таблицы {tableTitle}")
    public T clickOnButtonEditCellFromTable(String tableTitle, int row, int columnNum) {
        SelenideElement cell = $(getElementfromTable(getElementByTitle(tableTitle), row, columnNum));
        cell.$(By.xpath(".//mat-icon[@matsuffix]")).click();
        return getSelf();
    }

    @Step
    @Title("Клик по ячейке c текстом {cellText} в строке №{row} столбца №{columnNum} таблицы {tableTitle}")
    public T clickOnCellFromTable(String tableTitle, int row, int columnNum, String cellText) {
        getElementfromTable(getElementByTitle(tableTitle), row, columnNum, cellText).click();
        return getSelf();
    }

    @Step
    @Title("Получить значения столбца {columnName} таблицы")
    public List<String> getListValuesByColumnName(String tableName, String columnName) {
        return getListValuesbyColumnName(getElementByTitle(tableName), columnName);
    }

    @Step
    @Title("Получить заголовки и содержимое таблицы {tableName}")
    public Map<String, List<String>> getTableHeadersAndContent(String tableName) {
        return getTableContentAsMap(getElementByTitle(tableName));
    }

    @Step
    @Title("Получить заголовки и содержимое таблицы {tableName}")
    public String[][] getTableHeadersAndContentAsArray(String tableName) {
        return getTableContentAsArray(getElementByTitle(tableName));
    }

    @Step
    @Title("Снятие всех выбранных чекбоксов у выпадающего списка {titleDropDownList}")
    @Description("Снимаются все выбранные чекбоксы у падающего списка")
    public T cleanDropDownListCheckboxes(String titleDropDownList) {
        return cleanDropDownList(getElementByTitle(titleDropDownList));
    }

    @Step
    @Title("Переключиться на новую открытую вкладку")
    public T switchToNewTab() {
        return switchToNewtab();
    }

    @Step
    @Title("Переключиться на оставшуюся вкладку, после закрытия текущей")
    public T switchToOneTab() {
        return switchToOnetab();
    }

    @Step
    @Title("проверить вхождение в поле {elementTitle} списка значений: {expectedListElem}")
    @Description("Проверяется совпадение ожидаемого списка значений с акутальным и наоборот c подсчетом каждого значения в актуальных или ожидаемых списках")
    public T checkElementsOnFields(String elementTitle, List<String> expectedListElem) {
        return checklistElements(getElementByTitle(elementTitle), expectedListElem);
    }

    @Step
    @Title("проверить вхождение в поле {elementTitle} списка значений: {expectedListElem}")
    @Description("Проверяется вхождение ожидаемого списка значений в акутальный набор")
    public T checkElementsContainsOnFields(String elementTitle, List<String> expectedListElem) {
        return checklistContainsElements(getElementByTitle(elementTitle), expectedListElem);
    }

    @Step
    @Title("Получить значения {listCheckBox}")
    public List<String> getListCheckBox(String listCheckBox) {
        return getListElementsFromField(getElementByTitle(listCheckBox));
    }

    @Step
    @Title("Проверить, что чек-боксы напротив элементов {values} выпадающего списка {dropDownList} {expectedState}")
    @Description("Проверяется состояние чек-боксов напротив каждого элемента выпадающего списка.")
    public T assertCheckboxesSelected(String elementTitle, List<String> values, String expectedState) {
        return assertCheckBoxesSelected(getElementByTitle(elementTitle), values, expectedState.equals("включены"));
    }

    @Step
    @Title("Проверить, что значения списка {subList} входят в значения списка {fullList} с учетом порядка следования элементов в них")
    @Description("Проверяется состояние чек-боксов напротив каждого элемента выпадающего списка")
    public boolean assertIsSubsequence(List<String> subList, List<String> fullList) {
        return isSubsequence(subList, fullList);
    }
}
