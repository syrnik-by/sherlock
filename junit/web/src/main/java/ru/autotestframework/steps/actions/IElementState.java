package ru.autotestframework.steps.actions;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import ru.autotestframework.web_elements.elements.typified.TypifiedWebElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static ru.autotestframework.util.Validator.assertThat;

interface IElementState<T extends BaseActions<T>> {

    T getSelf();

    default T elementByTitleVisibility(String title, String visible) {
        if (visible.equals("отображается")) {
            assertThat(checkElementByTitleVisibility(title, true), "Элемент с тайтлом " + title + " не отображается");
        } else {
            assertThat(!checkElementByTitleVisibility(title, false), "Элемент с тайтлом " + title + " отображается");
        }
        return getSelf();
    }

    default boolean checkElementByTitleVisibility(String title, boolean visible) {
        SelenideElement element = $(getSelf().getElementByTitle(title).getWrappedElement());
        if (visible) {
            element.shouldBe(Condition.visible, Duration.ofSeconds(5));
            return true;
        } else {
            if (!element.exists()) {
                return false;
            }
            return false;
        }
    }

    default T elementByTitleActivity(String title, String active) {
        if (active.equals("активен")) {
            assertThat(checkElementByTitleActivity(title), "Элемент с тайтлом " + title + " не активен");
        } else {
            assertThat(!checkElementByTitleActivity(title), "Элемент с тайтлом " + title + " активен");
        }
        return getSelf();
    }

    private boolean checkElementByTitleActivity(String title) {
        TypifiedWebElement element = getSelf().getElementByTitle(title);
        element.shouldBe(Condition.visible, true);
        return element.isEnabled() && !element.getCssValue("pointer-events").equals("none");
    }

    default T elementByTitleSelected(String title, String select) {
        if (select.equals("выбран")) {
            assertThat(checkElementByTitleSelected(title), "Чек-бокс с тайтлом " + title + " не выбран");
        } else {
            assertThat(!checkElementByTitleSelected(title), "Чек-бокс с тайтлом " + title + " выбран");
        }
        return getSelf();
    }

    private boolean checkElementByTitleSelected(String title) {
        return getSelf().getElementByTitle(title).getWrappedElement().findElement(By.xpath(".//input[@type='checkbox']")).isSelected();
    }

    default T elementByTitleBlock(String title, String block) {
        if (block.equals("заблокирован")) {
            assertThat(checkElementByTitleBlock(title), "Элемент с тайтлом " + title + " не заблокирован");
        } else {
            assertThat(!checkElementByTitleBlock(title), "Элемент с тайтлом " + title + " заблокирован");
        }
        return getSelf();
    }

    private boolean checkElementByTitleBlock(String title) {
        TypifiedWebElement element = getSelf().getElementByTitle(title);
        return element.isEnabled() && element.findElement(By.tagName("input")).getAttribute("readonly") != null;
    }

    default T elementByTitleNotAvailableEditing(String title, String editing) {
        if (editing.equals("доступен для редактирования")) {
            assertThat(!checkElementByTitleNotAvailableEditing(title), "Элемент с тайтлом " + title + " не доступен для редактирования");
        } else {
            assertThat(checkElementByTitleNotAvailableEditing(title), "Элемент с тайтлом " + title + " доступен для редактирования");
        }
        return getSelf();
    }

    private boolean checkElementByTitleNotAvailableEditing(String title) {
        TypifiedWebElement element = getSelf().getElementByTitle(title);
        element.shouldBe(Condition.visible, true);
        return "true".equals(element.getAttribute("aria-disabled")) || "true".equals(element.getAttribute("disabled"));
    }
}
