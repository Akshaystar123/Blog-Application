package com.codewithduregesh.blog.services;

import com.codewithduregesh.blog.payloads.CommentDto;

public interface CommentServiceI {

	CommentDto createComment(CommentDto commentDto, Long postId);

	void deleteComment(Long commentId);
}
