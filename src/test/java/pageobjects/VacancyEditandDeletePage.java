package pageobjects;

import io.cucumber.java.hu.De;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Webdriverwaitutils;

import java.util.List;

public class VacancyEditandDeletePage {


    Webdriverwaitutils wait;
    WebDriver driver;
    JavascriptExecutor js;

    public VacancyEditandDeletePage(WebDriver driver){
        this.driver=driver;
        this.js=(JavascriptExecutor) driver;
        this.wait=new Webdriverwaitutils(driver);
        PageFactory.initElements(driver,this);

    }

    @FindBy(xpath = "//a[@mattooltip='Click to toggle Menu']")
    private WebElement menubar;

    @FindBy(xpath = "//span[text()='Vacancy']")
    private WebElement clickvacancymodule;


    @FindBy(xpath="//input[@placeholder='Search Site / Job Role / No Of Vacancies / Closed Vacancies']")private WebElement searchbox;

    @FindBy(xpath="//button[@class='btn btn-primary filter-clear-btn zc-global-search-btn ng-star-inserted']")private WebElement searchbutton;

    @FindBy(xpath = "//div[@class='zc-form-add-btn for-row ng-star-inserted']//i[@class='icon-edit ui-clickable ng-star-inserted']")
    private WebElement editoption;

    @FindBy(xpath = "//input[@id='no_of_vacancies']")
    private WebElement Noofvacancies;

    @FindBy(xpath = "//input[@id='closed_vacancies']")
    private WebElement NoofaClosedvacancies;

    @FindBy(xpath = "//button[@class='btn ml-1 btn-primary ng-star-inserted']")private WebElement updatebutton;
    @FindBy(xpath = "//button[@title='Close']")private WebElement cancelform;
    private final By popupMessageLocator = By.xpath("//div[contains(@class,'modal-body') and contains(text(),'Please enter all the mandatory fields before saving.')]");

    private final By toastOrPopupMsg = By.xpath("//div[(@role='alert' and contains(@class, 'toast-message')) or (contains(@class,'modal-body') and contains(text(),'Please enter all the mandatory fields before saving.'))]");
    private final By toastMsg = By.xpath("//div[@role='alert' and contains(@class, 'toast-message')]");
    @FindBy(xpath = "//button[@class='btn btn-sm btn-primary ng-star-inserted']")private WebElement popupOkButton;
    @FindBy(xpath = "//button[@id='dialog-cancel-btn']") private WebElement popupCancelButton;


    //-----------------------Delete Action Locators -----------------------------

    @FindBy(xpath = "//div[@class='zc-form-add-btn for-row ng-star-inserted']//i[@class='icon-delete ui-clickable ng-star-inserted']") private WebElement Deleteoption;

    private final By popupMsg = By.xpath("//div[@class='modal-body' and text()='Do you want to delete vacancy?']");
    private final By toastpopupmsgs = By.xpath("//div[@role='alert' and contains(@class, 'toast-message')] | //div[@class='modal-body' and text()='Do you want to delete vacancy?']");

    private final By noVacancyMessage = By.xpath("//td[contains(text(), 'No vacancies found')]");


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


    public void searchitem(String name) {
        try {
            WebElement ser = wait.waitForClickability(searchbox);
            if (ser != null && ser.isDisplayed()) {
                ser.clear();
                ser.click();
                ser.sendKeys(name);
                wait.waitForClickability(searchbutton).click();
            }
        } catch (Exception e) {

            try {
                js.executeScript("arguments[0].click();", searchbox);
                js.executeScript("arguments[0].value = arguments[1];", searchbox, name);
                js.executeScript("arguments[0].click();",searchbutton);
            } catch (Exception ex) {
                System.out.println("JS click also failed: " + ex.getMessage());
            }
        }
    }


    public void clickeditbutton() {
        try {

            WebElement present = wait.waitForVisibilityBy(noVacancyMessage);
            if (present != null && present.isDisplayed()) {
                System.out.println("No record found, skipping edit.");
                return;
            }
        } catch (Exception ignore) {

        }

        try {
            WebElement edit = wait.waitForClickability(editoption);
            if (edit != null && edit.isDisplayed()) {
                edit.click();
            }
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", editoption);
        }
    }


    public void editvacancy(String noofvacancy,String noofclosedvacancy)
    {
           try{

               wait.waitForEnterText(Noofvacancies,noofvacancy);
               wait.waitForEnterText(NoofaClosedvacancies,noofclosedvacancy);

           } catch (Exception e) {
               System.out.println("Error : "+ e.getMessage());

           }
    }

    public void clickupdatebutton(){

        try{
            WebElement update=wait.waitForClickability(updatebutton);
            if (update!=null&&update.isDisplayed())
                update.click();

        } catch (Exception e) {
            js.executeScript("arguments[0].click();",updatebutton);

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

                        case "Vacancy Updated Successfully":
                            return message;

                        case "Please enter all the mandatory fields before saving.":
                            //scroll up and handle
                            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");

                            handlePopupOK();

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
  //----------------------------------Delete Action Methods----------------------------------------------


    public void searchdelete(String name) {
        try {
            WebElement ser = wait.waitForClickability(searchbox);
            if (ser != null && ser.isDisplayed()) {
                ser.clear();
                ser.click();
                ser.sendKeys(name);
                wait.waitForClickability(searchbutton).click();
            }
        } catch (Exception e) {

            try {
                js.executeScript("arguments[0].click();", searchbox);
                js.executeScript("arguments[0].value = arguments[1];", searchbox, name);
                js.executeScript("arguments[0].click();",searchbutton);
            } catch (Exception ex) {
                System.out.println("JS click also failed: " + ex.getMessage());
            }
        }
    }

    public void clickdelete() {
        try {
            // Check if No vacancies found
            WebElement present = wait.waitForVisibilityBy(noVacancyMessage);
            if (present != null && present.isDisplayed()) {
                System.out.println("No record found, skipping delete.");
                return;
            }
        } catch (Exception ignore) {

        }

        try {
            WebElement del = wait.waitForClickability(Deleteoption);
            if (del != null && del.isDisplayed()) {
                del.click();

                WebElement pop = wait.waitForVisibilityBy(popupMsg);
                if (pop != null && pop.isDisplayed()) {
                    handlePopupdelete();
                }
            }
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", Deleteoption);
            handlePopupdelete();
        }
    }


    public String gettoastmessage() {

      String message = "";
      try {

          List<WebElement> messageElements = wait.waitForAllElementsVisible(toastpopupmsgs);
          if (messageElements != null && !messageElements.isEmpty()) {
              for(WebElement msgelement:messageElements) {

                  if(msgelement.isDisplayed())
                      message =msgelement.getText();

                  System.out.println("Toast message: " + message);

                  switch (message) {

                      case "Deleted successfully":
                          return message;

                      case "Do you want to delete vacancy?":
                          //scroll up and handle
                          ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");

                          handlePopupdelete();

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

    public boolean handlePopupdelete() {
        try {
            WebElement popupMsg = wait.waitForVisibilityBy(toastpopupmsgs);
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



}
