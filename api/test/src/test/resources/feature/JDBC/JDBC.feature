Feature: CRUD

  Scenario: Read from database

    Given url 'http://quick-api.com:8080/JDBC/employees/12345'
    When method GET
    Then status 200