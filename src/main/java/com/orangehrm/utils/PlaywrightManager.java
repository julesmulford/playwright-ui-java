package com.orangehrm.utils;

import com.microsoft.playwright.*;
import com.orangehrm.config.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class PlaywrightManager {

    private static final Logger LOGGER = LogManager.getLogger(PlaywrightManager.class);
    private static final ThreadLocal<Playwright> PLAYWRIGHT_TL = new ThreadLocal<>();
    private static final ThreadLocal<Browser> BROWSER_TL = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> CONTEXT_TL = new ThreadLocal<>();
    private static final ThreadLocal<Page> PAGE_TL = new ThreadLocal<>();

    private PlaywrightManager() {}

    public static void initBrowser() {
        LOGGER.info("Initializing Playwright browser ({})", Configuration.getBrowserType());
        Playwright playwright = Playwright.create();
        PLAYWRIGHT_TL.set(playwright);

        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
            .setHeadless(Configuration.isHeadless())
            .setSlowMo(0);

        Browser browser = switch (Configuration.getBrowserType().toLowerCase()) {
            case "firefox" -> playwright.firefox().launch(launchOptions);
            case "webkit" -> playwright.webkit().launch(launchOptions);
            default -> playwright.chromium().launch(launchOptions);
        };
        BROWSER_TL.set(browser);
    }

    public static void initContext() {
        Browser browser = getBrowser();
        BrowserContext context = browser.newContext(new Browser.NewContextOptions()
            .setViewportSize(1920, 1080)
            .setIgnoreHTTPSErrors(true));
        context.setDefaultTimeout(Configuration.getDefaultTimeoutMs());
        context.setDefaultNavigationTimeout(Configuration.getDefaultTimeoutMs());
        CONTEXT_TL.set(context);
        PAGE_TL.set(context.newPage());
        LOGGER.info("Browser context and page created");
    }

    public static void startTracing() {
        getContext().tracing().start(new Tracing.StartOptions()
            .setScreenshots(true)
            .setSnapshots(true)
            .setSources(true));
    }

    public static void stopTracing(String outputPath) {
        try {
            getContext().tracing().stop(new Tracing.StopOptions().setPath(java.nio.file.Paths.get(outputPath)));
        } catch (Exception e) {
            LOGGER.warn("Failed to stop tracing: {}", e.getMessage());
        }
    }

    public static Page getPage() {
        return PAGE_TL.get();
    }

    public static BrowserContext getContext() {
        return CONTEXT_TL.get();
    }

    public static Browser getBrowser() {
        return BROWSER_TL.get();
    }

    public static void closeContext() {
        try {
            if (PAGE_TL.get() != null) PAGE_TL.get().close();
            if (CONTEXT_TL.get() != null) CONTEXT_TL.get().close();
        } catch (Exception e) {
            LOGGER.warn("Error closing context: {}", e.getMessage());
        } finally {
            PAGE_TL.remove();
            CONTEXT_TL.remove();
        }
    }

    public static void closeBrowser() {
        try {
            if (BROWSER_TL.get() != null) BROWSER_TL.get().close();
            if (PLAYWRIGHT_TL.get() != null) PLAYWRIGHT_TL.get().close();
        } catch (Exception e) {
            LOGGER.warn("Error closing browser: {}", e.getMessage());
        } finally {
            BROWSER_TL.remove();
            PLAYWRIGHT_TL.remove();
        }
    }
}
