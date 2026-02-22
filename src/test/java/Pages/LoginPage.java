package Pages;

import Base.BasePage;
import com.datnguyen.Utils.GetEnv;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {
    public LoginPage(AndroidDriver driver) {
        super(driver);
    }

    // ===== LOCATORS =====

    private final By usernameField = AppiumBy.accessibilityId("test-Username");
    private final By passwordField = AppiumBy.accessibilityId("test-Password");
    private final By loginButton = AppiumBy.accessibilityId("test-LOGIN");
    private final By errorMessage = AppiumBy.accessibilityId("test-Error message");


    // ===== ACTIONS =====

    public void enterUsername(String username) {
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    public void loginWithValidCred() {
        GetEnv getEnv = new GetEnv();
        enterUsername(getEnv.get("TEST_USERNAME"));
        enterPassword(getEnv.get("TEST_PASSWORD"));
        clickLogin();
    }

    public void loginWithInvalidCred() {
        enterUsername("FakeUsername");
        enterPassword("FakePassword");
        clickLogin();
    }

    // ===== VALIDATIONS =====
    public String getErrorMessage() {
        WebElement container = driver.findElement(errorMessage);
        WebElement text = container.findElement(By.className("android.widget.TextView"));
        return text.getText();
    }
}
