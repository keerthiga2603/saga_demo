package com.example.saga.service;

import com.example.saga.model.Order;
import com.example.saga.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * PROGRAM 4 - Saga orchestration.
 *
 * Flow: Create Order -> Make Payment -> Reserve Inventory -> Confirm Order.
 * Each step is its own local transaction. There is no distributed
 * transaction across them, so a failure at step 3 is handled by
 * compensating step 2 (refund) and cancelling the order.
 */
@Service
public class SagaService {

    @Autowired private OrderRepository orderRepository;
    @Autowired private InventoryService inventoryService;
    @Autowired private PaymentService paymentService;

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

        // Step 3 - Reserve inventory (business invariant is checked here)
        boolean reserved = inventoryService.reserveStock(
                order.getProductId(), order.getQuantity());

        if (!reserved) {
            // Compensation - undo the payment, then cancel the order
            paymentService.refund(order.getAmount());
            order.setStatus("CANCELLED");
            return orderRepository.save(order);
        }

        // Step 4 - Confirm order
        order.setStatus("CONFIRMED");
        return orderRepository.save(order);
    }
}
