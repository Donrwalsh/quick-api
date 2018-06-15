package com.perficient.employees.controller;

import com.perficient.employees.dao.EmployeeDAO;
import com.perficient.employees.exception.DatabaseException;
import com.perficient.employees.exception.EmployeeNotFoundException;
import com.perficient.employees.exception.UnhandledException;
import com.perficient.employees.model.Employee;
import com.perficient.employees.exception.DatabaseConnectionException;
import com.perficient.employees.service.ExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="employees")
public class EmployeeController {

    @Autowired
    private EmployeeDAO employeeDAO;

    @Autowired
    private ExceptionService exceptionService;

    @PostMapping(consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<?> create(@RequestBody() @Valid Employee input) {
        if (input.getEmpNo() != 0) {
            throw new DatabaseException("Your request body contained an emp_no. Please remove it and try again");
        }
        List<Employee> response = new ArrayList<>();
        URI location;
        int createdID = 0;
        try {
            createdID = employeeDAO.create(input);
            response = employeeDAO.show(Integer.toString(createdID));
        } catch (Exception e) {
            exceptionService.toss(e);
        }
        location = URI.create("http://www.quick-api.com/employees/employees/" + createdID);
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{emp_no}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> read(@PathVariable String emp_no) {
        validateEmployee(emp_no);
        List<Employee> response = new ArrayList<>();
        try {
            response = employeeDAO.show(emp_no);
        } catch (Exception e) {
            exceptionService.toss(e);
        }
        return ResponseEntity.ok().body(response);
    }

    @PutMapping(path = "/{emp_no}", consumes="application/json")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> update(@PathVariable String emp_no, @RequestBody() Employee input) {
        validateEmployee(emp_no);
        List<Employee> response = new ArrayList<>();
        try {
            employeeDAO.update(emp_no, input);
            response = employeeDAO.show(emp_no);
        } catch (Exception e) {
            exceptionService.toss(e);
        }
        return ResponseEntity.ok().body(response);
    }

    private void validateEmployee(String id) {
        try {
            if (this.employeeDAO.show(id).isEmpty()) {
                throw new EmployeeNotFoundException(id);
            }
        } catch (Exception e) {
            exceptionService.toss(e);
        }
    }
}