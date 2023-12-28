package com.spring.finaldemo.controller;
import com.spring.finaldemo.entity.Category;
import com.spring.finaldemo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Create a new category
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category savedCategory = categoryRepository.save(category);
        return ResponseEntity.ok(savedCategory);
    }

    // Get all categories
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return ResponseEntity.ok(categories);
    }

    // Get category by ID
    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long categoryId) {
        return categoryRepository.findById(categoryId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update category by ID
    @PutMapping("/{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long categoryId, @RequestBody Category updatedCategory) {
        return categoryRepository.findById(categoryId)
                .map(existingCategory -> {
                    existingCategory.setCategoryName(updatedCategory.getCategoryName());
                    Category savedCategory = categoryRepository.save(existingCategory);
                    return ResponseEntity.ok(savedCategory);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete category by ID
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Object> deleteCategory(@PathVariable Long categoryId) {
        return categoryRepository.findById(categoryId)
                .map(existingCategory -> {
                    categoryRepository.delete(existingCategory);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
