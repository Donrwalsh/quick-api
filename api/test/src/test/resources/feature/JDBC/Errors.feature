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

    #Create endpoint will not accept the 'emp_no' attribute despite it being a valid Employee attribute
    Given url URL
    And request {"gender":"M","birth_date":"1987-10-22","last_name":"Tables","hire_date":"2001-01-01","first_name":"Robert","emp_no":500}
    When method POST
    Then status 400
    And match response contains {status:"BAD_REQUEST",message:"Database Error"}

  Scenario: Read Endpoint

    #Read endpoint should not accept requests with invalid URI param
    Given url URL + 'potato'
    When method GET
    Then status 400
    And match response contains {status:"BAD_REQUEST",message:"SQL Syntax Error"}

