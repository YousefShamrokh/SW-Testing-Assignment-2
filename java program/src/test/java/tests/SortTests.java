package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AccountPage;
import pages.CategoryPage;
import pages.HomePage;
import pages.LoginPage;

import java.util.ArrayList;
import java.util.List;

public class SortTests extends BaseTest {

    @Test
    public void sortByNameAscendingAndDescending() {

        HomePage home = new HomePage(driver);
        home.goToLogin();

        LoginPage login = new LoginPage(driver);
        login.login(config.get("email"), config.get("password"));


        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Phones & PDAs"))).click();

        CategoryPage category = new CategoryPage(driver);


        category.sortBy("Name (A - Z)");

        List<String> ascNames = category.getProductNames();
        List<String> ascSorted = new ArrayList<>(ascNames);

        ascSorted.sort(String.CASE_INSENSITIVE_ORDER);

        Assert.assertEquals(ascNames, ascSorted, "Products are not sorted A-Z");


        category.sortBy("Name (Z - A)");

        List<String> descNames = category.getProductNames();
        List<String> descSorted = new ArrayList<>(descNames);

        descSorted.sort(String.CASE_INSENSITIVE_ORDER.reversed());

        Assert.assertEquals(descNames, descSorted, "Products are not sorted Z-A");

        AccountPage account = new AccountPage(driver);
        account.clickLogoutButton();
        account.clickConfirmOnLogout();
    }
}