package Tests;

import Base.BaseTest;
import Pages.LoginPage;
import Pages.MainPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    @Test(groups = "filter-test")
    public void testFilterButton() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginWithValidCred();

        MainPage mainPage = new MainPage(driver);
        mainPage.isLoaded();

        mainPage.clickFilter();
        Assert.assertTrue(driver.findElement(mainPage.filterOptions).isDisplayed(), "Filter options should be displayed");
        Assert.assertTrue(driver.findElement(mainPage.cancelFilter).isDisplayed(), "Cancel filter button should be displayed");

        mainPage.clickCancelFilter();
        Assert.assertTrue(driver.findElements(mainPage.filterOptions).isEmpty(), "Filter options should be hidden after canceling");
    }

    @Test(groups = "filter-test")
    public void testFilterByNameAToZ() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginWithValidCred();

        MainPage mainPage = new MainPage(driver);
        mainPage.isLoaded();

        mainPage.clickFilter();

        mainPage.selectFilterOption("Name (A to Z)");

        List<String> actualNames = mainPage.getAllProductNames();
        List<String> expectedNames = new ArrayList<>(actualNames);
        Collections.sort(expectedNames);

        Assert.assertEquals(actualNames, expectedNames, "Products should be sorted from A to Z");
    }

    @Test(groups = "filter-test")
    public void testFilterByNameZToA() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginWithValidCred();

        MainPage mainPage = new MainPage(driver);
        mainPage.isLoaded();

        mainPage.clickFilter();

        mainPage.selectFilterOption("Name (Z to A)");

        List<String> actualNames = mainPage.getAllProductNames();
        List<String> expectedNames = new ArrayList<>(actualNames);
        expectedNames.sort(Collections.reverseOrder());

        Assert.assertEquals(actualNames, expectedNames, "Products should be sorted from Z to A");
    }

    @Test(groups = "filter-test")
    public void testFilterByPriceLowToHigh() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginWithValidCred();

        MainPage mainPage = new MainPage(driver);
        mainPage.isLoaded();

        mainPage.clickFilter();

        mainPage.selectFilterOption("Price (low to high)");

        List<Double> actualPrices = mainPage.getAllProductPrices();
        List<Double> expectedPrices = new ArrayList<>(actualPrices);
        Collections.sort(expectedPrices);

        Assert.assertEquals(actualPrices, expectedPrices, "Products should be sorted by price from low to high");
    }

     @Test(groups = "filter-test")
    public void testFilterByPriceHighToLow() {
         LoginPage loginPage = new LoginPage(driver);
         loginPage.loginWithValidCred();

         MainPage mainPage = new MainPage(driver);
         mainPage.isLoaded();

         mainPage.clickFilter();

         mainPage.selectFilterOption("Price (high to low)");

         List<Double> actualPrices = mainPage.getAllProductPrices();
         List<Double> expectedPrices = new ArrayList<>(actualPrices);
         expectedPrices.sort(Collections.reverseOrder());

         Assert.assertEquals(actualPrices, expectedPrices, "Products should be sorted by price from high to low");
    }



}
