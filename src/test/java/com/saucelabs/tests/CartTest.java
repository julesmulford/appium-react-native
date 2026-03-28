package com.saucelabs.tests;

import com.saucelabs.base.BaseTest;
import com.saucelabs.data.TestDataFactory;
import com.saucelabs.screens.CartScreen;
import com.saucelabs.screens.LoginScreen;
import com.saucelabs.screens.ProductListScreen;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Feature("Cart")
class CartTest extends BaseTest {

    @BeforeEach
    void loginAndAddItem() {
        new LoginScreen(driver).login(TestDataFactory.validUsername(), TestDataFactory.validPassword());
        ProductListScreen list = new ProductListScreen(driver);
        list.addFirstProductToCart();
        list.tapCart();
    }

    @Test
    @Tag("smoke")
    @DisplayName("Cart contains at least one item after adding a product")
    @Story("Cart Contents")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verifies the cart screen is displayed and contains at least one item after adding a product from the list.")
    void testCartHasItem() {
        CartScreen cart = new CartScreen(driver);
        cart.assertOnScreen();
        Assertions.assertThat(cart.getItemCount())
                .as("Cart should have at least one item after adding a product")
                .isGreaterThan(0);
    }

    @Test
    @Tag("regression")
    @DisplayName("Removing an item from the cart empties it")
    @Story("Remove from Cart")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies that removing the only item from the cart results in an empty cart.")
    void testRemoveItemFromCart() {
        CartScreen cart = new CartScreen(driver);
        cart.removeFirstItem();
        Assertions.assertThat(cart.getItemCount())
                .as("Cart should be empty after removing the only item")
                .isEqualTo(0);
    }
}
