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

    @Given("the user opens the RentiCar application")
    public void openApplication() {
        webDriver.get(MyWebDriver.BASE_URL + "/login");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.auth-card")));
    }

    @Given("the user clicks on the Register link")
    public void clickRegisterLink() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/register']"))).click();
        wait.until(ExpectedConditions.urlContains("/register"));
    }

    @When("the user fills in valid name, email, password, and role")
    public void fillRegistrationForm() {
        String uniqueEmail = "renter." + System.currentTimeMillis() + "@renticar.test";
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name"))).sendKeys("Carlos Mendoza");
        webDriver.findElement(By.id("email")).sendKeys(uniqueEmail);
        webDriver.findElement(By.id("password")).sendKeys("SecurePass123");
        webDriver.findElement(By.cssSelector("input[type='radio'][value='arrendatario']")).click();
    }

    @And("the user clicks the Register button")
    public void clickRegisterButton() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.auth-button[type='submit']"))).click();
    }

    @Then("the user is registered successfully in the system")
    public void verifySuccessfulRegistration() {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        Assertions.assertNotNull(alert.getText());
        alert.accept();
        wait.until(ExpectedConditions.urlContains("/login"));
        Assertions.assertTrue(webDriver.getCurrentUrl().contains("/login"));
    }
}
