package com.mydemoapp.automation.listeners;

import com.mydemoapp.automation.driver.DriverFactory;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;

/**
 * Attaches a device screenshot to the Allure report whenever a test fails,
 * so a failure can be diagnosed from the report alone without re-running it.
 * Registered in {@code testng.xml} alongside Allure's own {@code AllureTestNg}
 * listener (which is what actually produces the result files this enriches).
 */
public class AllureScreenshotListener implements ITestListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(AllureScreenshotListener.class);

    @Override
    public void onTestFailure(ITestResult result) {
        LOGGER.error("Test failed: {}", result.getName(), result.getThrowable());
        try {
            byte[] screenshot = DriverFactory.getDriver().getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(result.getName() + " - failure screenshot", "image/png",
                    new ByteArrayInputStream(screenshot), "png");
        } catch (WebDriverException | IllegalStateException e) {
            LOGGER.warn("Could not capture failure screenshot for {}", result.getName(), e);
        }
    }
}
