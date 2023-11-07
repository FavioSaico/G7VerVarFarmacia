import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.LoginPage;
import pages.ReporteProductosPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReporteProductosTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private ReporteProductosPage reporteProductosPage;

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

        driver.get("http://localhost/G7VerVarFarmacia/farmacia/productreport.php");

        esperar(1000);

        // REPORTE DE VENTAS
        reporteProductosPage = new ReporteProductosPage(driver);
    }

    @AfterEach
    void tearDown() { // cerramos el navegador
        driver.quit();
    }

    @Test
    void CP01_Test(){
    	reporteProductosPage.setStartdate("11122021"); // 11/12/2021
    	reporteProductosPage.setEnddate("20012023"); // 12/01/2023
    	reporteProductosPage.submit();

        esperar(2000);

        String urlEsperado = "http://localhost/G7VerVarFarmacia/farmacia/getproductreport.php";
        String urlActual = driver.getCurrentUrl();
        assertEquals(urlEsperado,urlActual,() -> "Reporte de productos no generado");
    }

    @Test
    void CP02_Test(){
    	reporteProductosPage.setStartdate("14102022"); // 14/10/2022
    	reporteProductosPage.setEnddate("17032024"); // 17/03/2024
    	reporteProductosPage.submit();

        esperar(2000);

        String mensajeEsperado = "La fecha final debe ser menor o igual a la fecha actual";
        String mensajeActual = reporteProductosPage.errorEndDateMessage();
        assertEquals(mensajeEsperado,mensajeActual,() -> "Fecha actual mayor o igual a la fecha final");
    }
    
    @Test
    void CP03_Test(){
    	reporteProductosPage.setStartdate("14102022"); // 14/10/2022
    	reporteProductosPage.submit();

        esperar(2000);

        String mensajeEsperado = "La fecha final no puede estar vacía";
        String mensajeActual = reporteProductosPage.errorEndDateMessage();
        assertEquals(mensajeEsperado,mensajeActual,() -> "Fecha final no es nula");
    }
    
    @Test
    void CP04_Test(){
    	reporteProductosPage.setStartdate("15062023"); // 15/06/2023
    	reporteProductosPage.setEnddate("19042023"); // 19/04/2023
    	reporteProductosPage.submit();

        esperar(2000);

        String mensajeEsperado = "La fecha inicial debe ser menor o igual a la fecha final";
        String mensajeActual = reporteProductosPage.errorStartDateMessage();
        assertEquals(mensajeEsperado,mensajeActual,() -> "Fecha final mayor o igual a la fecha inicial");
    }
    
    @Test
    void CP05_Test(){
    	reporteProductosPage.setEnddate("16062023"); // 16/06/2023
    	reporteProductosPage.submit();

        esperar(2000);

        String mensajeEsperado = "La fecha inicial no puede estar vacía";
        String mensajeActual = reporteProductosPage.errorStartDateMessage();
        assertEquals(mensajeEsperado,mensajeActual,() -> "Fecha inicial no es nula");
    }
    
}
