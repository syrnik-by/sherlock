package ru.autotestframework.regress.card_request.verification.strategies_verification.go_card_ef;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.autotestframework.pages.card_request.verification.VerificationStrategyPage;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import java.util.List;

import static ru.autotestframework.steps.asserts.Asserts.assertContains;

@Tag("regress")
@Tag("card_request")
@Tag("verification")
@Tag("go_card_ef")
@Tag("on_claim_2_go_employer_verification")
@ClassName("Карточка заявки. Верификация. ЭФ стратегий верификации. ГО карточка ЭФ. На заявке Тип №2 ГО с Проверки работодателя")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OnClaim2GoEmployerVerificationTest extends BaseTest {
    private String claim;

    @BeforeEach
    public void login(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_3207088.json", 1, testInfo).get(0);
        actionsClaimSteps.appointResponsiblePerson(claim);
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi()
                    .openMenuLinks("Личный кабинет")
                    .goTo(personalAccountPage);
        }
        navigateToVerificationStrategyPage();
    }

    private VerificationStrategyPage navigateToVerificationStrategyPage() {
        return personalAccountPage.clickOnElement("Раздел Верификация").waitBusyCondition()
                .doubleClickByText(claim)
                .switchToNewTab()
                .goTo(verificationStrategyPage)
                .waitBusyCondition();
    }

    @AfterAll
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("data_display_main_work_place_3208423")
    @DisplayName("3208423 - Экранная форма стратегии \"Вопрос в ГО\". Отображение данных в степпере при отправке со стратегии объекта Основное место работы")
    @WorkItemIds({"3208423"})
    public void data_display_main_work_place_3208423() {
        verificationStrategyPage
                .elementByTitleContains("Поле Наименование стратегии", "Проверка открытых источников")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Методологический")
                .fillInput("Поле Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .switchToOneTab()
                .elementByTitleVisibility("Раздел Вопрос в ГО", "отображается")
                .clickOnElement("Раздел Вопрос в ГО")
                .doubleClickByText(claim)
                .goTo(questionInGoPage)
                .switchToNewTab()
                .elementByTitleContains("Поле Наименование стратегии", "Вопрос в ГО");
        List<String> values = questionInGoPage.getListValuesByColumnName("Таблица Степпер", "Объект проверки");
        assertContains(values.toString(), "Заемщик - Романов Юрий Иванович 05.12.1990");
        questionInGoPage.closeCurrentTab();
    }

    @Test
    @Tag("data_display_open_sources_3208428")
    @DisplayName("3208428 - Экранная форма стратегии \"Вопрос в ГО\". Отображение в поле \"Стратегия\". Открытые источники")
    @WorkItemIds({"3208428"})
    public void data_display_open_sources_3208428() {
        verificationStrategyPage.elementByTitleContains("Поле Наименование стратегии", "Проверка открытых источников")
                .elementByTitleContains("Поле Проверка работадателя", "(Проверить!)")
                .selectValueFromDropDownList("Выпадающий список Результат по заявке", "Вопрос в ГО")
                .selectValueFromDropDownList("Выпадающий список Тип вопроса", "Методологический")
                .fillInput("Поле Комментарий", "test")
                .clickOnElement("Кнопка Далее")
                .clickOnElement("Кнопка Завершить проверку")
                .goTo(personalAccountPage)
                .switchToOneTab()
                .elementByTitleVisibility("Раздел Вопрос в ГО", "отображается")
                .clickOnElement("Раздел Вопрос в ГО")
                .doubleClickByText(claim)
                .goTo(questionInGoPage)
                .switchToNewTab()
                .elementByTitleContains("Поле Наименование стратегии", "Вопрос в ГО");
        List<String> values = questionInGoPage.getListValuesByColumnName("Таблица Степпер", "Стратегия");
        assertContains(values.toString(), "Проверка открытых источников");
        questionInGoPage.closeCurrentTab();
    }
}
