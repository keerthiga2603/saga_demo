package com.example.chapter4.service;

import com.example.chapter4.model.Order;
import com.example.chapter4.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class SagaService {

    private final OrderRepository orderRepository;
    private final InventoryService inventoryService;
    private final PaymentService paymentService;

    public SagaService(OrderRepository orderRepository,
                       InventoryService inventoryService,
                       PaymentService paymentService) {
        this.orderRepository = orderRepository;
        this.inventoryService = inventoryService;
        this.paymentService = paymentService;
    }

    // Program 4 - Saga Orchestration
    public Order placeOrder(Order order) {

        // Step 1 - Create order
        order.setStatus("CREATED");
        orderRepository.save(order);

        // Step 2 - Payment
        boolean paid = paymentService.makePayment(order.getAmount());
        if (!paid) {
            order.setStatus("PAYMENT_FAILED");
            return orderRepository.save(order);
        }

        order.setStatus("PAID");
        orderRepository.save(order);

        // Step 3 - Reserve inventory
        boolean reserved = inventoryService.reserveStock(
                order.getProductId(), order.getQuantity());

        if (!reserved) {
            // Compensation
            paymentService.refund(order.getAmount());
            order.setStatus("CANCELLED");
            return orderRepository.save(order);
        }

        // Step 4 - Confirm order
        order.setStatus("CONFIRMED");
        return orderRepository.save(order);
    }
}
