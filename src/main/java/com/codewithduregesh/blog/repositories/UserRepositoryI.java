package com.codewithduregesh.blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithduregesh.blog.entities.User;


public interface UserRepositoryI extends JpaRepository<User, Long>{

	
	Optional<User> findByEmail(String email);
}
