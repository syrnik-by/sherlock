package ru.autotestframework.steps.elements;


import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.WebDriver;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;

import static ru.autotestframework.pages.BasePage.sleep;

public interface ITabSteps<T> {

    T getSelf();

    default T switchToNewtab() {
        sleep(1);
        WebDriver driver = WebDriverRunner.getWebDriver();
        LinkedHashSet<String> allWindows = new LinkedHashSet<>(driver.getWindowHandles());
        LinkedHashMap<String, String> urlToWindowMap = new LinkedHashMap<>(); // Словарь для хранения URL и соответствующих окон

        // Перебираем все открытые вкладки
        for (String window : allWindows) {
            driver.switchTo().window(window); // Переключаемся на вкладку
            String currentUrl = driver.getCurrentUrl(); // Получаем URL текущей вкладки

            // Проверяем, есть ли уже такой URL в словаре
            if (urlToWindowMap.containsKey(currentUrl)) {
                driver.close();
                driver.switchTo().window(urlToWindowMap.get(currentUrl));
                return getSelf();
            }
            urlToWindowMap.put(currentUrl, window);
        }
        return getSelf(); // Возвращаем текущий объект
    }

    default T switchToOnetab() {
        sleep(1);
        WebDriver driver = WebDriverRunner.getWebDriver();
        LinkedHashSet<String> allWindow = new LinkedHashSet<>(driver.getWindowHandles());

        for (String window : allWindow) {
            driver.switchTo().window(String.valueOf(window));
        }
        return getSelf();
    }

    default T closeCurrentTab() {
        WebDriver driver = WebDriverRunner.getWebDriver();
        String currentWindow = driver.getWindowHandle(); // Сохраняем текущую вкладку
        LinkedHashSet<String> allWindows = new LinkedHashSet<>(driver.getWindowHandles()); // Получаем все открытые вкладки

        // Закрываем текущую вкладку
        driver.close();

        // Переключаемся на другую вкладку, если она есть
        if (allWindows.size() > 1) {
            LinkedList<String> windowsList = new LinkedList<>(allWindows);
            int currentIndex = windowsList.indexOf(currentWindow); // Находим индекс текущей вкладки
            if (currentIndex > 0) {
                driver.switchTo().window(windowsList.get(currentIndex - 1)); // Переключаемся на вкладку слева
            }
        }

        return getSelf();
    }

    default T switchToFirstTab() {
        if (WebDriverRunner.hasWebDriverStarted()) {
            WebDriver driver = WebDriverRunner.getWebDriver();
            LinkedHashSet<String> windows = new LinkedHashSet<>(driver.getWindowHandles());
            String firstWindow = windows.iterator().next();
            // Проверяем, есть ли более одной вкладки
            if (windows.size() > 1) {
                // Закрываем все остальные вкладки
                windows.stream()
                        .filter(window -> !window.equals(firstWindow))
                        .forEach(window -> {
                            driver.switchTo().window(window);
                            driver.close();
                        });
                driver.switchTo().window(firstWindow);
            } else if (windows.size() == 1) {
                driver.switchTo().window(firstWindow);
            }
        }
        return getSelf();
    }
}
