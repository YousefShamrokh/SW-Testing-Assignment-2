package tests;

import base.BaseTest;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.AccountPage;
import pages.CheckoutPage;
import pages.HomePage;
import pages.LoginPage;
import utils.ExcelReader;

import java.util.List;

@Epic("Shopping Checkout")
@Feature("Checkout Process")
public class CheckoutTests extends BaseTest {

    @DataProvider(name = "CheckoutData")
    public Object[][] getRegData() {
        String path = "src/main/resources/testData.xlsx";
        return ExcelReader.getTestData(path, "CheckoutDataInput");
    }

    @Test(dataProvider = "CheckoutData")
    @Story("Complete checkout and confirm order")
    @Severity(SeverityLevel.CRITICAL)
    @Description("User completes full checkout process with delivery details and confirms order")
    public void normalCheckoutAndConfirmOrder(String pUrl, String pName, String date,String fName, String lName, String address, String city, String post, String country, String region) throws InterruptedException {
        Allure.step("Open home page and navigate to login");
        HomePage home = new HomePage(driver);
        home.goToLogin();
        Thread.sleep(1000);

        Allure.step("Login with valid credentials");
        LoginPage login = new LoginPage(driver);
        login.login(config.get("email"), config.get("password"));
        Thread.sleep(1000);

        wait.until(ExpectedConditions.urlContains("route=account"));

        Allure.step("Clear existing cart items");
        driver.get(config.get("categoryUrl"));
        Thread.sleep(1000);
        while (true) {
            List<WebElement> removeButtons = driver.findElements(
                    By.cssSelector("td.text-center button.btn-danger")
            );

            if (removeButtons.isEmpty())
                break;

            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", removeButtons.get(0));

            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException ignored) {}
        }
        System.out.println("DEBUG: Executing test with URL -> [" + pUrl + "]");
        Allure.step("Navigate to product page");
        driver.get(pUrl);
        Thread.sleep(1000);

        Allure.step("Add product to cart");
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("input-option225")
        )).sendKeys(date);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("button-cart"))).click();
        Thread.sleep(1000);

        Allure.step("Verify product added successfully");
        WebElement successMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div.alert-success")
        ));

        Assert.assertTrue(successMsg.getText().contains(pName));

        Allure.step("Navigate to shopping cart");
        driver.get(config.get("cartUrl"));
        Thread.sleep(1000);

        Allure.step("Verify product in cart");
        String productName = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".table-responsive tbody tr td:nth-child(2) a")
        )).getText();

        Assert.assertEquals(productName, pName);

        Allure.step("Proceed to checkout");
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("a.btn.btn-primary"))).click();
        Thread.sleep(1000);

        Allure.step("Wait for checkout page to load");
        wait.until(ExpectedConditions.urlContains("route=checkout/checkout"));
        Thread.sleep(1000);

        CheckoutPage checkout = new CheckoutPage(driver);

        Allure.step("Fill billing details");
        checkout.fillBillingDetails(
                fName, lName,
                address, city,
                post, country, region
        );
        Thread.sleep(1000);

        Allure.step("Continue to shipping");
        checkout.continueShipping();
        Thread.sleep(1000);

        Allure.step("Add delivery comment and continue to payment");
        checkout.addCommentAndContinueDelivery("Please deliver ASAP");
        Thread.sleep(1000);

        Allure.step("Agree to terms and continue to payment");
        checkout.agreeTermsAndContinuePayment();
        Thread.sleep(1000);

        Allure.step("Confirm order");
        checkout.confirmOrder();
        Thread.sleep(1000);

        wait.until(ExpectedConditions.urlContains("checkout/success"));

        Allure.step("Verify order success message");
        String orderMsg = checkout.getOrderSuccessMessage();
        Assert.assertTrue(orderMsg.contains("Your order has been placed!"),
                "Order confirmation failed. Got: " + orderMsg);

        Allure.step("Verify cart is empty after order");
        String cartText = driver.findElement(By.id("cart-total")).getText();
        Assert.assertTrue(cartText.contains("0 item(s)"));

        Allure.step("Logout user");
        AccountPage account = new AccountPage(driver);
        account.clickLogoutButton();
        account.clickConfirmOnLogout();
    }
}
