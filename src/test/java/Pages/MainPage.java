package Pages;

import Base.BasePage;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;

public class MainPage extends BasePage {
    private WebDriverWait wait;

    public MainPage(AndroidDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ===== LOCATORS =====

    private final By productsContainer = AppiumBy.accessibilityId("test-PRODUCTS");

    private final By totalItems = AppiumBy.androidUIAutomator("new UiSelector().description(\"test-Item\")");

    private final By productCard = AppiumBy.accessibilityId("test-Item");

    private final By toggle = AppiumBy.accessibilityId("test-Toggle");

    private final By firstProductContainer = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.view.ViewGroup\").instance(20)");

    // ===== ACTIONS =====

    public void isLoaded() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(productsContainer));
    }

    public boolean isItemDisplayed(String itemName) {
        By itemSelector = AppiumBy.accessibilityId("test-" + itemName);
        return !driver.findElements(itemSelector).isEmpty();
    }

    public int getTotalItems() {
        // wait for first item visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(totalItems));

        List<WebElement> items = driver.findElements(totalItems);

        return items.size();
    }

    public void clickToggle() {
        WebElement toggleElement = driver.findElement(toggle);
        toggleElement.click();
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
