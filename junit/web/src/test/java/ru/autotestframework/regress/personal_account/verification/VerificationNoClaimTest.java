package ru.autotestframework.regress.personal_account.verification;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.autotestframework.BaseTest;
import ru.autotestframework.pages.FilterListSettingsPage;
import ru.autotestframework.pages.PersonalAccountPage;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.ExternalId;
import ru.psb.testit.annotations.WorkItemIds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag("regress")
@Tag("personal_account")
@Tag("verification")
@Tag("verification_no_claim")
@ClassName("Личный кабинет. Верификация")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VerificationNoClaimTest extends BaseTest {

    @BeforeEach
    public void setUp() {
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi();
        }
        loginPage.checkModal()
                .openMenuLinks("Личный кабинет");
        if (!personalAccountPage.postPonedTable.isVisible()) {
            personalAccountPage.clickOnElement("Кнопка раскрыть таблицу Отложено");
        }
    }

    @BeforeEach
    public void resetFiltersIfNeeded() {
        if (!filterListSettingsPage.checkElementByTitleVisibility("Список активных фильтров", false)) {
            personalAccountPage.clickOnElement("Кнопка Настройка списка(таблица В работе)")
                    .goTo(filterListSettingsPage).resetFilters()
                    .goTo(personalAccountPage).clickOnElement("Кнопка Настройка списка(таблица Отложено)")
                    .goTo(filterListSettingsPage).resetFilters();
        } else {
            filterListSettingsPage.resetFilters()
                    .goTo(personalAccountPage).clickOnElement("Кнопка Настройка списка(таблица Отложено)")
                    .goTo(filterListSettingsPage).resetFilters();
        }
    }

    @Test
    @Tag("drag_columns")
    @DisplayName("1644947 - Личный кабинет. Перемещение столбцов")
    @WorkItemIds({"1644947"})
    public void dragColumns_1644947() {
        List<String> expectedFilters;
        personalAccountPage.clickOnElement("Кнопка Настройка списка(таблица В работе)")
                .goTo(filterListSettingsPage)
                .dragColumns("из правой колонки в левую",
                        List.of("Владелец блокировки",
                                "Дата рождения заемщика",
                                "Андеррайтер",
                                "Наименование филиала"))
                .checkElementsOnFields("Список активных фильтров",
                        List.of("Номер заявки",
                                "Стратегия",
                                "Время попадания на РП",
                                "ФИО заемщика",
                                "Сумма кредита",
                                "Вид кредита",
                                "Тип заявки",
                                "Программа кредитования",
                                "Статус заявки",
                                "Предыдущий статус",
                                "Владелец блокировки",
                                "Дата рождения заемщика",
                                "Андеррайтер",
                                "Наименование филиала"));
        expectedFilters = filterListSettingsPage.getFiltersname("Список активных фильтров");
        filterListSettingsPage.clickOnElement("Кнопка Закрыть окно фильтров")
                .goTo(personalAccountPage)
                .checkTableHeaders("Таблица в работе", expectedFilters)
                .clickOnElement("Кнопка Настройка списка(таблица В работе)")
                .goTo(filterListSettingsPage)
                .clickOnElement("Кнопка Сбросить")
                .checkElementsOnFields("Список активных фильтров",
                        List.of("Номер заявки",
                                "Стратегия",
                                "Время попадания на РП",
                                "ФИО заемщика",
                                "Сумма кредита",
                                "Вид кредита",
                                "Тип заявки",
                                "Программа кредитования",
                                "Статус заявки",
                                "Предыдущий статус"));
        expectedFilters = filterListSettingsPage.getFiltersname("Список активных фильтров");
        filterListSettingsPage.clickOnElement("Кнопка Закрыть окно фильтров")
                .goTo(personalAccountPage)
                .checkTableHeaders("Таблица в работе", expectedFilters);
    }

    @Test
    @Tag("horizontal_scroll")
    @DisplayName("1644946 - Личный кабинет. Горизонтальный скроллинг")
    @WorkItemIds({"1644946"})
    public void horizontalScroll_1644946() {
        List<String> expectedFilters;
        personalAccountPage.checkScroll("горизонтальный", "Таблица в работе", false)
                .clickOnElement("Кнопка Настройка списка(таблица В работе)")
                .goTo(filterListSettingsPage)
                .dragColumns("из правой колонки в левую",
                        List.of("Владелец блокировки",
                                "Дата рождения заемщика",
                                "Андеррайтер",
                                "Наименование филиала"))
                .checkElementsOnFields("Список активных фильтров",
                        List.of("Номер заявки",
                                "Стратегия",
                                "Время попадания на РП",
                                "ФИО заемщика",
                                "Сумма кредита",
                                "Вид кредита",
                                "Тип заявки",
                                "Программа кредитования",
                                "Статус заявки",
                                "Предыдущий статус",
                                "Владелец блокировки",
                                "Дата рождения заемщика",
                                "Андеррайтер",
                                "Наименование филиала"));
        expectedFilters = filterListSettingsPage.getFiltersname("Список активных фильтров");
        filterListSettingsPage.clickOnElement("Кнопка Закрыть окно фильтров")
                .goTo(personalAccountPage)
                .checkTableHeaders("Таблица в работе", expectedFilters)
                .checkScroll("горизонтальный", "Таблица в работе", true);
    }

    @Test
    @Tag("list_of_remembered_columns")
    @DisplayName("1644950 - Личный кабинет. Перечень отображаемых столбцов для блока «В работе» " +
            "запоминается отдельно от перечня отображаемых столбцов для блока «Отложено»")
    @WorkItemIds({"1644950"})
    public void listOfRememberedColumns_1644950() {
        List<String> expectedFilters;
        List<String> expectedFiltersPostponed;
        personalAccountPage.clickOnElement("Кнопка Настройка списка(таблица В работе)")
                .goTo(filterListSettingsPage)
                .dragColumns("из правой колонки в левую",
                        List.of("Изменивший"))
                .checkElementsOnFields("Список активных фильтров",
                        List.of("Номер заявки",
                                "Стратегия",
                                "Время попадания на РП",
                                "ФИО заемщика",
                                "Сумма кредита",
                                "Вид кредита",
                                "Тип заявки",
                                "Программа кредитования",
                                "Статус заявки",
                                "Предыдущий статус",
                                "Изменивший"));
        expectedFilters = filterListSettingsPage.getFiltersname("Список активных фильтров");
        filterListSettingsPage.clickOnElement("Кнопка Закрыть окно фильтров")
                .goTo(personalAccountPage)
                .checkTableHeaders("Таблица в работе", expectedFilters)
                .clickOnElement("Кнопка Настройка списка(таблица Отложено)")
                .goTo(filterListSettingsPage)
                .dragColumns("из правой колонки в левую",
                        List.of("Была доработка"))
                .checkElementsOnFields("Список активных фильтров",
                        List.of("Номер заявки",
                                "Стратегия",
                                "Время попадания на РП",
                                "ФИО заемщика",
                                "Сумма кредита",
                                "Вид кредита",
                                "Тип заявки",
                                "Программа кредитования",
                                "Статус заявки",
                                "Предыдущий статус",
                                "Была доработка"));
        expectedFiltersPostponed = filterListSettingsPage.getFiltersname("Список активных фильтров");
        filterListSettingsPage.clickOnElement("Кнопка Закрыть окно фильтров")
                .goTo(personalAccountPage)
                .checkTableHeaders("Таблица Отложено",
                        expectedFiltersPostponed)
                .clickOnElement("Кнопка выхода");

        setUp();
        personalAccountPage.checkTableHeaders("Таблица в работе", expectedFilters)
                .checkTableHeaders("Таблица Отложено", expectedFiltersPostponed);
    }

    @ParameterizedTest
    @CsvSource({
            "1644945, в работе, таблица В работе",
            "1644953, Отложено, таблица Отложено"
    })
    @Tag("default_columns")
    @Tag("smoke")
    @ExternalId("{id}")
    @DisplayName("{id} - Личный кабинет. Таблица \"{tableName}\" - столбцы по умолчанию")
    @WorkItemIds("{id}")
    public void defaultColumns_1644945_1644953(String id, String tableName, String buttonName) {
        personalAccountPage.clickOnElement("Кнопка Настройка списка(" + buttonName + ")")
                .goTo(filterListSettingsPage).resetFilters();
        personalAccountPage.checkTableHeaders("Таблица " + tableName,
                List.of("Номер заявки",
                        "Стратегия",
                        "Время попадания на РП",
                        "ФИО заемщика",
                        "Сумма кредита",
                        "Вид кредита",
                        "Тип заявки",
                        "Программа кредитования",
                        "Статус заявки",
                        "Предыдущий статус"));
    }

    @Test
    @Tag("additional_columns_display")
    @DisplayName("1644948 - Личный кабинет. Таблица \"В работе\" - дополнительные столбцы для отображения")
    @WorkItemIds({"1644948"})
    public void additionalColumnsDisplay_1644948() {
        personalAccountPage.clickOnElement("Кнопка Настройка списка(таблица В работе)")
                .goTo(filterListSettingsPage)
                .checkElementsOnFields("Список не активных фильтров",
                        List.of("Владелец блокировки",
                                "Дата рождения заемщика",
                                "Форма подтверждения дохода",
                                "Отправивший на доработку/корректировку",
                                "Наименование филиала",
                                "Наименование доп. офиса",
                                "Наименование опер. офиса",
                                "Наименование работодателя",
                                "Изменивший",
                                "Дата создания",
                                "Признак «Госслужащий»",
                                "ИНН работодателя",
                                "Номер клиента PSB Retail",
                                "Управленческий статус заемщика",
                                "Андеррайтер",
                                "Дата версии",
                                "Макс. сумма кредита",
                                "Дата изменения",
                                "Была доработка",
                                "Удостоверение личности военнослужащего",
                                "Дата возврата заявки",
                                "Возврат из отложенных",
                                "ГО",
                                "Утверждающий",
                                "КПП работодателя",
                                "Дата принятия решения",
                                "Отправка на доработку",
                                "Полномочия",
                                "Время попадания в очередь",
                                "Назначена вручную",
                                "Причина назначения",
                                "Перевод в отложенные",
                                "Причина перевода в отложенные",
                                "Региональное время",
                                "Верификатор",
                                "НИС"))
                .clickOnElement("Кнопка Закрыть окно фильтров");
    }

    @Test
    @Tag("check_pagination")
    @DisplayName("1644940 - Личный кабинет. Проверка пагинации. Значение по умолчанию")
    @WorkItemIds({"1644940"})
    public void checkPagination_1644940() {
        personalAccountPage.checkElementByTitleEquals("Выпадающий список Отображать по (таблица В работе)", "10")
                .checkElementByTitleEquals("Выпадающий список Отображать по (таблица Отложено)", "10");
    }

    @Test
    @Tag("smoke")
    @Tag("check_toggle")
    @DisplayName("1644943 - Личный кабинет. Проверка наличия переключателя \"Новая заявка\"")
    @WorkItemIds({"1644943"})
    public void checkToggleNewClaim_1644943() {
        personalAccountPage
                .clickOnElement("Раздел Верификация")
                .assertElementByTitleVisibility("Раздел Верификация", "отображается")
                .assertElementByTitleVisibility("Раздел Андеррайтинг", "отображается")
                .assertElementByTitleVisibility("Раздел Утверждение", "отображается")
                .assertElementByTitleVisibility("Раздел Проверка сотрудниками ОПМ", "отображается")
                .assertElementByTitleVisibility("Раздел Вопрос в ГО", "отображается")
                .checkBottomLine("Раздел Верификация")
                .colorElementEquals("Раздел Верификация", "rgba(44, 47, 123, 1)")
                .assertElementByTitleVisibility("Переключатель Новая заявка", "отображается")
                .clickOnElement("Раздел Андеррайтинг")
                .assertElementByTitleVisibility("Переключатель Новая заявка", "отображается")
                .clickOnElement("Раздел Утверждение")
                .assertElementByTitleVisibility("Переключатель Новая заявка", "не отображается")
                .clickOnElement("Раздел Вопрос в ГО")
                .assertElementByTitleVisibility("Переключатель Новая заявка", "не отображается")
                .clickOnElement("Раздел Проверка сотрудниками ОПМ")
                .assertElementByTitleVisibility("Переключатель Новая заявка", "не отображается");
    }

    @Test
    @Tag("remembering_columns")
    @DisplayName("1644939 - Личный кабинет. Запоминание перечня отображаемых столбцов для блока Андеррайтинг + Утверждение + Верификация")
    @WorkItemIds({"1644939"})
    public void rememberingColumns_1644939() {
        Map<String, List<String>> tabsAndColumns = new HashMap<>();
        addEntry(tabsAndColumns, "Верификация", List.of("Наименование филиала", "Утверждающий"));
        addEntry(tabsAndColumns, "Андеррайтинг", List.of("Изменивший", "Была доработка"));
        addEntry(tabsAndColumns, "Утверждение", List.of("Дата рождения заемщика", "Дата принятия решения"));

        tabsAndColumns.forEach((key, values) ->
                personalAccountPage
                        .clickOnElement("Раздел " + key)
                        .clickOnElement("Кнопка Настройка списка(таблица В работе)")
                        .goTo(filterListSettingsPage)
                        .dragColumns("из правой колонки в левую",
                                List.of(values.get(0)))
                        .checkElementsOnFields("Список активных фильтров",
                                List.of(values.get(0)))
                        .clickOnElement("Кнопка Закрыть окно фильтров")
                        .goTo(personalAccountPage)
                        .checkContainsTableHeaders("Таблица в работе", List.of(values.get(0)))
                        .clickOnElement("Кнопка Настройка списка(таблица Отложено)")
                        .goTo(filterListSettingsPage)
                        .dragColumns("из правой колонки в левую",
                                List.of(values.get(1)))
                        .checkElementsOnFields("Список активных фильтров",
                                List.of(values.get(1)))
                        .clickOnElement("Кнопка Закрыть окно фильтров")
                        .goTo(personalAccountPage)
                        .checkContainsTableHeaders("Таблица Отложено", List.of(values.get(1))));

        checkRememberColumns(tabsAndColumns);
        resetAndCheckColumns(tabsAndColumns);
    }

    private void checkRememberColumns(Map<String, List<String>> map) {
        map.forEach((key, values) ->
                personalAccountPage
                        .clickOnElement("Раздел " + key)
                        .checkContainsTableHeaders("Таблица в работе", List.of(values.get(0)))
                        .checkContainsTableHeaders("Таблица Отложено", List.of(values.get(1))));
    }

    private void resetAndCheckColumns(Map<String, List<String>> map) {
        map.forEach((key, values) ->
                personalAccountPage
                        .clickOnElement("Раздел " + key)
                        .clickOnElement("Кнопка Настройка списка(таблица В работе)")
                        .goTo(filterListSettingsPage).resetFilters()
                        .goTo(personalAccountPage).clickOnElement("Кнопка Настройка списка(таблица Отложено)")
                        .goTo(filterListSettingsPage).resetFilters()
                        .goTo(personalAccountPage)
                        .checkNotContainsTableHeaders("Таблица в работе", List.of(values.get(0)))
                        .checkNotContainsTableHeaders("Таблица Отложено", List.of(values.get(1))));
    }

    private void addEntry(Map<String, List<String>> map, String key, List<String> values) {
        map.put(key, new ArrayList<>(values));
    }
}