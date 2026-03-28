package com.saucelabs.screens;

import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;

public class LoginScreen extends BaseScreen {

    private static final By USERNAME  = By.xpath("//android.widget.EditText[@content-desc=\"test-Username\"]");
    private static final By PASSWORD  = By.xpath("//android.widget.EditText[@content-desc=\"test-Password\"]");
    private static final By LOGIN_BTN = By.xpath("//*[@content-desc=\"test-LOGIN\"]");
    private static final By ERROR_MSG = By.xpath("//*[@content-desc=\"test-Error Message\"]");

    public LoginScreen(AndroidDriver driver) {
        super(driver);
    }

    @Step("Enter username: {username}")
    public LoginScreen enterUsername(String username) {
        typeText(USERNAME, username);
        return this;
    }

    @Step("Enter password")
    public LoginScreen enterPassword(String password) {
        typeText(PASSWORD, password);
        return this;
    }

    @Step("Tap LOGIN button")
    public void tapLogin() {
        tap(LOGIN_BTN);
    }

    @Step("Login with username: {username}")
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        tapLogin();
    }

    @Step("Assert error message is visible")
    public void assertErrorVisible() {
        attachScreenshot("Login Error");
        Assertions.assertThat(isVisible(ERROR_MSG))
                .as("Error message should be visible after failed login")
                .isTrue();
    }
}
