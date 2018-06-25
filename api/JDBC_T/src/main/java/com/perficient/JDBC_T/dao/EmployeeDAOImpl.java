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

//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        Object[] params = new Object[] { input.getBirthDate(), input.getFirstName(),
//                input.getLastName(), input.getGender(), input.getHireDate() };
//        return jdbcTemplate.update(CREATE_EMPLOYEE, input.getBirthDate(), input.getFirstName(),
//                input.getLastName(), input.getGender(), input.getHireDate());

        //return keyHolder.getKey().intValue();
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

//    @Override
//    public int create(Employee input) throws Exception {
//        return databaseService.performUpdate(
//                "INSERT INTO employees (emp_no, birth_date, first_name, last_name, gender, hire_date) VALUES (" +
//                        input.getEmpNo() + "," +
//                        "'" + input.getBirthDate() + "', " +
//                        "'" + input.getFirstName() + "', " +
//                        "'" + input.getLastName() + "', " +
//                        "'" + input.getGender() + "', " +
//                        "'" + input.getHireDate() + "');"
//        );
//    }

//    @Override
//    public void update(String emp_no, Employee input) throws Exception {
//        boolean toggle = false;
//
//        List<Object> updates = new ArrayList<>();
//        updates.add(input.getBirthDate() == null ? null : "birth_date = '" + input.getBirthDate() + "'");
//        updates.add(input.getFirstName() == null ? null : "first_name = '" + input.getFirstName() + "'");
//        updates.add(input.getLastName() == null ? null : "last_name = '" + input.getLastName() + "'");
//        updates.add(input.getGender() == null ? null : "gender = '" + input.getGender() + "'");
//        updates.add(input.getHireDate() == null ? null : "hire_date = '" + input.getHireDate() + "'");
//
//        String query  = "UPDATE employees SET ";
//        for (Object object: updates) {
//            if (object != null) {
//                if (toggle) {
//                    query += ", ";
//                }
//                query += object;
//                toggle = true;
//            }
//        }
//        query += " WHERE emp_no = " + emp_no;
//        databaseService.performUpdate(query);
//    }
//
//    @Override
//    public void delete(String emp_no) throws Exception {
//        databaseService.performUpdate("DELETE FROM employees WHERE emp_no = " + emp_no);
//    }

}
