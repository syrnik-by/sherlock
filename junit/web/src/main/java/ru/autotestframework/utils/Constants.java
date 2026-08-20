package ru.autotestframework.utils;

import com.codeborne.selenide.SelenideElement;

import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$x;

public class Constants {

    public static final String REQUESTS = "requests";
    public static final String VERIFICATION = "verification";
    public static final String EMPLOYEE = "employee";

    public static final int TIMEOUT = 20;
    public static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public static final SelenideElement SPINNER = $x("//mat-spinner[@role='progressbar']|//div[contains(@class,'psb-spinner-root')]");
    public static final SelenideElement BODY = $x("//body");
    public static final String TABLE_ROWS = ".//tbody/tr | .//tr[position()>1]";
}
