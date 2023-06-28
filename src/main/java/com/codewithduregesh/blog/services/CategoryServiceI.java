package com.codewithduregesh.blog.services;

import java.util.List;

import com.codewithduregesh.blog.payloads.CategoryDto;

public interface CategoryServiceI {

	// create
	public CategoryDto createCategory(CategoryDto categoryDto);

	// update
	public CategoryDto updateCategory(CategoryDto categoryDto,Long categoryId);

	// delete
	public void deleteCategory(Long catId);

	// get
	public CategoryDto getCategory(Long categoryId);

	// getAll
	List<CategoryDto> getCategories();

}
