package Tests;

import Base.BaseTest;
import Pages.LoginPage;
import Pages.MainPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class MainPageTest extends BaseTest {
    @AfterMethod
    public void resetApp() {
        if (driver != null) {
            driver.terminateApp("com.swaglabsmobileapp");
            driver.activateApp("com.swaglabsmobileapp");
            driver.quit();
            driver = null;
        }
    }

    @Test(groups = "product-count-test")
    public void testTotalProductsCount() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginWithValidCred();

        MainPage mainPage = new MainPage(driver);
        mainPage.isLoaded();

        int total = mainPage.getTotalItems();
        Assert.assertEquals(total, 6, "Product count should be 6");
    }

    @Test(groups = "toggle-test")
    public void testToggle() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginWithValidCred();

        MainPage mainPage = new MainPage(driver);
        mainPage.isLoaded();

        // Before toggle (grid)
        var before = mainPage.getFirstTwoProductLocations();

        mainPage.clickToggle();

        // After toggle (list)
        var after = mainPage.getFirstTwoProductLocations();

        Assert.assertEquals(before.get(1).getY(), before.get(0).getY(), "Before toggle should be in grid layout");
        Assert.assertNotEquals(after.get(1).getY(), after.get(0).getY(), "After toggle should be in list layout");
    }

}
