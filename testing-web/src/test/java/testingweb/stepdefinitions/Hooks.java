package testingweb.stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import testingweb.selenium.MyWebDriver;

public class Hooks {

    @Before
    public void setUp() {
        WebDriver driver = MyWebDriver.getWebDriver();
        driver.manage().deleteAllCookies();
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl != null && currentUrl.startsWith("http")) {
            ((JavascriptExecutor) driver).executeScript("window.localStorage.clear(); window.sessionStorage.clear();");
        }
    }

    @After
    public void tearDown() {
        if (Boolean.parseBoolean(System.getProperty("quitAfter", "false"))) {
            MyWebDriver.quitDriver();
        }
    }
}
