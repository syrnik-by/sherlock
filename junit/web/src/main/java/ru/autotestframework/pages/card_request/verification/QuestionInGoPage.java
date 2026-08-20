package ru.autotestframework.pages.card_request.verification;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import org.openqa.selenium.support.FindBy;
import ru.autotestframework.pages.BasePage;
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
import ru.psb.testit.annotations.Step;
import ru.psb.testit.annotations.Title;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;

@PageEntry(title = "Страница Вопрос в ГО")
public class QuestionInGoPage extends TopBar<QuestionInGoPage> {

    @Element("Поле Наименование стратегии")
    @FindBy(xpath = "//div[@class='top-panel-title']")
    public TextBlock nameOfStrategyTextBlock;

    @Element("Выпадающий список Результат проверки")
    @FindBy(xpath = "//div[text()='Результат проверки']/..//nz-select")
    public Button displayByDropDownPostPoned;

    @Element("Выпадающий список Тип вопроса")
    @FindBy(xpath = "//app-verification-select[@formcontrolname='questionTypeCode']//nz-select")
    public Button questionTypeDropDown;

    @Element("Поле Степпер")
    @FindBy(xpath = "//div[@class='ant-steps-item-description']")
    public TextBlock stepperTextBlock;

    @Element("Таблица Степпер")
    @FindBy(xpath = "//app-opm-go-messages-table")
    @FindHeadersBy(xpath = ".//th[contains(@role, 'columnheader')]")
    @FindCellsBy(xpath = ".//td[contains(@role, 'cell')]")
    public WebTable stepperTable;

    @Element("Поле Комментарий")
    @FindBy(xpath = "//div[contains(@class, 'mat-form-field-wrapper')]//textarea")
    public TextInput commentField;

    @Element("Кнопка Далее")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Далее')]]")
    public Button nextButton;

    @Element("Кнопка Завершить проверку")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Завершить проверку')]]")
    public Button completeVerificationButton;

    @Element("Кнопка Взять в работу")
    @FindBy(xpath = "//div[contains(@class, 'header')]//button[.//span[contains(text(), 'Взять в работу')]]")
    public Button takeToWorkButton;

    @Element("Модальное окно Заполнение поля")
    @FindBy(xpath = "//div[contains(@class, 'app-documents-error') or @class='modal']")
    public TextBlock modalInfoError;

    @Element("Поле Блок")
    @FindBy(xpath = "//nz-tree-node")
    public ElementsCollection resulTextBlock;

    @Element("Контйнер для заголовков")
    @FindBy(xpath = "//cdk-virtual-scroll-viewport")
    public TextBlock resultTextBlockContainer;

    public final static String SWITCHER = "./nz-tree-node-switcher";
    public final static String CHECKBOX = "./nz-tree-node-checkbox";


    @Getter
    public enum CheckboxState {
        CHECKED("включен"),
        UNCHECKED("выключен"),
        INDETERMINATE("промежуточный");

        final String displayName;

        CheckboxState(String displayName) {
            this.displayName = displayName;
        }

        public static CheckboxState fromString(String state) {
            switch (state.toLowerCase()) {
                case "включен":
                    return CHECKED;
                case "выключен":
                    return UNCHECKED;
                case "промежуточный":
                    return INDETERMINATE;
                default:
                    throw new IllegalArgumentException("Недопустимое состояние чек-бокса: " + state);
            }
        }
    }

    @Step
    @Title("Раскрыть последний блок из цепочки  \"{path}\"")
    public QuestionInGoPage expandBlocks(String path) {
        String[] headers = path.split(">");
        String lastHeader = headers[headers.length - 1].trim(); // Получаем последний заголовок
        String parentHeader = headers[0].trim(); // Первый заголовок как родительский

        int attempts = 0;
        // Перебираем все блоки и ищем совпадение с первым заголовком
        while (attempts < 10) {
            for (int i = 0; i < resulTextBlock.size(); i++) {
                // Проверяем, что родительский заголовок совпадает
                if (resulTextBlock.get(i).getText().equals(parentHeader)) {
                    // Проверяем, соответствует ли заголовок блока последнему заголовку
                    for (int j = i; j < resulTextBlock.size() + i; j++) {
                        SelenideElement lastBlock = resulTextBlock.get(j);
                        if (lastBlock.getText().equals(lastHeader)) {
                            SelenideElement switcher = $(lastBlock).$x(SWITCHER);
                            if (switcher.should(visible).exists()) {
                                if (!Objects.requireNonNull(switcher.getAttribute("class")).contains("open")) {
                                    switcher.click();
                                }
                                return this;
                            } else {
                                throw new ElementInteractionException("Свитчер для блока '" + lastBlock + "' не найден.");
                            }
                        }
                    }
                    throw new ElementInteractionException("Блок с заголовком '" + lastHeader + "' не найден.");
                }
            }
            executeJavaScript("arguments[0].scrollTop += 100;", resultTextBlockContainer);
            attempts++;
        }
        throw new ElementInteractionException("Заголовок не найден");
    }

