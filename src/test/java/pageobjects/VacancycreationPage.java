package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
        this.dt=new Datepickutils(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//a[@mattooltip='Click to toggle Menu']")
    private WebElement menubar;

    @FindBy(xpath = "//span[text()='Vacancy']")
    private WebElement clickvacancymodule;

    @FindBy(xpath = "//span[text()='Add']")
    private WebElement addvacancy;

    @FindBy(xpath = "//p-autocomplete//input[@placeholder='Search site']")
    private WebElement siteinput;


    By siteoptions = By.xpath("//ul[@role='listbox']//li[@role='option']");


    @FindBy(xpath = "//i[@class='icon-calendar ui-state-default ng-star-inserted']")
    private WebElement dateopen;


    // Calendar month/year selection
    private final By yearLocator = By.xpath("//div[@class='bs-datepicker-head']//button[contains(@class,'current')]");
    private final By prevBtn = By.xpath("//div[contains(@class,'bs-datepicker-head')]//button[contains(@class,'previous')]");
    private final By nextBtn = By.xpath("//div[contains(@class,'bs-datepicker-head')]//button[contains(@class,'next')]");
    private final By allMonthsLocator = By.xpath("//td[@role='gridcell']//span");


    @FindBy(xpath = "//p-select[@placeholder='Select job role']")
    private WebElement jobroledrp;

    By jobroleoption = By.xpath("//ul[@class='p-select-list ng-star-inserted']//li[@role='option']");


    @FindBy(xpath = "//p-iconfield//input[@type='text']")
    private WebElement jobrolesearchbox;


    @FindBy(xpath = "//input[@id='no_of_vacancies']")
    private WebElement Noofvacancies;

    @FindBy(xpath = "//input[@id='closed_vacancies']")
    private WebElement NoofaClosedvacancies;


    @FindBy(xpath = "//span[text()='Save']")
    private WebElement savebutton;

    private final By toastOrPopupMsg = By.xpath("//div[(@role='alert' and contains(@class, 'toast-message')) or (contains(@class,'modal-body') and contains(text(),'Please enter all the mandatory fields before saving.'))]");

    private final By toastMsg = By.xpath("//div[@role='alert' and contains(@class, 'toast-message')]");
    private final By popupMessageLocator = By.xpath("//div[contains(@class,'modal-body') and contains(text(),'Please enter all the mandatory fields before saving.')]");
    @FindBy(xpath = "//button[@class='btn btn-sm btn-primary ng-star-inserted']")private WebElement popupOkButton;
    @FindBy(xpath = "//button[@id='dialog-cancel-btn']") private WebElement popupCancelButton;


    @FindBy(xpath = "//button[@title='Close']")private WebElement cancelform;


    public void navigatevacancymodule() {
        try {
            WebElement ele = wait.waitForClickability(menubar);              //Please enter all the mandatory fields before saving
            if (ele != null && ele.isDisplayed())
                ele.click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", menubar);


        }
        try {
            WebElement vac = wait.waitForClickability(clickvacancymodule);
            if (vac != null && vac.isDisplayed())
                vac.click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", clickvacancymodule);
        }

    }

    public void addvacancy() {
        try {
            WebElement add = wait.waitForClickability(addvacancy);
            if (add != null && add.isDisplayed())
                add.click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", addvacancy);
        }
    }

    public void createvacancy(String inputsite,String expectedsite,  String jobrole, String noofvacancies, String noofclosedvacancies) {


        try{
            WebElement site=wait.waitForClickability(siteinput);
           if (site!=null&&site.isDisplayed())
               site.click();

        } catch (Exception e) {
            js.executeScript("arguments[0].click();",siteinput);
        }


        try {

            Dropdownutils.selectFromAutoSuggest(driver,siteinput,siteoptions,inputsite,expectedsite);
            Dropdownutils.selectbyvisibletextlistretry(driver,jobroledrp,jobroleoption,jobrole);
            wait.waitForEnterText(Noofvacancies,noofvacancies);
            wait.waitForEnterText(NoofaClosedvacancies,noofclosedvacancies);

        } catch (Exception e) {
            System.out.println("error : " + e.getMessage());
        }

    }

    public void selectdate(String expmonth, String expyear){

        try{
            wait.waitForClickability(dateopen).click();

             dt.selectMonthAndYear(yearLocator,prevBtn,nextBtn,expmonth,expyear,allMonthsLocator);

        } catch (RuntimeException e) {

            System.out.println("error : " + e.getMessage());
        }

    }

    public void clicksave(){

        try{
            WebElement save=wait.waitForClickability(savebutton);
            if (save!=null&&save.isDisplayed())
                save.click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();",savebutton);

        }
    }





    public String getvalidationmessage() {

        String message = "";
        try {

            List<WebElement> messageElements = wait.waitForAllElementsVisible(toastOrPopupMsg);
            if (messageElements != null && !messageElements.isEmpty()) {
                for(WebElement msgelement:messageElements) {

                    if(msgelement.isDisplayed())
                        message =msgelement.getText();

                    System.out.println("Toast message: " + message);

                    switch (message) {

                        case "Vacancy created successfully":
                            return message;

                        case "Vacancy is not available in this month":
                            return message;

                        case "Please enter all the mandatory fields before saving.":
                            //scroll up and handle
                            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");

                            handlePopupOK();
                            cancelform();

                            return "Mandatory fields missing";

                        default:
                            return message;

                    }
                }


            }else {
                System.out.println("Messge element not visible");
            }
        } catch (Exception e) {

            System.out.println("----------Error ------" + e.getMessage());
        }

        return "No Messege Displayed";
    }


    public boolean handlePopupOK() {
        try {
            WebElement popupMsg = wait.waitForVisibilityBy(popupMessageLocator);
            if (popupMsg != null && popupMsg.isDisplayed()) {
                WebElement okBtn = wait.waitForClickability(popupOkButton);

                try {
                    okBtn.click();
                    System.out.println("clicked ok button");
                } catch (Exception ex) {
                    System.out.println("clicked jsok button");

                    js.executeScript("arguments[0].click();", popupOkButton);
                }
                return true;
            }
        } catch (Exception e) {
            System.out.println("Popup not displayed: " + e.getMessage());
        }
        return false;
    }

    public void cancelform(){

        try{
            WebElement close=wait.waitForClickability(cancelform);
            if (close!=null&&close.isDisplayed())
                close.click();

        } catch (Exception e) {
            js.executeScript("arguments[0].click();",cancelform);

        }
    }


}



