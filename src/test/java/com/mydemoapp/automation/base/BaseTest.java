package com.mydemoapp.automation.base;

import com.mydemoapp.automation.config.ConfigReader;
import com.mydemoapp.automation.driver.AppiumServerManager;
import com.mydemoapp.automation.driver.DriverFactory;
import com.mydemoapp.automation.pages.CatalogPage;
import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

/**
 * Every test class extends this. It owns the Appium session lifecycle so
 * individual test classes only deal with page objects and assertions:
 * <ul>
 *   <li>starts a local Appium server once per suite (unless disabled);</li>
 *   <li>launches a fresh app session before each test method, with
 *       {@code full.reset=true} so no test inherits another's login/cart
 *       state;</li>
 *   <li>quits the driver after each test method, regardless of outcome.</li>
 * </ul>
 */
public abstract class BaseTest {

    protected AndroidDriver driver;

    /** The app's own landing screen — every test starts here. */
    protected CatalogPage catalogPage;

    @BeforeSuite(alwaysRun = true)
    public void startAppiumServerIfNeeded() {
        if (ConfigReader.getBoolean("appium.server.autostart", true)) {
            AppiumServerManager.start(ConfigReader.get("appium.server.url"));
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void launchApp() {
        driver = DriverFactory.createDriver();
        catalogPage = new CatalogPage(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void closeApp() {
        DriverFactory.quitDriver();
    }

    @AfterSuite(alwaysRun = true)
    public void stopAppiumServerIfNeeded() {
        if (ConfigReader.getBoolean("appium.server.autostart", true)) {
            AppiumServerManager.stop();
        }
    }
}
