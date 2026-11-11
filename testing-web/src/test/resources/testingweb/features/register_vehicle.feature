Feature: Vehicle Publication
  Background:
    Given the owner signs in and navigates to the publish vehicle form

  Scenario: Successful vehicle publication in the catalog
    When the owner enters vehicle brand "Kia", model "Sportage", year "2024", and daily price "210"
    And the owner uploads the vehicle image
    And the owner clicks the Publish Vehicle button
    Then the vehicle "Kia Sportage" appears in My Vehicles
