package stepdefinitions;

import drivers.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pageobjects.Appselectionpage;

public class AppSelectionSteps {

 WebDriver driver;
 Appselectionpage ap;
    @Given("user is on the App Selection page")
    public void user_is_on_the_app_selection_page() {
       driver= DriverManager.getDriver();
       ap=new Appselectionpage(driver);

    }
    @When("user clicks on the Talent_Acquistion")
    public void user_clicks_on_the_talent_acquistion() {
       ap.clickOnAppSelection();
    }
    @Then("Apollo TalentAcq app should be displayed")
    public void apollo_talent_acq_app_should_be_displayed() {
      boolean displayed=ap.isAppDisplayed();
        Assert.assertTrue(displayed ,"Talent Acquistion is not displayed");
    }
}
