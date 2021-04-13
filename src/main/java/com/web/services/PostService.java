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
import com.web.entities.Comment;
import com.web.entities.Post;
import com.web.entities.User;
import com.web.repositories.PostRepo;

@Service
public class PostService {
	public final char SPACE = ' ';
	public final char TAB = '\t';
	public final char BREAK_LINE = '\n';

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
	
	public List<Post> findAllPostUserId(final int id) {
		String sql = "select * from tbl_posts where user_id ='"+ id +"'";
		Query query = entityManager.createNativeQuery(sql, Post.class);
		return query.getResultList();
	}

	public List<Comment> findAllCommentByIdPost(int idPost) {
		String sql = "select id,user_id, content, create_date from tbl_comment, tbl_comment_post where tbl_comment_post.id_post ='"
				+ idPost + "' and tbl_comment_post.id_comment = tbl_comment.id";
		Query query = entityManager.createNativeQuery(sql, Comment.class);
		return query.getResultList();
	}

	public void deletePostById(@PathVariable int id) {
		postRepo.deleteById(id);
	}

//	seachPost
	@SuppressWarnings("unchecked")
	public List<Post> search(final SearchSomethings searchSomethings) {

		String jpql = "Select p from Post p where CONCAT(p.content,' ', p.media) LIKE '%"
				+ searchSomethings.getKeyword() + "%'";
		Query query = entityManager.createQuery(jpql, Post.class);
		return query.getResultList();
	}

//	searchUser
	@SuppressWarnings("unchecked")
	public List<User> searchUser(final SearchSomethings searchSomethings) {

		String jpql = "Select p from User p where CONCAT(p.phone,' ', p.name) LIKE '%" + searchSomethings.getKeyword()
				+ "%'";
		Query query = entityManager.createQuery(jpql, User.class);
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

	// Hàm đếm số từ trong chuỗi
	public int countWords(String input) {
		if (input == null) {
			return -1;
		}
		int count = 0;
		int size = input.length();
		boolean notCounted = true;
		for (int i = 0; i < size; i++) {
			if (input.charAt(i) != SPACE && input.charAt(i) != TAB && input.charAt(i) != BREAK_LINE) {
				if (notCounted) {
					count++;
					notCounted = false;
				}
			} else {
				notCounted = true;
			}
		}
		return count;
	}
}
