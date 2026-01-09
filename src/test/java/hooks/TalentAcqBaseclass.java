package hooks;

import drivers.DriverManager;
import io.cucumber.java.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import utils.*;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.*;

public class TalentAcqBaseclass {

    public static String url;
    public static String username;
    public static String password;

    private static ExtentReports extent;

    private static String readBrowserFromProps() throws Exception {
        Properties p = new Properties();
        p.load(new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/global.properties"));return p.getProperty("Browser");
    }

    private static WebDriver newDriver(String br) {
        switch (br.toLowerCase()) {
            case "firefox":
                FirefoxOptions ff = new FirefoxOptions();


                ff.addPreference("geo.prompt.testing", true);
                ff.addPreference("geo.prompt.testing.allow", true);
                ff.addPreference("dom.webnotifications.enabled", false);
                ff.addPreference("permissions.default.desktop-notification", 2);

                ff.addArguments("--disable-notifications");
                ff.addArguments("--disable-popup-blocking");
                ff.addArguments("--enable-clipboard-read-write");

                return new FirefoxDriver(ff);

            case "edge":

                System.setProperty("webdriver.edge.driver", System.getProperty("user.dir") + "/drivers/msedgedriver.exe");

                EdgeOptions ed = new EdgeOptions();

                Map<String, Object> prefs = new HashMap<>();

                prefs.put("profile.default_content_setting_values.clipboard", 1);

                prefs.put("profile.default_content_setting_values.notifications", 2);

                prefs.put("profile.default_content_setting_values.geolocation", 1);

                ed.setExperimentalOption("prefs", prefs);
                ed.addArguments("--enable-clipboard-read-write");

                // Extra safety
                ed.addArguments("--disable-notifications");
                ed.addArguments("--disable-popup-blocking");
                ed.addArguments("--disable-infobars");

                return new EdgeDriver(ed);



            default: //Chrome
                ChromeOptions ch = new ChromeOptions();
                Map<String, Object> chprefs = new HashMap<>();

                chprefs.put("profile.default_content_setting_values.clipboard", 1);

                chprefs.put("profile.default_content_setting_values.notifications", 2);

                chprefs.put("profile.default_content_setting_values.geolocation", 1);

                ch.setExperimentalOption("prefs", chprefs);

                ch.addArguments("--enable-clipboard-read-write");

                ch.addArguments("--disable-notifications");
                ch.addArguments("--disable-popup-blocking");
                ch.addArguments("--disable-infobars");
                ch.addArguments("--disable-web-security");
                ch.addArguments("--no-sandbox");
                ch.addArguments("--disable-dev-shm-usage");

                return new ChromeDriver(ch);

        }
    }
    // ================= BEFORE ALL =================

    @BeforeAll
    public static void beforeAll() throws Exception {

        TalentAcqconfiguration cfg = new TalentAcqconfiguration();
        url = cfg.geturl();
        username = cfg.getusername();
        password = cfg.getpassword();
        extent = ExtentManager.getExtent();

        WebDriver driver = newDriver(readBrowserFromProps());
        DriverManager.setDriver(driver);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get(url);
    }

    // ================= BEFORE SCENARIO =================

    @Before
    public void beforeScenario(Scenario scenario) {

        ExtentTest test = extent.createTest(scenario.getName());
        ExtentTestManager.setTest(test);
        test.info("Scenario Started : " + scenario.getName());
    }

    // ================= AFTER STEP =================

    @AfterStep
    public void afterStep(Scenario scenario) {

        WebDriver driver = DriverManager.getDriver();

        if (scenario.isFailed() && driver != null) {
            try {
                File src = ((TakesScreenshot) driver)
                        .getScreenshotAs(OutputType.FILE);

                byte[] bytes = FileUtils.readFileToByteArray(src);

                scenario.attach(bytes, "image/png", "Failed Step");

                ExtentTestManager.getTest().fail("Step Failed").addScreenCaptureFromBase64String(((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64));

            } catch (Exception e) {
                ExtentTestManager.getTest().fail("Screenshot capture failed");
            }
        }
    }

    // ================= AFTER SCENARIO =================

    @After
    public void afterScenario(Scenario scenario) {

        if (scenario.isFailed()) {
            ExtentTestManager.getTest().fail("Scenario FAILED");
        } else {
            ExtentTestManager.getTest().pass("Scenario PASSED");
        }

    }

    // ================= AFTER ALL =================

    @AfterAll
    public static void afterAll() {

        if (extent != null) {
            extent.flush();
        }
        DriverManager.quitDriver();
    }
}
