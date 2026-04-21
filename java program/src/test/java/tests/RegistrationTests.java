package tests;

import base.BaseTest;
import pages.*;

import org.testng.Assert;
import org.testng.annotations.Test;

public class RegistrationTests extends BaseTest {

    @Test
    public void registrationWithoutErrors() throws InterruptedException {

        HomePage home = new HomePage(driver);
        home.goToRegister();

        RegisterPage register = new RegisterPage(driver);

        String email = "user" + System.currentTimeMillis() + "@mail.com";

        register.enterFirstName("John");
        register.enterLastName("Doe");
        register.enterEmail(email);
        register.enterTelephone("1234567890");
        register.enterPassword("12345");
        register.confirmPassword("12345");

        register.acceptPolicy();
        register.clickContinue();

        AccountPage account = new AccountPage(driver);

        Assert.assertTrue(account.isRegistrationSuccessful(), "Registration was not successful");
        Assert.assertTrue(account.isLogoutDisplayed(), "Logout option is not displayed, user might not be logged in");

        account.clickLogoutButton();


        Thread.sleep(2000);

        account.clickConfirmOnLogout();

        Thread.sleep(2000);

        Assert.assertTrue(home.isLoginDisplayed(), "Login option is not displayed after logout, user might still be logged in");
    }

    @Test
    public void registrationWithErrors(){

        HomePage home = new HomePage(driver);
        home.goToRegister();

        RegisterPage register = new RegisterPage(driver);

        String email = "user" + System.currentTimeMillis() + "@mail.com";

        register.enterFirstName("First");
        register.enterLastName("Name");

        register.acceptPolicy();
        register.clickContinue();

        register.enterEmail(email);
        register.enterTelephone("1234567890");

        register.acceptPolicy();
        register.clickContinue();

        register.enterPassword("123");
        register.confirmPassword("123");

        register.acceptPolicy();
        register.clickContinue();

        AccountPage account = new AccountPage(driver);
        Assert.assertFalse(account.isRegistrationSuccessful());
    }
}