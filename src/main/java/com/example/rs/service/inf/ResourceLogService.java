package com.example.rs.service.inf;

import com.example.rs.entity.ResourceLog;

import java.util.List;

public interface ResourceLogService {
    List<ResourceLog> getAllResourceLogs();
    int insertResourceLog(ResourceLog resourceLog);
    int deleteResourceLog(ResourceLog resourceLog);
}
