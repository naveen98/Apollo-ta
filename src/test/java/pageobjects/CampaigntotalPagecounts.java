package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Paginations;
import utils.Webdriverwaitutils;

public class CampaigntotalPagecounts {

    WebDriver driver;
    Webdriverwaitutils wait;
    Paginations pg;

    By paginationTextLocator = By.xpath("//*[contains(text(),'Pages')]");
    By nextButton = By.xpath("//button[@class='p-ripple p-paginator-next']");
    By previousButton = By.xpath("//button[@class='p-ripple p-disabled p-paginator-prev']");

    @FindBy(xpath="//a[@title='Reload']")private WebElement gridrefresh;

    public CampaigntotalPagecounts(WebDriver driver) {
        this.driver = driver;
        this.wait = new Webdriverwaitutils(driver);
        this.pg = new Paginations(driver);
        PageFactory.initElements(driver, this);
    }

    public int getTotalPagesFromText() {
        try {
            WebElement paginationTextElement = wait.waitForVisibilityBy(paginationTextLocator);

            if (paginationTextElement != null && paginationTextElement.isDisplayed()) {
                String paginationText = paginationTextElement.getText();
                System.out.println(" Pagination Text: " + paginationText);

                int totalPages = Paginations.extractTotalCountFromPaginationText(paginationText);
                System.out.println(" Extracted Total Pages: " + totalPages);

                return totalPages;
            } else {
                System.out.println(" Pagination text element not visible.");
                return 0;
            }

        } catch (Exception e) {
            System.out.println(" Exception while getting total pages: " + e.getMessage());
            return 0;
        }
    }


    public WebElement getNextButton() {
        try {
            WebElement nextBtn = wait.waitForVisibilityBy(nextButton);
            if (nextBtn != null && nextBtn.isDisplayed()) {
                return nextBtn;
            } else {
                System.out.println("Next button not visible.");
                return null;
            }
        } catch (Exception e) {
            System.out.println(" Exception clicking Next button: " + e.getMessage());
            return null;
        }
    }

    public WebElement getPreviousButton() {
        try {
            WebElement prevBtn = wait.waitForVisibilityBy(previousButton);
            if (prevBtn != null && prevBtn.isDisplayed()) {
                return prevBtn;
            } else {
                System.out.println(" Previous button not visible.");
                return null;
            }
        } catch (Exception e) {
            System.out.println(" Exception clicking Previous button: " + e.getMessage());
            return null;
        }
    }


    // Navigate to specific page number
    public void navigateToPage(int pageNumber) {
        pg.goToPage(pageNumber);

    }

    // Click next page
    public void clickNextPage() {
        pg.clickNext(getNextButton());
    }

    // Click previous page
    public void clickPreviousPage() {
        pg.clickPrevious(getPreviousButton());
    }

    public int getCurrentPageNumber() {
        try {
            WebElement activePage = driver.findElement(
                    By.xpath("//a[contains(@class,'ui-paginator-page') and contains(@class,'ui-state-active')]"));
            return Integer.parseInt(activePage.getText());
        } catch (Exception e) {
            System.out.println("Could not find current active page: " + e.getMessage());
            return -1;
        }
    }

    public void Gridrefresh() {
        WebElement grid=wait.waitForVisibility(gridrefresh);

        try {
            if(grid!=null&&grid.isDisplayed()) {
                wait.waitForClickability(gridrefresh).click();
            }else {
                System.out.println("Grid Not clickable");
            }

        }
        catch (Exception e) {
            System.out.println("Exception "+e.getMessage());
        }
    }




}

