package com.example.chapter4.service;

import com.example.chapter4.model.Product;
import com.example.chapter4.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {

    private final ProductRepository productRepository;

    public InventoryService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Program 2 - Business Invariant
    // Stock must never become negative.
    @Transactional
    public boolean reserveStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));

        if (quantity <= 0 || product.getStock() < quantity) {
            return false;
        }

        product.setStock(product.getStock() - quantity);
        productRepository.save(product);
        return true;
    }

    @Transactional
    public void restoreStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));

        product.setStock(product.getStock() + quantity);
        productRepository.save(product);
    }
}
