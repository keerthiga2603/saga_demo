package com.example.saga.controller;

import com.example.saga.model.Order;
import com.example.saga.model.Product;
import com.example.saga.repository.OrderRepository;
import com.example.saga.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Read-only endpoints used by the web page (index.html).
 *
 * These are NOT part of Programs 1-5. They are kept in a separate controller
 * so that OrderController stays exactly as written in the chapter document.
 * Only queries live here, which also matches the CQS idea: commands change
 * state, queries just read it.
 */
@RestController
public class ViewController {

    @Autowired private OrderRepository orderRepository;
    @Autowired private ProductRepository productRepository;

    @GetMapping("/orders")
    public List<Order> allOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/products")
    public List<Product> allProducts() {
        return productRepository.findAll();
    }

    /** Puts the demo data back to its starting values. */
    @PostMapping("/reset")
    public List<Product> reset() {
        orderRepository.deleteAll();
        productRepository.findAll().forEach(p -> {
            p.setStock(10);
            productRepository.save(p);
        });
        return productRepository.findAll();
    }
}
