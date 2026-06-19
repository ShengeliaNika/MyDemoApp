package com.mydemoapp.automation.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;

/** Final "Checkout Complete" confirmation screen. */
public class CheckoutCompletePage extends BasePage {

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/completeTV")
    private WebElement completeTitle;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/thankYouTV")
    private WebElement thankYouText;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/shoopingBt")
    private WebElement continueShoppingButton;

    public CheckoutCompletePage(AndroidDriver driver) {
        super(driver);
    }

    public String getCompleteTitle() {
        return textOf(completeTitle);
    }

    public String getThankYouText() {
        return textOf(thankYouText);
    }

    @Step("Continue shopping")
    public CatalogPage continueShopping() {
        click(continueShoppingButton);
        return new CatalogPage(driver);
    }
}
