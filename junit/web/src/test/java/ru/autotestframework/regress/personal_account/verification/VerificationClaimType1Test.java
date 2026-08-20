package ru.autotestframework.regress.personal_account.verification;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;
import static ru.autotestframework.utils.Constants.DF;

@Tag("regress")
@Tag("personal_account")
@Tag("verification")
@Tag("verification_claim_type_1")
@ClassName("Личный кабинет. Верификация. На каждый кейс отдельная заявка")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VerificationClaimType1Test extends BaseTest {

    private LocalDateTime nowTime;

    @BeforeEach
    public void setup() {
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi();
        }
        loginPage.openMenuLinks("Личный кабинет");
    }

    @AfterEach
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("fill_field_1650056")
    @Tag("smoke")
    @DisplayName("1650056 - Личный кабинет. Заполнение полей для Отложенной заявки")
    @WorkItemIds({"1650056"})
    public void fill_field_1650056(TestInfo testInfo) {
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1954447.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Отложить")
                .waitText(10, "Перевод заявки в отложенные")
                .selectValueFromDropDownList("Выпадающий список Причина (Перевод заявки в отложенные)", "Недозвон Заемщику")
                .fillInput("Поле ввода комментарий (Перевод заявки в отложенные)", "Перевод заявки в отложенные АТ")
                .fillInput("Поле ввода Время для звонка участнику", getTimeNow().plusMinutes(7).format(DF))
                .clickOnElement("Кнопка Отложить заявку")
                .switchToOneTab()
                .goTo(personalAccountPage).waitBusyCondition()
                .clickOnElement("Кнопка раскрыть таблицу Отложено")
                .clickOnElement("Кнопка Настройка списка(таблица Отложено)")
                .goTo(filterListSettingsPage)
                .dragColumns("из правой колонки в левую",
                        List.of("Перевод в отложенные",
                                "Возврат из отложенных"))
                .clickOnElement("Кнопка Закрыть окно фильтров");
        assertIsTrue(personalAccountPage.getTextFromTable("Таблица Отложено", 1, "Перевод в отложенные").
                        equals(nowTime.format(DF)),
                "Значение в столбце Перевод в отложенные должно быть равно " + nowTime.format(DF));
        assertIsTrue(personalAccountPage.getTextFromTable("Таблица Отложено", 1, "Возврат из отложенных").
                        equals(nowTime.plusMinutes(2).format(DF)),
                "Значение в столбце Возврат из отложенных должно быть равно " + nowTime.plusMinutes(2).format(DF));
    }

    @Test
    @Tag("fill_field_1650090")
    @DisplayName("1650090 - Личный кабинет. Заполнение полей для заявки на Доработке")
    @WorkItemIds({"1650090"})
    public void fill_field_1650090(TestInfo testInfo) {
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1954447.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Отправить на доработку")
                .selectValueFromDropDownList("Выпадающий список Причина доработки верификация", "Ошибка МРК при вводе контактных данных клиента (адресов, телефонов)")
                .fillInput("Поле ввода Внутренний комментарий", "Комментарий")
                .fillInput("Поле ввода Комментарий для МРК", "Комментарий для МРК")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку");
        String timeSend = getTimeNow().format(DF);
        String timeReturn = actionsClaimSteps.repeatSendSclRequestToStand("9").format(DF);
        loginPage.switchToOneTab()
                .goTo(personalAccountPage).waitBusyCondition()
                .checkNotifications()
                .clickOnElement("Кнопка Настройка списка(таблица В работе)")
                .goTo(filterListSettingsPage)
                .dragColumns("из правой колонки в левую",
                        List.of("Отправивший на доработку/корректировку",
                                "Была доработка",
                                "Дата возврата заявки",
                                "Отправка на доработку",
                                "Изменивший"))
                .clickOnElement("Кнопка Закрыть окно фильтров");

        assertIsTrue(personalAccountPage.getTextFromTable("Таблица в работе", 1, "Отправивший на доработку/корректировку").
                        equals("Автоматическое Тестирование1"),
                "Значение в столбце Отправивший на доработку/корректировку должно быть равно Автоматическое Тестирование1" +
                        ". Фактическое значение: " + personalAccountPage.getTextFromTable("Таблица в работе", 1, "Отправивший на доработку/корректировку"));
        assertIsTrue(personalAccountPage.getTextFromTable("Таблица в работе", 1, "Была доработка").
                        equals("Да"),
                "Значение в столбце Была доработка должно быть равно Да");
        assertIsTrue(personalAccountPage.getTextFromTable("Таблица в работе", 1, "Отправка на доработку").
                        equals(timeSend),
                "Значение в столбце Отправка на доработку должно быть равно " + timeSend + ". Фактическое значение: " + personalAccountPage.getTextFromTable("Таблица в работе", 1, "Отправка на доработку"));
        assertIsTrue(personalAccountPage.getTextFromTable("Таблица в работе", 1, "Дата возврата заявки").
                        equals(timeSend),
                "Значение в столбце Дата возврата заявки должно быть равно " + timeReturn +
                        ". Фактическое значение: " + personalAccountPage.getTextFromTable("Таблица в работе", 1, "Дата возврата заявки"));
        assertIsTrue(personalAccountPage.getTextFromTable("Таблица в работе", 1, "Изменивший").
                        equals("Автоматическое Тестирование1"),
                "Значение в столбце Изменивший на доработку/корректировку должно быть равно Автоматическое Тестирование1");
        personalAccountPage.clickOnElement("Кнопка Настройка списка(таблица В работе)")
                .goTo(filterListSettingsPage)
                .resetFilters();
    }

    private LocalDateTime getTimeNow() {
        nowTime = LocalDateTime.now();
        return nowTime;
    }

}
