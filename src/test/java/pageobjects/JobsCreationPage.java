package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Dropdownutils;
import utils.Radiobuttons;
import utils.Webdriverwaitutils;

import java.util.List;

public class JobsCreationPage {

    WebDriver driver;
    Webdriverwaitutils wait;
    JavascriptExecutor js;
    Radiobuttons rd;
    public JobsCreationPage(WebDriver driver){


        this.wait=new Webdriverwaitutils(driver);
        this.driver=driver;
        this.js=(JavascriptExecutor) driver;
        this.rd=new Radiobuttons(driver) ;
        PageFactory.initElements(driver,this);


    }

    @FindBy(xpath = "//a[@mattooltip='Click to toggle Menu']")
    private WebElement menubar;

    @FindBy(xpath = "(//span[contains(text(), 'Jobs')])[2]")private WebElement jobmoduleclick;

    @FindBy (xpath="(//span[contains(text(), 'Jobs')])[1]")private WebElement jobmoduletextdisplay;

    @FindBy(xpath="//button[@title= 'Add Job']")private WebElement addjob;

    @FindBy(xpath="//input[@id='title']")private WebElement inputtitle;

    @FindBy(xpath="//input[@id='code']")private WebElement inputcode;

    @FindBy(xpath="//p-select[@placeholder='Select job role']")private WebElement jobroledrp;

    By jobroleoption=By.xpath("//ul[@class='p-select-list ng-star-inserted']//li[@role='option']");

    @FindBy(xpath = "(//iframe[contains(@class,'tox-edit-area__iframe')])[1]")
    private WebElement descriptionIframe;

    @FindBy(xpath = "(//iframe[contains(@class,'tox-edit-area__iframe')])[2]")
    private WebElement rolesIframe;

    @FindBy(xpath="  //button[@id='btnNext']")private WebElement nextbtn;


    //Requirements
    @FindBy(xpath="//p-autocomplete//input[@type='text']")private WebElement inputrequiredskills;

    @FindBy(xpath="//p-multiselect[@placeholder='Select minimum qualification']")private WebElement minimumqualificationdrp;

    @FindBy(xpath="//p-multiselect[@placeholder='Select experience']")private WebElement experiencedrp;

    By requiredskilloptions=By.xpath("//div[@class='p-autocomplete-list-container']//ul//li[@class='p-ripple p-autocomplete-option ng-star-inserted']");

    By minimumqualificationoption=By.xpath("//p-multiselect-item//li[@role='option']");

    By experienceoption=By.xpath("//p-multiselect-item//li[@role='option']");

    @FindBy(xpath = "//textarea[@placeholder='Enter others']")private WebElement textarea;

    @FindBy(xpath = "//button[@id='btNext']")private WebElement reqnextbtn;

       //settings
    @FindBy(xpath="//input[@placeholder='Enter cooling period (days)']")private WebElement coolingperiodsinput;

    @FindBy(xpath="//button[@id='btnSave']")private WebElement savebtn;


    @FindBy(xpath = "//p-iconfield//input[@type='text']")private WebElement searchrolebox;


    private  final By toastMsg = By.xpath("//div[contains(@class, 'toast-message') and contains(@class, 'ng-star-inserted')]");

    @FindBy(xpath="//button[@class='close']")private WebElement cancelform;


    public void navigatetojobs(){

        try{
            WebElement ele= wait.waitForClickability(menubar);
            if(ele!=null&&ele.isDisplayed())
                ele.click();
        }
        catch (Exception e){

            js.executeScript("arguments[0].click();",menubar);

        }

        try{
            WebElement nav=wait.waitForClickability(jobmoduleclick);
            if(nav!=null&&nav.isDisplayed())

                nav.click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();",jobmoduleclick);
        }
    }

    public void clickaddjob(){

       try {
           WebElement add = wait.waitForClickability(addjob);
           if(add!=null&&add.isDisplayed())

               add.click();
       } catch (Exception e) {
           js.executeScript("arguments[0].click();",addjob);

       }
    }

    public void addjob(String title, String code, String roleoption) {
        try {
            wait.waitForEnterText(inputtitle, title);
            wait.waitForEnterText(inputcode, code);

            Dropdownutils.selectDropdownWithSearch(driver, jobroledrp, jobroleoption, searchrolebox, roleoption);

        } catch (Exception e) {
            System.out.println("Error while adding job: " + e.getMessage());
        }
    }



    public void enterDescription(String desctext) {
        // switch into Description iframe
        wait.waitForPresence(By.xpath("(//iframe[contains(@class,'tox-edit-area__iframe')])[1]"));
        driver.switchTo().frame(descriptionIframe);


           WebElement body = wait.waitForPresence(By.id("tinymce"));
           wait.waitForEnterText(body, desctext);
           driver.switchTo().defaultContent();
    }


    public void enterRolesAndResponsibilities(String roletext) {
        wait.waitForPresence(By.xpath("(//iframe[contains(@class,'tox-edit-area__iframe')])[2]"));
        driver.switchTo().frame(rolesIframe);

        WebElement body = wait.waitForPresence(By.id("tinymce"));
       wait.waitForEnterText(body,roletext);
        driver.switchTo().defaultContent();
    }


    public void  clicknext(){

        try{
            WebElement nxt=wait.waitForClickability(nextbtn);
            if(nxt!=null&&nxt.isDisplayed())
                nxt.click();

        } catch (Exception e) {

           js.executeScript("arguments[0].click();",nextbtn);
          }
    }

    //------------------requirement section----------------------

    public void requirement(String inputreq,String expreqskills,String qualification,String experience,String other){

        try{
            Dropdownutils.selectFromAutoSuggest(driver,inputrequiredskills,requiredskilloptions,inputreq,expreqskills);
            Dropdownutils.selectbyvisibletextlistretry(driver,minimumqualificationdrp,minimumqualificationoption,qualification);
            Dropdownutils.selectbyvisibletextlistretry(driver,experiencedrp,experienceoption,experience);
            wait.waitForEnterText(textarea,other);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void clickreqnextbtn(){

        try{
            WebElement nxt=wait.waitForClickability(reqnextbtn);
            if(nxt!=null&&nxt.isDisplayed())
                nxt.click();

        } catch (Exception e) {

            js.executeScript("arguments[0].click();",reqnextbtn);
        }
    }



    //------------------------settings---------------------
    public void coolingperiodselection(String coolingperiod){
      wait.waitForEnterText(coolingperiodsinput,coolingperiod);

    }

    public void savebutton(){
        try{
           wait.waitForVisibility(savebtn);

            WebElement save=wait.waitForClickability(savebtn);
            if(save!=null&&save.isDisplayed())
                save.click();
            System.out.println("save button clicked ");
        } catch (Exception e) {
            js.executeScript("arguments[0],click();",savebtn);
            System.out.println("save button clicked ");

        }
    }


    public String gettoastmessage() {

        String message = "";
        List<WebElement> msg=wait.waitForAllElementsVisible(toastMsg);
        for(WebElement msgs:msg)
        {
            if (msgs != null && msgs.isDisplayed())
                message = msgs.getText();

            return message;
        }
        return "no message displayed";

    }

    public void clickcloseform(){

        try{
            WebElement close=wait.waitForVisibility(cancelform);
            if(close!=null && close.isDisplayed()){
                close.click();
            }

        } catch (Exception e) {
            js.executeScript("arguments[0].click();",cancelform);
        }
    }


}
