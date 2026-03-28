package com.saucelabs.screens;

import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;

public class ProductListScreen extends BaseScreen {

    private static final By PRODUCTS_HEADER    = By.xpath("//*[@content-desc=\"test-PRODUCTS\"]");
    private static final By FIRST_PRODUCT      = By.xpath("(//*[@content-desc=\"test-Item\"])[1]");
    private static final By FIRST_ADD_TO_CART  = By.xpath("(//*[@content-desc=\"test-ADD TO CART\"])[1]");
    private static final By CART_BTN           = By.xpath("//*[@content-desc=\"test-CART\"]");

    public ProductListScreen(AndroidDriver driver) {
        super(driver);
    }

    @Step("Assert product list is visible")
    public void assertProductListVisible() {
        attachScreenshot("Product List");
        Assertions.assertThat(isVisible(PRODUCTS_HEADER))
                .as("Products header should be visible on the product list screen")
                .isTrue();
    }

    @Step("Tap first product to open detail")
    public void tapFirstProduct() {
        tap(FIRST_PRODUCT);
    }

    @Step("Add first product to cart")
    public void addFirstProductToCart() {
        tap(FIRST_ADD_TO_CART);
    }

    @Step("Tap cart icon")
    public void tapCart() {
        tap(CART_BTN);
    }
}
