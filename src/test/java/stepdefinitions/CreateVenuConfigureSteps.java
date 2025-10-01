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

    private static final String FILE_PATH = "C:\\Users\\navee\\IdeaProjects\\Apollo-ta\\src\\test\\resources\\configurevenu.xlsx";
    private static final String SHEET_NAME = "venu";


    @Given("I am on the Venu Module page")
    public void i_am_on_the_venu_module_page() {
        driver = DriverManager.getDriver();
        venupage = new CreateVenuPage(driver);

    }

    @When("I create new Venu Configuration  from Excel test data")
    public void i_create_new_venu_configuration_from_excel_test_data() throws IOException {
        String[][] data = Excelutils.getcelldatas(FILE_PATH, SHEET_NAME);


        for (int i = 0; i < data.length; i++) {
            try {
                String venuadd      = data[i][0].trim();
                String availability = data[i][1].trim();
                String state        = data[i][2].trim();
                String region       = data[i][3].trim();
                String city         = data[i][4].trim();
                String area         = data[i][5].trim();

                if (venuadd.isEmpty() && availability.isEmpty() && state.isEmpty())
                    continue;

                venupage.clickAddConfigure();
                venupage.createvenu(venuadd, availability, state, region, city, area);


            } catch (Exception e) {
                System.out.println("Error " + e.getMessage());

            }

        }

        }


    }

