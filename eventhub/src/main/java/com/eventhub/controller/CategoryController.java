package com.eventhub.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventhub.dto.request.CategoryRequest;
import com.eventhub.entity.Category;
import com.eventhub.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {
	
	private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    
    @PostMapping
    public Category createCategory(@Valid @RequestBody CategoryRequest request)
    {
    	return categoryService.createCategory(request);
    }
    
    @GetMapping
    public List<Category> getAllCategories()
    {
    	return categoryService.getAllCategories();
    }
    
    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id)
    {
    	return categoryService.getCategoryById(id);
    }
    
    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable Long id,@Valid @RequestBody CategoryRequest request)
    {
    	return categoryService.updateCategory(id, request);
    }
    
    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable Long id)
    {
    	categoryService.deleteCategory(id);
    	return "Category deleted successfully";
    }
    
}
