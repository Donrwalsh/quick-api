Feature: Developer Environment Employee CRUD Endpoints

  Background:
    * def URL = apiURL +  '/JDBC/employees/'

  Scenario: Simple JDBC CRUD Validation

    #Create
    Given url URL
    And request {"gender":"M","birth_date":"1987-10-22","last_name":"Tables","hire_date":"2001-01-01","first_name":"Robert"}
    When method POST
    Then status 201
    * def id = response[0].empNo
    And match response[0] contains {gender:'M', birthDate:'1987-10-22', firstName:'Robert', lastName:'Tables', hireDate:'2001-01-01', empNo:'#notnull'}

    #Validate (Read)
    Given url URL + id
    When method GET
    Then status 200
    And match response[0] contains {gender:'M', birthDate:'1987-10-22', firstName:'Robert', lastName:'Tables', hireDate:'2001-01-01', empNo:'#(id)'}

    #Update
    Given url URL + id
    And request {"gender":"F","birth_date":"1990-08-07","last_name":"Farvour","hire_date":"2001-01-01","first_name":"Laura"}
    When method PUT
    Then status 200
    And match response[0] contains {"gender":"F","birthDate":"1990-08-07","lastName":"Farvour","hireDate":"2001-01-01","firstName":"Laura"}

    #Validate (Read)
    Given url URL + id
    When method GET
    Then status 200
    And match response[0] contains {"gender":"F","birthDate":"1990-08-07","lastName":"Farvour","hireDate":"2001-01-01","firstName":"Laura"}

    #Delete
    Given url URL + id
    When method DELETE
    Then status 204

    #Validate (Read)
    Given url URL + id
    When method GET
    Then status 404