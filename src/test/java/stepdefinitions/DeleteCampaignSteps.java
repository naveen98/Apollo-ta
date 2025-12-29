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

    @Given("I Navigate to Campaign Module")
    public void i_navigate_to_campaign_module() {

        driver = DriverManager.getDriver();
        deleteCampaignPage = new DeleteCampaignPage(driver);
        campaignCounts = new CamapaignsCountsPage(driver);
        pageCounts = new CampaigntotalPagecounts(driver);

        deleteCampaignPage.navigateCampaignsModule();
    }

    @When("i click on Delete Campaign")
    public void i_click_on_delete_campaign() {

        // ===== BEFORE DELETE =====
        countBeforeDelete = campaignCounts.getTotalCampaignCount();
        pageBeforeDelete = pageCounts.getTotalPagesFromText();

        // ===== DELETE FLOW =====
        deleteCampaignPage.searchcampaigns("FestiveSale");
        deleteCampaignPage.clickDeleteOption();

        Assert.assertTrue(
                deleteCampaignPage.handlePopupOK(),
                "Delete confirmation popup not displayed"
        );

        String toast = deleteCampaignPage.getvalidationmessage();
        Assert.assertEquals(
                toast,
                "Campaign Deleted Successfully",
                "Toast message mismatch"
        );

        // ===== AFTER DELETE =====
        driver.navigate().refresh();

        countAfterDelete = campaignCounts.getTotalCampaignCount();
        pageAfterDelete = pageCounts.getTotalPagesFromText();

        Assert.assertEquals(
                countAfterDelete,
                countBeforeDelete - campaignsDeleted,
                "Campaign count mismatch after delete"
        );

        Assert.assertTrue(
                pageAfterDelete <= pageBeforeDelete,
                "Pagination count should not increase after delete"
        );
    }

    @Then("i Verify the Deleted Campaign")
    public void i_verify_the_deleted_campaign() {

        deleteCampaignPage.searchcampaigns("FestiveSale");

        boolean noRecord = deleteCampaignPage.isNoRecordFoundDisplayed();

        Assert.assertTrue(noRecord, "Deleted campaign is still visible");

        System.out.println(" Deleted campaign verified : No records found is Displayed");
    }
}
