package com.example.chapter4.service;

import com.example.chapter4.model.Order;
import com.example.chapter4.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderQueryService {

    private final OrderRepository repository;

    public OrderQueryService(OrderRepository repository) {
        this.repository = repository;
    }

    // Program 7 - CQRS read side
    public Order findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
    }

    public List<Order> findAll() {
        return repository.findAll();
    }
}
