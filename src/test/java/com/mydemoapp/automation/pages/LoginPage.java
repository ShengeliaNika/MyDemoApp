package com.mydemoapp.automation.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;

/** Any non-empty password authenticates any username except alice@example.com, which the app locks out. */
public class LoginPage extends BasePage {

    public static final String STANDARD_USER = "bob@example.com";
    public static final String LOCKED_OUT_USER = "alice@example.com";
    public static final String VISUAL_USER = "visual@example.com";
    public static final String PASSWORD = "10203040";

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/loginTV")
    private WebElement screenTitle;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/nameET")
    private WebElement usernameInput;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/passwordET")
    private WebElement passwordInput;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/loginBtn")
    private WebElement loginButton;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/nameErrorTV")
    private WebElement usernameErrorText;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/passwordErrorTV")
    private WebElement passwordErrorText;

    public LoginPage(AndroidDriver driver) {
        super(driver);
    }

    public String getScreenTitle() {
        return textOf(screenTitle);
    }

    /** Logs in and follows the app to the catalog screen, as it does on success. */
    @Step("Log in as \"{username}\"")
    public CatalogPage loginAs(String username, String password) {
        type(usernameInput, username);
        type(passwordInput, password);
        click(loginButton);
        return new CatalogPage(driver);
    }

    @Step("Log in as the standard user")
    public CatalogPage loginAsStandardUser() {
        return loginAs(STANDARD_USER, PASSWORD);
    }

    /** Submits the form and stays on the Login screen, for negative/validation tests. */
    @Step("Submit login form with username=\"{username}\", password=\"{password}\"")
    public LoginPage submitInvalidCredentials(String username, String password) {
        type(usernameInput, username);
        type(passwordInput, password);
        click(loginButton);
        return this;
    }

    public boolean isUsernameErrorDisplayed() {
        return isDisplayed(usernameErrorText);
    }

    public boolean isPasswordErrorDisplayed() {
        return isDisplayed(passwordErrorText);
    }

    public String getUsernameErrorText() {
        return textOf(usernameErrorText);
    }

    public String getPasswordErrorText() {
        return textOf(passwordErrorText);
    }
}
