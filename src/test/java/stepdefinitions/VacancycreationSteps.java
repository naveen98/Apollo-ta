package stepdefinitions;

import drivers.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import pageobjects.VacancycreationPage;
import utils.Excelutils;

import java.io.IOException;

public class VacancycreationSteps {

    WebDriver driver;
    VacancycreationPage vc;

    @Given("i navigated to vacancy module")
    public void i_navigated_to_vacancy_module() {
        driver = DriverManager.getDriver();
        vc = new VacancycreationPage(driver);
        vc.navigatevacancymodule();
    }

    @Then("i create a vacancy from excel sheet")
    public void i_create_a_vacancy_from_excel_sheet() throws IOException {
        String path = "C:\\Users\\navee\\IdeaProjects\\Apollo-ta\\src\\test\\resources\\vacancycreation.xlsx";
        String sheetname = "vacancy";


        String[][] data = Excelutils.getcelldatas(path, sheetname);

        for (int i = 0; i < data.length; i++) {
            System.out.println(" starting Execution ");

            try {
                String siteInput = data[i][0];
                String siteExpected = data[i][1];
                String year = data[i][2];
                String month = data[i][3];
                String jobRoleInput = data[i][4];
                String noOfVacancy = data[i][5];
                String noOfClosedVacancy = data[i][6];

                vc.addvacancy();
                vc.createvacancy(siteInput, siteExpected, jobRoleInput, noOfVacancy, noOfClosedVacancy);
                vc.selectdate(month, year);
                vc.clicksave();

                String message = vc.getToastMessage();
                boolean success = message.toLowerCase().contains("saved") || message.toLowerCase().contains("successfully");

                if (success) {
                    System.out.println(" Vacancy Created: " + message);
                } else {
                    vc.cancelform();
                    System.out.println(" Vacancy Creation Failed: " + message);
                }

            } catch (Exception e) {
                System.out.println("Exception  : " + e.getMessage());
                try {
                    vc.cancelform();
                } catch (Exception ex) {
                    System.out.println(" Error closing form: " + ex.getMessage());
                }
            }
        }
    }
}
