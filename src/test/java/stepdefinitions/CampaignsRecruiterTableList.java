package stepdefinitions;

import drivers.DriverManager;
import io.cucumber.java.en.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pageobjects.CampaignRecruiterAddingPage;
import utils.Excelutils;
import utils.ExtentTestManager;

import java.io.IOException;
import java.util.List;

public class CampaignsRecruiterTableList {

    private static final Logger log =
            LogManager.getLogger(CampaignsRecruiterTableList.class);

    WebDriver driver;
    CampaignRecruiterAddingPage ad;

    List<String[]> campaignRecruiterList;
    String expectedRecruiterName = "Arya A";

    // ================= GIVEN =================

    @Given("I navigate to campaign Recruiter Table")
    public void i_navigate_to_campaign_recruiter_table() {

        driver = DriverManager.getDriver();
        ad = new CampaignRecruiterAddingPage(driver);

        Assert.assertNotNull(driver, "WebDriver is NULL");

        ad.navigatemenu();
        ad.navigatecampaignmodule();

        ExtentTestManager.getTest()
                .info("Navigated to Campaign Recruiter Table");
    }

    // ================= WHEN =================

    @When("I capture added recruiter in campaign")
    public void i_capture_added_recruiter_in_campaign() throws IOException {

        ad.searchcampaigns("FestiveSale");

        Assert.assertFalse(
                ad.isNoRecordFoundDisplayed(),
                "No campaign record found for FestiveSale"
        );

        ad.selectcampaignrow();
        ad.clickrecruitertab();

        campaignRecruiterList =
                ad.getCampaignrecruiterTableData();

        // ---------- TABLE ASSERTIONS ----------
        Assert.assertNotNull(
                campaignRecruiterList,
                "Recruiter table data is NULL"
        );

        Assert.assertTrue(
                campaignRecruiterList.size() > 0,
                "Recruiter table is EMPTY"
        );

        log.info("Total recruiters found: " +
                campaignRecruiterList.size());

        ExtentTestManager.getTest().info(
                "Total recruiters found: " +
                        campaignRecruiterList.size()
        );

        // ---------- WRITE TO EXCEL ----------
        String[] headers = {
                "Name", "Application Received", "Joined"
        };

        String path =
                "D:\\selenium-intellij\\src\\test\\resources\\campaigndetails.xlsx";
        String shname = "recruiterlist";

        Excelutils.writeTable(
                path, shname, headers, campaignRecruiterList
        );

        ExtentTestManager.getTest()
                .info("Recruiter table data written to Excel");

        ad.logoutfromapplication();
    }

    // ================= THEN =================

    @Then("I should see the added recruiter in campaign recruiter table")
    public void i_should_see_added_recruiter_in_campaign_recruiter_table() {

        boolean recruiterFound = false;

        for (String[] row : campaignRecruiterList) {

            String recruiterName = row[0];

            if (recruiterName.equalsIgnoreCase(expectedRecruiterName)) {
                recruiterFound = true;
                break;
            }
        }

        Assert.assertTrue(
                recruiterFound,
                "Expected recruiter NOT found in table: "
                        + expectedRecruiterName
        );

        log.info(
                "Recruiter verified successfully: "
                        + expectedRecruiterName
        );

        ExtentTestManager.getTest()
                .pass(
                        "Recruiter verified successfully: "
                                + expectedRecruiterName
                );
    }
}
