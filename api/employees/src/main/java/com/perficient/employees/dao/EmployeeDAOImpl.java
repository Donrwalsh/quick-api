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

    @Override
    public boolean create(Employee input) throws Exception {
        databaseService.performUpdate(
                "INSERT INTO employees (emp_no, birth_date, first_name, last_name, gender, hire_date) VALUES (" +
                        input.getEmpNo() + "," +
                        "'" + input.getBirthDate() + "', " +
                        "'" + input.getFirstName() + "', " +
                        "'" + input.getLastName() + "', " +
                        "'" + input.getGender() + "', " +
                        "'" + input.getHireDate() + "')"
        );
        return false;
    }
}
