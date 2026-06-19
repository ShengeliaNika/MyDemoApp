package com.mydemoapp.automation.pages.components;

import com.mydemoapp.automation.pages.BasePage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;

/**
 * The top header bar (hamburger menu, cart icon/badge, sort icon) is part of
 * {@code MainActivity}'s layout and is present above every fragment, so it
 * is modeled once as a component and composed into the pages that need it
 * instead of being redeclared on each one.
 */
public class AppHeader extends BasePage {

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/menuIV")
    private WebElement menuButton;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/cartRL")
    private WebElement cartButton;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/cartTV")
    private WebElement cartBadgeCount;

    public AppHeader(AndroidDriver driver) {
        super(driver);
    }

    @Step("Open navigation drawer")
    public void openMenu() {
        click(menuButton);
    }

    @Step("Open cart")
    public void openCart() {
        click(cartButton);
    }

    public int getCartItemCount() {
        return Integer.parseInt(textOf(cartBadgeCount));
    }
}
