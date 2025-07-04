package com.healthsys.common.model;

import java.util.Date;

public class TestPackage {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Date createdAt;

    // 构造函数
    public TestPackage() {
    }

    public TestPackage(Long id, String name, String description, Double price, Date createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
