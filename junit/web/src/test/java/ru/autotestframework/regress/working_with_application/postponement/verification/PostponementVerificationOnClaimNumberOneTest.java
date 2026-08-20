package ru.autotestframework.regress.working_with_application.postponement.verification;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;
import static ru.autotestframework.utils.Constants.DF;
import static ru.autotestframework.utils.Constants.REQUESTS;

@Tag("regress")
@Tag("working_with_application")
@Tag("verification")
@Tag("postponement")
@Tag("postponement_verification_on_claim_number_one_test")
@ClassName("Работа с заявкой. Откладывание. Верификация. На заявке №1. Откладывание Верификация")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PostponementVerificationOnClaimNumberOneTest extends BaseTest {

    String claim;
    private LocalDateTime nowTime;
    Map<String, String> claimParams = Map.of(
            "Code", "stub5");

    @BeforeEach
    public void login(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_4133097.json", 1, testInfo, claimParams).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi()
                    .openMenuLinks("Личный кабинет")
                    .goTo(personalAccountPage)
                    .clickOnElement("Раздел Верификация");
        }
    }

    @AfterEach
    public void cleanQueueClaims() {
        incomeVerificationPage.closeCurrentTab();
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("checking_presence_button_1723056")
    @DisplayName("1723056 - Проверка присутствия кнопки \"Отложить\"  на каждой экранной форме")
    @WorkItemIds({"1723056"})
    public void checking_presence_button_1723056(TestInfo testInfo) {
        personalAccountPage.doubleClickByText(claim).switchToNewTab()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка дохода/Версия 1")
                .goTo(cardRequestPage)
                .assertElementByTitleVisibility("Кнопка Отложить", "отображается")
                .clickOnElement("Шаг №2")
                .clickOnElement("Кнопка Взять в работу")
                .assertElementByTitleVisibility("Кнопка Отложить", "отображается");
    }

    @Test
    @Tag("checking_active_button_1723057")
    @DisplayName("1723057 - Проверка активности/не активности кнопки \"Отложить\" в зависимости от режима просмотра/обработки заявки")
    @WorkItemIds({"1723057"})
    public void checking_active_button_1723057(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "Code", "stub1");
        personalAccountPage.doubleClickByText(claim).switchToNewTab()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка дохода/Версия 1")
                .goTo(cardRequestPage)
                .assertElementByTitleActivity("Кнопка Отложить", "активен");
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_4133097.json", 1, testInfo, claimParams).get(0);
        loginPage.openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim);
        String statusClaim = queuesPage.getTextFromTable("Таблица результаты поиска", 1, "Статус заявки");
        assertIsTrue(statusClaim.equals("Ожидает"),
                "Значение в столбце Статус заявки должно быть равно Ожидает. Фактическое значение: " + statusClaim);
        queuesPage.doubleClickByText(claim).switchToNewTab().waitBusyCondition()
                .goTo(cardRequestPage)
                .assertElementByTitleActivity("Кнопка Отложить", "не активен")
                .closeCurrentTab();
    }

    @Test
    @Tag("checking_open_modal_window_1723052")
    @DisplayName("1723052 - Проверка открытия модального окна при нажатии на кнопку \"Отложить\"")
    @WorkItemIds({"1723052"})
    public void checking_open_modal_window_1723052(TestInfo testInfo) {
        personalAccountPage.doubleClickByText(claim).switchToNewTab()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка дохода/Версия 1")
                .goTo(cardRequestPage)
                .assertElementByTitleActivity("Кнопка Отложить", "активен")
                .clickOnElement("Кнопка Отложить")
                .assertElementByTitleVisibility("Модальное окно Перевод заявки в отложенные", "отображается");
    }

    @Test
    @Tag("checking_values_reason_field_1723053")
    @DisplayName("1723053 - Проверка значений поля \"Причина\" при откладывании заявки")
    @WorkItemIds({"1723053"})
    public void checking_values_reason_field_1723053(TestInfo testInfo) {
        List<String> actualDropDownListCheckBox;
        actionsClaimSteps.executeQuery(REQUESTS, "SELECT * FROM requests.rqs_dir_delay_reason;");
        List<String> valuesFromDb = actionsClaimSteps.getValuesFromResponseDb("description");

        personalAccountPage.doubleClickByText(claim).switchToNewTab()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка дохода/Версия 1")
                .goTo(cardRequestPage)
                .assertElementByTitleActivity("Кнопка Отложить", "активен")
                .clickOnElement("Кнопка Отложить")
                .clickOnElement("Выпадающий список Причина (Перевод заявки в отложенные)");
        actualDropDownListCheckBox = cardRequestPage.getListCheckBox("Список Причин (Перевод заявки в отложенные)");
        assertIsTrue(valuesFromDb.equals(actualDropDownListCheckBox), "Список " + valuesFromDb + " соответствует списку " + actualDropDownListCheckBox);
    }

    @Test
    @Tag("checking_save_comment_1723054")
    @DisplayName("1723054 - Проверка сохранения комментария при откладывании заявки и просмотр этого комментария по кнопке \"Внутренний комментарий\"")
    @WorkItemIds({"1723054"})
    public void checking_save_comment_1723054(TestInfo testInfo) {
        personalAccountPage.doubleClickByText(claim).switchToNewTab()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка дохода/Версия 1")
                .goTo(cardRequestPage)
                .assertElementByTitleActivity("Кнопка Отложить", "активен")
                .clickOnElement("Кнопка Отложить")
                .selectValueFromDropDownList("Выпадающий список Причина (Перевод заявки в отложенные)", "Недозвон Заемщику")
                .fillInput("Поле ввода комментарий (Перевод заявки в отложенные)", "комАТ")
                .fillInput("Поле ввода Время для звонка участнику", getTimeNow().plusMinutes(6).format(DF))
                .clickOnElement("Кнопка Отложить заявку")
                .switchToOneTab()
                .goTo(personalAccountPage).waitBusyCondition()
                .clickOnElement("Кнопка раскрыть таблицу Отложено")
                .doubleClickByText(claim).switchToNewTab()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка дохода/Версия 1")
                .goTo(cardRequestPage)
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - Отложена (рассмотрение)")
                .clickOnElement("Кнопка Внутренний комментарий")
                .checkElementByTitleContains("Поле История комментариев", "комАТ");
    }

    @Test
    @Tag("checking_save_info_1723055")
    @DisplayName("1723055 - Проверка сохранения всей информации, введенной при проверках ")
    @WorkItemIds({"1723055"})
    public void checking_save_info_1723055(TestInfo testInfo) {
        personalAccountPage.doubleClickByText(claim).switchToNewTab()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка дохода/Версия 1")
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Должность клиента не позволяет оценить его доход")
                .clickOnElement("Кнопка Рассчитать").waitBusyCondition()
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Доход завышен. Руководитель/Специалист")
                .goTo(cardRequestPage)
                .assertElementByTitleActivity("Кнопка Отложить", "активен")
                .clickOnElement("Кнопка Отложить")
                .selectValueFromDropDownList("Выпадающий список Причина (Перевод заявки в отложенные)", "Вопрос в ГО")
                .fillInput("Поле ввода комментарий (Перевод заявки в отложенные)", "комАТ")
                .clickOnElement("Кнопка Отложить заявку").waitBusyCondition().switchToOneTab()
                .goTo(personalAccountPage).waitBusyCondition()
                .clickOnElement("Кнопка раскрыть таблицу Отложено")
                .doubleClickByText(claim).switchToNewTab()
                .goTo(cardRequestPage)
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - Отложена (рассмотрение)")
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка дохода/Версия 1")
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Доход завышен. Руководитель/Специалист");
    }

    @Test
    @Tag("checking_visible_button_1723064")
    @DisplayName("1723064 - Проверка присутствия кнопки \"Взять в работу\" на каждой экранной форме")
    @WorkItemIds({"1723064"})
    public void checking_visible_button_1723064(TestInfo testInfo) {
        personalAccountPage.doubleClickByText(claim).switchToNewTab()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка дохода/Версия 1")
                .goTo(cardRequestPage)
                .assertElementByTitleVisibility("Кнопка Взять в работу", "отображается")
                .goTo(incomeVerificationPage)
                .selectValueFromDropDownList("Выпадающий список Результат проверки", "Должность клиента не позволяет оценить его доход")
                .clickOnElement("Кнопка Рассчитать").waitBusyCondition()
                .checkElementByTitleEquals("Выпадающий список Результат проверки", "Доход завышен. Руководитель/Специалист")
                .clickOnElement("Кнопка Далее")
                .assertElementByTitleActivity("Иконка Степ 2", "активен")
                .goTo(cardRequestPage)
                .assertElementByTitleVisibility("Кнопка Взять в работу", "отображается")

                .goTo(incomeVerificationPage)
                .clickOnElement("Иконка 'галочка' на первом шаге степера")
                .clickOnElement("Кнопка Изменить результат");
    }

    @Test
    @Tag("checking_active_button_1723062")
    @DisplayName("1723062 - Проверка активности/не активности кнопки \"Взять в работу\" в зависимости от режима просмотра/обработки заявки")
    @WorkItemIds({"1723062"})
    public void checking_active_button_1723062(TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "Code", "stub1");

        personalAccountPage.doubleClickByText(claim).switchToNewTab()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка дохода/Версия 1")
                .goTo(cardRequestPage)
                .assertElementByTitleVisibility("Кнопка Взять в работу", "не активен");
        String secondClaim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_4133097.json", 1, testInfo, claimParams).get(0);
        loginPage.openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(secondClaim).doubleClickByText(secondClaim).switchToNewTab().waitBusyCondition()
                .goTo(cardRequestPage)
                .assertElementByTitleVisibility("Кнопка Взять в работу", "не активен")
                .closeCurrentTab().closeCurrentTab().switchToOneTab()
                .goTo(loginPage)
                .openMenuLinks("Личный кабинет")
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Верификация").doubleClickByText(claim).switchToNewTab()
                .goTo(incomeVerificationPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "Проверка дохода/Версия 1")
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Отложить")
                .selectValueFromDropDownList("Выпадающий список Причина (Перевод заявки в отложенные)", "Недозвон Заемщику")
                .fillInput("Поле ввода комментарий (Перевод заявки в отложенные)", "комАТ")
                .fillInput("Поле ввода Время для звонка участнику", getTimeNow().plusMinutes(6).format(DF))
                .clickOnElement("Кнопка Отложить заявку").waitBusyCondition().switchToOneTab()
                .goTo(personalAccountPage).waitBusyCondition()
                .clickOnElement("Кнопка раскрыть таблицу Отложено")
                .doubleClickByText(claim).switchToNewTab()
                .goTo(cardRequestPage)
                .assertElementByTitleVisibility("Кнопка Взять в работу", "активен")
                .clickOnElement("Кнопка Взять в работу")
                .goTo(cardRequestPage)
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - На рассмотрении");
    }

    private LocalDateTime getTimeNow() {
        nowTime = LocalDateTime.now();
        return nowTime;
    }
}
