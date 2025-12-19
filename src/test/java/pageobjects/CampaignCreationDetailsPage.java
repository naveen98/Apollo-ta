package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Datepickutils;
import utils.Webdriverwaitutils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CampaignCreationDetailsPage {

    WebDriver driver;
    Webdriverwaitutils wait;
    JavascriptExecutor js;
    Datepickutils dt;

    public CampaignCreationDetailsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new Webdriverwaitutils(driver);
        this.js = (JavascriptExecutor) driver;
        this.dt = new Datepickutils(driver);
        PageFactory.initElements(driver, this);
    }

    // ================= Menu =================

    @FindBy(xpath = "//a[contains(@class,'sidebar-toggle')]")
    private WebElement menubar;

    @FindBy(xpath = "//li[@id='menu-li-campaign-campaign']//span[text()='Campaigns']")
    private WebElement campaignmodule;

    //================= Date =================

    @FindBy(xpath = "//table//tr//th[.//span[text()='Start Date']]")
    private WebElement startDateColumn;

    @FindBy(xpath = "//table//tr//th[.//span[text()='End Date']]")
    private WebElement endDateColumn;

    /* ================= Calender ================= */

    private final By monthText =
            By.xpath("//button[@aria-label='Choose Month']");

    private final By yearText =
            By.xpath("//button[@aria-label='Choose Year']");

    private final By allDates =
            By.xpath("//span[contains(@class,'p-datepicker-day') and not(contains(@class,'p-disabled'))]");

    private final By nextBtn =
            By.xpath("//button[contains(@class,'p-datepicker-next-button')]");

    private final By prevBtn =
            By.xpath("//button[contains(@class,'p-datepicker-prev-button')]");

    // ================= Table =================

    private final By campaignRows = By.xpath("//p-table//table//tbody//tr");

    // ================= Navigation =================

    public void navigatemenu() {
        safeClick(menubar);
    }

    public void navigatecampignmodule() {
        safeClick(campaignmodule);
    }

    // ================= Start Date =================

    public void selectStartDate(String month, String year, String date) {
        openCalendar(startDateColumn);
        dt.datepickers(monthText, yearText, prevBtn, nextBtn, allDates,
                month, year, date);
        dt.datepickers(monthText, yearText, prevBtn, nextBtn, allDates,
                month, year, date);
    }

    //================= End Date =================

    public void selectEndDate(String month, String year, String date) {
        // openCalendar(endDateColumn);
        dt.datepickers(monthText, yearText, prevBtn, nextBtn, allDates,
                month, year, date);
    }

    // ================= Calender open ==============

    private void openCalendar(WebElement column) {

        safeClick(column);

        WebElement chooseDateBtn = column.findElement(
                By.xpath(".//button[@aria-label='Choose Date']")
        );
        safeClick(chooseDateBtn);
    }

    // ================= Table Data  =================

    public List<String[]> getCampaignTableData() {

        List<String[]> tableData = new ArrayList<>();


        wait.waitForPresence(campaignRows);

        List<WebElement> rows = driver.findElements(By.xpath("//p-table//table//tbody//tr"));

        for (int i = 1; i <= rows.size(); i++) {

            // Fetch columns freshly for each row
            List<WebElement> cols = driver.findElements(By.xpath("//p-table//table//tbody//tr[" + i + "]/td"));

            // Read each cell into variables
            String name      = cols.get(0).getText().trim();
            String medium    = cols.get(1).getText().trim();
            String contact   = cols.get(2).getText().trim();
            String startDate = cols.get(3).getText().trim();
            String endDate   = cols.get(4).getText().trim();
            String status    = cols.get(5).getText().trim();
            String createdBy = cols.get(6).getText().trim();

            // Store in array
            String[] rowData = {name, medium, contact, startDate, endDate, status, createdBy};

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
}
