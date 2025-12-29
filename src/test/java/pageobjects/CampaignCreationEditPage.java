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


    @FindBy(xpath = "//div//button[@id='btnNxt']//span[contains(text(),'Next')]")
    private WebElement nextbtn;

    private By nextbtnby =
            By.xpath("//div//button[@id='btnNxt']//span[contains(text(),'Next')]");

    // ---------- Radio buttons ----------

    @FindBy(xpath = "//div[@class='form-group zc-field-radio'][.//span[contains(text(),'Share Venue Contact Info')]]")
    private WebElement shareVenueContactInfoLabel;

    @FindBy(xpath = "//div[@class='form-group zc-field-radio'][.//span[contains(text(),'Medium')]]")
    private WebElement mediumLabel;

    // ---------- Target ----------
    @FindBy(xpath = "//div//input[@placeholder='Search Name / State / Region / City / Area']")
    private WebElement searchinputbox;

    @FindBy(xpath = "//button[@id='btnNext']")
    private WebElement targetnextbtn;

    // ---------- Notes ----------
    @FindBy(xpath = "//textarea[@placeholder='Enter notes']")
    private WebElement notes;

    @FindBy(xpath = "//zc-button//div//button[@id='btnUpdate']")
    private WebElement updatebutton;

    // ---------- Toast ----------
    private By toastMsg =
            By.xpath("//div[contains(@class,'toast-message') and contains(@class,'ng-star-inserted')]");

    // ---------- Close ----------
    @FindBy(xpath = "//button[contains(@class,'close')]")
    private WebElement closeform;



    @FindBy(xpath="//td[contains(text(),' No records found ')]")private WebElement norecordfound;

    @FindBy(xpath="//div//button[@type='button']//span[contains(text(),'Search')]")private WebElement searchbutton;
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

    public void clickCloseForm() {
        try {
            wait.waitForClickability(closeform).click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", closeform);
        }
    }
}
