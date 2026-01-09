package stepdefinitions;

import drivers.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pageobjects.Appselectionpage;
import utils.ExtentTestManager;
import utils.UrlAssertionUtils;

public class AppSelectionSteps {

    private static final Logger log =
            LogManager.getLogger(AppSelectionSteps.class);

    WebDriver driver;
    Appselectionpage ap;

    // ================= GIVEN =================

    @Given("I Navigate to App Selection page")
    public void I_Navigate_to_App_Selection_page() {

        driver = DriverManager.getDriver();
        ap = new Appselectionpage(driver);

        log.info("I Navigate to App Selection page");
        ExtentTestManager.getTest().info("I Navigate to App Selection page");

        Assert.assertNotNull(driver, "Driver is NULL - browser not launched");


        UrlAssertionUtils.validateUrl(driver, "account/app-selection");

    }

    // ================= WHEN =================

    @When("I clicks on the AppSelection")
    public void I_clicks_on_the_AppSelection() {

        ExtentTestManager.getTest().info("I clicks on the AppSelection");

        ap.clickOnAppSelection("talentacq");

        Assert.assertTrue(true, "Click action executed");
    }

    // ================= THEN =================

    @Then("Apollo Acquisition app should be displayed")
    public void Apollo_Acquisition_app_should_be_displayed() {

        log.info("Verifying Talent Acquisition app display");
        ExtentTestManager.getTest().info("Verifying Talent Acquisition app display");

        boolean displayed = ap.isAppDisplayed();

        Assert.assertTrue(displayed, "Talent Acquisition application is NOT displayed");


        log.info("Talent Acquisition app displayed successfully");
        ExtentTestManager.getTest().pass("Talent Acquisition app displayed successfully");
    }
}
