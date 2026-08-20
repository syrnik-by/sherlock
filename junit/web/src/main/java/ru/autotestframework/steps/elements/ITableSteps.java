package ru.autotestframework.steps.elements;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import ru.autotestframework.ui_core.exceptions.ElementInteractionException;
import ru.autotestframework.web_elements.elements.WebTable;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static ru.autotestframework.pages.BasePage.firstPage;
import static ru.autotestframework.pages.BasePage.previousText;
import static ru.autotestframework.steps.actions.BaseActions.sleep;
import static ru.autotestframework.util.Validator.assertThat;
import static ru.autotestframework.utils.Constants.TABLE_ROWS;

public interface ITableSteps<T> {

    T getSelf();

    default T checkHeaders(WebTable table, List<String> expectedHeaders) {
        List<String> actualHeaders = getColumnNames(table);
        assertThat(actualHeaders.equals(expectedHeaders),
                "Заголовки таблицы " + table.getTitle() + " не совпадают с ожидаемыми \n" +
                        "\nАктуальные значения: \n" + actualHeaders +
                        "\nОжидаемые значения: \n" + expectedHeaders);
        return getSelf();
    }

    default T checkContainsHeaders(WebTable table, List<String> expectedHeaders) {
        List<String> actualHeaders = initTable(table).findElements(By.xpath(table.getHeadersPath()))
                .stream().map(WebElement::getText).collect(Collectors.toList());
        // Проверка на вхождение ожидаемых заголовков в актуальные
        assertThat(new HashSet<>(actualHeaders).containsAll(expectedHeaders),
                "Заголовки таблицы " + table.getTitle() + " не содержат ожидаемые значения.\n" +
                        "\nАктуальные значения: \n" + actualHeaders +
                        "\nОжидаемые значения: \n" + expectedHeaders);
        return getSelf();
    }

    default T checkNotContainsHeaders(WebTable table, List<String> expectedHeaders) {
        List<String> actualHeaders = initTable(table).findElements(By.xpath(table.getHeadersPath()))
                .stream().map(WebElement::getText).collect(Collectors.toList());
        // Проверка на вхождение ожидаемых заголовков в актуальные
        assertThat(!new HashSet<>(actualHeaders).containsAll(expectedHeaders),
                "Заголовки таблицы " + table.getTitle() + " содержат ожидаемые \n" +
                        "\nАктуальные значения: \n" + actualHeaders +
                        "\nОжидаемые значения: \n" + expectedHeaders);
        return getSelf();
    }

    default Map<String, List<String>> getTableContentAsMap(WebTable tableName) {
        Map<String, List<String>> tableContent = new LinkedHashMap<>();
        // Получаем все названия столбцов
        List<String> columnNames = getColumnNames(tableName);
        // Для каждого названия столбца получаем значения и добавляем в Map
        for (String columnName : columnNames) {
            List<String> values = getListValuesbyColumnName(tableName, columnName);
            tableContent.put(columnName, values);
        }
        return tableContent;
    }

    default String[][] getTableContentAsArray(WebTable tableName) {
        // Получаем все названия столбцов
        List<String> columnNames = getColumnNames(tableName);
        int rowCount = initTable(tableName).getRowsQuantity();
        String[][] tableContent = new String[rowCount + 1][columnNames.size()];

        for (int i = 0; i < columnNames.size(); i++) {
            tableContent[0][i] = columnNames.get(i); // Заголовки в первой строке
        }

        // Заполняем массив данными
        for (int i = 0; i < columnNames.size(); i++) {
            List<String> values = getListValuesbyColumnName(tableName, columnNames.get(i));
            for (int j = 0; j < rowCount; j++) {
                tableContent[j + 1][i] = j < values.size() ? values.get(j).trim() : ""; // Заполняем ячейку значением или пустой строкой
            }
        }

        return tableContent;
    }

    default List<String> getListValuesbyColumnName(WebTable tableName, String columnName) {
        int columnIndex = getColumnIndexByName(tableName, columnName);

        SelenideElement table = tableName.getSelenideElement().shouldBe(visible, Duration.ofSeconds(10));
        String cellsPath = tableName.getCellsPath();

        List<String> values = table.$$x(TABLE_ROWS)
                .stream()
                .map(row -> row.$$x(cellsPath).get(columnIndex).getText())
                .collect(Collectors.toList());

        if (values.isEmpty()) {
            throw new ElementInteractionException("Данные в столбце '" + columnName +
                    "' таблицы '" + tableName.getTitle() + "' отсутствуют. Таблица пуста." );
        }
        return values;
    }

    default SelenideElement getElementfromTable(WebTable tableName, int rowNum, String columnName) {
        int columnIndex = getColumnIndexByName(tableName, columnName);

        SelenideElement table = tableName.getSelenideElement().shouldBe(Condition.visible, Duration.ofSeconds(10));
        String cellsPath = tableName.getCellsPath();

        // Проверяем количество строк в таблице
        ElementsCollection rows = table.$$x(TABLE_ROWS);
        rows.forEach(row -> row.shouldBe(Condition.visible));
        int rowCount = table.$$x(TABLE_ROWS).size();

        if (rowCount == 0) {
            throw new ElementInteractionException("Таблица \"" + tableName.getTitle() + "\" пуста. Невозможно получить данные.");
        }

        if (rowNum < 1 || rowNum > rowCount) {
            throw new ElementInteractionException("Неверный номер строки: " + rowNum + ". Количество строк: " + rowCount);
        }

        try {
            SelenideElement row = table.$$x(TABLE_ROWS).get(rowNum - 1);
            // Проверяем наличие ячейки перед получением текста
            if (row.$$x(cellsPath).size() <= columnIndex) {
                throw new ElementInteractionException("Ячейка столбца \"" + columnName + "\" строки " + rowNum + " не найдена или отсутствует");
            }
            return row.$$x(cellsPath).get(columnIndex);
        } catch (NoSuchElementException e) {
            throw new ElementInteractionException("Не удалось найти элемент в строке " + rowNum + ", столбец: " + columnName, e);
        }
    }

