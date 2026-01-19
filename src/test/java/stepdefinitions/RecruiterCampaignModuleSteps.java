package stepdefinitions;

import drivers.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pageobjects.RecruiterCamapignModulePage;
import utils.Excelutils;
import utils.ExtentTestManager;
import utils.UrlAssertionUtils;

import java.io.IOException;
import java.util.List;

public class RecruiterCampaignModuleSteps {

    WebDriver driver;
    RecruiterCamapignModulePage recruiterCampaignPage;
    List<String[]> campaignTableData;

    // ================= GIVEN =================

    @Given("I Navigate To Campaign Module for capturing table data")
    public void I_navigate_to_campaign_module_for_capturing_table_data() {

        driver = DriverManager.getDriver();
        Assert.assertNotNull(driver, "WebDriver is NULL");

        recruiterCampaignPage = new RecruiterCamapignModulePage(driver);

        recruiterCampaignPage.navigatemenu();

        recruiterCampaignPage.waitforsearchcampaign();

        UrlAssertionUtils.validateUrl(driver,"https://apollota.v37.dev.zeroco.de/ta/campaign/campaign");

        ExtentTestManager.logPass("Navigated to Recruiter Campaign module");
    }

    // ================= WHEN =================

    @When("I Captures the campaigns Table data")
    public void i_captures_the_campaigns_table_data() throws IOException {

        recruiterCampaignPage.waitforcampainstext();

        campaignTableData = recruiterCampaignPage.getCampaignTableData();

        // ---------- Assertions ----------
        Assert.assertNotNull(campaignTableData, "Campaign table data is NULL");

        Assert.assertTrue(campaignTableData.size()>=0, "Campaign table is EMPTY");

        String[] headers = {"name", "medium", "shareVenue", "startDate", "endDate", "status", "createdBy"};

        String path = "D:\\selenium-intellij\\src\\test\\resources\\RecruiterDetails.xlsx";
        String sheetname = "camapigns";

        Excelutils.writeTable(path, sheetname, headers, campaignTableData);

        ExtentTestManager.logPass("Campaign table data captured and written to Excel");

        // Optional logging
        for (String[] row : campaignTableData) {
            ExtentTestManager.getTest().info("Campaign | " + "name=" + row[0] + ", medium=" + row[1] + ", shareVenue=" + row[2] + ", startDate=" + row[3] + ", endDate=" + row[4] + ", status=" + row[5] + ", createdBy=" + row[6]);
        }
    }

    // ================= THEN =================

    @Then("I should click on logout")
    public void i_should_click_on_logout() {

        recruiterCampaignPage.logoutfromapplication();

        UrlAssertionUtils.validateUrl(driver,"https://apollota.v37.dev.zeroco.de/");

        ExtentTestManager.logPass("Recruiter Campaign table verified and logout successful");
    }
}
