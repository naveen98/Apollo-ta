package utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class ExtentTestManager {

    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    public static void setTest(ExtentTest test) {

        extentTest.set(test);
    }

    public static ExtentTest getTest() {

        return extentTest.get();

    }



    public static void logPass(String message) {
        getTest().pass(MarkupHelper.createLabel(message, ExtentColor.GREEN));
    }

    public static void logFail(String message) {
        getTest().pass(MarkupHelper.createLabel(message, ExtentColor.RED));
    }


    public static void logWarning(String message) {
        getTest().warning(MarkupHelper.createLabel(message, ExtentColor.YELLOW));
    }



}
