package com.perficient.JDBC.dao;

import com.perficient.JDBC.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import com.perficient.JDBC.service.DatabaseService;
import javax.inject.Named;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
                    LocalDate.parse(response.getString("birth_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    response.getString("first_name"),
                    response.getString("last_name"),
                    response.getString("gender"),
                    LocalDate.parse(response.getString("hire_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            );
            result.add(responseEmployee);
        }
        return result;
    }

    @Override
    public int create(Employee input) throws Exception {
        return databaseService.performUpdate(
                "INSERT INTO employees (birth_date, first_name, last_name, gender, hire_date) VALUES (" +
                        "'" + input.getBirthDate() + "', " +
                        "'" + input.getFirstName() + "', " +
                        "'" + input.getLastName() + "', " +
                        "'" + input.getGender() + "', " +
                        "'" + input.getHireDate() + "');"
        );
    }

    @Override
    public void update(String emp_no, Employee input) throws Exception {
        boolean toggle = false;

        List<Object> updates = new ArrayList<>();
        updates.add(input.getBirthDate() == null ? null : "birth_date = '" + input.getBirthDate() + "'");
        updates.add(input.getFirstName() == null ? null : "first_name = '" + input.getFirstName() + "'");
        updates.add(input.getLastName() == null ? null : "last_name = '" + input.getLastName() + "'");
        updates.add(input.getGender() == null ? null : "gender = '" + input.getGender() + "'");
        updates.add(input.getHireDate() == null ? null : "hire_date = '" + input.getHireDate() + "'");

        String query  = "UPDATE employees SET ";
        for (Object object: updates) {
            if (object != null) {
                if (toggle) {
                    query += ", ";
                }
                query += object;
                toggle = true;
            }
        }
        query += " WHERE emp_no = " + emp_no;
        databaseService.performUpdate(query);
    }

    @Override
    public void delete(String emp_no) throws Exception {
        databaseService.performUpdate("DELETE FROM employees WHERE emp_no = " + emp_no);
    }

}
