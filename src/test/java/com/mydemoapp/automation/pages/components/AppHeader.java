package com.mydemoapp.automation.pages.components;

import com.mydemoapp.automation.pages.BasePage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;

/** Header bar (menu, cart) belongs to MainActivity's layout, so it's modeled once and composed into pages. */
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
