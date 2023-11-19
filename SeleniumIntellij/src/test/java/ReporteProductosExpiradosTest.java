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
import pages.ReporteProductosExpiradosPage;

import java.time.Duration;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReporteProductosExpiradosTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private ReporteProductosExpiradosPage reporteProductosExpiradosPage;
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

        // REPORTE DE VENTAS
        driver.get("http://localhost/G7VerVarFarmacia/farmacia/expreport.php");
        reporteProductosExpiradosPage = new ReporteProductosExpiradosPage(driver);

        waiter.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
    }

    @AfterEach
    void tearDown() { // cerramos el navegador
        driver.quit();
    }

    @Test
    void CP01_Test(){
    	reporteProductosExpiradosPage.setStartdate((Objects.equals(navegador, "firefox"))?"2021-12-11":"11/12/2021"); // 11/12/2021
    	reporteProductosExpiradosPage.setEnddate((Objects.equals(navegador, "firefox"))?"2023-01-20":"20/01/2023"); // 20/01/2023
    	reporteProductosExpiradosPage.submit();

        waiter = new WebDriverWait(driver, Duration.ofSeconds(3));
        boolean isGenerated = waiter.until(ExpectedConditions.urlToBe("http://localhost/G7VerVarFarmacia/farmacia/getproductreport.php"));

        assertTrue(isGenerated,() -> "Reporte de productos expirados no generado");
    }

    @Test
    void CP02_Test(){
    	reporteProductosExpiradosPage.setStartdate((Objects.equals(navegador, "firefox"))?"2022-10-14":"14/10/2022"); // 14/10/2022
    	reporteProductosExpiradosPage.setEnddate((Objects.equals(navegador, "firefox"))?"2024-03-17":"17/03/2024"); // 17/03/2024
    	reporteProductosExpiradosPage.submit();

        String mensajeEsperado = "La fecha final debe ser menor o igual a la fecha actual";
        String mensajeActual = reporteProductosExpiradosPage.errorEndDateMessage();
        assertEquals(mensajeEsperado,mensajeActual,() -> "Fecha actual mayor o igual a la fecha final");
    }
    
    @Test
    void CP03_Test(){
    	reporteProductosExpiradosPage.setStartdate((Objects.equals(navegador, "firefox"))?"2022-10-14":"14/10/2022"); // 14/10/2022
    	reporteProductosExpiradosPage.submit();

        String mensajeEsperado = "La fecha final no puede estar vacía";
        String mensajeActual = reporteProductosExpiradosPage.errorEndDateMessage();
        assertEquals(mensajeEsperado,mensajeActual,() -> "Fecha final no es nula");
    }
    
    @Test
    void CP04_Test(){
    	reporteProductosExpiradosPage.setStartdate((Objects.equals(navegador, "firefox"))?"2023-06-15":"15/06/2023"); // 15/06/2023
    	reporteProductosExpiradosPage.setEnddate((Objects.equals(navegador, "firefox"))?"2019-04-19":"19/04/2023"); // 19/04/2023
    	reporteProductosExpiradosPage.submit();

        String mensajeEsperado = "La fecha inicial debe ser menor o igual a la fecha final";
        String mensajeActual = reporteProductosExpiradosPage.errorStartDateMessage();
        assertEquals(mensajeEsperado,mensajeActual,() -> "Fecha final mayor o igual a la fecha inicial");
    }
    
    @Test
    void CP05_Test(){
    	reporteProductosExpiradosPage.setEnddate((Objects.equals(navegador, "firefox"))?"2023-06-16":"16/06/2023"); // 16/06/2023
    	reporteProductosExpiradosPage.submit();

        String mensajeEsperado = "La fecha inicial no puede estar vacía";
        String mensajeActual = reporteProductosExpiradosPage.errorStartDateMessage();
        assertEquals(mensajeEsperado,mensajeActual,() -> "Fecha inicial no es nula");
    }
    
}
