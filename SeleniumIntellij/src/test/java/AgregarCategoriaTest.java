import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.LoginPage;
import pages.AgregarCategoriaPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AgregarCategoriaTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private AgregarCategoriaPage agregarCategoriaPage;


    @BeforeAll // se ejecuta antes de todos pero solo una vez
    static void setupClass() {
    	WebDriverManager.chromedriver().setup();
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
        driver.get("http://localhost:90/G7VerVarFarmacia/farmacia/login.php");
        driver.manage().window().maximize();

        // LOGIN
        loginPage = new LoginPage(driver);

        loginPage.setEmail("hola@configuroweb.com");
        loginPage.setPassword("1234abcd..");
        loginPage.submit();

        assertTrue(loginPage.isSuccessMessageDisplayed(), "No se logro el Logeado");

        driver.get("http://localhost:90/G7VerVarFarmacia/farmacia/add-category.php");

        esperar(1000);

        // AGREGAR CATEGORIA
        agregarCategoriaPage = new AgregarCategoriaPage(driver);
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

        esperar(2000);
        
        String urlEsperado = "http://localhost:90/G7VerVarFarmacia/farmacia/categories.php";
        String urlActual = driver.getCurrentUrl();
        assertEquals(urlEsperado,urlActual,() -> "Categoria no se registro correctamente");
    }

    @Test
    void CP02_Test(){
    	agregarCategoriaPage.setCategoryName("FarmaCod");
    	agregarCategoriaPage.setCategoryStatus("No disponible");
    	agregarCategoriaPage.submit();

        esperar(2000);

        String urlEsperado = "http://localhost:90/G7VerVarFarmacia/farmacia/categories.php";
        String urlActual = driver.getCurrentUrl();
        assertEquals(urlEsperado,urlActual,() -> "Categoria no se registro correctamente");
    }

    @Test
    void CP03_Test(){
    	agregarCategoriaPage.setCategoryName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    	agregarCategoriaPage.setCategoryStatus("Disponible");
    	agregarCategoriaPage.submit();

        esperar(2000);

        String mensajeEsperado = "El nombre debe tener menos de 50 caracteres";
        String mensajeActual = agregarCategoriaPage.errorCategoryNameMessage();
        assertEquals(mensajeEsperado,mensajeActual,() -> "Nombre de categoria menor o igual a 50 caracteres");
    }

    @Test
    void CP04_Test(){
    	agregarCategoriaPage.setCategoryStatus("No disponible");
    	agregarCategoriaPage.submit();

        esperar(2000);

        String mensajeEsperado = "Escriba el nombre de la categoría";
        String mensajeActual = agregarCategoriaPage.errorCategoryNameMessage();
        assertEquals(mensajeEsperado,mensajeActual,() -> "Nombre de categoria no nulo");
    }
    
    @Test
    void CP05_Test(){
    	agregarCategoriaPage.setCategoryName("TeamWolf");
    	agregarCategoriaPage.submit();

        esperar(2000);

        String mensajeEsperado = "Debe seleccionar un estado";
        String mensajeActual = agregarCategoriaPage.errorCategoryStatusMessage();
        assertEquals(mensajeEsperado,mensajeActual,() -> "Estado de categoria no nulo");
    }
    
}