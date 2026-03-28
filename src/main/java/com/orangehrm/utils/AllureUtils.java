package com.orangehrm.utils;

import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class AllureUtils {

    private static final Logger LOGGER = LogManager.getLogger(AllureUtils.class);

    private AllureUtils() {}

    public static void attachScreenshot(Page page, String name) {
        try {
            byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(false));
            Allure.addAttachment(name, "image/png", new ByteArrayInputStream(screenshot), ".png");
        } catch (Exception e) {
            LOGGER.warn("Failed to attach screenshot: {}", e.getMessage());
        }
    }

    public static void attachText(String name, String content) {
        Allure.addAttachment(name, "text/plain", new ByteArrayInputStream(content.getBytes()), ".txt");
    }

    public static void attachFile(String filePath, String name, String mimeType) {
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(filePath));
            Allure.addAttachment(name, mimeType, new ByteArrayInputStream(bytes), "");
        } catch (IOException e) {
            LOGGER.warn("Failed to attach file '{}': {}", filePath, e.getMessage());
        }
    }

    public static void attachHtml(Page page, String name) {
        try {
            String html = page.content();
            Allure.addAttachment(name, "text/html", new ByteArrayInputStream(html.getBytes()), ".html");
        } catch (Exception e) {
            LOGGER.warn("Failed to attach HTML: {}", e.getMessage());
        }
    }
}
