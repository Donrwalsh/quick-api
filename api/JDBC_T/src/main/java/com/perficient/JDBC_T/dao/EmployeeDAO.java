package com.perficient.JDBC_T.dao;

import com.perficient.JDBC_T.model.Employee;

import java.util.List;

public interface EmployeeDAO {
    List<Employee> show(String id);
    int create(Employee input);
    void update(String id, Employee input);
    void delete(String id);
}
