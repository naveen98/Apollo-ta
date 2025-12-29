package stepdefinitions;

import drivers.DriverManager;
import io.cucumber.java.en.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pageobjects.CamapaignsCountsPage;
import pageobjects.CampaignCreationPage;
import pageobjects.CampaigntotalPagecounts;
import utils.Excelutils;
import utils.Paginations;
import utils.Radiobuttons;

import java.io.IOException;

public class CamapignCreationSteps {

    WebDriver driver;
    CampaignCreationPage cp;
    Radiobuttons rd;
    JavascriptExecutor js;
    CamapaignsCountsPage cc;
    CampaigntotalPagecounts pgcounts;

    private int countBeforeCreation;
    private int countAfterCreation;
    private int beforePageTotalCount;
    private int afterpagetotlalcount;

    // ---------------- GIVEN ----------------

    @Given("I am on the campaign module page")
    public void i_am_on_the_campaign_module_page() {
        driver = DriverManager.getDriver();
        cp = new CampaignCreationPage(driver);
        rd = new Radiobuttons(driver);
        cc = new CamapaignsCountsPage(driver);
        js = (JavascriptExecutor) driver;
        pgcounts = new CampaigntotalPagecounts(driver);
    }

    // ---------------- WHEN ----------------

    @When("I navigate to campaign module for count verification")
    public void i_navigate_to_campaign_module_for_count_verification() {
        cp.navigatemenu();
        cp.navigatecampignmodule();
    }

    // ---------------- AND ----------------

    @And("I capture campaign count before creation")
    public void i_capture_campaign_count_before_creation() {
        countBeforeCreation = cc.getTotalCampaignCount();
        beforePageTotalCount = pgcounts.getTotalPagesFromText();

        System.out.println("Campaign Count BEFORE creation : " + countBeforeCreation);
        System.out.println("Page Count BEFORE creation     : " + beforePageTotalCount);
    }

    // ---------------- THEN ----------------

    @Then("I create campaigns from excel and verify campaign count")
    public void i_create_campaigns_from_excel_and_verify_campaign_count() throws IOException {

        String path = "D:\\selenium-intellij\\src\\test\\resources\\campaigncreation.xlsx";
        String sheetname = "create";

        String[][] data = Excelutils.getcelldatas(path, sheetname);

        int campaignsCreated = 0;

        for (int i = 0; i < 1; i++) {

            String camname = data[i][0];
            String campcode = data[i][1];
            String timeBound = data[i][2];

            String startmonth = data[i][3];
            String startyear = data[i][4];
            String startdate = data[i][5];
            String endmonth = data[i][6];
            String endyear = data[i][7];
            String enddate = data[i][8];

            String medium = data[i][9];
            String locationtype = data[i][10];
            String venueAddr = data[i][11];
            String contactinfo = data[i][12];
            String sourcetype = data[i][13];
            String notes = data[i][14];

            System.out.println("----- Creating Campaign : " + camname + " -----");

            cp.clickcampaignbtn();
            cp.createcampaign(camname, campcode);

            cp.selectRadioButtonOption("Time Bound Campaign", timeBound);

            if (timeBound.equalsIgnoreCase("Yes") && cp.iscalenderfieldsvisible()) {
                cp.startdate(startmonth, startyear, startdate);
                cp.enddate(endmonth, endyear, enddate);
            }

            cp.selectRadioButtonOption("Share Venue Contact Info", contactinfo);
            cp.selectRadioButtonOption("Medium", medium);

            if (medium.equalsIgnoreCase("Offline")) {

                if (locationtype.equalsIgnoreCase("venue")) {
                    cp.waitForVenueInputVisible();
                    cp.selectVenue(venueAddr);
                    js.executeScript("window.scrollBy(0,300);");

                } else if (locationtype.equalsIgnoreCase("mylocation")) {
                    cp.useMyLocation();
                    js.executeScript("window.scrollBy(0,300);");
                }
            }

            cp.clickNextButton();
            cp.selectfromsourcetype(sourcetype);
            cp.addtargetnextbtn();
            cp.NoteDescription(notes);
           // cp.clickSaveAndContinue();

            String toastMessage = cp.gettoastmessage();

            if (toastMessage.toLowerCase().contains("campaign")
                    || toastMessage.toLowerCase().contains("saved")) {

                System.out.println("Campaign Saved Successfully : " + camname);
                campaignsCreated++;

            } else {
                System.out.println("Campaign creation FAILED : " + toastMessage);
                cp.clickcloseform();
            }
        }

        // -------- AFTER CREATION COUNTS --------

        driver.navigate().refresh();

        countAfterCreation = cc.getTotalCampaignCount();
        afterpagetotlalcount = pgcounts.getTotalPagesFromText();

        System.out.println("Campaign Count BEFORE creation : " + countBeforeCreation);
        System.out.println("Campaigns Created              : " + campaignsCreated);
        System.out.println("Campaign Count AFTER creation  : " + countAfterCreation);

        System.out.println("Page Count BEFORE creation     : " + beforePageTotalCount);
        System.out.println("Page Count AFTER creation      : " + afterpagetotlalcount);

        // -------- VERIFICATION 1 : CAMPAIGN COUNT --------
        Assert.assertEquals(countAfterCreation, countBeforeCreation + campaignsCreated, "Campaign count mismatch after creating campaign");

        // -------- VERIFICATION 2 : PAGINATION COUNT --------
        Assert.assertTrue(afterpagetotlalcount >= beforePageTotalCount, "Pagination count should not decrease after campaign creation");

        System.out.println("Campaign count verification PASSED");
        System.out.println("Pagination count verification PASSED");


    }
}
