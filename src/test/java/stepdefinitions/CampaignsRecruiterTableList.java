package stepdefinitions;

import drivers.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pageobjects.CampaignRecruiterAddingPage;
import utils.Excelutils;
import utils.Webdriverwaitutils;

import java.io.IOException;
import java.util.List;

public class CampaignsRecruiterTableList {

    WebDriver driver;
    CampaignRecruiterAddingPage ad;

    @Given("I navigate to campaign Recruiter Table")
    public void i_navigate_to_campaign_recruiter_table() {
        driver= DriverManager.getDriver();
        ad = new CampaignRecruiterAddingPage(driver);
        ad.navigatemenu();
        ad.navigatecampaignmodule();


    }

    @When("I  capture added recruiter in campaign")
    public void i_capture_added_recruiter_in_campaign() throws IOException {
        // Search Campaign
        ad.searchcampaigns("FestiveSale");

        if (ad.isNoRecordFoundDisplayed()) {
            System.out.println("No record found for : ");
            return;
        }


        // Select Campaign Row
        ad.selectcampaignrow();

        // Click Recruiter Tab
        ad.clickrecruitertab();
        List<String[]> CampaignRecruteList = ad.getCampaignrecruiterTableData();

        String[] headers = {"Name", "Application Received", "Joined"};

        String path = "D:\\selenium-intellij\\src\\test\\resources\\campaigndetails.xlsx";
        String shname = "recruiterlist";

        Excelutils.writeTable(path, shname, headers, CampaignRecruteList);


        ad.logoutfromapplication();


    }





}
