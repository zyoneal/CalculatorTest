package com.viktor.calculator.config;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Properties;

public class Config {

    public static final String DB_URL = "db.url";

    public static final String DB_LOGIN = "db.login";

    public static final String DB_PASSWORD = "db.password";

    private static final Properties properties = new Properties();

    public static synchronized String getProperty(String name) {
        if (properties.isEmpty()) {
            try (BufferedInputStream is = new BufferedInputStream(new FileInputStream("target/classes/config.properties"))) {
                properties.load(is);
            } catch (Exception exception) {
                exception.printStackTrace();
                throw new RuntimeException(exception);
            }
        }
        return properties.getProperty(name);
    }

}
