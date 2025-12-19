package pageobjects;

import org.apache.xmlbeans.impl.xb.xsdschema.FieldDocument;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Datepickutils;
import utils.Dropdownutils;
import utils.Radiobuttons;
import utils.Webdriverwaitutils;

import java.nio.channels.ScatteringByteChannel;
import java.security.PrivateKey;
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

    @FindBy(xpath = "//li[@id='menu-li-campaign-campaign']//span[text()='Campaigns']")
    private WebElement campaignmodule;

    @FindBy(xpath = "//h4[text()='Campaigns']")
    private WebElement campaigntxtdisplay;

    @FindBy(xpath = "//button[@class='btn btn-primary ng-star-inserted']//span[text()='Create Campaign']")
    private WebElement CreateCampaignbtn;


    @FindBy(xpath = "//input[@id='name']")
    private WebElement campname;
    @FindBy(xpath = "//input[@id='code']")
    private WebElement campcode;
    @FindBy(xpath = "(//input[@placeholder='DD/MM/YYYY'])[1]")
    private WebElement startdateopen;
    @FindBy(xpath = "(//input[@placeholder='DD/MM/YYYY'])[2]")
    private WebElement enddateopen;




    @FindBy(xpath = "//input[@placeholder='Search Nearest Location']")
    private WebElement venuaddress;

    //input[contains(@class,'map-search-address-bar')]


    private By Venuaddressinputbox=By.xpath("//div//input[@placeholder='Search Nearest Location']");

    // Locator for autocomplete results
    private By venueOptions = By.xpath("//div[contains(@class,'pac-item')]");

    @FindBy(xpath = "//button[@class='btn btn-default my-location ng-star-inserted']")
    private WebElement mylocationbtn;

    @FindBy(xpath = "//div//button[@id='btnNxt']//span[contains(text(),'Next')]")
    private WebElement nextbtn;

    private By nextbtnby=By.xpath("//div//button[@id='btnNxt']//span[contains(text(),'Next')]");




    //radio locators

    @FindBy(xpath = "   //div[@class='form-group zc-field-radio'][.//span[contains(text(),'Time Bound Campaign')]]")
    private WebElement TimeBoundCampaignlabel;

    @FindBy(xpath = "//div[@class='form-group zc-field-radio'][.//span[contains(text(),'Share Venue Contact Info')]]")
    private WebElement ShareVenueContactinfolabel;


    @FindBy(xpath = "//div[@class='form-group zc-field-radio'][.//span[contains(text(),'Medium')]]")
    private WebElement mediumlabel;



    public boolean iscalenderfieldsvisible(){
        try {
            return startdateopen.isDisplayed();
        }
        catch (Exception e){
            return  false;

        }

    }

    public void waitForVenueInputVisible() {
        wait.waitForVisibilityBy(Venuaddressinputbox);
    }



    //calender

    private By Monthtextlocator = By.xpath("//button[@class='current ng-star-inserted']");
    private By yeartextlocator = By.xpath("//button[@class='current']");
    private By alldates = By.xpath("//td[@role='gridcell']");
    private By startnext = By.xpath("//button[@class='next']");
    private By startprevious = By.xpath("//button[@class='previous']");

    @FindBy(xpath = "//div//button[@class='close']")private WebElement closeform;


    //TARGETS
    @FindBy(xpath = "//p-select[@placeholder='Select source type']//span[@id='source_type_uid']")
    private WebElement sourcetypedrp;

    private By sourcetypeoptions=By.xpath("//p-selectitem//li[@role='option']");

    @FindBy(xpath="//input[@role='searchbox']")private WebElement searchinputforsoucetypeoptions;




    @FindBy(xpath = "//button[@id='btnNext']")
    private WebElement targetnextbtn;

    //notes
    @FindBy(xpath = "//textarea[@placeholder='Enter notes']")
    private WebElement notes;

    @FindBy(xpath = "//zc-button//div//button[@id='btnSave']")
    private WebElement saveandcontinubuttonn;


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

    public void createcampaign(String campaignname, String code) {
        wait.waitForEnterText(campname, campaignname);
        wait.waitForEnterText(campcode, code);
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
//    public void selectVenue(String venueText)  {
//        WebElement input = wait.waitForVisibility(venuaddress);
//        input.click();
//        input.sendKeys(venueText);
//        input.sendKeys(Keys.SPACE);
//        input.sendKeys(Keys.BACK_SPACE);
//
//        try {
//           new WebDriverWait(driver, Duration.ofSeconds(30))
//                    .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(venueOptions));
//
//            input.sendKeys(Keys.ARROW_DOWN);
//            input.sendKeys(Keys.ENTER);
//
//
//        } catch (TimeoutException e) {
//
//            System.out.println("No venue suggestions appeared, pressing ENTER directly");
//            input.sendKeys(Keys.BACK_SPACE);
//            input.sendKeys(Keys.ARROW_DOWN);
//            input.sendKeys(Keys.ENTER);
//        }
//    }

    public void selectVenue(String venueText) {

        WebElement input = wait.waitForVisibility(venuaddress);
        input.click();
        input.clear();

        input.sendKeys(venueText);
        input.sendKeys(Keys.SPACE);

        try {
            WebElement option = new WebDriverWait(driver, Duration.ofSeconds(20))
                    .until(ExpectedConditions.elementToBeClickable(venueOptions));

            option.click();

            js.executeScript("arguments[0].blur();", input);

            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(driver -> (Boolean) js.executeScript(
                            "return document.body.scrollHeight > window.innerHeight;"
                    ));

        } catch (TimeoutException e) {
            input.sendKeys(venueText);
            input.sendKeys(Keys.SPACE);
            wait.waitForClickabilityBy(venueOptions).click();
           // input.sendKeys(Keys.ENTER);
        }
    }



    public void clickSaveAndContinue(){

        clickElement(saveandcontinubuttonn);
    }


    //  radio button selector

    public boolean selectRadioButtonOption(String labelText, String optionText) {
        try {
            String xpath = "//div[@class='form-group zc-field-radio'][.//span[contains(text(),'" + labelText + "')]]";

            WebElement radioGroup = wait.waitForVisibility(driver.findElement(By.xpath(xpath)));

            List<WebElement> options = radioGroup.findElements(By.xpath(".//label[contains(@class,'custom-radio')]"));

            for (WebElement option : options) {
                if (option.getText().trim().equalsIgnoreCase(optionText)) {

                    WebElement button = option.findElement(By.tagName("input"));

                    js.executeScript("arguments[0].scrollIntoView({block:'center'});", option);

                    clickElement(option);

                    return button.isSelected();
                }
            }
        } catch (Exception e) {
            System.out.println("Radio selection error: " + e.getMessage());
        }
        return false;
    }


    //   common click
    private void clickElement(WebElement element) {
        try {
            wait.waitForClickability(element);
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);

            try {
                element.click();
            } catch (ElementClickInterceptedException e) {
                js.executeScript("arguments[0].click();", element);
            }

        } catch (Exception e) {
            js.executeScript("arguments[0].click();", element);
        }
    }



     //Mylocation
    public void useMyLocation() {

        try {
            wait.waitForClickability(mylocationbtn).click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", mylocationbtn);
        }

    }


    public void selectfromsourcetype(String value){
         try {
             WebElement ele= wait.waitForVisibility(sourcetypedrp);

             Dropdownutils.selectPrimeNgDropdown(driver, sourcetypedrp, sourcetypeoptions, value);
         } catch (Exception e) {
             throw new RuntimeException(e);
         }
    }

