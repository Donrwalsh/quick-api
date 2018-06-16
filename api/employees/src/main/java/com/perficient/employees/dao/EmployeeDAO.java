package com.perficient.employees.dao;

import com.perficient.employees.model.Employee;

import java.util.List;

public interface EmployeeDAO {
    List<Employee> show(String id) throws Exception;
    int create(Employee input) throws Exception;
    void update(String emp_no, Employee input) throws Exception;
    void delete(String emp_no) throws Exception;
}
