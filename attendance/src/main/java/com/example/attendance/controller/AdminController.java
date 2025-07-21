package com.example.attendance.controller;

import com.example.attendance.entity.AttendanceRecord;
import com.example.attendance.entity.Employee;
import com.example.attendance.service.AttendanceService;
import com.example.attendance.service.EmployeeService;
import com.example.attendance.service.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private SystemConfigService systemConfigService;

    // 获取所有员工列表
    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        return employeeService.getAllEmployees();
    }

    // 添加员工
    @PostMapping("/employee")
    public Employee addEmployee(@RequestBody @Valid Employee emp) {
        return employeeService.addEmployee(emp);
    }

    // 初始化数据
    @PostMapping("/init")
    public String init() {
        return employeeService.init();
    }

    // 管理员查询所有员工某天的考勤数据
    @GetMapping("/attendance/records")
    public List<AttendanceRecord> getAllRecords(@RequestParam(required = false) String date) {
        LocalDate d;
        if (date == null || date.trim().isEmpty()) {
            d = systemConfigService.getCurrentDate();
        } else {
            d = LocalDate.parse(date);
        }
        return attendanceService.getAllRecordsByDate(d);
    }

    // 设置当前考勤日期
    @PostMapping("/set-date")
    public String setDate(@RequestParam @NotBlank(message = "日期不能为空") String date) {
        systemConfigService.setCurrentDate(LocalDate.parse(date));
        return "考勤日期已设置";
    }

    // 获取当前考勤日期
    @GetMapping("/current-date")
    public LocalDate getCurrentDate() {
        return systemConfigService.getCurrentDate();
    }

    // 通过工号获取员工姓名
    @GetMapping("/employee-name")
    public String getEmployeeName(@RequestParam @NotBlank(message = "工号不能为空") String jobNumber) {
        Employee emp = employeeService.getEmployeeByJobNumber(jobNumber);
        return emp != null ? emp.getName() : "";
    }
} 