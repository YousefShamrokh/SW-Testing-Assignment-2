package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class CurrencyPage {

    WebDriver driver;

    By currencyBtn     = By.cssSelector("#form-currency .btn-group button");
    By currencyOptions = By.cssSelector("#form-currency .btn-group ul li button");

    public CurrencyPage(WebDriver driver) {
        this.driver = driver;
    }

    public void changeCurrencyTo(String currencySymbol) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(currencyBtn)).click();
        List<WebElement> options = driver.findElements(currencyOptions);
        for (WebElement option : options) {
            if (option.getText().contains(currencySymbol)) {
                option.click();
                break;
            }
        }
    }

    public String getActiveCurrencySymbol() {
        return driver.findElement(currencyBtn).getText().trim();
    }
}