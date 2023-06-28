package com.codewithduregesh.blog.services;

import java.util.List;

import com.codewithduregesh.blog.entities.Category;
import com.codewithduregesh.blog.entities.Post;
import com.codewithduregesh.blog.entities.User;
import com.codewithduregesh.blog.payloads.PostDto;
import com.codewithduregesh.blog.payloads.PostResponse;

public interface PostServiceI {

	// create
	PostDto createPost(PostDto postDto, Long userId, Long categoryId);

	// update
	PostDto updatePost(PostDto postDto, Long postId);

	// delete
	void deletePost(Long postId);

	// get all Post
	PostResponse getAllPost(int pageNumber, int pageSize, String sortBy, String sortDir);

	// get single post
	PostDto getPostById(Long postId);

	// get all post of category
	List<PostDto> getAllPostByCategory(Long categoryId);

	// get all post of user
	List<PostDto> getPostByUser(Long userId);

	// search post
	List<PostDto> searchPost(String keyword);

}
