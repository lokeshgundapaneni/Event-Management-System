package com.eventhub.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eventhub.dto.request.CategoryRequest;
import com.eventhub.dto.response.CategoryResponse;
import com.eventhub.entity.Category;
import com.eventhub.exception.CategoryNotFoundException;
import com.eventhub.repository.CategoryRepository;

@Service
public class CategoryService {
	
	private final CategoryRepository repo;

	public CategoryService(CategoryRepository repo) {
	    this.repo = repo;
	}
	
	
	public CategoryResponse mapToResponse(Category category)
	{
		return new CategoryResponse(
				category.getId(),category.getName(),category.getDescription(),category.getIcon());
	}
	
//	creating the category
	public CategoryResponse createCategory(CategoryRequest request)
	{
		Category cat=new Category();
		cat.setName(request.getName());
		cat.setDescription(request.getDescription());
		cat.setIcon(request.getIcon());
		Category savedCategory= repo.save(cat);
		return mapToResponse(savedCategory);
	}
	
//	get all the categories
	public List<CategoryResponse> getAllCategories()
	{
		return repo.findAll()
	               .stream()
	               .map(this::mapToResponse)
	               .toList();
	}
	
//	get category by id
	public CategoryResponse getCategoryById(Long id)
	{
		Category cat= repo.findById(id).orElseThrow(()->
									new CategoryNotFoundException("Category not found with id : " + id));
		return mapToResponse(cat);
	}
	
//	update category
	public CategoryResponse updateCategory(Long id,CategoryRequest request)
	{
		Category cat = repo.findById(id).orElseThrow(()->
									new CategoryNotFoundException("Category not found with id : " + id));
		cat.setName(request.getName());
		cat.setDescription(request.getDescription());
		cat.setIcon(request.getIcon());
		Category updatedCategory = repo.save(cat);
		return mapToResponse(updatedCategory);
	}
	
//	delete category
	public void deleteCategory(Long id)
	{
		Category cat = repo.findById(id).orElseThrow(() ->
									new CategoryNotFoundException("Category not found with id : " + id));
		repo.delete(cat);
	}
	
	
	

}
