import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.LoginPage;
import pages.AgregarProveedorPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AgregarProveedorTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private AgregarProveedorPage agregarProveedorPage;


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
        driver.get("http://localhost/G7VerVarFarmacia/farmacia/login.php");
        driver.manage().window().maximize();

        // LOGIN
        loginPage = new LoginPage(driver);

        loginPage.setEmail("hola@configuroweb.com");
        loginPage.setPassword("1234abcd..");
        loginPage.submit();

        assertTrue(loginPage.isSuccessMessageDisplayed(), "No se logro el Logeado");

        driver.get("http://localhost/G7VerVarFarmacia/farmacia/add-brand.php");

        esperar(1000);

        // AGREGAR PROVEEDOR
        agregarProveedorPage = new AgregarProveedorPage(driver);
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

        esperar(2000);
        
        String urlEsperado = "http://localhost/G7VerVarFarmacia/farmacia/brand.php";
        String urlActual = driver.getCurrentUrl();
        assertEquals(urlEsperado,urlActual,() -> "Proveedor no se registro correctamente");
    }

    @Test
    void CP02_Test(){
    	agregarProveedorPage.setBrandName("FarmaCod");
    	agregarProveedorPage.setBrandStatus("No disponible");
    	agregarProveedorPage.submit();

        esperar(2000);

        String urlEsperado = "http://localhost:90/G7VerVarFarmacia/farmacia/brand.php";
        String urlActual = driver.getCurrentUrl();
        assertEquals(urlEsperado,urlActual,() -> "Proveedor no se registro correctamente");
    }

    @Test
    void CP03_Test(){
    	agregarProveedorPage.setBrandName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    	agregarProveedorPage.setBrandStatus("Disponible");
    	agregarProveedorPage.submit();

        esperar(2000);

        String mensajeEsperado = "El nombre debe tener menos de 50 caracteres";
        String mensajeActual = agregarProveedorPage.errorBrandNameMessage();
        assertEquals(mensajeEsperado,mensajeActual,() -> "Nombre de proveedor menor o igual a 50 caracteres");
    }

    @Test
    void CP04_Test(){
    	agregarProveedorPage.setBrandStatus("No disponible");
    	agregarProveedorPage.submit();

        esperar(2000);

        String mensajeEsperado = "Escriba el nombre del proveedor";
        String mensajeActual = agregarProveedorPage.errorBrandNameMessage();
        assertEquals(mensajeEsperado,mensajeActual,() -> "Nombre de proveedor no nulo");
    }
    
    @Test
    void CP05_Test(){
    	agregarProveedorPage.setBrandName("TeamWolf");
    	agregarProveedorPage.submit();

        esperar(2000);

        String mensajeEsperado = "Debe seleccionar un estado";
        String mensajeActual = agregarProveedorPage.errorBrandStatusMessage();
        assertEquals(mensajeEsperado,mensajeActual,() -> "Estado de proveedor no nulo");
    }
    
}