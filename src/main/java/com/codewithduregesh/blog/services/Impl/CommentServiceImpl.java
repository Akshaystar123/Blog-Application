package com.codewithduregesh.blog.services.Impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithduregesh.blog.entities.Comment;
import com.codewithduregesh.blog.entities.Post;
import com.codewithduregesh.blog.exceptions.ResourceNotFoundException;
import com.codewithduregesh.blog.payloads.CommentDto;
import com.codewithduregesh.blog.repositories.CommentRepositoryI;
import com.codewithduregesh.blog.repositories.PostRepositoryI;
import com.codewithduregesh.blog.services.CommentServiceI;

@Service
public class CommentServiceImpl implements CommentServiceI {

	@Autowired
	private PostRepositoryI postRepositoryI;

	@Autowired
	private CommentRepositoryI commentRepositoryI;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Long postId) {

		Post post = this.postRepositoryI.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));

		Comment comment = this.modelMapper.map(commentDto, Comment.class);

		comment.setPost(post);

		Comment save = this.commentRepositoryI.save(comment);

		return this.modelMapper.map(save, CommentDto.class);
	}

	@Override
	public void deleteComment(Long commentId) {

		Comment comment = this.commentRepositoryI.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "commenetId", commentId));

		this.commentRepositoryI.delete(comment);
	}

}
