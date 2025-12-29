package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Webdriverwaitutils;

import java.util.ArrayList;
import java.util.List;

public class VenuTableDataFetchPage {

    WebDriver driver;

    Webdriverwaitutils wait;
    JavascriptExecutor js;

    public VenuTableDataFetchPage(WebDriver driver) {

        this.driver = driver;
        this.wait = new Webdriverwaitutils(driver);
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver,this);

    }


    //------------Navigations---------------------
    @FindBy(xpath = "//a[@mattooltip='Click to toggle Menu']")
    private WebElement Menubar;


    @FindBy(xpath = "//li[@id='menu-li-venue-venue']//span[contains(text(),'Venues')]")
    private WebElement venumodule;


    @FindBy(xpath="//td[contains(text(),' No records found ')]")private WebElement norecordfound;


    @FindBy(xpath="//div//input[@placeholder='Search Name /  Contact Person / Contact Mobile No / Venue / Availability In']")private WebElement searchinputbox;
   private By searchinputboxBy=By.xpath("//div//input[@type='text' and contains(@placeholder,'Contact Person')]");
    @FindBy(xpath="//div//button[@class='btn btn-primary filter-clear-btn zc-global-search-btn ng-star-inserted']")private WebElement searchbutton;


    //---------Action Methods---------------------

    public boolean isNoRecordFoundDisplayed() {
        try {
            return wait.waitForVisibility(norecordfound).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }


    // search fields

    public void SeachVenudetails(String username){
        wait.waitForPresence(searchinputboxBy).clear();
        wait.waitForPresence(searchinputboxBy).sendKeys(username);
        clickElement(searchbutton);

    }



    public void navigatetovenumodule() {

        try {

            WebElement ele = wait.waitForClickability(Menubar);
            ele.click();
        } catch (Exception e) {

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", Menubar);
        }

        try {
            WebElement ele = wait.waitForClickability(venumodule);
            ele.click();
        } catch (Exception e) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", venumodule);
        }
    }



    private final By Venuetabledatarows = By.xpath("//div//p-table//table//tbody//tr");

    public List<String[]> getVenueTableData() {

        List<String[]> tableData = new ArrayList<>();

        // Wait until table rows are present
        wait.waitForPresence(Venuetabledatarows);

        List<WebElement> rows = driver.findElements(
                By.xpath("//div//p-table//table//tbody//tr"));

        for (WebElement row : rows) {

            List<WebElement> cols = row.findElements(By.tagName("td"));

            String Name          = cols.get(0).getText().trim();
            String contactPerson = cols.get(1).getText().trim();
            String contactMobileNumber = cols.get(2).getText().trim();
            String venue = cols.get(3).getText().trim();
            String availabilityIn = cols.get(4).getText().trim();

            String[] rowData = {
                    Name,
                    contactPerson,
                    contactMobileNumber,
                    venue,
                    availabilityIn
            };

            tableData.add(rowData);
        }

        return tableData;
    }

    // ================= Safe click =================


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



}
