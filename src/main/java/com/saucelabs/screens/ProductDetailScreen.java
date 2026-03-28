package com.saucelabs.screens;

import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;

public class ProductDetailScreen extends BaseScreen {

    private static final By ADD_TO_CART = By.xpath("//*[@content-desc=\"test-ADD TO CART\"]");

    public ProductDetailScreen(AndroidDriver driver) {
        super(driver);
    }

    @Step("Assert product detail screen is displayed")
    public void assertOnScreen() {
        attachScreenshot("Product Detail");
        Assertions.assertThat(isVisible(ADD_TO_CART))
                .as("Add to Cart button should be visible on product detail screen")
                .isTrue();
    }

    @Step("Add product to cart from detail screen")
    public void addToCart() {
        tap(ADD_TO_CART);
    }
}
