package stepdefinitions;

import drivers.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pageobjects.ApplicationPage;
import utils.Excelutils;

import java.io.IOException;
import java.util.List;

public class ApplicationSteps {
    WebDriver driver;
    ApplicationPage ap;


    @Given("iam navigate to Application module")
    public void iam_navigate_to_application_module() {
        driver= DriverManager.getDriver();
        ap=new ApplicationPage(driver);
        ap.navigatetoapplications();
    }

    @When("i verify the application details")
    public void i_verify_the_application_details() {
        ap.Searchapplications("hyderabad");


    }

    @Then("i capture the applicants data into excel")
    public void i_capture_the_applicants_data_into_excel() throws IOException {

        List<String[]> applicantsdata = ap.getApplicationsTableData();
        String[] headers = {
                "ApplicationNo", "Applicant", "JobRole", "Location", "Region", "Recruiter", "Progress"
        };

        String path = "D:\\selenium-intellij\\src\\test\\resources\\ApplicationTableData.xlsx";
        String shname = "applications";
        Excelutils.writeTable(path, shname, headers, applicantsdata);
        driver.close();


    }






}
