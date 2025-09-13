package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Paginations {

    WebDriver driver;
    Webdriverwaitutils wait;

    public Paginations(WebDriver driver) {
        this.driver = driver;
        this.wait = new Webdriverwaitutils(driver);
    }

    //  total page count from text
    public static int extractTotalCountFromPaginationText(String pagetext) {
        if (pagetext == null || pagetext.isEmpty())
            return -1;

        try {
            //Total 52, Pages 1 - 6
            int totalIndex = pagetext.indexOf("Total");
            int commaIndex = pagetext.indexOf(",", totalIndex);

            if (totalIndex == -1 || commaIndex == -1)
                return -1;

            String countStr = pagetext.substring(totalIndex + 6, commaIndex).trim();
            return Integer.parseInt(countStr);

        } catch (Exception e) {
            return -1;
        }
    }


    // Navigate to page number
    public void goToPage(int pageNumber) {
        try {
            By pagelocator = By.xpath("//span[@class='ui-paginator-pages']//*[text()='" + pageNumber + "']");
            WebElement pageElement = wait.waitForClickabilityby(pagelocator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", pageElement);
            pageElement.click();
            System.out.println("Navigated to page: " + pageNumber);
        } catch (Exception e) {
            System.out.println("Failed to navigate to page " + pageNumber + " : " + e.getMessage());
        }
    }

    // Click next page button
    public void clickNext(WebElement nextBtnLocator) {
        try {
            WebElement nextBtn = wait.waitForClickability(nextBtnLocator);
            nextBtn.click();
            System.out.println("Clicked next page.");
        } catch (Exception e) {
            System.out.println("Next button not clickable: " + e.getMessage());
        }
    }

    // Click previous page button
    public void clickPrevious(WebElement prevBtnLocator) {
        try {
            WebElement prevBtn = wait.waitForClickability(prevBtnLocator);
            prevBtn.click();
            System.out.println("Clicked previous page.");
        } catch (Exception e) {
            System.out.println("Previous button not clickable: " + e.getMessage());
        }
    }
}
