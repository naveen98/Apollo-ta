package stepdefinitions;

import drivers.DriverManager;
import hooks.TalentAcqBaseclass;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pageobjects.TALoginpage;
import utils.ExtentTestManager;
import utils.UrlAssertionUtils;

public class LoginSteps {

    private static final Logger log =
            LogManager.getLogger(LoginSteps.class);

    private WebDriver driver;
    private TALoginpage lp;

    public LoginSteps() {
        this.driver = DriverManager.getDriver();
        this.lp = new TALoginpage(driver);
    }

    // ================= GIVEN =================

    @Given("I am on the TalentAcquisition login page")
    public void i_am_on_the_TalentAcquisition_login_page() {

        log.info("User is on TalentAcquisition login page");
        ExtentTestManager.getTest().info("User is on TalentAcquisition login page");

        Assert.assertNotNull(driver, "Driver is NULL - browser not launched");

        //  simple URL check (optional but useful)
        UrlAssertionUtils.validateUrl(driver, "https://apollota.v37.dev.zeroco.de/");

    }

    // ================= WHEN =================

    @When("I login using valid credentials")
    public void i_login_using_valid_credentials() {

        log.info("Logging in using valid credentials");
        ExtentTestManager.getTest().info("Logging in using valid credentials");

        lp.logindata(TalentAcqBaseclass.username, TalentAcqBaseclass.password);
    }

    // ================= THEN =================

    @Then("I should see the TalentAcquisition page")
    public void i_should_see_the_TalentAcquisition_page() {

        log.info("Verifying TalentAcquisition home page display");
        ExtentTestManager.getTest().info("Verifying TalentAcquisition home page display");

        boolean isDisplayed = lp.TalentAcquisitionIsdisplayed();

        Assert.assertTrue(isDisplayed, "TalentAcquisition is NOT displayed after login");

        //  simple URL validation after login
        UrlAssertionUtils.validateUrl(driver, "/app-selection");

        log.info("TalentAcquisition displayed successfully");
        ExtentTestManager.getTest()
                .pass("TalentAcquisition displayed successfully");
    }
}
