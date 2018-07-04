Feature: CRUD

  Scenario: Read from database

    Given url apiURL + '/JDBC/employees/12345'
    When method GET
    Then status 200