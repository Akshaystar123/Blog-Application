package com.codewithduregesh.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithduregesh.blog.config.AppConstants;
import com.codewithduregesh.blog.payloads.ApiResponse;
import com.codewithduregesh.blog.payloads.CategoryDto;
import com.codewithduregesh.blog.services.CategoryServiceI;


@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryServiceI categoryServiceI;

	private static Logger logger= LoggerFactory.getLogger(CategoryController.class);

	/**
	 * @author Akshay 
	 * @apiNote create category 
	 * @param CategoryDto
	 * @return 
	 */
	
	// create
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		
		logger.info("Initiating service call for create category"+categoryDto);

		CategoryDto createCategory = this.categoryServiceI.createCategory(categoryDto);
		
		logger.info("Completed service call for create category"+categoryDto);

		return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);
	}

	/**
	 * @author Akshay 
	 * @apiNote update category 
	 * @param categoryDto
	 * @return
	 */
	
	// update
	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Long catId) {
		
		logger.info("Initiating service call for update category"+categoryDto);

		CategoryDto updatedCategory = this.categoryServiceI.updateCategory(categoryDto,catId);
		
		logger.info("Completed service call for create category"+categoryDto);

		return new ResponseEntity<CategoryDto>(updatedCategory, HttpStatus.CREATED);
	}
	
	/**
	 * @author Akshay 
	 * @apiNote delete category 
	 * @param catId
	 * @return
	 */
	// delete
	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse> deleteCategory( @PathVariable Long catId) {
		
		logger.info("Initiating service call for delete category"+catId);

		this.categoryServiceI.deleteCategory(catId);
		
		logger.info("Initiating service call for delete category"+catId);

		return new ResponseEntity<ApiResponse>(new ApiResponse(AppConstants.DELETE_CATEGORY, true),HttpStatus.OK);
	}

	/**
	 * @author Akshay 
	 * @apiNote get category 
	 * @param catId
	 * @return
	 */
	
	// get
	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDto> getCategory(@Valid @PathVariable Long catId) {
		
		logger.info("Initiating service call get single category"+catId);

		CategoryDto categoryDto = this.categoryServiceI.getCategory(catId);
		
		logger.info("Completed service call for get single category"+catId);

		return new ResponseEntity<CategoryDto>(categoryDto,HttpStatus.OK);
	}
	
	/**
	 * @author Akshay 
	 * @apiNote get categoryDto 
	 * @return
	 */
	
	// getAll
	@GetMapping("/")
	public ResponseEntity <List<CategoryDto>> getAllCategory() {
		
		logger.info("Initiating service call get of all category");

	 List<CategoryDto> categories = this.categoryServiceI.getCategories();
	 
		logger.info(AppConstants.DELETE_CATEGORY);
	 
		return ResponseEntity.ok(categories);
	}
}
