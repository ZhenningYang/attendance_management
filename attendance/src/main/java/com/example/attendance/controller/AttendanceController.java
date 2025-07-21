package com.example.attendance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.attendance.service.AttendanceService;
import com.example.attendance.service.SystemConfigService;
import com.example.attendance.entity.AttendanceRecord;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import javax.validation.constraints.*;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private SystemConfigService systemConfigService;

    // 签到
    @PostMapping("/sign-in")
    public String signIn(@RequestParam @NotBlank(message = "工号不能为空") String jobNumber) {
        LocalDate date = systemConfigService.getCurrentDate();
        return attendanceService.signIn(jobNumber, date);
    }

    // 签退
    @PostMapping("/sign-out")
    public String signOut(@RequestParam @NotBlank(message = "工号不能为空") String jobNumber) {
        LocalDate date = systemConfigService.getCurrentDate();
        return attendanceService.signOut(jobNumber, date);
    }

    // 查询某员工所有考勤记录
    @GetMapping("/records")
    public List<AttendanceRecord> getRecordsByEmployee(@RequestParam @NotBlank(message = "工号不能为空") String jobNumber) {
        return attendanceService.getRecordsByEmployee(jobNumber);
    }

    // 查询某天所有考勤记录
    @GetMapping("/records-by-date")
    public List<AttendanceRecord> getRecordsByDate(@RequestParam String date) {
        return attendanceService.getRecordsByDate(LocalDate.parse(date));
    }

    // 统计有效打卡天数
    @GetMapping("/valid-days")
    public long countValidDays(@RequestParam @NotBlank(message = "工号不能为空") String jobNumber) {
        return attendanceService.countValidDays(jobNumber);
    }

    // 分页查询某员工考勤明细
    @GetMapping("/records/page")
    public Page<AttendanceRecord> getRecordsByEmployeePage(
        @RequestParam @NotBlank(message = "工号不能为空") String jobNumber,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return attendanceService.getRecordsByEmployeePage(jobNumber, pageable);
    }
} 