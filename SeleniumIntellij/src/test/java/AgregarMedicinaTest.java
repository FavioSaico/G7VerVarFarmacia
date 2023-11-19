import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.AgregarMedicinaPage;
import pages.LoginPage;

import java.time.Duration;
import java.util.Calendar;
import java.util.Objects;

import io.github.cdimascio.dotenv.Dotenv;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AgregarMedicinaTest {

    private WebDriver driver;
    private AgregarMedicinaPage agregarMedicinaPage;
    private LoginPage loginPage;
    private Dotenv dotenv = Dotenv.configure().load();
    private WebDriverWait waiter;
    private String navegador = "firefox"; // "edge", "firefox" y "chrome"

    @BeforeEach // antes de cada prueba
    void setup() {
        driver = WebDriverManager.getInstance(navegador).create();

        driver.get("http://localhost/G7VerVarFarmacia/farmacia/login.php");
        driver.manage().window().maximize();

        // LOGIN
        loginPage = new LoginPage(driver);

        loginPage.setEmail(dotenv.get("EMAIL"));
        loginPage.setPassword(dotenv.get("PASSWORD"));
        loginPage.submit();

        waiter = new WebDriverWait(driver, Duration.ofSeconds(3));
        waiter.until(ExpectedConditions.urlToBe("http://localhost/G7VerVarFarmacia/farmacia/dashboard.php"));

        // AGREGAR MEDICINA
        driver.get("http://localhost/G7VerVarFarmacia/farmacia/add-product.php");
        agregarMedicinaPage = new AgregarMedicinaPage(driver);
    }

    @AfterEach
    void tearDown() { // cerramos el navegador
        driver.quit();
    }

    @Test
    void CP01_Test(){
        agregarMedicinaPage.setMedicineImage("image-3mb.jpg");
        agregarMedicinaPage.setMedicineName("Amoxicilina 100gr");
        agregarMedicinaPage.setQuantity("50");
        agregarMedicinaPage.setUnitQuantity("25");
        agregarMedicinaPage.setPrice("50");
        agregarMedicinaPage.setPrm("270");
        agregarMedicinaPage.setBatchNo("685507");
        agregarMedicinaPage.setExpdate((Objects.equals(navegador, "firefox"))?"2024-12-02":"02/12/2024"); // 02/12/2024
        agregarMedicinaPage.setBrandName("Cipla");
        agregarMedicinaPage.setCategoryName("Pastillas");
        agregarMedicinaPage.setProductStatus("Disponible");
        agregarMedicinaPage.submit();

        waiter = new WebDriverWait(driver, Duration.ofSeconds(3));
        boolean isRegistered = waiter.until(ExpectedConditions.urlToBe("http://localhost/G7VerVarFarmacia/farmacia/product.php"));

        // verificamos el cambio de url
        assertTrue(isRegistered,() -> "Medicamento no se registro correctamente");
    }

    @Test
    void CP02_Test(){
        agregarMedicinaPage.setMedicineImage("image-4mb.jpg");
        agregarMedicinaPage.setMedicineName("Naproxeno 100gr");
        agregarMedicinaPage.setQuantity("30");
        agregarMedicinaPage.setUnitQuantity("10");
        agregarMedicinaPage.setPrice("13");
        agregarMedicinaPage.setPrm("385");
        agregarMedicinaPage.setBatchNo("166707");
        agregarMedicinaPage.setExpdate((Objects.equals(navegador, "firefox"))?"2025-05-08":"08/05/2025"); // 08/05/2025
        agregarMedicinaPage.setBrandName("Inkafarma");
        agregarMedicinaPage.setCategoryName("Vacunas");
        agregarMedicinaPage.setProductStatus("No disponible");
        agregarMedicinaPage.submit();

        waiter = new WebDriverWait(driver, Duration.ofSeconds(3));
        boolean isRegistered = waiter.until(ExpectedConditions.urlToBe("http://localhost/G7VerVarFarmacia/farmacia/product.php"));

        // verificamos el cambio de url
        assertTrue(isRegistered,() -> "Medicamento no se registro correctamente");
    }

    @Test
    void CP03_Test(){
        agregarMedicinaPage.setMedicineName("Amoxicilina 100gr");
        agregarMedicinaPage.setQuantity("100");
        agregarMedicinaPage.setUnitQuantity("600");
        agregarMedicinaPage.setPrice("65");
        agregarMedicinaPage.setPrm("117");
        agregarMedicinaPage.setBatchNo("282270");
        agregarMedicinaPage.setExpdate((Objects.equals(navegador, "firefox"))?"2026-06-09":"09/06/2026"); // 09/06/2026
        agregarMedicinaPage.setBrandName("Cipla");
        agregarMedicinaPage.setCategoryName("Pastillas");
        agregarMedicinaPage.setProductStatus("Disponible");
        agregarMedicinaPage.setMedicineImage("image-6mb.jpg");

        String textErrorActual = agregarMedicinaPage.getErrorMessage("missing-image");
        String textErrorEsperado = "El tamaño de la imagen es demasiado grande. Por favor, elige una imagen más pequeña.";
        // verificamos el mensaje de error
        assertEquals(textErrorEsperado,textErrorActual,() -> "El tamaño de la imagen no es 6MB");
    }

    @Test
    void CP04_Test(){
        agregarMedicinaPage.setMedicineName("Levotiroxina 100gr");
        agregarMedicinaPage.setQuantity("150");
        agregarMedicinaPage.setUnitQuantity("30");
        agregarMedicinaPage.setPrice("300");
        agregarMedicinaPage.setPrm("451");
        agregarMedicinaPage.setBatchNo("337852");
        agregarMedicinaPage.setExpdate((Objects.equals(navegador, "firefox"))?"2024-02-20":"20/02/2024"); // 20/02/2024
        agregarMedicinaPage.setBrandName("Cipla");
        agregarMedicinaPage.setCategoryName("Pastillas");
        agregarMedicinaPage.setProductStatus("No disponible");
        agregarMedicinaPage.setMedicineImage("image-4mb.gif");

        String textErrorActual = agregarMedicinaPage.getErrorMessage("missing-image");
        String textErrorEsperado = "Solo se permiten archivos con extensiones .jpg, .jpeg o .png.";
        assertEquals(textErrorEsperado,textErrorActual,() -> "La imagen no es un gif");
    }

    @Test
    void CP05_Test(){
        agregarMedicinaPage.setMedicineName("Levotiroxina 100gr");
        agregarMedicinaPage.setQuantity("45");
        agregarMedicinaPage.setUnitQuantity("47");
        agregarMedicinaPage.setPrice("650");
        agregarMedicinaPage.setPrm("321");
        agregarMedicinaPage.setBatchNo("476200");
        agregarMedicinaPage.setExpdate((Objects.equals(navegador, "firefox"))?"2025-12-31":"31/12/2025"); // 31/12/2025
        agregarMedicinaPage.setBrandName("Cipla");
        agregarMedicinaPage.setCategoryName("Vacunas");
        agregarMedicinaPage.setProductStatus("Disponible");
        agregarMedicinaPage.submit();

        String textErrorActual = agregarMedicinaPage.getErrorMessage("missing-image");
        String textErrorEsperado = "Selecciona una imagen.";
        assertEquals(textErrorEsperado,textErrorActual,() -> "Se subio una imagen");
    }

    @Test
    void CP06_Test(){
        agregarMedicinaPage.setMedicineImage("image-2mb.png");
        agregarMedicinaPage.setMedicineName("NO");
        agregarMedicinaPage.setQuantity("50");
        agregarMedicinaPage.setUnitQuantity("25");
        agregarMedicinaPage.setPrice("50");
        agregarMedicinaPage.setPrm("123");
        agregarMedicinaPage.setBatchNo("583072");
        agregarMedicinaPage.setExpdate((Objects.equals(navegador, "firefox"))?"2024-05-08":"08/05/2024"); // 08/05/2024
        agregarMedicinaPage.setBrandName("Cipla");
        agregarMedicinaPage.setCategoryName("Vacunas");
        agregarMedicinaPage.setProductStatus("No disponible");
        agregarMedicinaPage.submit();

        String textErrorActual = agregarMedicinaPage.getErrorMessage("missing-name");
        String textErrorEsperado = "El nombre de la medicina debe tener al menos 3 caracteres.";
        assertEquals(textErrorEsperado,textErrorActual,() -> "El nombre del medicamente tiene más de 2 caracteres");
    }

    @Test
    void CP07_Test(){
        agregarMedicinaPage.setMedicineImage("image-4mb.jpg");
        agregarMedicinaPage.setMedicineName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        agregarMedicinaPage.setQuantity("30");
        agregarMedicinaPage.setUnitQuantity("10");
        agregarMedicinaPage.setPrice("13");
        agregarMedicinaPage.setPrm("415");
        agregarMedicinaPage.setBatchNo("408572");
        agregarMedicinaPage.setExpdate((Objects.equals(navegador, "firefox"))?"2024-12-11":"11/12/2024"); // 11/12/2024
        agregarMedicinaPage.setBrandName("Inkafarma");
        agregarMedicinaPage.setCategoryName("Pastillas");
        agregarMedicinaPage.setProductStatus("Disponible");
        agregarMedicinaPage.submit();

        String textErrorActual = agregarMedicinaPage.getErrorMessage("missing-name");
        String textErrorEsperado = "El nombre de la medicina no puede tener más de 50 caracteres.";
        assertEquals(textErrorEsperado,textErrorActual,() -> "El nombre del medicamente tiene más de 2 caracteres");
    }

    @Test
    void CP08_Test(){
        agregarMedicinaPage.setMedicineImage("image-3mb.jpg");
        //agregarMedicinaPage.setMedicineName(null);
        agregarMedicinaPage.setQuantity("100");
        agregarMedicinaPage.setUnitQuantity("600");
        agregarMedicinaPage.setPrice("65");
        agregarMedicinaPage.setPrm("233");
        agregarMedicinaPage.setBatchNo("973296");
        agregarMedicinaPage.setExpdate((Objects.equals(navegador, "firefox"))?"2030-10-05":"05/10/2030"); // 05/10/2030
        agregarMedicinaPage.setBrandName("Inkafarma");
        agregarMedicinaPage.setCategoryName("Pastillas");
        agregarMedicinaPage.setProductStatus("No disponible");
        agregarMedicinaPage.submit();

        String textErrorActual = agregarMedicinaPage.getErrorMessage("missing-name");
        String textErrorEsperado = "Completa este campo";
        assertEquals(textErrorEsperado,textErrorActual,() -> "El nombre del medicamento no es nulo");
    }

    @Test
    void CP09_Test(){
        agregarMedicinaPage.setMedicineImage("image-2mb.png");
        agregarMedicinaPage.setMedicineName("Amoxicilina 100gr");
        agregarMedicinaPage.setQuantity("0");
        agregarMedicinaPage.setUnitQuantity("30");
        agregarMedicinaPage.setPrice("300");
        agregarMedicinaPage.setPrm("322");
        agregarMedicinaPage.setBatchNo("346389");
        agregarMedicinaPage.setExpdate((Objects.equals(navegador, "firefox"))?"2028-05-15":"15/05/2028"); // 15/05/2028
        agregarMedicinaPage.setBrandName("Inkafarma");
        agregarMedicinaPage.setCategoryName("Vacunas");
        agregarMedicinaPage.setProductStatus("Disponible");
        agregarMedicinaPage.submit();

        String textErrorActual = agregarMedicinaPage.getErrorMessage("missing-quantity");
        String textErrorEsperado = "El valor debe ser superior o igual a 1";
        assertEquals(textErrorEsperado,textErrorActual,() -> "La cantidad es mayor a 1");
    }

    @Test
    void CP010_Test(){
        agregarMedicinaPage.setMedicineImage("image-4mb.jpg");
        agregarMedicinaPage.setMedicineName("Levotiroxina 100gr");
        agregarMedicinaPage.setQuantity("1000");
        agregarMedicinaPage.setUnitQuantity("47");
        agregarMedicinaPage.setPrice("650");
        agregarMedicinaPage.setPrm("413");
        agregarMedicinaPage.setBatchNo("930638");
        agregarMedicinaPage.setExpdate((Objects.equals(navegador, "firefox"))?"2026-06-09":"09/06/2026"); // 09/06/2026
        agregarMedicinaPage.setBrandName("Inkafarma");
        agregarMedicinaPage.setCategoryName("Vacunas");
        agregarMedicinaPage.setProductStatus("No disponible");
        agregarMedicinaPage.submit();

        String textErrorActual = agregarMedicinaPage.getErrorMessage("missing-quantity");
        String textErrorEsperado = "El valor debe ser inferior o igual a 999";
        assertEquals(textErrorEsperado,textErrorActual,() -> "La cantidad es menor de 1000");
    }

    @Test
    void CP011_Test(){
        agregarMedicinaPage.setMedicineImage("image-3mb.jpg");
        agregarMedicinaPage.setMedicineName("Levotiroxina 100gr");
        //agregarMedicinaPage.setQuantity("1000");
        agregarMedicinaPage.setUnitQuantity("98");
        agregarMedicinaPage.setPrice("50");
        agregarMedicinaPage.setPrm("143");
        agregarMedicinaPage.setBatchNo("417099");
        agregarMedicinaPage.setExpdate((Objects.equals(navegador, "firefox"))?"2027-02-15":"15/02/2027"); // 15/02/2027
        agregarMedicinaPage.setBrandName("Cipla");
        agregarMedicinaPage.setCategoryName("Pastillas");
        agregarMedicinaPage.setProductStatus("Disponible");
        agregarMedicinaPage.submit();

        String textErrorActual = agregarMedicinaPage.getErrorMessage("missing-quantity");
        String textErrorEsperado = "Completa este campo";
        assertEquals(textErrorEsperado,textErrorActual,() -> "La cantidad del medicamento no es nulo");
    }

    @Test
    void CP012_Test(){
        agregarMedicinaPage.setMedicineImage("image-2mb.png");
        agregarMedicinaPage.setMedicineName("Amoxicilina 100gr");
        agregarMedicinaPage.setQuantity("30");
        agregarMedicinaPage.setUnitQuantity("0");
        agregarMedicinaPage.setPrice("13");
        agregarMedicinaPage.setPrm("111");
        agregarMedicinaPage.setBatchNo("107164");
        agregarMedicinaPage.setExpdate((Objects.equals(navegador, "firefox"))?"2024-12-02":"02/12/2024"); // 02/12/2024
        agregarMedicinaPage.setBrandName("Cipla");
        agregarMedicinaPage.setCategoryName("Pastillas");
        agregarMedicinaPage.setProductStatus("No disponible");
        agregarMedicinaPage.submit();

        String textErrorActual = agregarMedicinaPage.getErrorMessage("missing-rate");
        String textErrorEsperado = "El valor debe ser superior o igual a 1";
        assertEquals(textErrorEsperado,textErrorActual,() -> "La cantidad es mayor a 1");
    }

    @Test
    void CP013_Test(){
        agregarMedicinaPage.setMedicineImage("image-4mb.jpg");
        agregarMedicinaPage.setMedicineName("Levotiroxina 100gr");
        agregarMedicinaPage.setQuantity("100");
        agregarMedicinaPage.setUnitQuantity("1000");
        agregarMedicinaPage.setPrice("65");
        agregarMedicinaPage.setPrm("242");
        agregarMedicinaPage.setBatchNo("828191");
        agregarMedicinaPage.setExpdate((Objects.equals(navegador, "firefox"))?"2024-12-31":"31/12/2024"); // 31/12/2024
        agregarMedicinaPage.setBrandName("Cipla");
        agregarMedicinaPage.setCategoryName("Vacunas");
        agregarMedicinaPage.setProductStatus("Disponible");
        agregarMedicinaPage.submit();

        String textErrorActual = agregarMedicinaPage.getErrorMessage("missing-rate");
        String textErrorEsperado = "El valor debe ser inferior o igual a 999";
        assertEquals(textErrorEsperado,textErrorActual,() -> "La cantidad es menor de 1000");
    }

    @Test
    void CP014_Test(){
        agregarMedicinaPage.setMedicineImage("image-3mb.jpg");
        agregarMedicinaPage.setMedicineName("Naproxeno 100gr");
        agregarMedicinaPage.setQuantity("150");
        //agregarMedicinaPage.setUnitQuantity("98");
        agregarMedicinaPage.setPrice("300");
        agregarMedicinaPage.setPrm("210");
        agregarMedicinaPage.setBatchNo("534571");
        agregarMedicinaPage.setExpdate((Objects.equals(navegador, "firefox"))?"2028-02-20":"20/02/2028"); // 20/02/2028
        agregarMedicinaPage.setBrandName("Cipla");
        agregarMedicinaPage.setCategoryName("Vacunas");
        agregarMedicinaPage.setProductStatus("No disponible");
        agregarMedicinaPage.submit();

        String textErrorActual = agregarMedicinaPage.getErrorMessage("missing-rate");
        String textErrorEsperado = "Completa este campo";
        assertEquals(textErrorEsperado,textErrorActual,() -> "La cantidad del medicamento no es nulo");
    }

    @Test
    void CP015_Test(){
        agregarMedicinaPage.setMedicineImage("image-2mb.png");
        agregarMedicinaPage.setMedicineName("Amoxicilina 100gr");
        agregarMedicinaPage.setQuantity("45");
        agregarMedicinaPage.setUnitQuantity("25");
        agregarMedicinaPage.setPrice("0");
        agregarMedicinaPage.setPrm("101");
        agregarMedicinaPage.setBatchNo("292803");
        agregarMedicinaPage.setExpdate((Objects.equals(navegador, "firefox"))?"2025-05-08":"08/05/2025"); // 08/05/2025
        agregarMedicinaPage.setBrandName("Inkafarma");
        agregarMedicinaPage.setCategoryName("Pastillas");
        agregarMedicinaPage.setProductStatus("Disponible");
        agregarMedicinaPage.submit();

        String textErrorActual = agregarMedicinaPage.getErrorMessage("missing-price");
        String textErrorEsperado = "El valor debe ser superior o igual a 1";
        assertEquals(textErrorEsperado,textErrorActual,() -> "La cantidad es mayor a 1");
    }

    @Test
    void CP016_Test(){
        agregarMedicinaPage.setMedicineImage("image-4mb.jpg");
        agregarMedicinaPage.setMedicineName("Levotiroxina 100gr");
        agregarMedicinaPage.setQuantity("50");
        agregarMedicinaPage.setUnitQuantity("10");
        agregarMedicinaPage.setPrice("10000");
        agregarMedicinaPage.setPrm("178");
        agregarMedicinaPage.setBatchNo("565809");
        agregarMedicinaPage.setExpdate((Objects.equals(navegador, "firefox"))?"2024-01-15":"15/01/2024"); // 15/01/2024
        agregarMedicinaPage.setBrandName("Inkafarma");
        agregarMedicinaPage.setCategoryName("Pastillas");
        agregarMedicinaPage.setProductStatus("No disponible");
        agregarMedicinaPage.submit();

        String textErrorActual = agregarMedicinaPage.getErrorMessage("missing-price");
        String textErrorEsperado = "El valor debe ser inferior o igual a 999";
        assertEquals(textErrorEsperado,textErrorActual,() -> "El precio es menor de 1000");
    }

    @Test
    void CP017_Test(){
        agregarMedicinaPage.setMedicineImage("image-3mb.jpg");
        agregarMedicinaPage.setMedicineName("Naproxeno 100gr");
        agregarMedicinaPage.setQuantity("30");
        agregarMedicinaPage.setUnitQuantity("600");
        //agregarMedicinaPage.setPrice("300");
        agregarMedicinaPage.setPrm("288");
        agregarMedicinaPage.setBatchNo("570788");
        agregarMedicinaPage.setExpdate((Objects.equals(navegador, "firefox"))?"2029-02-20":"20/02/2029"); // 20/02/2029
        agregarMedicinaPage.setBrandName("Inkafarma");
        agregarMedicinaPage.setCategoryName("Vacunas");
        agregarMedicinaPage.setProductStatus("Disponible");
        agregarMedicinaPage.submit();

        String textErrorActual = agregarMedicinaPage.getErrorMessage("missing-price");
        String textErrorEsperado = "Completa este campo";
        assertEquals(textErrorEsperado,textErrorActual,() -> "El precio del medicamento no es nulo");
    }

    @Test
    void CP018_Test(){
        agregarMedicinaPage.setMedicineImage("image-2mb.png");
        agregarMedicinaPage.setMedicineName("Amoxicilina 100gr");
        agregarMedicinaPage.setQuantity("100");
        agregarMedicinaPage.setUnitQuantity("30");
        agregarMedicinaPage.setPrice("50");
        agregarMedicinaPage.setPrm("-1");
        agregarMedicinaPage.setBatchNo("993095");
        agregarMedicinaPage.setExpdate((Objects.equals(navegador, "firefox"))?"2025-05-05":"05/05/2025"); // 05/05/2025
        agregarMedicinaPage.setBrandName("Inkafarma");
        agregarMedicinaPage.setCategoryName("Vacunas");
        agregarMedicinaPage.setProductStatus("No disponible");
        agregarMedicinaPage.submit();

        String textErrorActual = agregarMedicinaPage.getErrorMessage("missing-mrp");
        String textErrorEsperado = "El valor debe ser superior o igual a 1";
        assertEquals(textErrorEsperado,textErrorActual,() -> "El PRM es mayor a 1");
    }

    @Test
    void CP019_Test(){
        agregarMedicinaPage.setMedicineImage("image-4mb.jpg");
        agregarMedicinaPage.setMedicineName("Levotiroxina 100gr");
        agregarMedicinaPage.setQuantity("150");
        agregarMedicinaPage.setUnitQuantity("47");
        agregarMedicinaPage.setPrice("13");
        agregarMedicinaPage.setPrm("501");
        agregarMedicinaPage.setBatchNo("503540");
        agregarMedicinaPage.setExpdate((Objects.equals(navegador, "firefox"))?"2024-08-13":"13/08/2024"); // 13/08/2024
        agregarMedicinaPage.setBrandName("Cipla");
        agregarMedicinaPage.setCategoryName("Pastillas");
        agregarMedicinaPage.setProductStatus("Disponible");
        agregarMedicinaPage.submit();

        String textErrorActual = agregarMedicinaPage.getErrorMessage("missing-mrp");
        String textErrorEsperado = "El valor debe ser inferior o igual a 500";
        assertEquals(textErrorEsperado,textErrorActual,() -> "El PRM es menor de 500");
    }

    @Test
    void CP020_Test(){
        agregarMedicinaPage.setMedicineImage("image-3mb.jpg");
        agregarMedicinaPage.setMedicineName("Naproxeno 100gr");
        agregarMedicinaPage.setQuantity("45");
        agregarMedicinaPage.setUnitQuantity("25");
        agregarMedicinaPage.setPrice("65");
        //agregarMedicinaPage.setPrm("288");
        agregarMedicinaPage.setBatchNo("512276");
        agregarMedicinaPage.setExpdate((Objects.equals(navegador, "firefox"))?"2025-07-25":"25/07/2025"); // 25/07/2025
        agregarMedicinaPage.setBrandName("Cipla");
        agregarMedicinaPage.setCategoryName("Pastillas");
        agregarMedicinaPage.setProductStatus("No disponible");
        agregarMedicinaPage.submit();

        String textErrorActual = agregarMedicinaPage.getErrorMessage("missing-mrp");
        String textErrorEsperado = "Completa este campo";
        assertEquals(textErrorEsperado,textErrorActual,() -> "El PRM del medicamento no es nulo");
    }

    @Test
    void CP21_Test(){
        agregarMedicinaPage.setMedicineImage("image-2mb.png");
        agregarMedicinaPage.setMedicineName("Amoxicilina 100gr");
        agregarMedicinaPage.setQuantity("50");
        agregarMedicinaPage.setUnitQuantity("10");
        agregarMedicinaPage.setPrice("300");
        agregarMedicinaPage.setPrm("487");
        agregarMedicinaPage.setBatchNo("99999");
        agregarMedicinaPage.setExpdate((Objects.equals(navegador, "firefox"))?"2024-12-31":"31/12/2024"); // 31/12/2024
        agregarMedicinaPage.setBrandName("Cipla");
        agregarMedicinaPage.setCategoryName("Vacunas");
        agregarMedicinaPage.setProductStatus("Disponible");
        agregarMedicinaPage.submit();

        String textErrorActual = agregarMedicinaPage.getErrorMessage("missing-bno");
        String textErrorEsperado = "El valor debe ser superior o igual a 100000";
        assertEquals(textErrorEsperado,textErrorActual,() -> "El No de Lote es mayor a 100000");
    }

    @Test
    void CP22_Test(){
        agregarMedicinaPage.setMedicineImage("image-4mb.jpg");
        agregarMedicinaPage.setMedicineName("Levotiroxina 100gr");
        agregarMedicinaPage.setQuantity("30");
        agregarMedicinaPage.setUnitQuantity("600");
        agregarMedicinaPage.setPrice("650");
        agregarMedicinaPage.setPrm("398");
        agregarMedicinaPage.setBatchNo("1000000");
        agregarMedicinaPage.setExpdate((Objects.equals(navegador, "firefox"))?"2024-10-15":"15/10/2024"); // 15/10/2024
        agregarMedicinaPage.setBrandName("Cipla");
        agregarMedicinaPage.setCategoryName("Vacunas");
        agregarMedicinaPage.setProductStatus("No disponible");
        agregarMedicinaPage.submit();

        String textErrorActual = agregarMedicinaPage.getErrorMessage("missing-bno");
        String textErrorEsperado = "El valor debe ser inferior o igual a 999999";
        assertEquals(textErrorEsperado,textErrorActual,() -> "El No de Lote es menor de 999999");
    }

    @Test
    void CP23_Test(){
        agregarMedicinaPage.setMedicineImage("image-3mb.jpg");
        agregarMedicinaPage.setMedicineName("Naproxeno 100gr");
        agregarMedicinaPage.setQuantity("100");
        agregarMedicinaPage.setUnitQuantity("30");
        agregarMedicinaPage.setPrice("50");
        agregarMedicinaPage.setPrm("499");
        //agregarMedicinaPage.setBatchNo("512276");
        agregarMedicinaPage.setExpdate((Objects.equals(navegador, "firefox"))?"2025-05-08":"08/05/2025"); // 08/05/2025
        agregarMedicinaPage.setBrandName("Inkafarma");
        agregarMedicinaPage.setCategoryName("Pastillas");
        agregarMedicinaPage.setProductStatus("Disponible");
        agregarMedicinaPage.submit();

        String textErrorActual = agregarMedicinaPage.getErrorMessage("missing-bno");
        String textErrorEsperado = "Completa este campo";
        assertEquals(textErrorEsperado,textErrorActual,() -> "El No de Lote del medicamento no es nulo");
    }

    @Test
    void CP24_Test(){
        agregarMedicinaPage.setMedicineImage("image-2mb.png");
        agregarMedicinaPage.setMedicineName("Amoxicilina 100gr");
        agregarMedicinaPage.setQuantity("150");
        agregarMedicinaPage.setUnitQuantity("47");
        agregarMedicinaPage.setPrice("13");
        agregarMedicinaPage.setPrm("411");
        agregarMedicinaPage.setBatchNo("709291");
        agregarMedicinaPage.setExpdate((Objects.equals(navegador, "firefox"))?"2023-10-02":"02/10/2023"); // 02/10/2023
        agregarMedicinaPage.setBrandName("Inkafarma");
        agregarMedicinaPage.setCategoryName("Pastillas");
        agregarMedicinaPage.setProductStatus("No disponible");
        agregarMedicinaPage.submit();

        Calendar c = Calendar.getInstance();
        String dia = Integer.toString(c.get(Calendar.DATE));
        String mes = Integer.toString(c.get(Calendar.MONTH)+1);
        String anio = Integer.toString(c.get(Calendar.YEAR));

        String textErrorActual = agregarMedicinaPage.getErrorMessage("missing-expdate");
        String textErrorEsperado = "El valor debe ser " + dia + "/" + mes + "/" + anio + " (Fecha Actual) o superior";
        assertEquals(textErrorEsperado,textErrorActual,() -> "La fecha de expiracion es mayor a la fecha actual");
    }

    @Test
    void CP25_Test(){
        agregarMedicinaPage.setMedicineImage("image-3mb.jpg");
        agregarMedicinaPage.setMedicineName("Levotiroxina 100gr");
        agregarMedicinaPage.setQuantity("45");
        agregarMedicinaPage.setUnitQuantity("25");
        agregarMedicinaPage.setPrice("65");
        agregarMedicinaPage.setPrm("275");
        agregarMedicinaPage.setBatchNo("949838");
        agregarMedicinaPage.setExpdate((Objects.equals(navegador, "firefox"))?"2100-10-10":"10/10/2100"); // 10/10/2100
        agregarMedicinaPage.setBrandName("Inkafarma");
        agregarMedicinaPage.setCategoryName("Vacunas");
        agregarMedicinaPage.setProductStatus("Disponible");
        agregarMedicinaPage.submit();

        String textErrorActual = agregarMedicinaPage.getErrorMessage("missing-expdate");
        String textErrorEsperado = "El valor debe ser 31/12/2030 o anterior";
        assertEquals(textErrorEsperado,textErrorActual,() -> "La fecha de expiracion es menor de 31/12/2030");
    }

    @Test
    void CP26_Test(){
        agregarMedicinaPage.setMedicineImage("image-4mb.jpg");
        agregarMedicinaPage.setMedicineName("Naproxeno 100gr");
        agregarMedicinaPage.setQuantity("50");
        agregarMedicinaPage.setUnitQuantity("10");
        agregarMedicinaPage.setPrice("300");
        agregarMedicinaPage.setPrm("189");
        agregarMedicinaPage.setBatchNo("404920");
        //agregarMedicinaPage.setExpdate("08052025"); // 08/05/2025
        agregarMedicinaPage.setBrandName("Inkafarma");
        agregarMedicinaPage.setCategoryName("Vacunas");
        agregarMedicinaPage.setProductStatus("No disponible");
        agregarMedicinaPage.submit();

        String textErrorActual = agregarMedicinaPage.getErrorMessage("missing-expdate");
        String textErrorEsperado = "Completa este campo";
        assertEquals(textErrorEsperado,textErrorActual,() -> "La fecha de expiracion no es nula");
    }

    @Test
    void CP27_Test(){
        agregarMedicinaPage.setMedicineImage("image-2mb.png");
        agregarMedicinaPage.setMedicineName("Amoxicilina 100gr");
        agregarMedicinaPage.setQuantity("30");
        agregarMedicinaPage.setUnitQuantity("600");
        agregarMedicinaPage.setPrice("650");
        agregarMedicinaPage.setPrm("345");
        agregarMedicinaPage.setBatchNo("805346");
        agregarMedicinaPage.setExpdate((Objects.equals(navegador, "firefox"))?"2025-08-22":"22/08/2025"); // 22/08/2025
        //agregarMedicinaPage.setBrandName("Inkafarma");
        agregarMedicinaPage.setCategoryName("Pastillas");
        agregarMedicinaPage.setProductStatus("Disponible");
        agregarMedicinaPage.submit();

        String textErrorActual = agregarMedicinaPage.getErrorMessage("missing-brandName");
        String textErrorEsperado = "Selecciona un elemento de la lista";
        assertEquals(textErrorEsperado,textErrorActual,() -> "Se selecciono un elemento de la lista");
    }

    @Test
    void CP28_Test(){
        agregarMedicinaPage.setMedicineImage("image-4mb.jpg");
        agregarMedicinaPage.setMedicineName("Levotiroxina 100gr");
        agregarMedicinaPage.setQuantity("100");
        agregarMedicinaPage.setUnitQuantity("30");
        agregarMedicinaPage.setPrice("70");
        agregarMedicinaPage.setPrm("321");
        agregarMedicinaPage.setBatchNo("416267");
        agregarMedicinaPage.setExpdate((Objects.equals(navegador, "firefox"))?"2024-12-31":"31/12/2024"); // 31/12/2024
        agregarMedicinaPage.setBrandName("Cipla");
        //agregarMedicinaPage.setCategoryName("Vacunas");
        agregarMedicinaPage.setProductStatus("No disponible");
        agregarMedicinaPage.submit();

        String textErrorActual = agregarMedicinaPage.getErrorMessage("missing-categoryName");
        String textErrorEsperado = "Selecciona un elemento de la lista";
        assertEquals(textErrorEsperado,textErrorActual,() -> "Se selecciono un elemento de la lista");
    }

    @Test
    void CP29_Test(){
        agregarMedicinaPage.setMedicineImage("image-3mb.jpg");
        agregarMedicinaPage.setMedicineName("Naproxeno 100gr");
        agregarMedicinaPage.setQuantity("150");
        agregarMedicinaPage.setUnitQuantity("47");
        agregarMedicinaPage.setPrice("13");
        agregarMedicinaPage.setPrm("488");
        agregarMedicinaPage.setBatchNo("881890");
        agregarMedicinaPage.setExpdate((Objects.equals(navegador, "firefox"))?"2025-10-18":"18/10/2025"); // 18/10/2025
        agregarMedicinaPage.setBrandName("Inkafarma");
        agregarMedicinaPage.setCategoryName("Vacunas");
        //agregarMedicinaPage.setProductStatus("No disponible");
        agregarMedicinaPage.submit();

        String textErrorActual = agregarMedicinaPage.getErrorMessage("missing-productStatus");
        String textErrorEsperado = "Selecciona un elemento de la lista";
        assertEquals(textErrorEsperado,textErrorActual,() -> "Se selecciono un elemento de la lista");
    }
}
