package ru.autotestframework.pages.card_request;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import ru.autotestframework.pages.components.LeftBar;
import ru.autotestframework.ui_core.page_manager.Element;
import ru.autotestframework.ui_core.page_manager.PageEntry;
import ru.autotestframework.ui_core.services.table_service.FindCellsBy;
import ru.autotestframework.ui_core.services.table_service.FindHeadersBy;
import ru.autotestframework.web_elements.elements.*;
import ru.autotestframework.web_elements.elements.typified.TypifiedWebElement;
import ru.psb.testit.annotations.Description;
import ru.psb.testit.annotations.Step;
import ru.psb.testit.annotations.Title;

import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.$$x;
import static org.hamcrest.core.IsEqual.equalTo;
import static ru.autotestframework.util.Validator.assertThat;

@PageEntry(title = "Страница вкладки Решение Андеррайтера")
public class UnderwriterDecisionPage extends LeftBar<UnderwriterDecisionPage> {

    @Element("Кнопка Основные данные")
    @FindBy(xpath = "//span[contains(text(), 'Основные данные')]")
    public Button basicDataButton;

    @Element("Выпадающий список Причина доработки")
    @FindBy(xpath = "//div[./span[contains(text(), 'Причина доработки')]]//mat-select")
    public Button listReasonForRevisionButton;

    @Element("Поле ввода Комментарий МРК и отлагательных условий")
    @FindBy(xpath = "//textarea[@placeholder='Комментарий МРК и отлагательных условий']")
    public TextInput textareaCommentMkrTextInput;

    @Element("Поле ввода Внутренний комментарий андеррайтера")
    @FindBy(xpath = "//textarea[@placeholder='Внутренний комментарий андеррайтера']")
    public TextInput textareaCommentUnderwriterTextInput;

    @Element("Кнопка История(Комментарий МРК)")
    @FindBy(xpath = "//div[contains(@class,'conclusion-chooseReason')]//button[./span[contains(text(), 'История')]]")
    public Button historyCommentMkrButton;

    @Element("Кнопка История(Комментарий андеррайтера)")
    @FindBy(xpath = "//div[contains(@class,'conclusion-chooseReason')]//button[./span[contains(text(), 'История')]]")
    public Button historyCommentUnderwriterButton;

    @Element("Блок Доля страховки / созаемщики")
    @FindBy(xpath = "//div[contains(@class,'desition-form-wrapper')][./span[contains(text(), '100%')]][./div[contains(text(), '')]]")
    public TextBlock blockShareInsuranceTextBlock;

    @Element("Чек-бокс массового выбора")
    @FindBy(xpath = "//table//th[1]//mat-checkbox[.//input[contains(@class,'mat-checkbox-input')]]")
    public ClassicCheckBox chooseAllCheckBox;

    @Element("Информация по заявке (Дата и Статус)")
    @FindBy(xpath = "//span[contains(text(),  'Статус')]")
    public TextBlock infoDateStatusTextBlock;

    @Element("Таблица Подбор решений")
    @FindBy(xpath = "//app-desitions-table")
    @FindCellsBy(xpath = ".//td[@role='cell']")
    @FindHeadersBy(xpath = ".//th[@role='columnheader']")
    public WebTable solutionSelectionTable;

    @Element("Кнопка Доработка")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Доработка')]]")
    public Button refinementButton;

    @Element("Кнопка Сохранить")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Сохранить')]]")
    public Button saveButton;

    @Element("Кнопка Принять решение")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Принять решение')]]")
    public Button decideButton;

    @Element("Кнопка Сформировать заключение")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Сформировать заключение')]]")
    public Button formConclusionButton;

    @Element("Кнопка На утверждение")
    @FindBy(xpath = "//button[.//span[contains(text(), 'На утверждение')]]")
    public Button forApprovalButton;

    @Element("Кнопка На утверждение в ГО")
    @FindBy(xpath = "//button[.//span[contains(text(), 'На утверждение в ГО')]]")
    public Button forApprovalGOButton;

    @Element("Кнопка На предыдущий этап")
    @FindBy(xpath = "//button[.//span[contains(text(), 'На предыдущий этап')]]")
    public Button previousStepButton;

    @Element("Кнопка Сохранить и закрыть")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Сохранить и закрыть')]]")
    public Button saveCloseButton;

    @Element("Кнопка Выйти без сохранения")
    @FindBy(xpath = "//button[.//span[contains(text(), 'Выйти без сохранения')]]")
    public Button exitWithoutSavingButton;

    @Element("Чек-бокс Звонок клиенту")
    @FindBy(xpath = "//span[contains(text(), ' Звонок клиенту ')]/..//..//label")
    public ClassicCheckBox callToClientCheckBox;

    @Element("Чек-бокс Звонок арендодателю соседняя организация")
    @FindBy(xpath = "//span[contains(text(), ' Звонок арендодателю соседняя организация ')]/..//..//label")
    public ClassicCheckBox callingLandlordCheckBox;

