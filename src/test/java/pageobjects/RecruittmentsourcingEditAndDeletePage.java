package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Webdriverwaitutils;

import java.time.Duration;
import java.util.List;

public class RecruittmentsourcingEditAndDeletePage {

    WebDriver driver;
    Webdriverwaitutils wait;
    JavascriptExecutor js;

    public RecruittmentsourcingEditAndDeletePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new Webdriverwaitutils(driver);
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    // ---------- Locators ----------
    @FindBy(xpath = "//a[@mattooltip='Click to toggle Menu']")
    private WebElement menubar;

    @FindBy(xpath = "//span[text()='Sourcing']")
    private WebElement sourcingmodule;

    @FindBy(xpath = "(//li[@id='menu-li-sourcing-recrtmnt-score-config'])[2]")
    private WebElement recruitmentscoring;

    @FindBy(xpath = "//input[@placeholder='Search  Source Type / Sourcing effort / Hiring effort']")
    private WebElement searchbar;

    @FindBy(xpath = "//button[@class='btn btn-primary filter-clear-btn zc-global-search-btn']")
    private WebElement searchbutton;


    @FindBy(xpath = "//i[@id='dropdownBasic1']")
    private WebElement actionbutton;
    @FindBy(xpath = "//i[@class='icon-edit']")
    private WebElement editbutton;


    @FindBy(xpath = "//input[@placeholder='Enter source type']")
    private WebElement sourcetypeinput;

    @FindBy(xpath = "(//input[@placeholder='Enter sourcing effort'])[1]")
    private WebElement SingleRecruitmentsourcingeffort;

    @FindBy(xpath = "(//input[@placeholder='Enter sourcing effort'])[2]")
    private WebElement MultiRecruitmentsourcingeffort;

    @FindBy(xpath = "(//input[@placeholder='Enter sourcing effort'])[3]")
    private WebElement PartRecruitmentsourcingeffort;

    @FindBy(xpath = "(//input[@placeholder='Enter hiring effort'])[1]")
    private WebElement singlierechiringeffort;

    @FindBy(xpath = "(//input[@placeholder='Enter hiring effort'])[2]")
    private WebElement multireqhiringeffort;

    @FindBy(xpath = "(//input[@placeholder='Enter hiring effort'])[3]")
    private WebElement partreqhiringeffort;

    @FindBy(xpath = "//button[@id='btnSave']")
    private WebElement updatebutton;

    @FindBy(xpath = "//button[@class='btn btn-sm btn-primary ng-star-inserted']")
    private WebElement popupOkButton;

    @FindBy(xpath = "//button[@id='dialog-cancel-btn']")
    private WebElement popupCancelButton;

    @FindBy(xpath = "//button[@title='Close']")
    private WebElement cancelFormButton;

    @FindBy(xpath = "//button[@title='Reset']")
    private WebElement resetButton;

    private final By toastOrPopupMsg = By.xpath("//div[(@role='alert' and contains(@class, 'toast-message')) or (contains(@class,'modal-body') and contains(text(),'Please enter all the mandatory fields before saving.'))]");
    private final By toastMsg = By.xpath("//div[@role='alert' and contains(@class, 'toast-message')]");
    private final By popupMessageLocator = By.xpath("//div[contains(@class,'modal-body') and contains(text(),'Please enter all the mandatory fields before saving.')]");

    //---------------------  locators for Delete --------------------------------

    @FindBy(xpath = "//i[@class='icon-delete']")
    private WebElement deleteicon;

    private final By ToastOrPopupMsg = By.xpath("//div[(@role='alert' and contains(@class, 'toast-message')) or (contains(@class,'modal-body') and contains(text(),'Do you want to delete this scoring configuration?'))]");
    private final By ToastMsg = By.xpath("//div[@role='alert' and contains(@class, 'toast-message')]");
    private final By PopupMessageLocator = By.xpath("//div[contains(@class,'modal-body') and contains(text(),'Do you want to delete this scoring configuration?')]");


    @FindBy(xpath = "//button[@id='dialog-okay-btn']")
    private WebElement deleteokbutton;

    @FindBy(xpath = " //table//tbody//tr//td[text()=' No records found ']")
    private WebElement norecordfound;


    //-------------Action Methods for Edit -------------------------
    public void navigatetorecruitmentsourcing() {
        clickElement(menubar);
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", sourcingmodule);
        clickElement(sourcingmodule);
        clickElement(recruitmentscoring);
    }


