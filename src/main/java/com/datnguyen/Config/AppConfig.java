package com.datnguyen.Config;

import com.datnguyen.Utils.ConfigReader;

import io.appium.java_client.android.options.UiAutomator2Options;

public class AppConfig {
    public static String getApkPath() {
        String apkPath = System.getProperty("user.dir")
                + "/src/main/java/com/datnguyen/Base/saucedemo.apk";
        return apkPath;
    }
    public static String apkPath = getApkPath();

    public static UiAutomator2Options installApp() {
        UiAutomator2Options options = new UiAutomator2Options();
        final String udid = ConfigReader.get("UDID");
        options.setUdid(udid);
        options.setApp(apkPath);
        return options;
    }
}
