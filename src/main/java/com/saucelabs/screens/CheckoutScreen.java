package com.saucelabs.screens;

import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;

public class CheckoutScreen extends BaseScreen {

    private static final By FIRST_NAME   = By.xpath("//android.widget.EditText[@content-desc=\"test-First Name\"]");
    private static final By LAST_NAME    = By.xpath("//android.widget.EditText[@content-desc=\"test-Last Name\"]");
    private static final By ZIP          = By.xpath("//android.widget.EditText[@content-desc=\"test-Zip/Postal Code\"]");
    private static final By CONTINUE_BTN = By.xpath("//*[@content-desc=\"test-CONTINUE\"]");
    private static final By FINISH_BTN   = By.xpath("//*[@content-desc=\"test-FINISH\"]");
    private static final By SUCCESS_MSG  = By.xpath("//*[contains(@text,'THANK YOU')]");
    private static final By ERROR_MSG    = By.xpath("//*[contains(@text,'must not be empty')]");

    public CheckoutScreen(AndroidDriver driver) {
        super(driver);
    }

    @Step("Fill checkout info: firstName={firstName}, lastName={lastName}, zip={zip}")
    public CheckoutScreen fillInfo(String firstName, String lastName, String zip) {
        typeText(FIRST_NAME, firstName);
        typeText(LAST_NAME, lastName);
        typeText(ZIP, zip);
        return this;
    }

    @Step("Tap CONTINUE button")
    public CheckoutScreen tapContinue() {
        tap(CONTINUE_BTN);
        return this;
    }

    @Step("Tap FINISH button")
    public void tapFinish() {
        tap(FINISH_BTN);
    }

    @Step("Assert checkout is complete")
    public void assertComplete() {
        attachScreenshot("Checkout Complete");
        Assertions.assertThat(isVisible(SUCCESS_MSG))
                .as("Thank you / success message should be visible after checkout")
                .isTrue();
    }

    @Step("Assert checkout validation error is shown")
    public void assertValidationError() {
        attachScreenshot("Checkout Validation Error");
        Assertions.assertThat(isVisible(ERROR_MSG))
                .as("Validation error message should be visible when required fields are empty")
                .isTrue();
    }
}
