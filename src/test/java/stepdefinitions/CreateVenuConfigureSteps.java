package stepdefinitions;

import drivers.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pageobjects.CreateVenuPage;
import utils.Excelutils;

import java.io.IOException;

public class CreateVenuConfigureSteps {

    WebDriver driver;
    CreateVenuPage venupage;

    private static final String path = "D:\\selenium-intellij\\src\\test\\resources\\configurevenu.xlsx";
    private static final String sheetname = "venu";


    @Given("I navigate to Venu module from Menubar")
    public void I_navigate_to_Venu_module_from_Menubar() {

        driver = DriverManager.getDriver();
        venupage = new CreateVenuPage(driver);
        venupage.navigatetovenumodule();


    }

    @When("I create new Venu configuration from Excel test data")
    public void I_create_new_Venu_configuration_from_Excel_test_data() throws IOException {
        String[][] data = Excelutils.getcelldatas(path, sheetname);

        for (int i = 0; i < 1; i++) {
            try {
                String name         = data[i][0].trim();
                String availability = data[i][1].trim();
                String state        = data[i][2].trim();
                String region       = data[i][3].trim();
                String city         = data[i][4].trim();
                String area         = data[i][5].trim();
                String location     = data[i][6].trim();
                String ContactPerson = data[i][7].trim();
                String ContactNumber = data[i][8].trim();
                String notes        = data[i][9].trim();
                String locationtype  = data[i][10].trim();



                venupage.ClickCreateConfigure();
                if (locationtype.equalsIgnoreCase("select"))
                {
                    venupage.createvenu(name, location);

                } else if (locationtype.equalsIgnoreCase("currentlocation")) {

                    venupage.creatvenuename(name);
                    venupage.usemylocation();

                }


                venupage.Availabitylocations(availability,state,region,city,area);
                venupage.contactFields(ContactPerson,ContactNumber);
                venupage.notes(notes);

                venupage.clicksavebutton();

                String message = venupage.getvalidationmessage();

                boolean success = message.toLowerCase().contains("saved") || message.toLowerCase().contains("successfully");

                if (success) {
                    System.out.println("Successfully saved: " + message);

                } else {
                    System.out.println("Failed to save: " + message);
                }

            } catch (Exception e) {
                System.out.println("Error " + e.getMessage());

            }


        }

    }


}

