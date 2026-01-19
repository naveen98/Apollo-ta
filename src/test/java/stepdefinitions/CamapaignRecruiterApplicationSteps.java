package stepdefinitions;

import drivers.DriverManager;
import io.cucumber.java.en.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import pageobjects.CamapaignRecruiterApplicationPage;
import utils.Excelutils;
import utils.ExtentTestManager;
import utils.UrlAssertionUtils;

import java.io.IOException;

public class CamapaignRecruiterApplicationSteps {

    private static final Logger log =
            LogManager.getLogger(CamapaignRecruiterApplicationSteps.class);

    WebDriver driver;
    CamapaignRecruiterApplicationPage recruiterAppPage;
    String copiedUrl;

    // ================= GIVEN =================

    @Given("I am on campaign recruiter page")
    public void i_am_on_campaign_recruiter_page() {

        driver = DriverManager.getDriver();
        recruiterAppPage = new CamapaignRecruiterApplicationPage(driver);

        Assert.assertNotNull(driver, "Driver is NULL");

        recruiterAppPage.navigatemenu();
        recruiterAppPage.navigatecampignmodule();
        UrlAssertionUtils.validateUrl(driver,"https://apollota.v37.dev.zeroco.de/ta/campaign/campaign");

        ExtentTestManager.logPass("Navigated to Campaign Recruiter Page");
    }

    // ================= WHEN =================

    @When("I click recruiter copy url and open in new tab")
    public void i_click_recruiter_copy_url_and_open_in_new_tab() {

        recruiterAppPage.waitforseachvisible();
        recruiterAppPage.searchcampaigns("Apollolabs");

        recruiterAppPage.selectcampaignrow();
        recruiterAppPage.clickrecruitertab();
        recruiterAppPage.searchcrecruiter("arya");


        recruiterAppPage.clickRecruiter();
        recruiterAppPage.clickCopyUrl();
        recruiterAppPage.clickOkOnAlert();
        recruiterAppPage.closePopup();

        copiedUrl = recruiterAppPage.getCopiedUrlFromClipboard();

        Assert.assertNotNull(copiedUrl, "Copied URL is NULL");

        recruiterAppPage.openUrlInNewTab(copiedUrl);

        ExtentTestManager.logPass("Recruiter URL opened in new tab");
    }

    // ================= AND =================

    @And("I enter mobile number and otp")
    public void i_enter_mobile_number_and_otp() {

        recruiterAppPage.enterMobileNumber("9000008752");
        recruiterAppPage.clicksendotpbutton();

        recruiterAppPage.enterOtp("000000");
        recruiterAppPage.clicksendotpbutton();

        String otpverify = recruiterAppPage.getvalidationmessage();

        Assert.assertTrue(otpverify.toLowerCase().contains("otp") || otpverify.toLowerCase().contains("successfully"), "OTP verification failed. Toast message: " + otpverify);

        ExtentTestManager.logPass("OTP verification successful");

        // ================= CONDITIONAL STOP =================
        if (recruiterAppPage.isDashboardLoaded()) {

            ExtentTestManager.getTest().info("Application Dashboard displayed. Skipping application submission.");

            recruiterAppPage.clicklogoutfordashboard();

            throw new SkipException("Dashboard already exists for this recruiter. Application submission skipped.");
        }
    }
   // ================= THEN =================

    @Then("I click submit application")
    public void i_click_submit_application() throws IOException {

        recruiterAppPage.clickviawebeapplication();
        recruiterAppPage.clickApplyWithManual();

        String path = "D:\\selenium-intellij\\src\\test\\resources\\applicationform.xlsx";
        String sheet = "application";

        String[][] data = Excelutils.getcelldatas(path, sheet);

        Assert.assertNotNull(data, "Excel data is NULL");

        ExtentTestManager.getTest().info("Excel data loaded successfully");

        for (String[] row : data) {

            // ---------- READ DATA ----------
            String name = row[0];
            String email = row[1];
            String dobMonth = row[2];
            String dobYear = row[3];
            String dobDate = row[4];
            String gender = row[5];
            String jobRole = row[6];
            String graduation = row[7];
            String college = row[8];
            String university = row[9];
            String skills = row[10];
            String location = row[11];
            String startMonth = row[12];
            String startYear = row[13];
            String endMonth = row[14];
            String endYear = row[15];
            String checkbox = row[16];
            String totalExp = row[17];
            String retailExp = row[18];
            String dpharmState = row[19];
            String councilNo = row[20];
            String filePath = row[21].replaceAll("^\"|\"$", "");

            // ---------- FORM ----------
            recruiterAppPage.DateofBirth(dobMonth, dobYear, dobDate);

            recruiterAppPage.fillApplicationForm(name, email, gender, jobRole, graduation, college, university);

            recruiterAppPage.selectgraduationstart(startMonth, startYear);
            recruiterAppPage.selectgraduationend(endMonth, endYear);

            recruiterAppPage.handleJobRoleCondition(jobRole, totalExp, retailExp);
            recruiterAppPage.handleGraduationCondition(graduation, dpharmState, councilNo);

         //   recruiterAppPage.addfile(filePath);
            recruiterAppPage.selectlocation(location);
            recruiterAppPage.Setskills(skills);
            recruiterAppPage.selectCheckbox(checkbox);
            recruiterAppPage.clickSubmit();

            String status= recruiterAppPage.getvalidationmessage();
            log.info("Toast message : " + status);
            ExtentTestManager.getTest().info("Toast message : " + status);

            Assert.assertTrue(status != null, "Toast message is NULL");

            Assert.assertTrue(status.toLowerCase().contains("Application") || status.toLowerCase().contains("successfully"), "application failed. Toast message: " + status);

            ExtentTestManager.logPass("Application submitted successfully : ");



        }

    }


}
