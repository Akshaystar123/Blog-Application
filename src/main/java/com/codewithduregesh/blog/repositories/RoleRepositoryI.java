package com.codewithduregesh.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithduregesh.blog.entities.Role;

public interface RoleRepositoryI extends JpaRepository<Role, Long>{

	
}
