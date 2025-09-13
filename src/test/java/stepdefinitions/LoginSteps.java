package stepdefinitions;

import drivers.DriverManager;
import hooks.TalentAcqBaseclass;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pageobjects.TALoginpage;

public class LoginSteps {

private WebDriver driver;
private TALoginpage lp;
public LoginSteps(){

    this.driver = DriverManager.getDriver();
    this.lp = new TALoginpage(driver);

}
    @Given("I am on the CMS login page")
    public void i_am_on_the_cms_login_page() {

   lp=new TALoginpage(DriverManager.getDriver());

    }
    @When("I login using valid credentials")
    public void i_login_using_valid_credentials() {
    lp.logindata(TalentAcqBaseclass.username,TalentAcqBaseclass.password);

    }
    @Then("I should see the CMS home page")
    public void i_should_see_the_cms_home_page() {

    }




}
