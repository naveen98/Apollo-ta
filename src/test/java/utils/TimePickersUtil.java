package utils;

	import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

	public class TimePickersUtil {

	    WebDriver driver;

	    public TimePickersUtil(WebDriver driver) {
	        this.driver = driver;
	    }

	    public void setTime(By timeInputLocator, String time) {
	        try {
	            WebElement timeField = driver.findElement(timeInputLocator);
	            timeField.clear();  
	            timeField.sendKeys(time); 
	            System.out.println("Time set to: " + time);
	        } catch (Exception e) {
	            System.out.println("Failed to set time: " + e.getMessage());
	        }
	    }
	}


