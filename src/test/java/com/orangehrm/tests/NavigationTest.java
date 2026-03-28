package com.orangehrm.tests;

import com.orangehrm.base.BaseTest;
import com.orangehrm.components.SideMenuComponent;
import com.orangehrm.data.TestDataFactory;
import com.orangehrm.pages.DashboardPage;
import com.orangehrm.pages.LoginPage;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Navigation")
@Feature("Side Menu Navigation")
@Tag("smoke")
@DisplayName("Navigation Tests")
class NavigationTest extends BaseTest {

    private SideMenuComponent sideMenu;

    @BeforeEach
    void loginAndGoToDashboard() {
        LoginPage loginPage = new LoginPage(page);
        DashboardPage dashboardPage = new DashboardPage(page);
        sideMenu = new SideMenuComponent(page);

        loginPage.navigate();
        loginPage.loginAs(TestDataFactory.adminUser());
        dashboardPage.waitForLoad();
    }

    @Test
    @Story("Navigate to PIM")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Side menu PIM link should navigate to employee list")
    void sideMenu_navigateToPIM_shouldLoadEmployeeList() {
        sideMenu.navigateTo("PIM");
        assertThat(page.url()).contains("/pim/viewEmployeeList");
    }

    @Test
    @Story("Navigate to Admin")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Side menu Admin link should navigate to admin page")
    void sideMenu_navigateToAdmin_shouldLoadAdminPage() {
        sideMenu.navigateTo("Admin");
        assertThat(page.url()).contains("/admin/");
    }

    @Test
    @Story("Core Menu Items Visible")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Core menu items should all be visible")
    void sideMenu_coreItems_shouldBeVisible() {
        String[] expectedItems = {"Admin", "PIM", "Leave", "Time", "Recruitment", "Dashboard"};
        for (String item : expectedItems) {
            assertThat(sideMenu.isMenuItemVisible(item))
                .as("Menu item '%s' should be visible", item)
                .isTrue();
        }
    }
}
