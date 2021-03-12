package com.web.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
