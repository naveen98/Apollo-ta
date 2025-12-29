package stepdefinitions;

import drivers.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.apache.poi.ss.formula.functions.T;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageobjects.RecruiterDashboardPage;
import pageobjects.TALoginpage;
import utils.RecruiterConfigReader;


public class RecruiterLoginSteps {

    WebDriver driver;
    TALoginpage loginpage;


    @Given("I login with recruiter credentials")
    public void i_login_with_recruiter_credentials() {
        driver=DriverManager.getDriver();

         loginpage=new TALoginpage(driver);

       // driver.get(RecruiterConfigReader.getProperty("baseurlrecruiter"));

        loginpage.logindata(RecruiterConfigReader.getProperty("recruiterusername"),
                RecruiterConfigReader.getProperty("recruiterpassword"));
    }


    @When("I verify recruiter dashboard page")
    public void i_verify_recruiter_dashboard_page() {

        RecruiterDashboardPage dashboard = new RecruiterDashboardPage(driver);
        boolean isdisplayed=dashboard.isRecruiterDashboardDisplayed();

        if(isdisplayed){
            System.out.println("Recruitment Dashboard : " + isdisplayed);
        }

    }
}
