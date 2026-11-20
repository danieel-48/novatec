package com.novatec.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name="product")
public class Product {
    @Id 
    @Column(name = "product_id", length = 6)
    private String productId;
    
    @Column (nullable = false, length = 100)
    private String name;

    @Column (nullable = false, length = 10)
    private String type;

    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
