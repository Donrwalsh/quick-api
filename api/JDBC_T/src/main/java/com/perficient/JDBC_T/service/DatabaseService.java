package com.perficient.JDBC_T.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

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

    public int performUpdate(String query) throws Exception {
        int resultID = 0;
        Connection con = getConnection();
        Statement stmt = con.createStatement();
        stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()){
            resultID = rs.getInt(1);
        }
        return resultID;
    }
}