    @Element("Чек-бокс Звонок супруге/конт.лицу")
    @FindBy(xpath = "//span[contains(text(), ' Звонок супруге/конт.лицу ')]/..//..//label")
    public ClassicCheckBox callSpouseContactPersonCheckBox;

    @Element("Чек-бокс Наличие проверок по совместительству")
    @FindBy(xpath = "//span[contains(text(), ' Наличие проверок по совместительству ')]/..//..//label")
    public ClassicCheckBox availabilityConcurrentAuditsCheckBox;

    @Element("Чек-бокс Звонок работодателю")
    @FindBy(xpath = "//span[contains(text(), ' Звонок работодателю ')]/..//..//label")
    public ClassicCheckBox callEmployerCheckBox;

    @Element("Чек-бокс Проверка более 1 созаемщика с доходом")
    @FindBy(xpath = "//span[contains(text(), ' Проверка более 1 созаемщика с доходом ')]/..//..//label")
    public ClassicCheckBox checkingMore1BorrowerCheckBox;

    @Element("Выпадающий список Тип заявки")
    @FindBy(xpath = "//div[./span[contains(text(), 'Тип заявки')]]//mat-select")
    public TextBlock applicationTypeDropDown;

    @Element("Выпадающий список Тип заявки (Созаемщик)")
    @FindBy(xpath = "//div//span[text() = 'Созаемщик']//..//..//span[contains(text(), 'Тип заявки')]/..//mat-select")
    public Button applicationTypeCoBorrowerDropDown;

    @Element("Выпадающий список Проведенные проверки")
    @FindBy(xpath = "//div[./span[contains(text(), 'Проведенные проверки')]]//mat-select")
    public Button checkCarriedOutDropDown;

    @Element("Выпадающий список Проведенные проверки (Созаемщик)")
    @FindBy(xpath = "//div//span[text() = 'Созаемщик']//..//..//span[text() =  ' Проведенные проверки ']/..//mat-form-field")
    public Button checkCarriedOutCoBorrowerDropDown;

    @Element("Выпадающий список Сегмент клиента")
    @FindBy(xpath = "//div[./span[contains(text(), 'Сегмент клиента')]]//mat-select")
    public Button customerSegmentDropDown;

    @Element("Выпадающий список Статус Клиента")
    @FindBy(xpath = "//div[./span[contains(text(), 'Статус Клиента')]]//mat-select")
    public Button customerStatusDropDown;

    @Element("Выпадающий список Полномочия")
    @FindBy(xpath = "//div[./p[contains(text(), 'Полномочия')]]//mat-select")
    public Button powersDropDown;

    @Element("Выпадающий список Одобрить/Отклонить")
    @FindBy(xpath = "//div[./p[contains(text(), 'Одобрить/Отклонить')]]//mat-select")
    public Button approveRejectDropDown;

    @Element("Выпадающий список Тип одобрения/Причина отклонения")
    @FindBy(xpath = "//div[./p[contains(text(), 'Тип одобрения/Причина отклонения')]]//mat-select")
    public Button typeApprovalReasonRejectionDropDown;

    @Element("Поле ввода Дата обработки")
    @FindBy(xpath = "//div[./p[contains(text(), 'Дата обработки')]]//div[contains(@class, 'text-element')]")
    public TextInput dateProcessingTextInput;

    @Element("Поле ввода Андеррайтер")
    @FindBy(xpath = "//div[./p[contains(text(), 'Андеррайтер')]]//div[contains(@class, 'text-element')]")
    public TextInput underwriterTextInput;

    @Element("Поле ввода Дата утверждения")
    @FindBy(xpath = "//div[./p[contains(text(), 'Дата утверждения')]]//div[contains(@class, 'text-element')]")
    public TextInput approvalTateTextInput;

    @Element("Поле ввода Утверждающий")
    @FindBy(xpath = "//div[./p[contains(text(), 'Утверждающий')]]//div[contains(@class, 'text-element')]")
    public TextInput approverTextInput;

    @Element("Поле ввода Комментарий утверждающего")
    @FindBy(xpath = "//div[./p[contains(text(), 'Комментарий утверждающего')]]//textarea")
    public TextInput approverCommentTextInput;

    @Element("Поле Сегмент клиента")
    @FindBy(xpath = "//div[./span[contains(text(), 'Сегмент клиента')]]//span[contains(@class, 'min-line')]")
    public TextBlock customerSegmentTextBlock;

    @Element("Поле Статус клиента")
    @FindBy(xpath = "//div[./span[contains(text(), 'Статус Клиента')]]//span[contains(@class, 'min-line')]")
    public TextBlock clientStatusTextBlock;

    @Element("Кнопка редактировать 1 строку таблцы Подбор решений")
    @FindBy(xpath = "(//app-desitions-table//mat-icon[@data-mat-icon-name='edit'])[1]")
    public Button editFirstRowButton;

