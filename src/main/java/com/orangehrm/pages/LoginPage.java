package com.orangehrm.pages;

import com.microsoft.playwright.Page;
import com.orangehrm.constants.AppConstants;
import com.orangehrm.models.TestUser;
import io.qameta.allure.Step;

public final class LoginPage extends BasePage {

    public LoginPage(Page page) {
        super(page);
    }

    @Step("Navigate to login page")
    public void navigate() {
        navigate(AppConstants.Urls.LOGIN);
        waitForSelector(AppConstants.Selectors.USERNAME_INPUT);
    }

    @Step("Login as {user.username()}")
    public void loginAs(TestUser user) {
        login(user.username(), user.password());
    }

    @Step("Enter username '{username}' and password and submit")
    public void login(String username, String password) {
        logger.info("Logging in as: {}", username);
        fill(AppConstants.Selectors.USERNAME_INPUT, username);
        fill(AppConstants.Selectors.PASSWORD_INPUT, password);
        click(AppConstants.Selectors.SUBMIT_BUTTON);
    }

    @Step("Get login error message")
    public String getErrorMessage() {
        waitForSelector(AppConstants.Selectors.LOGIN_ERROR, AppConstants.Timeouts.SHORT_MS);
        return getText(AppConstants.Selectors.LOGIN_ERROR);
    }

    public boolean isErrorDisplayed() {
        try {
            waitForSelector(AppConstants.Selectors.LOGIN_ERROR, AppConstants.Timeouts.SHORT_MS);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isOnLoginPage() {
        return page.url().contains("/auth/login");
    }
}
