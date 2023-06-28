package com.codewithduregesh.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codewithduregesh.blog.config.AppConstants;
import com.codewithduregesh.blog.entities.Post;
import com.codewithduregesh.blog.payloads.ApiResponse;
import com.codewithduregesh.blog.payloads.PostDto;
import com.codewithduregesh.blog.payloads.PostResponse;
import com.codewithduregesh.blog.services.FileServiceI;
import com.codewithduregesh.blog.services.PostServiceI;



@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostServiceI postServiceI;

	@Autowired
	private FileServiceI fileServiceI;

	@Value("${project.image}")
	private String path;
	
	private static Logger logger= LoggerFactory.getLogger(PostController.class);


	/**
	 * @author Akshay 
	 * @apiNote create post 
	 * @param postDto
	 * @return
	 */
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createpost(@RequestBody PostDto postDto, @PathVariable Long userId,
			@PathVariable Long categoryId) {

		logger.info("Initiating service call for creating post"+userId);

		PostDto createPost = this.postServiceI.createPost(postDto, userId, categoryId);

		logger.info("Completed service call for creating post"+userId);

		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
	}

	/**
	 * @author Akshay 
	 * @apiNote get post 
	 * @param userId
	 * @return
	 */
	
	// get by user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Long userId) {
		
		logger.info("Initiating service call for get post by user"+userId);

		List<PostDto> posts = this.postServiceI.getPostByUser(userId);
		
		logger.info("Completed service call for get post by user"+userId);

		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
	}
	/**
	 * @author Akshay 
	 * @apiNote get post 
	 * @param comment
	 * @return
	 */
	
	// get by category

	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Long categoryId) {
		
		logger.info("Initiating service call for get post by category"+categoryId);
		
		List<PostDto> posts = this.postServiceI.getAllPostByCategory(categoryId);
		
		logger.info("Completed service call for get post by category"+categoryId);

		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
	}

	/**
	 * @author Akshay 
	 * @apiNote get all post 
	 * @return
	 */
	// get all posts

	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
		
		logger.info("Initiating service call for get Allpost by pagination");

		PostResponse allPost = this.postServiceI.getAllPost(pageNumber, pageSize, sortBy, sortDir);
		
		logger.info("Completed service call for get Allpost by pagination");

		return new ResponseEntity<PostResponse>(allPost, HttpStatus.OK);
	}
	/**
	 * @author Akshay 
	 * @apiNote get post by id
	 * @param postId
	 * @return
	 */
	
	// get post details by id

	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Long postId) {
		
		logger.info("Initiating service call for get single post"+postId);

		PostDto postDto = this.postServiceI.getPostById(postId);
		
		logger.info("Completed service call for get single post"+postId);

		return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
	}

	// delete post

	@DeleteMapping("/posts/{postId}")
	public ApiResponse deletePost(@PathVariable Long postId) {
		
		logger.info("Initiating service call for detete post"+postId);

		this.postServiceI.deletePost(postId);
		
		logger.info("Completed service call for detete post"+postId);

		return new ApiResponse("Post is successfully deleted..!!", true);
	}

	/**
	 * @author Akshay 
	 * @apiNote create comment 
	 * @param comment
	 * @return
	 */
	// update post

	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Long postId) {

		logger.info("Initiating service call for update post"+postId);

		PostDto updatePost = this.postServiceI.updatePost(postDto, postId);
		
		logger.info("Completed service call for update post"+postId);

		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}

	/**
	 * @author Akshay 
	 * @apiNote search Post By Title
	 * @param keyword
	 * @return
	 */
	// search
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keyword") String keyword) {

		logger.info("Initiating service call for search post by title"+keyword);

		List<PostDto> result = this.postServiceI.searchPost(keyword);

		logger.info("Completed service call for search post by title"+keyword);

		return new ResponseEntity<List<PostDto>>(result, HttpStatus.OK);
	}

	/**
	 * @author Akshay 
	 * @apiNote upload Post image 
	 * @param comment
	 * @return
	 */
	@PostMapping("/post/upload/image/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,
			@PathVariable Long postId) throws IOException {
		
		logger.info("Initiating service call for upload image"+postId);

		PostDto postDto = this.postServiceI.getPostById(postId);

		String fileName = this.fileServiceI.uploadImage(path, image);

		postDto.setImageName(fileName);
		PostDto updatePost = this.postServiceI.updatePost(postDto, postId);

		logger.info("Completed service call for upload image"+postId);

		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}

	/**
	 * @author Akshay 
	 * @apiNote download image 
	 * @param 
	 * @return
	 */
	
	// method to serve files

	@GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response)
			throws IOException {
		
		logger.info("Initiating service call for download image"+imageName);

		InputStream resource = this.fileServiceI.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
		
		logger.info("Completed service call for download image"+imageName);


	}
	
}
