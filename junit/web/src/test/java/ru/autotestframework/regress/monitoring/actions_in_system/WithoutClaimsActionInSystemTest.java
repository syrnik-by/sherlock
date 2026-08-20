package ru.autotestframework.regress.monitoring.actions_in_system;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.autotestframework.BaseTest;
import ru.autotestframework.pages.monitoring.ActionsInSystemPage;
import ru.autotestframework.pages.setting_control.AssignmentStrategyConfiguration;
import ru.psb.testit.annotations.*;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.models.LinkType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;
import static ru.autotestframework.utils.Constants.*;

@Tag("regress")
@Tag("monitoring")
@Tag("actions_in_system")
@Tag("without_claims_actions_in_system")
@ClassName("Мониторинг. Действия в системе")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WithoutClaimsActionInSystemTest extends BaseTest {

    private final String dateTime = LocalDateTime.now().withMinute(0).format(DF);
    private final DateTimeFormatter df = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final DateTimeFormatter df_with_seconds = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    @BeforeAll
    public void login() {
        loginPage.openAuthorizationPage()
                .loginViaUi()
                .openMenuLinks("Настройки > Общие > Общие настройки системы")
                .goTo(commonSystemSettingsPage)
                .waitBusyCondition()
                .fillInput("Поле ввода Количество минут для автоматического возврата отложенной заявки", "5")
                .fillInput("Поле ввода Максимальное количество заявок в личной очереди, при котором кнопка «Новая заявка» должна быть заблокирована", "100")
                .fillInput("Поле ввода Напомнить о звонке за (минут)", "10")
                .clickOnElement("Кнопка Сохранить");
    }

    @BeforeEach
    public void beforeEach() {
        try {
            loginPage.checkUrlContains("login")
                    .openAuthorizationPage()
                    .loginViaUi()
                    .openMenuLinks("Личный кабинет");
        } catch (ConditionNotMetException e) {
            loginPage.checkModal();
        }
    }

    @AfterEach
    public void afterEach() {
        BODY.click(ClickOptions.usingDefaultMethod().offset(400, 200));
        while (scriptConfigurationPage.appCreateModal.isDisplayed()) {
            scriptConfigurationPage.closeMainScriptButton.click();
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "3973122; Дополнительный вопрос; Дополнительные вопросы",
            "3973134; Основной скрипт; Основной скрипт"
    })
    @Tag("questions_deletion_3973122_3973134")
    @DisplayName("{id} - Мониторинг. Действия в системе. Вопросы. {displayName}. Удаление")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void questions_deletion_3973122_3973134(String id, String displayName, String typeOfQuestion) {
        clearTableFromQuestions();
        loginPage.openMenuLinks("Управление настройками > Настройка скриптов")
                .goTo(scriptConfigurationPage)
                .clickOnElement("Вкладка Вопросы")
                .clickOnElement("Кнопка Создать")
                .assertElementByTitleVisibility("Модальное окно Создание вопроса", "отображается")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса (Создание вопроса)", typeOfQuestion);
        if (id.equals("3973122")) {
            scriptConfigurationPage.assertElementByTitleVisibility("Выпадающий список Блок вопроса (Создание вопроса)", "отображается")
                    .assertElementByTitleVisibility("Выпадающий список Принадлежность абоненту (Создание вопроса)", "отображается")
                    .selectValueFromDropDownList("Выпадающий список Блок вопроса (Создание вопроса)", "Вопросы по клиенту")
                    .selectValueFromDropDownList("Выпадающий список Принадлежность абоненту (Создание вопроса)", "Клиент");
        }
        scriptConfigurationPage.fillInput("Поле ввода Текст вопроса (Создание вопроса)", "\"Тест_мониторинг(АТ)\"")
                .clickOnElement("Кнопка Сохранить (Создание вопроса)")
                .fillInput("Поле ввода Текст вопроса", "\"Тест_мониторинг(АТ)\"")
                .clickOnElement("Кнопка Найти")
                .waitBusyCondition()
                .clickOnCellFromTable("Таблица Результаты поиска", 1, 1)
                .clickOnElement("Кнопка Удалить")
                .assertElementByTitleVisibility("Модальное окно Подтверждение удаления", "отображается")
                .clickOnElement("Кнопка Удалить (Подтверждение удаления)");
        String dateTimeNow = LocalDateTime.now().format(DF);
        dbSteps.executeQuery(VERIFICATION,
                "SELECT id, business_id FROM verification.verification.vrf_item vi " +
                        "WHERE item_text = '\"Тест_мониторинг(АТ)\"' " +
                        "ORDER BY id DESC LIMIT 1;");
        String idDB = dbSteps.getValuesFromResponseDb("id").get(0);
        String business_id = "";
        if (id.equals("3973122")) {
            business_id = dbSteps.getValuesFromResponseDb("business_id").get(0);
        }
        scriptConfigurationPage.assertElementByTitleVisibility("Модальное окно Информация об ошибке", "отображается")
                .clickOnElement("Кнопка Ок (Информация об ошибке)");
        goToActionsInSystemPage("Автоматическое Тестирование1", "", "Вопросы. Удаление")
                .sortValuesInColumn("Время", "убыванию")
                .waitBusyCondition();
        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", List.of("Автоматическое Тестирование1")),
                Map.entry("ФИО пользователя (объект)", List.of("")),
                Map.entry("Время", List.of(dateTimeNow)),
                Map.entry("Тип события", List.of("Вопросы. Удаление")),
                Map.entry("Описание события", List.of("Пользователь Автоматическое Тестирование1 удалил " + typeOfQuestion +
                        (id.equals("3973122") ? " № " + business_id + " (" + idDB + ")" : " (" + idDB + ")"))));
        checkTableMonitoring(expectedValues);
        actionsInSystemPage.clickOnElement("Кнопка Удалить все")
                .clickOnElement("Кнопка Сбросить сортировку");
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "3948864; Дополнительный вопрос. Блок вопроса; Дополнительные вопросы; Вопросы по клиенту; Клиент; \"Тест_мониторинг(АТ)\"; Дополнительные вопросы; Вопросы по антифроду; Клиент",
            "3951069; Подсказки. Текст вопроса; Подсказки; ''; ''; \"Тест_мониторинг(АТ) новое значение\"; Подсказки; ''; ''",
            "3973119; Тип вопроса; Основной скрипт; Приветствие; ''; \"Тест_мониторинг(АТ)\"; Ситуационные вопросы; ''; Клиент"
    })
    @Tag("questions_modification_3948864_3951069_3973119")
    @DisplayName("{id} - Мониторинг. Действия в системе. Вопросы.  Редактирование. {displayName}")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void questions_modification_3948864_3951069_3973119(String id, String displayName, String typeOfQuestion, String oldBlock, String oldAffiliation, String newText, String newTypeOfQuestion, String newBlock, String newAffiliation) {
        clearTableFromQuestions();
        loginPage.openMenuLinks("Управление настройками > Настройка скриптов")
                .goTo(scriptConfigurationPage)
                .clickOnElement("Вкладка Вопросы")
                .clickOnElement("Кнопка Создать")
                .assertElementByTitleVisibility("Модальное окно Создание вопроса", "отображается")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса (Создание вопроса)", typeOfQuestion);
        if (id.equals("3948864")) {
            scriptConfigurationPage.assertElementByTitleVisibility("Выпадающий список Блок вопроса (Создание вопроса)", "отображается")
                    .assertElementByTitleVisibility("Выпадающий список Принадлежность абоненту (Создание вопроса)", "отображается")
                    .selectValueFromDropDownList("Выпадающий список Блок вопроса (Создание вопроса)", "Вопросы по клиенту")
                    .selectValueFromDropDownList("Выпадающий список Принадлежность абоненту (Создание вопроса)", "Клиент");
        }
        scriptConfigurationPage.fillInput("Поле ввода Текст вопроса (Создание вопроса)", "\"Тест_мониторинг(АТ)\"")
                .clickOnElement("Кнопка Сохранить (Создание вопроса)")
                .fillInput("Поле ввода Текст вопроса", "\"Тест_мониторинг(АТ)\"")
                .clickOnElement("Кнопка Найти")
                .waitBusyCondition()
                .clickOnCellFromTable("Таблица Результаты поиска", 1, 2)
                .assertElementByTitleVisibility("Модальное окно Создание вопроса", "отображается");
        if (id.equals("3948864")) {
            scriptConfigurationPage.selectValueFromDropDownList("Выпадающий список Блок вопроса (Создание вопроса)", "Вопросы по антифроду");
        } else if (id.equals("3951069")) {
            scriptConfigurationPage.fillInput("Поле ввода Текст вопроса (Создание вопроса)", "\"Тест_мониторинг(АТ) новое значение\"");
        } else {
            scriptConfigurationPage.selectValueFromDropDownList("Выпадающий список Тип вопроса (Создание вопроса)", "Ситуационные вопросы")
                    .selectValueFromDropDownList("Выпадающий список Принадлежность абоненту (Создание вопроса)", "Клиент");
        }
        scriptConfigurationPage.clickOnElement("Кнопка Сохранить (Создание вопроса)");
        String dateTimeNow = LocalDateTime.now().format(DF);
        goToActionsInSystemPage("Автоматическое Тестирование1", "", "Вопросы. Редактирование")
                .sortValuesInColumn("Время", "убыванию")
                .waitBusyCondition();
        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", List.of("Автоматическое Тестирование1")),
                Map.entry("ФИО пользователя (объект)", List.of("")),
                Map.entry("Время", List.of(dateTimeNow)),
                Map.entry("Тип события", List.of("Вопросы. Редактирование")),
                Map.entry("Описание события", List.of("Пользователь Автоматическое Тестирование1 отредактировал " + typeOfQuestion + ". " +
                        "Старое значение: Текст вопроса = \"\"Тест_мониторинг(АТ)\"\" Тип вопроса = \"" + typeOfQuestion + "\" Блок вопроса = \"" + oldBlock + "\" Принадлежность абоненту = \"" + oldAffiliation + "\" Название профессии = \"\" " +
                        "Новое значение: Текст вопроса = \"" + newText + "\" Тип вопроса = \"" + newTypeOfQuestion + "\" Блок вопроса = \"" + newBlock + "\" Принадлежность абоненту = \"" + newAffiliation + "\" Название профессии = \"\"")));
        checkTableMonitoring(expectedValues);
        actionsInSystemPage.clickOnElement("Кнопка Удалить все")
                .clickOnElement("Кнопка Сбросить сортировку");
        clearTableFromQuestions();
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "2652914; Дополнительные вопросы; Дополнительные вопросы",
            "2652912; Подсказки; Подсказки"
    })
    @Tag("questions_creation_2652914_2652912")
    @DisplayName("{id} - Мониторинг. Действия в системе. Вопросы. Создание. {displayName}")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void questions_creation_2652914_2652912(String id, String displayName, String typeOfQuestion) {
        clearTableFromQuestions();
        loginPage.openMenuLinks("Управление настройками > Настройка скриптов")
                .goTo(scriptConfigurationPage)
                .clickOnElement("Вкладка Вопросы")
                .clickOnElement("Кнопка Создать")
                .assertElementByTitleVisibility("Модальное окно Создание вопроса", "отображается")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса (Создание вопроса)", typeOfQuestion);
        if (id.equals("2652914")) {
            scriptConfigurationPage.assertElementByTitleVisibility("Выпадающий список Блок вопроса (Создание вопроса)", "отображается")
                    .assertElementByTitleVisibility("Выпадающий список Принадлежность абоненту (Создание вопроса)", "отображается")
                    .selectValueFromDropDownList("Выпадающий список Блок вопроса (Создание вопроса)", "Вопросы по клиенту")
                    .selectValueFromDropDownList("Выпадающий список Принадлежность абоненту (Создание вопроса)", "Клиент");
        }
        scriptConfigurationPage.fillInput("Поле ввода Текст вопроса (Создание вопроса)", "\"Тест_мониторинг(АТ)\"")
                .clickOnElement("Кнопка Сохранить (Создание вопроса)");
        String dateTimeNow = LocalDateTime.now().format(DF);
        goToActionsInSystemPage("Автоматическое Тестирование1", "", "Вопросы. Создание")
                .sortValuesInColumn("Время", "убыванию")
                .waitBusyCondition();
        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", List.of("Автоматическое Тестирование1")),
                Map.entry("ФИО пользователя (объект)", List.of("")),
                Map.entry("Время", List.of(dateTimeNow)),
                Map.entry("Тип события", List.of("Вопросы. Создание")),
                Map.entry("Описание события", List.of("Пользователь Автоматическое Тестирование1 создал " + typeOfQuestion + " с настройками: Текст вопроса = \"\"Тест_мониторинг(АТ)\"\" Тип вопроса = \"" + typeOfQuestion + "\" " +
                        (id.equals("2652914") ? "Блок вопроса = \"Вопросы по клиенту\" Принадлежность абоненту = \"Клиент\" Название профессии = \"\"" :
                                "Блок вопроса = \"\" Принадлежность абоненту = \"\" Название профессии = \"\""))));
        checkTableMonitoring(expectedValues);
        actionsInSystemPage.clickOnElement("Кнопка Удалить все")
                .clickOnElement("Кнопка Сбросить сортировку");
        clearTableFromQuestions();
    }

    @Test
    @Tag("questions_deletion_multiple_questions_3973131")
    @DisplayName("3973131 - Мониторинг. Действия в системе. Вопросы. Удаление нескольких вопросов")
    @WorkItemIds({"3973131"})
    public void questions_deletion_multiple_questions_3973131() {
        clearTableFromQuestions();
        loginPage.openMenuLinks("Управление настройками > Настройка скриптов")
                .goTo(scriptConfigurationPage)
                .clickOnElement("Вкладка Вопросы")
                .clickOnElement("Кнопка Создать")
                .assertElementByTitleVisibility("Модальное окно Создание вопроса", "отображается")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса (Создание вопроса)", "Ситуационные вопросы")
                .assertElementByTitleVisibility("Выпадающий список Принадлежность абоненту (Создание вопроса)", "отображается")
                .selectValueFromDropDownList("Выпадающий список Принадлежность абоненту (Создание вопроса)", "Клиент")
                .fillInput("Поле ввода Текст вопроса (Создание вопроса)", "\"Тест_мониторинг(АТ)\"")
                .clickOnElement("Кнопка Сохранить (Создание вопроса)")
                .clickOnElement("Кнопка Создать")
                .assertElementByTitleVisibility("Модальное окно Создание вопроса", "отображается")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса (Создание вопроса)", "Профессиональные вопросы")
                .assertElementByTitleVisibility("Выпадающий список Название профессии (Создание вопроса)", "отображается")
                .selectValueFromDropDownList("Выпадающий список Название профессии (Создание вопроса)", "Специалисты")
                .fillInput("Поле ввода Текст вопроса (Создание вопроса)", "\"Тест_мониторинг(АТ)\"")
                .clickOnElement("Кнопка Сохранить (Создание вопроса)")
                .clickOnElement("Кнопка Создать")
                .assertElementByTitleVisibility("Модальное окно Создание вопроса", "отображается")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса (Создание вопроса)", "Подсказки")
                .fillInput("Поле ввода Текст вопроса (Создание вопроса)", "\"Тест_мониторинг(АТ)\"")
                .clickOnElement("Кнопка Сохранить (Создание вопроса)")
                .fillInput("Поле ввода Текст вопроса", "\"Тест_мониторинг(АТ)\"")
                .clickOnElement("Кнопка Найти")
                .clickOnCellFromTable("Таблица Результаты поиска", 1, 1)
                .clickOnCellFromTable("Таблица Результаты поиска", 2, 1)
                .clickOnCellFromTable("Таблица Результаты поиска", 3, 1)
                .clickOnElement("Кнопка Удалить")
                .assertElementByTitleVisibility("Модальное окно Подтверждение удаления", "отображается")
                .clickOnElement("Кнопка Удалить (Подтверждение удаления)");
        String dateTimeNow = LocalDateTime.now().format(DF);
        dbSteps.executeQuery(VERIFICATION,
                "SELECT id FROM verification.verification.vrf_item vi " +
                        "WHERE item_text = '\"Тест_мониторинг(АТ)\"' " +
                        "ORDER BY id DESC LIMIT 3;");
        String id_situation = dbSteps.getValuesFromResponseDb("id").get(2);
        String id_prof = dbSteps.getValuesFromResponseDb("id").get(1);
        String id_cheat = dbSteps.getValuesFromResponseDb("id").get(0);
        goToActionsInSystemPage("Автоматическое Тестирование1", "", "Вопросы. Удаление")
                .sortValuesInColumn("Время", "убыванию")
                .waitBusyCondition();
        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", List.of("Автоматическое Тестирование1")),
                Map.entry("ФИО пользователя (объект)", List.of("")),
                Map.entry("Время", List.of(dateTimeNow)),
                Map.entry("Тип события", List.of("Вопросы. Удаление")),
                Map.entry("Описание события", List.of("Пользователь Автоматическое Тестирование1 удалил Ситуационные вопросы, Профессиональные вопросы, Подсказки " +
                        "(" + id_situation + ", " + id_prof + ", " + id_cheat + ")")));
        checkTableMonitoring(expectedValues);
        actionsInSystemPage.clickOnElement("Кнопка Удалить все")
                .clickOnElement("Кнопка Сбросить сортировку");
        clearTableFromQuestions();
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "1723900; Действия в системе",
            "1723894; Действия с заявками"
    })
    @Tag("questions_uploading_report_data_1723900_1723894")
    @DisplayName("{id} - Мониторинг. Действия в системе. Выгрузка данных отчета «{pageName}»")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void questions_uploading_report_data_1723900_1723894(String id, String pageName) {
        loginPage.openMenuLinks("Мониторинг > " + pageName)
                .goTo(id.equals("1723900") ? actionsInSystemPage : actionsRequestsPage);
        if (id.equals("1723900")) {
            actionsInSystemPage.selectEventType("Заявка отправлена на доработку");
        } else {
            actionsRequestsPage.inputTypeOperation("Отправка на доработку. Выбор причины доработки");
        }
        actionsRequestsPage.clickOnElement("Кнопка Найти")
                .waitBusyCondition()
                .assertElementByTitleActivity("Кнопка Выгрузить данные", "активен")
                .clickOnElement("Кнопка Выгрузить данные");
        String dateTimeNow = LocalDateTime.now().format(DF);
        actionsInSystemPage.waitBusyCondition()
                .assertElementByTitleVisibility("Модальное окно Уведомление о создании отчета", "отображается")
                .clickOnElement("Кнопка Ок (Уведомление о создании отчета)")
                .clickOnElement("Кнопка Удалить все");
        goToActionsInSystemPage("Автоматическое Тестирование1", "", "Выгрузка данных отчета «" + pageName + "»")
                .sortValuesInColumn("Время", "убыванию")
                .waitBusyCondition();
        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", List.of("Автоматическое Тестирование1")),
                Map.entry("ФИО пользователя (объект)", List.of("")),
                Map.entry("Время", List.of(dateTimeNow)),
                Map.entry("Тип события", List.of("Выгрузка данных отчета «" + pageName + "»")),
                Map.entry("Описание события", List.of("Пользователь Автоматическое Тестирование1 кликнул по кнопке " +
                        "\"Выгрузить данные\" отчета «" + pageName + "»")));
        checkTableMonitoring(expectedValues);
        actionsInSystemPage.checkNotifications()
                .clickOnElement("Кнопка Удалить все")
                .clickOnElement("Кнопка Сбросить сортировку");
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "4010092; Назначение стратегий",
            "4010097; Правила обработки результата",
            "4010095; Специальные критерии назначения"
    })
    @Tag("publishing_new_version_of_guide_4010092_4010097_4010095")
    @DisplayName("{id} - Мониторинг. Действия в системе. Выпуск новой версии справочника (\"{tabName}\")")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void publishing_new_version_of_guide_4010092_4010097_4010095(String id, String tabName) {
        loginPage.openMenuLinks("Управление настройками > Настройки назначения стратегий")
                .goTo(assignmentStrategyConfiguration)
                .clickOnElement("Вкладка " + tabName)
                .clickOnElement("Вкладка Редактируемая версия")
                .clickOnElement("Кнопка Опубликовать версию")
                .assertElementByTitleVisibility("Модальное окно Опубликовать версию", "отображается")
                .clickOnElement("Переключатель Опубликовать сразу (Опубликовать версию)")
                .assertElementByTitleActivity("Кнопка Опубликовать (Опубликовать версию)", "активен")
                .clickOnElement("Кнопка Опубликовать (Опубликовать версию)")
                .assertElementByTitleVisibility("Модальное окно Подтверждение публикации", "отображается")
                .clickOnElement("Кнопка Опубликовать (Подтверждение публикации)")
                .waitText(5, "Справочник \"" + tabName + "\" опубликован");
        String dateTimeNow = LocalDateTime.now().format(DF);
        goToActionsInSystemPage("Система", "", "Выпуск новой версии справочника")
                .sortValuesInColumn("Время", "убыванию")
                .waitBusyCondition();
        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", List.of("Система")),
                Map.entry("ФИО пользователя (объект)", List.of("")),
                Map.entry("Время", List.of(dateTimeNow)),
                Map.entry("Тип события", List.of("Выпуск новой версии справочника")),
                Map.entry("Описание события", List.of(tabName + ". Выпуск новой версии")));
        checkTableMonitoring(expectedValues);
        actionsInSystemPage.clickOnElement("Кнопка Удалить все")
                .clickOnElement("Кнопка Сбросить сортировку");
    }

    @Test
    @Tag("exiting_system_1723890")
    @DisplayName("1723890 - Мониторинг. Действия в системе. Выход из системы")
    @WorkItemIds({"1723890"})
    public void exiting_system_1723890() {
        loginPage.clickOnElement("Кнопка выхода");
        String dateTimeNow = LocalDateTime.now().format(DF);
        loginPage.openAuthorizationPage()
                .loginViaUi()
                .waitBusyCondition();
        goToActionsInSystemPage("Автоматическое Тестирование1", "", "Выход из системы")
                .sortValuesInColumn("Время", "убыванию")
                .waitBusyCondition();
        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", List.of("Автоматическое Тестирование1")),
                Map.entry("ФИО пользователя (объект)", List.of("")),
                Map.entry("Время", List.of(dateTimeNow)),
                Map.entry("Тип события", List.of("Выход из системы")),
                Map.entry("Описание события", List.of("Пользователь TESTAT1 вышел из системы")));
        checkTableMonitoring(expectedValues);
        actionsInSystemPage.clickOnElement("Кнопка Удалить все")
                .clickOnElement("Кнопка Сбросить сортировку");
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "1723885; Количество минут для автоматического возврата отложенной заявки; 6; 5",
            "1723891; Максимальное количество заявок в личной очереди, при котором кнопка «Новая заявка» должна быть заблокирована; 101; 100",
            "1723872; Напомнить о звонке за (минут); 11; 10"
    })
    @Tag("changing_value_of_common_system_setting_1723885_1723891_1723872")
    @DisplayName("{id} - Мониторинг. Действия в системе. Изменение значения общей настройки системы \"{blockName}\"")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void changing_value_of_common_system_setting_1723885_1723891_1723872(String id, String blockName, String newValue, String oldValue) {
        loginPage.openMenuLinks("Настройки > Общие > Общие настройки системы")
                .goTo(commonSystemSettingsPage)
                .waitBusyCondition()
                .fillInput("Поле ввода " + blockName, newValue)
                .clickOnElement("Кнопка Сохранить")
                .waitText(2, "Настройки системы сохранены");
        String dateTimeNow = LocalDateTime.now().format(DF);
        goToActionsInSystemPage("Автоматическое Тестирование1", "", "Изменение значения общей настройки системы \"" + blockName + "\"")
                .sortValuesInColumn("Время", "убыванию")
                .waitBusyCondition();
        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", List.of("Автоматическое Тестирование1")),
                Map.entry("Время", List.of(dateTimeNow)),
                Map.entry("Тип события", List.of("Изменение значения общей настройки системы \"" + blockName + "\"")),
                Map.entry("Описание события", List.of("Пользователь Автоматическое Тестирование1 изменил значение общей настройки системы \"" + blockName + "\" = " + newValue)));
        checkTableMonitoring(expectedValues);
        actionsInSystemPage.clickOnElement("Кнопка Удалить все")
                .clickOnElement("Кнопка Сбросить сортировку")
                .openMenuLinks("Настройки > Общие > Общие настройки системы")
                .goTo(commonSystemSettingsPage)
                .fillInput("Поле ввода " + blockName, oldValue)
                .clickOnElement("Кнопка Сохранить")
                .waitText(2, "Настройки системы сохранены");
    }

    @Test
    @Tag("changing_options_priority_setting_3946402")
    @DisplayName("3946402 - Мониторинг. Действия в системе. Изменение параметров настройки приоритетов")
    @WorkItemIds({"3946402"})
    public void changing_options_priority_setting_3946402() {
        loginPage.openMenuLinks("Настройки > Настройки приоритетов")
                .goTo(prioritySettingsPage)
                .waitBusyCondition()
                .clickOnElement("Кнопка Добавить")
                .assertElementByTitleVisibility("Модальное окно Настройки приоритетов", "отображается")
                .clickOnElement("Иконка Переключение редактирования поля (Настройки приоритетов)")
                .fillInput("Поле ввода Название настройки приоритетов (Настройки приоритетов)", "Тест_мониторинг(АТ_не трогать)")
                .selectValueFromDropDownList("Выпадающий список Действует (Настройки приоритетов)", "Нет")
                .selectValueFromDropDownList("Выпадающий список Этап обработки (Настройки приоритетов)", "Андеррайтинг")
                .selectValueFromDropDownList("Выпадающий список Сортировка настройки приоритета (Настройки приоритетов)", "Время захода на этап ручной проверки Изначальное")
                .selectValueFromDropDownList("Выпадающий список Тип сортировки (Настройки приоритетов)", "Убывание")
                .selectValueFromDropDownList("Выпадающий список Первый у условия (Настройки приоритетов)", "Вид кредита")
                .selectValueFromDropDownList("Выпадающий список Второй у условия (Настройки приоритетов)", "Равно")
                .selectValueFromDropDownList("Выпадающий список Третий у условия (Настройки приоритетов)", "Единый кредитный лимит")
                .selectValueFromDropDownList("Выпадающий список Первый у сортировки (Настройки приоритетов)", "Время захода на этап ручной проверки Изначальное")
                .selectValueFromDropDownList("Выпадающий список Второй у сортировки (Настройки приоритетов)", "Убывание")
                .clickOnElement("Кнопка Сохранить (Настройки приоритетов)");
        String dateTimeNow = LocalDateTime.now().format(DF);
        prioritySettingsPage.doubleClickByText("Тест_мониторинг(АТ_не трогать)")
                .assertElementByTitleVisibility("Модальное окно Настройки приоритетов", "отображается")
                .selectValueFromDropDownList("Выпадающий список Этап обработки (Настройки приоритетов)", "Верификация")
                .clickOnElement("Кнопка Сохранить (Настройки приоритетов)");
        goToActionsInSystemPage("Автоматическое Тестирование1", "", "Изменение параметров настройки приоритетов")
                .sortValuesInColumn("Время", "убыванию")
                .waitBusyCondition();
        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", List.of("Автоматическое Тестирование1")),
                Map.entry("ФИО пользователя (объект)", List.of("")),
                Map.entry("Время", List.of(dateTimeNow)),
                Map.entry("Тип события", List.of("Изменение параметров настройки приоритетов")),
                Map.entry("Описание события", List.of("Пользователь Автоматическое Тестирование1 изменил параметры настройки приоритетов \"Тест_мониторинг(АТ_не трогать)\" для этапа \"Андеррайтинг\". " +
                        "Старое значение: Название настройки приоритетов = \"Тест_мониторинг(АТ_не трогать)\" Действует = \"Нет\" Этап обработки = \"Андеррайтинг\" Сортировка настройки приоритета = \"Время захода на этап ручной проверки Изначальное\" " +
                        "Порядок сортировки настройки приоритета = \"Убывание\" Правила { Номер правила = \"0\" Действует = \"Да\" Название правила = \"Название приоритета\" Действует от = \"00:00\" " +
                        "Действует по = \"23:59\" Сортировка правила = \"Время захода на этап ручной проверки Изначальное\" Порядок сортировки правила = \"Убывание\" Условия { Условие { Параметр = \"Вид кредита\" Оператор = \"Равно\" " +
                        "Значение = \"Единый кредитный лимит\" } } } Новое значение: Название настройки приоритетов = \"Тест_мониторинг(АТ_не трогать)\" Действует = \"Нет\" Этап обработки = \"Верификация\" " +
                        "Сортировка настройки приоритета = \"Время захода на этап ручной проверки Изначальное\" Порядок сортировки настройки приоритета = \"Убывание\" Правила { Номер правила = \"0\" Действует = \"Да\" " +
                        "Название правила = \"Название приоритета\" Действует от = \"00:00\" Действует по = \"23:59\" Сортировка правила = \"Время захода на этап ручной проверки Изначальное\" " +
                        "Порядок сортировки правила = \"Убывание\" Условия { Условие { Параметр = \"Вид кредита\" Оператор = \"Равно\" Значение = \"Единый кредитный лимит\" } } }")));
        checkTableMonitoring(expectedValues);
        actionsInSystemPage.clickOnElement("Кнопка Удалить все")
                .clickOnElement("Кнопка Сбросить сортировку")
                .openMenuLinks("Настройки > Настройки приоритетов")
                .goTo(prioritySettingsPage)
                .selectValueFromDropDownList("Выпадающий список Этап обработки", "Верификация")
                .clickOnCheckboxNearName("Тест_мониторинг(АТ_не трогать)")
                .clickOnElement("Кнопка Удалить");
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "1723893; Изменение смены графика работы сотрудника; Изменена смена; : тип дня «Рабочий», время смены «09:00» – «18:00», время обеда «13:00» – «14:00»",
            "1723880; Назначение графика работы сотруднику; Автоматическое Тестирование1 назначил Автоматическое Тестирование5 график работы по шаблону «Тест_мониторинг(АТ_не трогать!)»; ''"
    })
    @Tag("changing_work_schedule_1723893_1723880")
    @DisplayName("{id} - Мониторинг. Действия в системе. {displayName}")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void changing_work_schedule_1723893_1723880(String id, String displayName, String descriptionPart1, String descriptionPart2) {
        loginPage.openMenuLinks("Сотрудники > Графики работы")
                .goTo(workSchedulesPage)
                .waitBusyCondition()
                .fillInput("Поиск по ФИО", "Автоматическое Тестирование5")
                .clickOnElement("Кнопка Найти")
                .checkRowCount("Таблица Графики работы", 1)
                .clickOnCellFromTable("Таблица Графики работы", 1, 1)
                .clickOnElement("Кнопка Назначить")
                .selectValueFromDropDownList("Выпадающий список Шаблон", "Тест_мониторинг(АТ_не трогать!)").waitBusyCondition();
        assertIsTrue(workSchedulesPage.getValueByElementTitle("Поле ввода Начало смены (Назначить график)").equals("09:00"),
                "Поле ввода Конец обеда должно быть равно 09:00. Фактическое значение: " + workSchedulesPage.getValueByElementTitle("Поле ввода Начало смены (Назначить график)"));
        assertIsTrue(workSchedulesPage.getValueByElementTitle("Поле ввода Конец смены (Назначить график)").equals("18:00"),
                "Поле ввода Конец обеда должно быть равно 18:00. Фактическое значение: " + workSchedulesPage.getValueByElementTitle("Поле ввода Конец смены (Назначить график)"));
        assertIsTrue(workSchedulesPage.getValueByElementTitle("Поле ввода Всего часов (Назначить график)").equals("8"),
                "Поле ввода Конец обеда должно быть равно 8. Фактическое значение: " + workSchedulesPage.getValueByElementTitle("Поле ввода Всего часов (Назначить график)"));
        assertIsTrue(workSchedulesPage.getValueByElementTitle("Поле ввода Начало обеда (Назначить график)").equals("14:00"),
                "Поле ввода Конец обеда должно быть равно 14:00. Фактическое значение: " + workSchedulesPage.getValueByElementTitle("Поле ввода Начало обеда (Назначить график)"));
        assertIsTrue(workSchedulesPage.getValueByElementTitle("Поле ввода Конец обеда (Назначить график)").equals("15:00"),
                "Поле ввода Конец обеда должно быть равно 15:00. Фактическое значение: " + workSchedulesPage.getValueByElementTitle("Поле ввода Конец обеда (Назначить график)"));
        String todayDate = LocalDate.now().format(df);
        String tomorrowDate = LocalDate.now().plusDays(1).format(df);
        workSchedulesPage.fillDateField("Поле Начало периода", todayDate)
                .fillDateField("Поле Конец периода", tomorrowDate)
                .clickOnElement("Кнопка Сохранить")
                .waitBusyCondition();
        if (id.equals("1723893")) {
            workSchedulesPage.clickOnCellFromTable("Таблица Графики работы", 1, 1)
                    .clickOnElement("Кнопка Редактировать")
                    .selectValueFromDropDownList("Выпадающий список Тип дня", "Рабочий")
                    .fillDateField("Поле Начало периода", todayDate)
                    .fillDateField("Поле Конец периода", tomorrowDate)
                    .fillInput("Поле ввода Начало смены (Назначить график)", "09:00")
                    .fillInput("Поле ввода Конец смены (Назначить график)", "18:00")
                    .fillInput("Поле ввода Начало обеда (Назначить график)", "13:00")
                    .fillInput("Поле ввода Конец обеда (Назначить график)", "14:00")
                    .clickOnElement("Кнопка Сохранить");
        }
        String dateTimeNow = LocalDateTime.now().format(DF);
        goToActionsInSystemPage("Автоматическое Тестирование1", "Автоматическое Тестирование5", displayName)
                .sortValuesInColumn("Время", "убыванию")
                .waitBusyCondition();
        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", List.of("Автоматическое Тестирование1")),
                Map.entry("ФИО пользователя (объект)", List.of("Автоматическое Тестирование5")),
                Map.entry("Время", List.of(dateTimeNow)),
                Map.entry("Тип события", List.of(displayName)),
                Map.entry("Описание события", List.of(descriptionPart1 + " на период " + todayDate + " – " + tomorrowDate + descriptionPart2)));
        checkTableMonitoring(expectedValues);
        actionsInSystemPage.clickOnElement("Кнопка Удалить все")
                .clickOnElement("Кнопка Сбросить сортировку");
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "2652911; . Тип прозвона: Клиент. Удаление Совместительства, Подсказки и смена статусов Ситуационного и Проф. вопросов; Комбинация PD и одобренного лимита; Потребительское кредитование; Интернет; Частичные зарплатные клиенты; 2-НДФЛ.Основное; Специалист; Клиент",
            "2652916; . Тип прозвона: Конт. лицо. Смена вопросов+Удаление подсказки; Отметка о рождении ребенка до 1,5 лет на момент обращения; Потребительское кредитование; DSA; Прочие клиенты; 3-НДФЛ.Основное; Учредитель; Контактное лицо",
            "2652907; . Тип прозвона: Работодатель. Смена вопросов+Основ. скрипт+Подсказка; Наличие просрочки 180+ за последние Х месяцев; Ипотека; Офис; Новые зарплатные клиенты; 2-НДФЛ.Основное; Руководитель; Работодатель",
            "2652909; 2-х скриптов. Тип прозвона: Конт. лицо. Смена вопросов и скрипта +Удаление подсказки; Отметка о рождении ребенка до 1,5 лет на момент обращения; Потребительское кредитование; DSA; Пенсионеры; 3-НДФЛ.Основное; Учредитель; Контактное лицо"
    })
    @Tag("common_script_modification_type_of_call_2652911_2652916_2652907_2652909")
    @DisplayName("{id} - Мониторинг. Действия в системе. Общий скрипт. Редактирование {displayName}")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void common_script_modification_type_of_call_2652911_2652916_2652907_2652909(String id, String displayName, String assignmentReason, String product, String channel, String segment, String form, String status, String callType) {
        String description = "";
        switch (id) {
            case "2652911": {
                description = "Пользователь Автоматическое Тестирование1 отредактировал общий скрипт с причиной назначения \"Комбинация PD и одобренного лимита\" и типом прозвона \"Клиент\" Старое значение: Основной скрипт = \"Скрипт для мониторинга\" Основной скрипт. Неподтвержденный = \"\" Доп.вопрос = \"133\" Доп. вопрос. Неподтв./Совм. = \"134\" Ситуац. вопрос = \"Обязательно\" Проф. вопрос = \"Необязательно\" Текст подсказки = \"Если плохо отвечает, можно сбросить звонок (мониторинг)\" Новое значение: Основной скрипт = \"Скрипт для мониторинга\" Основной скрипт. Неподтвержденный = \"-\" Доп.вопрос = \"133\" Доп. вопрос. Неподтв./Совм. = \"\" Ситуац. вопрос = \"Необязательно\" Проф. вопрос = \"Обязательно\" Текст подсказки = \"\"";
                break;
            }
            case "2652916": {
                description = "Пользователь Автоматическое Тестирование1 отредактировал общий скрипт с причиной назначения \"Отметка о рождении ребенка до 1,5 лет на момент обращения\" и типом прозвона \"Контактное лицо\" Старое значение: Основной скрипт = \"Скрипт для мониторинга\" Основной скрипт. Неподтвержденный = \"\" Доп.вопрос = \"25\" Доп. вопрос. Неподтв./Совм. = \"\" Ситуац. вопрос = \"-\" Проф. вопрос = \"-\" Текст подсказки = \"Если плохо отвечает, можно сбросить звонок (мониторинг)\" Новое значение: Основной скрипт = \"Скрипт для мониторинга\" Основной скрипт. Неподтвержденный = \"-\" Доп.вопрос = \"24\" Доп. вопрос. Неподтв./Совм. = \"-\" Ситуац. вопрос = \"-\" Проф. вопрос = \"-\" Текст подсказки = \"\"";
                break;
            }
            case "2652907": {
                description = "Пользователь Автоматическое Тестирование1 отредактировал общий скрипт с причиной назначения \"Наличие просрочки 180+ за последние Х месяцев\" и типом прозвона \"Работодатель\" Старое значение: Основной скрипт = \"Скрипт для мониторинга\" Основной скрипт. Неподтвержденный = \"\" Доп.вопрос = \"64\" Доп. вопрос. Неподтв./Совм. = \"65\" Ситуац. вопрос = \"Необязательно\" Проф. вопрос = \"-\" Текст подсказки = \"Если плохо отвечает, можно сбросить звонок (мониторинг)\" Новое значение: Основной скрипт = \"Другой скрипт для мониторинга\" Основной скрипт. Неподтвержденный = \"\" Доп.вопрос = \"65\" Доп. вопрос. Неподтв./Совм. = \"64\" Ситуац. вопрос = \"Необязательно\" Проф. вопрос = \"-\" Текст подсказки = \"Получена информация, что было взыскание по кредитным платежам - Результат проверки \"Анализ кредитной истории\"\"";
                break;
            }
            case "2652909": {
                description = "Пользователь Автоматическое Тестирование1 отредактировал общий скрипт с причиной назначения \"Отметка о рождении ребенка до 1,5 лет на момент обращения, Наличие просрочки 180+ за последние Х месяцев\" и типом прозвона \"Контактное лицо\" Старое значение: Основной скрипт = \"Скрипт для мониторинга\" Основной скрипт. Неподтвержденный = \"\" Доп.вопрос = \"25\" Доп. вопрос. Неподтв./Совм. = \"\" Ситуац. вопрос = \"-\" Проф. вопрос = \"-\" Текст подсказки = \"Если плохо отвечает, можно сбросить звонок (мониторинг)\", Основной скрипт = \"Другой скрипт для мониторинга\" Основной скрипт. Неподтвержденный = \"\" Доп.вопрос = \"24\" Доп. вопрос. Неподтв./Совм. = \"\" Ситуац. вопрос = \"-\" Проф. вопрос = \"-\" Текст подсказки = \"Если плохо отвечает, можно сбросить звонок (мониторинг)\" Новое значение: Основной скрипт = \"Другой скрипт для мониторинга\" Основной скрипт. Неподтвержденный = \"-\" Доп.вопрос = \"24, 25\" Доп. вопрос. Неподтв./Совм. = \"-\" Ситуац. вопрос = \"-\" Проф. вопрос = \"-\" Текст подсказки = \"\"";
            }
        }
        loginPage.openMenuLinks("Управление настройками > Настройка скриптов")
                .goTo(scriptConfigurationPage)
                .clickOnElement("Вкладка Общий скрипт")
                .clickOnElement("Кнопка Создать")
                .assertElementByTitleVisibility("Модальное окно Создание общего скрипта", "отображается")
                .selectValueFromDropDownList("Выпадающий список Причина назначения. Критерий отправки на СЗ (Создание общего скрипта)", assignmentReason)
                .selectValueFromDropDownList("Выпадающий список Продукт (Создание общего скрипта)", product)
                .selectValueFromDropDownList("Выпадающий список Канал поступления (Создание общего скрипта)", channel)
                .selectValueFromDropDownList("Выпадающий список Сегмент (Создание общего скрипта)", segment)
                .selectValueFromDropDownList("Выпадающий список Форма подтверждения дохода (Создание общего скрипта)", form)
                .selectValueFromDropDownList("Выпадающий список Статус клиента (Создание общего скрипта)", status)
                .selectValueFromDropDownList("Выпадающий список Тип прозвона (Создание общего скрипта)", callType)
                .assertElementByTitleVisibility("Форма Тип прозвона", "отображается");
        if (id.equals("2652911")) {
            scriptConfigurationPage.selectValueFromDropDownList("Выпадающий список Основной скрипт (Тип прозвона)", "Скрипт для мониторинга", true)
                    .selectValueFromDropDownList("Выпадающий список Ситуационный вопрос (Тип прозвона)", "Обязательно")
                    .selectValueFromDropDownList("Выпадающий список Доп. вопрос. Основное (Звонок клиенту)", "133. Опишите маршрут до работы (мониторинг)")
                    .selectValueFromDropDownList("Выпадающий список Доп. вопрос. Совместительство (Звонок клиенту)", "134. Сколько зарабатываешь? (мониторинг)");
        } else if (id.equals("2652916")) {
            scriptConfigurationPage.selectValueFromDropDownList("Выпадающий список Основной скрипт (Тип прозвона)", "Скрипт для мониторинга", true)
                    .selectValueFromDropDownList("Выпадающий список Доп. вопрос (Тип прозвона)", "25. Вся ли зарплата Вам приходит на карту? (мониторинг)");
        } else if (id.equals("2652907")) {
            scriptConfigurationPage.selectValueFromDropDownList("Выпадающий список Основной скрипт. Подтвержденный (Звонок работодателю)", "Скрипт для мониторинга", true)
                    .selectValueFromDropDownList("Выпадающий список Доп. вопрос. Подтвержденный (Звонок работодателю)", "64. Сколько сотрудников у Вас? (мониторинг)")
                    .selectValueFromDropDownList("Выпадающий список Доп. вопрос. Неподтвержденный (Звонок работодателю)", "65. Сколько этажей у центрального офиса? (мониторинг)");
        } else if (id.equals("2652909")) {
            scriptConfigurationPage.selectValueFromDropDownList("Выпадающий список Основной скрипт (Тип прозвона)", "Скрипт для мониторинга", true)
                    .selectValueFromDropDownList("Выпадающий список Доп. вопрос (Тип прозвона)", "25. Вся ли зарплата Вам приходит на карту? (мониторинг)")
                    .selectValueFromDropDownList("Выпадающий список Текст подсказки (Тип прозвона)", "Если плохо отвечает, можно сбросить звонок (мониторинг)")
                    .clickOnElement("Кнопка Сохранить (Создание общего скрипта)")
                    .clickOnElement("Кнопка Создать")
                    .assertElementByTitleVisibility("Модальное окно Создание общего скрипта", "отображается")
                    .selectValueFromDropDownList("Выпадающий список Причина назначения. Критерий отправки на СЗ (Создание общего скрипта)", "Наличие просрочки 180+ за последние Х месяцев")
                    .selectValueFromDropDownList("Выпадающий список Продукт (Создание общего скрипта)", product)
                    .selectValueFromDropDownList("Выпадающий список Канал поступления (Создание общего скрипта)", channel)
                    .selectValueFromDropDownList("Выпадающий список Сегмент (Создание общего скрипта)", segment)
                    .selectValueFromDropDownList("Выпадающий список Форма подтверждения дохода (Создание общего скрипта)", form)
                    .selectValueFromDropDownList("Выпадающий список Статус клиента (Создание общего скрипта)", status)
                    .selectValueFromDropDownList("Выпадающий список Тип прозвона (Создание общего скрипта)", callType)
                    .selectValueFromDropDownList("Выпадающий список Основной скрипт (Тип прозвона)", "Другой скрипт для мониторинга", true)
                    .selectValueFromDropDownList("Выпадающий список Доп. вопрос (Тип прозвона)", "24. Дайте характеристику на заёмщика (мониторинг)");
        }
        scriptConfigurationPage.selectValueFromDropDownList("Выпадающий список Текст подсказки (Тип прозвона)", "Если плохо отвечает, можно сбросить звонок (мониторинг)")
                .clickOnElement("Кнопка Сохранить (Создание общего скрипта)");
        if (!id.equals("2652909")) {
            scriptConfigurationPage.selectValueFromDropDownList("Выпадающий список Причина назначения", assignmentReason);
        }
        scriptConfigurationPage.selectValueFromDropDownList("Выпадающий список Продукт", product)
                .selectValueFromDropDownList("Выпадающий список Канал поступления", channel)
                .selectValueFromDropDownList("Выпадающий список Сегмент", segment)
                .selectValueFromDropDownList("Выпадающий список Форма подтверждения дохода", form)
                .selectValueFromDropDownList("Выпадающий список Статус клиента", status)
                .selectValueFromDropDownList("Выпадающий список Тип прозвона", callType)
                .clickOnElement("Кнопка Найти")
                .waitBusyCondition();
        if (id.equals("2652909")) {
            scriptConfigurationPage.clickOnCellFromTable("Таблица Результаты поиска", 1, 1)
                    .clickOnCellFromTable("Таблица Результаты поиска", 2, 1)
                    .clickOnElement("Кнопка Редактировать");
        } else {
            scriptConfigurationPage.clickOnCellFromTable("Таблица Результаты поиска", 1, 2);
        }
        scriptConfigurationPage.assertElementByTitleVisibility("Форма Тип прозвона", "отображается");
        if (id.equals("2652911")) {
            scriptConfigurationPage.selectValueFromDropDownList("Выпадающий список Ситуационный вопрос (Тип прозвона)", "Необязательно")
                    .selectValueFromDropDownList("Выпадающий список Проф. вопрос (Звонок клиенту)", "Обязательно")
                    .clickOnCrossNearTextBlock("Форма Тип прозвона", "Доп. вопрос. Совместительство")
                    .clickOnCrossNearTextBlock("Форма Тип прозвона", "Текст подсказки");
        } else if (id.equals("2652916")) {
            scriptConfigurationPage.clickOnCrossNearTextBlock("Форма Тип прозвона", "Доп. вопрос")
                    .selectValueFromDropDownList("Выпадающий список Доп. вопрос (Тип прозвона)", "24. Дайте характеристику на заёмщика (мониторинг)")
                    .clickOnCrossNearTextBlock("Форма Тип прозвона", "Текст подсказки");
        } else if (id.equals("2652907")) {
            scriptConfigurationPage.selectValueFromDropDownList("Выпадающий список Основной скрипт. Подтвержденный (Звонок работодателю)", "Другой скрипт для мониторинга", true)
                    .clickOnCrossNearTextBlock("Форма Тип прозвона", "Доп. вопрос. Подтвержденный")
                    .selectValueFromDropDownList("Выпадающий список Доп. вопрос. Подтвержденный (Звонок работодателю)", "65. Сколько этажей у центрального офиса? (мониторинг)")
                    .clickOnCrossNearTextBlock("Форма Тип прозвона", "Доп. вопрос. Неподтвержденный")
                    .selectValueFromDropDownList("Выпадающий список Доп. вопрос. Неподтвержденный (Звонок работодателю)", "64. Сколько сотрудников у Вас? (мониторинг)")
                    .clickOnCrossNearTextBlock("Форма Тип прозвона", "Текст подсказки")
                    .selectValueFromDropDownList("Выпадающий список Текст подсказки (Тип прозвона)", "Получена информация, что было взыскание по кредитным платежам - Результат проверки \"Анализ кредитной истории\"");
        } else if (id.equals("2652909")) {
            scriptConfigurationPage.selectValueFromDropDownList("Выпадающий список Основной скрипт (Тип прозвона)", "Другой скрипт для мониторинга", true)
                    .selectValueFromDropDownList("Выпадающий список Доп. вопрос (Тип прозвона)", "24. Дайте характеристику на заёмщика (мониторинг)")
                    .clickOnElement("Кнопка Добавить вопрос у Доп. вопрос (Тип прозвона)")
                    .selectValueFromDropDownList("Выпадающий список Доп. вопрос 2 (Тип прозвона)", "25. Вся ли зарплата Вам приходит на карту? (мониторинг)");
        }
        scriptConfigurationPage.clickOnElement("Кнопка Сохранить (Создание общего скрипта)");
        String dateTimeNow = LocalDateTime.now().format(DF);
        goToActionsInSystemPage("Автоматическое Тестирование1", "", "Общий скрипт. Редактирование")
                .sortValuesInColumn("Время", "убыванию")
                .waitBusyCondition();
        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", List.of("Автоматическое Тестирование1")),
                Map.entry("ФИО пользователя (объект)", List.of("")),
                Map.entry("Время", List.of(dateTimeNow)),
                Map.entry("Тип события", List.of("Общий скрипт. Редактирование")),
                Map.entry("Описание события", List.of(description)));
        checkTableMonitoring(expectedValues);
        actionsInSystemPage.clickOnElement("Кнопка Удалить все")
                .clickOnElement("Кнопка Сбросить сортировку")
                .openMenuLinks("Управление настройками > Настройка скриптов")
                .goTo(scriptConfigurationPage)
                .clickOnElement("Вкладка Общий скрипт");
        if (!id.equals("2652909")) {
            scriptConfigurationPage.selectValueFromDropDownList("Выпадающий список Причина назначения", assignmentReason);
        }
        scriptConfigurationPage.selectValueFromDropDownList("Выпадающий список Продукт", product)
                .selectValueFromDropDownList("Выпадающий список Канал поступления", channel)
                .selectValueFromDropDownList("Выпадающий список Сегмент", segment)
                .selectValueFromDropDownList("Выпадающий список Форма подтверждения дохода", form)
                .selectValueFromDropDownList("Выпадающий список Статус клиента", status)
                .selectValueFromDropDownList("Выпадающий список Тип прозвона", callType)
                .clickOnElement("Кнопка Найти")
                .waitBusyCondition();
        if (id.equals("2652909")) {
            scriptConfigurationPage.clickOnCellFromTable("Таблица Результаты поиска", 1, 1)
                    .clickOnCellFromTable("Таблица Результаты поиска", 2, 1);
        } else {
            scriptConfigurationPage.clickOnCellFromTable("Таблица Результаты поиска", 1, 1);
        }
        scriptConfigurationPage.clickOnElement("Кнопка Удалить")
                .assertElementByTitleVisibility("Модальное окно Подтверждение удаления", "отображается")
                .clickOnElement("Кнопка Удалить (Подтверждение удаления)")
                .waitBusyCondition()
                .assertElementByTitleVisibility("Модальное окно Информация об ошибке", "отображается")
                .clickOnElement("Кнопка Ок (Информация об ошибке)");
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "2652910; Создание. Причина назначения: Критерий отправки на СЗ. Тип прозвона: Клиент; Комбинация PD и одобренного лимита; Потребительское кредитование; Интернет; Частичные зарплатные клиенты; 2-НДФЛ.Основное; Специалист; Клиент; Скрипт для мониторинга; -; 133; 134; Обязательно; Необязательно",
            "2652918; Создание. Причина назначения: Критерий отправки на СЗ. Тип прозвона: Работодатель; Наличие просрочки 180+ за последние Х месяцев; Потребительское кредитование; Офис; Новые зарплатные клиенты; 2-НДФЛ.Основное; Руководитель; Работодатель; Скрипт для мониторинга; ''; 64; 65; Необязательно; -",
            "2652917; Создание. Причина назначения: Результаты проверок. Тип прозвона: Конт. лицо; Доход по коду 2611.2-НДФЛ; Потребительское кредитование; DSA; Прочие клиенты; 3-НДФЛ.Основное; Учредитель; Контактное лицо; Другой скрипт для мониторинга; -; 24, 25; -; -; -;",
            "2652922; Создание. Причина назначения: Результаты проверок. Тип прозвона: Супруг (а); Негатив на работодателя в сети; Потребительское кредитование; Интернет; Прочие клиенты; Выписка из ПФР.Основное; Специалист; Супруг(а); Другой скрипт для мониторинга; -; 30, 31; -; -; -",
            "3973126; Успешное создание. Причина назначения: Результаты проверок+Критерий отправки на СЗ. Тип прозвона: Супруг (а); Наличие негативных статусов в КИ клиента; Потребительское кредитование; Интернет; Новые зарплатные клиенты; Справка по форме Банка/работодателя. Основное; Специалист; Супруг(а); Другой скрипт для мониторинга; -; 30, 31; -; -; -"
    })
    @Tag("common_script_modification_assignment_reason_2652910_2652918_2652917_2652922_3973126")
    @DisplayName("{id} - Мониторинг. Действия в системе. Общий скрипт. {displayName}")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void common_script_modification_assignment_reason_2652910_2652918_2652917_2652922_3973126(String id, String displayName, String assignmentReason, String product, String channel, String segment, String form, String status, String callType,
                                                                                                     String script, String script2, String addQuestion, String addQuestion2, String sitQuestion, String profQuestion) {
        loginPage.openMenuLinks("Управление настройками > Настройка скриптов")
                .goTo(scriptConfigurationPage)
                .clickOnElement("Вкладка Общий скрипт")
                .clickOnElement("Кнопка Создать")
                .assertElementByTitleVisibility("Модальное окно Создание общего скрипта", "отображается");
        if (id.equals("2652910") || id.equals("2652918")) {
            scriptConfigurationPage.selectValueFromDropDownList("Выпадающий список Причина назначения. Критерий отправки на СЗ (Создание общего скрипта)", assignmentReason);
        } else if (id.equals("3973126")) {
            scriptConfigurationPage.selectValueFromDropDownList("Выпадающий список Причина назначения. Критерий отправки на СЗ (Создание общего скрипта)", assignmentReason)
                    .selectValueFromDropDownList("Выпадающий список Причина назначения. Результаты проверок (Создание общего скрипта)", "Телефон не привязан");
        } else {
            scriptConfigurationPage.selectValueFromDropDownList("Выпадающий список Причина назначения. Результаты проверок (Создание общего скрипта)", assignmentReason);
        }
        scriptConfigurationPage.selectValueFromDropDownList("Выпадающий список Продукт (Создание общего скрипта)", product)
                .selectValueFromDropDownList("Выпадающий список Канал поступления (Создание общего скрипта)", channel)
                .selectValueFromDropDownList("Выпадающий список Сегмент (Создание общего скрипта)", segment)
                .selectValueFromDropDownList("Выпадающий список Форма подтверждения дохода (Создание общего скрипта)", form)
                .selectValueFromDropDownList("Выпадающий список Статус клиента (Создание общего скрипта)", status)
                .selectValueFromDropDownList("Выпадающий список Тип прозвона (Создание общего скрипта)", callType)
                .assertElementByTitleVisibility("Форма Тип прозвона", "отображается");
        if (id.equals("2652910")) {
            scriptConfigurationPage.selectValueFromDropDownList("Выпадающий список Основной скрипт (Тип прозвона)", "Скрипт для мониторинга", true)
                    .selectValueFromDropDownList("Выпадающий список Ситуационный вопрос (Тип прозвона)", "Обязательно")
                    .selectValueFromDropDownList("Выпадающий список Проф. вопрос (Звонок клиенту)", "Необязательно")
                    .selectValueFromDropDownList("Выпадающий список Доп. вопрос. Основное (Звонок клиенту)", "133. Опишите маршрут до работы (мониторинг)")
                    .selectValueFromDropDownList("Выпадающий список Доп. вопрос. Совместительство (Звонок клиенту)", "134. Сколько зарабатываешь? (мониторинг)");
        } else if (id.equals("2652918")) {
            scriptConfigurationPage.selectValueFromDropDownList("Выпадающий список Основной скрипт. Подтвержденный (Звонок работодателю)", "Скрипт для мониторинга", true)
                    .selectValueFromDropDownList("Выпадающий список Ситуационный вопрос (Тип прозвона)", "Необязательно")
                    .selectValueFromDropDownList("Выпадающий список Доп. вопрос. Подтвержденный (Звонок работодателю)", "64. Сколько сотрудников у Вас? (мониторинг)")
                    .selectValueFromDropDownList("Выпадающий список Доп. вопрос. Неподтвержденный (Звонок работодателю)", "65. Сколько этажей у центрального офиса? (мониторинг)");
        } else if (id.equals("2652917")) {
            scriptConfigurationPage.selectValueFromDropDownList("Выпадающий список Основной скрипт (Тип прозвона)", "Другой скрипт для мониторинга", true)
                    .selectValueFromDropDownList("Выпадающий список Доп. вопрос (Тип прозвона)", "24. Дайте характеристику на заёмщика (мониторинг)")
                    .clickOnElement("Кнопка Добавить вопрос у Доп. вопрос (Тип прозвона)")
                    .selectValueFromDropDownList("Выпадающий список Доп. вопрос 2 (Тип прозвона)", "25. Вся ли зарплата Вам приходит на карту? (мониторинг)");
        } else if (id.equals("2652922") || id.equals("3973126")) {
            scriptConfigurationPage.selectValueFromDropDownList("Выпадающий список Основной скрипт (Тип прозвона)", "Другой скрипт для мониторинга", true)
                    .selectValueFromDropDownList("Выпадающий список Доп. вопрос (Тип прозвона)", "30. Какой у Вас знак зодиака? (мониторинг)")
                    .clickOnElement("Кнопка Добавить вопрос у Доп. вопрос (Тип прозвона)")
                    .selectValueFromDropDownList("Выпадающий список Доп. вопрос 2 (Тип прозвона)", "31. Брали ли Вы кредит? (мониторинг)");
        }
        scriptConfigurationPage.selectValueFromDropDownList("Выпадающий список Текст подсказки (Тип прозвона)", "Если плохо отвечает, можно сбросить звонок (мониторинг)")
                .clickOnElement("Кнопка Сохранить (Создание общего скрипта)");
        String dateTimeNow = LocalDateTime.now().format(DF);
        goToActionsInSystemPage("Автоматическое Тестирование1", "", "Общий скрипт. Создание")
                .sortValuesInColumn("Время", "убыванию")
                .waitBusyCondition();
        String formIncomePart = id.equals("3973126") || id.equals("2652922") || id.equals("2652917") ? form + " " : form;
        String assignmentReasonPart = id.equals("3973126") ? assignmentReason + ", Телефон не привязан" : assignmentReason;
        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", List.of("Автоматическое Тестирование1")),
                Map.entry("ФИО пользователя (объект)", List.of("")),
                Map.entry("Время", List.of(dateTimeNow)),
                Map.entry("Тип события", List.of("Общий скрипт. Создание")),
                Map.entry("Описание события", List.of("Пользователь Автоматическое Тестирование1 создал общий скрипт с причиной назначения \"" + assignmentReasonPart + "\" с настройками: " +
                        "Причина назначения = \"" + assignmentReasonPart + "\" Продукт = \"" + product + "\" Канал = \"" + channel + "\" Сегмент = \"" + segment + "\" Форма подтверждения дохода = \"" + formIncomePart + "\" " +
                        "Статус клиента = \"" + status + "\" Тип прозвона = \"" + callType + "\" Основной скрипт = \"" + script + "\" Основной скрипт. Неподтвержденный = \"" + script2 + "\" Доп.вопрос = \"" + addQuestion + "\" Доп. вопрос. Неподтв./Совм. = \"" + addQuestion2 + "\" " +
                        "Ситуац. вопрос = \"" + sitQuestion + "\" Проф. вопрос = \"" + profQuestion + "\" Текст подсказки = \"Если плохо отвечает, можно сбросить звонок (мониторинг)\"")));
        checkTableMonitoring(expectedValues);
        actionsInSystemPage.clickOnElement("Кнопка Удалить все")
                .clickOnElement("Кнопка Сбросить сортировку")
                .openMenuLinks("Управление настройками > Настройка скриптов")
                .goTo(scriptConfigurationPage)
                .clickOnElement("Вкладка Общий скрипт")
                .selectValueFromDropDownList("Выпадающий список Причина назначения", assignmentReason)
                .selectValueFromDropDownList("Выпадающий список Продукт", product)
                .selectValueFromDropDownList("Выпадающий список Канал поступления", channel)
                .selectValueFromDropDownList("Выпадающий список Сегмент", segment)
                .selectValueFromDropDownList("Выпадающий список Форма подтверждения дохода", form)
                .selectValueFromDropDownList("Выпадающий список Статус клиента", status)
                .selectValueFromDropDownList("Выпадающий список Тип прозвона", callType)
                .clickOnElement("Кнопка Найти")
                .waitBusyCondition()
                .clickOnCellFromTable("Таблица Результаты поиска", 1, 1)
                .clickOnElement("Кнопка Удалить")
                .assertElementByTitleVisibility("Модальное окно Подтверждение удаления", "отображается")
                .clickOnElement("Кнопка Удалить (Подтверждение удаления)")
                .waitBusyCondition()
                .assertElementByTitleVisibility("Модальное окно Информация об ошибке", "отображается")
                .clickOnElement("Кнопка Ок (Информация об ошибке)");
    }

    @Test
    @Tag("common_script_deletion_type_of_call_contact_2652921")
    @DisplayName("2652921 - Мониторинг. Действия в системе. Общий скрипт. Удаление. Тип прозвона: Конт. лицо")
    @WorkItemIds({"2652921"})
    public void common_script_deletion_type_of_call_contact_2652921() {
        loginPage.openMenuLinks("Управление настройками > Настройка скриптов")
                .goTo(scriptConfigurationPage)
                .clickOnElement("Вкладка Общий скрипт")
                .clickOnElement("Кнопка Создать")
                .assertElementByTitleVisibility("Модальное окно Создание общего скрипта", "отображается")
                .selectValueFromDropDownList("Выпадающий список Причина назначения. Критерий отправки на СЗ (Создание общего скрипта)", "Отметка о рождении ребенка до 1,5 лет на момент обращения")
                .selectValueFromDropDownList("Выпадающий список Продукт (Создание общего скрипта)", "Потребительское кредитование")
                .selectValueFromDropDownList("Выпадающий список Канал поступления (Создание общего скрипта)", "DSA")
                .selectValueFromDropDownList("Выпадающий список Сегмент (Создание общего скрипта)", "Прочие клиенты")
                .selectValueFromDropDownList("Выпадающий список Форма подтверждения дохода (Создание общего скрипта)", "3-НДФЛ.Основное")
                .selectValueFromDropDownList("Выпадающий список Статус клиента (Создание общего скрипта)", "Учредитель")
                .selectValueFromDropDownList("Выпадающий список Тип прозвона (Создание общего скрипта)", "Контактное лицо")
                .assertElementByTitleVisibility("Форма Тип прозвона", "отображается")
                .selectValueFromDropDownList("Выпадающий список Основной скрипт (Тип прозвона)", "Скрипт для мониторинга", true)
                .selectValueFromDropDownList("Выпадающий список Доп. вопрос (Тип прозвона)", "25. Вся ли зарплата Вам приходит на карту? (мониторинг)")
                .selectValueFromDropDownList("Выпадающий список Текст подсказки (Тип прозвона)", "Если плохо отвечает, можно сбросить звонок (мониторинг)")
                .clickOnElement("Кнопка Сохранить (Создание общего скрипта)")
                .selectValueFromDropDownList("Выпадающий список Причина назначения", "Отметка о рождении ребенка до 1,5 лет на момент обращения")
                .selectValueFromDropDownList("Выпадающий список Продукт", "Потребительское кредитование")
                .selectValueFromDropDownList("Выпадающий список Канал поступления", "DSA")
                .selectValueFromDropDownList("Выпадающий список Сегмент", "Прочие клиенты")
                .selectValueFromDropDownList("Выпадающий список Форма подтверждения дохода", "3-НДФЛ.Основное")
                .selectValueFromDropDownList("Выпадающий список Статус клиента", "Учредитель")
                .selectValueFromDropDownList("Выпадающий список Тип прозвона", "Контактное лицо")
                .clickOnElement("Кнопка Найти")
                .waitBusyCondition()
                .clickOnCellFromTable("Таблица Результаты поиска", 1, 1)
                .clickOnElement("Кнопка Удалить")
                .assertElementByTitleVisibility("Модальное окно Подтверждение удаления", "отображается")
                .clickOnElement("Кнопка Удалить (Подтверждение удаления)")
                .assertElementByTitleVisibility("Модальное окно Информация об ошибке", "отображается")
                .clickOnElement("Кнопка Ок (Информация об ошибке)");
        String dateTimeNow = LocalDateTime.now().format(DF);
        goToActionsInSystemPage("Автоматическое Тестирование1", "", "Общий скрипт. Удаление")
                .sortValuesInColumn("Время", "убыванию")
                .waitBusyCondition();
        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", List.of("Автоматическое Тестирование1")),
                Map.entry("ФИО пользователя (объект)", List.of("")),
                Map.entry("Время", List.of(dateTimeNow)),
                Map.entry("Тип события", List.of("Общий скрипт. Удаление")),
                Map.entry("Описание события", List.of("Пользователь Автоматическое Тестирование1 удалил общий скрипт с причиной назначения \"Отметка о рождении ребенка до 1,5 лет на момент обращения\"")));
        checkTableMonitoring(expectedValues);
        actionsInSystemPage.clickOnElement("Кнопка Удалить все")
                .clickOnElement("Кнопка Сбросить сортировку");
    }

    @Test
    @Tag("opening_report_actions_with_claims_1723875")
    @DisplayName("1723875 - Мониторинг. Действия в системе. Открытие отчета «Действия с заявками»")
    @WorkItemIds({"1723875"})
    public void opening_report_actions_with_claims_1723875() {
        loginPage.openMenuLinks("Мониторинг > Действия с заявками");
        String dateTimeNow = LocalDateTime.now().format(DF);
        goToActionsInSystemPage("Автоматическое Тестирование1", "", "Открытие отчета «Действия с заявками»")
                .sortValuesInColumn("Время", "убыванию")
                .waitBusyCondition();
        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", List.of("Автоматическое Тестирование1")),
                Map.entry("ФИО пользователя (объект)", List.of("")),
                Map.entry("Время", List.of(dateTimeNow)),
                Map.entry("Тип события", List.of("Открытие отчета «Действия с заявками»")),
                Map.entry("Описание события", List.of("Пользователь Автоматическое Тестирование1 открыл отчет «Действия с заявками»")));
        checkTableMonitoring(expectedValues);
        actionsInSystemPage.clickOnElement("Кнопка Удалить все")
                .clickOnElement("Кнопка Сбросить сортировку");
    }

    // todo Не работает из-за бага. Баг заведен https://alm.headoffice.psbank.local/sd/operator/#uuid:GMtask$147084539
    @Test
    @Tag("creating_new_options_priority_setting_1723901")
    @DisplayName("1723901 - Мониторинг. Действия в системе. Создание новой настройки приоритетов")
    @WorkItemIds({"1723901"})
    public void creating_new_options_priority_setting_1723901() {
        loginPage.openMenuLinks("Настройки > Настройки приоритетов")
                .goTo(prioritySettingsPage)
                .waitBusyCondition()
                .clickOnElement("Кнопка Добавить")
                .assertElementByTitleVisibility("Модальное окно Настройки приоритетов", "отображается")
                .clickOnElement("Иконка Переключение редактирования поля (Настройки приоритетов)")
                .fillInput("Поле ввода Название настройки приоритетов (Настройки приоритетов)", "Тест_мониторинг(АТ_не трогать)")
                .selectValueFromDropDownList("Выпадающий список Действует (Настройки приоритетов)", "Нет")
                .selectValueFromDropDownList("Выпадающий список Этап обработки (Настройки приоритетов)", "Андеррайтинг")
                .selectValueFromDropDownList("Выпадающий список Сортировка настройки приоритета (Настройки приоритетов)", "Время захода на этап ручной проверки Изначальное")
                .selectValueFromDropDownList("Выпадающий список Тип сортировки (Настройки приоритетов)", "Убывание")
                .selectValueFromDropDownList("Выпадающий список Первый у условия (Настройки приоритетов)", "Вид кредита")
                .selectValueFromDropDownList("Выпадающий список Второй у условия (Настройки приоритетов)", "Равно")
                .selectValueFromDropDownList("Выпадающий список Третий у условия (Настройки приоритетов)", "Единый кредитный лимит")
                .selectValueFromDropDownList("Выпадающий список Первый у сортировки (Настройки приоритетов)", "Время захода на этап ручной проверки Изначальное")
                .selectValueFromDropDownList("Выпадающий список Второй у сортировки (Настройки приоритетов)", "Убывание")
                .clickOnElement("Кнопка Сохранить (Настройки приоритетов)");
        String dateTimeNow = LocalDateTime.now().format(DF);
        goToActionsInSystemPage("Автоматическое Тестирование1", "", "Создание новой настройки приоритетов")
                .sortValuesInColumn("Время", "убыванию")
                .waitBusyCondition();
        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", List.of("Автоматическое Тестирование1")),
                Map.entry("ФИО пользователя (объект)", List.of("")),
                Map.entry("Время", List.of(dateTimeNow)),
                Map.entry("Тип события", List.of("Создание новой настройки приоритетов")),
                Map.entry("Описание события", List.of("Пользователь Автоматическое Тестирование1 добавил настройку приоритетов Тест_мониторинг(АТ_не трогать) для этапа Андеррайтинг")));
        checkTableMonitoring(expectedValues);
        actionsInSystemPage.clickOnElement("Кнопка Удалить все")
                .clickOnElement("Кнопка Сбросить сортировку")
                .openMenuLinks("Настройки > Настройки приоритетов")
                .goTo(prioritySettingsPage)
                .clickOnCheckboxNearName("Тест_мониторинг(АТ_не трогать)")
                .clickOnElement("Кнопка Удалить");
    }

    @Test
    @Tag("main_script_deletion_error_2652919")
    @DisplayName("2652919 - Мониторинг. Действия в системе. Основной скрипт. Ошибка  удаления")
    @WorkItemIds({"2652919"})
    public void main_script_deletion_error_2652919() {
        loginPage.openMenuLinks("Управление настройками > Настройка скриптов")
                .goTo(scriptConfigurationPage)
                .clickOnElement("Вкладка Основной скрипт")
                .fillInput("Поле ввода Наименование скрипта", "Просто скрипт")
                .selectValueFromDropDownList("Выпадающий список Категория скрипта", "LIGHT")
                .clickOnElement("Кнопка Найти")
                .waitBusyCondition()
                .clickOnCellFromTable("Таблица Результаты поиска", 1, 1)
                .clickOnElement("Кнопка Удалить")
                .assertElementByTitleVisibility("Модальное окно Подтверждение удаления", "отображается")
                .clickOnElement("Кнопка Удалить (Подтверждение удаления)");
        String dateTimeNow = LocalDateTime.now().format(DF);
        scriptConfigurationPage.assertElementByTitleVisibility("Модальное окно Информация об ошибке", "отображается")
                .checkElementByTitleContains("Модальное окно Информация об ошибке", "Выбранный скрипт используется в настройке Общего скрипта. Для удаления, внесите изменения в Общий скрипт!")
                .clickOnElement("Кнопка Ок (Информация об ошибке)");
        goToActionsInSystemPage("Автоматическое Тестирование1", "", "Основной скрипт. Ошибка удаления")
                .sortValuesInColumn("Время", "убыванию")
                .waitBusyCondition();
        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", List.of("Автоматическое Тестирование1")),
                Map.entry("ФИО пользователя (объект)", List.of("")),
                Map.entry("Время", List.of(dateTimeNow)),
                Map.entry("Тип события", List.of("Основной скрипт. Ошибка удаления")),
                Map.entry("Описание события", List.of("Ошибка удаления основного скрипта \"Просто скрипт\".")));
        checkTableMonitoring(expectedValues);
        actionsInSystemPage.clickOnElement("Кнопка Удалить все")
                .clickOnElement("Кнопка Сбросить сортировку");
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "3947442; \"Категория скрипта\"; STANDARD; . Как я могу к Вам обращаться?",
            "3973135; \"Текст скрипта\"; LIGHT; .",
    })
    @Tag("assignment_strategies_3468868_3468866")
    @DisplayName("{id} - Мониторинг. Действия в системе. Основной скрипт. Редактирование поля {displayName}")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void main_script_modifying_block_3947442_3973135(String id, String displayName, String category, String text) {
        clearTableFromMainScript();
        loginPage.openMenuLinks("Управление настройками > Настройка скриптов")
                .goTo(scriptConfigurationPage)
                .clickOnElement("Вкладка Основной скрипт")
                .clickOnElement("Кнопка Создать")
                .assertElementByTitleVisibility("Модальное окно Создание основного скрипта", "отображается")
                .fillInput("Выпадающий список Текст скрипта (Создание основного скрипта)", "Добрый день! Звонок из")
                .selectValueFromDropDownList("Выпадающий список Текст скрипта (Создание основного скрипта)", "Добрый день! Звонок из Промсвязьбанка.")
                .clickOnElement("Кнопка Добавить (Создание основного скрипта)")
                .fillInput("Выпадающий список Текст скрипта 2 (Создание основного скрипта)", "Как я могу к Вам")
                .selectValueFromDropDownList("Выпадающий список Текст скрипта 2 (Создание основного скрипта)", "Как я могу к Вам обращаться?")
                .selectValueFromDropDownList("Выпадающий список Категория скрипта (Создание основного скрипта)", "Light")
                .fillInput("Поле ввода Наименование скрипта (Создание основного скрипта)", "Тест_мониторинг(АТ_не трогать)")
                .clickOnElement("Кнопка Сохранить (Создание основного скрипта)")
                .fillInput("Поле ввода Наименование скрипта", "Тест_мониторинг(АТ_не трогать)")
                .clickOnElement("Кнопка Найти")
                .waitBusyCondition()
                .clickOnCellFromTable("Таблица Результаты поиска", 1, 2)
                .assertElementByTitleVisibility("Модальное окно Создание основного скрипта", "отображается");
        if (id.equals("3947442")) {
            scriptConfigurationPage.selectValueFromDropDownList("Выпадающий список Категория скрипта (Создание основного скрипта)", "Standard");
        } else {
            scriptConfigurationPage.clickOnCrossNearTextBlock("Модальное окно Создание основного скрипта", "Текст скрипта", "2")
                    .clickOnCrossNearTextBlock("Модальное окно Создание основного скрипта", "Текст скрипта", "2");
        }
        scriptConfigurationPage.clickOnElement("Кнопка Сохранить (Создание основного скрипта)");
        String dateTimeNow = LocalDateTime.now().format(DF);
        goToActionsInSystemPage("Автоматическое Тестирование1", "", "Основной скрипт. Редактирование")
                .sortValuesInColumn("Время", "убыванию")
                .waitBusyCondition();
        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", List.of("Автоматическое Тестирование1")),
                Map.entry("ФИО пользователя (объект)", List.of("")),
                Map.entry("Время", List.of(dateTimeNow)),
                Map.entry("Тип события", List.of("Основной скрипт. Редактирование")),
                Map.entry("Описание события", List.of("Пользователь Автоматическое Тестирование1 отредактировал основной скрипт \"Тест_мониторинг(АТ_не трогать)\". " +
                        "Старое значение: Наименование скрипта = \"Тест_мониторинг(АТ_не трогать) \"Категория скрипта = \"Light\" Текст скрипта = \"Добрый день! Звонок из Промсвязьбанка. Как я могу к Вам обращаться?\". " +
                        "Новое значение: Наименование скрипта = \"Тест_мониторинг(АТ_не трогать)\" Категория скрипта = \"" + category + "\" Текст скрипта = \"Добрый день! Звонок из Промсвязьбанка" + text + "\"")));
        checkTableMonitoring(expectedValues);
        actionsInSystemPage.clickOnElement("Кнопка Удалить все")
                .clickOnElement("Кнопка Сбросить сортировку");
        clearTableFromMainScript();
    }

    @Test
    @Tag("changing_user_status_1723895")
    @DisplayName("1723895 - Мониторинг. Действия в системе. Изменился статус пользователя \"В системе\"")
    @WorkItemIds({"1723895"})
    public void changing_user_status_1723895() {
        loginPage.clickOnElement("Кнопка выхода")
                .openAuthorizationPage()
                .loginViaUi();
        String dateTimeNow = LocalDateTime.now().format(DF);
        loginPage.waitBusyCondition();
        goToActionsInSystemPage("", "Автоматическое Тестирование1", "Изменился статус пользователя \"В системе\"")
                .sortValuesInColumn("Время", "убыванию")
                .waitBusyCondition();
        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", List.of("Система")),
                Map.entry("ФИО пользователя (объект)", List.of("Автоматическое Тестирование1")),
                Map.entry("Время", List.of(dateTimeNow)),
                Map.entry("Тип события", List.of("Изменился статус пользователя \"В системе\"")),
                Map.entry("Описание события", List.of("Статус пользователя Автоматическое Тестирование1 изменился c \"Вне системы\" на \"В системе\"")));
        checkTableMonitoring(expectedValues);
        actionsInSystemPage.clickOnElement("Кнопка Удалить все")
                .clickOnElement("Кнопка Сбросить сортировку");
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "1723873; Привязка шаблона к пользователю; привязал к сотруднику",
            "1723899; Отвязка шаблона от пользователя; отвязал от сотрудника"
    })
    @Tag("employee_card_uncoupling_template_1723899_1723873")
    @DisplayName("{id} - Мониторинг. Действия в системе. Карточка сотрудника.{displayName} пользователю")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void employee_card_uncoupling_template_1723899_1723873(String id, String displayName, String description) {
        loginPage.openMenuLinks("Сотрудники > Список сотрудников")
                .goTo(listEmployeesPage)
                .waitBusyCondition()
                .clickOnElement("Кнопка фильтра ФИО")
                .fillInput("Поле ФИО сотрудника (модальное окно Поиск сотрудника)", "Автоматическое Тестирование5")
                .clickOnElement("Чек-бокс Сотрудник (модальное окно Поиск сотрудника)")
                .clickOnElement("Кнпока Добавить (модальное окно Поиск сотрудника)")
                .clickOnElement("Кнопка Найти")
                .waitBusyCondition()
                .clickOnCellFromTable("Таблица Список сотрудников", 1, 2)
                .waitBusyCondition()
                .switchToNewTab()
                .goTo(cardEmployeePage)
                .waitBusyCondition()
                .clickOnElement("Вкладка Роли / Шаблоны")
                .openListViaClick("Блок Список шаблонов роли", "Рассмотрение заявок (Процессная)");
        if (cardEmployeePage.selectTemplateNoStep("Блок Список шаблонов роли", "Тест_мониторинг(АТ_НЕ трогать!)") != null) {
            cardEmployeePage.clickOnElement("Кнопка Отвязать")
                    .openListViaClick("Блок Список шаблонов роли (Привязка шаблонов)", "Рассмотрение заявок (Процессная)")
                    .selectTemplate("Блок Список шаблонов роли (Привязка шаблонов)", "Тест_мониторинг(АТ_НЕ трогать!)")
                    .clickOnElement("Кнопка Отвязать (Отвязка шаблонов)");
        }
        cardEmployeePage.clickOnElement("Вкладка Роли / Шаблоны")
                .clickOnElement("Кнопка Привязать")
                .openListViaClick("Блок Список шаблонов роли (Привязка шаблонов)", "Рассмотрение заявок (Процессная)")
                .selectTemplate("Блок Список шаблонов роли (Привязка шаблонов)", "Тест_мониторинг(АТ_НЕ трогать!)")
                .clickOnElement("Кнопка Привязать (Привязка шаблонов)");
        if (id.equals("1723899")) {
            cardEmployeePage.waitBusyCondition()
                    .clickOnElement("Кнопка Отвязать")
                    .openListViaClick("Блок Список шаблонов роли (Привязка шаблонов)", "Рассмотрение заявок (Процессная)")
                    .selectTemplate("Блок Список шаблонов роли (Привязка шаблонов)", "Тест_мониторинг(АТ_НЕ трогать!)")
                    .clickOnElement("Кнопка Отвязать (Отвязка шаблонов)");
        }
        String dateTimeNow = LocalDateTime.now().format(DF);
        cardEmployeePage.closeCurrentTab()
                .switchToOneTab()
                .waitBusyCondition();
        goToActionsInSystemPage("Автоматическое Тестирование1", "", "Карточка сотрудника. " + displayName)
                .sortValuesInColumn("Время", "убыванию")
                .waitBusyCondition();
        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", List.of("Автоматическое Тестирование1")),
                Map.entry("ФИО пользователя (объект)", List.of("Автоматическое Тестирование5")),
                Map.entry("Время", List.of(dateTimeNow)),
                Map.entry("Тип события", List.of("Карточка сотрудника. " + displayName)),
                Map.entry("Описание события", List.of("Пользователь Автоматическое Тестирование1 " + description + " Автоматическое Тестирование5 шаблон \"Тест_мониторинг(АТ_НЕ трогать!)\", " +
                        "созданный на основании роли \"Рассмотрение заявок \" с типом \"Процессная\", с разрешениями: \"Заявки в обработке. Вкладка 'Дополнительная информация'. Просмотр\"")));
        checkTableMonitoring(expectedValues);
        actionsInSystemPage.clickOnElement("Кнопка Удалить все")
                .clickOnElement("Кнопка Сбросить сортировку");
        if (id.equals("1723873")) {
            actionsInSystemPage.openMenuLinks("Сотрудники > Список сотрудников")
                    .goTo(listEmployeesPage)
                    .waitBusyCondition()
                    .clickOnElement("Кнопка фильтра ФИО")
                    .fillInput("Поле ФИО сотрудника (модальное окно Поиск сотрудника)", "Автоматическое Тестирование5")
                    .clickOnElement("Чек-бокс Сотрудник (модальное окно Поиск сотрудника)")
                    .clickOnElement("Кнпока Добавить (модальное окно Поиск сотрудника)")
                    .clickOnElement("Кнопка Найти")
                    .waitBusyCondition()
                    .clickOnCellFromTable("Таблица Список сотрудников", 1, 2)
                    .waitBusyCondition()
                    .switchToNewTab()
                    .goTo(cardEmployeePage)
                    .waitBusyCondition()
                    .clickOnElement("Вкладка Роли / Шаблоны")
                    .clickOnElement("Кнопка Отвязать")
                    .waitBusyCondition()
                    .openListViaClick("Блок Список шаблонов роли (Привязка шаблонов)", "Рассмотрение заявок (Процессная)")
                    .selectTemplate("Блок Список шаблонов роли (Привязка шаблонов)", "Тест_мониторинг(АТ_НЕ трогать!)")
                    .clickOnElement("Кнопка Отвязать (Отвязка шаблонов)")
                    .closeCurrentTab()
                    .switchToOneTab();
        }
    }

    @Test
    @Tag("employee_card_establishing_options_1723879")
    @DisplayName("1723879 - Мониторинг. Действия в системе. Карточка сотрудника.Установка параметров разрешения")
    @WorkItemIds({"1723879"})
    public void employee_card_establishing_options_1723879() {
        loginPage.openMenuLinks("Сотрудники > Список сотрудников")
                .goTo(listEmployeesPage)
                .waitBusyCondition()
                .clickOnElement("Кнопка фильтра ФИО")
                .fillInput("Поле ФИО сотрудника (модальное окно Поиск сотрудника)", "Автоматическое Тестирование5")
                .clickOnElement("Чек-бокс Сотрудник (модальное окно Поиск сотрудника)")
                .clickOnElement("Кнпока Добавить (модальное окно Поиск сотрудника)")
                .clickOnElement("Кнопка Найти")
                .waitBusyCondition()
                .clickOnCellFromTable("Таблица Список сотрудников", 1, 2)
                .switchToNewTab()
                .goTo(cardEmployeePage)
                .waitBusyCondition()
                .clickOnElement("Вкладка Роли / Шаблоны")
                .waitBusyCondition()
                .openListViaClick("Блок Список шаблонов роли", "Сотрудник ГО (Функциональная)")
                .selectTemplate("Блок Список шаблонов роли", "Все Максимум ГО")
                .fillInput("Строка поиска по наименованию Разрешения", "Графики работы. Изменение")
                .clickOnElement("Кнопка Найти(блок Название шаблона)")
                .doubleClickByText("Параметры")
                .assertElementByTitleVisibility("Модальное окно Параметры разрешения", "отображается")
                .selectUnit("Автоматически")
                .clickOnElement("Кнопка Сохранить (Параметры разрешения)")
                .waitBusyCondition();
        String dateTimeNow = LocalDateTime.now().format(DF);
        cardEmployeePage.closeCurrentTab()
                .switchToOneTab()
                .waitBusyCondition();
        goToActionsInSystemPage("Автоматическое Тестирование1", "", "Карточка сотрудника.Установка параметров разрешения")
                .sortValuesInColumn("Время", "убыванию")
                .waitBusyCondition();
        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", List.of("Автоматическое Тестирование1")),
                Map.entry("ФИО пользователя (объект)", List.of("Автоматическое Тестирование5")),
                Map.entry("Время", List.of(dateTimeNow)),
                Map.entry("Тип события", List.of("Карточка сотрудника.Установка параметров разрешения")),
                Map.entry("Описание события", List.of("Пользователь Автоматическое Тестирование1 установил в карточке сотрудника Автоматическое Тестирование5 в шаблоне \"Все Максимум ГО\", " +
                        "созданном на основании роли \"Сотрудник ГО\" с типом \"Функциональная\", параметры разрешения \"Графики работы. Изменение\": Подразделения: Автоматически")));
        checkTableMonitoring(expectedValues);
        actionsInSystemPage.clickOnElement("Кнопка Удалить все")
                .clickOnElement("Кнопка Сбросить сортировку")
                .openMenuLinks("Сотрудники > Список сотрудников")
                .goTo(listEmployeesPage)
                .waitBusyCondition()
                .clickOnElement("Кнопка фильтра ФИО")
                .fillInput("Поле ФИО сотрудника (модальное окно Поиск сотрудника)", "Автоматическое Тестирование5")
                .clickOnElement("Чек-бокс Сотрудник (модальное окно Поиск сотрудника)")
                .clickOnElement("Кнпока Добавить (модальное окно Поиск сотрудника)")
                .clickOnElement("Кнопка Найти")
                .waitBusyCondition()
                .clickOnCellFromTable("Таблица Список сотрудников", 1, 2)
                .switchToNewTab()
                .goTo(cardEmployeePage)
                .waitBusyCondition()
                .clickOnElement("Вкладка Роли / Шаблоны")
                .openListViaClick("Блок Список шаблонов роли", "Сотрудник ГО (Функциональная)")
                .selectTemplate("Блок Список шаблонов роли", "Все Максимум ГО")
                .fillInput("Строка поиска по наименованию Разрешения", "Графики работы. Изменение")
                .clickOnElement("Кнопка Найти(блок Название шаблона)")
                .doubleClickByText("Параметры")
                .assertElementByTitleVisibility("Модальное окно Параметры разрешения", "отображается")
                .selectUnit("Без ограничений")
                .clickOnElement("Кнопка Сохранить (Параметры разрешения)")
                .closeCurrentTab()
                .switchToOneTab()
                .waitBusyCondition();
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "1723888; Назначение; назначил пользователю",
            "1723881; Отзыв; отозвал у пользователя"
    })
    @Tag("process_functions_1723888_1723881")
    @DisplayName("{id} - Мониторинг. Действия в системе. {displayName} процессных функций")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void process_functions_1723888_1723881(String id, String displayName, String description) {
        loginPage.openMenuLinks("Сотрудники > Список сотрудников")
                .goTo(listEmployeesPage)
                .waitBusyCondition()
                .clickOnElement("Кнопка фильтра ФИО")
                .fillInput("Поле ФИО сотрудника (модальное окно Поиск сотрудника)", "Автоматическое Тестирование5")
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
        String s = cardEmployeePage.getTextByElementTitle("Блок Функции (Назначение процессной функции)");
        if (!s.contains("ФССП")) {
            cardEmployeePage.selectValueFromDropDownList("Выпадающий список Название функции (Назначение процессной функции)", "ФССП")
                    .clickOnElement("Кнопка Добавить (Назначение процессной функции)")
                    .clickOnElement("Кнопка Сохранить (Назначение процессной функции)")
                    .waitBusyCondition()
                    .clickOnElement("Процессная функция - Редактировать");
        }
        cardEmployeePage.deleteFunctionFromList("ФССП")
                .clickOnElement("Кнопка Сохранить (Назначение процессной функции)")
                .waitBusyCondition();
        if (id.equals("1723888")) {
            cardEmployeePage.clickOnElement("Процессная функция - Редактировать")
                    .assertElementByTitleVisibility("Модальное окно Назначение процессной функции", "отображается")
                    .selectValueFromDropDownList("Выпадающий список Название функции (Назначение процессной функции)", "ФССП")
                    .clickOnElement("Кнопка Добавить (Назначение процессной функции)")
                    .clickOnElement("Кнопка Сохранить (Назначение процессной функции)")
                    .waitBusyCondition();
        }
        String dateTimeNow = LocalDateTime.now().format(DF);
        cardEmployeePage.closeCurrentTab()
                .switchToOneTab()
                .waitBusyCondition();
        goToActionsInSystemPage("Автоматическое Тестирование1", "", displayName + " процессных функций")
                .sortValuesInColumn("Время", "убыванию")
                .waitBusyCondition();
        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", List.of("Автоматическое Тестирование1")),
                Map.entry("ФИО пользователя (объект)", List.of("Автоматическое Тестирование5")),
                Map.entry("Время", List.of(dateTimeNow)),
                Map.entry("Тип события", List.of(displayName + " процессных функций")),
                Map.entry("Описание события", List.of("Пользователь Автоматическое Тестирование1 " + description + " Автоматическое Тестирование5 процессные функции: ФССП")));
        checkTableMonitoring(expectedValues);
        actionsInSystemPage.clickOnElement("Кнопка Удалить все")
                .clickOnElement("Кнопка Сбросить сортировку");
        if (id.equals("1723881")) {
            cardEmployeePage.openMenuLinks("Сотрудники > Список сотрудников")
                    .goTo(listEmployeesPage)
                    .waitBusyCondition()
                    .clickOnElement("Кнопка фильтра ФИО")
                    .fillInput("Поле ФИО сотрудника (модальное окно Поиск сотрудника)", "Автоматическое Тестирование5")
                    .clickOnElement("Чек-бокс Сотрудник (модальное окно Поиск сотрудника)")
                    .clickOnElement("Кнпока Добавить (модальное окно Поиск сотрудника)")
                    .clickOnElement("Кнопка Найти")
                    .waitBusyCondition()
                    .clickOnCellFromTable("Таблица Список сотрудников", 1, 2)
                    .switchToNewTab()
                    .goTo(cardEmployeePage)
                    .waitBusyCondition()
                    .clickOnElement("Процессная функция - Редактировать")
                    .assertElementByTitleVisibility("Модальное окно Назначение процессной функции", "отображается")
                    .selectValueFromDropDownList("Выпадающий список Название функции (Назначение процессной функции)", "ФССП")
                    .clickOnElement("Кнопка Добавить (Назначение процессной функции)")
                    .clickOnElement("Кнопка Сохранить (Назначение процессной функции)")
                    .waitBusyCondition()
                    .closeCurrentTab()
                    .switchToNewTab();
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "3468860; сразу",
            "3468875; позже",
    })
    @Tag("assignment_strategies_publish_version_3468875_3468860")
    @DisplayName("{id} - Мониторинг. Действия в системе. Назначение стратегий. Публикация версии - опубликовать {displayName}")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void assignment_strategies_publish_version_3468875_3468860(String id, String displayName) {
        loginPage.openMenuLinks("Управление настройками > Настройки назначения стратегий")
                .goTo(assignmentStrategyConfiguration)
                .clickOnElement("Вкладка Назначение стратегий")
                .clickOnElement("Вкладка Редактируемая версия")
                .clickOnElement("Кнопка Опубликовать версию")
                .assertElementByTitleVisibility("Модальное окно Опубликовать версию", "отображается");
        String specialDate = "";
        if (id.equals("3468860")) {
            assignmentStrategyConfiguration.clickOnElement("Переключатель Опубликовать сразу (Опубликовать версию)")
                    .assertElementByTitleActivity("Кнопка Опубликовать (Опубликовать версию)", "активен");
        } else {
            specialDate = LocalDateTime.now().plusMinutes(3).withSecond(0).format(df_with_seconds);
            assignmentStrategyConfiguration.fillInput("Поле ввода Опубликовать позже (Опубликовать версию)", specialDate);
        }
        assignmentStrategyConfiguration.clickOnElement("Кнопка Опубликовать (Опубликовать версию)")
                .assertElementByTitleVisibility("Модальное окно Подтверждение публикации", "отображается")
                .clickOnElement("Кнопка Опубликовать (Подтверждение публикации)");
        if (id.equals("3468860")) {
            assignmentStrategyConfiguration.waitText(5, "Справочник \"Назначение стратегий\" опубликован");
        } else {
            assignmentStrategyConfiguration.clickOnElement("Вкладка Редактируемая версия")
                    .waitText(5, "Опубликовать: " + specialDate);
        }
        String dateTimeNow = LocalDateTime.now().format(DF);
        goToActionsInSystemPage("Автоматическое Тестирование1", "", "Назначение стратегий. Публикация версии")
                .sortValuesInColumn("Время", "убыванию")
                .waitBusyCondition();
        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", List.of("Автоматическое Тестирование1")),
                Map.entry("ФИО пользователя (объект)", List.of("")),
                Map.entry("Время", List.of(dateTimeNow)),
                Map.entry("Тип события", List.of("Назначение стратегий. Публикация версии")),
                Map.entry("Описание события", List.of("Пользователь Автоматическое Тестирование1 опубликовал версию. Опубликовать сразу: " +
                        (id.equals("3468860") ? "да" : "нет") + " Опубликовать позже: " + (id.equals("3468860") ? "" : specialDate))));
        checkTableMonitoring(expectedValues);
        actionsInSystemPage.clickOnElement("Кнопка Удалить все")
                .clickOnElement("Кнопка Сбросить сортировку");
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "3468869; по кнопке \"Редактировать\" (Карандаш)",
            "3468874; через чек-бокс и кнопку \"Редактировать\"",
    })
    @Tag("assignment_strategies_modification_3468869_3468874")
    @DisplayName("{id} - Мониторинг. Действия в системе. Назначение стратегий. Редактирование ({displayName})")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void assignment_strategies_modification_3468869_3468874(String id, String displayName) {
        fillFiltersAndCheckStrategy().clickOnElement("Кнопка Создать")
                .assertElementByTitleVisibility("Модальное окно Создание правила назначения стратегии", "отображается")
                .selectValueFromDropDownList("Выпадающий список Причина назначения (Создание правила назначения стратегии)", "Нет кредитов в КИ")
                .selectValueFromDropDownList("Выпадающий список Продукт (Создание правила назначения стратегии)", "Потребительское кредитование")
                .selectValueFromDropDownList("Выпадающий список Сегмент (Создание правила назначения стратегии)", "Новые зарплатные клиенты")
                .clickOnElement("Переключатель ФССП (Создание правила назначения стратегии)")
                .clickOnElement("Кнопка Сохранить (Создание правила назначения стратегии)")
                .waitBusyCondition();
        if (id.equals("3468869")) {
            assignmentStrategyConfiguration.clickOnCellFromTable("Таблица Результаты поиска", 1, 2)
                    .assertElementByTitleVisibility("Модальное окно Создание правила назначения стратегии", "отображается")
                    .clickOnElement("Переключатель Проверка сайта (Создание правила назначения стратегии)");
        } else {
            assignmentStrategyConfiguration.clickOnCellFromTable("Таблица Результаты поиска", 1, 1)
                    .clickOnElement("Кнопка Редактировать")
                    .assertElementByTitleVisibility("Модальное окно Создание правила назначения стратегии", "отображается")
                    .selectValueFromDropDownList("Выпадающий список Стратегия для назначения (Создание правила назначения стратегии)", "Прозвон клиента");
        }
        assignmentStrategyConfiguration.clickOnElement("Кнопка Сохранить (Создание правила назначения стратегии)")
                .clickOnElement("Кнопка Удалить все");
        String dateTimeNow = LocalDateTime.now().format(DF);
        goToActionsInSystemPage("Автоматическое Тестирование1", "", "Назначение стратегий. Редактирование")
                .sortValuesInColumn("Время", "убыванию")
                .waitBusyCondition();
        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", List.of("Автоматическое Тестирование1")),
                Map.entry("ФИО пользователя (объект)", List.of("")),
                Map.entry("Время", List.of(dateTimeNow)),
                Map.entry("Тип события", List.of("Назначение стратегий. Редактирование")),
                Map.entry("Описание события", List.of("Пользователь Автоматическое Тестирование1 отредактировал правило назначения для " +
                        "причины назначения \"Нет кредитов в КИ\" с продуктом \"Потребительское кредитование\" и сегментом \"Новые зарплатные клиенты\"")));
        checkTableMonitoring(expectedValues);
        actionsInSystemPage.clickOnElement("Кнопка Удалить все")
                .clickOnElement("Кнопка Сбросить сортировку");
        fillFiltersAndCheckStrategy().clickOnElement("Кнопка Удалить все");
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "3468868; Создание; создал",
            "3468866; Удаление; удалил",
    })
    @Tag("assignment_strategies_3468868_3468866")
    @DisplayName("{id} - Мониторинг. Действия в системе. Назначение стратегий. {displayName}")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void assignment_strategies_3468868_3468866(String id, String displayName, String description) {
        fillFiltersAndCheckStrategy().clickOnElement("Кнопка Создать")
                .assertElementByTitleVisibility("Модальное окно Создание правила назначения стратегии", "отображается")
                .selectValueFromDropDownList("Выпадающий список Причина назначения (Создание правила назначения стратегии)", "Нет кредитов в КИ")
                .selectValueFromDropDownList("Выпадающий список Продукт (Создание правила назначения стратегии)", "Потребительское кредитование")
                .selectValueFromDropDownList("Выпадающий список Сегмент (Создание правила назначения стратегии)", "Новые зарплатные клиенты")
                .clickOnElement("Переключатель ФССП (Создание правила назначения стратегии)")
                .clickOnElement("Кнопка Сохранить (Создание правила назначения стратегии)")
                .waitBusyCondition();
        if (id.equals("3468866")) {
            assignmentStrategyConfiguration.clickOnCellFromTable("Таблица Результаты поиска", 1, 1)
                    .clickOnElement("Кнопка Удалить")
                    .assertElementByTitleVisibility("Модальное окно Подтверждение удаления", "отображается")
                    .clickOnElement("Кнопка Удалить (Подтверждение удаления)")
                    .assertElementByTitleVisibility("Модальное окно Информация об ошибке", "отображается")
                    .clickOnElement("Кнопка Ок (Информация об ошибке)");
        }
        assignmentStrategyConfiguration.clickOnElement("Кнопка Удалить все");
        String dateTimeNow = LocalDateTime.now().format(DF);
        goToActionsInSystemPage("Автоматическое Тестирование1", "", "Назначение стратегий. " + displayName)
                .sortValuesInColumn("Время", "убыванию")
                .waitBusyCondition();
        Map<String, List<String>> expectedValues = Map.ofEntries(
                Map.entry("ФИО пользователя", List.of("Автоматическое Тестирование1")),
                Map.entry("ФИО пользователя (объект)", List.of("")),
                Map.entry("Время", List.of(dateTimeNow)),
                Map.entry("Тип события", List.of("Назначение стратегий. " + displayName)),
                Map.entry("Описание события", List.of("Пользователь Автоматическое Тестирование1 " + description + " правила назначения для продуктов: " +
                        "\"Потребительское кредитование\" и стратегий: \"ФССП\" с причинами назначения: \"Нет кредитов в КИ\" и сегментами: \"Новые зарплатные клиенты\"")));
        checkTableMonitoring(expectedValues);
        actionsInSystemPage.clickOnElement("Кнопка Удалить все")
                .clickOnElement("Кнопка Сбросить сортировку");
        fillFiltersAndCheckStrategy().clickOnElement("Кнопка Удалить все");
    }

    private void checkTableMonitoring(Map<String, List<String>> expectedValues) {
        for (Map.Entry<String, List<String>> expected : expectedValues.entrySet()) {
            List<String> actualValuesCells = actionsInSystemPage.getListValuesByColumnName("Таблица Действия в системе", expected.getKey());
            List<String> expectedValuesCells = expected.getValue();
            for (int i = 0; i < expectedValuesCells.size(); i++) {
                String actualValue = actualValuesCells.get(i).trim();
                String expectedValue = expectedValuesCells.get(i).trim();
                if (expected.getKey().equals("Время")) {
                    actualValue = actualValue.substring(0, 16);
                    expectedValue = expectedValue.substring(0, 16);
                }

                assertIsTrue(expectedValue.equals(actualValue),
                        "Значение столбца " + expected.getKey() + " строки " + (i + 1) +
                                " должно быть равно " + expectedValue + " . Фактическое значение = " + actualValue);
            }
        }
    }

    private void clearTableFromQuestions() {
        loginPage.openMenuLinks("Управление настройками > Настройка скриптов")
                .goTo(scriptConfigurationPage)
                .clickOnElement("Вкладка Вопросы")
                .fillInput("Поле ввода Текст вопроса", "\"Тест_мониторинг(АТ)\"")
                .clickOnElement("Кнопка Найти");
        int count = scriptConfigurationPage.getRowCountFromTable("Таблица Результаты поиска");
        if (count != 0) {
            for (int i = 0; i < count; i++) {
                scriptConfigurationPage.clickOnCellFromTable("Таблица Результаты поиска", i + 1, 1);
            }
            scriptConfigurationPage.clickOnElement("Кнопка Удалить")
                    .assertElementByTitleVisibility("Модальное окно Подтверждение удаления", "отображается")
                    .clickOnElement("Кнопка Удалить (Подтверждение удаления)")
                    .waitBusyCondition();
        }
        scriptConfigurationPage.assertElementByTitleVisibility("Модальное окно Информация об ошибке", "отображается")
                .clickOnElement("Кнопка Ок (Информация об ошибке)");
    }

    private AssignmentStrategyConfiguration fillFiltersAndCheckStrategy() {
        loginPage.openMenuLinks("Управление настройками > Настройки назначения стратегий")
                .goTo(assignmentStrategyConfiguration)
                .clickOnElement("Вкладка Назначение стратегий")
                .clickOnElement("Вкладка Редактируемая версия")
                .selectValueFromDropDownList("Выпадающий список Продукт", "Потребительское кредитование")
                .selectValueFromDropDownList("Выпадающий список Причина назначения", "Нет кредитов в КИ")
                .selectValueFromDropDownList("Выпадающий список Сегмент", "Новые зарплатные клиенты")
                .clickOnElement("Кнопка Найти")
                .waitBusyCondition();
        if (assignmentStrategyConfiguration.getRowCountFromTable("Таблица Результаты поиска") != 0) {
            assignmentStrategyConfiguration.clickOnCellFromTable("Таблица Результаты поиска", 1, 1)
                    .clickOnElement("Кнопка Удалить")
                    .assertElementByTitleVisibility("Модальное окно Подтверждение удаления", "отображается")
                    .clickOnElement("Кнопка Удалить (Подтверждение удаления)");
        }
        return assignmentStrategyConfiguration.assertElementByTitleVisibility("Модальное окно Информация об ошибке", "отображается")
                .clickOnElement("Кнопка Ок (Информация об ошибке)");
    }

    private void clearTableFromMainScript() {
        loginPage.openMenuLinks("Управление настройками > Настройка скриптов")
                .goTo(scriptConfigurationPage)
                .clickOnElement("Вкладка Основной скрипт")
                .fillInput("Поле ввода Наименование скрипта", "Тест_мониторинг(АТ_не трогать)")
                .clickOnElement("Кнопка Найти");
        int count = scriptConfigurationPage.getRowCountFromTable("Таблица Результаты поиска");
        if (count != 0) {
            for (int i = 0; i < count; i++) {
                scriptConfigurationPage.clickOnCellFromTable("Таблица Результаты поиска", i + 1, 1);
            }
            scriptConfigurationPage.clickOnElement("Кнопка Удалить")
                    .assertElementByTitleVisibility("Модальное окно Подтверждение удаления", "отображается")
                    .clickOnElement("Кнопка Удалить (Подтверждение удаления)")
                    .waitBusyCondition();
        }
        scriptConfigurationPage.assertElementByTitleVisibility("Модальное окно Информация об ошибке", "отображается")
                .clickOnElement("Кнопка Ок (Информация об ошибке)");
    }


    private ActionsInSystemPage goToActionsInSystemPage(String fio, String fioObject, String eventType) {
        return scriptConfigurationPage.openMenuLinks("Мониторинг > Действия в системе")
                .goTo(actionsInSystemPage)
                .fillInput("Поле ввода ФИО пользователя", fio)
                .fillInput("Поле ввода ФИО пользователя (объект)", fioObject)
                .selectEventType(eventType)
                .fillInput("Поле ввода Время от", dateTime)
                .clickOnElement("Кнопка Найти")
                .waitBusyCondition();
    }
}
