package tests;

import base.BaseTest;
import org.testng.annotations.BeforeMethod;
import pages.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import org.testng.annotations.Listeners;
import io.qameta.allure.testng.AllureTestNg;

@Listeners({
        AllureTestNg.class,
        listeners.TestListener.class
})

public class LoginTests extends BaseTest {

    @Test
    public void validLogin(){

        HomePage home = new HomePage(driver);
        home.goToLogin();

        LoginPage login = new LoginPage(driver);
        login.login("valid1234@mail.com", "valid123");

        AccountPage account = new AccountPage(driver);

        Assert.assertTrue(account.isLogoutDisplayed());
    }

    @Test
    public void invalidLogin(){

        HomePage home = new HomePage(driver);
        home.goToLogin();

        LoginPage login = new LoginPage(driver);
        login.login("wrong@email.com","wrongpass");

        Assert.assertTrue(login.getErrorMessage()
                .contains("Warning: No match for E-Mail Address"));
    }
}

