package com.perficient.JDBC.controller;

import com.perficient.JDBC.dao.EmployeeDAO;
import com.perficient.JDBC.exception.DatabaseException;
import com.perficient.JDBC.exception.EmployeeNotFoundException;
import com.perficient.JDBC.model.Employee;
import com.perficient.JDBC.service.ExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="sanity")
public class SanityController {


    @Autowired
    private ExceptionService exceptionService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    void check() throws Exception {}
}