package com.perficient.employees.dao;

import com.perficient.employees.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import com.perficient.employees.service.DatabaseService;
import javax.inject.Named;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Named
public class EmployeeDAOImpl implements EmployeeDAO {

    @Autowired
    private DatabaseService databaseService;

    @Override
    public List<Employee> show(String id) throws Exception {
        ResultSet response =databaseService.performQuery(
                "SELECT * FROM employees where emp_no = " + id);
        List<Employee> result = new ArrayList<>();
        while (response.next()) {
            Employee responseEmployee = new Employee(
                    response.getInt("emp_no"),
                    response.getDate("birth_date"),
                    response.getString("first_name"),
                    response.getString("last_name"),
                    response.getString("gender"),
                    response.getDate("hire_date")
            );
            result.add(responseEmployee);
        }
        return result;
    }
}
