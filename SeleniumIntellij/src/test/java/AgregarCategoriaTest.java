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
import pages.AgregarCategoriaPage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AgregarCategoriaTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private AgregarCategoriaPage agregarCategoriaPage;
    private WebDriverWait waiter;
    private Dotenv dotenv = Dotenv.configure().load();
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

        // AGREGAR CATEGORIA
        driver.get("http://localhost/G7VerVarFarmacia/farmacia/add-category.php");
        agregarCategoriaPage = new AgregarCategoriaPage(driver);

        waiter.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
    }

    @AfterEach
    void tearDown() { // cerramos el navegador
        driver.quit();
    }

    @Test
    void CP01_Test(){
    	agregarCategoriaPage.setCategoryName("TeamWolf");
    	agregarCategoriaPage.setCategoryStatus("Disponible");
    	agregarCategoriaPage.submit();

        waiter = new WebDriverWait(driver, Duration.ofSeconds(3));
        boolean isRegistered = waiter.until(ExpectedConditions.urlToBe("http://localhost/G7VerVarFarmacia/farmacia/categories.php"));

        assertTrue(isRegistered,() -> "Categoria no se registro correctamente");
    }

    @Test
    void CP02_Test(){
    	agregarCategoriaPage.setCategoryName("FarmaCod");
    	agregarCategoriaPage.setCategoryStatus("No disponible");
    	agregarCategoriaPage.submit();

        waiter = new WebDriverWait(driver, Duration.ofSeconds(3));
        boolean isRegistered = waiter.until(ExpectedConditions.urlToBe("http://localhost/G7VerVarFarmacia/farmacia/categories.php"));

        assertTrue(isRegistered,() -> "Categoria no se registro correctamente");
    }

    @Test
    void CP03_Test(){
    	agregarCategoriaPage.setCategoryName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    	agregarCategoriaPage.setCategoryStatus("Disponible");
    	agregarCategoriaPage.submit();

        String mensajeEsperado = "El nombre debe tener menos de 50 caracteres";
        String mensajeActual = agregarCategoriaPage.errorCategoryNameMessage();
        assertEquals(mensajeEsperado,mensajeActual,() -> "Nombre de categoria menor o igual a 50 caracteres");
    }

    @Test
    void CP04_Test(){
    	agregarCategoriaPage.setCategoryStatus("No disponible");
    	agregarCategoriaPage.submit();

        String mensajeEsperado = "Escriba el nombre de la categoría";
        String mensajeActual = agregarCategoriaPage.errorCategoryNameMessage();
        assertEquals(mensajeEsperado,mensajeActual,() -> "Nombre de categoria no nulo");
    }
    
    @Test
    void CP05_Test(){
    	agregarCategoriaPage.setCategoryName("TeamWolf");
    	agregarCategoriaPage.submit();

        String mensajeEsperado = "Debe seleccionar un estado";
        String mensajeActual = agregarCategoriaPage.errorCategoryStatusMessage();
        assertEquals(mensajeEsperado,mensajeActual,() -> "Estado de categoria no nulo");
    }
    
}