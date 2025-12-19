package stepdefinitions;

import drivers.DriverManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pageobjects.TeamMappingEditandDeletePage;
import utils.Excelutils;

import java.io.IOException;

public class TeamMappingEditAndDeleteSteps {

    WebDriver driver;
    TeamMappingEditandDeletePage ed;


    @Given("Navigate team mapping module")
    public void navigate_team_mapping_module() {
        driver= DriverManager.getDriver();
        ed=new TeamMappingEditandDeletePage(driver);
        ed.navigateToTeamMapping();

    }

    @When("i click on edit action recruiters")
    public void i_click_on_edit_action_recruiters() throws IOException {

        String path = "D:\\selenium-intellij\\src\\test\\resources\\TeammappingEditandDelete.xlsx";
        String sheetName = "edit";


        ed.clickRecruitertab();

        String[][] data = Excelutils.getcelldatas(path, sheetName);

        for (String[] row : data) {

            String username = row[0];

            // Yes / No  Excel
            String candidateAllocation = row[1];
            String candidateAutoAllocation = row[2];


            ed.searchteammappinguser(username);

            if (ed.isNoRecordFoundDisplayedFast()) {
                System.out.println("No record found for: " + username);
                continue;
            }

            ed.waitForEditOption();

            ed.clickeditoption();

            // -------------------- value check ------------------------
            String currentValue = ed.getCandidateAllocationValue();

            //  (No-> Yes Or Yes ->No)
            if (!currentValue.equalsIgnoreCase(candidateAllocation)) {

                ed.selectRadioButtonOption("Candidate Allocation", candidateAllocation);

                // If New value is Yes select auto allocation
                if (candidateAllocation.equalsIgnoreCase("Yes")) {
                    if (ed.isCandidateAutoAllocationVisible()) {
                        ed.selectRadioButtonOption("Candidate Auto Allocation", candidateAutoAllocation);
                    }
                }
            }

            // Value Already Yes -> open Auto Allocation
            else if (currentValue.equalsIgnoreCase("Yes")) {

                if (ed.isCandidateAutoAllocationVisible()) {
                    ed.selectRadioButtonOption("Candidate Auto Allocation", candidateAutoAllocation);
                }
            }


            ed.clickRecruiterupdateButton();

            String message = ed.getvalidationmessage();
            boolean success = message.toLowerCase().contains("updated") || message.toLowerCase().contains("successful");
            if (success) {
                System.out.println("Updated successfully :" + message);
            } else {
                System.out.println("Updated Failed :" + message);

            }
        }
    }


    @And("i click on delete action recruiters")
    public void i_click_on_delete_action_recruiters() throws IOException {
        String path = "D:\\selenium-intellij\\src\\test\\resources\\TeammappingEditandDelete.xlsx";
        String sheetName = "delete";
        String[][] data = Excelutils.getcelldatas(path, sheetName);

        for (String[] row : data) {

            String username = row[0];

            ed.searchteammappinguser(username);

            if (ed.isNoRecordFoundDisplayedFast()) {
                System.out.println("No record found for: " + username);
                continue;
            }
            ed.clickdeleteoption();
            ed.clickdeleteconfirmbuttonokbutton();

        }
            String message = ed.getvalidationmessage();
            boolean success = message.toLowerCase().contains("deleted") || message.toLowerCase().contains("successfully");

            if (success) {
                System.out.println("Deleted successfully :" + message);
            } else {

                System.out.println("Deleted Failed :" + message);

            }

    }

    @Then("i click on edit and delete action region hr")
    public void i_click_on_edit_and_delete_action_region_hr() {

           ed.clickregionHRmappingtab();

           String username="shivam";

           ed.searchteammappinguser(username);
              if(ed.isNoRecordFoundDisplayedFast()) {
                  System.out.println("No record found for: " + username);
                  return;

              }
                ed.clickdeleteoption();
                ed.clickdeleteconfirmbuttonokbutton();
            String message = ed.getvalidationmessage();
            boolean success = message.toLowerCase().contains("deleted") || message.toLowerCase().contains("successfully");
            if (success) {
                System.out.println("Deleted successfully :" + message);
            } else {

                System.out.println("Deleted Failed :" + message);

            }

    }


}
