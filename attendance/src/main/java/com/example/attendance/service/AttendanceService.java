package com.example.attendance.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.attendance.repository.AttendanceRecordRepository;
import com.example.attendance.repository.EmployeeRepository;
import com.example.attendance.entity.AttendanceRecord;
import com.example.attendance.entity.Employee;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class AttendanceService {
    @Autowired
    private AttendanceRecordRepository attendanceRecordRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    // 签到（支持指定日期）
    public String signIn(String jobNumber, LocalDate date) {
        Employee emp = employeeRepository.findById(jobNumber).orElse(null);
        if (emp == null) {
            return "员工不存在";
        }
        AttendanceRecord record = attendanceRecordRepository.findByEmployeeIdAndSignDate(jobNumber, date);
        if (record == null) {
            record = new AttendanceRecord();
            record.setEmployeeId(jobNumber);
            record.setSignDate(date);
            record.setSignInTime(LocalDateTime.of(date, LocalTime.now()));
            attendanceRecordRepository.save(record);
        } else if (record.getSignInTime() == null) {
            record.setSignInTime(LocalDateTime.of(date, LocalTime.now()));
            attendanceRecordRepository.save(record);
        } else {
            return "今天已签到";
        }
        updateValidDays(jobNumber);
        return "签到成功";
    }
    // 签到（默认当天，兼容老接口）
    public String signIn(String jobNumber) {
        return signIn(jobNumber, LocalDate.now());
    }

    // 签退（支持指定日期）
    public String signOut(String jobNumber, LocalDate date) {
        Employee emp = employeeRepository.findById(jobNumber).orElse(null);
        if (emp == null) {
            return "员工不存在";
        }
        AttendanceRecord record = attendanceRecordRepository.findByEmployeeIdAndSignDate(jobNumber, date);
        if (record == null || record.getSignInTime() == null) {
            return "未签到不能签退";
        } else if (record.getSignOutTime() == null) {
            record.setSignOutTime(LocalDateTime.of(date, LocalTime.now()));
            attendanceRecordRepository.save(record);
        } else {
            return "今天已签退";
        }
        updateValidDays(jobNumber);
        return "签退成功";
    }
    // 签退（默认当天，兼容老接口）
    public String signOut(String jobNumber) {
        return signOut(jobNumber, LocalDate.now());
    }

    // 查询某员工所有考勤记录
    public List<AttendanceRecord> getRecordsByEmployee(String jobNumber) {
        return attendanceRecordRepository.findByEmployeeId(jobNumber);
    }

    // 查询某天所有考勤记录
    public List<AttendanceRecord> getRecordsByDate(LocalDate date) {
        return attendanceRecordRepository.findBySignDate(date);
    }

    // 查询某天所有员工的考勤记录（管理员用）
    public List<AttendanceRecord> getAllRecordsByDate(LocalDate date) {
        return attendanceRecordRepository.findBySignDate(date);
    }

    // 统计有效打卡天数
    public long countValidDays(String jobNumber) {
        return attendanceRecordRepository.countByEmployeeIdAndSignInTimeIsNotNullAndSignOutTimeIsNotNull(jobNumber);
    }

    // 更新有效打卡天数
    private void updateValidDays(String jobNumber) {
        long validDays = attendanceRecordRepository.countByEmployeeIdAndSignInTimeIsNotNullAndSignOutTimeIsNotNull(jobNumber);
        Employee employee = employeeRepository.findById(jobNumber).orElse(null);
        if (employee != null) {
            employee.setValidDays((int) validDays);
            employeeRepository.save(employee);
        }
    }

    // 公共方法：刷新指定员工的有效打卡天数
    public void refreshValidDays(String jobNumber) {
        updateValidDays(jobNumber);
    }

    public Page<AttendanceRecord> getRecordsByEmployeePage(String jobNumber, Pageable pageable) {
        return attendanceRecordRepository.findByEmployeeId(jobNumber, pageable);
    }
} 