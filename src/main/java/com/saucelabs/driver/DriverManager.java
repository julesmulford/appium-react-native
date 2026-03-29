package com.saucelabs.driver;

import com.saucelabs.config.AppiumConfig;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.time.Duration;

public final class DriverManager {

    private static final Logger log = LogManager.getLogger(DriverManager.class);
    private static final ThreadLocal<AndroidDriver> driverHolder = new ThreadLocal<>();

    private DriverManager() {}

    public static AndroidDriver createDriver() {
        log.info("Creating AndroidDriver for device: {}", AppiumConfig.getDeviceName());
        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName(AppiumConfig.getDeviceName())
                .setAppPackage(AppiumConfig.getAppId())
                .setAppActivity(AppiumConfig.getAppId() + ".MainActivity")
                .setNoReset(false)
                .setAutoGrantPermissions(true)
                .setDisableWindowAnimation(true);

        File appFile = new File(AppiumConfig.getAppPath());
        if (appFile.exists()) {
            log.info("Installing APK from: {}", appFile.getAbsolutePath());
            options.setApp(appFile.getAbsolutePath());
        } else {
            log.warn("APK not found at '{}', assuming app is already installed", AppiumConfig.getAppPath());
        }

        AndroidDriver driver = new AndroidDriver(AppiumConfig.getAppiumServerUrl(), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(AppiumConfig.getExplicitWaitSeconds()));
        driverHolder.set(driver);
        log.info("AndroidDriver created successfully");
        return driver;
    }

    public static AndroidDriver getDriver() {
        return driverHolder.get();
    }

    public static void quitDriver() {
        AndroidDriver driver = driverHolder.get();
        if (driver != null) {
            try {
                driver.quit();
                log.info("AndroidDriver quit successfully");
            } catch (Exception e) {
                log.warn("Error while quitting driver: {}", e.getMessage());
            } finally {
                driverHolder.remove();
            }
        }
    }
}
