package ru.autotestframework.pages.card_request.result_checks;

import org.openqa.selenium.support.FindBy;
import ru.autotestframework.pages.BasePage;
import ru.autotestframework.ui_core.page_manager.Element;
import ru.autotestframework.ui_core.page_manager.PageEntry;
import ru.autotestframework.ui_core.services.table_service.FindCellsBy;
import ru.autotestframework.ui_core.services.table_service.FindHeadersBy;
import ru.autotestframework.web_elements.elements.Button;
import ru.autotestframework.web_elements.elements.TextBlock;
import ru.autotestframework.web_elements.elements.WebTable;

@PageEntry(title = "Результаты проверок")
public class ResultCheck extends BasePage<ResultCheck> {

    //Вкладка СПР
    @Element("Вкладка СПР")
    @FindBy(xpath = "//div[contains(@class,'mat-tab-label')]/span[text()='СПР']")
    public Button sprTab;

    @Element("Вкладка Ручные проверки")
    @FindBy(xpath = "//div[contains(@class,'mat-tab-label')]/span[text()='Ручные проверки']")
    public Button resultCheckTab;

    //СПАРК
    @Element("Выпадающий список СПАРК")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'СПАРК')]]")
    public Button sparkList;

    @Element("Выпадающий список Основное место работы")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'СПАРК')]]//mat-expansion-panel-header[.//mat-panel-title[contains(text(), 'Основное место работы')]]")
    public Button sparkPlaceWorkList;

    @Element("Поле Дата, статус запроса СПАРК")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'СПАРК')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Дата, статус запроса')]]//span[contains(@class, 'value')]")
    public TextBlock dateSparkTextBlock;

    @Element("Поле Использование кэша СПАРК")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'СПАРК')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Использование кэша')]]//span[contains(@class, 'value')]")
    public TextBlock cacheUseTextBlock;

    @Element("Поле Полное наименование компании СПАРК")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'СПАРК')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Полное наименование компании')]]//span[contains(@class, 'value')]")
    public TextBlock fullCompanyNameSparkTextBlock;

    @Element("Поле Сокращенное наименование компании СПАРК")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'СПАРК')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Сокращенное наименование компании')]]//span[contains(@class, 'value')]")
    public TextBlock shortCompanyNameSparkTextBlock;

    @Element("Поле ИНН СПАРК")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'СПАРК')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'ИНН')]]//span[contains(@class, 'value')]")
    public TextBlock innSparkTextBlock;

    @Element("Поле КПП СПАРК")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'СПАРК')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'КПП')]]//span[contains(@class, 'value')]")
    public TextBlock kppSparkTextBlock;

    @Element("Поле Дата регистрации компании СПАРК")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'СПАРК')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Дата регистрации компании')]]//span[contains(@class, 'value')]")
    public TextBlock dateFirstRegSparkTextBlock;

    @Element("Поле Статус компании СПАРК")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'СПАРК')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Статус компании')]]//span[contains(@class, 'value')]")
    public TextBlock statusSparkTextBlock;

    @Element("Поле Дата обновления статуса компании СПАРК")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'СПАРК')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Дата обновления статуса компании')]]//span[contains(@class, 'value')]")
    public TextBlock statusDateSparkTextBlock;

    @Element("Поле Действующая СПАРК")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'СПАРК')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Действующая')]]//span[contains(@class, 'value')]")
    public TextBlock isActingSparkTextBlock;

    @Element("Поле Ликвидирована СПАРК")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'СПАРК')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Ликвидирована')]]//span[contains(@class, 'value')]")
    public TextBlock egrulLicvidationSparkTextBlock;

    @Element("Поле ОКОПФ СПАРК")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'СПАРК')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'ОКОПФ')]]//span[contains(@class, 'value')]")
    public TextBlock fullNameOPFSparkTextBlock;

    @Element("Поле ОКФС СПАРК")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'СПАРК')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'ОКФС')]]//span[contains(@class, 'value')]")
    public TextBlock okfsSparkTextBlock;

    @Element("Поле ОКОГУ СПАРК")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'СПАРК')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'ОКОГУ')]]//span[contains(@class, 'value')]")
    public TextBlock okoguSparkTextBlock;

    @Element("Поле ОКВЭД СПАРК")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'СПАРК')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'ОКВЭД')]]//span[contains(@class, 'value')]")
    public TextBlock okvedSparkTextBlock;

    @Element("Поле Тип компании СПАРК")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'СПАРК')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Тип компании')]]//span[contains(@class, 'value')]")
    public TextBlock companyTypeSparkTextBlock;

    @Element("Поле Количество сотрудников СПАРК")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'СПАРК')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Количество сотрудников')]]//span[contains(@class, 'value')]")
    public TextBlock workersRangeSparkTextBlock;

    @Element("Поле Размер предприятия СПАРК")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'СПАРК')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Размер предприятия')]]//span[contains(@class, 'value')]")
    public TextBlock companySizeSparkTextBlock;

    @Element("Поле Уставный капитал СПАРК")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'СПАРК')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Уставный капитал')]]//span[contains(@class, 'value')]")
    public TextBlock charterCapitalSparkTextBlock;

    @Element("Поле Выручка, млн.руб. СПАРК")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'СПАРК')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Выручка, млн.руб.')]]//span[contains(@class, 'value')]")
    public TextBlock revenueSparkTextBlock;

    @Element("Поле Прибыль(убыток), млн.руб. СПАРК")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'СПАРК')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Прибыль(убыток), млн.руб.')]]//span[contains(@class, 'value')]")
    public TextBlock profitSparkTextBlock;

    @Element("Поле Количество ИП на компанию, активных СПАРК")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'СПАРК')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Количество ИП на компанию, активных')]]//span[contains(@class, 'value')]")
    public TextBlock activeSparkTextBlock;

    @Element("Поле Количество ИП на компанию, закрытых СПАРК")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'СПАРК')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Количество ИП на компанию, закрытых')]]//span[contains(@class, 'value')]")
    public TextBlock executedSparkTextBlock;

    @Element("Поле Компаний с аналогичным телефоном СПАРК")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'СПАРК')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Компаний с аналогичным телефоном')]]//span[contains(@class, 'value')]")
    public TextBlock telephoneCountSparkTextBlock;

    @Element("Поле Компаний с аналогичным адресом СПАРК")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'СПАРК')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Компаний с аналогичным адресом')]]//span[contains(@class, 'value')]")
    public TextBlock addressCountSparkTextBlock;

    @Element("Поле Компаний с аналогичным руководителем, в стране СПАРК")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'СПАРК')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Компаний с аналогичным руководителем, в стране')]]//span[contains(@class, 'value')]")
    public TextBlock managerCountInCountrySparkTextBlock;

    @Element("Поле Компаний с аналогичным руководителем, в регионе СПАРК")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'СПАРК')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Компаний с аналогичным руководителем, в регионе')]]//span[contains(@class, 'value')]")
    public TextBlock managerCountInRegionSparkTextBlock;


    //WS Open
    @Element("Выпадающий список WS Open")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'WS Open')]]//mat-panel-title[contains(text(), 'WS Open')]")
    public Button wsOpenYlList;

    @Element("Выпадающий список WS Open YL")
    @FindBy(xpath = "//mat-expansion-panel//mat-panel-title[contains(text(), 'Основное место работы и совместительство')]")
    public Button wsOpenYlNameList;

    @Element("Поле Дата, статус запроса")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'WS Open')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Дата, статус запроса')]]//span[contains(@class, 'value')]")
    public TextBlock dateTextBlock;

    @Element("Поле Полное наименование компании")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'WS Open')]]//span[contains(text(), 'Полное наименование компании')]//..//..//span[contains(@class, 'value')]")
    public TextBlock fullCompanyNameTextBlock;

    @Element("Поле ИНН")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'WS Open')]]//span[contains(text(), 'ИНН')]//..//..//span[contains(@class, 'value')]")
    public TextBlock innTextBlock;

    @Element("Поле КПП")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'WS Open')]]//span[contains(text(), 'КПП')]//..//..//span[contains(@class, 'value')]")
    public TextBlock kppTextBlock;

    @Element("Поле ОГРН")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'WS Open')]]//span[contains(text(), 'ОГРН')]//..//..//span[contains(@class, 'value')]")
    public TextBlock ogrnTextBlock;

    @Element("Поле Дата ОГРН")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'WS Open')]]//span[contains(text(), 'Дата ОГРН')]//..//..//span[contains(@class, 'value')]")
    public TextBlock dateOgrnTextBlock;

    @Element("Поле Дата последнего изменения")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'WS Open')]]//span[contains(text(), 'Дата последнего изменения')]//..//..//span[contains(@class, 'value')]")
    public TextBlock lastModifiedDateTextBlock;

    @Element("Поле Статус ЮЛ")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'WS Open')]]//span[contains(text(), 'Статус ЮЛ')]//..//..//span[contains(@class, 'value')]")
    public TextBlock statusYlTextBlock;

    @Element("Поле Адрес")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'WS Open')]]//span[contains(text(), 'Адрес')]//..//..//span[contains(@class, 'value')]")
    public TextBlock addressTextBlock;

    @Element("Поле Налоговый орган")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'WS Open')]]//span[contains(text(), 'Налоговый орган')]//..//..//span[contains(@class, 'value')]")
    public TextBlock taxAuthorityTextBlock;

    @Element("Поле ОКОПФ")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'WS Open')]]//span[contains(text(), 'ОКОПФ')]//..//..//span[contains(@class, 'value')]")
    public TextBlock fullNameOPFTextBlock;

    @Element("Поле ОКОГУ")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'WS Open')]]//span[contains(text(), 'ОКОГУ')]//..//..//span[contains(@class, 'value')]")
    public TextBlock okoguTextBlock;

    @Element("Поле ОКВЭД")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'WS Open')]]//span[contains(text(), 'ОКВЭД')]//..//..//span[contains(@class, 'value')]")
    public TextBlock versionOkvedTextBlock;

    @Element("Поле Уставный капитал")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'WS Open')]]//span[contains(text(), 'Уставный капитал')]//..//..//span[contains(@class, 'value')]")
    public TextBlock authorizedCapitalTextBlock;

    @Element("Поле Выручка, млн.руб.")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'WS Open')]]//span[contains(text(), 'Выручка, млн.руб.')]//..//..//span[contains(@class, 'value')]")
    public TextBlock revenueTextBlock;

    @Element("Поле Прибыль(убыток), млн.руб.")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'WS Open')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Прибыль(убыток), млн.руб.')]]//span[contains(@class, 'value')]")
    public TextBlock profitTextBlock;

    @Element("Выпадающий список Единоличный исполнительный орган")
    @FindBy(xpath = "//mat-panel-title[contains(text(), 'Единоличный исполнительный орган')]")
    public Button soleExecutiveList;

    @Element("Таблица Единоличный исполнительный орган WSOpen")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'WS Open')]]" +
            "//mat-expansion-panel[./mat-expansion-panel-header[.//mat-panel-title[contains(text(),'Единоличный исполнительный орган')]]]" +
            "/div[contains(@class,'mat-expansion-panel-content')]")
    @FindCellsBy(xpath = ".//div[contains(@class,'table-3-wrapper')]/span")
    @FindHeadersBy(xpath = ".//div[contains(@class,'header')]")
    public WebTable singleExecutiveBodyWsOpenTable;

    @Element("Выпадающий список Учредители")
    @FindBy(xpath = "//mat-panel-title[contains(text(), 'Учредители')]")
    public Button foundersList;

    @Element("Таблица Учредители WSOpen")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'WS Open')]]" +
            "//mat-expansion-panel[./mat-expansion-panel-header[.//mat-panel-title[contains(text(),'Учредители')]]]" +
            "/div[contains(@class,'mat-expansion-panel-content')]")
    @FindCellsBy(xpath = ".//div[contains(@class,'table-4-wrapper')]/span")
    @FindHeadersBy(xpath = ".//div[contains(@class,'header')]")
    public WebTable foundersWsOpenTable;

    @Element("Поле ФИО/Наименование Иностранное Ю/Л (Учредители)")
    @FindBy(xpath = "//div[contains(@class, 'expansionPanel-table')]//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Учредители')]]//span[contains(@aria-describedby, 'cdk-describedby-message-146')]")
    public TextBlock fullNameFounderUlInTextBlock;

    @Element("Поле ИНН Иностранное Ю/Л (Учредители)")
    @FindBy(xpath = "//div[contains(@class, 'expansionPanel-table')]//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Учредители')]]//span[contains(@aria-describedby, 'cdk-describedby-message-147')]")
    public TextBlock innFlFounderUlInTextBlock;

    @Element("Поле Доля Иностранное Ю/Л (Учредители)")
    @FindBy(xpath = "//div[contains(@class, 'expansionPanel-table')]//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Учредители')]]//span[contains(@aria-describedby, 'cdk-describedby-message-143')]")
    public TextBlock percentFounderUlInTextBlock;

    @Element("Поле Достоверность Иностранное Ю/Л (Учредители)")
    @FindBy(xpath = "//div[contains(@class, 'expansionPanel-table')]//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Учредители')]]//span[contains(@aria-describedby, 'cdk-describedby-message-66')]")
    public TextBlock reliabilityFounderUlInTextBlock;

    @Element("Поле ФИО/Наименование Субъект РФ (Учредители)")
    @FindBy(xpath = "//div[contains(@class, 'expansionPanel-table')]//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Учредители')]]//span[contains(@aria-describedby, 'cdk-describedby-message-148')]")
    public TextBlock fullNameFounderSubRfMoTextBlock;

    @Element("Поле Доля Субъект РФ (Учредители)")
    @FindBy(xpath = "//div[contains(@class, 'expansionPanel-table')]//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Учредители')]]//span[contains(@aria-describedby, 'cdk-describedby-message-143')]")
    public TextBlock percentFounderSubRfMoTextBlock;

    @Element("Поле Достоверность Субъект РФ (Учредители)")
    @FindBy(xpath = "//div[contains(@class, 'expansionPanel-table')]//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Учредители')]]//span[contains(@aria-describedby, 'cdk-describedby-message-67')]")
    public TextBlock reliabilityFounderSubRfMoTextBlock;

    @Element("Поле ФИО/Наименование ПИФ (Учредители)")
    @FindBy(xpath = "//div[contains(@class, 'expansionPanel-table')]//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Учредители')]]//span[contains(@aria-describedby, 'cdk-describedby-message-149')]")
    public TextBlock fullNameFounderPifTextBlock;

    @Element("Поле Доля ПИФ (Учредители)")
    @FindBy(xpath = "//div[contains(@class, 'expansionPanel-table')]//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Учредители')]]//span[contains(@aria-describedby, 'cdk-describedby-message-143')]")
    public TextBlock percentFounderPifTextBlock;

    @Element("Поле Достоверность ПИФ (Учредители)")
    @FindBy(xpath = "//div[contains(@class, 'expansionPanel-table')]//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Учредители')]]//span[contains(@aria-describedby, 'cdk-describedby-message-66')]")
    public TextBlock reliabilityFounderPifTextBlock;

    @Element("Выпадающий список Лицензии")
    @FindBy(xpath = "//mat-panel-title[contains(text(), 'Лицензии')]")
    public Button licensesList;

    @Element("Поле Лицензии 1")
    @FindBy(xpath = "(//mat-panel-title[contains(text(), 'Лицензии')]/../../following-sibling::div//span)[1]")
    public TextBlock licenses1TextBlock;

    @Element("Поле Лицензии 2")
    @FindBy(xpath = "(//mat-panel-title[contains(text(), 'Лицензии')]/../../following-sibling::div//span)[2]")
    public TextBlock licenses2TextBlock;


    //WS Open ИП

    @Element("Выпадающий список Основное место работы WS Open ИП")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'WS Open')]]//mat-expansion-panel-header[.//mat-panel-title[contains(text(), 'Основное место работы')]]")
    public Button workPlaceList;

    @Element("Выпадающий список Совместительство")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'WS Open')]]//mat-expansion-panel-header[.//mat-panel-title[contains(text(), 'Совместительство')]]")
    public Button partTimeJobList;

    @Element("Поле Дата, статус запроса ИП")
    @FindBy(xpath = "//mat-expansion-panel[//mat-panel-title[contains(text(), 'WS Open')] and mat-expansion-panel-header[.//mat-panel-title[contains(text(), 'Основное место работы - Болконский Андрей Николаевич')]]]" +
            "//div[span[contains(text(), ' Дата, статус запроса ')]]//..//span[contains(@class, 'value')]")
    public TextBlock dateIpTextBlock;

    @Element("Поле ФИО")
    @FindBy(xpath = "//mat-expansion-panel[//mat-panel-title[contains(text(), 'WS Open')] and mat-expansion-panel-header[.//mat-panel-title[contains(text(), 'Основное место работы - Болконский Андрей Николаевич')]]]" +
            "//div[span[contains(text(), ' ФИО ')]]//..//span[contains(@class, 'value')]")
    public TextBlock fullNameTextBlock;

    @Element("Поле ИНН ИП")
    @FindBy(xpath = "//mat-expansion-panel[//mat-panel-title[contains(text(), 'WS Open')] and mat-expansion-panel-header[.//mat-panel-title[contains(text(), 'Основное место работы - Болконский Андрей Николаевич')]]]" +
            "//div[span[contains(text(), ' ИНН ')]]//..//span[contains(@class, 'value')]")
    public TextBlock innIpTextBlock;

    @Element("Поле Дата ОГРНИП")
    @FindBy(xpath = "//mat-expansion-panel[//mat-panel-title[contains(text(), 'WS Open')] and mat-expansion-panel-header[.//mat-panel-title[contains(text(), 'Основное место работы - Болконский Андрей Николаевич')]]]" +
            "//div[span[contains(text(), ' Дата присвоения ОГРНИП ')]]//..//span[contains(@class, 'value')]")
    public TextBlock dateOgrnIpTextBlock;

    @Element("Поле Дата последнего изменения ИП")
    @FindBy(xpath = "//mat-expansion-panel[//mat-panel-title[contains(text(), 'WS Open')] and mat-expansion-panel-header[.//mat-panel-title[contains(text(), 'Основное место работы - Болконский Андрей Николаевич')]]]" +
            "//div[span[contains(text(), ' Дата последнего изменения ')]]//..//span[contains(@class, 'value')]")
    public TextBlock lastModifiedDateIpTextBlock;

    @Element("Поле Статус ИП")
    @FindBy(xpath = "//mat-expansion-panel[//mat-panel-title[contains(text(), 'WS Open')] and mat-expansion-panel-header[.//mat-panel-title[contains(text(), 'Основное место работы - Болконский Андрей Николаевич')]]]" +
            "//div[span[contains(text(), ' Статус ')]]//..//span[contains(@class, 'value')]")
    public TextBlock statusIpTextBlock;

    @Element("Поле Дата начала действия ИП")
    @FindBy(xpath = "//mat-expansion-panel[//mat-panel-title[contains(text(), 'WS Open')] and mat-expansion-panel-header[.//mat-panel-title[contains(text(), 'Основное место работы - Болконский Андрей Николаевич')]]]" +
            "//div[span[contains(text(), ' Дата начала действия ИП ')]]//..//span[contains(@class, 'value')]")
    public TextBlock dateStartTextBlock;

    @Element("Поле Дата прекращения ИП")
    @FindBy(xpath = "//mat-expansion-panel[//mat-panel-title[contains(text(), 'WS Open')] and mat-expansion-panel-header[.//mat-panel-title[contains(text(), 'Основное место работы - Болконский Андрей Николаевич')]]]" +
            "//div[span[contains(text(), ' Дата прекращения ИП ')]]//..//span[contains(@class, 'value')]")
    public TextBlock dateStopIPTextBlock;

    @Element("Поле Наименование налогового органа")
    @FindBy(xpath = "//mat-expansion-panel[//mat-panel-title[contains(text(), 'WS Open')] and mat-expansion-panel-header[.//mat-panel-title[contains(text(), 'Основное место работы - Болконский Андрей Николаевич')]]]" +
            "//div[span[contains(text(), ' Информация о налоговом органе ')]]//..//span[contains(@class, 'value')]")
    public TextBlock nameNoTextBlock;

    @Element("Поле Дата открытия счета в налоговом органе")
    @FindBy(xpath = "//mat-expansion-panel[//mat-panel-title[contains(text(), 'WS Open')] and mat-expansion-panel-header[.//mat-panel-title[contains(text(), 'Основное место работы - Болконский Андрей Николаевич')]]]" +
            "//div[span[contains(text(), ' Дата открытия счета в налоговом органе ')]]//..//span[contains(@class, 'value')]")
    public TextBlock dateStartAccNOTextBlock;

    @Element("Поле ОКВЭД ИП")
    @FindBy(xpath = "//mat-expansion-panel[//mat-panel-title[contains(text(), 'WS Open')] and mat-expansion-panel-header[.//mat-panel-title[contains(text(), 'Основное место работы - Болконский Андрей Николаевич')]]]" +
            "//div[span[contains(text(), ' ОКВЭД ')]]//..//span[contains(@class, 'value')]")
    public TextBlock codeOKVEDTextBlock;

    @Element("Поле Дата, статус запроса Совместительство")
    @FindBy(xpath = "//mat-expansion-panel[//mat-panel-title[contains(text(), 'WS Open')] and mat-expansion-panel-header[.//mat-panel-title[contains(text(), 'Совместительство')]]]" +
            "//div[span[contains(text(), ' Дата, статус запроса ')]]//..//span[contains(@class, 'value')]")
    public TextBlock dateIpPartTimeTextBlock;

    @Element("Поле ФИО Совместительство")
    @FindBy(xpath = "//mat-expansion-panel[//mat-panel-title[contains(text(), 'WS Open')] and mat-expansion-panel-header[.//mat-panel-title[contains(text(), 'Совместительство')]]]" +
            "//div[span[contains(text(), ' ФИО ')]]//..//span[contains(@class, 'value')]")
    public TextBlock fullNamePartTimeTextBlock;

    @Element("Поле ИНН ИП Совместительство")
    @FindBy(xpath = "//mat-expansion-panel[//mat-panel-title[contains(text(), 'WS Open')] and mat-expansion-panel-header[.//mat-panel-title[contains(text(), 'Совместительство')]]]" +
            "//div[span[contains(text(), ' ИНН ')]]//..//span[contains(@class, 'value')]")
    public TextBlock innIpPartTimeTextBlock;

    @Element("Поле Дата ОГРНИП Совместительство")
    @FindBy(xpath = "//mat-expansion-panel[//mat-panel-title[contains(text(), 'WS Open')] and mat-expansion-panel-header[.//mat-panel-title[contains(text(), 'Совместительство')]]]" +
            "//div[span[contains(text(), ' Дата присвоения ОГРНИП ')]]//..//span[contains(@class, 'value')]")
    public TextBlock dateOgrnIpPartTimeTextBlock;

    @Element("Поле Дата последнего изменения ИП Совместительство")
    @FindBy(xpath = "//mat-expansion-panel[//mat-panel-title[contains(text(), 'WS Open')] and mat-expansion-panel-header[.//mat-panel-title[contains(text(), 'Совместительство')]]]" +
            "//div[span[contains(text(), ' Дата последнего изменения ')]]//..//span[contains(@class, 'value')]")
    public TextBlock lastModifiedDateIpPartTimeTextBlock;

    @Element("Поле Статус ИП Совместительство")
    @FindBy(xpath = "//mat-expansion-panel[//mat-panel-title[contains(text(), 'WS Open')] and mat-expansion-panel-header[.//mat-panel-title[contains(text(), 'Совместительство')]]]" +
            "//div[span[contains(text(), ' Статус ')]]//..//span[contains(@class, 'value')]")
    public TextBlock statusIpPartTimeTextBlock;

    @Element("Поле Дата начала действия ИП Совместительство")
    @FindBy(xpath = "//mat-expansion-panel[//mat-panel-title[contains(text(), 'WS Open')] and mat-expansion-panel-header[.//mat-panel-title[contains(text(), 'Совместительство')]]]" +
            "//div[span[contains(text(), ' Дата начала действия ИП ')]]//..//span[contains(@class, 'value')]")
    public TextBlock dateStartPartTimeTextBlock;

    @Element("Поле Дата прекращения ИП Совместительство")
    @FindBy(xpath = "//mat-expansion-panel[//mat-panel-title[contains(text(), 'WS Open')] and mat-expansion-panel-header[.//mat-panel-title[contains(text(), 'Совместительство')]]]" +
            "//div[span[contains(text(), ' Дата прекращения ИП ')]]//..//span[contains(@class, 'value')]")
    public TextBlock dateStopIpPartTimeTextBlock;

    @Element("Поле Наименование налогового органа Совместительство")
    @FindBy(xpath = "//mat-expansion-panel[//mat-panel-title[contains(text(), 'WS Open')] and mat-expansion-panel-header[.//mat-panel-title[contains(text(), 'Совместительство')]]]" +
            "//div[span[contains(text(), ' Информация о налоговом органе ')]]//..//span[contains(@class, 'value')]")
    public TextBlock nameNoPartTimeTextBlock;

    @Element("Поле Дата открытия счета в налоговом органе Совместительство")
    @FindBy(xpath = "//mat-expansion-panel[//mat-panel-title[contains(text(), 'WS Open')] and mat-expansion-panel-header[.//mat-panel-title[contains(text(), 'Совместительство')]]]" +
            "//div[span[contains(text(), ' Дата открытия счета в налоговом органе ')]]//..//span[contains(@class, 'value')]")
    public TextBlock dateStartAccNOPartTimeTextBlock;

    @Element("Поле ОКВЭД ИП Совместительство")
    @FindBy(xpath = "//mat-expansion-panel[//mat-panel-title[contains(text(), 'WS Open')] and mat-expansion-panel-header[.//mat-panel-title[contains(text(), 'Совместительство')]]]" +
            "//div[span[contains(text(), ' ОКВЭД ')]]//..//span[contains(@class, 'value')]")
    public TextBlock codeOKVEDPartTimeTextBlock;

    @Element("Выпадающий список Реестровый доход")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Реестровый доход')]]")
    public Button registerIncomeDropDown;

    @Element("Поле Сумма пенсионных зачислений")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Реестровый доход')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Сумма пенсионных зачислений')]]//span[contains(@class, 'value')]")
    public TextBlock pensionCreditAmountTextBlock;

    //Клиентские признаки
    @Element("Выпадающий список Клиентские признаки")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Клиентские признаки')]]")
    public Button clientSignsList;

    @Element("Поле Количество сотрудников работодателя")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Клиентские признаки')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Количество сотрудников работодателя')]]//span[contains(@class, 'value')]")
    public TextBlock employeeWorkersCountTextBlock;

    @Element("Поле Источник (количество сотрудников)")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Клиентские признаки')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Источник (количество сотрудников)')]]//span[contains(@class, 'value')]")
    public TextBlock employeeWorkersSourceTextBlock;

    @Element("Поле Выручка работодателя")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Клиентские признаки')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Выручка работодателя')]]//span[contains(@class, 'value')]")
    public TextBlock employeeRevenueActTextBlock;

    @Element("Поле Реестровые зачисления, макс.")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Клиентские признаки')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Реестровые зачисления, макс.')]]//span[contains(@class, 'value')]")
    public TextBlock sumMaxZpTextBlock;

    @Element("Выпадающий список Список признаков")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Клиентские признаки')]]//mat-expansion-panel")
    public Button buttonListSignsList;

    @Element("Поле КП по участнику сделки")
    @FindBy(xpath = "//mat-expansion-panel-header[contains(normalize-space(), 'Клиентские признаки')]/..//mat-expansion-panel-header[contains(normalize-space(), 'Список признаков')]/following-sibling::div[@role='region']//span[contains(normalize-space(), 'КП по участнику сделки')]/../following-sibling::div/span")
    public TextBlock cPtransactionParticipantClientSignsTextBlock;

    @Element("Поле КП по основному месту работы")
    @FindBy(xpath = "//mat-expansion-panel-header[contains(normalize-space(), 'Клиентские признаки')]/..//mat-expansion-panel-header[contains(normalize-space(), 'Список признаков')]/following-sibling::div[@role='region']//span[contains(normalize-space(), 'КП по основному месту работы')]/../following-sibling::div/span")
    public TextBlock cPMainPlaceClientSignsTextBlock;

    @Element("Поле КП по совместительству")
    @FindBy(xpath = "//mat-expansion-panel-header[contains(normalize-space(), 'Клиентские признаки')]/..//mat-expansion-panel-header[contains(normalize-space(), 'Список признаков')]/following-sibling::div[@role='region']//span[contains(normalize-space(), 'КП по совместительству')]/../following-sibling::div/span")
    public TextBlock cPPartTimeTextClientSignsBlock;

    @Element("Поле Сумма зачислений, гражданская пенсия, С_гп")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Реестровый доход')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Сумма зачислений, гражданская пенсия, С_гп')]]//span[contains(@class, 'value')]")
    public TextBlock civilPensionAmountTextBlock;

    @Element("Поле Количество месяцев для расчета С_гп")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Реестровый доход')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Количество месяцев для расчета С_гп')]]//span[contains(@class, 'value')]")
    public TextBlock monthNumberToCalculateTextBlock;

    @Element("Поле Сумма зачислений, военная пенсия, С_вп")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Реестровый доход')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Сумма зачислений, военная пенсия, С_вп')]]//span[contains(@class, 'value')]")
    public TextBlock amountOfCreditsMilitaryPensionTextBlock;

    @Element("Поле Количество месяцев для расчета С_вп")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Реестровый доход')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Количество месяцев для расчета С_вп')]]//span[contains(@class, 'value')]")
    public TextBlock monthNumberToCalculateSWpTextBlock;

    @Element("Выпадающий список Реестровые зачисления")
    @FindBy(xpath = "//mat-expansion-panel-header[.//mat-panel-title[contains(text(), 'Реестровые зачисления')]]")
    public Button registryPaymentsDropDown;

    @Element("Таблица Реестровые зачисления")
    @FindBy(xpath = "//mat-expansion-panel-header[.//mat-panel-title[contains(text(), 'Реестровые зачисления')]]/parent::mat-expansion-panel//div[contains(@class,'mat-expansion-panel-body')]")
    @FindCellsBy(xpath = ".//span[contains(@class,'value')]")
    @FindHeadersBy(xpath = ".//div[contains(@class,'header')]")
    public WebTable registryPaymentsTable;

    //Чистый и прогнозный доход
    @Element("Выпадающий список Чистый и прогнозный доход")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Чистый и прогнозный доход')]]")
    public Button netAndForecastIncomeList;

    @Element("Поле Среднемесячный чистый доход, Д_сч")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Чистый и прогнозный доход')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Среднемесячный чистый доход, Д_сч')]]//span[contains(@class, 'value')]")
    public TextBlock averageMonthlyNetIncomeTextBlock;

    @Element("Поле Количество месяцев для расчета Д_сч")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Чистый и прогнозный доход')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Количество месяцев для расчета Д_сч')]]//span[contains(@class, 'value')]")
    public TextBlock numberMonthsToCalculateTextBlock;

    @Element("Поле Подтвержденный доход")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Чистый и прогнозный доход')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Подтвержденный доход')]]//span[contains(@class, 'value')]")
    public TextBlock incomeConfTextBlock;

    @Element("Поле Неподтвержденный доход,  заявл. осн. доход и пенсия")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Чистый и прогнозный доход')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Неподтвержденный доход,  заявл. осн. доход и пенсия')]]//span[contains(@class, 'value')]")
    public TextBlock incomeNconfPos1TextBlock;

    @Element("Поле Неподтвержденный доход, реестр. осн. доход и заявл. пенсия")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Чистый и прогнозный доход')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Неподтвержденный доход, реестр. осн. доход и заявл. пенсия')]]//span[contains(@class, 'value')]")
    public TextBlock incomeNconfPos2TextBlock;

    @Element("Поле Неподтвержденный доход,  заявл. осн.доход и реестр. пенсия")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Чистый и прогнозный доход')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Неподтвержденный доход,  заявл. осн.доход и реестр. пенсия')]]//span[contains(@class, 'value')]")
    public TextBlock incomeNconfPos3TextBlock;

    @Element("Поле Неподтвержденный доход, заявл.осн.доход")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Чистый и прогнозный доход')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Неподтвержденный доход, заявл.осн.доход')]]//span[contains(@class, 'value')]")
    public TextBlock incomeNconfPos4TextBlock;

    @Element("Поле Доход для КК без подтверждения дохода")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Чистый и прогнозный доход')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Доход для КК без подтверждения дохода')]]//span[contains(@class, 'value')]")
    public TextBlock incomeCcdNconfTextBlock;

    @Element("Поле Доход по анкете")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Чистый и прогнозный доход')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Доход по анкете')]]//span[contains(@class, 'value')]")
    public TextBlock mainIncomeTextBlock;

    @Element("Поле Сумма реестровых зачислений  по всем ИНН")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Чистый и прогнозный доход')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Сумма реестровых зачислений по всем ИНН')]]//span[contains(@class, 'value')]")
    public TextBlock sumZpTotalTextBlock;

    @Element("Поле Модель предсказания дохода")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Чистый и прогнозный доход')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Модель предсказания дохода')]]//span[contains(@class, 'value')]")
    public TextBlock outModelIncomeTypeTextBlock;

    @Element("Поле Модельный доход")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Чистый и прогнозный доход')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Модельный доход')]]//span[contains(@class, 'value')]")
    public TextBlock outModelIncomeResultTextBlock;

    @Element("Поле Средний доход в регионе")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Чистый и прогнозный доход')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Средний доход в регионе')]]//span[contains(@class, 'value')]")
    public TextBlock avgIncomeTextBlock;

    @Element("Поле Минимальный доход в регионе")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Чистый и прогнозный доход')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Минимальный доход в регионе')]]//span[contains(@class, 'value')]")
    public TextBlock minIncomeTextBlock;

    @Element("Выпадающий список Маркировка обязательств")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Маркировка обязательств')]]")
    public Button commitmentLabelingDropDown;

    @Element("Поле Сумма ЕП по кредитам, модифицированная")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Маркировка обязательств')]]//div[contains(@class,'table-item')][.//span[normalize-space()='Сумма ЕП по кредитам, модифицированная']]//span[contains(@class, 'value')]")
    public TextBlock epCreditSumModifiedTextBlock;

    @Element("Поле Сумма ЕП по КК, модифицированная")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Маркировка обязательств')]]//div[contains(@class,'table-item')][.//span[normalize-space()='Сумма ЕП по КК, модифицированная']]//span[contains(@class, 'value')]")
    public TextBlock epKkSumModifiedTextBlock;

    @Element("Поле Сумма ЕП по кредитам")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Маркировка обязательств')]]//div[contains(@class,'table-item')][.//span[normalize-space()='Сумма ЕП по кредитам']]//span[contains(@class, 'value')]")
    public TextBlock epLoanSumTextBlock;

    @Element("Поле Сумма ЕП по КК")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Маркировка обязательств')]]//div[contains(@class,'table-item')][.//span[normalize-space()='Сумма ЕП по КК']]//span[contains(@class, 'value')]")
    public TextBlock epKKSumTextBlock;

    @Element("Поле Признак ППД")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Маркировка обязательств')]]//div[contains(@class,'table-item')][.//span[normalize-space()='Признак ППД']]//span[contains(@class, 'value')]")
    public TextBlock pndTextBlock;

    @Element("Поле Признак ПКИ")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Маркировка обязательств')]]//div[contains(@class,'table-item')][.//span[normalize-space()='Признак ПКИ']]//span[contains(@class, 'value')]")
    public TextBlock pkiTextBlock;

    @Element("Поле Признак достаточной КИ")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Маркировка обязательств')]]//div[contains(@class,'table-item')][.//span[normalize-space()='Признак достаточной КИ']]//span[contains(@class, 'value')]")
    public TextBlock enoughkiTextBlock;

    @Element("Поле Количество пропущенных БКИ")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Маркировка обязательств')]]//div[contains(@class,'table-item')][.//span[normalize-space()='Количество пропущенных БКИ']]//span[contains(@class, 'value')]")
    public TextBlock bkiTextBlock;

    @Element("Поле Количество запросов на бизнес")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Маркировка обязательств')]]//div[contains(@class,'table-item')][.//span[normalize-space()='Количество запросов на бизнес']]//span[contains(@class, 'value')]")
    public TextBlock businessRequestQuantityTextBlock;

    @Element("Поле Количество кредитов на бизнес")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Маркировка обязательств')]]//div[contains(@class,'table-item')][.//span[normalize-space()='Количество кредитов на бизнес']]//span[contains(@class, 'value')]")
    public TextBlock businessLoanQuantityTextBlock;
    //ЕП Макс
    @Element("Выпадающий список ЕП Макс")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕП Макс')]]")
    public Button epMaxList;

    @Element("Поле Общий результат")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕП Макс')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Общий результат')]]//span[contains(@class, 'value')]")
    public TextBlock epEpMaxTextBlock;

    @Element("Поле Доход, подтвержденный")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕП Макс')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Доход, подтвержденный')]]//span[contains(@class, 'value')]")
    public TextBlock incomeConfEpMaxTextBlock;

    @Element("Поле Доход, неподтвержденный (pos1)")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕП Макс')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Доход, неподтвержденный (pos1)')]]//span[contains(@class, 'value')]")
    public TextBlock incomeNconfPos1EpMaxTextBlock;

    @Element("Поле Доход, неподтвержденный (pos2)")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕП Макс')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Доход, неподтвержденный (pos2)')]]//span[contains(@class, 'value')]")
    public TextBlock incomeNconfPos2EpMaxTextBlock;

    @Element("Поле Доход, неподтвержденный (pos3)")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕП Макс')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Доход, неподтвержденный (pos3)')]]//span[contains(@class, 'value')]")
    public TextBlock incomeNconfPos3EpMaxTextBlock;

    @Element("Поле Доход, неподтвержденный (pos4)")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕП Макс')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Доход, неподтвержденный (pos4)')]]//span[contains(@class, 'value')]")
    public TextBlock incomeNconfPos4EpMaxTextBlock;

    @Element("Поле Доход для КК без подтверждения дохода EpMax")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕП Макс')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Доход для КК без подтверждения дохода')]]//span[contains(@class, 'value')]")
    public TextBlock incomeCcdNconfEpMaxTextBlock;

    @Element("Поле Прогнозный доход EpMax")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕП Макс')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Прогнозный доход')]]//span[contains(@class, 'value')]")
    public TextBlock outModelIncomeResultEpMaxTextBlock;

    @Element("Поле Минимальный доход для региона EpMax")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕП Макс')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Минимальный доход для региона')]]//span[contains(@class, 'value')]")
    public TextBlock minIncomeEpMaxTextBlock;

    @Element("Поле PTI EpMax")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕП Макс')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'PTI')]]//span[contains(@class, 'value')]")
    public TextBlock ptiIncomeConfEpMaxTextBlock;

    @Element("Поле ЕП для подтвержденного дохода")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕП Макс')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'ЕП для подтвержденного дохода')]]//span[contains(@class, 'value')]")
    public TextBlock mpConfEpMaxTextBlock;

    @Element("Поле ЕП для не подтвержденного дохода (pos1)")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕП Макс')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'ЕП для не подтвержденного дохода (pos1)')]]//span[contains(@class, 'value')]")
    public TextBlock mpNconfPos1EpMaxTextBlock;

    @Element("Поле ЕП для не подтвержденного дохода (pos2)")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕП Макс')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'ЕП для не подтвержденного дохода (pos2)')]]//span[contains(@class, 'value')]")
    public TextBlock mpNconfPos2EpMaxTextBlock;

    @Element("Поле ЕП для не подтвержденного дохода (pos3)")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕП Макс')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'ЕП для не подтвержденного дохода (pos3)')]]//span[contains(@class, 'value')]")
    public TextBlock mpNconfPos3EpMaxTextBlock;

    @Element("Поле ЕП для не подтвержденного дохода (pos4)")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕП Макс')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'ЕП для не подтвержденного дохода (pos4)')]]//span[contains(@class, 'value')]")
    public TextBlock mpNconfPos4EpMaxTextBlock;

    @Element("Поле ЕП для КК без подтверждения дохода")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕП Макс')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'ЕП для КК без подтверждения дохода')]]//span[contains(@class, 'value')]")
    public TextBlock mpCcdNconfEpMaxTextBlock;

    @Element("Поле Общая сумма платежей по кредитам")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕП Макс')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Общая сумма платежей по кредитам')]]//span[contains(@class, 'value')]")
    public TextBlock evkEpMaxTextBlock;

    @Element("Поле Общая сумма платежей по кредитным картам")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕП Макс')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Общая сумма платежей по кредитным картам')]]//span[contains(@class, 'value')]")
    public TextBlock epCardEpMaxTextBlock;

    @Element("Поле EVK без учета EVK некарточных кредитов")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕП Макс')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'EVK без учета EVK некарточных кредитов')]]//span[contains(@class, 'value')]")
    public TextBlock evkModEpMaxTextBlock;

    @Element("Поле EP_CARD без учета EP_CARD карточных кредитов")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕП Макс')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'EP_CARD без учета EP_CARD карточных кредитов')]]//span[contains(@class, 'value')]")
    public TextBlock epCardModEpMaxTextBlock;

    @Element("Поле ЕП макс по подтвержденному доходу")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕП Макс')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'ЕП макс по подтвержденному доходу')]]//span[contains(@class, 'value')]")
    public TextBlock epMaxConfEpMaxTextBlock;

    @Element("Поле ЕП макс по не подтвержденному доходу (pos1)")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕП Макс')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'ЕП макс по не подтвержденному доходу (pos1)')]]//span[contains(@class, 'value')]")
    public TextBlock epMaxNconfPos1TextBlock;

    @Element("Поле ЕП макс по не подтвержденному доходу (pos2)")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕП Макс')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'ЕП макс по не подтвержденному доходу (pos2)')]]//span[contains(@class, 'value')]")
    public TextBlock epMaxNconfPos2TextBlock;

    @Element("Поле ЕП макс по не подтвержденному доходу (pos3)")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕП Макс')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'ЕП макс по не подтвержденному доходу (pos3)')]]//span[contains(@class, 'value')]")
    public TextBlock epMaxNconfPos3TextBlock;

    @Element("Поле ЕП макс по не подтвержденному доходу (pos4)")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕП Макс')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'ЕП макс по не подтвержденному доходу (pos4)')]]//span[contains(@class, 'value')]")
    public TextBlock epMaxNconfPos4TextBlock;

    @Element("Поле ЕП макс для КК без подтверждения дохода")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕП Макс')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'ЕП макс для КК без подтверждения дохода')]]//span[contains(@class, 'value')]")
    public TextBlock epMaxCcdNconfTextBlock;

    @Element("Поле ЕП макс итоговый")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕП Макс')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'ЕП макс итоговый')]]//span[contains(@class, 'value')]")
    public TextBlock epMaxTextBlock;

    //Скоринг
    @Element("Выпадающий список Скоринг")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Скоринг')]]")
    public Button scoringList;

    @Element("Выпадающий список Скоринг-список признаков")
    @FindBy(xpath = "//mat-expansion-panel-header[contains(normalize-space(), 'Скоринг')]/..//mat-expansion-panel-header[contains(normalize-space(), 'Список признаков')]")
    public Button clientSignsDropDown;

    @Element("Скоринг-Поле КП по участнику сделки")
    @FindBy(xpath = "//mat-expansion-panel-header[contains(normalize-space(), 'Скоринг')]/..//mat-expansion-panel-header[contains(normalize-space(), 'Список признаков')]/following-sibling::div[@role='region']//span[contains(normalize-space(), 'КП по участнику сделки')]/../following-sibling::div/span")
    public TextBlock cPtransactionParticipantTextBlock;

    @Element("Скоринг-Поле КП по основному месту работы")
    @FindBy(xpath = "//mat-expansion-panel-header[contains(normalize-space(), 'Скоринг')]/..//mat-expansion-panel-header[contains(normalize-space(), 'Список признаков')]/following-sibling::div[@role='region']//span[contains(normalize-space(), 'КП по основному месту работы')]/../following-sibling::div/span")
    public TextBlock cPMainPlaceTextBlock;

    @Element("Скоринг-Поле КП по совместительству")
    @FindBy(xpath = "//mat-expansion-panel-header[contains(normalize-space(), 'Скоринг')]/..//mat-expansion-panel-header[contains(normalize-space(), 'Список признаков')]/following-sibling::div[@role='region']//span[contains(normalize-space(), 'КП по совместительству')]/../following-sibling::div/span")
    public TextBlock cPPartTimeTextBlock;

    @Element("Поле Граница отсечения")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Скоринг')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Граница отсечения')]]//span[contains(@class, 'value')]")
    public TextBlock cutOffScoringTextBlock;

    @Element("Поле Скоринг БКИ")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Скоринг')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Скоринг БКИ')]]//span[contains(@class, 'value')]")
    public TextBlock scoreBkiScoringTextBlock;

    @Element("Поле Итоговый скоринг с учетом скоринга БКИ")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Скоринг')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Итоговый скоринг с учетом скоринга БКИ')]]//span[contains(@class, 'value')]")
    public TextBlock scoreScoringTextBlock;

    @Element("Поле Решение по сегменту")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Скоринг')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Решение по сегменту')]]//span[contains(@class, 'value')]")
    public TextBlock checkScoreScoringTextBlock;

    //Ограничение лимита
    @Element("Выпадающий список Ограничение лимита")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Ограничение лимита')]]")
    public Button limitList;

    @Element("Поле Размер ограничения лимита по КК с подтверждением дохода")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Ограничение лимита')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Размер ограничения лимита по КК с подтверждением дохода')]]//span[contains(@class, 'value')]")
    public TextBlock maxRiskLimitCcTextBlock;

    @Element("Поле Результат по ограничению лимита по КК без подтверждения дохода")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Ограничение лимита')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Результат по ограничению лимита по КК без подтверждения дохода')]]//span[contains(@class, 'value')]")
    public TextBlock maxRiskLimitCcNcCheckResultTextBlock;

    @Element("Поле Результат по ограничению лимита по ЕКЛ")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Ограничение лимита')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Результат по ограничению лимита по ЕКЛ')]]//span[contains(@class, 'value')]")
    public TextBlock maxRiskLimitEklCheckResultTextBlock;

    @Element("Поле Результат по ограничению лимита по потреб. кредиту")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Ограничение лимита')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Результат по ограничению лимита по потреб. кредиту')]]//span[contains(@class, 'value')]")
    public TextBlock maxRiskLimitPlCheckResultTextBlock;

    @Element("Поле Результат по ограничению лимита по КК с подтверждением дохода")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Ограничение лимита')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Результат по ограничению лимита по КК с подтверждением дохода')]]//span[contains(@class, 'value')]")
    public TextBlock maxRiskLimitCcCheckResultTextBlock;

    @Element("Поле Размер ограничения лимита по потреб. кредиту")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Ограничение лимита')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Размер ограничения лимита по потреб. кредиту')]]//span[contains(@class, 'value')]")
    public TextBlock maxRiskLimitPlTextBlock;

    @Element("Поле Размер ограничения лимита по КК без подтверждения дохода")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Ограничение лимита')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Размер ограничения лимита по КК без подтверждения дохода')]]//span[contains(@class, 'value')]")
    public TextBlock maxRiskLimitCcNcTextBlock;

    @Element("Поле Размер ограничения лимита по ЕКЛ")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Ограничение лимита')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Размер ограничения лимита по ЕКЛ')]]//span[contains(@class, 'value')]")
    public TextBlock maxRiskLimitEklTextBlock;

    @Element("Поле Общий результат Limit")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Ограничение лимита')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Общий результат')]]//span[contains(@class, 'value')]")
    public TextBlock riskLimitsCheckResultTextBlock;

    //ПДН
    @Element("Выпадающий список ПДН")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ПДН')]]")
    public Button pdnList;

    @Element("Поле Общий результат по блоку")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ПДН')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Общий результат по блоку')]]//span[contains(@class, 'value')]")
    public TextBlock approvedPdnTextBlock;

    @Element("Поле ПДН")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ПДН')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'ПДН')]]//span[contains(@class, 'value')]")
    public TextBlock pdnTextBlock;

    //Валидация лимита
    @Element("Выпадающий список Валидация лимита")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Валидация лимита')]]")
    public Button validationLimitList;

    @Element("Поле Общий результат по блоку Валидация")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Валидация лимита')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Общий результат по блоку')]]//span[contains(@class, 'value')]")
    public TextBlock validationLimitTextBlock;

    //ЕКЛ, подбор
    @Element("Выпадающий список ЕКЛ, подбор")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, подбор')]]")
    public Button selectionEklList;

    @Element("Поле ЕКЛ макс")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, подбор')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'ЕКЛ макс')]]//span[contains(@class, 'value')]")
    public TextBlock eklMaxTextBlock;

    @Element("Поле Процентная ставка выбранного бренда")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, подбор')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Процентная ставка выбранного бренда')]]//span[contains(@class, 'value')]")
    public TextBlock selectedBrandPercentageTextBlock;

    @Element("Поле Максимальная сумма кредита по всем брендам")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, подбор')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Максимальная сумма кредита по всем брендам')]]//span[contains(@class, 'value')]")
    public TextBlock loanMaxBrandTextBlock;

    @Element("Поле Максимальный срок кредита по всем брендам")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, подбор')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Максимальный срок кредита по всем брендам')]]//span[contains(@class, 'value')]")
    public TextBlock loanTermMaxBrandTextBlock;

    @Element("Поле Максимальный лимит для КК")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, подбор')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Максимальный лимит для КК')]]//span[contains(@class, 'value')]")
    public TextBlock maxLimitKkTextBlock;

    @Element("Поле Минимальный платеж по КК")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, подбор')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Минимальный платеж по КК')]]//span[contains(@class, 'value')]")
    public TextBlock minPaymentKkTextBlock;

    @Element("Поле Месячный платеж ПК")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, подбор')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Месячный платеж ПК')]]//span[contains(@class, 'value')]")
    public TextBlock loanMonthlyPaymentTextBlock;

    @Element("Поле ЕП для подтвержденного дохода Екл")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, подбор')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'ЕП для подтвержденного дохода')]]//span[contains(@class, 'value')]")
    public TextBlock paymentForConfirmedIncomeTextBlock;

    @Element("Поле ЕП для неподтвержденного дохода Екл")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, подбор')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'ЕП для неподтвержденного дохода')]]//span[contains(@class, 'value')]")
    public TextBlock paymentForUnconfirmedIncomeTextBlock;

    @Element("Поле ЕП для КК без подтверждения дохода Екл")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, подбор')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'ЕП для КК без подтверждения дохода')]]//span[contains(@class, 'value')]")
    public TextBlock paymentCcForUnconfirmedIncomeTextBlock;

    //СКП

    @Element("Выпадающий список СКП")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, подбор')]]//mat-panel-title[contains(text(), 'СКП')]")
    public Button skpEklList;

    @Element("Выпадающий список Card")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, подбор')]]//mat-panel-title[contains(text(), 'Card')]")
    public Button skpCardEklList;

    @Element("Поле Тип продукта Card")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, подбор')]]//mat-expansion-panel[.//mat-panel-title[contains(text(),'Card')]])[2]" +
            "//div[@class='ng-star-inserted']/div[contains(@class,'item')][.//span[contains(text(),'Тип продукта')]]/div[2]")
    public TextBlock typeProductCardTextBlock;

    @Element("Поле Бренд Card")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, подбор')]]//mat-expansion-panel[.//mat-panel-title[contains(text(),'Card')]])[2]" +
            "//div[@class='ng-star-inserted']/div[contains(@class,'item')][.//span[contains(text(),'Бренд')]]/div[2]")
    public TextBlock brandCardTextBlock;

    @Element("Поле Процентная ставка, без учета страхования ПК и КК: Card")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, подбор')]]//mat-expansion-panel[.//mat-panel-title[contains(text(),'Card')]])[2]" +
            "//div[@class='ng-star-inserted']/div[contains(@class,'item')][.//span[contains(text(),'Процентная ставка, без учета страхования ПК и КК')]]/div[2]")
    public TextBlock percentageCardTextBlock;

    @Element("Поле Процентная ставка с учетом страхования: Card")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, подбор')]]//mat-expansion-panel[.//mat-panel-title[contains(text(),'Card')]])[2]" +
            "//div[@class='ng-star-inserted']/div[contains(@class,'item')][.//span[contains(text(),'Процентная ставка с учетом страхования')]]/div[2]")
    public TextBlock percentageCardWithInsuranceTextBlock;

    @Element("Поле Ставка минимального платежа КК: Card")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, подбор')]]//mat-expansion-panel[.//mat-panel-title[contains(text(),'Card')]])[2]" +
            "//div[@class='ng-star-inserted']/div[contains(@class,'item')][.//span[contains(text(),'Ставка минимального платежа КК')]]/div[2]")
    public TextBlock minPaymentCardTextBlock;

    @Element("Поле Ставка страхового тарифа ПК: Card")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, подбор')]]//mat-expansion-panel[.//mat-panel-title[contains(text(),'Card')]])[2]" +
            "//div[@class='ng-star-inserted']/div[contains(@class,'item')][.//span[contains(text(),'Ставка страхового тарифа ПК')]]/div[2]")
    public TextBlock insuranceRateCardTextBlock;

    @Element("Поле Минимальный лимит по программе кредитования ПК и КК: Card")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, подбор')]]//mat-expansion-panel[.//mat-panel-title[contains(text(),'Card')]])[2]" +
            "//div[@class='ng-star-inserted']/div[contains(@class,'item')][.//span[contains(text(),'Минимальный лимит по программе кредитования ПК и КК')]]/div[2]")
    public TextBlock minLimitOnProgramCreditCardTextBlock;

    @Element("Поле Максимальный лимит по программе кредитования ПК и КК: Card")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, подбор')]]//mat-expansion-panel[.//mat-panel-title[contains(text(),'Card')]])[2]" +
            "//div[@class='ng-star-inserted']/div[contains(@class,'item')][.//span[contains(text(),'Максимальный лимит по программе кредитования ПК и КК')]]/div[2]")
    public TextBlock maxLimitOnProgramCreditCardTextBlock;

    @Element("Поле Минимальный срок кредитования по программе ПК: Card")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, подбор')]]//mat-expansion-panel[.//mat-panel-title[contains(text(),'Card')]])[2]" +
            "//div[@class='ng-star-inserted']/div[contains(@class,'item')][.//span[contains(text(),'Минимальный срок кредитования по программе ПК')]]/div[2]")
    public TextBlock minimumLoanTermCardTextBlock;

    @Element("Поле Максимальный срок кредитования по программе ПК Card")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, подбор')]]//mat-expansion-panel[.//mat-panel-title[contains(text(),'Card')]])[2]" +
            "//div[@class='ng-star-inserted']/div[contains(@class,'item')][.//span[contains(text(),'Максимальный срок кредитования по программе ПК')]]/div[2]")
    public TextBlock maximumLoanTermCardTextBlock;

    @Element("Выпадающий список Пакет документов")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, подбор')]]//mat-panel-title[contains(text(), 'Пакет документов')]")
    public Button packageDocumentsCardEklList;

    @Element("Выпадающий список Пакет документов Loan")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, подбор')]]//mat-panel-title[contains(text(), 'Loan')]/../../..//mat-panel-title[contains(text(), 'Пакет документов')]")
    public Button packageDocumentsCardEklListLoan;

    @Element("Выпадающий список Пакет документов Card")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, подбор')]]//mat-panel-title[contains(text(), 'Card')]/../../..//mat-panel-title[contains(text(), 'Пакет документов')]")
    public Button packageDocumentsCardEklListCard;

    @Element("Поле Наименование документа Card")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, подбор')]]//mat-expansion-panel[.//mat-panel-title[contains(text(),'Card')]])[2]" +
            "//div[@class='ng-star-inserted']/div[contains(@class,'table')][.//div[contains(text(),'Наименование документа')]]//div[contains(@class, 'wrapper ')][1]//span")
    public TextBlock nameDocCardTextBlock;

    @Element("Поле Обязательность документа Card")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, подбор')]]//mat-expansion-panel[.//mat-panel-title[contains(text(),'Card')]])[2]" +
            "//div[@class='ng-star-inserted']/div[contains(@class,'table')][.//div[contains(text(),'Обязательность')]]//div[contains(@class, 'wrapper ')][2]//span")
    public TextBlock mandatoryDocumentCardTextBlock;

    //loan
    @Element("Выпадающий список Loan")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, подбор')]]//mat-panel-title[contains(text(), 'Loan')]")
    public Button skpLoanEklList;

    @Element("Поле Тип продукта loan")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, подбор')]]//mat-expansion-panel[.//mat-panel-title[contains(text(),'Loan')]])[2]" +
            "//div[@class='ng-star-inserted']/div[contains(@class,'item')][.//span[contains(text(),'Тип продукта')]]/div[2]")
    public TextBlock typeProductLoanTextBlock;

    @Element("Поле Бренд loan")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, подбор')]]//mat-expansion-panel[.//mat-panel-title[contains(text(),'Loan')]])[2]" +
            "//div[@class='ng-star-inserted']/div[contains(@class,'item')][.//span[contains(text(),'Бренд')]]/div[2]")
    public TextBlock brandLoanTextBlock;

    @Element("Поле Процентная ставка, без учета страхования ПК и КК: loan")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, подбор')]]//mat-expansion-panel[.//mat-panel-title[contains(text(),'Loan')]])[2]" +
            "//div[@class='ng-star-inserted']/div[contains(@class,'item')][.//span[contains(text(),'Процентная ставка, без учета страхования ПК и КК')]]/div[2]")
    public TextBlock percentageLoanTextBlock;

    @Element("Поле Процентная ставка с учетом страхования: loan")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, подбор')]]//mat-expansion-panel[.//mat-panel-title[contains(text(),'Loan')]])[2]" +
            "//div[@class='ng-star-inserted']/div[contains(@class,'item')][.//span[contains(text(),'Процентная ставка с учетом страхования')]]/div[2]")
    public TextBlock percentageLoanWithInsuranceTextBlock;

    @Element("Поле Ставка минимального платежа КК: loan")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, подбор')]]//mat-expansion-panel[.//mat-panel-title[contains(text(),'Loan')]])[2]" +
            "//div[@class='ng-star-inserted']/div[contains(@class,'item')][.//span[contains(text(),'Ставка минимального платежа КК')]]/div[2]")
    public TextBlock minPaymentLoanTextBlock;

    @Element("Поле Ставка страхового тарифа ПК: loan")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, подбор')]]//mat-expansion-panel[.//mat-panel-title[contains(text(),'Loan')]])[2]" +
            "//div[@class='ng-star-inserted']/div[contains(@class,'item')][.//span[contains(text(),'Ставка страхового тарифа ПК')]]/div[2]")
    public TextBlock insuranceRateLoanTextBlock;

    @Element("Поле Минимальный лимит по программе кредитования ПК и КК: loan")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, подбор')]]//mat-expansion-panel[.//mat-panel-title[contains(text(),'Loan')]])[2]" +
            "//div[@class='ng-star-inserted']/div[contains(@class,'item')][.//span[contains(text(),'Минимальный лимит по программе кредитования ПК и КК')]]/div[2]")
    public TextBlock minLimitOnProgramCreditLoanTextBlock;

    @Element("Поле Максимальный лимит по программе кредитования ПК и КК: loan")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, подбор')]]//mat-expansion-panel[.//mat-panel-title[contains(text(),'Loan')]])[2]" +
            "//div[@class='ng-star-inserted']/div[contains(@class,'item')][.//span[contains(text(),'Максимальный лимит по программе кредитования ПК и КК')]]/div[2]")
    public TextBlock maxLimitOnProgramCreditLoanTextBlock;

    @Element("Поле Минимальный срок кредитования по программе ПК: loan")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, подбор')]]//mat-expansion-panel[.//mat-panel-title[contains(text(),'Loan')]])[2]" +
            "//div[@class='ng-star-inserted']/div[contains(@class,'item')][.//span[contains(text(),'Минимальный срок кредитования по программе ПК')]]/div[2]")
    public TextBlock minimumLoanTermLoanTextBlock;

    @Element("Поле Максимальный срок кредитования по программе ПК loan")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, подбор')]]//mat-expansion-panel[.//mat-panel-title[contains(text(),'Loan')]])[2]" +
            "//div[@class='ng-star-inserted']/div[contains(@class,'item')][.//span[contains(text(),'Максимальный срок кредитования по программе ПК')]]/div[2]")
    public TextBlock maximumLoanTermLoanTextBlock;

    @Element("Поле Наименование документа loan")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, подбор')]]//mat-expansion-panel[.//mat-panel-title[contains(text(),'Loan')]])[2]" +
            "//div[@class='ng-star-inserted']/div[contains(@class,'table')][.//div[contains(text(),'Наименование документа')]]//div[contains(@class, 'wrapper ')][1]//span")
    public TextBlock nameDocLoanTextBlock;

    @Element("Поле Обязательность документа loan")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, подбор')]]//mat-expansion-panel[.//mat-panel-title[contains(text(),'Loan')]])[2]" +
            "//div[@class='ng-star-inserted']/div[contains(@class,'table')][.//div[contains(text(),'Обязательность')]]//div[contains(@class, 'wrapper ')][2]//span")
    public TextBlock mandatoryDocumentLoanTextBlock;


    //ЕКЛ, выбрано клиентом

    @Element("Выпадающий список ЕКЛ, выбрано клиентом")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]")
    public Button eclChosenByClientListButton;

    @Element("Поле ЕКЛ макс Выбор клиентом")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'ЕКЛ макс')]]//span[contains(@class, 'value')]")
    public TextBlock eklMaxByClientTextBlock;

    @Element("Поле Процентная ставка выбранного бренда Выбор клиентом")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Процентная ставка выбранного бренда')]]//span[contains(@class, 'value')]")
    public TextBlock selectedBrandPercentageByClientTextBlock;

    @Element("Поле Максимальная сумма кредита по всем брендам Выбор клиентом")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Максимальная сумма кредита по всем брендам')]]//span[contains(@class, 'value')]")
    public TextBlock loanMaxBrandByClientTextBlock;

    @Element("Поле Максимальный срок кредита по всем брендам Выбор клиентом")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Максимальный срок кредита по всем брендам')]]//span[contains(@class, 'value')]")
    public TextBlock loanTermMaxBrandByClientTextBlock;

    @Element("Поле Максимальный лимит для КК Выбор клиентом")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Максимальный лимит для КК')]]//span[contains(@class, 'value')]")
    public TextBlock maxLimitKkByClientTextBlock;

    @Element("Поле Сумма ПК Выбор клиентом")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Сумма ПК')]]//span[contains(@class, 'value')]")
    public TextBlock sumPkByClientTextBlock;

    @Element("Поле Срок ПК Выбор клиентом")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Срок ПК')]]//span[contains(@class, 'value')]")
    public TextBlock pcTermByClientTextBlock;

    @Element("Поле Месячный платеж ПК Выбор клиентом")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Месячный платеж ПК')]]//span[contains(@class, 'value')]")
    public TextBlock pcMonthlyPaymentByClientTextBlock;

    @Element("Поле Лимит КК Выбор клиентом")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Лимит КК')]]//span[contains(@class, 'value')]")
    public TextBlock limitKkByClientTextBlock;

    @Element("Поле Минимальный платеж по КК Выбор клиентом")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Минимальный платеж по КК')]]//span[contains(@class, 'value')]")
    public TextBlock minimumPaymentCcByClientTextBlock;

    @Element("Поле Отказ от КК:Нет Выбор клиентом")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Отказ от КК')]]//span[contains(@class, 'value')]")
    public TextBlock refusalCcByClientTextBlock;

    @Element("Поле Отказ от ПК:Да Выбор клиентом")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Отказ от ПК')]]//span[contains(@class, 'value')]")
    public TextBlock refusalPcByClientTextBlock;

    @Element("Поле Остаток максимального лимита Выбор клиентом")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Остаток максимального лимита')]]//span[contains(@class, 'value')]")
    public TextBlock remainingMaximumLimitByClientTextBlock;

    @Element("Поле Рефинансирование Выбор клиентом")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Рефинансирование')]]//span[contains(@class, 'value')]")
    public TextBlock refinancingByClientTextBlock;

    @Element("Поле Страхование Выбор клиентом")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Страхование')]]//span[contains(@class, 'value')]")
    public TextBlock insuranceByClientTextBlock;

    @Element("Поле Платеж в нагрузке Выбор клиентом")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Платеж в нагрузке')]]//span[contains(@class, 'value')]")
    public TextBlock paymentLoadByClientTextBlock;

    @Element("Поле Платеж при рефинансировании ПК Выбор клиентом")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Платеж при рефинансировании ПК')]]//span[contains(@class, 'value')]")
    public TextBlock paymentRefinancingByClientTextBlock;

    @Element("Поле Непогашенная задолженность Выбор клиентом")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Непогашенная задолженность')]]//span[contains(@class, 'value')]")
    public TextBlock outstandingDebtByClientTextBlock;

    //ЕКЛ, выбрано клиентом, итоговый
    @Element("Выпадающий список ЕКЛ, выбрано клиентом, итоговый")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом, итоговый')]]")
    public Button eclChosenByClientListResultButton;

    @Element("Поле ЕКЛ макс Выбрано клиентом, итоговый")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом, итоговый')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'ЕКЛ макс')]]//span[contains(@class, 'value')]")
    public TextBlock eclMaxByClientResultTextBlock;

    @Element("Поле Процентная ставка выбранного бренда Выбрано клиентом, итоговый")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом, итоговый')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Процентная ставка выбранного бренда')]]//span[contains(@class, 'value')]")
    public TextBlock selectedBrandPercentageByClientResultTextBlock;

    @Element("Поле Максимальный срок кредита по всем брендам Выбрано клиентом, итоговый")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом, итоговый')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Максимальный срок кредита по всем брендам')]]//span[contains(@class, 'value')]")
    public TextBlock maxLoanTermForAllBrandsResultTextBlock;

    @Element("Поле Максимальный лимит для КК Выбрано клиентом, итоговый")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом, итоговый')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Максимальный лимит для КК')]]//span[contains(@class, 'value')]")
    public TextBlock maxLimitKkResultTextBlock;

    @Element("Поле Сумма ПК Выбрано клиентом, итоговый")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом, итоговый')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Сумма ПК')]]//span[contains(@class, 'value')]")
    public TextBlock sumPkByClientResultTextBlock;

    @Element("Поле Срок ПК Выбрано клиентом, итоговый")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом, итоговый')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Срок ПК')]]//span[contains(@class, 'value')]")
    public TextBlock pcTermByClientResultTextBlock;

    @Element("Поле Месячный платеж ПК Выбрано клиентом, итоговый")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом, итоговый')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Месячный платеж ПК')]]//span[contains(@class, 'value')]")
    public TextBlock pcMonthlyPaymentResultTextBlock;

    @Element("Поле Лимит КК Выбрано клиентом, итоговый")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом, итоговый')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Лимит КК')]]//span[contains(@class, 'value')]")
    public TextBlock limitKkByClientResultTextBlock;

    @Element("Поле Минимальный платеж по КК Выбрано клиентом, итоговый")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом, итоговый')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Минимальный платеж по КК')]]//span[contains(@class, 'value')]")
    public TextBlock minimumPaymentCcByClientResultTextBlock;

    @Element("Выпадающий список СКП Выбрано клиентом, итоговый")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом, итоговый')]]//mat-panel-title[contains(text(),'СКП')]")
    public Button skpDropDownButton;

    @Element("Выпадающий список Loan Выбрано клиентом, итоговый")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом, итоговый')]]//mat-panel-title[contains(text(),'Loan')]")
    public Button loanDropDownButton;

    @Element("Выпадающий список Пакет документов Loan Выбрано клиентом, итоговый")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]" +
            "//mat-panel-title[contains(text(),'СКП')]/../../following-sibling::div" +
            "//mat-panel-title[contains(text(),'Loan')]/../../..//mat-panel-title[contains(text(),'Пакет документов')])[2]")
    public Button documentPackageLoanResultDropDownButton;

    @Element("Выпадающий список Пакет документов Card Выбрано клиентом, итоговый")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом, итоговый')]]" +
            "//mat-expansion-panel[./mat-expansion-panel-header[.//mat-panel-title[contains(text(),'Card')]]]" +
            "//mat-panel-title[contains(text(),'Пакет документов')]")
    public Button documentPackageCardResultDropDownButton;

    @Element("Таблица Пакет документов Loan Выбрано клиентом, итоговый")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом, итоговый')]]" +
            "//mat-expansion-panel[./mat-expansion-panel-header[.//mat-panel-title[contains(text(),'Loan')]]]" +
            "//mat-expansion-panel[./mat-expansion-panel-header[.//mat-panel-title[contains(text(),'Пакет документов')]]]" +
            "/div[contains(@class,'mat-expansion-panel-content')]")
    @FindCellsBy(xpath = ".//div[contains(@class,'table-2-wrapper')]/span")
    @FindHeadersBy(xpath = ".//div[contains(@class,'header')]")
    public WebTable settingPrioritySettingsTable;

    @Element("Таблица Пакет документов Card Выбрано клиентом, итоговый")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом, итоговый')]]" +
            "//mat-expansion-panel[./mat-expansion-panel-header[.//mat-panel-title[contains(text(),'Card')]]]" +
            "//mat-expansion-panel[./mat-expansion-panel-header[.//mat-panel-title[contains(text(),'Пакет документов')]]]" +
            "/div[contains(@class,'mat-expansion-panel-content')]")
    @FindCellsBy(xpath = ".//div[contains(@class,'table-2-wrapper')]/span")
    @FindHeadersBy(xpath = ".//div[contains(@class,'header')]")
    public WebTable settingPriorityCardClientResultSettingsTable;

    @Element("Поле Платеж при рефинансировании ПК Выбрано клиентом, итоговый")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом, итоговый')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Платеж при рефинансировании ПК')]]//span[contains(@class, 'value')]")
    public TextBlock paymentRefinancingByClientResultTextBlock;

    @Element("Поле Отказ от КК Выбрано клиентом, итоговый")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом, итоговый')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Отказ от КК')]]//span[contains(@class, 'value')]")
    public TextBlock refusalCcResultTextBlock;

    @Element("Поле Отказ от ПК Выбрано клиентом, итоговый")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом, итоговый')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Отказ от ПК')]]//span[contains(@class, 'value')]")
    public TextBlock refusalPcByClientResultTextBlock;

    @Element("Поле Остаток максимального лимита Выбрано клиентом, итоговый")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом, итоговый')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Остаток максимального лимита')]]//span[contains(@class, 'value')]")
    public TextBlock remainingMaximumLimitByClientResultTextBlock;

    @Element("Поле Рефинансирование Выбрано клиентом, итоговый")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом, итоговый')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Рефинансирование')]]//span[contains(@class, 'value')]")
    public TextBlock refinancingByClientResultTextBlock;

    @Element("Поле Страхование Выбрано клиентом, итоговый")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом, итоговый')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Страхование')]]//span[contains(@class, 'value')]")
    public TextBlock insuranceByClientResultTextBlock;

    @Element("Поле Платеж в нагрузке Выбрано клиентом, итоговый")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом, итоговый')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Платеж в нагрузке')]]//span[contains(@class, 'value')]")
    public TextBlock paymentLoadByClientResultTextBlock;

    @Element("Поле Непогашенная задолженность Выбрано клиентом, итоговый")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом, итоговый')]]//div[contains(@class,'table-item')][.//span[contains(text(), 'Непогашенная задолженность')]]//span[contains(@class, 'value')]")
    public TextBlock outstandingВebtByClientResultTextBlock;

    //Card Выбрано клиентом
    @Element("Выпадающий список СКП Выбор клиентом")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]//mat-panel-title[contains(text(), 'СКП')]")
    public Button skpEklByClientList;

    @Element("Выпадающий список Card Выбор клиентом")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]//mat-panel-title[contains(text(), 'Card')]")
    public Button skpCardByClientList;

    @Element("Поле Тип продукта Card Выбор клиентом")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]//mat-expansion-panel[.//mat-panel-title[contains(text(),'Card')]])[2]" +
            "//div[@class='ng-star-inserted']/div[contains(@class,'item')][.//span[contains(text(),'Тип продукта')]]/div[2]")
    public TextBlock typeProductCardByClientTextBlock;

    @Element("Поле Бренд Card Выбор клиентом")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]//mat-expansion-panel[.//mat-panel-title[contains(text(),'Card')]])[2]" +
            "//div[@class='ng-star-inserted']/div[contains(@class,'item')][.//span[contains(text(),'Бренд')]]/div[2]")
    public TextBlock brandCardByClientTextBlock;

    @Element("Поле Процентная ставка, без учета страхования ПК и КК: Card Выбор клиентом")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]//mat-expansion-panel[.//mat-panel-title[contains(text(),'Card')]])[2]" +
            "//div[@class='ng-star-inserted']/div[contains(@class,'item')][.//span[contains(text(),'Процентная ставка, без учета страхования ПК и КК')]]/div[2]")
    public TextBlock percentageCardByClientTextBlock;

    @Element("Поле Процентная ставка с учетом страхования: Card Выбор клиентом")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]//mat-expansion-panel[.//mat-panel-title[contains(text(),'Card')]])[2]" +
            "//div[@class='ng-star-inserted']/div[contains(@class,'item')][.//span[contains(text(),'Процентная ставка с учетом страхования')]]/div[2]")
    public TextBlock percentageCardWithInsuranceByClientTextBlock;

    @Element("Поле Ставка минимального платежа КК: Card Выбор клиентом")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]//mat-expansion-panel[.//mat-panel-title[contains(text(),'Card')]])[2]" +
            "//div[@class='ng-star-inserted']/div[contains(@class,'item')][.//span[contains(text(),'Ставка минимального платежа КК')]]/div[2]")
    public TextBlock minPaymentCardByClientTextBlock;

    @Element("Поле Ставка страхового тарифа ПК: Card Выбор клиентом")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]//mat-expansion-panel[.//mat-panel-title[contains(text(),'Card')]])[2]" +
            "//div[@class='ng-star-inserted']/div[contains(@class,'item')][.//span[contains(text(),'Ставка страхового тарифа ПК')]]/div[2]")
    public TextBlock insuranceRateCardByClientTextBlock;

    @Element("Поле Минимальный лимит по программе кредитования ПК и КК: Card Выбор клиентом")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]//mat-expansion-panel[.//mat-panel-title[contains(text(),'Card')]])[2]" +
            "//div[@class='ng-star-inserted']/div[contains(@class,'item')][.//span[contains(text(),'Минимальный лимит по программе кредитования ПК и КК')]]/div[2]")
    public TextBlock minLimitOnProgramCreditCardByClientTextBlock;

    @Element("Поле Максимальный лимит по программе кредитования ПК и КК: Card Выбор клиентом")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]//mat-expansion-panel[.//mat-panel-title[contains(text(),'Card')]])[2]" +
            "//div[@class='ng-star-inserted']/div[contains(@class,'item')][.//span[contains(text(),'Максимальный лимит по программе кредитования ПК и КК')]]/div[2]")
    public TextBlock maxLimitOnProgramCreditCardByClientTextBlock;

    @Element("Поле Минимальный срок кредитования по программе ПК: Card Выбор клиентом")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]//mat-expansion-panel[.//mat-panel-title[contains(text(),'Card')]])[2]" +
            "//div[@class='ng-star-inserted']/div[contains(@class,'item')][.//span[contains(text(),'Минимальный срок кредитования по программе ПК')]]/div[2]")
    public TextBlock minimumLoanTermCardByClientTextBlock;

    @Element("Поле Максимальный срок кредитования по программе ПК Card Выбор клиентом")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]//mat-expansion-panel[.//mat-panel-title[contains(text(),'Card')]])[2]" +
            "//div[@class='ng-star-inserted']/div[contains(@class,'item')][.//span[contains(text(),'Максимальный срок кредитования по программе ПК')]]/div[2]")
    public TextBlock maximumLoanTermCardByClientTextBlock;

    @Element("Выпадающий список Пакет документов Выбор клиентом")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]" +
            "//mat-expansion-panel[.//mat-panel-title[contains(text(),'Card')]])[2]//mat-panel-title[contains(text(), 'Пакет документов')]")
    public Button packageDocumentsCardByClientEklList;

    @Element("Поле Наименование документа Card Выбор клиентом")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]//mat-expansion-panel[.//mat-panel-title[contains(text(),'Card')]])[2]" +
            "//div[@class='ng-star-inserted']/div[contains(@class,'table')][.//div[contains(text(),'Наименование документа')]]//div[contains(@class, 'wrapper ')][1]//span")
    public TextBlock nameDocCardByClientTextBlock;

    @Element("Поле Обязательность документа Card Выбор клиентом")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]//mat-expansion-panel[.//mat-panel-title[contains(text(),'Card')]])[2]" +
            "//div[@class='ng-star-inserted']/div[contains(@class,'table')][.//div[contains(text(),'Обязательность')]]//div[contains(@class, 'wrapper ')][2]//span")
    public TextBlock mandatoryDocumentCardByClientTextBlock;

    //Loan Выбрано клиентом
    @Element("Выпадающий список Loan Выбор клиентом")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]//mat-panel-title[contains(text(), 'Loan')]")
    public Button skpLoanByClientList;

    @Element("Поле Тип продукта Loan Выбор клиентом")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]" +
            "//mat-expansion-panel[.//mat-panel-title[contains(text(),'Loan')]])[2]//div[@class='ng-star-inserted']" +
            "/div[contains(@class,'item')][.//span[contains(text(),'Тип продукта')]]/div[2]")
    public TextBlock typeProductLoanByClientTextBlock;

    @Element("Поле Бренд Loan Выбор клиентом")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]" +
            "//mat-expansion-panel[.//mat-panel-title[contains(text(),'Loan')]])[2]//div[@class='ng-star-inserted']" +
            "/div[contains(@class,'item')][.//span[contains(text(),'Бренд')]]/div[2]")
    public TextBlock brandLoanByClientTextBlock;

    @Element("Поле Процентная ставка, без учета страхования ПК и КК: Loan Выбор клиентом")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]" +
            "//mat-expansion-panel[.//mat-panel-title[contains(text(),'Loan')]])[2]//div[@class='ng-star-inserted']" +
            "/div[contains(@class,'item')][.//span[contains(text(),'Процентная ставка, без учета страхования ПК и КК')]]/div[2]")
    public TextBlock percentageLoanByClientTextBlock;

    @Element("Поле Процентная ставка с учетом страхования: Loan Выбор клиентом")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]" +
            "//mat-expansion-panel[.//mat-panel-title[contains(text(),'Loan')]])[2]//div[@class='ng-star-inserted']" +
            "/div[contains(@class,'item')][.//span[contains(text(),'Процентная ставка с учетом страхования')]]/div[2]")
    public TextBlock percentageLoanWithInsuranceByClientTextBlock;

    @Element("Поле Ставка минимального платежа КК: Loan Выбор клиентом")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]" +
            "//mat-expansion-panel[.//mat-panel-title[contains(text(),'Loan')]])[2]//div[@class='ng-star-inserted']" +
            "/div[contains(@class,'item')][.//span[contains(text(),'Ставка минимального платежа КК')]]/div[2]")
    public TextBlock minPaymentLoanByClientTextBlock;

    @Element("Поле Ставка страхового тарифа ПК: Loan Выбор клиентом")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]" +
            "//mat-expansion-panel[.//mat-panel-title[contains(text(),'Loan')]])[2]//div[@class='ng-star-inserted']" +
            "/div[contains(@class,'item')][.//span[contains(text(),'Ставка страхового тарифа ПК')]]/div[2]")
    public TextBlock insuranceRateLoanByClientTextBlock;

    @Element("Поле Минимальный лимит по программе кредитования ПК и КК: Loan Выбор клиентом")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]" +
            "//mat-expansion-panel[.//mat-panel-title[contains(text(),'Loan')]])[2]//div[@class='ng-star-inserted']" +
            "/div[contains(@class,'item')][.//span[contains(text(),'Минимальный лимит по программе кредитования ПК и КК')]]/div[2]")
    public TextBlock minLimitOnProgramCreditLoanByClientTextBlock;

    @Element("Поле Максимальный лимит по программе кредитования ПК и КК: Loan Выбор клиентом")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]" +
            "//mat-expansion-panel[.//mat-panel-title[contains(text(),'Loan')]])[2]//div[@class='ng-star-inserted']" +
            "/div[contains(@class,'item')][.//span[contains(text(),'Максимальный лимит по программе кредитования ПК и КК')]]/div[2]")
    public TextBlock maxLimitOnProgramCreditLoanByClientTextBlock;

    @Element("Поле Минимальный срок кредитования по программе ПК: Loan Выбор клиентом")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]" +
            "//mat-expansion-panel[.//mat-panel-title[contains(text(),'Loan')]])[2]//div[@class='ng-star-inserted']" +
            "/div[contains(@class,'item')][.//span[contains(text(),'Минимальный срок кредитования по программе ПК')]]/div[2]")
    public TextBlock minimumLoanTermLoanByClientTextBlock;

    @Element("Поле Максимальный срок кредитования по программе ПК Loan Выбор клиентом")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]" +
            "//mat-expansion-panel[.//mat-panel-title[contains(text(),'Loan')]])[2]//div[@class='ng-star-inserted']" +
            "/div[contains(@class,'item')][.//span[contains(text(),'Максимальный срок кредитования по программе ПК')]]/div[2]")
    public TextBlock maximumLoanTermLoanByClientTextBlock;

    @Element("Выпадающий список Пакет документов Выбор клиентом Loan")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]" +
            "//mat-expansion-panel[.//mat-panel-title[contains(text(),'Loan')]])[2]//mat-panel-title[contains(text(), 'Пакет документов')]")
    public Button packageDocumentsLoanByClientEklList;

    @Element("Поле Наименование документа Loan Выбор клиентом")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]//mat-expansion-panel[.//mat-panel-title[contains(text(),'Loan')]])[2]" +
            "//div[@class='ng-star-inserted']/div[contains(@class,'table')][.//div[contains(text(),'Наименование документа')]]//div[contains(@class, 'wrapper ')][1]//span")
    public TextBlock nameDocLoanByClientTextBlock;

    @Element("Поле Обязательность документа Loan Выбор клиентом")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом')]]//mat-expansion-panel[.//mat-panel-title[contains(text(),'Loan')]])[2]" +
            "//div[@class='ng-star-inserted']/div[contains(@class,'table')][.//div[contains(text(),'Обязательность')]]//div[contains(@class, 'wrapper ')][2]//span")
    public TextBlock mandatoryDocumentLoanByClientTextBlock;

    //Выбор клиентом, итоговый
    @Element("Выпадающий список Card Выбрано клиентом, итоговый")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом, итоговый')]]//mat-panel-title[contains(text(),'Card')]")
    public Button cardDropDownButton;

    @Element("Поле Card Тип продукта Выбрано клиентом, итоговый")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом, итоговый')]]" +
            "//mat-expansion-panel[.//mat-panel-title[contains(text(),'Card')]])[last()]//div[@class='ng-star-inserted']" +
            "/div[contains(@class,'item')][.//span[contains(text(),'Тип продукта')]]/div[2]")
    public TextBlock cardProductTypeChooseByClientResultTextBlock;

    @Element("Поле Бренд Card Выбрано клиентом, итоговый")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом, итоговый')]]" +
            "//mat-expansion-panel[.//mat-panel-title[contains(text(),'Card')]])[last()]//div[@class='ng-star-inserted']" +
            "/div[contains(@class,'item')][.//span[contains(text(),'Бренд')]]/div[2]")
    public TextBlock brandCardChooseByClientResultTextBlock;

    @Element("Поле Процентная ставка, без учета страхования ПК и КК: Card Выбрано клиентом, итоговый")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом, итоговый')]]" +
            "//mat-expansion-panel[.//mat-panel-title[contains(text(),'Card')]])[last()]//div[@class='ng-star-inserted']" +
            "/div[contains(@class,'item')][.//span[contains(text(),'без учета страхования ПК и КК')]]/div[2]")
    public TextBlock percentageCardByClientResultTextBlock;

    @Element("Поле Процентная ставка с учетом страхования: Card Выбрано клиентом, итоговый")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом, итоговый')]]" +
            "//mat-expansion-panel[.//mat-panel-title[contains(text(),'Card')]])[last()]//div[@class='ng-star-inserted']" +
            "/div[contains(@class,'item')][.//span[contains(text(),'с учетом страхования')]]/div[2]")
    public TextBlock percentageCardWithInsuranceByClientResultTextBlock;

    @Element("Поле Ставка минимального платежа КК: Card Выбрано клиентом, итоговый")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом, итоговый')]]" +
            "//mat-expansion-panel[.//mat-panel-title[contains(text(),'Card')]])[last()]//div[@class='ng-star-inserted']" +
            "/div[contains(@class,'item')][.//span[contains(text(),'Ставка минимального платежа КК')]]/div[2]")
    public TextBlock minPaymentCardResultTextBlock;

    @Element("Поле Ставка страхового тарифа ПК: Card Выбрано клиентом, итоговый")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом, итоговый')]]" +
            "//mat-expansion-panel[.//mat-panel-title[contains(text(),'Card')]])[last()]//div[@class='ng-star-inserted']" +
            "/div[contains(@class,'item')][.//span[contains(text(),'Ставка страхового тарифа ПК')]]/div[2]")
    public TextBlock insuranceRateCardByClientResultTextBlock;

    @Element("Поле Минимальный лимит по программе кредитования ПК и КК: Card Выбрано клиентом, итоговый")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом, итоговый')]]" +
            "//mat-expansion-panel[.//mat-panel-title[contains(text(),'Card')]])[last()]//div[@class='ng-star-inserted']" +
            "/div[contains(@class,'item')][.//span[contains(text(),'Минимальный лимит по программе кредитования ПК и КК')]]/div[2]")
    public TextBlock minLimitOnProgramCreditCardByClientResultTextBlock;

    @Element("Выпадающий список Loan Выбрано клиентом, итоговый")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом, итоговый')]]//mat-panel-title[contains(text(),'Loan')]")
    public Button loanClientResultDropDownButton;

    @Element("Поле Loan Тип продукта Выбрано клиентом, итоговый")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом, итоговый')]]" +
            "//mat-expansion-panel[.//mat-panel-title[contains(text(),'Loan')]])[last()]//div[@class='ng-star-inserted']" +
            "/div[contains(@class,'item')][.//span[contains(text(),'Тип продукта')]]/div[2]")
    public TextBlock loanProductTypeChooseByClientResultTextBlock;

    @Element("Поле Бренд Loan Выбрано клиентом, итоговый")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом, итоговый')]]" +
            "//mat-expansion-panel[.//mat-panel-title[contains(text(),'Loan')]])[last()]//div[@class='ng-star-inserted']" +
            "/div[contains(@class,'item')][.//span[contains(text(),'Бренд')]]/div[2]")
    public TextBlock brandLoanChooseByClientResultTextBlock;

    @Element("Поле Процентная ставка, без учета страхования ПК и КК: Loan Выбрано клиентом, итоговый")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом, итоговый')]]" +
            "//mat-expansion-panel[.//mat-panel-title[contains(text(),'Loan')]])[last()]//div[@class='ng-star-inserted']" +
            "/div[contains(@class,'item')][.//span[contains(text(),'без учета страхования ПК и КК')]]/div[2]")
    public TextBlock percentageLoanByClientResultTextBlock;

    @Element("Поле Процентная ставка с учетом страхования: Loan Выбрано клиентом, итоговый")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом, итоговый')]]" +
            "//mat-expansion-panel[.//mat-panel-title[contains(text(),'Loan')]])[last()]//div[@class='ng-star-inserted']" +
            "/div[contains(@class,'item')][.//span[contains(text(),'с учетом страхования')]]/div[2]")
    public TextBlock percentageLoanWithInsuranceByClientResultTextBlock;

    @Element("Поле Ставка минимального платежа КК: Loan Выбрано клиентом, итоговый")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом, итоговый')]]" +
            "//mat-expansion-panel[.//mat-panel-title[contains(text(),'Loan')]])[last()]//div[@class='ng-star-inserted']" +
            "/div[contains(@class,'item')][.//span[contains(text(),'Ставка минимального платежа КК')]]/div[2]")
    public TextBlock minPaymentLoanResultTextBlock;

    @Element("Поле Ставка страхового тарифа ПК: Loan Выбрано клиентом, итоговый")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом, итоговый')]]" +
            "//mat-expansion-panel[.//mat-panel-title[contains(text(),'Loan')]])[last()]//div[@class='ng-star-inserted']" +
            "/div[contains(@class,'item')][.//span[contains(text(),'Ставка страхового тарифа ПК')]]/div[2]")
    public TextBlock insuranceRateLoanByClientResultTextBlock;

    @Element("Поле Максимальный лимит по программе кредитования ПК и КК: Loan Выбрано клиентом, итоговый")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом, итоговый')]]" +
            "//mat-expansion-panel[.//mat-panel-title[contains(text(),'Loan')]])[last()]//div[@class='ng-star-inserted']" +
            "/div[contains(@class,'item')][.//span[contains(text(),'Максимальный лимит по программе кредитования ПК и КК')]]/div[2]")
    public TextBlock maxLimitOnProgramCreditLoanByClientResultTextBlock;

    @Element("Поле Минимальный лимит по программе кредитования ПК и КК: Loan Выбрано клиентом, итоговый")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом, итоговый')]]" +
            "//mat-expansion-panel[.//mat-panel-title[contains(text(),'Loan')]])[last()]//div[@class='ng-star-inserted']" +
            "/div[contains(@class,'item')][.//span[contains(text(),'Минимальный лимит по программе кредитования ПК и КК')]]/div[2]")
    public TextBlock minLimitOnProgramCreditLoanByClientResultTextBlock;

    //Преандеррайтинг
    @Element("Выпадающий список Преандеррайтинг")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), ' Преандерайтинг ')]]")
    public Button preunderwritingDropDownButton;

    @Element("Поле Преандеррайтинг Да")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), ' Преандерайтинг ')]]" +
            "//div[contains(@class,'table-item')][.//span[contains(text(), 'Преандеррайтинг')]]//span[contains(@class, 'value')]")
    public TextBlock preunderwritingYesTextBlock;

    @Element("Поле Корректировка данных")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), ' Преандерайтинг ')]]" +
            "//div[contains(@class,'table-item')][.//span[contains(text(), 'Корректировка данных')]]//span[contains(@class, 'value')]")
    public TextBlock preunderwritingOtherDataTextBlock;

    //Принятие решения
    @Element("Выпадающий список Принятие решения")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), ' Принятие решения ')]]")
    public Button decisionMakingDropDownButton;

    @Element("Поле Проверка EP_MAX")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), ' Принятие решения ')]]" +
            "//div[contains(@class,'table-item')][.//span[contains(text(), 'Проверка EP_MAX')]]//span[contains(@class, 'value')]")
    public TextBlock checkingEpMaxTextBlock;

    @Element("Поле Проверка СФ")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), ' Принятие решения ')]]" +
            "//div[contains(@class,'table-item')][.//span[contains(text(), 'Проверка СФ')]]//span[contains(@class, 'value')]")
    public TextBlock checkingSfTextBlock;

    @Element("Поле Проверка отказов на предыдущих этапах")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), ' Принятие решения ')]]" +
            "//div[contains(@class,'table-item')][.//span[contains(text(), 'Проверка отказов на предыдущих этапах')]]//span[contains(@class, 'value')]")
    public TextBlock checkingFailuresPreviousStepsTextBlock;

    @Element("Поле БКИ (NBKI)")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), ' Принятие решения ')]]" +
            "//div[contains(@class,'table-item')][.//span[contains(text(), 'БКИ (NBKI)')]]//span[contains(@class, 'value')]")
    public TextBlock checkingNbkiTextBlock;

    @Element("Поле БКИ (EQUIFAX)")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), ' Принятие решения ')]]" +
            "//div[contains(@class,'table-item')][.//span[contains(text(), 'БКИ (EQUIFAX)')]]//span[contains(@class, 'value')]")
    public TextBlock checkingEquifaxTextBlock;

    @Element("Поле Проверка скорбалла")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), ' Принятие решения ')]]" +
            "//div[contains(@class,'table-item')][.//span[contains(text(), 'Проверка скорбалла')]]//span[contains(@class, 'value')]")
    public TextBlock checkScoreTextBlock;

    @Element("Поле Ограничение лимита")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), ' Принятие решения ')]]" +
            "//div[contains(@class,'table-item')][.//span[contains(text(), 'Ограничение лимита')]]//span[contains(@class, 'value')]")
    public TextBlock checkLimitTextBlock;

    @Element("Поле Обработка ответа ЕКЛ")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), ' Принятие решения ')]]" +
            "//div[contains(@class,'table-item')][.//span[contains(text(), 'Обработка ответа ЕКЛ')]]//span[contains(@class, 'value')]")
    public TextBlock checkEklTextBlock;

    @Element("Поле Обработка результатов преандеррайтинга")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), ' Принятие решения ')]]" +
            "//div[contains(@class,'table-item')][.//span[contains(text(), 'Обработка результатов преандеррайтинга')]]//span[contains(@class, 'value')]")
    public TextBlock checkResultsProcessingTextBlock;

    @Element("Поле Критерии отправки на серую зону")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), ' Принятие решения ')]]" +
            "//div[contains(@class,'table-item')][.//span[contains(text(), 'Критерии отправки на серую зону')]]//span[contains(@class, 'value')]")
    public TextBlock checkCriteriaSendingGrayZoneTextBlock;

    @Element("Поле Обработка стратегий ручных проверок (2)")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), ' Принятие решения ')]]" +
            "//div[contains(@class,'table-item')][.//span[contains(text(), 'Обработка стратегий ручных проверок (2)')]]//span[contains(@class, 'value')]")
    public TextBlock handlingStrategiesTextBlock;

    @Element("Поле Валидация лимита")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), ' Принятие решения ')]]" +
            "//div[contains(@class,'table-item')][.//span[contains(text(), 'Валидация лимита')]]//span[contains(@class, 'value')]")
    public TextBlock checkValidationLimitTextBlock;

    @Element("Поле ПДН Принятие решения")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), ' Принятие решения ')]]" +
            "//div[contains(@class,'table-item')][.//span[contains(text(), 'ПДН')]]//span[contains(@class, 'value')]")
    public TextBlock checkPdnTextBlock;

    @Element("Поле Маршрутизация доработки")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), ' Принятие решения ')]]" +
            "//div[contains(@class,'table-item')][.//span[contains(text(), 'Маршрутизация доработки')]]//span[contains(@class, 'value')]")
    public TextBlock refinementRoutingTextBlock;

    @Element("Поле Причина доработки")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), ' Принятие решения ')]]" +
            "//div[contains(@class,'table-item')][.//span[contains(text(), 'Причина доработки')]]//span[contains(@class, 'value')]")
    public TextBlock reasonRevisionTextBlock;

    @Element("Поле Причина отказа")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), ' Принятие решения ')]]" +
            "//div[contains(@class,'table-item')][.//span[contains(text(), 'Причина отказа')]]//span[contains(@class, 'value')]")
    public TextBlock reasonRejectionTextBlock;

    //Критерии отправки на СЗ

    @Element("Выпадающий список Критерии отправки на СЗ")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[normalize-space()='Критерии отправки на СЗ']]")
    public Button criteriaSendingSzDropDownButton;

    @Element("Выпадающий список Критерии отправки на СЗ-Клиент")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(normalize-space(), 'Критерии отправки на СЗ')]]/..//mat-panel-title[contains(text(), 'Клиент')]")
    public Button criteriaSendingSzClientsDropDownButton;

    @Element("Таблица Критерии отправки на СЗ-Клиент")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(normalize-space(), 'Критерии отправки на СЗ')]]/div[contains(@class,'mat-expansion-panel-content')]//mat-expansion-panel//div[@role='region']/div")
    @FindCellsBy(xpath = ".//div[contains(@class,'table-3-wrapper')]/span")
    @FindHeadersBy(xpath = ".//div[contains(@class,'header')]")
    public WebTable clientCriteriaSendingSzTable;

    @Element("Выпадающий список Критерии отправки на СЗ-Основное место работы")
    @FindBy(xpath = "//mat-expansion-panel-header[contains(normalize-space(), 'Критерии отправки на СЗ')]/..//mat-expansion-panel-header[contains(normalize-space(), 'Основное место работы')]")
    public Button mainPlaceCriteriaSendingSzClientsDropDownButton;

    @Element("Таблица Критерии отправки на СЗ-Основное место работы")
    @FindBy(xpath = "//mat-expansion-panel-header[contains(normalize-space(), 'Критерии отправки на СЗ')]/..//mat-expansion-panel-header[contains(normalize-space(), 'Основное место работы')]/following-sibling::div[@role='region']/div")
    @FindCellsBy(xpath = ".//div[contains(@class,'table-3-wrapper')]/span")
    @FindHeadersBy(xpath = ".//div[contains(@class,'header')]")
    public WebTable mainPlaceCriteriaSendingSzTable;

    @Element("Выпадающий список Критерии отправки на СЗ-Совместительство")
    @FindBy(xpath = "//mat-expansion-panel-header[contains(normalize-space(), 'Критерии отправки на СЗ')]/..//mat-expansion-panel-header[contains(normalize-space(), 'Совместительство')]//mat-panel-title")
    public TextBlock partTimeCriteriaSendingSzClientsDropDownButton;

    @Element("Таблица Критерии отправки на СЗ-Совместительство")
    @FindBy(xpath = "//mat-expansion-panel-header[contains(normalize-space(), 'Критерии отправки на СЗ')]/..//mat-expansion-panel-header[contains(normalize-space(), 'Совместительство')]/following-sibling::div[@role='region']/div")
    @FindCellsBy(xpath = ".//div[contains(@class,'table-3-wrapper')]/span")
    @FindHeadersBy(xpath = ".//div[contains(@class,'header')]")
    public WebTable partTimeCriteriaSendingSzTable;

    //Стоп-факторы

    @Element("Выпадающий список Стоп-факторы")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Стоп-факторы')]]")
    public Button stopFactorsDropDownButton;

    @Element("Выпадающий список Стоп-факторы-Клиент")
    @FindBy(xpath = "//mat-panel-title[normalize-space()='Стоп-факторы']/../../..//mat-panel-title[normalize-space()='Клиент']")
    public Button stopFactorsClientDropDownButton;

    @Element("Таблица Стоп-факторы-Клиент")
    @FindBy(xpath = "//mat-panel-title[normalize-space()='Стоп-факторы']/../../..//mat-panel-title[normalize-space()='Клиент']/../../following-sibling::div[contains(@class,'mat-expansion-panel-content')]")
    @FindCellsBy(xpath = ".//div[contains(@class,'table-3-wrapper')]/span")
    @FindHeadersBy(xpath = ".//div[contains(@class,'header')]")
    public WebTable stopFactorsTable;

    @Element("Поле Общий результат Стоп-факторы")
    @FindBy(xpath = "//mat-panel-title[normalize-space()='Стоп-факторы']/../../..//span[normalize-space()='Общий результат']/../following-sibling::div/span")
    public TextBlock overallResultTextBlock;

    @Element("Поле Список признаков после стоп-факторов")
    @FindBy(xpath = "//mat-panel-title[normalize-space()='Стоп-факторы']/../../..//span[normalize-space()='Список признаков после стоп-факторов']/../following-sibling::div/span")
    public TextBlock listStopFactorsTextBlock;


    //Итоговое решение

    @Element("Выпадающий список Итоговое решение")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Итоговое решение')]]")
    public Button finalDecisionDropDownButton;

    @Element("Поле Решение СПР по заявке")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Итоговое решение')]]" +
            "//div[contains(@class,'table-item')][.//span[contains(text(), 'Решение СПР по заявке')]]//span[contains(@class, 'value')]")
    public TextBlock decisionSprTextBlock;

    @Element("Поле Последний метод вызова")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Итоговое решение')]]" +
            "//div[contains(@class,'table-item')][.//span[contains(text(), 'Последний метод вызова')]]//span[contains(@class, 'value')]")
    public TextBlock lastCallMethodTextBlock;

    @Element("Поле Отклонения по заявке по сегментам")
    @FindBy(xpath = "//mat-expansion-panel[.//mat-panel-title[contains(text(), 'Итоговое решение')]]" +
            "//div[contains(@class,'table-item')][.//span[contains(text(), 'Отклонения по заявке по сегментам')]]//span[contains(@class, 'value')]")
    public TextBlock deviationsOnApplicationTextBlock;


    @Element("Поле Максимальный лимит по программе кредитования ПК и КК: Card Выбрано клиентом, итоговый")
    @FindBy(xpath = "(//mat-expansion-panel[.//mat-panel-title[contains(text(), 'ЕКЛ, выбрано клиентом, итоговый')]]" +
            "//mat-expansion-panel[.//mat-panel-title[contains(text(),'Card')]])[last()]//div[@class='ng-star-inserted']" +
            "/div[contains(@class,'item')][.//span[contains(text(),'Максимальный лимит по программе кредитования ПК и КК')]]/div[2]")
    public TextBlock maxLimitOnProgramCreditCardByClientResultTextBlock;
    //TODO нет возможности разделить класс?

}
