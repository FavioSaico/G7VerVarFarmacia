package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AgregarFacturaPage {

    private WebDriver driver;
    private By orderNumberFieldLocator = By.name("uno");
    private By orderDateFieldLocator = By.id("orderDate");
    private By clientNameFieldLocator = By.id("clientName");
    private By clientContactFieldLocator = By.id("clientContact");
    private By productNameSelectLocator = By.id("productName1");
    private By availableQuantityFieldLocator = By.id("available_quantity1");
    private By rateValueLocator = By.id("rateValue1");
    private By priceValueLocator = By.id("priceValue1");
    private By quantityFieldLocator = By.id("quantity1");
    private By totalValueLocator = By.id("totalValue1");
    private By subtotalFieldLocator = By.id("subtotal");
    private By totalAmountValueLocator = By.id("totalAmountValue");
    private By discountFieldLocator = By.id("discount");
    private By grandTotalValueLocator = By.id("grandTotalValue");
    private By vatValueLocator = By.id("vatValue");
    private By paidFieldLocator = By.id("paid");
    private By dueValueLocator = By.id("dueValue");
    private By paymentTypeSelectLocator = By.id("paymentType");
    private By paymentStatusSelectLocator = By.id("paymentStatus");
    private By paymentPlaceSelectLocator = By.id("paymentPlace");
    private By submitButtonLocator= By.id("createOrderBtn");
    //private By errorMessageLocator = By.className("invalid-feedback");

    public AgregarFacturaPage(WebDriver driver) {
        this.driver = driver;
    }

    //Setters
    public void setOrderDate(String orderDate) {
    	WebElement orderDateField = driver.findElement(orderDateFieldLocator);
    	orderDateField.clear();
    	orderDateField.sendKeys(orderDate);
    }
    
    public void setClientName(String clientName) {
    	WebElement clientNameField = driver.findElement(clientNameFieldLocator);
    	clientNameField.clear();
    	clientNameField.sendKeys(clientName);
    }

    public void setClientContact(String clientContact) {
    	WebElement clientContactField = driver.findElement(clientContactFieldLocator);
    	clientContactField.clear();
    	clientContactField.sendKeys(clientContact);
    }
    
    public void setProductName(String productName) {
    	WebElement productNameSelect = driver.findElement(productNameSelectLocator);
    	Select selectProductName = new Select(productNameSelect);
    	selectProductName.selectByVisibleText(productName);
    }
    
    public void setQuantity(String quantity) {
    	WebElement quantityField = driver.findElement(quantityFieldLocator);
    	quantityField.clear();
    	quantityField.sendKeys(quantity);
    }
    
    public void setDiscount(String discount) {
    	WebElement discountField = driver.findElement(discountFieldLocator);
    	discountField.clear();
    	discountField.sendKeys(discount);
    }
    
    public void setPaid(String paid) {
    	WebElement paidField = driver.findElement(paidFieldLocator);
    	paidField.clear();
    	paidField.sendKeys(paid);
    }
    
    public void setPaymentType(String paymentType) {
    	WebElement paymentTypeSelect = driver.findElement(paymentTypeSelectLocator);
    	Select selectPaymentType = new Select(paymentTypeSelect);
    	selectPaymentType.selectByVisibleText(paymentType);
    }
    
    public void setPaymentStatus(String paymentStatus) {
    	WebElement paymentStatusSelect = driver.findElement(paymentStatusSelectLocator);
    	Select selectPaymentStatus = new Select(paymentStatusSelect);
    	selectPaymentStatus.selectByVisibleText(paymentStatus);
    }
    
    public void setPaymentPlace(String paymentPlace) {
    	WebElement paymentPlaceSelect = driver.findElement(paymentPlaceSelectLocator);
    	Select selectPaymentPlace = new Select(paymentPlaceSelect);
    	selectPaymentPlace.selectByVisibleText(paymentPlace);
    }
    
    //Submit
    public void submit() {
        WebElement orderSubmitButton = driver.findElement(submitButtonLocator);
        orderSubmitButton.click();
    }
    
    //Getters
    public String getErrorMessage(String id) {
        // Esperar hasta que el elemento con la clase especificada sea visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));

        String Message = errorMessage.getText();

        return Message;
    }
    
    public String getOrderNumber() {
    	WebElement orderNumberField = driver.findElement(orderNumberFieldLocator);
    	String orderNumber = orderNumberField.getAttribute("value");
    	return orderNumber;
    }

    public String getAvailableQuantity() {
    	WebElement availableQuantityField = driver.findElement(availableQuantityFieldLocator);
    	String availableQuantity = availableQuantityField.getText();
    	return availableQuantity;
    }
    
    public String getRateValue() {
    	WebElement rateValueField = driver.findElement(rateValueLocator);
    	String rateValue = rateValueField.getAttribute("value");
    	return rateValue;
    }
    
    public String getPriceValue() {
    	WebElement priceValueField = driver.findElement(priceValueLocator);
    	String priceValue = priceValueField.getAttribute("value");
    	return priceValue;
    }
    
    public String getTotalValue() {
    	WebElement totalValueField = driver.findElement(totalValueLocator);
    	String totalValue = totalValueField.getAttribute("value");
    	return totalValue;
    }
    
    public String getSubTotalValue() {
    	WebElement subTotalValueField = driver.findElement(subtotalFieldLocator);
    	String subtTotalValue = subTotalValueField.getAttribute("value");
    	return subtTotalValue;
    }
    
    public String getTotalAmountValue() {
    	WebElement totalAmountValueField = driver.findElement(totalAmountValueLocator);
    	String totalAmountValue = totalAmountValueField.getAttribute("value");
    	return totalAmountValue;
    }
    
    public String getGrandTotalValue() {
    	WebElement grandTotalValueField = driver.findElement(grandTotalValueLocator);
    	String grandTotalValue = grandTotalValueField.getAttribute("value");
    	return grandTotalValue;
    }
    
    public String getVatValue() {
    	WebElement vatValueField = driver.findElement(vatValueLocator);
    	String vatValue = vatValueField.getAttribute("value");
    	return vatValue;
    }
    
    public String getDueValue() {
    	WebElement dueValueField = driver.findElement(dueValueLocator);
    	String dueValue = dueValueField.getAttribute("value");
    	return dueValue;
    }
    
}
