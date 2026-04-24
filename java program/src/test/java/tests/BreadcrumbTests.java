package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AccountPage;
import pages.CategoryPage;
import pages.HomePage;
import pages.LoginPage;

public class BreadcrumbTests extends BaseTest {

    @Test

    public void checkBreadcrumbAndSideMenu() {
        HomePage home = new HomePage(driver);
        home.goToLogin();

        LoginPage login = new LoginPage(driver);
        login.login(config.get("email"), config.get("password"));

        driver.get(config.get("categoryUrl"));

        CategoryPage category = new CategoryPage(driver);

        String lastCrumb = category.getLastBreadcrumb();
        String activeLink = category.getActiveSideMenuLink();



        Assert.assertEquals(lastCrumb, "Tablets");


        Assert.assertEquals(activeLink, "Tablets (1)");


        AccountPage account = new AccountPage(driver);
        account.clickLogoutButton();


        account.clickConfirmOnLogout();

    }}