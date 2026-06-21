package com.mydemoapp.automation.pages;

import com.mydemoapp.automation.utils.WaitUtils;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

/** Common ancestor for every Page Object; initializes {@code @AndroidFindBy} fields via PageFactory. */
public abstract class BasePage {

    protected final AndroidDriver driver;

    protected BasePage(AndroidDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(15)), this);
    }

    protected void click(WebElement element) {
        WaitUtils.waitForClickable(driver, element).click();
    }

    protected void type(WebElement element, String text) {
        WebElement field = WaitUtils.waitForVisible(driver, element);
        field.clear();
        field.sendKeys(text);
    }

    protected String textOf(WebElement element) {
        return WaitUtils.waitForVisible(driver, element).getText();
    }

    /** Non-throwing presence check, used for assertions on optional/error elements. */
    protected boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
