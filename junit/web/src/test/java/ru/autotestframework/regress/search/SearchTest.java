package ru.autotestframework.regress.search;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;
import static ru.autotestframework.utils.Constants.REQUESTS;

@Tag("regress")
@Tag("search")
@ClassName("Поиск")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SearchTest extends BaseTest {

    @BeforeAll
    public void login() {
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi();
        }
    }

    @AfterEach
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("smoke")
    @Tag("queues_1300204")
    @DisplayName("1300204 - Поиск. Заполнение полей на вкладке")
    @WorkItemIds({"1300204"})
    public void search_1300204(TestInfo testInfo) {
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1299498.json", 1, testInfo).get(0);
        String lastName = actionsClaimSteps.getValueByJsonPathFromRequestBody("claimWithVersions[0].forms[0].formPrimary.lastName");
        String previousLastName = actionsClaimSteps.getValueByJsonPathFromRequestBody("claimWithVersions[0].forms[0].formPrimary.previousLastName");
        String firstName = actionsClaimSteps.getValueByJsonPathFromRequestBody("claimWithVersions[0].forms[0].formPrimary.firstName");
        String middleName = actionsClaimSteps.getValueByJsonPathFromRequestBody("claimWithVersions[0].forms[0].formPrimary.middleName");
        loginPage.checkModal()
                .openMenuLinks("Поиск")
                .goTo(searchPage).waitBusyCondition()
                .clickOnElement("Кнопка Настройка списка")
                .goTo(filterListSettingsPage)
                .resetFilters()
                .goTo(searchPage)
                .searchClaimOnPage(claim);
        Map<String, String> expectedValues = Map.of(
                "Номер заявки", claim,
                "ФИО заемщика", lastName + " " + "(" + previousLastName + ")" + " " + firstName + " " + middleName,
                "Сумма кредита", "200 000 000",
                "Вид кредита", "Единый кредитный лимит",
                "Владелец блокировки", "",
                "Тип заявки", "Типовая",
                "Статус заявки", "Ожидает",
                "Этап обработки", "Верификация",
                "Стратегия", "L0.Проверка документов");
        validateExpectedValues(expectedValues);
    }

    @Test
    @Tag("queues_4133004")
    @DisplayName("4133004 - Поиск. Заполнение полей на вкладке")
    @WorkItemIds({"4133004"})
    public void search_4133004(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "Code", "stub1");
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_4132756.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        loginPage
                .openMenuLinks("Личный кабинет")
                .goTo(personalAccountPage)
                .doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Отправить на доработку")
                .selectValueFromDropDownList("Выпадающий список Причина доработки", "Ошибка МРК при вводе контактных данных клиента (адресов, телефонов)")
                .fillInput("Поле ввода Внутренний комментарий", "коммент внут")
                .fillInput("Поле ввода Комментарий для МРК", "Коммент для мрк")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .switchToOneTab()
                .waitBusyCondition();
        actionsClaimSteps.checkStatusClaimFromDb(claim, 9);
        actionsClaimSteps.repeatSendSclRequestToStand("9","data/json/claim_template_4132755.json");
        personalAccountPage
                .openMenuLinks("Поиск")
                .goTo(searchPage)
                .waitBusyCondition()
                .clickOnElement("Кнопка Настройка списка")
                .goTo(filterListSettingsPage)
                .dragColumns("из правой колонки в левую",
                        List.of("Дата рождения заемщика",
                                "Номер клиента PSB Retail",
                                "Андеррайтер",
                                "Дата принятия решения",
                                "ИНН работодателя",
                                "КПП работодателя",
                                "Отправка на доработку",
                                "Дата возврата заявки",
                                "Форма подтверждения дохода",
                                "Отправивший на доработку/корректировку",
                                "Наименование филиала",
                                "Наименование опер. офиса",
                                "Наименование доп. офиса",
                                "Наименование работодателя",
                                "Изменивший",
                                "Дата создания",
                                "Признак «Госслужащий»",
                                "Управленческий статус заемщика",
                                "Дата версии",
                                "Макс. сумма кредита",
                                "Была доработка",
                                "Удостоверение личности военнослужащего",
                                "Дата изменения",
                                "Программа кредитования",
                                "Утверждающий",
                                "НИС",
                                "Верификатор"))
                .clickOnElement("Кнопка Закрыть окно фильтров")
                .goTo(searchPage)
                .searchClaimOnPage(claim);
        Map<String, String> expectedValues = Map.of(
                "Верификатор", "Автоматическое Тестирование1",
                "Стратегия", "ФССП");
        validateExpectedValues(expectedValues);
        searchPage.clickOnElement("Кнопка Настройка списка")
                .goTo(filterListSettingsPage)
                .resetFilters();

    }

    private void validateExpectedValues(Map<String, String> expectedValues) {
        for (Map.Entry<String, String> expected : expectedValues.entrySet()) {
            String actualValue = searchPage.getTextFromTable("Таблица результаты поиска", 1, expected.getKey());
            assertIsTrue(actualValue.equals(expected.getValue()),
                    "Значение столбца " + expected.getKey() + " строки 1 должно быть равно " + expected.getValue() + " . Фактическое значение = " + actualValue);
        }
    }
}