//    public void clickNext() {
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//
//        try {
//            WebElement nextBtn = wait.until(
//                    ExpectedConditions.presenceOfElementLocated(nextbtnby)
//            );
//
//            js.executeScript(
//                    "window.scrollTo(0, arguments[0].getBoundingClientRect().top + window.pageYOffset - 120);",
//                    nextBtn
//            );
//
//            nextBtn = wait.until(
//                    ExpectedConditions.elementToBeClickable(nextbtnby)
//            );
//
//            nextBtn.click();
//
//        } catch (Exception e) {
//            WebElement nextBtn = driver.findElement(nextbtnby);
//            js.executeScript("arguments[0].click();", nextBtn);
//        }
//    }
//

    public void clickNext() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.cssSelector(".pac-container")
            ));

            WebElement nextBtn = wait.until(
                    ExpectedConditions.presenceOfElementLocated(nextbtnby)
            );

            js.executeScript(
                    "window.scrollTo(0, arguments[0].getBoundingClientRect().top + window.pageYOffset - 120);",
                    nextBtn
            );

            wait.until(ExpectedConditions.elementToBeClickable(nextbtnby)).click();

        } catch (Exception e) {
            WebElement nextBtn = driver.findElement(nextbtnby);
            js.executeScript("arguments[0].click();", nextBtn);
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

            WebElement savebtn = wait.waitForClickability(saveandcontinubuttonn);

            if (savebtn != null && savebtn.isDisplayed())
                savebtn.click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", saveandcontinubuttonn);
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
