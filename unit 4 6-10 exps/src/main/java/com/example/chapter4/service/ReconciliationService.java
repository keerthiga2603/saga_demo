package com.example.chapter4.service;

import com.example.chapter4.model.Order;
import org.springframework.stereotype.Service;

@Service
public class ReconciliationService {

    // Program 10 - Reconciliation
    public String reconcile(Order order) {

        if ("PAID".equals(order.getStatus())) {
            return "Order is paid but not confirmed. Check inventory workflow.";
        }

        if ("CANCELLED".equals(order.getStatus())) {
            return "Verify that compensation/refund completed.";
        }

        return "Order does not require reconciliation.";
    }
}
