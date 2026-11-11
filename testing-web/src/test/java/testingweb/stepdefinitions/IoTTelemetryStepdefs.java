package testingweb.stepdefinitions;

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

public class IoTTelemetryStepdefs {
    private final WebDriver webDriver = MyWebDriver.getWebDriver();
    private final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));

    @Given("El usuario autenticado abre el panel de rastreo IoT del vehiculo {string}")
    public void elUsuarioAutenticadoAbreElPanelDeRastreoIoTDelVehiculo(String vehicleId) {
        webDriver.get(MyWebDriver.BASE_URL + "/login");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email"))).sendKeys("estefano.solis@codexa.pe");
        webDriver.findElement(By.id("password")).sendKeys("admin123");
        webDriver.findElement(By.cssSelector("button.auth-button[type='submit']")).click();
        wait.until(ExpectedConditions.urlContains("/my-vehicles"));

        webDriver.get(MyWebDriver.BASE_URL + "/tracking/" + vehicleId);
    }

    @When("El mapa Leaflet y los sensores de telemetria terminan de cargar")
    public void elMapaLeafletYLosSensoresDeTelemetriaTerminanDeCargar() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#map.leaflet-container")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.status-panel")));
    }

    @Then("Se visualiza el marcador del vehiculo en el mapa y el panel de estado")
    public void seVisualizaElMarcadorDelVehiculoEnElMapaYElPanelDeEstado() {
        Assertions.assertTrue(webDriver.findElement(By.cssSelector("#map.leaflet-container")).isDisplayed());
        Assertions.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".leaflet-marker-icon"))).isDisplayed());
        Assertions.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".status-panel .status-badge"))).isDisplayed());
    }
}
