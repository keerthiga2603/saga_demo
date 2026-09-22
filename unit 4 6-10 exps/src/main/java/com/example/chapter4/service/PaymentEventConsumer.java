package com.example.chapter4.service;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PaymentEventConsumer {

    // Program 8 - Simple Idempotency
    private final Set<String> processedEvents = new HashSet<>();

    public synchronized void handle(String eventId, Long orderId) {
        if (processedEvents.contains(eventId)) {
            System.out.println("Duplicate event ignored");
            return;
        }

        System.out.println("Processing payment for order " + orderId);
        processedEvents.add(eventId);
    }
}
