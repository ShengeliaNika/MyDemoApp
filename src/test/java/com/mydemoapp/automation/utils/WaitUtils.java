package com.mydemoapp.automation.utils;

import com.mydemoapp.automation.config.ConfigReader;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/** Centralizes explicit-wait conditions so page objects stay declarative. */
public final class WaitUtils {

    private WaitUtils() {
    }

    private static WebDriverWait waitFor(AndroidDriver driver) {
        int seconds = ConfigReader.getInt("explicit.wait.seconds", 15);
        return new WebDriverWait(driver, Duration.ofSeconds(seconds));
    }

    public static WebElement waitForVisible(AndroidDriver driver, WebElement element) {
        return waitFor(driver).until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitForClickable(AndroidDriver driver, WebElement element) {
        return waitFor(driver).until(ExpectedConditions.elementToBeClickable(element));
    }

    public static boolean waitForInvisible(AndroidDriver driver, WebElement element) {
        return waitFor(driver).until(ExpectedConditions.invisibilityOf(element));
    }
}
