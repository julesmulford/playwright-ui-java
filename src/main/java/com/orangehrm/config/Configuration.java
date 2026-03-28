package com.orangehrm.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Configuration {

    private static final Properties PROPERTIES = new Properties();

    static {
        String env = System.getenv("TEST_ENVIRONMENT");
        if (env == null) env = "local";
        loadProperties("test.properties");
        loadProperties("test-" + env + ".properties");
    }

    private static void loadProperties(String filename) {
        try (InputStream in = Configuration.class.getClassLoader().getResourceAsStream(filename)) {
            if (in != null) {
                PROPERTIES.load(in);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file: " + filename, e);
        }
    }

    public static String getBaseUrl() {
        return get("app.base.url", "https://opensource-demo.orangehrmlive.com");
    }

    public static String getAdminUsername() {
        return get("app.admin.username", "Admin");
    }

    public static String getAdminPassword() {
        return get("app.admin.password", "admin123");
    }

    public static String getBrowserType() {
        return get("browser.type", "chromium");
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(get("browser.headless", "true"));
    }

    public static int getDefaultTimeoutMs() {
        return Integer.parseInt(get("browser.timeout.ms", "30000"));
    }

    public static boolean isCaptureScreenshotOnFailure() {
        return Boolean.parseBoolean(get("test.screenshot.on.failure", "true"));
    }

    public static boolean isCapturTraceOnFailure() {
        return Boolean.parseBoolean(get("test.trace.on.failure", "true"));
    }

    private static String get(String key, String defaultValue) {
        String envKey = key.toUpperCase().replace(".", "_");
        String envValue = System.getenv(envKey);
        if (envValue != null && !envValue.isBlank()) {
            return envValue;
        }
        return PROPERTIES.getProperty(key, defaultValue);
    }
}
