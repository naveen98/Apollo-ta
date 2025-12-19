package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Webdriverwaitutils;

import java.time.Duration;
import java.util.List;

public class RecruitmentSourcingPage {

    WebDriver driver;
    Webdriverwaitutils wait;
    JavascriptExecutor js;

    public RecruitmentSourcingPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new Webdriverwaitutils(driver);
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    // ---------- Locators ----------
    @FindBy(xpath = "//a[@mattooltip='Click to toggle Menu']")
    private WebElement menubar;

    @FindBy(xpath = "//a[@class='nav-link nav-dropdown-toggle']//span[text()='Sourcing']")
    private WebElement sourcingmodule;

    @FindBy(xpath = "(//li[@id='menu-li-sourcing-recrtmnt-score-config'])[2]")
    private WebElement recruitmentscoring;

    @FindBy(xpath = "//div//button[@class='btn btn-primary ng-star-inserted']//span[contains(text(),'Add Scoring')]")
    private WebElement addsourcingbutton;

    @FindBy(xpath = "//zc-text//input[@id='source_type']")
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
    private WebElement savebutton;

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

    // ---------- Actions ----------

    private void clickElement(WebElement element) {
        try {
            WebElement ele = wait.waitForClickability(element);
            if (ele != null && ele.isDisplayed())
             ele.click();
        } catch (ElementClickInterceptedException e) {
            js.executeScript("arguments[0].scrollIntoView(true);", element);
            js.executeScript("arguments[0].click();", element);
        }
    }

    public void navigatetorecruitmentsourcing() {
        clickElement(menubar);
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", sourcingmodule);
        clickElement(sourcingmodule);
        clickElement(recruitmentscoring);
    }

    public void waitforaddscourcing(){
        clickElement(addsourcingbutton);
    }

    public void addsourcingbutton() {
        wait.waitForVisibility(addsourcingbutton);
        clickElement(addsourcingbutton);
     wait.waitForVisibility(sourcetypeinput);

    }

    public void createsourcingconfiguration(String sourcetype, String singlesourcing, String singlehiring,
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


    public void clicksave() {
        clickElement(savebutton);
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
                        case "Recruitment Scoring Configuration Saved Successfully":
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

    public void waitfortoast(){
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(10));
            shortWait.until(ExpectedConditions.visibilityOfElementLocated(toastMsg));
        } catch (TimeoutException e) {
            System.out.println("Toast did not appear .");
        }

    }

    public void waitForToastToDisappear() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(10));
            shortWait.until(ExpectedConditions.invisibilityOfElementLocated(toastMsg));
        } catch (TimeoutException e) {
            System.out.println("Toast did not disappear .");
        }
    }


}
