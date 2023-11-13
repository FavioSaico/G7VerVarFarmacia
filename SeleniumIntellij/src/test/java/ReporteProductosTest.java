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
import pages.ReporteProductosPage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReporteProductosTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private ReporteProductosPage reporteProductosPage;
    private Dotenv dotenv = Dotenv.configure().load();
    private WebDriverWait waiter;
    
    @BeforeEach // antes de cada prueba
    void setup() {
        //driver = WebDriverManager.getInstance("chrome").create();
        driver = WebDriverManager.getInstance("edge").create();
        //driver = WebDriverManager.getInstance("firefox").create();

        driver.get("http://localhost/G7VerVarFarmacia/farmacia/login.php");
        driver.manage().window().maximize();

        // LOGIN
        loginPage = new LoginPage(driver);

        loginPage.setEmail(dotenv.get("EMAIL"));
        loginPage.setPassword(dotenv.get("PASSWORD"));
        loginPage.submit();

        waiter = new WebDriverWait(driver, Duration.ofSeconds(3));
        waiter.until(ExpectedConditions.urlToBe("http://localhost/G7VerVarFarmacia/farmacia/dashboard.php"));

        // REPORTE DE VENTAS
        driver.get("http://localhost/G7VerVarFarmacia/farmacia/productreport.php");
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

        waiter = new WebDriverWait(driver, Duration.ofSeconds(3));
        boolean isGenerated = waiter.until(ExpectedConditions.urlToBe("http://localhost/G7VerVarFarmacia/farmacia/getproductreport.php"));

        assertTrue(isGenerated,() -> "Reporte de productos no generado");
    }

    @Test
    void CP02_Test(){
    	reporteProductosPage.setStartdate("14102022"); // 14/10/2022
    	reporteProductosPage.setEnddate("17032024"); // 17/03/2024
    	reporteProductosPage.submit();

        String mensajeEsperado = "La fecha final debe ser menor o igual a la fecha actual";
        String mensajeActual = reporteProductosPage.errorEndDateMessage();
        assertEquals(mensajeEsperado,mensajeActual,() -> "Fecha actual mayor o igual a la fecha final");
    }
    
    @Test
    void CP03_Test(){
    	reporteProductosPage.setStartdate("14102022"); // 14/10/2022
    	reporteProductosPage.submit();

        String mensajeEsperado = "La fecha final no puede estar vacía";
        String mensajeActual = reporteProductosPage.errorEndDateMessage();
        assertEquals(mensajeEsperado,mensajeActual,() -> "Fecha final no es nula");
    }
    
    @Test
    void CP04_Test(){
    	reporteProductosPage.setStartdate("15062023"); // 15/06/2023
    	reporteProductosPage.setEnddate("19042023"); // 19/04/2023
    	reporteProductosPage.submit();

        String mensajeEsperado = "La fecha inicial debe ser menor o igual a la fecha final";
        String mensajeActual = reporteProductosPage.errorStartDateMessage();
        assertEquals(mensajeEsperado,mensajeActual,() -> "Fecha final mayor o igual a la fecha inicial");
    }
    
    @Test
    void CP05_Test(){
    	reporteProductosPage.setEnddate("16062023"); // 16/06/2023
    	reporteProductosPage.submit();

        String mensajeEsperado = "La fecha inicial no puede estar vacía";
        String mensajeActual = reporteProductosPage.errorStartDateMessage();
        assertEquals(mensajeEsperado,mensajeActual,() -> "Fecha inicial no es nula");
    }
    
}
