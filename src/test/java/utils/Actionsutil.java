package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

	public class Actionsutil {

		public Actions act;

		public WebDriver driver;

		public Actionsutil(WebDriver driver) {
			this.driver = driver;
			this.act = new Actions(driver);

		}

		// hover on element

		public void hoverelement(WebElement element) {

			act.moveToElement(element).perform();

		}
	    //right click
		public void rightclick(WebElement element) {
			act.contextClick(element).perform();

		}
	    
		
	    //double click
		public void doubleClick(WebElement element) {
			act.doubleClick(element).perform();
		}

		// drag and drop
		public void dragdrop(WebElement source, WebElement target) {
			act.dragAndDrop(source, target).perform();

		}
	      //click and hold
		public void clickAndHold(WebElement element) {
			act.clickAndHold(element).perform();
		}
	   // sendkeys
		
		public void sendKeys(WebElement element, String keys) {
			act.moveToElement(element).sendKeys(keys).perform();
		}

		//hover and click
		
		 public void hoverAndClick(WebElement hoverElement, WebElement clickElement) {
		        act.moveToElement(hoverElement).moveToElement(clickElement).click().perform();
		    }
		 //drag slider
		 public void dragSlider(WebElement slider, int start, int end) {
			    act.dragAndDropBy(slider, start, end).perform();
			}
	}



