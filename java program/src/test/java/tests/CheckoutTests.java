package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AccountPage;
import pages.CheckoutPage;
import pages.HomePage;
import pages.LoginPage;
import java.time.Duration;
import java.util.List;

public class CheckoutTests extends BaseTest {

    @Test
    public void normalCheckoutAndConfirmOrder() {
        HomePage home = new HomePage(driver);
        home.goToLogin();

        LoginPage login = new LoginPage(driver);
        login.login(config.get("email"), config.get("password"));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));



        driver.get(config.get("categoryUrl"));
        while (true) {
            List<WebElement> removeButtons = driver.findElements(
                    By.cssSelector("td.text-center button.btn-danger"));
            if (removeButtons.isEmpty()) break;
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();", removeButtons.get(0));
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
        }


      driver.get(config.get("productUrl"));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("button-cart"))).click();


        WebElement successMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div.alert-success")));
        Assert.assertTrue(successMsg.getText().contains("iPod Touch"),
                "Product not added successfully");


      driver.get(config.get("cartUrl"));
        String productName = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".table-responsive tbody tr td:nth-child(2) a"))).getText();
        Assert.assertEquals(productName, "iPod Touch");


        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("a.btn.btn-primary"))).click();


        wait.until(ExpectedConditions.urlContains("route=checkout/checkout"));

        CheckoutPage checkout = new CheckoutPage(driver);


        checkout.fillBillingDetails(
                "Test", "User",
                "123 Test Street", "Cairo",
                "12345", "Egypt", "Cairo"
        );


        checkout.continueShipping();


        checkout.addCommentAndContinueDelivery("Please deliver ASAP");

        String totalText = driver.findElement(
                By.cssSelector(".table.table-bordered.table-hover tfoot")).getText();
        Assert.assertTrue(totalText.contains("Flat Shipping Rate"),
                "Shipping rate not included");


        checkout.agreeTermsAndContinuePayment();


        checkout.confirmOrder();


        String orderMsg = checkout.getOrderSuccessMessage();
        Assert.assertTrue(orderMsg.contains("Your order has been placed!"),
                "Order confirmation message not found. Got: " + orderMsg);


        String cartText = driver.findElement(By.id("cart-total")).getText();
        Assert.assertTrue(cartText.contains("0 item(s)"),
                "Cart should be empty after order");


        AccountPage account = new AccountPage(driver);
        account.clickLogoutButton();
        account.clickConfirmOnLogout();
    }
}