    @Step
    @Title("Клик по чек-боксу для последнего блока из цепочки \"{path}\"")
    public QuestionInGoPage clickCheckbox(String path) {
        String[] headers = path.split(">");
        String lastHeader = headers[headers.length - 1].trim(); // Получаем последний заголовок
        String parentHeader = headers[0].trim(); // Первый заголовок как родительский

        // Перебираем все блоки и ищем совпадение с первым заголовком
        for (int i = 0; i < resulTextBlock.size(); i++) {
            // Проверяем, что родительский заголовок совпадает
            if (resulTextBlock.get(i).getText().equals(parentHeader)) {
                // Проверяем, соответствует ли заголовок блока последнему заголовку
                for (int j = i; j < resulTextBlock.size(); j++) {
                    SelenideElement lastBlock = resulTextBlock.get(j);
                    if (lastBlock.getText().equals(lastHeader)) {
                        SelenideElement checkbox = $(lastBlock).$x(CHECKBOX);
                        if (checkbox.should(visible).exists()) {
                            checkbox.click(); // Кликаем на чек-бокс
                            return this;
                        } else {
                            throw new ElementInteractionException("Чек-бокс для блока '" + lastHeader + "' не найден.");
                        }
                    }
                }
                throw new ElementInteractionException("Блок с заголовком '" + lastHeader + "' не найден.");
            }
        }
        return this;
    }

    @Step
    @Title("Проверить, что чек-бокс в последнем заголовке в заданном пути \"{path}\" = {expectedState}")
    public QuestionInGoPage assertCheckboxState(String path, String expectedState) {
        String[] headers = path.split(">");
        String lastHeader = headers[headers.length - 1].trim(); // Получаем последний заголовок
        String parentHeader = headers[0].trim(); // Первый заголовок как родительский

        // Перебираем все блоки и ищем совпадение с первым заголовком
        for (int i = 0; i < resulTextBlock.size(); i++) {
            // Проверяем, что родительский заголовок совпадает
            if (resulTextBlock.get(i).getText().equals(parentHeader)) {
                // Проверяем, соответствует ли заголовок блока последнему заголовку
                for (int j = i; j < resulTextBlock.size(); j++) {
                    SelenideElement lastBlock = resulTextBlock.get(j);
                    if (lastBlock.getText().equals(lastHeader)) {
                        SelenideElement checkbox = $(lastBlock).$x(CHECKBOX);

                        if (checkbox.should(visible).exists()) {
                            String classAttribute = checkbox.getAttribute("class");
                            CheckboxState actualState;
                            assert classAttribute != null;
                            if (classAttribute.contains("checkbox-checked")) {
                                actualState = CheckboxState.CHECKED;
                            } else if (classAttribute.contains("checkbox-indeterminate")) {
                                actualState = CheckboxState.INDETERMINATE; // Чек-бокс промежуточный
                            } else {
                                actualState = CheckboxState.UNCHECKED; // Чек-бокс выключен
                            }

                            assertThat(actualState.getDisplayName()).as("Чек-бокс для блока '" + lastHeader + "' должен быть "
                                            + expectedState)
                                    .isEqualTo(CheckboxState.fromString(expectedState).getDisplayName());
                            return this;
                        } else {
                            throw new ElementInteractionException("Чек-бокс для блока '" + lastHeader + "' не найден.");
                        }
                    }
                }
                throw new ElementInteractionException("Блок с заголовком '" + lastHeader + "' не найден.");
            }
        }
        return this;
    }

    @Step
    @Title("Проверить наличие и сортировку последних заголовков в заданном списке путей \"{paths}\"")
    public QuestionInGoPage assertSortHeaders(List<String> paths) {
        List<String> actualLastHeaders = new ArrayList<>();
        List<String> expectedLastHeaders = new ArrayList<>();
        String parentBlock = "";

        for (String path : paths) {
            String[] headers = path.split(">");
            String lastHeader = headers[headers.length - 1].trim(); // Получаем последний заголовок
            String parentHeader = headers[0].trim(); // Первый заголовок как родительский
            parentBlock = parentHeader;
            expectedLastHeaders.add(lastHeader);
            // Перебираем все блоки и ищем совпадение с первым заголовком
            for (int i = 0; i < resulTextBlock.size(); i++) {
                // Проверяем, что родительский заголовок совпадает
                if (resulTextBlock.get(i).getText().equals(parentHeader)) {
                    for (int j = i; j < resulTextBlock.size(); j++) {
                        SelenideElement lastBlock = resulTextBlock.get(j);
                        if (lastBlock.getText().equals(lastHeader)) {
                            actualLastHeaders.add(lastBlock.getText());
                            break;
                        }
                    }
                }
            }
        }

        assertIsTrue(expectedLastHeaders.equals(actualLastHeaders), "Сортировка по возрастанию и набор значений актуального списка заголовков "
                + actualLastHeaders + " совпадает с ожидаемым " + expectedLastHeaders + " для родительского заголовка " + parentBlock);
        return this;
    }

    @Step
    @Title("Проверить наличие и сортировку последних заголовков по возрастанию в заданном списке путей \"{paths}\"")
    public QuestionInGoPage assertHeaderNotContains(String path) {
        String[] headers = path.split(">");
        String lastHeader = headers[headers.length - 1].trim(); // Получаем последний заголовок
        String parentHeader = headers[0].trim(); // Первый заголовок как родительский
        // Перебираем все блоки и ищем совпадение с первым заголовком
        for (int i = 0; i < resulTextBlock.size(); i++) {
            // Проверяем, что родительский заголовок совпадает
            if (resulTextBlock.get(i).getText().equals(parentHeader)) {
                for (int j = i; j < resulTextBlock.size(); j++) {
                    SelenideElement lastBlock = resulTextBlock.get(j);
                    if (lastBlock.getText().equals(lastHeader)) {
                        throw new ElementInteractionException("Заголовок " + lastBlock + " присутствует в цепочке заголовков");
                    }
                }
            }
        }
        return this;
    }
}
