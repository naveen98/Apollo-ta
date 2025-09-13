package pageobjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Webdriverwaitutils;


public class NavigatetoVenuPages {

	WebDriver driver;
	Webdriverwaitutils wait;

	public NavigatetoVenuPages(WebDriver driver) {

		this.driver = driver;
		this.wait = new Webdriverwaitutils(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//a[@class='icon-bars sidebar-toggle']")
	private WebElement Menubar;
	@FindBy(xpath = "//span[normalize-space()='Venue']")
	private WebElement venumodule;
	@FindBy(xpath = "(//span[contains(text(),'Configure Venue')])[1]")
	private WebElement venumoduletextdisplay;

	public void navigatetovenumodule() {

        try {

            WebElement ele = wait.waitForClickability(Menubar);
            ele.click();
        } catch (Exception e) {

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", Menubar);
        }

        try {
            WebElement ele = wait.waitForClickability(venumodule);
           ele.click();
        } catch (Exception e) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", venumodule);
        }
	}

	public boolean istextdisplayed() {

		try {
			WebElement text = wait.waitForVisibility(venumoduletextdisplay);
			return text.isDisplayed();

		} catch (Exception e) {
			System.out.println("Not displayed");

		}
		return false;

	}

}
