package ru.autotestframework.steps.elements;

import com.codeborne.selenide.*;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import ru.autotestframework.ui_core.exceptions.ElementInteractionException;
import ru.autotestframework.ui_core.typified_elements.IElement;
import ru.autotestframework.web_elements.elements.typified.TypifiedWebElement;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.Assert.*;
import static ru.autotestframework.util.Validator.assertThat;
import static ru.autotestframework.utils.Constants.BODY;

public interface IDropDownListSteps<T> {

    ElementsCollection SELECT_OPTIONS = $$x(
            "//mat-option//span[contains(@class,'option')]" +
                    "|//button[@role='menuitem']/span[text()]" +
                    "|//button[@role='menuitem']" +
                    "|//nz-option-item" +
                    "|//li[@nz-menu-item]"+
                    "|//nz-auto-option");
    ElementsCollection SELECT_ALL = $$x("//div[contains(@class,'select-all')]/mat-checkbox");
    ElementsCollection ONLY_SELECTED = $$x("//mat-option[@aria-selected='true']");

    T getSelf();

    /**
     * Выбирает значения из выпадающего списка
     *
     * <p> Сначала проверяет открыт ли список. Если список НЕ открыт, то открывает его с помощью клика, в ином случае
     * пропускает клик. Затем выбирает элементы из списка кликая на них.
     *
     * @param dropDownList выпадающий список
     * @param values       список значений для выборки
     */
    default T selectFromDropDownList(IElement dropDownList, List<String> values) {
        dropDownList.shouldBe(visible, true);
        if (checkDropDownListOpen(dropDownList)) {
            dropDownList.click();
        }
        ElementsCollection actualListElem = getListElements(values.get(0).equals("Выбрать все")
                ? SELECT_ALL
                : SELECT_OPTIONS);
        SelenideElement listElem = actualListElem.get(0);
        boolean isCheckboxOption = listElem.should(visible).getTagName().equals("nz-option-item");

        if (!isCheckboxOption) {
            for (String value : values) {
                if (checkBoxIsSelected(value) != null) {
                    handleCheckboxOption(dropDownList, actualListElem, value);
                } else {
                    handleNormalOption(dropDownList, actualListElem, value);
                }
            }
            closeDropDownList();
        } else {
            for (int i = 0; i < values.size(); i++) {
                if (i > 0) {
                    dropDownList.click();
                }
                selectOption(actualListElem, values.get(i), dropDownList.getText());
                actualListElem.shouldHave(CollectionCondition.size(0));
            }
        }
        return getSelf();
    }

    private void handleCheckboxOption(IElement dropDownList, ElementsCollection actualListElem, String value) {
        Boolean beforeClick = checkBoxIsSelected(value);
        selectOption(actualListElem, value, dropDownList.getTitle());
        if (value.equals("Выбрать все")) {
            sleep(1000);
        }
        Boolean afterClick = checkBoxIsSelected(value);
        assertThat(beforeClick != afterClick, "Неверное значение чек-бокса для значения " + value);
    }

    private void handleNormalOption(IElement dropDownList, ElementsCollection actualListElem, String value) {
        String curValue = dropDownList.getText().trim();
        if (!curValue.equals(value)) {
            selectOption(actualListElem, value, dropDownList.getTitle());
        }
    }

    private Boolean checkBoxIsSelected(String value) {
        SelenideElement checkBoxSub = $x("//button[@role='menuitem']/span[contains(text(),'" + value + "')]/..//input[@type='checkbox']");
        SelenideElement checkBox = $x("//mat-option//span[contains(text(),'" + value + "')]/../../mat-pseudo-checkbox");
        SelenideElement checkBoxSelectAll = $x("//div[contains(@class,'select-all')]/mat-checkbox");
        if (checkBoxSub.exists()) {
            return checkBoxSub.isSelected();
        } else if (checkBox.exists()) {
            return Objects.requireNonNull(checkBox.getAttribute("class")).contains("checked");
        } else if (checkBoxSelectAll.exists()) {
            return Objects.requireNonNull(checkBoxSelectAll.getAttribute("class")).contains("checked");
        }
        return null;
    }

    default T assertCheckBoxesSelected(IElement dropDownList, List<String> values, boolean expectedState) {
        dropDownList.shouldBe(visible, true);
        dropDownList.click();
        for (String value : values) {
            Boolean isSelected = checkBoxIsSelected(value);
            if (isSelected == null) {
                throw new ElementInteractionException("Чек-бокс для значения '" + value + "' не найден.");
            }
            assertEquals("Чек-бокс для значения '" + value + "' должен быть " + (expectedState ? "включен" : "отключен") +
                    ", но он " + (isSelected ? "включен" : "отключен") + ".", expectedState, isSelected);
        }
        closeDropDownList();
        return getSelf();
    }

