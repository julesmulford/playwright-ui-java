package com.orangehrm.tests;

import com.orangehrm.base.BaseTest;
import com.orangehrm.data.TestDataFactory;
import com.orangehrm.pages.DashboardPage;
import com.orangehrm.pages.LoginPage;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Dashboard")
@Feature("Dashboard Validation")
@Tag("regression")
@DisplayName("Dashboard Tests")
class DashboardTest extends BaseTest {

    private DashboardPage dashboardPage;

    @BeforeEach
    void loginAndNavigateToDashboard() {
        LoginPage loginPage = new LoginPage(page);
        dashboardPage = new DashboardPage(page);
        loginPage.navigate();
        loginPage.loginAs(TestDataFactory.adminUser());
        dashboardPage.waitForLoad();
    }

    @Test
    @Story("Dashboard Heading")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Dashboard should show correct heading")
    void dashboard_shouldDisplayCorrectHeading() {
        assertThat(dashboardPage.getHeadingText())
            .as("Dashboard heading should be 'Dashboard'")
            .isEqualTo("Dashboard");
    }

    @Test
    @Story("Dashboard URL")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Dashboard URL should contain /dashboard/index")
    void dashboard_shouldHaveCorrectUrl() {
        assertThat(page.url()).contains("/dashboard/index");
    }

    @Test
    @Story("Logged In Username")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Dashboard should show logged-in username")
    void dashboard_shouldShowLoggedInUsername() {
        assertThat(dashboardPage.getLoggedInUsername())
            .as("Logged-in username should be visible")
            .isNotBlank();
    }
}
