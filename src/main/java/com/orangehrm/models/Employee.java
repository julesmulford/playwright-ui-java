package com.orangehrm.models;

public record Employee(
    String firstName,
    String lastName,
    String middleName,
    String employeeId
) {
    public Employee(String firstName, String lastName) {
        this(firstName, lastName, null, null);
    }

    public String fullName() {
        if (middleName == null || middleName.isBlank()) {
            return firstName + " " + lastName;
        }
        return firstName + " " + middleName + " " + lastName;
    }
}
