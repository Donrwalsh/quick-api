Feature: Developer Environment Employee CRUD Endpoints

Background:
  * def URL = 'http://quick-api-dev.com:8080/JDBC/employees/'

  Scenario: Create a new Employee

    Given url URL
    And request {"gender":"M","birth_date":"1987-10-22","last_name":"Tables","hire_date":"2001-01-01","first_name":"Robert"}
    When method POST
    Then status 201
      * def id = response[0].empNo
#    And match response[0] contains {gender:'M', birthDate:'1987-10-22', firstName:'Robert', lastName:'Tables', hireDate:'2001-01-01', empNo:'#notnull'}

    Given url URL + id
    When method GET
    Then status 200
#    And match response[0] contains {gender:'M', birthDate:'1987-10-22', firstName:'Robert', lastName:'Tables', hireDate:'2001-01-01', empNo:#'(id)'}