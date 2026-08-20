package ru.autotestframework;

import com.codeborne.selenide.WebDriverRunner;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import ru.autotestframework.configuration.FrameworkDefaultVariables;
import ru.autotestframework.pages.*;
import ru.autotestframework.pages.card_request.*;
import ru.autotestframework.pages.card_request.result_checks.ManualChecks;
import ru.autotestframework.pages.card_request.result_checks.ResultCheck;
import ru.autotestframework.pages.card_request.system_reports_block.*;
import ru.autotestframework.pages.card_request.verification.*;
import ru.autotestframework.pages.employees.CardEmployeePage;
import ru.autotestframework.pages.employees.ListEmployeesPage;
import ru.autotestframework.pages.employees.WorkSchedulesPage;
import ru.autotestframework.pages.monitoring.ActionsInSystemPage;
import ru.autotestframework.pages.monitoring.ActionsRequestsPage;
import ru.autotestframework.pages.setting_control.AssignmentStrategyConfiguration;
import ru.autotestframework.pages.setting_control.ScriptConfigurationPage;
import ru.autotestframework.pages.settings.CommonSystemSettingsPage;
import ru.autotestframework.pages.settings.PrioritySettingsPage;
import ru.autotestframework.steps.dbApiSteps.ActionsClaimSteps;
import ru.autotestframework.steps.dbApiSteps.DbSteps;
import ru.autotestframework.ui_core.junit.BaseUITest;
import ru.autotestframework.utils.Attachments;
import ru.autotestframework.utils.ClearingQueueClaims;


