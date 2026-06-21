package com.mydemoapp.automation.driver;

import com.mydemoapp.automation.config.ConfigReader;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.time.Duration;

/** Builds and tears down the {@link AndroidDriver} for the running test. */
public final class DriverFactory {

    private static final ThreadLocal<AndroidDriver> DRIVER = new ThreadLocal<>();

    private DriverFactory() {
    }

    public static AndroidDriver createDriver() {
        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName(ConfigReader.get("device.name"))
                .setApp(resolveAppPath())
                .setAppPackage(ConfigReader.get("app.package"))
                .setAppActivity(ConfigReader.get("app.activity"))
                .setFullReset(ConfigReader.getBoolean("full.reset", true))
                .setAutoGrantPermissions(true)
                .setNewCommandTimeout(Duration.ofSeconds(ConfigReader.getInt("new.command.timeout.seconds", 120)));

        String platformVersion = ConfigReader.get("platform.version");
        if (!platformVersion.isBlank()) {
            options.setPlatformVersion(platformVersion);
        }

        AndroidDriver driver = new AndroidDriver(toUrl(ConfigReader.get("appium.server.url")), options);
        DRIVER.set(driver);
        return driver;
    }

    public static AndroidDriver getDriver() {
        AndroidDriver driver = DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException("Driver has not been initialized for this thread. Call createDriver() first.");
        }
        return driver;
    }

    public static void quitDriver() {
        AndroidDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
    }

    private static String resolveAppPath() {
        String configuredPath = ConfigReader.get("app.path");
        Path path = Path.of(!configuredPath.isBlank() ? configuredPath
                : System.getProperty("app.path", "apps/mda-2.2.0-25.apk"));
        if (!path.toFile().exists()) {
            throw new IllegalStateException(
                    "App under test not found at " + path.toAbsolutePath() +
                    ". Run `mvn generate-test-resources` (or a full `mvn test`) to download it, " +
                    "or place the APK there manually.");
        }
        return path.toAbsolutePath().toString();
    }

    private static URL toUrl(String serverUrl) {
        try {
            return new URL(serverUrl);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid appium.server.url: " + serverUrl, e);
        }
    }
}
