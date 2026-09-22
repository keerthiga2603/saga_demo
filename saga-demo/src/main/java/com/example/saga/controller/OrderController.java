package com.example.saga.controller;

import com.example.saga.model.Order;
import com.example.saga.service.SagaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * PROGRAM 5 - REST controller.
 *
 * The entry point of the workflow. POST /orders starts the saga and returns
 * the final state of the order (CONFIRMED or CANCELLED).
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private SagaService sagaService;

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return sagaService.placeOrder(order);
    }
}
