package ru.autotestframework.steps.asserts;


import ru.psb.testit.annotations.Step;
import ru.psb.testit.annotations.Title;

import java.util.Arrays;

import static org.junit.Assert.*;

public class Asserts {

    @Step
    @Title("Проверяем значение {objectName} не должно быть Null")
    public static <T> void assertIsNotNull(T object, String objectName) {
        assertNotNull(formatMessage("Значение %s не должно быть Null", objectName), object);
    }

    @Step
    @Title("Проверяем значение {objectName} должно быть Null")
    public static <T> void assertIsNull(T object, String objectName) {
        assertNull(formatMessage("Значение %s должно быть Null", objectName), object);
    }

    @Step
    @Title("Проверяем значение {objectName} должно быть равно {expectedObject}")
    public static <T> void assertIsEquals(T expectedObject, T actualObject, String objectName) {
        assertEquals(formatMessage("Значение %s должно быть равно %s", objectName, String.valueOf(expectedObject)), expectedObject, actualObject);
    }

    @Step
    @Title("Проверяем, что строка {exploredString} содержит значение {containsString}")
    public static void assertContains(String exploredString, String containsString) {
        assertTrue(formatMessage("Строка %s должна содержать в себе %s", exploredString, containsString),
                exploredString != null && exploredString.contains(containsString));
    }

    @Step
    @Title("Проверяем корректность утверждения: '{message}'")
    public static void assertIsTrue(Boolean bool, String message) {
        assertTrue(message, bool);
    }

    private static String formatMessage(String message, String objectName, String... expectedObject) {
        return String.format(message, objectName, Arrays.toString(expectedObject));
    }
}
