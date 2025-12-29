package pageobjects;

import org.apache.commons.collections4.bag.SynchronizedSortedBag;
import org.bouncycastle.math.ec.WNafL2RMultiplier;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Dropdownutils;
import utils.Webdriverwaitutils;

import java.time.Duration;
import java.util.List;

public class TeamMappingPage {

    WebDriver driver;
    Webdriverwaitutils wait;
    JavascriptExecutor js;

    public TeamMappingPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new Webdriverwaitutils(driver);
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }


    // -------------------- Main Menu ----------------------


    @FindBy(xpath = "//a[@mattooltip='Click to toggle Menu']")
    private WebElement menubar;

    @FindBy(xpath = "//li[@id='menu-li-team-mapping-team-mapping']")
    private WebElement clickTeamMapping;

    @FindBy(xpath = "//div[contains(@class,'group-actions')]//button[.//i[contains(@class,'add')]]")
    private WebElement addRecruiterButton;


    // user autosuggest input

    @FindBy(xpath = "//div//p-autocomplete//input[contains(@placeholder,'Search user')]")
    private WebElement searchUserInput;

    private final By userSearchOptions =
            By.xpath("//p-overlay//ul[@role='listbox']//li[@role='option']");


    //  radio allocations

    @FindBy(xpath = "//div[@class='form-group zc-field-radio'][.//span[contains(text(),'Candidate Allocation')]]")
    private WebElement candidateAllocationSection;

    @FindBy(xpath = "//div[@class='form-group zc-field-radio'][.//span[contains(text(),'Candidate Auto Allocation')]]")
    private WebElement candidateAutoAllocationSection;



    //       location and searches

    @FindBy(xpath = "//p-iconfield//input[@role='searchbox']")
    private WebElement commonSearchInput;

    @FindBy(xpath = "//div//button[@id='btnSave']")
    private WebElement RecruitersaveButton;

    @FindBy(xpath = "//p-select[@placeholder='Select state']")
    private WebElement stateDropdown;

    @FindBy(xpath = "//p-select[@placeholder='Select region']")
    private WebElement regionDropdown;

    @FindBy(xpath = "//p-select[@placeholder='Select city']")
    private WebElement cityDropdown;

    @FindBy(xpath = "//p-multiselect[@placeholder='Select area']")
    private WebElement areaDropdown;

    @FindBy(xpath = "//p-multiselect[@placeholder='Select site']")
    private WebElement siteDropdown;

    private final By dropdownOptions =
            By.xpath("//ul[@role='listbox']//li[@role='option']");

    private final By areaOptions =
            By.xpath("//p-multiselect-item//li[@role='option']");


    //  aite add form

    @FindBy(xpath = "//div//button[@title='Add']//span[contains(text(),'Add')]")
    private WebElement addsitesbutton;

    @FindBy(xpath = "//div//p-autocomplete//input[@placeholder='Search site']")
    private WebElement siteSearchInput;

    private final By siteInputOptions =
            By.xpath("//ul[@role='listbox']//li[contains(@class,'p-autocomplete-option')]");

       @FindBy(xpath = "//div//button[@id='btnAdd']")
       private WebElement addSiteSaveButton;



    // settings icon

    @FindBy(xpath = "//p-table//table//tbody//tr//td//div//i[@class='icon-settings ui-clickable ng-star-inserted']")
    private WebElement settingsicons;

    @FindBy(xpath = "//button[@class='close']")
    private WebElement closeform;


    @FindBy(xpath="(//div[@role='tab'])[1]")private WebElement clickrecruitertab;

   private final By recruitertab =
                     By.xpath("(//div[@role='tab'])[1]");




     /*  public void clickRecruitertab(){
       try {
           WebElement ele = wait.waitForPresence(recruitertab);

           if (ele != null && ele.isDisplayed())
               ele.click();
       }
         catch (Exception e){
            js.executeScript("arguments[0].click();",clickrecruitertab);         }

    }

*/




    public void clickRecruitertab() {
        WebElement ele = wait.waitForPresence(recruitertab);

        try {
            Actions actions = new Actions(driver);
            actions.moveToElement(ele).perform();
            ele.click();
        } catch (Exception e) {
            System.out.println("Normal click failed, using JS click");
            js.executeScript("arguments[0].click();", ele);
        }
    }



    //   team mapping search

    @FindBy(xpath = "//div[@class='ui-inputgroup ng-star-inserted']//input[@placeholder='Search user ']")
    private WebElement searchBox;

    @FindBy(xpath = "//div//button[@class='btn btn-primary filter-clear-btn zc-global-search-btn ng-star-inserted']//span[contains(text(),' Search')]")
    private WebElement searchbutton;



    //     navigation teammapaing

    public void navigateToTeamMapping() {
        clickElement(menubar);
        clickElement(clickTeamMapping);
    }

    public void waitForRecruiterAdd() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//button[.//span[contains(text(),'Add recruiter')]]")
        ));
    }

    public void clickAddRecruiterTeamMappingButton() {

        By locator = By.xpath("//div//button[@title='Add Recruiter']");

        for (int i = 1; i <= 3; i++) {
            try {
                WebElement btn = driver.findElement(locator);
                btn.click();
                System.out.println("Clicked on attempt: " + i);
                break;
            } catch (StaleElementReferenceException e) {
                System.out.println("Retry due to stale element: " + i);
            }
        }
    }



    //   user autosuggest method

    public void userAdd(String userInput, String userExpected) {
        Dropdownutils.selectFromAutoSuggest(
                driver,
                searchUserInput,
                userSearchOptions,
                userInput,
                userExpected
        );
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


    public boolean isCandidateAutoAllocationVisible() {
        try {
            return candidateAutoAllocationSection.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }



    // sit selection

    public void clickAddSiteButton() {

        clickElement(addsitesbutton);
    }

    public void addSites(String input, String expected) {
        Dropdownutils.selectFromAutoSuggest(
                driver,
                siteSearchInput,
                siteInputOptions,
                input,
                expected
        );
    }

    public void clickAddSiteSave() {

        clickElement(addSiteSaveButton);
    }

    public void waitforaddsitesbutton(){
        try{

        WebElement ele= wait.waitForVisibility(addSiteSaveButton);
        if(ele!=null&&ele.isDisplayed()) {
            System.out.println("Add site button is visible");
            ele.click();
        }


        }catch (Exception e){
            System.out.println("Exception occurred while clicking add site button: " + e.getMessage());
            js.executeScript("arguments[0].click();",addSiteSaveButton);

        }

    }



    //  recruiter save button

    public void clickRecruiterSaveButton() {
        clickElement(RecruitersaveButton);
    }



    // teammapping search
    public void searchTeamMappingUser(String username) {

        int attempt = 1;
        int maxAttempts = 3;

        while (attempt <= maxAttempts) {
            try {
                WebElement box = wait.waitForVisibility(searchBox);
                box.clear();
                box.sendKeys(username);
                clickElement(searchbutton);
                System.out.println("Search successful on attempt: " + attempt);
                break;
            } catch (Exception e) {
                System.out.println("Retry search attempt: " + attempt);
                if (attempt == maxAttempts) {
                    throw e;
                }
                attempt++;
            }
        }
    }




    // settings icon
    public void clickSettingIcon() {
        clickElement(settingsicons);
    }

    public void waitForSettingsForm() {
        wait.waitForVisibility(closeform);
    }

    public boolean isCloseDisplayed() {
        try {
            return closeform.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void closeSettingsForm() {
        clickElement(closeform);
    }

    public boolean isNoRecordFoundDisplayedFast() {

        return driver.findElements(By.xpath("//td[contains(text(),'No records found')]")).size() > 0;
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


    //Region hr mapping selection
    @FindBy(xpath = "(//div[@role='tab'])[2]")
    private WebElement regionHRmappingtab;

    @FindBy(xpath = "//div//button[@class='btn btn-primary ng-star-inserted']//span[contains(text(),'Add Region HR')]")
    private WebElement addregionhrbutton;

    @FindBy(xpath="//div//button[@title='Add']")private WebElement Addregionhrformbutton;


    @FindBy(xpath = "//div[contains(@class,'modal')]//p-autocomplete//input[@placeholder='Search user']")
    private WebElement regionhrsearchinput;

    private final By regionhrsearchoptions =
            By.xpath("//ul[@role='listbox']//li[@role='option']");

    @FindBy(xpath = "//div//button[@id='btnSave']")
    private WebElement regionhrsavebutton;


    // ---------- state / region dropdowns ----------
    @FindBy(xpath = "//p-select[@placeholder='Select state']")
    private WebElement hrstatedrp;

    @FindBy(xpath = "//p-multiselect[@placeholder='Select region']")
    private WebElement hrregiondrp;

    @FindBy(xpath = "//button[@id='btnAdd']//span[contains(text(),'Add')]")
    private WebElement hraddregionsavebutton;

    private final By hrstateoption =
            By.xpath("//p-selectitem//li[@role='option']");

    private final By hrregionoption =
            By.xpath("//p-multiselect-item//li[@role='option']");

    @FindBy(xpath = "//p-iconfield//input[@role='searchbox']")
    private WebElement hrregiondrpcommonsearchinput;


    // -------- Region HR Methods ---------
    public void clickRegionHRmappingtab() {
        clickElement(regionHRmappingtab);
    }

    public void clickAddRegionHRbutton() {
        clickElement(addregionhrbutton);
    }

    public void addRegionHR(String input, String expected) {
        Dropdownutils.selectFromAutoSuggest(
                driver,
                regionhrsearchinput,
                regionhrsearchoptions,
                input,
                expected
        );
    }

    public void clickRegionHRsave() {

        clickElement(regionhrsavebutton);
    }

    public void clickHRSettingIcon() {

        clickElement(settingsicons);
    }

    public void clickAddRegionsButton() {
        clickElement(addregionhrbutton);
    }

    public void clickAddRegionInsideFormButton() {

        clickElement(Addregionhrformbutton);
    }

    //Addregionhrformbutton
    public void selectHRStateRegion(String state, String region) {
        Dropdownutils.selectDropdownWithSearch(driver, hrstatedrp, hrstateoption, commonSearchInput, state);
        Dropdownutils.selectDropdownWithSearch(driver, hrregiondrp, hrregionoption, hrregiondrpcommonsearchinput, region);
    }

    public void clickHRAddRegionSave() {
        clickElement(hraddregionsavebutton);
    }

}
