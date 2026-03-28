package com.saucelabs.data;

/**
 * Provides static test data for use across all test classes.
 * Credentials align with the Sauce Labs React Native Demo App defaults.
 */
public final class TestDataFactory {

    private TestDataFactory() {}

    public static String validUsername() {
        return "bob@example.com";
    }

    public static String validPassword() {
        return "10203040";
    }

    public static String invalidUsername() {
        return "invalid@example.com";
    }

    public static String invalidPassword() {
        return "wrongpassword";
    }

    public static String checkoutFirstName() {
        return "John";
    }

    public static String checkoutLastName() {
        return "Doe";
    }

    public static String checkoutZip() {
        return "10001";
    }
}
