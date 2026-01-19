package pageobjects;

import drivers.DriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Datepickutils;
import utils.Dropdownutils;
import utils.Webdriverwaitutils;

import java.time.Duration;
import java.util.List;

public class CamapaignRecruiterApplicationPage {

    WebDriver driver;
    Webdriverwaitutils wait;
    JavascriptExecutor js;
    Datepickutils dt;

    public CamapaignRecruiterApplicationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new Webdriverwaitutils(driver);
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
        this.dt = new Datepickutils(driver);
    }

    // ================== CAMPAIGN PAGE ==================

    @FindBy(xpath = "//a[@class='icon-bars sidebar-toggle']")
    private WebElement menubar;

    @FindBy(xpath = "//li[@id='menu-li-campaign-campaign']//span[text()='Campaigns']")
    private WebElement campaignmodule;

    @FindBy(xpath = "//div//button[@type='button']//span[contains(text(),' Search')]")
    private WebElement searchbutton;

    @FindBy(xpath = "//div//input[@placeholder='Search Name / Medium / Share Venue Contact Info']")
    private WebElement searchinputbox;

    @FindBy(xpath = "(//div//input[@placeholder='Search '])[1]")
    private WebElement recruitersearchinputbox;

    @FindBy(xpath = "//div[@class='candidate-name']//span[contains(text(),'Apollolabs')]")
    private WebElement rowselection;

    @FindBy(xpath = "//div[@role='tab' and .//span[normalize-space()='Recruiters']]")
    private WebElement recruitertab;

    @FindBy(xpath = "//td[contains(text(),'No records found')]")
    private WebElement norecordfound;

    // ================== RECRUITER POPUP ==================

    @FindBy(xpath = "//div[@class='qr-section']")
    private WebElement recruiterLink;

    @FindBy(xpath = "//button[@class='copy-btn btn btn-primary icon-files-o']")
    private WebElement copyUrlButton;

    @FindBy(xpath = "//button//span[text()='OK']")
    private WebElement okButton;

    @FindBy(xpath = "//button[@aria-label='Close']")
    private WebElement closePopup;

    // ================== LOGIN ==================

    @FindBy(xpath = "//div//zc-number//input[@id='phone']")
    private WebElement mobileNumberInput;

    @FindBy(xpath = "//div//zc-button//button[@id='button']")
    private WebElement sendotpbutton;

    @FindBy(xpath = "//div//zc-number//input[@id='phone_otp']")
    private WebElement otpInput;

    @FindBy(xpath = "//div//button[@id='button']")
    private WebElement verifyotpbutton;

    // ================== APPLICATION FORM ==================

    @FindBy(xpath = "(//div//label[@class='custom-control custom-radio'])[1]")
    private WebElement applyviawebapplication;

    @FindBy(xpath = "(//div//label[@class='custom-control custom-radio'])[2]")
    private WebElement applyviaManual;

    @FindBy(xpath = "//div//zc-text//input[@id='name']")
    private WebElement firstName;

    @FindBy(xpath = "//div//zc-email//input[@id='email']")
    private WebElement emailinput;

    @FindBy(xpath = "//div//input[@placeholder='Enter DOB']")
    private WebElement DATEOFBIRTH;

    @FindBy(xpath = "(//div//input[@placeholder='MM/YYYY'])[1]")private WebElement startyearopen;
    @FindBy(xpath = "(//div//input[@placeholder='MM/YYYY'])[2]")private WebElement endyearopen;


    @FindBy(xpath = "//div//p-select[@placeholder='Select gender']")
    private WebElement genderdropdown;

    @FindBy(xpath = "//div//p-select[@placeholder='Select job role']")
    private WebElement jobroledrp;

    @FindBy(xpath = "//div//p-select[@placeholder='Select Graduation']")
    private WebElement graduationdrp;

    @FindBy(xpath = " //div//p-multiselect[@placeholder='Select skills']//span[@class='p-multiselect-dropdown-icon icon-angle-down ng-star-inserted']")
    private WebElement skillsdrp;

    @FindBy(xpath = "//div//p-select[@placeholder='Select ']")
    private WebElement preferedjoblocation;

    @FindBy(xpath = "//zc-text//input[@placeholder='Enter institution']")
    private WebElement collegeinput;

    @FindBy(xpath = "//zc-text//input[@placeholder='Enter university name']")
    private WebElement universitytype;

    @FindBy(xpath = "//div//button[@id='btnSubmit']")
    private WebElement submitButton;

    // ================== CONDITIONAL FIELDS ==================

    @FindBy(xpath = "//input[@placeholder='Enter total experience']")
    private WebElement totalExperience;

    @FindBy(xpath = "//input[@placeholder='Enter pharmacy retail experience']")
    private WebElement retailExperience;

    @FindBy(xpath = "//div//p-select[@placeholder='Select D.Pharm state']")
    private WebElement dpharmState;

    @FindBy(xpath = "//input[@placeholder='Enter Pharmacy Council Certificate']")
    private WebElement councilNumber;

    @FindBy(css = "input.custom-file-input")
    private WebElement pcLicense;

    // ================== CHECKBOX ==================

    @FindBy(xpath = "//p[contains(text(),'I here by declare')]/ancestor::label/preceding-sibling::input")
    private WebElement ihearbycheckox;

    // ================== DATE PICKER ==================

    private By Monthtextlocator = By.xpath("//div//button[@class='current ng-star-inserted']");
    private By yeartextlocator = By.xpath("//div//button[@class='current']");
    private By alldates = By.xpath("//td[@role='gridcell']");
    private By startnext = By.xpath("//div//button[@class='next']");
    private By startprevious = By.xpath("//div//button[@class='previous']");


    //======================================Graduation ==================================
    // Graduation calendar
    private By graduationYearText =
            By.xpath("//button[contains(@class,'current')]");

    private By graduationPrev =
            By.xpath("//button[contains(@class,'previous')]");

    private By graduationNext =
            By.xpath("//button[contains(@class,'next')]");

    // Month buttons
    private By graduationMonths =
            By.xpath("//td[@role='gridcell']");


    private By toastMsg = By.xpath("//div[contains(@class, 'toast-message') and contains(@class, 'ng-star-inserted')]");




    By toastOrPopupMsg = By.xpath(
            "//div[(@role='alert' and contains(@class,'toast-message')) " +
                    "or (contains(@class,'modal-body') and (contains(text(),'Please fill mandatory fields') " +
                    "or contains(text(),'Please enter OTP')))]");


    By otpmsg = By.xpath("//div[contains(@class,'modal-body') and contains(text(),'Please enter the OTP')]");


    @FindBy(xpath = "//button[@id='dialog-okay-btn']")
    private WebElement popupOkButton;

    @FindBy(xpath = "//button[@id='dialog-cancel-btn']")
    private WebElement popupCancelButton;

    // Close form button
    private final By closeFormBtn =
            By.xpath("//button[@class='close' and @title='Close']");



    // ================== DROPDOWN OPTIONS ==================

    private By genderoptions = By.xpath("//div//p-selectitem//li[@role='option']");
    private By jobroleoptions = By.xpath("//div//p-selectitem//li[@role='option']");
    private By graduationoptions = By.xpath("//div//p-selectitem//li[@role='option']");
    private By skillsoptions = By.xpath("//div//p-multiselect-item//li[@role='option']");
    private By preferedoptions = By.xpath("//div//p-selectitem//li[@role='option']");
    private By dpharmStateOptions = By.xpath("//div//p-selectitem//li[@role='option']");

    // ================== ACTION METHODS ==================

    public void navigatemenu() {
        clickElement(menubar);
    }

    public void navigatecampignmodule() {
        clickElement(campaignmodule);
    }

    public void waitforseachvisible() {
        wait.waitForVisibility(searchinputbox);
    }

    public void searchcampaigns(String name) {
        wait.waitForVisibility(searchinputbox).clear();
        wait.waitForVisibility(searchinputbox).sendKeys(name);
        clickElement(searchbutton);
    }

    public void selectcampaignrow() {
        clickElement(rowselection);
    }

    public void clickrecruitertab() {
        clickElement(recruitertab);
    }

    public void searchcrecruiter(String recruiter) {
        wait.waitForVisibility(recruitersearchinputbox).clear();
        wait.waitForVisibility(recruitersearchinputbox).sendKeys(recruiter);
        clickElement(searchbutton);
    }

    public boolean isNoRecordFoundDisplayed() {
        return driver.findElements(By.xpath("//td[contains(text(),'No records found')]")).size() > 0;
    }

    public void clickRecruiter() {
        clickElement(recruiterLink);
    }

    public void clickCopyUrl() {
        clickElement(copyUrlButton);
    }

    public void clickOkOnAlert() {
        try {
            driver.switchTo().alert().accept();
        } catch (Exception ignored) {

        }
    }

    public void closePopup() {

        clickElement(closePopup);
    }

    public String getCopiedUrlFromClipboard() {
        return (String) js.executeScript("return navigator.clipboard.readText();");


    }

    public void openUrlInNewTab(String url) {

        js.executeScript("window.open()");
        for (String win : driver.getWindowHandles()) {
            driver.switchTo().window(win);
        }
        driver.get(url);
    }

    public void enterMobileNumber(String mobile) {
        wait.waitForVisibility(mobileNumberInput).sendKeys(mobile);
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", sendotpbutton);
    }

    public void clicksendotpbutton() {
        clickElement(sendotpbutton);
    }

    public void enterOtp(String otp) {
        wait.waitForVisibility(otpInput).sendKeys(otp);
    }

    public void clickviawebeapplication() {
        clickElement(applyviawebapplication);
    }

    public boolean isapplywithwebapplicationdisplayed(){

        return wait.waitForVisibility(applyviawebapplication).isDisplayed();


    }

    public void clickApplyWithManual() {
        clickElement(applyviaManual);
    }

    public void DateofBirth(String month, String year, String date) {
        clickElement(DATEOFBIRTH);
        dt.datepickers(Monthtextlocator, yeartextlocator, startprevious, startnext, alldates, month, year, date);
    }

    public void selectgraduationstart(String month, String year) {
        clickElement(startyearopen);

        dt.selectMonthAndYear(graduationYearText, graduationPrev, graduationNext, graduationMonths, month, year);
    }

    public void selectgraduationend(String month, String year) {
        clickElement(endyearopen);
        dt.selectMonthAndYear(graduationYearText, graduationPrev, graduationNext, graduationMonths, month, year);
    }


    public void fillApplicationForm(String name, String email, String gender, String jobRole,
                                    String graduation, String college, String university) {
        wait.waitForEnterText(firstName, name);

        wait.waitForEnterText(emailinput, email);

        Dropdownutils.selectbyvisibletextlistretry(driver, genderdropdown, genderoptions, gender);
        Dropdownutils.selectbyvisibletextlistretry(driver, jobroledrp, jobroleoptions, jobRole);
        Dropdownutils.selectbyvisibletextlistretry(driver, graduationdrp, graduationoptions, graduation);

        wait.waitForEnterText(collegeinput, college);
        wait.waitForEnterText(universitytype, university);

    }

       public void selectlocation(String location){

        Dropdownutils.selectbyvisibletextlistretry(driver, preferedjoblocation, preferedoptions, location);
    }


    public void Setskills(String skills){

        Dropdownutils.selectMultipleByVisibleText(driver, skillsdrp, skillsoptions, skills);


    }

    // ================== CONDITIONAL LOGIC ==================

    public void handleJobRoleCondition(String jobRole, String totalExp, String retailExp) {

        if (jobRole == null) return;

        if (jobRole.equalsIgnoreCase("Pharmacy Assistant")) {

            if (totalExp != null && !totalExp.isBlank()) {
                wait.waitForVisibility(totalExperience)
                        .sendKeys(totalExp);
            }

            if (retailExp != null && !retailExp.isBlank()) {
                wait.waitForVisibility(retailExperience)
                        .sendKeys(retailExp);
            }
        }
    }


    public void handleGraduationCondition(String graduation, String state, String councilNo) {

        if (graduation == null)
            return;

        if (graduation.equalsIgnoreCase("D.Pharmacy") || graduation.equalsIgnoreCase("M.Pharmacy")) {

            if (state != null && !state.isBlank()) {
                Dropdownutils.selectbyvisibletextlistretry(driver, dpharmState, dpharmStateOptions, state);
            }

            if (councilNo != null && !councilNo.isBlank()) {
                wait.waitForVisibility(councilNumber).sendKeys(councilNo);
            }

        }
    }




    public void addfile(String imagefile) {
        try {
            js.executeScript("arguments[0].style.display = 'block'; arguments[0].style.visibility = 'visible'; arguments[0].style.height = 'auto';", pcLicense);

            // Upload image file
            pcLicense.sendKeys(imagefile);

        } catch (Exception e) {
            System.out.println("File upload failed: " + e.getMessage());
        }
    }



    public void selectCheckbox(String checkboxText) {

        if (checkboxText == null || checkboxText.trim().isEmpty()) {
            return;
        }

        String[] values = checkboxText.split(",");

        for (String value : values) {

            String trimmedText = value.trim();

            By checkboxLocator = By.xpath(
                    "//label[.//span[contains(normalize-space(),'" + trimmedText + "')]]");

            WebElement checkboxLabel = wait.waitForVisibilityBy(checkboxLocator);

            js.executeScript("arguments[0].scrollIntoView({block:'center'});", checkboxLabel);
            js.executeScript("arguments[0].click();", checkboxLabel);
        }
    }


    public void clickSubmit() {
        clickElement(submitButton);
    }

    // ================== COMMON CLICK ==================

    private void clickElement(WebElement element) {
        try {
            wait.waitForClickability(element);
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);
            element.click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", element);
        }
    }



    //-----------------------------------------
    public boolean handlePopupOK() {
        try {
            WebElement popupMsg = wait.waitForVisibilityBy(toastOrPopupMsg);
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


    public void closeForm() {
        try {
            WebElement closeBtn = wait.waitForVisibilityBy(closeFormBtn);
            js.executeScript("arguments[0].click();", closeBtn);
        } catch (Exception ignored) {}
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
                        case "Application submitted successfully":
                            return message;

                        case "OTP Sent Successfully":
                            handlePopupOK();
                            return message;

                        case "Please enter the OTP":
                            handlePopupOK();
                            return message;


                        case "Please fill mandatory fields":
                            // js.executeScript("window.scrollTo(0, 0);");
                            handlePopupOK();
                            return "Mandatory fields missing";

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




    //=====================OTP=====================


    @FindBy(xpath="//div//a[@class='nav-link dropdown-toggle icon-users']") private WebElement logoutforuser;

    @FindBy(xpath="//ul//li//a[@class='dropdown-item']") private WebElement logoutbutton;




    public boolean isOtpValidationMessagePresent() {
        try {
            return driver.findElements(otpmsg).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public String getOtpValidationMessageText() {
        return driver.findElement(otpmsg).getText();
    }


    public boolean isDashboardLoaded() {
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                return  driver.getCurrentUrl().contains("https://apollota.v37.dev.zeroco.de/ta/dashboard/app-dashboard");

            } catch (TimeoutException e) {
                return false;


            }


    }



    public void clicklogoutfordashboard(){

        clickElement(logoutforuser);
        clickElement(logoutbutton);
    }

    public void clicklogoutfordashboards() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement profileIcon = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div//a[@class='nav-link dropdown-toggle icon-users']")));
                profileIcon.click();

        WebElement logoutBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//ul//li//a[@class='dropdown-item']")));
        logoutBtn.click();
    }


    public String getotpvalidationmessage() {
        try {
            List<WebElement> messageElements = wait.waitForAllElementsVisible(otpmsg);

            if (messageElements != null && !messageElements.isEmpty()) {
                for (WebElement msgElement : messageElements) {
                    if (!msgElement.isDisplayed())
                        continue;

                    String message = msgElement.getText().trim();
                    System.out.println("Toast message: " + message);

                    switch (message) {

                        case "Please enter the OTP":
                            handlePopupOK();
                            return message;


                        case "Please fill mandatory fields":
                            // js.executeScript("window.scrollTo(0, 0);");
                            handlePopupOK();
                            return "Mandatory fields missing";

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
