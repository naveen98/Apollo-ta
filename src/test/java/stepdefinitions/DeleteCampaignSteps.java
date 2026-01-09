package stepdefinitions;

import drivers.DriverManager;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pageobjects.*;

public class DeleteCampaignSteps {

    WebDriver driver;
    DeleteCampaignPage deleteCampaignPage;
    CamapaignsCountsPage campaignCounts;
    CampaigntotalPagecounts pageCounts;

    int countBeforeDelete;
    int countAfterDelete;
    int pageBeforeDelete;
    int pageAfterDelete;

    final int campaignsDeleted = 1;
    final String campaignName = "FestiveSale";

    // ================= GIVEN =================

    @Given("I Navigate to Campaign Module")
    public void i_navigate_to_campaign_module() {

        driver = DriverManager.getDriver();
        Assert.assertNotNull(driver, "WebDriver is NULL");

        deleteCampaignPage = new DeleteCampaignPage(driver);
        campaignCounts = new CamapaignsCountsPage(driver);
        pageCounts = new CampaigntotalPagecounts(driver);

        deleteCampaignPage.navigateCampaignsModule();
    }

    // ================= WHEN =================

    @When("i click on Delete Campaign")
    public void i_click_on_delete_campaign() {

        // ===== BEFORE DELETE =====
        countBeforeDelete = campaignCounts.getTotalCampaignCount();
        pageBeforeDelete = pageCounts.getTotalPagesFromText();

        Assert.assertTrue(
                countBeforeDelete > 0,
                "No campaigns available to delete"
        );

        // ===== DELETE FLOW =====
        deleteCampaignPage.searchcampaigns(campaignName);

        Assert.assertFalse(
                deleteCampaignPage.isNoRecordFoundDisplayed(),
                "Campaign not found for deletion: " + campaignName
        );

        deleteCampaignPage.clickDeleteOption();

        Assert.assertTrue(
                deleteCampaignPage.handlePopupOK(),
                "Delete confirmation popup not displayed"
        );

        String toast = deleteCampaignPage.getvalidationmessage();

        Assert.assertNotNull(
                toast,
                "Toast message is NULL"
        );

        Assert.assertTrue(
                toast.toLowerCase().contains("deleted"),
                "Unexpected toast message: " + toast
        );

        // ===== AFTER DELETE =====
        driver.navigate().refresh();

        countAfterDelete = campaignCounts.getTotalCampaignCount();
        pageAfterDelete = pageCounts.getTotalPagesFromText();

        // ----- Count Assertion -----
        Assert.assertEquals(
                countAfterDelete,
                countBeforeDelete - campaignsDeleted,
                "Campaign count mismatch after delete"
        );

        // ----- Pagination Assertion -----
        Assert.assertTrue(
                pageAfterDelete <= pageBeforeDelete,
                "Pagination count increased after delete"
        );
    }

    // ================= THEN =================

    @Then("i Verify the Deleted Campaign")
    public void i_verify_the_deleted_campaign() {

        deleteCampaignPage.searchcampaigns(campaignName);

        Assert.assertTrue( deleteCampaignPage.isNoRecordFoundDisplayed(),  "Deleted campaign is still visible: " + campaignName  );

        System.out.println("Deleted campaign verified successfully – No records found");
    }
}
