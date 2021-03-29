package com.web.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.entities.AjaxResponse;
import com.web.entities.User;
import com.web.repositories.ResponseRepo;

@Service
public class ResponseService {
	@PersistenceContext
	protected EntityManager entityManager;
	@Autowired
	public ResponseRepo responseRepo;

	public AjaxResponse findResponseByCode(int code) {
		String sql = "select * from tbl_response where code = '" + code + "'";
		Query query = entityManager.createNativeQuery(sql, User.class);
		return (AjaxResponse) query.getSingleResult();
	}
}
