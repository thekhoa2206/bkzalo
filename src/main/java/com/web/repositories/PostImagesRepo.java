package com.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.entities.PostImages;

@Repository 
public interface PostImagesRepo extends JpaRepository<PostImages, Integer> {

}
