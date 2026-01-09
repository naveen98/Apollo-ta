package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Dropdownutils;
import utils.Webdriverwaitutils;

import java.util.ArrayList;
import java.util.List;

public class CampaignRecruiterAddingPage {

    WebDriver driver;
    Webdriverwaitutils wait;
    JavascriptExecutor js;

    public CampaignRecruiterAddingPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new Webdriverwaitutils(driver);
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver,this);

    }


    @FindBy(xpath = "//a[contains(@class,'sidebar-toggle')]")
    private WebElement menubar;

    @FindBy(xpath = "//li[@id='menu-li-campaign-campaign']//span[text()='Campaigns']")
    private WebElement campaignmodule;



    @FindBy(xpath="//td[contains(text(),' No records found ')]")private WebElement norecordfound;

    @FindBy(xpath = "//div//input[@placeholder='Search Name / State / Region / City / Area']")
    private WebElement searchinputbox;

    @FindBy(xpath="//div//button[@type='button']//span[contains(text(),'Search')]")private WebElement searchbutton;

    @FindBy(xpath = "//div[@class='candidate-name']//span[contains(text(),'FestiveSale')]")private WebElement rowselection;


    @FindBy(xpath = "//div[@role='tab' and .//span[normalize-space()='Recruiters']]")private WebElement recruitertab;

    @FindBy(xpath = "//button[@title='Add Recruiter']//span[contains(text(),'Add ')]")private WebElement addrecruiterbutton;

    @FindBy(xpath = "//div//p-autocomplete//input[contains(@placeholder,'Search and select for recruiter')]")private WebElement recruiterinputsearchbox;

    private final By recruiteroptions = By.xpath("//p-overlay//ul[@role='listbox']//li[@role='option']");


    @FindBy(xpath = "//div//button[@id='button']//span[contains(text(),'Save')]")private WebElement saverecruiterbutton;




    // ================== ACTION METHODS ==================

    public void navigatemenu() {
        try {
            wait.waitForClickability(menubar).click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", menubar);
        }
    }


    //===========Search and Select Campaigns=================
    public void searchcampaigns(String username){
        wait.waitForVisibility(searchinputbox).clear();
        wait.waitForVisibility(searchinputbox).sendKeys(username);
        clickElement(searchbutton);
    }


    public void selectcampaignrow(){
        clickElement(rowselection);
    }



    public boolean isNoRecordFoundDisplayed() {
        try {
            return driver.findElements(By.xpath("//td[contains(text(),'No records found')]")).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

   // ===========Search and Select User from Auto-suggest Dropdown=================
    public void userAdd(String userInput, String userExpected) {
        Dropdownutils.selectFromAutoSuggest(
                driver,
                recruiterinputsearchbox,
                recruiteroptions,
                userInput,
                userExpected
        );
    }

    private By toastMsg = By.xpath("//div[contains(@class, 'toast-message') and contains(@class, 'ng-star-inserted')]");


    public String gettoastmessage() {

        String message = "";
        List<WebElement> msg=wait.waitForAllElementsVisible(toastMsg);
        for(WebElement msgs:msg)
        {
            if (msgs != null && msgs.isDisplayed())
                message = msgs.getText();

            return message;
        }
        return "no message displayed";

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



    public void clickrecruitertab() {
        clickElement(recruitertab);

    }

    public void clickaddrecruiterbutton() {
        clickElement(addrecruiterbutton);

    }




    public void clicksaverecruiterbutton() {
        clickElement(saverecruiterbutton);
    }



    public void navigatecampaignmodule() {
        clickElement(campaignmodule);
    }





    //=========================Capture the Recruiter Campaign list=========================


    private final By Recruiteraddedtablelist = By.xpath("//div//p-table//table//tbody//tr");

    public List<String[]> getCampaignrecruiterTableData() {

        List<String[]> tableData = new ArrayList<>();

        wait.waitForPresence(Recruiteraddedtablelist);

        List<WebElement> rows = driver.findElements(
                By.xpath("//div//p-table//table//tbody//tr")
        );

        for (WebElement row : rows) {

            List<WebElement> cols = row.findElements(By.tagName("td"));

            // Skip invalid rows (No records )
            if (cols.size() < 4) {
                continue;
            }

            String recruiterName = cols.get(1).getText().trim();
            String applicationsReceived = cols.get(2).getText().trim();
            String joined = cols.get(3).getText().trim();

            tableData.add(new String[]{
                    recruiterName,
                    applicationsReceived,
                    joined
            });
        }

        return tableData;
    }



    //=================================Logout====================================

    @FindBy(xpath = "(//div//span[@class='welcome-user-pic'])[1]")private WebElement userprofileicon;

    @FindBy(xpath = "(//button[@id='logout'])[1]")private WebElement logoutbutton;

    public void logoutfromapplication() {
        clickElement(userprofileicon);
        clickElement(logoutbutton);
    }




}
