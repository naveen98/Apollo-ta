package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class Dropdownutils {

    public static WebDriver driver;

    public static void selectbyvisibletext(WebDriver driver, String drplocator, String text) {

        try {
            WebElement drpElement = driver.findElement(By.xpath(drplocator));
            Select s = new Select(drpElement);
            s.selectByVisibleText(text);

            System.out.println("Selected: " + text);

        } catch (Exception e) {
            System.out.println(" option not found: " + e.getMessage());
        }
    }

    public static void selectbyoptions(WebDriver driver, String drplocator, String text) {

        try {

            WebElement drpelement = driver.findElement(By.xpath(drplocator));

            Select s = new Select(drpelement);

            List<WebElement> options = s.getOptions();

            System.out.println(options.size()); // count

            // print those options
            for (int i = 0; i < options.size(); i++) {

                s.selectByVisibleText(text);

                System.out.println(options.get(i).getText());
            }
        } catch (Exception c) {

            System.out.println("not found : " + c.getMessage());
        }

    }

    public static void selectCheckboxFromDropdown(WebDriver driver, WebElement accessTypeDropdown, By optionsLocator, String expectedLabel) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Step 1: Click the drop down
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(accessTypeDropdown));
            // js.executeScript("arguments[0].scrollIntoView(true);", dropdown);
            js.executeScript("arguments[0].click();", dropdown);

            // Step 2: Wait for the options panel (not just options)
            wait.until(ExpectedConditions.presenceOfElementLocated(optionsLocator));
            List<WebElement> options = driver.findElements(optionsLocator);

            boolean found = false;

            for (WebElement option : options) {
                String labelText = option.getText().trim();
                boolean isSelected = option.getAttribute("class").contains("ui-state-highlight");

                if (labelText.equalsIgnoreCase(expectedLabel)) {
                    if (isSelected) {
                        System.out.println("Option already selected: " + labelText);
                        return;
                    }

                    js.executeScript("arguments[0].scrollIntoView(true);", option);
                    js.executeScript("arguments[0].click();", option);
                    found = true;
                    System.out.println("Selected checkbox option: " + labelText);
                    break;
                }
            }

            if (!found) {
                System.out.println(" Checkbox option not found in dropdown: " + expectedLabel);
            }

        } catch (Exception e) {
            System.out.println(" Exception while selecting checkbox from dropdown: " + e.getMessage());
        }
    }

    public static void selectbyvisibletextlist(WebDriver driver, WebElement drplocator, By listlocator,
                                               String visibletext) {
        try {
            WebDriverWait waits = new WebDriverWait(driver, Duration.ofSeconds(30));
            JavascriptExecutor js = (JavascriptExecutor) driver;


            try {
                waits.until(ExpectedConditions.elementToBeClickable(drplocator)).click();
            } catch (Exception e) {
                js.executeScript("arguments[0].click();", drplocator);
            }

            // Wait for options to appear
            List<WebElement> options = waits.until(ExpectedConditions.presenceOfAllElementsLocatedBy(listlocator));
            boolean found = false;

            // System.out.println(options.size());

            for (WebElement option : options) {
                String text = option.getText().trim();
                // System.out.println(text);
                if (text.equalsIgnoreCase(visibletext.trim())) {

                    js.executeScript("arguments[0].scrollIntoView(true);", option);


                    try {
                        waits.until(ExpectedConditions.elementToBeClickable(option)).click();
                    } catch (Exception ex) {

                        waits.until(ExpectedConditions.elementToBeClickable(option)).click();
                        js.executeScript("arguments[0].click();", option);


                    }
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Dropdown option not found: " + visibletext);
            }
        } catch (Exception e) {
            System.out.println("Dropdown selection failed: " + e.getMessage());
        }
    }

    public static void safeSelectFromAutoSuggest(WebDriver driver, WebElement inputField, By optionsLocator,
                                                 String inputText, String expectedOptionText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        boolean found = false;

        try {
            inputField.clear();
            inputField.sendKeys(inputText);

            for (int attempt = 0; attempt < 3 && !found; attempt++) {
                try {
                    List<WebElement> suggestions = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(optionsLocator));
                    for (WebElement suggestion : suggestions) {
                        String text = suggestion.getText().trim();
                        if (text.equalsIgnoreCase(expectedOptionText.trim())) {
                            try {
                                suggestion.click();
                            } catch (Exception e) {
                                js.executeScript("arguments[0].click();", suggestion);
                            }
                            found = true;
                            System.out.println("Selected autosuggest option: " + text);
                            break;
                        }
                    }
                } catch (StaleElementReferenceException sere) {
                    System.out.println("Stale element in autosuggest, retrying... attempt " + attempt);
                } catch (Exception e) {
                    System.out.println("Retry " + attempt + " for autosuggest: " + expectedOptionText);
                }
            }

            if (!found) {
                System.out.println("Expected option not found in autosuggest: " + expectedOptionText);
            }
        } catch (Exception e) {
            System.out.println("Auto-suggestion selection failed: " + e.getMessage());
        }
    }


    public static void selectFromAutoSuggest(WebDriver driver, WebElement inputField, By optionsLocator,
                                             String inputText, String expectedOptionText) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            inputField.clear();
            inputField.sendKeys(inputText);

            List<WebElement> suggestions = wait
                    .until(ExpectedConditions.presenceOfAllElementsLocatedBy(optionsLocator));
            boolean found = false;

            for (WebElement suggestion : suggestions) {
                String text = suggestion.getText().trim();
                if (text.equalsIgnoreCase(expectedOptionText.trim())) {
                    try {
                        suggestion.click();

                    } catch (Exception e) {
                        JavascriptExecutor js = (JavascriptExecutor) driver;
                        js.executeScript("arguments[0].click();", suggestion);
                    }
                    found = true;
                    break;

                }
            }
            if (!found) {

               // System.out.println("Expected option not found in autosuggest: " + expectedOptionText);

            }
        } catch (Exception e) {
            System.out.println("Auto-suggestion selection failed: " + e.getMessage());
        }
    }

    public static void selectFromAutoSuggestdrp(WebDriver driver, WebElement dropdown, By optionsLocator,
                                                String searchText, String matchText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Click dropdown to show options
        dropdown.click();

        List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(optionsLocator));

        for (WebElement option : options) {
            String text = option.getText().trim();
            if (text.equalsIgnoreCase(matchText)) {
                option.click();
                break;
            }
        }
    }

    public static void selectbyvisibletextlistup(WebDriver driver, WebElement drplocator, By listlocator, String visibletext) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", drplocator);
            try {
                wait.until(ExpectedConditions.elementToBeClickable(drplocator)).click();
            } catch (Exception e) {
                js.executeScript("arguments[0].click();", drplocator);
            }

            Thread.sleep(1000);

            wait.until(ExpectedConditions.presenceOfElementLocated(listlocator));
            List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(listlocator));

            boolean found = false;
            for (WebElement option : options) {
                if (option.getText().trim().equalsIgnoreCase(visibletext.trim())) {
                    try {
                        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
                    } catch (Exception ex) {
                        js.executeScript("arguments[0].click();", option);
                    }
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Dropdown option not found: " + visibletext);
            }

        } catch (Exception e) {
            System.out.println("Dropdown selection failed: " + e.getMessage());
        }
    }

    public static void selectByVisibleTextFluent(WebDriver driver, WebElement dropdown, By listLocator, String visibleText) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            // Try to click the dropdown to open
            try {
                dropdown.click();
            } catch (Exception e) {
                js.executeScript("arguments[0].click();", dropdown);
            }

            // Fluent wait to load the list options
            FluentWait<WebDriver> fluentWait = new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(50))
                    .pollingEvery(Duration.ofMillis(500))
                    .ignoring(NoSuchElementException.class);

            // Wait for dropdown options to be present
            List<WebElement> options = fluentWait.until(drv -> {
                List<WebElement> elems = drv.findElements(listLocator);
                return elems.isEmpty() ? null : elems;
            });

            boolean found = false;

            for (WebElement option : options) {
                String text = option.getText().trim();
                if (text.equalsIgnoreCase(visibleText.trim())) {
                    try {
                        option.click();
                    } catch (Exception e) {
                        js.executeScript("arguments[0].click();", option); // JS fallback click
                    }
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Dropdown value not found: " + visibleText);
            }

        } catch (Exception e) {
            System.out.println("Dropdown  failed: " + e.getMessage());
        }
    }
  //-----------------dropdown with visbiletext with retry------------------------------------------------
    public static void selectbyvisibletextlistretry(WebDriver driver, WebElement drplocator, By listlocator, String visibleText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            // Click drop down
            try {
                wait.until(ExpectedConditions.visibilityOf(drplocator));

                wait.until(ExpectedConditions.elementToBeClickable(drplocator)).click();

            } catch (Exception e) {

                js.executeScript("arguments[0].click();", drplocator);
            }


            boolean found = false;

            int retries = 0;


            while (!found && retries < 3) {
                try {

                    List<WebElement> options = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(listlocator));

                    for (WebElement option : options) {
                        String text = option.getText().trim();
                        if (text.equalsIgnoreCase(visibleText.trim())) {
                            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", option);
                            wait.until(ExpectedConditions.elementToBeClickable(option)).click();
                            found = true;
                            break;
                        }
                    }
                    break;
                } catch (Exception ee) {
                    retries++;
                }
            }

            if (!found) {
                System.out.println("Dropdown option not found: " + visibleText);
            }

        } catch (Exception e) {
            System.out.println("Dropdown selection failed: " + e.getMessage());
        }


    }

    //--------------dropdown with search------------------------------------------------
    public static void selectDropdownWithSearch(WebDriver driver,
                                                WebElement dropdownElement,
                                                By optionsLocator,
                                                WebElement searchInput,
                                                String valueToSelect) {
        try {
            // Click the dropdown
            dropdownElement.click();

            // Wait for the search input to appear
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(searchInput));


            searchInput.clear();
            searchInput.sendKeys(valueToSelect);

            // Wait for options to be visible
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(optionsLocator));
            List<WebElement> options = driver.findElements(optionsLocator);

            for (WebElement option : options) {
                if (option.getText().equalsIgnoreCase(valueToSelect)) {
                    option.click();
                    return;
                }
            }

            throw new RuntimeException("Option '" + valueToSelect + "' not found in dropdown");

        } catch (Exception e) {
            throw new RuntimeException("Failed to select value from dropdown: " + e.getMessage());
        }
    }



}
