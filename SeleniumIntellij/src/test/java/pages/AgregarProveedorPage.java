package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
	
public class AgregarProveedorPage {

	private WebDriver driver;
    private By brandNameFieldLocator = By.id("brandName");
    private By brandStatusSelectLocator = By.id("brandStatus");
    private By submitButtonLocator = By.id("createBrandBtn");
    private By errorNameMessageLocator = By.id("error-brand");
    private By errorStatusMessageLocator = By.id("error-status");
    
    public AgregarProveedorPage(WebDriver driver) {
        this.driver = driver;
    }
	
    public void setBrandName(String brandName) {
        WebElement brandNameField = driver.findElement(brandNameFieldLocator);
        brandNameField.clear();
        brandNameField.sendKeys(brandName);
    }
    
    public void setBrandStatus(String brandStatus) {
        WebElement brandStatusSelect = driver.findElement(brandStatusSelectLocator);
        Select selectBrandName = new Select(brandStatusSelect);
        selectBrandName.selectByVisibleText(brandStatus);
    }
    
    public void submit() {
        WebElement brandSubmitButton = driver.findElement(submitButtonLocator);
        brandSubmitButton.click();
    }
    
    public String errorBrandNameMessage() {
        WebElement errorBrandNameMessage = driver.findElement(errorNameMessageLocator);
        if (errorBrandNameMessage.isDisplayed()) {
        	String Message = errorBrandNameMessage.getText();
            return Message;
        } else {
        	return "Undisplayed";
        }
    }
    
    public String errorBrandStatusMessage() {
        WebElement errorBrandStatusMessage = driver.findElement(errorStatusMessageLocator);
        if (errorBrandStatusMessage.isDisplayed()) {
        	String Message = errorBrandStatusMessage.getText();
            return Message;
        } else {
        	return "Undisplayed";
        }
    }
    
}
