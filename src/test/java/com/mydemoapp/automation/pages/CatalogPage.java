package com.mydemoapp.automation.pages;

import com.mydemoapp.automation.pages.components.AppHeader;
import com.mydemoapp.automation.utils.WaitUtils;
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

    // The app wires the click listener to the product image, not its title text,
    // so this finds the title and steps to the sibling image via fromParent.
    @Step("Select product \"{productName}\" from the catalog")
    public ProductDetailPage selectProduct(String productName) {
        String selector = "new UiSelector().text(\"" + productName + "\")"
                + ".fromParent(new UiSelector().resourceId(\"com.saucelabs.mydemoapp.android:id/productIV\"))";
        WebElement productImage = WaitUtils.waitForPresence(driver, AppiumBy.androidUIAutomator(selector));
        click(productImage);
        return new ProductDetailPage(driver);
    }
}
