package com.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.entities.Post;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {

}
