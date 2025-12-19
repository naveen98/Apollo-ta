package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Webdriverwaitutils;

import java.util.ArrayList;
import java.util.List;

public class ApplicationPage {

    WebDriver driver;
    JavascriptExecutor js;
    Webdriverwaitutils wait;

    public ApplicationPage(WebDriver driver){
        this.driver=driver;
        this.js=(JavascriptExecutor) driver;
        this.wait=new Webdriverwaitutils(driver);
        PageFactory.initElements(driver,this);


    }

    //Locatiors


    @FindBy(xpath = "//a[contains(@class,'sidebar-toggle')]")
    private WebElement menubar;

    @FindBy(xpath = "//li[@id='menu-li-candidate-candidate']//span[text()='Applications']")
    private WebElement applicationmodule;

    @FindBy(xpath="//div//input[@class='ng-pristine ng-valid ng-touched']")private WebElement searchinputbox;
    @FindBy(xpath="//button[@class='btn btn-primary filter-clear-btn zc-global-search-btn ng-star-inserted']")private WebElement searchbutton;


    @FindBy(xpath="//p-table//table//tbody//tr")private WebElement applicationsrow;
    @FindBy(xpath="//td[contains(text(),' No records found ')]")private WebElement norecordfound;



    //---------Action Methods---------------------

    public void navigatetoapplications(){
        clickElement(menubar);
        clickElement(applicationmodule);
    }


    public boolean isNoRecordFoundDisplayed() {
        try {
            return wait.waitForVisibility(norecordfound).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }


    // search fields

    public void Searchapplications(String username){
        wait.waitForVisibility(searchinputbox).clear();
        wait.waitForVisibility(searchinputbox).sendKeys(username);
        clickElement(searchbutton);

    }


    public void clickonapplicationrow(){
        clickElement(applicationsrow);
    }

    // ================= Table Data  =================

    public List<String[]> getApplicationsTableData() {

        List<String[]> tableData = new ArrayList<>();

        wait.waitForVisibility(applicationsrow);

        //  Get all rows
        List<WebElement> rows = driver.findElements(By.xpath("//p-table//table//tbody//tr"));

        // Iterate row by row
        for (WebElement row : rows) {

            //  Get all columns in the current row
            List<WebElement> cols = row.findElements(By.tagName("td"));

            // Read cell values
            String[] rowData = {

                    cols.get(0).getText().trim(),
                    cols.get(1).getText().trim(),
                    cols.get(2).getText().trim(),
                    cols.get(3).getText().trim(),
                    cols.get(4).getText().trim(),
                    cols.get(5).getText().trim(),
                    cols.get(6).getText().trim()
            };


            tableData.add(rowData);
        }

        return tableData;
    }


    // ---------------------- Common Click ----------------------

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
