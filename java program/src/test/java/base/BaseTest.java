package base;

import configuration.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.AllureUtils;

public class BaseTest {
    protected WebDriver driver;
    ConfigReader config = new ConfigReader();

    @BeforeMethod
    public void setup() {

        System.setProperty("webdriver.chrome.driver","chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get(config.get("url"));
    }

    @AfterMethod(alwaysRun = true)
    public void teardown(ITestResult result) {
        if (driver != null && result.getStatus() == ITestResult.FAILURE) {
            AllureUtils.takeScreenshot(driver);
        }

        if (driver != null) {
            driver.quit();
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
}
