package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {

    WebDriver driver;

    public HomePage(WebDriver driver){
        this.driver = driver;
    }

    By myAccount = By.partialLinkText("My Account");
    By register = By.linkText("Register");
    By login = By.linkText("Login");

    public void goToRegister(){

        driver.findElement(myAccount).click();
        driver.findElement(register).click();
    }

    public void goToLogin(){

        driver.findElement(myAccount).click();
        driver.findElement(login).click();
    }

    public boolean isLoginDisplayed(){
        try {
            driver.findElement(myAccount).click();
            return driver.findElement(login).isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }
}