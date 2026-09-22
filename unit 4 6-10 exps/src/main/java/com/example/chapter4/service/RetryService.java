package com.example.chapter4.service;

import org.springframework.stereotype.Service;

@Service
public class RetryService {

    private final InventoryService inventoryService;

    public RetryService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    // Program 9 - Simple Retry
    public void updateInventoryWithRetry(Long productId, int quantity) {
        int attempts = 0;

        while (attempts < 3) {
            try {
                boolean updated = inventoryService.reserveStock(productId, quantity);

                if (!updated) {
                    throw new IllegalStateException("Inventory reservation failed");
                }

                System.out.println("Inventory updated");
                return;

            } catch (Exception e) {
                attempts++;
                System.out.println("Retry attempt: " + attempts);
            }
        }

        System.out.println("Inventory update failed after retries");
    }
}
