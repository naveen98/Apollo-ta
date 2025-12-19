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


   @Given ("Iam navigate to Venu module from Menubar")
    public void Iam_navigate_to_Venu_module_from_Menubar(){
        driver= DriverManager.getDriver();
        vv=new VenuTableDataFetchPage(driver);
        vv.navigatetovenumodule();
    }

     @When("I verify the created Venu details")
        public void I_verify_the_created_Venu_details() {
         vv.SeachVenudetails("naveen");

     }

    @Then("I capture the Venu data into excel file")
    public void I_capture_the_Venu_data_into_excel_file() throws IOException {


        List<String[]> venudata = vv.getVenueTableData();

        String[] headers = {
                "name", "ContactPerson", "ContactMobileNumber", "Venue", "AvailabilityIn"};

        String path = "D:\\selenium-intellij\\src\\test\\resources\\configurevenu.xlsx";
        String shname = "table";
        Excelutils.writeTable(path, shname, headers, venudata);
        driver.close();


    }






}
