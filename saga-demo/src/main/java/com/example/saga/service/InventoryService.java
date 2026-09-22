package com.example.saga.service;

import com.example.saga.model.Product;
import com.example.saga.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * PROGRAM 2 - Business invariant.
 *
 * Invariant: stock must never become negative.
 * This service owns the product data, so it is the only place the rule
 * is enforced. reserveStock() refuses rather than allowing a negative value.
 */
@Service
public class InventoryService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public boolean reserveStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId).orElseThrow();

        if (product.getStock() < quantity) {
            return false;
        }

        product.setStock(product.getStock() - quantity);
        productRepository.save(product);
        return true;
    }

    @Transactional
    public void restoreStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId).orElseThrow();
        product.setStock(product.getStock() + quantity);
        productRepository.save(product);
    }
}
