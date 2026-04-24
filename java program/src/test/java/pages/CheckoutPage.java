package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutPage {

    WebDriver driver;
    WebDriverWait wait;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }


    By newAddressOption    = By.cssSelector("#payment-address input[value='new']");
    By billingContinueBtn  = By.id("button-payment-address");
    By shippingContinueBtn = By.id("button-shipping-address");
    By deliveryContinueBtn = By.id("button-shipping-method");
    By deliveryComment     = By.name("comment");
    By termsCheckbox       = By.name("agree");
    By paymentContinueBtn  = By.id("button-payment-method");
    By confirmOrderBtn     = By.id("button-confirm");
    By orderSuccessHeader  = By.cssSelector("#content h1");


    public void fillBillingDetails(String first, String last,
                                   String address, String city,
                                   String post, String country, String region) {

        wait.until(ExpectedConditions.elementToBeClickable(newAddressOption)).click();

        driver.findElement(By.id("input-payment-firstname")).sendKeys(first);
        driver.findElement(By.id("input-payment-lastname")).sendKeys(last);
        driver.findElement(By.id("input-payment-address-1")).sendKeys(address);
        driver.findElement(By.id("input-payment-city")).sendKeys(city);
        driver.findElement(By.id("input-payment-postcode")).sendKeys(post);

        new Select(driver.findElement(By.id("input-payment-country")))
                .selectByVisibleText(country);

        new Select(driver.findElement(By.id("input-payment-zone")))
                .selectByVisibleText(region);

        driver.findElement(billingContinueBtn).click();

        wait.until(ExpectedConditions.elementToBeClickable(shippingContinueBtn));
    }


    public void continueShipping() {
        driver.findElement(shippingContinueBtn).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(deliveryComment));
    }


    public void addCommentAndContinueDelivery(String comment) {
        driver.findElement(deliveryComment).sendKeys(comment);
        driver.findElement(deliveryContinueBtn).click();
    }


    public void agreeTermsAndContinuePayment() {
        driver.findElement(termsCheckbox).click();
        driver.findElement(paymentContinueBtn).click();
    }


    public void confirmOrder() {
        driver.findElement(confirmOrderBtn).click();
    }


    public String getOrderSuccessMessage() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(orderSuccessHeader)
        ).getText();
    }
}