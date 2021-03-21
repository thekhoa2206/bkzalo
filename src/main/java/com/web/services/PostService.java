package com.web.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.web.common.SearchSomethings;
import com.web.entities.Post;
import com.web.repositories.PostRepo;

@Service
public class PostService {

	@Autowired
	public PostRepo postRepo;
	@PersistenceContext
	protected EntityManager entityManager;

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

	@SuppressWarnings("unchecked")
	public List<Post> search(final SearchSomethings searchSomethings) {

		String jpql = "Select p from Post p where CONCAT(p.content,' ', p.media) LIKE '%"
				+ searchSomethings.getKeyword() + "%'";
		Query query = entityManager.createQuery(jpql, Post.class);
		return query.getResultList();
	}

	@Transactional(rollbackOn = Exception.class)
	public void savePost(Post post) throws Exception {
		try {
			if (post.getId() != null) { // chỉnh sửa
				// lấy dữ liệu cũ của sản phẩm
				Post postInDb = postRepo.findById(post.getId()).get();

			}
			postRepo.save(post);
		} catch (Exception e) {
			throw e;
		}
	}
}
