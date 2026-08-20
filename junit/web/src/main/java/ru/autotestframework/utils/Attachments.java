package ru.autotestframework.utils;

import com.codeborne.selenide.WebDriverRunner;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import ru.autotestframework.BaseTest;
import ru.autotestframework.pages.BasePage;
import ru.psb.testit.services.Adapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class Attachments extends BasePage<Attachments> implements InvocationInterceptor, TestExecutionExceptionHandler {

    private static Map<String, Boolean> screenshotTaken = new ConcurrentHashMap<>();

    @Override
    public void interceptAfterEachMethod(Invocation<Void> invocation, ReflectiveInvocationContext<Method> invocationContext, ExtensionContext extensionContext) throws Throwable {
        if (BaseTest.class.isAssignableFrom(invocationContext.getExecutable().getDeclaringClass())) {
            extensionContext.getExecutionException().ifPresent(exception -> doScreen(extensionContext.getTestMethod().get().getName()));
        }
        invocation.proceed();
    }

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        // Создание скриншота при возникновении исключения
        log.info("Исключение в тесте {}: {}", context.getRequiredTestMethod().getName(), throwable.getMessage());
        String testMethodName = context.getRequiredTestMethod().getName();
        doScreen(testMethodName);
        switchToFirstTab();
        // Пробрасываем исключение дальше, чтобы тест помечался как упавший
        throw throwable;
    }

    private void doScreen(String testMethod) {
        if (!screenshotTaken.getOrDefault(testMethod, false)) {
            if (WebDriverRunner.hasWebDriverStarted()) { // Проверка на наличие WebDriver
                String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
                File attach = new File("build/"
                        .concat(testMethod)
                        .concat("_")
                        .concat(currentDate)
                        .concat("_")
                        .concat(UUID.randomUUID().toString())
                        .concat(".png"));
                attach.deleteOnExit();
                try (OutputStream out = new FileOutputStream(attach)) {
                    out.write(((TakesScreenshot) WebDriverRunner.getWebDriver())
                            .getScreenshotAs(OutputType.BYTES));
                    log.info("Скрин -> {}", attach);
                    Adapter.addAttachments(attach.getAbsolutePath());
                    screenshotTaken.put(testMethod, true);
                } catch (Exception e) {
                    log.error("Ошибка добавления скриншота", e);
                }
            } else {
                log.warn("WebDriver не инициализирован. Скриншот не будет сделан.");
            }
        } else {
            log.warn("Скриншот уже был сделан для теста: {}", testMethod);
        }
    }
}