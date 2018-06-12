package com.perficient.employees.dao;

import com.perficient.employees.model.Employee;

import java.util.List;

public interface EmployeeDAO {
    List<Employee> show(String id) throws Exception;
}
