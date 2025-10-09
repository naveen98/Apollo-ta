package hooks;

import drivers.DriverManager;
import io.cucumber.java.AfterAll;
import io.cucumber.java.AfterStep;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import utils.TalentAcqconfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class TalentAcqBaseclass {

    public static String url;
    public static String username;
    public static String password;

    private static String readBrowserFromProps() throws Exception {
        Properties p = new Properties();
        p.load(new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/global.properties"));
        return p.getProperty("Browser");
    }

    private static WebDriver newDriver(String br) {
        switch (br.toLowerCase()) {
            case "firefox":
                FirefoxOptions ffOptions = new FirefoxOptions();
                ffOptions.addPreference("geo.prompt.testing", true);   // auto-respond
                ffOptions.addPreference("geo.prompt.testing.allow", true); // allow location
                return new FirefoxDriver(ffOptions);

            case "edge":
                EdgeOptions edOptions = new EdgeOptions();

                // Set geolocation preference: 1 = allow, 2 = block
                Map<String, Object> edgeContentSettings = new HashMap<>();
                edgeContentSettings.put("geolocation", 1);

                Map<String, Object> edgeProfile = new HashMap<>();
                edgeProfile.put("default_content_setting_values", edgeContentSettings);

                Map<String, Object> edgePrefs = new HashMap<>();
                edgePrefs.put("profile", edgeProfile);

                edOptions.setExperimentalOption("prefs", edgePrefs);
                WebDriverManager.edgedriver().setup();

                return new EdgeDriver(edOptions);


            default: // chrome
                ChromeOptions chromeOptions = new ChromeOptions();
                Map<String, Object> prefs = new HashMap<>();
                Map<String, Object> profile = new HashMap<>();
                Map<String, Object> content = new HashMap<>();
                content.put("geolocation", 1); // block location prompt
                profile.put("default_content_setting_values", content);
                prefs.put("profile", profile);
                chromeOptions.setExperimentalOption("prefs", prefs);
                return new ChromeDriver(chromeOptions);
        }
    }

    @BeforeAll
    public static void beforeAll() throws Exception {
        TalentAcqconfiguration cfg = new TalentAcqconfiguration();
        url = cfg.geturl();
        username = cfg.getusername();
        password = cfg.getpassword();

        WebDriver driver = newDriver(readBrowserFromProps());
        DriverManager.setDriver(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get(url);
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        WebDriver driver = DriverManager.getDriver();
        if (scenario.isFailed() && driver != null) {
            try {
                File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                byte[] bytes = FileUtils.readFileToByteArray(src);
                scenario.attach(bytes, "image/png", "Failed Step Screenshot");
            } catch (IOException ignored) {}
        }
    }

    @AfterAll
    public static void afterAll() {

        DriverManager.quitDriver();
    }
}
