package com.example.rs.dao;

import com.example.rs.entity.ResourceLog;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ResourceLogMapper {
    List<ResourceLog> getAllResourceLogs();
    int insertResourceLog(ResourceLog resourceLog);
    int deleteResourceLog(ResourceLog resourceLog);
}
