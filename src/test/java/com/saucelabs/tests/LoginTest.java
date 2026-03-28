package com.saucelabs.tests;

import com.saucelabs.base.BaseTest;
import com.saucelabs.data.TestDataFactory;
import com.saucelabs.screens.LoginScreen;
import com.saucelabs.screens.ProductListScreen;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Feature("Authentication")
class LoginTest extends BaseTest {

    @Test
    @Tag("smoke")
    @DisplayName("Login with valid credentials navigates to product list")
    @Story("Successful Login")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verifies that a user with correct credentials can log in and land on the products screen.")
    void testLoginWithValidCredentials() {
        new LoginScreen(driver).login(TestDataFactory.validUsername(), TestDataFactory.validPassword());
        new ProductListScreen(driver).assertProductListVisible();
    }

    @Test
    @Tag("regression")
    @DisplayName("Login with invalid password shows error message")
    @Story("Failed Login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies that an error message is displayed when a valid username is combined with a wrong password.")
    void testLoginWithInvalidPassword() {
        new LoginScreen(driver).login(TestDataFactory.validUsername(), TestDataFactory.invalidPassword());
        new LoginScreen(driver).assertErrorVisible();
    }

    @Test
    @Tag("regression")
    @DisplayName("Login with invalid username and password shows error message")
    @Story("Failed Login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies that an error message is displayed when both username and password are incorrect.")
    void testLoginWithInvalidCredentials() {
        new LoginScreen(driver).login(TestDataFactory.invalidUsername(), TestDataFactory.invalidPassword());
        new LoginScreen(driver).assertErrorVisible();
    }

    @Test
    @Tag("regression")
    @DisplayName("Tapping login with empty fields shows error message")
    @Story("Failed Login")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that an error message is displayed when the LOGIN button is tapped with no credentials entered.")
    void testLoginWithEmptyFields() {
        new LoginScreen(driver).tapLogin();
        new LoginScreen(driver).assertErrorVisible();
    }
}
