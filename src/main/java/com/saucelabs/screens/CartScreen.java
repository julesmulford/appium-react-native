package com.saucelabs.screens;

import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Step;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;

public class CartScreen extends BaseScreen {

    private static final By CHECKOUT_BTN = By.xpath("//*[@content-desc=\"test-CHECKOUT\"]");
    private static final By REMOVE_BTN   = By.xpath("(//*[@content-desc=\"test-REMOVE\"])[1]");
    private static final By CART_ITEMS   = By.xpath("//*[@content-desc=\"test-Item\"]");

    public CartScreen(AndroidDriver driver) {
        super(driver);
    }

    @Step("Assert cart screen is displayed")
    public void assertOnScreen() {
        attachScreenshot("Cart");
        Assertions.assertThat(isVisible(CHECKOUT_BTN))
                .as("Checkout button should be visible on the cart screen")
                .isTrue();
    }

    @Step("Remove first item from cart")
    public void removeFirstItem() {
        tap(REMOVE_BTN);
    }

    @Step("Tap CHECKOUT button")
    public void tapCheckout() {
        tap(CHECKOUT_BTN);
    }

    @Step("Get cart item count")
    public int getItemCount() {
        return driver.findElements(CART_ITEMS).size();
    }
}
