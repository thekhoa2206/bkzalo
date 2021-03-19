package com.web.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.web.entities.Comment;
import com.web.entities.User;
import com.web.repositories.CommentRepo;
import com.web.repositories.PostRepo;

@Service
public class CommentService {
	@PersistenceContext
	protected EntityManager entityManager;

	@Autowired
	public CommentRepo commentRepo;

	@Autowired
	public PostRepo postRepo;

	public Comment findCommentById(final int id) {

		String sql = "select * from tbl_comment where id = '" + id + "'";

		Query query = entityManager.createNativeQuery(sql, Comment.class);
		return (Comment) query.getSingleResult();
	}

	public List<Comment> findPostCommentById(final int id) {

		String sql = "select * from tbl_comment,tbl_comment_post where tbl_comment_post.id_post =  '" + id + "'";
		Query query = entityManager.createNativeQuery(sql, Comment.class);
		return query.getResultList();
	}

	@Transactional(rollbackOn = Exception.class)
	public void deleteComment(int idComment) {
		String sql = "delete from tbl_comment where id = '" + idComment + "'";
		Query query = entityManager.createNativeQuery(sql);
		query.executeUpdate();
	}

	@Transactional(rollbackOn = Exception.class)
	public void saveComment(Comment data) throws Exception {
		try {
			data.setCreatedDate(java.time.LocalDateTime.now());
			commentRepo.save(data);
		} catch (Exception e) {
			throw e;
		}
	}

}
