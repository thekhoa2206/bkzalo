package com.web.services;

import java.io.File;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
<<<<<<< HEAD
	
//	public FakeUser convertUserToFakeUser(User user) {
//		return new FakeUser(user);
//	}
=======

	public User finUserByPhone(final String phone) {
		String sql = "select * from tbl_users where phone = '" + phone + "'";
		Query query = entityManager.createNativeQuery(sql, User.class);
		return (User) query.getSingleResult();
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
>>>>>>> 59ce72850f9e23457adeb21f36afae4fef697298
}
