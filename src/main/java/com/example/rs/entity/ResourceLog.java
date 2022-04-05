package com.example.rs.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = "resource")
@JsonIgnoreProperties(value = "handler")
public class ResourceLog {
    private Integer id;
    private Resource resource;
    private Integer versionNumber;
    private String path;
    private String htmlInfo;
    private String infoTitle;
    private Integer currVersion;
    private String date;
}
