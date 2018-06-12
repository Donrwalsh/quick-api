package com.perficient.employees.service;

import com.perficient.employees.exception.DatabaseException;
import com.perficient.employees.exception.UnhandledException;
import org.springframework.stereotype.Service;

@Service
public class ExceptionService {

    public void toss(Exception e) {
        switch (e.getClass().getSimpleName()) {
            case "SQLIntegrityConstraintViolationException":
                throw new DatabaseException(e.getMessage());
            default:
                throw new UnhandledException(e.getClass().getSimpleName(), e.getMessage());
        }
    }
}
