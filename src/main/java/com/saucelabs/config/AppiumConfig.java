package com.saucelabs.config;

import java.net.MalformedURLException;
import java.net.URL;

public final class AppiumConfig {

    private AppiumConfig() {}

    public static URL getAppiumServerUrl() {
        try {
            return new URL(System.getenv().getOrDefault("APPIUM_SERVER_URL", "http://127.0.0.1:4723"));
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Invalid Appium URL", e);
        }
    }

    public static String getAppPath() {
        return System.getenv().getOrDefault("APP_PATH", "app/MyDemoAppRN.apk");
    }

    public static String getAppId() {
        return System.getenv().getOrDefault("APP_ID", "com.saucelabs.mydemoapp.rn");
    }

    public static String getDeviceName() {
        return System.getenv().getOrDefault("DEVICE_NAME", "emulator-5554");
    }

    public static int getExplicitWaitSeconds() {
        return Integer.parseInt(System.getenv().getOrDefault("EXPLICIT_WAIT", "15"));
    }
}
