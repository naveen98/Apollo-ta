package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Datepickutils;
import utils.Dropdownutils;
import utils.Radiobuttons;
import utils.Webdriverwaitutils;

import java.nio.channels.ScatteringByteChannel;
import java.time.Duration;
import java.util.List;

public class CampaignCreationPage {

    WebDriver driver;
    Webdriverwaitutils wait;
    JavascriptExecutor js;
    Datepickutils dt;
    Radiobuttons rd;


    public CampaignCreationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new Webdriverwaitutils(driver);
        this.js = (JavascriptExecutor) driver;
        this.dt = new Datepickutils(driver);
        this.rd = new Radiobuttons(driver);
        PageFactory.initElements(driver, this);

    }


    @FindBy(xpath = "//a[@class='icon-bars sidebar-toggle']")
    private WebElement menubar;

    @FindBy(xpath = "//span[text()='Campaigns']")
    private WebElement campaignmodule;

    @FindBy(xpath = "//h4[text()='Campaigns']")
    private WebElement campaigntxtdisplay;

    @FindBy(xpath = "//span[text()='Create Campaign']")
    private WebElement CreateCampaignbtn;


    @FindBy(xpath = "//input[@id='name']")
    private WebElement campname;
    @FindBy(xpath = "//input[@id='code']")
    private WebElement campcode;
    @FindBy(xpath = "(//input[@placeholder='DD/MM/YYYY'])[1]")
    private WebElement startdateopen;
    @FindBy(xpath = "(//input[@placeholder='DD/MM/YYYY'])[2]")
    private WebElement enddateopen;

    @FindBy(xpath = "//p-select[@placeholder='Select state']")
    private WebElement statedrp;
    @FindBy(xpath = "//p-select[@placeholder='Select region']")
    private WebElement regiondrp;
    @FindBy(xpath = "//p-select[@placeholder='Select city']")
    private WebElement citydrp;
    @FindBy(xpath = "(//p-select[@placeholder='Select area'])[2]")
    private WebElement areadrp;

    @FindBy(xpath = "//input[@placeholder='Search Nearest Location']")
    private WebElement venuaddress;


    // Locator for autocomplete results
    private By venueOptions = By.xpath("//div[contains(@class,'pac-item')]");

    @FindBy(xpath = "//button[@class='btn btn-default my-location ng-star-inserted']")
    private WebElement mylocationbtn;

    @FindBy(xpath = "(//button[@class='btn btn-primary ng-star-inserted'])[3]")
    private WebElement nextbtn;

    By stateoptions = By.xpath("//ul[@role='listbox']//li[@class='p-ripple p-select-option']");
    By regionoptions = By.xpath("//ul[@role='listbox']//li[@class='p-ripple p-select-option']");
    By cityoptions = By.xpath("//ul[@role='listbox']//li[@class='p-ripple p-select-option']");
    By areaoptions = By.xpath("//ul[@class='p-select-list ng-star-inserted']//li[@class='p-ripple p-select-option']");
    //calender
    private By Monthtextlocator = By.xpath("//button[@class='current ng-star-inserted']");private By yeartextlocator = By.xpath("//button[@class='current']");
    private By alldates = By.xpath("//td[@role='gridcell']");
    private By startnext = By.xpath("//button[@class='next']");
    private By startprevious = By.xpath("//button[@class='previous']");

    @FindBy(xpath = "//button[@class='close']")private WebElement closeform;


    //TARGETS
    @FindBy(xpath = "//button[@title='add more']")
    private WebElement addmorebtn;
    @FindBy(xpath = "//p-select[@placeholder='Select job role']")
    private WebElement jobroledrp;
    @FindBy(xpath = "//input[@placeholder='Enter target no. of hires']")
    private WebElement targetnohires;

    By jobroleotions = By.xpath("//ul[@role='listbox']//li[@class='p-ripple p-select-option']");

    @FindBy(xpath = "//button[@title='btnAdd']")
    private WebElement targetaddbtn;

    @FindBy(xpath = "(//button[@title='Next'])[2]")
    private WebElement targetnextbtn;

    //notes
    @FindBy(xpath = "//textarea[@placeholder='Enter notes']")
    private WebElement notes;

    @FindBy(xpath = "//button[@title='Save & Continue']")
    private WebElement saveandcontinubtn;


    private By toastMsg = By.xpath("//div[contains(@class, 'toast-message') and contains(@class, 'ng-star-inserted')]");


    //Action Methods
    public void navigatemenu() {
        try {
            wait.waitForClickability(menubar).click();
        } catch (Exception e) {
            // js.executeScript("arguments[0].scrollIntoView({block: 'center'});", menubar);
            js.executeScript("arguments[0].click();", menubar);
        }
    }

    public boolean istextdisplayed() {

        try {
            WebElement text = wait.waitForVisibility(campaigntxtdisplay);
            return text.isDisplayed();

        } catch (Exception e) {
            System.out.println("Not displayed");

        }
        return false;

    }

    public void navigatecampignmodule() {
        try {

            WebElement cam = wait.waitForVisibility(campaignmodule);
            cam.click();

        } catch (Exception e) {
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", campaignmodule);
            js.executeScript("arguments[0].click();", campaignmodule);
        }
    }


    public void clickcampaignbtn() {
        try {
            WebElement add = wait.waitForVisibility(CreateCampaignbtn);
            if (add != null && add.isDisplayed())
                add.click();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void createcampaign(String camname, String code, String state, String region, String city, String area) {
        wait.waitForEnterText(campname, camname);
        wait.waitForEnterText(campcode, code);
        Dropdownutils.selectbyvisibletextlistretry(driver, statedrp, stateoptions, state);
        Dropdownutils.selectbyvisibletextlistretry(driver, regiondrp, regionoptions, region);
        Dropdownutils.selectbyvisibletextlistretry(driver, citydrp, cityoptions, city);
        Dropdownutils.selectbyvisibletextlistretry(driver, areadrp, areaoptions, area);

    }

    public void startdate(String month, String year, String date) {

        try {
            wait.waitForClickability(startdateopen).click();
            dt.datepickers(Monthtextlocator, yeartextlocator, startprevious, startnext, alldates, month, year, date);
        } catch (Exception e) {
            System.out.println("Error selecting start date: " + e.getMessage());
        }

    }

    public void enddate(String month, String year, String date) {

        try {
            wait.waitForClickability(enddateopen).click();
            dt.datepickers(Monthtextlocator, yeartextlocator, startprevious, startnext, alldates, month, year, date);
        } catch (Exception e) {
            System.out.println("Error selecting start date: " + e.getMessage());
        }

    }

    public void selectVenue(String venueText) {
        WebElement input = wait.waitForVisibility(venuaddress);
        input.click();
        input.sendKeys(venueText);

        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOfElementLocated(venueOptions));

            input.sendKeys(Keys.ARROW_DOWN);
            input.sendKeys(Keys.ENTER);

        } catch (TimeoutException e) {

            System.out.println("No venue suggestions appeared, pressing ENTER directly");
            input.sendKeys(Keys.BACK_SPACE);
            input.sendKeys(Keys.ARROW_DOWN);
            input.sendKeys(Keys.ENTER);
        }
    }

    // ===== Medium selection =====
    public void handleMedium(String medium, String venueText) {
        boolean selected = rd.selectRadioButtonOption("Medium", medium);
        if (!selected) {
            throw new RuntimeException("Failed to select Medium: " + medium);
        }

        switch (medium.toLowerCase()) {
            case "offline":

                selectVenue(venueText);
                break;

            case "online":
                System.out.println("Online selected, venue not required");
                break;

            default:
                throw new IllegalArgumentException("Invalid medium: " + medium);
        }
    }

    public void useMyLocation() {

        try {
            wait.waitForClickability(mylocationbtn).click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", mylocationbtn);
        }

    }


    public void clicknext() {
        scrollToBottom();
        try {

            WebElement nxt = wait.waitForClickability(nextbtn);
            if (nxt != null && nxt.isDisplayed())
                nxt.click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", nextbtn);
        }

    }
    public void scrollToBottom() {
        try {
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        } catch (Exception e) {
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        }
    }

    public void addmorebtn() {
        try {
            WebElement ad = wait.waitForVisibility(addmorebtn);
            ad.click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", addmorebtn);

        }

    }

    public void targetsection(String jobrole, String noofhires) {

        Dropdownutils.selectbyvisibletextlistretry(driver, jobroledrp, jobroleotions, jobrole);
        wait.waitForEnterText(targetnohires, noofhires);

        try {
            WebElement ele = wait.waitForClickability(targetaddbtn);
            if (ele != null && ele.isDisplayed())
                ele.click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", targetaddbtn);

        }

    }

    public void addtargetnextbtn() {
        try {
            WebElement ad = wait.waitForVisibility(targetnextbtn);
            if (ad != null && ad.isDisplayed()) {
                ad.click();
            }
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", targetnextbtn);

        }
    }

    public void NoteDescription(String description) {
        wait.waitForEnterText(notes, description);

        try {

            WebElement savebtn = wait.waitForClickability(saveandcontinubtn);

            if (savebtn != null && savebtn.isDisplayed())
                savebtn.click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", saveandcontinubtn);
        }


    }


    public String gettoastmessage() {

        String message = "";
        List<WebElement> msg=wait.waitForAllElementsVisible(toastMsg);
        for(WebElement msgs:msg)
        {
            if (msgs != null && msgs.isDisplayed())
                message = msgs.getText();

            return message;
        }
        return "no message displayed";

    }
    public void clickcloseform(){

        try{
            WebElement close=wait.waitForVisibility(closeform);
            if(close!=null && close.isDisplayed()){
                close.click();
            }

        } catch (Exception e) {
            js.executeScript("arguments[0].click();",closeform);
        }
    }


}
