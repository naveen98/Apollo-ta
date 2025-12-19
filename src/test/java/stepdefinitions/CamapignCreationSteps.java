package stepdefinitions;

import drivers.DriverManager;
import io.cucumber.java.en.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pageobjects.Appselectionpage;
import pageobjects.CampaignCreationPage;
import utils.Excelutils;
import utils.Radiobuttons;

import java.io.IOException;

public class CamapignCreationSteps {

    WebDriver driver;
    CampaignCreationPage cp;
    Radiobuttons rd;
    JavascriptExecutor js;


    @Given("i am on the campaign module page")
    public void i_am_on_the_campaign_module_page() {
        driver = DriverManager.getDriver();
        cp = new CampaignCreationPage(driver);
        rd = new Radiobuttons(driver);
        this.js = (JavascriptExecutor) driver;

    }

    @When("i navigate to campaign module")
    public void i_navigate_to_campaign_module() {
        cp.navigatemenu();
        cp.navigatecampignmodule();
    }

    @When("i create new campaign from excel sheet")
    public void i_create_new_campaign_from_excel_sheet() throws IOException {

        String path = "D:\\selenium-intellij\\src\\test\\resources\\campaigncreation.xlsx";
        String sheetname = "create";

        String[][] data = Excelutils.getcelldatas(path, sheetname);

        for (int i = 0; i < 2; i++) {

            String camname = data[i][0];
            String campcode = data[i][1];
            String Timboundcampaigns = data[i][2];

            String startmonth = data[i][3];
            String startyear = data[i][4];
            String startdate = data[i][5];
            String endmonth = data[i][6];
            String endyear = data[i][7];
            String enddate = data[i][8];

            String medium = data[i][9];
            String locationtype = data[i][10];
            String venueAddr = data[i][11];
            String contactinfo = data[i][12];
            String sourcetype = data[i][13];
            String notes = data[i][14];

            System.out.println("-- Creating Campaign --");

            // Open campaign form
            cp.clickcampaignbtn();

            // Fill basic details
            cp.createcampaign(camname, campcode);

            // Time Bound Campaign selection
            cp.selectRadioButtonOption("Time Bound Campaign", Timboundcampaigns);

            if (Timboundcampaigns.equalsIgnoreCase("Yes") && cp.iscalenderfieldsvisible()) {
                cp.startdate(startmonth, startyear, startdate);
                cp.enddate(endmonth, endyear, enddate);
            }

            // Contact info
            cp.selectRadioButtonOption("Share Venue Contact Info", contactinfo);

            // Medium  Online / Offline
            // Medium = Online / Offline
            cp.selectRadioButtonOption("Medium", medium);

            if (medium.equalsIgnoreCase("Offline")) {

                if (locationtype.equalsIgnoreCase("venue")) {

                    // Wait until search input field becomes visible
                    cp.waitForVenueInputVisible();

                    // Enter venue address and select suggestion
                    cp.selectVenue(venueAddr);
                    js.executeScript("window.scrollBy(0, 300);");


                    System.out.println("Entered Venue Address: " + venueAddr);

                } else if (locationtype.equalsIgnoreCase("mylocation")) {

                    js.executeScript("window.scrollBy(0, 300);");

                    cp.useMyLocation();
                     System.out.println("Selected My Location");


                 }

            }

            // NEXT Source Type
                 cp.clickNext();
                   cp.selectfromsourcetype(sourcetype);

            // NEXT  Notes
             cp.addtargetnextbtn();
             cp.NoteDescription(notes);

            // Save campaign
            cp.clickSaveAndContinue();

            // Validate toast message
            String ToastMessage = cp.gettoastmessage();

            if (ToastMessage.toLowerCase().contains("saved")
                    || ToastMessage.toLowerCase().contains("successfully")) {

                System.out.println("Campaign created successfully.");
                Assert.assertTrue(true);
                continue;
            } else {
                System.out.println("Campaign creation failed: " + ToastMessage);
                cp.clickcloseform();
            }


        }

    }

}
