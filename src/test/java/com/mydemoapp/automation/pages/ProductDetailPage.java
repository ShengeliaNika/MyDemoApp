package com.mydemoapp.automation.pages;

import com.mydemoapp.automation.pages.components.AppHeader;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;

/** The product detail screen reached by tapping a catalog item. */
public class ProductDetailPage extends BasePage {

    public final AppHeader header;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/productTV")
    private WebElement productTitle;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/priceTV")
    private WebElement productPrice;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/plusIV")
    private WebElement increaseQuantityButton;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/minusIV")
    private WebElement decreaseQuantityButton;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/noTV")
    private WebElement quantityText;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/cartBt")
    private WebElement addToCartButton;

    public ProductDetailPage(AndroidDriver driver) {
        super(driver);
        this.header = new AppHeader(driver);
    }

    @Step("Open cart")
    public CartPage openCart() {
        header.openCart();
        return new CartPage(driver);
    }

    public String getProductTitle() {
        return textOf(productTitle);
    }

    public String getProductPrice() {
        return textOf(productPrice);
    }

    public int getQuantity() {
        return Integer.parseInt(textOf(quantityText));
    }

    @Step("Increase quantity by {times}")
    public ProductDetailPage increaseQuantity(int times) {
        for (int i = 0; i < times; i++) {
            click(increaseQuantityButton);
        }
        return this;
    }

    @Step("Decrease quantity by {times}")
    public ProductDetailPage decreaseQuantity(int times) {
        for (int i = 0; i < times; i++) {
            click(decreaseQuantityButton);
        }
        return this;
    }

    /** Adds the current quantity to the cart and stays on this screen, mirroring app behavior. */
    @Step("Add product to cart")
    public ProductDetailPage addToCart() {
        click(addToCartButton);
        return this;
    }
}
