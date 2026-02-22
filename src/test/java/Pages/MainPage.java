package Pages;

import Base.BasePage;
import com.datnguyen.Utils.Scroll;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.*;

public class MainPage extends BasePage {
    private WebDriverWait wait;

    public MainPage(AndroidDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ===== LOCATORS =====

    private final By productsContainer = AppiumBy.accessibilityId("test-PRODUCTS");

    private final By productTitles = AppiumBy.accessibilityId("test-Item title");

    private final By productPrice = AppiumBy.androidUIAutomator("new UiSelector().textContains(\"$\")");

    private final By productCard = AppiumBy.accessibilityId("test-Item");

    private final By toggle = AppiumBy.accessibilityId("test-Toggle");

    private final By filterButton = AppiumBy.accessibilityId("test-Modal Selector Button");

    public final By filterOptions = AppiumBy.accessibilityId("Selector container");

    public final By cancelFilter = AppiumBy.androidUIAutomator("\t\n" +
            "new UiSelector().className(\"android.view.ViewGroup\").instance(14)");

    private final By filterOptionNameAToZ = AppiumBy.androidUIAutomator(
            "new UiSelector().className(\"android.widget.TextView\").text(\"Name (A to Z)\")");

    private final By filterOptionNameZToA = AppiumBy.androidUIAutomator(
            "new UiSelector().className(\"android.widget.TextView\").text(\"Name (Z to A)\")");

    private final By filterOptionPriceLowToHigh = AppiumBy.androidUIAutomator(
            "new UiSelector().className(\"android.widget.TextView\").text(\"Price (low to high)\")");

    private final By filterOptionPriceHighToLow = AppiumBy.androidUIAutomator(
            "new UiSelector().className(\"android.widget.TextView\").text(\"Price (high to low)\")");

    // ===== ACTIONS =====

    public void isLoaded() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(productsContainer));
    }

    public boolean isItemDisplayed(String itemName) {
        By itemSelector = AppiumBy.accessibilityId("test-" + itemName);
        return !driver.findElements(itemSelector).isEmpty();
    }

    public int getTotalItems() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(productTitles));

        Set<String> uniqueItems = new HashSet<>();
        boolean endReached = false;

        while (!endReached) {
            List<WebElement> items = driver.findElements(productTitles);
            int previousCount = uniqueItems.size();

            for (WebElement item : items) {
                uniqueItems.add(item.getText());
            }

            Scroll scroll = new Scroll();
            scroll.scrollDown(driver);

            if (uniqueItems.size() == previousCount) {
                endReached = true;
            }
        }

        return uniqueItems.size();
    }

    public List<String> getAllProductNames() {

        Set<String> uniqueNames = new LinkedHashSet<>();
        boolean endReached = false;

        while (!endReached) {
            List<WebElement> elements = driver.findElements(productTitles);
            int previousSize = uniqueNames.size();

            for (WebElement el : elements) {
                uniqueNames.add(el.getText());
            }

            Scroll scroll = new Scroll();
            scroll.scrollDown(driver);

            if (uniqueNames.size() == previousSize) {
                endReached = true;
            }
        }

        return new ArrayList<>(uniqueNames);
    }

    public List<Double> getAllProductPrices() {

        Set<Double> uniquePrices = new LinkedHashSet<>();
        boolean endReached = false;

        while (!endReached) {
            List<WebElement> elements = driver.findElements(productPrice);
            int previousSize = uniquePrices.size();

            for (WebElement el : elements) {
                String priceText = el.getText().replace("$", "").trim();
                uniquePrices.add(Double.valueOf(priceText));
            }

            Scroll scroll = new Scroll();
            scroll.scrollDown(driver);

            if (uniquePrices.size() == previousSize) {
                endReached = true;
            }
        }

        return new ArrayList<>(uniquePrices);
    }

    public void clickToggle() {
        WebElement toggleElement = driver.findElement(toggle);
        toggleElement.click();
    }

    public void clickFilter() {
        WebElement filterElement = driver.findElement(filterButton);
        filterElement.click();
    }

    public void clickCancelFilter() {
        WebElement cancelElement = driver.findElement(cancelFilter);
        cancelElement.click();
    }

    public void selectFilterOption(String option) {
        By optionSelector = switch (option) {
            case "Name (A to Z)" -> filterOptionNameAToZ;
            case "Name (Z to A)" -> filterOptionNameZToA;
            case "Price (low to high)" -> filterOptionPriceLowToHigh;
            case "Price (high to low)" -> filterOptionPriceHighToLow;
            default -> throw new IllegalArgumentException("Invalid filter option: " + option);
        };

        WebElement optionElement = driver.findElement(optionSelector);
        optionElement.click();
    }

    public List<Point> getFirstTwoProductLocations() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(productCard));
        List<WebElement> products = driver.findElements(productCard);

        return List.of(
                products.get(0).getLocation(),
                products.get(1).getLocation()
        );
    }
}
