package com.perficient.employees.controller;

import com.perficient.employees.dao.EmployeeDAO;
import com.perficient.employees.exception.DatabaseException;
import com.perficient.employees.exception.EmployeeNotFoundException;
import com.perficient.employees.exception.UnhandledException;
import com.perficient.employees.model.Employee;
import com.perficient.employees.exception.DatabaseConnectionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="employees")
public class EmployeeController {

    @Autowired
    private EmployeeDAO employeeDAO;

    @PostMapping(consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<?> create(@RequestBody(required = false) Employee input) {
        try {
            employeeDAO.create(input);
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } catch (Exception e) {
            throw new UnhandledException(e.toString());
        }
        URI location = URI.create("https://www.google.com");
        return ResponseEntity.created(location).body(input);
    }

    @GetMapping("/{emp_no}")
    @ResponseStatus(HttpStatus.OK)
    List<Employee> read(@PathVariable String emp_no) {
        validateEmployee(emp_no);
        try {
            return employeeDAO.show(emp_no);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            List<Employee> response = new ArrayList<>();
            return response;
        }
    }

    private void validateEmployee(String id) {
        try {
            if (this.employeeDAO.show(id).isEmpty()) {
                throw new EmployeeNotFoundException(id);
            }
        } catch (DatabaseConnectionException e) {
            throw new DatabaseConnectionException(e.getMessage());
        } catch (SQLSyntaxErrorException e) {
            throw new DatabaseException(e.getMessage());
        } catch (EmployeeNotFoundException e) {
            throw new EmployeeNotFoundException(id);
        } catch (Exception e) {
            throw new UnhandledException(e.toString());
        }
    }
}