package com.example.chapter4.controller;

import com.example.chapter4.model.Order;
import com.example.chapter4.service.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chapter4")
public class Chapter4Controller {

    private final OrderService orderService;
    private final OrderCqsService cqsService;
    private final OrderCommandService commandService;
    private final OrderQueryService queryService;
    private final PaymentEventConsumer eventConsumer;
    private final RetryService retryService;
    private final ReconciliationService reconciliationService;

    public Chapter4Controller(OrderService orderService,
                              OrderCqsService cqsService,
                              OrderCommandService commandService,
                              OrderQueryService queryService,
                              PaymentEventConsumer eventConsumer,
                              RetryService retryService,
                              ReconciliationService reconciliationService) {
        this.orderService = orderService;
        this.cqsService = cqsService;
        this.commandService = commandService;
        this.queryService = queryService;
        this.eventConsumer = eventConsumer;
        this.retryService = retryService;
        this.reconciliationService = reconciliationService;
    }

    @PostMapping("/acid/orders")
    public Order localAcid(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }

    @PostMapping("/cqs/orders")
    public Order cqsCommand(@RequestBody Order order) {
        return cqsService.createOrder(order);
    }

    @GetMapping("/cqs/orders/{id}")
    public Order cqsQuery(@PathVariable Long id) {
        return cqsService.getOrder(id);
    }

    @PostMapping("/cqrs/orders")
    public Order cqrsCommand(@RequestBody Order order) {
        return commandService.create(order);
    }

    @GetMapping("/cqrs/orders/{id}")
    public Order cqrsQuery(@PathVariable Long id) {
        return queryService.findById(id);
    }

    @GetMapping("/cqrs/orders")
    public List<Order> allOrders() {
        return queryService.findAll();
    }

    @PostMapping("/idempotency")
    public String idempotency(@RequestParam String eventId,
                              @RequestParam Long orderId) {
        eventConsumer.handle(eventId, orderId);
        return "Event handled. Check console for duplicate-processing behavior.";
    }

    @PostMapping("/retry")
    public String retry(@RequestParam Long productId,
                         @RequestParam int quantity) {
        retryService.updateInventoryWithRetry(productId, quantity);
        return "Retry process completed. Check console for attempts.";
    }

    @PostMapping("/reconcile")
    public String reconcile(@RequestBody Order order) {
        return reconciliationService.reconcile(order);
    }
}
