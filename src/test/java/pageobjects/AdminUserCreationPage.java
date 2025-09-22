package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Dropdownutils;
import utils.Webdriverwaitutils;

import java.util.List;


public class AdminUserCreationPage {

    WebDriver driver;
    Webdriverwaitutils wait;
    JavascriptExecutor js;

    public AdminUserCreationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new Webdriverwaitutils(driver);
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);

    }
    //locators


    By salutrationdropdown = By.xpath("//p-select[@placeholder='Select']");


    @FindBy(xpath = "//button[@title='Add']")
    private WebElement addbtn;

    @FindBy(xpath = "(//p-select[contains(@placeholder,'Select')])[1]")
    private WebElement salutrationdrp;
    @FindBy(xpath = "//input[@id='first_name']")
    private WebElement firstname;
    @FindBy(xpath = "//input[@id='middle_name']")
    private WebElement middlename;
    @FindBy(xpath = "//input[@id='last_name']")
    private WebElement lastname;
    @FindBy(xpath = "//input[@id='email']")
    private WebElement emailid;
    @FindBy(xpath = "//input[@id='phone']")
    private WebElement mobilenumber;
    @FindBy(xpath = "//input[@id='login_unique']")
    private WebElement username;
    @FindBy(xpath = "//p-select[@placeholder='Select role']")
    private WebElement roledrp;
    @FindBy(xpath = "//p-select[@placeholder='Select reports to']")
    private WebElement reportdrp;
    @FindBy(xpath = "//input[@id='password']")
    private WebElement password;
    @FindBy(xpath = "//input[@id='confirm_password']")
    private WebElement confirmpassword;

    By salutoptions = By.xpath("//ul[@role='listbox']//li[@class='p-ripple p-select-option']");
    By roleoptions = By.xpath("//ul[@role='listbox']//li[@role='option']");
    By reportoptions = By.xpath("//ul[@role='listbox']//li[@role='option']");

    @FindBy(xpath = "//button[@title='Save']")
    private WebElement savebutton;


    By msglocator = By.xpath("//div[contains(@class,'toast-title') or text()='Please fill mandatory fields' or contains(text(),'User saved successfully')]");
    @FindBy(xpath = "(//button[@title='Close'])[1]")
    private WebElement cancelbutton;

    @FindBy(xpath = "(//button[normalize-space()='OK'])[1]")
    private WebElement popokbutton;

    //Action Methods
    public void addbutton() {
        try {
            WebElement add = wait.waitForVisibility(addbtn);
            if (add != null && add.isDisplayed()) {
                add.click();
            }
        } catch (Exception e) {

            js.executeScript("arguments[0].click();", addbtn);

        }
    }

    public void clickcancelform() {
        WebElement cancel = wait.waitForVisibility(cancelbutton);
        if (cancel != null && cancel.isDisplayed())
            cancel.click();
    }

    public void waitaddbtn() {
        WebElement addb = wait.waitForVisibility(addbtn);

    }

    public void usercreation(String sal, String fname, String mname, String lname, String email, String mob, String user, String role, String report, String pwd, String cnfpwd) {

        try {

        /* WebElement drp=wait.waitForPresence(salutrationdropdown);
            if(drp!=null && drp.isDisplayed())*/
            Dropdownutils.selectbyvisibletextlistretry(driver, salutrationdrp, salutoptions, sal);

            wait.waitForEnterText(firstname, fname);
            wait.waitForEnterText(middlename, mname);
            wait.waitForEnterText(lastname, lname);
            wait.waitForEnterText(emailid, email);
            wait.waitForEnterText(mobilenumber, mob);
            wait.waitForEnterText(username, user);
            Dropdownutils.selectbyvisibletextlistretry(driver, roledrp, roleoptions, role);

        /*    WebElement repo=wait.waitForVisibility(reportdrp);
            if(repo!=null && repo.isDisplayed())
*/
            Dropdownutils.selectbyvisibletextlistretry(driver, reportdrp, reportoptions, report);


            wait.waitForEnterText(password, pwd);

            wait.waitForEnterText(confirmpassword, cnfpwd);

        } catch (Exception e) {
            System.out.println(" error ");
        }

    }

    public void clicksave() {
        try {
            WebElement save = wait.waitForVisibility(savebutton);
            if (save != null && save.isDisplayed()) {
                save.click();
            }
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", savebutton);

        }

    }


    //************ handle popup ***************
    public boolean handlepopup() {

        try {
            WebElement popbtn = wait.waitForClickability(popokbutton);
            if (popbtn != null && popbtn.isDisplayed()) {
                try {
                    //((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", popbtn);

                    popbtn.click();
                    //   clickcancelform();

                } catch (Exception e) {
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    js.executeScript("arguments[0].click();", popbtn);


                }
                return true;

            }
        } catch (Exception c) {

            System.out.println("no popup ");
            System.out.println("popokbutton  " + c.getMessage());

        }
        return false;

    }

    public String getvalidationmessage() {

        String message = "";
        try {

            List<WebElement> messageElements = wait.waitForAllElementsVisible(msglocator);
            if (messageElements != null && !messageElements.isEmpty()) {
                for (WebElement msgelement : messageElements) {

                    if (msgelement.isDisplayed())
                        message = msgelement.getText();

                    System.out.println("Toast message: " + message);

                    switch (message) {

                        case "User saved successfully":
                            return message;

                        case "Username Already exists":
                            return "username already exists";

                        case "User Updated successfully":
                            return message;

                        case "Password should be between 6 and 12 characters.,New Password and Confirm Password did not match, please verify.":
                            return message;

                        case "New Password and Confirm Password did not match, please verify.":
                            return message;


                        case "Please fill mandatory fields":
                            //scroll up and handle
                            // ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");

                            handlepopup();

                            return "Mandatory fields missing";

                        default:
                            return message;

                    }
                }


            } else {
                System.out.println("Messge element not visible");
            }
        } catch (Exception e) {

            System.out.println("----------Error ------" + e.getMessage());
        }

        return "No Messege Displayed";
    }

    public void waitForToast() {
        try {
            wait.waitForVisibilityBy(msglocator);
        } catch (Exception e) {
            System.out.println("Toast did not disappear in time: " + e.getMessage());
        }

    }

    public void waitForToastToDisappear() {
        try {
            wait.waitForInvisibilityOfElementLocated(msglocator, 5);
        } catch (Exception e) {
            System.out.println("Toast did not disappear in time: " + e.getMessage());
        }
    }
}
