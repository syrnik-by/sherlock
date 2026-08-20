package ru.autotestframework.regress.card_request.calculated_checks;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import java.util.List;

@Tag("regress")
@Tag("card_request")
@Tag("calculated_checks_separate_claim")
@ClassName("На каждый кейс отдельная заявка. Карточка заявки. Рассчитанные проверки")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CalculatedChecksSeparateClaimTest extends BaseTest {

    private String claim;

    @BeforeEach
    public void login() {
        try {
            loginPage.checkUrlContains("application-queues");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi()
                    .openMenuLinks("Очереди")
                    .goTo(queuesPage);
        }
    }

    @AfterEach
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("smoke")
    @Tag("check_block_3208009")
    @DisplayName("3208009 - Верификация. Просмотр блока  \"Верификация рассчитанные проверки\" отображение поля \"Причина назначения\"  ")
    @WorkItemIds({"3208009"})
    public void check_block_3208009(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3862081.json", 1, testInfo).get(0);
        queuesPage
                .searchClaimOnPage(claim)
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .clickOnElement("Кнопка Основные данные").switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Вкладка Результаты проверок").switchToNewTab()
                .goTo(resultCheck)
                .clickOnElement("Вкладка Ручные проверки")
                .goTo(manualChecks)
                .clickOnElement("Кнопка Верификация рассчитанные проверки")

                .checkContainsTableHeaders("Таблица Верификация рассчитанные проверки",
                        List.of("Версия набора",
                                "Версия проверки",
                                "Время назначения стратегии",
                                "Пройдена",
                                "Приоритет",
                                "Наименование стратегии",
                                "Участник сделки",
                                "Работодатель",
                                "Причина назначения",
                                "Причина исключения",
                                "Доб. автомат.",
                                "Удал. автомат.",
                                "Необяз.",
                                "Добавлена ОПМ/ГО/Андер. ГО",
                                "Пройти после ОПМ/ГО",
                                "Финальная проверка",
                                "Результат проверки",
                                "Маршрутизация",
                                "Итоговый результат группы",
                                "Ссылка"))
                .closeCurrentTab().closeCurrentTab();
    }

}
