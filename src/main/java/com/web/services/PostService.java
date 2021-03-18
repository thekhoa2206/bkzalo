package com.web.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.web.entities.Post;
import com.web.repositories.PostRepo;

@Service
public class PostService {

	@Autowired
	public PostRepo postRepo;
	@PersistenceContext
	protected EntityManager entityManager;

//	public List<Post> findAll() {
//		return this.postRepo.findAll();
//	}
	public Post findPostById(final int id) {

		String sql = "select * from tbl_posts where id = '" + id + "'";
		Query query = entityManager.createNativeQuery(sql, Post.class);
		return (Post) query.getSingleResult();
	}

	public List<Post> findAllPostUser() {
		String sql = "select * from tbl_posts";
		Query query = entityManager.createNativeQuery(sql, Post.class);
		return query.getResultList();
	}

	public void deletePostById(@PathVariable int id) {
		postRepo.deleteById(id);
	}

}
