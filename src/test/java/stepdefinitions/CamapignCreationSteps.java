package stepdefinitions;

import drivers.DriverManager;
import io.cucumber.java.en.*;
import org.bouncycastle.jcajce.provider.symmetric.SCRYPT;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pageobjects.Appselectionpage;
import pageobjects.CampaignCreationPage;
import utils.Excelutils;
import utils.Radiobuttons;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CamapignCreationSteps {
    WebDriver driver;
    CampaignCreationPage cp;

    Radiobuttons rd;

    public CamapignCreationSteps() throws IOException {
    }

    @Given("i am on the campagn module page")
    public void i_am_on_the_campagn_module_page() {

        driver = DriverManager.getDriver();
        cp = new CampaignCreationPage(driver);
        rd = new Radiobuttons(driver);

    }

    @When("i navigate to campaign module")
    public void i_navigate_to_campaign_module() {
        cp.navigatemenu();
        // boolean displayed=cp.istextdisplayed();
        // Assert.assertTrue(displayed, "campaign module not displayed");

        cp.navigatecampignmodule();
    }

    @When("i create new campaign from excel sheet")
    public void i_create_new_campaign_from_excel_sheet() throws IOException {
        String path = "E:\\projects\\raj-projects\\TalentAcquisition\\src\\test\\resources\\campaigncreation.xlsx";
        String sheetname = "create";

        String[][] data = Excelutils.getcelldatas(path, sheetname);

        for (int i = 0; i < data.length; i++) {
            String camname = data[i][0];
            String campcode = data[i][1];
            String startmonth = data[i][2];
            String startyear = data[i][3];
            String startdate = data[i][4];
            String endmonth = data[i][5];
            String endyear = data[i][6];
            String enddate = data[i][7];
            String state = data[i][8];
            String region = data[i][9];
            String city = data[i][10];
            String area = data[i][11];

            String medium = data[i][12];   // "Offline" or "Online"
            String venueAddr = data[i][13];   // only used when Offline

            //    String mode=data[i][14];


            cp.clickcampaignbtn();
            cp.createcampaign(camname, campcode, state, region, city, area);
            cp.startdate(startmonth, startyear, startdate);
            cp.enddate(endmonth, endyear, enddate);

            // Select Medium and handle venue
            cp.handleMedium(medium, venueAddr);
            cp.clicknext();
        }


        //Taget section
        String tarpath = "E:\\projects\\raj-projects\\TalentAcquisition\\src\\test\\resources\\campaigncreation.xlsx";
        String tarsheetname = "target";

        String tardata[][] = Excelutils.getcelldatas(tarpath, tarsheetname);

        for (int k = 0; k < tardata.length; k++) {
            String jbrole = tardata[k][0];
            String noofhires = tardata[k][1];
            String desc = tardata[k][2];

            cp.addmorebtn();
            cp.targetsection(jbrole, noofhires);
            cp.addtargetnextbtn();
            cp.NoteDescription(desc);
        }

            String ToastMessage=cp.gettoastmessage();

            if (ToastMessage.toLowerCase().contains("saved") || ToastMessage.toLowerCase().contains("successfully")) {
                System.out.println(" Campaign created successfully: ");
                Assert.assertTrue(true);

            } else {
                System.out.println(" User creation failed: " + ToastMessage);
                Assert.fail();
                cp.clickcloseform();



            }


    }
}
