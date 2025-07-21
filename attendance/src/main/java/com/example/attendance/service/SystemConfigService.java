package com.example.attendance.service;

import com.example.attendance.entity.SystemConfig;
import com.example.attendance.repository.SystemConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SystemConfigService {
    @Autowired
    private SystemConfigRepository systemConfigRepository;

    public LocalDate getCurrentDate() {
        return systemConfigRepository.findById(1L)
                .map(SystemConfig::getCurrentDay)
                .orElse(LocalDate.now());
    }

    public void setCurrentDate(LocalDate date) {
        SystemConfig config = systemConfigRepository.findById(1L).orElse(new SystemConfig());
        config.setCurrentDay(date);
        systemConfigRepository.save(config);
    }
} 