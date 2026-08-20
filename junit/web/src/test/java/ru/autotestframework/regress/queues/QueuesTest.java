package ru.autotestframework.regress.queues;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.*;
import ru.psb.testit.annotations.DisplayName;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;
import static ru.autotestframework.steps.elements.IDataSteps.removeSpaces;
import static ru.autotestframework.utils.Constants.REQUESTS;

@Tag("regress")
@Tag("queues")
@ClassName("Очереди")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class QueuesTest extends BaseTest {

    @BeforeEach
    @Order(1)
    public void login() {
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi();
        }
    }

    @BeforeEach
    @Order(2)
    public void preconditions(TestInfo testInfo) {
        if (testInfo.getTags().contains("queues_1290225")) {
            goToEmployeeCard();
            cardEmployeePage
                    .clickOnElement("Кнопка Категория рассмотрения - Редактировать")
                    .selectValueFromDropDownList("Выпадающий список Категория рассмотрения", "Автотесты_очередь")
                    .clickOnElement("Кнопка Категория рассмотрения - Сохранить")
                    .closeCurrentTab();
        }
    }

    @AfterEach
    public void cleanQueueClaims(TestInfo testInfo) {
        String testName = testInfo.getDisplayName();
        if (testName.contains("1290241")) {
            goToEmployeeCard();
            List<String> block = cardEmployeePage.getListCheckBox("Блок Процессная функция");
            if (!block.contains("Андеррайтинг")) {
                cardEmployeePage.clickOnElement("Процессная функция - Редактировать")
                        .selectValueFromDropDownList("Выпадающий список Название функции (модальное окно Назначение ПФ)", "Андеррайтинг")
                        .clickOnElement("Кнопка Добавить (модальное окно Назначение ПФ)")
                        .clickOnElement("Кнопка Сохранить (модальное окно Назначение ПФ)");
                assertIsTrue(cardEmployeePage.getListCheckBox("Блок Процессная функция").contains("Андеррайтинг"),
                        "Блок Процессная функция содержит ПФ Андеррайтинг");
            }

        } else if (testName.contains("1290225")) {
            goToEmployeeCard();
            cardEmployeePage
                    .clickOnElement("Кнопка Категория рассмотрения - Редактировать")
                    .selectValueFromDropDownList("Выпадающий список Категория рассмотрения", "Максимум_рассмотрение")
                    .clickOnElement("Кнопка Категория рассмотрения - Сохранить");
        }
        cardEmployeePage.switchToFirstTab();
        clearingQueueClaims.requestExpireAfterTestScenario();
    }


    @Step
    @Title("Переход в карточку сотрудника")
    private void goToEmployeeCard() {
        personalAccountPage
                .openMenuLinks("Сотрудники > Список сотрудников")
                .goTo(listEmployeesPage)
                .clickOnElement("Кнопка фильтра ФИО")
                .fillInput("Поле ФИО сотрудника (модальное окно Поиск сотрудника)", "Автоматическое Тестирование1")
                .clickOnElement("Чек-бокс Сотрудник (модальное окно Поиск сотрудника)")
                .clickOnElement("Кнпока Добавить (модальное окно Поиск сотрудника)")
                .clickOnElement("Кнопка Найти")
                .clickOnElement("ФИО сотрудника(первая строка)")
                .switchToNewTab()
                .goTo(cardEmployeePage);
    }

    @Test
    @Tag("smoke")
    @Tag("queues_1296894")
    @DisplayName("1296894 - Очередь. Заполнение полей на вкладке")
    @WorkItemIds({"1296894"})
    public void queues_1296894(TestInfo testInfo) {
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1290938.json", 1, testInfo).get(0);
        actionsClaimSteps.executeQuery(REQUESTS, "SELECT rrai.date_created FROM requests.rqs_request_additional_info rrai " +
                "FULL JOIN requests.rqs_request rr ON rr.id = rrai.request_id " +
                "WHERE rr.is_current_version = true " +
                "AND claim_id = '" + claim + "'");
        String dateTime = LocalDateTime.parse(
                        actionsClaimSteps.getVariables("date_created").replaceAll("\\.\\d+", ""),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
        loginPage
                .checkModal()
                .openMenuLinks("Очереди")
                .goTo(queuesPage).waitBusyCondition()
                .searchClaimOnPage(claim);
        Map<String, String> expectedValues = Map.of(
                "Номер заявки", claim,
                "Время попадания на РП", dateTime,
                "ФИО заемщика", "Шерлоков (Иванов) Шерлок Шерлокович",
                "Сумма кредита", "200 000 000",
                "Вид кредита", "Единый кредитный лимит",
                "Владелец блокировки", "",
                "Тип заявки", "Типовая",
                "Статус заявки", "Ожидает",
                "Этап обработки", "Андеррайтинг",
                "Стратегия", "");
        validateExpectedValues(expectedValues);
    }

    @Test
    @Tag("smoke")
    @Tag("queues_1290225")
    @DisplayName("1290225 - Очередь. Признаки взятия заявки в работу")
    @WorkItemIds({"1290225"})
    public void queues_1290225(TestInfo testInfo) {
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1290938.json", 1, testInfo).get(0);
        personalAccountPage
                .openMenuLinks("Личный кабинет")
                .clickOnElement("Раздел Андеррайтинг")
                .activateToggleNewClaim();
        String actualClaim = searchPage.getTextFromTable("Таблица результаты поиска", 1, "Номер заявки");
        assertIsTrue(actualClaim.equals(claim),
                "Значение в столбце Номер заявки должно быть равно " + claim + ". Фактическое значение: " + actualClaim);
        personalAccountPage
                .openMenuLinks("Очереди")
                .goTo(queuesPage).waitBusyCondition()
                .searchClaimOnPage(claim);
        Map<String, String> expectedValues = Map.of(
                "Номер заявки", claim,
                "Статус заявки", "На рассмотрении",
                "Владелец блокировки", "Автоматическое Тестирование1");
        validateExpectedValues(expectedValues);
        assertIsTrue(queuesPage.getElementfromTable(queuesPage.queuesTable, 1, 2).$x(".//i[@nztype='unlock']").isDisplayed(),
                "Иконка Замок в таблице результаты поиска отображается");
    }

    @Test
    @Tag("smoke")
    @Tag("queues_1290241")
    @DisplayName("1290241 - Очередь. Неназначение заявки для пользователя с отсутствием ПФ - Андеррайтинг")
    @WorkItemIds({"1290241"})
    public void queues_1290241(TestInfo testInfo) {
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1290938.json", 1, testInfo).get(0);
        personalAccountPage
                .openMenuLinks("Сотрудники > Список сотрудников")
                .goTo(listEmployeesPage)
                .clickOnElement("Кнопка фильтра ФИО")
                .fillInput("Поле ФИО сотрудника (модальное окно Поиск сотрудника)", "Автоматическое Тестирование1")
                .clickOnElement("Чек-бокс Сотрудник (модальное окно Поиск сотрудника)")
                .clickOnElement("Кнпока Добавить (модальное окно Поиск сотрудника)")
                .clickOnElement("Кнопка Найти")
                .clickOnElement("ФИО сотрудника(первая строка)")
                .switchToNewTab()
                .goTo(cardEmployeePage);

        if (cardEmployeePage.getListCheckBox("Блок Процессная функция").contains("Андеррайтинг")) {
            cardEmployeePage.clickOnElement("Процессная функция - Редактировать")
                    .clickOnElement("Кнопка Удалить ПФ Андеррайтинг (модальное окно Назначение ПФ)")
                    .clickOnElement("Кнопка Сохранить (модальное окно Назначение ПФ)");
            assertIsTrue(!cardEmployeePage.getListCheckBox("Блок Процессная функция").contains("Андеррайтинг"),
                    "Блок Процессная функция не содержит ПФ Андеррайтинг");
        }
        cardEmployeePage.openMenuLinks("Очереди")
                .goTo(queuesPage).waitBusyCondition()
                .searchClaimOnPage(claim)
                .clickOnElement("Чек-бокс массового выбора")
                .clickOnElement("Кнопка Назначить заявку")
                .fillInput("Поле ввода ФИО сотрудника", "Автоматическое тестирование1")
                .clickOnElement("Кнопка Найти (окно поиск сотрудников)")
                .clickOnElement("Чек-бокс массового выбора (окно поиск сотрудников)")
                .selectValueFromDropDownList("Выпадающий список Причина назначения (окно поиск сотрудников)", "Отдаленные регионы")
                .clickOnElement("Кнопка Назначить").waitBusyCondition();
        assertIsTrue(queuesPage.getTextByElementTitle("Результаты назначения")
                        .equals("Не назначено заявок: 1\n" +
                                "Заявка " + claim + ": Не назначена по причине \"Процессная функция сотрудника не позволяет рассмотреть заявку\""),
                "Модальное окно заполнено значением \"Заявка" + claim + ": Не назначена по причине \\\"Процессная функция сотрудника не позволяет рассмотреть заявку\\\"");
        queuesPage.clickOnElement("Кнопка Иконка закрыть")
                .switchToFirstTab();
    }

    @Test
    @Tag("queues_1290245")
    @DisplayName("1290245 - Очередь. Распределение заявок между сотрудниками до равного количества")
    @WorkItemIds({"1290245"})
    public void queues_1290245(TestInfo testInfo) {
        List<String> claims = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1290938.json", 6, testInfo);

        personalAccountPage
                .openMenuLinks("Очереди")
                .goTo(queuesPage).waitBusyCondition()
                .selectClaimOnTable(claims.get(0))
                .selectClaimOnTable(claims.get(1))
                .assignRequest(Collections.singletonList("Автоматическое Тестирование1"));
        assertIsTrue(queuesPage.getTextByElementTitle("Результаты назначения")
                        .equals("Назначено заявок: 2\n" +
                                "Пользователю: Автоматическое Тестирование1"),
                "Модальное окно заполнено значением \"Назначено заявок: 2 Пользователю: Автоматическое Тестирование1\"");
        queuesPage.clickOnElement("Кнопка Иконка закрыть")
                .selectClaimOnTable(claims.get(2))
                .assignRequest(Collections.singletonList("Автоматическое Тестирование2"));
        assertIsTrue(queuesPage.getTextByElementTitle("Результаты назначения")
                        .equals("Назначено заявок: 1\n" +
                                "Пользователю: Автоматическое Тестирование2"),
                "Модальное окно заполнено значением \"Назначено заявок: 1 Пользователю: Автоматическое Тестирование2\"");
        queuesPage.clickOnElement("Кнопка Иконка закрыть")
                .selectClaimOnTable(claims.get(3))
                .selectClaimOnTable(claims.get(4))
                .selectClaimOnTable(claims.get(5))
                .assignRequest(List.of("Автоматическое Тестирование1", "Автоматическое Тестирование2"),
                        "Кнопка Распределяется до равного количества заявок в личном кабинете");
        String actualText = queuesPage.getTextByElementTitle("Результаты назначения");
        assertIsTrue(queuesPage.getTextByElementTitle("Результаты назначения")
                        .equals("Назначено заявок: 3\n" +
                                "Пользователю: Автоматическое Тестирование1 назначено - 1 заявка\n" +
                                "Пользователю: Автоматическое Тестирование2 назначено - 2 заявки"),
                "Модальное окно заполнено значением \"Назначено заявок: 3 \n" +
                        "Пользователю: Автоматическое Тестирование1 назначено - 1 заявка" +
                        "Пользователю: Автоматическое Тестирование2 назначено - 2 заявки\"");
        queuesPage.clickOnElement("Кнопка Иконка закрыть")
                .switchToFirstTab();
    }

    @Test
    @Tag("queues_1290239")
    @DisplayName("1290239 - Очередь. Изменение статуса заявки при ручном назначении на этапе Утверждения")
    @WorkItemIds({"1290239"})
    public void queues_1290239(TestInfo testInfo) {
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1290938.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        transferApplicationStatusAwaitingApproval(claim);
        personalAccountPage
                .openMenuLinks("Очереди")
                .goTo(queuesPage).waitBusyCondition()
                .searchClaimOnPage(claim)
                .selectClaimOnTable(claim)
                .assignRequest(Collections.singletonList("Автоматическое Тестирование1"));
        assertIsTrue(queuesPage.getTextByElementTitle("Результаты назначения")
                        .equals("Назначено заявок: 1\n" +
                                "Пользователю: Автоматическое Тестирование1"),
                "Модальное окно заполнено значением \"Назначено заявок: 1 Пользователю: Автоматическое Тестирование1\"");
        queuesPage.clickOnElement("Кнопка Иконка закрыть")
                .searchClaimOnPage(claim);
        Map<String, String> expectedValues = Map.of(
                "Номер заявки", claim,
                "Владелец блокировки", "Автоматическое Тестирование1",
                "Статус заявки", "На утверждении");
        validateExpectedValues(expectedValues);
    }

    @Test
    @Tag("queues_2932728")
    @DisplayName("2932728 - Проверка добавления и отображения новых полей \"Верификатор\" и \"Стратегия\"")
    @WorkItemIds({"2932728"})
    public void queues_2932728() {
        personalAccountPage
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .waitBusyCondition()
                .resetFilters()
                .clickOnElement("Кнопка Настройка списка")
                .goTo(filterListSettingsPage)
                .resetFilters()
                .goTo(queuesPage)
                .clickOnElement("Кнопка Настройка списка")
                .goTo(filterListSettingsPage)
                .checkElementsContainsOnFields("Список не активных фильтров", List.of("Верификатор"))
                .checkElementsContainsOnFields("Список активных фильтров", List.of("Стратегия"))
                .dragColumns("из правой колонки в левую",
                        List.of(
                                "Программа кредитования",
                                "Дата рождения заемщика",
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
                                "Утверждающий",
                                "ГО",
                                "Полномочия",
                                "Верификатор"))
                .checkElementsContainsOnFields("Список активных фильтров", List.of("Верификатор"))
                .clickOnElement("Кнопка Закрыть окно фильтров")
                .goTo(queuesPage)
                .checkContainsTableHeaders("Таблица Очереди", List.of("Верификатор"))
                .clickOnElement("Кнопка Настройка списка")
                .goTo(filterListSettingsPage)
                .resetFilters();
    }

    @Test
    @Tag("queues_1291403")
    @DisplayName("1291403 - Очередь. Неназначение заявки для пользователя с отсутствием ПФ - Утверждение")
    @WorkItemIds({"1291403"})
    public void queues_1291403(TestInfo testInfo) {
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1290938.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        transferApplicationStatusAwaitingApproval(claim);
        loginPage.openMenuLinks("Сотрудники > Список сотрудников")
                .goTo(listEmployeesPage)
                .waitBusyCondition()
                .clickOnElement("Кнопка фильтра ФИО")
                .fillInput("Поле ФИО сотрудника (модальное окно Поиск сотрудника)", "Автоматическое Тестирование1")
                .clickOnElement("Чек-бокс Сотрудник (модальное окно Поиск сотрудника)")
                .clickOnElement("Кнпока Добавить (модальное окно Поиск сотрудника)")
                .clickOnElement("Кнопка Найти")
                .waitBusyCondition()
                .clickOnCellFromTable("Таблица Список сотрудников", 1, 2)
                .switchToNewTab()
                .goTo(cardEmployeePage)
                .waitBusyCondition()
                .clickOnElement("Процессная функция - Редактировать")
                .assertElementByTitleVisibility("Модальное окно Назначение процессной функции", "отображается");
        if (cardEmployeePage.getTextByElementTitle("Блок Функции (Назначение процессной функции)").contains("Утверждение")) {
            cardEmployeePage.deleteFunctionFromList("Утверждение");
        }
        cardEmployeePage.clickOnElement("Кнопка Сохранить (Назначение процессной функции)")
                .closeCurrentTab()
                .waitBusyCondition()
                .openMenuLinks("Очереди")
                .goTo(queuesPage).waitBusyCondition()
                .selectClaimOnTable(claim)
                .assignRequest(Collections.singletonList("Автоматическое Тестирование1"));
        assertIsTrue(queuesPage.getTextByElementTitle("Результаты назначения")
                        .equals("Не назначено заявок: 1\n" +
                                "Заявка " + claim + ": Не назначена по причине \"Процессная функция сотрудника не позволяет рассмотреть заявку\""),
                "Модальное окно заполнено значением \"Не назначено заявок: 1 Заявка " + claim + ": Не назначена по причине \"Процессная функция сотрудника не позволяет рассмотреть заявку\"");
        queuesPage.clickOnElement("Кнопка Иконка закрыть");
        loginPage.openMenuLinks("Сотрудники > Список сотрудников")
                .goTo(listEmployeesPage)
                .waitBusyCondition()
                .clickOnElement("Кнопка фильтра ФИО")
                .fillInput("Поле ФИО сотрудника (модальное окно Поиск сотрудника)", "Автоматическое Тестирование1")
                .clickOnElement("Чек-бокс Сотрудник (модальное окно Поиск сотрудника)")
                .clickOnElement("Кнпока Добавить (модальное окно Поиск сотрудника)")
                .clickOnElement("Кнопка Найти")
                .waitBusyCondition()
                .clickOnCellFromTable("Таблица Список сотрудников", 1, 2)
                .switchToNewTab()
                .goTo(cardEmployeePage)
                .waitBusyCondition()
                .clickOnElement("Процессная функция - Редактировать")
                .assertElementByTitleVisibility("Модальное окно Назначение процессной функции", "отображается");
        if (!cardEmployeePage.getTextByElementTitle("Блок Функции (Назначение процессной функции)").contains("Утверждение")) {
            cardEmployeePage.selectValueFromDropDownList("Выпадающий список Название функции (Назначение процессной функции)", "Утверждение")
                    .clickOnElement("Кнопка Добавить (Назначение процессной функции)");
        }
        cardEmployeePage.clickOnElement("Кнопка Сохранить (Назначение процессной функции)");
    }

    @Test
    @Tag("queues_4132764")
    @Tag("working_with_application")
    @DisplayName("4132764 - Очередь. Отображение значений в полях \"Верификатор\" и \"Стратегия\"")
    @WorkItemIds({"4132764"})
    public void queues_4132764(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "Code", "stub1");
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_4132756.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage
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
        actionsClaimSteps.repeatSendSclRequestToStand("9", "data/json/claim_template_4132755.json");
        personalAccountPage
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .waitBusyCondition()
                .checkModal()
                .clickOnElement("Кнопка Настройка списка")
                .goTo(filterListSettingsPage)
                .checkElementsContainsOnFields("Список не активных фильтров", List.of("Верификатор"))
                .checkElementsContainsOnFields("Список активных фильтров", List.of("Стратегия"))
                .dragColumns("из правой колонки в левую",
                        List.of(
                                "Программа кредитования",
                                "Дата рождения заемщика",
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
                                "Утверждающий",
                                "ГО",
                                "Полномочия",
                                "Верификатор"))
                .checkElementsContainsOnFields("Список активных фильтров", List.of("Верификатор"))
                .clickOnElement("Кнопка Закрыть окно фильтров")
                .goTo(queuesPage)
                .searchClaimOnPage(claim);
        Map<String, String> expectedValues = Map.of(
                "Верификатор", "Автоматическое Тестирование1",
                "Стратегия", "ФССП");
        validateExpectedValues(expectedValues);
        queuesPage.clickOnElement("Кнопка Настройка списка")
                .goTo(filterListSettingsPage)
                .resetFilters();
    }

    @Test
    @Tag("queues_4126365")
    @DisplayName("4126365 - Очередь. Проверка сортировки по дополнительным полям")
    @WorkItemIds({"4126365"})
    public void queues_4126365() {
        personalAccountPage
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .resetFilters()
                .selectValueFromDropDownList("Выпадающий список Этап обработки", "Утверждение")
                .clickOnElement("Кнопка Найти")
                .waitBusyCondition()
                .clickOnElement("Кнопка Настройка списка")
                .goTo(filterListSettingsPage)
                .dragColumns("из правой колонки в левую",
                        List.of("Форма подтверждения дохода",
                                "Дата создания"))
                .clickOnElement("Кнопка Закрыть окно фильтров")
                .goTo(queuesPage);

        List<String> lendingProgramExpected = queuesPage.getSortedList(queuesPage.getListValuesByColumnName("Таблица Очереди", "Форма подтверждения дохода"),
                "string", "возрастанию");
        queuesPage.clickOnElement("сортировка Столбец Форма подтверждения дохода");
        List<String> lendingProgramActual = queuesPage.getListValuesByColumnName("Таблица Очереди", "Форма подтверждения дохода");
        assertIsTrue(lendingProgramExpected.equals(lendingProgramActual), "Значения в столбце Программа кредитования таблицы являются отсортированными по возрастанию");

        lendingProgramExpected = queuesPage.getSortedList(lendingProgramActual, "string", "убыванию");
        queuesPage.clickOnElement("сортировка Столбец Форма подтверждения дохода");
        lendingProgramActual = queuesPage.getListValuesByColumnName("Таблица Очереди", "Форма подтверждения дохода");
        assertIsTrue(lendingProgramExpected.equals(lendingProgramActual), "Значения в столбце Форма подтверждения дохода таблицы являются отсортированными по убыванию");

        List<String> dateCreationExpected = queuesPage.getSortedListWithNeighboringColumns("Таблица Очереди",
                "Дата создания", "Форма подтверждения дохода", "timestamp", "убыванию");
        queuesPage.clickOnElement("сортировка Столбец Дата создания")
                .waitBusyCondition()
                .clickOnElement("сортировка Столбец Дата создания");
        List<String> dateCreationActual = queuesPage.getListValuesByColumnName("Таблица Очереди", "Дата создания");

        assertIsTrue(personalAccountPage.assertIsSubsequence(dateCreationExpected, dateCreationActual), "Значения в столбце Дата таблицы очереди являются отсортированными по убыванию");

        List<String> timeRPexpected = queuesPage.getSortedList(queuesPage.getListValuesByColumnName("Таблица Очереди", "Время попадания на РП")
                , "timestamp", "убыванию");
        queuesPage.clickOnElement("Кнопка Сбросить сортировку");
        List<String> timeRPactual = queuesPage.getListValuesByColumnName("Таблица Очереди", "Время попадания на РП");
        assertIsTrue(timeRPexpected.equals(timeRPactual), "Значения в столбце Время попадания на РП таблицы являются отсортированными по убыванию");

        queuesPage.clickOnElement("Кнопка Настройка списка")
                .goTo(filterListSettingsPage)
                .resetFilters();
    }

    @Step
    @Title("Перевод заявки в статус Ожидает утверждения")
    private void transferApplicationStatusAwaitingApproval(String claim) {
        loginPage.openMenuLinks("Личный кабинет")
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Андеррайтинг")
                .goTo(personalAccountPage).doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(cardRequestPage)
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - На рассмотрении")
                .selectValueFromDropDownList("Выпадающий список Занятость подтверждена", "Звонок не назначался")
                .fillInput("Поле ввода Внутренний комментарий андеррайтера", "Коментарий АТ")
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .selectValueFromDropDownList("Выпадающий список Проведенные проверки", "Проверка критичных данных")
                .selectValueFromDropDownList("Выпадающий список Полномочия", "Собственные")
                .selectValueFromDropDownList("Выпадающий список Одобрить/Отклонить", "Одобрить")
                .selectValueFromDropDownList("Выпадающий список Тип одобрения/Причина отклонения", "Одобрено")
                .clickOnElement("Кнопка На утверждение")
                .switchToOneTab();
    }

    private void validateExpectedValues(Map<String, String> expectedValues) {
        for (Map.Entry<String, String> expected : expectedValues.entrySet()) {
            String actualValue = queuesPage.getTextFromTable("Таблица Очереди", 1, expected.getKey());
            assertIsTrue(actualValue.equals(expected.getValue()),
                    "Значение столбца " + expected.getKey() + " строки 1 должно быть равно " + expected.getValue() + " . Фактическое значение = " + actualValue);
        }
    }
}
