package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getExtent() {

        if (extent == null) {

            ExtentSparkReporter reporter = new ExtentSparkReporter("test-output/ExtentReport.html");

            reporter.config().setReportName("Cucumber Automation Report");
            reporter.config().setDocumentTitle("Apollo Talent Acquisition Test Execution");

            extent = new ExtentReports();
            extent.attachReporter(reporter);

            extent.setSystemInfo("Framework", "Cucumber");
            extent.setSystemInfo("Browser", "Chrome");
        }
        return extent;
    }
}
