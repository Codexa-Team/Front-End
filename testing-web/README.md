# RentiCar Web System Testing (`testing-web`)

Suite de pruebas de sistema E2E basadas en **Selenium WebDriver 4**, **Cucumber BDD (Gherkin)** y **JUnit 5 Platform Suite** para el frontend de RentiCar.

## Estructura del Proyecto

- `src/test/resources/testingweb/features/`: Escenarios de prueba en lenguaje Gherkin (`.feature`).
- `src/test/java/testingweb/stepdefinitions/`: Definiciones de pasos en Java (`*Stepdefs.java`) y ciclo de vida (`Hooks.java`).
- `src/test/java/testingweb/selenium/MyWebDriver.java`: Gestor de controlador ChromeDriver con Selenium Manager integrado.
- `src/test/java/testingweb/runner/`: Clases ejecutables (`RunCucumberTest` global y runners individuales por flujo).

## Ejecución en IntelliJ IDEA

1. Abrir el directorio `Front-End/testing-web` como proyecto Maven en IntelliJ IDEA (o hacer clic derecho sobre `testing-web/pom.xml` -> **Add as Maven Project**).
2. Navegar a `src/test/java/testingweb/runner/`.
3. Ejecutar cualquier runner con clic derecho -> **Run 'RunCucumberTestSignUp'**:
   - `RunCucumberTest` (Ejecuta toda la suite)
   - `RunCucumberTestSignUp`
   - `RunCucumberTestSignIn`
   - `RunCucumberTestRegisterVehicle`
   - `RunCucumberTestRentVehicle`
   - `RunCucumberTestIoTTelemetry`

## Ejecución desde Consola (Maven Wrapper)

```bash
# Ejecutar toda la suite apuntando a producción (Render)
./mvnw test -Dtest=RunCucumberTest

# Ejecutar un escenario individual en modo headless
./mvnw test -Dtest=RunCucumberTestSignIn -Dheadless=true -DquitAfter=true

# Ejecutar contra entorno local
./mvnw test -Dtest=RunCucumberTest -Dapp.url=http://localhost:4200
```
