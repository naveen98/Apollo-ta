package utils;

import java.io.FileInputStream;
import java.util.Properties;

public class RecruiterConfigReader {

    private static Properties prop;

    static {
        try {
            prop = new Properties();
            prop.load(new FileInputStream(
                    "src/test/resources/Recruiter.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String getProperty(String key) {
        return prop.getProperty(key);
    }
}
