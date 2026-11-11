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

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;

public class RegisterVehicleStepdefs {
    private final WebDriver webDriver = MyWebDriver.getWebDriver();
    private final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));

    @Given("El arrendador inicia sesion y navega al formulario de publicacion")
    public void elArrendadorIniciaSesionYNavegaAlFormularioDePublicacion() {
        webDriver.get(MyWebDriver.BASE_URL + "/login");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email"))).sendKeys("estefano.solis@codexa.pe");
        webDriver.findElement(By.id("password")).sendKeys("admin123");
        webDriver.findElement(By.cssSelector("button.auth-button[type='submit']")).click();
        wait.until(ExpectedConditions.urlContains("/my-vehicles"));

        webDriver.get(MyWebDriver.BASE_URL + "/publish-vehicle");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("brand")));
    }

    @When("Ingresa los datos del vehiculo marca {string}, modelo {string}, anio {string} y precio {string}")
    public void ingresaLosDatosDelVehiculo(String brand, String model, String year, String price) {
        webDriver.findElement(By.id("brand")).sendKeys(brand);
        webDriver.findElement(By.id("model")).sendKeys(model);
        webDriver.findElement(By.id("year")).sendKeys(year);
        webDriver.findElement(By.id("pricePerDay")).sendKeys(price);
    }

    @And("Adjunta la imagen del vehiculo")
    public void adjuntaLaImagenDelVehiculo() throws URISyntaxException {
        URL resource = getClass().getClassLoader().getResource("car-sample.jpg");
        Assertions.assertNotNull(resource, "Sample car image resource not found");
        String absolutePath = new File(resource.toURI()).getAbsolutePath();
        webDriver.findElement(By.id("imageFile")).sendKeys(absolutePath);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("img.image-preview")));
    }

    @And("Hace click en el boton Publicar Vehiculo")
    public void haceClickEnElBotonPublicarVehiculo() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.submit-button"))).click();
    }

    @Then("El vehiculo {string} aparece registrado en Mis Vehiculos")
    public void elVehiculoApareceRegistradoEnMisVehiculos(String expectedVehicleTitle) {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        Assertions.assertNotNull(alert.getText());
        alert.accept();
        wait.until(ExpectedConditions.urlContains("/my-vehicles"));
        boolean vehicleVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h3[contains(text(), '" + expectedVehicleTitle + "')]")
        )).isDisplayed();
        Assertions.assertTrue(vehicleVisible);
    }
}
