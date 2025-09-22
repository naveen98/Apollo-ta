package pageobjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Webdriverwaitutils;


public class Appselectionpage {
    WebDriver driver;
    Webdriverwaitutils wait;
    JavascriptExecutor js;

    public Appselectionpage(WebDriver driver) {
        this.driver = driver;
        this.wait = new Webdriverwaitutils(driver);
        PageFactory.initElements(driver, this);
        this.js=(JavascriptExecutor) driver;
    }

    // Locators
    @FindBy(xpath= "//strong[text()='Talent Acquisition']")
    private WebElement Taselection;

    @FindBy(xpath = "//h3[normalize-space()='Talent Acquisition']")
    private WebElement textdisplay;

    @FindBy(xpath = "//strong[normalize-space()='Administration']")private WebElement adminselection;



    // Actions
    public void clickOnAppSelection(String appselect) {
        try {
            if(appselect.equalsIgnoreCase("admin")) {
                WebElement app = wait.waitForClickability(adminselection);
                app.click();
                System.out.println("Admin selected");
            } else if (appselect.equalsIgnoreCase("talentacq")) {

                WebElement app = wait.waitForClickability(Taselection);
                app.click();
                System.out.println("Talent Acquistion selected");
            }

        }
        catch (Exception e){
            if (appselect.equalsIgnoreCase("admin")) {
                js.executeScript("arguments[0].click();", adminselection);
            } else if (appselect.equalsIgnoreCase("talentacq")) {
                js.executeScript("arguments[0].click();", Taselection);
            }
        }
    }


    public boolean isAppDisplayed() {
        try {
            WebElement text = wait.waitForVisibility(textdisplay);
            return text.isDisplayed();
        } catch (Exception e) {
            System.out.println("App not displayed: " + e.getMessage());
        }
        return false;
    }
}
