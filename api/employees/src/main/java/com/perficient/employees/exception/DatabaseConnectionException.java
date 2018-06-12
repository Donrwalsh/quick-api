package com.perficient.employees.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DatabaseConnectionException extends RuntimeException {

    public DatabaseConnectionException(String msg) { super("Unable to connect to database:" + msg); }
}
