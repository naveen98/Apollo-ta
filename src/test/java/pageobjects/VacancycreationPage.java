package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Datepickutils;
import utils.Dropdownutils;
import utils.Webdriverwaitutils;

import java.util.List;

public class VacancycreationPage {

    WebDriver driver;
    Webdriverwaitutils wait;
    JavascriptExecutor js;
    Datepickutils dt;

    public VacancycreationPage(WebDriver driver) {
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
        this.wait = new Webdriverwaitutils(driver);
        this.dt = new Datepickutils(driver);
        PageFactory.initElements(driver, this);
    }

    //================== LOCATORS ==================//

    @FindBy(xpath = "//a[@mattooltip='Click to toggle Menu']")
    private WebElement menubar;

    @FindBy(xpath = "//li[@id='menu-li-vacancy-vacancy']//span[text()='Vacancies']")
    private WebElement clickvacancymodule;

    @FindBy(xpath = "//button[contains(@class,'btn-primary')]//span[text()='Add']")
    private WebElement AddVacancycreationbutton;

    @FindBy(xpath = "//p-autocomplete//input[@placeholder='Search site']")
    private WebElement siteinput;

    private final By siteoptions = By.xpath("//ul[@role='listbox']//li[@role='option']");

    @FindBy(xpath = "//input[@placeholder='MM/YYYY']")
    private WebElement dateopen;

    private final By yearLocator = By.xpath("//div[@class='bs-datepicker-head']//button[contains(@class,'current')]");
    private final By prevBtn = By.xpath("//div[contains(@class,'previous')]");
    private final By nextBtn = By.xpath("//div[contains(@class,'next')]");
    private final By allMonthsLocator = By.xpath("//td[@role='gridcell']//span");

    @FindBy(xpath = "//p-select[@placeholder='Select job role']")
    private WebElement jobroledrp;

    private final By jobroleoption =
            By.xpath("//ul[@class='p-select-list ng-star-inserted']//li[@role='option']");

    @FindBy(xpath = "//input[@id='no_of_vacancies']")
    private WebElement Noofvacancies;

    @FindBy(xpath = "//span[text()='Save']")
    private WebElement savebutton;

    // Toast or Popup messages
    private final By toastOrPopupMsg =
            By.xpath("//div[(@role='alert' and contains(@class,'toast-message'))     or (contains(@class,'modal-body')     and contains(text(),'Please enter all the mandatory fields before saving'))]");

    @FindBy(xpath = "//button[@id='dialog-okay-btn']")
    private WebElement popupOkButton;

    @FindBy(xpath = "//button[@id='dialog-cancel-btn']")
    private WebElement popupCancelButton;

    // Close form button
    private final By closeFormBtn =
            By.xpath("//button[@class='close' and @title='Close']");



    private final By popupMessageLocator = By.xpath("//div[contains(@class,'modal-body') and contains(text(),'Please enter all the mandatory fields before saving.')]");




    //================== ACTION METHODS ==================//

    public void navigatevacancymodule() {
        clickElement(menubar);
        clickElement(clickvacancymodule);
    }

    public void addvacancyCreation() {
        clickElement(AddVacancycreationbutton);
    }

    public void createVacancy(String inputSite, String expectedSite, String jobRole, String noOfVacancies) {
        safeClick(siteinput);
        Dropdownutils.selectFromAutoSuggest(driver, siteinput, siteoptions, inputSite, expectedSite);
        Dropdownutils.selectbyvisibletextlistretry(driver, jobroledrp, jobroleoption, jobRole);
        wait.waitForEnterText(Noofvacancies, noOfVacancies);
    }

    public void selectdate(String expmonth, String expyear) {
        clickElement(dateopen);
        dt.selectMonthAndYear(yearLocator, prevBtn, nextBtn, expmonth, expyear, allMonthsLocator);
    }

    public void clicksave() {
        clickElement(savebutton);
    }

    //==================  CLICK HANDLING ==================//

    private void clickElement(WebElement element) {
        try {
            wait.waitForClickability(element).click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", element);
        }
    }

    public void safeClick(WebElement element) {
        try {
            js.executeScript("arguments[0].scrollIntoView(true);", element);
            wait.waitForVisibility(element);

            try {
                wait.waitForClickability(element).click();
            } catch (Exception e1) {
                new Actions(driver).moveToElement(element).click().perform();
            }

        } catch (Exception e2) {
            js.executeScript("arguments[0].click();", element);
        }
    }

    //================== POPUP & MESSAGE HANDLING ==================//

    public boolean popupIsVisible() {
        try {
            WebElement popup = wait.waitForVisibilityBy(By.xpath("//div[contains(@class,'modal-body') and contains(text(),'Please enter all the mandatory fields')]"));
            return popup != null && popup.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getPopupText() {
        try {
            return driver.findElement(By.xpath("//div[contains(@class,'modal-body') and contains(text(),'Please enter all the mandatory fields')]"))
                    .getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public void clickPopupOK() {
        try {
            clickElement(popupOkButton);
        } catch (Exception ignored) {}
    }

    public void closeForm() {
        try {
            WebElement closeBtn = wait.waitForVisibilityBy(closeFormBtn);
            js.executeScript("arguments[0].click();", closeBtn);
        } catch (Exception ignored) {}
    }

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
                        case "Vacancy created successfully":

                            return message;

                        case "Vacancy is already added to this month!":
                             closeForm();
                            return message;

                        case "Please enter all the mandatory fields before saving.":
                           // js.executeScript("window.scrollTo(0, 0);");
                            handlePopupOK();
                            closeForm();
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

    //---------------------------------------------------------------

}
