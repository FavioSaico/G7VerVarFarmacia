import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import pages.AgregarProveedorPage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AgregarProveedorTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private AgregarProveedorPage agregarProveedorPage;
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

        // AGREGAR PROVEEDOR
        driver.get("http://localhost/G7VerVarFarmacia/farmacia/add-brand.php");
        agregarProveedorPage = new AgregarProveedorPage(driver);

        waiter.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
    }

    @AfterEach
    void tearDown() { // cerramos el navegador
        driver.quit();
    }

    @Test
    void CP01_Test(){
    	agregarProveedorPage.setBrandName("TeamWolf");
    	agregarProveedorPage.setBrandStatus("Disponible");
    	agregarProveedorPage.submit();

        waiter = new WebDriverWait(driver, Duration.ofSeconds(3));
        boolean isRegistered = waiter.until(ExpectedConditions.urlToBe("http://localhost/G7VerVarFarmacia/farmacia/brand.php"));

        assertTrue(isRegistered,() -> "Proveedor no se registro correctamente");
    }

    @Test
    void CP02_Test(){
    	agregarProveedorPage.setBrandName("FarmaCod");
    	agregarProveedorPage.setBrandStatus("No disponible");
    	agregarProveedorPage.submit();

        waiter = new WebDriverWait(driver, Duration.ofSeconds(3));
        boolean isRegistered = waiter.until(ExpectedConditions.urlToBe("http://localhost/G7VerVarFarmacia/farmacia/brand.php"));

        assertTrue(isRegistered,() -> "Proveedor no se registro correctamente");
    }

    @Test
    void CP03_Test(){
    	agregarProveedorPage.setBrandName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    	agregarProveedorPage.setBrandStatus("Disponible");
    	agregarProveedorPage.submit();

        String mensajeEsperado = "El nombre debe tener menos de 50 caracteres";
        String mensajeActual = agregarProveedorPage.errorBrandNameMessage();
        assertEquals(mensajeEsperado,mensajeActual,() -> "Nombre de proveedor menor o igual a 50 caracteres");
    }

    @Test
    void CP04_Test(){
    	agregarProveedorPage.setBrandStatus("No disponible");
    	agregarProveedorPage.submit();

        String mensajeEsperado = "Escriba el nombre del proveedor";
        String mensajeActual = agregarProveedorPage.errorBrandNameMessage();
        assertEquals(mensajeEsperado,mensajeActual,() -> "Nombre de proveedor no nulo");
    }
    
    @Test
    void CP05_Test(){
    	agregarProveedorPage.setBrandName("TeamWolf");
    	agregarProveedorPage.submit();

        String mensajeEsperado = "Debe seleccionar un estado";
        String mensajeActual = agregarProveedorPage.errorBrandStatusMessage();
        assertEquals(mensajeEsperado,mensajeActual,() -> "Estado de proveedor no nulo");
    }
    
}