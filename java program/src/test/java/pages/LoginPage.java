package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    WebDriver driver;
    By email = By.id("input-email");
    By password = By.id("input-password");
    By loginBtn = By.cssSelector("input[value='Login']");
    By errorMessage = By.cssSelector(".alert-danger");

    public LoginPage(WebDriver driver){
        this.driver = driver;
    }

    public void login(String user,String pass){
        driver.findElement(email).sendKeys(user);
        driver.findElement(password).sendKeys(pass);
        driver.findElement(loginBtn).click();
    }

    public String getErrorMessage(){
        return driver.findElement(errorMessage).getText();
    }

}

