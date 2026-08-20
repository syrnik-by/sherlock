package ru.autotestframework.regress.personal_account.underwriting;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.*;
import ru.autotestframework.BaseTest;
import ru.autotestframework.pages.LoginPage;
import ru.autotestframework.pages.PersonalAccountPage;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.WorkItemIds;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static ru.autotestframework.steps.asserts.Asserts.assertIsTrue;
import static ru.autotestframework.utils.Constants.DF;

@Tag("regress")
@Tag("personal_account")
@Tag("underwriting")
@Tag("underwriting_claim_type_1")
@ClassName("Личный кабинет. Андеррайтинг")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UnderwritingClaimType1Test extends BaseTest {

    private String claim;
    private LocalDateTime nowTime;

    @BeforeAll
    public void createClaim(TestInfo testInfo) {
        claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_1956228.json", 1, testInfo)
                .toString().replaceAll("^\\[|]$", "");
        actionsClaimSteps.appointResponsiblePerson(claim);
    }

    @BeforeEach
    public void login () {
        try {
            loginPage.checkUrlContains("underwriter");
        } catch (ConditionNotMetException e) {
            loginPage.openAuthorizationPage()
                    .loginViaUi()
                    .openMenuLinks("Личный кабинет")
                    .goTo(personalAccountPage)
                    .clickOnElement("Раздел Андеррайтинг");
        }
    }

    @AfterAll
    public void cleanQueueClaims() {
        clearingQueueClaims.requestExpireAfterTestScenario();
    }

    @Test
    @Tag("smoke")
    @Tag("fill_field_1650684")
    @DisplayName("1650684 - Личный кабинет. Андеррайтинг. Заполнение полей для Отложенной заявки")
    @WorkItemIds({"1650684"})
    public void fill_field_1650684() {
        personalAccountPage.doubleClickByText(claim)
                .switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Кнопка Отложить")
                .waitText(10, "Перевод заявки в отложенные")
                .selectValueFromDropDownList("Выпадающий список Причина (Перевод заявки в отложенные)", "Недозвон Заемщику")
                .fillInput("Поле ввода комментарий (Перевод заявки в отложенные)", "Перевод заявки в отложенные АТ")
                .fillInput("Поле ввода Время для звонка участнику", getTimeNow().plusMinutes(7).format(DF))
                .clickOnElement("Кнопка Отложить заявку")
                .switchToOneTab()
                .goTo(personalAccountPage).waitBusyCondition()
                .clickOnElement("Кнопка раскрыть таблицу Отложено")
                .clickOnElement("Кнопка Настройка списка(таблица Отложено)")
                .goTo(filterListSettingsPage)
                .dragColumns("из правой колонки в левую",
                        List.of("Перевод в отложенные",
                                "Возврат из отложенных"))
                .clickOnElement("Кнопка Закрыть окно фильтров");
        assertIsTrue(personalAccountPage.getTextFromTable("Таблица Отложено", 1, "Перевод в отложенные").
                        equals(nowTime.format(DF)),
                "Значение в столбце Перевод в отложенные должно быть равно " + nowTime.format(DF));
        assertIsTrue(personalAccountPage.getTextFromTable("Таблица Отложено", 1, "Возврат из отложенных").
                        equals(nowTime.plusMinutes(2).format(DF)),
                "Значение в столбце Возврат из отложенных должно быть равно " + nowTime.plusMinutes(2).format(DF));

        personalAccountPage
                .checkNotifications()
                .clickOnElement("Кнопка Настройка списка(таблица В работе)")
                .goTo(filterListSettingsPage)
                .resetFilters();
    }

    private LocalDateTime getTimeNow() {
        nowTime = LocalDateTime.now();
        return nowTime;
    }

}