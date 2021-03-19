package com.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD

import com.web.entities.Post;

public interface PostRepo extends JpaRepository<Post, Integer>{
=======
import org.springframework.stereotype.Repository;

import com.web.entities.Post;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {

>>>>>>> ddcc72f034452c79ad6d61a8dfdd7bb6d4e89026

}
