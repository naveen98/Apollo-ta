package utils;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class UrlAssertionUtils {

    public static void validateUrl(WebDriver driver, String expectedText) {

        String actualUrl = driver.getCurrentUrl();

        Assert.assertTrue(actualUrl.contains(expectedText), "URL validation failed. Expected to contain: " + expectedText + " but was: " + actualUrl);

    }
}
