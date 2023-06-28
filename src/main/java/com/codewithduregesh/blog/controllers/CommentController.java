package com.codewithduregesh.blog.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithduregesh.blog.config.AppConstants;
import com.codewithduregesh.blog.payloads.ApiResponse;
import com.codewithduregesh.blog.payloads.CommentDto;
import com.codewithduregesh.blog.services.CommentServiceI;

@RestController
@RequestMapping("/api/")
public class CommentController {

	@Autowired
	private CommentServiceI commentServiceI;
	
	private static Logger logger= LoggerFactory.getLogger(CommentController.class);


	/**
	 * @author Akshay 
	 * @apiNote create comment 
	 * @param comment
	 * @return
	 */
	
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comment, @PathVariable Long postId) {

		logger.info("Initiating service call for creating comment"+postId);

		CommentDto createComment = this.commentServiceI.createComment(comment, postId);

		logger.info("Completed service call for created comment"+postId);

		return new ResponseEntity<CommentDto>(createComment, HttpStatus.OK);
	}

	/**
	 * @author Akshay 
	 * @apiNote delete comment 
	 * @param commentId
	 * @return
	 */
	
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Long commentId) {
		
		logger.info("Initiating service call for creating comment"+commentId);

		this.commentServiceI.deleteComment(commentId);
		
		logger.info("Initiating service call for creating comment"+commentId);

		return new ResponseEntity<ApiResponse>(new ApiResponse(AppConstants.DELETE_COMMENT, true),
				HttpStatus.OK);
	}

}
