package com.example.rs.service.inf;

import com.example.rs.entity.Resource;

import java.util.List;

public interface ResourceService {
    List<Resource> getAllResources();
    int updateResource(Resource resource);
    int deleteResource(Resource resource);
    List<Resource> getResourceByAccountId(String id);
    Resource getResourceById(String id);
}
