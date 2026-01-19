package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Datepickutils;
import utils.Radiobuttons;
import utils.Webdriverwaitutils;

import java.util.List;

public class CampaignCreationEditPage {

    WebDriver driver;
    Webdriverwaitutils wait;
    JavascriptExecutor js;
    Datepickutils dt;
    Radiobuttons rd;


    public CampaignCreationEditPage(WebDriver driver) {
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


    @FindBy(xpath = "(//div//button[@class='btn btn-primary ml-1 ng-star-inserted'][contains(text(),' Next ')])[1]")
    private WebElement nextbtn;

    private By nextbtnby =
            By.xpath("(//div//button[@class='btn btn-primary ml-1 ng-star-inserted'][contains(text(),' Next ')])[1]");

    // ---------- Radio buttons ----------

    @FindBy(xpath = "//div[@class='form-group zc-field-radio'][.//span[contains(text(),'Share Venue Contact Info')]]")
    private WebElement shareVenueContactInfoLabel;

    @FindBy(xpath = "//div[@class='form-group zc-field-radio'][.//span[contains(text(),'Medium')]]")
    private WebElement mediumLabel;

    // ---------- Target ----------
    @FindBy(xpath = "//div//input[@placeholder='Search Name / Medium / Share Venue Contact Info']")
    private WebElement searchinputbox;

    @FindBy(xpath = "(//button[@class='btn btn-primary ml-1 ng-star-inserted'])[2]")
    private WebElement targetnextbtn;

    // ---------- Notes ----------
    @FindBy(xpath = "//textarea[@placeholder='Enter notes']")
    private WebElement notes;

    @FindBy(xpath = "//button[contains(text(),' Submit ')]")
    private WebElement updatebutton;


    // ---------- Toast ----------
    private By toastMsg =
            By.xpath("//div[contains(@class,'toast-message') and contains(@class,'ng-star-inserted')]");

    private By toastorpopup=By.xpath("//div[(@role='alert' and contains(@class,'toast-message'))     or (contains(@class,'modal-body')     and contains(text(),'Please enter all the mandatory fields before saving'))]");

    @FindBy(xpath = "//button[@id='dialog-okay-btn']")
    private WebElement popupOkButton;

    @FindBy(xpath = "//button[@id='dialog-cancel-btn']")
    private WebElement popupCancelButton;

    private final By popupMessageLocator = By.xpath("//div[contains(@class,'modal-body') and contains(text(),'Please enter all the mandatory fields before saving.')]");



    // ---------- Close ----------
    @FindBy(xpath = "//button[contains(@class,'close')]")
    private WebElement closeform;



    @FindBy(xpath="//td[contains(text(),' No records found ')]")private WebElement norecordfound;

    @FindBy(xpath="//div//button[@type='button']//span[contains(text(),' Search')]")private WebElement searchbutton;
    @FindBy(xpath = "//div[@class='dropdown-menu user-menu actions-menu show']//button//i[@class='icon-edit']")private WebElement Editoption;
    @FindBy(xpath = "//div[@class='dropdown-menu user-menu actions-menu show']//button//i[@class='icon-delete']")private WebElement Deleteoption;
    @FindBy(xpath = "//div[@class='dropdown-menu user-menu actions-menu show']//button//i[@class='icon-cancel-c']")private WebElement Canceloption;



    @FindBy(xpath = "//div//i[@id='dropdownBasic1']")private WebElement clickactionsbuttons;



    public boolean isNoRecordFoundDisplayed() {
        try {
            return driver.findElements(By.xpath("//td[contains(text(),'No records found')]")).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }



    // ================== ACTION METHODS ==================

    public void navigatemenu() {
        try {
            wait.waitForClickability(menubar).click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", menubar);
        }
    }

    public void searchcampaigns(String username){
        wait.waitForVisibility(searchinputbox).clear();
        wait.waitForVisibility(searchinputbox).sendKeys(username);
        clickElement(searchbutton);
    }


    public void  clickEditOption() {
        clickElement(clickactionsbuttons);
        clickElement(Editoption);
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


    public void clickupdatebutton()
    {
        clickElement(updatebutton);
    }


    public void navigateCampaignModule() {
        try {
            wait.waitForClickability(campaignmodule).click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", campaignmodule);
        }
    }

    public void clickNextButton() {
        try {
            wait.waitForClickability(nextbtn).click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", nextbtn);
        }
    }

    public void addTargetNextBtn() {
        try {
            wait.waitForClickability(targetnextbtn).click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", targetnextbtn);
        }
    }



    public boolean selectRadioButtonOption(String labelText, String optionText) {
        try {
            String xpath = "//div[@class='form-group zc-field-radio'][.//span[contains(text(),'" + labelText + "')]]";

            WebElement radioGroup = wait.waitForVisibility(driver.findElement(By.xpath(xpath)));

            List<WebElement> options = radioGroup.findElements(By.xpath(".//label[contains(@class,'custom-radio')]"));

            for (WebElement option : options) {
                if (option.getText().trim().equalsIgnoreCase(optionText)) {

                    WebElement button = option.findElement(By.tagName("input"));

                    js.executeScript("arguments[0].scrollIntoView({block:'center'});", option);

                    clickElement(option);

                    return button.isSelected();
                }
            }
        } catch (Exception e) {
            System.out.println("Radio selection error: " + e.getMessage());
        }
        return false;
    }




    public String getToastMessage() {
        List<WebElement> messages = wait.waitForAllElementsVisible(toastMsg);
        for (WebElement msg : messages) {
            if (msg != null && msg.isDisplayed()) {
                return msg.getText();
            }
        }
        return "No message displayed";
    }


    //-----------------------------------------
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
    //===================================================================================


    public String gettoastmessage() {
        try {
            List<WebElement> messageElements = wait.waitForAllElementsVisible(toastorpopup);

            if (messageElements != null && !messageElements.isEmpty()) {
                for (WebElement msgElement : messageElements) {
                    if (!msgElement.isDisplayed())
                        continue;

                    String message = msgElement.getText().trim();
                    System.out.println("Toast message: " + message);

                    switch (message) {
                        case "Campaign Updated Successfully":

                            return message;

                        case "Code already exists!":
                            clickcloseform();
                            return  message;

                        case "Campaign Deleted Successfully":

                            return message;


                        case"Please enter all the mandatory fields before saving.":
                            handlePopupOK();

                            return message;


                        default:
                            // cancelform();
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

    public void clickcloseform(){

        try{
            WebElement close=wait.waitForVisibility(closeform);
            if(close!=null && close.isDisplayed()){
                close.click();
            }

        } catch (Exception e) {
            js.executeScript("arguments[0].click();",closeform);
        }
    }




    public void clickCloseForm() {
        try {
            wait.waitForClickability(closeform).click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", closeform);
        }
    }
}
