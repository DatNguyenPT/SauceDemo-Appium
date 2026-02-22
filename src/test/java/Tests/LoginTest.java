package Tests;

import Base.BaseTest;
import Pages.LoginPage;
import Pages.MainPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.datnguyen.Utils.GetEnv;

public class LoginTest extends BaseTest {
    @AfterMethod
    public void resetApp() {
        if (driver != null) {
            driver.terminateApp("com.swaglabsmobileapp");
            driver.activateApp("com.swaglabsmobileapp");
            driver.quit();
            driver = null;
        }
    }

    @Test(groups = "valid-login")
    public void shouldLoginSuccessfullyWithValidCredentials() {
        GetEnv getEnv = new GetEnv();
        LoginPage loginPage = new LoginPage(driver);

        loginPage.loginWithValidCred();

        MainPage mainPage = new MainPage(driver);
        mainPage.isLoaded();

        Assert.assertTrue(mainPage.getTotalItems() > 0,
                "Items should be displayed after successful login");
    }

    @Test(groups = "invalid-login")
    public void shouldShowErrorMessageWithInvalidCredentials() {
        System.out.println("Driver null? " + (driver == null));
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginWithInvalidCred();

        String errorMessage = loginPage.getErrorMessage();
        System.out.println("Error Message: " + errorMessage);

        Assert.assertTrue(
                errorMessage.contains(
                        "Username and password do not match any user in this service."
                ),
                "Error message is incorrect"
        );
    }
}