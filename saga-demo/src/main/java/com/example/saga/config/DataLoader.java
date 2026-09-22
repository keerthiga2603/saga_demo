package com.example.saga.config;

import com.example.saga.model.Product;
import com.example.saga.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Seeds two products when the application starts, so the demo has
 * stock to reserve. Product 101 starts with 10 units.
 */
@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner seedProducts(ProductRepository productRepository) {
        return args -> {
            if (productRepository.count() == 0) {
                productRepository.save(newProduct(101L, "Wireless Mouse", 10));
                productRepository.save(newProduct(102L, "Mechanical Keyboard", 10));
            }
        };
    }

    private Product newProduct(Long id, String name, int stock) {
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setStock(stock);
        return product;
    }
}
