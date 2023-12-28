package com.spring.finaldemo.repository;

import com.spring.finaldemo.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
//    List<Product> findByProductNameContainingIgnoreCase(String productName);

    Page<Product> findByProductNameContainingIgnoreCase(String productName, Pageable pageable);

    Page<Product> findByCategory_CategoryNameContainingIgnoreCase(String categoryName, Pageable pageable);


}
