package com.orangehrm.constants;

public final class AppConstants {

    private AppConstants() {}

    public static final class Urls {
        public static final String LOGIN = "/web/index.php/auth/login";
        public static final String DASHBOARD = "/web/index.php/dashboard/index";
        public static final String EMPLOYEE_LIST = "/web/index.php/pim/viewEmployeeList";
        public static final String ADD_EMPLOYEE = "/web/index.php/pim/addEmployee";
        private Urls() {}
    }

    public static final class Selectors {
        public static final String USERNAME_INPUT = "input[name='username']";
        public static final String PASSWORD_INPUT = "input[name='password']";
        public static final String SUBMIT_BUTTON = "button[type='submit']";
        public static final String LOGIN_ERROR = ".oxd-alert-content-text";
        public static final String DASHBOARD_HEADING = "h6.oxd-text--h6";
        public static final String USER_DROPDOWN = ".oxd-userdropdown-tab";
        public static final String LOGOUT_LINK = "a:has-text('Logout')";
        public static final String EMPLOYEE_SEARCH_INPUT = "input[placeholder='Type for hints...']";
        public static final String ADD_BUTTON = "button:has-text('Add')";
        public static final String TABLE_ROWS = ".oxd-table-body .oxd-table-row";
        public static final String FIRST_NAME_INPUT = "input[name='firstName']";
        public static final String LAST_NAME_INPUT = "input[name='lastName']";
        public static final String SAVE_BUTTON = "button[type='submit']:has-text('Save')";
        public static final String SUCCESS_TOAST = ".oxd-toast--success";
        public static final String DELETE_CONFIRM = ".orangehrm-dialog-popup button:has-text('Yes, Delete')";
        public static final String SEARCH_BUTTON = "button[type='submit']:has-text('Search')";
        public static final String TABLE_DELETE_BUTTON = "button[title='Delete']";
        public static final String TABLE_EDIT_BUTTON = "button[title='Edit']";
        private Selectors() {}
    }

    public static final class Timeouts {
        public static final int DEFAULT_MS = 30000;
        public static final int SHORT_MS = 5000;
        public static final int TOAST_MS = 10000;
        public static final int NAVIGATION_MS = 30000;
        private Timeouts() {}
    }

    public static final class Tags {
        public static final String SMOKE = "smoke";
        public static final String REGRESSION = "regression";
        public static final String E2E = "e2e";
        private Tags() {}
    }
}
