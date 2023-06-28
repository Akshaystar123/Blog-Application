package com.codewithduregesh.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithduregesh.blog.entities.Category;
import com.codewithduregesh.blog.entities.Post;
import com.codewithduregesh.blog.entities.User;

public interface PostRepositoryI extends JpaRepository<Post, Long>{

	
	List<Post> findAllByUser(User user);
	
	List<Post> findAllByCategory(Category catgory);

	//@Query("select p from Post p where p.title like:key")
	List<Post> searchPostByTitleContaining(String title);
}
