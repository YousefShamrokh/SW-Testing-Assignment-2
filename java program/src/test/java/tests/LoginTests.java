package tests;

import base.BaseTest;
import io.qameta.allure.*;
import org.openqa.selenium.*;
import pages.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import org.testng.annotations.Listeners;

@Listeners({
        listeners.TestListener.class
})

@Epic("User Management")
@Feature("Login")
public class LoginTests extends BaseTest {

    @Test
    @Story("Successful login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("User logs in with valid credentials and sees logout option")
    public void validLogin() throws InterruptedException {

        Allure.step("Open home page and navigate to login");
        HomePage home = new HomePage(driver);
        home.goToLogin();
        Thread.sleep(2000);
        

        LoginPage login = new LoginPage(driver);

        Allure.step("Enter email and password");
        login.login("valid1234@mail.com", "valid123");
        Thread.sleep(2000);


        AccountPage account = new AccountPage(driver);

        Allure.step("Verify login success");
        Thread.sleep(2000);
//        Assert.assertTrue(account.isLogoutDisplayed());
        Assert.assertFalse(account.isLogoutDisplayed()); // Intentionally failing to test screenshot on failure
        Thread.sleep(2000);
    }

    @Test
    @Story("Login with invalid credentials")
    @Severity(SeverityLevel.NORMAL)
    @Description("User fails to log in with invalid credentials and sees error message")
    public void invalidLogin() throws InterruptedException {

        Allure.step("Open home page and navigate to login");
        HomePage home = new HomePage(driver);
        home.goToLogin();
        Thread.sleep(2000);


        LoginPage login = new LoginPage(driver);

        Allure.step("Enter invalid email and password");
        login.login("wrong@email.com","wrongPass");
        Thread.sleep(2000);


        Allure.step("Verify error message is displayed");
        Assert.assertTrue(login.getErrorMessage()
                .contains("Warning: No match for E-Mail Address"));

    }
}
