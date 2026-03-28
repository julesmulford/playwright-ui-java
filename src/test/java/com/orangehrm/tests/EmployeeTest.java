package com.orangehrm.tests;

import com.orangehrm.base.BaseTest;
import com.orangehrm.data.TestDataFactory;
import com.orangehrm.models.Employee;
import com.orangehrm.pages.*;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Employee Management")
@Feature("Employee CRUD")
@Tag("regression")
@DisplayName("Employee Tests")
class EmployeeTest extends BaseTest {

    private EmployeeListPage employeeListPage;
    private AddEmployeePage addEmployeePage;

    @BeforeEach
    void loginAndNavigate() {
        LoginPage loginPage = new LoginPage(page);
        DashboardPage dashboardPage = new DashboardPage(page);
        employeeListPage = new EmployeeListPage(page);
        addEmployeePage = new AddEmployeePage(page);

        loginPage.navigate();
        loginPage.loginAs(TestDataFactory.adminUser());
        dashboardPage.waitForLoad();
    }

    @Test
    @Story("Create Employee")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("New employee should be created and appear in list")
    void employee_create_shouldSucceedAndAppearInList() {
        Employee employee = TestDataFactory.newEmployee();

        employeeListPage.navigate();
        employeeListPage.clickAddEmployee();
        addEmployeePage.fillAndSave(employee);

        employeeListPage.navigate();
        employeeListPage.searchByName(employee.lastName());

        assertThat(employeeListPage.isEmployeeInList(employee.lastName()))
            .as("Newly created employee should appear in search results")
            .isTrue();
    }

    @Test
    @Story("Delete Employee")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Deleted employee should no longer appear in list")
    void employee_delete_shouldRemoveFromList() {
        Employee employee = TestDataFactory.newEmployee();

        // Create
        employeeListPage.navigate();
        employeeListPage.clickAddEmployee();
        addEmployeePage.fillAndSave(employee);

        // Delete
        employeeListPage.navigate();
        employeeListPage.searchByName(employee.lastName());
        employeeListPage.deleteEmployee(employee.lastName());

        // Verify
        employeeListPage.searchByName(employee.lastName());
        assertThat(employeeListPage.isEmployeeInList(employee.lastName()))
            .as("Deleted employee should not appear in search results")
            .isFalse();
    }

    @Test
    @Story("Employee List Loads")
    @Severity(SeverityLevel.NORMAL)
    @Tag("smoke")
    @DisplayName("Employee list should contain at least one record")
    void employeeList_shouldLoadWithRecords() {
        employeeListPage.navigate();
        assertThat(employeeListPage.getRowCount())
            .as("Employee list should contain at least one record")
            .isGreaterThan(0);
    }

    @Test
    @Story("Search Employees")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Search should filter employee results")
    void employee_search_shouldFilterResults() {
        employeeListPage.navigate();
        int initialCount = employeeListPage.getRowCount();

        employeeListPage.searchByName("Admin");
        int filteredCount = employeeListPage.getRowCount();

        assertThat(filteredCount)
            .as("Search should return same or fewer results than unfiltered list")
            .isLessThanOrEqualTo(initialCount);
    }
}
