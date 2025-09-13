package stepdefinitions;

import drivers.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pageobjects.NavigatetoVenuPages;

public class NavigatetoVenumodulesteps {
    WebDriver driver;
    NavigatetoVenuPages Np;

    @Given("I am logged into Talent Acqusition")
    public void i_am_logged_into_talent_acqusition() {
        driver= DriverManager.getDriver();
        Np=new NavigatetoVenuPages(driver);
    }
    @When("I Navigate to venu module from  Menubar")
    public void i_navigate_to_venu_module_from_menubar() {

        Np.navigatetovenumodule();
    }
    @Then("venu module page should visible")
    public void venu_module_page_should_visible() {
       boolean displayed=Np.istextdisplayed();
       Assert.assertTrue(displayed, "Venu module not displayed");

    }


}
