package utils;

import java.io.InputStream;
import java.util.Properties;

public class TalentAcqconfiguration {

    private Properties pro;
    private String url;
    private String username;
    private String password;

    public TalentAcqconfiguration() {
        try {
            // Load global.properties from classpath (src/test/resources or src/main/resources)
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("global.properties");

            if (inputStream == null) {
                throw new RuntimeException("global.properties file not found in classpath");
            }

            pro = new Properties();
            pro.load(inputStream);

            // Read environment
            String env = pro.getProperty("env");
            if (env == null) {
                throw new RuntimeException("'env' property missing in global.properties");
            }

            switch (env.toLowerCase()) {
                case "dev":
                    url = pro.getProperty("baseurldev");
                    username = pro.getProperty("usernamedev");
                    password = pro.getProperty("passworddev");
                    break;
                case "uat":
                    url = pro.getProperty("baseurluat");
                    username = pro.getProperty("usernameuat");
                    password = pro.getProperty("passworduat");
                    break;
                case "prod":
                    url = pro.getProperty("baseurlprod");
                    username = pro.getProperty("usernameprod");
                    password = pro.getProperty("passwordprod");
                    break;
                default:
                    throw new RuntimeException("Invalid 'env' property value: " + env);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error loading configuration: " + e.getMessage(), e);
        }
    }

    public String geturl() {

        return url;
    }

    public String getusername() {

        return username;
    }

    public String getpassword() {

        return password;
    }
}