    @Element("Поле ввода Ставка, % 1 строки таблицы Подбор решений")
    @FindBy(xpath = "(//app-desitions-table//th[contains(text(), 'Ставка, %')]/../../..//mat-form-field//input)[3]")
    public TextInput rangeFirstRowTextInput;

    @Element("Кнопка Сохранить таблцы Подбор решений")
    @FindBy(xpath = "//app-desitions-table//span[contains(text(), 'Сохранить')]")
    public Button saveAppDesitionsButton;

    @Element("Модальное окно Информация об ошибке")
    @FindBy(xpath = "//div[@class='modal-error_description']")
    public TextBlock infoErrorModal;

    @Element("Кнопка ОК на модальном окне")
    @FindBy(xpath = "//div[contains(@class, 'modal-error')]//button")
    public Button OkButton;

    @Element("Модальное окно Потеря изменений")
    @FindBy(xpath = "//div[contains(@class, 'action-title')]")
    public TextBlock changesLostModal;

    @Element("Кнопка Да на модальном окне")
    @FindBy(xpath = "//app-button//span[contains(text(), 'Да')]")
    public TextBlock yesButton;

    @Element("Кнопка Нет на модальном окне")
    @FindBy(xpath = "//div[contains(@class, 'application-attention-btn')]//span[contains(text(), 'Нет')]")
    public TextBlock noButton;

    @Element("Блок Выбранных проверок")
    @FindBy(xpath = "//div[contains(@class,'applied-checks')]")
    public TextBlock blockSelectedChecks;

    @Element("Плашки Выбранных проверок")
    @FindBy(xpath = "//div[contains(@class,'psb-table-filter-value')]")
    public List<TextBlock> diesSelectedChecks;

    @Element("Кнопка Взять в работу")
    @FindBy(xpath = "//div[contains(@class, 'header')]//button[.//span[contains(text(), 'Взять в работу')]]")
    public Button takeToWorkButton;


    @Override
    @Step
    @Title("проверить вхождение в выпадающий список {elementTitle} списка значений: {expectedValues}")
    @Description("Проверяется совпадение ожидаемого списка значений с акутальным и наоборот c подсчетом каждого значения в актуальных или ожидаемых списках")
    public UnderwriterDecisionPage checkDropDownListElements(String elementTitle, List<String> expectedValues) {
        TypifiedWebElement dropDownList = getElementByTitle(elementTitle);
        dropDownList.click();
        List<String> actualListElem = getListElements($$x("//mat-option/span[contains(text(),'') and not (contains(text(),'Выбрать причину'))]"))
                .stream().map(SelenideElement::getText)
                .collect(Collectors.toList());
        assertThat(expectedValues.equals(actualListElem),
                "Актуальные и ожидаемые значения для выпадающего списка " + elementTitle + " не совпадают \n" +
                        "\nАктуальные значения: \n" + actualListElem +
                        "\nОжидаемые значения: \n" + expectedValues);
        closeDropDownList();
        return this;
    }


    @Step
    @Title("Проверить наличие плашек выбранных проверок {expectedValues} {closeIcon}")
    @Description("Выполнение проверки наличия плашек выбранных проверок с крестиком в соответствии с заданным списком.")
    public UnderwriterDecisionPage checkSelectedChecks(List<String> expectedValues, String closeIcon) {
        List<String> actualValues = diesSelectedChecks.stream()
                .map(TextBlock::getText)
                .map(String::trim)
                .collect(Collectors.toList());

        assertThat(actualValues.size(), equalTo(expectedValues.size()),
                "Количество актуальных значений фильтров (" + actualValues.size() +
                        ") не совпадает с количеством ожидаемых значений (" + expectedValues.size() + ")");
        if (!expectedValues.isEmpty()) {
            for (String expectedValue : expectedValues) {
                boolean isPresent = actualValues.contains(expectedValue.trim());
                assertThat(isPresent, "Значение отсутствует: " + expectedValue);
                if (closeIcon.equals("с крестиком")) {
                    boolean hasCloseIcon = diesSelectedChecks.stream()
                            .filter(filter -> filter.getText().trim().equals(expectedValue.trim()))
                            .anyMatch(filter -> filter.getSelenideElement().$x(".//i[@nztype='close']").isDisplayed());
                    assertThat(hasCloseIcon, "Иконка с крестиком отсутствует для фильтра: " + expectedValue);
                }
            }
        }
        return this;
    }

    @Step
    @Title("Удалить проведенные проверки {delReasonChecks} нажатием на крестик")
    public UnderwriterDecisionPage deleteConductedChecks(List<String> conductedChecks) {
        conductedChecks
                .forEach(delReasonCheck ->
                        diesSelectedChecks.stream()
                                .filter(filter -> filter.getText().trim().equals(delReasonCheck.trim()))
                                .findFirst()
                                .ifPresent(filter -> filter.getSelenideElement().$x(".//i[@nztype='close']").click())
                );
        return getSelf();
    }
}
