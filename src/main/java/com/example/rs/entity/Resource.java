package com.example.rs.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = "account")
@JsonIgnoreProperties(value = "handler")
public class Resource {
    /**
     * 资源id
     */
    private Integer id;
    /**
     * 资源所依赖的账号
     */
    private Account account;
    /**
     * 资源的 url 地址页面
     */
    private String url;
    /**
     * 资源的名字
     */
    private String name;
    /**
     * 当前版本
     */
    private String currVersion;
    /**
     * 版本号
     */
    private Integer versionNumber;
    /**
     * 当前版本的发布时间
     */
    private String date;
    /**
     * 资源本地化名称
     */
    private String localName;
    /**
     * 资源更新信息标题
     */
    private String infoTitle;
    /**
     * 资源更新信息id
     */
    private String updateInfoNumber;
    /**
     * 资源路径
     */
    private String filePath;
    /**
     * 资源更新 html 信息
     */
    private String htmlInfo;
}
