Feature: El app permite la publicacion de un nuevo vehiculo
  Background:
    Given El arrendador inicia sesion y navega al formulario de publicacion

  Scenario: Publicacion exitosa de un vehiculo en el catalogo
    When Ingresa los datos del vehiculo marca "Kia", modelo "Sportage", anio "2024" y precio "210"
    And Adjunta la imagen del vehiculo
    And Hace click en el boton Publicar Vehiculo
    Then El vehiculo "Kia Sportage" aparece registrado en Mis Vehiculos
