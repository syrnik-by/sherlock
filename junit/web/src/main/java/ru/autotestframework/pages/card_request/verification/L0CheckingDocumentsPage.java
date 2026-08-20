package ru.autotestframework.pages.card_request.verification;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.autotestframework.pages.BasePage;
import ru.autotestframework.ui_core.exceptions.ElementInteractionException;
import ru.autotestframework.ui_core.page_manager.Element;
import ru.autotestframework.ui_core.page_manager.PageEntry;
import ru.autotestframework.web_elements.elements.Button;
import ru.autotestframework.web_elements.elements.TextBlock;
import ru.autotestframework.web_elements.elements.TextInput;
import ru.autotestframework.web_elements.elements.typified.TypifiedWebElement;
import ru.psb.testit.annotations.Description;
import ru.psb.testit.annotations.Step;
import ru.psb.testit.annotations.Title;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.actions;
import static org.junit.jupiter.api.Assertions.*;
import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;
import static ru.autotestframework.util.Validator.assertThat;

@PageEntry(title = "ЭФ стратегии «L0/Проверка документов» этапа «Верификация»")
public class L0CheckingDocumentsPage extends BasePage<L0CheckingDocumentsPage> {

    @Element("Поле Наименование стратегии")
    @FindBy(xpath = "//div[@class='top-panel-title']")
    public TextBlock nameOfStrategyTextBlock;

    @Element("Кнопка Основные данные")
    @FindBy(xpath = "//app-main-data-button/a[contains(text(), 'Основные данные')]")
    public Button basicDataButton;

    @Element("Поле Результат проверки - Документ")
    @FindBy(xpath = "//mat-expansion-panel")
    public List<TextBlock> resultCheckTextBlock;

    @Element("Вложенное поле для Результата проверки - Триггер")
    @FindBy(xpath = "//app-risk-item")
    public List<TextBlock> subResultCheckTextBlock;

    @Element("Поле Заголовок документов")
    @FindBy(xpath = "//div[@class='group-header']")
    public List<TextBlock> documentHeaderTextBlock;

    @Element("Всплывающая подсказка")
    @FindBy(xpath = "//div[contains(@class, 'ant-tooltip-inner')]")
    public TextBlock textBlockToolTip;

    @Element("Выпадающий список Выберите звание/должность")
    @FindBy(xpath = "//span[text()='Выберите звание/должность']/../../..//nz-select")
    public Button selectRankDropDown;

    @Element("Поле Доход завышен/не завышен")
    @FindBy(xpath = "//div[contains(@class,'risk__message')]//span")
    public TextBlock fieldIncome;

    @Element("Шаг №2. Заёмщик. Основное место работы")
    @FindBy(xpath = "//span[contains(@class,'ant-steps-icon') and contains(text(),'2')]/..")
    public Button buttonStep2;

    @Element("Шаг №3. Заёмщик. Совместительство")
    @FindBy(xpath = "//span[contains(@class,'ant-steps-icon') and contains(text(),'3')]/..")
    public Button buttonStep3;

    @Element("Иконка Шаг №2 Заёмщик. Основное место работы - заблокирован")
    @FindBy(xpath = "//div[contains(text() , 'Основное место работы')]//..//..//div[contains(@class, 'blocked')]//span[text() = 'Заблокирован']")
    public Button blockedStep2Button;

    @Element("Иконка Шаг №3 Заёмщик. Совместительство - заблокирован")
    @FindBy(xpath = "//div[contains(text() , 'Совместительство')]//..//..//div[contains(@class, 'blocked')]//span[text() = 'Заблокирован']")
    public Button blockedStep3Button;

    @Element("Иконка для активации поля ввода Скоррект. доход/По Осн. месту")
    @FindBy(xpath = "//div[span[contains(text(), 'По осн. месту')]]/following-sibling::mat-form-field//div//mat-icon")
    public TextInput iconCorrectTextInput;

    @Element("Поле ввода Скоррект. доход/По Осн. месту")
    @FindBy(xpath = "//div[span[contains(text(), 'По осн. месту')]]/following-sibling::mat-form-field//div//input[not(@value)]")
    public TextInput correctTextInput;

