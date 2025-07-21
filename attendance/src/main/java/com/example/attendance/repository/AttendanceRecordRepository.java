package com.example.attendance.repository;

import com.example.attendance.entity.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Long> {
    AttendanceRecord findByEmployeeIdAndSignDate(String employeeId, LocalDate signDate);
    List<AttendanceRecord> findByEmployeeId(String employeeId);
    List<AttendanceRecord> findBySignDate(LocalDate signDate);
    long countByEmployeeIdAndSignInTimeIsNotNullAndSignOutTimeIsNotNull(String employeeId);
    Page<AttendanceRecord> findByEmployeeId(String employeeId, Pageable pageable);
} 