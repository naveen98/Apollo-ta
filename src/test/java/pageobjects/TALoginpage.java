package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Webdriverwaitutils;

import java.util.ArrayList;
import java.util.List;

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

    @FindBy(xpath="//span[text()='Please close pending candidate interview scheduled records status to proceed further.']")private WebElement recruiterpendingappnotificationdisplay;

    @FindBy(xpath="//h3[text()=' Recruitment Dashboard']")private WebElement recruitdashboardtextdisplay;

    public void logindata(String username, String password) {

        try {
            wait.waitForVisibility(txtusername).sendKeys(username);
            wait.waitForVisibility(txtpassword).sendKeys(password);
            wait.waitForClickability(btnlogin).click();

        } catch (Exception e) {
            System.out.println("exception " + e.getMessage());

        }

    }

    public boolean ispendingactiontextdisplayed() {
        try {
            return driver.findElement(By.xpath("//span[text()='Please close pending candidate interview scheduled records status to proceed further.']")).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }


    public void waitfordisplayrecruiterdashboardtext(){

        wait.waitForVisibility(recruitdashboardtextdisplay);

    }


    public void geturl(String url){
        driver.get(url);
    }

    public boolean TalentAcquisitionIsdisplayed() {

        try {
            WebElement Text = wait.waitForVisibility(admdisplays);
            return Text.isDisplayed();

        } catch (Exception e) {
            System.out.println("exception " + e.getMessage());

        }
        return false;

    }



    public void openUrlInNewTab(String url) {

        // Open new tab with URL
        ((JavascriptExecutor) driver)
                .executeScript("window.open(arguments[0], '_blank');", url);

        // Switch to newly opened tab
        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabs.size() - 1));
    }
}


