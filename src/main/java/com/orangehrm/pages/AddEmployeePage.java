package com.orangehrm.pages;

import com.microsoft.playwright.Page;
import com.orangehrm.constants.AppConstants;
import com.orangehrm.models.Employee;
import io.qameta.allure.Step;

public final class AddEmployeePage extends BasePage {

    public AddEmployeePage(Page page) {
        super(page);
    }

    @Step("Wait for Add Employee page to load")
    public void waitForLoad() {
        waitForUrl(".*pim/addEmployee.*");
        waitForSelector(AppConstants.Selectors.FIRST_NAME_INPUT);
    }

    @Step("Fill employee form")
    public void fillEmployeeDetails(Employee employee) {
        logger.info("Filling employee form for: {}", employee.fullName());
        fill(AppConstants.Selectors.FIRST_NAME_INPUT, employee.firstName());
        fill(AppConstants.Selectors.LAST_NAME_INPUT, employee.lastName());
        if (employee.middleName() != null && !employee.middleName().isBlank()) {
            fill("input[name='middleName']", employee.middleName());
        }
    }

    @Step("Save employee form")
    public void save() {
        click(AppConstants.Selectors.SAVE_BUTTON);
        waitForNetworkIdle();
    }

    @Step("Fill and save employee")
    public void fillAndSave(Employee employee) {
        waitForLoad();
        fillEmployeeDetails(employee);
        save();
    }

    public boolean isSuccessToastVisible() {
        try {
            waitForSelector(AppConstants.Selectors.SUCCESS_TOAST, AppConstants.Timeouts.TOAST_MS);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
