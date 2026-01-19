package stepdefinitions;

import drivers.DriverManager;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pageobjects.CamapaignRecruiterApplicationPage;
import utils.Excelutils;
import utils.ExtentTestManager;
import utils.UrlAssertionUtils;

public class CamapaignRecruiterApplicationNegativeSteps {

    WebDriver driver;
    CamapaignRecruiterApplicationPage page;
    String copiedUrl;

    // ================= GIVEN =================
    @Given("I am on campaign recruiter page for checking Invalid data")
    public void I_am_on_campaign_recruiter_page_for_checking_Invalid_data() {

        driver = DriverManager.getDriver();
        page = new CamapaignRecruiterApplicationPage(driver);

        Assert.assertNotNull(driver, "Driver is NULL");

        page.navigatemenu();
        page.navigatecampignmodule();

        UrlAssertionUtils.validateUrl(driver,"https://apollota.v37.dev.zeroco.de/ta/campaign/campaign");

        ExtentTestManager.logPass("Navigated to Campaign Recruiter Page");
    }

    // ================= WHEN =================
    @When("I open recruiters application url in new tab")
    public void I_open_recruiters_application_url_in_new_tab() {

        page.waitforseachvisible();
        page.searchcampaigns("Apollolabs");
        page.selectcampaignrow();
        page.clickrecruitertab();
        page.searchcrecruiter("arya");

        page.clickRecruiter();
        page.clickCopyUrl();
        page.clickOkOnAlert();
        page.closePopup();

        copiedUrl = page.getCopiedUrlFromClipboard();
        Assert.assertNotNull(copiedUrl);

        page.openUrlInNewTab(copiedUrl);


    }

    // ================= AND =================
    @Then("I should see validation errors after submitting with invalid data")
    public void I_should_see_validation_errors_after_submitting_with_invalid_data() throws Exception {

        String path = "D:\\selenium-intellij\\src\\test\\resources\\applicationform.xlsx";
        String sheet = "invalid";

        String[][] data = Excelutils.getcelldatas(path, sheet);

        for (int i = 0; i < data.length; i++) {

            // ================= STORE DATA =================
            String mobile = data[i][0];
            String otp = data[i][1];
            String name = data[i][2];
            String email = data[i][3];
            String dobMonth = data[i][4];
            String dobYear = data[i][5];
            String dobDate = data[i][6];
            String gender = data[i][7];
            String jobRole = data[i][8];
            String graduation = data[i][9];
            String college = data[i][10];
            String university = data[i][11];
            String skills = data[i][12];
            String location = data[i][13];
            String startMonth = data[i][14];
            String startYear = data[i][15];
            String endMonth = data[i][16];
            String endYear = data[i][17];
            String checkbox = data[i][18];
            String totalExp = data[i][19];
            String retailExp = data[i][20];
            String state = data[i][21];
            String councilNo = data[i][22];
            String filePath = data[i][23].replaceAll("^\"|\"$", "");
            String expectedMsg = data[i][24];

            // ================= OTP FLOW =================
            page.enterMobileNumber(mobile);
            page.clicksendotpbutton();

            page.enterOtp(otp);
            page.clicksendotpbutton();

            if (page.isOtpValidationMessagePresent()) {
                String otpMessage = page.getOtpValidationMessageText();

                ExtentTestManager.getTest().info(" Invalid OTP: " + otpMessage);
                driver.navigate().refresh();
                continue;
            }



            if (page.isDashboardLoaded()) {
                ExtentTestManager.getTest().info(" Navigated to dashboard");
                page.clicklogoutfordashboards();
                continue;
            }

            // ================= APPLICATION FORM =================
            ExtentTestManager.getTest().info(" Filling application form");

            page.clickviawebeapplication();
            page.clickApplyWithManual();

            page.DateofBirth(dobMonth, dobYear, dobDate);
            page.fillApplicationForm(name, email, gender, jobRole,
                    graduation, college, university);

            page.selectgraduationstart(startMonth, startYear);
            page.selectgraduationend(endMonth, endYear);

            page.handleJobRoleCondition(jobRole, totalExp, retailExp);
            page.handleGraduationCondition(graduation, state, councilNo);

            //   if (filePath != null && !filePath.trim().isEmpty()) {
           //      page.addfile(filePath);
           //   }



            page.selectlocation(location);
            page.Setskills(skills);
            page.selectCheckbox(checkbox);

            // ================= SUBMIT =================
            page.clickSubmit();
            String formMessage = page.getvalidationmessage();

            ExtentTestManager.logPass(" Validation Message: " + formMessage);

            try {
                Assert.assertTrue(formMessage != null && formMessage.toLowerCase().contains(expectedMsg.toLowerCase()), "Expected: " + expectedMsg + " | Actual: " + formMessage);

                ExtentTestManager.getTest().pass("  Validation successful");

                 page.clicklogoutfordashboard();
                 ExtentTestManager.getTest().info(" Logged out successfully");

            } catch (AssertionError e) {

                ExtentTestManager.getTest().fail("  Validation FAILED " + "Expected: " + expectedMsg  + "Actual: " + formMessage);

            }
            page.clicklogoutfordashboard();

        }
    }
}
