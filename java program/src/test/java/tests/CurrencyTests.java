package tests;

import base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.AccountPage;
import pages.CurrencyPage;
import pages.HomePage;
import pages.LoginPage;
import utils.ExcelReader;

@Epic("Site Settings")
@Feature("Currency Management")
public class CurrencyTests extends BaseTest {

    @DataProvider(name = "CurrencyData")
    public Object[][] getRegData() {
        String path = "src/main/resources/testData.xlsx";
        return ExcelReader.getTestData(path, "CurrencyDataInput");
    }

    @Test(dataProvider = "CurrencyData")
    @Story("Change currency to Euro")
    @Severity(SeverityLevel.NORMAL)
    @Description("User changes the site currency from default to Euro and verifies the symbol")
    public void changeCurrencyToEuro(String Currency) throws InterruptedException {
        Allure.step("Open home page and navigate to login");
        HomePage home = new HomePage(driver);
        home.goToLogin();
        Thread.sleep(1000);

        Allure.step("Login with valid credentials");
        LoginPage login = new LoginPage(driver);
        login.login(config.get("email"), config.get("password"));
        Thread.sleep(1000);

        Allure.step("Change currency to Euro");
        CurrencyPage currency = new CurrencyPage(driver);
        currency.changeCurrencyTo(Currency);
        Thread.sleep(1000);

        Allure.step("Verify currency symbol is Euro");
        String symbol = currency.getActiveCurrencySymbol();
        Assert.assertTrue(symbol.contains("€"),
                "Currency was not changed to Euro. Current: " + symbol);

        Allure.step("Logout user");
        AccountPage account = new AccountPage(driver);
        account.clickLogoutButton();
        account.clickConfirmOnLogout();
    }

}
