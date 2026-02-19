package com.datnguyen.Config;

import com.datnguyen.Utils.ConfigReader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

import org.openqa.selenium.MutableCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class DriverConfig {
    public static AndroidDriver initializeDriver() {

        String runEnv = System.getProperty("run.env");

        if (runEnv == null) {
            runEnv = ConfigReader.get("run.env");
        }

        boolean isBrowserStack = runEnv.equalsIgnoreCase("browserstack");

        String prefix = isBrowserStack ? "bs." : "local.";

        MutableCapabilities caps = new MutableCapabilities();

        // ===== BASIC CAPS =====
        caps.setCapability("platformName", ConfigReader.get(prefix + "platformName"));
        caps.setCapability("appium:automationName", ConfigReader.get(prefix + "automationName"));
        caps.setCapability("appium:deviceName", ConfigReader.get(prefix + "deviceName"));
        caps.setCapability("appium:platformVersion", ConfigReader.get(prefix + "platformVersion"));
        caps.setCapability("appium:autoGrantPermissions",
                Boolean.parseBoolean(ConfigReader.get(prefix + "autoGrantPermissions")));
        caps.setCapability("appium:newCommandTimeout",
                Integer.parseInt(ConfigReader.get(prefix + "newCommandTimeout")));
        caps.setCapability("appium:noReset",
                Boolean.parseBoolean(ConfigReader.get(prefix + "noReset")));
        caps.setCapability("appium:appWaitActivity", ConfigReader.get(prefix + "appWaitActivity"));
        caps.setCapability("appium:appWaitDuration",
                Integer.parseInt(ConfigReader.get(prefix + "appWaitDuration")));
        // ===== APP =====
        if (isBrowserStack) {

            caps.setCapability("appium:app", System.getenv("APP_ID"));

            MutableCapabilities bstackOptions = new MutableCapabilities();
            bstackOptions.setCapability("userName", System.getenv("BROWSERSTACK_USERNAME"));
            bstackOptions.setCapability("accessKey", System.getenv("BROWSERSTACK_ACCESS_KEY"));
            bstackOptions.setCapability("projectName", ConfigReader.get("bs.projectName"));
            bstackOptions.setCapability("buildName", ConfigReader.get("bs.buildName"));
            bstackOptions.setCapability("sessionName", ConfigReader.get("bs.sessionName"));
            bstackOptions.setCapability("debug",
                    Boolean.parseBoolean(ConfigReader.get("bs.debug")));
            bstackOptions.setCapability("networkLogs",
                    Boolean.parseBoolean(ConfigReader.get("bs.networkLogs")));

            caps.setCapability("bstack:options", bstackOptions);

        } else {
            caps.setCapability("appium:app", AppConfig.apkPath);
        }

        // ===== SERVER URL =====
        String protocol = ConfigReader.get(prefix + "protocol");
        String hostname = ConfigReader.get(prefix + "hostname");
        String port = ConfigReader.get(prefix + "port");
        String path = ConfigReader.get(prefix + "path");

        URL appiumServerUrl;
        try {
            appiumServerUrl = new URL(protocol + "://" + hostname + ":" + port + path);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid Appium Server URL", e);
        }

        AndroidDriver driver = new AndroidDriver(appiumServerUrl, caps);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        return driver;
    }
}
