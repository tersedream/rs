package com.example.rs.service.impl;

import com.example.rs.dao.ResourceMapper;
import com.example.rs.entity.Resource;
import com.example.rs.service.inf.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    ResourceMapper resourceMapper;
    @Override
    public List<Resource> getAllResources() {
        return resourceMapper.getAllResources();
    }

    @Override
    public int updateResource(Resource resource) {
        return resourceMapper.updateResource(resource);
    }

    @Override
    public int deleteResource(Resource resource) {
        return 0;
    }

    @Override
    public List<Resource> getResourceByAccountId(String id) {
        return resourceMapper.getResourcesByAccountId(id);
    }

    @Override
    public Resource getResourceById(String id) {
        return resourceMapper.getResourceById(id);
    }
}
