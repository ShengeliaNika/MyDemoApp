package com.mydemoapp.automation.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;

/** The slide-out navigation drawer; rows have no resource-ids, so most are matched by visible label. */
public class MenuPage extends BasePage {

    @AndroidFindBy(accessibility = "Login Menu Item")
    private WebElement loginMenuItem;

    @AndroidFindBy(accessibility = "Logout Menu Item")
    private WebElement logoutMenuItem;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Catalog\")")
    private WebElement catalogMenuItem;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"About\")")
    private WebElement aboutMenuItem;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"WebView\")")
    private WebElement webViewMenuItem;

    // Native AlertDialog confirming "Log Out" - standard AOSP button ids.
    @AndroidFindBy(id = "android:id/button1")
    private WebElement logoutConfirmButton;

    @AndroidFindBy(id = "android:id/button2")
    private WebElement logoutCancelButton;

    public MenuPage(AndroidDriver driver) {
        super(driver);
    }

    @Step("Open Login screen from the navigation drawer")
    public LoginPage openLogin() {
        click(loginMenuItem);
        return new LoginPage(driver);
    }

    @Step("Open Catalog screen from the navigation drawer")
    public CatalogPage openCatalog() {
        click(catalogMenuItem);
        return new CatalogPage(driver);
    }

    @Step("Open About screen from the navigation drawer")
    public AboutPage openAbout() {
        click(aboutMenuItem);
        return new AboutPage(driver);
    }

    @Step("Log out and confirm the dialog")
    public LoginPage logout() {
        click(logoutMenuItem);
        click(logoutConfirmButton);
        return new LoginPage(driver);
    }

    public boolean isUserLoggedIn() {
        return isDisplayed(logoutMenuItem);
    }
}