    default T cleanDropDownList(IElement dropDownList) {
        dropDownList.click();
        ElementsCollection values = getListElements(ONLY_SELECTED);
        for (SelenideElement value : values) {
            String str = value.getText().strip();
            if (checkBoxIsSelected(str) != null && Boolean.TRUE.equals(checkBoxIsSelected(str))) {
                handleCheckboxOption(dropDownList, values, str);
            }
        }
        return getSelf();
    }

    private void selectOption(ElementsCollection actualListElem, String value, String dropDownList) {
        boolean valueFound = false;

        // Сначала проверяем на полное совпадение
        for (SelenideElement option : actualListElem) {
            String optionText = option.getText().strip();
            String optionTitle = Objects.requireNonNull(option.getAttribute("title")).strip();

            if (optionText.equals(value) || optionTitle.equals(value)) {
                option.shouldBe(visible).click();
                valueFound = true;
                break; // Выходим из цикла при полном совпадении
            }
        }

        // Если полное совпадение не найдено, проверяем на частичное совпадение
        if (!valueFound) {
            for (SelenideElement option : actualListElem) {
                String optionText = option.getText().strip();
                String optionTitle = Objects.requireNonNull(option.getAttribute("title")).strip();

                if (optionText.contains(value) || optionTitle.contains(value)) {
                    option.shouldBe(visible).click();
                    valueFound = true;
                    break; // Выходим из цикла при частичном совпадении
                }
            }
        }

        if (!valueFound) {
            throw new ElementInteractionException("Значение " + value + " не найдено в выпадающем списке " + dropDownList);
        }
    }

    default T checkDropdownListElements(TypifiedWebElement elementTitle, List<String> expectedListElem) {
        elementTitle.click();
        ElementsCollection listElem = getListElements(SELECT_OPTIONS);
        List<String> actualListElem = listElem.stream()
                .map(element -> {
                    String title = element.getAttribute("title");
                    return (title != null && !title.isEmpty()) ? title : element.getText();
                })
                .collect(Collectors.toList());

        List<String> trimmedExpectedListElem = expectedListElem.stream()
                .map(String::trim)
                .collect(Collectors.toList());

        // Проверка на недостающие значения
        List<String> missingValues = trimmedExpectedListElem.stream()
                .filter(value -> {
                    boolean isMissing = !actualListElem.contains(value);
                    if (isMissing) {
                        System.out.println("Недостающее значение: " + value);
                    }
                    return isMissing;
                })
                .collect(Collectors.toList());

        assertThat(missingValues.isEmpty(), "Недостающие значения в актуальном наборе: \n" + missingValues +
                "\nАктуальные значения: \n" + actualListElem +
                "\nОжидаемые значения: \n" + trimmedExpectedListElem);
        elementTitle.click();
        return getSelf();
    }

    default T checkMultipleChoice(IElement dropDownList, boolean editable) {
        WebElement webElement = dropDownList.getWrappedElement();
        SelenideElement dropDown = $(webElement);
        SelenideElement optionsContainer = $x("//nz-option-container | //div[@role = 'listbox']");
        dropDown.click();
        ElementsCollection options = getListElements($$x(".//nz-option-item//span[text()] | //mat-option//span[text()] | //nz-option-item//div[text()]"));
        options.get(1).click();
        if (editable) {
            assertTrue("Множественный выбор - доступен", optionsContainer.shouldBe(visible).exists());
            options.get(1).click();
            BODY.click();
        } else {
            assertFalse("Множественный выбор - не доступен", optionsContainer.shouldBe(disappear).exists());
        }
        return getSelf();
    }

    default ElementsCollection getListElements(ElementsCollection elements) {
        elements = elements.shouldHave(CollectionCondition.sizeGreaterThan(0));
        if (elements.isEmpty()) {
            throw new ElementInteractionException("Выпадающий список пустой.");
        }
        return elements;
    }

    default void closeDropDownList() {
        BODY.click(ClickOptions.usingDefaultMethod().offset(400, 200));
        if (SELECT_OPTIONS.get(0).isDisplayed()) {
            BODY.sendKeys(Keys.ESCAPE);
        }
    }

    /**
     * Проверяет открыт ли выпадающий список при помощи проверки атрибутов списка.
     *
     * @param dropDownList выпадающий список
     */
    default boolean checkDropDownListOpen(IElement dropDownList) {
        dropDownList.shouldBe(visible, true);
        String className = dropDownList.getAttribute("className");
        String ariaExpanded = dropDownList.getAttribute("aria-expanded");
        return (className == null || !className.contains("ant-select-open"))
                && (ariaExpanded == null || !ariaExpanded.contains("true"));
    }
}
