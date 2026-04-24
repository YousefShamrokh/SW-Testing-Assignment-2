package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CartPage {

    WebDriver driver;

    By cartBtn       = By.id("cart");
    By viewCartBtn   = By.linkText("View Cart");
    By checkoutBtn   = By.linkText("Checkout");
    By successAlert  = By.className("alert-success");
    By cartItemNames = By.cssSelector("#content table tbody tr td:nth-child(2) a");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getSuccessMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successAlert)).getText();
    }

    public void openCartDropdown() {
        driver.findElement(cartBtn).click();
    }

    public void goToViewCart() {
        openCartDropdown();
        driver.findElement(viewCartBtn).click();
    }

    public void goToCheckout() {
        openCartDropdown();
        driver.findElement(checkoutBtn).click();
    }

    public List<String> getCartItemNames() {
        List<WebElement> items = driver.findElements(cartItemNames);
        List<String> names = new ArrayList<>();
        for (WebElement item : items) names.add(item.getText().trim());
        return names;
    }
}