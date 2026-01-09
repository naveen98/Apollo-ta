package stepdefinitions;

import drivers.DriverManager;
import io.cucumber.java.en.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pageobjects.CampaignRecruiterAddingPage;
import utils.ExtentTestManager;

public class CampaignRecruiterAddingSteps {

    private static final Logger log = LogManager.getLogger(CampaignRecruiterAddingSteps.class);

    WebDriver driver;
    CampaignRecruiterAddingPage rp;

    String recruiterName = "Arya A";

    // ---------------- GIVEN ----------------

    @Given("I have navigated to the Campaigns module")
    public void I_have_navigated_to_the_Campaigns_module() {

        driver = DriverManager.getDriver();
        rp = new CampaignRecruiterAddingPage(driver);

        rp.navigatemenu();

        ExtentTestManager.getTest().info("Navigated to Campaign module");
        Assert.assertNotNull(driver, "WebDriver is NULL");
    }

    // ---------------- WHEN ----------------

    @When("I click on a campaign and add recruiters")
    public void I_click_on_a_campaign_and_add_recruiters() {

        rp.searchcampaigns("FestiveSale");

        Assert.assertFalse(rp.isNoRecordFoundDisplayed(), "No campaign record found");

        rp.selectcampaignrow();
        rp.clickrecruitertab();
        rp.clickaddrecruiterbutton();

        rp.userAdd("Arya", recruiterName);
        rp.clicksaverecruiterbutton();

        ExtentTestManager.getTest().info("Recruiter added: " + recruiterName);
    }

    // ---------------- THEN ----------------

    @Then("I should see the recruiters successfully added to the campaign")
    public void verify_recruiter_added_to_campaign() {

        //  Toast message validation
        String toastMessage = rp.gettoastmessage();

        log.info("Toast Message : " + toastMessage);
        ExtentTestManager.getTest().info("Toast Message : " + toastMessage);

        Assert.assertNotNull(toastMessage, "Toast message is NULL");

        Assert.assertTrue(toastMessage.toLowerCase().contains("recruiter") || toastMessage.toLowerCase().contains("added") || toastMessage.toLowerCase().contains("success"), "Recruiter addition failed. Toast: " + toastMessage   );

    }
}
