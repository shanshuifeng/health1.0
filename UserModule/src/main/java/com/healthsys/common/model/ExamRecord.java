package com.healthsys.common.model;

import java.util.Date;

public class ExamRecord {
    private Long id;
    private Long appointmentId;
    private Long testId;
    private String resultValue;
    private Date examDate;
    private Date createdAt;

    // 构造函数
    public ExamRecord() {}

    public ExamRecord(Long id, Long appointmentId, Long testId, String resultValue, Date examDate) {
        this.id = id;
        this.appointmentId = appointmentId;
        this.testId = testId;
        this.resultValue = resultValue;
        this.examDate = examDate;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public String getResultValue() {
        return resultValue;
    }

    public void setResultValue(String resultValue) {
        this.resultValue = resultValue;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
