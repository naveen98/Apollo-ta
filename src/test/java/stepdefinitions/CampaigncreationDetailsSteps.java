package stepdefinitions;

import drivers.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pageobjects.CampaignCreationDetailsPage;
import utils.Excelutils;

import java.io.IOException;
import java.util.List;

public class CampaigncreationDetailsSteps {

    WebDriver driver;
    CampaignCreationDetailsPage cp;

    @Given("iam navigate to campaign module")
    public void iam_navigate_to_campaign_module() {
        driver = DriverManager.getDriver();
        cp = new CampaignCreationDetailsPage(driver);
        cp.navigatemenu();
        cp.navigatecampignmodule();
    }

    @When("i verify the created campaign details")
    public void i_verify_the_created_campaign_details() throws IOException {

        String path = "D:\\selenium-intellij\\src\\test\\resources\\campaigndetails.xlsx";
        String shname = "details";
        String[][] data = Excelutils.getcelldatas(path, shname);

        for (int i = 0; i < 1; i++) {

            String startMonth = data[i][0].trim();
            String startYear  = data[i][1].trim();
            String startDate  = data[i][2].trim();

            String endMonth   = data[i][3].trim();
            String endYear    = data[i][4].trim();
            String endDate    = data[i][5].trim();

            // Start Date
            cp.selectStartDate(startMonth, startYear, startDate);


        }
    }

    @Then("i capture the data into excel")
    public void i_capture_the_data_into_excel() throws IOException {

        List<String[]> campaignData = cp.getCampaignTableData();

        String[] headers = {
                "Name", "Medium", "Share Contact No",
                "Start Date", "End Date", "Status", "Created By"
        };

        String path = "D:\\selenium-intellij\\src\\test\\resources\\campaigndetails.xlsx";

        Excelutils.writeTable(path, "Campaigns", headers, campaignData);


    }
}
