package testingweb.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testingweb.selenium.MyWebDriver;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDate;

public class RentVehicleStepdefs {
    private final WebDriver webDriver = MyWebDriver.getWebDriver();
    private final WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(20));

    // A fresh renter prevents the UI from disabling booking when an active reservation already exists.
    private String registerFreshRenter() throws Exception {
        String apiBase = MyWebDriver.BASE_URL.contains("localhost")
                ? "http://localhost:8080/api/v1"
                : "https://renticar-backend.onrender.com/api/v1";
        String uniqueEmail = "renter.booking." + System.currentTimeMillis() + "@test.pe";
        String payload = String.format(
                "{\"name\":\"Carlos Tester\",\"email\":\"%s\",\"password\":\"Test123456\",\"role\":\"arrendatario\"}",
                uniqueEmail
        );
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiBase + "/authentication/sign-up"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertTrue(response.statusCode() == 201 || response.statusCode() == 200,
                "Failed to register test renter: " + response.statusCode());
        return uniqueEmail;
    }

    @Given("the renter signs in and views the details of vehicle {string}")
    public void signInAndOpenVehicleDetails(String vehicleId) throws Exception {
        String renterEmail = registerFreshRenter();

        webDriver.get(MyWebDriver.BASE_URL + "/login");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email"))).sendKeys(renterEmail);
        webDriver.findElement(By.id("password")).sendKeys("Test123456");
        webDriver.findElement(By.cssSelector("button.auth-button[type='submit']")).click();
        wait.until(ExpectedConditions.urlContains("/dashboard"));

        webDriver.get(MyWebDriver.BASE_URL + "/vehicles/" + vehicleId);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.detail-container")));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.action-button.primary")));
    }

    @When("the renter clicks the Rent Vehicle button")
    public void clickRentButton() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.action-button.primary"))).click();
        wait.until(ExpectedConditions.urlContains("/booking/"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("startDate")));
    }

    @And("the renter selects valid available start and end dates")
    public void selectValidStartAndEndDates() {
        // Unique future windows avoid backend overlap conflicts across repeated test runs.
        long dayOffset = ((System.currentTimeMillis() / 1000) % 700) * 4;
        LocalDate start = LocalDate.of(2027, 1, 1).plusDays(dayOffset);
        LocalDate end = start.plusDays(3);

        WebElement startInput = webDriver.findElement(By.id("startDate"));
        WebElement endInput = webDriver.findElement(By.id("endDate"));
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        // Native <input type="date"> rejects sendKeys formatting depending on OS locale.
        js.executeScript(
                "const el = arguments[0]; el.value = arguments[1]; el.dispatchEvent(new Event('input')); el.dispatchEvent(new Event('change'));",
                startInput, start.toString()
        );
        js.executeScript(
                "const el = arguments[0]; el.value = arguments[1]; el.dispatchEvent(new Event('input')); el.dispatchEvent(new Event('change'));",
                endInput, end.toString()
        );
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.total-price")));
    }

    @And("the renter clicks the Confirm Booking button")
    public void clickConfirmBookingButton() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.action-button.primary[type='submit']"))).click();
    }

    @Then("the system confirms the booking and redirects to My Bookings")
    public void verifyBookingConfirmation() {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        Assertions.assertNotNull(alert.getText());
        alert.accept();
        wait.until(ExpectedConditions.urlContains("/my-bookings"));
        Assertions.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.booking-card"))).isDisplayed());
    }
}