    default SelenideElement getElementfromTable(WebTable tableName, int rowNum, int columnNum, String... cellText) {
        SelenideElement table = tableName.getSelenideElement().shouldBe(Condition.visible);
        String cellsPath = tableName.getCellsPath();

        // Проверяем количество строк в таблице
        ElementsCollection rows = table.$$x(".//tbody/tr | ./table/tr[not(th)]");
        rows.forEach(row -> row.shouldBe(Condition.visible));
        int rowCount = rows.size();

        if (rowCount == 0) {
            throw new ElementInteractionException("Таблица \"" + tableName.getTitle() + "\" пуста. Невозможно получить данные.");
        }

        if (rowNum < 1 || rowNum > rowCount) {
            throw new ElementInteractionException("Неверный номер строки: " + rowNum + ". Количество строк: " + rowCount);
        }

        try {
            SelenideElement row = table.$$x(".//tbody/tr | ./table/tr[not(th)]").get(rowNum - 1);
            // Проверяем наличие ячейки
            ElementsCollection cells = row.$$x(cellsPath);
            if (columnNum < 0 || cells.size() < columnNum) {
                throw new ElementInteractionException("Ячейка столбца с номером " + columnNum + " строки " + rowNum + " не найдена или отсутствует");
            }

            SelenideElement cell = cells.get(columnNum - 1);

            // Если указан текст, ищем его в ячейке
            if (cellText.length > 0 && cellText[0] != null && !cellText[0].isEmpty()) {
                //SelenideElement textElement = cell.shouldHave(Condition.exactText(cellText[0]));
                SelenideElement textElement = cell.$x(".//*[contains(text(), '" + cellText[0] + "')]");
                if (textElement.exists()) {
                    return textElement; // Возвращаем элемент с текстом
                } else {
                    throw new ElementInteractionException("Текст \"" + cellText[0] + "\" не найден в ячейке строки " + rowNum + ", столбца: " + columnNum);
                }
            }
            return cell; // Возвращаем ячейку, если текст не указан
        } catch (NoSuchElementException e) {
            throw new ElementInteractionException("Не удалось найти элемент в строке " + rowNum + ", столбец: " + columnNum, e);
        }
    }

    default T checkRowcount(WebTable tableName, int expectedRowCount) {
        int actualRowCount = getRowCount(tableName);
        assertThat(actualRowCount == expectedRowCount, "Количество строк в таблице " + actualRowCount + ", ожидалось " + expectedRowCount,
                actualRowCount, expectedRowCount);
        return getSelf();
    }

    default int getRowCount(WebTable tableName) {
        return initTable(tableName).getRowsQuantity();
    }

    default List<WebElement> getListRows(WebTable tableName) {
        return initTable(tableName).getWrappedElement().findElements(By.xpath(".//tr"));
    }

    default boolean clickOnNextPage(WebElement countOfRecords, WebElement nextPage) {
        String counter = $(countOfRecords).shouldBe(Condition.visible, Duration.ofSeconds(5)).getText().trim();
        int total = Integer.parseInt(counter.substring(counter.indexOf("из") + 2).trim());
        int totalOnPage = Integer.parseInt(counter.substring(counter.indexOf("-") + 1, counter.indexOf("из") - 1).trim());
        if (total != totalOnPage) {
            if (!firstPage) {
                nextPage.click();
                sleep(1);
            } else {
                firstPage = false;
            }
            return true;
        }
        return false;
    }

    private List<String> getColumnNames(WebTable tableName) {
        return initTable(tableName).findElements(By.xpath(tableName.getHeadersPath()))
                .stream().map(WebElement::getText).collect(Collectors.toList());
    }

    default int getColumnIndexByName(WebTable tableName, String columnName) {
        SelenideElement table = tableName.getSelenideElement().shouldBe(visible, Duration.ofSeconds(10));
        String headerPath = tableName.getHeadersPath();

        List<SelenideElement> headers = table.$$x(headerPath);
        int columnIndex = IntStream.range(0, headers.size())
                .filter(i -> headers.get(i).getText().trim().equals(columnName))
                .findFirst()
                .orElse(-1);

        // Если столбец не найден, выбрасываем исключение
        if (columnIndex == -1) {
            throw new ElementInteractionException("Указанный столбец " + columnName + " не найден.");
        }
        return columnIndex;
    }

    static WebTable initTable(WebTable table) {
        table.shouldBe(visible, true);
        String currentText = table.getWrappedElement().getText();
        if (!previousText.equals(currentText)) {
            previousText = currentText;
            table.shouldBe(visible, true);
            table.init();
        }
        return table;
    }
}