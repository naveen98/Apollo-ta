package stepdefinitions;

import drivers.DriverManager;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pageobjects.JobsCreationPage;
import utils.Excelutils;
import utils.Radiobuttons;

import java.io.IOException;

public class JobCreationSteps {

    WebDriver driver;
    JobsCreationPage jb;
    Radiobuttons rd;

    @Given("i navigate to job module page")
    public void i_navigate_to_job_module_page() {
        driver = DriverManager.getDriver();
        jb = new JobsCreationPage(driver);
        jb.navigatetojobs();
    }

    @When("i create job from excel sheet")
    public void i_create_job_from_excel_sheet() throws IOException {

        String path = "C:\\Users\\navee\\IdeaProjects\\Apollo-ta\\src\\test\\resources\\jobcreation.xlsx";
        String sheetname = "job";

        String data[][] = Excelutils.getcelldatas(path, sheetname);

        for (int i = 0; i < data.length; i++) {

            System.out.println(" starting : ");

            try {

                String title = data[i][0];
                String code = data[i][1];
                String jobrole = data[i][2];
                String description = data[i][3];
                String rolesandresposibilities = data[i][4];
                String reqinput = data[i][5];
                String reqexp = data[i][6];
                String qualification = data[i][7];
                String experience = data[i][8];
                String other = data[i][9];
                String coolingperiod = data[i][10];

                // job creation steps
                jb.clickaddjob();
                jb.addjob(title, code, jobrole);
                jb.enterDescription(description);
                jb.enterRolesAndResponsibilities(rolesandresposibilities);
                jb.clicknext();
                jb.requirement(reqinput, reqexp, qualification, experience, other);
                jb.clickreqnextbtn();
                jb.coolingperiodselection(coolingperiod);
                jb.savebutton();

                // Toast message
                String message = jb.gettoastmessage();
                boolean success = message.toLowerCase().contains("saved") || message.toLowerCase().contains("successfully");

                if (success) {
                    System.out.println( " created successfully: " + message);
                } else {
                    // jb.clickcloseform();
                    System.out.println(" failed  " + message);
                }

            } catch (Exception e) {
                System.out.println("Exception  " + ": " + e.getMessage());

                try {
                    jb.clickcloseform();
                } catch (Exception Ex) {
                    System.out.println(" failure: " + Ex.getMessage());
                }


            }

        }


    }
}