    @Element("Кнопка Взять шаг в работу")
    @FindBy(xpath = "//button[span[contains(text(), 'Взять шаг в работу')]]")
    public Button buttonTakeStepInWork;

    @Element("Кнопка Снять отметки о предоставлении документов")
    @FindBy(xpath = "//mat-icon[@data-mat-icon-name='unselect-all']")
    public Button buttonUnmark;

    @Element("Кнопка Далее")
    @FindBy(xpath = "//button/span[contains(text(),'Далее')]")
    public Button buttonNext;

    @Element("Кнопка Завершить проверку")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Завершить проверку')]]")
    public Button buttonFinishCheck;

    @Element("Кнопка ОК")
    @FindBy(xpath = "//button[.//span[contains(text(), 'ОК') or contains(text(), 'Ок')]]")
    public Button buttonOk;

    @Element("Модальное окно с сообщением")
    @FindBy(xpath = "//mat-dialog-container//*[@class='main-text' or @class='modal_message']")
    public TextBlock textBlockModalInfo;

    @Element("Проверка Заемщик. Основное место работы")
    @FindBy(xpath = "//div[@class='ant-steps-item-title']/div[text()='Заемщик. Основное место работы']")
    public TextBlock mainPlaceWorkTextBlock;

    @Element("Проверка Заемщик")
    @FindBy(xpath = "//div[@class='ant-steps-item-title']/div[text()='Заемщик']")
    public TextBlock borrowerTextBlock;

    @Element("Проверка Заемщик. Совместительство")
    @FindBy(xpath = "//div[@class='ant-steps-item-title']/div[text()='Заемщик. Совместительство']")
    public TextBlock partTimeWorkTextBlock;

    @Element("Поле Признак на модальном окне Детализация признаков фальсификации")
    @FindBy(xpath = "//app-details-modal//label[contains(@class,'ant-checkbox-group-item')]")
    public List<TextBlock> listOfSignsTextBlock;

    @Element("Модальное окно Детализация признаков")
    @FindBy(xpath = "//app-details-modal")
    public TextBlock modalDetailsSignTextBlock;

    @Element("Кнопка Готово на модальном окне Детализация признаков")
    @FindBy(xpath = "//app-details-modal//button")
    public Button readyButton;

    @Element("Кнопка Закрыть модальное окно Детализация признаков")
    @FindBy(xpath = "//app-details-modal//span[@nztype='close']")
    public Button closeButton;

    @Element("Поле Поиск признака")
    @FindBy(xpath = "//app-details-modal//input[contains(@class,'search-input')]")
    public TextInput searchSignTextInput;

    @Element("Кнопка Закрыть модальное окно Детализация признаков")
    @FindBy(xpath = "//app-details-modal//span[contains(@class,'close')]")
    public Button closeSignButton;

    @Element("Поле ввода Внутренний комментарий")
    @FindBy(xpath = "//div[contains(text(), 'Внутренний комментарий')]//..//textarea")
    public TextInput textareaCommentTextInput;

    @Element("Выпадающий список Результат по заявке")
    @FindBy(xpath = "//div[./div[contains(text(), 'Результат по заявке')]]//nz-select")
    public Button resultApplicationButton;

    @Element("Выпадающий список Тип вопроса")
    @FindBy(xpath = "//div[./div[contains(text(), 'Тип вопроса')]]//nz-select")
    public Button typeForQuestionButton;

    @Element("Поле Комментарий")
    @FindBy(xpath = "//div[contains(@class, 'mat-form-field-wrapper')]//textarea")
    public TextInput commentField;

    @Element("Иконка подсказка")
    @FindBy(xpath = "//span[contains(text(), 'Деятельность компании подразумевает большую закупочную часть или траты вне персонала')]//..//app-hint-icon")
    public TextBlock hintTextBlock;

