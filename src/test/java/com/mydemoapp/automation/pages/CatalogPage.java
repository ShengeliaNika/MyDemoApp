package com.mydemoapp.automation.pages;

import com.mydemoapp.automation.pages.components.AppHeader;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;

/** The product catalog ("Products") screen — the app's default landing screen. */
public class CatalogPage extends BasePage {

    public static final String SAUCE_LABS_BACKPACK = "Sauce Labs Backpack";

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/productTV")
    private WebElement screenTitle;

    public final AppHeader header;

    public CatalogPage(AndroidDriver driver) {
        super(driver);
        this.header = new AppHeader(driver);
    }

    public String getScreenTitle() {
        return textOf(screenTitle);
    }

    @Step("Open navigation drawer")
    public MenuPage openMenu() {
        header.openMenu();
        return new MenuPage(driver);
    }

    @Step("Open cart")
    public CartPage openCart() {
        header.openCart();
        return new CartPage(driver);
    }

    /**
     * Taps a product card in the catalog by its visible title and opens its detail page.
     * The app wires the navigation click listener to the product image, not the title
     * text (see {@code ProductsAdapter}), so this locates the title and then steps to
     * the sibling image via {@code UiSelector#fromParent} rather than guessing an index.
     */
    @Step("Select product \"{productName}\" from the catalog")
    public ProductDetailPage selectProduct(String productName) {
        String selector = "new UiSelector().text(\"" + productName + "\")"
                + ".fromParent(new UiSelector().resourceId(\"com.saucelabs.mydemoapp.android:id/productIV\"))";
        WebElement productImage = driver.findElement(AppiumBy.androidUIAutomator(selector));
        click(productImage);
        return new ProductDetailPage(driver);
    }
}
