package com.mydemoapp.automation.tests;

import com.mydemoapp.automation.base.BaseTest;
import com.mydemoapp.automation.models.ShippingAddress;
import com.mydemoapp.automation.pages.CartPage;
import com.mydemoapp.automation.pages.CheckoutInfoPage;
import com.mydemoapp.automation.pages.LoginPage;
import com.mydemoapp.automation.pages.ProductDetailPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.mydemoapp.automation.pages.CatalogPage.SAUCE_LABS_BACKPACK;
import static com.mydemoapp.automation.pages.LoginPage.LOCKED_OUT_USER;
import static com.mydemoapp.automation.pages.LoginPage.PASSWORD;
import static com.mydemoapp.automation.pages.LoginPage.STANDARD_USER;

@Epic("My Demo App")
@Feature("Validation messages")
public class ValidationTests extends BaseTest {

    @Test(description = "Submitting the login form with no username shows a 'Username is required' error")
    public void loginWithEmptyUsernameShowsRequiredError() {
        LoginPage loginPage = catalogPage.openMenu().openLogin();

        loginPage.submitInvalidCredentials("", PASSWORD);

        Assert.assertTrue(loginPage.isUsernameErrorDisplayed(), "Username error should be visible");
        Assert.assertEquals(loginPage.getUsernameErrorText(), "Username is required");
    }

    @Test(description = "Submitting the login form with no password shows a password-required error")
    public void loginWithEmptyPasswordShowsRequiredError() {
        LoginPage loginPage = catalogPage.openMenu().openLogin();

        loginPage.submitInvalidCredentials(STANDARD_USER, "");

        Assert.assertTrue(loginPage.isPasswordErrorDisplayed(), "Password error should be visible");
        Assert.assertEquals(loginPage.getPasswordErrorText(), "Enter Password");
    }

    @Test(description = "Logging in as the known locked-out user shows a 'locked out' error instead of succeeding")
    public void loginAsLockedOutUserShowsLockedOutError() {
        LoginPage loginPage = catalogPage.openMenu().openLogin();

        loginPage.submitInvalidCredentials(LOCKED_OUT_USER, PASSWORD);

        Assert.assertTrue(loginPage.isPasswordErrorDisplayed(), "Locked-out error should be visible");
        Assert.assertEquals(loginPage.getPasswordErrorText(), "Sorry this user has been locked out.");
    }

    @Test(description = "Submitting the shipping address form with every field empty surfaces field-level validation errors")
    public void checkoutInfoWithEmptyFieldsShowsValidationErrors() {
        catalogPage = catalogPage.openMenu().openLogin().loginAsStandardUser();
        ProductDetailPage detailPage = catalogPage.selectProduct(SAUCE_LABS_BACKPACK);
        detailPage.addToCart();
        CartPage cartPage = detailPage.openCart();
        CheckoutInfoPage checkoutInfoPage = cartPage.proceedToCheckout();

        checkoutInfoPage.fillShippingAddress(ShippingAddress.empty());
        checkoutInfoPage.submitAndExpectValidationErrors();

        Assert.assertTrue(checkoutInfoPage.hasAnyValidationErrorDisplayed(),
                "Submitting an empty shipping form should surface validation errors instead of proceeding");
        Assert.assertTrue(checkoutInfoPage.isFullNameErrorDisplayed(), "Full name is a required field");
        Assert.assertTrue(checkoutInfoPage.isZipErrorDisplayed(), "Zip code is a required field");
    }

    @Test(description = "Attempting checkout as a guest redirects to the Login screen instead of the shipping form")
    public void proceedToCheckoutAsGuestRedirectsToLogin() {
        ProductDetailPage detailPage = catalogPage.selectProduct(SAUCE_LABS_BACKPACK);
        detailPage.addToCart();
        CartPage cartPage = detailPage.openCart();

        LoginPage loginPage = cartPage.proceedToCheckoutAsGuest();

        Assert.assertEquals(loginPage.getScreenTitle(), "Login",
                "Checkout should require authentication and route guests to the Login screen");
    }
}
