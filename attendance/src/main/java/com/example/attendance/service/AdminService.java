package com.example.attendance.service;

import com.example.attendance.entity.Employee;
import com.example.attendance.repository.EmployeeRepository;
import com.example.attendance.repository.AttendanceRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private AttendanceRecordRepository attendanceRecordRepository;
    @Autowired
    private org.springframework.jdbc.core.JdbcTemplate jdbcTemplate;
    // 批量刷新所有员工的有效打卡天数
    @Autowired
    private AttendanceService attendanceService;

    public void initData() {
        // 清空表
        attendanceRecordRepository.deleteAll();
        employeeRepository.deleteAll();

        // 重置自增主键
        jdbcTemplate.execute("ALTER TABLE employee AUTO_INCREMENT = 1");
        jdbcTemplate.execute("ALTER TABLE attendance_record AUTO_INCREMENT = 1");
    }

    public void refreshAllValidDays() {
        for (Employee employee : employeeRepository.findAll()) {
            attendanceService.refreshValidDays(employee.getJobNumber());
        }
    }
} 