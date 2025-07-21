package com.example.attendance.repository;

import com.example.attendance.entity.SystemConfig;
import org.springframework.data.jpa.repository.JpaRepository;
 
public interface SystemConfigRepository extends JpaRepository<SystemConfig, Long> {
} 