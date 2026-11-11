package testingweb.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testingweb.selenium.MyWebDriver;

import java.time.Duration;

public class SignUpStepdefs {
    private final WebDriver webDriver = MyWebDriver.getWebDriver();
    private final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));

    @Given("El usuario ingresa a la aplicacion RentiCar")
    public void elUsuarioIngresaALaAplicacionRentiCar() {
        webDriver.get(MyWebDriver.BASE_URL + "/login");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.auth-card")));
    }

    @Given("Hace click en el enlace Registrate del formulario")
    public void haceClickEnElEnlaceRegistrateDelFormulario() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/register']"))).click();
        wait.until(ExpectedConditions.urlContains("/register"));
    }

    @When("Ingresa los datos requeridos de nombre, correo, contrasena y rol")
    public void ingresaLosDatosRequeridosDeNombreCorreoContrasenaYRol() {
        String uniqueEmail = "renter." + System.currentTimeMillis() + "@renticar.test";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name"))).sendKeys("Carlos Mendoza");
        webDriver.findElement(By.id("email")).sendKeys(uniqueEmail);
        webDriver.findElement(By.id("password")).sendKeys("SecurePass123");
        webDriver.findElement(By.cssSelector("input[type='radio'][value='arrendatario']")).click();
    }

    @And("Hace click en el boton Registrarse")
    public void haceClickEnElBotonRegistrarse() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.auth-button[type='submit']"))).click();
    }

    @Then("El usuario es registrado en el sistema exitosamente")
    public void elUsuarioEsRegistradoEnElSistemaExitosamente() {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        Assertions.assertNotNull(alert.getText());
        alert.accept();
        wait.until(ExpectedConditions.urlContains("/login"));
        Assertions.assertTrue(webDriver.getCurrentUrl().contains("/login"));
    }
}
