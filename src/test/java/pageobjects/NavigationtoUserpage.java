package pageobjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Webdriverwaitutils;

public class NavigationtoUserpage {


    WebDriver driver;
    Webdriverwaitutils wait;
    JavascriptExecutor js;


    public NavigationtoUserpage(WebDriver driver){
        this.driver=driver;
        this.wait=new Webdriverwaitutils(driver);
        this.js=(JavascriptExecutor)driver;
        PageFactory.initElements(driver,this);

    }

    @FindBy(xpath = "//a[contains(@class, 'sidebar-toggle')]")
    private WebElement Menubar;

    @FindBy(xpath = "//a[contains(@class, 'nav-dropdown-toggle') and .//span[text()='Users']]")
    private WebElement userdrp;

    @FindBy(xpath = "//a[contains(@href, '/users/list') and .//span[text()='Users']]")
    private WebElement userclick;

     @FindBy(xpath = "//h2[contains(text(),'Users')]")private WebElement
            txtdisplay;

    public void navigatetousers() {
        try {
            WebElement menu = wait.waitForClickability(Menubar);
            try {
                menu.click();
            } catch (Exception e) {
                js.executeScript("arguments[0].click();", menu);
            }

            WebElement userdp = wait.waitForClickability(userdrp);
            try {
                userdp.click();
            } catch (Exception e) {
                js.executeScript("arguments[0].click();", userdp);
            }

            WebElement user = wait.waitForClickability(userclick);
            try {
                user.click();
            } catch (Exception e) {
                js.executeScript("arguments[0].click();", user);
            }

        } catch (Exception e) {
            System.out.println("Failed to navigate to users: " + e.getMessage());
        }
    }

    public boolean isdisplayed(){
       try{
         WebElement text=wait.waitForVisibility(txtdisplay);
         System.out.println(text.getText());
         return text.isDisplayed();


       } catch (Exception e) {
            System.out.println("not displayed");

       }
       return false;

    }


}
