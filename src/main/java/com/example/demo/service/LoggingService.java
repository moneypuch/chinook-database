package com.example.demo.service;

import com.example.demo.entity.ActivityLog;
import com.example.demo.repository.ActivityLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LoggingService {

    @Autowired
    private ActivityLogRepository activityLogRepository;

    public void logActivity(String userId, String action) {
        ActivityLog log = new ActivityLog();
        log.setUserId(userId);
        log.setAction(action);
        log.setTimestamp(LocalDateTime.now());
        activityLogRepository.save(log);
    }
}