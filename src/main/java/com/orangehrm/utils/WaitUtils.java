package com.orangehrm.utils;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.BooleanSupplier;

public final class WaitUtils {

    private static final Logger LOGGER = LogManager.getLogger(WaitUtils.class);

    private WaitUtils() {}

    public static void waitForCondition(BooleanSupplier condition, int timeoutMs, String description) {
        long deadline = System.currentTimeMillis() + timeoutMs;
        int pollMs = 500;
        while (System.currentTimeMillis() < deadline) {
            if (condition.getAsBoolean()) return;
            try { Thread.sleep(pollMs); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
        throw new RuntimeException("Condition '" + description + "' not met within " + timeoutMs + "ms");
    }

    public static void waitForNetworkIdle(Page page) {
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }

    public static void waitForSelectorHidden(Page page, String selector, int timeoutMs) {
        page.locator(selector).first().waitFor(
            new com.microsoft.playwright.Locator.WaitForOptions()
                .setState(WaitForSelectorState.HIDDEN)
                .setTimeout(timeoutMs));
    }
}
