package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class AccountPage {

    WebDriver driver;
    WebDriverWait wait;

    By successHeader     = By.cssSelector("#content h1");
    By myAccountDropdown = By.partialLinkText("My Account");
    By logoutLink        = By.cssSelector("a[href*='route=account/logout']");
    By continueBtn       = By.cssSelector("a[href*='route=common/home']");

    public AccountPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isRegistrationSuccessful(){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(successHeader));
            String actualText = header.getText();
            System.out.println("Header found: " + actualText);
            return actualText.contains("Your Account Has Been Created");
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    public boolean isLogoutDisplayed() {
        try {

            return driver.findElement(logoutLink) != null;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    public void clickLogoutButton() {

        wait.until(ExpectedConditions.elementToBeClickable(myAccountDropdown)).click();
        wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();
    }

    public void clickConfirmOnLogout() {
        wait.until(ExpectedConditions.elementToBeClickable(continueBtn)).click();
    }
}