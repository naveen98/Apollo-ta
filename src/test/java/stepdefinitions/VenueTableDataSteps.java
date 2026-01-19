package stepdefinitions;

import drivers.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pageobjects.VenuTableDataFetchPage;
import utils.Excelutils;

import java.io.IOException;
import java.util.List;

public class VenueTableDataSteps {
    WebDriver driver;
    VenuTableDataFetchPage vv;


   @Given ("Iam navigate to Venue module for capturing Venu details")
    public void Iam_navigate_to_Venue_module_for_capturing_Venu_details(){
        driver= DriverManager.getDriver();
        vv=new VenuTableDataFetchPage(driver);
        vv.navigatetovenumodule();
    }

     @When("I verify the created Venue details")
        public void I_verify_the_created_Venue_details() {
         vv.SeachVenudetails("naveen");

     }

    @Then("I capture the Venue data")
    public void I_capture_the_Venue_data() throws IOException {


        List<String[]> venudata = vv.getVenueTableData();

        String[] headers = {
                "name", "ContactPerson", "ContactMobileNumber", "Venue", "AvailabilityIn"};

        String path = "D:\\selenium-intellij\\src\\test\\resources\\configurevenu.xlsx";
        String shname = "table";
        Excelutils.writeTable(path, shname, headers, venudata);

    }


}
