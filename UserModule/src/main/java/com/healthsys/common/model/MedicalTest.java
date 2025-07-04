package com.healthsys.common.model;

import java.util.Date;

public class MedicalTest {
    private Long id;
    private String name;
    private String code;
    private String description;
    private String normalRange;
    private Double price;
    private Date createdAt;

    // 构造函数
    public MedicalTest() {
    }

    public MedicalTest(Long id, String name, String code, String description, String normalRange, Double price, Date createdAt) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.description = description;
        this.normalRange = normalRange;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNormalRange() {
        return normalRange;
    }

    public void setNormalRange(String normalRange) {
        this.normalRange = normalRange;
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
