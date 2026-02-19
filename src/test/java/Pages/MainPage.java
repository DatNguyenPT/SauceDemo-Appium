package Pages;

import Base.BasePage;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
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

    private final By productsContainer =
            AppiumBy.accessibilityId("test-PRODUCTS");

    private final By totalItems =
            AppiumBy.androidUIAutomator(
                    "new UiSelector().description(\"test-Item\")"
            );

    // ===== ACTIONS =====

    public void isLoaded() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(productsContainer));
    }

    public boolean isItemDisplayed(String itemName) {
        By itemSelector = AppiumBy.accessibilityId("test-" + itemName);
        return driver.findElement(itemSelector).isDisplayed();
    }

    public int getTotalItems() {

        // wait for first item visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(totalItems));

        List<WebElement> items = driver.findElements(totalItems);

        return items.size();
    }
}
