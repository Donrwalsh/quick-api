package com.perficient.JDBC.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.time.LocalDate;

public class Employee {

    private int emp_no;
    @NotNull
    private LocalDate birth_date;
    @NotNull
    @NotEmpty
    private String first_name;
    @NotNull
    @NotEmpty
    private String last_name;
    @NotNull
    @NotEmpty
    private String gender;
    @NotNull
    private LocalDate hire_date;

    public Employee(int emp_no, LocalDate birth_date, String first_name, String last_name, String gender, LocalDate hire_date) {
        this.emp_no = emp_no;
        this.birth_date = birth_date;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.hire_date = hire_date;
    }

    public Employee() {}

    @JsonCreator
    public Employee(LocalDate birth_date, String first_name, String last_name, String gender, LocalDate hire_date) {
        this.birth_date = birth_date;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.hire_date = hire_date;
    }

    public int getEmpNo() { return emp_no; }
    public LocalDate getBirthDate() { return birth_date; }
    public String getFirstName() { return first_name; }
    public String getLastName() { return last_name; }
    public String getGender() { return gender; }
    public LocalDate getHireDate() { return hire_date; }

    public void setEmp_no(int emp_no) {
        this.emp_no = emp_no;
    }
}
