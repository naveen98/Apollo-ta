package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Webdriverwaitutils {
	
	    public WebDriver driver;
	    public WebDriverWait wait;

	     public Webdriverwaitutils(WebDriver driver) {
	
	        this.driver = driver;
	        this.wait = new WebDriverWait(driver, Duration.ofSeconds(60));
	    }

	    public WebElement waitForVisibility(WebElement element) {
	        return wait.until(ExpectedConditions.visibilityOf(element));
	    }

	    public WebElement waitForClickability(WebElement element) {
	        return wait.until(ExpectedConditions.elementToBeClickable(element));
	    }

	   
	    public WebElement waitForPresence(By locator) {
	        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	    }
	    

	    
	    public Alert waitForAlert() {
	        return wait.until(ExpectedConditions.alertIsPresent());
	    }

	    public boolean waitForTextToBePresent(By locator, String text) {
	        return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
	    }

	    public List<WebElement> waitForAllElementsVisible(By locator) {
	        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	    }

	    public List<WebElement> waitForAllElementsVisible(List<WebElement> elements) {
	        return wait.until(ExpectedConditions.visibilityOfAllElements(elements));
	    }

	    public WebElement waitForVisibilityBy(By locator) {
	        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	    }

	    public WebElement waitForEnterText(WebElement element, String text) {
	        try {
	            WebElement ele = waitForVisibility(element);
	            try {
	                ele.clear();
	                ele.sendKeys(text);
	            } catch (Exception e) {
	                // JavaScript fallback if clear/sendKeys fails
	                JavascriptExecutor js = (JavascriptExecutor) driver;
	                js.executeScript("arguments[0].value = arguments[1];", ele, text);
	            }

	            return ele;
	        } catch (Exception e) {
	            System.out.println("Failed to enter text: " + text + ". Error: " + e.getMessage());
	            throw new RuntimeException("Unable to interact with element to enter text.", e);
	        }
	    }


	    public WebElement clickElement(WebElement locator) {
	    	  
	          WebElement element= wait.until(ExpectedConditions.elementToBeClickable(locator));
	          JavascriptExecutor js=(JavascriptExecutor)driver;
	          js.executeScript("arguments[0].scrollIntoView(true);", element);
	         js.executeScript("arguments[0].click();", element);
	         return element;
	     }
	    public void waitForElementToBeClickable(WebElement locator) {
	        wait.until(ExpectedConditions.elementToBeClickable(locator));
	    }

	    public WebElement waitForClickabilityby(By locator) {
	        return wait.until(ExpectedConditions.elementToBeClickable(locator));
	    }

	    public WebElement waitForPresenceOfElementLocatedby(By locator) {
	        return new WebDriverWait(driver, Duration.ofSeconds(60))
	            .until(ExpectedConditions.presenceOfElementLocated(locator));
	    }
	 
	    
	    public WebElement waitForTextToBePresentsendkeys(WebElement element, String text) {
	        try {
	            // Wait until element is visible
	            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	            wait.until(ExpectedConditions.visibilityOf(element));
	            wait.until(ExpectedConditions.elementToBeClickable(element));

	            try {
	                element.clear();
	                element.sendKeys(text);
	            } catch (Exception e) {
	                JavascriptExecutor js = (JavascriptExecutor) driver;
	                js.executeScript("arguments[0].value='" + text + "';", element);
	            }

	            return element;

	        } catch (Exception e) {
	            System.out.println("Failed to enter text: " + text + ". Error: " + e.getMessage());
	            throw e;
	        }
	    }

		

}
