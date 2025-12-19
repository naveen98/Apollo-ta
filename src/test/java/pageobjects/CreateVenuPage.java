package pageobjects;


import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Dropdownutils;
import utils.Webdriverwaitutils;

import java.time.Duration;
import java.util.List;

public class CreateVenuPage {
    WebDriver driver;
    Webdriverwaitutils wait;
    JavascriptExecutor js;

    public CreateVenuPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new Webdriverwaitutils(driver);
        this.js = (JavascriptExecutor) driver;

    }


    //------------Navigations---------------------
    @FindBy(xpath = "//a[@class='icon-bars sidebar-toggle']")
    private WebElement Menubar;

    @FindBy(xpath = "//li[@id='menu-li-venue-venue']//span[contains(text(),'Venues')]")
    private WebElement venumodule;

    public void navigatetovenumodule() {

        try {

            WebElement ele = wait.waitForClickability(Menubar);
            ele.click();
        } catch (Exception e) {

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", Menubar);
        }

        try {
            WebElement ele = wait.waitForClickability(venumodule);
            ele.click();
        } catch (Exception e) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", venumodule);
        }
    }


    //=================== Locators ===========================


    @FindBy(xpath = "//div//button[@class='btn btn-primary ng-star-inserted']")
    private WebElement Createconfigurevenubtn;


    private By CreateconfigurevenubtnBy = By.xpath("//div//button[@class='btn btn-primary ng-star-inserted']");


    @FindBy(xpath = "//zc-text//input[@id='title']")
    private WebElement nametxtbox;

    private By nametxtboxBy = By.xpath("//zc-text//input[@id='title']");

    @FindBy(xpath = "//zc-textarea//textarea[@id='address']")
    private WebElement venuaddresstxtbox;


    @FindBy(xpath = "//zc-text//input[@id='contact_person']")
    private WebElement contactpersontxtbox;

    @FindBy(xpath = "//zc-number//input[@id='contact_mobile_no']")
    private WebElement contactmobiletxtbox;



    //location locators
    @FindBy(xpath = "//div//input[@placeholder='Search Nearest Location']")
    private WebElement locationinputbox;


    private By locationOptions = By.xpath("//div[contains(@class,'pac-item')]");


    @FindBy(xpath = "//button[@class='btn btn-default my-location ng-star-inserted']//span[contains(text(),'My location')]")
    private WebElement mylocationbtn;


    //---------------------------------------Availability Locators----------------------------------
    @FindBy(xpath = "//p-select[@placeholder='Select availability in']")
    private WebElement availabilitydrp;

    @FindBy(xpath = "//p-multiselect[@placeholder='Select state']")
    private WebElement statedrp;


    @FindBy(xpath = "//button[@class='btn btn-primary ng-star-inserted']//span[contains(text(),'Add')]")
    private WebElement availabitityinsideaddbutton;

    @FindBy(xpath = "//p-select[@placeholder='Select state']")
    private WebElement innerstatedropdown;

    @FindBy(xpath = "//p-multiselect[@placeholder='Select region']")
    private WebElement innerregiondropdown;

    @FindBy(xpath="//p-select[@placeholder='Select region']")private WebElement regiondropdownbothareacity;


    @FindBy(xpath = "//p-multiselect[@placeholder='Select city']")
    private WebElement citydropdown;

    @FindBy(xpath = "//p-select[@placeholder='Select city']")
    private WebElement citydropdownbothareacity;


    @FindBy(xpath = "//p-multiselect[@placeholder='Select area']")
    private WebElement Areacitydropdown;



    By availabityoptions = By.xpath("//p-selectitem//li[@role='option']");

    By stateoptions = By.xpath("//p-multiselect-item//li[@role='option']");


    By innerstateoptions = By.xpath("//p-selectitem//li[@role='option']");

    By regionoptions = By.xpath("//p-multiselect-item//li[@role='option']");


    By Cityregionoptions = By.xpath("//p-selectitem//li[@role='option']");

    By cityoptions = By.xpath("//p-multiselect-item//li[@role='option']");

    By areacityoptions = By.xpath("//p-selectitem//li[@role='option']");

    By areaoptions = By.xpath("//p-multiselect-item//li[@role='option']");


    // add availability after locations add button

    @FindBy(xpath = "//button[@id='btnAdd']//span[contains(text(),'Add')]")
    private WebElement Availabilityaddlocations;

    @FindBy(xpath = "//zc-textarea//textarea[@id='notes']")
    private WebElement notestxtarea;


    @FindBy(xpath = "//button[@id='btnSave']")
    private WebElement savebtn;



    private final By toastOrPopupMsg =
            By.xpath("//div[(@role='alert' and contains(@class, 'toast-message')) or (contains(@class,'modal-body') and contains(text(),'Please fill mandatory fields'))]");


    private final By popupMessageLocator = By.xpath("//div[contains(@class,'modal-body') and contains(text(),'Please fill mandatory fields')]");

    @FindBy(xpath = "//button[@class='btn btn-sm btn-primary ng-star-inserted']")
    private WebElement popupOkButton;

    @FindBy(xpath = "//button[@id='dialog-cancel-btn']")
    private WebElement popupCancelButton;

    @FindBy(xpath = "//button[@title='Close']")
    private WebElement cancelFormButton;

    //--------------------------------------------------------------------------------------

    @FindBy(xpath = "//p-iconfield//input[@role='searchbox']")
    private WebElement searchinside;



    //--------------location action method-------------------------

    public void selectVenue(String locationtext) {

        WebElement input = wait.waitForVisibility(locationinputbox);
        input.click();
        input.sendKeys(locationtext);

        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(locationOptions));

            input.sendKeys(Keys.ARROW_DOWN);
            input.sendKeys(Keys.ENTER);


        } catch (TimeoutException e) {

            System.out.println("No location suggestions appeared, pressing ENTER directly");
            input.sendKeys(Keys.BACK_SPACE);
            input.sendKeys(Keys.ARROW_DOWN);
            input.sendKeys(Keys.ENTER);
        }
    }



    public void addafteravailabity(){

        try{
            WebElement ele=wait.waitForVisibility(Availabilityaddlocations);
            if (ele!=null&&ele.isDisplayed())
                ele.click();

        }catch (Exception e){
            js.executeScript("arguments[0].scrollIntoView(true);",Availabilityaddlocations);
            js.executeScript("arguments[0].click();",Availabilityaddlocations);
        }
    }

    public void clicksavebutton() {
        try {
            WebElement ele = wait.waitForVisibility(savebtn);
            if (ele != null && ele.isDisplayed())
                ele.click();
        } catch (Exception e) {
            js.executeScript("arguments[0].scrollIntoView(true);", savebtn);
            js.executeScript("arguments[0].click();", savebtn);
        }

    }

    public void creatvenuename(String name){

        wait.waitForEnterText(nametxtbox, name);
    }

    public void usemylocation(){

        try {
            WebElement ele = wait.waitForVisibility(mylocationbtn);
            if (ele != null && ele.isDisplayed())
                ele.click();
        } catch (Exception e) {
            js.executeScript("arguments[0].scrollIntoView(true);", mylocationbtn);
            js.executeScript("arguments[0].click();", mylocationbtn);
        }

    }



    public void ClickCreateConfigure() {
        try {
            wait.waitForVisibilityWithRetry(CreateconfigurevenubtnBy).click();
        } catch (Exception e) {
            System.out.println("addcongigure " + e.getMessage());
        }
    }

    public void AvailabilityInsideAddbutton() {

        try {
            wait.waitForVisibility(availabitityinsideaddbutton).click();

        } catch (Exception e) {
            js.executeScript("arguments[0].scrollIntoView(true);", availabitityinsideaddbutton);
            js.executeScript("arguments[0].click();", availabitityinsideaddbutton);
        }
    }

    public void createvenu(String name, String location) {

        wait.waitForVisibilityBy(nametxtboxBy);
        wait.waitForEnterText(nametxtbox, name);
        selectVenue(location);

    }

    public void contactFields(String contactPerson, String contactMobNo) {

        wait.waitForEnterText(contactpersontxtbox, contactPerson);
        wait.waitForEnterText(contactmobiletxtbox, contactMobNo);


    }


    public void Availabitylocations(String availability,  String state, String region, String city, String area) {

        Dropdownutils.selectbyvisibletextlistretry(driver, availabilitydrp, availabityoptions, availability);

        String avail = "";
        if (availability != null) {
            avail = availability.trim().toLowerCase();
        }

        switch (avail) {
            case "state":
                if (!state.isEmpty()) {
                    Dropdownutils.selectbyvisibletextlistretry(driver, statedrp, stateoptions, state);
                }
                break;

            case "region":
                AvailabilityInsideAddbutton();
                if (!state.isEmpty()) {
                    Dropdownutils.selectbyvisibletextlistretry(driver, innerstatedropdown, innerstateoptions, state);
                }
                if (!region.isEmpty()) {
                    Dropdownutils.selectbyvisibletextlistretry(driver, innerregiondropdown, regionoptions, region);
                }
                addafteravailabity();
                break;

            case "city":
                AvailabilityInsideAddbutton();
                if (!state.isEmpty()) {
                    Dropdownutils.selectbyvisibletextlistretry(driver, innerstatedropdown, innerstateoptions, state);
                }
                if (!region.isEmpty()) {
                    Dropdownutils.selectbyvisibletextlistretry(driver, regiondropdownbothareacity, regionoptions, region);
                }
                if (!city.isEmpty()) {
                    Dropdownutils.selectbyvisibletextlistretry(driver, citydropdownbothareacity, cityoptions, city);
                }
                addafteravailabity();
                break;

            case "area":
                AvailabilityInsideAddbutton();
                if (!state.isEmpty()) {
                    Dropdownutils.selectbyvisibletextlist(driver, innerstatedropdown, innerstateoptions, state);
                }
                if (!region.isEmpty()) {
                    Dropdownutils.selectDropdownWithSearch(driver, regiondropdownbothareacity, regionoptions,searchinside, region);
                }
                if (!city.isEmpty()) {
                    Dropdownutils.selectDropdownWithSearch(driver, citydropdownbothareacity, cityoptions,searchinside, city);
                }
                if (!area.isEmpty()) {
                    Dropdownutils.selectDropdownWithSearch(driver, Areacitydropdown, areaoptions,searchinside, area);
                }
                addafteravailabity();
                break;

            default:

                throw new IllegalArgumentException("error  availability: " + availability);

        }

    }
    public void notes(String note){
        wait.waitForEnterText(notestxtarea,note);

    }

    public void cancelform() {

        wait.waitForClickability(cancelFormButton);
    }




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


    public String getvalidationmessage() {
        try {
            List<WebElement> messageElements = wait.waitForAllElementsVisible(toastOrPopupMsg);

            if (messageElements != null && !messageElements.isEmpty()) {
                for (WebElement msgElement : messageElements) {
                    if (!msgElement.isDisplayed())
                        continue;

                    String message = msgElement.getText().trim();
                    System.out.println("Toast message: " + message);

                    switch (message) {
                        case "Venue Saved Successfully":

                            return message;

                        case "Venue Updated Successfully":


                            return message;

                        case "Please fill mandatory fields":
                            js.executeScript("window.scrollTo(0, 0);");
                            handlePopupOK();
                          //  cancelform();
                            return "Mandatory fields missing";

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

}