@RequiredArgsConstructor
@Configuration
@ExtendWith(Attachments.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseTest extends BaseUITest {

    @Autowired
    protected ActionsClaimSteps actionsClaimSteps;

    @Autowired
    protected DbSteps dbSteps;

    @Autowired
    public FrameworkDefaultVariables defaultVariables;

    @Autowired
    public ClearingQueueClaims clearingQueueClaims;

    protected LoginPage loginPage;
    protected ManualChecks manualChecks;
    protected PersonalAccountPage personalAccountPage;
    protected FilterListSettingsPage filterListSettingsPage;
    protected CheckingOpenSourcesPage checkingOpenSourcesPage;
    protected FsspPage fsspPage;
    protected SearchPage searchPage;
    protected IncomeVerificationPage incomeVerificationPage;
    protected CustomerCallPage customerCallPage;
    protected CallContactPersonSpoursePage callContactPersonSpoursePage;
    protected CallingEmployerConfirmedPhoneRequiredPage callingEmployerConfirmedPhoneRequiredPage;
    protected CallingEmployerAnyPhoneRequiredOneClaimPage callingEmployerAnyPhoneRequiredOneClaimPage;
    protected CallingEmployerAnyPhonePage callingEmployerAnyPhonePage;
    protected CallingEmployerConfirmedPhonePage callingEmployerConfirmedPhonePage;
    protected CheckingPreviousClaimsPage checkingPreviousClaimsPage;
    protected VerificationOpmEmployeesPage verificationOpmEmployeesPage;
    protected L0CheckingDocumentsPage l0CheckingDocumentsPage;
    protected CardRequestPage cardRequestPage;
    protected UnderwriterDecisionPage underwriterDecisionPage;
    protected ResultCheck resultCheck;
    protected QueuesPage queuesPage;
    protected AutocheckPage autocheckPage;
    protected VerificationStrategyPage verificationStrategyPage;
    protected IdealCiPage idealCiPage;
    protected AntiFraudPage antiFraudPage;
    protected QuestionInGoPage questionInGoPage;
    protected ActionsRequestsPage actionsRequestsPage;
    protected ActionsInSystemPage actionsInSystemPage;
    protected ListEmployeesPage listEmployeesPage;
    protected CardEmployeePage cardEmployeePage;
    protected HistoryPage historyPage;
    protected AdditionalInformation additionalInformation;
    protected PreviousClaimsPage previousClaimsPage;
    protected WorkSchedulesPage workSchedulesPage;
    protected ScriptConfigurationPage scriptConfigurationPage;
    protected AssignmentStrategyConfiguration assignmentStrategyConfiguration;
    protected CommonSystemSettingsPage commonSystemSettingsPage;
    protected PrioritySettingsPage prioritySettingsPage;
    protected ApplicationDocumentsPage applicationDocumentsPage;
    protected ChangingFieldsPage changingFieldsPage;

    private void initializePages() {
        loginPage = new LoginPage(defaultVariables);
        manualChecks = new ManualChecks();
        personalAccountPage = new PersonalAccountPage();
        filterListSettingsPage = new FilterListSettingsPage();
        checkingOpenSourcesPage = new CheckingOpenSourcesPage();
        fsspPage = new FsspPage();
        searchPage = new SearchPage();
        incomeVerificationPage = new IncomeVerificationPage();
        l0CheckingDocumentsPage = new L0CheckingDocumentsPage();
        customerCallPage = new CustomerCallPage();
        callContactPersonSpoursePage = new CallContactPersonSpoursePage();
        callingEmployerConfirmedPhoneRequiredPage = new CallingEmployerConfirmedPhoneRequiredPage();
        callingEmployerAnyPhoneRequiredOneClaimPage = new CallingEmployerAnyPhoneRequiredOneClaimPage();
        callingEmployerConfirmedPhonePage = new CallingEmployerConfirmedPhonePage();
        callingEmployerAnyPhonePage = new CallingEmployerAnyPhonePage();
        checkingPreviousClaimsPage = new CheckingPreviousClaimsPage();
        verificationOpmEmployeesPage = new VerificationOpmEmployeesPage();
        cardRequestPage = new CardRequestPage();
        underwriterDecisionPage = new UnderwriterDecisionPage();
        resultCheck = new ResultCheck();
        queuesPage = new QueuesPage();
        autocheckPage = new AutocheckPage();
        verificationStrategyPage = new VerificationStrategyPage();
        questionInGoPage = new QuestionInGoPage();
        idealCiPage = new IdealCiPage();
        antiFraudPage = new AntiFraudPage();
        actionsRequestsPage = new ActionsRequestsPage();
        listEmployeesPage = new ListEmployeesPage();
        cardEmployeePage = new CardEmployeePage();
        historyPage = new HistoryPage();
        additionalInformation = new AdditionalInformation();
        actionsInSystemPage = new ActionsInSystemPage();
        previousClaimsPage = new PreviousClaimsPage();
        workSchedulesPage = new WorkSchedulesPage();
        scriptConfigurationPage = new ScriptConfigurationPage();
        assignmentStrategyConfiguration = new AssignmentStrategyConfiguration();
        commonSystemSettingsPage = new CommonSystemSettingsPage();
        prioritySettingsPage = new PrioritySettingsPage();
        applicationDocumentsPage = new ApplicationDocumentsPage();
        changingFieldsPage = new ChangingFieldsPage();
    }

    @BeforeAll
    @Order(0)
    public void startUp() {
        super.startUp();
        initializePages();
    }

    @BeforeEach
    @Order(0)
    public void checkSessionActive() {
        if (!isSessionActive()) {
            restartDriver(); // перезапускаем драйвер
        }
    }

    @AfterAll
    public void teardown() {
        super.teardown();
    }

    private void restartDriver() {
        if (WebDriverRunner.hasWebDriverStarted()) {
            WebDriverRunner.closeWebDriver();
        }
        startUp();
    }

    private boolean isSessionActive() {
        try {
            if (!WebDriverRunner.hasWebDriverStarted()) {
                return false;
            }
            WebDriver driver = WebDriverRunner.getWebDriver();
            return !driver.getWindowHandles().isEmpty();
        } catch (WebDriverException | IllegalStateException e) {
            return false;
        }
    }
}