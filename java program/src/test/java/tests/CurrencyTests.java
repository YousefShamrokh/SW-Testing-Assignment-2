package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AccountPage;
import pages.CurrencyPage;
import pages.HomePage;
import pages.LoginPage;

public class CurrencyTests extends BaseTest {

    @Test
    public void changeCurrencyToEuro() {
        HomePage home = new HomePage(driver);
        home.goToLogin();

        LoginPage login = new LoginPage(driver);
        login.login(config.get("email"), config.get("password"));

        CurrencyPage currency = new CurrencyPage(driver);
        currency.changeCurrencyTo("€");

        String symbol = currency.getActiveCurrencySymbol();
        Assert.assertTrue(symbol.contains("€"),
                "Currency was not changed to Euro. Current: " + symbol);

        AccountPage account = new AccountPage(driver);
        account.clickLogoutButton();
        account.clickConfirmOnLogout();
    }



}