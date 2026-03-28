package com.orangehrm.data;

import com.orangehrm.models.Employee;

import java.util.UUID;

public final class EmployeeBuilder {

    private String firstName = "Test";
    private String lastName = "Employee" + UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase();
    private String middleName;
    private String employeeId;

    private EmployeeBuilder() {}

    public static EmployeeBuilder create() {
        return new EmployeeBuilder();
    }

    public EmployeeBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public EmployeeBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public EmployeeBuilder withMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public EmployeeBuilder withUniqueSuffix() {
        this.lastName = "Auto" + UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase();
        return this;
    }

    public Employee build() {
        return new Employee(firstName, lastName, middleName, employeeId);
    }
}
