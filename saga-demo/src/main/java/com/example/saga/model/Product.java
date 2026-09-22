package com.example.saga.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * Product entity (Section 3 of the chapter document).
 * Stock is the value protected by the business invariant in Program 2.
 */
@Entity
public class Product {

    @Id
    private Long id;
    private String name;
    private int stock;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}
