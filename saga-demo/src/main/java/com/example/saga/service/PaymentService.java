package com.example.saga.service;

import org.springframework.stereotype.Service;

/**
 * PROGRAM 3 - Payment and compensation.
 *
 * makePayment() is the forward action. refund() is its compensating action:
 * a committed payment cannot be rolled back, so it is undone by a new
 * business operation that reverses the effect.
 */
@Service
public class PaymentService {

    public boolean makePayment(double amount) {
        System.out.println("Payment successful: Rs." + amount);
        return true;
    }

    public void refund(double amount) {
        System.out.println("Payment refunded: Rs." + amount);
    }
}
