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
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

    private static final String DELETE_EMPLOYEE_BY_ID = "DELETE FROM employees WHERE emp_no = ?";

    @Override
    public List<Employee> show(String id) {
        return jdbcTemplate.query(GET_EMPLOYEE_BY_ID, new EmployeeMapper(), id);
    }

    @Override
    public int create(Employee input) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(CREATE_EMPLOYEE, Statement.RETURN_GENERATED_KEYS);
                statement.setObject(1, input.getBirthDate());
                statement.setString(2, input.getFirstName());
                statement.setString(3, input.getLastName());
                statement.setString(4, input.getGender());
                statement.setObject(5, input.getHireDate());
                return statement;
            }
        }, holder);

        return holder.getKey().intValue();
    }

    @Override
    public void update(String id, Employee input) {
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
        params.add(id);

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

    @Override
    public void delete(String id) {
        jdbcTemplate.update(DELETE_EMPLOYEE_BY_ID, id);
    }

    private static final class EmployeeMapper implements RowMapper<Employee> {

        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {

            Employee employee = new Employee(
                    rs.getInt("emp_no"),
                    LocalDate.parse(rs.getString("birth_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("gender"),
                    LocalDate.parse(rs.getString("hire_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            );

            return employee;
        }
    }

}
