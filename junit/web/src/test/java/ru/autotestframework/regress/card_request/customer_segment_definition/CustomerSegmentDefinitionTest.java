package ru.autotestframework.regress.card_request.customer_segment_definition;

import com.codeborne.selenide.ex.ConditionNotMetException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.autotestframework.BaseTest;
import ru.psb.testit.annotations.ClassName;
import ru.psb.testit.annotations.DisplayName;
import ru.psb.testit.annotations.ExternalId;
import ru.psb.testit.annotations.WorkItemIds;

import java.util.Map;

@Tag("regress")
@Tag("card_request")
@Tag("customer_segment_definition")
@ClassName("Карточка заявки. Определение сегмента клиента. На каждый кейс отдельная заявка")
public class CustomerSegmentDefinitionTest extends BaseTest {

    @BeforeAll
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

    @ParameterizedTest
    @CsvSource(value = {
            "2516846, Пенсионеры(клиент), NO, null, null, Non_Workers_Retiree",
            "2517677, Пенсионеры(работодатель), NO, Non_Workers_Retiree, null, Comp_Rel_Corp",
            "2518010, Зарплатный клиент с полными зачислениями военнослужащий, FormSpravType5, Client_Salary, Comp_Type_OPK_Siloviki_OKOGU, null",
            "2518121, Зарплатные клиенты с полными зачислениями, ConfirmedIncomeForm4, Client_Salary_Early_Macro, null, null",
            "2518166, Новый зарплатный клиент военнослужащий, NO, Comp_Type_OPK_Siloviki_Lex, null, Client_Salary_New",
            "2518167, Новые зарплатные клиенты, NO, Client_Salary_Early_Spec, null, null",
            "2518170, Частичный зарплатный клиент военнослужащий, NO, Pos_War_NIS, null, Client_Salary",
            "2518185, Частичные зарплатные клиенты, NO, Client_Salary, null, null",
            "2518196, Прочие ОПК, NO, null, null, Comp_Type_TOP_OPK",
            "2518204, Госслужащие, NO, null, null, Comp_Type_Public_Servant_WSOpen",
            "2518209, Крупные работодатели, NO, Comp_Type_Big_earn, null, null",
            "2518216, Корпоративные клиенты, NO, Comp_Rel_Corp, null, null",
            "2518224, Прочие клиенты, NO, null, null, null"
    })
    @Tag("smoke")
    @Tag("customer_segment_definition_test")
    @DisplayName("{id} - Определение сегмента клиента - {displayName}")
    @WorkItemIds({"{id}"})
    @ExternalId("{id}")
    public void customer_segment_definition_test(String id, String displayName,
                                                 String incomeMain, String kpMainOne,
                                                 String kpMainTwo, String kpClient,
                                                 TestInfo testInfo) {
        Map<String, String> claimParams = Map.of(
                "incomeMain", incomeMain,
                "kpMainOne", kpMainOne,
                "kpMainTwo", kpMainTwo,
                "kpClient", kpClient);
        String claim = actionsClaimSteps.sendSclRequestToStandWithSpecifiedJson("data/json/claim_template_2516792.json", 1, testInfo, claimParams).get(0);
        queuesPage
                .searchClaimOnPage(claim)
                .doubleClickByText(claim)
                .switchToNewTab().waitBusyCondition()
                .goTo(fsspPage)
                .checkElementByTitleEquals("Поле Наименование стратегии", "ФССП/Версия 1")
                .clickOnElement("Кнопка Основные данные").switchToNewTab()
                .goTo(cardRequestPage)
                .clickOnElement("Ссылка Автопроверки")
                .switchToNewTab()
                .goTo(autocheckPage)
                .checkElementByTitleContains("Поле Сегмент клиента", displayName.contains("(")
                        ? displayName.substring(0, displayName.indexOf('('))
                        : displayName)
                .closeCurrentTab().closeCurrentTab().closeCurrentTab();
    }
}
