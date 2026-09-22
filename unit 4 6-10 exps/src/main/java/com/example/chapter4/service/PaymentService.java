package com.example.chapter4.service;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    // Program 3 - Payment and Compensation
    public boolean makePayment(double amount) {
        if (amount <= 0) {
            return false;
        }

        System.out.println("Payment successful: Rs." + amount);
        return true;
    }

    public void refund(double amount) {
        System.out.println("Payment refunded: Rs." + amount);
    }
}
