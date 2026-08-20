package ru.autotestframework.regress.personal_account.statement;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.ExternalId;
import ru.psb.testit.annotations.WorkItemIds;

import java.util.List;

@Tag("regress")
@Tag("personal_account")
@Tag("statement")
@Tag("statement_no_claim")
@ClassName("Личный кабинет. Утверждение")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StatementNoClaimTest extends BaseTest {

    @BeforeEach
    public void login() {
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi();
        }
        personalAccountPage.checkModal()
                .openMenuLinks("Личный кабинет")
                .clickOnElement("Раздел Утверждение");
        if (!personalAccountPage.postPonedTable.isVisible()) {
            personalAccountPage.clickOnElement("Кнопка раскрыть таблицу Отложено");
        }
    }

    @Test
    @Tag("drag_columns_1651124")
    @DisplayName("1651124 - Личный кабинет. Утверждение. Перемещение столбцов")
    @WorkItemIds({"1651124"})
    public void dragColumns_1651124() {
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
    @Tag("horizontal_scroll_1651121")
    @DisplayName("1651121 - Личный кабинет. Утверждение. Горизонтальный скроллинг")
    @WorkItemIds({"1651121"})
    public void horizontalScroll_1651121() {
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
    @Tag("list_of_remembered_columns_1651123")
    @DisplayName("1651123 - Личный кабинет. Утверждение. Перечень отображаемых столбцов для блока «В работе» " +
            "запоминается отдельно от перечня отображаемых столбцов для блока «Отложено»")
    @WorkItemIds({"1651123"})
    public void listOfRememberedColumns_1651123() {
        List<String> expectedFilters;
        personalAccountPage.clickOnElement("Кнопка Настройка списка(таблица В работе)")
                .goTo(filterListSettingsPage)
                .dragColumns("из правой колонки в левую",
                        List.of("Изменивший"))
                .checkElementsOnFields("Список активных фильтров",
                        List.of("Номер заявки",
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
                                "Время попадания на РП",
                                "ФИО заемщика",
                                "Сумма кредита",
                                "Вид кредита",
                                "Тип заявки",
                                "Была доработка",
                                "Программа кредитования",
                                "Статус заявки",
                                "Предыдущий статус"));
        filterListSettingsPage.clickOnElement("Кнопка Закрыть окно фильтров")
                .goTo(personalAccountPage)
                .checkTableHeaders("Таблица Отложено", List.of("Номер заявки",
                        "Время попадания на РП",
                        "ФИО заемщика",
                        "Сумма кредита",
                        "Вид кредита",
                        "Тип заявки",
                        "Была доработка",
                        "Программа кредитования",
                        "Статус заявки",
                        "Предыдущий статус"))
                .clickOnElement("Кнопка выхода");
        login();
        personalAccountPage.checkTableHeaders("Таблица в работе", expectedFilters)
                .checkTableHeaders("Таблица Отложено", List.of("Номер заявки",
                        "Время попадания на РП",
                        "ФИО заемщика",
                        "Сумма кредита",
                        "Вид кредита",
                        "Тип заявки",
                        "Была доработка",
                        "Программа кредитования",
                        "Статус заявки",
                        "Предыдущий статус"));
    }

    @ParameterizedTest
    @CsvSource({
            "1651116, в работе, таблица В работе",
            "1651128, Отложено, таблица Отложено"
    })
    @Tag("default_columns_1651116")
    @Tag("default_columns_1651128")
    @ExternalId("{id}")
    @DisplayName("{id} - Личный кабинет. Утверждение. Таблица \"{tableName}\" - столбцы по умолчанию")
    @WorkItemIds("{id}")
    public void defaultColumns_1651116_1651128(String id, String tableName, String buttonName) {
        personalAccountPage.clickOnElement("Кнопка Настройка списка(" + buttonName + ")")
                .goTo(filterListSettingsPage).resetFilters();
        personalAccountPage.checkTableHeaders("Таблица " + tableName,
                List.of("Номер заявки",
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
    @Tag("additional_columns_display_1651130")
    @DisplayName("1651130 - Личный кабинет. Утверждение. Таблица \"В работе\" - дополнительные столбцы для отображения")
    @WorkItemIds({"1651130"})
    public void additionalColumnsDisplay_1651130() {
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
    @Tag("check_pagination_1651118")
    @DisplayName("1651118 - Личный кабинет. Утверждение. Проверка пагинации. Значение по умолчанию")
    @WorkItemIds({"1651118"})
    public void checkPagination_1651118() {
        personalAccountPage.checkElementByTitleEquals("Выпадающий список Отображать по (таблица В работе)", "10")
                .checkElementByTitleEquals("Выпадающий список Отображать по (таблица Отложено)", "10");
    }
}