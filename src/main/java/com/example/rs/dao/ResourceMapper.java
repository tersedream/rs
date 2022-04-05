package com.example.rs.dao;

import com.example.rs.entity.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ResourceMapper {
    List<Resource> getAllResources();
    List<Resource> getResourcesByAccountId(String id);
    Resource getResourceById(String id);
    int updateResource(Resource resource);
}
