package drivers;

import org.openqa.selenium.WebDriver;

public class DriverManager {
    private static WebDriver driver;

    // Save browser
    public static void setDriver(WebDriver d) {
        driver = d;
    }

    // Get browser
    public static WebDriver getDriver() {
        return driver;
    }

    // Close browser
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
