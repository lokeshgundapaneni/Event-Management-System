package com.eventhub.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eventhub.dto.request.CategoryRequest;
import com.eventhub.entity.Category;
import com.eventhub.exception.CategoryNotFoundException;
import com.eventhub.repository.CategoryRepository;

@Service
public class CategoryService {
	
	private final CategoryRepository repo;

	public CategoryService(CategoryRepository repo) {
	    this.repo = repo;
	}
	
//	creating the category
	public Category createCategory(CategoryRequest request)
	{
		Category cat=new Category();
		cat.setName(request.getName());
		cat.setDescription(request.getDescription());
		return repo.save(cat);
	}
	
//	get all the categories
	public List<Category> getAllCategories()
	{
		return repo.findAll();
	}
	
//	get category by id
	public Category getCategoryById(Long id)
	{
		return repo.findById(id).orElseThrow(()->
									new CategoryNotFoundException("Category not found with id : " + id));
	}
	
//	update category
	public Category updateCategory(Long id,CategoryRequest request)
	{
		Category cat = repo.findById(id).orElseThrow(()->
									new CategoryNotFoundException("Category not found with id : " + id));
		cat.setName(request.getName());
		cat.setDescription(request.getDescription());
		return repo.save(cat);
	}
	
//	delete category
	public void deleteCategory(Long id)
	{
		Category cat = repo.findById(id).orElseThrow(() ->
									new CategoryNotFoundException("Category not found with id : " + id));
		repo.delete(cat);
	}
	
	
	

}
