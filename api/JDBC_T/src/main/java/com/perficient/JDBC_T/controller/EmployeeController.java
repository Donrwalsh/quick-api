package com.perficient.JDBC_T.controller;

import com.perficient.JDBC_T.dao.EmployeeDAO;
import com.perficient.JDBC_T.exception.DatabaseException;
import com.perficient.JDBC_T.exception.EmployeeNotFoundException;
import com.perficient.JDBC_T.model.Employee;
import com.perficient.JDBC_T.service.ExceptionService;
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
    ResponseEntity<?> create(@RequestBody() @Valid Employee input) throws Exception {
        List<Employee> response = new ArrayList<>();
        URI location;
        int createdID = 0;
        try {
            if (input.getEmpNo() != 0) {
                throw new DatabaseException("Your request body contained an emp_no. Please remove it and try again");
            }
            createdID = employeeDAO.create(input);
            response = employeeDAO.show(Integer.toString(createdID));
        } catch (Exception e) {
            exceptionService.toss(e);
        }
        location = URI.create("http://www.quick-api.com/JDBC_T/JDBC_T/" + createdID);
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
            if (!isNumeric(id)) {
                throw new SQLSyntaxErrorException(id + " is not a valid employee id. Please use an integer");
            }
            if (this.employeeDAO.show(id).isEmpty()) {
                throw new EmployeeNotFoundException(id);
            }
        } catch (Exception e) {
            exceptionService.toss(e);
        }
    }

    public static boolean isNumeric(String str)
    {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }
}