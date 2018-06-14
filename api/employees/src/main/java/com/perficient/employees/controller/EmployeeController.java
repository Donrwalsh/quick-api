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
        try {
            input.setEmp_no(employeeDAO.create(input));
        } catch (Exception e) {
            exceptionService.toss(e);
        }
        URI location = URI.create("http://www.quick-api.com/employees/employees/" + input.getEmpNo());
        return ResponseEntity.created(location).body(input);
    }

    @GetMapping("/{emp_no}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> read(@PathVariable String emp_no) {
        validateEmployee(emp_no);
        List<Employee> response;
        try {
            response = employeeDAO.show(emp_no);
        } catch (Exception e) {
            throw new UnhandledException(e.getClass().getSimpleName(), e.getMessage());
        }
        return ResponseEntity.ok().body(response);
    }

    @PutMapping(path = "/{emp_no}", consumes="application/json")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> update(@PathVariable String emp_no, @RequestBody() Employee input) {
        try {
            employeeDAO.update(emp_no, input);
        } catch (Exception e) {
            throw new UnhandledException(e.getClass().getSimpleName(), e.getMessage());
        }
        return ResponseEntity.ok().body("hello world");
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
            throw new UnhandledException(e.getClass().getSimpleName(), e.getMessage());
        }
    }
}