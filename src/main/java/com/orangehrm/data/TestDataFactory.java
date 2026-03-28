package com.orangehrm.data;

import com.orangehrm.config.Configuration;
import com.orangehrm.models.Employee;
import com.orangehrm.models.TestUser;

public final class TestDataFactory {

    private TestDataFactory() {}

    public static TestUser adminUser() {
        return new TestUser(Configuration.getAdminUsername(), Configuration.getAdminPassword());
    }

    public static TestUser invalidUser() {
        return new TestUser("invalid_user_xyz", "wrong_password_xyz");
    }

    public static TestUser emptyCredentials() {
        return new TestUser("", "");
    }

    public static Employee newEmployee() {
        return EmployeeBuilder.create().withUniqueSuffix().build();
    }

    public static Employee employeeWithName(String firstName, String lastName) {
        return EmployeeBuilder.create()
            .withFirstName(firstName)
            .withLastName(lastName)
            .build();
    }
}
