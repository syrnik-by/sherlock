package ru.autotestframework.regress.personal_account.underwriting;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.ExternalId;
import ru.psb.testit.annotations.WorkItemIds;

import java.util.ArrayList;
import java.util.List;

@Tag("regress")
@Tag("personal_account")
@Tag("underwriting")
@Tag("underwriting_no_claim")
@ClassName("Личный кабинет. Андеррайтинг")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UnderwritingNoClaimTest extends BaseTest {

    private static final List<String> DEFAULT_COLUMNS = List.of(
            "Номер заявки", "Время попадания на РП", "ФИО заемщика", "Сумма кредита", "Вид кредита",
            "Тип заявки", "Программа кредитования", "Статус заявки", "Предыдущий статус"
    );

    @BeforeEach
    public void setUp() {
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            navigationToUnderwritingSection();
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

    private void navigationToUnderwritingSection()
    {
        loginPage.openAuthorizationPage()
                .loginViaUi()
                .openMenuLinks("Личный кабинет")
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Андеррайтинг")
                .clickOnElement("Кнопка раскрыть таблицу Отложено")
                .waitElementVisible("Таблица Отложено");
    }

    @Test
    @Tag("drag_columns_1650672")
    @DisplayName("1650672 - Личный кабинет. Андеррайтинг. Перемещение столбцов")
    @WorkItemIds({"1650672"})
    public void dragColumns_1650672() {
        List<String> expectedFilters;
        personalAccountPage.clickOnElement("Кнопка Настройка списка(таблица В работе)")
                .goTo(filterListSettingsPage)
                .dragColumns("из правой колонки в левую",
                        List.of("Владелец блокировки",
                                "Дата рождения заемщика",
                                "Андеррайтер",
                                "Наименование филиала"))
                .checkElementsOnFields("Список активных фильтров",
                        combineColumns(List.of("Владелец блокировки", "Дата рождения заемщика", "Андеррайтер", "Наименование филиала")));
        expectedFilters = filterListSettingsPage.getFiltersname("Список активных фильтров");
        filterListSettingsPage.clickOnElement("Кнопка Закрыть окно фильтров")
                .goTo(personalAccountPage)
                .checkTableHeaders("Таблица в работе", expectedFilters)
                .clickOnElement("Кнопка Настройка списка(таблица В работе)")
                .goTo(filterListSettingsPage)
                .clickOnElement("Кнопка Сбросить")
                .checkElementsOnFields("Список активных фильтров", DEFAULT_COLUMNS);
        expectedFilters = filterListSettingsPage.getFiltersname("Список активных фильтров");
        filterListSettingsPage.clickOnElement("Кнопка Закрыть окно фильтров")
                .goTo(personalAccountPage)
                .checkTableHeaders("Таблица в работе", expectedFilters);
    }

    @Test
    @Tag("horizontal_scroll_1650679")
    @DisplayName("1650679 - Личный кабинет. Андеррайтинг. Горизонтальный скроллинг")
    @WorkItemIds({"1650679"})
    public void horizontalScroll_1650679() {
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
                        combineColumns(List.of("Владелец блокировки", "Дата рождения заемщика", "Андеррайтер", "Наименование филиала")));
        expectedFilters = filterListSettingsPage.getFiltersname("Список активных фильтров");
        filterListSettingsPage.clickOnElement("Кнопка Закрыть окно фильтров")
                .goTo(personalAccountPage)
                .checkTableHeaders("Таблица в работе", expectedFilters)
                .checkScroll("горизонтальный", "Таблица в работе", true)
                .clickOnElement("Кнопка Настройка списка(таблица В работе)")
                .goTo(filterListSettingsPage)
                .clickOnElement("Кнопка Сбросить")
                .checkElementsOnFields("Список активных фильтров", DEFAULT_COLUMNS);
        expectedFilters = filterListSettingsPage.getFiltersname("Список активных фильтров");
        filterListSettingsPage.clickOnElement("Кнопка Закрыть окно фильтров")
                .goTo(personalAccountPage)
                .checkTableHeaders("Таблица в работе", expectedFilters);
    }


    @ParameterizedTest
    @CsvSource({
            "1650668, в работе, таблица В работе",
            "1650680, Отложено, таблица Отложено"
    })
    @Tag("default_columns_1650668")
    @Tag("default_columns_1650680")
    @ExternalId("{id}")
    @DisplayName("{id} - Личный кабинет. Андеррайтинг. Таблица \"{tableName}\" - столбцы по умолчанию")
    @WorkItemIds("{id}")
    public void defaultColumns_1644945_1644953(String id, String tableName, String buttonName) {
        personalAccountPage.clickOnElement("Кнопка Настройка списка(" + buttonName + ")")
                .goTo(filterListSettingsPage).resetFilters();
        personalAccountPage.checkTableHeaders("Таблица " + tableName,
                DEFAULT_COLUMNS);
    }

    @Test
    @Tag("additional_columns_display_1650674")
    @DisplayName("1650674 - Личный кабинет. Андеррайтинг. Таблица \"В работе\" - дополнительные столбцы для отображения")
    @WorkItemIds({"1650674"})
    public void additionalColumnsDisplay_1650674() {
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
                                "НИС"))
                .clickOnElement("Кнопка Закрыть окно фильтров");
    }

    @Test
    @Tag("check_pagination_1650673")
    @DisplayName("1650673 - Личный кабинет. Андеррайтинг. Проверка пагинации. Значение по умолчанию")
    @WorkItemIds({"1650673"})
    public void checkPagination_1650673() {
        personalAccountPage.checkElementByTitleEquals("Выпадающий список Отображать по (таблица В работе)", "10")
                .checkElementByTitleEquals("Выпадающий список Отображать по (таблица Отложено)", "10");
    }

    @Test
    @Tag("list_of_remembered_columns_1650670")
    @DisplayName("1650670 - Личный кабинет. Андеррайтинг. Перечень отображаемых столбцов для блока «В работе» " +
            "запоминается отдельно от перечня отображаемых столбцов для блока «Отложено»")
    @WorkItemIds({"1650670"})
    public void listOfRememberedColumns_1650670() {
        List<String> expectedFilters;
        List<String> expectedFiltersPostponed;
        personalAccountPage.clickOnElement("Кнопка Настройка списка(таблица В работе)")
                .goTo(filterListSettingsPage)
                .dragColumns("из правой колонки в левую",
                        List.of("Изменивший"))
                .checkElementsOnFields("Список активных фильтров",
                        combineColumns(List.of("Изменивший")));
        expectedFilters = filterListSettingsPage.getFiltersname("Список активных фильтров");
        filterListSettingsPage.clickOnElement("Кнопка Закрыть окно фильтров")
                .goTo(personalAccountPage)
                .checkTableHeaders("Таблица в работе", expectedFilters)
                .clickOnElement("Кнопка Настройка списка(таблица Отложено)")
                .goTo(filterListSettingsPage)
                .dragColumns("из правой колонки в левую",
                        List.of("Была доработка"))
                .checkElementsOnFields("Список активных фильтров",
                        combineColumns(List.of("Была доработка")));
        expectedFiltersPostponed = filterListSettingsPage.getFiltersname("Список активных фильтров");
        filterListSettingsPage.clickOnElement("Кнопка Закрыть окно фильтров")
                .waitElementDisappear("Список активных фильтров")
                .goTo(personalAccountPage)
                .checkTableHeaders("Таблица Отложено",
                        expectedFiltersPostponed)
                .clickOnElement("Кнопка выхода");
        navigationToUnderwritingSection();
        personalAccountPage.checkTableHeaders("Таблица в работе", expectedFilters)
                .checkTableHeaders("Таблица Отложено", expectedFiltersPostponed)
                .clickOnElement("Кнопка Настройка списка(таблица В работе)")
                .goTo(filterListSettingsPage).resetFilters()
                .goTo(personalAccountPage).clickOnElement("Кнопка Настройка списка(таблица Отложено)")
                .goTo(filterListSettingsPage).resetFilters()
                .goTo(personalAccountPage)
                .checkNotContainsTableHeaders("Таблица в работе", List.of("Изменивший"))
                .checkNotContainsTableHeaders("Таблица Отложено", List.of("Была доработка"));
    }

    private List<String> combineColumns(List<String> additionalFilters) {
        List<String> combined = new ArrayList<>(UnderwritingNoClaimTest.DEFAULT_COLUMNS);
        combined.addAll(additionalFilters);
        return combined;
    }

}