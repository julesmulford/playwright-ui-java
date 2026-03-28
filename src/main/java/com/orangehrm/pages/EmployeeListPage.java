package com.orangehrm.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.orangehrm.constants.AppConstants;
import io.qameta.allure.Step;

public final class EmployeeListPage extends BasePage {

    public EmployeeListPage(Page page) {
        super(page);
    }

    @Step("Navigate to Employee List")
    public void navigate() {
        navigate(AppConstants.Urls.EMPLOYEE_LIST);
        waitForSelector(".oxd-table");
    }

    @Step("Search for employee: {name}")
    public void searchByName(String name) {
        logger.info("Searching employee: {}", name);
        fill(AppConstants.Selectors.EMPLOYEE_SEARCH_INPUT, name);
        click(AppConstants.Selectors.SEARCH_BUTTON);
        waitForNetworkIdle();
    }

    @Step("Click Add Employee button")
    public void clickAddEmployee() {
        click(AppConstants.Selectors.ADD_BUTTON);
        waitForUrl(".*pim/addEmployee.*");
    }

    public int getRowCount() {
        waitForNetworkIdle();
        return page.locator(AppConstants.Selectors.TABLE_ROWS).count();
    }

    public boolean isEmployeeInList(String employeeName) {
        Locator rows = page.locator(AppConstants.Selectors.TABLE_ROWS);
        int count = rows.count();
        for (int i = 0; i < count; i++) {
            if (rows.nth(i).innerText().contains(employeeName)) return true;
        }
        return false;
    }

    @Step("Delete employee: {employeeName}")
    public void deleteEmployee(String employeeName) {
        logger.info("Deleting employee: {}", employeeName);
        Locator rows = page.locator(AppConstants.Selectors.TABLE_ROWS);
        int count = rows.count();
        for (int i = 0; i < count; i++) {
            Locator row = rows.nth(i);
            if (row.innerText().contains(employeeName)) {
                row.locator(AppConstants.Selectors.TABLE_DELETE_BUTTON).click();
                waitForSelector(AppConstants.Selectors.DELETE_CONFIRM);
                click(AppConstants.Selectors.DELETE_CONFIRM);
                waitForNetworkIdle();
                return;
            }
        }
        throw new RuntimeException("Employee '" + employeeName + "' not found for deletion.");
    }
}
