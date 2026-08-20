package ru.autotestframework.utils.annotations;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Optional;

public class DisabledIfWeekdayExtension implements ExecutionCondition {

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        Optional<DisabledIfWeekday> annotation = context.getElement()
                .flatMap(el -> Optional.ofNullable(el.getAnnotation(DisabledIfWeekday.class)));

        if (annotation.isEmpty()) {
            return ConditionEvaluationResult.enabled("No @DisabledIfWeekday annotation found");
        }

        DayOfWeek[] disabledDays = annotation.get().value();
        DayOfWeek today = LocalDate.now().getDayOfWeek();

        for (DayOfWeek day : disabledDays) {
            if (day == today) {
                return ConditionEvaluationResult.disabled("Test disabled on " + today);
            }
        }

        return ConditionEvaluationResult.enabled("Test enabled on " + today);
    }
}