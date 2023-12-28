package com.spring.finaldemo.entity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(name = "product_name")
    private String productName;
    private double price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    // getters and setters


    public Product() {
    }

    public Product(String productName, double price, Category category) {
        this.productName = productName;
        this.price = price;
        this.category = category;
    }

}