package com.orangehrm.pages;

import com.microsoft.playwright.Page;
import com.orangehrm.constants.AppConstants;
import io.qameta.allure.Step;

public final class DashboardPage extends BasePage {

    public DashboardPage(Page page) {
        super(page);
    }

    @Step("Wait for Dashboard to load")
    public void waitForLoad() {
        logger.info("Waiting for Dashboard");
        waitForUrl(".*dashboard/index.*");
        waitForSelector(AppConstants.Selectors.DASHBOARD_HEADING);
    }

    public String getHeadingText() {
        return getText(AppConstants.Selectors.DASHBOARD_HEADING);
    }

    public boolean isLoaded() {
        return page.url().contains("/dashboard/index") && isVisible(AppConstants.Selectors.DASHBOARD_HEADING);
    }

    @Step("Logout")
    public void logout() {
        logger.info("Performing logout");
        click(AppConstants.Selectors.USER_DROPDOWN);
        click(AppConstants.Selectors.LOGOUT_LINK);
        waitForUrl(".*auth/login.*");
    }

    public String getLoggedInUsername() {
        return getText(".oxd-userdropdown-name");
    }
}
