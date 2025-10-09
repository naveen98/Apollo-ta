package stepdefinitions;

import drivers.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import pageobjects.RecruitmentSourcingPage;
import utils.Excelutils;

import java.io.IOException;

public class RecruitmentSourcingSteps {
    WebDriver driver;
    RecruitmentSourcingPage rp;

    @Given("i navigate to sourcing module under recruitment scoring")
    public void navigate_to_sourcing_module_under_recruitment_scoring() {
        driver = DriverManager.getDriver();
        rp = new RecruitmentSourcingPage(driver);
        rp.navigatetorecruitmentsourcing();
    }

    @Then("i add scoring from excel sheet")
    public void i_add_scoring_from_excel_sheet() throws IOException {
        String path = "C:\\Users\\navee\\IdeaProjects\\Apollo-ta\\src\\test\\resources\\recruitmentsourcing.xlsx";
        String sheetname = "sourcing";

        String[][] data = Excelutils.getcelldatas(path, sheetname);

        for (int i = 0; i < data.length; i++) {
            String sourcetype = data[i][0];
            String singlesoucingeffort = data[i][1];
            String singlehiringeffort = data[i][2];
            String multisourcineffort = data[i][3];
            String multihiringeffort = data[i][4];
            String partsourcingeffort = data[i][5];
            String parthiringeffort = data[i][6];

            rp.addsourcingbutton();

            rp.createsourcingconfiguration(sourcetype, singlesoucingeffort, singlehiringeffort, multisourcineffort, multihiringeffort, partsourcingeffort, parthiringeffort);

            // save button
            rp.clicksave();

            //validation message
            String message = rp.getvalidationmessage();

            boolean success = message.toLowerCase().contains("saved") || message.toLowerCase().contains("successfully");

            if (success) {
                System.out.println("Successfully saved: " + message);

            } else {
                System.out.println("Failed to save: " + message);
            }
        }
    }
}
