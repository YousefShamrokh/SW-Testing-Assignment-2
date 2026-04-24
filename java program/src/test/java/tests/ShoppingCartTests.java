package tests;

import base.BaseTest;
import org.openqa.selenium.By;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AccountPage;
import pages.CartPage;
import pages.HomePage;
import pages.LoginPage;
import java.time.Duration;
import java.util.List;

public class ShoppingCartTests extends BaseTest {

    @Test
    public void addItemsToCartAndVerify() {
        HomePage home = new HomePage(driver);
        home.goToLogin();

        LoginPage login = new LoginPage(driver);
        login.login(config.get("email"), config.get("password"));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get(config.get("product1Url"));

        wait.until(ExpectedConditions.elementToBeClickable(By.id("button-cart"))).click();

        CartPage cart = new CartPage(driver);
        String successMsg = cart.getSuccessMessage();
        Assert.assertTrue(successMsg.contains("Samsung Galaxy Tab 10.1"),
                "Success message does not mention Samsung Galaxy Tab 10.1");


        cart.goToViewCart();
        List<String> cartItems = cart.getCartItemNames();
        Assert.assertTrue(cartItems.contains("Samsung Galaxy Tab 10.1"));


driver.get(config.get("product2Url"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("input-option225"))).sendKeys("2025-11-30");


        wait.until(ExpectedConditions.elementToBeClickable(
                By.id("button-cart"))).click();

        String laptopSuccessMsg = cart.getSuccessMessage();
        Assert.assertTrue(laptopSuccessMsg.contains("HP LP3065"),
                "Success message does not mention HP LP3065");


        cart.goToViewCart();
        List<String> finalCartItems = cart.getCartItemNames();
        Assert.assertTrue(finalCartItems.contains("HP LP3065"),
                "HP LP3065 not found in cart");
        AccountPage account = new AccountPage(driver);
        account.clickLogoutButton();
        account.clickConfirmOnLogout();
    }
}