package com.example.chapter4.service;

import com.example.chapter4.model.Order;
import com.example.chapter4.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // Program 1 - Local ACID Transaction
    @Transactional
    public Order saveOrder(Order order) {
        order.setStatus("CREATED");
        return orderRepository.save(order);
    }
}
