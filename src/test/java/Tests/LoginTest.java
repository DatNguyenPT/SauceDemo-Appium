package Tests;

import Base.BaseTest;
import Pages.LoginPage;
import Pages.MainPage;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.datnguyen.Utils.GetEnv;

public class LoginTest extends BaseTest {
    @AfterMethod
    public void resetApp(ITestResult result) {
        if (driver != null) {
            driver.terminateApp("com.swaglabsmobileapp");
            driver.activateApp("com.swaglabsmobileapp");
            String status = result.isSuccess() ? "passed" : "failed";
            String reason = result.isSuccess()
                    ? "Test passed successfully"
                    : result.getThrowable() != null
                    ? result.getThrowable().getMessage()
                    : "Test failed";

            driver.executeScript(
                    "browserstack_executor: {\"action\": \"setSessionStatus\", " +
                            "\"arguments\": {\"status\":\"" + status + "\", " +
                            "\"reason\": \"" + reason + "\"}}"
            );

            driver.executeScript(
                    "browserstack_executor: {\"action\": \"setSessionName\", " +
                            "\"arguments\": {\"name\":\"" + result.getMethod().getMethodName() + "\"}}"
            );

            driver.executeScript(
                    "browserstack_executor: {\"action\": \"annotate\", " +
                            "\"arguments\": {\"data\":\"Login step completed\", \"level\":\"info\"}}"
            );
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

    @Test(groups = "invalid-login")
    public void shouldShowErrorMessageWithEmptyCredentials() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("", "");

        String errorMessage = loginPage.getErrorMessage();
        System.out.println("Error Message: " + errorMessage);

        Assert.assertTrue(
                errorMessage.contains("Username is required"),
                "Error message is incorrect"
        );
    }

    @Test(groups = "invalid-login")
    public void shouldShowErrorMessageWithEmptyPassword() {
        GetEnv getEnv = new GetEnv();
        String username = getEnv.get("TEST_USERNAME");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, "");

        String errorMessage = loginPage.getErrorMessage();
        System.out.println("Error Message: " + errorMessage);

        Assert.assertTrue(
                errorMessage.contains("Password is required"),
                "Error message is incorrect"
        );
    }
}