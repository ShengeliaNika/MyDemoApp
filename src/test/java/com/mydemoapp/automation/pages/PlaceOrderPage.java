package com.mydemoapp.automation.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;

/** "Review your order" step — the final confirmation before placing the order. */
public class PlaceOrderPage extends BasePage {

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/totalAmountTV")
    private WebElement totalAmountText;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/paymentBtn")
    private WebElement placeOrderButton;

    public PlaceOrderPage(AndroidDriver driver) {
        super(driver);
    }

    public String getTotalAmountText() {
        return textOf(totalAmountText);
    }

    @Step("Place order")
    public CheckoutCompletePage placeOrder() {
        click(placeOrderButton);
        return new CheckoutCompletePage(driver);
    }
}
