package com.example.saga.service;

import com.example.saga.model.Order;
import com.example.saga.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * PROGRAM 1 - Local ACID transaction.
 *
 * Everything inside saveOrder() runs under one transaction manager, so the
 * work either commits together or rolls back together.
 */
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public Order saveOrder(Order order) {
        order.setStatus("CREATED");
        return orderRepository.save(order);
    }
}
