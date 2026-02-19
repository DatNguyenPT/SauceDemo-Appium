package com.datnguyen.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigReader.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {

            properties.load(input);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties");
        }
    }

    public static String get(String key) {
        String systemValue = System.getProperty(key);
        return systemValue != null ? systemValue : properties.getProperty(key);
    }
}
