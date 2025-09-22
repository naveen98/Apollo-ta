package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Radiobuttons {

    public WebDriver driver;
    JavascriptExecutor js;

    public Radiobuttons(WebDriver driver) {
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
    }


         // Example: labelText = "Medium", optionText = "Offline" or "Online"

    public boolean selectRadioButtonOption(String labelText, String optionText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        try {
            // find the radio group  label
            String labelxpath = "//div[@class='form-group zc-field-radio']" +
                    "[.//span[contains(normalize-space(.),'" + labelText + "')]]";
            WebElement radioGroup = wait.until(
                    ExpectedConditions.presenceOfElementLocated(By.xpath(labelxpath)));

            // inside this group, get all labels (each radio option)offline /online
            List<WebElement> options = radioGroup.findElements(By.xpath(".//label"));

            for (WebElement option : options) {
                String label = option.getText().trim();
                if (label.equalsIgnoreCase(optionText.trim())) {
                    js.executeScript("arguments[0].scrollIntoView({block:'center'});", option);
                    WebElement radiobtn = option.findElement(By.tagName("input"));

                    if (!radiobtn.isSelected()) {
                        try {
                            wait.until(ExpectedConditions.elementToBeClickable(option)).click();
                        } catch (Exception e) {
                            js.executeScript("arguments[0].click();", option);
                        }
                    }
                    return radiobtn.isSelected();
                }
            }

            System.out.println("Radio option '" + optionText + "' not found under label '" + labelText + "'");
        } catch (Exception e) {
            System.out.println("Exception in selecting radio [" + labelText + " : " + optionText + "]: " + e.getMessage());
        }
        return false;
    }
}
