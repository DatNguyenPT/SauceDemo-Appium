package Base;

import com.datnguyen.Config.AppConfig;
import com.datnguyen.Config.DriverConfig;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.annotations.BeforeMethod;
import java.lang.reflect.Method;


public abstract class BaseTest {

    protected AndroidDriver driver;
    protected UiAutomator2Options appConfig;

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method) {
        driver = DriverConfig.initializeDriver(method.getName());
        appConfig = AppConfig.installApp();
    }
}
