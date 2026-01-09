package stepdefinitions;

import drivers.DriverManager;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pageobjects.ApplicationsDetailsFromRecruiterLoginPage;
import pageobjects.CamapaignRecruiterApplicationPage;
import pageobjects.TALoginpage;
import utils.Excelutils;
import utils.ExtentTestManager;
import utils.RecruiterConfigReader;
import utils.UrlAssertionUtils;

import java.io.IOException;
import java.util.List;

public class RecruiterApplicationsDetails {


    WebDriver driver;
    ApplicationsDetailsFromRecruiterLoginPage ap;

    @Given("I Navigate to applications module")
    public void i_navigate_to_applications_module() {

        driver=DriverManager.getDriver();
        ap=new ApplicationsDetailsFromRecruiterLoginPage(driver);

        ap.navigateApplicationModule();


        boolean displayed = ap.isnavigatedtxt();

        Assert.assertTrue(displayed, "Application module text  is NOT displayed");


        ExtentTestManager.getTest().info("Navigated to Applications module");

    }
    @Then("I verify the candidate application and capture the status")
    public void i_verify_the_candidate_application_and_capture_the_status() throws IOException {


        String  applicantno="APP0126-100000020";

        ap.Searchapplicants(applicantno);

        Assert.assertTrue(ap.isnamedisplayed());

        List<String[]> ApplicationData = ap.getApplicationTableData();

        String[] headers = {"ApplicationNo", "Applicant", "JobRole", "Location", "Region", "Recruiter","Submitted","Active Since","Progress"};

        String path = "D:\\selenium-intellij\\src\\test\\resources\\applicationdetails.xlsx";

        Excelutils.writeTable(path, "applicationdata", headers, ApplicationData);


    }


}
