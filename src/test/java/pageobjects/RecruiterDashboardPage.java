package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Webdriverwaitutils;

public class RecruiterDashboardPage {

    WebDriver driver;
    Webdriverwaitutils wait;

    public RecruiterDashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new Webdriverwaitutils(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[contains(text(),' Arya A ')]")
    private WebElement usernameDisplay;

    public boolean isRecruiterDashboardDisplayed() {
        return wait.waitForVisibility(usernameDisplay).isDisplayed();
    }
}
