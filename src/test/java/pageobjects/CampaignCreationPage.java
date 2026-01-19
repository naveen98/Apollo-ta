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
import java.util.ArrayList;
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


    private By Venuaddressinputbox = By.xpath("//div//input[@placeholder='Search Nearest Location']");

    // Locator for autocomplete results
    private By venueOptions = By.xpath("//div[@class='pac-container pac-logo']//div[contains(@class,'pac-item')]");

    @FindBy(xpath = "//div//button[@class='btn btn-default my-location ng-star-inserted']//span[contains(text(),'My location')]")
    private WebElement mylocationbtn;

    private By mylocationbtnby = By.xpath("//div//button[@class='btn btn-default my-location ng-star-inserted']//span[contains(text(),'My location')]");

    @FindBy(xpath = "(//div//button[@class='btn btn-primary ml-1 ng-star-inserted'][contains(text(),' Next ')])[1]")
    private WebElement nextbtn;

    private By nextbtnby = By.xpath("(//div//button[@class='btn btn-primary ml-1 ng-star-inserted'][contains(text(),' Next ')])[1]");


    //radio locators

    @FindBy(xpath = "   //div[@class='form-group zc-field-radio'][.//span[contains(text(),'Time Bound Campaign')]]")
    private WebElement TimeBoundCampaignlabel;

    @FindBy(xpath = "//div[@class='form-group zc-field-radio'][.//span[contains(text(),'Share Venue Contact Info')]]")
    private WebElement ShareVenueContactinfolabel;


    @FindBy(xpath = "//div[@class='form-group zc-field-radio'][.//span[contains(text(),'Medium')]]")
    private WebElement mediumlabel;


    public boolean iscalenderfieldsvisible() {
        try {
            return startdateopen.isDisplayed();
        } catch (Exception e) {
            return false;

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

    @FindBy(xpath = "//div//button[@class='close']")
    private WebElement closeform;


    //TARGETS
    @FindBy(xpath = "//div//p-select[@placeholder='Select source type']//span[@id='source_type_uid']")
    private WebElement sourcetypedrp;

    private By sourcetypeoptions = By.xpath("//p-selectitem//li[@role='option']");

    @FindBy(xpath = "//input[@role='searchbox']")
    private WebElement searchinputforsoucetypeoptions;


    @FindBy(xpath = "(//button[@class='btn btn-primary ml-1 ng-star-inserted'])[2]")
    private WebElement targetnextbtn;

    //notes
    @FindBy(xpath = "//textarea[@placeholder='Enter notes']")
    private WebElement notes;

    @FindBy(xpath = "//button[@class='btn btn-primary ml-1 ng-star-inserted'][contains(text(),' Submit ')]")
    private WebElement saveandcontinubuttonn;


    private By toastMsg = By.xpath("//div[contains(@class, 'toast-message') and contains(@class, 'ng-star-inserted')]");
    private By toastorpopup=By.xpath("//div[(@role='alert' and contains(@class,'toast-message'))     or (contains(@class,'modal-body')     and contains(text(),'Please enter all the mandatory fields before saving'))]");

    @FindBy(xpath = "//button[@id='dialog-okay-btn']")
    private WebElement popupOkButton;

    @FindBy(xpath = "//button[@id='dialog-cancel-btn']")
    private WebElement popupCancelButton;

    private final By popupMessageLocator = By.xpath("//div[contains(@class,'modal-body') and contains(text(),'Please enter all the mandatory fields before saving.')]");





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


    public void waitforcreatecampaign(){
         wait.waitForVisibility(CreateCampaignbtn);

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


   // Select venu address
    public void selectVenue(String venueText) {


        WebElement input = wait.waitForVisibility(venuaddress);
        try{
            input.click();
            input.clear();
            input.sendKeys(venueText);

          //  input.sendKeys(Keys.SPACE);
            WebElement option = wait.waitForVisibilityBy(venueOptions);
            option.click();

        } catch (ElementClickInterceptedException | StaleElementReferenceException | TimeoutException e) {

            input.sendKeys(Keys.SPACE);
            WebElement option = driver.findElement(venueOptions);
           js.executeScript("arguments[0].scrollIntoView(true);", option);
           js.executeScript("arguments[0].click();", option);
        }
    }



    public void clickNextButton() {

        By nextButton = By.xpath("(//div//button[@class='btn btn-primary ml-1 ng-star-inserted'][contains(text(),' Next ')])[1]");

        try {
            WebElement nextElement = wait.waitForVisibilityBy(nextButton);
            js.executeScript("arguments[0].scrollIntoView(true);", nextElement);
            wait.waitForClickability(nextElement).click();

        } catch (ElementClickInterceptedException | StaleElementReferenceException | TimeoutException e) {

            WebElement nextElement = driver.findElement(nextButton);
            js.executeScript("arguments[0].scrollIntoView(true);", nextElement);
            js.executeScript("arguments[0].click();", nextElement);
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


    //------------------use my location----------------------------------------

    public void useMyLocation() {

        try {

            WebElement ele = wait.waitForPresence(mylocationbtnby);
            if (ele != null && ele.isDisplayed()) {
                try {
                    js.executeScript("arguments[0].scrollIntoView(true);", ele);
                    ele.click();

                } catch (ElementClickInterceptedException |
                         StaleElementReferenceException |
                         TimeoutException e) {

                    js.executeScript("arguments[0].scrollIntoView(true);", ele);
                    js.executeScript("arguments[0].click();", ele);
                }
            }

            // Click Next button
            WebElement nextElement = driver.findElement(By.xpath("//div//button[@id='btnNxt']//span[contains(text(),'Next')]"));

            try {

                js.executeScript("arguments[0].scrollIntoView(true);", nextElement);
                wait.waitForClickability(nextElement).click();
            } catch (StaleElementReferenceException |
                     ElementClickInterceptedException |
                     TimeoutException e) {
                // JS fallback for Next button
                js.executeScript("arguments[0].scrollIntoView(true);", nextElement);
                js.executeScript("arguments[0].click();", nextElement);
            }

        } catch (Exception e) {
            System.out.println("Failed to click Use My Location or Next button: " + e.getMessage());
            throw e;
        }
    }


    public void selectfromsourcetype(String value){
         try {

             Dropdownutils.selectPrimeNgDropdown(driver, sourcetypedrp, sourcetypeoptions, value);
         } catch (Exception e) {

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



    //================================================================

    //-----------------------------------------
    public boolean handlePopupOK() {
        try {
            WebElement popupMsg = wait.waitForVisibilityBy(popupMessageLocator);
            if (popupMsg != null && popupMsg.isDisplayed()) {
                WebElement okBtn = wait.waitForClickability(popupOkButton);
                try {
                    okBtn.click();
                } catch (Exception ex) {
                    js.executeScript("arguments[0].click();", popupOkButton);
                }
                return true;
            }
        } catch (Exception e) {
            System.out.println("Popup not displayed: " + e.getMessage());
        }
        return false;
    }
    //===================================================================================


    public String gettoastmessage() {
        try {
            List<WebElement> messageElements = wait.waitForAllElementsVisible(toastorpopup);

            if (messageElements != null && !messageElements.isEmpty()) {
                for (WebElement msgElement : messageElements) {
                    if (!msgElement.isDisplayed())
                        continue;

                    String message = msgElement.getText().trim();
                    System.out.println("Toast message: " + message);

                    switch (message) {
                        case "Campaign Saved Successfully":

                            return message;

                        case "Code already exists!":
                            clickcloseform();
                            return  message;

                        case "Campaign Deleted Successfully":

                            return message;

                        case"Please enter all the mandatory fields before saving.":
                            handlePopupOK();

                            return message;


                        default:
                            // cancelform();
                            return message;
                    }
                }
            } else {
                System.out.println("Toast or popup not visible.");
            }
        } catch (Exception e) {
            System.out.println("Error  validation message: " + e.getMessage());
        }
        return "No Message Displayed";
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


    //   validation messages
    @FindBy(xpath = "//div[@class='invalid-feedback ng-star-inserted']")
    private List<WebElement> validationmessages;

    // Get all validation messages displayed

    public List<String> getValidationMessages() {
        List<String> messages = new ArrayList<>();
        try {
            if (validationmessages != null && !validationmessages.isEmpty()) {
                wait.waitForAllElementsVisible(validationmessages);

                for (WebElement el : validationmessages) {
                    if (el != null && el.isDisplayed()) {
                        String text = el.getText().trim();
                        if (!text.isEmpty()) {
                            messages.add(text);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error fetching validation messages: " + e.getMessage());
        }
        return messages;
    }


}
