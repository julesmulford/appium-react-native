package com.saucelabs.tests;

import com.saucelabs.base.BaseTest;
import com.saucelabs.data.TestDataFactory;
import com.saucelabs.screens.CartScreen;
import com.saucelabs.screens.CheckoutScreen;
import com.saucelabs.screens.LoginScreen;
import com.saucelabs.screens.ProductListScreen;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Feature("Checkout")
class CheckoutTest extends BaseTest {

    @BeforeEach
    void loginAndNavigateToCheckout() {
        new LoginScreen(driver).login(TestDataFactory.validUsername(), TestDataFactory.validPassword());
        ProductListScreen list = new ProductListScreen(driver);
        list.addFirstProductToCart();
        list.tapCart();
        new CartScreen(driver).tapCheckout();
    }

    @Test
    @Tag("smoke")
    @DisplayName("Complete checkout with valid shipping info shows thank you screen")
    @Story("Successful Checkout")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verifies that filling in valid checkout details and tapping FINISH displays the order confirmation screen.")
    void testCompleteCheckout() {
        new CheckoutScreen(driver)
                .fillInfo(TestDataFactory.checkoutFirstName(), TestDataFactory.checkoutLastName(), TestDataFactory.checkoutZip())
                .tapContinue()
                .tapFinish();
        new CheckoutScreen(driver).assertComplete();
    }

    @Test
    @Tag("regression")
    @DisplayName("Tapping CONTINUE without filling in checkout fields shows validation error")
    @Story("Checkout Validation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies that a validation error is shown when the user taps CONTINUE without entering any shipping information.")
    void testCheckoutValidationError() {
        new CheckoutScreen(driver).tapContinue();
        new CheckoutScreen(driver).assertValidationError();
    }
}
