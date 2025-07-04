package com.healthsys.common.model;

import java.util.Date;

public class Appointment {
    private Long id;
    private Long userId;
    private Long packageId;
    private Date appointmentTime;
    private Date examTime;
    private String status;  // PENDING, COMPLETED, CANCELLED
    private boolean paymentStatus; // false=未支付, true=已支付

    public Appointment(Long userId, Long packageId, Date appointmentTime) {
        this.userId = userId;
        this.packageId = packageId;
        this.appointmentTime = appointmentTime;
        this.status = "PENDING";
        this.paymentStatus = false;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public Long getPackageId() { return packageId; }
    public Date getAppointmentTime() { return appointmentTime; }
    public Date getExamTime() { return examTime; }
    public String getStatus() { return status; }
    public boolean isPaymentStatus() { return paymentStatus; }

    public void setId(Long id) { this.id = id; }
    public void setExamTime(Date examTime) { this.examTime = examTime; }
    public void setStatus(String status) { this.status = status; }
    public void setPaymentStatus(boolean paymentStatus) { this.paymentStatus = paymentStatus; }

    public String getStatusDisplay() {
        return switch(status) {
            case "PENDING" -> "待检查";
            case "COMPLETED" -> "已完成";
            case "CANCELLED" -> "已取消";
            default -> status;
        };
    }

    public String getPaymentStatusDisplay() {
        return paymentStatus ? "已支付" : "未支付";
    }
}