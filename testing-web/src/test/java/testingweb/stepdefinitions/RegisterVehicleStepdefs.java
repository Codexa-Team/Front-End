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

    @Given("the owner signs in and navigates to the publish vehicle form")
    public void signInAndNavigateToPublishForm() {
        webDriver.get(MyWebDriver.BASE_URL + "/login");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email"))).sendKeys("estefano.solis@codexa.pe");
        webDriver.findElement(By.id("password")).sendKeys("admin123");
        webDriver.findElement(By.cssSelector("button.auth-button[type='submit']")).click();
        wait.until(ExpectedConditions.urlContains("/my-vehicles"));

        webDriver.get(MyWebDriver.BASE_URL + "/publish-vehicle");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("brand")));
    }

    @When("the owner enters vehicle brand {string}, model {string}, year {string}, and daily price {string}")
    public void enterVehicleDetails(String brand, String model, String year, String price) {
        webDriver.findElement(By.id("brand")).sendKeys(brand);
        webDriver.findElement(By.id("model")).sendKeys(model);
        webDriver.findElement(By.id("year")).sendKeys(year);
        webDriver.findElement(By.id("pricePerDay")).sendKeys(price);
    }

    @And("the owner uploads the vehicle image")
    public void uploadVehicleImage() throws URISyntaxException {
        URL resource = getClass().getClassLoader().getResource("car-sample.jpg");
        Assertions.assertNotNull(resource, "Sample car image resource not found");
        String absolutePath = new File(resource.toURI()).getAbsolutePath();
        webDriver.findElement(By.id("imageFile")).sendKeys(absolutePath);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("img.image-preview")));
    }

    @And("the owner clicks the Publish Vehicle button")
    public void clickPublishVehicleButton() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.submit-button"))).click();
    }

    @Then("the vehicle {string} appears in My Vehicles")
    public void verifyVehicleInMyVehicles(String expectedVehicleTitle) {
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
