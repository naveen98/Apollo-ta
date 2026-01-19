package stepdefinitions;

import com.aventstack.extentreports.markuputils.ExtentColor;
import drivers.DriverManager;
import io.cucumber.java.en.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pageobjects.CamapaignsCountsPage;
import pageobjects.CampaignCreationPage;
import pageobjects.CampaigntotalPagecounts;
import utils.Excelutils;
import utils.Radiobuttons;
import utils.ExtentTestManager;
import utils.UrlAssertionUtils;

import java.io.IOException;
import java.util.List;

public class CamapignCreationSteps {

    private static final Logger log =
            LogManager.getLogger(CamapignCreationSteps.class);

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

    // ================= GIVEN =================

    @Given("I am on the campaign module page")
    public void i_am_on_the_campaign_module_page() {

      try {
          driver = DriverManager.getDriver();

          cp = new CampaignCreationPage(driver);
          rd = new Radiobuttons(driver);
          cc = new CamapaignsCountsPage(driver);
          js = (JavascriptExecutor) driver;
          pgcounts = new CampaigntotalPagecounts(driver);


          Assert.assertTrue(driver != null, "WebDriver is NULL");
          ExtentTestManager.getTest().info("Campaign module initialized");

      }

      catch (Exception e){
          ExtentTestManager.logFail("Exception in campaign module initialization: " + e.getMessage());
      }

    }

    // ================= WHEN =================

    @When("I navigate to campaign module for count verification")
    public void i_navigate_to_campaign_module_for_count_verification() {

      try {
          cp.navigatemenu();
          cp.navigatecampignmodule();
          cp.waitforcreatecampaign();


          UrlAssertionUtils.validateUrl(driver, "https://apollota.v37.dev.zeroco.de/ta/campaign/campaign");
          ExtentTestManager.logPass("Navigated to Campaign Module successfully");
      }

      catch (Exception e){
          ExtentTestManager.logFail("Exception while navigating to campaign module: " + e.getMessage());
      }


    }



    // ================= AND =================

    @And("I capture campaign count before creation")
    public void i_capture_campaign_count_before_creation() {

        try {
            countBeforeCreation = cc.getTotalCampaignCount();

            beforePageTotalCount = pgcounts.getTotalPagesFromText();

            log.info("Campaign Count BEFORE creation : " + countBeforeCreation);
            log.info("Page Count BEFORE creation     : " + beforePageTotalCount);

            ExtentTestManager.getTest().info("Campaign Count BEFORE creation : " + countBeforeCreation);
            ExtentTestManager.getTest().info("Page Count BEFORE creation : " + beforePageTotalCount);

            Assert.assertTrue(countBeforeCreation >= 0, "Campaign count should be zero or more");
            Assert.assertTrue(beforePageTotalCount > 0, "Page count should be greater than zero");
            ExtentTestManager.logPass("Campaign and page counts captured successfully before creation");

        }
        catch (Exception e){
            ExtentTestManager.logFail("Exception while capturing campaign count before creation: " + e.getMessage());
        }

    }


    // ================= THEN =================

    @Then("I create campaigns and verify campaign count")
    public void I_create_campaigns_and_verify_campaign_count() throws IOException {
        try {

            String path = "D:\\selenium-intellij\\src\\test\\resources\\campaigncreation.xlsx";
            String sheetname = "create";

            String[][] data = Excelutils.exceldata(path, sheetname);

            Assert.assertTrue(data != null, "Excel data is NULL");

            int campaignsCreated = 0;

            for (int i = 0; i < data.length; i++) {

                try {
                    int countBeforeEachCreation = cc.getTotalCampaignCount();

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

                    // ================= Campaign Details Validation =================
                    List<String> campaigndetailsMessages = cp.getValidationMessages();
                    if (!campaigndetailsMessages.isEmpty()) {
                        for (String msg : campaigndetailsMessages) {
                            ExtentTestManager.logFail("Campaign Form Validation Errors at Row: " + msg);
                        }
                        driver.navigate().refresh();
                        continue;
                    } else {
                        ExtentTestManager.logPass("Campaign Form validated successfully");
                    }

                    // ================= Source Type Validation =================
                    cp.selectfromsourcetype(sourcetype);
                    cp.addtargetnextbtn();

                    List<String> sourcetypeMessages = cp.getValidationMessages();
                    if (!sourcetypeMessages.isEmpty()) {
                        for (String msg : sourcetypeMessages) {
                            ExtentTestManager.logFail("SourceType Validation Errors at Row: " + msg);
                        }
                        driver.navigate().refresh();
                        continue;
                    } else {
                        ExtentTestManager.logPass("SourceType validated successfully");
                    }

                    // ================= Notes Validation =================
                    cp.NoteDescription(notes);

                    List<String> NotesMessages = cp.getValidationMessages();
                    if (!NotesMessages.isEmpty()) {
                        for (String msg : NotesMessages) {
                            ExtentTestManager.logFail("Notes Validation Errors at Row: " + msg);
                        }
                        driver.navigate().refresh();
                        continue;
                    } else {
                        ExtentTestManager.logPass("Notes validated successfully");
                    }

                    // ================= Toast Message Validation =================
                    String toastMessage = cp.gettoastmessage();
                    driver.navigate().refresh();
                    int countAfterEachCreation = cc.getTotalCampaignCount();

                    if (toastMessage != null && (toastMessage.toLowerCase().contains("campaign") || toastMessage.toLowerCase().contains("saved"))) {

                        // SUCCESS CASE
                        Assert.assertEquals(countAfterEachCreation, countBeforeEachCreation + 1, "Campaign count did not increase");
                        campaignsCreated++;
                        ExtentTestManager.logPass("Campaign created successfully: " + "Campaign Name :" + camname + toastMessage);

                    } else {

                        // FAILURE CASE
                        Assert.assertNotNull(toastMessage, "Expected error toast but toast is NULL");

                        Assert.assertTrue(
                                toastMessage.toLowerCase().contains("already") ||
                                        toastMessage.toLowerCase().contains("exists") ||
                                        toastMessage.toLowerCase().contains("code"),
                                "Unexpected toast message: " + toastMessage);

                        Assert.assertEquals(countAfterEachCreation, countBeforeEachCreation,
                                "Campaign count changed for INVALID creation");

                        ExtentTestManager.logFail("Campaign creation failed as expected: " + camname);
                    }

                } catch (Exception e) {
                    ExtentTestManager.getTest().info("Exception occurred for row : " + e.getMessage());
                    try {
                        driver.navigate().refresh();
                    } catch (Exception refreshEx) {
                        log.error("Exception while refreshing page: ", refreshEx);
                    }
                    continue;
                }
            }

            // ================= FINAL VERIFICATION =================
            countAfterCreation = cc.getTotalCampaignCount();
            Assert.assertEquals(countAfterCreation, countBeforeCreation + campaignsCreated, "FINAL campaign count mismatch");

            ExtentTestManager.logPass("FINAL VERIFICATION PASSED | Total campaigns created: " + campaignsCreated);
        } catch (Exception d) {
            ExtentTestManager.logFail("Exception in campaign creation and verification: " + d.getMessage());
        }
    }

}