Feature: User Sign Up
  Background:
    Given the user opens the RentiCar application

  Scenario: Successful new user registration
    Given the user clicks on the Register link
    When the user fills in valid name, email, password, and role
    And the user clicks the Register button
    Then the user is registered successfully in the system
