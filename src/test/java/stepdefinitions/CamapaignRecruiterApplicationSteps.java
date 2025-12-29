package stepdefinitions;

import drivers.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pageobjects.CamapaignRecruiterApplicationPage;
import pageobjects.CampaignRecruiterAddingPage;


public class CamapaignRecruiterApplicationSteps {

    WebDriver driver;
    CamapaignRecruiterApplicationPage recruiterAppPage;
    String copiedUrl;

    // ---------------- GIVEN ----------------

    @Given("I am on campaign recruiter page")
    public void i_am_on_campaign_recruiter_page() {
        driver = DriverManager.getDriver();
        recruiterAppPage = new CamapaignRecruiterApplicationPage(driver);
    }

    // ---------------- WHEN ----------------

    @When("I click recruiter copy url and open in new tab")
    public void i_click_recruiter_copy_url_and_open_in_new_tab() {

        recruiterAppPage.clickRecruiter();
        recruiterAppPage.clickCopyUrl();
        recruiterAppPage.clickOk();
        recruiterAppPage.closePopup();

        copiedUrl = recruiterAppPage.getCopiedUrlFromClipboard();
        recruiterAppPage.openUrlInNewTab(copiedUrl);
    }

    @When("I enter mobile number and otp")
    public void i_enter_mobile_number_and_otp() {

        recruiterAppPage.enterMobileNumber("9999999999");
        recruiterAppPage.enterOtp("000000");
        recruiterAppPage.clickNext();
    }

    @When("I complete application using manual apply")
    public void i_complete_application_using_manual_apply() {

        recruiterAppPage.clickApplyWithManual();

        recruiterAppPage.fillApplicationForm(
                "Test",
                "User",
                "testuser@mail.com"
        );

        recruiterAppPage.clickSubmit();
    }
}
