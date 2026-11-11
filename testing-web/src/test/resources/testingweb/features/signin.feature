Feature: El app permite el inicio de sesion de usuario
  Background:
    Given El usuario ingresa a la pantalla de inicio de sesion

  Scenario: Inicio de sesion exitoso con credenciales validas
    When Ingresa el correo "estefano.solis@codexa.pe" y la contrasena "admin123"
    And Hace click en el boton Ingresar
    Then El usuario ingresa a su panel correspondiente en RentiCar
