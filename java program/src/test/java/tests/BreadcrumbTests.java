package tests;

import base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AccountPage;
import pages.CategoryPage;
import pages.HomePage;
import pages.LoginPage;

@Epic("Product Navigation")
@Feature("Breadcrumb and Side Menu")
public class BreadcrumbTests extends BaseTest {

    @Test
    @Story("Verify breadcrumb and side menu")
    @Severity(SeverityLevel.NORMAL)
    @Description("User navigates to category and verifies breadcrumb and side menu display correctly")
    public void checkBreadcrumbAndSideMenu() throws InterruptedException {
        Allure.step("Open home page and navigate to login");
        HomePage home = new HomePage(driver);
        home.goToLogin();
        Thread.sleep(1000);

        Allure.step("Login with valid credentials");
        LoginPage login = new LoginPage(driver);
        login.login(config.get("email"), config.get("password"));
        Thread.sleep(1000);

        Allure.step("Navigate to category page");
        driver.get(config.get("categoryUrl"));
        Thread.sleep(1000);

        Allure.step("Retrieve breadcrumb and side menu information");
        CategoryPage category = new CategoryPage(driver);
        String lastCrumb = category.getLastBreadcrumb();
        String activeLink = category.getActiveSideMenuLink();

        Allure.step("Verify breadcrumb is correct");
        Assert.assertEquals(lastCrumb, "Tablets");

        Allure.step("Verify side menu link is correct");
        Assert.assertEquals(activeLink, "Tablets (1)");

        Allure.step("Logout user");
        AccountPage account = new AccountPage(driver);
        account.clickLogoutButton();
        Thread.sleep(1000);

        account.clickConfirmOnLogout();

    }}
