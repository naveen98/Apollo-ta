package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Datepickutils;
import utils.Webdriverwaitutils;

import java.util.ArrayList;
import java.util.List;

public class ApplicationsDetailsFromRecruiterLoginPage {

    WebDriver driver;
    Webdriverwaitutils wait;
    JavascriptExecutor js;

    public ApplicationsDetailsFromRecruiterLoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new Webdriverwaitutils(driver);
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    // ================= Menu =================

    @FindBy(xpath = "//a[contains(@class,'sidebar-toggle')]")
    private WebElement menubar;

    @FindBy(xpath = "//li[@id='menu-li-candidate-candidate']//span[text()='Applications']")
    private WebElement applicationmodule;

    @FindBy(xpath = "(//div//input[@placeholder='Search '])[1]")
    private WebElement searchinputbox;

    @FindBy(xpath="//div//button[@type='button']//span[contains(text(),' Search')]")private WebElement searchbutton;

    // ================= Table =================

    private final By ApplicationRows = By.xpath("//p-table//table//tbody//tr");

    // ================= Navigation =================

    public void navigatemenu() {

        safeClick(menubar);

    }

    public void navigateApplicationModule() {
        safeClick(applicationmodule);
    }

    public boolean isNoRecordFoundDisplayed() {
        try {
            return driver.findElements(By.xpath("//td[contains(text(),'No records found')]")).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }


    public void Searchapplicants(String username){
        wait.waitForVisibility(searchinputbox).clear();
        wait.waitForVisibility(searchinputbox).sendKeys(username);
        safeClick(searchbutton);
    }


    @FindBy(xpath="//span[contains(text(),'Cristiano')]")private WebElement nametext;

    public boolean isnamedisplayed(){
        WebElement ele= wait.waitForVisibility(nametext);
        return  ele.isDisplayed();

    }


    // ================= Table Data  =================

    public List<String[]> getApplicationTableData() {

        List<String[]> tableData = new ArrayList<>();


        wait.waitForPresence(ApplicationRows);

        List<WebElement> rows = driver.findElements(By.xpath("//p-table//table//tbody//tr"));

        for (int i = 1; i <= rows.size(); i++) {

            // Fetch columns freshly for each row
            List<WebElement> cols = driver.findElements(By.xpath("//p-table//table//tbody//tr[" + i + "]/td"));

            // Read each cell into variables
            String ApplicationNo = cols.get(0).getText().trim();
            String Applicant   = cols.get(1).getText().trim();
            String JobRole   = cols.get(2).getText().trim();
            String Location = cols.get(3).getText().trim();
            String Region   = cols.get(4).getText().trim();
            String Recruiter = cols.get(5).getText().trim();
            String Submitted = cols.get(6).getText().trim();
            String ActiveSince = cols.get(7).getText().trim();
            String Progress = cols.get(8).getText().trim();

            // Store in array
            String[] rowData = {ApplicationNo, Applicant, JobRole, Location, Region, Recruiter, Submitted,ActiveSince,Progress};

            tableData.add(rowData);
        }

        return tableData;
    }


    // ================= Safe click =================

    private void safeClick(WebElement element) {
        try {
            wait.waitForClickability(element).click();
        } catch (Exception e) {
            js.executeScript(
                    "arguments[0].scrollIntoView({block:'center'});",
                    element
            );
            js.executeScript("arguments[0].click();", element);
        }
    }

    // Locators
    @FindBy(xpath= "//div[@class='zc-data-list-filters ng-star-inserted']")
    private WebElement txtdisplay;


    public boolean isnavigatedtxt() {
        try {
            WebElement text = wait.waitForVisibility(txtdisplay);
            return text.isDisplayed();
        } catch (Exception e) {
            System.out.println("App not displayed: " + e.getMessage());
        }
        return false;
    }

}
