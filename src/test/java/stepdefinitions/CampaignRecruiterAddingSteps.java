package stepdefinitions;

import drivers.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pageobjects.CampaignRecruiterAddingPage;

public class CampaignRecruiterAddingSteps {

    WebDriver driver;
    CampaignRecruiterAddingPage rp;

    // ---------------- GIVEN ----------------

    @Given("navigate to campaign module")
    public void navigate_to_campaign_module() {

        driver = DriverManager.getDriver();
        rp = new CampaignRecruiterAddingPage(driver);

        rp.navigatemenu();
    }

    // ---------------- WHEN ----------------

    @When("I click on campaign and add recruiters")
    public void i_click_on_campaign_and_add_recruiters() {

        // Search Campaign
        rp.searchcampaigns("FestiveSale");

        if (rp.isNoRecordFoundDisplayed()) {
            System.out.println("No record found for : ");
            return;
        }

        // Select Campaign Row
        rp.selectcampaignrow();

        // Click Recruiter Tab
        rp.clickrecruitertab();

        // Click Add Recruiter Button
        rp.clickaddrecruiterbutton();

        // Search & Select Recruiter from auto-suggest
        rp.userAdd("Arya", "Arya A");

        // Save Recruiter
        rp.clicksaverecruiterbutton();
    }

}
