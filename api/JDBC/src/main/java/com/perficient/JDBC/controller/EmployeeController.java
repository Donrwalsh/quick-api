package com.perficient.JDBC.controller;

import com.perficient.JDBC.dao.EmployeeDAO;
import com.perficient.JDBC.exception.DatabaseException;
import com.perficient.JDBC.exception.EmployeeNotFoundException;
import com.perficient.JDBC.model.Employee;
import com.perficient.JDBC.service.ExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
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
    ResponseEntity<?> create(@RequestBody() @Valid Employee input) throws Exception {
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
        location = URI.create("http://www.quick-api.com/JDBC/JDBC/" + createdID);
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{emp_no}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> read(@PathVariable String emp_no) throws Exception {
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
    ResponseEntity<?> update(@PathVariable String emp_no, @RequestBody() Employee input) throws Exception {
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

    @DeleteMapping(path = "/{emp_no}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable String emp_no) throws Exception {
        validateEmployee(emp_no);
        try {
            employeeDAO.delete(emp_no);
        } catch (Exception e) {
            exceptionService.toss(e);
        }
    }

    private void validateEmployee(String id) throws Exception {
        try {
            if (this.employeeDAO.show(id).isEmpty()) {
                throw new EmployeeNotFoundException(id);
            }
        } catch (Exception e) {
            exceptionService.toss(e);
        }
    }
}