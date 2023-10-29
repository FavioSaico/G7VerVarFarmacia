package pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class AgregarMedicinaPage {

    private WebDriver driver;
    private By medicineImageFieldLocator = By.id("MedicineImage");
    private By medicineNameFieldLocator = By.id("productName");
    private By quantityFieldLocator = By.id("quantity");
    private By unitQuantityFieldLocator = By.id("rate");
    private By priceFieldLocator = By.id("price");
    private By prmFieldLocator = By.id("mrp");
    private By batchNoFieldLocator = By.id("Batch No");
    private By expdateFieldLocator = By.id("expdate");
    private By brandNameSelectLocator = By.id("brandName");
    //private By selectBrandNameLocator;
    private By categoryNameSelectLocator = By.id("categoryName");
    //private By selectCategoryNameLocator;
    private By productStatusSelectLocator = By.id("productStatus");
    //private By selectProductStatusLocator;
    private By submitButtonLocator= By.id("createProductBtn");
    private Alert alerta;

    public AgregarMedicinaPage(WebDriver driver) {
        this.driver = driver;
    }

    public void setMedicineImage(String pathImage) {
        WebElement medicineImageField = driver.findElement(medicineImageFieldLocator);
        medicineImageField.clear();
        medicineImageField.sendKeys(pathImage);
    }

    public void setMedicineName(String name) {
        WebElement medicineNameField = driver.findElement(medicineNameFieldLocator);
        medicineNameField.clear();
        medicineNameField.sendKeys(name);
    }

    public void setQuantity(String quantity) {
        WebElement quantityField = driver.findElement(quantityFieldLocator);
        quantityField.clear();
        quantityField.sendKeys(quantity);
    }

    public void setUnitQuantity(String unitQuantity) {
        WebElement unitQuantityField = driver.findElement(unitQuantityFieldLocator);
        unitQuantityField.clear();
        unitQuantityField.sendKeys(unitQuantity);
    }

    public void setPrice(String price) {
        WebElement priceField = driver.findElement(priceFieldLocator);
        priceField.clear();
        priceField.sendKeys(price);
    }

    public void setPrm(String prm) {
        WebElement prmField = driver.findElement(prmFieldLocator);
        prmField.clear();
        prmField.sendKeys(prm);
    }

    public void setBatchNo(String batchNo) {
        WebElement batchNoField = driver.findElement(batchNoFieldLocator);
        batchNoField.clear();
        batchNoField.sendKeys(batchNo);
    }

    public void setExpdate(String expdate) {
        WebElement expdateField = driver.findElement(expdateFieldLocator);
        expdateField.clear();
        expdateField.sendKeys(expdate);
    }

    public void setBrandName(String brandName) {
        WebElement brandNameSelect = driver.findElement(brandNameSelectLocator);
        //brandNameSelect.clear();
        Select selectBrandName = new Select(brandNameSelect);
        selectBrandName.selectByVisibleText(brandName);
    }

    public void setCategoryName(String categoryName) {
        WebElement categoryNameSelect = driver.findElement(categoryNameSelectLocator);
        //categoryNameSelect.clear();
        Select selectCategoryName = new Select(categoryNameSelect);
        selectCategoryName.selectByVisibleText(categoryName);
    }

    public void setProductStatus(String productStatus) {
        WebElement productStatusSelect = driver.findElement(productStatusSelectLocator);
        //productStatusSelect.clear();
        Select selectProductStatus = new Select(productStatusSelect);
        selectProductStatus.selectByVisibleText(productStatus);
    }

    public String getAlertMessage() {
        alerta = driver.switchTo().alert();
        String textAlertActual = alerta.getText();
        alerta.accept();
        return textAlertActual;
    }

    public void submit() {
        WebElement productSubmitButton = driver.findElement(submitButtonLocator);
        productSubmitButton.click();
    }
}
