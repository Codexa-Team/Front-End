Feature: IoT Vehicle Telemetry Monitoring
  Background:
    Given the authenticated user opens the IoT tracking panel for vehicle "1"

  Scenario: Real-time GPS location and telemetry monitoring
    When the Leaflet map and telemetry sensors finish loading
    Then the vehicle marker is displayed on the map along with the status panel
