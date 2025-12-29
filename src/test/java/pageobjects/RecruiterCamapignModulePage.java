package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Webdriverwaitutils;

import java.util.ArrayList;
import java.util.List;

public class RecruiterCamapignModulePage {

    WebDriver driver;
    Webdriverwaitutils wait;
    JavascriptExecutor js;

    public RecruiterCamapignModulePage(WebDriver driver){

        this.driver=driver;
        this.wait=new Webdriverwaitutils(driver);
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

    // ================== ACTION METHODS ==================

    public void navigatemenu() {
        try {
            wait.waitForClickability(menubar).click();
            wait.waitForClickability(campaignmodule).click();

        } catch (Exception e) {
            js.executeScript("arguments[0].click();", menubar);
            js.executeScript("arguments[0].click();", campaignmodule);

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
    private final By Recruiteraddedtablelist = By.xpath("//div//p-table//table//tbody//tr");

    public List<String[]> getCampaignrecruiterTabTableData() {

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

     @FindBy(xpath="//p[text()=' Campaigns']/preceding-sibling::h1")private  WebElement campaignstext;


    public void waitforcampainstext() {

        wait.waitForVisibility(campaignstext);


    }


    // ================= Campaign Table =================

    private final By campaignTableRows =
            By.xpath("//div//p-table//table//tbody//tr");

    public List<String[]> getCampaignTableData() {

        List<String[]> tableData = new ArrayList<>();

        wait.waitForPresence(campaignTableRows);

        List<WebElement> rows = driver.findElements(campaignTableRows);

        for (WebElement row : rows) {

            List<WebElement> cols = row.findElements(By.tagName("td"));

            //  Skip "No records found" or invalid rows
            if (cols.size() < 7) {
                continue;
            }

            String name = cols.get(0).getText().trim();
            String medium = cols.get(1).getText().trim();
            String shareVenue = cols.get(2).getText().trim();
            String startDate = cols.get(3).getText().trim();
            String endDate = cols.get(4).getText().trim();
            String status = cols.get(5).getText().trim();
            String createdBy = cols.get(6).getText().trim();

            tableData.add(new String[]{name, medium, shareVenue, startDate, endDate, status, createdBy });
        }

        return tableData;
    }




    //=================================Logout====================================

    @FindBy(xpath = "//a[contains(@class,'zc-profile-dp-link')]//span[contains(@class,'welcome-user-pic')]")private WebElement userprofileicon;

    @FindBy(xpath = "(//button[@id='logout'])[1]")private WebElement logoutbutton;

    public void logoutfromapplication() {
        clickElement(userprofileicon);
        clickElement(logoutbutton);
    }


}
