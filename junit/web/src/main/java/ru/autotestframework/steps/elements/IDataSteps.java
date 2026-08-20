package ru.autotestframework.steps.elements;

import ru.autotestframework.core.exception.ExecutionException;
import ru.autotestframework.ui_core.exceptions.ElementInteractionException;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

public interface IDataSteps {

    default List<String> convertFormatAndSort(List<String> listValues, String format, String condition) {
        switch (format) {
            case "timestamp":
                DateTimeFormatter fullFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
                DateTimeFormatter shortFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

                List<LocalDateTime> dateTimeList = listValues.stream()
                        .map(value -> parseDate(value, fullFormat, shortFormat))
                        .collect(Collectors.toList());

                // Определяем формат для возврата на основе первой строки
                DateTimeFormatter formatterToReturn = determineReturnFormat(listValues.get(0), fullFormat, shortFormat);

                List<LocalDateTime> sortedDateTimes = sortList(dateTimeList, condition);
                return sortedDateTimes.stream().map(dt -> dt.format(formatterToReturn)).collect(Collectors.toList());
            case "bigint":
                List<BigInteger> bigIntegerList = sortList(listValues.stream().map(obj -> obj.replace(" ", ""))
                        .map(BigInteger::new).collect(Collectors.toList()), condition);
                return bigIntegerList.stream().map(BigInteger::toString).collect(Collectors.toList());
            case "string":
                return sortList(new ArrayList<>(listValues), condition);
            default:
                throw new ExecutionException("Задан неверный формат значений списка");
        }
    }

    default List<String> sortByNeighboringColumns(List<String> listValues, List<String> neighboringColumnValues,
                                                  String format, String condition) {
        if (listValues.size() != neighboringColumnValues.size()) {
            throw new IllegalArgumentException("Списки должны быть одинаковой длины.");
        }

        // Создаем карту для хранения индексов значений соседнего столбца
        Map<String, List<Integer>> valueIndicesMap = new HashMap<>();
        for (int i = 0; i < neighboringColumnValues.size(); i++) {
            valueIndicesMap.computeIfAbsent(neighboringColumnValues.get(i), k -> new ArrayList<>()).add(i);
        }

        // Фильтруем значения на основе повторяющихся индексов
        List<String> filteredValues = valueIndicesMap.values().stream()
                .filter(indices -> indices.size() > 2) // Оставляем только повторяющиеся
                .flatMap(indices -> indices.stream().map(listValues::get)) // Получаем значения из listValues
                .collect(Collectors.toList());

        // Сортируем отфильтрованные значения по типу
        return convertFormatAndSort(filteredValues, format, condition);
    }

    static List<String> removeSpaces(List<String> inputList) {
        return inputList.stream()
                .map(s -> s.replaceAll("\\s+", "")) // Удаляем все пробелы
                .collect(Collectors.toList());
    }

    private <E extends Comparable<? super E>> List<E> sortList(List<E> list, String condition) {
        return list.stream().sorted(condition.equals("убыванию") ? Comparator.reverseOrder() : Comparator.naturalOrder())
                .collect(Collectors.toList());
    }

    private LocalDateTime parseDate(String value, DateTimeFormatter fullFormat, DateTimeFormatter shortFormat) {
        Optional<LocalDateTime> dateTime;

        try {
            dateTime = Optional.of(LocalDateTime.parse(value, fullFormat));
        } catch (DateTimeParseException ignored) {
            // Попробуем короткий формат, если полный не сработал
            try {
                dateTime = Optional.of(LocalDateTime.parse(value, shortFormat));
            } catch (DateTimeParseException e) {
                throw new ExecutionException("Некорректный формат даты: " + value);
            }
        }

        return dateTime.orElseThrow(() -> new ExecutionException("Некорректный формат даты и времени: " + value));
    }

    private DateTimeFormatter determineReturnFormat(String value, DateTimeFormatter fullFormat, DateTimeFormatter shortFormat) {
        // Определяем, какой формат использовать для возврата
        return value.contains(":") && value.length() > 16 ? fullFormat : shortFormat;
    }

    default boolean isSubsequence(List<String> subList, List<String> fullList) {
        if (fullList.isEmpty() || subList.size() > fullList.size()) {
            throw new IllegalArgumentException("полный список пуст или меньше по размеру, чем подсписок");
        }
        int subIndex = 0; // Индекс для подсписка
        for (String element : fullList) {
            // Проверяем, совпадает ли текущий элемент с элементом подсписка
            if (element.equals(subList.get(subIndex))) {
                subIndex++; // Переходим к следующему элементу подсписка
                if (subIndex == subList.size()) {
                    return true; // Все элементы подсписка найдены
                }
            }
        }
        return false;
    }
}
