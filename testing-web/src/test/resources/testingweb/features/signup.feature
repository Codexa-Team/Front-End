Feature: El app permite el registro de nuevo usuario
  Background:
    Given El usuario ingresa a la aplicacion RentiCar

  Scenario: Registro de nuevo usuario en la plataforma
    Given Hace click en el enlace Registrate del formulario
    When Ingresa los datos requeridos de nombre, correo, contrasena y rol
    And Hace click en el boton Registrarse
    Then El usuario es registrado en el sistema exitosamente
