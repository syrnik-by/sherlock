package ru.autotestframework.steps.actions;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ex.ElementNotFound;
import org.openqa.selenium.WebElement;
import ru.autotestframework.web_elements.elements.typified.TypifiedWebElement;

import java.util.Objects;

import static com.codeborne.selenide.Selenide.*;
import static ru.autotestframework.util.Validator.assertThat;

interface IElementOperations<T extends BaseActions<T>> extends IElementWait<T> {

    T getSelf();

    default T clickOn(String title) {
        waitElementVisible(title).click(title);
        return getSelf(); // Используем getSelf вместо прямого приведения
    }

    default T fill(String title, String value) {
        waitElementVisible(title).fillField(title, value);
        return getSelf(); // Используем getSelf вместо прямого приведения
    }

    default T doubleClickOn(String text) {
        try {
            sleep(3000);
            WebElement element = $x("//td//*[contains(text(), '" + text + "')]").shouldBe(Condition.visible);

            int width = element.getSize().getWidth();
            int height = element.getSize().getHeight();
            int offsetX = width / 2;
            int offsetY = (int) (height * 0.95);

            actions().moveToElement(element, offsetX, offsetY).doubleClick().perform();
        } catch (ElementNotFound e) {
            throw new RuntimeException("Ошибка: элемент с текстом '" + text + "' не найден.", e);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при попытке двойного клика на элемент с текстом '" + text + "': " + e.getMessage(), e);
        }
        return getSelf();
    }

    default T elementByTitleContains(String title, String text) {
        getSelf().getElementByTitle(title).shouldBe(Condition.visible, true);
        getSelf().getElementByTitle(title).shouldBe(Condition.text(text), true);
        return getSelf();
    }

    default T elementByTitleNotContains(String title, String text) {
        getSelf().getElementByTitle(title).shouldBe(Condition.text(text), false);
        return getSelf();
    }

    default T elementByTitleEquals(String title, String text) {
        getSelf().getElementByTitle(title).shouldBe(Condition.exactText(text), true);
        return getSelf();
    }

    default T colorElementEq(String elementTitle, String value) {
        TypifiedWebElement element = getSelf().getElementByTitle(elementTitle);
        String resultValue = element.getCssValue("color");
        assertThat(Objects.equals(resultValue, value),
                "Цвет элемента " + elementTitle + " не равно ожидаемому " + value +
                        ". Фактическое значение: " + resultValue);
        return getSelf();
    }
}
