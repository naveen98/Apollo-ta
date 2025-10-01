package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;

public class Datepickutils {
	public WebDriver driver;
	public Webdriverwaitutils wait;

	public Datepickutils(WebDriver driver) {
		this.driver = driver;
		this.wait = new Webdriverwaitutils(driver);

	}

	public void datepickers(By monthyearLocator, By nextButton, By allDates, String expMonth, String expYear,String expDate) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		try {
			// select month
			while (true) {

				String monthyeartext = wait.waitForVisibilityBy(monthyearLocator).getText();

				String[] monthyear = monthyeartext.split(" ");

				String currentmonth = monthyear[0];

				String currentyear = monthyear[1];

				System.out.println("Current Month: " + currentmonth + " " + "currentyear :" + currentyear);

				if (currentmonth.equals(expMonth) && currentyear.equals(expYear)) {


				// next
				WebElement nextbtn = wait.waitForVisibilityBy(nextButton);
				js.executeScript("arguments[0].click();", nextbtn);

			}

		}
		}catch (Exception e) {
			System.out.println("Month year invalid selection  :" );

		}


		// select the date
		try {

			List<WebElement> dates = wait.waitForAllElementsVisible(allDates);

			for (WebElement d : dates) {

				String datemsg = d.getText();

				String parts[] = datemsg.split("\\s+");
				String date = parts[0];

				// String price = parts[1];

			//	System.out.println(date);

				if (date.equals(expDate)) {

					js.executeScript("arguments[0].click();", d);

					break;

				} else {

					System.out.println("Invalid Date");
				}
			}

		} catch (Exception e) {

			System.out.println("Invalid Date Selection" + e.getMessage());
		}

	}


	//both cases can handle

	  public void datepickers(By monthLocator, By yearLocator, By prevBtn, By nextBtn, By allDates, String expMonth, String expYear, String expDate) {
	        JavascriptExecutor js = (JavascriptExecutor) driver;
	        String[] monthList = {"January", "February", "March", "April", "May", "June",
	                              "July", "August", "September", "October", "November", "December"};

	        try {
	            while (true) {
	                String currentMonth = wait.waitForVisibilityBy(monthLocator).getText().trim();
	                String currentYear = wait.waitForVisibilityBy(yearLocator).getText().trim();

	                int currentYearNum = Integer.parseInt(currentYear);
	                int expYearNum = Integer.parseInt(expYear);
	                int currentMonthIndex = Arrays.asList(monthList).indexOf(currentMonth);
	                int expMonthIndex = Arrays.asList(monthList).indexOf(expMonth);

	                if (currentMonth.equalsIgnoreCase(expMonth) && currentYear.equalsIgnoreCase(expYear)) {
	                    break;
	                } else if (currentYearNum > expYearNum || (currentYearNum == expYearNum && currentMonthIndex > expMonthIndex)) {
	                    WebElement prev = wait.waitForClickabilityby(prevBtn);
	                    js.executeScript("arguments[0].click();", prev);
	                } else {
	                    WebElement next = wait.waitForClickabilityby(nextBtn);
	                    js.executeScript("arguments[0].click();", next);
	                }
	                Thread.sleep(1000);
	            }
	        } catch (Exception e) {
	            System.out.println("Month/Year selection failed: " + e.getMessage());
	        }


	        //Date Selection
	        try {
	            List<WebElement> dates = wait.waitForAllElementsVisible(allDates);

	            for (WebElement d : dates) {

	                String datetext = d.getText().trim();

	             //   System.out.println("Date : " + datetext);

	                if (!datetext.isEmpty() && datetext.equals(expDate)) {
	                    try {
	                        wait.waitForClickability(d).click();
	                        //System.out.println("Date clicked using WebDriver: " + expDate);
	                    } catch (Exception clickEx) {
	                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", d);
	                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", d);
	                     //   System.out.println("Date clicked using Js: " + expDate);
	                    }
	                    break;
	                }
	            }
	        } catch (Exception e) {
	            System.out.println("Date selection failed: " + e.getMessage());
	        }
	  }



        public void selectMonthAndYear(By yearLocator, By prevBtn, By nextBtn, String expMonth, String expYear, By allMonthsLocator) {

            JavascriptExecutor js = (JavascriptExecutor) driver;

            try {
                while (true) {
                    String currentYear =wait.waitForVisibilityBy(yearLocator).getText().trim();
                    int currentYearNum = Integer.parseInt(currentYear);
                    int expYearNum = Integer.parseInt(expYear);

                    if (currentYearNum == expYearNum) {
                        break;
                    } else if (currentYearNum > expYearNum) {
                        WebElement prev = wait.waitForClickabilityby(prevBtn);
                        js.executeScript("arguments[0].click();", prev);
                    } else {
                        WebElement next = wait.waitForClickabilityby(nextBtn);
                        js.executeScript("arguments[0].click();", next);
                    }

                  //  Thread.sleep(5000);
                }

                List<WebElement> monthCells = wait.waitForAllElementsVisible(allMonthsLocator);

                for (WebElement cell : monthCells) {
                    String cellText = cell.getText().trim();
                    if (!cellText.isEmpty() && cellText.equalsIgnoreCase(expMonth)) {
                        js.executeScript("arguments[0].click();", cell);
                        break;
                    }
                }

            } catch (Exception e) {

                System.out.println("Month/Year selection failed: " + e.getMessage());
            }
        }
    }



	



