package Base;

import com.datnguyen.Config.AppConfig;
import com.datnguyen.Config.DriverConfig;

import com.datnguyen.Utils.GetEnv;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {

    protected AndroidDriver driver;
    protected UiAutomator2Options appConfig;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        driver = DriverConfig.initializeDriver();
        appConfig = AppConfig.installApp();
    }
}
