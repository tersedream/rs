package com.example.rs.control;

import com.example.rs.entity.Resource;
import com.example.rs.service.inf.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestControl {
    @Autowired
    ResourceService resourceService;

    @GetMapping("/a")
    public List<Resource> a() {
        return resourceService.getAllResources();
    }
    @GetMapping("/b")
    public String b() {
        System.err.println("进入了个人信息中心");
        return "个人信息中心";
    }
    @GetMapping("/c")
    public String c() {
        System.err.println("进入了订单信息管理d");
        return "订单信息管理";
    }
}
