package com.saucelabs.tests;

import com.saucelabs.base.BaseTest;
import com.saucelabs.data.TestDataFactory;
import com.saucelabs.screens.LoginScreen;
import com.saucelabs.screens.ProductDetailScreen;
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

@Feature("Products")
class ProductTest extends BaseTest {

    @BeforeEach
    void login() {
        new LoginScreen(driver).login(TestDataFactory.validUsername(), TestDataFactory.validPassword());
    }

    @Test
    @Tag("smoke")
    @DisplayName("Product list is visible after login")
    @Story("Browse Products")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verifies the product list screen is displayed after a successful login.")
    void testProductListVisible() {
        new ProductListScreen(driver).assertProductListVisible();
    }

    @Test
    @Tag("regression")
    @DisplayName("Tapping a product opens the product detail screen")
    @Story("Browse Products")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies that tapping on the first product in the list navigates to its detail screen.")
    void testViewProductDetail() {
        new ProductListScreen(driver).tapFirstProduct();
        new ProductDetailScreen(driver).assertOnScreen();
    }

    @Test
    @Tag("regression")
    @DisplayName("Adding a product to the cart and navigating to cart")
    @Story("Add to Cart")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies that a product can be added to the cart from the product list and the cart screen opens.")
    void testAddProductToCart() {
        ProductListScreen list = new ProductListScreen(driver);
        list.addFirstProductToCart();
        list.tapCart();
    }
}
