package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
	
public class AgregarCategoriaPage {

	private WebDriver driver;
    private By categoryNameFieldLocator = By.id("categoriesName");
    private By categoryStatusSelectLocator = By.id("categoriesStatus");
    private By submitButtonLocator = By.id("createCategoriesBtn");
    private By errorNameMessageLocator = By.id("error-category");
    private By errorStatusMessageLocator = By.id("error-status");
    
    public AgregarCategoriaPage(WebDriver driver) {
        this.driver = driver;
    }
	
    public void setCategoryName(String categoryName) {
        WebElement categoryNameField = driver.findElement(categoryNameFieldLocator);
        categoryNameField.clear();
        categoryNameField.sendKeys(categoryName);
    }
    
    public void setCategoryStatus(String categoryStatus) {
        WebElement categoryStatusSelect = driver.findElement(categoryStatusSelectLocator);
        Select selectCategoryStatus = new Select(categoryStatusSelect);
        selectCategoryStatus.selectByVisibleText(categoryStatus);
    }
    
    public void submit() {
        WebElement categorySubmitButton = driver.findElement(submitButtonLocator);
        categorySubmitButton.click();
    }
    
    public String errorCategoryNameMessage() {
        WebElement errorCategoryNameMessage = driver.findElement(errorNameMessageLocator);
        if (errorCategoryNameMessage.isDisplayed()) {
        	String Message = errorCategoryNameMessage.getText();
            return Message;
        } else {
        	return "Undisplayed";
        }
    }
    
    public String errorCategoryStatusMessage() {
        WebElement errorCategoryStatusMessage = driver.findElement(errorStatusMessageLocator);
        if (errorCategoryStatusMessage.isDisplayed()) {
        	String Message = errorCategoryStatusMessage.getText();
            return Message;
        } else {
        	return "Undisplayed";
        }
    }
    
}
