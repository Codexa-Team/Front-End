Feature: El app permite el monitoreo de telemetria IoT vehicular
  Background:
    Given El usuario autenticado abre el panel de rastreo IoT del vehiculo "1"

  Scenario: Visualizacion de posicion GPS y telemetria en tiempo real
    When El mapa Leaflet y los sensores de telemetria terminan de cargar
    Then Se visualiza el marcador del vehiculo en el mapa y el panel de estado
