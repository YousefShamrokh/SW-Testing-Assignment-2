package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountPage {

    WebDriver driver;
    By successHeader = By.xpath("//h1[text()='Your Account Has Been Created!']");
    By logoutLink = By.linkText("Logout");
    By continueBtn = By.xpath("//*[contains(text(), 'Continue')]");

    public AccountPage(WebDriver driver){
        this.driver = driver;
    }

    public boolean isRegistrationSuccessful(){
        try {
            return driver.findElement(successHeader).isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    public boolean isLogoutDisplayed(){
        return driver.findElement(logoutLink).isDisplayed();
    }

    public void clickLogoutButton(){
        driver.findElement(logoutLink).click();
    }

    public void clickConfirmOnLogout() {
        driver.findElement(continueBtn).click();
    }
}