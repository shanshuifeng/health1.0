package com.healthsys.common.model;

import java.util.Date;

public class User {
    private int id;
    private String phone;
    private String password;
    private String name;
    private Date birthDate;
    private String gender;
    private String role;
    private String idNumber;
    private Date createdAt;
    private Date updatedAt;
    private boolean firstLogin;

    // 构造函数
    public User(int id, String phone, String password, String name, Date birthDate,
                String gender, String role, String idNumber, boolean firstLogin) {
        this.id = id;
        this.phone = phone;
        this.password = password;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.role = role;
        this.idNumber = idNumber;
        this.firstLogin = firstLogin;
    }

    public User() {
        // 初始化默认值
        this.firstLogin = true; // 默认首次登录
        this.createdAt = new Date(); // 默认当前时间
        this.updatedAt = new Date(); // 默认当前时间
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; } // 修改为 int 类型以匹配字段类型

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Date getBirthDate() { return birthDate; }
    public void setBirthDate(Date birthDate) { this.birthDate = birthDate; } // 修改为 Date 类型以匹配字段类型

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getIdNumber() { return idNumber; }
    public void setIdNumber(String idNumber) { this.idNumber = idNumber; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

    public boolean isFirstLogin() { return firstLogin; }
    public void setFirstLogin(boolean firstLogin) { this.firstLogin = firstLogin; }
}
