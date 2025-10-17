package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Dropdownutils;
import utils.Webdriverwaitutils;

import java.time.Duration;

public class QuestionnairePages {

    WebDriver driver;
    Webdriverwaitutils wait;
    JavascriptExecutor js;

    public QuestionnairePages(WebDriver driver) {

        this.driver = driver;
        this.wait = new Webdriverwaitutils(driver);
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//a[@mattooltip='Click to toggle Menu']")
    private WebElement menubar;

    @FindBy(xpath ="//li[@id='menu-li-questions-questionnaire']") private  WebElement clickonquestionaries;
    @FindBy(xpath ="//i[@class='icon-plus-1 ng-star-inserted']") private  WebElement addquestionariesbutton;
    @FindBy(xpath ="//p-select[@placeholder='Select question type']") private  WebElement questiontypedrp;
    @FindBy(xpath ="//p-select[@placeholder='Select behaviour']") private  WebElement selectbehaviourdrp;
    @FindBy(xpath ="//p-select[@placeholder='Select competency']") private  WebElement competencydrp;
    @FindBy(xpath ="//textarea[@placeholder='Enter question']") private  WebElement Enterquestioninputbox;
    @FindBy(xpath ="//button[@id='btnSave']") private  WebElement savequestion;

    @FindBy(xpath = "//input[@placeholder='Enter answer']")private WebElement addanswerbox;

   @FindBy(xpath = "//p-iconfield//input[@role='searchbox']")private WebElement textsearchbox;

    final private By questionoptions=By.xpath("//ul[@role='listbox']//li[@role='option']");
    final private By behaviourptions=By.xpath("//ul[@role='listbox']//li[@role='option']");
    final private By competencyoptions=By.xpath("//ul[@role='listbox']//li[@role='option']");

    @FindBy(xpath = "//p-iconfield//input[@class='p-select-filter p-component p-inputtext']")private WebElement competenctysearchbiputbox;



    @FindBy(css = "input.custom-file-input")private WebElement questionimagefile;
    @FindBy(xpath ="//input[@class='custom-file-input']")private WebElement questionaudiofile;


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

    public void navigatetoQuestionaries() {
        clickElement(menubar);
        wait.waitForVisibility(clickonquestionaries);
        clickElement(clickonquestionaries);
    }


    public void addquestion(){

        clickElement(addquestionariesbutton);
    }

    public void createquestion(String questiontype,String behaviour,String competency,String question,String addanswer){

        Dropdownutils.selectDropdownWithSearch(driver,questiontypedrp,questionoptions,textsearchbox,questiontype);
        Dropdownutils.selectbyvisibletextlistretry(driver,selectbehaviourdrp,behaviourptions,behaviour);
        Dropdownutils.selectDropdownWithSearch(driver,competencydrp,competencyoptions,competenctysearchbiputbox,competency);
        wait.waitForEnterText(Enterquestioninputbox,question);
         wait.waitForEnterText(addanswerbox,addanswer);

    }

    public void addfiles(String imagefile, String audiofile) {
        try {
            js.executeScript("arguments[0].style.display = 'block'; arguments[0].style.visibility = 'visible'; arguments[0].style.height = 'auto';", questionimagefile);

            // Upload image file
            questionimagefile.sendKeys(imagefile);

            js.executeScript("arguments[0].style.display = 'block'; arguments[0].style.visibility = 'visible'; arguments[0].style.height = 'auto';", questionaudiofile);
            questionaudiofile.sendKeys(audiofile);
        } catch (Exception e) {
            System.out.println("File upload failed: " + e.getMessage());
        }
    }
public void savebutton() {
    WebDriverWait shortwait= new WebDriverWait(driver,Duration.ofSeconds(100));

      WebElement ele= shortwait.until(ExpectedConditions.visibilityOf(savequestion));



}



}
