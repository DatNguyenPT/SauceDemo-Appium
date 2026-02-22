package com.datnguyen.Config;

import com.datnguyen.Utils.ConfigReader;
import com.datnguyen.Utils.GetEnv;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

import org.openqa.selenium.MutableCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class DriverConfig {
    public static AndroidDriver initializeDriver(String testName) {
        GetEnv getEnv = new GetEnv();
        String runEnv = System.getProperty("run.env");

        if (runEnv == null) {
            runEnv = ConfigReader.get("run.env");
        }

        boolean isBrowserStack = runEnv.equalsIgnoreCase("browserstack");

        String prefix = isBrowserStack ? "browserstack." : "local.";

        MutableCapabilities caps = new MutableCapabilities();

        // ===== BASIC CAPS =====
        caps.setCapability("platformName", ConfigReader.get(prefix + "platformName"));
        caps.setCapability("appium:automationName", ConfigReader.get(prefix + "automationName"));
        caps.setCapability("appium:deviceName", ConfigReader.get(prefix + "deviceName"));
        caps.setCapability("appium:platformVersion", ConfigReader.get(prefix + "platformVersion"));
        caps.setCapability("appium:autoGrantPermissions",
            Boolean.parseBoolean(ConfigReader.get(prefix + "autoGrantPermissions")));

        String newCommandTimeoutStr = ConfigReader.get(prefix + "newCommandTimeout");
        int newCommandTimeout = newCommandTimeoutStr != null ? Integer.parseInt(newCommandTimeoutStr) : 240;
        caps.setCapability("appium:newCommandTimeout", newCommandTimeout);

        caps.setCapability("appium:noReset",
            Boolean.parseBoolean(ConfigReader.get(prefix + "noReset")));
        caps.setCapability("appium:appWaitActivity", ConfigReader.get(prefix + "appWaitActivity"));

        String appWaitDurationStr = ConfigReader.get(prefix + "appWaitDuration");
        int appWaitDuration = appWaitDurationStr != null ? Integer.parseInt(appWaitDurationStr) : 30000;
        caps.setCapability("appium:appWaitDuration", appWaitDuration);
        // ===== APP =====
        if (isBrowserStack) {

            caps.setCapability("appium:app", getEnv.get("APP_ID"));

            MutableCapabilities bstackOptions = new MutableCapabilities();
            bstackOptions.setCapability("userName", getEnv.get("BROWSERSTACK_USERNAME"));
            bstackOptions.setCapability("accessKey", getEnv.get("BROWSERSTACK_ACCESS_KEY"));
            bstackOptions.setCapability("projectName", ConfigReader.get("bs.projectName"));

            String branch = System.getenv("GITHUB_REF_NAME");
            String runNumber = System.getenv("GITHUB_RUN_NUMBER");

            String buildName;

            if (runNumber != null) {
                buildName = branch + " - Build #" + runNumber;
            } else {
                buildName = "Local Build - " + System.currentTimeMillis();
            }

            bstackOptions.setCapability("buildName", buildName);
            bstackOptions.setCapability("sessionName", testName);
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
