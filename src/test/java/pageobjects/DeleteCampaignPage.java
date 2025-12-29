package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Datepickutils;
import utils.Radiobuttons;
import utils.Webdriverwaitutils;

import java.util.List;

public class DeleteCampaignPage {

    WebDriver driver;
    Webdriverwaitutils wait;
    JavascriptExecutor js;
    Datepickutils dt;
    Radiobuttons rd;


    public DeleteCampaignPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new Webdriverwaitutils(driver);
        this.js = (JavascriptExecutor) driver;
        this.dt = new Datepickutils(driver);
        this.rd = new Radiobuttons(driver);
        PageFactory.initElements(driver, this);
    }

    // ================== LOCATORS ==================

    @FindBy(xpath = "//a[contains(@class,'sidebar-toggle')]")
    private WebElement menubar;

    @FindBy(xpath = "//li[@id='menu-li-campaign-campaign']//span[text()='Campaigns']")
    private WebElement campaignmodule;



    // ---------- Radio buttons ----------


    // ---------- Search  ----------
    @FindBy(xpath = "//div//input[@placeholder='Search Name / State / Region / City / Area']")
    private WebElement searchinputbox;

    @FindBy(xpath="//div//button[@type='button']//span[contains(text(),'Search')]")private WebElement searchbutton;

    @FindBy(xpath="//td[contains(text(),' No records found ')]")private WebElement norecordfound;


    // ---------- Toast ----------
    private By toastMsg =
            By.xpath("//div[contains(@class,'toast-message') and contains(@class,'ng-star-inserted')]");

    // ---------- Close ----------
    @FindBy(xpath = "//button[contains(@class,'close')]")
    private WebElement closeform;

    private final By DelpopupMsg = By.xpath("//div[@class='modal-body' and starts-with(normalize-space(), 'Do you want to delete')]");

    @FindBy(xpath = "//button[@class='btn btn-sm btn-primary ng-star-inserted']")private WebElement popupOkButton;
    @FindBy(xpath = "//button[@id='dialog-cancel-btn']") private WebElement popupCancelButton;



    //-------------------------Actions Buttons----------------------------------------

    @FindBy(xpath = "//div//i[@id='dropdownBasic1']")private WebElement clickactionsbuttons;
    @FindBy(xpath = "//div[@class='dropdown-menu user-menu actions-menu show']//button//i[@class='icon-delete']")private WebElement Deleteoption;
    @FindBy(xpath = "//div[@class='dropdown-menu user-menu actions-menu show']//button//i[@class='icon-cancel-c']")private WebElement Canceloption;







    public boolean isNoRecordFoundDisplayed() {
        try {
            return driver.findElements(By.xpath("//td[contains(text(),'No records found')]")).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }



    // ================== ACTION METHODS ==================

    public void navigateCampaignsModule() {
        try {
            wait.waitForClickability(menubar).click();
            wait.waitForClickability(campaignmodule).click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", menubar);
            js.executeScript("arguments[0].click();", campaignmodule);
        }
    }



    public void searchcampaigns(String username){
        wait.waitForVisibility(searchinputbox).clear();
        wait.waitForVisibility(searchinputbox).sendKeys(username);
        clickElement(searchbutton);
    }


    public void  clickDeleteOption() {
        clickElement(clickactionsbuttons);
        clickElement(Deleteoption);

    }

    private void clickElement(WebElement element) {
        try {
            wait.waitForClickability(element);
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);

            try {
                element.click();
            } catch (ElementClickInterceptedException e) {
                js.executeScript("arguments[0].click();", element);
            }

        } catch (Exception e) {
            js.executeScript("arguments[0].click();", element);
        }
    }


    public String getvalidationmessage() {

        String message = "";
        try {

            List<WebElement> messageElements = wait.waitForAllElementsVisible(toastMsg);
            if (messageElements != null && !messageElements.isEmpty()) {
                for(WebElement msgelement:messageElements) {

                    if(msgelement.isDisplayed())
                        message =msgelement.getText();

                    System.out.println("Toast message: " + message);

                    switch (message) {

                        case "Campaign Deleted Successfully":
                            return message;

                        default:
                            return message;

                    }
                }


            }else {
                System.out.println("Messge element not visible");
            }
        } catch (Exception e) {

            System.out.println("----------Error ------" + e.getMessage());
        }

        return "No Messege Displayed";
    }



    public boolean handlePopupOK() {
        try {
            WebElement popupMsg = wait.waitForVisibilityBy(DelpopupMsg);
            if (popupMsg != null && popupMsg.isDisplayed()) {
                WebElement okBtn = wait.waitForClickability(popupOkButton);

                try {
                    okBtn.click();
                    System.out.println("clicked ok button");
                } catch (Exception ex) {
                    System.out.println("clicked jsok button");

                    js.executeScript("arguments[0].click();", popupOkButton);
                }
                return true;
            }
        } catch (Exception e) {
            System.out.println("Popup not displayed: " + e.getMessage());
        }
        return false;
    }


    public void clickCloseForm() {
        try {
            wait.waitForClickability(closeform).click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", closeform);
        }
    }


}
