package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Webdriverwaitutils;

public class TALoginpage {
    WebDriver driver;
    Webdriverwaitutils wait;

    public TALoginpage(WebDriver driver) {

        this.driver = driver;
        this.wait = new Webdriverwaitutils(driver);
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//input[@id='appUserName']")
    private WebElement txtusername;
    @FindBy(xpath = "//input[@id='appPassword']")
    private WebElement txtpassword;
    @FindBy(xpath = "//button[@id='loginBtn']")
    private WebElement btnlogin;
    @FindBy(xpath = "//strong[normalize-space()='Administration']")
    private WebElement admdisplays;

    public void logindata(String username, String password) {

        try {
            wait.waitForVisibility(txtusername).sendKeys(username);
            wait.waitForVisibility(txtpassword).sendKeys(password);
            wait.waitForClickability(btnlogin).click();

        } catch (Exception e) {
            System.out.println("exception " + e.getMessage());

        }

    }

    public boolean cmsdisplayed() {

        try {
            WebElement Text = wait.waitForVisibility(admdisplays);
            return Text.isDisplayed();

        } catch (Exception e) {
            System.out.println("exception " + e.getMessage());

        }
        return false;

    }
}


