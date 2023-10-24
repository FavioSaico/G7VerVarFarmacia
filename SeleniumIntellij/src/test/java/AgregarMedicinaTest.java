import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AgregarMedicinaTest {

    private WebDriver driver;
    private WebElement medicineImageInput;
    private WebElement medicineNameField;
    private WebElement quantityField;
    private WebElement unitQuantityField;
    private WebElement priceField;
    private WebElement prmField;
    private WebElement batchNoField;
    private WebElement expdateField;

    private WebElement brandNameSelect;
    private Select selectBrandName;
    private WebElement categoryNameSelect;
    private Select selectCategoryName;
    private WebElement productStatusSelect;
    private Select selectProductStatus;
    private WebElement productSubmitButton;


    @BeforeAll // se ejecuta antes de todos pero solo una vez
    static void setupClass() {
        //WebDriverManager.chromedriver().setup();
        System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver.exe");
    }

    public void esperar(int tiempo){
        try {
            Thread.sleep(tiempo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach // antes de cada prueba
    void setup() {
        driver = new ChromeDriver();
        driver.get("http://localhost/farmacia/login.php");
        driver.manage().window().maximize();

        WebElement correoField = driver.findElement(By.id("email"));
        correoField.clear();
        correoField.sendKeys("hola@configuroweb.com");

        WebElement contrasenaField = driver.findElement(By.id("password"));
        contrasenaField.clear();
        contrasenaField.sendKeys("1234abcd..");

        WebElement submitButton = driver.findElement(By.id("loginBtn"));
        submitButton.click();

        WebElement successMessage = driver.findElement(By.className("successMessageText"));
        assertTrue(successMessage.isDisplayed(), "La información fue registrada exitosamente");

        driver.get("http://localhost/farmacia/add-product.php");

        esperar(2000);

        medicineImageInput = driver.findElement(By.id("MedicineImage"));

        medicineNameField = driver.findElement(By.id("productName"));
        medicineNameField.clear();

        quantityField = driver.findElement(By.id("quantity"));
        quantityField.clear();

        unitQuantityField = driver.findElement(By.id("rate"));
        unitQuantityField.clear();

        priceField = driver.findElement(By.id("price"));
        priceField.clear();

        prmField = driver.findElement(By.id("mrp"));
        prmField.clear();

        batchNoField = driver.findElement(By.id("Batch No"));
        batchNoField.clear();

        expdateField = driver.findElement(By.id("expdate"));
        expdateField.clear();

        brandNameSelect = driver.findElement(By.id("brandName"));
        selectBrandName = new Select(brandNameSelect);

        categoryNameSelect = driver.findElement(By.id("categoryName"));
        selectCategoryName = new Select(categoryNameSelect);

        productStatusSelect = driver.findElement(By.id("productStatus"));
        selectProductStatus = new Select(productStatusSelect);

        productSubmitButton = driver.findElement(By.id("createProductBtn"));
    }

    @AfterEach
    void tearDown() { // cerramos el navegador
        driver.quit();
    }

    @Test
    void CP01_Test(){
        medicineImageInput.sendKeys("C:\\Users\\USER\\Pictures\\generico.jpg");

        medicineNameField.sendKeys("Amoxicilina 100gr");

        quantityField.sendKeys("50");

        unitQuantityField.sendKeys("25");

        priceField.sendKeys("50");

        prmField.sendKeys("270");

        batchNoField.sendKeys("685507");

        expdateField.sendKeys("02122024"); // 02/12/2024

        selectBrandName.selectByVisibleText("Cipla");

        selectCategoryName.selectByVisibleText("Pastillas");

        selectProductStatus .selectByVisibleText("Disponible");

        esperar(2000);

        productSubmitButton.click();

        String urlEsperado = "http://localhost/farmacia/product.php";
        String urlActual = driver.getCurrentUrl();

        //assertTrue("");
        assertEquals(urlEsperado,urlActual,() -> "Medicamento no se registro correctamente");
    }

    @Test
    void CP02_Test(){
        medicineImageInput.sendKeys("C:\\Users\\USER\\Pictures\\generico.jpg");

        medicineNameField.sendKeys("Naproxeno 100gr");

        quantityField.sendKeys("30");

        unitQuantityField.sendKeys("10");

        priceField.sendKeys("13");

        prmField.sendKeys("385");

        batchNoField.sendKeys("166707");

        expdateField.sendKeys("08052025"); // 02/12/2024

        selectBrandName.selectByVisibleText("Inkafarma");

        selectCategoryName.selectByVisibleText("Vacunas");

        selectProductStatus .selectByVisibleText("No disponible");

        esperar(2000);
        productSubmitButton.click();

        String urlEsperado = "http://localhost/farmacia/product.php";
        String urlActual = driver.getCurrentUrl();

        // verificamos el cambio de url
        assertEquals(urlEsperado,urlActual,() -> "Medicamento no se registro correctamente");
    }

    @Test
    void CP03_Test(){
        medicineNameField.sendKeys("Amoxicilina 100gr");

        quantityField.sendKeys("100");

        unitQuantityField.sendKeys("600");

        priceField.sendKeys("65");

        prmField.sendKeys("117");

        batchNoField.sendKeys("282270");

        expdateField.sendKeys("09062026");

        selectBrandName.selectByVisibleText("Cipla");

        selectCategoryName.selectByVisibleText("Pastillas");

        selectProductStatus .selectByVisibleText("Disponible");

        medicineImageInput.sendKeys("C:\\Users\\USER\\Pictures\\image-6mb.jpg");

        esperar(2000);
        // mensaje de alerta esperado
        Alert alerta = driver.switchTo().alert();
        String textAlertActual = alerta.getText();
        alerta.accept();

        esperar(1000);

        String textAlertEsperado = "El tamaño de la imagen es demasiado grande. Por favor, elige una imagen más pequeña.";

        // verificamos el mensaje del alert
        assertEquals(textAlertEsperado,textAlertActual,() -> "El tamaño de la imagen no es 6MB");
    }


    @Test
    void CP04_Test(){
        medicineNameField.sendKeys("Levotiroxina 100gr");

        quantityField.sendKeys("150");

        unitQuantityField.sendKeys("30");

        priceField.sendKeys("300");

        prmField.sendKeys("451");

        batchNoField.sendKeys("337852");

        expdateField.sendKeys("20022024"); // 02/12/2024

        selectBrandName.selectByVisibleText("Cipla");

        selectCategoryName.selectByVisibleText("Pastillas");

        selectProductStatus .selectByVisibleText("No disponible");

        // input de imagen
        medicineImageInput.sendKeys("C:\\Users\\USER\\Pictures\\image.gif");

        esperar(2000);

        // mensaje de alerta esperado
        Alert alerta = driver.switchTo().alert();
        String textAlertActual = alerta.getText();
        alerta.accept();

        esperar(1000);

        String textAlertEsperado = "Solo se permiten archivos con extensiones .jpg, .jpeg o .png.";

        // verificamos el cambio de url
        assertEquals(textAlertEsperado,textAlertActual,() -> "La imagen no es un gif");
    }

    @Test
    void CP05_Test(){
        medicineNameField.sendKeys("Levotiroxina 100gr");

        quantityField.sendKeys("45");

        unitQuantityField.sendKeys("47");

        priceField.sendKeys("650");

        prmField.sendKeys("321");

        batchNoField.sendKeys("476200");

        expdateField.sendKeys("31122025"); // 31/12/2025

        selectBrandName.selectByVisibleText("Cipla");

        selectCategoryName.selectByVisibleText("Vacunas");

        selectProductStatus .selectByVisibleText("Disponible");

        productSubmitButton.click();

        esperar(1000);

        // input de imagen
        //String rutaImagen = medicineImageInput.getText();

        // mensaje de alerta
        /*Alert alerta = driver.switchTo().alert();
        String textAlertActual = alerta.getText();
        alerta.accept();

        String rutaImagenEsperado = "Selecciona una imagen."; // null*/


        WebElement successMessage = driver.findElement(By.id("missing-image"));
        assertTrue(successMessage.isDisplayed(), "Se subio una imagen");

        // verificamos si existe una imagen
        //assertEquals(rutaImagenEsperado,textAlertActual,() -> "Se subio una imagen");
    }

    @Test
    void CP06_Test(){
        medicineImageInput.sendKeys("C:\\Users\\USER\\Pictures\\generico.jpg");

        medicineNameField.sendKeys("NO");

        quantityField.sendKeys("50");

        unitQuantityField.sendKeys("25");

        priceField.sendKeys("50");

        prmField.sendKeys("123");

        batchNoField.sendKeys("583072");

        expdateField.sendKeys("08052024"); // 08/05/2024

        selectBrandName.selectByVisibleText("Cipla");

        selectCategoryName.selectByVisibleText("Vacunas");

        selectProductStatus .selectByVisibleText("No disponible");

        productSubmitButton.click();

        esperar(2000);

        // mensaje de alerta
        Alert alerta = driver.switchTo().alert();
        String textAlertActual = alerta.getText();
        alerta.accept();

        String textAlertEsperado = "El nombre de la medicina debe tener al menos 3 caracteres.";

        // verificamos el cambio de url
        assertEquals(textAlertEsperado,textAlertActual,() -> "El nombre del medicamente tiene más de 2 caracteres");
    }

    @Test
    void CP07_Test(){
        medicineImageInput.sendKeys("C:\\Users\\USER\\Pictures\\generico.jpg");

        medicineNameField.sendKeys("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        quantityField.sendKeys("30");

        unitQuantityField.sendKeys("10");

        priceField.sendKeys("13");

        prmField.sendKeys("415");

        batchNoField.sendKeys("408572");

        expdateField.sendKeys("11122024"); // 11/12/2024

        selectBrandName.selectByVisibleText("Inkafarma");

        selectCategoryName.selectByVisibleText("Pastillas");

        selectProductStatus .selectByVisibleText("Disponible");

        productSubmitButton.click();

        esperar(2000);

        // mensaje de alerta
        Alert alerta = driver.switchTo().alert();
        String textAlertActual = alerta.getText();
        alerta.accept();

        String textAlertEsperado = "El nombre de la medicina no puede tener más de 50 caracteres.";

        // verificamos el cambio de url
        assertEquals(textAlertEsperado,textAlertActual,() -> "El nombre del medicamente tiene más de 2 caracteres");
    }

    @Test
    void CP08_Test(){
        medicineImageInput.sendKeys("C:\\Users\\USER\\Pictures\\generico.jpg");

        quantityField.sendKeys("100");

        unitQuantityField.sendKeys("600");

        priceField.sendKeys("65");

        prmField.sendKeys("233");

        batchNoField.sendKeys("973296");

        expdateField.sendKeys("05102030"); // 5/10/2030

        selectBrandName.selectByVisibleText("Inkafarma");

        selectCategoryName.selectByVisibleText("Pastillas");

        selectProductStatus .selectByVisibleText("No disponible");

        productSubmitButton.click();

        esperar(2000);

        // mensaje de alerta
        //String textNameActual = medicineNameField.getAttribute("value");

        //String textNameEsperado = ""; // null

        WebElement successMessage = driver.findElement(By.id("missing-name"));
        assertTrue(successMessage.isDisplayed(), "El nombre del medicamente no es nulo");

        // verificamos el cambio de url
        //assertEquals(textNameEsperado,textNameActual,() -> "El nombre del medicamente no es nulo");
    }

}
