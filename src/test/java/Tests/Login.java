package Tests;

import Base.BaseTest;
import Pages.LoginPage;
import Pages.MainPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.github.cdimascio.dotenv.Dotenv;

public class Login extends BaseTest {

    private final Dotenv dotenv = Dotenv.load();

    @AfterMethod
    public void resetApp() {
        if (driver != null) {
            driver.terminateApp("com.swaglabsmobileapp");
            driver.activateApp("com.swaglabsmobileapp");
            driver.quit();
            driver = null;
        }
    }

    @Test
    public void shouldLoginSuccessfullyWithValidCredentials() {
        LoginPage loginPage = new LoginPage(driver);
        String username = dotenv.get("TEST_USERNAME");
        String password = dotenv.get("TEST_PASSWORD");

        loginPage.login(username, password);

        MainPage mainPage = new MainPage(driver);
        mainPage.isLoaded();

        Assert.assertTrue(mainPage.getTotalItems() > 0,
                "Items should be displayed after successful login");
    }

    @Test(groups = "invalid-login")
    public void shouldShowErrorMessageWithInvalidCredentials() {
        System.out.println("Driver null? " + (driver == null));
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("fakeUser", "fakePass");

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