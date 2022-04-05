package com.example.rs.control;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestControl {
    @GetMapping("/a")
    public String a() {
        System.err.println("进入了购物车");
        return "购物车";
    }
    @GetMapping("/b")
    public String b() {
        System.err.println("进入了个人信息中心");
        return "个人信息中心";
    }
    @GetMapping("/c")
    public String c() {
        System.err.println("进入了订单信息管理");
        return "订单信息管理";
    }
}
