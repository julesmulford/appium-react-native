package com.saucelabs.base;

import com.saucelabs.driver.DriverManager;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.OutputType;

import java.io.ByteArrayInputStream;

public abstract class BaseTest {

    private static final Logger log = LogManager.getLogger(BaseTest.class);
    protected AndroidDriver driver;

    @BeforeEach
    void setUp(TestInfo info) {
        log.info("Starting test: {}", info.getDisplayName());
        Allure.parameter("Test Name", info.getDisplayName());
        driver = DriverManager.createDriver();
    }

    @AfterEach
    void tearDown(TestInfo info) {
        log.info("Tearing down test: {}", info.getDisplayName());
        if (driver != null) {
            try {
                byte[] screenshot = driver.getScreenshotAs(OutputType.BYTES);
                Allure.addAttachment(
                        "Final Screenshot - " + info.getDisplayName(),
                        "image/png",
                        new ByteArrayInputStream(screenshot),
                        "png"
                );
            } catch (Exception e) {
                log.warn("Could not capture final screenshot for '{}': {}", info.getDisplayName(), e.getMessage());
            }
        }
        DriverManager.quitDriver();
    }
}
