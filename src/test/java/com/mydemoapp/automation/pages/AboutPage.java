package com.mydemoapp.automation.pages;

import com.mydemoapp.automation.pages.components.AppHeader;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** The "About" screen, reached from the navigation drawer — used to prove drawer navigation works. */
public class AboutPage extends BasePage {

    public final AppHeader header;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/aboutTV")
    private WebElement screenTitle;

    public AboutPage(AndroidDriver driver) {
        super(driver);
        this.header = new AppHeader(driver);
    }

    public String getScreenTitle() {
        return textOf(screenTitle);
    }

    public MenuPage openMenu() {
        header.openMenu();
        return new MenuPage(driver);
    }
}