    public final static String ICON_DOCUMENT_PROVIDED = ".//*[local-name()='svg' and @data-icon='file-text']";
    public final static String ICON_DOCUMENT_NOT_PROVIDED = ".//*[local-name()='svg' and @data-icon='file-excel']";
    public final static String ICON_TRIGGER = ".//*[local-name()='svg' and @data-icon='question-circle']";
    public final static String ICON_UNSELECT_ALL = ".//*[local-name()='svg']";
    public final static String RADIO_BUTTON_YES = "./../..//mat-radio-button[@value='true' and not(contains(@class, 'radio-checked'))]//span[@class='mat-radio-container']" +
            "|./..//mat-radio-button[not(contains(@class, 'radio-checked'))]//span[contains(text(),'Да')]";
    public final static String RADIO_BUTTON_NO = "./../..//mat-radio-button[@value='false' and not(contains(@class, 'radio-checked'))]//span[@class='mat-radio-container']" +
            "|./..//mat-radio-button[not(contains(@class, 'radio-checked'))]//span[contains(text(),'Нет')]";
    public final static String RADIO_BUTTON_NO_DATA = "./..//mat-radio-button[not (contains(@class, 'radio-checked'))]//span[contains(text(),'Нет данных')]";
    public final static String BUTTON_EDIT = "./../..//mat-icon[@data-mat-icon-name='edit-list']";
    public final static String CHECKBOX_SELECTED_SIGN = "./span[contains(@class, 'checkbox-checked')]";
    public final static String CHECKBOX_UNSELECTED_SIGN = "./span[@class='ant-checkbox']";

    public final static String STEP_1 = "Шаг №1. Заёмщик";
    public final static String STEP_2 = "Шаг №2. Заёмщик. Основное место работы";
    public final static String STEP_3 = "Шаг №3. Заёмщик. Совместительство";
    public final static List<String> EXPECTED_DOCS_STEP_1 = List.of("ФОТО", "Паспорт", "Справки, подтверждающие закрытие обязательств");
    public final static Map<String, List<String>> EXPECTED_DOCS_STEP_2 = Map.of(
            "Подтверждение дохода", List.of(
                    "2-НДФЛ",
                    "Выписка с з/п счета",
                    "Справка по форме банка/работодателя",
                    "3-НДФЛ",
                    "Выписка с з/п счета/2-НДФЛ",
                    "Выписка с з/п счета/Выписка из ПФР",
                    "2-НДФЛ/Выписка из ПФР",
                    "Справка по форме банка/Выписка с з/п счета/2-НДФЛ"),
            "Подтверждение трудоустройства", List.of(
                    "Трудовая книжка",
                    "Трудовой договор",
                    "Выписка из ПФР",
                    "Контракт о прохождении военной службы"),
            "Прочие документы", List.of(
                    "Удостоверение военнослужащего",
                    "Военный билет"),
            "Реестры", List.of(
                    "Проверка реестров"),
            "Дополнительные документы", List.of(
                    "Выбор дополнительных документов"));
    private final Map<String, TextBlock> headerCache = new HashMap<>();
    private final Map<String, TextBlock> triggerCache = new HashMap<>();
    private final Map<String, TextBlock> documentCache = new HashMap<>();

    @Step
    @Title("Получить актуальный список документов из таблицы Результаты проверки")
    @Description("Получение актуального списка документов из таблицы Результаты проверки")
    public List<String> getActualListDocuments() {
        resultCheckTextBlock.get(0).shouldBe(Condition.visible, true);
        return resultCheckTextBlock.stream().map(TypifiedWebElement::getText).collect(Collectors.toList());
    }

    @Step
    @Title("Получить {signType} признаки из всплывающего окна «Детализация признаков»")
    @Description("Получение актуального списка признаков из всплывающего окна «Детализация признаков»")
    public List<String> getActualListOfSigns(String signType) {
        listOfSignsTextBlock.get(0).shouldBe(Condition.visible, true);
        return listOfSignsTextBlock.stream()
                .filter(element -> {
                    try {
                        return element.findElement(By.xpath(signType.equals("выбранные")
                                ? CHECKBOX_SELECTED_SIGN
                                : CHECKBOX_UNSELECTED_SIGN)).isDisplayed();
                    } catch (NoSuchElementException e) {
                        return false;
                    }
                })
                .map(TypifiedWebElement::getText)
                .collect(Collectors.toList());
    }

