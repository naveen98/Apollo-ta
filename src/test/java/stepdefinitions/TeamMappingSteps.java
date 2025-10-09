package stepdefinitions;

import drivers.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import pageobjects.TeamMappingPage;
import utils.Excelutils;

import java.io.IOException;

public class TeamMappingSteps {

    WebDriver driver;
    TeamMappingPage tm;

    @Given("i navigate to team mapping module")
    public void i_navigate_to_team_mapping_module() {
        driver = DriverManager.getDriver();
        tm = new TeamMappingPage(driver);
        tm.navigateToTeamMapping();
    }

    @Then("i add user team mapping")
    public void i_add_user_team_mapping() throws IOException {
        tm.clickAddTeamMappingButton();

        String path = "C:\\Users\\navee\\IdeaProjects\\Apollo-ta\\src\\test\\resources\\teammapping.xlsx";
        String sheetname = "teammapping";

        String[][] data = Excelutils.getcelldatas(path, sheetname);

        for (String[] row : data) {
            String userInput = row[0];
            String userExpected = row[1];
            String candidateAllocation = row[2];
            String candidateAutoAllocation = row[3];
            String maxCandidateAllocation = row[4];
            String state = row[5];
            String region = row[6];
            String city = row[7];
            String area = row[8];
            String site = row[9];

            tm.userAdd(userInput, userExpected);
            tm.selectRadioButtonOption("Candidate Allocation", candidateAllocation);

            if (candidateAllocation.equalsIgnoreCase("Yes")) {
                if (tm.isCandidateAutoAllocationVisible()) {
                    tm.selectRadioButtonOption("Candidate Auto Allocation", candidateAutoAllocation);
                }

                if (tm.isMaxCandidateAllocationVisible()) {
                    tm.enterMaxCandidateAllocation(maxCandidateAllocation);
                }

                tm.clickInsideAddArea();
                tm.fillLocationDetails(state, region, city, area, site);
                tm.clickAddButton();
            }
        }

        tm.clickSaveButton();
    }
}
