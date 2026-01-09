package stepdefinitions;

import drivers.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pageobjects.RecruiterDashboardPage;
import pageobjects.TALoginpage;
import utils.ExtentTestManager;
import utils.RecruiterConfigReader;

public class RecruiterLoginSteps {

    WebDriver driver;
    TALoginpage loginpage;

    // ================= GIVEN =================

    @Given("I login with recruiter credentials")
    public void i_login_with_recruiter_credentials() {

        driver = DriverManager.getDriver();
        Assert.assertNotNull(driver, "WebDriver is NULL");

        loginpage = new TALoginpage(driver);

        loginpage.logindata(RecruiterConfigReader.getProperty("recruiterusername"), RecruiterConfigReader.getProperty("recruiterpassword"));

        ExtentTestManager.getTest().info("Recruiter login completed");
    }

    // ================= WHEN =================

    @When("I verify recruiter dashboard page")
    public void i_verify_recruiter_dashboard_page() {

        RecruiterDashboardPage dashboard =
                new RecruiterDashboardPage(driver);

        // ---------- DASHBOARD ASSERTION ----------
        Assert.assertTrue(
                dashboard.isRecruiterDashboardDisplayed(),
                "Recruiter Dashboard is NOT displayed"
        );

        ExtentTestManager.getTest()
                .pass("Recruiter Dashboard verified successfully");
    }
}
