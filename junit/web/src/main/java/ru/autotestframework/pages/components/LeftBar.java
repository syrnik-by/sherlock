package ru.autotestframework.pages.components;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import ru.autotestframework.ui_core.page_manager.Element;
import ru.autotestframework.ui_core.page_manager.PageEntry;
import ru.autotestframework.web_elements.elements.Button;
import ru.autotestframework.web_elements.elements.TextBlock;
import ru.psb.testit.annotations.Step;
import ru.psb.testit.annotations.Title;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

@PageEntry(title = "Левая панель")
public class LeftBar<T extends Notifications<T>> extends Notifications<T> {

    private static final ElementsCollection MENU_ITEM_LOCATOR = $$x("//a[contains(@class,'ant-menu-item')]|//li[contains(@class,'ant-menu')]");
    private static final ElementsCollection SUBMENU_ITEM_LOCATOR = $$x("//div[contains(@class,'cdk-overlay-pane')]/descendant::span");
    private static final SelenideElement MENU_ICON_LOCATOR = $x("//div[contains(@class,'header-logo-burger')]/i[contains(@class,'menu')]");

    @Element("Модальное окно Информация об ошибке")
    @FindBy(xpath = "//div[contains(@class, 'modal-error')]//div[contains(text(), 'Информация об ошибке')]")
    public TextBlock modalInfoError;

    @Element("Кнопка Ок на модальном окне")
    @FindBy(xpath = "//div[contains(@class, 'modal-error')]//button/span[contains(text(), 'Ок')]")
    public Button buttonOkModalInfoError;

    @Step
    @Title("перейти с помощью меню на страницу {menuPath}")
    public T openMenuLinks(String menuPath) {
        waitBusyCondition();
        openMenuIfClosed();
        checkNotifications();
        String[] menuLinks = menuPath.split(">");
        List<String> linkList = Arrays.stream(menuLinks).map(String::trim).collect(Collectors.toList());
        for (int i = 0; i < linkList.size(); i++) {
            String linkText = linkList.get(i);
            waitBusyCondition();
            if (modalInfoError.isDisplayed()) {
                buttonOkModalInfoError.click();
            }
            getMenuItemViaText(linkText, i == 0 ? MENU_ITEM_LOCATOR : SUBMENU_ITEM_LOCATOR).click();
        }
        return getSelf();
    }

    public SelenideElement getMenuItemViaText(String text, ElementsCollection itemLocator) {
        return itemLocator.find(Condition.text(text)).shouldBe(visible);
    }

    private void openMenuIfClosed() {
        if (MENU_ICON_LOCATOR
                .shouldBe(enabled)
                .is(Condition.attributeMatching("class", "^.*menu-fold.*$")
                )) {
            MENU_ICON_LOCATOR.click();
        }
    }

}
