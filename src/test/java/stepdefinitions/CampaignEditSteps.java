package stepdefinitions;

import drivers.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pageobjects.CampaignCreationEditPage;
import utils.Excelutils;

import java.io.IOException;

public class CampaignEditSteps {

    WebDriver driver;
    CampaignCreationEditPage cp;

    @Given("I navigate to campaign module")
    public void i_navigate_to_campaign_module() {

        driver = DriverManager.getDriver();
        cp = new CampaignCreationEditPage(driver);

        Assert.assertNotNull(driver, "Driver is NULL");
        cp.navigatemenu();
        cp.navigateCampaignModule();
    }

    @When("I verify edit action for campaign")
    public void I_verify_edit_action_for_campaign() throws IOException {

        String path = "D:\\selenium-intellij\\src\\test\\resources\\campaigncreation.xlsx";
        String sheetName = "edit";

        String campaignName = "MediKit";
        cp.searchcampaigns(campaignName);

        Assert.assertFalse(cp.isNoRecordFoundDisplayed(), "No campaign found with name: " + campaignName);

        String[][] data = Excelutils.getcelldatas(path, sheetName);
        Assert.assertNotNull(data, "Excel data is NULL");

        for (int i = 0; i < 1; i++) {

            String medium = data[i][1];
            String shareVenueInfo = data[i][2];


            cp.clickEditOption();

            cp.selectRadioButtonOption("Medium", medium);
            cp.selectRadioButtonOption("Share Venue Contact Info", shareVenueInfo);

            cp.clickNextButton();
            cp.addTargetNextBtn();
            cp.clickupdatebutton();

            String toastMessage = cp.getToastMessage();
            Assert.assertNotNull(toastMessage, "Toast message is NULL after update");

            Assert.assertTrue(toastMessage.toLowerCase().contains("updated") || toastMessage.toLowerCase().contains("success"), "Campaign update failed. Actual message: " + toastMessage);
        }
    }
}
