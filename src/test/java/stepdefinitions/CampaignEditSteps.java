package stepdefinitions;

import drivers.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pageobjects.CampaignCreationEditPage;
import utils.Excelutils;

import java.io.IOException;


public class CampaignEditSteps {
    WebDriver driver;
    CampaignCreationEditPage cp;

    @Given("I navigate to campaign module")
    public void i_navigate_to_campaign_module() {
        driver = DriverManager.getDriver();
        cp = new CampaignCreationEditPage(DriverManager.getDriver());
        cp.navigatemenu();
        cp.navigateCampaignModule();
    }

    @When("I verify edit action for campaign")
    public void I_verify_edit_action_for_campaign() throws IOException {


        String path="D:\\selenium-intellij\\src\\test\\resources\\campaigncreation.xlsx";
        String  Sheetname="edit";

        // ===== Read Excel Data =====

        String campaignName="MediKit";
        cp.searchcampaigns(campaignName);

        if (cp.isNoRecordFoundDisplayed()) {
            System.out.println("No record found for: ");
            return;
        }


        String [][]data = Excelutils.getcelldatas(path, Sheetname);
              for(int i=0; i < 1;i++){


            String medium=data[i][1];
            String shareVenueInfo=data[i][2];

            cp.clickEditOption();
            cp.selectRadioButtonOption("Medium", medium);

            cp.selectRadioButtonOption("Share Venue Contact Info", shareVenueInfo);
            cp.clickNextButton();
            cp.addTargetNextBtn();
            cp.clickupdatebutton();

            String message=cp.getToastMessage();
            boolean success=message.toLowerCase().contains("updated")||message.toLowerCase().contains("successfully");



            if(success){
                System.out.println("Campaign Updated Successfully:" + message);
            }
            else{

                System.out.println("Update failed :" + message);
            }

        }


    }
}
