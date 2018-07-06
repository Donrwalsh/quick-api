package com.perficient.JDBC_T.service;

import com.perficient.JDBC_T.exception.DatabaseConnectionException;
import com.perficient.JDBC_T.exception.DatabaseException;
import com.perficient.JDBC_T.exception.EmployeeNotFoundException;
import com.perficient.JDBC_T.exception.UnhandledException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;

@Service
public class ExceptionService {

    public void toss(Exception e) throws Exception {
        switch (e.getClass().getSimpleName()) {
            case "SQLIntegrityConstraintViolationException":
                throw new DatabaseException(e.getMessage());
            case "EmployeeNotFoundException":
                throw new EmployeeNotFoundException(e.getMessage());
            case "DatabaseException":
                throw new DatabaseException(e.getMessage());
            case "DatabaseConnectionException":
                throw new DatabaseConnectionException(e.getMessage());
            case "SQLSyntaxErrorException":
                throw new SQLSyntaxErrorException(e.getMessage());
            case "SQLException":
                throw new SQLException(e.getMessage());
            case "MysqlDataTruncation":
                throw new SQLException(e.getMessage());
            default:
                throw new UnhandledException(e.getClass().getSimpleName(), e.getMessage());
        }
    }
}
