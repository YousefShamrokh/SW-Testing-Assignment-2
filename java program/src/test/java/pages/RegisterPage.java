package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class RegisterPage {

    WebDriver driver;
    WebDriverWait wait;

    By firstName = By.id("input-firstname");
    By lastName = By.id("input-lastname");
    By email = By.id("input-email");
    By telephone = By.id("input-telephone");
    By password = By.id("input-password");
    By confirmPassword = By.id("input-confirm");
    By privacyPolicy = By.name("agree");
    By continueBtn = By.cssSelector("input[value='Continue']");

    public RegisterPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void enterFirstName(String value){
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstName)).clear();
        driver.findElement(firstName).sendKeys(value);
    }

    public void enterLastName(String value){
        wait.until(ExpectedConditions.visibilityOfElementLocated(lastName)).clear();
        driver.findElement(lastName).sendKeys(value);
    }

    public void enterEmail(String value){
        wait.until(ExpectedConditions.visibilityOfElementLocated(email)).clear();
        driver.findElement(email).sendKeys(value);
    }

    public void enterTelephone(String value){
        wait.until(ExpectedConditions.visibilityOfElementLocated(telephone)).clear();
        driver.findElement(telephone).sendKeys(value);
    }

    public void enterPassword(String value){
        wait.until(ExpectedConditions.visibilityOfElementLocated(password)).clear();
        driver.findElement(password).sendKeys(value);
    }

    public void confirmPassword(String value){
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmPassword)).clear();
        driver.findElement(confirmPassword).sendKeys(value);
    }

    public void acceptPolicy(){
        wait.until(ExpectedConditions.elementToBeClickable(privacyPolicy)).click();
    }

    public void clickContinue(){
        wait.until(ExpectedConditions.elementToBeClickable(continueBtn)).click();
    }
}