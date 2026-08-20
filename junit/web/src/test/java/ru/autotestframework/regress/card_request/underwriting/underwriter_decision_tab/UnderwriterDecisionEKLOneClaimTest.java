package ru.autotestframework.regress.card_request.underwriting.underwriter_decision_tab;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static ru.autotestframework.steps.asserts.Asserts.assertIsEquals;
import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;
import static ru.autotestframework.utils.Constants.REQUESTS;

@Tag("no_check_verification")
@Tag("regress")
@Tag("card_request")
@Tag("underwriting")
@Tag("underwriter_decision_tab")
@Tag("underwriter_decision_ekl_one_claim")
@ClassName("Карточка заявки. Андеррайтинг. Вкладка \"Решение андеррайтера\". Вкладка \"Решение андеррайтера\". На заявке Тип №1 Решение андеррайтера. ЕКЛ")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UnderwriterDecisionEKLOneClaimTest extends BaseTest {

    private String claim;

    String text = "Однако частновладельческого сектора в городе не " +
            "оказалось, и братья пообедали в летнем кооперативном саду, где " +
            "особые плакаты извещали граждан о  последнем  арбатовском " +
            "нововведении в области народного питания: " +
            "   ПИВО ОТПУСКАЕТСЯ ТОЛЬКО ЧЛЕНАМ ПРОФСОЮЗА " +
            "   -- Удовлетворимся квасом, - сказал Балаганов. " +
            "   -- Тем более, - добавил Остап, - что местные квасы " +
            "изготовляются артелью  частников,  сочувствующих  советской " +
            "власти.  А  теперь  расскажите,  чем провинился головорез " +
            "Паниковский. Я люблю рассказы о мелких жульничествах.";

    @BeforeAll
    public void login(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2476903.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi();
        }
    }

    @BeforeEach
    public void goToUnderwriting() {
        personalAccountPage.openMenuLinks("Личный кабинет")
                .clickOnElement("Раздел Андеррайтинг")
                .waitBusyCondition();
    }

    @AfterAll
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("smoke")
    @Tag("block_modification_reason_mandatory_filling_1722054")
    @DisplayName("1722054 - Блок Причина доработки. Обязательность заполнения.")
    @WorkItemIds({"1722054"})
    public void block_modification_reason_mandatory_filling_1722054() {
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .clickOnElement("Кнопка Доработка")
                .assertElementByTitleVisibility("Модальное окно Информация об ошибке", "отображается")
                .checkElementByTitleContains("Модальное окно Информация об ошибке", "Необходимо заполнить атрибут “Причина доработки”")
                .checkElementByTitleContains("Модальное окно Информация об ошибке", "Необходимо заполнить атрибут “Комментарий МРК”")
                .clickOnElement("Кнопка ОК на модальном окне")
                .assertElementByTitleVisibility("Модальное окно Информация об ошибке", "не отображается")
                .clickOnElement("Кнопка Принять решение")
                .assertElementByTitleVisibility("Модальное окно Информация об ошибке", "отображается")
                .checkElementByTitleNotContains("Модальное окно Информация об ошибке", "Необходимо заполнить атрибут “Причина доработки”")
                .checkElementByTitleNotContains("Модальное окно Информация об ошибке", "Необходимо заполнить атрибут “Комментарий МРК”")
                .clickOnElement("Кнопка ОК на модальном окне")
                .assertElementByTitleVisibility("Модальное окно Информация об ошибке", "не отображается")
                .clickOnElement("Кнопка На утверждение")
                .assertElementByTitleVisibility("Модальное окно Информация об ошибке", "отображается")
                .checkElementByTitleNotContains("Модальное окно Информация об ошибке", "Необходимо заполнить атрибут “Причина доработки”")
                .checkElementByTitleNotContains("Модальное окно Информация об ошибке", "Необходимо заполнить атрибут “Комментарий МРК”")
                .clickOnElement("Кнопка ОК на модальном окне")
                .clickOnElement("Кнопка Выйти без сохранения")
                .checkElementByTitleContains("Модальное окно Потеря изменений", "Выполненные изменения будут утеряны. Да\\Нет?")
                .clickOnElement("Кнопка Да на модальном окне")
                .switchToOneTab();
    }

    @Test
    @Tag("smoke")
    @Tag("internal_comment_block_1722102")
    @DisplayName("1722102 - Блок Внутренний комментарий.")
    @WorkItemIds({"1722102"})
    public void internal_comment_block_1722102() {
        personalAccountPage.openMenuLinks("Очереди")
                .goTo(queuesPage)
                .waitBusyCondition()
                .resetFilters()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .assertElementByTitleNotAvailableEditing("Поле ввода Внутренний комментарий андеррайтера", "не доступен для редактирования")
                .closeCurrentTab()
                .switchToOneTab()
                .goTo(queuesPage)
                .openMenuLinks("Личный кабинет")
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Андеррайтинг").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .waitBusyCondition()
                .fillInput("Поле ввода Внутренний комментарий андеррайтера", "test");
        assertIsEquals("test", underwriterDecisionPage.getValueByElementTitle("Поле ввода Внутренний комментарий андеррайтера"),
                "Поле ввода Внутренний комментарий андеррайтера");
        cardRequestPage.clickOnElement("Вкладка основные данные (с подчеркиванием)");
        assertIsEquals("test", underwriterDecisionPage.getValueByElementTitle("Поле ввода Внутренний комментарий андеррайтера"),
                "Поле ввода Внутренний комментарий андеррайтера");
        cardRequestPage.clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .clearInput("Поле ввода Внутренний комментарий андеррайтера");
        assertIsEquals("", underwriterDecisionPage.getValueByElementTitle("Поле ввода Внутренний комментарий андеррайтера"),
                "Поле ввода Внутренний комментарий андеррайтера");
        underwriterDecisionPage.fillInput("Поле ввода Внутренний комментарий андеррайтера", text);
        assertIsEquals(text, underwriterDecisionPage.getValueByElementTitle("Поле ввода Внутренний комментарий андеррайтера"),
                "Поле ввода Внутренний комментарий андеррайтера");
        underwriterDecisionPage.checkScroll("вертикальный", "Поле ввода Внутренний комментарий андеррайтера", true)
                .clickOnElement("Кнопка Сохранить и закрыть")
                .switchToOneTab()
                .waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .waitBusyCondition();
        assertIsEquals(text, cardRequestPage.getValueByElementTitle("Поле ввода Внутренний комментарий андеррайтера"),
                "Поле ввода Внутренний комментарий андеррайтера");
        cardRequestPage.clearInput("Поле ввода Внутренний комментарий андеррайтера");
        assertIsEquals("", cardRequestPage.getValueByElementTitle("Поле ввода Внутренний комментарий андеррайтера"),
                "Поле ввода Внутренний комментарий андеррайтера");
        cardRequestPage.fillInput("Поле ввода Внутренний комментарий андеррайтера", " ");
        cardRequestPage.clickOnElement("Кнопка Сохранить и закрыть")
                .waitBusyCondition()
                .goTo(personalAccountPage)
                .switchToOnetab();
    }

    @Test
    @Tag("smoke")
    @Tag("underwriter_decision_mandatory_area_2510438")
    @DisplayName("2510438 - Решение Андеррайтера. Обязательность поля Проведенные проверки")
    @WorkItemIds({"2510438"})
    public void underwriter_decision_mandatory_area_2510438() {
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .waitBusyCondition()
                .clickOnElement("Кнопка редактировать Скоррект. доход По осн. месту")
                .fillInput("Поле редактировать Скоррект. доход По осн. месту", "50001")
                .clickOnElement("Кнопка Пересчитать лимит")
                .checkElementByTitleContains("Модальное окно предупреждения", "Необходимо заполнить атрибут “Проведенные проверки”")
                .clickOnElement("Кнопка ОК на модальном окне Информация об ошибке")
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .clickOnElement("Кнопка Доработка")
                .checkElementByTitleContains("Модальное окно Информация об ошибке", "Необходимо заполнить атрибут “Проведенные проверки”")
                .clickOnElement("Кнопка ОК на модальном окне")
                .clickOnElement("Кнопка Принять решение")
                .checkElementByTitleContains("Модальное окно Информация об ошибке", "Необходимо заполнить атрибут “Проведенные проверки”")
                .clickOnElement("Кнопка ОК на модальном окне")
                .clickOnElement("Кнопка На утверждение")
                .checkElementByTitleContains("Модальное окно Информация об ошибке", "Необходимо заполнить атрибут “Проведенные проверки”")
                .clickOnElement("Кнопка ОК на модальном окне")
                .clickOnElement("Кнопка Выйти без сохранения")
                .checkElementByTitleContains("Модальное окно Потеря изменений", "Выполненные изменения будут утеряны. Да\\Нет?")
                .clickOnElement("Кнопка Да на модальном окне")
                .switchToOneTab();
    }

    @Test
    @Tag("smoke")
    @Tag("underwriter_decision_ability_to_edit_another_2510486")
    @DisplayName("2510486 - Решение Андеррайтера. Проведенные проверки. Возможность редактирования другого андеррайтера")
    @WorkItemIds({"2510486"})
    public void underwriter_decision_ability_to_edit_another_2510486() {
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .selectValueFromDropDownList("Выпадающий список Проведенные проверки", List.of(
                        "Определение рисков", "Проверка ФССП", "Проверка доход"))
                .clickOnElement("Кнопка Сохранить и закрыть")
                .switchToOneTab()
                .goTo(personalAccountPage)
                .waitBusyCondition()
                .clickOnElement("Кнопка выхода")
                .goTo(loginPage)
                .openAuthorizationPage()
                .waitBusyCondition()
                .loginViaUiOnUser("user2")
                .openMenuLinks("Очереди")
                .goTo(queuesPage)
                .searchClaimOnPage(claim)
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .checkElementByTitleContains("Блок Выбранных проверок", "Определение рисков")
                .checkElementByTitleContains("Блок Выбранных проверок", "Проверка ФССП")
                .checkElementByTitleContains("Блок Выбранных проверок", "Проверка доход")
                .clickOnElement("Кнопка Выйти без сохранения")
                .checkElementByTitleContains("Модальное окно Потеря изменений", "Выполненные изменения будут утеряны. Да\\Нет?")
                .clickOnElement("Кнопка Да на модальном окне")
                .switchToOneTab()
                .goTo(queuesPage)
                .waitBusyCondition()
                .resetFilters()
                .clickOnElement("Кнопка выхода")
                .goTo(loginPage)
                .openAuthorizationPage()
                .loginViaUi()
                .openMenuLinks("Личный кабинет")
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Андеррайтинг")
                .waitBusyCondition();
        // возвращение заявки в первоначальное состояние
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .selectValueFromDropDownList("Выпадающий список Проведенные проверки", List.of(
                        "Определение рисков", "Проверка ФССП", "Проверка доход"))
                .clickOnElement("Кнопка Сохранить и закрыть")
                .switchToOneTab();
    }

    @Test
    @Tag("smoke")
    @Tag("modal_window_with_errors_correction_1722677")
    @DisplayName("1722677 - Модальное окно с ошибками - корректировка.")
    @WorkItemIds({"1722677"})
    public void modal_window_with_errors_correction_1722677() {
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .waitBusyCondition()
                .clickOnElement("Кнопка редактировать Скоррект. доход По осн. месту")
                .fillInput("Поле редактировать Скоррект. доход По осн. месту", "50001")
                .clickOnElement("Кнопка Пересчитать лимит")
                .checkElementByTitleContains("Модальное окно предупреждения", "Необходимо заполнить атрибут “Проведенные проверки”")
                .checkElementByTitleContains("Модальное окно предупреждения", "Необходимо заполнить атрибут “Внутренний комментарий андеррайтера”")
                .checkElementByTitleContains("Модальное окно предупреждения", "Необходимо заполнить атрибут “Занятость подтверждена”")
                .clickOnElement("Кнопка ОК на модальном окне Информация об ошибке")
                .clickOnElement("Кнопка Выйти без сохранения")
                .checkElementByTitleContains("Модальное окно Потеря изменений", "Выполненные изменения будут утеряны. Да\\Нет?")
                .clickOnElement("Кнопка Да на модальном окне")
                .switchToOneTab();
    }

    @Test
    @Tag("type_of_claim_ekl_1722046")
    @DisplayName("1722046 - Решение Андеррайтера. Тип заявки. (ЕКЛ)")
    @WorkItemIds({"1722046"})
    public void type_of_claim_ekl_1722046() {
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .waitBusyCondition()
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .assertElementByTitleNotAvailableEditing("Выпадающий список Тип заявки", "не доступен для редактирования")
                .checkElementByTitleEquals("Выпадающий список Тип заявки", "Заявка ЕКЛ")
                .clickOnElement("Кнопка Выйти без сохранения")
                .checkElementByTitleContains("Модальное окно Потеря изменений", "Выполненные изменения будут утеряны. Да\\Нет?")
                .clickOnElement("Кнопка Да на модальном окне")
                .switchToOneTab();
    }

    @Test
    @Tag("reason_for_revision_1722050")
    @DisplayName("1722050 - Блок Причина доработки. Выпадающий список")
    @WorkItemIds({"1722050"})
    public void reason_for_revision_1722050() {
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .waitBusyCondition()
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .checkDropDownListElements("Выпадающий список Причина доработки",
                        List.of(
                                "",
                                "Ошибка МРК при вводе контактных данных клиента (адресов, телефонов)",
                                "Ошибка МРК при вводе персональных данных заявителя/супруги заявителя (ФИО, дата рождения, пол, паспортные данные)",
                                "Ошибка МРК при вводе дополнительной информации о заявителе (семейное положение, иждивенцы, образование, коммунальные платежи)",
                                "Ошибка МРК при вводе контактных данных о работодателе/занятости клиента (должность, стаж, № трудовой книжки)",
                                "Некорректные документы (нарушение требований к оформлению, ошибка МРК)",
                                "Некорректные документы (истечение сроков действия документов, ошибка МРК)",
                                "Неполный пакет документов (ошибка МРК)",
                                "Клиент указал некорректные данные в Анкете",
                                "Уточнение данных по кредитам клиента",
                                "Нечитаемые скан-образы документов",
                                "Паспорт недействителен (Нетиповая заявка)",
                                "Отказ работодателя подтвердить место работы",
                                "Отказ клиента",
                                "Недозвон",
                                "Недозвон с запросом документов",
                                "Отсутствует фото",
                                "Клиент не соответствует требованиям Банка",
                                "Запрос иных документов",
                                "Тех. отказ по отлагательным условиям по кредиту",
                                "Запрос Созаемщика"))
                .clickOnElement("Кнопка Выйти без сохранения")
                .checkElementByTitleContains("Модальное окно Потеря изменений", "Выполненные изменения будут утеряны. Да\\Нет?")
                .clickOnElement("Кнопка Да на модальном окне")
                .switchToOneTab();
    }

    @Test
    @Tag("type_of_claim_ekl_1722068")
    @DisplayName("1722068 - Проверка заполнения поля \"статус клиента\" на вкладке \"Решение андеррайтера\" и в отчете \"Автопроверки\"")
    @WorkItemIds({"1722068"})
    public void type_of_claim_ekl_1722068() {
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .waitBusyCondition()
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .checkElementByTitleEquals("Поле Статус клиента", "")
                .clickOnElement("Кнопка Основные данные")
                .goTo(cardRequestPage)
                .clickOnElement("Ссылка Автопроверки")
                .switchToNewTab()
                .goTo(autocheckPage)
                .checkElementByTitleContains("Поле Статус клиента", "Специалист")
                .closeCurrentTab()
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Выйти без сохранения")
                .checkElementByTitleContains("Модальное окно Потеря изменений", "Выполненные изменения будут утеряны. Да\\Нет?")
                .clickOnElement("Кнопка Да на модальном окне")
                .switchToOneTab();
    }

    @Test
    @Tag("type_of_claim_ekl_1722063")
    @DisplayName("1722063 - Проверка недоступности редактирования поля \"статус клиента\" в случае, если пользователь зашел в заявку не из ЛК")
    @WorkItemIds({"1722063"})
    public void type_of_claim_ekl_1722063() {
        personalAccountPage.openMenuLinks("Очереди")
                .goTo(queuesPage)
                .waitBusyCondition()
                .searchClaimOnPage(claim)
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .waitBusyCondition()
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .checkElementByTitleEquals("Поле Статус клиента", "")
                .elementByTitleNotAvailableEditing("Выпадающий список Статус Клиента", "не доступен для редактирования")
                .clickOnElement("Кнопка Выйти без сохранения")
                .checkElementByTitleContains("Модальное окно Потеря изменений", "Выполненные изменения будут утеряны. Да\\Нет?")
                .clickOnElement("Кнопка Да на модальном окне")
                .switchToOneTab()
                .goTo(queuesPage)
                .resetFilters();
    }

    @Test
    @Tag("modal_window_with_errors_1722045")
    @DisplayName("1722045 - Блок Причина доработки. Модальное окно ошибок")
    @WorkItemIds({"1722045"})
    public void modal_window_with_errors_1722045() {
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .waitBusyCondition()
                .selectValueFromDropDownList("Выпадающий список Занятость подтверждена", "Звонок не назначался")
                .fillInput("Поле ввода Внутренний комментарий андеррайтера", "от андера")
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .clickOnElement("Кнопка Доработка")
                .checkElementByTitleContains("Модальное окно Информация об ошибке", "Необходимо заполнить атрибут “Проведенные проверки”")
                .checkElementByTitleContains("Модальное окно Информация об ошибке", "Необходимо заполнить атрибут “Причина доработки”")
                .checkElementByTitleContains("Модальное окно Информация об ошибке", "Необходимо заполнить атрибут “Комментарий МРК”")
                .clickOnElement("Кнопка ОК на модальном окне")
                .clickOnElement("Кнопка Выйти без сохранения")
                .checkElementByTitleContains("Модальное окно Потеря изменений", "Выполненные изменения будут утеряны. Да\\Нет?")
                .clickOnElement("Кнопка Да на модальном окне")
                .switchToOneTab();
    }

    @Test
    @Tag("save_client_status_1722065")
    @DisplayName("1722065 - Проверка возможности сохранения пустого значения в поле \"статус клиента\"")
    @WorkItemIds({"1722065"})
    public void save_client_status_1722065() {
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .checkElementByTitleEquals("Поле Статус клиента", "")
                .selectValueFromDropDownList("Поле Статус клиента", "")
                .clickOnElement("Кнопка Сохранить и закрыть")
                .switchToOneTab()
                .waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .checkElementByTitleEquals("Поле Статус клиента", "")
                .clickOnElement("Кнопка Выйти без сохранения")
                .checkElementByTitleContains("Модальное окно Потеря изменений", "Выполненные изменения будут утеряны. Да\\Нет?")
                .clickOnElement("Кнопка Да на модальном окне")
                .switchToOneTab();
    }

    @Test
    @Tag("check_client_status_1722053")
    @DisplayName("1722053 - Проверка присутствия 4 значений при редактировании поля \"статус клиента\"")
    @WorkItemIds({"1722053"})
    public void check_client_status_1722053() {
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .checkDropDownListElements("Выпадающий список Статус Клиента",
                        List.of("",
                                "Специалист",
                                "ЛПР",
                                "Учредитель"))
                .clickOnElement("Кнопка Выйти без сохранения")
                .checkElementByTitleContains("Модальное окно Потеря изменений", "Выполненные изменения будут утеряны. Да\\Нет?")
                .clickOnElement("Кнопка Да на модальном окне")
                .switchToOneTab();
    }

    @Test
    @Tag("save_client_status_1722061")
    @DisplayName("1722061 - Проверка корректной записи значения в БД в случае, если статус клиента = специалист")
    @WorkItemIds({"1722061"})
    public void save_client_status_1722061() {
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .checkElementByTitleEquals("Поле Статус клиента", "")
                .selectValueFromDropDownList("Поле Статус клиента", "Специалист")
                .clickOnElement("Кнопка Сохранить и закрыть")
                .switchToOneTab()
                .waitBusyCondition();
        actionsClaimSteps.executeQuery(REQUESTS,
                "SELECT rr.claim_id, ruc.und_applicant_status_bw FROM requests.rqs_underwriter_check ruc " +
                        "JOIN requests.rqs_form rf ON rf.id = ruc.form_id " +
                        "JOIN requests.rqs_request rr ON rr.id = rf.request_id " +
                        "WHERE rr.claim_id ='" + claim + "'");
        assertIsTrue(actionsClaimSteps.getVariables("und_applicant_status_bw").equals("1"), "Значение und_applicant_status_bw == 1 для заявки " + claim);
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .selectValueFromDropDownList("Поле Статус клиента", "")
                .clickOnElement("Кнопка Сохранить и закрыть")
                .switchToOneTab()
                .waitBusyCondition();
    }

    @Test
    @Tag("save_client_status_1722048")
    @DisplayName("1722048 - Проверка на неизменность значения поля \"статус клиента\" в отчете Автопроверок " +
            "после выбора и сохранения значения в поле \"статус\" клиента на вкладке \"Решения андеррайтера\"")
    @WorkItemIds({"1722048"})
    public void save_client_status_1722048() {
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .checkElementByTitleEquals("Поле Статус клиента", "")
                .clickOnElement("Кнопка Основные данные")
                .goTo(cardRequestPage)
                .clickOnElement("Ссылка Автопроверки")
                .switchToNewTab()
                .goTo(autocheckPage)
                .checkElementByTitleEquals("Поле Статус клиента", "Специалист")
                .closeCurrentTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .selectValueFromDropDownList("Поле Статус клиента", "Учредитель")

                .clickOnElement("Кнопка Сохранить и закрыть")
                .switchToOneTab()
                .waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .checkElementByTitleEquals("Поле Статус клиента", "Учредитель")
                .clickOnElement("Кнопка Основные данные")
                .goTo(cardRequestPage)
                .clickOnElement("Ссылка Автопроверки")
                .switchToNewTab()
                .goTo(autocheckPage)
                .checkElementByTitleEquals("Поле Статус клиента", "Специалист")
                .closeCurrentTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .selectValueFromDropDownList("Поле Статус клиента", "")
                .clickOnElement("Кнопка Сохранить и закрыть")
                .switchToOneTab()
                .waitBusyCondition();
    }

    @Test
    @Tag("conclusions_block_1722101")
    @DisplayName("1722101 - Блок Заключение. Полномочия.")
    @WorkItemIds({"1722101"})
    public void conclusions_block_1722101() {
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .checkDropDownListElements("Выпадающий список Полномочия",
                        List.of("",
                                "Собственные",
                                "ЦСКО группа 1",
                                "ЦСКО группа 2",
                                "ЦСКО группа 3",
                                "ЦСКО группа 4",
                                "ЦСКО группа 5",
                                "ЦСКО группа обучения",
                                "ГО",
                                "КРК",
                                "СЦ Орёл группа 1",
                                "Смена",
                                "Недозвон",
                                "МП-1",
                                "МП-2",
                                "МП-3",
                                "ДЧК",
                                "ЛСПР"))
                .clickOnElement("Кнопка Выйти без сохранения")
                .checkElementByTitleContains("Модальное окно Потеря изменений", "Выполненные изменения будут утеряны. Да\\Нет?")
                .clickOnElement("Кнопка Да на модальном окне")
                .switchToOneTab();
    }

    @Test
    @Tag("conclusions_block_1722095")
    @DisplayName("1722095 - Блок Заключение. Поле Одобрить/Отклонить.")
    @WorkItemIds({"1722095"})
    public void conclusions_block_1722095() {
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .checkDropDownListElements("Выпадающий список Одобрить/Отклонить",
                        List.of("",
                                "Одобрить",
                                "Отклонить"))
                .clickOnElement("Кнопка Выйти без сохранения")
                .checkElementByTitleContains("Модальное окно Потеря изменений", "Выполненные изменения будут утеряны. Да\\Нет?")
                .clickOnElement("Кнопка Да на модальном окне")
                .switchToOneTab();
    }

    @Test
    @Tag("conclusions_block_1722085")
    @DisplayName("1722085 - Блок Заключение. Тип одобрения/Причина отклонения.")
    @WorkItemIds({"1722085"})
    public void conclusions_block_1722085() {
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .selectValueFromDropDownList("Выпадающий список Одобрить/Отклонить", "Одобрить")
                .checkDropDownListElements("Выпадающий список Тип одобрения/Причина отклонения",
                        List.of("",
                                "Одобрено",
                                "Одобрено с отлагательным условием по кредиту",
                                "Одобрено с отлагательным условием"))
                .selectValueFromDropDownList("Выпадающий список Одобрить/Отклонить", "Отклонить")
                .checkDropDownListElements("Выпадающий список Тип одобрения/Причина отклонения",
                        List.of("",
                                "Негатив на Клиента",
                                "Исполнительные/судебные производства на Клиента",
                                "Триггеры AFW",
                                "Клиент предоставляет ложные анкетные данные",
                                "Клиент заявку не подавал/Заявка от брокера",
                                "Кредит на бизнес",
                                "Кредит для третьего лица",
                                "Негативная характеристика третьих лиц на Клиента",
                                "Трудоустройство не по найму / временная работа",
                                "Клиент уволен / находится в стадии увольнения",
                                "Ликвидация/банкротство/негатив на работодателя",
                                "Недозвон до работодателя",
                                "Работодатель отказывается подтверждать занятость Клиента",
                                "Подставной рабочий телефон",
                                "Контактные данные компании не подтвердились",
                                "Документы имеют признаки фальсификации",
                                "Данные не соответствуют предыдущим заявкам",
                                "Отрицательная оценка трудовой деятельности",
                                "Признаки предоставления недостоверных сведений о доходе",
                                "Признаки предоставления недостоверных сведений о трудоустройстве",
                                "Анализ кредитной истории",
                                "Стоп-факторы программы кредитования",
                                "ТО не снят",
                                "Нетиповая: отсутствие критериев значимости",
                                "Нетиповая: низкий скоринговый балл",
                                "Нетиповая: отрицательная платежеспособность",
                                "Собственник бизнеса",
                                "Реструктуризация: высокая долговая нагрузка",
                                "Реструктуризация: ухудшение фин.состояния не подтверждено",
                                "Негатив на Супруга(у)",
                                "Исполнительные/судебные производства на Супруга(у)",
                                "Отказ по Созаемщику",
                                "Недозвон до Клиента",
                                "Недозвон до Супруга(и)"))
                .clickOnElement("Кнопка Выйти без сохранения")
                .checkElementByTitleContains("Модальное окно Потеря изменений", "Выполненные изменения будут утеряны. Да\\Нет?")
                .clickOnElement("Кнопка Да на модальном окне")
                .switchToOneTab();
    }

    @Test
    @Tag("conclusions_block_1722089")
    @DisplayName("1722089 - Блок Заключение. Обязательность заполнения")
    @WorkItemIds({"1722089"})
    public void conclusions_block_1722089() {
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .waitBusyCondition()
                .clickOnElement("Кнопка редактировать Скоррект. доход По осн. месту")
                .fillInput("Поле редактировать Скоррект. доход По осн. месту", "50001")
                .clickOnElement("Кнопка Пересчитать лимит")
                .checkElementByTitleNotContains("Модальное окно Информация об ошибке", "Необходимо заполнить атрибут “Тип одобрения/Причина отклонения”")
                .checkElementByTitleNotContains("Модальное окно Информация об ошибке", "Необходимо заполнить атрибут “Полномочия”")
                .checkElementByTitleNotContains("Модальное окно Информация об ошибке", "Необходимо заполнить атрибут “Одобрить/Отклонить”")
                .clickOnElement("Кнопка ОК на модальном окне Информация об ошибке")
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .clickOnElement("Кнопка Доработка")
                .checkElementByTitleNotContains("Модальное окно Информация об ошибке", "Необходимо заполнить атрибут “Тип одобрения/Причина отклонения”")
                .checkElementByTitleNotContains("Модальное окно Информация об ошибке", "Необходимо заполнить атрибут “Полномочия”")
                .checkElementByTitleNotContains("Модальное окно Информация об ошибке", "Необходимо заполнить атрибут “Одобрить/Отклонить”")
                .clickOnElement("Кнопка ОК на модальном окне")
                .clickOnElement("Кнопка Принять решение")
                .checkElementByTitleContains("Модальное окно Информация об ошибке", "Необходимо заполнить атрибут “Тип одобрения/Причина отклонения”")
                .checkElementByTitleContains("Модальное окно Информация об ошибке", "Необходимо заполнить атрибут “Полномочия”")
                .checkElementByTitleContains("Модальное окно Информация об ошибке", "Необходимо заполнить атрибут “Одобрить/Отклонить”")
                .clickOnElement("Кнопка ОК на модальном окне")
                .clickOnElement("Кнопка На утверждение")
                .checkElementByTitleContains("Модальное окно Информация об ошибке", "Необходимо заполнить атрибут “Тип одобрения/Причина отклонения”")
                .checkElementByTitleContains("Модальное окно Информация об ошибке", "Необходимо заполнить атрибут “Полномочия”")
                .checkElementByTitleContains("Модальное окно Информация об ошибке", "Необходимо заполнить атрибут “Одобрить/Отклонить”")
                .clickOnElement("Кнопка ОК на модальном окне")
                .clickOnElement("Кнопка Выйти без сохранения")
                .checkElementByTitleContains("Модальное окно Потеря изменений", "Выполненные изменения будут утеряны. Да\\Нет?")
                .clickOnElement("Кнопка Да на модальном окне")
                .switchToOneTab();
    }

    @Test
    @Tag("underwriter_decision_2511107")
    @DisplayName("2511107 - Решение Андеррайтера. Обязательность поля Сегмент клиента.")
    @WorkItemIds({"2511107"})
    public void underwriter_decision_2511107() {
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .waitBusyCondition()
                .clickOnElement("Кнопка редактировать Скоррект. доход По осн. месту")
                .fillInput("Поле редактировать Скоррект. доход По осн. месту", "50001")
                .clickOnElement("Кнопка Пересчитать лимит")
                .checkElementByTitleNotContains("Модальное окно Информация об ошибке", "Необходимо заполнить атрибут “Сегмент клиента”")
                .clickOnElement("Кнопка ОК на модальном окне Информация об ошибке")
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .clickOnElement("Кнопка Доработка")
                .checkElementByTitleNotContains("Модальное окно Информация об ошибке", "Необходимо заполнить атрибут “Сегмент клиента”")
                .clickOnElement("Кнопка ОК на модальном окне")
                .clickOnElement("Кнопка Принять решение")
                .checkElementByTitleNotContains("Модальное окно Информация об ошибке", "Необходимо заполнить атрибут “Сегмент клиента”")
                .clickOnElement("Кнопка ОК на модальном окне")
                .clickOnElement("Кнопка На утверждение")
                .checkElementByTitleNotContains("Модальное окно Информация об ошибке", "Необходимо заполнить атрибут “Сегмент клиента”")
                .clickOnElement("Кнопка ОК на модальном окне")
                .clickOnElement("Кнопка Выйти без сохранения")
                .checkElementByTitleContains("Модальное окно Потеря изменений", "Выполненные изменения будут утеряны. Да\\Нет?")
                .clickOnElement("Кнопка Да на модальном окне")
                .switchToOneTab();
    }

    @Test
    @Tag("underwriter_decision_1722104")
    @DisplayName("1722104 - Решение Андеррайтера. Обязательность поля Статус клиента.")
    @WorkItemIds({"1722104"})
    public void underwriter_decision_1722104() {
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .waitBusyCondition()
                .clickOnElement("Кнопка редактировать Скоррект. доход По осн. месту")
                .fillInput("Поле редактировать Скоррект. доход По осн. месту", "50001")
                .clickOnElement("Кнопка Пересчитать лимит")
                .checkElementByTitleNotContains("Модальное окно Информация об ошибке", "Необходимо заполнить атрибут “Статус клиента”")
                .clickOnElement("Кнопка ОК на модальном окне Информация об ошибке")
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .clickOnElement("Кнопка Доработка")
                .checkElementByTitleNotContains("Модальное окно Информация об ошибке", "Необходимо заполнить атрибут “Статус клиента”")
                .clickOnElement("Кнопка ОК на модальном окне")
                .clickOnElement("Кнопка Принять решение")
                .checkElementByTitleNotContains("Модальное окно Информация об ошибке", "Необходимо заполнить атрибут “Статус клиента”")
                .clickOnElement("Кнопка ОК на модальном окне")
                .clickOnElement("Кнопка На утверждение")
                .checkElementByTitleNotContains("Модальное окно Информация об ошибке", "Необходимо заполнить атрибут “Статус клиента”")
                .clickOnElement("Кнопка ОК на модальном окне")
                .clickOnElement("Кнопка Выйти без сохранения")
                .checkElementByTitleContains("Модальное окно Потеря изменений", "Выполненные изменения будут утеряны. Да\\Нет?")
                .clickOnElement("Кнопка Да на модальном окне")
                .switchToOneTab();
    }


    @Test
    @Tag("underwriter_decision_1722087")
    @DisplayName("1722087 - Решение Андеррайтера. Проведенные проверки. Выбор проверок")
    @WorkItemIds({"1722087"})
    public void underwriter_decision_1722087() {
        List<String> allChecks = new ArrayList<>(List.of(
                "Проверка критичных данных",
                "Проверка документов",
                "Определение рисков",
                "Проверка минимальных требований",
                "Проверка ФССП",
                "Проверка негатив",
                "Проверка предыдущих заявок",
                "Проверка антифрод-отчет",
                "Проверка доход",
                "Проверка открытые источники – сайт",
                "Проверка открытые источники – работодатель",
                "Проверка открытые источники – привязка телефона",
                "Проверка открытые источники – Бесконтактное подтверждение",
                "Звонок клиенту",
                "Звонок работодателю по любому телефону",
                "Звонок работодателю по подтвержденному телефону",
                "Обязательный звонок работодателю по любому телефону",
                "Обязательный звонок работодателю по подтвержденному телефону",
                "Звонок контактному лицу/супруге",
                "Звонок арендодателю",
                "Нерезультативный звонок",
                "Проверка долговой нагрузки",
                "Повторная проверка этапов",
                "Проверка открытые источники - брокерские услуги"
        ));
        List<String> fewChecks = List.of("Определение рисков", "Проверка ФССП", "Проверка доход");
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .checkDropDownListElements("Выпадающий список Проведенные проверки", allChecks)
                .selectValueFromDropDownList("Выпадающий список Проведенные проверки", fewChecks)
                .checkSelectedChecks(fewChecks, "с крестиком")
                .selectValueFromDropDownList("Выпадающий список Проведенные проверки", fewChecks)
                .checkSelectedChecks(Collections.emptyList(), "с крестиком")
                .selectValueFromDropDownList("Выпадающий список Проведенные проверки", "Выбрать все")
                .checkSelectedChecks(allChecks, "с крестиком")
                .deleteConductedChecks(fewChecks)
                .assertCheckboxesSelected("Выпадающий список Проведенные проверки", fewChecks, "отключены")
                .clickOnElement("Кнопка Сохранить и закрыть")
                .switchToOneTab()
                .waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .waitBusyCondition();
        allChecks.removeIf(fewChecks::contains);
        underwriterDecisionPage.checkSelectedChecks(allChecks, "с крестиком")
                .assertCheckboxesSelected("Выпадающий список Проведенные проверки", fewChecks, "отключены")
                .selectValueFromDropDownList("Выпадающий список Проведенные проверки", "Выбрать все")
                .selectValueFromDropDownList("Выпадающий список Проведенные проверки", "Выбрать все")
                .assertCheckboxesSelected("Выпадающий список Проведенные проверки", allChecks, "отключены")
                .checkSelectedChecks(Collections.emptyList(), "с крестиком")
                .clickOnElement("Кнопка Сохранить и закрыть")
                .switchToOneTab();
    }

    @Test
    @Tag("internal_comment_block_1722059")
    @DisplayName("1722059 - Блок Причина доработки. Ввод Комментария МРК и корректировка размера поля.")
    @WorkItemIds({"1722059"})
    public void internal_comment_block_1722059() {
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage);
        String text = generateRandomString(100, true);
        underwriterDecisionPage.fillInput("Поле ввода Комментарий МРК и отлагательных условий", text);
        assertIsEquals(text, underwriterDecisionPage.getValueByElementTitle("Поле ввода Комментарий МРК и отлагательных условий"),
                "Поле ввода Комментарий МРК и отлагательных условий");
        underwriterDecisionPage.clearInput("Поле ввода Комментарий МРК и отлагательных условий");
        assertIsEquals("", underwriterDecisionPage.getValueByElementTitle("Поле ввода Комментарий МРК и отлагательных условий"),
                "Поле ввода Комментарий МРК и отлагательных условий");
        text = generateRandomString(100, false);
        underwriterDecisionPage.fillInput("Поле ввода Комментарий МРК и отлагательных условий", text);
        assertIsEquals(text, underwriterDecisionPage.getValueByElementTitle("Поле ввода Комментарий МРК и отлагательных условий"),
                "Поле ввода Комментарий МРК и отлагательных условий");
        underwriterDecisionPage.clearInput("Поле ввода Комментарий МРК и отлагательных условий");
        text = "=+-№%";
        underwriterDecisionPage.fillInput("Поле ввода Комментарий МРК и отлагательных условий", text);
        assertIsEquals(text, underwriterDecisionPage.getValueByElementTitle("Поле ввода Комментарий МРК и отлагательных условий"),
                "Поле ввода Комментарий МРК и отлагательных условий");
        underwriterDecisionPage.clearInput("Поле ввода Комментарий МРК и отлагательных условий");
        text = "=+-";
        underwriterDecisionPage.fillInput("Поле ввода Комментарий МРК и отлагательных условий", text);
        assertIsEquals(text, underwriterDecisionPage.getValueByElementTitle("Поле ввода Комментарий МРК и отлагательных условий"),
                "Поле ввода Комментарий МРК и отлагательных условий");
        underwriterDecisionPage.clearInput("Поле ввода Комментарий МРК и отлагательных условий");
        text = "Дверь распахнулась, и в комнату проник \n" +
                "Паниковский. Прижимая шляпу к сальному пиджаку, он остановился \n" +
                "около стола и долго шевелил толстыми губами. После этого председатель подскочил на стуле и широко \n" +
                "раскрыл рот.Друзья услышали протяжный крик. Со словами \"все назад\" Остап увлек за собою Балаганова.Они побежали \n" +
                "на бульвар и спрятались за деревом. \n" +
                "--Снимите шляпы, --сказал Остап, --обнажите головы. \n" +
                "Сейчас состоится вынос тела.";
        underwriterDecisionPage.fillInput("Поле ввода Комментарий МРК и отлагательных условий", text);
        assertIsEquals(text, underwriterDecisionPage.getValueByElementTitle("Поле ввода Комментарий МРК и отлагательных условий"),
                "Поле ввода Комментарий МРК и отлагательных условий");
        underwriterDecisionPage
                .checkScroll("вертикальный", "Поле ввода Комментарий МРК и отлагательных условий", true)
                .clearInput("Поле ввода Комментарий МРК и отлагательных условий")
                .checkScroll("вертикальный", "Поле ввода Комментарий МРК и отлагательных условий", false)
                .clickOnElement("Кнопка Выйти без сохранения")
                .checkElementByTitleContains("Модальное окно Потеря изменений", "Выполненные изменения будут утеряны. Да\\Нет?")
                .clickOnElement("Кнопка Да на модальном окне")
                .switchToOneTab();
    }

    public String generateRandomString(int length, boolean lang) {
        String cyrillic = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ" +
                "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
        String latin = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                "abcdefghijklmnopqrstuvwxyz";
        String alphabet = lang ? cyrillic : latin;
        Random random = new Random();
        StringBuilder result = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            result.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }
        return result.toString();
    }
}
