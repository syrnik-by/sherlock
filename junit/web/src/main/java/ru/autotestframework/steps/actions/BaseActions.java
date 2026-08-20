package ru.autotestframework.steps.actions;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.WebDriverConditions;
import org.springframework.stereotype.Component;
import ru.autotestframework.ui_core.junit.InjectedPage;
import ru.psb.testit.annotations.Step;
import ru.psb.testit.annotations.Title;

import static com.codeborne.selenide.Selenide.refresh;
import static com.codeborne.selenide.Selenide.webdriver;

@Component
public class BaseActions<T extends BaseActions<T>> extends InjectedPage<T> implements
        IElementOperations<T>,
        IElementWait<T>,
        IElementState<T> {

    // Метод для получения текущего экземпляра страницы
    @SuppressWarnings("unchecked")
    @Override
    public T getSelf() {
        return (T) this; // Приведение типа с подавлением предупреждения
    }

    @Step
    @Title("Установить ожидание в секундах = {seconds} сек.")
    public static void sleep(int seconds) {
        try {
            Thread.sleep((long) seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Step
    @Title("цвет элемента {elementTitle} равен {value}")
    public T colorElementEquals(String elementTitle, String value) {
        return colorElementEq(elementTitle, value);
    }

    @Step
    @Title("url равен {url}")
    public T checkUrlEquals(String url) {
        webdriver().shouldHave(WebDriverConditions.url(url));
        return getSelf();
    }

    @Step
    @Title("url содержит {text}")
    public T checkUrlContains(String text) {
        webdriver().shouldHave(WebDriverConditions.urlContaining(text));
        return getSelf();
    }

    @Step
    @Title("Обновить страницу")
    public static void refreshPage() {
        refresh();
    }

    @Step
    @Title("Нажать на элемент {title}")
    public T clickOnElement(String title) {
        return clickOn(title);
    }

    @Step
    @Title("Заполнить поле {title} значением {value}")
    public T fillInput(String title, String value) {
        return fill(title, value);
    }

    @Step
    @Title("Очистить поле {title}")
    public T clearInput(String title) {
         getElementByTitle(title).clear();
         return getSelf();
    }

    @Step
    @Title("Проверить, что элемент с тайтлом {title} НЕ содержит значение {text}")
    public T checkElementByTitleNotContains(String title, String text) {
        return elementByTitleNotContains(title, text);
    }

    @Step
    @Title("Проверить, что элемент с тайтлом {title} содержит значение {text}")
    public T checkElementByTitleContains(String title, String text) {
        return elementByTitleContains(title, text);
    }

    @Step
    @Title("Проверить, что элемент с тайтлом {title} содержит значение равное {text}")
    public T checkElementByTitleEquals(String title, String text) {
        return elementByTitleEquals(title, text);
    }

    @Step
    @Title("Получить значение из элемента {title}")
    public String getValueByElementTitle(String title) {
        getElementByTitle(title).shouldBe(Condition.visible, true);
        return getElementByTitle(title).getAttribute("value");
    }

    @Step
    @Title("Проверить наличие атрибута {attr} у элемента {title}")
    public boolean checkAttributeElementTitle(String attr, String title) {
        return getElementByTitle(title).getAttribute(attr) != null;
    }

    @Step
    @Title("Проверка, что элемент с тайтлом {title} {visible}")
    public T assertElementByTitleVisibility(String title, String visible) {
        return elementByTitleVisibility(title, visible);
    }

    @Step
    @Title("Проверка, что элемент с тайтлом {title} {editing}")
    public T assertElementByTitleNotAvailableEditing(String title, String editing) {
        return elementByTitleNotAvailableEditing(title, editing);
    }

    @Step
    @Title("Проверка, что элемент с тайтлом {title} {active}")
    public T assertElementByTitleActivity(String title, String active) {
        return elementByTitleActivity(title, active);
    }

    @Step
    @Title("Проверка, что элемент с тайтлом {title} {block}")
    public T assertElementByTitleBlock(String title, String block) {
        return elementByTitleBlock(title, block);
    }

    @Step
    @Title("Проверка, что чек-бокс с тайтлом {title} {select}")
    public T assertElementByTitleSelected(String title, String select) {
        return elementByTitleSelected(title, select);
    }

    @Step
    @Title("Дождаться появления текста {text}")
    public T waitText(int seconds, String text) {
        return waittext(seconds, text);
    }

    @Step
    @Title("Двойное нажатие на элемент содержащий текст {text}")
    public T doubleClickByText(String text) {
        return doubleClickOn(text);
    }

    @Step
    @Title("Получить значение из элемента {title}")
    public String getTextByElementTitle(String title) {
        getElementByTitle(title).shouldBe(Condition.visible, true);
        return getElementByTitle(title).getText();
    }
}
