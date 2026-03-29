# appium-react-native

Appium 2 test automation framework for the **Sauce Labs React Native Demo App** (`com.saucelabs.mydemoapp.rn`), built with Java 21, JUnit 5, and Allure reporting.

---

## Description

This framework demonstrates a production-grade Page Object (Screen Object) architecture for Android UI testing. It covers authentication, product browsing, cart management, and end-to-end checkout flows. Each test produces rich Allure reports with step-level detail and automatic screenshots on pass and fail.

---

## Tech Stack

| Component        | Version  |
|------------------|----------|
| Java             | 21       |
| Maven            | 3.9+     |
| Appium Java Client | 9.2.2  |
| Appium Server    | 2.x      |
| UiAutomator2 driver | latest |
| JUnit Jupiter    | 5.10.2   |
| Allure JUnit5    | 2.27.0   |
| AssertJ          | 3.25.3   |
| Log4j2           | 2.23.1   |

---

## Prerequisites

1. **Java 21** — [Temurin](https://adoptium.net/) recommended. Verify with `java -version`.
2. **Maven 3.9+** — Verify with `mvn -version`.
3. **Node.js 18+** — Required to run Appium. Verify with `node -version`.
4. **Appium 2 server**
   ```bash
   npm install -g appium@latest
   appium driver install uiautomator2
   ```
5. **Android SDK** — Set `ANDROID_HOME` and ensure `platform-tools` is on your `PATH`.
6. **Running emulator or connected device** — Default device name: `emulator-5554`. Override via `DEVICE_NAME` environment variable.
7. **Sauce Labs RN Demo APK** — Download from the [releases page](https://github.com/saucelabs/my-demo-app-rn/releases/latest) and place it at `app/MyDemoAppRN.apk`:
   ```bash
   mkdir -p app
   curl -L -o app/MyDemoAppRN.apk \
     "https://github.com/saucelabs/my-demo-app-rn/releases/latest/download/MyDemoAppRN.apk"
   ```

---

## Configuration

All configuration is driven by environment variables with sensible defaults:

| Variable           | Default                  | Description                          |
|--------------------|--------------------------|--------------------------------------|
| `APPIUM_SERVER_URL` | `http://127.0.0.1:4723` | Appium server endpoint               |
| `APP_PATH`         | `app/MyDemoAppRN.apk`    | Path to the APK (skipped if missing) |
| `APP_ID`           | `com.saucelabs.mydemoapp.rn` | Application package name         |
| `DEVICE_NAME`      | `emulator-5554`          | Android device or emulator name      |
| `EXPLICIT_WAIT`    | `15`                     | WebDriverWait timeout in seconds     |

---

## Running Tests

### Start Appium server

```bash
appium
```

### Run smoke suite (fast, critical path)

```bash
mvn test -Dgroups=smoke
```

### Run regression suite (full coverage)

```bash
mvn test -Dgroups=regression
```

### Run all tests

```bash
mvn test
```

### Run a specific test class

```bash
mvn test -Dtest=LoginTest
```

### Override device/server at runtime

```bash
DEVICE_NAME=emulator-5556 APPIUM_SERVER_URL=http://127.0.0.1:4724 mvn test -Dgroups=smoke
```

---

## Allure Reporting

Generate and open the report after a test run:

```bash
mvn allure:serve
```

Generate a static report in `target/allure-report`:

```bash
mvn allure:report
```

Results are written to `target/allure-results/` by default (configured in `src/test/resources/allure.properties`).

---

## Project Architecture

```
appium-react-native/
├── .github/workflows/ci.yml         GitHub Actions CI pipeline
├── pom.xml                           Maven build descriptor
└── src/
    ├── main/
    │   ├── resources/log4j2.xml      Logging configuration
    │   └── java/com/saucelabs/
    │       ├── config/AppiumConfig   Environment-driven server/device config
    │       ├── driver/DriverManager  ThreadLocal AndroidDriver lifecycle
    │       ├── screens/              Screen Objects (Page Object pattern)
    │       │   ├── BaseScreen        waitFor*, tap, typeText, isVisible, screenshot
    │       │   ├── LoginScreen
    │       │   ├── ProductListScreen
    │       │   ├── ProductDetailScreen
    │       │   ├── CartScreen
    │       │   └── CheckoutScreen
    │       └── data/TestDataFactory  Centralised static test data
    └── test/
        ├── resources/allure.properties
        └── java/com/saucelabs/
            ├── base/BaseTest         @BeforeEach driver init, @AfterEach screenshot + quit
            └── tests/
                ├── LoginTest         @Tag("smoke"), @Tag("regression")
                ├── ProductTest
                ├── CartTest
                └── CheckoutTest
```

### Key Design Decisions

- **DriverManager with ThreadLocal** — Supports parallel test execution. Each thread holds its own `AndroidDriver` instance; no static field sharing.
- **Screen Objects** — Each screen encapsulates its own locators and `@Step`-annotated actions. Tests read as plain English.
- **BaseTest** — Handles driver creation and teardown uniformly. A final screenshot is captured and attached to Allure after every test regardless of outcome.
- **AssertJ** — Fluent, descriptive assertions with meaningful failure messages throughout screen objects.
- **Allure `@Step`** — Every public screen action is decorated, providing full step-level traceability in reports.

---

## CI/CD

The GitHub Actions workflow (`.github/workflows/ci.yml`) runs in two jobs:

1. **build-verify** — Compiles main and test sources on every push/PR to validate the build.
2. **smoke-tests** — Spins up an Android API 31 emulator, installs Appium 2, downloads the APK, and runs the `smoke` tag. Allure results are uploaded as a build artifact.

---

## Suggested Repository Name

`appium-react-native`
