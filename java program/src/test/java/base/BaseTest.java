package base;

import configuration.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
   protected ConfigReader config = new ConfigReader();
    protected WebDriverWait wait;
    @BeforeMethod
    public void setup() {

        System.setProperty("webdriver.chrome.driver","chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get(config.get("url"));
    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }
}