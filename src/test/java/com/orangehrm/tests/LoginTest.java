package com.orangehrm.tests;

import com.orangehrm.base.BaseTest;
import com.orangehrm.data.TestDataFactory;
import com.orangehrm.pages.DashboardPage;
import com.orangehrm.pages.LoginPage;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Authentication")
@Feature("Login")
@Tag("smoke")
@DisplayName("Login Tests")
class LoginTest extends BaseTest {

    private LoginPage loginPage;
    private DashboardPage dashboardPage;

    @BeforeEach
    void initPages() {
        loginPage = new LoginPage(page);
        dashboardPage = new DashboardPage(page);
        loginPage.navigate();
    }

    @Test
    @Story("Valid Login")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Valid credentials should redirect to Dashboard")
    void login_withValidCredentials_shouldReachDashboard() {
        loginPage.loginAs(TestDataFactory.adminUser());
        dashboardPage.waitForLoad();

        assertThat(dashboardPage.isLoaded())
            .as("Dashboard should be loaded after valid login")
            .isTrue();
        assertThat(dashboardPage.getHeadingText())
            .as("Dashboard heading should be 'Dashboard'")
            .isEqualTo("Dashboard");
    }

    @Test
    @Story("Invalid Login")
    @Severity(SeverityLevel.CRITICAL)
    @Tag("regression")
    @DisplayName("Invalid credentials should display error message")
    void login_withInvalidCredentials_shouldShowError() {
        loginPage.loginAs(TestDataFactory.invalidUser());

        assertThat(loginPage.isErrorDisplayed())
            .as("Error message should be visible for invalid credentials")
            .isTrue();
        assertThat(loginPage.getErrorMessage())
            .as("Error text should mention invalid credentials")
            .containsIgnoringCase("Invalid credentials");
    }

    @Test
    @Story("Logout")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Logout should return user to login page")
    void logout_afterValidLogin_shouldReturnToLoginPage() {
        loginPage.loginAs(TestDataFactory.adminUser());
        dashboardPage.waitForLoad();
        dashboardPage.logout();

        assertThat(loginPage.isOnLoginPage())
            .as("Should be back on the login page after logout")
            .isTrue();
    }

    @Test
    @Story("Empty Credentials")
    @Severity(SeverityLevel.NORMAL)
    @Tag("regression")
    @DisplayName("Empty credentials should show validation error")
    void login_withEmptyCredentials_shouldShowValidationError() {
        loginPage.loginAs(TestDataFactory.emptyCredentials());

        assertThat(loginPage.isErrorDisplayed() || loginPage.isOnLoginPage())
            .as("Should show error or stay on login page for empty credentials")
            .isTrue();
    }

    @Test
    @Story("Wrong Password")
    @Severity(SeverityLevel.NORMAL)
    @Tag("regression")
    @DisplayName("Correct username with wrong password should show error")
    void login_withCorrectUsernameWrongPassword_shouldShowError() {
        loginPage.login(TestDataFactory.adminUser().username(), "totally_wrong_password");

        assertThat(loginPage.isErrorDisplayed())
            .as("Error should appear for wrong password")
            .isTrue();
    }
}
