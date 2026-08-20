package ru.autotestframework.steps.elements;

import com.codeborne.selenide.Condition;
import ru.autotestframework.web_elements.elements.typified.TypifiedWebElement;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ru.autotestframework.util.Validator.assertThat;

public interface IFieldSteps<T> {

    T getSelf();

    default T checklistElements(TypifiedWebElement elementTitle, List<String> expectedListElem) {
        elementTitle.getSelenideElement().shouldBe(Condition.visible, Duration.ofSeconds(10));
        // Получаем актуальные значения и удаляем пробелы
        List<String> actualListElem = Arrays.stream(elementTitle.getText().split("\n"))
                .map(String::trim)
                .filter(value -> !value.equals("Удалить все"))
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
        return getSelf();
    }

    default T checklistContainsElements(TypifiedWebElement elementTitle, List<String> expectedListElem) {
        elementTitle.getSelenideElement().shouldBe(Condition.visible, Duration.ofSeconds(10));

        // Получаем актуальные значения и удаляем пробелы
        List<String> actualListElem = Arrays.stream(elementTitle.getText().split("\n"))
                .map(String::trim)
                .filter(value -> !value.equals("Удалить все"))
                .collect(Collectors.toList());

        List<String> trimmedExpectedListElem = expectedListElem.stream()
                .map(String::trim)
                .collect(Collectors.toList());

        // Проверка на вхождение ожидаемых значений в актуальный набор
        List<String> notFoundValues = trimmedExpectedListElem.stream()
                .filter(value -> !actualListElem.contains(value))
                .collect(Collectors.toList());

        assertThat(notFoundValues.isEmpty(), "Некоторые ожидаемые значения отсутствуют в актуальном наборе: \n" + notFoundValues +
                "\nАктуальные значения: \n" + actualListElem +
                "\nОжидаемые значения: \n" + trimmedExpectedListElem);

        return getSelf();
    }


    default List<String> getListElementsFromField(TypifiedWebElement element) {
        element.shouldBe(Condition.visible, true);
        return new ArrayList<>(List.of(element.getText().split("\n")));
    }
}
