package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
	
public class ReporteVentasPage {

	private WebDriver driver;
    private By startDateFieldLocator = By.id("startDate");
    private By endDateFieldLocator = By.id("endDate");
    private By submitButtonLocator = By.id("generateReportBtn");
    private By errorStartDateMessageLocator = By.id("error-startdate");
    private By errorEndDateMessageLocator = By.id("error-enddate");
    
    public ReporteVentasPage(WebDriver driver) {
        this.driver = driver;
    }
	
    public void setStartdate(String startDate) {
        WebElement startdateField = driver.findElement(startDateFieldLocator);
        startdateField.clear();
        startdateField.sendKeys(startDate);
    }
    
    public void setEnddate(String startDate) {
        WebElement startdateField = driver.findElement(endDateFieldLocator);
        startdateField.clear();
        startdateField.sendKeys(startDate);
    }
    
    public void submit() {
        WebElement generateReportButton = driver.findElement(submitButtonLocator);
        generateReportButton.click();
    }
    
    public String errorStartDateMessage() {
        WebElement errorStartDateMessage = driver.findElement(errorStartDateMessageLocator);
        if (errorStartDateMessage.isDisplayed()) {
        	String Message = errorStartDateMessage.getText();
            return Message;
        } else {
        	return "Undisplayed";
        }
    }
    
    public String errorEndDateMessage() {
        WebElement errorEndDateMessage = driver.findElement(errorEndDateMessageLocator);
        if (errorEndDateMessage.isDisplayed()) {
        	String Message = errorEndDateMessage.getText();
            return Message;
        } else {
        	return "Undisplayed";
        }
    }
    
}
