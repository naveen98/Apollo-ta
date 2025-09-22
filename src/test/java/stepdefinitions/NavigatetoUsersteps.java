package stepdefinitions;

import drivers.DriverManager;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import pageobjects.NavigatetoVenuPages;
import pageobjects.NavigationtoUserpage;

public class NavigatetoUsersteps {
    WebDriver driver;
     NavigationtoUserpage np;

    @Given("I am logged into admin")
    public void i_am_logged_into_admin() {

        driver= DriverManager.getDriver();
        np=new NavigationtoUserpage(driver);

    }
    @When("i navigate to menu bar")
    public void i_navigate_to_menu_bar() {
        np.navigatetousers();

    }
    @Then("user module should be visible")
    public void user_module_should_be_visible() {
        np.isdisplayed();

    }






}
