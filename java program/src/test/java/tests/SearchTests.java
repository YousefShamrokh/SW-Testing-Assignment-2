package tests;

import base.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.AccountPage;
import pages.HomePage;
import pages.LoginPage;
import pages.SearchPage;
import utils.ExcelReader;

import java.util.List;

@Epic("Product Search")
@Feature("Search Functionality")
public class SearchTests extends BaseTest {

    @DataProvider(name = "SearchingData")
    public Object[][] getRegData() {
        String path = "src/main/resources/testData.xlsx";
        return ExcelReader.getTestData(path, "SearchDataInputs");
    }

    @DataProvider(name = "SearchingCategoryData")
    public Object[][] getRegData2() {
        String path = "src/main/resources/testData.xlsx";
        return ExcelReader.getTestData(path, "SearchCategoryDataInputs");
    }

    @Test(dataProvider = "SearchingData")
    @Story("Search by product name")
    @Severity(SeverityLevel.NORMAL)
    @Description("User searches for products by name and verifies results contain the search term")
    public void searchByName(String keyword) throws InterruptedException {
        Allure.step("Open home page and navigate to login");
        HomePage home = new HomePage(driver);
        home.goToLogin();
        Thread.sleep(1000);

        Allure.step("Login with valid credentials");
        LoginPage login = new LoginPage(driver);
        login.login(config.get("email"), config.get("password"));
        Thread.sleep(1000);

        Allure.step("Navigate to home page");
        driver.get(config.get("url"));
        Thread.sleep(1000);

        Allure.step("Search for " + keyword + " products");
        SearchPage search = new SearchPage(driver);
        search.searchFor(keyword);
        Thread.sleep(1000);

        Allure.step("Retrieve search results");
        List<String> results = search.getProductNames();

        Allure.step("Verify search results are not empty");
        Assert.assertFalse(results.isEmpty(), "No products found for 'Mac'");

        Allure.step("Verify all results contain " + keyword);
        for (String name : results) {
            Assert.assertTrue(name.toLowerCase().contains(keyword.toLowerCase()),
                    "Product name does not contain " + keyword + ": " + name);
        }

        Allure.step("Logout user");
        AccountPage account = new AccountPage(driver);
        account.clickLogoutButton();
        account.clickConfirmOnLogout();
    }

    @Test(dataProvider = "SearchingCategoryData")
    @Story("Search with subcategory filter")
    @Severity(SeverityLevel.NORMAL)
    @Description("User searches for products with and without subcategory filter")
    public void searchInSubcategories(String keyword, String Category, String product) throws InterruptedException {
        Allure.step("Open home page and navigate to login");
        HomePage home = new HomePage(driver);
        home.goToLogin();
        Thread.sleep(1000);

        Allure.step("Login with valid credentials");
        LoginPage login = new LoginPage(driver);
        login.login(config.get("email"), config.get("password"));
        Thread.sleep(1000);

        Allure.step("Search for " + keyword + " without subcategory filter");
        SearchPage search = new SearchPage(driver);
        search.searchWithCategory(keyword, Category, false);
        Thread.sleep(1000);

        Allure.step("Retrieve initial search results");
        List<String> results1 = search.getProductNames();

        Allure.step("Search for " +keyword + "with subcategory filter enabled");
        search.searchWithCategory(keyword, Category, true);
        Thread.sleep(1000);

        Allure.step("Retrieve filtered search results");
        List<String> results2 = search.getProductNames();

        Allure.step("Verify " + product +" is found with subcategory filter");
        boolean found = results2.stream()
                .anyMatch(n -> n.contains(product));

        Assert.assertTrue(found,
                product +" should be found when subcategory is ON");

        Allure.step("Logout user");
        AccountPage account = new AccountPage(driver);
        account.clickLogoutButton();
        account.clickConfirmOnLogout();
    }
}
