package com.codewithduregesh.blog.services.Impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithduregesh.blog.entities.Category;
import com.codewithduregesh.blog.entities.User;
import com.codewithduregesh.blog.exceptions.ResourceNotFoundException;
import com.codewithduregesh.blog.payloads.CategoryDto;
import com.codewithduregesh.blog.payloads.UserDto;
import com.codewithduregesh.blog.repositories.CategoryRepositoryI;
import com.codewithduregesh.blog.services.CategoryServiceI;

@Service
public class CategoryServiceImpl implements CategoryServiceI {

	@Autowired
	private CategoryRepositoryI categoryRepositoryI;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {

		    Category cat = this.modelMapper.map(categoryDto, Category.class);
		    Category addedcat = this.categoryRepositoryI.save(cat);
		
          return this.modelMapper.map(addedcat, CategoryDto.class);
		}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {

		Category cat= this.categoryRepositoryI.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category", "Category id", categoryId));
		
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		
		Category updatecat = this.categoryRepositoryI.save(cat);
		
		return this.modelMapper.map(updatecat, CategoryDto.class);
	}

	@Override
	public void  deleteCategory(Long categoryId) {

	Category cat = this.categoryRepositoryI.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category id", categoryId));
		
	this.categoryRepositoryI.delete(cat);
		
	}

	@Override
	public CategoryDto getCategory(Long categoryId) {

		this.categoryRepositoryI.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category id", categoryId));
		
		return this.modelMapper.map(categoryId, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCategories() {

		List<Category> categories = this.categoryRepositoryI.findAll();
		
		List<CategoryDto> catDtos = categories.stream().map((cat)-> this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		
		return catDtos;
	}

	public CategoryDto categoryToDto(Category category) {

		CategoryDto categoryDto = this.modelMapper.map(category, CategoryDto.class);

		
		return categoryDto;
}
	
	public Category dtoToCategory(CategoryDto categoryDto) {

	Category category = this.modelMapper.map(categoryDto, Category.class);

		
		return category;
}
}
