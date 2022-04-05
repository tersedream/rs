package com.example.rs.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString(exclude="resources")
@JsonIgnoreProperties(value = "handler")
public class Account {
    /**
     * 账号id
     */
    private Integer id;
    /**
     * 账号
     */
    private String account;
    /**
     * 账号明文密码
     */
    @JsonIgnore
    private String password;
    /**
     * 账号昵称
     */
    private String name;
    /**
     * 账号 cookie sessionId
     * 此字段用于访问 spigotmc.org 资源页面时用于身份认证，这不是已经持久字段，存活时间取决于服务器的设置.
     */
    private String sessionId;
    /**
     * 账号 cookie xfUser
     */
    private String xfUser;
    /**
     * 账户下关联的所有资源
     */
    @JsonIgnore
    private List<Resource> resources;
}
