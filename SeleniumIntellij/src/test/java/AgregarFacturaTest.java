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
import pages.AgregarFacturaPage;

import java.time.Duration;
import java.util.Objects;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class AgregarFacturaTest {

    private WebDriver driver;
    private AgregarFacturaPage agregarFacturaPage;
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

        //assertTrue(loginPage.isSuccessMessageDisplayed(), "No se logro el Logeado");

		waiter = new WebDriverWait(driver, Duration.ofSeconds(3));
		waiter.until(ExpectedConditions.urlToBe("http://localhost/G7VerVarFarmacia/farmacia/dashboard.php"));

        // AGREGAR FACTURA
		driver.get("http://localhost/G7VerVarFarmacia/farmacia/add-order.php");
        agregarFacturaPage = new AgregarFacturaPage(driver);

		waiter.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

		WebElement elementoParaEliminar = driver.findElement(By.className("text-center"));
		((JavascriptExecutor) driver).executeScript("arguments[0].remove();", elementoParaEliminar);
    }

    @AfterEach
    void tearDown() { // cerramos el navegador
        driver.quit();
    }

    @Test
    void CP01_Test(){
    	agregarFacturaPage.setOrderDate((Objects.equals(navegador, "firefox"))?"2023-11-05":"05/11/2023");
    	agregarFacturaPage.setClientName("Alex Kim");
    	agregarFacturaPage.setClientContact("555123456");
    	agregarFacturaPage.setProductName("Acetaminofen 500");
    	agregarFacturaPage.setQuantity("10");
    	agregarFacturaPage.setDiscount("10");
    	agregarFacturaPage.setPaid("300");
    	agregarFacturaPage.setPaymentType("Efectivo");
    	agregarFacturaPage.setPaymentStatus("Pago Completo");
    	agregarFacturaPage.setPaymentPlace("Colombia");


    	agregarFacturaPage.submit();

		waiter = new WebDriverWait(driver, Duration.ofSeconds(3));
		boolean isRegistered = waiter.until(ExpectedConditions.urlToBe("http://localhost/G7VerVarFarmacia/farmacia/Order.php"));

		assertTrue(isRegistered,() -> "Factura no se registro correctamente");
    }
    
    @Test
    void CP02_Test(){
    	agregarFacturaPage.setOrderDate((Objects.equals(navegador, "firefox"))?"2023-11-04":"04/11/2023");
    	agregarFacturaPage.setClientName("Mia Li");
    	agregarFacturaPage.setClientContact("555123456");
    	agregarFacturaPage.setProductName("Levotiroxina 100gr");
    	agregarFacturaPage.setQuantity("5");
    	agregarFacturaPage.setDiscount("9");
    	agregarFacturaPage.setPaid("200");
    	agregarFacturaPage.setPaymentType("Tarjeta de Crédito");
    	agregarFacturaPage.setPaymentStatus("Pago Pendiente");
    	agregarFacturaPage.setPaymentPlace("Internet");

		agregarFacturaPage.submit();

		waiter = new WebDriverWait(driver, Duration.ofSeconds(3));
		boolean isRegistered = waiter.until(ExpectedConditions.urlToBe("http://localhost/G7VerVarFarmacia/farmacia/Order.php"));

		assertTrue(isRegistered,() -> "Factura no se registro correctamente");
    }
    
    @Test
    void CP03_Test(){
    	agregarFacturaPage.setOrderDate((Objects.equals(navegador, "firefox"))?"2024-11-05":"05/11/2024");
    	agregarFacturaPage.setClientName("Leo Wu");
    	agregarFacturaPage.setClientContact("555123456");
    	agregarFacturaPage.setProductName("Acetaminofen 500");
    	agregarFacturaPage.setQuantity("3");
    	agregarFacturaPage.setDiscount("8");
    	agregarFacturaPage.setPaid("100");
    	agregarFacturaPage.setPaymentType("Efectivo");
    	agregarFacturaPage.setPaymentStatus("Pago Completo");
    	agregarFacturaPage.setPaymentPlace("Colombia");
    	agregarFacturaPage.submit();

        String textErrorActual = agregarFacturaPage.getErrorMessage("error-orderDate");
        String textErrorEsperado = "La fecha de factura debe ser menor o igual a la fecha actual";
        assertEquals(textErrorEsperado,textErrorActual,() -> "ERROR");
    }
    
    @Test
    void CP04_Test(){
    	agregarFacturaPage.setOrderDate("");
    	agregarFacturaPage.setClientName("Ava Chan");
    	agregarFacturaPage.setClientContact("555123456");
    	agregarFacturaPage.setProductName("Acetaminofen 500");
    	agregarFacturaPage.setQuantity("1");
    	agregarFacturaPage.setDiscount("7");
    	agregarFacturaPage.setPaid("50");
    	agregarFacturaPage.setPaymentType("Efectivo");
    	agregarFacturaPage.setPaymentStatus("Pago Completo");
    	agregarFacturaPage.setPaymentPlace("Internet");
    	agregarFacturaPage.submit();

        String textErrorActual = agregarFacturaPage.getErrorMessage("error-orderDate");
        String textErrorEsperado = "Seleccionar la fecha de factura";
        assertEquals(textErrorEsperado,textErrorActual,() -> "ERROR");
    }
    
    @Test
    void CP05_Test(){
    	agregarFacturaPage.setOrderDate((Objects.equals(navegador, "firefox"))?"2023-11-03":"03/11/2023");
    	agregarFacturaPage.setClientName("Jose Luis Cornejo De La Paz");
    	agregarFacturaPage.setClientContact("555123456");
    	agregarFacturaPage.setProductName("Acetaminofen 500");
    	agregarFacturaPage.setQuantity("6");
    	agregarFacturaPage.setDiscount("20");
    	agregarFacturaPage.setPaid("334");
    	agregarFacturaPage.setPaymentType("Efectivo");
    	agregarFacturaPage.setPaymentStatus("Pago Pendiente");
    	agregarFacturaPage.setPaymentPlace("Colombia");
    	agregarFacturaPage.submit();

        String textErrorActual = agregarFacturaPage.getErrorMessage("error-clientName");
        String textErrorEsperado = "El nombre del cliente debe tener menos de 21 caracteres";
        assertEquals(textErrorEsperado,textErrorActual,() -> "ERROR");
    }
    
    @Test
    void CP06_Test(){
    	agregarFacturaPage.setOrderDate((Objects.equals(navegador, "firefox"))?"2023-11-02":"02/11/2023");
    	//agregarFacturaPage.setClientName("Jose Luis Cornejo De La Paz");
    	agregarFacturaPage.setClientContact("555123456");
    	agregarFacturaPage.setProductName("Acetaminofen 500");
    	agregarFacturaPage.setQuantity("5");
    	agregarFacturaPage.setDiscount("20");
    	agregarFacturaPage.setPaid("275");
    	agregarFacturaPage.setPaymentType("Efectivo");
    	agregarFacturaPage.setPaymentStatus("Pago Pendiente");
    	agregarFacturaPage.setPaymentPlace("Internet");
    	agregarFacturaPage.submit();

        String textErrorActual = agregarFacturaPage.getErrorMessage("error-clientName");
        String textErrorEsperado = "Escribir nombre de cliente";
        assertEquals(textErrorEsperado,textErrorActual,() -> "ERROR");
    }
    
    @Test
    void CP07_Test(){
    	agregarFacturaPage.setOrderDate((Objects.equals(navegador, "firefox"))?"2023-11-01":"01/11/2023");
    	agregarFacturaPage.setClientName("Max Wong");
    	agregarFacturaPage.setClientContact("12345678");
    	agregarFacturaPage.setProductName("Acetaminofen 500");
    	agregarFacturaPage.setQuantity("4");
    	agregarFacturaPage.setDiscount("20");
    	agregarFacturaPage.setPaid("216");
    	agregarFacturaPage.setPaymentType("Tarjeta de Crédito");
    	agregarFacturaPage.setPaymentStatus("Pago Completo");
    	agregarFacturaPage.setPaymentPlace("Colombia");
    	agregarFacturaPage.submit();

        String textErrorActual = agregarFacturaPage.getErrorMessage("error-clientContact");
        String textErrorEsperado = "El móvil debe tener exactamente 9 caracteres numéricos";
        assertEquals(textErrorEsperado,textErrorActual,() -> "ERROR");
    }
    
    @Test
    void CP08_Test(){
    	agregarFacturaPage.setOrderDate((Objects.equals(navegador, "firefox"))?"2023-10-31":"31/10/2023");
    	agregarFacturaPage.setClientName("Luke Yip");
    	agregarFacturaPage.setClientContact("1234567898");
    	agregarFacturaPage.setProductName("Acetaminofen 500");
    	agregarFacturaPage.setQuantity("3");
    	agregarFacturaPage.setDiscount("20");
    	agregarFacturaPage.setPaid("157");
    	agregarFacturaPage.setPaymentType("Tarjeta de Crédito");
    	agregarFacturaPage.setPaymentStatus("Pago Completo");
    	agregarFacturaPage.setPaymentPlace("Internet");
    	agregarFacturaPage.submit();

        String textErrorActual = agregarFacturaPage.getErrorMessage("error-clientContact");
        String textErrorEsperado = "El móvil debe tener exactamente 9 caracteres numéricos";
        assertEquals(textErrorEsperado,textErrorActual,() -> "ERROR");
    }
    
    @Test
    void CP09_Test(){
    	agregarFacturaPage.setOrderDate((Objects.equals(navegador, "firefox"))?"2023-10-31":"30/10/2023");
    	agregarFacturaPage.setClientName("Lily Ho");
    	//agregarFacturaPage.setClientContact("12345678");
    	agregarFacturaPage.setProductName("Acetaminofen 500");
    	agregarFacturaPage.setQuantity("2");
    	agregarFacturaPage.setDiscount("20");
    	agregarFacturaPage.setPaid("98");
    	agregarFacturaPage.setPaymentType("Tarjeta de Crédito");
    	agregarFacturaPage.setPaymentStatus("Pago Pendiente");
    	agregarFacturaPage.setPaymentPlace("Colombia");
    	agregarFacturaPage.submit();

        String textErrorActual = agregarFacturaPage.getErrorMessage("error-clientContact");
        String textErrorEsperado = "Escribir numero de contacto del cliente";
        assertEquals(textErrorEsperado,textErrorActual,() -> "ERROR");
    }
    
    @Test
    void CP10_Test(){
    	agregarFacturaPage.setOrderDate((Objects.equals(navegador, "firefox"))?"2023-10-29":"29/10/2023");
    	agregarFacturaPage.setClientName("Ben Cheung");
    	agregarFacturaPage.setClientContact("555123456");
    	//agregarFacturaPage.setProductName("Acetaminofen 500");
    	agregarFacturaPage.setQuantity("10");
    	agregarFacturaPage.setDiscount("21");
    	agregarFacturaPage.setPaid("714.44");
    	agregarFacturaPage.setPaymentType("Tarjeta de Crédito");
    	agregarFacturaPage.setPaymentStatus("Pago Pendiente");
    	agregarFacturaPage.setPaymentPlace("Internet");
    	agregarFacturaPage.submit();

        String textErrorActual = agregarFacturaPage.getErrorMessage("error-productName");
        String textErrorEsperado = "Seleccionar al menos una medicina";
        assertEquals(textErrorEsperado,textErrorActual,() -> "ERROR");
    }
    
    @Test
    void CP11_Test(){
    	agregarFacturaPage.setOrderDate((Objects.equals(navegador, "firefox"))?"2023-10-28":"28/10/2023");
    	agregarFacturaPage.setClientName("Zoe Fung");
    	agregarFacturaPage.setClientContact("555123456");
    	agregarFacturaPage.setProductName("Acetaminofen 500");
    	agregarFacturaPage.setQuantity("60");
    	agregarFacturaPage.setDiscount("0");
    	agregarFacturaPage.setPaid("0");
    	agregarFacturaPage.setPaymentType("Efectivo");
    	agregarFacturaPage.setPaymentStatus("Pago Completo");
    	agregarFacturaPage.setPaymentPlace("Colombia");
    	agregarFacturaPage.submit();

        String textErrorActual = agregarFacturaPage.getErrorMessage("error-quantity");
        String textErrorEsperado = "La cantidad seleccionada no puede ser mayor a la disponible o menor a 1";
        assertEquals(textErrorEsperado,textErrorActual,() -> "ERROR");
    }
    
    @Test
    void CP12_Test(){
    	agregarFacturaPage.setOrderDate((Objects.equals(navegador, "firefox"))?"2023-10-27":"27/10/2023");
    	agregarFacturaPage.setClientName("Sam Lo");
    	agregarFacturaPage.setClientContact("555123456");
    	agregarFacturaPage.setProductName("Levotiroxina 100gr");
    	agregarFacturaPage.setQuantity("");
    	agregarFacturaPage.setDiscount("24");
    	agregarFacturaPage.setPaid("0");
    	agregarFacturaPage.setPaymentType("Efectivo");
    	agregarFacturaPage.setPaymentStatus("Pago Completo");
    	agregarFacturaPage.setPaymentPlace("Internet");
    	agregarFacturaPage.submit();

        String textErrorActual = agregarFacturaPage.getErrorMessage("error-quantity");
        String textErrorEsperado = "Seleccionar cantidad de medicina";
        assertEquals(textErrorEsperado,textErrorActual,() -> "ERROR");
    }
    
    @Test
    void CP13_Test(){
    	agregarFacturaPage.setOrderDate((Objects.equals(navegador, "firefox"))?"2023-10-26":"26/10/2023");
    	agregarFacturaPage.setClientName("Grace Lam");
    	agregarFacturaPage.setClientContact("555123456");
    	agregarFacturaPage.setProductName("Levotiroxina 100gr");
    	agregarFacturaPage.setQuantity("5");
    	agregarFacturaPage.setDiscount("-0.1");
    	agregarFacturaPage.setPaid("123.028");
    	agregarFacturaPage.setPaymentType("Efectivo");
    	agregarFacturaPage.setPaymentStatus("Pago Pendiente");
    	agregarFacturaPage.setPaymentPlace("Colombia");
    	agregarFacturaPage.submit();

        String textErrorActual = agregarFacturaPage.getErrorMessage("error-discount");
        String textErrorEsperado = "El descuento no puede ser mayor al monto total o menor a 0";
        assertEquals(textErrorEsperado,textErrorActual,() -> "ERROR");
    }
    
    @Test
    void CP14_Test(){
    	agregarFacturaPage.setOrderDate((Objects.equals(navegador, "firefox"))?"2023-10-25":"25/10/2023");
    	agregarFacturaPage.setClientName("Eli Ma");
    	agregarFacturaPage.setClientContact("555123456");
    	agregarFacturaPage.setProductName("Levotiroxina 100gr");
    	agregarFacturaPage.setQuantity("2");
    	agregarFacturaPage.setDiscount("160");
    	agregarFacturaPage.setPaid("962.88");
    	agregarFacturaPage.setPaymentType("Efectivo");
    	agregarFacturaPage.setPaymentStatus("Pago Pendiente");
    	agregarFacturaPage.setPaymentPlace("Internet");
    	agregarFacturaPage.submit();

        String textErrorActual = agregarFacturaPage.getErrorMessage("error-discount");
        String textErrorEsperado = "El descuento no puede ser mayor al monto total o menor a 0";
        assertEquals(textErrorEsperado,textErrorActual,() -> "ERROR");
    }
    
    @Test
    void CP15_Test(){
    	agregarFacturaPage.setOrderDate((Objects.equals(navegador, "firefox"))?"2023-10-25":"25/10/2023");
    	agregarFacturaPage.setClientName("Sofia Leung");
    	agregarFacturaPage.setClientContact("555123456");
    	agregarFacturaPage.setProductName("Levotiroxina 100gr");
    	agregarFacturaPage.setQuantity("5");
    	//agregarFacturaPage.setDiscount("-0.1");
    	agregarFacturaPage.setPaid("100");
    	agregarFacturaPage.setPaymentType("Tarjeta de Crédito");
    	agregarFacturaPage.setPaymentStatus("Pago Completo");
    	agregarFacturaPage.setPaymentPlace("Colombia");
    	agregarFacturaPage.submit();

        String textErrorActual = agregarFacturaPage.getErrorMessage("error-discount");
        String textErrorEsperado = "Escribir el descuento (0)";
        assertEquals(textErrorEsperado,textErrorActual,() -> "ERROR");
    }
    
    @Test
    void CP16_Test(){
    	agregarFacturaPage.setOrderDate((Objects.equals(navegador, "firefox"))?"2023-10-23":"23/10/2023");
    	agregarFacturaPage.setClientName("Jack Au");
    	agregarFacturaPage.setClientContact("555123456");
    	agregarFacturaPage.setProductName("Levotiroxina 100gr");
    	agregarFacturaPage.setQuantity("5");
    	agregarFacturaPage.setDiscount("7");
    	agregarFacturaPage.setPaid("-0.5");
    	agregarFacturaPage.setPaymentType("Tarjeta de Crédito");
    	agregarFacturaPage.setPaymentStatus("Pago Completo");
    	agregarFacturaPage.setPaymentPlace("Internet");
    	agregarFacturaPage.submit();

        String textErrorActual = agregarFacturaPage.getErrorMessage("error-paid");
        String textErrorEsperado = "El monto pagado no puede ser menor a 0";
        assertEquals(textErrorEsperado,textErrorActual,() -> "ERROR");
    }
    
    @Test
    void CP17_Test(){
    	agregarFacturaPage.setOrderDate((Objects.equals(navegador, "firefox"))?"2023-10-22":"22/10/2023");
    	agregarFacturaPage.setClientName("Ella Ngai");
    	agregarFacturaPage.setClientContact("555123456");
    	agregarFacturaPage.setProductName("Levotiroxina 100gr");
    	agregarFacturaPage.setQuantity("2");
    	agregarFacturaPage.setDiscount("20");
    	//agregarFacturaPage.setPaid("-0.5");
    	agregarFacturaPage.setPaymentType("Tarjeta de Crédito");
    	agregarFacturaPage.setPaymentStatus("Pago Pendiente");
    	agregarFacturaPage.setPaymentPlace("Colombia");
    	agregarFacturaPage.submit();

        String textErrorActual = agregarFacturaPage.getErrorMessage("error-paid");
        String textErrorEsperado = "Escribir el monto pagado";
        assertEquals(textErrorEsperado,textErrorActual,() -> "ERROR");
    }
    
    @Test
    void CP18_Test(){
    	agregarFacturaPage.setOrderDate((Objects.equals(navegador, "firefox"))?"2023-10-21":"21/10/2023");
    	agregarFacturaPage.setClientName("Noah Yeung");
    	agregarFacturaPage.setClientContact("555123456");
    	agregarFacturaPage.setProductName("Levotiroxina 100gr");
    	agregarFacturaPage.setQuantity("10");
    	agregarFacturaPage.setDiscount("20");
    	agregarFacturaPage.setPaid("50");
    	//agregarFacturaPage.setPaymentType("Tarjeta de Crédito");
    	agregarFacturaPage.setPaymentStatus("Pago Pendiente");
    	agregarFacturaPage.setPaymentPlace("Internet");
    	agregarFacturaPage.submit();

        String textErrorActual = agregarFacturaPage.getErrorMessage("error-paymentType");
        String textErrorEsperado = "Seleccionar el tipo de pago";
        assertEquals(textErrorEsperado,textErrorActual,() -> "ERROR");
    }
    
    @Test
    void CP19_Test(){
    	agregarFacturaPage.setOrderDate((Objects.equals(navegador, "firefox"))?"2023-10-20":"20/10/2023");
    	agregarFacturaPage.setClientName("Maya Chiu");
    	agregarFacturaPage.setClientContact("555123456");
    	agregarFacturaPage.setProductName("Levotiroxina 100gr");
    	agregarFacturaPage.setQuantity("10");
    	agregarFacturaPage.setDiscount("20");
    	agregarFacturaPage.setPaid("334");
    	agregarFacturaPage.setPaymentType("Tarjeta de Crédito");
    	//agregarFacturaPage.setPaymentStatus("Pago Pendiente");
    	agregarFacturaPage.setPaymentPlace("Colombia");
    	agregarFacturaPage.submit();

        String textErrorActual = agregarFacturaPage.getErrorMessage("error-paymentStatus");
        String textErrorEsperado = "Seleccionar el estado de pago";
        assertEquals(textErrorEsperado,textErrorActual,() -> "ERROR");
    }
    
    @Test
    void CP20_Test(){
    	agregarFacturaPage.setOrderDate((Objects.equals(navegador, "firefox"))?"2023-10-19":"19/10/2023");
    	agregarFacturaPage.setClientName("Liam Lau");
    	agregarFacturaPage.setClientContact("555123456");
    	agregarFacturaPage.setProductName("Levotiroxina 100gr");
    	agregarFacturaPage.setQuantity("1");
    	agregarFacturaPage.setDiscount("21");
    	agregarFacturaPage.setPaid("275");
    	agregarFacturaPage.setPaymentType("Efectivo");
    	agregarFacturaPage.setPaymentStatus("Pago Completo");
    	//agregarFacturaPage.setPaymentPlace("Internet");
    	agregarFacturaPage.submit();

        String textErrorActual = agregarFacturaPage.getErrorMessage("error-paymentPlace");
        String textErrorEsperado = "Seleccionar el lugar de pago";
        assertEquals(textErrorEsperado,textErrorActual,() -> "ERROR");
    }
    
    
}
