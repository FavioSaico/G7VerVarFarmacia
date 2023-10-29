package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    private WebDriver driver;

    private By emailFieldLocator = By.id("email");
    private By passwordFieldLocator = By.id("password");
    private By submitButtonLocator = By.id("loginBtn");

    private By successMessageLocator = By.className("successMessageText");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void setEmail(String correo) {
        WebElement emailField = driver.findElement(emailFieldLocator);
        emailField.clear();
        emailField.sendKeys(correo);
    }
    public void setPassword(String password) {
        WebElement passwordField = driver.findElement(passwordFieldLocator);
        passwordField.clear();
        passwordField.sendKeys(password);
    }
    public void submit() {
        WebElement submitButtonButton = driver.findElement(submitButtonLocator);
        submitButtonButton.click();
    }
    public boolean isSuccessMessageDisplayed() {
        WebElement successMessage = driver.findElement(successMessageLocator);
        return successMessage.isDisplayed();
    }

}
