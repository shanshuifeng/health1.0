package com.ncu.controller;

public interface PaymentService {
    boolean pay(Long appointmentId, double amount);
}
