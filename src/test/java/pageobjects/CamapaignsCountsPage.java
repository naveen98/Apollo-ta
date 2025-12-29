package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Datepickutils;
import utils.Radiobuttons;
import utils.Totalcounts;
import utils.Webdriverwaitutils;

public class CamapaignsCountsPage {


    WebDriver driver;
    Webdriverwaitutils wait;
    JavascriptExecutor js;
    Totalcounts tc;



    public CamapaignsCountsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new Webdriverwaitutils(driver);
        this.js = (JavascriptExecutor) driver;
        this.tc=new Totalcounts(driver);
        PageFactory.initElements(driver, this);

    }



    By totalcampaign = By.xpath("//p[text()=' Campaigns']/preceding-sibling::h1");
    By Applications =    By.xpath("//p[text()='Applications']/preceding-sibling::h1");
    By Assessments = By.xpath("//p[text()='Assessments']/preceding-sibling::h1");
    By Interviews = By.xpath("//p[text()='Interviews']/preceding-sibling::h1");
    By Abandoned = By.xpath("//p[text()='Abandoned']/preceding-sibling::h1");
    By Hires = By.xpath("//p[text()=' Hires']/preceding-sibling::h1");


    @FindBy(xpath = "//a[contains(@class,'sidebar-toggle')]")
    private WebElement menubar;


    @FindBy(xpath = "(//formly-field[@class='col questionary-card custom-card total-app ng-star-inserted'])[1]")
    private WebElement totalcampaigncount;

    public int getTotalCampaignCount() {
        try {
            WebElement totalCounts = wait.waitForVisibility(totalcampaigncount);

            if (totalCounts != null && totalCounts.isDisplayed()) {

                return tc.getcount(totalcampaign);



            } else {
                System.out.println("Total campaigns  not visible.");
                return 0;
            }

        } catch (Exception e) {
            System.out.println(" Exception while getting total campaign count: " + e.getMessage());
            return 0;
        }
    }

    public void navigatemenu() {
        try {
            wait.waitForClickability(menubar).click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", menubar);
        }
    }



}
