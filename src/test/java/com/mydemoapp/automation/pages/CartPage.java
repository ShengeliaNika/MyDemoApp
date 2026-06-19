package com.mydemoapp.automation.pages;

import com.mydemoapp.automation.pages.components.AppHeader;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;

/** "My Cart" screen, reached via the header cart icon. */
public class CartPage extends BasePage {

    public final AppHeader header;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/noItemTitleTV")
    private WebElement emptyCartTitle;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/itemsTV")
    private WebElement itemsCountText;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/totalPriceTV")
    private WebElement totalPriceText;

    // Same resource-id ("cartBt") is reused by the app for both "Proceed To Checkout"
    // (non-empty cart) and "Add to cart" on other screens; here it always resolves to
    // the checkout button because the empty-cart state hides this view entirely.
    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/cartBt")
    private WebElement proceedToCheckoutButton;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/shoppingBt")
    private WebElement goShoppingButton;

    public CartPage(AndroidDriver driver) {
        super(driver);
        this.header = new AppHeader(driver);
    }

    public boolean isEmpty() {
        return isDisplayed(emptyCartTitle);
    }

    public String getItemsCountText() {
        return textOf(itemsCountText);
    }

    public String getTotalPriceText() {
        return textOf(totalPriceText);
    }

    /**
     * Taps "Proceed To Checkout". Requires the user to already be logged in:
     * the app only routes here to the shipping-address form; for a guest it
     * routes to the Login screen instead (see {@link #proceedToCheckoutAsGuest()}).
     */
    @Step("Proceed to checkout")
    public CheckoutInfoPage proceedToCheckout() {
        click(proceedToCheckoutButton);
        return new CheckoutInfoPage(driver);
    }

    /** Taps "Proceed To Checkout" as a guest, which the app redirects to the Login screen. */
    @Step("Proceed to checkout as a guest")
    public LoginPage proceedToCheckoutAsGuest() {
        click(proceedToCheckoutButton);
        return new LoginPage(driver);
    }

    @Step("Go shopping")
    public CatalogPage goShopping() {
        click(goShoppingButton);
        return new CatalogPage(driver);
    }
}