    public void searchitem(String name) {
        try {
            WebElement ser = wait.waitForClickability(searchbar);
            if (ser != null && ser.isDisplayed()) {
                ser.clear();
                ser.click();
                ser.sendKeys(name);
                wait.waitForClickability(searchbutton).click();
            }
        } catch (Exception e) {

            try {
                js.executeScript("arguments[0].click();", searchbar);
                js.executeScript("arguments[0].value = arguments[1];", searchbar, name);
                js.executeScript("arguments[0].click();", searchbutton);
            } catch (Exception ex) {
                System.out.println("JS click also failed: " + ex.getMessage());
            }
        }
    }


    public void clickedit() {

        clickElement(actionbutton);
        clickElement(editbutton);

    }

    public void clickdeleteok() {
        clickElement(deleteokbutton);

    }


    private void clickElement(WebElement element) {
        try {
            WebElement ele = wait.waitForClickability(element);
            if (ele != null && ele.isDisplayed())
                ele.click();
        } catch (ElementClickInterceptedException e) {
            js.executeScript("arguments[0].click();", element);
        }
    }


    public void updatesourcingconfiguration(String sourcetype, String singlesourcing, String singlehiring,
                                            String multsourcing, String multihiring,
                                            String partsourcing, String partHiring) {

        wait.waitForEnterText(sourcetypeinput, sourcetype);
        wait.waitForEnterText(SingleRecruitmentsourcingeffort, singlesourcing);
        wait.waitForEnterText(singlierechiringeffort, singlehiring);
        wait.waitForEnterText(MultiRecruitmentsourcingeffort, multsourcing);
        wait.waitForEnterText(multireqhiringeffort, multihiring);
        wait.waitForEnterText(PartRecruitmentsourcingeffort, partsourcing);
        wait.waitForEnterText(partreqhiringeffort, partHiring);
    }


    public void clickupdate() {
        clickElement(updatebutton);
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
                        case "Recruitment Scoring Configuration Updated Successfully":
                            waitForToastToDisappear();
                            return message;

                        case "Source Type Already exist":

                            waitForToastToDisappear();
                            cancelform();
                            return message;

                        case "Please enter all the mandatory fields before saving.":
                            js.executeScript("window.scrollTo(0, 0);");
                            handlePopupOK();
                            cancelform();
                            return "Mandatory fields missing";

                        default:
                            cancelform();
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

    public void cancelform() {
        clickElement(cancelFormButton);
    }

    public void resetForm() {
        clickElement(resetButton);
    }


    public void waitForToastToDisappear() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(10));
            shortWait.until(ExpectedConditions.invisibilityOfElementLocated(toastMsg));
        } catch (TimeoutException e) {
            System.out.println("Toast did not disappear .");
        }
    }

    //---------------------Actions Methods For Delete --------------------------------------------

    public void clickdelete() {

        clickElement(actionbutton);
        clickElement(deleteicon);

    }

    public String getdeletevalidationmessage() {
        try {
            List<WebElement> messageElements = wait.waitForAllElementsVisible(toastMsg);

            if (messageElements != null && !messageElements.isEmpty()) {
                for (WebElement msgElement : messageElements) {
                    if (!msgElement.isDisplayed())
                        continue;

                    String message = msgElement.getText().trim();
                    System.out.println("Toast message: " + message);

                    switch (message) {
                        case "Deleted successfully":
                            return message;

                        default:
                            cancelform();
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


    public boolean handledeletePopupOK() {
        try {
            WebElement popupMsg = wait.waitForVisibilityBy(PopupMessageLocator);
            if (popupMsg != null && popupMsg.isDisplayed()) {
                WebElement okBtn = wait.waitForClickability(deleteokbutton);
                try {
                    okBtn.click();
                } catch (Exception ex) {
                    js.executeScript("arguments[0].click();", deleteokbutton);
                }
                return true;
            }
        } catch (Exception e) {
            System.out.println("Popup not displayed: " + e.getMessage());
        }
        return false;
    }

    public boolean isNoRecordFoundDisplayed() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(2));
            WebElement ele = shortWait.until(ExpectedConditions.visibilityOf(norecordfound));
            return ele != null && ele.isDisplayed();
        } catch (Exception e) {

            return false;
        }
    }


}
