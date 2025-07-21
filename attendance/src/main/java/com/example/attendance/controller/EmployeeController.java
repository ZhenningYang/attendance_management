package com.example.attendance.controller;

import com.example.attendance.entity.Employee;
import com.example.attendance.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{jobNumber}")
    public Optional<Employee> getEmployeeById(@PathVariable String jobNumber) {
        return employeeService.getEmployeeById(jobNumber);
    }

    @PostMapping
    public Employee saveEmployee(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    @DeleteMapping("/{jobNumber}")
    public void deleteEmployee(@PathVariable String jobNumber) {
        employeeService.deleteEmployee(jobNumber);
    }
}