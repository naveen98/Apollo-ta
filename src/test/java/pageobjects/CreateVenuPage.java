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

public class CreateVenuPage {
	WebDriver driver;
	Webdriverwaitutils wait;
    JavascriptExecutor js ;
	public CreateVenuPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.wait = new Webdriverwaitutils(driver);
       this.js = (JavascriptExecutor) driver;

	}

	@FindBy(xpath = "//button[@title='add']//span[contains(text(),'Configure Venue')]")
	private WebElement configurevenubtn;

	@FindBy(xpath = "//textarea[@id='address']")
	private WebElement venuaddresstxt;

    @FindBy(xpath = "//p-dropdown[@placeholder='Select availability in']")
    private WebElement availabilitydrp;


    @FindBy(xpath = "//p-multiselect[@placeholder='Select state']")
    private WebElement statedrp;

    @FindBy(xpath = "(//button[@title='add'])[2]")private WebElement addbtn;

    @FindBy(xpath = "(//p-dropdown[@placeholder='Select state'])[1]") private WebElement innerstatedropdown;
    @FindBy(xpath = "//p-multiselect[@placeholder='Select region']") private WebElement regiondropdown;
    @FindBy(xpath = "//p-multiselect[@placeholder='Select city']") private WebElement citydropdown;
    @FindBy(xpath = "//p-multiselect[@placeholder='Select area']") private WebElement Areacitydropdown;

    @FindBy(xpath = "(//button[@type='button'])[8]") private WebElement finaladdbtn;
    @FindBy(xpath = "//button[@id='btnSave']")private WebElement savebtn;



By availabityoptions = By.xpath("//li[@role='option']");

    By stateoptions = By.xpath("//li[@class='ui-multiselect-item ui-corner-all']");


    By innerstateoptions=By.xpath("//li[@role='option']");

    By regionoptions=By.xpath("//li[@class='ui-multiselect-item ui-corner-all']");

    By cityoptions=By.xpath("//li[@class='ui-multiselect-item ui-corner-all']");

    By areaoptions=By.xpath("//li[@class='ui-multiselect-item ui-corner-all']");


    public void clickAddConfigure() {
        try {
               wait.waitForVisibility(configurevenubtn).click();
        } catch (Exception e) {
                 System.out.println("addcongigure " +  e.getMessage());
        }
    }

    public void clickAdd() {

         try{
             wait.waitForVisibility(addbtn).click();

            } catch (Exception e) {
                js.executeScript("arguments[0].scrollIntoView({block:'center'});", addbtn);
                js.executeScript("arguments[0].click();", addbtn);
            }
    }

    public void createvenu(String venuaddressarea, String availability,
                           String state, String region, String city, String area) {


        wait.waitForEnterText(venuaddresstxt, venuaddressarea);

        Dropdownutils.selectbyvisibletextlist(driver, availabilitydrp, availabityoptions, availability);

        String avail = "";
        if (availability != null) {
            avail = availability.trim().toLowerCase();
        }

        switch (avail) {
            case "state":
                if (!state.isEmpty()) {
                    Dropdownutils.selectbyvisibletextlistretry(driver, statedrp, stateoptions, state);
                }
                break;

            case "region":
                clickAdd();
                if (!state.isEmpty()) {
                    Dropdownutils.selectbyvisibletextlistretry(driver, innerstatedropdown, innerstateoptions, state);
                }
                if (!region.isEmpty()) {
                    Dropdownutils.selectbyvisibletextlistretry(driver, regiondropdown, regionoptions, region);
                }
                wait.waitForVisibility(finaladdbtn).click();
                break;

            case "city":
                clickAdd();
                if (!state.isEmpty()) {
                    Dropdownutils.selectbyvisibletextlistretry(driver, innerstatedropdown, innerstateoptions, state);
                }
                if (!region.isEmpty()) {
                    Dropdownutils.selectbyvisibletextlistretry(driver, regiondropdown, regionoptions, region);
                }
                if (!city.isEmpty()) {
                    Dropdownutils.selectbyvisibletextlistretry(driver, citydropdown, cityoptions, city);
                }
                wait.waitForVisibility(finaladdbtn).click();
                break;

            case "area":
                clickAdd();
                if (!state.isEmpty()) {
                    Dropdownutils.selectbyvisibletextlist(driver, innerstatedropdown, innerstateoptions, state);
                }
                if (!region.isEmpty()) {
                    Dropdownutils.selectbyvisibletextlist(driver, regiondropdown, regionoptions, region);
                }
                if (!city.isEmpty()) {
                    Dropdownutils.selectbyvisibletextlist(driver, citydropdown, cityoptions, city);
                }
                if (!area.isEmpty()) {
                    Dropdownutils.selectbyvisibletextlist(driver, Areacitydropdown, areaoptions, area);
                }
                wait.waitForVisibility(finaladdbtn).click();
                break;

            default:
                throw new IllegalArgumentException("error  availability: " + availability);
        }

        // Save venue
        wait.waitForVisibility(savebtn).click();
    }

}
