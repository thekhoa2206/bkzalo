package com.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.entities.Post;

import org.springframework.stereotype.Repository;

import com.web.entities.Post;


@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {

}
