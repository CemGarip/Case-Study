#language: en
@UI
Feature: Flight Search, Filtering, Sorting and Data Analysis

  # --- Scenario Outline: Parameterized UI Test Structure for All Cases (Part 1 & 4) ---
  # Provides parameterized data for different routes and test types.
  @UI_Execution
  Scenario Outline: 1.0 - Parametrized Flight Search, Filtering & Analysis
    Given user navigates to "baseUrl" homepage
    And user selects round-trip travel type

    # Parameters are taken directly from the Examples table
    And user enters "<Origin>" as origin and "<Destination>" as destination
    And user selects "<DepartureDate>" as departure and "<ReturnDate>" as return date
    When user clicks the search button

    # --- Conditional Steps ---

    # PART 1: Basic Search and Time Filter (Common for all Part 1 tests)
    And user applies time filter if scenario is "<TestType>"

    # PART 1: Price Sorting (Only for Case 2)
    And user applies Turkish Airlines filter and sorts if scenario is "<TestType>"
    Then prices are verified to be sorted from lowest to highest if scenario is "<TestType>"

    # PART 4: Data Analysis (Only for the Nicosia route)
    Then flight data is extracted, saved to CSV, and analyzed if scenario is "<TestType>"


  # --- Data Sets (Examples) ---
    Examples:
      | Origin | Destination | DepartureDate | ReturnDate | TestType | Description |
      | İstanbul | Ankara | 15.01.2026 | 25.01.2026 | Part1_Basic | Case 1: Basic Search and Time Filter |
      | İstanbul | Ankara | 16.01.2026 | 26.01.2026 | Part1_Price | Case 2: Price Sorting for Turkish Airlines |
      | İstanbul | Lefkoşa | 15.02.2026 | 25.02.2026 | Part4_Analysis | Case 4: Data Extraction and Analysis |

  # --- Case 3: Critical Path Testing (One-Way Filtered Search) ---
  @UI_CriticalPath
  Scenario: 3.1 - Critical Path Testing (One-Way Filtered Search)
    Given user navigates to "baseUrl" homepage
    And user selects one-way travel type
    And user enters "İstanbul" as origin and "Ankara" as destination
    And user only selects "oneWayDepartureDate"
    When user clicks the search button

    And user selects direct flights only from the transit filter
    And user sets departure time filter between 6 and 18
    And user selects "All Airlines" from the airline filter
    And user selects "SAW" and "IST" airports from the airport filter
    And user sorts the results by price in ascending order
    Then the prices are verified to be sorted from lowest to highest
    And scrolling to the top of the page is successful