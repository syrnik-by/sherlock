package ru.autotestframework.regress.card_request.underwriting.underwriter_decision_tab;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import java.util.List;

import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;

@Tag("no_check_verification")
@Tag("regress")
@Tag("card_request")
@Tag("underwriting")
@Tag("underwriter_decision_tab")
@Tag("underwriter_decision_ekl_separate_claim")
@ClassName("Карточка заявки. Андеррайтинг. Вкладка \"Решение андеррайтера\". На каждый кейс отдельная заявка. Решение андеррайтера")
public class UnderwriterDecisionEKLSeparateClaimTest extends BaseTest {

    private String claim;

    @BeforeAll
    public void login() {
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi();
        }
    }

    @BeforeEach
    public void goToUnderwriting(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2476903.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.openMenuLinks("Личный кабинет")
                .clickOnElement("Раздел Андеррайтинг")
                .waitBusyCondition();
    }

    @AfterEach
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("underwriter_decision_1722093")
    @DisplayName("1722093 - Решение Андеррайтера. Проведенные проверки - 3 значения. На Утверждение")
    @WorkItemIds({"1722093"})
    public void underwriter_decision_1722093() {
        List<String> conductedChecks = List.of("Проверка критичных данных", "Проверка документов", "Проверка долговой нагрузки");
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .waitBusyCondition()
                .selectValueFromDropDownList("Выпадающий список Занятость подтверждена", "Звонок не назначался")
                .fillInput("Поле ввода Внутренний комментарий андеррайтера", "Коментарий")
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .selectValueFromDropDownList("Выпадающий список Проведенные проверки", conductedChecks)
                .checkSelectedChecks(conductedChecks, "с крестиком")
                .selectValueFromDropDownList("Выпадающий список Полномочия", "Собственные")
                .selectValueFromDropDownList("Выпадающий список Одобрить/Отклонить", "Одобрить")
                .selectValueFromDropDownList("Выпадающий список Тип одобрения/Причина отклонения", "Одобрено")
                .clickOnElement("Кнопка На утверждение")
                .switchToOneTab()
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Утверждение")
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .waitBusyCondition()
                .checkSelectedChecks(conductedChecks, "без крестика")
                .clickOnElement("Кнопка Выйти без сохранения")
                .switchToOneTab();
    }

    @Test
    @Tag("underwriter_decision_1722040")
    @DisplayName("1722040 - Проверка доступности редактирования поля \"статус клиента\" на этапе Утверждения")
    @WorkItemIds({"1722040"})
    public void underwriter_decision_1722040() {
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .waitBusyCondition()
                .selectValueFromDropDownList("Выпадающий список Занятость подтверждена", "Звонок не назначался")
                .fillInput("Поле ввода Внутренний комментарий андеррайтера", "Коментарий")
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
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .elementByTitleNotAvailableEditing("Выпадающий список Статус Клиента", "не доступен для редактирования")
                .clickOnElement("Кнопка Взять в работу")
                .waitBusyCondition()
                .elementByTitleNotAvailableEditing("Выпадающий список Статус Клиента", "доступен для редактирования")
                .clickOnElement("Кнопка Выйти без сохранения")
                .switchToOneTab();
    }

    @Test
    @Tag("underwriter_decision_1722060")
    @DisplayName("1722060 - Проверка на то, что выбранный статус клиента не затирается при внутренних переводах заявки по статусам")
    @WorkItemIds({"1722060"})
    public void underwriter_decision_1722060() {
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .waitBusyCondition()
                .selectValueFromDropDownList("Выпадающий список Занятость подтверждена", "Звонок не назначался")
                .fillInput("Поле ввода Внутренний комментарий андеррайтера", "Коментарий")
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .selectValueFromDropDownList("Выпадающий список Проведенные проверки", "Проверка критичных данных")
                .selectValueFromDropDownList("Выпадающий список Полномочия", "Собственные")
                .selectValueFromDropDownList("Выпадающий список Одобрить/Отклонить", "Одобрить")
                .selectValueFromDropDownList("Выпадающий список Тип одобрения/Причина отклонения", "Одобрено")
                .selectValueFromDropDownList("Выпадающий список Статус Клиента", "Учредитель")
                .clickOnElement("Кнопка На утверждение")
                .switchToOneTab()
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Утверждение")
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .checkElementByTitleEquals("Поле Статус клиента", "Учредитель")
                .clickOnElement("Кнопка Взять в работу")
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - На утверждении")
                .selectValueFromDropDownList("Выпадающий список Статус Клиента", "Специалист")
                .fillInput("Поле ввода Комментарий утверждающего", "Коментарий2")
                .clickOnElement("Кнопка На предыдущий этап")
                .switchToOneTab()
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Андеррайтинг")
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - На рассмотрении")
                .checkElementByTitleEquals("Поле Статус клиента", "Специалист")
                .clickOnElement("Кнопка Выйти без сохранения")
                .checkElementByTitleContains("Модальное окно Потеря изменений", "Выполненные изменения будут утеряны. Да\\Нет?")
                .clickOnElement("Кнопка Да на модальном окне")
                .switchToOneTab();
    }

    @Test
    @Tag("underwriter_decision_1722047")
    @DisplayName("1722047 - Решение Андеррайтера. Сохранение определения Сегмента клиента. Зарплатные клиенты с полными зачислениями")
    @WorkItemIds({"1722047"})
    public void underwriter_decision_1722047() {
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .waitBusyCondition()
                .selectValueFromDropDownList("Выпадающий список Занятость подтверждена", "Звонок не назначался")
                .fillInput("Поле ввода Внутренний комментарий андеррайтера", "Коментарий")
                .clickOnElement("Ссылка Автопроверки")
                .switchToNewTab()
                .goTo(autocheckPage)
                .checkElementByTitleContains("Поле Сегмент клиента", "Зарплатные клиенты с полными зачислениями")
                .closeCurrentTab()
                .goTo(cardRequestPage)
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
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .clickOnElement("Кнопка Взять в работу")
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - На утверждении")
                .checkElementByTitleEquals("Поле Сегмент клиента", "Зарплатные клиенты с полными зачислениями")
                .fillInput("Поле ввода Комментарий утверждающего", "Коментарий")
                .clickOnElement("Кнопка Принять решение")
                .switchToOneTab()
                .goTo(loginPage)
                .openMenuLinks("Поиск")
                .goTo(searchPage)
                .searchClaimOnPage(claim)
                .goTo(loginPage)
                .doubleClickByText(claim).switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .checkElementByTitleEquals("Поле Сегмент клиента", "Зарплатные клиенты с полными зачислениями")
                .closeCurrentTab();
    }

    @Test
    @Tag("underwriter_decision_1722052")
    @DisplayName("1722052 - Проверка на то, что выбранный статус клиента не затирается при внешних переводах заявки по статусам (Доработка)")
    @WorkItemIds({"1722052"})
    public void underwriter_decision_1722052() {
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .waitBusyCondition()
                .selectValueFromDropDownList("Выпадающий список Занятость подтверждена", "Звонок не назначался")
                .fillInput("Поле ввода Внутренний комментарий андеррайтера", "Коментарий")
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .selectValueFromDropDownList("Выпадающий список Статус Клиента", "ЛПР")
                .selectValueFromDropDownList("Выпадающий список Проведенные проверки", "Проверка критичных данных")
                .selectValueFromDropDownList("Выпадающий список Причина доработки", "Ошибка МРК при вводе контактных данных клиента (адресов, телефонов)")
                .fillInput("Поле ввода Комментарий МРК и отлагательных условий", "Комментарий МРК")
                .clickOnElement("Кнопка Доработка")
                .waitBusyCondition()
                .switchToOneTab()
                .openMenuLinks("Поиск")
                .goTo(searchPage)
                .searchClaimOnPage(claim);
        String statusClaim = searchPage.getTextFromTable("Таблица результаты поиска", 1, "Статус заявки");
        assertIsTrue(statusClaim.equals("На доработке"),
                "Значение в столбце Статус заявки должно быть равно На доработке. Фактическое значение: " + statusClaim);
        actionsClaimSteps.repeatSendSclRequestToStand("9");
        searchPage.openMenuLinks("Личный кабинет")
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Андеррайтинг")
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .checkElementByTitleEquals("Поле Статус клиента", "ЛПР")
                .clickOnElement("Кнопка Выйти без сохранения")
                .checkElementByTitleContains("Модальное окно Потеря изменений", "Выполненные изменения будут утеряны. Да\\Нет?")
                .clickOnElement("Кнопка Да на модальном окне")
                .switchToOneTab();
    }

    @Test
    @Tag("underwriter_decision_1722049")
    @DisplayName("1722049 - Проверка на то, что выбранные значения в поле проведенных проверок не перезатрутся после Доработки")
    @WorkItemIds({"1722049"})
    public void underwriter_decision_1722049() {
        List<String> allChecks = List.of(
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
        );
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .waitBusyCondition()
                .selectValueFromDropDownList("Выпадающий список Занятость подтверждена", "Звонок не назначался")
                .fillInput("Поле ввода Внутренний комментарий андеррайтера", "Коментарий")
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .selectValueFromDropDownList("Выпадающий список Статус Клиента", "ЛПР")
                .selectValueFromDropDownList("Выпадающий список Проведенные проверки", "Выбрать все")
                .selectValueFromDropDownList("Выпадающий список Причина доработки", "Ошибка МРК при вводе контактных данных клиента (адресов, телефонов)")
                .fillInput("Поле ввода Комментарий МРК и отлагательных условий", "Комментарий МРК")
                .clickOnElement("Кнопка Доработка")
                .waitBusyCondition()
                .switchToOneTab()
                .openMenuLinks("Поиск")
                .goTo(searchPage)
                .searchClaimOnPage(claim);
        String statusClaim = searchPage.getTextFromTable("Таблица результаты поиска", 1, "Статус заявки");
        assertIsTrue(statusClaim.equals("На доработке"),
                "Значение в столбце Статус заявки должно быть равно На доработке. Фактическое значение: " + statusClaim);
        actionsClaimSteps.repeatSendSclRequestToStand("9");
        searchPage.openMenuLinks("Личный кабинет")
                .goTo(personalAccountPage)
                .clickOnElement("Раздел Андеррайтинг")
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .waitBusyCondition()
                .checkSelectedChecks(allChecks, "с крестиком")
                .clickOnElement("Кнопка Выйти без сохранения")
                .checkElementByTitleContains("Модальное окно Потеря изменений", "Выполненные изменения будут утеряны. Да\\Нет?")
                .clickOnElement("Кнопка Да на модальном окне")
                .switchToOneTab();
    }

    @Test
    @Tag("underwriter_decision_1722086")
    @DisplayName("1722086 - Проверка на то, что выбранные значения в поле проведенных проверок не перезатрутся после Доработки")
    @WorkItemIds({"1722086"})
    public void underwriter_decision_1722086() {
        List<String> fewChecks = List.of(
                "Проверка документов",
                "Проверка доход",
                "Звонок контактному лицу/супруге",
                "Проверка долговой нагрузки");
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .waitBusyCondition()
                .selectValueFromDropDownList("Выпадающий список Занятость подтверждена", "Звонок не назначался")
                .fillInput("Поле ввода Внутренний комментарий андеррайтера", "Коментарий")
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .selectValueFromDropDownList("Выпадающий список Проведенные проверки", fewChecks)
                .selectValueFromDropDownList("Выпадающий список Полномочия", "Собственные")
                .selectValueFromDropDownList("Выпадающий список Одобрить/Отклонить", "Одобрить")
                .selectValueFromDropDownList("Выпадающий список Тип одобрения/Причина отклонения", "Одобрено")
                .clickOnElement("Кнопка Принять решение")
                .waitBusyCondition()
                .switchToOneTab()
                .openMenuLinks("Поиск")
                .goTo(searchPage)
                .searchClaimOnPage(claim);
        String statusClaim = searchPage.getTextFromTable("Таблица результаты поиска", 1, "Статус заявки");
        assertIsTrue(statusClaim.equals("Кредит разрешен"),
                "Значение в столбце Статус заявки должно быть равно Кредит разрешен. Фактическое значение: " + statusClaim);
        searchPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Решение Андеррайтера")
                .goTo(underwriterDecisionPage)
                .waitBusyCondition()
                .checkSelectedChecks(fewChecks, "без крестика")
                .closeCurrentTab();
    }
}
