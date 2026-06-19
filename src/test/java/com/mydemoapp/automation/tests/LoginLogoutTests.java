package com.mydemoapp.automation.tests;

import com.mydemoapp.automation.base.BaseTest;
import com.mydemoapp.automation.pages.LoginPage;
import com.mydemoapp.automation.pages.MenuPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("My Demo App")
@Feature("Login / Logout")
public class LoginLogoutTests extends BaseTest {

    @Test(description = "A user can log in with valid credentials and the menu reflects the logged-in state")
    public void loginWithValidCredentialsSucceeds() {
        MenuPage menu = catalogPage.openMenu();
        LoginPage loginPage = menu.openLogin();

        catalogPage = loginPage.loginAsStandardUser();

        Assert.assertEquals(catalogPage.getScreenTitle(), "Products",
                "Successful login should land the user back on the product catalog");
        Assert.assertTrue(catalogPage.openMenu().isUserLoggedIn(),
                "Navigation drawer should now offer 'Log Out' instead of 'Log In'");
    }

    @Test(description = "A logged-in user can log out and the app returns to the Login screen as a guest")
    public void logoutReturnsToLoginScreen() {
        LoginPage loginPage = catalogPage.openMenu().openLogin();
        catalogPage = loginPage.loginAsStandardUser();

        MenuPage menu = catalogPage.openMenu();
        Assert.assertTrue(menu.isUserLoggedIn(), "Precondition: user must be logged in before testing logout");

        LoginPage loginScreenAfterLogout = menu.logout();

        Assert.assertEquals(loginScreenAfterLogout.getScreenTitle(), "Login",
                "Logging out should return the user to the Login screen");
        Assert.assertFalse(loginScreenAfterLogout.isUsernameErrorDisplayed(),
                "Logout should present a clean Login screen with no stale validation state");
    }
}
