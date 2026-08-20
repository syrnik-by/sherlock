package ru.autotestframework.regress.card_request.verification.button_return_to_queue;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.ExternalId;
import ru.psb.testit.annotations.WorkItemIds;

import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;

@Tag("regress")
@Tag("verification")
@Tag("card_request")
@Tag("button_return_to_queue_separate_claim")
@ClassName("Карточка заявки. Верификация. Кнопка \"Вернуть в очередь\". На каждый кейс отдельная азявка")
public class ButtonReturnToQueueSeparateClaimTest extends BaseTest {

    private String claim;

    @BeforeEach
    public void login(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2056882.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
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

    @ParameterizedTest
    @CsvSource({
            "2653178, Очереди, Вопрос в ГО, Методологический, Вопрос в ГО, Ожидает",
            "2653183, Очереди, Отправить на Antifraud, Внутреннее мошенничество, Проверка сотрудниками ОПМ, Ожидает",
            "2653182, Поиск, Вопрос в ГО, Методологический, Вопрос в ГО, Ожидает",
            "2653185, Поиск, Отправить на Antifraud, Внутреннее мошенничество, Проверка сотрудниками ОПМ, Ожидает"
    })
    @Tag("button_return_to_queue_no_active_separate_claim")
    @DisplayName("{id} - Кнопка \"Вернуть в очередь\" неактивна на стратегии \"{strategy}\" на вкладке \"{tabName}\" " +
            "Статус \"{statusClaim}\"")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void button_return_to_queue_no_active_separate_claim(String id, String tabName, String resultOfClaim,
                                                                String questionType, String strategy, String statusClaim) {
        personalAccountPage
                .openMenuLinks("Личный кабинет")
                .waitBusyCondition()
                .doubleClickByText(claim).switchToNewTab().waitBusyCondition()
                .goTo(fsspPage)
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", resultOfClaim)
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", questionType)
                .fillInput("Поле ввода Комментарий", "коммент")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .switchToOneTab()
                .waitBusyCondition()
                .openMenuLinks(tabName)
                .goTo(tabName.equals("Очереди") ? queuesPage : searchPage)
                .waitBusyCondition()
                .searchClaimOnPage(claim)
                .doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", strategy + "/Версия 1")
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", statusClaim)
                .assertElementByTitleActivity("Кнопка Вернуть в очередь", "не активен")
                .closeCurrentTab();
    }

    @ParameterizedTest
    @CsvSource({
            "2653181, Очереди, Вопрос в ГО, Методологический, Вопрос в ГО, На рассмотрении",
            "2653176, Очереди, Отправить на Antifraud, Внутреннее мошенничество, Проверка сотрудниками ОПМ, На рассмотрении",
            "2653179, Поиск, Вопрос в ГО, Методологический, Вопрос в ГО, На рассмотрении",
            "2653188, Поиск, Отправить на Antifraud, Внутреннее мошенничество, Проверка сотрудниками ОПМ, На рассмотрении"
    })
    @Tag("button_return_to_queue_no_active_separate_claim_2")
    @DisplayName("{id} - Кнопка \"Вернуть в очередь\" неактивна на стратегии \"{strategy}\" на вкладке \"{tabName}\" " +
            "Статус \"{statusClaim}\"")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void button_return_to_queue_no_active_separate_claim_2(String id, String tabName, String resultOfClaim,
                                                                  String questionType, String strategy, String statusClaim) {
        personalAccountPage
                .openMenuLinks("Личный кабинет")
                .waitBusyCondition()
                .doubleClickByText(claim).switchToNewTab().waitBusyCondition()
                .goTo(fsspPage)
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", resultOfClaim)
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", questionType)
                .fillInput("Поле ввода Комментарий", "коммент")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .switchToOneTab()
                .waitBusyCondition();
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage.openMenuLinks(tabName)
                .goTo(tabName.equals("Очереди") ? queuesPage : searchPage)
                .waitBusyCondition()
                .searchClaimOnPage(claim)
                .doubleClickByText(claim).switchToNewTab()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", strategy + "/Версия 1")
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", statusClaim)
                .assertElementByTitleActivity("Кнопка Вернуть в очередь", "не активен")
                .closeCurrentTab();
    }

    @ParameterizedTest
    @CsvSource({
            "2653177, Вопрос в ГО, Методологический, Вопрос в ГО, Вопрос решен",
            "2653175, Отправить на Antifraud, Внутреннее мошенничество, Проверка сотрудниками ОПМ, Негатив опровергнут, продолжить рассмотрение заявки"
    })
    @Tag("button_return_to_queue_active {id}")
    @DisplayName("{id} - Кнопка \"Вернуть в очередь\" активна после возврата с \"{tabName}\"")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void button_return_to_queue_active(String id, String resultOfClaim, String questionType,
                                              String chapter, String verificationResult) {
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage
                .openMenuLinks("Личный кабинет")
                .waitBusyCondition()
                .doubleClickByText(claim).switchToNewTab()
                .goTo(cardRequestPage)
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - На рассмотрении")
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .assertElementByTitleActivity("Кнопка Вернуть в очередь", "активен")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", resultOfClaim)
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", questionType)
                .fillInput("Поле Комментарий", "Коммент АТ")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку").switchToOneTab().waitBusyCondition();
        actionsClaimSteps.appointResponsiblePerson(claim);
        personalAccountPage
                .openMenuLinks("Личный кабинет")
                .waitBusyCondition()
                .clickOnElement("Раздел " + chapter);
        assertIsTrue(personalAccountPage.getTextFromTable("Таблица в работе", 1, "Статус заявки").
                        equals("На рассмотрении"),
                "Значение в столбце Статус заявки должно быть На рассмотрении");
        personalAccountPage.doubleClickByText(claim).switchToNewTab()
                .goTo(questionInGoPage)
                .selectValueFromDropDownList("Выпадающий список Результат проверки", verificationResult)
                .fillInput("Поле Комментарий", "Коммент АТ");
        if (id.equals("2653175")) {
            verificationOpmEmployeesPage.fillInput("Поле Комментарий ОПМ", "Коммент АТ ОПМ");
        }
        questionInGoPage
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку").switchToOneTab().waitBusyCondition()
                .goTo(personalAccountPage)
                .openMenuLinks("Личный кабинет")
                .waitBusyCondition()
                .clickOnElement("Раздел Верификация")
                .doubleClickByText(claim).switchToNewTab()
                .goTo(cardRequestPage)
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - На рассмотрении")
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 2")
                .goTo(cardRequestPage)
                .assertElementByTitleActivity("Кнопка Вернуть в очередь", "активен")
                .clickOnElement("Кнопка Вернуть в очередь")
                .checkElementByTitleContains("Модальное окно предупреждения", "Заявка будет возвращена в очередь!\n" +
                        "Выполненные изменения будут утеряны. Да\\Нет?")
                .clickOnElement("Кнопка Да модального окна").switchToOneTab().waitBusyCondition();
        actionsClaimSteps.checkStatusClaimFromDb(claim, 3);
    }

    @ParameterizedTest
    @CsvSource({
            "2653174, Вопрос в ГО, Методологический, Вопрос в ГО",
            "2653173, Отправить на Antifraud, Внутреннее мошенничество, Проверка сотрудниками ОПМ"
    })
    @Tag("button_return_to_queue_no_active_separate_claim")
    @DisplayName("{id} - Кнопка \"Вернуть в очередь\" - активность на стратегии \"{chapter}\" в ЛК. Статус \"Ожидает\" / На рассмотрении")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void button_return_to_queue_active_chapter_awaiting(String id, String resultOfClaim, String questionType, String chapter) {
        personalAccountPage
                .openMenuLinks("Личный кабинет")
                .clickOnElement("Раздел Верификация")
                .waitBusyCondition()
                .doubleClickByText(claim).switchToNewTab().waitBusyCondition()
                .goTo(fsspPage)
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", resultOfClaim)
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", questionType)
                .fillInput("Поле ввода Комментарий", "Коммент АТ")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку").switchToOneTab().waitBusyCondition()
                .goTo(personalAccountPage)
                .openMenuLinks("Личный кабинет")
                .waitBusyCondition()
                .clickOnElement("Раздел " + chapter)
                .doubleClickByText(claim).switchToNewTab().waitBusyCondition()
                .goTo(cardRequestPage)
                .checkElementByTitleContains("Информация по заявке (Дата и Статус)", "Статус - Ожидает")
                .goTo(questionInGoPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", chapter + "/Версия 1")
                .goTo(cardRequestPage)
                .assertElementByTitleActivity("Кнопка Вернуть в очередь", "не активен")
                .clickOnElement("Кнопка Взять в работу")
                .assertElementByTitleActivity("Кнопка Вернуть в очередь", "активен")
                .clickOnElement("Кнопка Вернуть в очередь")
                .checkElementByTitleContains("Модальное окно предупреждения", "Заявка будет возвращена в очередь!\n" +
                        "Выполненные изменения будут утеряны. Да\\Нет?")
                .clickOnElement("Кнопка Да модального окна").switchToOneTab().waitBusyCondition();
        actionsClaimSteps.checkStatusClaimFromDb(claim, 3);
    }
}
