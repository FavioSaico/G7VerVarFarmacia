import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.LoginPage;
import pages.ReporteVentasPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReporteVentasTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private ReporteVentasPage reporteVentasPage;

    public void esperar(int tiempo){
        try {
            Thread.sleep(tiempo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @BeforeEach // antes de cada prueba
    void setup() {
        driver = WebDriverManager.getInstance("chrome").create();
        //driver = WebDriverManager.getInstance("edge").create();
        //driver = WebDriverManager.getInstance("firefox").create();

        driver.get("http://localhost/G7VerVarFarmacia/farmacia/login.php");
        driver.manage().window().maximize();

        // LOGIN
        loginPage = new LoginPage(driver);

        loginPage.setEmail("hola@configuroweb.com");
        loginPage.setPassword("1234abcd..");
        loginPage.submit();

        driver.get("http://localhost/G7VerVarFarmacia/farmacia/sales_report.php");

        esperar(1000);

        // REPORTE DE VENTAS
        reporteVentasPage = new ReporteVentasPage(driver);
    }

    @AfterEach
    void tearDown() { // cerramos el navegador
        driver.quit();
    }

    @Test
    void CP01_Test(){
    	reporteVentasPage.setStartdate("11122021"); // 11/12/2021
    	reporteVentasPage.setEnddate("20012023"); // 12/01/2023
    	reporteVentasPage.submit();

        esperar(1000);

        String urlEsperado = "http://localhost/G7VerVarFarmacia/farmacia/php_action/getsalereport.php";
        String urlActual = driver.getCurrentUrl();
        assertEquals(urlEsperado,urlActual,() -> "Reporte de ventas no generado");
    }

    @Test
    void CP02_Test(){
    	reporteVentasPage.setStartdate("14102022"); // 14/10/2022
    	reporteVentasPage.setEnddate("17032024"); // 17/03/2024
    	reporteVentasPage.submit();

        String mensajeEsperado = "La fecha final debe ser menor o igual a la fecha actual";
        String mensajeActual = reporteVentasPage.errorEndDateMessage();
        assertEquals(mensajeEsperado,mensajeActual,() -> "Fecha actual mayor o igual a la fecha final");
    }
    
    @Test
    void CP03_Test(){
    	reporteVentasPage.setStartdate("14102022"); // 14/10/2022
    	reporteVentasPage.submit();

        String mensajeEsperado = "La fecha final no puede estar vacía";
        String mensajeActual = reporteVentasPage.errorEndDateMessage();
        assertEquals(mensajeEsperado,mensajeActual,() -> "Fecha final no es nula");
    }
    
    @Test
    void CP04_Test(){
    	reporteVentasPage.setStartdate("15062023"); // 15/06/2023
    	reporteVentasPage.setEnddate("19042023"); // 19/04/2023
    	reporteVentasPage.submit();

        String mensajeEsperado = "La fecha inicial debe ser menor o igual a la fecha final";
        String mensajeActual = reporteVentasPage.errorStartDateMessage();
        assertEquals(mensajeEsperado,mensajeActual,() -> "Fecha final mayor o igual a la fecha inicial");
    }
    
    @Test
    void CP05_Test(){
    	reporteVentasPage.setEnddate("16062023"); // 16/06/2023
    	reporteVentasPage.submit();

        String mensajeEsperado = "La fecha inicial no puede estar vacía";
        String mensajeActual = reporteVentasPage.errorStartDateMessage();
        assertEquals(mensajeEsperado,mensajeActual,() -> "Fecha inicial no es nula");
    }
    
}
