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

    @FindBy(xpath = "//a[@mattooltip='Click to toggle Menu']")
    private WebElement menubar;

    @FindBy(xpath = "//li[@id='menu-li-team-mapping-team-mapping']")
    private WebElement clickteammapping;

    @FindBy(xpath = "//span[text()='Add recruiter team mapping']")
    private WebElement addteammappingbutton;

    @FindBy(xpath = "//p-autocomplete//input[@placeholder='Search user']")
    private WebElement searchUserInput;

    private final By userSearchOptions = By.xpath("//ul[@role='listbox']//li[@role='option']");

    // Candidate allocation
    @FindBy(xpath = "//div[@class='form-group zc-field-radio'][.//span[contains(text(),'Candidate Auto Allocation')]]")
    private WebElement candidateAutoAllocationSection;

    @FindBy(xpath = "//input[@placeholder='Enter max candidateallocation']")
    private WebElement maxCandidateAllocation;

    @FindBy(xpath = "//button[@title='Add']//span[contains(text(),'Add')]")
    private WebElement insideAddButton;

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

    private final By stateOptions = By.xpath("//ul[@role='listbox']//li[@class='p-ripple p-select-option']");
    private final By regionOptions = By.xpath("//ul[@role='listbox']//li[@class='p-ripple p-select-option']");
    private final By cityOptions = By.xpath("//ul[@role='listbox']//li[@class='p-ripple p-select-option']");
    private final By areaOptions = By.xpath("//div//ul[@role='listbox']//p-multiselect-item//li[@role='option']");
    private final By siteOptions = By.xpath("//ul[@role='listbox']//li[@class='p-ripple p-multiselect-option p-focus']");

    @FindBy(xpath = "//p-iconfield//input[@class='p-select-filter p-component p-inputtext']")
    private WebElement allSearchInput;

    @FindBy(xpath="//p-iconfield//input[@class='p-multiselect-filter p-component p-inputtext']")private WebElement areasechbox;

    @FindBy(xpath = "//p-iconfield//input[@type='text']")
    private WebElement regionSearchInput;

    @FindBy(xpath = "//p-iconfield//input[@type='text']")
    private WebElement citySearchInput;

    @FindBy(xpath = "//p-iconfield//input[@type='text']")
    private WebElement areaSearchInput;

    @FindBy(xpath = "(//span[text()='Add'])[2]")
    private WebElement addFormButton;

    @FindBy(xpath = "//button[@id='btnSave']")
    private WebElement saveButton;

    // ------------------ Actions -----------------------

    public void navigateToTeamMapping() {
        clickElement(menubar);
        clickElement(clickteammapping);
    }

    public void clickAddTeamMappingButton() {
        clickElement(addteammappingbutton);
    }

    public void userAdd(String userInput, String userExpected) {
        Dropdownutils.selectFromAutoSuggest(driver, searchUserInput, userSearchOptions, userInput, userExpected);
    }

    public void enterMaxCandidateAllocation(String value) {
        WebElement ele = wait.waitForClickability(maxCandidateAllocation);
        if (ele != null && ele.isDisplayed()) {
            ele.click();
            ele.clear();
            ele.sendKeys(value);
        }
    }

    public boolean selectRadioButtonOption(String labelText, String optionText) {
        try {
            String xpath = "//div[@class='form-group zc-field-radio'][.//span[contains(text(),'" + labelText + "')]]";
            WebElement radioGroup = new WebDriverWait(driver, Duration.ofSeconds(20))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));

            List<WebElement> options = radioGroup.findElements(By.xpath(".//label[contains(@class,'custom-radio')]"));
            for (WebElement option : options) {
                if (option.getText().trim().equalsIgnoreCase(optionText.trim())) {
                    js.executeScript("arguments[0].scrollIntoView({block:'center'});", option);
                    WebElement radioBtn = option.findElement(By.tagName("input"));
                    if (!radioBtn.isSelected()) {
                        clickElement(option);
                    }
                    return radioBtn.isSelected();
                }
            }
            System.err.println("Radio option not found: " + optionText);
        } catch (Exception e) {
            System.err.println("Error selecting radio: " + e.getMessage());
        }
        return false;
    }

    public void fillLocationDetails(String state, String region, String city, String area, String site) {
        Dropdownutils.selectDropdownWithSearch(driver, stateDropdown, stateOptions, allSearchInput, state);
        Dropdownutils.selectDropdownWithSearch(driver, regionDropdown, regionOptions,allSearchInput, region);
        Dropdownutils.selectDropdownWithSearch(driver, cityDropdown, cityOptions,allSearchInput, city);
        Dropdownutils.selectDropdownWithSearch(driver, areaDropdown, areaOptions,areasechbox, area);
        Dropdownutils.selectDropdownWithSearch(driver, siteDropdown, siteOptions,areasechbox, site);
    }

    public void clickInsideAddArea() {
        clickElement(insideAddButton);
    }

    public void clickAddButton() {
        clickElement(addFormButton);
    }

    public void clickSaveButton() {
        clickElement(saveButton);
    }

    public boolean isCandidateAutoAllocationVisible() {
        try {
            return candidateAutoAllocationSection.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isMaxCandidateAllocationVisible() {
        try {
            return maxCandidateAllocation.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ------------------ resuable -----------------------

    private void clickElement(WebElement element) {
        try {
            wait.waitForClickability(element);
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
            try {
                element.click();
            } catch (ElementClickInterceptedException e) {
                System.out.println("Element not clickable, using JS: " + e.getMessage());
                js.executeScript("arguments[0].click();", element);
            }
        } catch (Exception e) {
            System.out.println("Click failed, retrying with JS. Reason: " + e.getMessage());
            js.executeScript("arguments[0].click();", element);
        }
    }

}
