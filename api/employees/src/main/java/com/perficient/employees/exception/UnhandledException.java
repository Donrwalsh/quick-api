package com.perficient.employees.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
public class UnhandledException extends RuntimeException {

    public UnhandledException(String error) {
        super("Encountered an exception that is not handled: " + error);
    }
}
