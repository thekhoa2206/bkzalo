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
import com.web.entities.ChatMessage;
import com.web.entities.Comment;
import com.web.entities.Post;
import com.web.entities.Report;
import com.web.entities.User;
import com.web.repositories.PostRepo;
import com.web.repositories.ReportRepo;

@Service
public class PostService {
	public final char SPACE = ' ';
	public final char TAB = '\t';
	public final char BREAK_LINE = '\n';

	@Autowired
	public PostRepo postRepo;
	@PersistenceContext
	protected EntityManager entityManager;
	@Autowired
	ReportRepo reportRepo;

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
		String sql = "select * from tbl_posts where user_id ='" + id + "'";
		Query query = entityManager.createNativeQuery(sql, Post.class);
		return query.getResultList();
	}

	public List<Comment> findAllCommentByIdPost(int idPost) {
		String sql = "select id,user_id, content, create_date from tbl_comment, tbl_comment_post where tbl_comment_post.id_post ='"
				+ idPost + "' and tbl_comment_post.id_comment = tbl_comment.id";
		Query query = entityManager.createNativeQuery(sql, Comment.class);
		return query.getResultList();
	}
	public Post findPostbyIdAndUserId(final int id,final int user_id) {
		String sql = "select * from tbl_posts where id = '" + id + "' AND user_id = '"+user_id +"'";
		Query query = entityManager.createNativeQuery(sql, Post.class);
		return (Post) query.getSingleResult();
	}
	@Transactional(rollbackOn = Exception.class)
	public void deletePostById(final int id, final int user_id) {
		String sql = "delete from tbl_posts where user_id = '" + user_id + "' AND id = '" + id + "'";
		Query query = entityManager.createNativeQuery(sql);
		query.executeUpdate();
	}


	@SuppressWarnings("unchecked")
	public List<Post> search(final String keyword) {

		String sql = "Select p from Post p  where CONCAT(p.content,' ', p.media) LIKE '%"
				+ keyword + "%'";
		Query query = entityManager.createQuery(sql, Post.class);
		return query.getResultList();
	}
	@SuppressWarnings("unchecked")
	public List<User> searchUser(final String keyword) {

		String sql = "Select u from User u  where CONCAT(u.name,' ', u.phone) LIKE '%"
				+ keyword + "%'";
		Query query = entityManager.createQuery(sql, User.class);
		return query.getResultList();
	}
	@SuppressWarnings("unchecked")
	public List<ChatMessage> searchUserChat(final String keyword) {

		String sql = "Select c from ChatMessage c  where CONCAT(c.content,' ') LIKE '%"
				+ keyword + "%'";
		Query query = entityManager.createQuery(sql, ChatMessage.class);
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

	@Transactional(rollbackOn = Exception.class)
	public void saveReport(Report report) throws Exception {
		try {
			if (report.getId() != null) { // chỉnh sửa
				// lấy dữ liệu cũ của sản phẩm
				Report reportInDb = reportRepo.findById(report.getId()).get();

			}
			reportRepo.save(report);
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