    @Step
    @Title("Клик по признаку {sign}")
    @Description("Клик по признаку из всплывающего окна «Детализация признаков фальсификации»")
    public L0CheckingDocumentsPage clickOnSign(String sign) {
        listOfSignsTextBlock.get(0).shouldBe(Condition.visible, true);
        listOfSignsTextBlock.stream()
                .filter(element -> element.getText().equals(sign))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Признак '" + sign + "' не найден в списке."))
                .click();
        return this;
    }

    @Step
    @Title("Получить актуальный список документов из таблицы Результаты проверки по заголовку {header} (шаг №2 формы L0.Проверка документов)")
    @Description("Получение актуального списка документов из таблицы Результаты проверки по заголовку")
    public List<String> getActualListDocumentsByHeader(String header) {
        TextBlock docHeader = findResultBlockByHeader(header);
        return docHeader.findElements(By.xpath("./..//mat-expansion-panel"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    @Step
    @Title("Получить актуальный список триггеров для документа {document} из таблицы Результаты проверки")
    @Description("Получение актуального списка триггеров для указанного документа из таблицы Результаты проверки")
    public List<String> getActualListTriggersByDoc(String document) {
        TextBlock resultCheck = findResultBlockByDocument(document);
        return resultCheck.findElements(By.xpath("(.//app-rank-risk-item|.//app-risk-item)//span[contains(@class, 'risk')]"))
                .stream()
                .filter(WebElement::isDisplayed)
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    @Step
    @Title("Получить актуальный список признаков под триггером {trigger}")
    @Description("Получение актуального списка признаков")
    public List<String> getActualListSignsByTrigger(String trigger) {
        TextBlock resultCheck = findResultBlockByTrigger(trigger);
        return resultCheck.findElements(By.xpath(".//div[contains(@class, 'details')]//div[contains(@class, 'label')]"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    @Step
    @Title("Нажать на иконку '{iconTitle}' для документа '{document}'")
    @Description("Нажатие на иконку 'Документ не предоставлен' в блоке с результатом проверки, где есть определенный текст")
    public L0CheckingDocumentsPage clickOnNotProvidedIconForDoc(String document, String iconTitle) {
        TextBlock resultBlock = findResultBlockByDocument(document);
        // Ищем иконку по заголовку
        WebElement icon = resultBlock.findElement(By.xpath(getIconDataByTitle(iconTitle)));
        if (icon.isDisplayed()) {
            icon.click();
            validateIconClick(iconTitle, document);
            return this;
        }
        throw new ElementInteractionException("Иконка " + iconTitle + " не найдена для документа " + document);
    }

    private void validateIconClick(String iconTitle, String document) {
        if (iconTitle.equals("Документ не предоставлен")) {
            assertIsTrue(iconIsVisible(document, "Документ предоставлен"), "Иконка Документ предоставлен должна отображаться");
            assertIsTrue(!getActualListTriggersByDoc(document).isEmpty(), "Список триггеров должен быть развернут");
        } else {
            assertIsTrue(iconIsVisible(document, "Документ не предоставлен"), "Иконка Документ не предоставлен должна отображаться");
            assertIsTrue(getActualListTriggersByDoc(document).isEmpty(), "Список триггеров должен быть свернут");
        }
    }

    @Step
    @Title("Навести курсор мыши на иконку напротив документа '{document}' и вернуть текст подсказки")
    @Description("Навести курсор мыши на иконку напротив документа '{document}' и вернуть текст подсказки")
    public String mouseHoverOnIcon(String document) {
        return hoverOnIcon(findResultBlockByDocument(document));
    }

    @Step
    @Title("Навести курсор мыши на иконку напротив напротив триггера {trigger} и вернуть текст подсказки")
    @Description("Навести курсор мыши на иконку напротив документа '{document}' и вернуть текст подсказки")
    public String mouseHoverOnIconTrigger(String trigger) {
        return hoverOnIcon(findResultBlockByTrigger(trigger));
    }

    @Step
    @Title("Навести курсор мыши на иконку напротив элемента '{document}' и вернуть текст подсказки")
    @Description("Навести курсор мыши на иконку напротив документа '{document}' и вернуть текст подсказки")
    public String mouseHoverOnElement(String elementTitle) {
        return hoverOnIcon(getElementByTitle(elementTitle));
    }

    @Step
    @Title("Проверка видимости иконки '{iconTitle}' напротив документа '{document}'")
    @Description("Проверка видимости иконки напротив документа")
    public boolean iconIsVisible(String document, String iconTitle) {
        TextBlock resultBlock = findResultBlockByDocument(document);
        try {
            return resultBlock.findElement(By.xpath(getIconDataByTitle(iconTitle))).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Step
    @Title("Проверка ожидаемого {expectedText} текста подсказки после наведения курсора на иконку напротив документа '{document}'")
    public L0CheckingDocumentsPage checkToolTipText(String document, String expectedText) {
        assertEquals(mouseHoverOnIcon(document), expectedText, "Не отображается/не удалось получить или неверный текст подсказки");
        return this;
    }

    @Step
    @Title("Проверка ожидаемого {expectedText} текста подсказки после наведения курсора на иконку напротив триггера '{trigger}'")
    public void checkToolTipTextTrigger(String trigger, String expectedText) {
        assertEquals(expectedText, mouseHoverOnIconTrigger(trigger), "ННе отображается/не удалось получить или неверный текст подсказки для триггера " + trigger);
    }

    @Step
    @Title("Проверка ожидаемого {expectedText} текста подсказки после наведения курсора на элемент '{elementTitle}'")
    public L0CheckingDocumentsPage checkToolTipTextElement(String elementTitle, String expectedText) {
        assertEquals(expectedText, mouseHoverOnElement(elementTitle), "Не отображается или Не удалось получить текст подсказки для элемента' " + elementTitle);
        return this;
    }

    @Step
    @Title("Проверка наличия незаполненных радио-кнопок 'Да' и 'Нет' у триггера '{trigger}' из документа {document}")
    public void checkRadioButtonsForTrigger(String document, String trigger) {
        TextBlock resultBlock = findResultBlockByDocument(document);
        List<WebElement> triggersOfDoc = resultBlock.findElements(By.xpath(".//app-risk-item//span[contains(@class, 'risk')]"));
        for (WebElement trig : triggersOfDoc) {
            if (trig.getText().equals(trigger)) {
                assertTrue(trig.findElement(By.xpath(RADIO_BUTTON_YES)).isDisplayed(), "Радио-кнопка 'Да' не найдена/выбрана для триггера " + trigger);
                assertTrue(trig.findElement(By.xpath(RADIO_BUTTON_NO)).isDisplayed(), "Радио-кнопка 'Нет' не найдена/выбрана для триггера " + trigger);
            }
        }
    }

    @Step
    @Title("Проверка, что радио-кнопока '{button}' у триггера '{trigger}' из документа {document}, {select}")
    public void checkRadioButtonsCheckedForTrigger(String document, String trigger, String button, String select) {
        TextBlock resultBlock = findResultBlockByDocument(document);
        List<WebElement> triggersOfDoc = resultBlock.findElements(By.xpath(".//app-risk-item//span[contains(@class, 'risk')]"));
        for (WebElement trig : triggersOfDoc) {
            if (trig.getText().equals(trigger)) {
                String radioButtonXPath = "./../..//mat-radio-button[contains(@class, 'radio-checked')]//span[contains(text(),'" + button + "')]";
                boolean isChecked = trig.findElement(By.xpath(radioButtonXPath)).isDisplayed();
                if (select.equals("выбран")) {
                    assertTrue(isChecked, "Радио-кнопка " + button + " выбрана для триггера " + trigger);
                } else {
                    assertFalse(isChecked, "Радио-кнопка " + button + " не выбрана для триггера " + trigger);
                }
            }
        }
    }

    @Step
    @Title("Нажатие на кнопку {button} для триггера '{trigger}' из документа {document}")
    public L0CheckingDocumentsPage clickButtonsForTrigger(String button, String trigger, String document) {
        TextBlock resultBlock = findResultBlockByDocument(document);
        List<WebElement> triggersOfDoc = resultBlock.findElements(By.xpath(".//app-risk-item//span[contains(@class, 'risk')]"));
        for (WebElement trig : triggersOfDoc) {
            if (trig.getText().equals(trigger)) {
                $(trig.findElement(By.xpath(getIconDataByTitle(button)))).shouldBe(Condition.visible).click();
                return this;
            }
        }
        return this;
    }

    @Step
    @Title("Нажатие на радио-кнопку {button} в дополнительном поле '{addField}' для триггера {trigger}")
    public L0CheckingDocumentsPage clickButtonsForAddField(String button, String addField, String trigger) {
        TextBlock resultBlock = findResultBlockByTrigger(trigger);
        List<WebElement> addFieldsOfTrigger = resultBlock.findElements(By.xpath(".//div[contains(@class,'detail-item')]/div"));
        for (WebElement field : addFieldsOfTrigger) {
            if (field.getText().trim().equals(addField)) {
                $(field.findElement(By.xpath(getIconDataByTitle(button)))).shouldBe(Condition.visible).click();
                return this;
            }
        }
        throw new ElementInteractionException("Не найдено указанное дополнительное поле " + addField + " для триггера " + trigger);
    }


    @SafeVarargs
    @Step
    @Title("Проверить список документов по заголовку \"{stepName}\"")
    public final L0CheckingDocumentsPage checkDocsOnStep(String stepName, Map<String, List<String>>... optionalExpectedDocs) {
        if (!stepName.contains("1")) {
            // Если передан кастомный перечень документов, используем его, иначе - по умолчению EXPECTED_DOCS_STEP_2
            Map<String, List<String>> expectedDocs = (optionalExpectedDocs.length != 0)
                    ? optionalExpectedDocs[0]
                    : EXPECTED_DOCS_STEP_2;
            List<String> actualDocsAll = getActualListDocuments();
            for (Map.Entry<String, List<String>> entry : expectedDocs.entrySet()) {
                String headerDoc = entry.getKey(); // Заголовок документа
                List<String> expectedDocList = entry.getValue(); // Ожидаемый список документов
                // Получаем фактический список документов по заголовку
                List<String> actualDocs = getActualListDocumentsByHeader(headerDoc);

                // Проверяем, что фактический список документов совпадает с ожидаемым
                assertEquals(expectedDocList, actualDocs,
                        "Список документов не совпадает с актуальным для заголовка: " + headerDoc + ". Актуальные документы: " + actualDocs);
                checkIcons(expectedDocList);
            }
            // Проверяем, что все фактические документы присутствуют в ожидаемом списке
            for (String actualDoc : actualDocsAll) {
                boolean found = expectedDocs.values().stream()
                        .flatMap(List::stream)
                        .anyMatch(expectedDoc -> expectedDoc.equals(actualDoc));
                assertTrue(found, "Актуальный документ " + actualDoc + "  не найден в ожидаемом списке: " + expectedDocs);
            }
        } else {
            List<String> actualDocs = getActualListDocuments();
            assertEquals(EXPECTED_DOCS_STEP_1, actualDocs, "Список документов не совпадает с актуальным: " + actualDocs);
            checkIcons(EXPECTED_DOCS_STEP_1);
        }
        return this;
    }

    @Step
    @Title("Проверить видимость иконки напротив каждого документа")
    private void checkIcons(List<String> expectedDocList) {
        for (String doc : expectedDocList) {
            assertTrue(iconIsVisible(doc, "Документ не предоставлен"), "Напротив документа "
                    + doc + " не отображается иконка Документ НЕ предоставлен");
        }
    }

    @Override
    @Step
    @Title("цвет элемента {elementTitle} равен {value}")
    public L0CheckingDocumentsPage colorElementEquals(String elementTitle, String value) {
        TypifiedWebElement element = getSelf().getElementByTitle(elementTitle);
        String resultValue = element.getCssValue("fill");
        if (resultValue == null || resultValue.equals("rgb(0, 0, 0)")) {
            resultValue = element.getCssValue("background-color");
        }
        assertThat(Objects.equals(resultValue, value),
                "Цвет элемента " + elementTitle + " не равно ожидаемому " + value +
                        ". Фактическое значение: " + resultValue);
        return this;
    }

    private TextBlock findResultBlockByHeader(String header) {
        return findBlock(header, headerCache, documentHeaderTextBlock,
                docHeader -> docHeader.getText().trim().equals(header),
                "Не найден заголовок", null);
    }

    private TextBlock findResultBlockByDocument(String document) {
        return findBlock(document, documentCache, resultCheckTextBlock,
                rb -> rb.findElement(By.xpath("./mat-expansion-panel-header")).getText().trim().equals(document),
                "Не найден документ " + document,
                rb -> rb.findElement(By.xpath("./mat-expansion-panel-header")));
    }

    private TextBlock findResultBlockByTrigger(String trigger) {
        return findBlock(trigger, triggerCache, subResultCheckTextBlock,
                resultBlock -> resultBlock.getText().split("\n")[0].trim().equals(trigger),
                "Не найден триггер " + trigger, null);
    }

    private TextBlock findBlock(String key, Map<String, TextBlock> cache, List<TextBlock> blockList,
                                Predicate<TextBlock> predicate, String errorMessage,
                                Function<TextBlock, WebElement> elementFinder) {
        TextBlock cachedBlock = cache.get(key);
        if (cachedBlock != null) {
            try {
                if (elementFinder != null && elementFinder.apply(cachedBlock) != null) {
                    return cachedBlock; // Возвращаем кэшированный блок
                }
            } catch (StaleElementReferenceException | NoSuchElementException e) {
                cache.remove(key); // Удаляем устаревший элемент из кэша
            }
        }
        TextBlock resultBlock = blockList.stream()
                .filter(predicate)
                .findFirst()
                .orElseThrow(() -> new ElementInteractionException(errorMessage));
        cache.put(key, resultBlock);
        return resultBlock;
    }

    /**
     * Вспомогательный метод, который Наводит курсор на иконку в указанном блоке и возвращает текст подсказки.
     *
     * @param resultBlock блок, в котором ищется иконка
     * @return текст подсказки, если иконка найдена; throw в противном случае
     */
    private String hoverOnIcon(TypifiedWebElement resultBlock) {
        String[] icons = {ICON_DOCUMENT_PROVIDED, ICON_DOCUMENT_NOT_PROVIDED, ICON_TRIGGER, ICON_UNSELECT_ALL}; // Массив возможных иконок
        for (String icon : icons) {
            try {
                WebElement iconElement = resultBlock.findElement(By.xpath(icon));
                if (iconElement.isDisplayed()) {
                    actions().moveByOffset(-50, 0)
                            .moveToElement($(iconElement))
                            .pause(100)
                            .perform();
                    return textBlockToolTip.getText();
                }
            } catch (NoSuchElementException e) {
                // Игнорируем, если элемент не найден, продолжаем цикл
            }
        }
        return null;
    }

    /**
     * Возвращает значение data-icon в зависимости от заголовка иконки.
     *
     * @param iconTitle заголовок иконки
     * @return соответствующее значение data-icon или throw, если заголовок неверный
     */
    private String getIconDataByTitle(String iconTitle) {
        switch (iconTitle) {
            case "Документ предоставлен":
                return ICON_DOCUMENT_PROVIDED;
            case "Документ не предоставлен":
                return ICON_DOCUMENT_NOT_PROVIDED;
            case "Да":
                return RADIO_BUTTON_YES;
            case "Нет":
                return RADIO_BUTTON_NO;
            case "Нет данных":
                return RADIO_BUTTON_NO_DATA;
            case "Редактировать":
                return BUTTON_EDIT;
            default:
                throw new ElementInteractionException("Элемент задан неверно или не существует");
        }
    }
}
