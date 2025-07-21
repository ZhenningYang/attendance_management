package com.example.attendance.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.*;

@Entity
public class Employee {
    @Id
    @NotBlank(message = "工号不能为空")
    private String jobNumber; // 工号作为主键

    @NotBlank(message = "姓名不能为空")
    private String name;

    private int validDays;

    public Employee() {}

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValidDays() {
        return validDays;
    }

    public void setValidDays(int validDays) {
        this.validDays = validDays;
    }
} 