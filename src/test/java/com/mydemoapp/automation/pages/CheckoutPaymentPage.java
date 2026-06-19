package com.mydemoapp.automation.pages;

import com.mydemoapp.automation.models.PaymentCard;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;

/** "Enter a payment method" step of checkout. */
public class CheckoutPaymentPage extends BasePage {

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/nameET")
    private WebElement cardHolderNameInput;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/cardNumberET")
    private WebElement cardNumberInput;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/expirationDateET")
    private WebElement expirationDateInput;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/securityCodeET")
    private WebElement securityCodeInput;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/paymentBtn")
    private WebElement reviewOrderButton;

    public CheckoutPaymentPage(AndroidDriver driver) {
        super(driver);
    }

    @Step("Fill payment details")
    public CheckoutPaymentPage fillPaymentDetails(PaymentCard card) {
        type(cardHolderNameInput, card.cardHolderName());
        type(cardNumberInput, card.cardNumber());
        type(expirationDateInput, card.expirationDate());
        type(securityCodeInput, card.securityCode());
        return this;
    }

    @Step("Review order")
    public PlaceOrderPage reviewOrder() {
        click(reviewOrderButton);
        return new PlaceOrderPage(driver);
    }
}
