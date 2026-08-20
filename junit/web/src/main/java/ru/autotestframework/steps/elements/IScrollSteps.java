package ru.autotestframework.steps.elements;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebElement;
import ru.autotestframework.ui_core.exceptions.ElementInteractionException;
import ru.autotestframework.web_elements.elements.typified.TypifiedWebElement;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.codeborne.selenide.Selenide.*;
import static ru.autotestframework.steps.elements.IDropDownListSteps.SELECT_OPTIONS;
import static ru.autotestframework.util.Validator.assertThat;

public interface IScrollSteps<T> {

    T getSelf();

    Map<WebElement, WebElement> memoryForScrollableContainers = new HashMap<>();

    SelenideElement listChoosingOption = SELECT_OPTIONS.get(0);

    /**
     * Проверяет наличие и функционирование скролла у элемента
     *
     * <p> Ищет родительский элемент со скроллом и проверяет функциональность скролла путем прокручивания (скроллинга).
     * Проверяет отображаение элементов выпадающего списка. Если такие элементы отображатся, то ищет родительский элемент
     * со скроллом у данных элементов выпадающего списка. Если такие элементы не отображаются, то ищет родительский элемент
     * со скроллом у переданного элемента. При editable==false проверяется, что скролл отсутствует
     *
     * @param scrollType       тип скроллинга: вертикальный или горизонтальный
     * @param elementTitle     элемент со скроллом
     * @param editable         присутствие скролла
     * @param checkThisElement строгая проверка наличия скролла. Если true, то будет проверять наличие скролла именно у
     *                         переданного элемента, если false, то будет проверять у родительских элементов
     */
    default T checkScrolling(String scrollType, TypifiedWebElement elementTitle, Boolean editable, Boolean... checkThisElement) {
        // Проверяем наличие контейнера со скроллом
        try {
            WebElement scrollableContainer;
            if (checkThisElement != null && checkThisElement.length != 0) {
                // Проверяем скролл строго у этого элемента
                scrollableContainer = elementTitle;
            } else {
                // Проверяем отображение элементов выпадающего списка
                scrollableContainer = listChoosingOption.isDisplayed() ?
                        getScrollableContainer(listChoosingOption, scrollType) : getScrollableContainer(elementTitle, scrollType);
            }
            if (!editable) {
                // У элемента не должно быть скролла, но он есть
                throw new ElementInteractionException("У элемента " + elementTitle + " присутствует " + scrollType + " скролл");
            }
            // Проверяем функционирование скролла
            assertThat(checkFunctioningScroll(scrollableContainer, scrollType.equals("вертикальный") ? "scrollTop" : "scrollLeft"),
                    scrollType + " скроллинг не функционирует для элемента " + elementTitle.getText());
            return getSelf();
        } catch (ElementInteractionException e) {
            if (!editable) {
                return getSelf();
            } else {
                throw new ElementInteractionException("У элемента " + elementTitle + " нет " + scrollType + " скролла");
            }
        }
    }

    /**
     * Проверяет функционирование скролла
     *
     * <p> Изменяет значение scrollTop или scrollLeft в зависимости от типа скролла на 200 пикселей, тем самым двигая
     * скролл вниз или вправо. Если скролл изменил положение, то скролл считается функционирующим. После проверки возвращает
     * скролл в исходное состояние.
     *
     * @param element элемент со скроллом
     * @param scroll  параметр для изменения: scrollTop или scrollLeft
     */
    private boolean checkFunctioningScroll(WebElement element, String scroll) {
        int initialScroll = Integer.parseInt(Objects.requireNonNull(element.getAttribute(scroll)));
        executeJavaScript("arguments[0]." + scroll + " += 200;", element);
        int newScroll = Integer.parseInt(Objects.requireNonNull(element.getAttribute(scroll)));
        executeJavaScript("arguments[0]." + scroll + " -= 200;", element);
        return newScroll > initialScroll;
    }

    /**
     * Скроллинг элемента вниз или вправо
     *
     * <p> Прокручивает элемент на указаное значение вправо или вниз в зависимости от scrollType.
     * Проверяет отображаение элементов выпадающего списка. Если такие элементы отображатся, то ищет родительский элемент
     * со скроллом у данных элементов выпадающего списка. Если такие элементы не отображаются, то ищет родительский элемент
     * со скроллом у переданного элемента.
     *
     * @param elementTitle элемент со скроллом
     * @param scrollType   тип скроллинга: вертикальный или горизонтальный
     * @param amount       количество пикселей для прокрутки
     */
    default T scrollDropDownList(TypifiedWebElement elementTitle, String scrollType, int amount) {
        WebElement scrollableContainer = listChoosingOption.isDisplayed() ?
                getScrollableContainer(listChoosingOption, scrollType) : getScrollableContainer(elementTitle, scrollType);
        executeJavaScript("arguments[0]." + (scrollType.equals("вертикальный") ? "scrollTop" : "scrollLeft") + " += " + amount + ";", scrollableContainer);
        return getSelf();
    }

