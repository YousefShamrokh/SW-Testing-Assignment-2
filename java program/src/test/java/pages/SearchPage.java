


package pages;

import configuration.ConfigReader;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SearchPage {

    WebDriver driver;
    WebDriverWait wait;
     ConfigReader config ;

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.config = new ConfigReader();
    }


    By searchInput = By.cssSelector("#search input[name='search']");
    By searchBtn   = By.cssSelector("#search button");


    By keywordInput     = By.id("input-search");
    By categoryDropdown = By.name("category_id");
    By subCategoryCheck = By.name("sub_category");
    By subCategoryLabel = By.cssSelector("label.checkbox-inline");
    By searchButtonAdv  = By.id("button-search");


    By productResults = By.cssSelector(".product-layout .caption h4 a");


    public void searchFor(String keyword) {

        WebElement input = wait.until(
                ExpectedConditions.visibilityOfElementLocated(searchInput));

        input.clear();
        input.sendKeys(keyword);

        wait.until(ExpectedConditions.elementToBeClickable(searchBtn)).click();

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productResults));
    }


    public void searchWithCategory(String keyword, String category, boolean subCategory) {

        driver.get(config.get("searchUrl"));


        WebElement keywordField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(keywordInput));

        keywordField.clear();
        keywordField.sendKeys(keyword);


        WebElement dropdown = wait.until(
                ExpectedConditions.elementToBeClickable(categoryDropdown));

        new Select(dropdown).selectByVisibleText(category);


        WebElement checkbox = driver.findElement(subCategoryCheck);

        WebElement label = wait.until(
                ExpectedConditions.elementToBeClickable(subCategoryLabel));


                label.click();


        wait.until(ExpectedConditions.elementToBeClickable(searchButtonAdv)).click();


        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productResults));
    }


    public List<String> getProductNames() {

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(productResults));

        List<WebElement> elements = driver.findElements(productResults);

        List<String> names = new ArrayList<>();

        for (WebElement e : elements) {
            names.add(e.getText().trim());
        }

        return names;
    }



}