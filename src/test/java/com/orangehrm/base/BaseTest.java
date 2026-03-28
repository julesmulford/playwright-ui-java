package com.orangehrm.base;

import com.microsoft.playwright.Page;
import com.orangehrm.config.Configuration;
import com.orangehrm.utils.AllureUtils;
import com.orangehrm.utils.PlaywrightManager;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import io.qameta.allure.junit5.AllureJunit5;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@ExtendWith(AllureJunit5.class)
public abstract class BaseTest {

    protected static final Logger LOGGER = LogManager.getLogger(BaseTest.class);
    protected Page page;
    private String correlationId;
    private String tracePath;

    @BeforeEach
    void setUp(TestInfo testInfo) throws Exception {
        correlationId = UUID.randomUUID().toString().substring(0, 8);
        LOGGER.info("[{}] Starting test: {}", correlationId, testInfo.getDisplayName());

        PlaywrightManager.initBrowser();
        PlaywrightManager.initContext();

        if (Configuration.isCapturTraceOnFailure()) {
            PlaywrightManager.startTracing();
        }

        page = PlaywrightManager.getPage();
        Allure.parameter("Environment", System.getenv("TEST_ENVIRONMENT") != null ? System.getenv("TEST_ENVIRONMENT") : "local");
        Allure.parameter("CorrelationId", correlationId);
        Allure.parameter("Browser", Configuration.getBrowserType());
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        try {
            if (Configuration.isCaptureScreenshotOnFailure()) {
                AllureUtils.attachScreenshot(page, "final-state-" + correlationId);
            }

            if (Configuration.isCapturTraceOnFailure()) {
                tracePath = "target/traces/" + correlationId + ".zip";
                Files.createDirectories(Paths.get("target/traces"));
                PlaywrightManager.stopTracing(tracePath);
                AllureUtils.attachFile(tracePath, "playwright-trace.zip", "application/zip");
            }
        } catch (Exception e) {
            LOGGER.warn("Error in teardown: {}", e.getMessage());
        } finally {
            PlaywrightManager.closeContext();
            PlaywrightManager.closeBrowser();
            LOGGER.info("[{}] Finished test: {}", correlationId, testInfo.getDisplayName());
        }
    }
}
