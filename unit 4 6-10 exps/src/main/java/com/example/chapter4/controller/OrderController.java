package com.example.chapter4.controller;

import com.example.chapter4.model.Order;
import com.example.chapter4.service.SagaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final SagaService sagaService;

    public OrderController(SagaService sagaService) {
        this.sagaService = sagaService;
    }

    // Program 5 - REST Controller
    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return sagaService.placeOrder(order);
    }
}
