package com.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.entities.Comment;
import com.web.entities.User;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
