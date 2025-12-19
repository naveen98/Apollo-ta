package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Webdriverwaitutils;

import java.time.Duration;
import java.util.List;

public class TeamMappingEditandDeletePage {

    WebDriver driver;
    JavascriptExecutor js;
    Webdriverwaitutils wait;
    public TeamMappingEditandDeletePage(WebDriver driver){
        this.driver=driver;
        this.js=(JavascriptExecutor) driver;
        this.wait=new Webdriverwaitutils(driver);
        PageFactory.initElements(driver,this);

    }

    // -------------------- Main Menu ----------------------

    @FindBy(xpath = "//a[@mattooltip='Click to toggle Menu']")
    private WebElement menubar;

    @FindBy(xpath = "//li[@id='menu-li-team-mapping-team-mapping']")
    private WebElement clickTeamMapping;

    //Search and Select Team Mapping Users
    @FindBy(xpath = "//input[@placeholder='Search user ']")
    private WebElement searchBox;
    @FindBy(xpath="//button[@type='button']//span[contains(text(),'Search')]")private WebElement searchbutton;
    @FindBy(xpath = "//i[@class='icon-edit ui-clickable ng-star-inserted']")private WebElement Editoption;
    @FindBy(xpath = "//i[@class='icon-delete ui-clickable ng-star-inserted']")private WebElement Deleteoption;



// -------------------- Candidate Allocation ----------------------

    @FindBy(xpath = "//div[@class='form-group zc-field-radio'][.//span[contains(text(),'Candidate Allocation')]]")
    private WebElement candidateAllocationSection;

    @FindBy(xpath = "//div[@class='form-group zc-field-radio'][.//span[contains(text(),'Candidate Auto Allocation')]]")
    private WebElement candidateAutoAllocationSection;

    @FindBy(xpath = "//button[@id='btnUpdate']")
    private WebElement RecruiterupdateButton;


    //Delete confirmation
    @FindBy(xpath = "//button[@id='dialog-okay-btn']")
    private WebElement deleteconfirmbuttonokbutton;

    @FindBy(xpath="//td[contains(text(),' No records found ')]")private WebElement norecordfound;


    //-------------------Toast Messages---------------------------
    private final By popupMessageLocator = By.xpath("//div[contains(@class,'modal-body') and contains(text(),'Please enter all the mandatory fields before saving.')]");

    private final By toastOrPopupMsg = By.xpath("//div[(@role='alert' and contains(@class, 'toast-message')) or (contains(@class,'modal-body') and contains(text(),'Please enter all the mandatory fields before saving.'))]");
    private final By toastMsg = By.xpath("//div[@role='alert' and contains(@class, 'toast-message')]");
    @FindBy(xpath = "//button[@class='btn btn-sm btn-primary ng-star-inserted']")private WebElement popupOkButton;
    @FindBy(xpath = "//button[@id='dialog-cancel-btn']") private WebElement popupCancelButton;



    public boolean isNoRecordFoundDisplayed() {
        try {
            return wait.waitForVisibility(norecordfound).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }



    //--------------Action Methods--------------------
    public void navigateToTeamMapping() {
        clickElement(menubar);
        clickElement(clickTeamMapping);
    }

    public void searchteammappinguser(String username){
        wait.waitForVisibility(searchBox).clear();
        wait.waitForVisibility(searchBox).sendKeys(username);
        clickElement(searchbutton);
    }

       public void clickeditoption(){
        clickElement(Editoption);

    }

    public void clickdeleteoption(){
        clickElement(Deleteoption);

    }

    public void clickRecruiterupdateButton() {
        clickElement(RecruiterupdateButton);
    }

    public void clickdeleteconfirmbuttonokbutton(){
        clickElement(deleteconfirmbuttonokbutton);
    }


    public boolean isCandidateAllocationVisible() {
        try {
            return candidateAllocationSection.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void waitForEditOption() {
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(15));

        w.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//i[@class='icon-edit ui-clickable ng-star-inserted']")
        ));
    }

    public boolean isNoRecordFoundDisplayedFast() {
        try {
            return driver.findElements(By.xpath("//td[contains(text(),'No records found')]")).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public String getCandidateAllocationValue() {
        try {
            WebElement yesOption = driver.findElement(
                    By.xpath("//div[@class='form-group zc-field-radio'][.//span[contains(text(),'Candidate Allocation')]]//label[contains(.,'Yes')]//input"));

            if (yesOption.isSelected()) {
                return "Yes";
            }
            return "No";
        } catch (Exception e) {
            return "No";
        }
    }




    public boolean isCandidateAutoAllocationVisible() {
        try {
            return candidateAutoAllocationSection.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean selectRadioButtonOption(String labelText, String optionText) {
        try {
            String xpath = "//div[@class='form-group zc-field-radio'][.//span[contains(text(),'" + labelText + "')]]";
            WebElement radioGroup = new WebDriverWait(driver, Duration.ofSeconds(60))
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
            System.out.println("Radio option not found: " + optionText);
        } catch (Exception e) {
            System.out.println("Error selecting radio: " + e.getMessage());
        }
        return false;
    }






    // ---------------------- Common Click ----------------------

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

    //-----------------validation message--------------------------------

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

                        case "User Team Mapping Updated Successfully":
                            return message;

                        case "User Team Deleted Successfully":
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


   //---------------------------------------------Region Hr ------------------------------------------

    @FindBy(xpath="(//div[@role='tab'])[2]")private WebElement regionHRmappingtab;



    public void clickregionHRmappingtab(){
        clickElement(regionHRmappingtab);
    }


    @FindBy(xpath="(//div[@role='tab'])[1]")private WebElement clickrecruitertab;



    public void clickRecruitertab(){
        clickElement(clickrecruitertab);
    }






}
