package com.codewithduregesh.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithduregesh.blog.entities.Category;
import com.codewithduregesh.blog.payloads.CategoryDto;

public interface CategoryRepositoryI extends JpaRepository<Category, Long >{



}

