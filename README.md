# playwright-ui-java

> **GitHub repo description:** Enterprise-grade Playwright UI test framework — Java 21, Maven, JUnit 5, AssertJ, Log4j2, Allure, Page Object Model targeting OrangeHRM.

Production-ready Web UI automation framework using Microsoft Playwright for Java with JUnit 5, demonstrating enterprise patterns: Page Object Model, builder-based test data, Log4j2 structured logging, parallel execution, and Allure reporting with rich failure evidence.

## Tech Stack

| Component | Technology | Version |
|-----------|-----------|---------|
| Language | Java | 21 |
| Build | Maven | 3.9+ |
| Browser Automation | Playwright Java | 1.44.0 |
| Test Framework | JUnit 5 | 5.10.2 |
| Assertions | AssertJ | 3.25.3 |
| Logging | Log4j2 | 2.23.1 |
| Reporting | Allure JUnit5 | 2.27.0 |

## Project Structure

```
playwright-ui-java/
├── .github/workflows/ci.yml
├── src/
│   ├── main/java/com/orangehrm/
│   │   ├── config/Configuration.java
│   │   ├── constants/AppConstants.java
│   │   ├── models/Employee.java
│   │   ├── models/TestUser.java
│   │   ├── data/EmployeeBuilder.java
│   │   ├── data/TestDataFactory.java
│   │   ├── pages/BasePage.java
│   │   ├── pages/LoginPage.java
│   │   ├── pages/DashboardPage.java
│   │   ├── pages/EmployeeListPage.java
│   │   ├── pages/AddEmployeePage.java
│   │   ├── components/SideMenuComponent.java
│   │   └── utils/
│   │       ├── PlaywrightManager.java
│   │       ├── AllureUtils.java
│   │       └── WaitUtils.java
│   └── test/java/com/orangehrm/
│       ├── base/BaseTest.java
│       ├── tests/LoginTest.java
│       ├── tests/DashboardTest.java
│       ├── tests/NavigationTest.java
│       └── tests/EmployeeTest.java
├── src/test/resources/
│   ├── log4j2.xml
│   └── test.properties
├── allure.properties
├── .gitignore
├── pom.xml
└── README.md
```

## Prerequisites

- Java 21 JDK
- Maven 3.9+

## Setup

```bash
git clone https://github.com/YOUR_USERNAME/playwright-ui-java.git
cd playwright-ui-java
mvn install -DskipTests
mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install --with-deps"
```

## Running Tests

```bash
# All tests
mvn test

# Smoke tests only
mvn test -Dgroups=smoke

# Regression tests
mvn test -Dgroups=regression

# Specific test class
mvn test -Dtest=LoginTest

# Parallel (configured in pom.xml)
mvn test -Dparallel=methods -DthreadCount=4
```

## Allure Reporting

```bash
mvn allure:serve   # generate and open in browser
mvn allure:report  # generate report only
```

## CI/CD

GitHub Actions workflow runs smoke and regression suites, uploads Allure results and Surefire reports as artifacts.

## Architecture Decisions

**PlaywrightManager**: Thread-local Playwright instance management ensures safe parallel execution. Each JUnit 5 test method gets its own browser context for isolation.

**BaseTest**: JUnit 5 extension via `@ExtendWith` handles lifecycle. `@BeforeEach` creates context/page, `@AfterEach` captures artifacts and closes context.

**Test Data**: Immutable `Employee` record + `EmployeeBuilder` fluent builder with UUID suffix for uniqueness.

## Scaling Notes

- Increase `threadCount` in `pom.xml` for more parallelism
- Add `test-{env}.properties` files for environment matrix
- Multi-browser: parameterize via JVM system property `-Dbrowser=firefox`
