package com.example.rs.service.impl;

import com.example.rs.dao.ResourceLogMapper;
import com.example.rs.entity.ResourceLog;
import com.example.rs.service.inf.ResourceLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ResourceLogServiceImpl implements ResourceLogService {
    @Autowired
    ResourceLogMapper resourceLogMapper;

    @Override
    public List<ResourceLog> getAllResourceLogs() {
        return resourceLogMapper.getAllResourceLogs();
    }

    @Override
    public int insertResourceLog(ResourceLog resourceLog) {
        return resourceLogMapper.insertResourceLog(resourceLog);
    }

    @Override
    public int deleteResourceLog(ResourceLog resourceLog) {
        return resourceLogMapper.deleteResourceLog(resourceLog);
    }
}
