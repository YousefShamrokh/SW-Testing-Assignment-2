package tests;

import base.BaseTest;
import org.testng.annotations.DataProvider;
import pages.*;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.qameta.allure.*;
import utils.ExcelReader;

@Epic("User Management")
@Feature("Registration")
public class RegistrationTests extends BaseTest {

    @DataProvider(name = "RegData")
    public Object[][] getRegData() {
        String path = "src/main/resources/testData.xlsx";
        return ExcelReader.getTestData(path, "RegistrationDataInputs");
    }

    @Test(dataProvider = "RegData")
    @Story("Successful registration")
    @Severity(SeverityLevel.CRITICAL)
    @Description("User registers successfully and logs out")
    public void registrationWithoutErrors(String FirstName, String LastName, String Telephone, String Password, String ConfirmPassword) throws InterruptedException {

        Allure.step("Open home page and navigate to registration");
        HomePage home = new HomePage(driver);
        home.goToRegister();

        RegisterPage register = new RegisterPage(driver);

        String email = FirstName + LastName + System.currentTimeMillis() + "@mail.com";

        Allure.step("Fill registration form");
        register.enterFirstName(FirstName);
        Thread.sleep(1000);
        register.enterLastName(LastName);
        Thread.sleep(1000);
        register.enterEmail(email);
        Thread.sleep(1000);
        register.enterTelephone(Telephone);
        Thread.sleep(1000);
        register.enterPassword(Password);
        Thread.sleep(1000);
        register.confirmPassword(ConfirmPassword);
        Thread.sleep(1000);

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

    @Test(dataProvider = "RegData")
    @Story("Registration with errors")
    @Severity(SeverityLevel.NORMAL)
    @Description("User fails to register due to missing/invalid data")
    public void registrationWithErrors(String FirstName, String LastName, String Telephone, String Password, String ConfirmPassword) throws InterruptedException {

        Allure.step("Open home page and navigate to registration");
        HomePage home = new HomePage(driver);
        home.goToRegister();


        RegisterPage register = new RegisterPage(driver);

        String email = "user" + System.currentTimeMillis() + "@mail.com";

        Allure.step("Partially fill registration form");
        register.enterFirstName(FirstName);
        Thread.sleep(1000);
        register.enterLastName(LastName);
        Thread.sleep(1000);

        register.acceptPolicy();
        register.clickContinue();

        Allure.step("Fill more fields incorrectly");
        register.enterEmail(email);
        Thread.sleep(1000);
        register.enterTelephone(Telephone);
        Thread.sleep(1000);

        register.acceptPolicy();
        register.clickContinue();

        register.enterPassword(Password);
        Thread.sleep(1000);
        register.confirmPassword(ConfirmPassword);
        Thread.sleep(1000);

        register.acceptPolicy();
        register.clickContinue();
        AccountPage account = new AccountPage(driver);

        Allure.step("Verify registration failed");
        Assert.assertFalse(account.isRegistrationSuccessful());
    }
}