    /**
     * Проверяет нахождение скролла в нижней позиции
     *
     * <p> Проверяет дохождение скролла до низа с погрешностью в 2 пикселя.
     * Проверяет отображаение элементов выпадающего списка. Если такие элементы отображатся, то ищет родительский элемент
     * со скроллом у данных элементов выпадающего списка. Если такие элементы не отображаются, то ищет родительский элемент
     * со скроллом у переданного элемента.
     *
     * @param elementTitle элемент со скроллом
     */
    default boolean isABottom(TypifiedWebElement elementTitle) {
        WebElement scrollableContainer = listChoosingOption.isDisplayed() ?
                getScrollableContainer(listChoosingOption, "вертикальный") : getScrollableContainer(elementTitle, "вертикальный");
        int scrollTop = Integer.parseInt(Objects.requireNonNull(scrollableContainer).getAttribute("scrollTop"));
        int clientHeight = Integer.parseInt(Objects.requireNonNull(scrollableContainer).getAttribute("clientHeight"));
        int scrollHeight = Integer.parseInt(Objects.requireNonNull(scrollableContainer).getAttribute("scrollHeight"));
        return scrollTop + clientHeight >= scrollHeight - 2;
    }

    /**
     * Проверяет нахождение скролла в верхней позиции
     *
     * <p> Проверяет нахождение скролла в верхней (изначальной) позиции
     * Проверяет отображаение элементов выпадающего списка. Если такие элементы отображатся, то ищет родительский элемент
     * со скроллом у данных элементов выпадающего списка. Если такие элементы не отображаются, то ищет родительский элемент
     * со скроллом у переданного элемента.
     *
     * @param elementTitle элемент со скроллом
     */
    default boolean isATop(TypifiedWebElement elementTitle) {
        WebElement scrollableContainer = listChoosingOption.isDisplayed() ?
                getScrollableContainer(listChoosingOption, "вертикальный") : getScrollableContainer(elementTitle, "вертикальный");
        return Integer.parseInt(Objects.requireNonNull(scrollableContainer).getAttribute("scrollTop")) == 0;
    }

    /**
     * Ищет родительский элемент со скроллом
     *
     * <p> Ищет родительский элемент со скроллом от текущего элемента. Сначала проверяет был ли уже найден родительский\
     * элемент со скроллом для данного элемента. Если был, то возвращает его. Если нет, то ищет заново. Проверяет
     * наличие скролла по каждому элементу, идя рекурсивно вверх, пока не дойдет до html или body.
     *
     * @param elementTitle элемент со скроллом
     * @param scrollType   тип скроллинга: вертикальный или горизонтальный
     */
    private WebElement getScrollableContainer(WebElement elementTitle, String scrollType) {
        // Оптимизация. Если мы уже получали родительский элемент со скроллом для этого элемента, то проверем что на
        // нем все ещё есть скролл (после возможных изменений) и вернем его
        if (memoryForScrollableContainers.containsKey(elementTitle)) {
            WebElement scrollableContainer = memoryForScrollableContainers.get(elementTitle);
            boolean bool = hasScroll(scrollableContainer, scrollType); // Проверяем есть ли на нем скролл.
            if (bool) {
                return scrollableContainer;
            } else { // Если скролла нет, то удаляем из памяти и пытаемся найти заново
                memoryForScrollableContainers.remove(elementTitle);
            }
        }
        SelenideElement current = Selenide.$(elementTitle);
        while (true) {
            if (Boolean.TRUE.equals(hasScroll(current, scrollType))) {
                memoryForScrollableContainers.put(elementTitle, current); // Сохраняем родительский элемент на будущее
                return current;
            }
            // Проверяем есть ли родительский элемент
            // Если текущий элемент body или html, то считаем что родительский элемент скролла не имеет
            String tagName = current.getTagName();
            if ("body".equalsIgnoreCase(tagName) || "html".equalsIgnoreCase(tagName)) {
                throw new ElementInteractionException("У элемента " + elementTitle + " нет родительского элемента с " + scrollType + " скроллом");
            }
            try {
                SelenideElement parent = current.parent();
                // Проверяем не вернулся ли тот же элемент (проверка нахождения на корневом уровне)
                if (parent.equals(current)) {
                    throw new ElementInteractionException("У элемента " + elementTitle + " нет родительского элемента с " + scrollType + " скроллом");
                }
                current = parent;
            } catch (ElementInteractionException e) {
                throw new ElementInteractionException("У элемента " + elementTitle + " нет родительского элемента с " + scrollType + " скроллом");
            }
        }
    }

    /**
     * Проверяет наличие скролла на элементе
     *
     * <p> Проверяет наличие скролла на данном элементе при помощи сравнения scroll и client параметров.
     *
     * @param elementTitle элемент со скроллом
     * @param scrollType   тип скроллинга: вертикальный или горизонтальный
     */
    private boolean hasScroll(WebElement elementTitle, String scrollType) {
        int scrollHeightOrWidth = Integer.parseInt(Objects.requireNonNull(elementTitle.getAttribute(
                scrollType.equals("вертикальный") ? "scrollHeight" : "scrollWidth")));
        int clientHeightOrWidth = scrollType.equals("вертикальный") ? elementTitle.getSize().getHeight() : elementTitle.getSize().getWidth();
        return scrollHeightOrWidth > clientHeightOrWidth;
    }
}
