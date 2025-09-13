package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Totalcounts {
	
	WebDriver driver;
	Webdriverwaitutils wait;
	
	public Totalcounts(WebDriver driver) {
		this.wait= new Webdriverwaitutils(driver);
		
        this.driver = driver;
	}

	
	public int getcount(By locator) {
	    WebElement element = wait.waitForVisibilityBy(locator);
	    String countText = element.getText().trim();
	    try {
	        return Integer.parseInt(countText);
	    } catch (NumberFormatException e) {
	        System.out.println("Failed to parse count: " + countText);
	        return -1; 
	    }
	}
	
	//total page  count 
	 public static int extractTotalCountFromPaginationText(String pagetext) {
	        if (pagetext == null || pagetext.isEmpty()) {
	            return -1;
	        }

	        try {
	            int startIndex = pagetext.indexOf("Total") + 6; 
	            int endIndex = pagetext.indexOf("Pages") - 2; 
	            String countStr = pagetext.substring(startIndex, endIndex).trim();

	            return Integer.parseInt(countStr);
	            
	            
	        } catch (Exception e) {
	            return -1;
	        }
	    }
	
	
}


