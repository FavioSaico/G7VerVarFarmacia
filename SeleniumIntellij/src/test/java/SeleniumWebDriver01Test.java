
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SeleniumWebDriver01Test {
    private WebDriver driver;

    @BeforeAll // se ejecuta antes de todos pero solo una vez
    static void setupClass() { // static para que podamos llamarlo durante toda la ejecución del progrma
        WebDriverManager.chromedriver().setup(); // cargamos el webdriver, se crea una vez y se mantiene durante todas las ejecuciones
    }


    @BeforeEach // antes de cada prueba
    void setup() { // instanciamos una instancia del navegador, abrimos el navegador
        driver = new ChromeDriver();
    }

    @AfterEach // despues de cada prueba
    void tearDown() { // cerramos el navegador
        driver.quit();
    }

    @Test // una prueba
    void CheckTitleAfterQueryTest() {
        // Abrimos una página web.
        driver.get("https://www.google.com");

        // Buscamos la caja de texto del buscador y simulamos la acción de búsqueda.
        driver.findElement(By.name("q")).sendKeys("UNMSM" + Keys.RETURN);

        // Esperamos tres segundos para que se complete la búsqueda.
        // Nota: En la práctica real, es mejor usar las esperas de Selenium.
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Imprimimos el título de la página.
        System.out.println("Título de la página: " + driver.getTitle());
    }
}

