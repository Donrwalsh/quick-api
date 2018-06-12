package com.perficient.employees.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

public class Employee {

    private int emp_no;
    @NotNull
    private Date birth_date;
    @NotNull
    private String first_name;
    @NotNull
    private String last_name;
    @NotNull
    private String gender;
    @NotNull
    private Date hire_date;

    public Employee(int emp_no, Date birth_date, String first_name, String last_name, String gender, Date hire_date) {
        this.emp_no = emp_no;
        this.birth_date = birth_date;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.hire_date = hire_date;
    }

    public int getEmpNo() { return emp_no; }
    public Date getBirthDate() { return birth_date; }
    public String getFirstName() { return first_name; }
    public String getLastName() { return last_name; }
    public String getGender() { return gender; }
    public Date getHireDate() { return hire_date; }

    public void setEmp_no(int emp_no) {
        this.emp_no = emp_no;
    }
}
