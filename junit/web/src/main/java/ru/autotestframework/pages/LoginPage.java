package ru.autotestframework.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverConditions;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.FindBy;
import org.springframework.stereotype.Component;
import ru.autotestframework.configuration.FrameworkDefaultVariables;
import ru.autotestframework.pages.components.TopBar;
import ru.autotestframework.ui_core.page_manager.Element;
import ru.autotestframework.ui_core.page_manager.PageEntry;
import ru.autotestframework.web_elements.elements.Button;
import ru.autotestframework.web_elements.elements.TextInput;
import ru.psb.testit.annotations.Step;
import ru.psb.testit.annotations.Title;

import java.util.Map;

import static com.codeborne.selenide.Selenide.webdriver;

@Component
@PageEntry(title = "Страница авторизации")
public class LoginPage extends TopBar<LoginPage> {

    @Element("Поле ввода логина")
    @FindBy(xpath = "//input[@formcontrolname='username']")
    public TextInput loginInput;

    @Element("Поле ввода пароля")
    @FindBy(xpath = "//input[@formcontrolname='password']")
    public TextInput passwordInput;

    @Element("Кнопка Войти")
    @FindBy(xpath = "//button[./span[contains(text(), 'Войти')]]")
    public Button authActiveButton;

    private final Map<String, String> frameworkDefaultVariables;

    public LoginPage(FrameworkDefaultVariables defaultVariables) {
        this.frameworkDefaultVariables = defaultVariables.getVariables();
    }

    @Step
    @Title("Открыть страницу авторизации ЛКА Шерлок ")
    public LoginPage openAuthorizationPage() {
        Selenide.open(frameworkDefaultVariables.get("lka.url"));
        return this;
    }

    @Step
    @Title("Авторизованы через UI")
    public LoginPage loginViaUi() {
        loginViaUiOnUser("user");
        return this;
    }

    @Step
    @Title("Авторизованы через UI")
    public LoginPage loginViaUiOnUser(String user) {
        if (loginInput.isDisplayed()) {
            loginInput.clean();
            loginInput.write(frameworkDefaultVariables.get("lka." + user));
            passwordInput.clean();
            passwordInput.write(frameworkDefaultVariables.get("lka.password"));
            authActiveButton.click();
            waitBusyCondition();
            checkNotifications();
        }
        return this;
    }
}
