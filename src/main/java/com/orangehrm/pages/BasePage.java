package com.orangehrm.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.orangehrm.config.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class BasePage {

    protected final Page page;
    protected final Logger logger;

    protected BasePage(Page page) {
        this.page = page;
        this.logger = LogManager.getLogger(getClass());
    }

    protected void navigate(String path) {
        String url = Configuration.getBaseUrl() + path;
        logger.info("Navigating to: {}", url);
        page.navigate(url, new Page.NavigateOptions().setWaitUntil(com.microsoft.playwright.options.WaitUntilState.NETWORKIDLE));
    }

    protected void click(String selector) {
        logger.debug("Clicking: {}", selector);
        page.locator(selector).first().click();
    }

    protected void fill(String selector, String value) {
        logger.debug("Filling '{}' with value", selector);
        page.locator(selector).first().fill(value);
    }

    protected String getText(String selector) {
        return page.locator(selector).first().innerText();
    }

    protected boolean isVisible(String selector) {
        return page.locator(selector).first().isVisible();
    }

    protected void waitForSelector(String selector) {
        waitForSelector(selector, Configuration.getDefaultTimeoutMs());
    }

    protected void waitForSelector(String selector, int timeoutMs) {
        page.locator(selector).first().waitFor(
            new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(timeoutMs));
    }

    protected void waitForUrl(String pattern) {
        page.waitForURL(java.util.regex.Pattern.compile(pattern));
    }

    protected void waitForNetworkIdle() {
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }
}
