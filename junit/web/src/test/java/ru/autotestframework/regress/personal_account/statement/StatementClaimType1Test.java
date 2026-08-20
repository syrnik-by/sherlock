package ru.autotestframework.regress.personal_account.statement;

import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;
import static ru.autotestframework.utils.Constants.DF;


@Tag("regress")
@Tag("personal_account")
@Tag("statement")
@Tag("statement_claim_type_1")
@ClassName("Личный кабинет. Утверждение")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StatementClaimType1Test extends BaseTest {

    private static List<String> claim = new ArrayList<>();
    private LocalDateTime nowTime;
    private LocalDateTime nowTimeAdd20;

    @BeforeAll
    public void setUp(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_type1_statement.json", 1, testInfo);
    }

    @Test
    @Tag("fill_field_1651125")
    @DisplayName("1651125 - Личный кабинет. Утверждение. Заполнение полей для Отложенной заявки")
    @WorkItemIds({"1651125"})
    public void test_1651125() {
        //сохранить Количество минут для автоматического возврата отложенной заявки
        int time = actionsClaimSteps.getTimeAutomaticRefundApplication();
        actionsClaimSteps.appointResponsiblePerson(claim.get(0));
        loginPage.openAuthorizationPage()
                .loginViaUi()
                .openMenuLinks("Личный кабинет")
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Андеррайтинг")
                .doubleClickByText(claim.get(0))
                .switchToNewTab()
                .goTo(cardRequestPage)
                .fillInput("Поле ввода Внутренний комментарий андеррайтера", "Коментарий АТ")
                .selectValueFromDropDownList("Выпадающий список Занятость подтверждена", "Звонок не назначался")
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .selectValueFromDropDownList("Выпадающий список Проведенные проверки", "Проверка критичных данных")
                .selectValueFromDropDownList("Выпадающий список Полномочия", "Собственные")
                .selectValueFromDropDownList("Выпадающий список Одобрить/Отклонить", "Одобрить")
                .selectValueFromDropDownList("Выпадающий список Тип одобрения/Причина отклонения", "Одобрено")
                .clickOnElement("Кнопка На утверждение")
                .switchToOneTab()
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Утверждение")
                .doubleClickByText(claim.get(0))
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Взять в работу")
                .clickOnElement("Кнопка Отложить")
                .waitText(10, "Перевод заявки в отложенные")
                .selectValueFromDropDownList("Выпадающий список Причина (Перевод заявки в отложенные)", "Недозвон Заемщику")
                .fillInput("Поле ввода комментарий (Перевод заявки в отложенные)", "Перевод заявки в отложенные АТ")
                .fillInput("Поле ввода Время для звонка участнику", saveTodayDateTimeAdd20().format(DF))
                .clickOnElement("Кнопка Отложить заявку")
                .switchToOneTab()
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Утверждение")
                .clickOnElement("Кнопка раскрыть таблицу Отложено")
                .clickOnElement("Кнопка Настройка списка(таблица Отложено)")
                .goTo(filterListSettingsPage)
                .dragColumns("из правой колонки в левую",
                        List.of("Возврат из отложенных", "Перевод в отложенные"))
                .clickOnElement("Кнопка Закрыть окно фильтров");

        String recoveryTime = personalAccountPage.getTextFromTable("Таблица Отложено", 1, "Возврат из отложенных");
        LocalDateTime recoveryTimeDate = LocalDateTime.parse(recoveryTime, DF);

        String transferDeferred = personalAccountPage.getTextFromTable("Таблица Отложено", 1, "Перевод в отложенные");
        LocalDateTime transferDeferredDate = LocalDateTime.parse(transferDeferred, DF);

        assertIsTrue(Duration.between(transferDeferredDate, nowTime).getSeconds() <= 60,
                "Разница во времени между " + transferDeferredDate + " и " + nowTime + " должна быть меньше 60 секунд ");

        assertIsTrue(recoveryTimeDate.isBefore(nowTimeAdd20), "Время возврата заявки должно быть меньше вводимого");

        assertIsTrue(Duration.between(recoveryTimeDate, nowTimeAdd20).getSeconds() >= time * 60L,
                "Разница во времени между " + recoveryTimeDate + " и " + nowTimeAdd20 + " должна быть больше " + time + " минут");

        assertIsTrue(Duration.between(recoveryTimeDate, nowTimeAdd20).getSeconds() <= (time + 1) * 60L,
                "Разница во времени между " + recoveryTimeDate + " и " + nowTimeAdd20 + " должна быть меньше " + (time + 1) + " минут");

        personalAccountPage.clickOnElement("Кнопка Настройка списка(таблица Отложено)")
                .goTo(filterListSettingsPage)
                .resetFilters();
    }

    @Step
    @Title("Сохранение текущей даты-время + 20мин")
    public LocalDateTime saveTodayDateTimeAdd20() {
        nowTime = LocalDateTime.now();
        nowTimeAdd20 = nowTime.plusMinutes(20); // Добавляем указанное количество минут
        return nowTimeAdd20;
    }

}