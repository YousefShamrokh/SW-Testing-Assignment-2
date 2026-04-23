package tests;

import base.BaseTest;
import io.qameta.allure.testng.AllureTestNg;
import org.testng.annotations.Listeners;
import pages.*;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.qameta.allure.*;

@Listeners({
        AllureTestNg.class,
        listeners.TestListener.class
})

@Epic("User Management")
@Feature("Registration")
public class RegistrationTests extends BaseTest {

    @Test
    @Story("Successful registration")
    @Severity(SeverityLevel.CRITICAL)
    @Description("User registers successfully and logs out")
    public void registrationWithoutErrors() throws InterruptedException {

        Allure.step("Open home page and navigate to registration");
        HomePage home = new HomePage(driver);
        home.goToRegister();

        RegisterPage register = new RegisterPage(driver);

        String email = "user" + System.currentTimeMillis() + "@mail.com";

        Allure.step("Fill registration form");
        register.enterFirstName("John");
        register.enterLastName("Doe");
        register.enterEmail(email);
        register.enterTelephone("1234567890");
        register.enterPassword("12345");
        register.confirmPassword("12345");

        Allure.step("Accept policy and submit registration");
        register.acceptPolicy();
        register.clickContinue();

        AccountPage account = new AccountPage(driver);

        Allure.step("Verify registration success");
        Assert.assertTrue(account.isRegistrationSuccessful(), "Registration was not successful");
        Assert.assertTrue(account.isLogoutDisplayed(), "Logout option is not displayed, user might not be logged in");

        Allure.step("Logout");
        account.clickLogoutButton();


        Thread.sleep(2000);

        account.clickConfirmOnLogout();

        Thread.sleep(2000);

        Allure.step("Verify user logged out");
        Assert.assertTrue(home.isLoginDisplayed(), "Login option is not displayed after logout, user might still be logged in");
    }

    @Test
    @Story("Registration with errors")
    @Severity(SeverityLevel.NORMAL)
    @Description("User fails to register due to missing/invalid data")
    public void registrationWithErrors(){

        Allure.step("Open home page and navigate to registration");
        HomePage home = new HomePage(driver);
        home.goToRegister();

        RegisterPage register = new RegisterPage(driver);

        String email = "user" + System.currentTimeMillis() + "@mail.com";

        Allure.step("Partially fill registration form");
        register.enterFirstName("First");
        register.enterLastName("Name");

        register.acceptPolicy();
        register.clickContinue();

        Allure.step("Fill more fields incorrectly");
        register.enterEmail(email);
        register.enterTelephone("1234567890");

        register.acceptPolicy();
        register.clickContinue();

        register.enterPassword("123");
        register.confirmPassword("123");

        register.acceptPolicy();
        register.clickContinue();

        AccountPage account = new AccountPage(driver);

        Allure.step("Verify registration failed");
        Assert.assertFalse(account.isRegistrationSuccessful());
    }
}