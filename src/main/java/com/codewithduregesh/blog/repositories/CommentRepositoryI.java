package com.codewithduregesh.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithduregesh.blog.entities.Comment;

public interface CommentRepositoryI extends JpaRepository<Comment, Long>{

}
