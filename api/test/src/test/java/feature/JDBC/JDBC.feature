Feature: Developer Environment Employee CRUD Endpoints

Background:
  * def URL = 'http://quick-api-dev.com:8080/JDBC/employees/'

  Scenario: Simple CRUD Validation

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

#  Scenario: Update the new Employee
#
#    Given url URL + id
#    And request {"gender":"F","birth_date":"1990-08-07","last_name":"Farvour","hire_date":"2001-01-01","first_name":"Laura"}
#    When method PUT
#    Then status 200
##    And match response[0] contains {"gender":"F","birth_date":"1990-08-07","last_name":"Farvour","hire_date":"2001-01-01","first_name":"Laura"}
#
#  Scenario: Validate the updated Employee
#
#    Given url URL + id
#    When method GET
#    Then status 200
##    And match response[0] contains {"gender":"F","birth_date":"1990-08-07","last_name":"Farvour","hire_date":"2001-01-01","first_name":"Laura"}
#
#  Scenario: Delete the updated Employee
#
#    Given url URL + id
#    When method DELETE
#    Then status 204
#
#  Scenario: Validate the deleted Employee
#
#    Given url URL + id
#    When method GET
#    Then status 404