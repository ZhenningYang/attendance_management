package com.example.attendance.service;

import com.example.attendance.entity.Employee;
import com.example.attendance.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(String jobNumber) {
        return employeeRepository.findById(jobNumber);
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(String jobNumber) {
        employeeRepository.deleteById(jobNumber);
    }

    // 恢复添加员工方法
    public Employee addEmployee(Employee employee) {
        // 可根据实际需求添加唯一性校验
        return employeeRepository.save(employee);
    }

    // 恢复初始化数据方法
    public String init() {
        employeeRepository.deleteAll();
        return "数据已清空";
    }

    // 通过工号获取员工信息
    public Employee getEmployeeByJobNumber(String jobNumber) {
        return employeeRepository.findById(jobNumber).orElse(null);
    }
} 