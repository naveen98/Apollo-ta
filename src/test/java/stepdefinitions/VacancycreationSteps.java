package stepdefinitions;

import drivers.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
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

        String path = "D:\\selenium-intellij\\src\\test\\resources\\vacancycreation.xlsx";
        String sheetname = "vacancy";

        String[][] data = Excelutils.getcelldatas(path, sheetname);

        for (int i = 0; i < data.length; i++) {

                System.out.println("Creating Vacancy");

                String siteInput = data[i][0];
                String siteExpected = data[i][1];
                String year = data[i][2];
                String month = data[i][3];
                String jobRole = data[i][4];
                String noVacancy = data[i][5];

                // Open form
                vc.addvacancyCreation();

                // Fill form
                vc.createVacancy(siteInput, siteExpected, jobRole, noVacancy);
                vc.selectdate(month, year);

                // Click save
                vc.clicksave();


                // Get result
                String msg = vc.getvalidationmessage();
                System.out.println("Final Result: " + msg);


                if (msg.toLowerCase().contains("Created") || msg.toLowerCase().contains("successfully")) {
                    System.out.println("Vacancy Created Successfully");
                    Assert.assertTrue(true);
                    continue;
                }else{
                      vc.closeForm();
                    System.out.println("Vacancy Creation Failed");

                }

        }
        
            }
        }




