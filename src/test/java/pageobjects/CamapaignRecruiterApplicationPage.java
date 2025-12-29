package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Webdriverwaitutils;

public class CamapaignRecruiterApplicationPage {

    WebDriver driver;
    Webdriverwaitutils wait;
    JavascriptExecutor js;

    public CamapaignRecruiterApplicationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new Webdriverwaitutils(driver);
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    // ---------------- Recruiter Page ----------------

    @FindBy(xpath = "//span[text()='Recruiter']")
    private WebElement recruiterLink;

    @FindBy(xpath = "//button//span[text()='Copy URL']")
    private WebElement copyUrlButton;

    @FindBy(xpath = "//button//span[text()='OK']")
    private WebElement okButton;

    @FindBy(xpath = "//button[@aria-label='Close']")
    private WebElement closePopup;

    // ---------------- Login Page ----------------

    @FindBy(xpath = "//input[@placeholder='Mobile Number']")
    private WebElement mobileNumberInput;

    @FindBy(xpath = "//input[@placeholder='Enter OTP']")
    private WebElement otpInput;

    @FindBy(xpath = "//button//span[text()='Next']")
    private WebElement nextButton;

    // ---------------- Application Page ----------------

    @FindBy(xpath = "//span[text()='Apply with Manual']")
    private WebElement applyWithManual;

    @FindBy(xpath = "//input[@placeholder='First Name']")
    private WebElement firstName;

    @FindBy(xpath = "//input[@placeholder='Last Name']")
    private WebElement lastName;

    @FindBy(xpath = "//input[@placeholder='Email']")
    private WebElement email;

    @FindBy(xpath = "//button//span[text()='Submit']")
    private WebElement submitButton;

    // ---------------- ACTION METHODS ----------------

    public void clickRecruiter() {
        wait.waitForClickability(recruiterLink).click();
    }

    public void clickCopyUrl() {
        wait.waitForClickability(copyUrlButton).click();
    }

    public void clickOk() {
        wait.waitForClickability(okButton).click();
    }

    public void closePopup() {
        wait.waitForClickability(closePopup).click();
    }

    public String getCopiedUrlFromClipboard() {
        return (String) js.executeScript("return navigator.clipboard.readText();");
    }

    public void openUrlInNewTab(String url) {
        js.executeScript("window.open()");
        for (String window : driver.getWindowHandles()) {
            driver.switchTo().window(window);
        }
        driver.get(url);
    }

    public void enterMobileNumber(String mobile) {
        wait.waitForVisibility(mobileNumberInput).sendKeys(mobile);
    }

    public void enterOtp(String otp) {
        wait.waitForVisibility(otpInput).sendKeys(otp);
    }

    public void clickNext() {
        wait.waitForClickability(nextButton).click();
    }

    public void clickApplyWithManual() {
        wait.waitForClickability(applyWithManual).click();
    }

    public void fillApplicationForm(String fName, String lName, String mail) {
        firstName.sendKeys(fName);
        lastName.sendKeys(lName);
        email.sendKeys(mail);
    }

    public void clickSubmit() {
        wait.waitForClickability(submitButton).click();
    }
}
