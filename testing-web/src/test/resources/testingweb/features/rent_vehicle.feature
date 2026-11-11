Feature: El app permite la reserva de un vehiculo disponible
  Background:
    Given El arrendatario inicia sesion y accede al detalle del vehiculo "1"

  Scenario: Reserva exitosa de vehiculo con fechas validas
    When Hace click en el boton de reserva del vehiculo
    And Selecciona fechas disponibles validas de inicio y fin
    And Hace click en el boton Confirmar Reserva
    Then El sistema confirma la reserva y redirige a Mis Reservas
