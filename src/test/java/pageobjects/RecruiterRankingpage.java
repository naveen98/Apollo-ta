package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Dropdownutils;
import utils.Webdriverwaitutils;

import java.time.Duration;
import java.util.List;

public class RecruiterRankingpage {

    WebDriver driver;
    Webdriverwaitutils wait;
    JavascriptExecutor js;
    public RecruiterRankingpage(WebDriver driver){
        this.driver=driver;
        this.js=(JavascriptExecutor) driver;
        this.wait=new Webdriverwaitutils(driver);
        PageFactory.initElements(driver,this);


    }
    // ---------- Locators ----------
    @FindBy(xpath = "//a[@mattooltip='Click to toggle Menu']")
    private WebElement menubar;

    @FindBy(xpath = "//span[text()='Sourcing']")
    private WebElement sourcingmodule;

    @FindBy(xpath = "//li[@id='menu-li-sourcing-recruiter-ranking']")
    private WebElement recruiterrankingmodule;

    @FindBy(xpath = "//span[text()='Add Ranking']")
    private WebElement addrankingbutton;

    @FindBy(xpath = "//input[@placeholder='Enter rank']")
    private WebElement rankinputbox;

    @FindBy(xpath = "//p-select[@placeholder='Select recruiter']")
    private WebElement recruiterselectdropdown;

    private final By recruiterselectionoption=By.xpath("//ul[@role='listbox']//li[@class='p-ripple p-select-option']");


    @FindBy(xpath = "//input[@placeholder='Enter vacancies closed']")
    private WebElement vacanciesclosedinputbox;

    @FindBy(xpath = "//input[@placeholder='Enter sourcing effort']")
    private WebElement sourcingeffortinputbox;

    @FindBy(xpath = "//input[@placeholder='Enter hiring effor']")
    private WebElement hiringeffortinputbox;

    @FindBy(xpath = "//button[@id='btnSave']")
    private WebElement savebutton;


    @FindBy(xpath = "//button[@class='btn btn-sm btn-primary ng-star-inserted']")
    private WebElement popupOkButton;

    @FindBy(xpath = "//button[@id='dialog-cancel-btn']")
    private WebElement popupCancelButton;

    @FindBy(xpath = "//button[@title='Close']")
    private WebElement cancelFormButton;

    @FindBy(xpath = "//button[@title='Reset']")
    private WebElement resetButton;


    private final By toastOrPopupMsg = By.xpath("//div[(@role='alert' and contains(@class, 'toast-message')) or (contains(@class,'modal-body') and contains(text(),'Please enter all the mandatory fields before saving.'))]");
    private final By toastMsg = By.xpath("//div[@role='alert' and contains(@class, 'toast-message')]");
    private final By popupMessageLocator = By.xpath("//div[contains(@class,'modal-body') and contains(text(),'Please enter all the mandatory fields before saving.')]");



    private void clickElement(WebElement element) {
        try {
            WebElement ele = wait.waitForClickability(element);
            if (ele != null && ele.isDisplayed())
                ele.click();
        } catch (ElementClickInterceptedException e) {
            js.executeScript("arguments[0].scrollIntoView(true);", element);
            js.executeScript("arguments[0].click();", element);
        }
    }

    public void navigatetorecruitmentsourcing() {
        clickElement(menubar);
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", sourcingmodule);
        clickElement(sourcingmodule);
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", sourcingmodule);

        clickElement(recruiterrankingmodule);
    }

    public void clickaddrankingbutton(){
        clickElement(addrankingbutton);
    }

    public void createrankingdata(String rank,String recruiter,String vacancyclosed,String sourcingeffort,String hiringeffort){


        wait.waitForEnterText(rankinputbox,rank);
        Dropdownutils.selectbyvisibletextlistretry(driver,recruiterselectdropdown,recruiterselectionoption,recruiter);
        wait.waitForEnterText(vacanciesclosedinputbox,vacancyclosed);
        wait.waitForEnterText(sourcingeffortinputbox,sourcingeffort);
        wait.waitForEnterText(hiringeffortinputbox,hiringeffort);

    }

    public  void  clicksave(){

        clickElement(savebutton);
    }

    public String getvalidationmessage() {
        try {
            List<WebElement> messageElements = wait.waitForAllElementsVisible(toastOrPopupMsg);

            if (messageElements != null && !messageElements.isEmpty()) {
                for (WebElement msgElement : messageElements) {
                    if (!msgElement.isDisplayed())
                        continue;

                    String message = msgElement.getText().trim();
                    System.out.println("Toast message: " + message);

                    switch (message) {
                        case "Recruiter Ranking Saved Successfully":
                            waitForToastToDisappear();
                            return message;

                        case "Recruiter Ranking Already exist":

                            waitForToastToDisappear();
                            cancelform();
                            return message;

                        case "Please enter all the mandatory fields before saving.":
                            js.executeScript("window.scrollTo(0, 0);");
                            handlePopupOK();
                            cancelform();
                            return "Mandatory fields missing";

                        default:
                            cancelform();
                            return message;
                    }
                }
            } else {
                System.out.println("Toast or popup not visible.");
            }
        } catch (Exception e) {
            System.out.println("Error  validation message: " + e.getMessage());
        }
        return "No Message Displayed";
    }

    public void cancelform() {
        clickElement(cancelFormButton);
    }


    public boolean handlePopupOK() {
        try {
            WebElement popupMsg = wait.waitForVisibilityBy(popupMessageLocator);
            if (popupMsg != null && popupMsg.isDisplayed()) {
                WebElement okBtn = wait.waitForClickability(popupOkButton);
                try {
                    okBtn.click();
                } catch (Exception ex) {
                    js.executeScript("arguments[0].click();", popupOkButton);
                }
                return true;
            }
        } catch (Exception e) {
            System.out.println("Popup not displayed: " + e.getMessage());
        }
        return false;
    }
    public void waitForToastToDisappear() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(10));
            shortWait.until(ExpectedConditions.invisibilityOfElementLocated(toastMsg));
        } catch (TimeoutException e) {
            System.out.println("Toast did not disappear .");
        }
    }



}
