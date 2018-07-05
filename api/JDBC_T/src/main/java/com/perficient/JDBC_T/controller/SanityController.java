package com.perficient.JDBC_T.controller;

import com.perficient.JDBC_T.service.ExceptionService;
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