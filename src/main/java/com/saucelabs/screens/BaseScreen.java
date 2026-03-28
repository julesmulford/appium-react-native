package com.saucelabs.screens;

import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.ByteArrayInputStream;
import java.time.Duration;

public abstract class BaseScreen {

    protected final AndroidDriver driver;
    protected final WebDriverWait wait;
    private static final Logger log = LogManager.getLogger(BaseScreen.class);

    protected BaseScreen(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    protected WebElement waitForElement(By locator) {
        log.debug("Waiting for element: {}", locator);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForClickable(By locator) {
        log.debug("Waiting for clickable: {}", locator);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void tap(By locator) {
        log.debug("Tapping element: {}", locator);
        waitForClickable(locator).click();
    }

    protected void typeText(By locator, String text) {
        log.debug("Typing '{}' into: {}", text, locator);
        WebElement el = waitForClickable(locator);
        el.clear();
        el.sendKeys(text);
    }

    protected boolean isVisible(By locator) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            log.debug("Element not visible: {}", locator);
            return false;
        }
    }

    protected void attachScreenshot(String name) {
        try {
            byte[] screenshot = driver.getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(name, "image/png", new ByteArrayInputStream(screenshot), "png");
            log.debug("Screenshot attached: {}", name);
        } catch (Exception e) {
            log.warn("Failed to capture screenshot '{}': {}", name, e.getMessage());
        }
    }
}
