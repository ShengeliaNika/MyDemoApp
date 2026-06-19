package com.mydemoapp.automation.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;

/**
 * The slide-out navigation drawer. "Login"/"Logout" are matched by
 * accessibility id (the app sets a distinct {@code contentDescription} for
 * that entry specifically so it can be identified regardless of its label),
 * the remaining static entries are matched by their visible label since the
 * app does not expose resource-ids for individual drawer rows.
 */
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

    // Native AlertDialog shown by "Log Out" - standard AOSP button ids.
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

    /**
     * Opens "Log Out" and confirms the dialog. The app restarts {@code MainActivity}
     * and lands back on the Login screen (see {@code MainActivity#showLogoutAlertDialog}).
     */
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
