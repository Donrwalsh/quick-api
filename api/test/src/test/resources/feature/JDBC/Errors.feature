Feature: JDBC CRUD Error Validation

  Background:
    * def URL = apiURL + '/JDBC/employees/'

  Scenario: Create Endpoint

    #Create endpoint requires Valid Employee object in request body
    Given url URL
    And request {}
    When method POST
    Then status 400
    And match response contains {status:"BAD_REQUEST",message:"Validation error"}

    #Create endpoint will not accept bodies containing non-Employee attributes
    Given url URL
    And request {"potato":"potato"}
    When method POST
    Then status 400
    And match response contains {status:"BAD_REQUEST",message:"Malformed JSON request"}



