package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class CheckoutPage {

    WebDriver driver;
    WebDriverWait wait;

    By billingContinueBtn = By.id("button-payment-address");
    By shippingContinueBtn = By.id("button-shipping-address");
    By deliveryContinueBtn = By.id("button-shipping-method");
    By deliveryComment = By.name("comment");
    By termsCheckbox = By.name("agree");
    By paymentContinueBtn = By.id("button-payment-method");
    By confirmOrderBtn = By.id("button-confirm");
    By orderSuccessHeader = By.cssSelector("#content h1");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    private void fillField(String id, String value) {
        WebElement field = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id(id))
        );
        field.clear();
        field.sendKeys(value);
    }

    public void fillBillingDetails(String first, String last,
                                   String address, String city,
                                   String post, String country, String region) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("checkout-checkout")));

        WebElement newAddress = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("input[name='payment_address'][value='new']")
                )
        );

        if (!newAddress.isSelected()) {
            newAddress.click();
        }

        fillField("input-payment-firstname", first);
        fillField("input-payment-lastname", last);
        fillField("input-payment-address-1", address);
        fillField("input-payment-city", city);
        fillField("input-payment-postcode", post);

        Select countrySelect = new Select(
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.id("input-payment-country")
                ))
        );
        countrySelect.selectByVisibleText(country);

        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("#input-payment-zone option")
        ));

        Select regionSelect = new Select(
                driver.findElement(By.id("input-payment-zone"))
        );
        regionSelect.selectByIndex(1);

        wait.until(ExpectedConditions.elementToBeClickable(billingContinueBtn)).click();
    }

    public void continueShipping() {
        wait.until(ExpectedConditions.elementToBeClickable(
                shippingContinueBtn
        )).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(deliveryComment));
    }

    public void addCommentAndContinueDelivery(String comment) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(deliveryComment))
                .sendKeys(comment);

        wait.until(ExpectedConditions.elementToBeClickable(deliveryContinueBtn))
                .click();
    }

    public void agreeTermsAndContinuePayment() {
        wait.until(ExpectedConditions.elementToBeClickable(termsCheckbox)).click();
        wait.until(ExpectedConditions.elementToBeClickable(paymentContinueBtn)).click();
    }

    public void confirmOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(confirmOrderBtn)).click();
    }

    public String getOrderSuccessMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                orderSuccessHeader
        )).getText();
    }
}