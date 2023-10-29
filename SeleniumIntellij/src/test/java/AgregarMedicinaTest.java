import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import pages.AgregarMedicinaPage;
import pages.LoginPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AgregarMedicinaTest {

    private WebDriver driver;
    private AgregarMedicinaPage agregarMedicinaPage;
    private LoginPage loginPage;


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
        driver.get("http://localhost/G7VerVarFarmacia/farmacia/login.php");
        driver.manage().window().maximize();

        // LOGIN
        loginPage = new LoginPage(driver);

        loginPage.setEmail("hola@configuroweb.com");
        loginPage.setPassword("1234abcd..");
        loginPage.submit();

        assertTrue(loginPage.isSuccessMessageDisplayed(), "No se logro el Logeado");

        driver.get("http://localhost/G7VerVarFarmacia/farmacia/add-product.php");

        esperar(1000);

        // AGREGAR MEDICINA
        agregarMedicinaPage = new AgregarMedicinaPage(driver);
    }

    @AfterEach
    void tearDown() { // cerramos el navegador
        driver.quit();
    }

    @Test
    void CP01_Test(){
        agregarMedicinaPage.setMedicineImage("C:\\Users\\USER\\Pictures\\generico.jpg");
        agregarMedicinaPage.setMedicineName("Amoxicilina 100gr");
        agregarMedicinaPage.setQuantity("50");
        agregarMedicinaPage.setUnitQuantity("25");
        agregarMedicinaPage.setPrice("50");
        agregarMedicinaPage.setPrm("270");
        agregarMedicinaPage.setBatchNo("685507");
        agregarMedicinaPage.setExpdate("02122024"); // 02/12/2024
        agregarMedicinaPage.setBrandName("Cipla");
        agregarMedicinaPage.setCategoryName("Pastillas");
        agregarMedicinaPage.setProductStatus("Disponible");
        agregarMedicinaPage.submit();

        esperar(2000);

        String urlEsperado = "http://localhost/G7VerVarFarmacia/farmacia/product.php";
        String urlActual = driver.getCurrentUrl();
        assertEquals(urlEsperado,urlActual,() -> "Medicamento no se registro correctamente");
    }

    @Test
    void CP02_Test(){
        agregarMedicinaPage.setMedicineImage("C:\\Users\\USER\\Pictures\\generico.jpg");
        agregarMedicinaPage.setMedicineName("Naproxeno 100gr");
        agregarMedicinaPage.setQuantity("30");
        agregarMedicinaPage.setUnitQuantity("10");
        agregarMedicinaPage.setPrice("13");
        agregarMedicinaPage.setPrm("385");
        agregarMedicinaPage.setBatchNo("166707");
        agregarMedicinaPage.setExpdate("08052025"); // 08/05/2025
        agregarMedicinaPage.setBrandName("Inkafarma");
        agregarMedicinaPage.setCategoryName("Vacunas");
        agregarMedicinaPage.setProductStatus("No disponible");
        agregarMedicinaPage.submit();

        esperar(2000);

        String urlEsperado = "http://localhost/G7VerVarFarmacia/farmacia/product.php";
        String urlActual = driver.getCurrentUrl();
        // verificamos el cambio de url
        assertEquals(urlEsperado,urlActual,() -> "Medicamento no se registro correctamente");
    }

    @Test
    void CP03_Test(){
        agregarMedicinaPage.setMedicineName("Amoxicilina 100gr");
        agregarMedicinaPage.setQuantity("100");
        agregarMedicinaPage.setUnitQuantity("600");
        agregarMedicinaPage.setPrice("65");
        agregarMedicinaPage.setPrm("117");
        agregarMedicinaPage.setBatchNo("282270");
        agregarMedicinaPage.setExpdate("09062026"); // 09/06/2026
        agregarMedicinaPage.setBrandName("Cipla");
        agregarMedicinaPage.setCategoryName("Pastillas");
        agregarMedicinaPage.setProductStatus("Disponible");
        agregarMedicinaPage.setMedicineImage("C:\\Users\\USER\\Pictures\\image-6mb.jpg");
        esperar(2000);
        String textAlertActual = agregarMedicinaPage.getAlertMessage();

        String textAlertEsperado = "El tamaño de la imagen es demasiado grande. Por favor, elige una imagen más pequeña.";
        // verificamos el mensaje del alert
        assertEquals(textAlertEsperado,textAlertActual,() -> "El tamaño de la imagen no es 6MB");
    }

    @Test
    void CP04_Test(){
        agregarMedicinaPage.setMedicineName("Levotiroxina 100gr");
        agregarMedicinaPage.setQuantity("150");
        agregarMedicinaPage.setUnitQuantity("30");
        agregarMedicinaPage.setPrice("300");
        agregarMedicinaPage.setPrm("451");
        agregarMedicinaPage.setBatchNo("337852");
        agregarMedicinaPage.setExpdate("20022024"); // 20/02/2024
        agregarMedicinaPage.setBrandName("Cipla");
        agregarMedicinaPage.setCategoryName("Pastillas");
        agregarMedicinaPage.setProductStatus("No disponible");
        agregarMedicinaPage.setMedicineImage("C:\\Users\\USER\\Pictures\\image.gif");
        esperar(1000);
        String textAlertActual = agregarMedicinaPage.getAlertMessage();

        String textAlertEsperado = "Solo se permiten archivos con extensiones .jpg, .jpeg o .png.";
        // verificamos el cambio de url
        assertEquals(textAlertEsperado,textAlertActual,() -> "La imagen no es un gif");
    }

    @Test
    void CP05_Test(){
        agregarMedicinaPage.setMedicineName("Levotiroxina 100gr");
        agregarMedicinaPage.setQuantity("45");
        agregarMedicinaPage.setUnitQuantity("47");
        agregarMedicinaPage.setPrice("650");
        agregarMedicinaPage.setPrm("321");
        agregarMedicinaPage.setBatchNo("476200");
        agregarMedicinaPage.setExpdate("231122025"); // 31/12/2025
        agregarMedicinaPage.setBrandName("Cipla");
        agregarMedicinaPage.setCategoryName("Vacunas");
        agregarMedicinaPage.setProductStatus("Disponible");
        //agregarMedicinaPage.setMedicineImage(null);
        agregarMedicinaPage.submit();

        esperar(1000);

        WebElement successMessage = driver.findElement(By.id("missing-image"));
        assertTrue(successMessage.isDisplayed(), "Se subio una imagen");
    }

    @Test
    void CP06_Test(){
        agregarMedicinaPage.setMedicineImage("C:\\Users\\USER\\Pictures\\generico.jpg");
        agregarMedicinaPage.setMedicineName("NO");
        agregarMedicinaPage.setQuantity("50");
        agregarMedicinaPage.setUnitQuantity("25");
        agregarMedicinaPage.setPrice("50");
        agregarMedicinaPage.setPrm("123");
        agregarMedicinaPage.setBatchNo("583072");
        agregarMedicinaPage.setExpdate("08052024"); // 08/05/2024
        agregarMedicinaPage.setBrandName("Cipla");
        agregarMedicinaPage.setCategoryName("Vacunas");
        agregarMedicinaPage.setProductStatus("No disponible");
        agregarMedicinaPage.submit();
        esperar(2000);

        // mensaje de alerta
        String textAlertActual = agregarMedicinaPage.getAlertMessage();
        String textAlertEsperado = "El nombre de la medicina debe tener al menos 3 caracteres.";
        // verificamos el cambio de url
        assertEquals(textAlertEsperado,textAlertActual,() -> "El nombre del medicamente tiene más de 2 caracteres");
    }

    @Test
    void CP07_Test(){
        agregarMedicinaPage.setMedicineImage("C:\\Users\\USER\\Pictures\\generico.jpg");
        agregarMedicinaPage.setMedicineName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        agregarMedicinaPage.setQuantity("30");
        agregarMedicinaPage.setUnitQuantity("10");
        agregarMedicinaPage.setPrice("13");
        agregarMedicinaPage.setPrm("415");
        agregarMedicinaPage.setBatchNo("408572");
        agregarMedicinaPage.setExpdate("11122024"); // 11/12/2024
        agregarMedicinaPage.setBrandName("Inkafarma");
        agregarMedicinaPage.setCategoryName("Pastillas");
        agregarMedicinaPage.setProductStatus("Disponible");
        agregarMedicinaPage.submit();
        esperar(2000);

        // mensaje de alerta
        String textAlertActual = agregarMedicinaPage.getAlertMessage();
        String textAlertEsperado = "El nombre de la medicina no puede tener más de 50 caracteres.";
        // verificamos el cambio de url
        assertEquals(textAlertEsperado,textAlertActual,() -> "El nombre del medicamente tiene más de 2 caracteres");
    }

    @Test
    void CP08_Test(){
        agregarMedicinaPage.setMedicineImage("C:\\Users\\USER\\Pictures\\generico.jpg");
        //agregarMedicinaPage.setMedicineName(null);
        agregarMedicinaPage.setQuantity("100");
        agregarMedicinaPage.setUnitQuantity("600");
        agregarMedicinaPage.setPrice("65");
        agregarMedicinaPage.setPrm("233");
        agregarMedicinaPage.setBatchNo("973296");
        agregarMedicinaPage.setExpdate("05102030"); // 5/10/2030
        agregarMedicinaPage.setBrandName("Inkafarma");
        agregarMedicinaPage.setCategoryName("Pastillas");
        agregarMedicinaPage.setProductStatus("No disponible");
        agregarMedicinaPage.submit();

        esperar(1000);

        WebElement successMessage = driver.findElement(By.id("missing-name"));
        assertTrue(successMessage.isDisplayed(), "El nombre del medicamento no es nulo");
    }

    @Test
    void CP09_Test(){
        agregarMedicinaPage.setMedicineImage("C:\\Users\\USER\\Pictures\\generico.jpg");
        agregarMedicinaPage.setMedicineName("Amoxicilina 100gr");
        agregarMedicinaPage.setQuantity("0");
        agregarMedicinaPage.setUnitQuantity("30");
        agregarMedicinaPage.setPrice("300");
        agregarMedicinaPage.setPrm("322");
        agregarMedicinaPage.setBatchNo("346389");
        agregarMedicinaPage.setExpdate("15052028"); // 15/05/2028
        agregarMedicinaPage.setBrandName("Inkafarma");
        agregarMedicinaPage.setCategoryName("Vacunas");
        agregarMedicinaPage.setProductStatus("Disponible");
        agregarMedicinaPage.submit();

        esperar(1000);

        // mensaje de alerta
        String textAlertActual = agregarMedicinaPage.getAlertMessage();
        String textAlertEsperado = "El valor debe ser superior o igual a 1";
        // verificamos el cambio de url
        assertEquals(textAlertEsperado,textAlertActual,() -> "La cantidad es mayor a 1");
    }

    @Test
    void CP010_Test(){
        agregarMedicinaPage.setMedicineImage("C:\\Users\\USER\\Pictures\\generico.jpg");
        agregarMedicinaPage.setMedicineName("Levotiroxina 100gr");
        agregarMedicinaPage.setQuantity("1000");
        agregarMedicinaPage.setUnitQuantity("47");
        agregarMedicinaPage.setPrice("650");
        agregarMedicinaPage.setPrm("413");
        agregarMedicinaPage.setBatchNo("930638");
        agregarMedicinaPage.setExpdate("09062026"); // 09/06/2026
        agregarMedicinaPage.setBrandName("Inkafarma");
        agregarMedicinaPage.setCategoryName("Vacunas");
        agregarMedicinaPage.setProductStatus("No disponible");
        agregarMedicinaPage.submit();

        esperar(1000);

        // mensaje de alerta
        String textAlertActual = agregarMedicinaPage.getAlertMessage();
        String textAlertEsperado = "El valor debe ser inferior o igual a 999";
        // verificamos el cambio de url
        assertEquals(textAlertEsperado,textAlertActual,() -> "La cantidad es menor de 1000");
    }

    @Test
    void CP011_Test(){
        agregarMedicinaPage.setMedicineImage("C:\\Users\\USER\\Pictures\\generico.jpg");
        agregarMedicinaPage.setMedicineName("Levotiroxina 100gr");
        //agregarMedicinaPage.setQuantity("1000");
        agregarMedicinaPage.setUnitQuantity("98");
        agregarMedicinaPage.setPrice("50");
        agregarMedicinaPage.setPrm("143");
        agregarMedicinaPage.setBatchNo("417099");
        agregarMedicinaPage.setExpdate("15022027"); // 15/02/2027
        agregarMedicinaPage.setBrandName("Cipla");
        agregarMedicinaPage.setCategoryName("Pastillas");
        agregarMedicinaPage.setProductStatus("Disponible");
        agregarMedicinaPage.submit();

        esperar(1000);

        WebElement successMessage = driver.findElement(By.id("missing-quantity"));
        assertTrue(successMessage.isDisplayed(), "La cantidad del medicamento no es nulo");
    }

}
