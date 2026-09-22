package com.example.chapter4.service;

import com.example.chapter4.model.Order;
import com.example.chapter4.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderCommandService {

    private final OrderRepository repository;

    public OrderCommandService(OrderRepository repository) {
        this.repository = repository;
    }

    // Program 7 - CQRS write side
    public Order create(Order order) {
        order.setStatus("CREATED");
        return repository.save(order);
    }
}
