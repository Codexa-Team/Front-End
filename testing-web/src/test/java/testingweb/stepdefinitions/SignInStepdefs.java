package testingweb.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testingweb.selenium.MyWebDriver;

import java.time.Duration;

public class SignInStepdefs {
    private final WebDriver webDriver = MyWebDriver.getWebDriver();
    private final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));

    @Given("El usuario ingresa a la pantalla de inicio de sesion")
    public void elUsuarioIngresaALaPantallaDeInicioDeSesion() {
        webDriver.get(MyWebDriver.BASE_URL + "/login");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
    }

    @When("Ingresa el correo {string} y la contrasena {string}")
    public void ingresaElCorreoYLaContrasena(String email, String password) {
        webDriver.findElement(By.id("email")).clear();
        webDriver.findElement(By.id("email")).sendKeys(email);
        webDriver.findElement(By.id("password")).clear();
        webDriver.findElement(By.id("password")).sendKeys(password);
    }

    @And("Hace click en el boton Ingresar")
    public void haceClickEnElBotonIngresar() {
        webDriver.findElement(By.cssSelector("button.auth-button[type='submit']")).click();
    }

    @Then("El usuario ingresa a su panel correspondiente en RentiCar")
    public void elUsuarioIngresaASuPanelCorrespondienteEnRentiCar() {
        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("/my-vehicles"),
                ExpectedConditions.urlContains("/dashboard")
        ));
        String currentUrl = webDriver.getCurrentUrl();
        Assertions.assertTrue(currentUrl.contains("/my-vehicles") || currentUrl.contains("/dashboard"));
    }
}
