package com.codewithduregesh.blog.services.Impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.codewithduregesh.blog.entities.Category;
import com.codewithduregesh.blog.entities.Post;
import com.codewithduregesh.blog.entities.User;
import com.codewithduregesh.blog.exceptions.ResourceNotFoundException;
import com.codewithduregesh.blog.payloads.PostDto;
import com.codewithduregesh.blog.payloads.PostResponse;
import com.codewithduregesh.blog.repositories.CategoryRepositoryI;
import com.codewithduregesh.blog.repositories.PostRepositoryI;
import com.codewithduregesh.blog.repositories.UserRepositoryI;
import com.codewithduregesh.blog.services.PostServiceI;

@Service
public class PostServiceImpl implements PostServiceI {

	@Autowired
	private PostRepositoryI postRepositoryI;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepositoryI userRepositoryI;

	@Autowired
	private CategoryRepositoryI categoryRepositoryI;

	@Override
	public PostDto createPost(PostDto postDto, Long userId, Long categoryId) {

		User user = this.userRepositoryI.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User id", userId));

		Category category = this.categoryRepositoryI.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category id", categoryId));

		Post post = this.modelMapper.map(postDto, Post.class);

		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);

		Post newpost = this.postRepositoryI.save(post);

		return this.modelMapper.map(newpost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Long postId) {

		Post post = this.postRepositoryI.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));

		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());

		Post save = this.postRepositoryI.save(post);

		return this.modelMapper.map(save, PostDto.class);
	}

	@Override
	public void deletePost(Long postId) {

		Post post = this.postRepositoryI.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));

		this.postRepositoryI.delete(post);
	}

	@Override
	public PostResponse getAllPost(int pageNumber, int pageSize, String sortBy,String sortDir) {

		// int pageSize = 5;
		// int pageNumber = 1;
		
		Sort sort=null;
		if(sortDir.equalsIgnoreCase("asc"))
		{
			sort=Sort.by(sortBy).ascending();
			
		}else {
			sort=Sort.by(sortBy).descending();
		
		}
		
		Pageable p = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());

		Page<Post> pagepost = this.postRepositoryI.findAll(p);

		List<Post> allposts = pagepost.getContent();

		List<PostDto> postDtos = allposts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();

		postResponse.setContenet(postDtos);
		postResponse.setPageNumber(pagepost.getNumber());
		postResponse.setPageSize(pagepost.getSize());
		postResponse.setTotalElement(pagepost.getTotalElements());
		postResponse.setTotalPages(pagepost.getTotalPages());
		postResponse.setLastPage(pagepost.isLast());

		return postResponse;
	}

	@Override
	public PostDto getPostById(Long postId) {

		Post post = this.postRepositoryI.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getAllPostByCategory(Long categoryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PostDto> getPostByUser(Long userId) {
		// TODO Auto-generated method stub
		return null;

	}

	@Override
	public List<PostDto> searchPost(String keyword) {

	 List<Post> posts = this.postRepositoryI.searchPostByTitleContaining(keyword);
	
	  List<PostDto> postDto = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList()); 
	 
	 return postDto;
	}

}
