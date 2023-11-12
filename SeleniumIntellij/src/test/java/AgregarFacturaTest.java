import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.LoginPage;
import pages.AgregarFacturaPage;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class AgregarFacturaTest {

    private WebDriver driver;
    private AgregarFacturaPage agregarFacturaPage;
    private LoginPage loginPage;


    @BeforeAll // se ejecuta antes de todos pero solo una vez
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
        //System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromedriver.exe");
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

        //assertTrue(loginPage.isSuccessMessageDisplayed(), "No se logro el Logeado");

        driver.get("http://localhost/G7VerVarFarmacia/farmacia/add-order.php");

        esperar(1000);

        // AGREGAR FACTURA
        agregarFacturaPage = new AgregarFacturaPage(driver);
    }

    @AfterEach
    void tearDown() { // cerramos el navegador
        driver.quit();
    }

    @Test
    void CP01_Test(){
    	agregarFacturaPage.setOrderDate("05112023");
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

        esperar(2000);

        String urlEsperado = "http://localhost/G7VerVarFarmacia/farmacia/Order.php";
        String urlActual = driver.getCurrentUrl();
		assertEquals(urlEsperado,urlActual,() -> "Factura no se registro correctamente");
    }
    
    @Test
    void CP02_Test(){
    	agregarFacturaPage.setOrderDate("04112023");
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
        esperar(2000);
		
        String urlEsperado = "http://localhost/G7VerVarFarmacia/farmacia/Order.php";
        String urlActual = driver.getCurrentUrl();

		assertEquals(urlEsperado,urlActual,() -> "Factura no se registro correctamente");
    }
    
    @Test
    void CP03_Test(){
    	agregarFacturaPage.setOrderDate("05112024");
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
    	agregarFacturaPage.setOrderDate("03112023");
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
    	agregarFacturaPage.setOrderDate("02112023");
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
    	agregarFacturaPage.setOrderDate("01112023");
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
    	agregarFacturaPage.setOrderDate("31102023");
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
    	agregarFacturaPage.setOrderDate("30102023");
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
    	agregarFacturaPage.setOrderDate("29102023");
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
    	agregarFacturaPage.setOrderDate("28102023");
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
    	agregarFacturaPage.setOrderDate("27102023");
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
    	agregarFacturaPage.setOrderDate("26102023");
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
    	agregarFacturaPage.setOrderDate("25102023");
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
    	agregarFacturaPage.setOrderDate("24102023");
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
    	agregarFacturaPage.setOrderDate("23102023");
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
    	agregarFacturaPage.setOrderDate("22102023");
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
    	agregarFacturaPage.setOrderDate("21102023");
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
    	agregarFacturaPage.setOrderDate("20102023");
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
    	agregarFacturaPage.setOrderDate("19102023");
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
