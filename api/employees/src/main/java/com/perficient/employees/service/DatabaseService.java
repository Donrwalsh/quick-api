package com.perficient.employees.service;

import com.perficient.employees.exception.DatabaseConnectionException;
import com.perficient.employees.exception.DatabaseException;
import com.perficient.employees.exception.UnhandledException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class DatabaseService {

    @Value("${datasource.url}")
    private String url;

    @Value("${datasource.username}")
    private String username;

    @Value("${datasource.driver-class-name}")
    private String driverClassName;

    @Value("${datasource.password}")
    private String password;

    public Connection getConnection() throws Exception {
        Class.forName(driverClassName);
        Connection con = DriverManager.getConnection(
                url,
                username,
                password);
        return con;
    }

    public ResultSet performQuery(String query) throws Exception {
        Connection con = getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        return rs;
    }
}
