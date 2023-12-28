package com.spring.finaldemo.entity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "category")
public class Category {

    @Id
    @Column(name = "category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(name = "category_name")
    private String categoryName;

    // getters and setters


    public Category() {
    }

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

}