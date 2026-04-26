package tests;

import base.BaseTest;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.AccountPage;
import pages.CartPage;
import pages.HomePage;
import pages.LoginPage;
import utils.ExcelReader;

import java.time.Duration;
import java.util.List;

@Epic("Shopping Cart")
@Feature("Cart Management")
public class ShoppingCartTests extends BaseTest {

    @DataProvider(name = "ShoppingCartData")
    public Object[][] getRegData() {
        String path = "src/main/resources/testData.xlsx";
        return ExcelReader.getTestData(path, "ShoppingDataInput");
    }

    @Test(dataProvider = "ShoppingCartData")
    @Story("Add items to cart and verify")
    @Severity(SeverityLevel.CRITICAL)
    @Description("User adds multiple items to cart and verifies they appear in the cart")
    public void addItemsToCartAndVerify(String Product1Url, String Product1Name, String Product2Url, String Product2Name) throws InterruptedException {
        Allure.step("Open home page and navigate to login");
        HomePage home = new HomePage(driver);
        home.goToLogin();
        Thread.sleep(1000);

        Allure.step("Login with valid credentials");
        LoginPage login = new LoginPage(driver);
        login.login(config.get("email"), config.get("password"));
        Thread.sleep(1000);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        Allure.step("Navigate to first product");
        driver.get(Product1Url); //config.get("product1Url")
        Thread.sleep(1000);

        Allure.step("Add first product " + Product1Name + " to cart");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("button-cart"))).click();
        Thread.sleep(1000);

        Allure.step("Verify success message for first product");
        CartPage cart = new CartPage(driver);
        String successMsg = cart.getSuccessMessage();
        Assert.assertTrue(successMsg.contains(Product1Name),
                "Success message does not mention" + Product1Name);

        Allure.step("Navigate to shopping cart");
        cart.goToViewCart();
        Thread.sleep(1000);

        Allure.step("Verify first product is in cart");
        List<String> cartItems = cart.getCartItemNames();
        Assert.assertTrue(cartItems.contains(Product1Name));

        Allure.step("Navigate to second product");
        driver.get(Product2Url); //config.get("product2Url")
        Thread.sleep(1000);

        Allure.step("Fill product date field");
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("input-option225"))).sendKeys("2025-11-30");
        Thread.sleep(1000);

        Allure.step("Add second product "+ Product2Name + " to cart");
        wait.until(ExpectedConditions.elementToBeClickable(
                By.id("button-cart"))).click();
        Thread.sleep(1000);

        Allure.step("Verify success message for second product");
        String laptopSuccessMsg = cart.getSuccessMessage();
        Assert.assertTrue(laptopSuccessMsg.contains(Product2Name),
                "Success message does not mention" + Product2Name);

        Allure.step("Navigate to shopping cart");
        cart.goToViewCart();
        Thread.sleep(1000);

        Allure.step("Verify both products are in cart");
        List<String> finalCartItems = cart.getCartItemNames();
        Assert.assertTrue(finalCartItems.contains(Product2Name),
                Product2Name + " not found in cart");

        Allure.step("Logout user");
        AccountPage account = new AccountPage(driver);
        account.clickLogoutButton();
        account.clickConfirmOnLogout();
    }
}
