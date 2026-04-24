package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AccountPage;
import pages.HomePage;
import pages.LoginPage;
import pages.SearchPage;

import java.util.List;

public class SearchTests extends BaseTest {

    @Test
    public void searchByName() {

        HomePage home = new HomePage(driver);
        home.goToLogin();

        LoginPage login = new LoginPage(driver);
        login.login(config.get("email"), config.get("password"));


        driver.get(config.get("url"));
        SearchPage search = new SearchPage(driver);

        search.searchFor("Mac");

        List<String> results = search.getProductNames();

        Assert.assertFalse(results.isEmpty(), "No products found for 'Mac'");

        for (String name : results) {
            Assert.assertTrue(name.toLowerCase().contains("mac"),
                    "Product name does not contain Mac: " + name);
        }

        AccountPage account = new AccountPage(driver);
        account.clickLogoutButton();
        account.clickConfirmOnLogout();
    }



@Test
public void searchInSubcategories() {

    HomePage home = new HomePage(driver);
    home.goToLogin();

    LoginPage login = new LoginPage(driver);
    login.login(config.get("email"), config.get("password"));

    SearchPage search = new SearchPage(driver);


    search.searchWithCategory("Apple", "Components", false);

    List<String> results1 = search.getProductNames();

    System.out.println("STEP1 RESULTS: " + results1);


    search.searchWithCategory("Apple", "Components", true);

    List<String> results2 = search.getProductNames();

    System.out.println("STEP2 RESULTS: " + results2);

    boolean found = results2.stream()
            .anyMatch(n -> n.contains("Apple Cinema 30"));

    Assert.assertTrue(found,
            "Apple Cinema 30 should be found when subcategory is ON");

    AccountPage account = new AccountPage(driver);
    account.clickLogoutButton();
    account.clickConfirmOnLogout();
}
}