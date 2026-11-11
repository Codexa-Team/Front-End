Feature: User Sign In
  Background:
    Given the user opens the login page

  Scenario: Successful login with valid credentials
    When the user enters email "estefano.solis@codexa.pe" and password "admin123"
    And the user clicks the Sign In button
    Then the user is redirected to their RentiCar dashboard
