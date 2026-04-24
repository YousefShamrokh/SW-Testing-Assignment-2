package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CategoryPage {

    WebDriver driver;
    WebDriverWait wait;

    By breadcrumbItems = By.cssSelector("ul.breadcrumb li");
    By sideMenuActive  = By.cssSelector("#column-left .list-group-item.active");
    By sortDropdown    = By.id("input-sort");
    By productNames    = By.cssSelector(".product-layout .caption h4 a");

    public CategoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getLastBreadcrumb() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(breadcrumbItems));
        List<WebElement> crumbs = driver.findElements(breadcrumbItems);
        return crumbs.get(crumbs.size() - 1).getText().trim();
    }

    public String getActiveSideMenuLink() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(sideMenuActive))
                .getText().trim();
    }


    public void sortBy(String value) {
        List<WebElement> oldProducts = driver.findElements(productNames);

        new Select(driver.findElement(sortDropdown)).selectByVisibleText(value);


        if (!oldProducts.isEmpty()) {
            wait.until(ExpectedConditions.stalenessOf(oldProducts.get(0)));
        }


        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productNames));
    }


    public List<String> getProductNames() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productNames));

        List<WebElement> elements = driver.findElements(productNames);
        List<String> names = new ArrayList<>();

        for (WebElement e : elements) {
            names.add(e.getText().trim());
        }

        return names;
    }
}