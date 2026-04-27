package tests;

import base.BaseTest;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.AccountPage;
import pages.CategoryPage;
import pages.HomePage;
import pages.LoginPage;
import utils.ExcelReader;

import java.util.ArrayList;
import java.util.List;

@Epic("Product Browsing")
@Feature("Product Sorting")
public class SortTests extends BaseTest {

    @DataProvider(name = "SortData")
    public Object[][] getRegData() {
        String path = "src/main/resources/testData.xlsx";
        return ExcelReader.getTestData(path, "SortDataInputs");
    }

    @Test(dataProvider = "SortData")
    @Story("Sort products by name ascending and descending")
    @Severity(SeverityLevel.NORMAL)
    @Description("User sorts products by name in both ascending (A-Z) and descending (Z-A) order")
    public void sortByNameAscendingAndDescending(String sortTab, String SortA, String SortB) throws InterruptedException {
        Allure.step("Open home page and navigate to login");
        HomePage home = new HomePage(driver);
        home.goToLogin();
        Thread.sleep(1000);

        Allure.step("Login with valid credentials");
        LoginPage login = new LoginPage(driver);
        login.login(config.get("email"), config.get("password"));
        Thread.sleep(1000);

        Allure.step("Navigate to " + sortTab +  " category");
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText(sortTab))).click();
        Thread.sleep(1000);

        CategoryPage category = new CategoryPage(driver);

        Allure.step("Sort products by Name (A - Z)");
        category.sortBy(SortA);
        Thread.sleep(1000);

        Allure.step("Retrieve sorted product names (A-Z)");
        List<String> ascNames = category.getProductNames();
        List<String> ascSorted = new ArrayList<>(ascNames);

        Allure.step("Verify products are sorted A-Z");
        ascSorted.sort(String.CASE_INSENSITIVE_ORDER);
        Assert.assertEquals(ascNames, ascSorted, "Products are not sorted A-Z");

        Allure.step("Sort products by Name (Z - A)");
        category.sortBy(SortB);
        Thread.sleep(1000);

        Allure.step("Retrieve sorted product names (Z-A)");
        List<String> descNames = category.getProductNames();
        List<String> descSorted = new ArrayList<>(descNames);

        Allure.step("Verify products are sorted Z-A");
        descSorted.sort(String.CASE_INSENSITIVE_ORDER.reversed());
        Assert.assertEquals(descNames, descSorted, "Products are not sorted Z-A");

        Allure.step("Logout user");
        AccountPage account = new AccountPage(driver);
        account.clickLogoutButton();
        account.clickConfirmOnLogout();
    }
}
