package stepdefinitions;

import drivers.DriverManager;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import pageobjects.TeamMappingPage;
import utils.Excelutils;

import java.io.IOException;

public class TeamMappingSteps {

    WebDriver driver;
    TeamMappingPage tm;


    @Given("i navigates to team mapping module")
    public void i_navigates_to_team_mapping_module() {

        driver = DriverManager.getDriver();
        tm = new TeamMappingPage(driver);
        tm.navigateToTeamMapping();

    }

    // -------------------------------------------------------------------------
    @When("i add user team mapping and sites to recruiters")
    public void i_add_user_team_mapping_and_sites_to_recruiters() throws Exception {

        String path = "D:\\selenium-intellij\\src\\test\\resources\\teammapping.xlsx";

        // ----------------- USER TEAM MAPPING -----------------
        String sheetname = "searchrecruiter";
        String[][] userData = Excelutils.getcelldatas(path, sheetname);
        tm.navigateToTeamMapping();
     //   tm.clickRecruitertab();
        tm.clickAddRecruiterTeamMappingButton();

        for (String[] row : userData) {

            String userInput = row[0];
            String userExpected = row[1];
            String candidateAllocation = row[2];
            String candidateAutoAllocation = row[3];

            tm.userAdd(userInput, userExpected);
            tm.selectRadioButtonOption("Candidate Allocation", candidateAllocation);

            if (candidateAllocation.equalsIgnoreCase("Yes")
                    && tm.isCandidateAutoAllocationVisible()) {

                tm.selectRadioButtonOption("Candidate Auto Allocation", candidateAutoAllocation);
            }

            tm.clickRecruiterSaveButton();

            if (tm.isCloseDisplayed()) {
                tm.closeSettingsForm();
            }
        }

        // ----------------- SITE MAPPING TO RECRUITER -----------------
        String sh = "sites";
        String[][] siteData = Excelutils.getcelldatas(path, sh);

        for (String[] row : siteData) {

            String siteInput = row[0];
            String siteExpected = row[1];
            String username = row[2];

            tm.searchTeamMappingUser(username);

            if (tm.isNoRecordFoundDisplayedFast()) {
                System.out.println("User not found for site mapping: " + username);
                continue;
            }

            tm.clickSettingIcon();
            tm.waitForSettingsForm();

            tm.clickAddSiteButton();
            tm.waitForSettingsForm();

            tm.addSites(siteInput, siteExpected);

            //sitesave buttons

            tm.waitforaddsitesbutton();


            tm.clickAddSiteSave();
            tm.closeSettingsForm();


//            if (tm.isCloseDisplayed()) {
//                tm.closeSettingsForm();
//            }
        }
    }

    // -------------------------------------------------------------------------
    @Then("i add region hr team mapping and map sites to region hr")
    public void i_add_region_hr_team_mapping_and_map_sites_to_region_hr() throws Exception {

        String path = "D:\\selenium-intellij\\src\\test\\resources\\teammapping.xlsx";

        // ----------------- REGION HR MAPPING -----------------
        String sheetname = "regionhr";
        String[][] regionData = Excelutils.getcelldatas(path, sheetname);

        tm.clickRegionHRmappingtab();


        for (String[] row : regionData) {

            String hrInput = row[0];
            String hrExpected = row[1];

            tm.clickAddRegionHRbutton();

            tm.addRegionHR(hrInput, hrExpected);
            tm.clickRegionHRsave();

            if (tm.isCloseDisplayed()) {
                tm.closeSettingsForm();
            }
        }

        // ----------------- STATE & REGION MAPPING -----------------
        String sheetnames = "locations";
        String[][] locationData = Excelutils.getcelldatas(path, sheetnames);

        for (String[] row : locationData) {

            String state = row[0];
            String region = row[1];
            String username = row[2];

            tm.searchTeamMappingUser(username);

            if (tm.isNoRecordFoundDisplayedFast()) {
                System.out.println("Region HR not found: " + username);
                continue;
            }

            tm.clickHRSettingIcon();
           // tm.waitForSettingsForm();

            tm.clickAddRegionInsideFormButton();
           // tm.waitForSettingsForm();

            tm.selectHRStateRegion(state, region);

            tm.clickHRAddRegionSave();

            if (tm.isCloseDisplayed()) {
                tm.closeSettingsForm();
            }
        }
    }
}
