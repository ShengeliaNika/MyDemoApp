package com.mydemoapp.automation.pages;

import com.mydemoapp.automation.models.ShippingAddress;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;

/** "Enter a shipping address" step of checkout. */
public class CheckoutInfoPage extends BasePage {

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/fullNameET")
    private WebElement fullNameInput;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/address1ET")
    private WebElement addressLine1Input;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/address2ET")
    private WebElement addressLine2Input;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/cityET")
    private WebElement cityInput;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/stateET")
    private WebElement stateInput;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/zipET")
    private WebElement zipInput;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/countryET")
    private WebElement countryInput;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/paymentBtn")
    private WebElement toPaymentButton;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/fullNameErrorTV")
    private WebElement fullNameErrorText;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/address1ErrorTV")
    private WebElement addressLine1ErrorText;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/cityErrorTV")
    private WebElement cityErrorText;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/zipErrorTV")
    private WebElement zipErrorText;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/countryErrorTV")
    private WebElement countryErrorText;

    public CheckoutInfoPage(AndroidDriver driver) {
        super(driver);
    }

    @Step("Fill shipping address form")
    public CheckoutInfoPage fillShippingAddress(ShippingAddress address) {
        type(fullNameInput, address.fullName());
        type(addressLine1Input, address.addressLine1());
        type(addressLine2Input, address.addressLine2());
        type(cityInput, address.city());
        type(stateInput, address.state());
        type(zipInput, address.zip());
        type(countryInput, address.country());
        return this;
    }

    /** Submits the form and follows the app to the payment step, for the happy path. */
    @Step("Continue to payment")
    public CheckoutPaymentPage continueToPayment() {
        click(toPaymentButton);
        return new CheckoutPaymentPage(driver);
    }

    /** Submits the form and stays here, for validation tests against incomplete data. */
    @Step("Submit shipping form expecting validation errors")
    public CheckoutInfoPage submitAndExpectValidationErrors() {
        click(toPaymentButton);
        return this;
    }

    public boolean hasAnyValidationErrorDisplayed() {
        return isDisplayed(fullNameErrorText)
                || isDisplayed(addressLine1ErrorText)
                || isDisplayed(cityErrorText)
                || isDisplayed(zipErrorText)
                || isDisplayed(countryErrorText);
    }

    public boolean isFullNameErrorDisplayed() {
        return isDisplayed(fullNameErrorText);
    }

    public boolean isZipErrorDisplayed() {
        return isDisplayed(zipErrorText);
    }
}
