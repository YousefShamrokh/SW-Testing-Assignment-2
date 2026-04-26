package tests;

import base.BaseTest;
import io.qameta.allure.*;
import org.openqa.selenium.*;
import org.testng.annotations.DataProvider;
import pages.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ExcelReader;

@Epic("User Management")
@Feature("Login")
public class LoginTests extends BaseTest {

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        String path = "src/main/resources/testData.xlsx";
        return ExcelReader.getTestData(path, "loginDataInputs");
    }

    @Test(dataProvider = "loginData")
    @Story("Successful login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("User logs in with valid credentials and sees logout option")
    public void validLogin(String email, String password) throws InterruptedException {

        Allure.step("Open home page and navigate to login");
        HomePage home = new HomePage(driver);
        home.goToLogin();
        Thread.sleep(1000);


        LoginPage login = new LoginPage(driver);

        Allure.step("Enter email and password");
        login.login(email, password);
        Thread.sleep(1000);


        AccountPage account = new AccountPage(driver);

        Allure.step("Verify login success");
        Thread.sleep(1000);
        Assert.assertTrue(account.isLogoutDisplayed());
//        Assert.assertFalse(account.isLogoutDisplayed()); // Intentionally failing to test screenshot on failure
        Thread.sleep(1000);
    }

    @Test(dataProvider = "loginData")
    @Story("Login with invalid credentials")
    @Severity(SeverityLevel.NORMAL)
    @Description("User fails to log in with invalid credentials and sees error message")
    public void invalidLogin(String email, String password) throws InterruptedException {

        Allure.step("Open home page and navigate to login");
        HomePage home = new HomePage(driver);
        home.goToLogin();
        Thread.sleep(1000);


        LoginPage login = new LoginPage(driver);

        Allure.step("Enter invalid email and password");
        login.login(email,password);
        Thread.sleep(1000);


        Allure.step("Verify error message is displayed");
        Assert.assertTrue(login.getErrorMessage()
                .contains("Warning: No match for E-Mail Address"));

    }
}
