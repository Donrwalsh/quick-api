package com.perficient.employees.controller;

import com.perficient.employees.dao.EmployeeDAO;
import com.perficient.employees.exception.DatabaseException;
import com.perficient.employees.exception.EmployeeNotFoundException;
import com.perficient.employees.exception.UnhandledException;
import com.perficient.employees.model.Employee;
import com.perficient.employees.exception.DatabaseConnectionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("employees/{emp_no}")
public class EmployeeController {

    @Autowired
    private EmployeeDAO employeeDAO;

    @GetMapping
    List<Employee> response(@PathVariable String emp_no) {
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
        } catch (Exception e) {
            throw new UnhandledException(e.toString());
        }
    }
}