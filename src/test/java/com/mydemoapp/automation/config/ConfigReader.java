package com.mydemoapp.automation.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/** Loads {@code config.properties}; any value can be overridden with a matching {@code -Dkey=value}. */
public final class ConfigReader {

    private static final String CONFIG_FILE = "config.properties";
    private static final Properties PROPERTIES = load();

    private ConfigReader() {
    }

    private static Properties load() {
        Properties properties = new Properties();
        try (InputStream stream = ConfigReader.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (stream == null) {
                throw new IllegalStateException("Could not find " + CONFIG_FILE + " on the test classpath");
            }
            properties.load(stream);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read " + CONFIG_FILE, e);
        }
        return properties;
    }

    public static String get(String key) {
        String override = System.getProperty(key);
        if (override != null && !override.isBlank()) {
            return override;
        }
        return PROPERTIES.getProperty(key, "");
    }

    public static String get(String key, String defaultValue) {
        String value = get(key);
        return value.isBlank() ? defaultValue : value;
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        String value = get(key);
        return value.isBlank() ? defaultValue : Boolean.parseBoolean(value);
    }

    public static int getInt(String key, int defaultValue) {
        String value = get(key);
        return value.isBlank() ? defaultValue : Integer.parseInt(value);
    }
}
