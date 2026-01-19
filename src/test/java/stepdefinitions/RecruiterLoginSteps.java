package stepdefinitions;

import drivers.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import pageobjects.RecruiterDashboardPage;
import pageobjects.TALoginpage;
import utils.ExtentTestManager;
import utils.RecruiterConfigReader;
import utils.UrlAssertionUtils;

public class RecruiterLoginSteps {

    private static final Logger log = LoggerFactory.getLogger(RecruiterLoginSteps.class);
    WebDriver driver;
    TALoginpage loginpage;
    RecruiterDashboardPage dashboard;

    // ================= GIVEN =================

    @Given("I login with recruiter credentials")
    public void i_login_with_recruiter_credentials() {
     dashboard = new RecruiterDashboardPage(driver);

        driver = DriverManager.getDriver();
        Assert.assertNotNull(driver, "WebDriver is NULL");

        loginpage = new TALoginpage(driver);


        loginpage.logindata(RecruiterConfigReader.getProperty("recruiterusername"), RecruiterConfigReader.getProperty("recruiterpassword"));

        // Check if Pending Actions text is displayed

        if (loginpage.ispendingactiontextdisplayed()) {
            log.info("Pending actions list is displayed");
            ExtentTestManager.logPass("Pending actions are displayed after login");

        } else {
            // No pending actions  Dashboard should be displayed

            loginpage.waitfordisplayrecruiterdashboardtext();

            UrlAssertionUtils.validateUrl(driver, "https://apollota.v37.dev.zeroco.de/ta/dashboard/dashboard");

            log.info("Recruiter dashboard is displayed");

            ExtentTestManager.logPass("Recruiter login completed successfully and dashboard is displayed");
        }
    }


        // ================= WHEN =================

    @When("I verify recruiter dashboard page")
    public void i_verify_recruiter_dashboard_page() {

           // ---------- DASHBOARD ASSERTION ----------
             UrlAssertionUtils.validateUrl(driver, "https://apollota.v37.dev.zeroco.de/ta/dashboard/dashboard");
             ExtentTestManager.logPass("Recruiter Dashboard verified successfully");
    }
}
