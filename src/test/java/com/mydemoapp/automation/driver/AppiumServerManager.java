package com.mydemoapp.automation.driver;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/** Starts and stops a local Appium server for the test run; can be disabled via {@code appium.server.autostart}. */
public final class AppiumServerManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppiumServerManager.class);

    private static AppiumDriverLocalService service;

    private AppiumServerManager() {
    }

    public static synchronized void start(String serverUrl) {
        if (service != null && service.isRunning()) {
            return;
        }

        URL url = toUrl(serverUrl);
        File logFile = new File("target/appium-server.log");
        AppiumServiceBuilder builder = new AppiumServiceBuilder()
                .withIPAddress(url.getHost())
                .usingPort(url.getPort())
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withLogFile(logFile);

        service = builder.build();
        service.start();
        LOGGER.info("Local Appium server started at {} (log: {})", serverUrl, logFile.getAbsolutePath());
    }

    public static synchronized void stop() {
        if (service != null && service.isRunning()) {
            service.stop();
            LOGGER.info("Local Appium server stopped");
        }
    }

    private static URL toUrl(String serverUrl) {
        try {
            return new URL(serverUrl);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid appium.server.url: " + serverUrl, e);
        }
    }
}
