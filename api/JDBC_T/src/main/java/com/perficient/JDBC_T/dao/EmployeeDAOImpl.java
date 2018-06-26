package com.perficient.JDBC_T.dao;

import com.perficient.JDBC_T.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.inject.Named;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Named
public class EmployeeDAOImpl implements EmployeeDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public EmployeeDAOImpl(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }

    private static final String GET_EMPLOYEE_BY_ID = "SELECT * FROM `employees` WHERE emp_no = ?";

    private static final String CREATE_EMPLOYEE = "INSERT INTO employees " +
            "(birth_date, first_name, last_name, gender, hire_date) VALUES (?, ?, ?, ?, ?) ";

    private static String UPDATE_EMPLOYEE = "UPDATE employees SET ";

    @Override
    public List<Employee> show(String id) throws Exception {
        return jdbcTemplate.query(GET_EMPLOYEE_BY_ID, new EmployeeMapper(), id);
    }

    @Override
    public int create(Employee input) throws Exception {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(CREATE_EMPLOYEE, Statement.RETURN_GENERATED_KEYS);
                statement.setDate(1, input.getBirthDate());
                statement.setString(2, input.getFirstName());
                statement.setString(3, input.getLastName());
                statement.setString(4, input.getGender());
                statement.setDate(5, input.getHireDate());
                return statement;
            }
        }, holder);

        return holder.getKey().intValue();
    }

    private static final class EmployeeMapper implements RowMapper<Employee> {

        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {

            Employee employee = new Employee(
                    rs.getInt("emp_no"),
                    rs.getDate("birth_date"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("gender"),
                    rs.getDate("hire_date")
            );

            return employee;
        }
    }

    @Override
    public void update(String emp_no, Employee input) throws Exception {
        String query = UPDATE_EMPLOYEE;
        boolean toggle = false;

        List<Object> updates = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        if (input.getBirthDate() != null) {
            updates.add("birth_date = ?");
            params.add(input.getBirthDate());
        }
        if (input.getFirstName() != null) {
            updates.add("first_name = ?");
            params.add(input.getFirstName());
        }
        if (input.getLastName() != null) {
            updates.add("last_name = ?");
            params.add(input.getLastName());
        }
        if (input.getGender() != null) {
            updates.add("gender = ?");
            params.add(input.getGender());
        }
        if (input.getHireDate() != null) {
            updates.add("hire_date = ?");
            params.add(input.getHireDate());
        }
        params.add(emp_no);

        Object[] array = new Object[params.size()];
        for (int i = 0; i < params.size(); i++) {
            array[i] = params.get(i);
        }

        for (Object object: updates) {
            if (toggle) {
                query += ", ";
            }
            query += object;
            toggle = true;
        }
        query += " WHERE emp_no = ?";
        jdbcTemplate.update(query, array);
    }
//
//    @Override
//    public void delete(String emp_no) throws Exception {
//        databaseService.performUpdate("DELETE FROM employees WHERE emp_no = " + emp_no);
//    }

}
