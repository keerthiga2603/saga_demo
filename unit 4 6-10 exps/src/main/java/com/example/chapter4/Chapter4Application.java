package com.example.chapter4;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.chapter4.model.Product;
import com.example.chapter4.repository.ProductRepository;

@SpringBootApplication
public class Chapter4Application {

    public static void main(String[] args) {
        SpringApplication.run(Chapter4Application.class, args);
    }

    @Bean
    CommandLineRunner seedData(ProductRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                Product product = new Product();
                product.setId(101L);
                product.setName("Laptop");
                product.setStock(10);
                repository.save(product);
            }
        };
    }
}
