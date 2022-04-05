package com.example.rs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
@MapperScan("com.example.rs.dao")
@SpringBootApplication
@EnableScheduling
@ServletComponentScan
public class RsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RsApplication.class, args);
    }

}
