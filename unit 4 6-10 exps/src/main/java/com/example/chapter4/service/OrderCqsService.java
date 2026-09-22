package com.example.chapter4.service;

import com.example.chapter4.model.Order;
import com.example.chapter4.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderCqsService {

    private final OrderRepository repository;

    public OrderCqsService(OrderRepository repository) {
        this.repository = repository;
    }

    // Program 6 - CQS Command
    public Order createOrder(Order order) {
        order.setStatus("CREATED");
        return repository.save(order);
    }

    // Program 6 - CQS Query
    public Order getOrder(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
    }
}
