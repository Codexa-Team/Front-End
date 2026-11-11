Feature: Vehicle Rental Booking
  Background:
    Given the renter signs in and views the details of vehicle "1"

  Scenario: Successful vehicle booking with valid dates
    When the renter clicks the Rent Vehicle button
    And the renter selects valid available start and end dates
    And the renter clicks the Confirm Booking button
    Then the system confirms the booking and redirects to My Bookings
