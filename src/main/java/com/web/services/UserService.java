package com.web.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.web.entities.Friend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.entities.Post;
import com.web.entities.User;
import com.web.repositories.UserRepo;

@Service
public class UserService {
	@PersistenceContext
	protected EntityManager entityManager;
	@Autowired
	public UserRepo userRepo;

	public User findUserById(final int id) {

		String sql = "select * from tbl_users where id = '" + id + "'";
		Query query = entityManager.createNativeQuery(sql, User.class);
		return (User) query.getSingleResult();
	}

	public User finUserByPhone(final String phone) {
		String sql = "select * from tbl_users where phone = '" + phone + "'";
		Query query = entityManager.createNativeQuery(sql, User.class);
		return (User) query.getSingleResult();
	}

	public List<Post> findPostById(final int id) {
		String sql = "select * from tbl_posts where id = '" + id + "'";
		Query query = entityManager.createNativeQuery(sql, Post.class);
		return query.getResultList();
	}

	public List<Friend> findFriendById(final int id) {
//		String sql = "select * from tbl_friends AS t1, tbl_users AS t2 where t1.id_user_b = '" + id + "' AND t2.id = '" + t1.id_user_a + "' ";
//		String sql = "select * from tbl_friends , tbl_users where tbl_friends.id_user_b = '" + id + "' AND tbl_users.id = '" + tbl_friends.id_user_a + "' ";
		String sql = "select * from tbl_friends where id_user_b = '" + id + "' ";

		Query query = entityManager.createNativeQuery(sql, Friend.class);
		return query.getResultList();
	}

	@Transactional(rollbackOn = Exception.class)
	public void saveUser(User user) throws Exception {
		try {
			if (user.getId() != null) { // chỉnh sửa
				// lấy dữ liệu cũ của sản phẩm
				User userInDb = userRepo.findById(user.getId()).get();

			}

			userRepo.save(user);
		} catch (Exception e) {
			throw e;
		}
	}

	@Transactional(rollbackOn = Exception.class)
	public void saveGuestUser(User user) throws Exception {
		try {
			userRepo.save(user);
		} catch (Exception e) {
			throw e;
		}
	}

}
