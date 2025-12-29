package stepdefinitions;

import drivers.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pageobjects.RecruiterCamapignModulePage;
import utils.Excelutils;

import java.io.IOException;
import java.util.List;

public class RecruiterCampaignModuleSteps {

    WebDriver driver;
    RecruiterCamapignModulePage recruiterCampaignPage;
    List<String[]> campaignTableData;

    @Given("I Navigate To Campaign Module")
    public void i_navigate_to_campaign_module() {

        driver=DriverManager.getDriver();
        recruiterCampaignPage = new RecruiterCamapignModulePage(driver);

        recruiterCampaignPage.navigatemenu();

    }

    @When("I Captures the campaigns Table data")
    public void i_captures_the_campaigns_table_data() throws IOException {

        recruiterCampaignPage.waitforcampainstext();
        campaignTableData = recruiterCampaignPage.getCampaignTableData();

        String[] headers = {
                "name", "medium", "shareVenue",
                "startDate", "endDate", "status", "createdBy"
        };

        String path = "D:\\selenium-intellij\\src\\test\\resources\\RecruiterDetails.xlsx";
        String sheetname = "camapigns";

        Excelutils.writeTable(path, sheetname, headers, campaignTableData);

        for (String[] row : campaignTableData) {
            System.out.println(
                    "name: " + row[0] +
                            " | medium: " + row[1] +
                            " | shareVenue: " + row[2] +
                            " | startDate: " + row[3] +
                            " | endDate: " + row[4] +
                            " | status: " + row[5] +
                            " | createdBy: " + row[6]);
        }

    }



    @Then("I should click on logout")
    public void i_should_click_on_logout() {

        recruiterCampaignPage.logoutfromapplication();
    }


}
